package com.pchome.akbpfp.db.service.report;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

import com.pchome.akbpfp.db.dao.report.AdVideoPerformanceReportVO;
import com.pchome.akbpfp.db.dao.report.IAdVideoPerformanceReportDAO;
import com.pchome.enumerate.report.EnumReport;
import com.pchome.enumerate.report.EnumReportDevice;
import com.pchome.utils.CommonUtils;

public class AdVideoPerformanceReportService implements IAdVideoPerformanceReportService {

	private IAdVideoPerformanceReportDAO adVideoPerformanceReportDAO;

	public void setAdVideoPerformanceReportDAO(IAdVideoPerformanceReportDAO adVideoPerformanceReportDAO) {
		this.adVideoPerformanceReportDAO = adVideoPerformanceReportDAO;
	}

//	@Override
//	public List<AdVideoPerformanceReportVO> loadReportDateList(ReportQueryConditionVO reportQueryConditionVO) throws Exception {
//		List<Object> list = adVideoPerformanceReportDAO.getReportDataList(reportQueryConditionVO);
//		
//		List<AdVideoPerformanceReportVO> adVideoPerformanceReportVOList = new ArrayList<>();
//		for (Object object : list) {
//			Object[] objArray = (Object[]) object;
//			AdVideoPerformanceReportVO adVideoPerformanceReportVO = new AdVideoPerformanceReportVO();
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
//			String device = "";
//			if(StringUtils.isBlank(reportQueryConditionVO.getAdPvclkDevice())){
//				device = "全部";
//			}else{
//				if(reportQueryConditionVO.getAdPvclkDevice().equals("PC")){
//					device = "電腦";
//				}
//				if(reportQueryConditionVO.getAdPvclkDevice().equals("mobile")){
//					device = "行動裝置";
//				}
//			}
//			adVideoPerformanceReportVO.setDevice(device);
//			if(StringUtils.isNotBlank(objArray[23].toString())){
//				String size[] = objArray[23].toString().split("_");
//				if(size.length == 2){
//					adVideoPerformanceReportVO.setTemplateProductWidth(size[0]);
//					adVideoPerformanceReportVO.setTemplateProductHeight(size[1]);
//				}
//			}
//			adVideoPerformanceReportVO.setAdActionName(objArray[24].toString());
//			adVideoPerformanceReportVO.setAdVideoUniqSum(objArray[26].toString());
//			String sec = objArray[27].toString();
//			if(sec.length() == 1){
//				sec= "0"+sec;
//			}
//			adVideoPerformanceReportVO.setAdVideoSec(sec);
//			adVideoPerformanceReportVOList.add(adVideoPerformanceReportVO);
//		}
//		return adVideoPerformanceReportVOList;
//	}
//
//	public List<AdVideoPerformanceReportVO> loadReportChart(ReportQueryConditionVO reportQueryConditionVO) throws Exception {
//		List<Object> list = adVideoPerformanceReportDAO.getReportChart(reportQueryConditionVO);
//		List<AdVideoPerformanceReportVO> adVideoPerformanceReportVOList = new ArrayList<>();
//		for (Object object : list) {
//			Object[] objArray = (Object[]) object;
//			AdVideoPerformanceReportVO adVideoPerformanceReportVO = new AdVideoPerformanceReportVO();
//			adVideoPerformanceReportVO.setReportDate((Date) objArray[1]);
//			adVideoPerformanceReportVO.setAdPvSum(objArray[4].toString());
//			adVideoPerformanceReportVO.setAdViewSum(objArray[5].toString());
//			adVideoPerformanceReportVO.setAdViewRatings(objArray[6].toString());
//			adVideoPerformanceReportVO.setSingleAdViewCost(objArray[7].toString());
//			adVideoPerformanceReportVO.setThousandsCost(objArray[8].toString());
//			adVideoPerformanceReportVO.setCostSum(objArray[9].toString());
//			adVideoPerformanceReportVO.setAdVideoProcess100Ratings(objArray[10].toString());
//			adVideoPerformanceReportVO.setAdClkSum(objArray[11].toString());
//			adVideoPerformanceReportVO.setAdVideoMusicSum(objArray[12].toString());
//			adVideoPerformanceReportVO.setAdVideoReplaySum(objArray[13].toString());
//			adVideoPerformanceReportVO.setAdVideoUniqSum(objArray[20].toString());
//			adVideoPerformanceReportVOList.add(adVideoPerformanceReportVO);
//		}
//		return adVideoPerformanceReportVOList;
//	}
//
//	@Override
//	public List<AdVideoPerformanceReportVO> loadReportDateCount(ReportQueryConditionVO reportQueryConditionVO) throws Exception {
//		List<Object> list = adVideoPerformanceReportDAO.getReportCount(reportQueryConditionVO);
//		List<AdVideoPerformanceReportVO> adVideoPerformanceReportVOList = new ArrayList<>();
//		for (Object object : list) {
//			Object[] objArray = (Object[]) object;
//			AdVideoPerformanceReportVO adVideoPerformanceReportVO = new AdVideoPerformanceReportVO();
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
//		}
//		return adVideoPerformanceReportVOList;
//	}

