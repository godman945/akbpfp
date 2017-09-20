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
	public List<AdVideoPerformanceReportVO> loadReportDate(ReportQueryConditionVO reportQueryConditionVO) throws Exception {
		
		List<Object> list = adVideoPerformanceReportDAO.getReportList(reportQueryConditionVO);
		
		List<AdVideoPerformanceReportVO> adVideoPerformanceReportVOList = new ArrayList<>();
		for (Object object : list) {
			Object[] objArray = (Object[]) object;

			AdVideoPerformanceReportVO adVideoPerformanceReportVO = new AdVideoPerformanceReportVO();
			adVideoPerformanceReportVO.setAdStatus(objArray[0].toString());
			adVideoPerformanceReportVO.setAdGroupName(objArray[1].toString());
			adVideoPerformanceReportVO.setTemplateProductWidth(objArray[2].toString());
			adVideoPerformanceReportVO.setTemplateProductHeight(objArray[3].toString());
			adVideoPerformanceReportVO.setVideoUrl(objArray[4].toString());
			adVideoPerformanceReportVO.setAdPriceType(objArray[5].toString());
			adVideoPerformanceReportVO.setAdPvClkDevice(objArray[6].toString());
			adVideoPerformanceReportVO.setAdPvSum(objArray[7].toString());
			adVideoPerformanceReportVO.setAdViewSum(objArray[8].toString());
			adVideoPerformanceReportVO.setAdViewRatings(objArray[9].toString());
			adVideoPerformanceReportVO.setSingleAdViewCost(objArray[10].toString());
			adVideoPerformanceReportVO.setThousandsCost(objArray[11].toString());
			adVideoPerformanceReportVO.setCostSum(objArray[12].toString());
			adVideoPerformanceReportVO.setAdVideoProcess25Sum(objArray[13].toString());
			adVideoPerformanceReportVO.setAdVideoProcess50Sum(objArray[14].toString());
			adVideoPerformanceReportVO.setAdVideoProcess75Sum(objArray[15].toString());
			adVideoPerformanceReportVO.setAdVideoProcess100Sum(objArray[16].toString());
			adVideoPerformanceReportVO.setAdVideoUniqSum(objArray[17].toString());
			adVideoPerformanceReportVO.setAdVideoMusicSum(objArray[18].toString());
			adVideoPerformanceReportVO.setAdVideoReplaySum(objArray[19].toString());
			adVideoPerformanceReportVO.setAdClkSum(objArray[20].toString());
			adVideoPerformanceReportVO.setAdImg(objArray[21].toString());
			adVideoPerformanceReportVO.setAdVideoProcess100Ratings(objArray[22].toString());
			adVideoPerformanceReportVO.setAdLinkUrl(objArray[23].toString());
			adVideoPerformanceReportVOList.add(adVideoPerformanceReportVO);
		}
		return adVideoPerformanceReportVOList;
	}

	public List<AdVideoPerformanceReportVO> loadReportChart(ReportQueryConditionVO reportQueryConditionVO) throws Exception {
		
		List<Object> list = adVideoPerformanceReportDAO.getReportChartList(reportQueryConditionVO);
		List<AdVideoPerformanceReportVO> adVideoPerformanceReportVOList = new ArrayList<>();
		for (Object object : list) {
			Object[] objArray = (Object[]) object;
			AdVideoPerformanceReportVO adVideoPerformanceReportVO = new AdVideoPerformanceReportVO();
			adVideoPerformanceReportVO.setReportDate((Date) objArray[0]);
			adVideoPerformanceReportVO.setAdPvSum(objArray[1].toString());
			adVideoPerformanceReportVO.setAdViewSum(objArray[2].toString());
			adVideoPerformanceReportVO.setAdViewRatings(objArray[3].toString());
			adVideoPerformanceReportVO.setSingleAdViewCost(objArray[4].toString());
			adVideoPerformanceReportVO.setThousandsCost(objArray[5].toString());
			adVideoPerformanceReportVO.setCostSum(objArray[6].toString());
			adVideoPerformanceReportVO.setAdVideoUniqSum(objArray[7].toString());
			adVideoPerformanceReportVO.setAdVideoMusicSum(objArray[8].toString());
			adVideoPerformanceReportVO.setAdVideoReplaySum(objArray[9].toString());
			adVideoPerformanceReportVO.setAdClkSum(objArray[10].toString());
			adVideoPerformanceReportVO.setAdVideoProcess100Ratings(objArray[11].toString());
			adVideoPerformanceReportVOList.add(adVideoPerformanceReportVO);
		}
		return adVideoPerformanceReportVOList;
	}
}
