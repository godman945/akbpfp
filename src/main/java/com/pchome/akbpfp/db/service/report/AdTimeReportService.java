package com.pchome.akbpfp.db.service.report;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.mortbay.log.Log;
import org.springframework.beans.factory.annotation.Autowired;

import com.pchome.akbpfp.db.dao.report.AdActionReportVO;
import com.pchome.akbpfp.db.dao.report.AdTimeReportVO;
import com.pchome.akbpfp.db.dao.report.IAdTimeReportDAO;
import com.pchome.akbpfp.db.pojo.PfpAdGroup;
import com.pchome.akbpfp.db.service.ad.IPfpAdGroupService;
import com.pchome.enumerate.ad.EnumAdPriceType;
import com.pchome.enumerate.ad.EnumAdStyleType;
import com.pchome.enumerate.ad.EnumAdTimeCode;
import com.pchome.enumerate.ad.EnumAdType;
import com.pchome.enumerate.utils.EnumStatus;
import com.pchome.utils.CommonUtils;

public class AdTimeReportService implements IAdTimeReportService {

	private IPfpAdGroupService adGroupService;
	private IAdTimeReportDAO adTimeReportDAO;

	public void setAdGroupService(IPfpAdGroupService adGroupService) {
		this.adGroupService = adGroupService;
	}

	public void setAdTimeReportDAO(IAdTimeReportDAO adTimeReportDAO) {
		this.adTimeReportDAO = adTimeReportDAO;
	}

	public List<AdTimeReportVO> loadReportDate(String sqlType, String searchTime, String searchText, String adSearchWay, String adShowWay, String adPvclkDevice, String customerInfoId, String adOperatingRule, String startDate, String endDate, int page, int pageSize) throws Exception {
		return adTimeReportDAO.getReportList(sqlType, searchTime, searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, adOperatingRule, startDate, endDate, page, pageSize);
	}

