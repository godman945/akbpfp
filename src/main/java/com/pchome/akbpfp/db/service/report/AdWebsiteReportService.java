package com.pchome.akbpfp.db.service.report;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import com.pchome.akbpfp.db.dao.report.AdWebsiteReportVO;
import com.pchome.akbpfp.db.dao.report.IAdWebsiteReportDAO;
import com.pchome.akbpfp.db.pojo.PfpAdGroup;
import com.pchome.akbpfp.db.service.ad.IPfbxWebsiteCategoryService;
import com.pchome.akbpfp.db.service.ad.IPfpAdGroupService;
import com.pchome.enumerate.report.EnumReport;
import com.pchome.enumerate.report.EnumReportAdType;
import com.pchome.enumerate.report.EnumReportDevice;
import com.pchome.enumerate.utils.EnumStatus;
import com.pchome.utils.CommonUtils;

public class AdWebsiteReportService implements IAdWebsiteReportService {

	private IPfpAdGroupService adGroupService;
	private IPfbxWebsiteCategoryService pfbxWebsiteCategoryService;
	private IAdWebsiteReportDAO adWebsiteReportDAO;

	public void setPfbxWebsiteCategoryService(IPfbxWebsiteCategoryService pfbxWebsiteCategoryService) {
		this.pfbxWebsiteCategoryService = pfbxWebsiteCategoryService;
	}

	public void setAdGroupService(IPfpAdGroupService adGroupService) {
		this.adGroupService = adGroupService;
	}
	
	public void setAdWebsiteReportDAO(IAdWebsiteReportDAO adWebsiteReportDAO) {
		this.adWebsiteReportDAO = adWebsiteReportDAO;
	}

	@Override
	public List<AdWebsiteReportVO> loadReportDate(String sqlType, String searchWebsiteCode, String searchText, String adSearchWay, String adShowWay, String adPvclkDevice, String customerInfoId, String adOperatingRule, String startDate, String endDate, int page, int pageSize) throws Exception {
		return adWebsiteReportDAO.getReportList(sqlType, searchWebsiteCode, searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, adOperatingRule, startDate, endDate, page, pageSize);
	}

