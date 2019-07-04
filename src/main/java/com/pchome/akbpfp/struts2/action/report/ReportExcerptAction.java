package com.pchome.akbpfp.struts2.action.report;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.pchome.akbpfp.db.dao.report.AdCampaginReportVO;
import com.pchome.akbpfp.db.dao.report.AdGroupReportVO;
import com.pchome.akbpfp.db.dao.report.AdvertiseReportVO;
import com.pchome.akbpfp.db.pojo.PfpCode;
import com.pchome.akbpfp.db.pojo.PfpCustomerInfo;
import com.pchome.akbpfp.db.service.codeManage.IPfpCodeService;
import com.pchome.akbpfp.db.service.customerInfo.IPfpCustomerInfoService;
import com.pchome.akbpfp.db.service.report.IAdActionReportService;
import com.pchome.akbpfp.db.service.report.IAdGroupReportService;
import com.pchome.akbpfp.db.service.report.IAdReportService;
import com.pchome.enumerate.report.EnumReport;
import com.pchome.soft.depot.utils.DateValueUtil;
import com.pchome.soft.depot.utils.SpringOpenFlashUtil;
import com.pchome.utils.CommonUtils;

public class ReportExcerptAction extends BaseReportAction {

	private static final long serialVersionUID = 3038297271832911228L;

	private IAdActionReportService adActionReportService;
	private IAdGroupReportService adGroupReportService;
	private IAdReportService adReportService;
	private IPfpCodeService pfpCodeService;
	
	private LinkedHashMap<String, String> dateSelectMap; // 查詢日期的 rang map,查詢日期頁面顯示
	private boolean hasPfpCodeflag = false; // 是否有使用轉換追蹤的PFP帳號
	
	private String viewType = "campagin"; // 預設總廣告成效(campagin)、分類(adGroup)、廣告明細(advertise)
	private String adActionSeq = ""; // 廣告活動序號
	private String adGroupSeq = ""; // 廣告分類序號
	private String startDate = ""; // 查詢開始日期
	private String endDate = ""; // 查詢結束日期
	private String searchText = ""; // 搜尋文字
	private String whereMap; // 篩選
	private String sortBy = ""; // 排序
	private int page = 1; // 第幾頁
	private int pageSize = 10; // 每頁筆數
	private int totalPage = 0; //總頁數
	
	private List<?> resultData = new ArrayList<>(); // 查詢結果
	private List<?> resultSumData = new ArrayList<>(); // 查詢結果加總
	
	// 下載報表
	private String isDownload = "false";
	private IPfpCustomerInfoService customerInfoService;
	private InputStream downloadFileStream; // 下載報表的 input stream
	private String downloadFileName; // 下載顯示名
	private String showHideColumn = ""; // 哪些欄位顯示或隱藏
	private Map<String, Boolean> showHideColumnMap = new HashMap<>(); // 紀錄顯示其他欄位值
	
	// 圖表
	private SpringOpenFlashUtil openFlashUtil;
	private String flashData;
	private String charType = ""; // 度量
	
	// 商品成效報表
	private InputStream returnData;
	private String adSeq;
	private String pfpCustomerInfoId;
	private String admServer;
		
	/**
	 * 查詢
	 */
	@Override
	public String execute() throws Exception {
	
		dateSelectMap = DateValueUtil.getInstance().getDateRangeMap();
		
		String startDateCookie = super.getChoose_start_date();
		String endDateCookie = super.getChoose_end_date();
		log.info(">>> startDateCookie = " + startDateCookie);
		log.info(">>> endDateCookie = " + endDateCookie);

		if (StringUtils.isEmpty(startDate)) {
			if (StringUtils.isNotEmpty(startDateCookie)) {
				startDate = startDateCookie;
			} else {
				startDate = DateValueUtil.getInstance().dateToString(DateValueUtil.getInstance().getDateForStartDateAddDay(DateValueUtil.getInstance().getDateValue(DateValueUtil.TODAY, DateValueUtil.DBPATH), -30));
			}
		}

		if (StringUtils.isEmpty(endDate)) {
			if (StringUtils.isNotEmpty(endDateCookie)) {
				endDate = endDateCookie;
			} else {
				endDate = DateValueUtil.getInstance().getDateValue(DateValueUtil.TODAY, DateValueUtil.DBPATH);
			}
		}
		
		// 查詢日期寫進 cookie
		super.setChooseDate(startDate, endDate);
		
		// 檢查是否有使用轉換追蹤的PFP帳號
		PfpCode pfpCode = pfpCodeService.getPfpCode(super.getCustomer_info_id());
		if (pfpCode != null) {
			hasPfpCodeflag = true;
		}
		
		if ("campagin".equals(viewType)) { // 第一層總廣告成效 = 廣告成效 campagin
			processCampaginData();
		} else if ("adGroup".equals(viewType)) { // 第二層 = 分類成效 adGroup
			processAdGroupData();
		} else if ("advertise".equals(viewType)) { // 第三層 = 廣告明細成效 Advertise
			processAdvertiseData();
		}
		
		if (Boolean.parseBoolean(isDownload)) {
			if ("campagin".equals(viewType)) { // 第一層總廣告成效 = 廣告成效 campagin
				processCampaginDownloadReport();
			} else if ("adGroup".equals(viewType)) { // 第二層 = 分類成效 adGroup
				processAdGroupDownloadReport();
			} else if ("advertise".equals(viewType)) { // 第三層 = 廣告明細成效 Advertise
				processAdvertiseDownloadReport();
			}
			viewType = SUCCESS;
		}
		
		return viewType;
	}

