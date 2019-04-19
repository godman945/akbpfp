package com.pchome.akbpfp.db.service.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.pchome.akbpfp.db.dao.report.AdVideoPerformanceReportVO;
import com.pchome.akbpfp.db.dao.report.IAdVideoPerformanceReportDAO;
import com.pchome.akbpfp.db.vo.report.ReportQueryConditionVO;

public class AdVideoPerformanceReportService implements IAdVideoPerformanceReportService {

	private IAdVideoPerformanceReportDAO adVideoPerformanceReportDAO;

	public void setAdVideoPerformanceReportDAO(IAdVideoPerformanceReportDAO adVideoPerformanceReportDAO) {
		this.adVideoPerformanceReportDAO = adVideoPerformanceReportDAO;
	}

	@Override
	public List<AdVideoPerformanceReportVO> loadReportDateList(ReportQueryConditionVO reportQueryConditionVO) throws Exception {
		List<Object> list = adVideoPerformanceReportDAO.getReportDataList(reportQueryConditionVO);
		
		List<AdVideoPerformanceReportVO> adVideoPerformanceReportVOList = new ArrayList<>();
		for (Object object : list) {
			Object[] objArray = (Object[]) object;
			AdVideoPerformanceReportVO adVideoPerformanceReportVO = new AdVideoPerformanceReportVO();
			adVideoPerformanceReportVO.setAdSeq((String) objArray[0]);
			adVideoPerformanceReportVO.setAdStatus(objArray[1].toString());
			adVideoPerformanceReportVO.setTitle(objArray[2].toString());
			adVideoPerformanceReportVO.setAdPriceType(objArray[3].toString());
			adVideoPerformanceReportVO.setAdPvClkDevice(objArray[4].toString());
			adVideoPerformanceReportVO.setAdPvSum(objArray[5].toString());
			adVideoPerformanceReportVO.setAdViewSum(objArray[6].toString());
			adVideoPerformanceReportVO.setAdViewRatings(objArray[7].toString());
			adVideoPerformanceReportVO.setSingleAdViewCost(objArray[8].toString());
			adVideoPerformanceReportVO.setThousandsCost(objArray[9].toString());
			adVideoPerformanceReportVO.setCostSum(objArray[10].toString());
			adVideoPerformanceReportVO.setAdVideoProcess25Sum(objArray[11].toString());
			adVideoPerformanceReportVO.setAdVideoProcess50Sum(objArray[12].toString());
			adVideoPerformanceReportVO.setAdVideoProcess75Sum(objArray[13].toString());
			adVideoPerformanceReportVO.setAdVideoProcess100Sum(objArray[14].toString());
			adVideoPerformanceReportVO.setAdVideoUniqSum(objArray[15].toString());
			adVideoPerformanceReportVO.setAdVideoMusicSum(objArray[16].toString());
			adVideoPerformanceReportVO.setAdVideoReplaySum(objArray[17].toString());
			adVideoPerformanceReportVO.setAdClkSum(objArray[18].toString());
			adVideoPerformanceReportVO.setAdImg(objArray[19].toString());
			adVideoPerformanceReportVO.setAdVideoProcess100Ratings(objArray[20].toString());
			adVideoPerformanceReportVO.setAdLinkUrl(objArray[21].toString());
			adVideoPerformanceReportVO.setVideoUrl(objArray[22].toString());
			String device = "";
			if(StringUtils.isBlank(reportQueryConditionVO.getAdPvclkDevice())){
				device = "全部";
			}else{
				if(reportQueryConditionVO.getAdPvclkDevice().equals("PC")){
					device = "電腦";
				}
				if(reportQueryConditionVO.getAdPvclkDevice().equals("mobile")){
					device = "行動裝置";
				}
			}
			adVideoPerformanceReportVO.setDevice(device);
			if(StringUtils.isNotBlank(objArray[23].toString())){
				String size[] = objArray[23].toString().split("_");
				if(size.length == 2){
					adVideoPerformanceReportVO.setTemplateProductWidth(size[0]);
					adVideoPerformanceReportVO.setTemplateProductHeight(size[1]);
				}
			}
			adVideoPerformanceReportVO.setAdActionName(objArray[24].toString());
			adVideoPerformanceReportVO.setAdVideoUniqSum(objArray[26].toString());
			String sec = objArray[27].toString();
			if(sec.length() == 1){
				sec= "0"+sec;
			}
			adVideoPerformanceReportVO.setAdVideoSec(sec);
			adVideoPerformanceReportVOList.add(adVideoPerformanceReportVO);
		}
		return adVideoPerformanceReportVOList;
	}