	/**
	 * 影音廣告成效(明細)
	 * @param vo
	 * @return
	 */
	@Override
	public List<AdVideoPerformanceReportVO> queryReportAdVideoPerformanceData(AdVideoPerformanceReportVO vo) {
		List<Map<String, Object>> adVideoPerformanceList = adVideoPerformanceReportDAO.getAdVideoPerformanceList(vo);
		
		Map<String, String> adPriceTypeMap = CommonUtils.getInstance().getAdPriceTypeMap();

		// 檢查前端畫面選擇的篩選條件
		JSONObject tempJSONObject = new JSONObject();
		if(vo.getWhereMap() != null) {
			tempJSONObject = new JSONObject(vo.getWhereMap());
		}
		String selectAdDevice = tempJSONObject.optString("adDevice"); // 裝置
		
		List<AdVideoPerformanceReportVO> adVideoPerformanceVOList = new ArrayList<>();
		for (Map<String, Object> dataMap : adVideoPerformanceList) {
			AdVideoPerformanceReportVO adVideoPerformanceReportVO = new AdVideoPerformanceReportVO();
			
			// 播放狀態
			int adStatus = (int) dataMap.get("ad_status");
			if(4 == adStatus) {
				adVideoPerformanceReportVO.setAdStatusOnOff(true); // on亮綠燈
			}

			// 影音廣告
			adVideoPerformanceReportVO.setTitle((String) dataMap.get("title"));
			String videoSize = (String) dataMap.get("video_size");
			String[] size = videoSize.split("_");
			adVideoPerformanceReportVO.setTemplateProductWidth(size[0]);
			adVideoPerformanceReportVO.setTemplateProductHeight(size[1]);
			adVideoPerformanceReportVO.setAdVideoSec((String) dataMap.get("video_seconds"));
			adVideoPerformanceReportVO.setAdLinkUrl((String) dataMap.get("real_url"));
			// 預覽畫面要再用參數
			adVideoPerformanceReportVO.setAdImg((String) dataMap.get("img"));
			adVideoPerformanceReportVO.setVideoUrl((String) dataMap.get("video_url"));
			
			// 廣告活動
			adVideoPerformanceReportVO.setAdActionName((String) dataMap.get("ad_action_name"));
			
			// 廣告分類
			adVideoPerformanceReportVO.setAdGroupName((String) dataMap.get("ad_group_name"));
			
			// 廣告計費方式
			adVideoPerformanceReportVO.setAdClkPriceType(adPriceTypeMap.get(dataMap.get("ad_price_type")));
			
			// 裝置
			if (EnumReportDevice.PCANDMOBILE.getDevType().equalsIgnoreCase(selectAdDevice)) {
				adVideoPerformanceReportVO.setAdDevice(EnumReportDevice.PCANDMOBILE.getDevTypeName());
			} else {
				String adDevice = (String) dataMap.get("ad_pvclk_device");
				if (EnumReportDevice.PC.getDevType().equalsIgnoreCase(adDevice)) {
					adDevice = EnumReportDevice.PC.getDevTypeName();
				} else if (EnumReportDevice.MOBILE.getDevType().equalsIgnoreCase(adDevice)) {
					adDevice = EnumReportDevice.MOBILE.getDevTypeName();
				}
				adVideoPerformanceReportVO.setAdDevice(adDevice);
			}
			
			// 曝光數
			BigDecimal adPvSum = (BigDecimal) dataMap.get("ad_pv_sum");
			adVideoPerformanceReportVO.setAdPvSum(adPvSum);
			
			// 收視數
			BigDecimal adViewSum = (BigDecimal) dataMap.get("ad_view_sum");
			adVideoPerformanceReportVO.setAdViewSum(adViewSum);
			
			// 收視率 = 收視數 / 曝光數 * 100
			adVideoPerformanceReportVO.setAdViewRatings(CommonUtils.getInstance().getCalculateDivisionValue(adViewSum, adPvSum, 100));
			
			// 費用
			BigDecimal adPriceSum = BigDecimal.valueOf((Double) dataMap.get("ad_price_sum"));
			adVideoPerformanceReportVO.setAdPriceSum(adPriceSum.doubleValue());
			
			// 單次收視費用 = 總費用 / 收視數
			adVideoPerformanceReportVO.setSingleAdViewCost(CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, adViewSum));
			
			// 千次曝光費用 = 總費用 / 曝光數 * 1000
			Double kiloCost = CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, adPvSum, 1000);
			BigDecimal bigDecimal = BigDecimal.valueOf(kiloCost); // 算完千次曝光費用後，再處理小數至第二位，然後四捨五入
			adVideoPerformanceReportVO.setKiloCost(bigDecimal.setScale(2, RoundingMode.HALF_UP).doubleValue());
			