	/**
	 * 取得第一層總廣告成效資料
	 * @throws Exception
	 */
	private void processCampaginData() throws Exception {
		AdCampaginReportVO vo = new AdCampaginReportVO();
		vo.setCustomerInfoId(super.getCustomer_info_id());
		vo.setStartDate(startDate);
		vo.setEndDate(endDate);
		vo.setSearchText(searchText);
		vo.setWhereMap(whereMap);
		vo.setSortBy(sortBy);
		vo.setPage(page);
		vo.setPageSize(pageSize);
		vo.setDownloadOrIsNotCuttingPagination(Boolean.parseBoolean(isDownload));
		resultData = adActionReportService.queryReportAdCampaginData(vo);
		resultSumData = adActionReportService.queryReportAdCampaginSumData(vo);
		totalPage = CommonUtils.getTotalPage(vo.getRowCount(), pageSize);
	}

	/**
	 * 取得第二層 = 分類成效 adGroup資料
	 * @throws Exception 
	 */
	private void processAdGroupData() throws Exception {
		AdGroupReportVO vo = new AdGroupReportVO();
		vo.setAdActionSeq(adActionSeq);
		vo.setCustomerInfoId(super.getCustomer_info_id());
		vo.setStartDate(startDate);
		vo.setEndDate(endDate);
		vo.setSearchText(searchText);
		vo.setWhereMap(whereMap);
		vo.setSortBy(sortBy);
		vo.setPage(page);
		vo.setPageSize(pageSize);
		vo.setDownloadOrIsNotCuttingPagination(Boolean.parseBoolean(isDownload));
		resultData = adGroupReportService.queryReportAdGroupData(vo);
		resultSumData = adGroupReportService.queryReportAdGroupSumData(vo);
		totalPage = CommonUtils.getTotalPage(vo.getRowCount(), pageSize);
	}

	/**
	 * 取得第三層 = 廣告明細成效 Advertise資料
	 * @throws Exception 
	 */
	private void processAdvertiseData() throws Exception {
		AdvertiseReportVO vo = new AdvertiseReportVO();
		vo.setAdGroupSeq(adGroupSeq);
		vo.setCustomerInfoId(super.getCustomer_info_id());
		vo.setStartDate(startDate);
		vo.setEndDate(endDate);
		vo.setSearchText(searchText);
		vo.setWhereMap(whereMap);
		vo.setSortBy(sortBy);
		vo.setPage(page);
		vo.setPageSize(pageSize);
		vo.setDownloadOrIsNotCuttingPagination(Boolean.parseBoolean(isDownload));
		resultData = adReportService.queryReportAdvertiseData(vo);
		resultSumData = adReportService.queryReportAdvertiseSumData(vo);
		totalPage = CommonUtils.getTotalPage(vo.getRowCount(), pageSize);
	}

