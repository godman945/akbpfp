package com.pchome.akbpfp.db.service.report;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import com.pchome.akbpfp.db.dao.report.AdAgesexReportVO;
import com.pchome.akbpfp.db.dao.report.IAdAgesexReportDAO;
import com.pchome.akbpfp.db.pojo.PfpAdGroup;
import com.pchome.akbpfp.db.service.ad.IPfpAdGroupService;
import com.pchome.enumerate.ad.EnumAdAgeCode;
import com.pchome.enumerate.report.EnumReport;
import com.pchome.enumerate.report.EnumReportDevice;
import com.pchome.enumerate.utils.EnumStatus;
import com.pchome.utils.CommonUtils;

public class AdAgesexReportService implements IAdAgesexReportService {

	private IPfpAdGroupService adGroupService;
	private IAdAgesexReportDAO adAgesexReportDAO;

	public void setAdGroupService(IPfpAdGroupService adGroupService) {
		this.adGroupService = adGroupService;
	}

	public void setAdAgesexReportDAO(IAdAgesexReportDAO adAgesexReportDAO) {
		this.adAgesexReportDAO = adAgesexReportDAO;
	}

	public List<AdAgesexReportVO> loadReportDate(String sqlType, String searchAgesex, String searchText, String adSearchWay, String adShowWay, String adPvclkDevice, String customerInfoId, String adOperatingRule, String startDate, String endDate, int page, int pageSize) throws Exception {
		return adAgesexReportDAO.getReportList(sqlType, searchAgesex, searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, adOperatingRule, startDate, endDate, page, pageSize);
	}

	/**
	 * 廣告族群成效(明細)
	 * @throws Exception 
	 */
	@Override
	public List<AdAgesexReportVO> queryReportAdAgesexData(AdAgesexReportVO vo) throws Exception {
		List<Map<String, Object>> adAgesexList = adAgesexReportDAO.getAdAgesexList(vo);
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
		long nowTime = new Date().getTime();
		
		Map<String, String> adAgeMap = getAdAgeMap();
		Map<String, String> adStatusMap = CommonUtils.getInstance().getAdStatusMap();
		Map<Integer, String> adTypeMap = CommonUtils.getInstance().getAdType();
		Map<String, String> adStyleTypeMap = CommonUtils.getInstance().getAdStyleTypeMap();
		Map<String, String> adPriceTypeMap = CommonUtils.getInstance().getAdPriceTypeMap();
		
		// 檢查前端畫面選擇的篩選條件
		JSONObject tempJSONObject = new JSONObject();
		if(vo.getWhereMap() != null) {
			tempJSONObject = new JSONObject(vo.getWhereMap());
		}
		String selectAdDevice = tempJSONObject.optString("adDevice");
		
		List<AdAgesexReportVO> adAgesexVOList = new ArrayList<>();
		for (Map<String, Object> dataMap : adAgesexList) {
			AdAgesexReportVO adAgesexReportVO = new AdAgesexReportVO();
			
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
				adAgesexReportVO.setAdStatusOnOff(true); // on亮綠燈
			} else if (adActionStatus != EnumStatus.Broadcast.getStatusId()
					&& adGroupStatus == EnumStatus.Open.getStatusId()) {
				alter = "廣告" + adStatusMap.get(Integer.toString(adActionStatus));
			} else if (adActionStatus == EnumStatus.Broadcast.getStatusId()
					&& adGroupStatus != EnumStatus.Open.getStatusId()) {
				alter = "分類" + adStatusMap.get(Integer.toString(adGroupStatus));
			} else {
				alter = "廣告" + adStatusMap.get(Integer.toString(adActionStatus)) + "，" + "分類" + adStatusMap.get(Integer.toString(adGroupStatus));
			}
			adAgesexReportVO.setAdStatusName(alter); // 產excel報表使用
			
			adAgesexReportVO.setAdActionName(adActionName); // 廣告活動
			adAgesexReportVO.setAdGroupName(adGroupName); // 廣告分類
			
			if ("age".equalsIgnoreCase(vo.getViewType())) { // 年齡
				String ageCode = (String) dataMap.get("age_code");
				adAgesexReportVO.setAgeCode(ageCode); // 排序用
				adAgesexReportVO.setAge(adAgeMap.get(ageCode));
			} else { // 性別
				String sexCode = (String) dataMap.get("sex_code");
				String sexName = "不分性別";
				if (StringUtils.endsWith(sexCode, "M")) {
					sexName = "男性";
				} else if (StringUtils.endsWith(sexCode, "F")) {
					sexName = "女性";
				}
				adAgesexReportVO.setSex(sexName);
			}
			
			adAgesexReportVO.setAdType(adTypeMap.get(dataMap.get("ad_type"))); // 播放類型
			adAgesexReportVO.setAdOperatingRule(adStyleTypeMap.get(dataMap.get("ad_operating_rule"))); // 廣告樣式
			adAgesexReportVO.setAdClkPriceType(adPriceTypeMap.get(dataMap.get("ad_clk_price_type"))); // 廣告計費方式
			
			// 裝置
			if (EnumReportDevice.PCANDMOBILE.getDevType().equalsIgnoreCase(selectAdDevice)) {
				adAgesexReportVO.setAdDevice(EnumReportDevice.PCANDMOBILE.getDevTypeName());
			} else {
				String adDevice = (String) dataMap.get("ad_device");
				if (EnumReportDevice.PC.getDevType().equalsIgnoreCase(adDevice)) {
					adDevice = EnumReportDevice.PC.getDevTypeName();
				} else if (EnumReportDevice.MOBILE.getDevType().equalsIgnoreCase(adDevice)) {
					adDevice = EnumReportDevice.MOBILE.getDevTypeName();
				}
				adAgesexReportVO.setAdDevice(adDevice);
			}
			
			// 曝光數
			BigDecimal adPvSum = (BigDecimal) dataMap.get("ad_pv_sum");
			adAgesexReportVO.setAdPvSum(adPvSum);
			
			// 互動數
			BigDecimal adClkSum = (BigDecimal) dataMap.get("ad_clk_sum");
			adAgesexReportVO.setAdClkSum(adClkSum);
			
			// 互動率 = 總互動數 / 總曝光數 * 100
			adAgesexReportVO.setCtr(CommonUtils.getInstance().getCalculateDivisionValue(adClkSum, adPvSum, 100));
			
			// 費用
			BigDecimal adPriceSum = BigDecimal.valueOf((Double) dataMap.get("ad_price_sum"));
			adAgesexReportVO.setAdPriceSum(adPriceSum.doubleValue());
						
			// 單次互動費用 = 總費用 / 總互動次數
			adAgesexReportVO.setAvgCost(CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, adClkSum));
			