	/**
	 * 網站類型成效(明細)
	 * @throws Exception 
	 */
	@Override
	public List<AdWebsiteReportVO> queryReportAdWebsiteData(AdWebsiteReportVO vo) throws Exception {
		List<Map<String, Object>> adWebsiteList = adWebsiteReportDAO.getAdWebsiteList(vo);
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
		long nowTime = new Date().getTime();
		
		Map<String, String> websiteCategoryMap = getWebsiteCategoryMap();
		Map<String, String> adStatusMap = CommonUtils.getInstance().getAdStatusMap();
		Map<String, String> adStyleTypeMap = CommonUtils.getInstance().getAdStyleTypeMap();
		Map<String, String> adPriceTypeMap = CommonUtils.getInstance().getAdPriceTypeMap();
		
		// 檢查前端畫面選擇的篩選條件
		JSONObject tempJSONObject = new JSONObject();
		if(vo.getWhereMap() != null) {
			tempJSONObject = new JSONObject(vo.getWhereMap());
		}
		String selectAdType = tempJSONObject.optString("adType"); // 播放類型
		String selectAdDevice = tempJSONObject.optString("adDevice"); // 裝置
		
		List<AdWebsiteReportVO> adWebsiteVOList = new ArrayList<>();
		for (Map<String, Object> dataMap : adWebsiteList) {
			AdWebsiteReportVO adWebsiteReportVO = new AdWebsiteReportVO();
			
			String adGroupSeq = (String) dataMap.get("ad_group_seq");
			PfpAdGroup pfpAdGroup = adGroupService.getPfpAdGroupBySeq(adGroupSeq);
			String adGroupName = pfpAdGroup.getAdGroupName();
			int adGroupStatus = pfpAdGroup.getAdGroupStatus();

			String adActionName = pfpAdGroup.getPfpAdAction().getAdActionName();
			int adActionStatus = pfpAdGroup.getPfpAdAction().getAdActionStatus();
			Date adActionStartDate = pfpAdGroup.getPfpAdAction().getAdActionStartDate();
			Date adActionEndDate = pfpAdGroup.getPfpAdAction().getAdActionEndDate();
			
			//廣告狀態為開啟的話必須判斷走期( 待播放 or 走期中 or 已結束 )
			if (adActionStatus == EnumStatus.Open.getStatusId()) {
				long startDate = (dateFormat.parse(dateFormat2.format(adActionStartDate) + " 00:00:00")).getTime();
				long endDate = (dateFormat.parse(dateFormat2.format(adActionEndDate) + " 23:59:59")).getTime();
				if (nowTime < startDate) {
					adActionStatus = EnumStatus.Waitbroadcast.getStatusId();
				} else if (nowTime > endDate) {
					adActionStatus = EnumStatus.End.getStatusId();
				} else {
					adActionStatus = EnumStatus.Broadcast.getStatusId();
				}
			}
			
			// 播放狀態
			String alter = "";
			if (adActionStatus == EnumStatus.Broadcast.getStatusId()
					&& adGroupStatus == EnumStatus.Open.getStatusId()) {
				alter = "走期中";
				adWebsiteReportVO.setAdStatusOnOff(true); // on亮綠燈
			} else if (adActionStatus != EnumStatus.Broadcast.getStatusId()
					&& adGroupStatus == EnumStatus.Open.getStatusId()) {
				alter = "廣告" + adStatusMap.get(Integer.toString(adActionStatus));
			} else if (adActionStatus == EnumStatus.Broadcast.getStatusId()
					&& adGroupStatus != EnumStatus.Open.getStatusId()) {
				alter = "分類" + adStatusMap.get(Integer.toString(adGroupStatus));
			} else {
				alter = "廣告" + adStatusMap.get(Integer.toString(adActionStatus)) + "，" + "分類" + adStatusMap.get(Integer.toString(adGroupStatus));
			}
			adWebsiteReportVO.setAdStatusName(alter); // 產excel報表使用
			
			adWebsiteReportVO.setAdActionName(adActionName); // 廣告活動
			adWebsiteReportVO.setAdGroupName(adGroupName); // 廣告分類
			
			adWebsiteReportVO.setWebsiteCategoryName(websiteCategoryMap.get(dataMap.get("website_category_code"))); // 網站類型
			
			// 播放類型
			if (EnumReportAdType.SEARCHANDCHANNEL.getAdType().equalsIgnoreCase(selectAdType)) {
				adWebsiteReportVO.setAdType(EnumReportAdType.SEARCHANDCHANNEL.getAdTypeName());
			} else {
				int adType = (int) dataMap.get("ad_type");
				String adTypeName = "";
				if (EnumReportAdType.SEARCH.getAdType().equalsIgnoreCase(String.valueOf(adType))) {
					adTypeName = EnumReportAdType.SEARCH.getAdTypeName();
				} else if (EnumReportAdType.CHANNEL.getAdType().equalsIgnoreCase(String.valueOf(adType))) {
					adTypeName = EnumReportAdType.CHANNEL.getAdTypeName();
				}
				adWebsiteReportVO.setAdType(adTypeName);
			}
			
			adWebsiteReportVO.setAdOperatingRule(adStyleTypeMap.get(dataMap.get("ad_operating_rule"))); // 廣告樣式
			adWebsiteReportVO.setAdClkPriceType(adPriceTypeMap.get(dataMap.get("ad_clk_price_type"))); // 廣告計費方式
			
			// 裝置
			if (EnumReportDevice.PCANDMOBILE.getDevType().equalsIgnoreCase(selectAdDevice)) {
				adWebsiteReportVO.setAdDevice(EnumReportDevice.PCANDMOBILE.getDevTypeName());
			} else {
				String adDevice = (String) dataMap.get("ad_device");
				if (EnumReportDevice.PC.getDevType().equalsIgnoreCase(adDevice)) {
					adDevice = EnumReportDevice.PC.getDevTypeName();
				} else if (EnumReportDevice.MOBILE.getDevType().equalsIgnoreCase(adDevice)) {
					adDevice = EnumReportDevice.MOBILE.getDevTypeName();
				}
				adWebsiteReportVO.setAdDevice(adDevice);
			}
			
			// 曝光數
			BigDecimal adPvSum = (BigDecimal) dataMap.get("ad_pv_sum");
			adWebsiteReportVO.setAdPvSum(adPvSum);
			
			// 互動數
			BigDecimal adClkSum = (BigDecimal) dataMap.get("ad_clk_sum");
			adWebsiteReportVO.setAdClkSum(adClkSum);
			
			// 互動率 = 總互動數 / 總曝光數 * 100
			adWebsiteReportVO.setCtr(CommonUtils.getInstance().getCalculateDivisionValueRounding(adClkSum, adPvSum, 100, 2));
			
			// 費用
			BigDecimal adPriceSum = BigDecimal.valueOf((Double) dataMap.get("ad_price_sum"));
			adWebsiteReportVO.setAdPriceSum(adPriceSum.doubleValue());
						
			// 單次互動費用 = 總費用 / 總互動次數
			adWebsiteReportVO.setAvgCost(CommonUtils.getInstance().getCalculateDivisionValueRounding(adPriceSum, adClkSum, 2));
			
			// 千次曝光費用 = 總費用 / 曝光數 * 1000
			adWebsiteReportVO.setKiloCost(CommonUtils.getInstance().getCalculateDivisionValueRounding(adPriceSum, adPvSum, 1000, 2));
			
			// 轉換次數
			BigDecimal convertCount = (BigDecimal) dataMap.get("convert_count");
			adWebsiteReportVO.setConvertCount(convertCount);
			
			// 轉換率 = 轉換次數 / 互動數 * 100
			adWebsiteReportVO.setConvertCTR(CommonUtils.getInstance().getCalculateDivisionValueRounding(convertCount, adClkSum, 100, 2));
			
			// 總轉換價值
			BigDecimal convertPriceCount = (BigDecimal) dataMap.get("convert_price_count");
			adWebsiteReportVO.setConvertPriceCount(convertPriceCount);
			
			// 平均轉換成本 = 費用 / 轉換次數
			adWebsiteReportVO.setConvertCost(CommonUtils.getInstance().getCalculateDivisionValueRounding(adPriceSum, convertCount, 2));
			
			// 廣告投資報酬率 = (總轉換價值 / 費用) * 100
			adWebsiteReportVO.setConvertInvestmentCost(CommonUtils.getInstance().getCalculateDivisionValueRounding(convertPriceCount, adPriceSum, 100, 2));
			
			adWebsiteVOList.add(adWebsiteReportVO);
		}
		
		// 處理排序
		if (StringUtils.isNotBlank(vo.getSortBy())) {
			CommonUtils.getInstance().sort(adWebsiteVOList, vo.getSortBy().split("-")[0], vo.getSortBy().split("-")[1]);
		}
		return adWebsiteVOList;
	}