	public List<AdVideoPerformanceReportVO> loadReportChart(ReportQueryConditionVO reportQueryConditionVO) throws Exception {
		List<Object> list = adVideoPerformanceReportDAO.getReportChart(reportQueryConditionVO);
		List<AdVideoPerformanceReportVO> adVideoPerformanceReportVOList = new ArrayList<>();
		for (Object object : list) {
			Object[] objArray = (Object[]) object;
			AdVideoPerformanceReportVO adVideoPerformanceReportVO = new AdVideoPerformanceReportVO();
			adVideoPerformanceReportVO.setReportDate((Date) objArray[1]);
			adVideoPerformanceReportVO.setAdPvSum(objArray[4].toString());
			adVideoPerformanceReportVO.setAdViewSum(objArray[5].toString());
			adVideoPerformanceReportVO.setAdViewRatings(objArray[6].toString());
			adVideoPerformanceReportVO.setSingleAdViewCost(objArray[7].toString());
			adVideoPerformanceReportVO.setThousandsCost(objArray[8].toString());
			adVideoPerformanceReportVO.setCostSum(objArray[9].toString());
			adVideoPerformanceReportVO.setAdVideoProcess100Ratings(objArray[10].toString());
			adVideoPerformanceReportVO.setAdClkSum(objArray[11].toString());
			adVideoPerformanceReportVO.setAdVideoMusicSum(objArray[12].toString());
			adVideoPerformanceReportVO.setAdVideoReplaySum(objArray[13].toString());
			adVideoPerformanceReportVO.setAdVideoUniqSum(objArray[20].toString());
			adVideoPerformanceReportVOList.add(adVideoPerformanceReportVO);
		}
		return adVideoPerformanceReportVOList;
	}

	@Override
	public List<AdVideoPerformanceReportVO> loadReportDateCount(ReportQueryConditionVO reportQueryConditionVO) throws Exception {
		List<Object> list = adVideoPerformanceReportDAO.getReportCount(reportQueryConditionVO);
		List<AdVideoPerformanceReportVO> adVideoPerformanceReportVOList = new ArrayList<>();
		for (Object object : list) {
			Object[] objArray = (Object[]) object;
			AdVideoPerformanceReportVO adVideoPerformanceReportVO = new AdVideoPerformanceReportVO();
			adVideoPerformanceReportVO.setAdSeq((String) objArray[0]);
			adVideoPerformanceReportVO.setAdStatus(objArray[1].toString());
			adVideoPerformanceReportVO.setTitle(objArray[2].toString());
			adVideoPerformanceReportVO.setAdPriceType(objArray[3].toString());
			adVideoPerformanceReportVO.setAdPvClkDevice(objArray[4].toString());
			adVideoPerformanceReportVO.setAdPvSum(objArray[5].toString());
			adVideoPerformanceReportVO.setAdViewSum(objArray[6].toString());
			adVideoPerformanceReportVO.setAdViewRatings(objArray[7].toString());
			adVideoPerformanceReportVO.setSingleAdViewCost(objArray[8].toString());
			adVideoPerformanceReportVO.setThousandsCost(objArray[9].toString());
			adVideoPerformanceReportVO.setCostSum(objArray[10].toString());
			adVideoPerformanceReportVO.setAdVideoProcess25Sum(objArray[11].toString());
			adVideoPerformanceReportVO.setAdVideoProcess50Sum(objArray[12].toString());
			adVideoPerformanceReportVO.setAdVideoProcess75Sum(objArray[13].toString());
			adVideoPerformanceReportVO.setAdVideoProcess100Sum(objArray[14].toString());
			adVideoPerformanceReportVO.setAdVideoUniqSum(objArray[15].toString());
			adVideoPerformanceReportVO.setAdVideoMusicSum(objArray[16].toString());
			adVideoPerformanceReportVO.setAdVideoReplaySum(objArray[17].toString());
			adVideoPerformanceReportVO.setAdClkSum(objArray[18].toString());
			adVideoPerformanceReportVO.setAdImg(objArray[19].toString());
			adVideoPerformanceReportVO.setAdVideoProcess100Ratings(objArray[20].toString());
			adVideoPerformanceReportVO.setAdLinkUrl(objArray[21].toString());
			adVideoPerformanceReportVO.setVideoUrl(objArray[22].toString());
			if(StringUtils.isNotBlank(objArray[23].toString())){
				String size[] = objArray[23].toString().split("_");
				if(size.length == 2){
					adVideoPerformanceReportVO.setTemplateProductWidth(size[0]);
					adVideoPerformanceReportVO.setTemplateProductHeight(size[1]);
				}
			}
			adVideoPerformanceReportVO.setAdActionName(objArray[24].toString());
			adVideoPerformanceReportVO.setAdVideoUniqSum(objArray[26].toString());
			adVideoPerformanceReportVO.setAdVideoSec(objArray[27].toString());
			adVideoPerformanceReportVOList.add(adVideoPerformanceReportVO);
		}
		return adVideoPerformanceReportVOList;
	}
}