			// 影片播放進度-25%
			adVideoPerformanceReportVO.setAdVideoProcess25Sum((BigDecimal) dataMap.get("ad_video_process_25_sum"));
			
			// 影片播放進度-50%
			adVideoPerformanceReportVO.setAdVideoProcess50Sum((BigDecimal) dataMap.get("ad_video_process_50_sum"));
			
			// 影片播放進度-75%
			adVideoPerformanceReportVO.setAdVideoProcess75Sum((BigDecimal) dataMap.get("ad_video_process_75_sum"));
			
			// 影片播放進度-100%
			BigDecimal adVideoProcess100Sum = (BigDecimal) dataMap.get("ad_video_process_100_sum");
			adVideoPerformanceReportVO.setAdVideoProcess100Sum(adVideoProcess100Sum);
			
			// 影片完整播放率 = 影片播放進度-100% / 曝光數 * 100
			adVideoPerformanceReportVO.setAdVideoProcess100Ratings(CommonUtils.getInstance().getCalculateDivisionValue(adVideoProcess100Sum, adPvSum, 100));
			
			// 點選次數
			adVideoPerformanceReportVO.setAdClkSum((BigDecimal) dataMap.get("ad_clk_sum"));
			
			// 收視人數-不重複
			adVideoPerformanceReportVO.setAdVideoUniqSum((BigDecimal) dataMap.get("uniq_count"));
			
			// 聲音開啟次數
			adVideoPerformanceReportVO.setAdVideoMusicSum((BigDecimal) dataMap.get("ad_video_music_sum"));
			
			// 重播次數
			adVideoPerformanceReportVO.setAdVideoReplaySum((BigDecimal) dataMap.get("ad_video_replay_sum"));
			