	/**
	 * 網站類型成效(加總)
	 */
	@Override
	public List<AdWebsiteReportVO> queryReportAdWebsiteSumData(AdWebsiteReportVO vo) {
		List<Map<String, Object>> adWebsiteListSum = adWebsiteReportDAO.getAdWebsiteListSum(vo);
		
		// 曝光數
		BigDecimal adPvSum = new BigDecimal(0);
		// 互動數
		BigDecimal adClkSum = new BigDecimal(0);
		// 費用
		BigDecimal adPriceSum = new BigDecimal(0);
		// 轉換次數
		BigDecimal convertCount = new BigDecimal(0);
		// 總轉換價值
		BigDecimal convertPriceCount = new BigDecimal(0);
		
		List<AdWebsiteReportVO> adWebsiteVOListSum = new ArrayList<>();
		// 加總
		for (Map<String, Object> dataMap : adWebsiteListSum) {
			adPvSum = adPvSum.add((BigDecimal) dataMap.get("ad_pv_sum"));
			adClkSum = adClkSum.add((BigDecimal) dataMap.get("ad_clk_sum"));
			adPriceSum = adPriceSum.add(BigDecimal.valueOf((Double) dataMap.get("ad_price_sum")));
			convertCount = convertCount.add((BigDecimal) dataMap.get("convert_count"));
			convertPriceCount = convertPriceCount.add((BigDecimal) dataMap.get("convert_price_count"));
		}
		
		AdWebsiteReportVO adWebsiteReportVO = new AdWebsiteReportVO();
		// 曝光數
		adWebsiteReportVO.setAdPvSum(adPvSum);
		
		// 互動數
		adWebsiteReportVO.setAdClkSum(adClkSum);
		
		// 互動率 = 總互動數 / 總曝光數 * 100
		adWebsiteReportVO.setCtr(CommonUtils.getInstance().getCalculateDivisionValueRounding(adClkSum, adPvSum, 100, 2));
		
		// 費用
		adWebsiteReportVO.setAdPriceSum(adPriceSum.doubleValue());
		
		// 單次互動費用 = 總費用 / 總互動次數
		adWebsiteReportVO.setAvgCost(CommonUtils.getInstance().getCalculateDivisionValueRounding(adPriceSum, adClkSum, 2));
		
		// 千次曝光費用 = 總費用 / 曝光數 * 1000
		adWebsiteReportVO.setKiloCost(CommonUtils.getInstance().getCalculateDivisionValueRounding(adPriceSum, adPvSum, 1000, 2));
		
		// 轉換次數
		adWebsiteReportVO.setConvertCount(convertCount);
		
		// 轉換率 = 轉換次數 / 互動數 * 100
		adWebsiteReportVO.setConvertCTR(CommonUtils.getInstance().getCalculateDivisionValueRounding(convertCount, adClkSum, 100, 2));
		
		// 總轉換價值
		adWebsiteReportVO.setConvertPriceCount(convertPriceCount);
		
		// 平均轉換成本 = 費用 / 轉換次數
		adWebsiteReportVO.setConvertCost(CommonUtils.getInstance().getCalculateDivisionValueRounding(adPriceSum, convertCount, 2));
		
		// 廣告投資報酬率 = (總轉換價值 / 費用) * 100
		adWebsiteReportVO.setConvertInvestmentCost(CommonUtils.getInstance().getCalculateDivisionValueRounding(convertPriceCount, adPriceSum, 100, 2));
		
		// 總計幾筆
		adWebsiteReportVO.setRowCount(adWebsiteListSum.size());
		vo.setRowCount(adWebsiteListSum.size()); // 計算底下頁碼用
		
		adWebsiteVOListSum.add(adWebsiteReportVO);
		
		return adWebsiteVOListSum;
	}
	