			// 千次曝光費用 = 總費用 / 曝光數 * 1000
			Double kiloCost = CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, adPvSum, 1000);
			BigDecimal bigDecimal = BigDecimal.valueOf(kiloCost); // 算完千次曝光費用後，再處理小數至第二位，然後四捨五入
			adAgesexReportVO.setKiloCost(bigDecimal.setScale(2, RoundingMode.HALF_UP).doubleValue());
			
			// 轉換次數
			BigDecimal convertCount = (BigDecimal) dataMap.get("convert_count");
			adAgesexReportVO.setConvertCount(convertCount);
			
			// 轉換率 = 轉換次數 / 互動數 * 100
			adAgesexReportVO.setConvertCTR(CommonUtils.getInstance().getCalculateDivisionValue(convertCount, adClkSum, 100));
			
			// 總轉換價值
			BigDecimal convertPriceCount = (BigDecimal) dataMap.get("convert_price_count");
			adAgesexReportVO.setConvertPriceCount(convertPriceCount);
			
			// 平均轉換成本 = 費用 / 轉換次數
			adAgesexReportVO.setConvertCost(CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, convertCount));
			
			// 廣告投資報酬率 = (總轉換價值 / 費用) * 100
			adAgesexReportVO.setConvertInvestmentCost(CommonUtils.getInstance().getCalculateDivisionValue(convertPriceCount, adPriceSum, 100));
			
			adAgesexVOList.add(adAgesexReportVO);
		}
		
		// 處理排序
		if (StringUtils.isNotBlank(vo.getSortBy())) {
			CommonUtils.getInstance().sort(adAgesexVOList, vo.getSortBy().split("-")[0], vo.getSortBy().split("-")[1]);
		}
		return adAgesexVOList;
	}

	/**
	 * 廣告族群成效(加總)
	 */
	@Override
	public List<AdAgesexReportVO> queryReportAdAgesexSumData(AdAgesexReportVO vo) {
		List<Map<String, Object>> adAgesexListSum = adAgesexReportDAO.getAdAgesexListSum(vo);
		
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
		
		List<AdAgesexReportVO> adAgesexVOListSum = new ArrayList<>();
		// 加總
		for (Map<String, Object> dataMap : adAgesexListSum) {
			adPvSum = adPvSum.add((BigDecimal) dataMap.get("ad_pv_sum"));
			adClkSum = adClkSum.add((BigDecimal) dataMap.get("ad_clk_sum"));
			adPriceSum = adPriceSum.add(BigDecimal.valueOf((Double) dataMap.get("ad_price_sum")));
			convertCount = convertCount.add((BigDecimal) dataMap.get("convert_count"));
			convertPriceCount = convertPriceCount.add((BigDecimal) dataMap.get("convert_price_count"));
		}
		
		AdAgesexReportVO adAgesexReportVO = new AdAgesexReportVO();
		// 曝光數
		adAgesexReportVO.setAdPvSum(adPvSum);
		
		// 互動數
		adAgesexReportVO.setAdClkSum(adClkSum);
		
		// 互動率 = 總互動數 / 總曝光數 * 100
		adAgesexReportVO.setCtr(CommonUtils.getInstance().getCalculateDivisionValue(adClkSum, adPvSum, 100));
		
		// 費用
		adAgesexReportVO.setAdPriceSum(adPriceSum.doubleValue());
		
		// 單次互動費用 = 總費用 / 總互動次數
		adAgesexReportVO.setAvgCost(CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, adClkSum));
		
		// 千次曝光費用 = 總費用 / 曝光數 * 1000
		Double kiloCost = CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, adPvSum, 1000);
		BigDecimal bg = BigDecimal.valueOf(kiloCost); // 算完千次曝光費用後，再處理小數至第二位，然後四捨五入
		adAgesexReportVO.setKiloCost(bg.setScale(2, RoundingMode.HALF_UP).doubleValue());
		
		// 轉換次數
		adAgesexReportVO.setConvertCount(convertCount);
		
		// 轉換率 = 轉換次數 / 互動數 * 100
		adAgesexReportVO.setConvertCTR(CommonUtils.getInstance().getCalculateDivisionValue(convertCount, adClkSum, 100));
		
		// 總轉換價值
		adAgesexReportVO.setConvertPriceCount(convertPriceCount);
		
		// 平均轉換成本 = 費用 / 轉換次數
		adAgesexReportVO.setConvertCost(CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, convertCount));
		
		// 廣告投資報酬率 = (總轉換價值 / 費用) * 100
		adAgesexReportVO.setConvertInvestmentCost(CommonUtils.getInstance().getCalculateDivisionValue(convertPriceCount, adPriceSum, 100));
		
		// 總計幾筆
		adAgesexReportVO.setRowCount(adAgesexListSum.size());
		vo.setRowCount(adAgesexListSum.size()); // 計算底下頁碼用
		
		adAgesexVOListSum.add(adAgesexReportVO);
		
		return adAgesexVOListSum;
	}
	
	/**
	 * 廣告族群成效(圖表)
	 */
	@Override
	public List<AdAgesexReportVO> queryReportAdAgesexChartData(AdAgesexReportVO vo) {
		List<Map<String, Object>> adAgesexList = adAgesexReportDAO.getAdAgesexListChart(vo);
		
		List<AdAgesexReportVO> adAgesexVOList = new ArrayList<>();
		for (Map<String, Object> dataMap : adAgesexList) {
			AdAgesexReportVO adAgesexReportVO = new AdAgesexReportVO();
			
			if ("age".equalsIgnoreCase(vo.getViewType())) { // 年齡
				adAgesexReportVO.setAgeCode((String) dataMap.get("age_code"));
			} else { // 性別
				String sexCode = (String) dataMap.get("sex_code");
				if (sexCode == null) {
					adAgesexReportVO.setSexCode("N");
				} else {
					adAgesexReportVO.setSexCode(sexCode);
				}
			}
			
			// 曝光數
			BigDecimal adPvSum = (BigDecimal) dataMap.get("ad_pv_sum");
			adAgesexReportVO.setAdPvSum(adPvSum);
			
			// 互動數
			BigDecimal adClkSum = (BigDecimal) dataMap.get("ad_clk_sum");
			adAgesexReportVO.setAdClkSum(adClkSum);
			
			// 互動率 = 總互動數 / 總曝光數 * 100
			adAgesexReportVO.setCtr(CommonUtils.getInstance().getCalculateDivisionValue(adClkSum, adPvSum, 100));
			
			// 費用
			BigDecimal adPriceSum = BigDecimal.valueOf((Double) dataMap.get("ad_price_sum"));
			adAgesexReportVO.setAdPriceSum(adPriceSum.doubleValue());
						
			// 單次互動費用 = 總費用 / 總互動次數
			adAgesexReportVO.setAvgCost(CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, adClkSum));
			
			// 千次曝光費用 = 總費用 / 曝光數 * 1000
			Double kiloCost = CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, adPvSum, 1000);
			BigDecimal bigDecimal = BigDecimal.valueOf(kiloCost); // 算完千次曝光費用後，再處理小數至第二位，然後四捨五入
			adAgesexReportVO.setKiloCost(bigDecimal.setScale(2, RoundingMode.HALF_UP).doubleValue());
			
			// 轉換次數
			BigDecimal convertCount = (BigDecimal) dataMap.get("convert_count");
			adAgesexReportVO.setConvertCount(convertCount);
			
			// 轉換率 = 轉換次數 / 互動數 * 100
			adAgesexReportVO.setConvertCTR(CommonUtils.getInstance().getCalculateDivisionValue(convertCount, adClkSum, 100));
			
			// 總轉換價值
			BigDecimal convertPriceCount = (BigDecimal) dataMap.get("convert_price_count");
			adAgesexReportVO.setConvertPriceCount(convertPriceCount);
			
			// 平均轉換成本 = 費用 / 轉換次數
			adAgesexReportVO.setConvertCost(CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, convertCount));
			
			// 廣告投資報酬率 = (總轉換價值 / 費用) * 100
			adAgesexReportVO.setConvertInvestmentCost(CommonUtils.getInstance().getCalculateDivisionValue(convertPriceCount, adPriceSum, 100));
			
			adAgesexVOList.add(adAgesexReportVO);
		}
		
		return adAgesexVOList;
	}
	
	/**
	 * 取得年齡map
	 * @return
	 */
	private Map<String, String> getAdAgeMap() {
		Map<String, String> map = new LinkedHashMap<>();
		for (EnumAdAgeCode Age : EnumAdAgeCode.values()) {
			map.put(Age.getCode(), Age.getName());
		}
		return map;
	}

	/**
	 * 廣告族群成效(圖表)
	 * @param vo
	 * @return list
	 */
	@Override
	public List<Double> queryReportAdAgesexChartDataList(AdAgesexReportVO vo) {
		List<Map<String, Object>> adAgesexList = adAgesexReportDAO.getAdAgesexListChart(vo);
		
		String charType = vo.getCharType();
		String viewType = vo.getViewType();

		// 性別
		String sexCode = "";
		double sexM = 0; // 男性
		double sexF = 0; // 女性
		double sexN = 0; // 未知
		
		// 年齡
		String ageCode = "";
		double ageA = 0; // 18歲(不含)以下
		double ageB = 0; // 18歲~24歲
		double ageC = 0; // 25歲~34歲
		double ageD = 0; // 35歲~44歲
		double ageE = 0; // 45歲~54歲
		double ageF = 0; // 55歲~64歲
		double ageG = 0; // 65歲~74歲
		double ageH = 0; // 75歲以上
		double ageI = 0; // 未知
		
		double total = 0; // 總合
		for (Map<String, Object> dataMap : adAgesexList) {
			
			if ("age".equalsIgnoreCase(viewType)) { // 年齡
				ageCode = (String) dataMap.get("age_code");
			} else { // 性別
				sexCode = (String) dataMap.get("sex_code");
				if (sexCode == null) {
					sexCode = "N";
				}
			}

			if (charType.equals(EnumReport.REPORT_CHART_TYPE_PV.getTextValue())) {
				// 曝光數
				double adPvSum = ((BigDecimal) dataMap.get("ad_pv_sum")).doubleValue();
				if (StringUtils.equals(viewType, "sex")) {
					switch (sexCode) {
					case "M":
						sexM = adPvSum;
						break;
					case "F":
						sexF = adPvSum;
						break;
					default:
						sexN = adPvSum;
						break;
					}
				} else {
					switch (ageCode) {
					case "A":
						ageA = adPvSum;
						break;
					case "B":
						ageB = adPvSum;
						break;
					case "C":
						ageC = adPvSum;
						break;
					case "D":
						ageD = adPvSum;
						break;
					case "E":
						ageE = adPvSum;
						break;
					case "F":
						ageF = adPvSum;
						break;
					case "G":
						ageG = adPvSum;
						break;
					case "H":
						ageH = adPvSum;
						break;
					default:
						ageI = adPvSum;
						break;
					}
				}
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_CLICK.getTextValue())) {
				// 互動數
				double adClkSum = ((BigDecimal) dataMap.get("ad_clk_sum")).doubleValue();
				if (StringUtils.equals(viewType, "sex")) {
					switch (sexCode) {
					case "M":
						sexM = adClkSum;
						break;
					case "F":
						sexF = adClkSum;
						break;
					default:
						sexN = adClkSum;
						break;
					}
				} else {
					switch (ageCode) {
					case "A":
						ageA = adClkSum;
						break;
					case "B":
						ageB = adClkSum;
						break;
					case "C":
						ageC = adClkSum;
						break;
					case "D":
						ageD = adClkSum;
						break;
					case "E":
						ageE = adClkSum;
						break;
					case "F":
						ageF = adClkSum;
						break;
					case "G":
						ageG = adClkSum;
						break;
					case "H":
						ageH = adClkSum;
						break;
					default:
						ageI = adClkSum;
						break;
					}
				}
			} else if (charType.equals(EnumReport.REPORT_CHART_CONVERT.getTextValue())) {
				// 轉換次數
				double convertCount = ((BigDecimal) dataMap.get("convert_count")).doubleValue();
				if (StringUtils.equals(viewType, "sex")) {
					switch (sexCode) {
					case "M":
						sexM = convertCount;
						break;
					case "F":
						sexF = convertCount;
						break;
					default:
						sexN = convertCount;
						break;
					}
				} else {
					switch (ageCode) {
					case "A":
						ageA = convertCount;
						break;
					case "B":
						ageB = convertCount;
						break;
					case "C":
						ageC = convertCount;
						break;
					case "D":
						ageD = convertCount;
						break;
					case "E":
						ageE = convertCount;
						break;
					case "F":
						ageF = convertCount;
						break;
					case "G":
						ageG = convertCount;
						break;
					case "H":
						ageH = convertCount;
						break;
					default:
						ageI = convertCount;
						break;
					}
				}
			}
		}
		
		List<Double> dataList = new ArrayList<>();
		if (StringUtils.equals(viewType, "sex")) {
			total = sexM + sexF;
			if (total != 0) {
				sexM = Double.parseDouble(String.format("%.2f", (sexM / total) * 100));
				sexF = 100 - sexM;

				dataList.add(sexM);
				dataList.add(sexF);
			} else {
				dataList.add((double) 0);
				dataList.add((double) 0);
			}
		} else {
			total = ageA + ageB + ageC + ageD + ageE + ageF + ageG + ageH;
			if (total != 0) {
				dataList.add((ageA / total) * 100);
				dataList.add((ageB / total) * 100);
				dataList.add((ageC / total) * 100);
				dataList.add((ageD / total) * 100);
				dataList.add((ageE / total) * 100);
				dataList.add((ageF / total) * 100);
				dataList.add((ageG / total) * 100);
				dataList.add((ageH / total) * 100);
			} else {
				dataList.add((double) 0);
				dataList.add((double) 0);
				dataList.add((double) 0);
				dataList.add((double) 0);
				dataList.add((double) 0);
				dataList.add((double) 0);
				dataList.add((double) 0);
				dataList.add((double) 0);
			}
		}
		return dataList;
	}

}