	/**
	 * 下載第一層Campagin報表
	 * 先執行execute()，是下載報表才再執行此方法
	 * @throws Exception
	 */
	private void processCampaginDownloadReport() throws Exception {
		
		SimpleDateFormat dformat = new SimpleDateFormat("yyyyMMddhhmmss");
		String filename = "總廣告成效_" + dformat.format(new Date()) + FILE_TYPE;
		PfpCustomerInfo customerInfo = customerInfoService.findCustomerInfo(super.getCustomer_info_id());

		// 紀錄顯示其他欄位，哪些顯示哪些不顯示
		String[] showHideColumnArr = showHideColumn.split(",");
		for (int i = 0; i < showHideColumnArr.length; i++) {
			String mapKey = showHideColumnArr[i].split("-")[0];
			boolean mapVal = Boolean.parseBoolean(showHideColumnArr[i].split("-")[1]);
			showHideColumnMap.put(mapKey, mapVal);
		}
		
		StringBuilder content = new StringBuilder();
		content.append("帳戶," + customerInfo.getCustomerInfoTitle());
		content.append("\n");
		content.append("搜尋內容," + searchText);
		content.append("\n");
		content.append("日期範圍," + startDate + " 到 " + endDate);
		content.append("\n\n");
		
		content.append("狀態,廣告活動,");

		if (showHideColumnMap.get(EnumReport.ADTYPE.getTextValue())) {
			content.append("播放類型,");
		}
		if (showHideColumnMap.get(EnumReport.AD_OPERATING_RULE.getTextValue())) {
			content.append("廣告樣式,");
		}
		if (showHideColumnMap.get(EnumReport.AD_DATE.getTextValue())) {
			content.append("走期,");
		}
		
		content.append("裝置,曝光數,互動數,互動率,無效點選次數,單次互動費用,千次曝光費用,費用,");

		if (showHideColumnMap.get(EnumReport.CONVERT_COUNT.getTextValue())) {
			content.append("轉換次數,");
		}
		if (showHideColumnMap.get(EnumReport.CONVERT_CTR.getTextValue())) {
			content.append("轉換率,");
		}
		if (showHideColumnMap.get(EnumReport.CONVERT_PRICE_COUNT.getTextValue())) {
			content.append("總轉換價值,");
		}
		if (showHideColumnMap.get(EnumReport.CONVERT_COST.getTextValue())) {
			content.append("平均轉換成本,");
		}
		if (showHideColumnMap.get(EnumReport.CONVERT_INVESTMENT_COST.getTextValue())) {
			content.append("廣告投資報酬率,");
		}
		content.append("\n");
		
		if (!resultData.isEmpty()) {
			List<AdCampaginReportVO> reportData = new ArrayList<>((List<AdCampaginReportVO>) resultData);
			List<AdCampaginReportVO> reportSumData = new ArrayList<>((List<AdCampaginReportVO>) resultSumData);

			// 明細資料
			for (int i = 0; i < reportData.size(); i++) {
				content.append("\"" + reportData.get(i).getAdStatusName() + "\",");
				content.append("\"" + reportData.get(i).getAdActionName() + "\",");
				
				if (showHideColumnMap.get(EnumReport.ADTYPE.getTextValue())) {
					content.append("\"" + reportData.get(i).getAdType() + "\",");
				}
				if (showHideColumnMap.get(EnumReport.AD_OPERATING_RULE.getTextValue())) {
					content.append("\"" + reportData.get(i).getAdOperatingRule() + "\",");
				}
				if (showHideColumnMap.get(EnumReport.AD_DATE.getTextValue())) {
					content.append("\"" + reportData.get(i).getAdActionStartDate() + " ~ " + reportData.get(i).getAdActionEndDate() + "\",");
				}
				
				content.append("\"" + reportData.get(i).getAdDevice() + "\",");
				content.append("\"" + intFormat.format(reportData.get(i).getAdPvSum()) + "\",");
				content.append("\"" + doubleFormat.format(reportData.get(i).getAdClkSum()) + "\",");
				content.append("\"" + doubleFormat.format(reportData.get(i).getCtr()) + "%\",");
				content.append("\"" + doubleFormat.format(reportData.get(i).getAdInvClkSum()) + "\",");
				content.append("\"NT$ " + doubleFormat.format(reportData.get(i).getAvgCost()) + "\",");
				content.append("\"NT$ " + doubleFormat.format(reportData.get(i).getKiloCost()) + "\",");
				content.append("\"NT$ " + doubleFormat2.format(reportData.get(i).getAdPriceSum()) + "\",");
				
				if (showHideColumnMap.get(EnumReport.CONVERT_COUNT.getTextValue())) {
					content.append("\"" + doubleFormat.format(reportData.get(i).getConvertCount()) + "\",");
				}
				if (showHideColumnMap.get(EnumReport.CONVERT_CTR.getTextValue())) {
					content.append("\"" + doubleFormat.format(reportData.get(i).getConvertCTR()) + "%\",");
				}
				if (showHideColumnMap.get(EnumReport.CONVERT_PRICE_COUNT.getTextValue())) {
					content.append("\"NT$ " + doubleFormat.format(reportData.get(i).getConvertPriceCount()) + "\",");
				}
				if (showHideColumnMap.get(EnumReport.CONVERT_COST.getTextValue())) {
					content.append("\"NT$ " + doubleFormat.format(reportData.get(i).getConvertCost()) + "\",");
				}
				if (showHideColumnMap.get(EnumReport.CONVERT_INVESTMENT_COST.getTextValue())) {
					content.append("\"" + doubleFormat.format(reportData.get(i).getConvertInvestmentCost()) + "%\",");
				}
				content.append("\n");
			}
			
			content.append("\n");
			
			// 總計資料
			for (int i = 0; i < resultSumData.size(); i++) {
				content.append("\"\",");
				content.append("\"總計：" + intFormat.format(reportSumData.get(i).getRowCount()) + "\",");

				if (showHideColumnMap.get(EnumReport.ADTYPE.getTextValue())) {
					content.append("\"\",");
				}
				if (showHideColumnMap.get(EnumReport.AD_OPERATING_RULE.getTextValue())) {
					content.append("\"\",");
				}
				if (showHideColumnMap.get(EnumReport.AD_DATE.getTextValue())) {
					content.append("\"\",");
				}

				content.append("\"\",");
				content.append("\"" + doubleFormat.format(reportSumData.get(i).getAdPvSum()) + "\",");
				content.append("\"" + doubleFormat.format(reportSumData.get(i).getAdClkSum()) + "\",");
				content.append("\"" + doubleFormat.format(reportSumData.get(i).getCtr()) + "%\",");
				content.append("\"" + doubleFormat.format(reportSumData.get(i).getAdInvClkSum()) + "\",");
				content.append("\"NT$ " + doubleFormat.format(reportSumData.get(i).getAvgCost()) + "\",");
				content.append("\"NT$ " + doubleFormat.format(reportSumData.get(i).getKiloCost()) + "\",");
				content.append("\"NT$ " + doubleFormat2.format(reportSumData.get(i).getAdPriceSum()) + "\",");
				
				if (showHideColumnMap.get(EnumReport.CONVERT_COUNT.getTextValue())) {
					content.append("\"" + doubleFormat.format(reportSumData.get(i).getConvertCount()) + "\",");
				}
				if (showHideColumnMap.get(EnumReport.CONVERT_CTR.getTextValue())) {
					content.append("\"" + doubleFormat.format(reportSumData.get(i).getConvertCTR()) + "%\",");
				}
				if (showHideColumnMap.get(EnumReport.CONVERT_PRICE_COUNT.getTextValue())) {
					content.append("\"NT$ " + doubleFormat.format(reportSumData.get(i).getConvertPriceCount()) + "\",");
				}
				if (showHideColumnMap.get(EnumReport.CONVERT_COST.getTextValue())) {
					content.append("\"NT$ " + doubleFormat.format(reportSumData.get(i).getConvertCost()) + "\",");
				}
				if (showHideColumnMap.get(EnumReport.CONVERT_INVESTMENT_COST.getTextValue())) {
					content.append("\"" + doubleFormat.format(reportSumData.get(i).getConvertInvestmentCost()) + "%\",");
				}
			}
		}
		
		if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > -1) {
			downloadFileName = new String(filename.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
		} else {
			downloadFileName = URLEncoder.encode(filename, "UTF-8");
		}

		downloadFileStream = new ByteArrayInputStream(content.toString().getBytes("big5"));
	}
	