	/**
	 * 網站類型成效(圖表)
	 */
	@Override
	public List<AdWebsiteReportVO> queryReportAdWebsiteChartData(AdWebsiteReportVO vo) {
		List<Map<String, Object>> adWebsiteList = adWebsiteReportDAO.getAdWebsiteListChart(vo);
		
		Map<String, String> websiteCategoryMap = getWebsiteCategoryMap();
		
		List<AdWebsiteReportVO> adWebsiteVOList = new ArrayList<>();
		for (Map<String, Object> dataMap : adWebsiteList) {
			AdWebsiteReportVO adWebsiteReportVO = new AdWebsiteReportVO();
			
			// 網站類型
			adWebsiteReportVO.setWebsiteCategoryName(websiteCategoryMap.get(dataMap.get("website_category_code")));
			
			// 曝光數
			BigDecimal adPvSum = (BigDecimal) dataMap.get("ad_pv_sum");
			adWebsiteReportVO.setAdPvSum(adPvSum);
			
			// 互動數
			BigDecimal adClkSum = (BigDecimal) dataMap.get("ad_clk_sum");
			adWebsiteReportVO.setAdClkSum(adClkSum);
			
			// 互動率 = 總互動數 / 總曝光數 * 100
			adWebsiteReportVO.setCtr(CommonUtils.getInstance().getCalculateDivisionValueRounding(adClkSum, adPvSum, 100, 2));
			
			// 費用
			BigDecimal adPriceSum = BigDecimal.valueOf((Double) dataMap.get("ad_price_sum"));
			adWebsiteReportVO.setAdPriceSum(adPriceSum.doubleValue());
						
			// 單次互動費用 = 總費用 / 總互動次數
			adWebsiteReportVO.setAvgCost(CommonUtils.getInstance().getCalculateDivisionValueRounding(adPriceSum, adClkSum, 2));
			
			// 千次曝光費用 = 總費用 / 曝光數 * 1000
			adWebsiteReportVO.setKiloCost(CommonUtils.getInstance().getCalculateDivisionValueRounding(adPriceSum, adPvSum, 1000, 2));
			
			// 轉換次數
			BigDecimal convertCount = (BigDecimal) dataMap.get("convert_count");
			adWebsiteReportVO.setConvertCount(convertCount);
			
			// 轉換率 = 轉換次數 / 互動數 * 100
			adWebsiteReportVO.setConvertCTR(CommonUtils.getInstance().getCalculateDivisionValueRounding(convertCount, adClkSum, 100, 2));
			
			// 總轉換價值
			BigDecimal convertPriceCount = (BigDecimal) dataMap.get("convert_price_count");
			adWebsiteReportVO.setConvertPriceCount(convertPriceCount);
			
			// 平均轉換成本 = 費用 / 轉換次數
			adWebsiteReportVO.setConvertCost(CommonUtils.getInstance().getCalculateDivisionValueRounding(adPriceSum, convertCount, 2));
			
			// 廣告投資報酬率 = (總轉換價值 / 費用) * 100
			adWebsiteReportVO.setConvertInvestmentCost(CommonUtils.getInstance().getCalculateDivisionValueRounding(convertPriceCount, adPriceSum, 100, 2));
			
			adWebsiteVOList.add(adWebsiteReportVO);
		}
		
		return adWebsiteVOList;
	}
	