			adVideoPerformanceVOList.add(adVideoPerformanceReportVO);
		}
		
		// 處理排序
		if (StringUtils.isNotBlank(vo.getSortBy())) {
			CommonUtils.getInstance().sort(adVideoPerformanceVOList, vo.getSortBy().split("-")[0], vo.getSortBy().split("-")[1]);
		}
		return adVideoPerformanceVOList;
	}

	/**
	 * 影音廣告成效(加總)
	 * @param vo
	 * @return
	 */
	@Override
	public List<AdVideoPerformanceReportVO> queryReportAdVideoPerformanceSumData(AdVideoPerformanceReportVO vo) {
		List<Map<String, Object>> adVideoPerformanceListSum = adVideoPerformanceReportDAO.getAdVideoPerformanceListSum(vo);
		
		// 曝光數
		BigDecimal adPvSum = new BigDecimal(0);
		// 收視數
		BigDecimal adViewSum = new BigDecimal(0);
		// 費用
		BigDecimal adPriceSum = new BigDecimal(0);
		// 影片播放進度-25%
		BigDecimal adVideoProcess25Sum = new BigDecimal(0);
		// 影片播放進度-50%
		BigDecimal adVideoProcess50Sum = new BigDecimal(0);
		// 影片播放進度-75%
		BigDecimal adVideoProcess75Sum = new BigDecimal(0);
		// 影片播放進度-100%
		BigDecimal adVideoProcess100Sum = new BigDecimal(0);
		// 點選次數
		BigDecimal adClkSum = new BigDecimal(0);
		// 收視人數-不重複
		BigDecimal uniqCount = new BigDecimal(0);
		// 聲音開啟次數
		BigDecimal adVideoMusicSum = new BigDecimal(0);
		// 重播次數
		BigDecimal adVideoReplaySum = new BigDecimal(0);
		
		List<AdVideoPerformanceReportVO> adVideoPerformanceVOListSum = new ArrayList<>();
		// 加總
		for (Map<String, Object> dataMap : adVideoPerformanceListSum) {
			adPvSum = adPvSum.add((BigDecimal) dataMap.get("ad_pv_sum"));
			adViewSum = adViewSum.add((BigDecimal) dataMap.get("ad_view_sum"));
			adPriceSum = adPriceSum.add(BigDecimal.valueOf((Double) dataMap.get("ad_price_sum")));
			adVideoProcess25Sum = adVideoProcess25Sum.add((BigDecimal) dataMap.get("ad_video_process_25_sum"));
			adVideoProcess50Sum = adVideoProcess50Sum.add((BigDecimal) dataMap.get("ad_video_process_50_sum"));
			adVideoProcess75Sum = adVideoProcess75Sum.add((BigDecimal) dataMap.get("ad_video_process_75_sum"));
			adVideoProcess100Sum = adVideoProcess100Sum.add((BigDecimal) dataMap.get("ad_video_process_100_sum"));
			adClkSum = adClkSum.add((BigDecimal) dataMap.get("ad_clk_sum"));
			uniqCount = uniqCount.add((BigDecimal) dataMap.get("uniq_count"));
			adVideoMusicSum = adVideoMusicSum.add((BigDecimal) dataMap.get("ad_video_music_sum"));
			adVideoReplaySum = adVideoReplaySum.add((BigDecimal) dataMap.get("ad_video_replay_sum"));
		}
		
		AdVideoPerformanceReportVO adVideoPerformanceReportVO = new AdVideoPerformanceReportVO();
		// 曝光數
		adVideoPerformanceReportVO.setAdPvSum(adPvSum);
		
		// 收視數
		adVideoPerformanceReportVO.setAdViewSum(adViewSum);
		
		// 收視率 = 收視數 / 曝光數 * 100
		adVideoPerformanceReportVO.setAdViewRatings(CommonUtils.getInstance().getCalculateDivisionValue(adViewSum, adPvSum, 100));
		
		// 費用
		adVideoPerformanceReportVO.setAdPriceSum(adPriceSum.doubleValue());
		
		// 單次收視費用 = 總費用 / 收視數
		adVideoPerformanceReportVO.setSingleAdViewCost(CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, adViewSum));
		
		// 千次曝光費用 = 總費用 / 曝光數 * 1000
		Double kiloCost = CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, adPvSum, 1000);
		BigDecimal bigDecimal = BigDecimal.valueOf(kiloCost); // 算完千次曝光費用後，再處理小數至第二位，然後四捨五入
		adVideoPerformanceReportVO.setKiloCost(bigDecimal.setScale(2, RoundingMode.HALF_UP).doubleValue());
		
		// 影片播放進度-25%
		adVideoPerformanceReportVO.setAdVideoProcess25Sum(adVideoProcess25Sum);
		
		// 影片播放進度-50%
		adVideoPerformanceReportVO.setAdVideoProcess50Sum(adVideoProcess50Sum);
		
		// 影片播放進度-75%
		adVideoPerformanceReportVO.setAdVideoProcess75Sum(adVideoProcess75Sum);
		
		// 影片播放進度-100%
		adVideoPerformanceReportVO.setAdVideoProcess100Sum(adVideoProcess100Sum);
		
		// 影片完整播放率 = 影片播放進度-100% / 曝光數 * 100
		adVideoPerformanceReportVO.setAdVideoProcess100Ratings(CommonUtils.getInstance().getCalculateDivisionValue(adVideoProcess100Sum, adPvSum, 100));
		
		// 點選次數
		adVideoPerformanceReportVO.setAdClkSum(adClkSum);
		
		// 收視人數-不重複
		adVideoPerformanceReportVO.setAdVideoUniqSum(uniqCount);
		
		// 聲音開啟次數
		adVideoPerformanceReportVO.setAdVideoMusicSum(adVideoMusicSum);
		
		// 重播次數
		adVideoPerformanceReportVO.setAdVideoReplaySum(adVideoReplaySum);

		// 總計幾筆
		adVideoPerformanceReportVO.setRowCount(adVideoPerformanceListSum.size());
		vo.setRowCount(adVideoPerformanceListSum.size()); // 計算底下頁碼用
		
		adVideoPerformanceVOListSum.add(adVideoPerformanceReportVO);
		
		return adVideoPerformanceVOListSum;
	}

	/**
	 * 影音廣告成效(圖表)
	 * @param chartVo
	 * @return
	 */
	@Override
	public Map<Date, Float> queryReportAdVideoPerformanceChartDataMap(AdVideoPerformanceReportVO vo) {
		List<Map<String, Object>> adVideoPerformanceList = adVideoPerformanceReportDAO.getAdVideoPerformanceListChart(vo);
		
		String charType = vo.getCharType();
		Map<Date, Float> flashDataMap = new HashMap<>();
		for (Map<String, Object> dataMap : adVideoPerformanceList) {
			
			// 日期
			Date reportDate = (Date) dataMap.get("ad_video_date");
			// 曝光數
			BigDecimal adPvSum = (BigDecimal) dataMap.get("ad_pv_sum");
			// 收視數
			BigDecimal adViewSum = (BigDecimal) dataMap.get("ad_view_sum");
			// 費用
			BigDecimal adPriceSum = BigDecimal.valueOf((Double) dataMap.get("ad_price_sum"));
			
			if (charType.equals(EnumReport.REPORT_CHART_TYPE_PV.getTextValue())) {
				flashDataMap.put(reportDate, adPvSum.floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_VIEW.getTextValue())) {
				flashDataMap.put(reportDate, adViewSum.floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_VIEWRATINGS.getTextValue())) {
				// 收視率 = 收視數 / 曝光數 * 100
				flashDataMap.put(reportDate, CommonUtils.getInstance().getCalculateDivisionValue(adViewSum, adPvSum, 100).floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_SINGLE_ADVIEWCOST.getTextValue())) {
				// 單次收視費用 = 總費用 / 收視數
				flashDataMap.put(reportDate, CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, adViewSum).floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_KILOCOST.getTextValue())) {
				// 千次曝光費用 = 總費用 / 曝光數 * 1000
				Double kiloCost = CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, adPvSum, 1000);
				BigDecimal bigDecimal = BigDecimal.valueOf(kiloCost); // 算完千次曝光費用後，再處理小數至第二位，然後四捨五入
				flashDataMap.put(reportDate, bigDecimal.setScale(2, RoundingMode.HALF_UP).floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_COST.getTextValue())) {
				flashDataMap.put(reportDate, adPriceSum.floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_VIDEO_PROCESS100_RATINGS.getTextValue())) {
				// 影片播放進度-100%
				BigDecimal adVideoProcess100Sum = (BigDecimal) dataMap.get("ad_video_process_100_sum");
				// 影片完整播放率 = 影片播放進度-100% / 曝光數 * 100
				flashDataMap.put(reportDate, CommonUtils.getInstance().getCalculateDivisionValue(adVideoProcess100Sum, adPvSum, 100).floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_CLICK.getTextValue())) {
				// 點選次數
				BigDecimal adClkSum = (BigDecimal) dataMap.get("ad_clk_sum");
				flashDataMap.put(reportDate, adClkSum.floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_VIDEO_UNIQ.getTextValue())) {
				// 收視人數-不重複
				BigDecimal uniqCount = (BigDecimal) dataMap.get("uniq_count");
				flashDataMap.put(reportDate, uniqCount.floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_VIDEO_MUSIC.getTextValue())) {
				// 聲音開啟次數
				BigDecimal adVideoMusicSum = (BigDecimal) dataMap.get("ad_video_music_sum");
				flashDataMap.put(reportDate, adVideoMusicSum.floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_VIDEO_REPLAY.getTextValue())) {
				// 重播次數
				BigDecimal adVideoReplaySum = (BigDecimal) dataMap.get("ad_video_replay_sum");
				flashDataMap.put(reportDate, adVideoReplaySum.floatValue());
			}
			
		}
		
		return flashDataMap;
	}

	/**
	 * 影音廣告成效 尺寸下拉選單
	 * @return
	 */
	@Override
	public LinkedHashMap<String, String> queryReportAdVideoPerformanceSize(AdVideoPerformanceReportVO vo) {
		List<Map<String, Object>> adVideoPerformanceSizeList = adVideoPerformanceReportDAO.getAdVideoPerformanceSizeList(vo);
		
		LinkedHashMap<String, String> sizeMap = new LinkedHashMap<>();
		for (Map<String, Object> dataMap : adVideoPerformanceSizeList) {
			String videoSize = (String) dataMap.get("video_size");
			sizeMap.put(videoSize.replace("_", " x "), videoSize);
		}
		
		return sizeMap;
	}
	
}