	/**
	 * 下載第二層AdGroup報表
	 * 先執行execute()，是下載報表才再執行此方法
	 * @throws Exception
	 */
	private void processAdGroupDownloadReport() throws Exception {
		SimpleDateFormat dformat = new SimpleDateFormat("yyyyMMddhhmmss");
		String filename = "總廣告成效_" + dformat.format(new Date()) + FILE_TYPE;
		PfpCustomerInfo customerInfo = customerInfoService.findCustomerInfo(super.getCustomer_info_id());

		// 紀錄顯示其他欄位，哪些顯示哪些不顯示
		String[] showHideColumnArr = showHideColumn.split(",");
		for (int i = 0; i < showHideColumnArr.length; i++) {
			String mapKey = showHideColumnArr[i].split("-")[0];
			boolean mapVal = Boolean.parseBoolean(showHideColumnArr[i].split("-")[1]);
			showHideColumnMap.put(mapKey, mapVal);
		}
		
		StringBuilder content = new StringBuilder();
		content.append("帳戶," + customerInfo.getCustomerInfoTitle());
		content.append("\n");
		content.append("搜尋內容," + searchText);
		content.append("\n");
		content.append("日期範圍," + startDate + " 到 " + endDate);
		content.append("\n\n");
		
		content.append("狀態,廣告分類,");

		if (showHideColumnMap.get(EnumReport.ADTYPE.getTextValue())) {
			content.append("播放類型,");
		}

		if (showHideColumnMap.get(EnumReport.AD_OPERATING_RULE.getTextValue())) {
			content.append("廣告樣式,");
		}

		if (showHideColumnMap.get(EnumReport.AD_CLK_PRICE_TYPE.getTextValue())) {
			content.append("計價方式,");
		}

		content.append("裝置,曝光數,互動數,互動率,無效點選次數,單次互動費用,千次曝光費用,費用,");

		if (showHideColumnMap.get(EnumReport.CONVERT_COUNT.getTextValue())) {
			content.append("轉換次數,");
		}

		if (showHideColumnMap.get(EnumReport.CONVERT_CTR.getTextValue())) {
			content.append("轉換率,");
		}

		if (showHideColumnMap.get(EnumReport.CONVERT_PRICE_COUNT.getTextValue())) {
			content.append("總轉換價值,");
		}

		if (showHideColumnMap.get(EnumReport.CONVERT_COST.getTextValue())) {
			content.append("平均轉換成本,");
		}

		if (showHideColumnMap.get(EnumReport.CONVERT_INVESTMENT_COST.getTextValue())) {
			content.append("廣告投資報酬率,");
		}
		content.append("\n");
		
		if (!resultData.isEmpty()) {
			List<AdGroupReportVO> reportData = new ArrayList<>((List<AdGroupReportVO>) resultData);
			List<AdGroupReportVO> reportSumData = new ArrayList<>((List<AdGroupReportVO>) resultSumData);

			// 明細資料
			for (int i = 0; i < reportData.size(); i++) {
				content.append("\"" + reportData.get(i).getAdStatusName() + "\",");
				content.append("\"" + reportData.get(i).getAdGroupName() + "\",");
				
				if (showHideColumnMap.get(EnumReport.ADTYPE.getTextValue())) {
					content.append("\"" + reportData.get(i).getAdType() + "\",");
				}
				if (showHideColumnMap.get(EnumReport.AD_OPERATING_RULE.getTextValue())) {
					content.append("\"" + reportData.get(i).getAdOperatingRule() + "\",");
				}
				if (showHideColumnMap.get(EnumReport.AD_CLK_PRICE_TYPE.getTextValue())) {
					content.append("\"" + reportData.get(i).getAdClkPriceType() + "\",");
				}
				
				content.append("\"" + reportData.get(i).getAdDevice() + "\",");
				content.append("\"" + intFormat.format(reportData.get(i).getAdPvSum()) + "\",");
				content.append("\"" + doubleFormat.format(reportData.get(i).getAdClkSum()) + "\",");
				content.append("\"" + doubleFormat.format(reportData.get(i).getCtr()) + "%\",");
				content.append("\"" + doubleFormat.format(reportData.get(i).getAdInvClkSum()) + "\",");
				content.append("\"NT$ " + doubleFormat.format(reportData.get(i).getAvgCost()) + "\",");
				content.append("\"NT$ " + doubleFormat.format(reportData.get(i).getKiloCost()) + "\",");
				content.append("\"NT$ " + doubleFormat2.format(reportData.get(i).getAdPriceSum()) + "\",");
				
				if (showHideColumnMap.get(EnumReport.CONVERT_COUNT.getTextValue())) {
					content.append("\"" + doubleFormat.format(reportData.get(i).getConvertCount()) + "\",");
				}
				if (showHideColumnMap.get(EnumReport.CONVERT_CTR.getTextValue())) {
					content.append("\"" + doubleFormat.format(reportData.get(i).getConvertCTR()) + "%\",");
				}
				if (showHideColumnMap.get(EnumReport.CONVERT_PRICE_COUNT.getTextValue())) {
					content.append("\"NT$ " + doubleFormat.format(reportData.get(i).getConvertPriceCount()) + "\",");
				}
				if (showHideColumnMap.get(EnumReport.CONVERT_COST.getTextValue())) {
					content.append("\"NT$ " + doubleFormat.format(reportData.get(i).getConvertCost()) + "\",");
				}
				if (showHideColumnMap.get(EnumReport.CONVERT_INVESTMENT_COST.getTextValue())) {
					content.append("\"" + doubleFormat.format(reportData.get(i).getConvertInvestmentCost()) + "%\",");
				}
				content.append("\n");
			}
			
			content.append("\n");
			
			// 總計資料
			for (int i = 0; i < reportSumData.size(); i++) {
				content.append("\"\",");
				content.append("\"總計：" + intFormat.format(reportSumData.get(i).getRowCount()) + "\",");
				
				if (showHideColumnMap.get(EnumReport.ADTYPE.getTextValue())) {
					content.append("\"\",");
				}
				if (showHideColumnMap.get(EnumReport.AD_OPERATING_RULE.getTextValue())) {
					content.append("\"\",");
				}
				if (showHideColumnMap.get(EnumReport.AD_CLK_PRICE_TYPE.getTextValue())) {
					content.append("\"\",");
				}
				content.append("\"\",");
				content.append("\"" + doubleFormat.format(reportSumData.get(i).getAdPvSum()) + "\",");
				content.append("\"" + doubleFormat.format(reportSumData.get(i).getAdClkSum()) + "\",");
				content.append("\"" + doubleFormat.format(reportSumData.get(i).getCtr()) + "%\",");
				content.append("\"" + doubleFormat.format(reportSumData.get(i).getAdInvClkSum()) + "\",");
				content.append("\"NT$ " + doubleFormat.format(reportSumData.get(i).getAvgCost()) + "\",");
				content.append("\"NT$ " + doubleFormat.format(reportSumData.get(i).getKiloCost()) + "\",");
				content.append("\"NT$ " + doubleFormat2.format(reportSumData.get(i).getAdPriceSum()) + "\",");
				
				if (showHideColumnMap.get(EnumReport.CONVERT_COUNT.getTextValue())) {
					content.append("\"" + doubleFormat.format(reportSumData.get(i).getConvertCount()) + "\",");
				}
				if (showHideColumnMap.get(EnumReport.CONVERT_CTR.getTextValue())) {
					content.append("\"" + doubleFormat.format(reportSumData.get(i).getConvertCTR()) + "%\",");
				}
				if (showHideColumnMap.get(EnumReport.CONVERT_PRICE_COUNT.getTextValue())) {
					content.append("\"NT$ " + doubleFormat.format(reportSumData.get(i).getConvertPriceCount()) + "\",");
				}
				if (showHideColumnMap.get(EnumReport.CONVERT_COST.getTextValue())) {
					content.append("\"NT$ " + doubleFormat.format(reportSumData.get(i).getConvertCost()) + "\",");
				}
				if (showHideColumnMap.get(EnumReport.CONVERT_INVESTMENT_COST.getTextValue())) {
					content.append("\"" + doubleFormat.format(reportSumData.get(i).getConvertInvestmentCost()) + "%\",");
				}
			}
		}
		
		if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > -1) {
			downloadFileName = new String(filename.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
		} else {
			downloadFileName = URLEncoder.encode(filename, "UTF-8");
		}

		downloadFileStream = new ByteArrayInputStream(content.toString().getBytes("big5"));
	}
	