	/**
	 * 廣告播放時段成效(明細)
	 * @param vo
	 * @return
	 * @throws Exception 
	 */
	@Override
	public List<AdTimeReportVO> queryReportAdTimeData(AdTimeReportVO vo) throws Exception {
		List<Map<String, Object>> adTimeList = adTimeReportDAO.getAdTimeList(vo);
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
		long nowTime = new Date().getTime();
		
		Map<String, String> adStatusMap = CommonUtils.getInstance().getAdStatusMap();
		Map<Integer, String> adTypeMap = CommonUtils.getInstance().getAdType();
		Map<String, String> adStyleTypeMap = CommonUtils.getInstance().getAdStyleTypeMap();
		Map<String, String> adPriceTypeMap = CommonUtils.getInstance().getAdPriceTypeMap();
				
		List<AdTimeReportVO> adTimeVOList = new ArrayList<>();
		for (Map<String, Object> dataMap : adTimeList) {
			AdTimeReportVO adTimeReportVO = new AdTimeReportVO();
			
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
				adTimeReportVO.setAdStatusOnOff(true); // on亮綠燈
			} else if (adActionStatus != EnumStatus.Broadcast.getStatusId()
					&& adGroupStatus == EnumStatus.Open.getStatusId()) {
//				alter = "廣告" + CommonUtils.getInstance().getAdStatusMap().get(Integer.toString(adActionStatus));
				alter = "廣告" + adStatusMap.get(Integer.toString(adActionStatus));
			} else if (adActionStatus == EnumStatus.Broadcast.getStatusId()
					&& adGroupStatus != EnumStatus.Open.getStatusId()) {
//				alter = "分類" + CommonUtils.getInstance().getAdStatusMap().get(Integer.toString(adGroupStatus));
				alter = "分類" + adStatusMap.get(Integer.toString(adGroupStatus));
			} else {
//				alter = "廣告" + CommonUtils.getInstance().getAdStatusMap().get(Integer.toString(adActionStatus)) + "，" + "分類"
//						+ CommonUtils.getInstance().getAdStatusMap().get(Integer.toString(adGroupStatus));
				alter = "廣告" + adStatusMap.get(Integer.toString(adActionStatus)) + "，" + "分類" + adStatusMap.get(Integer.toString(adGroupStatus));
			}
			Log.info("alter:" + alter);
			adTimeReportVO.setAdStatusName(alter); // 產excel報表使用
			
			adTimeReportVO.setAdActionName(adActionName); // 廣告活動
			adTimeReportVO.setAdGroupName(adGroupName); // 廣告分類
			
			if ("week".equalsIgnoreCase(vo.getViewType())) { // 星期
				int weekCode = (int) dataMap.get("week_code");
				if (weekCode == 1) { // 排序用，將禮拜日改為最大值
					adTimeReportVO.setWeekCode(8);
				} else {
					adTimeReportVO.setWeekCode(weekCode);
				}
				adTimeReportVO.setWeek(getWeekName(Integer.toString(weekCode)));
			} else { // 時段
				String timeCode = (String) dataMap.get("time_code");
				adTimeReportVO.setTimeCode(timeCode); // 排序用
				adTimeReportVO.setTime(EnumAdTimeCode.getEnumAdTimeCodeData(timeCode).getName());
			}
			
			adTimeReportVO.setAdType(adTypeMap.get(dataMap.get("ad_type"))); // 播放類型
			adTimeReportVO.setAdOperatingRule(adStyleTypeMap.get(dataMap.get("ad_operating_rule"))); // 廣告樣式
			adTimeReportVO.setAdClkPriceType(adPriceTypeMap.get(dataMap.get("ad_clk_price_type"))); // 廣告計費方式
			
//			// 星期
////			adTimeReportVO.setTime(EnumAdTimeCode.getEnumAdTimeCodeData((String) dataMap.get("time_code")).getName());
//			
////			adTimeReportVO.setAdType(CommonUtils.getInstance().getAdType().get(dataMap.get("ad_type"))); // 播放類型
//			adTimeReportVO.setAdType(EnumAdType.getEnumAdTypeData((int) dataMap.get("ad_type")).getTypeName()); // 播放類型
//			adTimeReportVO.setAdOperatingRule(EnumAdStyleType.getEnumAdStyleTypeData((String) dataMap.get("ad_operating_rule")).getType()); // 廣告樣式
////			adTimeReportVO.setAdClkPriceType(CommonUtils.getInstance().getAdPriceTypeMap().get(dataMap.get("ad_clk_price_type"))); // 廣告計費方式
//			adTimeReportVO.setAdClkPriceType(EnumAdPriceType.getEnumAdPriceTypeData((String) dataMap.get("ad_clk_price_type")).getTypeName()); // 廣告計費方式
			
			String adDevice = "全部";
			if (vo.getWhereMap() != null) {
				adDevice = CommonUtils.getInstance().getAdDeviceName(vo.getWhereMap());
			}
			// 裝置
			adTimeReportVO.setAdDevice(adDevice);
			
			// 曝光數
			BigDecimal adPvSum = (BigDecimal) dataMap.get("ad_pv_sum");
			adTimeReportVO.setAdPvSum(adPvSum);
			
			// 互動數
			BigDecimal adClkSum = (BigDecimal) dataMap.get("ad_clk_sum");
			adTimeReportVO.setAdClkSum(adClkSum);
			
			// 互動率 = 總互動數 / 總曝光數 * 100
			adTimeReportVO.setCtr(CommonUtils.getInstance().getCalculateDivisionValue(adClkSum, adPvSum, 100));
			
			// 費用
			BigDecimal adPriceSum = BigDecimal.valueOf((Double) dataMap.get("ad_price_sum"));
			adTimeReportVO.setAdPriceSum(adPriceSum.doubleValue());
						
			// 單次互動費用 = 總費用 / 總互動次數
			adTimeReportVO.setAvgCost(CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, adClkSum));
			
