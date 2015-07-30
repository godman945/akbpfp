package com.pchome.akbpfp.db.service.report;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.pchome.akbpfp.db.dao.ad.PfpAdDetailDAO;
import com.pchome.akbpfp.db.dao.report.AdReportVO;
import com.pchome.akbpfp.db.dao.report.IAdReportDAO;
import com.pchome.akbpfp.db.pojo.PfpAdDetail;
import com.pchome.enumerate.report.EnumReport;

public class AdReportService implements IAdReportService {

	private IAdReportDAO adReportDAO;
	private PfpAdDetailDAO pfpAdDetailDAO;

    public void setAdReportDAO(IAdReportDAO adReportDAO) {
		this.adReportDAO = adReportDAO;
	}

	public void setPfpAdDetailDAO(PfpAdDetailDAO pfpAdDetailDAO) {
		this.pfpAdDetailDAO = pfpAdDetailDAO;
	}

	@Override
    public List<AdReportVO> loadReportDate(String sqlType, String adGroupSeq, String searchText, String adSearchWay, String adShowWay, String adPvclkDevice, String customerInfoId,String startDate, String endDate,int page,int pageSize) throws Exception {

		List<AdReportVO> dataList = adReportDAO.getReportList(sqlType, adGroupSeq, searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, startDate, endDate, page, pageSize);

		//補上預覽 html code
		if (sqlType.trim().equals(EnumReport.REPORT_HQLTYPE_ADVERTISE.getTextValue())){
		    List<PfpAdDetail> pfpAdDetailList = null;
		    String realUrl = null;
		    String img = null;
		    String adStyle = null;

			for (int i=0; i<dataList.size(); i++) {
				AdReportVO adReportVO = dataList.get(i);

				pfpAdDetailList = pfpAdDetailDAO.getPfpAdDetailByAdSeq(adReportVO.getAdSeq());
	            realUrl = null;
	            img = null;
	            adStyle = null;
				for (PfpAdDetail pfpAdDetail: pfpAdDetailList) {
                    if ("real_url".equals(pfpAdDetail.getAdDetailId())) {
                        realUrl = pfpAdDetail.getAdDetailContent();
                    }
                    if ("img".equals(pfpAdDetail.getAdDetailId())) {
                        img = pfpAdDetail.getAdDetailContent();
                    }
                    if (StringUtils.isBlank(adStyle)) {
                        adStyle = pfpAdDetail.getPfpAd().getAdStyle();
                    }
				}

				//String htmlCode = "<iframe height=\"120\" width=\"350\" src=\"adModel.html?adNo=" + adReportVO.getAdSeq() + "&tproNo=" + adReportVO.getTemplateProductSeq() + "\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" frameborder=\"0\" align=\"ceneter\" class=\"akb_iframe\"></iframe>";
				String htmlCode = "<span><iframe height=\"120\" width=\"350\" src=\"adModel.html?adNo=" + adReportVO.getAdSeq() + "&tproNo=tpro_201406300001\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" frameborder=\"0\" align=\"ceneter\" class=\"akb_iframe\"></iframe></span>";
				if ("IMG".equals(adStyle)) {
				    htmlCode += ("<span>" + realUrl + "<p><a style=\"cursor:pointer\" onclick=\"preview('" + img + "')\">預覽</a></span>");
				}

				adReportVO.setAdPreview(htmlCode);

				String adSeq = adReportVO.getAdSeq();
				List<PfpAdDetail> adPropertiesList = pfpAdDetailDAO.getPfpAdDetails(null, adSeq, null, null);
				for (int k=0; k<adPropertiesList.size(); k++) {
					PfpAdDetail adDetail = adPropertiesList.get(k);
					String adPropertiesName = adDetail.getAdDetailId();
					String adPropertiesValue = adDetail.getAdDetailContent();
					if (adPropertiesName.equals("title")) {
						adReportVO.setTitle(adPropertiesValue);
					} else if (adPropertiesName.equals("content")) {
						adReportVO.setContent(adPropertiesValue);
					} else if (adPropertiesName.equals("real_url")) {
						adReportVO.setRealUrl(adPropertiesValue);
					} else if (adPropertiesName.equals("show_url")) {
						adReportVO.setShowUrl(adPropertiesValue);
					}
				}
			}
		}

		return dataList;
	}
}