	/**
	 * 下載第三層Advertise報表
	 * 先執行execute()，是下載報表才再執行此方法
	 * @throws Exception
	 */
	private void processAdvertiseDownloadReport() throws Exception {
		
		SimpleDateFormat dformat = new SimpleDateFormat("yyyyMMddhhmmss");
		String filename = "總廣告成效_" + dformat.format(new Date()) + FILE_TYPE;
		PfpCustomerInfo customerInfo = customerInfoService.findCustomerInfo(super.getCustomer_info_id());

		// 紀錄顯示其他欄位，哪些顯示哪些不顯示
		String[] showHideColumnArr = showHideColumn.split(",");
		for (int i = 0; i < showHideColumnArr.length; i++) {
			String mapKey = showHideColumnArr[i].split("-")[0];
			boolean mapVal = Boolean.parseBoolean(showHideColumnArr[i].split("-")[1]);
			showHideColumnMap.put(mapKey, mapVal);
		}
		
		StringBuilder content = new StringBuilder();
		content.append("帳戶," + customerInfo.getCustomerInfoTitle());
		content.append("\n");
		content.append("搜尋內容," + searchText);
		content.append("\n");
		content.append("日期範圍," + startDate + " 到 " + endDate);
		content.append("\n\n");
		
		content.append("狀態,廣告名稱,廣告內容,影片長度,顯示連結,實際連結,");

		if (showHideColumnMap.get(EnumReport.ADTYPE.getTextValue())) {
			content.append("播放類型,");
		}

		if (showHideColumnMap.get(EnumReport.AD_OPERATING_RULE.getTextValue())) {
			content.append("廣告樣式,");
		}

		if (showHideColumnMap.get(EnumReport.AD_CLK_PRICE_TYPE.getTextValue())) {
			content.append("計價方式,");
		}

		content.append("裝置,曝光數,互動數,互動率,無效點選次數,單次互動費用,千次曝光費用,費用,");

		if (showHideColumnMap.get(EnumReport.CONVERT_COUNT.getTextValue())) {
			content.append("轉換次數,");
		}

		if (showHideColumnMap.get(EnumReport.CONVERT_CTR.getTextValue())) {
			content.append("轉換率,");
		}

		if (showHideColumnMap.get(EnumReport.CONVERT_PRICE_COUNT.getTextValue())) {
			content.append("總轉換價值,");
		}

		if (showHideColumnMap.get(EnumReport.CONVERT_COST.getTextValue())) {
			content.append("平均轉換成本,");
		}

		if (showHideColumnMap.get(EnumReport.CONVERT_INVESTMENT_COST.getTextValue())) {
			content.append("廣告投資報酬率,");
		}
		content.append("\n");
		
		if (!resultData.isEmpty()) {
			List<AdvertiseReportVO> reportData = new ArrayList<>((List<AdvertiseReportVO>) resultData);
			List<AdvertiseReportVO> reportSumData = new ArrayList<>((List<AdvertiseReportVO>) resultSumData);
			
			// 明細資料
			for (int i = 0; i < reportData.size(); i++) {
				content.append("\"" + reportData.get(i).getAdStatusName() + "\",");
				content.append("\"" + reportData.get(i).getAdName() + "\",");
				content.append("\"" + reportData.get(i).getContent() + "\",");
				String videoSec = StringUtils.isBlank(reportData.get(i).getVideoSec()) ? "" : "00:" + reportData.get(i).getVideoSec();
				content.append("\"" + videoSec + "\",");
				content.append("\"" + reportData.get(i).getShowUrl() + "\",");
				content.append("\"" + reportData.get(i).getRealUrl() + "\",");
				
				if (showHideColumnMap.get(EnumReport.ADTYPE.getTextValue())) {
					content.append("\"" + reportData.get(i).getAdType() + "\",");
				}
				if (showHideColumnMap.get(EnumReport.AD_OPERATING_RULE.getTextValue())) {
					content.append("\"" + reportData.get(i).getAdOperatingRule() + "\",");
				}
				if (showHideColumnMap.get(EnumReport.AD_CLK_PRICE_TYPE.getTextValue())) {
					content.append("\"" + reportData.get(i).getAdClkPriceType() + "\",");
				}
				
				content.append("\"" + reportData.get(i).getAdDevice() + "\",");
				content.append("\"" + intFormat.format(reportData.get(i).getAdPvSum()) + "\",");
				content.append("\"" + doubleFormat.format(reportData.get(i).getAdClkSum()) + "\",");
				content.append("\"" + doubleFormat.format(reportData.get(i).getCtr()) + "%\",");
				content.append("\"" + doubleFormat.format(reportData.get(i).getAdInvClkSum()) + "\",");
				content.append("\"NT$ " + doubleFormat.format(reportData.get(i).getAvgCost()) + "\",");
				content.append("\"NT$ " + doubleFormat.format(reportData.get(i).getKiloCost()) + "\",");
				content.append("\"NT$ " + doubleFormat2.format(reportData.get(i).getAdPriceSum()) + "\",");
				
				if (showHideColumnMap.get(EnumReport.CONVERT_COUNT.getTextValue())) {
					content.append("\"" + doubleFormat.format(reportData.get(i).getConvertCount()) + "\",");
				}
				if (showHideColumnMap.get(EnumReport.CONVERT_CTR.getTextValue())) {
					content.append("\"" + doubleFormat.format(reportData.get(i).getConvertCTR()) + "%\",");
				}
				if (showHideColumnMap.get(EnumReport.CONVERT_PRICE_COUNT.getTextValue())) {
					content.append("\"NT$ " + doubleFormat.format(reportData.get(i).getConvertPriceCount()) + "\",");
				}
				if (showHideColumnMap.get(EnumReport.CONVERT_COST.getTextValue())) {
					content.append("\"NT$ " + doubleFormat.format(reportData.get(i).getConvertCost()) + "\",");
				}
				if (showHideColumnMap.get(EnumReport.CONVERT_INVESTMENT_COST.getTextValue())) {
					content.append("\"" + doubleFormat.format(reportData.get(i).getConvertInvestmentCost()) + "%\",");
				}
				content.append("\n");
			}
			
			content.append("\n");
			
			// 總計資料
			for (int i = 0; i < reportSumData.size(); i++) {
				content.append("\"總計：" + intFormat.format(reportSumData.get(i).getRowCount()) + "\",");
				content.append("\"\",");
				content.append("\"\",");
				content.append("\"\",");
				content.append("\"\",");
				content.append("\"\",");
				
				if (showHideColumnMap.get(EnumReport.ADTYPE.getTextValue())) {
					content.append("\"\",");
				}
				if (showHideColumnMap.get(EnumReport.AD_OPERATING_RULE.getTextValue())) {
					content.append("\"\",");
				}
				if (showHideColumnMap.get(EnumReport.AD_CLK_PRICE_TYPE.getTextValue())) {
					content.append("\"\",");
				}
				content.append("\"\",");
				content.append("\"" + doubleFormat.format(reportSumData.get(i).getAdPvSum()) + "\",");
				content.append("\"" + doubleFormat.format(reportSumData.get(i).getAdClkSum()) + "\",");
				content.append("\"" + doubleFormat.format(reportSumData.get(i).getCtr()) + "%\",");
				content.append("\"" + doubleFormat.format(reportSumData.get(i).getAdInvClkSum()) + "\",");
				content.append("\"NT$ " + doubleFormat.format(reportSumData.get(i).getAvgCost()) + "\",");
				content.append("\"NT$ " + doubleFormat.format(reportSumData.get(i).getKiloCost()) + "\",");
				content.append("\"NT$ " + doubleFormat2.format(reportSumData.get(i).getAdPriceSum()) + "\",");
				
				if (showHideColumnMap.get(EnumReport.CONVERT_COUNT.getTextValue())) {
					content.append("\"" + doubleFormat.format(reportSumData.get(i).getConvertCount()) + "\",");
				}
				if (showHideColumnMap.get(EnumReport.CONVERT_CTR.getTextValue())) {
					content.append("\"" + doubleFormat.format(reportSumData.get(i).getConvertCTR()) + "%\",");
				}
				if (showHideColumnMap.get(EnumReport.CONVERT_PRICE_COUNT.getTextValue())) {
					content.append("\"NT$ " + doubleFormat.format(reportSumData.get(i).getConvertPriceCount()) + "\",");
				}
				if (showHideColumnMap.get(EnumReport.CONVERT_COST.getTextValue())) {
					content.append("\"NT$ " + doubleFormat.format(reportSumData.get(i).getConvertCost()) + "\",");
				}
				if (showHideColumnMap.get(EnumReport.CONVERT_INVESTMENT_COST.getTextValue())) {
					content.append("\"" + doubleFormat.format(reportSumData.get(i).getConvertInvestmentCost()) + "%\",");
				}
			}
		}
		
		if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > -1) {
			downloadFileName = new String(filename.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
		} else {
			downloadFileName = URLEncoder.encode(filename, "UTF-8");
		}

		downloadFileStream = new ByteArrayInputStream(content.toString().getBytes("big5"));
	}
	