			// 千次曝光費用 = 總費用 / 曝光數 * 1000
			Double kiloCost = CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, adPvSum, 1000);
			BigDecimal bigDecimal = BigDecimal.valueOf(kiloCost); // 算完千次曝光費用後，再處理小數至第二位，然後四捨五入
			adTimeReportVO.setKiloCost(bigDecimal.setScale(2, RoundingMode.HALF_UP).doubleValue());
			
			// 轉換次數
			BigDecimal convertCount = (BigDecimal) dataMap.get("convert_count");
			adTimeReportVO.setConvertCount(convertCount);
			
			// 轉換率 = 轉換次數 / 互動數 * 100
			adTimeReportVO.setConvertCTR(CommonUtils.getInstance().getCalculateDivisionValue(convertCount, adClkSum, 100));
			
			// 總轉換價值
			BigDecimal convertPriceCount = (BigDecimal) dataMap.get("convert_price_count");
			adTimeReportVO.setConvertPriceCount(convertPriceCount);
			
			// 平均轉換成本 = 費用 / 轉換次數
			adTimeReportVO.setConvertCost(CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, convertCount));
			
			// 廣告投資報酬率 = (總轉換價值 / 費用) * 100
			adTimeReportVO.setConvertInvestmentCost(CommonUtils.getInstance().getCalculateDivisionValue(convertPriceCount, adPriceSum, 100));
			
			adTimeVOList.add(adTimeReportVO);
		}
		
		// 處理排序
		if (StringUtils.isNotBlank(vo.getSortBy())) {
			CommonUtils.getInstance().sort(adTimeVOList, vo.getSortBy().split("-")[0], vo.getSortBy().split("-")[1]);
		}
		return adTimeVOList;
	}

	/**
	 * 廣告播放時段成效(加總)
	 * @param vo
	 * @return
	 */
	@Override
	public List<AdTimeReportVO> queryReportAdTimeSumData(AdTimeReportVO vo) {
		List<Map<String, Object>> adTimeListSum = adTimeReportDAO.getAdTimeListSum(vo);
		
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
		
		List<AdTimeReportVO> adTimeVOListSum = new ArrayList<>();
		// 加總
		for (Map<String, Object> dataMap : adTimeListSum) {
			adPvSum = adPvSum.add((BigDecimal) dataMap.get("ad_pv_sum"));
			adClkSum = adClkSum.add((BigDecimal) dataMap.get("ad_clk_sum"));
			adPriceSum = adPriceSum.add(BigDecimal.valueOf((Double) dataMap.get("ad_price_sum")));
			convertCount = convertCount.add((BigDecimal) dataMap.get("convert_count"));
			convertPriceCount = convertPriceCount.add((BigDecimal) dataMap.get("convert_price_count"));
		}
		
		AdTimeReportVO adActionReportVO = new AdTimeReportVO();
		// 曝光數
		adActionReportVO.setAdPvSum(adPvSum);
		
		// 互動數
		adActionReportVO.setAdClkSum(adClkSum);
		
		// 互動率 = 總互動數 / 總曝光數 * 100
		adActionReportVO.setCtr(CommonUtils.getInstance().getCalculateDivisionValue(adClkSum, adPvSum, 100));
		
		// 費用
		adActionReportVO.setAdPriceSum(adPriceSum.doubleValue());
		
		// 單次互動費用 = 總費用 / 總互動次數
		adActionReportVO.setAvgCost(CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, adClkSum));
		
		// 千次曝光費用 = 總費用 / 曝光數 * 1000
		Double kiloCost = CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, adPvSum, 1000);
		BigDecimal bg = BigDecimal.valueOf(kiloCost); // 算完千次曝光費用後，再處理小數至第二位，然後四捨五入
		adActionReportVO.setKiloCost(bg.setScale(2, RoundingMode.HALF_UP).doubleValue());
		
		// 轉換次數
		adActionReportVO.setConvertCount(convertCount);
		
		// 轉換率 = 轉換次數 / 互動數 * 100
		adActionReportVO.setConvertCTR(CommonUtils.getInstance().getCalculateDivisionValue(convertCount, adClkSum, 100));
		
		// 總轉換價值
		adActionReportVO.setConvertPriceCount(convertPriceCount);
		
		// 平均轉換成本 = 費用 / 轉換次數
		adActionReportVO.setConvertCost(CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, convertCount));
		
		// 廣告投資報酬率 = (總轉換價值 / 費用) * 100
		adActionReportVO.setConvertInvestmentCost(CommonUtils.getInstance().getCalculateDivisionValue(convertPriceCount, adPriceSum, 100));
		
		// 總計幾筆
		adActionReportVO.setRowCount(adTimeListSum.size());
		vo.setRowCount(adTimeListSum.size()); // 計算底下頁碼用
		
		adTimeVOListSum.add(adActionReportVO);
		
		return adTimeVOListSum;
	}
	
	/**
	 * 廣告播放時段成效(圖表)
	 * @param vo
	 * @return
	 * @throws Exception 
	 */
	@Override
	public List<AdTimeReportVO> queryReportAdTimeChartData(AdTimeReportVO vo) throws Exception {
		List<Map<String, Object>> adTimeList = adTimeReportDAO.getAdTimeListChart(vo);
		
		List<AdTimeReportVO> adTimeVOList = new ArrayList<>();
		for (Map<String, Object> dataMap : adTimeList) {
			AdTimeReportVO adTimeReportVO = new AdTimeReportVO();
			
			if ("week".equalsIgnoreCase(vo.getViewType())) { // 星期
				adTimeReportVO.setWeekCode((int) dataMap.get("week_code"));
			} else { // 時段
				adTimeReportVO.setTimeCode((String) dataMap.get("time_code")); // 排序用
			}
			
			// 曝光數
			BigDecimal adPvSum = (BigDecimal) dataMap.get("ad_pv_sum");
			adTimeReportVO.setAdPvSum(adPvSum);
			
			// 互動數
			BigDecimal adClkSum = (BigDecimal) dataMap.get("ad_clk_sum");
			adTimeReportVO.setAdClkSum(adClkSum);
			
			// 互動率 = 總互動數 / 總曝光數 * 100
			adTimeReportVO.setCtr(CommonUtils.getInstance().getCalculateDivisionValue(adClkSum, adPvSum, 100));
			
			// 費用
			BigDecimal adPriceSum = BigDecimal.valueOf((Double) dataMap.get("ad_price_sum"));
			adTimeReportVO.setAdPriceSum(adPriceSum.doubleValue());
						
			// 單次互動費用 = 總費用 / 總互動次數
			adTimeReportVO.setAvgCost(CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, adClkSum));
			
			// 千次曝光費用 = 總費用 / 曝光數 * 1000
			Double kiloCost = CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, adPvSum, 1000);
			BigDecimal bigDecimal = BigDecimal.valueOf(kiloCost); // 算完千次曝光費用後，再處理小數至第二位，然後四捨五入
			adTimeReportVO.setKiloCost(bigDecimal.setScale(2, RoundingMode.HALF_UP).doubleValue());
			
			// 轉換次數
			BigDecimal convertCount = (BigDecimal) dataMap.get("convert_count");
			adTimeReportVO.setConvertCount(convertCount);
			
			// 轉換率 = 轉換次數 / 互動數 * 100
			adTimeReportVO.setConvertCTR(CommonUtils.getInstance().getCalculateDivisionValue(convertCount, adClkSum, 100));
			
			// 總轉換價值
			BigDecimal convertPriceCount = (BigDecimal) dataMap.get("convert_price_count");
			adTimeReportVO.setConvertPriceCount(convertPriceCount);
			
			// 平均轉換成本 = 費用 / 轉換次數
			adTimeReportVO.setConvertCost(CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, convertCount));
			
			// 廣告投資報酬率 = (總轉換價值 / 費用) * 100
			adTimeReportVO.setConvertInvestmentCost(CommonUtils.getInstance().getCalculateDivisionValue(convertPriceCount, adPriceSum, 100));
			
			adTimeVOList.add(adTimeReportVO);
		}
		
		return adTimeVOList;
	}
	
	private String getWeekName(String weekCode) {
		String name = "";

		switch (weekCode) {
		case "1":
			name = "星期日";
			break;
		case "2":
			name = "星期一";
			break;
		case "3":
			name = "星期二";
			break;
		case "4":
			name = "星期三";
			break;
		case "5":
			name = "星期四";
			break;
		case "6":
			name = "星期五";
			break;
		case "7":
			name = "星期六";
			break;
		default:
			break;
		}

		return name;
	}
}