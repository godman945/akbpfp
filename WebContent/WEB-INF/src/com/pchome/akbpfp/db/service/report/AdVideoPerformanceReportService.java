package com.pchome.akbpfp.db.service.report;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public AdVideoPerformanceReportVO loadReportDateCount(ReportQueryConditionVO reportQueryConditionVO) throws Exception {
		List<Object> list = adVideoPerformanceReportDAO.getReportCount(reportQueryConditionVO);
		List<AdVideoPerformanceReportVO> adVideoPerformanceReportVOList = new ArrayList<>();
		AdVideoPerformanceReportVO adVideoPerformanceReportVO = new AdVideoPerformanceReportVO();
		
		double costSum = 0;
		int adVideoProcess25Sum = 0;
		int adVideoProcess50Sum = 0;
		int adVideoProcess75Sum = 0;
		int adVideoProcess100Sum = 0;
		double adPvSum = 0;
		double adViewSum = 0;
		int adClkSum = 0;
		int adVideoUniqSum = 0;
		int adVideoMusicSum = 0;
		int adVideoReplaySum = 0;
		for (Object object : list) {
			Object[] objArray = (Object[]) object;
			adPvSum = adPvSum + ((BigDecimal)objArray[5]).doubleValue();
			adViewSum = adViewSum +  ((BigDecimal)objArray[6]).doubleValue();
			costSum = costSum + ((Double)objArray[10]).intValue();
			adVideoProcess25Sum = adVideoProcess25Sum + ((BigDecimal)objArray[11]).intValue();
			adVideoProcess50Sum = adVideoProcess50Sum + ((BigDecimal)objArray[12]).intValue();
			adVideoProcess75Sum = adVideoProcess75Sum + ((BigDecimal)objArray[13]).intValue();
			adVideoProcess100Sum = adVideoProcess100Sum + ((BigDecimal)objArray[14]).intValue();
			adVideoUniqSum = adVideoUniqSum + ((BigDecimal)objArray[26]).intValue();
			adClkSum = adClkSum + ((BigDecimal)objArray[18]).intValue();
			
//			adVideoPerformanceReportVO.setAdSeq((String) objArray[0]);
//			adVideoPerformanceReportVO.setAdStatus(objArray[1].toString());
//			adVideoPerformanceReportVO.setTitle(objArray[2].toString());
//			adVideoPerformanceReportVO.setAdPriceType(objArray[3].toString());
//			adVideoPerformanceReportVO.setAdPvClkDevice(objArray[4].toString());
//			adVideoPerformanceReportVO.setAdPvSum(objArray[5].toString());
//			adVideoPerformanceReportVO.setAdViewSum(objArray[6].toString());
//			adVideoPerformanceReportVO.setAdViewRatings(objArray[7].toString());
//			adVideoPerformanceReportVO.setSingleAdViewCost(objArray[8].toString());
//			adVideoPerformanceReportVO.setThousandsCost(objArray[9].toString());
//			adVideoPerformanceReportVO.setCostSum(objArray[10].toString());
//			adVideoPerformanceReportVO.setAdVideoProcess25Sum(objArray[11].toString());
//			adVideoPerformanceReportVO.setAdVideoProcess50Sum(objArray[12].toString());
//			adVideoPerformanceReportVO.setAdVideoProcess75Sum(objArray[13].toString());
//			adVideoPerformanceReportVO.setAdVideoProcess100Sum(objArray[14].toString());
//			adVideoPerformanceReportVO.setAdVideoUniqSum(objArray[15].toString());
//			adVideoPerformanceReportVO.setAdVideoMusicSum(objArray[16].toString());
//			adVideoPerformanceReportVO.setAdVideoReplaySum(objArray[17].toString());
//			adVideoPerformanceReportVO.setAdClkSum(objArray[18].toString());
//			adVideoPerformanceReportVO.setAdImg(objArray[19].toString());
//			adVideoPerformanceReportVO.setAdVideoProcess100Ratings(objArray[20].toString());
//			adVideoPerformanceReportVO.setAdLinkUrl(objArray[21].toString());
//			adVideoPerformanceReportVO.setVideoUrl(objArray[22].toString());
//			if(StringUtils.isNotBlank(objArray[23].toString())){
//				String size[] = objArray[23].toString().split("_");
//				if(size.length == 2){
//					adVideoPerformanceReportVO.setTemplateProductWidth(size[0]);
//					adVideoPerformanceReportVO.setTemplateProductHeight(size[1]);
//				}
//			}
//			adVideoPerformanceReportVO.setAdActionName(objArray[24].toString());
//			adVideoPerformanceReportVO.setAdVideoUniqSum(objArray[26].toString());
//			adVideoPerformanceReportVO.setAdVideoSec(objArray[27].toString());
//			adVideoPerformanceReportVOList.add(adVideoPerformanceReportVO);
		}
		
		double adViewRatings = 0;
		double singleAdViewCost =0;
		double thousandsCost = 0;
		double adVideoProcess100Ratings = 0;
		//收視率
		adViewRatings = (adViewSum / adPvSum) * 100;
		//單次收視費用
		singleAdViewCost = costSum / adViewSum;
		//千次曝光費用
		thousandsCost = (costSum / adPvSum) * 1000;
		//影片完整播放率
		adVideoProcess100Ratings = ((double)adVideoProcess100Sum) / adPvSum * 100;
		
		adVideoPerformanceReportVO.setAdPvSum(String.valueOf(adPvSum));
		adVideoPerformanceReportVO.setAdViewSum(String.valueOf(adViewSum));
		adVideoPerformanceReportVO.setCostSum(String.valueOf(costSum));
		adVideoPerformanceReportVO.setAdVideoProcess25Sum(String.valueOf(adVideoProcess25Sum));
		adVideoPerformanceReportVO.setAdVideoProcess50Sum(String.valueOf(adVideoProcess50Sum));
		adVideoPerformanceReportVO.setAdVideoProcess75Sum(String.valueOf(adVideoProcess75Sum));
		adVideoPerformanceReportVO.setAdVideoProcess100Sum(String.valueOf(adVideoProcess100Sum));
		adVideoPerformanceReportVO.setAdClkSum(String.valueOf(adClkSum));
		adVideoPerformanceReportVO.setAdVideoMusicSum(String.valueOf(adVideoMusicSum));
		adVideoPerformanceReportVO.setAdVideoUniqSum(String.valueOf(adVideoUniqSum));
		adVideoPerformanceReportVO.setAdVideoReplaySum(String.valueOf(adVideoReplaySum));
		adVideoPerformanceReportVO.setAdViewRatings(String.valueOf(adViewRatings));
		adVideoPerformanceReportVO.setSingleAdViewCost(String.valueOf(singleAdViewCost));
		adVideoPerformanceReportVO.setThousandsCost(String.valueOf(thousandsCost));
		adVideoPerformanceReportVO.setAdVideoProcess100Ratings(String.valueOf(adVideoProcess100Ratings));
		adVideoPerformanceReportVO.setTotalSize(list.size());
		
		
		
//		return adVideoPerformanceReportVOList;
		
		
		
//		List<Object> list = adVideoPerformanceReportDAO.getReportCount(reportQueryConditionVO,"");
//		int adPvSum = 0;
//		int adViewSum = 0;
//		int adClkSum = 0;
//		int adVideoMusicSum = 0;
//		int adVideoReplaySum = 0;
//		int adVideoProcess25Sum = 0;
//		int adVideoProcess50Sum = 0;
//		int adVideoProcess75Sum = 0;
//		int adVideoProcess100Sum = 0;
//		int adVideoUniqSum = 0;
//		double adViewCost = 0;
//		double costSum = 0;
//		for (Object object : list) {
//			Object[] objArray = (Object[]) object;
//			if(objArray[0] == null){
//				break;
//			}
//			
//			adPvSum = adPvSum + ((BigDecimal)objArray[4]).intValue();
//			adViewSum = adViewSum + ((BigDecimal)objArray[5]).intValue();
//			adViewCost = adViewCost + ((Double)objArray[7]);
//			costSum = costSum + ((Double)objArray[9]);
//			adClkSum = adClkSum + ((BigDecimal)objArray[11]).intValue();
//			adVideoMusicSum = adVideoMusicSum + ((BigDecimal)objArray[12]).intValue();
//			adVideoReplaySum = adVideoReplaySum + ((BigDecimal)objArray[13]).intValue();
//			adVideoProcess25Sum = adVideoProcess25Sum + ((BigDecimal)objArray[15]).intValue();
//			adVideoProcess50Sum = adVideoProcess50Sum + ((BigDecimal)objArray[16]).intValue();
//			adVideoProcess75Sum = adVideoProcess75Sum + ((BigDecimal)objArray[17]).intValue();
//			adVideoProcess100Sum = adVideoProcess100Sum + ((BigDecimal)objArray[18]).intValue();
//			adVideoUniqSum = adVideoUniqSum + ((BigDecimal)objArray[20]).intValue();
//		}
//		DecimalFormat df = new DecimalFormat("#.##");
//		//互動率
//		String adViewRatings = df.format((((double)adViewSum / (double)adPvSum) * 100));
//		//千次曝光費用
//		String thousandsCost = df.format((costSum / ((double)adPvSum) * 1000));
//		//影片完整播放率
//		String adVideoProcess100Ratings = df.format(((double)adVideoProcess100Sum / (double)adPvSum) * 100);
//		//單次收視費用
//		String singleAdViewCost = df.format((costSum / (double)adViewSum));
//		//收視率
//		String engagementRate = df.format((((double)adViewSum / (double)adPvSum) * 100));
//		
//		AdVideoPerformanceReportVO adVideoPerformanceReportVO = new AdVideoPerformanceReportVO();
//		adVideoPerformanceReportVO.setAdPvSum(String.valueOf(adPvSum));
//		adVideoPerformanceReportVO.setAdViewSum(String.valueOf(adViewSum));
//		adVideoPerformanceReportVO.setAdClkSum(String.valueOf(adClkSum));
//		adVideoPerformanceReportVO.setAdVideoUniqSum(String.valueOf(adVideoUniqSum));
//		adVideoPerformanceReportVO.setCostSum(df.format(costSum));
//		adVideoPerformanceReportVO.setSingleAdViewCost(singleAdViewCost);
//		adVideoPerformanceReportVO.setAdViewRatings(adViewRatings);
//		adVideoPerformanceReportVO.setThousandsCost(thousandsCost);
//		adVideoPerformanceReportVO.setAdVideoMusicSum(String.valueOf(adVideoMusicSum));
//		adVideoPerformanceReportVO.setAdVideoReplaySum(String.valueOf(adVideoReplaySum));
//		adVideoPerformanceReportVO.setAdVideoProcess25Sum(String.valueOf(adVideoProcess25Sum));
//		adVideoPerformanceReportVO.setAdVideoProcess50Sum(String.valueOf(adVideoProcess50Sum));
//		adVideoPerformanceReportVO.setAdVideoProcess75Sum(String.valueOf(adVideoProcess75Sum));
//		adVideoPerformanceReportVO.setAdVideoProcess100Sum(String.valueOf(adVideoProcess100Sum));
//		adVideoPerformanceReportVO.setAdVideoProcess100Ratings(adVideoProcess100Ratings);
//		adVideoPerformanceReportVO.setEngagementRate(engagementRate);
//		adVideoPerformanceReportVO.setTotalSize(list.size());
		return adVideoPerformanceReportVO;
//		return null;
	}
}