	/**
	 * 圖表
	 * @return
	 * @throws Exception
	 */
	public String flashDataDownLoad() {
		Map<Date, Float> flashDataMap = new HashMap<>();
		if ("campagin".equals(viewType)) { // 第一層總廣告成效 = 廣告成效 campagin
			
			AdCampaginReportVO chartVo = new AdCampaginReportVO();
			chartVo.setCharType(charType);
			chartVo.setCustomerInfoId(super.getCustomer_info_id());
			chartVo.setStartDate(startDate);
			chartVo.setEndDate(endDate);
			chartVo.setSearchText(searchText);
			chartVo.setWhereMap(whereMap);
			chartVo.setDownloadOrIsNotCuttingPagination(true);
			flashDataMap = adActionReportService.queryReportAdCampaginChartDataMap(chartVo);
		} else if ("adGroup".equals(viewType)) { // 第二層 = 分類成效 adGroup
			
			AdGroupReportVO chartVo = new AdGroupReportVO();
			chartVo.setCharType(charType);
			chartVo.setAdActionSeq(adActionSeq);
			chartVo.setCustomerInfoId(super.getCustomer_info_id());
			chartVo.setStartDate(startDate);
			chartVo.setEndDate(endDate);
			chartVo.setSearchText(searchText);
			chartVo.setWhereMap(whereMap);
			flashDataMap = adGroupReportService.queryReportAdGroupChartDataMap(chartVo);
		} else if ("advertise".equals(viewType)) { // 第三層 = 廣告明細成效 Advertise
			
			AdvertiseReportVO chartVo = new AdvertiseReportVO();
			chartVo.setCharType(charType);
			chartVo.setAdGroupSeq(adGroupSeq);
			chartVo.setCustomerInfoId(super.getCustomer_info_id());
			chartVo.setStartDate(startDate);
			chartVo.setEndDate(endDate);
			chartVo.setSearchText(searchText);
			chartVo.setWhereMap(whereMap);
			flashDataMap = adReportService.queryReportAdvertiseChartDataMap(chartVo);
		}
		
		flashData = openFlashUtil.getChartDataForArray(charType, startDate, endDate, flashDataMap);
		return SUCCESS;
	}
	