	/**
	 * 取得網站類型Map
	 * @return
	 */
	public Map<String, String> getWebsiteCategoryMap() {
		Map<String, String> map = new LinkedHashMap<>();
		map = pfbxWebsiteCategoryService.getPfbxWebsiteCategoryNameMap();
		return map;
	}

	/**
	 *  網站類型成效(圖表)
	 * @param chartVo
	 * @return List Object
	 */
	@Override
	public List<Object> queryReportAdWebsiteChartDataListObj(AdWebsiteReportVO vo) {
		List<Map<String, Object>> adWebsiteList = adWebsiteReportDAO.getAdWebsiteListChart(vo);
		
		String charType = vo.getCharType();
		List<String> titleDataList = new ArrayList<>();
		List<Double> dataList = new ArrayList<>();
		Map<String, String> websiteCategoryMap = getWebsiteCategoryMap();
		
		for (Map<String, Object> dataMap : adWebsiteList) {
			// 曝光數
			BigDecimal adPvSum = (BigDecimal) dataMap.get("ad_pv_sum");
			// 互動數
			BigDecimal adClkSum = (BigDecimal) dataMap.get("ad_clk_sum");
			// 費用
			BigDecimal adPriceSum = BigDecimal.valueOf((Double) dataMap.get("ad_price_sum"));
			// 轉換次數
			BigDecimal convertCount = (BigDecimal) dataMap.get("convert_count");
			// 總轉換價值
			BigDecimal convertPriceCount = (BigDecimal) dataMap.get("convert_price_count");

			double data = 0;
			if (charType.equals(EnumReport.REPORT_CHART_TYPE_PV.getTextValue())) {
				data = adPvSum.doubleValue();
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_CLICK.getTextValue())) {
				data = adClkSum.doubleValue();
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_CTR.getTextValue())) {
				// 互動率 = 總互動數 / 總曝光數 * 100
				data = CommonUtils.getInstance().getCalculateDivisionValueRounding(adClkSum, adPvSum, 100, 2);
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_AVGCOST.getTextValue())) {
				// 單次互動費用 = 總費用 / 總互動次數
				data = CommonUtils.getInstance().getCalculateDivisionValueRounding(adPriceSum, adClkSum, 2);
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_KILOCOST.getTextValue())) {
				// 千次曝光費用 = 總費用 / 曝光數 * 1000
				data = CommonUtils.getInstance().getCalculateDivisionValueRounding(adPriceSum, adPvSum, 1000, 2);
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_COST.getTextValue())) {
				data = adPriceSum.doubleValue();
			} else if (charType.equals(EnumReport.REPORT_CHART_CONVERT.getTextValue())) {
				data = convertCount.doubleValue();
			} else if (charType.equals(EnumReport.REPORT_CHART_CONVERT_CTR.getTextValue())) {
				// 轉換率 = 轉換次數 / 互動數 * 100
				data = CommonUtils.getInstance().getCalculateDivisionValueRounding(convertCount, adClkSum, 100, 2);
			} else if (charType.equals(EnumReport.REPORT_CHART_CONVERT_PRICE.getTextValue())) {
				data = convertPriceCount.doubleValue();
			} else if (charType.equals(EnumReport.REPORT_CHART_CONVERT_COST.getTextValue())) {
				// 平均轉換成本 = 費用 / 轉換次數
				data = CommonUtils.getInstance().getCalculateDivisionValueRounding(adPriceSum, convertCount, 2);
			} else if (charType.equals(EnumReport.REPORT_CHART_CONVERT_INVESTMENT.getTextValue())) {
				// 廣告投資報酬率 = (總轉換價值 / 費用) * 100
				data = CommonUtils.getInstance().getCalculateDivisionValueRounding(convertPriceCount, adPriceSum, 100, 2);
			}
			
			// 網站類型
			titleDataList.add(websiteCategoryMap.get(dataMap.get("website_category_code")));
			
			dataList.add(data);
		}

		List<Object> objectList = new ArrayList<>();
		objectList.add(titleDataList);
		objectList.add(dataList);
		return objectList;
	}

}