	/**
	 * 商品成效報表
	 * @return
	 */
	public String adProdDetailReport() {
		String prodData = com.pchome.soft.depot.utils.HttpUtil.getInstance().getResult(admServer + "adDataRMIAction.html?pfpCustomerInfoId=" + pfpCustomerInfoId + "&adSeq=" + adSeq + "&startDate=" + startDate + "&endDate=" + endDate, "UTF-8");
		returnData = new ByteArrayInputStream(prodData.getBytes(StandardCharsets.UTF_8));
		return SUCCESS;
	}
	
	public InputStream getReturnData() {
		return returnData;
	}

	public String getAdmServer() {
		return admServer;
	}

	public void setAdmServer(String admServer) {
		this.admServer = admServer;
	}

	public void setAdSeq(String adSeq) {
		this.adSeq = adSeq;
	}

	public void setPfpCustomerInfoId(String pfpCustomerInfoId) {
		this.pfpCustomerInfoId = pfpCustomerInfoId;
	}

	public String getViewType() {
		return viewType;
	}

	public void setViewType(String viewType) {
		this.viewType = viewType;
	}

	public LinkedHashMap<String, String> getDateSelectMap() {
		return dateSelectMap;
	}

	public void setAdActionReportService(IAdActionReportService adActionReportService) {
		this.adActionReportService = adActionReportService;
	}
	
	public void setAdGroupReportService(IAdGroupReportService adGroupReportService) {
		this.adGroupReportService = adGroupReportService;
	}

	public void setAdReportService(IAdReportService adReportService) {
		this.adReportService = adReportService;
	}

	public void setPfpCodeService(IPfpCodeService pfpCodeService) {
		this.pfpCodeService = pfpCodeService;
	}

	public boolean isHasPfpCodeflag() {
		return hasPfpCodeflag;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public void setWhereMap(String whereMap) {
		this.whereMap = whereMap;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public List<?> getResultData() {
		return resultData;
	}

	public List<?> getResultSumData() {
		return resultSumData;
	}

	public void setIsDownload(String isDownload) {
		this.isDownload = isDownload;
	}

	public void setCustomerInfoService(IPfpCustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}

	public InputStream getDownloadFileStream() {
		return downloadFileStream;
	}

	public String getDownloadFileName() {
		return downloadFileName;
	}

	public void setShowHideColumn(String showHideColumn) {
		this.showHideColumn = showHideColumn;
	}

	public void setOpenFlashUtil(SpringOpenFlashUtil openFlashUtil) {
		this.openFlashUtil = openFlashUtil;
	}

	public String getFlashData() {
		return flashData;
	}

	public void setCharType(String charType) {
		this.charType = charType;
	}

	public void setAdActionSeq(String adActionSeq) {
		this.adActionSeq = adActionSeq;
	}

	public void setAdGroupSeq(String adGroupSeq) {
		this.adGroupSeq = adGroupSeq;
	}
	
}