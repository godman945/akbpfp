package com.pchome.akbpfp.struts2.action.report;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.StringEscapeUtils;

import com.pchome.enumerate.ad.EnumAdType;
import com.pchome.enumerate.report.EnumReport;
import com.pchome.enumerate.utils.EnumStatus;
import com.pchome.akbpfp.db.dao.ad.IPfpAdDAO;
import com.pchome.akbpfp.db.dao.ad.IPfpAdKeywordDAO;
import com.pchome.akbpfp.db.dao.report.AdActionReportVO;
import com.pchome.akbpfp.db.dao.report.AdCampaginReportVO;
import com.pchome.akbpfp.db.dao.report.AdGroupReportVO;
import com.pchome.akbpfp.db.dao.report.AdReportVO;
import com.pchome.akbpfp.db.dao.report.AdvertiseReportVO;
import com.pchome.akbpfp.db.dao.report.AdKeywordReportVO;
import com.pchome.akbpfp.db.pojo.PfpAd;
import com.pchome.akbpfp.db.pojo.PfpAdAction;
import com.pchome.akbpfp.db.pojo.PfpAdGroup;
import com.pchome.akbpfp.db.pojo.PfpAdKeyword;
import com.pchome.akbpfp.db.pojo.PfpCode;
import com.pchome.akbpfp.db.pojo.PfpCustomerInfo;
import com.pchome.akbpfp.db.service.customerInfo.IPfpCustomerInfoService;
import com.pchome.akbpfp.db.service.ad.IPfpAdActionService;
import com.pchome.akbpfp.db.service.ad.IPfpAdGroupService;
import com.pchome.akbpfp.db.service.codeManage.IPfpCodeService;
import com.pchome.akbpfp.db.service.report.IAdActionReportService;
import com.pchome.akbpfp.db.service.report.IAdGroupReportService;
import com.pchome.akbpfp.db.service.report.IAdKeywordReportService;
import com.pchome.akbpfp.db.service.report.IAdReportService;
import com.pchome.soft.depot.utils.DateValueUtil;
import com.pchome.utils.CommonUtils;
import com.pchome.soft.depot.utils.SpringOpenFlashUtil;

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
		
		StringBuffer content = new StringBuffer();
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
		
		if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") >= 1) {
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
		
		StringBuffer content = new StringBuffer();
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
		
		if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") >= 1) {
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
		
		StringBuffer content = new StringBuffer();
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
		
		if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") >= 1) {
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
//			List<AdvertiseReportVO> resultChartData = adReportService.queryReportAdvertiseChartData(chartVo);
			flashDataMap = adReportService.queryReportAdvertiseChartDataMap(chartVo);
		}
		
		flashData = openFlashUtil.getChartDataForArray(charType, startDate, endDate, flashDataMap);

		return SUCCESS;
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
	
	
	
	
//	private LinkedList<String> tableHeadList =null; //頁面table head
//	private LinkedList<String> tableDataTotalList =null; //頁面全部加總table total foot
//
//	private LinkedList<LinkedList<String>> tableDataList = null; // 頁面 table data
//
//	private String[] align_data = {"left", "center", "center", "center", "center", "center", "right", "right", "right", "right", "right", "right", "right", "right", "right", "right", "right", "right"};
//	private String[] align_sum = {"center", "center", "center", "center", "center", "center", "right", "right", "right", "right", "right", "right", "right", "right", "right", "right", "right", "right"};
//
//	private IAdActionReportService adActionReportService=null;
//	private IAdGroupReportService adGroupReportService=null;
//	private IAdKeywordReportService adKeywordReportService=null;
//	private IAdReportService adReportService=null;
//
//	private IPfpAdActionService adActionService=null;
//	private IPfpAdGroupService adGroupService=null;
//	private IPfpCustomerInfoService customerInfoService=null;
//	private IPfpAdKeywordDAO pfpAdKeywordDAO;
//	private IPfpAdDAO pfpAdDAO;
//
//	private SpringOpenFlashUtil openFlashUtil=null;
//
//	private Map<String,String> tableHeadNameMap;//自訂欄位
//
//	private LinkedList<String> tableHeadShowList =null;//自訂欄位選擇list,頁面顯示
//	private LinkedList<String> tableHeadNotShowList =null;//自訂欄位不選擇list,頁面顯示
//
//	private String optionSelect="";//自訂欄位選擇,頁面回傳
//	private String optionNotSelect="";//自訂欄位不選擇,頁面回傳
//
//	private String startDate="";//查詢開始日期
//	private String endDate="";//查詢結束日期
//
//	private LinkedHashMap<String,String> dateSelectMap=null;//查詢日期的 rang map,查詢日期頁面顯示
//
//	private int page=0; //第幾頁
//	private int pageSize=0; //每頁筆數
//	private int totalPage=0; //總頁數
//
//	private String adPvclkDevice=""; //裝置:pc、mobile
//	private String adType=""; //廣告,分類,廣告明細,關鍵字
//	private String adSearchWay=""; //文字搜尋方式,包含,開頭,全部
//	private String searchText=""; //搜尋文字
//	private String adShowWay=""; //全部,搜尋廣告,聯播廣告
//	private String searchId=""; //廣告id,分類id,廣告明細id,關鍵字id
//	private String adOperatingRule;//廣告樣式
//
//	private String stepStr="";//頁面顯示,目前位址,廣告活動-->act01-->
//	
//	private String charPic="";//圖表格式
//	private String charType="";//度量
//
//	//download report 
//	private String downloadFlag="";//download report 旗標
//
//	private InputStream downloadFileStream;//下載報表的 input stream
//
//	private String downloadFileName;//下載顯示名
//
//	private String flashData;//flash chart json data
//
//	//private String flashInputValue;//flash chart 頁面傳進來的data
//
//	private String reportTitle;
//
//	private String showSearchBarFlag = "y";
//	
//	//關鍵字報表另外呈現
//	private List<AdKeywordReportVO> AdKeywordReportVOList;
//	private AdKeywordReportVO AdKeywordReportDataTotal;
//	private NumberFormat intFormat = new DecimalFormat("###,###,###,###");
//	private NumberFormat doubleFormat = new DecimalFormat("###,###,###,##.##");
//	private NumberFormat doubleFormat2 = new DecimalFormat("###,###,###,###.###");
//	private	DecimalFormat doubleFormat3 = new DecimalFormat("#,##0.00");
//	private IPfpCodeService pfpCodeService;
//	private boolean hasPfpCodeflag = false;
//	
//	//商品成效報表
//	private InputStream returnData;
//	private String adSeq;
//	private String pfpCustomerInfoId;
//	private String admServer;
//	/**
//	 * Chart 
//	 */
//	public String flashDataDownLoad() throws Exception {
//
//		this.checkRedirectSSLUrl();
//		if(StringUtils.isNotBlank(this.resultType)){
//			return this.resultType;
//		}
//		
//		/*log.info(">>> flashInputValue = " + flashInputValue);
//
//		String fdata[] = flashInputValue.split("&");
//
//		startDate=StringUtils.defaultIfEmpty(fdata[0],"");
//		endDate=StringUtils.defaultIfEmpty(fdata[1],"");
//		adPvclkDevice=StringUtils.defaultIfEmpty(fdata[2],"");
//		adType=StringUtils.defaultIfEmpty(fdata[3],"");
//		adSearchWay=StringUtils.defaultIfEmpty(fdata[4],"");
//		adShowWay=StringUtils.defaultIfEmpty(fdata[5],"");
//		String charPic=StringUtils.defaultIfEmpty(fdata[6],"");//lineChart,barChart
//		String charType=StringUtils.defaultIfEmpty(fdata[7],"");//pv,ctr,click,avgcost.cost 
//		searchId=StringUtils.defaultIfEmpty(fdata[8],"");//pv,ctr,click,avgcost.cost 
//		searchText=StringUtils.defaultIfEmpty(fdata[9],"");*/
//
//		//查詢日期寫進 cookie
//		this.setChooseDate(startDate, endDate);
//
//		log.info("charPic="+charPic);//flash 樣式
//		log.info("charType="+charType);//資料
//		log.info("searchId="+searchId);//資料
//		log.info("chstartDate="+startDate);
//		log.info("chendDate="+endDate);
//
//		if(searchText.equals("Null")){
//			searchText="";
//		}
//
//		String customerInfoId=super.getCustomer_info_id();
//		log.info(">>> customerInfoId = " + customerInfoId);
//
//		List<AdActionReportVO> resultData_ad_action = null;
//		List<AdGroupReportVO> resultData_ad_group = null;
//		List<AdKeywordReportVO> resultData_kw = null;
//		List<AdReportVO> resultData_ad = null;
//
//		log.info(">>> adType = " + adType);
//
//		if (adType.equals(EnumReport.ADTYPE_ACTIVITY.getTextValue())) {
//
//			resultData_ad_action = adActionReportService.loadReportDate(EnumReport.REPORT_HQLTYPE_EXCERPT_CHART.getTextValue(),
//					searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, adOperatingRule, startDate, endDate, -1, -1);
//		} else if (adType.equals(EnumReport.ADTYPE_GROUP.getTextValue()) ||
//				   adType.equals(EnumReport.DETAIL_GROUP.getTextValue()) ||
//				   adType.equals(EnumReport.DETAIL_ACTIVITY.getTextValue())) {
//			resultData_ad_group = adGroupReportService.loadReportDate(EnumReport.REPORT_HQLTYPE_EXCERPT_CHART.getTextValue(),
//					searchId, searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, adOperatingRule, startDate, endDate, -1, -1);
//
//		} else if (adType.equals(EnumReport.ADTYPE_KEYWORD.getTextValue())) {
//			resultData_kw = adKeywordReportService.loadReportDate(EnumReport.REPORT_HQLTYPE_EXCERPT_CHART.getTextValue(),
//					searchId, searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, startDate, endDate, -1, -1);
//
//		} else if (adType.equals(EnumReport.ADTYPE_AD.getTextValue())) {
//			resultData_ad = adReportService.loadReportDate(EnumReport.REPORT_HQLTYPE_ADVERTISE_CHART.getTextValue(),
//					searchId, "", searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, adOperatingRule, startDate, endDate, -1, -1);
//		}
//		
//		Map<Date,Float> flashDataMap=new HashMap<Date,Float>();
//		
//		//------關鍵字專用------
//		List<Map<Date,Float>> mapList = new ArrayList<Map<Date,Float>>();
//		Map<Date,Float> flashPhrDataMap=new HashMap<Date,Float>();
//		Map<Date,Float> flashPreDataMap=new HashMap<Date,Float>();
//		Map<Date,Float> flashTotalDataMap=new HashMap<Date,Float>();
//		//------------------
//		
//		double pv = 0;
//		double click = 0;
//		double cost = 0;
//		double invClick = 0;
//		double ctr = 0;
//		double costAvg = 0;
//		double kiloCost = 0;
//
//		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//
//		if (resultData_ad_action!=null && resultData_ad_action.size()>0) {
//
//			for (int i=0; i<resultData_ad_action.size(); i++) {
//
//				ctr = 0;
//				costAvg = 0;
//				
//				AdActionReportVO vo = resultData_ad_action.get(i);
//				Date reportDate = vo.getReportDate();
//				pv = vo.getAdPvSum().doubleValue();
//				click = vo.getAdClkSum().doubleValue();
//				cost = vo.getAdPriceSum().doubleValue();
//				cost = new BigDecimal(String.valueOf(cost)).setScale(3, BigDecimal.ROUND_FLOOR).doubleValue();
//				invClick = vo.getAdInvClkSum().doubleValue();
//
//				//互動率 = 互動次數 / 曝光數
//				if (pv>0 && click>0) {
//					ctr = (click / pv) * 100;
//				}
//
//				//單次互動費用 = 總費用 / 總互動次數
//				if (cost>0 && click>0) {
//					costAvg = cost / click;
//				}
//
//				//千次曝光費用 = 總費用*1000 / 曝光數
//				if(cost>0 && pv>0){
//					 kiloCost = (cost * 1000) / pv;
//				}
//
//				if (charType.equals(EnumReport.REPORT_CHART_TYPE_PV.getTextValue())) {
//					flashDataMap.put(reportDate, new Float((float) pv));
//				} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_CTR.getTextValue())) {
//					flashDataMap.put(reportDate, new Float((float) ctr));
//				} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_CLICK.getTextValue())) {
//					flashDataMap.put(reportDate, new Float((float) click));
//				} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_INVALID.getTextValue())) {
//					flashDataMap.put(reportDate, new Float((float) invClick));
//				} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_AVGCOST.getTextValue())) {
//					flashDataMap.put(reportDate, new Float((float) costAvg));
//				} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_KILOCOST.getTextValue())) {
//					flashDataMap.put(reportDate, new Float((float) kiloCost));
//	            } else if (charType.equals(EnumReport.REPORT_CHART_TYPE_COST.getTextValue())) {
//					flashDataMap.put(reportDate, new Float((float) cost));
//				}
//			}
//
//		} else if (resultData_ad_group!=null && resultData_ad_group.size()>0) {
//
//				for (int i=0; i<resultData_ad_group.size(); i++) {
//
//					AdGroupReportVO vo = resultData_ad_group.get(i);
//
//					ctr = 0;
//					costAvg = 0;
//					
//					Date reportDate = vo.getReportDate();
//					pv = vo.getAdPvSum().doubleValue();
//					click = vo.getAdClkSum().doubleValue();
//					cost = vo.getAdPriceSum().doubleValue();
//					cost = new BigDecimal(String.valueOf(cost)).setScale(3, BigDecimal.ROUND_FLOOR).doubleValue();
//					invClick = vo.getAdInvClkSum().doubleValue();
//
//					//互動率 = 互動次數 / 曝光數
//					if (pv>0 && click>0) {
//						ctr = (click / pv) * 100;
//					}
//
//					//單次互動費用 = 總費用 / 總互動次數
//					if (cost>0 && click>0) {
//						costAvg = cost / click;
//					}
//
//					//千次曝光費用 = 總費用*1000 / 曝光數
//					if(cost>0 && pv>0){
//						 kiloCost = (cost * 1000) / pv;
//					}
//
//					if (charType.equals(EnumReport.REPORT_CHART_TYPE_PV.getTextValue())) {
//						flashDataMap.put(reportDate, new Float((float) pv));
//					} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_CTR.getTextValue())) {
//						flashDataMap.put(reportDate, new Float((float) ctr));
//					} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_CLICK.getTextValue())) {
//						flashDataMap.put(reportDate, new Float((float) click));
//					} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_INVALID.getTextValue())) {
//						flashDataMap.put(reportDate, new Float((float) invClick));
//					} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_AVGCOST.getTextValue())) {
//						flashDataMap.put(reportDate, new Float((float) costAvg));
//					} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_KILOCOST.getTextValue())) {
//						flashDataMap.put(reportDate, new Float((float) kiloCost));
//		            } else if (charType.equals(EnumReport.REPORT_CHART_TYPE_COST.getTextValue())) {
//						flashDataMap.put(reportDate, new Float((float) cost));
//					}
//				}
//
//		} else if (resultData_kw!=null && resultData_kw.size()>0) {
//			
//			for (int i=0; i<resultData_kw.size(); i++) {
//				
//				ctr = 0;
//				costAvg = 0;
//				
//				//詞組比對
//				double phrPv = 0;
//				double phrClick = 0;
//				double phrCost = 0;
//				double phrInvClick = 0;
//				double phrCtr = 0;
//				double phrCostAvg = 0;
//				
//				//精準比對
//				double prePv = 0;
//				double preClick = 0;
//				double preCost = 0;
//				double preInvClick = 0;
//				double preCtr = 0;
//				double preCostAvg = 0;
//				
//				//總計
//				double totalPv = 0;
//				double totalClick = 0;
//				double totalCost = 0;
//				double totalInvClick = 0;
//				double totalCtr = 0;
//				double totalCostAvg = 0;
//
//				AdKeywordReportVO vo = resultData_kw.get(i);
//
//				Date reportDate = dateFormat.parse(vo.getReportDate());
//				
//				//廣泛比對
//				pv = Double.parseDouble(vo.getKwPvSum());
//				click = Double.parseDouble(vo.getKwClkSum());
//				cost = Double.parseDouble(vo.getKwPriceSum());
//				invClick = Double.parseDouble(vo.getKwInvClkSum());
//
//				//點選率 = 點選次數 / 曝光數
//				if (pv>0 && click>0) {
//					ctr = (click / pv) * 100;
//				}
//
//				//平均點選費用 = 總費用 / 總點選次數
//				if (cost>0 && click>0) {
//					costAvg = cost / click;
//				}
//
//				//詞組比對
//				phrPv = Double.parseDouble(vo.getKwPhrPvSum());
//				phrClick = Double.parseDouble(vo.getKwPhrClkSum());
//				phrCost = Double.parseDouble(vo.getKwPhrPriceSum());
//				phrInvClick = Double.parseDouble(vo.getKwPhrInvClkSum());
//				
//				//點選率 = 點選次數 / 曝光數
//				if (phrPv>0 && phrClick>0) {
//					phrCtr = (phrClick / phrPv) * 100;
//				}
//
//				//平均點選費用 = 總費用 / 總點選次數
//				if (phrCost>0 && phrClick>0) {
//					phrCostAvg = phrCost / phrClick;
//				}
//				
//				//精準比對
//				prePv = Double.parseDouble(vo.getKwPrePvSum());
//				preClick = Double.parseDouble(vo.getKwPreClkSum());
//				preCost = Double.parseDouble(vo.getKwPrePriceSum());
//				preInvClick = Double.parseDouble(vo.getKwPreInvClkSum());
//				
//				//點選率 = 點選次數 / 曝光數
//				if (prePv>0 && preClick>0) {
//					preCtr = (preClick / prePv) * 100;
//				}
//
//				//平均點選費用 = 總費用 / 總點選次數
//				if (preCost>0 && preClick>0) {
//					preCostAvg = preCost / preClick;
//				}
//				
//				//總計
//				totalPv = pv + phrPv + prePv;
//				totalClick = click + phrClick + preClick;
//				totalCost = cost + phrCost + preCost;
//				totalInvClick = invClick + phrInvClick + preInvClick;
//				
//				//點選率 = 點選次數 / 曝光數
//				if (totalPv>0 && totalClick>0) {
//					totalCtr = (totalClick / totalPv) * 100;
//				}
//
//				//平均點選費用 = 總費用 / 總點選次數
//				if (totalCost>0 && totalClick>0) {
//					totalCostAvg = totalCost / totalClick;
//				}
//				
//				if (charType.equals(EnumReport.REPORT_CHART_TYPE_PV.getTextValue())) {
//					flashDataMap.put(reportDate, new Float((float) pv));
//					flashPhrDataMap.put(reportDate, new Float((float) phrPv));
//					flashPreDataMap.put(reportDate, new Float((float) prePv));
//					flashTotalDataMap.put(reportDate, new Float((float) totalPv));
//				} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_CTR.getTextValue())) {
//					flashDataMap.put(reportDate, new Float((float) ctr));
//					flashPhrDataMap.put(reportDate, new Float((float) phrCtr));
//					flashPreDataMap.put(reportDate, new Float((float) preCtr));
//					flashTotalDataMap.put(reportDate, new Float((float) totalCtr));
//				} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_CLICK.getTextValue())) {
//					flashDataMap.put(reportDate, new Float((float) click));
//					flashPhrDataMap.put(reportDate, new Float((float) phrClick));
//					flashPreDataMap.put(reportDate, new Float((float) preClick));
//					flashTotalDataMap.put(reportDate, new Float((float) totalClick));
//				} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_INVALID.getTextValue())) {
//					flashDataMap.put(reportDate, new Float((float) invClick));
//					flashPhrDataMap.put(reportDate, new Float((float) phrInvClick));
//					flashPreDataMap.put(reportDate, new Float((float) preInvClick));
//					flashTotalDataMap.put(reportDate, new Float((float) totalInvClick));
//				} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_AVGCOST.getTextValue())) {
//					flashDataMap.put(reportDate, new Float((float) costAvg));
//					flashPhrDataMap.put(reportDate, new Float((float) phrCostAvg));
//					flashPreDataMap.put(reportDate, new Float((float) preCostAvg));
//					flashTotalDataMap.put(reportDate, new Float((float) totalCostAvg));
//				} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_COST.getTextValue())) {
//					flashDataMap.put(reportDate, new Float((float) cost));
//					flashPhrDataMap.put(reportDate, new Float((float) phrCost));
//					flashPreDataMap.put(reportDate, new Float((float) preCost));
//					flashTotalDataMap.put(reportDate, new Float((float) totalCost));
//				}
//			}
//		} else if (resultData_ad!=null && resultData_ad.size()>0) {
//
//			for (int i=0; i<resultData_ad.size(); i++) {
//
//				ctr = 0;
//				costAvg = 0;
//				
//				AdReportVO vo = resultData_ad.get(i);
//
//				Date reportDate = dateFormat.parse(vo.getReportDate());
//				pv = Double.parseDouble(vo.getAdPvSum());
//				click = Double.parseDouble(vo.getAdClkSum());
//				cost = Double.parseDouble(vo.getAdPriceSum());
//				cost = new BigDecimal(String.valueOf(cost)).setScale(3, BigDecimal.ROUND_FLOOR).doubleValue();
//				invClick = Double.parseDouble(vo.getAdInvClkSum());
//
//				//互動率 = 互動次數 / 曝光數
//				if (pv>0 && click>0) {
//					ctr = (click / pv) * 100;
//				}
//
//				//單次互動費用 = 總費用 / 總互動次數
//				if (cost>0 && click>0) {
//					costAvg = cost / click;
//				}
//
//				//千次曝光費用 = 總費用*1000 / 曝光數
//				if(cost>0 && pv>0){
//					 kiloCost = (cost * 1000) / pv;
//				}
//				
//				if (charType.equals(EnumReport.REPORT_CHART_TYPE_PV.getTextValue())) {
//					flashDataMap.put(reportDate, new Float((float) pv));
//				} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_CTR.getTextValue())) {
//					flashDataMap.put(reportDate, new Float((float) ctr));
//				} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_CLICK.getTextValue())) {
//					flashDataMap.put(reportDate, new Float((float) click));
//				} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_INVALID.getTextValue())) {
//					flashDataMap.put(reportDate, new Float((float) invClick));
//				} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_AVGCOST.getTextValue())) {
//					flashDataMap.put(reportDate, new Float((float) costAvg));
//				} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_KILOCOST.getTextValue())) {
//					flashDataMap.put(reportDate, new Float((float) kiloCost));
//	            } else if (charType.equals(EnumReport.REPORT_CHART_TYPE_COST.getTextValue())) {
//					flashDataMap.put(reportDate, new Float((float) cost));
//				}
//			}
//		}
//
//		//flashData = openFlashUtil.getChartDataForMap(charPic, charType, startDate, endDate, flashDataMap);
//		if (adType.equals(EnumReport.ADTYPE_KEYWORD.getTextValue())) {
//			mapList.add(flashDataMap);
//			mapList.add(flashPhrDataMap);
//			mapList.add(flashPreDataMap);
//			mapList.add(flashTotalDataMap);
//			flashData = openFlashUtil.getKeywordChartDataForArray(charType, startDate, endDate, mapList);
//		} else {
//			flashData = openFlashUtil.getChartDataForArray(charType, startDate, endDate, flashDataMap);
//		}
//		return SUCCESS;
//	}
//
//	public ReportExcerptAction() {
//
//		reportTitle="總廣告成效";
//
//		downloadFlag="no";
//
//		tableHeadNameMap=new HashMap<String,String>();
//		tableHeadNameMap.put("曝光數", EnumReport.REPORT_CHART_TYPE_PV.getTextValue());
//		tableHeadNameMap.put("互動數", EnumReport.REPORT_CHART_TYPE_CLICK.getTextValue());
//		tableHeadNameMap.put("互動率", EnumReport.REPORT_CHART_TYPE_CTR.getTextValue());
//		tableHeadNameMap.put("無效點選次數", EnumReport.REPORT_CHART_TYPE_INVALID.getTextValue());
//		tableHeadNameMap.put("單次互動費用", EnumReport.REPORT_CHART_TYPE_AVGCOST.getTextValue());
//		tableHeadNameMap.put("千次曝光費用", EnumReport.REPORT_CHART_TYPE_KILOCOST.getTextValue());
//		tableHeadNameMap.put("費用", EnumReport.REPORT_CHART_TYPE_COST.getTextValue());
//		tableHeadNameMap.put("轉換次數", EnumReport.REPORT_CHART_CONVERT.getTextValue());
//		tableHeadNameMap.put("總轉換價值", EnumReport.REPORT_CHART_CONVERT_PRICE.getTextValue());
//		tableHeadNameMap.put("轉換率", EnumReport.REPORT_CHART_CONVERT_CTR.getTextValue());
//		tableHeadNameMap.put("平均轉換成本", EnumReport.REPORT_CHART_CONVERT_COST.getTextValue());
//		tableHeadNameMap.put("廣告投資報酬率", EnumReport.REPORT_CHART_CONVERT_INVESTMENT.getTextValue());
//		optionSelect="";
//		optionNotSelect="";
//
//		optionSelect="曝光數,互動數,互動率,無效點選次數,單次互動費用,千次曝光費用,費用";
//
//		tableHeadShowList=new LinkedList<String>();
//
//		tableHeadNotShowList=new LinkedList<String>();
//		
//		AdKeywordReportVOList = new ArrayList<AdKeywordReportVO>();
//		
//		AdKeywordReportDataTotal = new AdKeywordReportVO();
//	}
//
//	public String execute() throws Exception {
//
//		StringBuffer stepStrBuffer=new StringBuffer();
//
//		String customerInfoId=super.getCustomer_info_id();
//		log.info(">>> customerInfoId = " + customerInfoId);
//		PfpCode pfpCode = pfpCodeService.getPfpCode(super.getCustomer_info_id());
//		if(pfpCode != null){
//			hasPfpCodeflag = true;
//		}
//		if(hasPfpCodeflag){
//			optionSelect="曝光數,互動數,互動率,無效點選次數,單次互動費用,千次曝光費用,費用,轉換次數,總轉換價值,轉換率,平均轉換成本,廣告投資報酬率";	
//		}else{
//			optionSelect="曝光數,互動數,互動率,無效點選次數,單次互動費用,千次曝光費用,費用";
//		}
//		
//		tableHeadList = new LinkedList<String>();//table head
//		tableDataList = new LinkedList<LinkedList<String>>();//table data
//
//		//自訂欄位選擇
//		if(StringUtils.isNotEmpty(optionSelect)){
//
//			String data[]=optionSelect.split(",");
//			for(String s:data){
//				tableHeadShowList.addLast(s);
//			}
//		}
//
//		//自訂欄位移除
//		if(StringUtils.isNotEmpty(optionNotSelect)){
//			String data[]=optionNotSelect.split(",");
//			for(String s:data){
//				tableHeadNotShowList.addLast(s);
//			}
//		}
//
//		log.info(">>> startDate = " + startDate);
//		log.info(">>> endDate = " + endDate);
//
//		String startDate_cookie = this.getChoose_start_date();
//		String endDate_cookie = this.getChoose_end_date();
//
//		log.info(">>> startDate_cookie = " + startDate_cookie);
//		log.info(">>> endDate_cookie = " + endDate_cookie);
//
//		if (StringUtils.isEmpty(startDate)) {
//			if (StringUtils.isNotEmpty(startDate_cookie)) {
//				startDate = startDate_cookie;
//			} else {
//				startDate = DateValueUtil.getInstance().dateToString(DateValueUtil.getInstance().getDateForStartDateAddDay(DateValueUtil.getInstance().getDateValue(DateValueUtil.TODAY, DateValueUtil.DBPATH), -30));
//			}
//		}
//
//		if (StringUtils.isEmpty(endDate)) {
//			if (StringUtils.isNotEmpty(endDate_cookie)) {
//				endDate = endDate_cookie;
//			} else {
//				endDate=DateValueUtil.getInstance().getDateValue(DateValueUtil.TODAY, DateValueUtil.DBPATH);
//			}
//		}
//
//		log.info(">>>>>> startDate = " + startDate);
//		log.info(">>>>>> endDate = " + endDate);
//
//		log.info(">>>>>> adPvclkDevice = " + adPvclkDevice);
//		if(StringUtils.isBlank(adPvclkDevice)){
//			adPvclkDevice="";
//		}
//
//		if(StringUtils.isEmpty(adType)){
//			adType=EnumReport.ADTYPE_ACTIVITY.getTextValue();
//		}
//
//		if(StringUtils.isEmpty(adSearchWay)){
//			adSearchWay=EnumReport.ADSEARCH_INCLUDE.getTextValue();
//		}
//
//		if(StringUtils.isEmpty(adShowWay)){
//			adShowWay=Integer.toString(EnumAdType.AD_ALL.getType());
//		}
//
//		dateSelectMap=DateValueUtil.getInstance().getDateRangeMap();
//
//		//輸入欄位順序
//		if(!tableHeadShowList.isEmpty()){
//			for(String s:tableHeadShowList){
//
//				tableHeadList.addLast(s);
//
//			}
//		}
//
//		if(page==0){
//			page=1;
//		}
//		log.info("page="+page);
//
//		if(pageSize==0){
//			pageSize=20;
//		}
//		log.info("pageSize="+pageSize);
//
//		//downloadReport
//
//		log.info("downloadFlag="+downloadFlag);
//
//		if(downloadFlag.trim().equals("yes")){
//			page=-1;
//		}
//
//		int totalPageSize = 0;
//
//		log.info(">>> adPvclkDevice = " + adPvclkDevice);
//		log.info(">>> adType = " + adType);
//		log.info(">>> searchId = " + searchId);
//		log.info(">>> adSearchWay = " + adSearchWay);
//		log.info(">>> adShowWay = " + adShowWay);
//		log.info(">>> startDate = " + startDate);
//		log.info(">>> endDate = " + endDate);
//
//		tableHeadList.addFirst("裝置");
//		if(adType.equals(EnumReport.ADTYPE_KEYWORD.getTextValue())){
//			tableHeadList.addFirst("狀態");
//		} else {
//			if(adType.equals(EnumReport.ADTYPE_ACTIVITY.getTextValue())){
//				tableHeadList.addFirst("走期");
//			} else {
//				tableHeadList.addFirst("計價方式");
//			}
//			tableHeadList.addFirst("狀態");
//			tableHeadList.addFirst("廣告樣式");
//		}
//		tableHeadList.addFirst("播放類型");
//
////廣告層********************************************************************************************************
//		if(adType.equals(EnumReport.ADTYPE_ACTIVITY.getTextValue())){
//			
//			//麵包屑
//			stepStrBuffer.append("廣告");
//
//			//第一欄位
//			tableHeadList.addFirst("廣告");
//
//			//搜尋總計結果
//			List<AdActionReportVO> resultSumData = adActionReportService.loadReportDate(EnumReport.REPORT_HQLTYPE_EXCERPT_COUNT.getTextValue(),
//					searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, adOperatingRule, startDate, endDate, -1, -1);
//
//			totalPageSize = resultSumData.size();
//
//			//搜尋明細結果
//			List<AdActionReportVO> resultData = adActionReportService.loadReportDate(EnumReport.REPORT_HQLTYPE_EXCERPT.getTextValue(),
//					searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, adOperatingRule, startDate, endDate, page, pageSize);
//
//			if (resultSumData!=null && resultSumData.size()>0) {
//				resultSumDataTrans_ad_action(resultSumData);
//				resultDataTrans_ad_action(resultData);
//			}
//
//			showSearchBarFlag = "y";
//
////分類層********************************************************************************************************
//		} else if (adType.equals(EnumReport.ADTYPE_GROUP.getTextValue())){
//
//			if(StringUtils.isEmpty(searchId)) { //當層搜尋
//
//				//麵包屑
//				stepStrBuffer.append("<a href=\"javascript:adIdSearch('" + EnumReport.ADTYPE_ACTIVITY.getTextValue() + "','')\">廣告</a>");
//				stepStrBuffer.append("&nbsp; >> &nbsp;");
//				stepStrBuffer.append("分類");
//
//				showSearchBarFlag = "y";
//
//			}else{
//
//				PfpAdAction adAction = adActionService.getPfpAdActionBySeq(searchId);
//
//				if (downloadFlag.equals("yes")) { //下載
//
//					//麵包屑
//					stepStrBuffer.append("廣告：" + adAction.getAdActionName());
//
//				} else { //準備下一層，關鍵字層的資料
//
//					//麵包屑
//					stepStrBuffer.append("<a href=\"javascript:adIdSearch('" + EnumReport.ADTYPE_ACTIVITY.getTextValue() + "','')\">廣告</a>");
//					stepStrBuffer.append("&nbsp; >> &nbsp;");
//					stepStrBuffer.append("<a href=\"javascript:adIdSearch('" + EnumReport.ADTYPE_GROUP.getTextValue() + "','" + adAction.getAdActionSeq() + "')\">廣告：" + adAction.getAdActionName() + "</a>");
//					stepStrBuffer.append("&nbsp; >> &nbsp;");
//					stepStrBuffer.append("分類");
//
//				}
//
//				showSearchBarFlag = "n";
//			}
//
//			//第一欄位
//			tableHeadList.addFirst("分類");
//
//			//搜尋結果
//			List<AdGroupReportVO> resultSumData = adGroupReportService.loadReportDate(EnumReport.REPORT_HQLTYPE_EXCERPT_COUNT.getTextValue(),
//					searchId, searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, adOperatingRule, startDate, endDate, -1, -1);
//
//			totalPageSize = resultSumData.size();
//
//			List<AdGroupReportVO> resultData = adGroupReportService.loadReportDate(EnumReport.REPORT_HQLTYPE_EXCERPT.getTextValue(),
//					searchId, searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, adOperatingRule, startDate, endDate, page, pageSize);
//
//			if (resultSumData!=null && resultSumData.size()>0) {
//				resultSumDataTrans_ad_group(resultSumData);
//				resultDataTrans_ad_group(resultData);
//			}
//
////關鍵字層*******************************************************************************************************
//		}else if(adType.equals(EnumReport.ADTYPE_KEYWORD.getTextValue())){ //keyword
//
//			if(StringUtils.isEmpty(searchId)) { //當層搜尋
//
//				//麵包屑
//				stepStrBuffer.append("<a href=\"javascript:adIdSearch('" + EnumReport.ADTYPE_ACTIVITY.getTextValue() + "','')\">廣告</a>");
//				stepStrBuffer.append("&nbsp; >> &nbsp;");
//				stepStrBuffer.append("<a href=\"javascript:adIdSearch('" + EnumReport.ADTYPE_GROUP.getTextValue() + "','')\">分類</a>");
//				stepStrBuffer.append("&nbsp; >> &nbsp;");
//				stepStrBuffer.append("關鍵字");
//
//				showSearchBarFlag = "y";
//
//			} else {
//
//				PfpAdGroup adGroup = adGroupService.getPfpAdGroupBySeq(searchId);
//
//				if (downloadFlag.equals("yes")) { //下載
//
//					//麵包屑
//					stepStrBuffer.append("廣告：" + adGroup.getPfpAdAction().getAdActionName() + " >> 分類：" + adGroup.getAdGroupName());
//
//				} else {
//
//					//麵包屑
//					stepStrBuffer.append("<a href=\"javascript:adIdSearch('" + EnumReport.ADTYPE_ACTIVITY.getTextValue() + "','')\">廣告</a>");
//					stepStrBuffer.append("&nbsp; >> &nbsp;");
//					stepStrBuffer.append("<a href=\"javascript:adIdSearch('" + EnumReport.ADTYPE_GROUP.getTextValue() + "','" + adGroup.getPfpAdAction().getAdActionSeq() + "')\">廣告：" + adGroup.getPfpAdAction().getAdActionName() + "</a>");
//					stepStrBuffer.append("&nbsp; >> &nbsp;");
//					stepStrBuffer.append("<a href=\"javascript:adIdSearch('" + EnumReport.ADTYPE_GROUP.getTextValue() + "','')\">分類</a>");
//					stepStrBuffer.append("&nbsp; >> &nbsp;");
//					stepStrBuffer.append("<a href=\"javascript:adIdSearch('" + EnumReport.ADTYPE_KEYWORD.getTextValue() + "','" + adGroup.getAdGroupSeq() + "')\">分類：" + adGroup.getAdGroupName() + "</a>");
//					stepStrBuffer.append("&nbsp; >> &nbsp;");
//					stepStrBuffer.append("關鍵字 │ <a href=\"javascript:adIdSearch('" + EnumReport.ADTYPE_AD.getTextValue() + "','" + adGroup.getAdGroupSeq() + "')\">廣告明細</a>");
//
//				}
//
//				showSearchBarFlag = "n";
//			}
//
//			//第一欄位
//			tableHeadList.addFirst("關鍵字");
//
//			//搜尋結果
//			List<AdKeywordReportVO> resultSumData = adKeywordReportService.loadReportDate(EnumReport.REPORT_HQLTYPE_EXCERPT_COUNT.getTextValue(),
//					searchId, searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, startDate, endDate, -1, -1);
//
//			totalPageSize = resultSumData.size();
//
//			List<AdKeywordReportVO> resultData = adKeywordReportService.loadReportDate(EnumReport.REPORT_HQLTYPE_EXCERPT.getTextValue(),
//					searchId, searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, startDate, endDate, page, pageSize);
//
//			if (resultSumData!=null && resultSumData.size()>0) {
//				resultSumDataTrans_kw(resultSumData);
//				resultDataTrans_kw(resultData);
//			}
//
////廣告明細層****************************************************************************************************
//		}else if(adType.equals(EnumReport.ADTYPE_AD.getTextValue())){ //ad
//
//			if(StringUtils.isEmpty(searchId)) { //當層搜尋
//
//				//麵包屑
//				stepStrBuffer.append("<a href=\"javascript:adIdSearch('" + EnumReport.ADTYPE_ACTIVITY.getTextValue() + "','')\">廣告</a>");
//				stepStrBuffer.append("&nbsp; >> &nbsp;");
//				stepStrBuffer.append("<a href=\"javascript:adIdSearch('" + EnumReport.ADTYPE_GROUP.getTextValue() + "','')\">分類</a>");
//				stepStrBuffer.append("&nbsp; >> &nbsp;");
//				stepStrBuffer.append("廣告明細");
//
//				showSearchBarFlag = "y";
//
//			} else {
//
//				PfpAdGroup adGroup = adGroupService.getPfpAdGroupBySeq(searchId);
//
//				if (downloadFlag.equals("yes")) { //下載
//
//					//麵包屑
//					stepStrBuffer.append("廣告：" + adGroup.getPfpAdAction().getAdActionName() + " >> 分類：" + adGroup.getAdGroupName());
//
//				} else {
//
//					//麵包屑
//					stepStrBuffer.append("<a href=\"javascript:adIdSearch('" + EnumReport.ADTYPE_ACTIVITY.getTextValue() + "','')\">廣告</a>");
//					stepStrBuffer.append("&nbsp; >> &nbsp;");
//					stepStrBuffer.append("<a href=\"javascript:adIdSearch('" + EnumReport.ADTYPE_GROUP.getTextValue() + "','" + adGroup.getPfpAdAction().getAdActionSeq() + "')\">廣告：" + adGroup.getPfpAdAction().getAdActionName() + "</a>");
//					stepStrBuffer.append("&nbsp; >> &nbsp;");
//					stepStrBuffer.append("<a href=\"javascript:adIdSearch('" + EnumReport.ADTYPE_GROUP.getTextValue() + "','')\">分類</a>");
//					stepStrBuffer.append("&nbsp; >> &nbsp;");
//					stepStrBuffer.append("<a href=\"javascript:adIdSearch('" + EnumReport.ADTYPE_AD.getTextValue() + "','" + adGroup.getAdGroupSeq() + "')\">分類：" + adGroup.getAdGroupName() + "</a>");
//					stepStrBuffer.append("&nbsp; >> &nbsp;");
//					stepStrBuffer.append("<a href=\"javascript:adIdSearch('" + EnumReport.ADTYPE_KEYWORD.getTextValue() + "','" + adGroup.getAdGroupSeq() + "')\">關鍵字</a> │ 廣告明細");
//
//				}
//
//				showSearchBarFlag = "n";
//			}
//
//			//第一欄位
//			if(downloadFlag.equals("yes")){
//				tableHeadList.addFirst("實際連結");
//				tableHeadList.addFirst("顯示連結");
//				tableHeadList.addFirst("廣告內容");
//				tableHeadList.addFirst("廣告名稱");
//			} else {
//				tableHeadList.addFirst("廣告明細");
//			}
//
//			//搜尋結果
//			List<AdReportVO> resultSumData = adReportService.loadReportDate(EnumReport.REPORT_HQLTYPE_ADVERTISE_COUNT.getTextValue(), searchId, "", searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, adOperatingRule, startDate, endDate, -1, -1);
//
//			totalPageSize = resultSumData.size();
//
//			List<AdReportVO> resultData = adReportService.loadReportDate(EnumReport.REPORT_HQLTYPE_ADVERTISE.getTextValue(), searchId, "", searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, adOperatingRule, startDate, endDate, page, pageSize);
//
//			if (resultSumData!=null && resultSumData.size()>0) {
//				resultSumDataTrans_ad(resultSumData);
//				resultDataTrans_ad(resultData);
//			}
//		}
//
//		stepStr = stepStrBuffer.toString();
//
//		log.info("totalPageSize="+totalPageSize);
//		log.info("pageSize="+pageSize);
//
//		totalPage =(totalPageSize/pageSize);
//
//		if((totalPageSize%pageSize)>0){
//			totalPage+=1;
//		}
//
//		if(adType.equals(EnumReport.ADTYPE_KEYWORD.getTextValue())){
//			if(AdKeywordReportVOList.isEmpty()){
//				stepStr="";
//			}
//		} else {
//			if(tableDataList.isEmpty()){
//				stepStr="";
//			}	
//		}
//
//		if(downloadFlag.trim().equals("yes")){
//			log.info("makeDownloadReportData");
//			makeDownloadReportData();
//		}
//
//		log.info("time="+new Date().toString());
//
//		if(adType.equals(EnumReport.ADTYPE_KEYWORD.getTextValue())){
//			return "adKeywordReport";
//		} else {
//			return SUCCESS;
//		}
//	}
//
//	private void makeDownloadReportData() throws Exception {
//
//		SimpleDateFormat dformat = new SimpleDateFormat("yyyyMMddhhmmss");
//
//		String filename="總廣告成效報表_" + dformat.format(new Date()) + FILE_TYPE;
//
//		StringBuffer content=new StringBuffer();
//
//		PfpCustomerInfo customerInfo = customerInfoService.findCustomerInfo(super.getCustomer_info_id());
//
//		content.append("帳戶," + customerInfo.getCustomerInfoTitle());
//		content.append("\n\n");
//		content.append("報表名稱,PChome 總廣告成效");
//		content.append("\n\n");
//		content.append("播放類型," + getAdShowWayMap().get(adShowWay));
//		content.append("\n");
//		content.append("廣告樣式," + getAdStyleTypeMap().get(adOperatingRule));
//		content.append("\n");
//		content.append("裝置," + getAdPvclkDeviceMap().get(adPvclkDevice));
//		content.append("\n");
//		content.append("搜尋條件," + getAdSearchWayMap().get(adSearchWay));
//		content.append("\n");
//		content.append("搜尋內容," + searchText);
//		content.append("\n");
//		content.append("日期範圍," + startDate + " 到 " + endDate);
//		content.append("\n\n");
//
//		if(adType.equals(EnumReport.ADTYPE_KEYWORD.getTextValue())){
//			String comma = ",";
//			//欄位名稱
//			content.append("關鍵字" + comma);
//			content.append("類型" + comma);
//			content.append("狀態" + comma);
//			content.append("裝置" + comma);
//			content.append("廣泛比對方式" + comma);
//			content.append("詞組比對方式" + comma);
//			content.append("精準比對方式" + comma);
//			content.append("廣泛比對曝光數" + comma);
//			content.append("詞組比對曝光數" + comma);
//			content.append("精準比對曝光數" + comma);
//			content.append("總曝光數" + comma);
//			content.append("廣泛比對點選次數" + comma);
//			content.append("詞組比對點選次數" + comma);
//			content.append("精準比對點選次數" + comma);
//			content.append("總點選次數" + comma);
//			content.append("廣泛比對點選率" + comma);
//			content.append("詞組比對點選率" + comma);
//			content.append("精準比對點選率" + comma);
//			content.append("總點選率" + comma);
//			content.append("廣泛比對無效點選次數" + comma);
//			content.append("詞組比對無效點選次數" + comma);
//			content.append("精準比對無效點選次數" + comma);
//			content.append("總無效點選次數" + comma);
//			content.append("廣泛比對平均點選費用" + comma);
//			content.append("詞組比對平均點選費用" + comma);
//			content.append("精準比對平均點選費用" + comma);
//			content.append("總平均點選費用" + comma);
//			content.append("廣泛比對費用" + comma);
//			content.append("詞組比對費用" + comma);
//			content.append("精準比對費用" + comma);
//			content.append("總費用");
//			content.append("\n");
//			
//			//data
//			for(AdKeywordReportVO data:AdKeywordReportVOList){
//				content.append("\"" + data.getAdKeyword() + "\"" + comma);
//				content.append("\"" + data.getKwAdType() + "\"" + comma);
//				content.append("\"" + data.getAdStatus() + "\"" + comma);
//				content.append("\"" + data.getKwDevice() + "\"" + comma);
//				content.append("\"" + data.getKwOpen() + "\"" + comma);
//				content.append("\"" + data.getKwPhrOpen() + "\"" + comma);
//				content.append("\"" + data.getKwPreOpen() + "\"" + comma);
//				content.append("\"" + data.getKwPvSum() + "\"" + comma);
//				content.append("\"" + data.getKwPhrPvSum() + "\"" + comma);
//				content.append("\"" + data.getKwPrePvSum() + "\"" + comma);
//				content.append("\"" + data.getKwPvTotal() + "\"" + comma);
//				content.append("\"" + data.getKwClkSum() + "\"" + comma);
//				content.append("\"" + data.getKwPhrClkSum() + "\"" + comma);
//				content.append("\"" + data.getKwPreClkSum() + "\"" + comma);
//				content.append("\"" + data.getKwClkTotal() + "\"" + comma);
//				content.append("\"" + data.getKwCtrSum() + "%\"" + comma);
//				content.append("\"" + data.getKwPhrCtrSum() + "%\"" + comma);
//				content.append("\"" + data.getKwPreCtrSum() + "%\"" + comma);
//				content.append("\"" + data.getKwCtrTotal() + "%\"" + comma);
//				content.append("\"" + data.getKwInvClkSum() + "\"" + comma);
//				content.append("\"" + data.getKwPhrInvClkSum() + "\"" + comma);
//				content.append("\"" + data.getKwPreInvClkSum() + "\"" + comma);
//				content.append("\"" + data.getKwInvClkTotal() + "\"" + comma);
//				content.append("\"NT$ " + data.getKwPriceAvgSum() + "\"" + comma);
//				content.append("\"NT$ " + data.getKwPriceAvgSum() + "\"" + comma);
//				content.append("\"NT$ " + data.getKwPrePriceAvgSum() + "\"" + comma);
//				content.append("\"NT$ " + data.getKwPriceAvgTotal() + "\"" + comma);
//				content.append("\"NT$ " + data.getKwPriceSum() + "\"" + comma);
//				content.append("\"NT$ " + data.getKwPriceSum() + "\"" + comma);
//				content.append("\"NT$ " + data.getKwPrePriceSum() + "\"" + comma);
//				content.append("\"NT$ " + data.getKwPriceTotal() + "\"");
//				content.append("\n");
//			}
//			content.append("\n");
//			
//			//合計
//			if (AdKeywordReportDataTotal!=null) {
//				content.append("\"" + AdKeywordReportDataTotal.getDataTotal() + "\"" + comma);
//				content.append(" " + comma);
//				content.append(" " + comma);
//				content.append(" " + comma);
//				content.append(" " + comma);
//				content.append(" " + comma);
//				content.append(" " + comma);
//				content.append("\"" + AdKeywordReportDataTotal.getKwPvSum() + "\"" + comma);
//				content.append("\"" + AdKeywordReportDataTotal.getKwPhrPvSum() + "\"" + comma);
//				content.append("\"" + AdKeywordReportDataTotal.getKwPrePvSum() + "\"" + comma);
//				content.append("\"" + AdKeywordReportDataTotal.getKwPvTotal() + "\"" + comma);
//				content.append("\"" + AdKeywordReportDataTotal.getKwClkSum() + "\"" + comma);
//				content.append("\"" + AdKeywordReportDataTotal.getKwPhrClkSum() + "\"" + comma);
//				content.append("\"" + AdKeywordReportDataTotal.getKwPreClkSum() + "\"" + comma);
//				content.append("\"" + AdKeywordReportDataTotal.getKwClkTotal() + "\"" + comma);
//				content.append("\"" + AdKeywordReportDataTotal.getKwCtrSum() + "%\"" + comma);
//				content.append("\"" + AdKeywordReportDataTotal.getKwPhrCtrSum() + "%\"" + comma);
//				content.append("\"" + AdKeywordReportDataTotal.getKwPreCtrSum() + "%\"" + comma);
//				content.append("\"" + AdKeywordReportDataTotal.getKwCtrTotal() + "%\"" + comma);
//				content.append("\"" + AdKeywordReportDataTotal.getKwInvClkSum() + "\"" + comma);
//				content.append("\"" + AdKeywordReportDataTotal.getKwPhrInvClkSum() + "\"" + comma);
//				content.append("\"" + AdKeywordReportDataTotal.getKwPreInvClkSum() + "\"" + comma);
//				content.append("\"" + AdKeywordReportDataTotal.getKwInvClkTotal() + "\"" + comma);
//				content.append("\"NT$ " + AdKeywordReportDataTotal.getKwPriceAvgSum() + "\"" + comma);
//				content.append("\"NT$ " + AdKeywordReportDataTotal.getKwPriceAvgSum() + "\"" + comma);
//				content.append("\"NT$ " + AdKeywordReportDataTotal.getKwPrePriceAvgSum() + "\"" + comma);
//				content.append("\"NT$ " + AdKeywordReportDataTotal.getKwPriceAvgTotal() + "\"" + comma);
//				content.append("\"NT$ " + AdKeywordReportDataTotal.getKwPriceSum() + "\"" + comma);
//				content.append("\"NT$ " + AdKeywordReportDataTotal.getKwPriceSum() + "\"" + comma);
//				content.append("\"NT$ " + AdKeywordReportDataTotal.getKwPrePriceSum() + "\"" + comma);
//				content.append("\"NT$ " + AdKeywordReportDataTotal.getKwPriceTotal() + "\"");
//				content.append("\n");
//			}
//		} else {
//			for(String s:tableHeadList){
//				content.append("\"" + s + "\"");
//				content.append(",");
//			}
//			content.append("\n");
//			
//			if(adType.equals(EnumReport.ADTYPE_AD.getTextValue())){
//				for(LinkedList<String> sl:tableDataList){
//					int dataNumber = 1;
//					for(String s:sl){
//						if(s == null){
//							s = " ";
//						}
//						if(dataNumber == 14 || dataNumber == 15 || dataNumber == 16 || dataNumber == 18 || dataNumber == 20){
//							content.append(StringEscapeUtils.escapeCsv("=\"NT$"+s+"\""));
//						} else if(dataNumber == 12 || dataNumber == 19 || dataNumber == 21){
//							content.append("\"" + s + "%\"");
//						} else {
//							content.append("\"" + s + "\"");	
//						}
//						content.append(",");
//						dataNumber++;
//					}
//					content.append("\n");
//				}
//				content.append("\n");
//				
//				if (tableDataTotalList!=null) {
//					int dataTotalNumber = 1;
//					for(String s:tableDataTotalList){
//						if(dataTotalNumber == 14 || dataTotalNumber == 15 || dataTotalNumber == 16 || dataTotalNumber == 18 || dataTotalNumber == 20){
//							content.append(StringEscapeUtils.escapeCsv("=\"NT$"+s+"\""));
//						} else if(dataTotalNumber == 12 || dataTotalNumber == 19 || dataTotalNumber == 21){
//							content.append("\"" + s + "%\"");
//						} else {
//							content.append("\"" + s + "\"");
//						}
//						content.append(",");
//						dataTotalNumber++;
//					}
//					content.append("\n");
//				}
//			} else {
//				for(LinkedList<String> sl:tableDataList){
//					int dataNumber = 1;
//					for(String s:sl){
//						if(dataNumber == 11 || dataNumber == 12 || dataNumber == 13 || dataNumber == 15 || dataNumber == 17){
//							content.append(StringEscapeUtils.escapeCsv("=\"NT$"+s+"\""));
//						} else if(dataNumber == 9 || dataNumber == 16 || dataNumber == 18){
//							content.append("\"" + s + "%\"");
//						} else {
//							content.append("\"" + s + "\"");	
//						}
//						content.append(",");
//						dataNumber++;
//					}
//					content.append("\n");
//				}
//				content.append("\n");
//				
//				if (tableDataTotalList!=null) {
//					int dataTotalNumber = 1;
//					for(String s:tableDataTotalList){
//						if(dataTotalNumber == 11 || dataTotalNumber == 12 || dataTotalNumber == 13 || dataTotalNumber == 15 || dataTotalNumber == 17){
//							content.append(StringEscapeUtils.escapeCsv("=\"NT$"+s+"\""));
//						} else if(dataTotalNumber == 9 || dataTotalNumber == 16 || dataTotalNumber == 18){
//							content.append("\"" + s + "%\"");
//						} else {
//							content.append("\"" + s + "\"");
//						}
//						content.append(",");
//						dataTotalNumber++;
//					}
//					content.append("\n");
//				}
//			}
//		}
//		
//
//		if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
//			downloadFileName = new String(filename.getBytes("UTF-8"), "ISO8859-1");
//		} else {
//			downloadFileName = URLEncoder.encode(filename, "UTF-8");			
//		}
//
//		downloadFileStream = new ByteArrayInputStream(content.toString().getBytes("big5"));
//
//		//處理 BOM 開頭要加上 "\uFEFF"
//		//downloadFileStream = new ByteArrayInputStream(("\uFEFF" + content.toString()).getBytes("utf-8"));
//	}
//
//	private void resultSumDataTrans_ad_action(List<AdActionReportVO> resultSumData) throws Exception {
//		tableDataTotalList = new LinkedList<String>();
//		tableDataTotalList.add("總計：" + intFormat.format(resultSumData.size()));
//		tableDataTotalList.add("");
//		tableDataTotalList.add("");
//		tableDataTotalList.add("");
//		tableDataTotalList.add("");
//		tableDataTotalList.add("");
//
//		double t_pv = 0; //總曝光數
//		double t_click = 0; //總互動數
//		double t_ctr = 0; //互動率
//		double t_invalid = 0; //無效點選次數
//		double t_costAvg = 0; //單次互動費用
//		double t_kiloCost = 0;	//千次曝光費用
//		double t_cost = 0; //總費用
//		//轉換數
//		double t_convert_count = 0;
//		//轉換價值
//		double t_convert_price_count = 0;
//		double t_convert_ctr = 0;
//		double t_convert_cost = 0;
//		double t_convert_investment_cost = 0;
//		//加總
//		for (int i=0; i<resultSumData.size(); i++) {
//			AdActionReportVO vo = resultSumData.get(i);
//			t_pv += vo.getAdPvSum().doubleValue();
//			t_click += vo.getAdClkSum().doubleValue();
//			t_cost += vo.getAdPriceSum().doubleValue();
//			t_invalid += vo.getAdInvClkSum().doubleValue();
//			t_convert_count += vo.getConvertCount().doubleValue();
//			t_convert_price_count += vo.getConvertPriceCount().doubleValue();
//		}
//		t_cost = new BigDecimal(String.valueOf(t_cost)).setScale(3, BigDecimal.ROUND_FLOOR).doubleValue();
//		
//		//互動率 = 總互動次數 / 總曝光數
//		if (t_pv>0 && t_click>0) {
//			t_ctr = (t_click / t_pv) * 100;
//		}
//
//		//單次互動費用 = 總費用 / 總互動次數
//		if (t_cost>0 && t_click>0) {
//			t_costAvg = t_cost / t_click;
//		}
//		
//		//千次曝光費用 = 總費用*1000 / 曝光數
//		if(t_cost>0 && t_pv>0){
//			t_kiloCost = (t_cost * 1000) / t_pv;
//		}
//		//轉換率
//		if(t_convert_count > 0 && t_click > 0){
//			t_convert_ctr  = (t_convert_count / t_click) * 100;
//		}
//		//平均轉換成本
//		if(t_cost > 0 && t_convert_count > 0){
//			t_convert_cost = t_cost / t_convert_count;
//		}
//		//廣告投資報酬率
//		if(t_convert_price_count > 0 && t_cost > 0){
//			t_convert_investment_cost = (t_convert_price_count / t_cost) * 100;
//		}			
//		if (!tableHeadShowList.isEmpty()) {
//			String mapKey;
//			for (String s: tableHeadShowList) {
//				mapKey = tableHeadNameMap.get(s);
//				if(mapKey.equals(EnumReport.REPORT_CHART_CONVERT_CTR.getTextValue())){
//					tableDataTotalList.addLast(doubleFormat3.format(t_convert_ctr));
//				}else if(mapKey.equals(EnumReport.REPORT_CHART_CONVERT_COST.getTextValue())){
//					tableDataTotalList.addLast(doubleFormat3.format(t_convert_cost));
//				}else if(mapKey.equals(EnumReport.REPORT_CHART_CONVERT_INVESTMENT.getTextValue())){
//					tableDataTotalList.addLast(doubleFormat3.format(t_convert_investment_cost));
//				}else if(mapKey.equals(EnumReport.REPORT_CHART_CONVERT.getTextValue())){
//					tableDataTotalList.addLast(intFormat.format(t_convert_count));
//				}else if(mapKey.equals(EnumReport.REPORT_CHART_CONVERT_PRICE.getTextValue())){
//					tableDataTotalList.addLast(intFormat.format(t_convert_price_count));
//				}else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_PV.getTextValue())) {
//					tableDataTotalList.addLast(intFormat.format(t_pv));
//				} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_CLICK.getTextValue())) {
//					tableDataTotalList.addLast(intFormat.format(t_click));
//				} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_CTR.getTextValue())) {
//					tableDataTotalList.addLast(doubleFormat3.format(t_ctr));
//				} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_INVALID.getTextValue())) {
//					tableDataTotalList.addLast(intFormat.format(t_invalid));
//				} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_AVGCOST.getTextValue())) {
//					tableDataTotalList.addLast(doubleFormat3.format(t_costAvg));
//				} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_KILOCOST.getTextValue())) {
//					tableDataTotalList.addLast(doubleFormat3.format(t_kiloCost));
//				} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_COST.getTextValue())) {
//					tableDataTotalList.addLast(doubleFormat3.format(t_cost));
//				}
//			}
//		}
//	}
//
//	private void resultSumDataTrans_ad_group(List<AdGroupReportVO> resultSumData) throws Exception {
//
//		NumberFormat intFormat = new DecimalFormat("###,###,###,###");
//		NumberFormat doubleFormat = new DecimalFormat("###,###,###,##0.00");
//
//		tableDataTotalList = new LinkedList<String>();
//		tableDataTotalList.add("總計：" + intFormat.format(resultSumData.size()));
//		tableDataTotalList.add("");
//		tableDataTotalList.add("");
//		tableDataTotalList.add("");
//		tableDataTotalList.add("");
//		tableDataTotalList.add("");
//
//		double t_pv = 0; //總曝光數
//		double t_click = 0; //總互動數
//		double t_ctr = 0; //互動率
//		double t_invalid = 0; //無效點選次數
//		double t_costAvg = 0; //單次互動費用
//		double t_kiloCost = 0;	//千次曝光費用
//		double t_cost = 0; //總費用
//		//轉換數
//		double t_convert_count = 0;
//		//轉換價值
//		double t_convert_price_count = 0;
//		double t_convert_ctr = 0;
//		double t_convert_cost = 0;
//		double t_convert_investment_cost = 0;
//		//加總
//		for (int i=0; i<resultSumData.size(); i++) {
//			AdGroupReportVO vo = resultSumData.get(i);
//			t_pv += vo.getAdPvSum().doubleValue();
//			t_click += vo.getAdClkSum().doubleValue();
//			t_cost += vo.getAdPriceSum().doubleValue();
//			t_invalid += vo.getAdInvClkSum().doubleValue();
//			t_convert_count += vo.getConvertCount().doubleValue();
//			t_convert_price_count += vo.getConvertPriceCount().doubleValue();
//		}
//		t_cost = new BigDecimal(String.valueOf(t_cost)).setScale(3, BigDecimal.ROUND_FLOOR).doubleValue();
//		
//		//互動率 = 總互動次數 / 總曝光數
//		if (t_pv>0 && t_click>0) {
//			t_ctr = (t_click / t_pv) * 100;
//		}
//
//		//單次互動費用 = 總費用 / 總互動次數
//		if (t_cost>0 && t_click>0) {
//			t_costAvg = t_cost / t_click;
//		}
//		
//		//千次曝光費用 = 總費用*1000 / 曝光數
//		if(t_cost>0 && t_pv>0){
//			t_kiloCost = (t_cost * 1000) / t_pv;
//		}
//		//轉換率
//		if(t_convert_count > 0 && t_click > 0){
//			t_convert_ctr  = (t_convert_count / t_click) * 100;
//		}
//		//平均轉換成本
//		if(t_cost > 0 && t_convert_count > 0){
//			t_convert_cost = t_cost / t_convert_count;
//		}
//		//廣告投資報酬率
//		if(t_convert_price_count > 0 && t_cost > 0){
//			t_convert_investment_cost = (t_convert_price_count / t_cost) * 100;
//		}	
//		if (!tableHeadShowList.isEmpty()) {
//			String mapKey;
//			for (String s: tableHeadShowList) {
//				mapKey = tableHeadNameMap.get(s);
//				if(mapKey.equals(EnumReport.REPORT_CHART_CONVERT_CTR.getTextValue())){
//					tableDataTotalList.addLast(doubleFormat3.format(t_convert_ctr));
//				}else if(mapKey.equals(EnumReport.REPORT_CHART_CONVERT_COST.getTextValue())){
//					tableDataTotalList.addLast(doubleFormat3.format(t_convert_cost));
//				}else if(mapKey.equals(EnumReport.REPORT_CHART_CONVERT_INVESTMENT.getTextValue())){
//					tableDataTotalList.addLast(doubleFormat3.format(t_convert_investment_cost));
//				}else if(mapKey.equals(EnumReport.REPORT_CHART_CONVERT.getTextValue())){
//					tableDataTotalList.addLast(intFormat.format(t_convert_count));
//				}else if(mapKey.equals(EnumReport.REPORT_CHART_CONVERT_PRICE.getTextValue())){
//					tableDataTotalList.addLast(intFormat.format(t_convert_price_count));
//				}else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_PV.getTextValue())) {
//					tableDataTotalList.addLast(intFormat.format(t_pv));
//				} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_CLICK.getTextValue())) {
//					tableDataTotalList.addLast(intFormat.format(t_click));
//				} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_CTR.getTextValue())) {
//					tableDataTotalList.addLast(doubleFormat3.format(t_ctr));
//				} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_INVALID.getTextValue())) {
//					tableDataTotalList.addLast(intFormat.format(t_invalid));
//				} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_AVGCOST.getTextValue())) {
//					tableDataTotalList.addLast(doubleFormat3.format(t_costAvg));
//				} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_KILOCOST.getTextValue())) {
//					tableDataTotalList.addLast(doubleFormat.format(t_kiloCost));
//				} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_COST.getTextValue())) {
//					tableDataTotalList.addLast(doubleFormat3.format(t_cost));
//				}
//			}
//		}
//	}
//
//	private void resultSumDataTrans_kw(List<AdKeywordReportVO> resultSumData_kw) {
//
//		NumberFormat intFormat = new DecimalFormat("###,###,###,###");
//		NumberFormat doubleFormat = new DecimalFormat("###,###,###,##0.00");
//
//		//AdKeywordReportDataTotal = new AdKeywordReportVO();
//
//		AdKeywordReportDataTotal.setDataTotal("總計：" + intFormat.format(resultSumData_kw.size()));
//		
//		//廣泛比對
//		double t_pv = 0; //總曝光數
//		double t_click = 0; //總點選次數
//		double t_ctr = 0; //點選率
//		double t_invalid = 0; //無效點選次數
//		double t_costAvg = 0; //平均點選費用
//		double t_cost = 0; //總費用
//		
//		//詞組比對
//		double t_phrPv = 0; //總曝光數
//		double t_phrClick = 0; //總點選次數
//		double t_phrCtr = 0; //點選率
//		double t_phrInvalid = 0; //無效點選次數
//		double t_phrCostAvg = 0; //平均點選費用
//		double t_phrCost = 0; //總費用
//		
//		//精準比對
//		double t_prePv = 0; //總曝光數
//		double t_preClick = 0; //總點選次數
//		double t_preCtr = 0; //點選率
//		double t_preInvalid = 0; //無效點選次數
//		double t_preCostAvg = 0; //平均點選費用
//		double t_preCost = 0; //總費用
//		
//		//總計比對
//		double t_pvTotal = 0; //總曝光數
//		double t_clickTotal = 0; //總點選次數
//		double t_ctrTotal = 0; //點選率
//		double t_invalidTotal = 0; //無效點選次數
//		double t_costAvgTotal = 0; //平均點選費用
//		double t_costTotal = 0; //總費用
//
//		//加總
//		for (int i=0; i<resultSumData_kw.size(); i++) {
//
//			AdKeywordReportVO keywordReportVO = resultSumData_kw.get(i);
//
//			//廣泛比對
//			t_pv += new Double(keywordReportVO.getKwPvSum());
//			t_click += new Double(keywordReportVO.getKwClkSum());
//			t_cost += Math.round(new Double(keywordReportVO.getKwPriceSum()));
//			t_invalid += new Double(keywordReportVO.getKwInvClkSum());
//			
//			//詞組比對
//			t_phrPv += new Double(keywordReportVO.getKwPhrPvSum());
//			t_phrClick += new Double(keywordReportVO.getKwPhrClkSum());
//			t_phrCost += new Double(keywordReportVO.getKwPhrPriceSum());
//			t_phrInvalid += new Double(keywordReportVO.getKwPhrInvClkSum());
//			
//			//精準比對
//			t_prePv += new Double(keywordReportVO.getKwPrePvSum());
//			t_preClick += new Double(keywordReportVO.getKwPreClkSum());
//			t_preCost += new Double(keywordReportVO.getKwPrePriceSum());
//			t_preInvalid += new Double(keywordReportVO.getKwPreInvClkSum());
//		}
//
//		//總計
//		t_pvTotal = t_pv + t_phrPv + t_prePv;
//		t_clickTotal = t_click + t_phrClick + t_preClick;
//		t_costTotal = t_cost + t_phrCost + t_preCost;
//		t_invalidTotal = t_invalid + t_phrInvalid + t_preInvalid;
//		
//		//點選率 = 總點選次數 / 總曝光數
//		if (t_pv>0 && t_click>0) {	//廣泛比對
//			t_ctr = (t_click / t_pv) * 100;
//		}
//		if (t_phrPv>0 && t_phrClick>0) {	//詞組比對
//			t_phrCtr = (t_phrClick / t_phrPv) * 100;
//		}
//		if (t_prePv>0 && t_preClick>0) {	//精準比對
//			t_preCtr = (t_preClick / t_prePv) * 100;
//		}
//		if (t_pvTotal>0 && t_clickTotal>0) {	//總計
//			t_ctrTotal = (t_clickTotal / t_pvTotal) * 100;
//		}
//
//		//平均點選費用 = 總費用 / 總點選次數
//		if (t_cost>0 && t_click>0) {	//廣泛比對
//			t_costAvg = t_cost / t_click;
//		}
//		if (t_phrCost>0 && t_phrClick>0) {	//詞組比對
//			t_phrCostAvg = t_phrCost / t_phrClick;
//		}
//		if (t_preCost>0 && t_preClick>0) {	//精準比對
//			t_preCostAvg = t_preCost / t_preClick;
//		}
//		if (t_costTotal>0 && t_clickTotal>0) {	//總計
//			t_costAvgTotal = t_costTotal / t_clickTotal;
//		}
//		//廣泛比對
//		AdKeywordReportDataTotal.setKwPvSum(intFormat.format(t_pv));
//		AdKeywordReportDataTotal.setKwClkSum(intFormat.format(t_click));
//		AdKeywordReportDataTotal.setKwCtrSum(doubleFormat.format(t_ctr));
//		AdKeywordReportDataTotal.setKwPriceSum(intFormat.format(t_cost));
//		AdKeywordReportDataTotal.setKwPriceAvgSum(doubleFormat.format(t_costAvg));
//		AdKeywordReportDataTotal.setKwInvClkSum(intFormat.format(t_invalid));
//		
//		//詞組比對
//		AdKeywordReportDataTotal.setKwPhrPvSum(intFormat.format(t_phrPv));
//		AdKeywordReportDataTotal.setKwPhrClkSum(intFormat.format(t_phrClick));
//		AdKeywordReportDataTotal.setKwPhrCtrSum(doubleFormat.format(t_phrCtr));
//		AdKeywordReportDataTotal.setKwPhrPriceSum(intFormat.format(t_phrCost));
//		AdKeywordReportDataTotal.setKwPhrPriceAvgSum(doubleFormat.format(t_phrCostAvg));
//		AdKeywordReportDataTotal.setKwPhrInvClkSum(intFormat.format(t_phrInvalid));
//		
//		//精準比對
//		AdKeywordReportDataTotal.setKwPrePvSum(intFormat.format(t_prePv));
//		AdKeywordReportDataTotal.setKwPreClkSum(intFormat.format(t_preClick));
//		AdKeywordReportDataTotal.setKwPreCtrSum(doubleFormat.format(t_preCtr));
//		AdKeywordReportDataTotal.setKwPrePriceSum(intFormat.format(t_preCost));
//		AdKeywordReportDataTotal.setKwPrePriceAvgSum(doubleFormat.format(t_preCostAvg));
//		AdKeywordReportDataTotal.setKwPreInvClkSum(intFormat.format(t_preInvalid));
//		
//		//總計
//		AdKeywordReportDataTotal.setKwPvTotal(intFormat.format(t_pvTotal));
//		AdKeywordReportDataTotal.setKwClkTotal(intFormat.format(t_clickTotal));
//		AdKeywordReportDataTotal.setKwCtrTotal(doubleFormat.format(t_ctrTotal));
//		AdKeywordReportDataTotal.setKwPriceTotal(intFormat.format(t_costTotal));
//		AdKeywordReportDataTotal.setKwPriceAvgTotal(doubleFormat.format(t_costAvgTotal));
//		AdKeywordReportDataTotal.setKwInvClkTotal(intFormat.format(t_invalidTotal));
//		
//		/*tableDataTotalList = new LinkedList<String>();
//		tableDataTotalList.add("總計：" + intFormat.format(resultSumData_kw.size()));
//		tableDataTotalList.add("");
//		tableDataTotalList.add("");
//		tableDataTotalList.add("");
//
//		double t_pv = 0; //總曝光數
//		double t_click = 0; //總點選次數
//		double t_ctr = 0; //點選率
//		double t_invalid = 0; //無效點選次數
//		double t_costAvg = 0; //平均點選費用
//		double t_cost = 0; //總費用
//
//		//加總
//		for (int i=0; i<resultSumData_kw.size(); i++) {
//
//			AdKeywordReportVO vo = resultSumData_kw.get(i);
//
//			t_pv += new Double(vo.getKwPvSum());
//			t_click += new Double(vo.getKwClkSum());
//			t_cost += new Double(vo.getKwPriceSum());
//			t_invalid += new Double(vo.getKwInvClkSum());
//		}
//
//		//點選率 = 總點選次數 / 總曝光數
//		if (t_pv>0 && t_click>0) {
//			t_ctr = (t_click / t_pv) * 100;
//		}
//
//		//平均點選費用 = 總費用 / 總點選次數
//		if (t_cost>0 && t_click>0) {
//			t_costAvg = t_cost / t_click;
//		}
//		
//		if (!tableHeadShowList.isEmpty()) {
//			String mapKey;
//			for (String s: tableHeadShowList) {
//				mapKey = tableHeadNameMap.get(s);
//				if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_PV.getTextValue())) {
//					tableDataTotalList.addLast(intFormat.format(t_pv));
//				} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_CLICK.getTextValue())) {
//					tableDataTotalList.addLast(intFormat.format(t_click));
//				} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_CTR.getTextValue())) {
//					tableDataTotalList.addLast(doubleFormat.format(t_ctr));
//				} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_INVALID.getTextValue())) {
//					tableDataTotalList.addLast(intFormat.format(t_invalid));
//				} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_AVGCOST.getTextValue())) {
//					tableDataTotalList.addLast(doubleFormat.format(t_costAvg));
//				} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_COST.getTextValue())) {
//					tableDataTotalList.addLast(intFormat.format(t_cost));
//				}
//			}
//		}*/
//	}
//
//	private void resultSumDataTrans_ad(List<AdReportVO> resultSumData_ad) {
//		tableDataTotalList = new LinkedList<String>();
//		tableDataTotalList.add("總計：" + intFormat.format(resultSumData_ad.size()));
//		tableDataTotalList.add("");
//		tableDataTotalList.add("");
//		tableDataTotalList.add("");
//		tableDataTotalList.add("");
//		tableDataTotalList.add("");
//		if(downloadFlag.equals("yes")){
//			tableDataTotalList.add("");
//			tableDataTotalList.add("");
//			tableDataTotalList.add("");
//		}
//
//		double t_pv = 0; //總曝光數
//		double t_click = 0; //總互動數
//		double t_ctr = 0; //互動率
//		double t_invalid = 0; //無效點選次數
//		double t_costAvg = 0; //單次互動費用
//		double t_kiloCost = 0;	//千次曝光費用
//		double t_cost = 0; //總費用
//		//轉換數
//		double t_convert_count = 0;
//		//轉換價值
//		double t_convert_price_count = 0;
//		double t_convert_ctr = 0;
//		double t_convert_cost = 0;
//		double t_convert_investment_cost = 0;
//		//加總
//		for (int i=0; i<resultSumData_ad.size(); i++) {
//			AdReportVO vo = resultSumData_ad.get(i);
//			t_pv += new Double(vo.getAdPvSum());
//			t_click += new Double(vo.getAdClkSum());
//			t_cost += new Double(vo.getAdPriceSum());
//			t_invalid += new Double(vo.getAdInvClkSum());
//			t_convert_count += vo.getConvertCount().doubleValue();
//			t_convert_price_count += vo.getConvertPriceCount().doubleValue();
//		}
//		t_cost = new BigDecimal(String.valueOf(t_cost)).setScale(3, BigDecimal.ROUND_FLOOR).doubleValue();
//		
//		//互動率 = 總互動次數 / 總曝光數
//		if (t_pv>0 && t_click>0) {
//			t_ctr = (t_click / t_pv) * 100;
//		}
//
//		//單次互動費用 = 總費用 / 總互動次數
//		if (t_cost>0 && t_click>0) {
//			t_costAvg = t_cost / t_click;
//		}
//		
//		//千次曝光費用 = 總費用*1000 / 曝光數
//		if(t_cost>0 && t_pv>0){
//			t_kiloCost = (t_cost * 1000) / t_pv;
//		}
//		//轉換率
//		if(t_convert_count > 0 && t_click > 0){
//			t_convert_ctr  = (t_convert_count / t_click) * 100;
//		}
//		//平均轉換成本
//		if(t_cost > 0 && t_convert_count > 0){
//			t_convert_cost = t_cost / t_convert_count;
//		}
//		//廣告投資報酬率
//		if(t_convert_price_count > 0 && t_cost > 0){
//			t_convert_investment_cost = (t_convert_price_count / t_cost) * 100;
//		}	
//		if (!tableHeadShowList.isEmpty()) {
//			String mapKey;
//			for (String s: tableHeadShowList) {
//				mapKey = tableHeadNameMap.get(s);
//				if(mapKey.equals(EnumReport.REPORT_CHART_CONVERT_CTR.getTextValue())){
//					tableDataTotalList.addLast(doubleFormat3.format(t_convert_ctr));
//				}else if(mapKey.equals(EnumReport.REPORT_CHART_CONVERT_COST.getTextValue())){
//					tableDataTotalList.addLast(doubleFormat3.format(t_convert_cost));
//				}else if(mapKey.equals(EnumReport.REPORT_CHART_CONVERT_INVESTMENT.getTextValue())){
//					tableDataTotalList.addLast(doubleFormat3.format(t_convert_investment_cost));
//				}else if(mapKey.equals(EnumReport.REPORT_CHART_CONVERT.getTextValue())){
//					tableDataTotalList.addLast(intFormat.format(t_convert_count));
//				}else if(mapKey.equals(EnumReport.REPORT_CHART_CONVERT_PRICE.getTextValue())){
//					tableDataTotalList.addLast(intFormat.format(t_convert_price_count));
//				}else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_PV.getTextValue())) {
//					tableDataTotalList.addLast(intFormat.format(t_pv));
//				} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_CLICK.getTextValue())) {
//					tableDataTotalList.addLast(intFormat.format(t_click));
//				} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_CTR.getTextValue())) {
//					tableDataTotalList.addLast(doubleFormat3.format(t_ctr));
//				} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_INVALID.getTextValue())) {
//					tableDataTotalList.addLast(intFormat.format(t_invalid));
//				} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_AVGCOST.getTextValue())) {
//					tableDataTotalList.addLast(doubleFormat3.format(t_costAvg));
//				} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_KILOCOST.getTextValue())) {
//					tableDataTotalList.addLast(doubleFormat3.format(t_kiloCost));
//				} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_COST.getTextValue())) {
//					tableDataTotalList.addLast(doubleFormat3.format(t_cost));
//				}
//			}
//		}
//	}
//
//	private void resultDataTrans_ad_action(List<AdActionReportVO> resultData) throws Exception {
//		LinkedList<String> tableInDataList;
//		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
//
//		long nowTime = new Date().getTime();
//
//		for (int i=0; i<resultData.size(); i++) {
//
//			tableInDataList = new LinkedList<String>();
//
//			AdActionReportVO adActionReportVO = resultData.get(i);
//
//			String adActionSeq = adActionReportVO.getAdActionSeq();
//
//			PfpAdAction pfpAdAction =  adActionService.getPfpAdActionBySeq(adActionSeq);
//			if(pfpAdAction == null){
//				continue;
//			}
//			
//			
//			
//			String adActionName = pfpAdAction.getAdActionName();
//		    int adActionStatus = pfpAdAction.getAdActionStatus();
//		    String adActionStartDate = dateFormat2.format(pfpAdAction.getAdActionStartDate());
//		    String adActionEndDate = dateFormat2.format(pfpAdAction.getAdActionEndDate());
//
//			double pv = adActionReportVO.getAdPvSum().doubleValue();
//			double click = adActionReportVO.getAdClkSum().doubleValue();
//			double cost = new Double(adActionReportVO.getAdPriceSum());
//			double invClick = adActionReportVO.getAdInvClkSum().doubleValue();
//			double ctr = 0;
//			double costAvg = 0;
//			double kiloCost = 0;
//			double convertCount = adActionReportVO.getConvertCount().doubleValue();
//			double convertPriceCount = adActionReportVO.getConvertPriceCount().doubleValue();
//			double convertCTR = 0;
//			double convertCost = 0;
//			double convertInvestmentCost = 0;
//			String adType = adActionReportVO.getAdType();
//			String adDevice = adActionReportVO.getAdDevice();
//			String adOperatingRuleName = adActionReportVO.getAdOperatingRuleDesc();
//
//			//互動率 = 互動次數 / 曝光數
//			if (pv>0 && click>0) {
//				ctr = (click / pv) * 100;
//			}
//
//			//單次互動費用 = 總費用 / 總互動次數
//			if (cost>0 && click>0) {
//				costAvg = cost / click;
//			}
//
//			//千次曝光費用 = 總費用*1000 / 曝光數
//			if(cost>0 && pv>0){
//				kiloCost = (cost * 1000) / pv;
//			}
//			//轉換率
//			if(convertCount > 0 && click > 0){
//				convertCTR  = (convertCount / click) * 100;
//			}
//			//平均轉換成本
//			if(cost > 0 && convertCount > 0){
//				convertCost = cost / convertCount;
//			}
//			//廣告投資報酬率
//			if(convertPriceCount > 0 && cost > 0){
//				convertInvestmentCost = (convertPriceCount / cost) * 100;
//			}
//			//狀態為開啟的話必須判斷走期( 待播放 or 走期中 or 已結束 )
//			if (adActionStatus == EnumStatus.Open.getStatusId()) {
//				long startDate = (dateFormat.parse(adActionStartDate + " 00:00:00")).getTime();
//				long endDate = (dateFormat.parse(adActionEndDate + " 23:59:59")).getTime();
//				if (nowTime < startDate) {
//					adActionStatus = EnumStatus.Waitbroadcast.getStatusId();
//				} else if (nowTime > endDate) {
//					adActionStatus = EnumStatus.End.getStatusId();
//				} else {
//					adActionStatus = EnumStatus.Broadcast.getStatusId();
//				}
//			}
//
//			if (StringUtils.isNotEmpty(downloadFlag) && downloadFlag.equals("yes")) {
//				tableInDataList.addLast(adActionName);
//			} else {
//				tableInDataList.addLast("<a href=\"javascript:adIdSearch('" + EnumReport.ADTYPE_GROUP.getTextValue() + "','" + adActionSeq + "')\">" + adActionName + "</a>");
//			}
//
//			tableInDataList.addLast(adType);
//			tableInDataList.addLast(adOperatingRuleName);
//			tableInDataList.addLast(getAdStatusMap().get(Integer.toString(adActionStatus)));
//			
//			if (adActionEndDate.equals("3000-12-31")) {
//				adActionEndDate = "永久";
//			}
//
//			String adDate = adActionStartDate + " ~ " + adActionEndDate;
//
//			tableInDataList.addLast(adDate);
//			tableInDataList.addLast(adDevice);
//
//			if(!tableHeadShowList.isEmpty()){
//				String mapKey;
//				for(String s:tableHeadShowList){
//					mapKey=tableHeadNameMap.get(s);
//					if(mapKey.equals(EnumReport.REPORT_CHART_CONVERT_CTR.getTextValue())){
//						tableInDataList.addLast(doubleFormat3.format(convertCTR));
//					}else if(mapKey.equals(EnumReport.REPORT_CHART_CONVERT_COST.getTextValue())){
//						tableInDataList.addLast(doubleFormat3.format(convertCost));
//					}else if(mapKey.equals(EnumReport.REPORT_CHART_CONVERT_INVESTMENT.getTextValue())){
//						tableInDataList.addLast(doubleFormat3.format(convertInvestmentCost));
//					}else if(mapKey.equals(EnumReport.REPORT_CHART_CONVERT.getTextValue())){
//						tableInDataList.addLast(intFormat.format(convertCount));
//					}else if(mapKey.equals(EnumReport.REPORT_CHART_CONVERT_PRICE.getTextValue())){
//						tableInDataList.addLast(intFormat.format(convertPriceCount));
//					}else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_PV.getTextValue())) {
//						tableInDataList.addLast(intFormat.format(pv));
//					} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_CLICK.getTextValue())) {
//						tableInDataList.addLast(intFormat.format(click));
//					} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_CTR.getTextValue())) {
//						tableInDataList.addLast(doubleFormat3.format(ctr));
//					} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_INVALID.getTextValue())) {
//						tableInDataList.addLast(intFormat.format(invClick));
//					} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_AVGCOST.getTextValue())) {
//						tableInDataList.addLast(doubleFormat3.format(costAvg));
//					} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_KILOCOST.getTextValue())) {
//						tableInDataList.addLast(doubleFormat3.format(kiloCost));
//					} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_COST.getTextValue())) {
//						tableInDataList.addLast(doubleFormat3.format(cost));
//					}
//				}
//			}
//
//			tableDataList.addLast(tableInDataList);
//		}
//	}
//
//	private void resultDataTrans_ad_group(List<AdGroupReportVO> resultData) throws Exception {
//
//		LinkedList<String> tableInDataList;
//
//		NumberFormat intFormat = new DecimalFormat("###,###,###,###");
//		NumberFormat doubleFormat = new DecimalFormat("###,###,###,##0.00");
//
//		for (int i=0; i<resultData.size(); i++) {
//
//			tableInDataList = new LinkedList<String>();
//
//			AdGroupReportVO vo = resultData.get(i);
//
//			String adGroupSeq = vo.getAdGroupSeq();
//
//			PfpAdGroup pfpAdGroup = adGroupService.getPfpAdGroupBySeq(adGroupSeq);
//
//			String adGroupName = pfpAdGroup.getAdGroupName();
//			int adGroupStatus = pfpAdGroup.getAdGroupStatus();
//
//			double pv = vo.getAdPvSum().doubleValue();
//			double click = vo.getAdClkSum().doubleValue();
//			double cost = vo.getAdPriceSum().doubleValue();
//			double invClick = vo.getAdInvClkSum().doubleValue();
//			double ctr = 0;
//			double costAvg = 0;
//			double kiloCost = 0;
//			double convertCount = vo.getConvertCount().doubleValue();
//			double convertPriceCount = vo.getConvertPriceCount().doubleValue();
//			double convertCTR = 0;
//			double convertCost = 0;
//			double convertInvestmentCost = 0;
//			String adDevice = vo.getAdDevice();
//			String adType = vo.getAdType();
//			String adOperatingRuleName = vo.getAdOperatingRule();
//			String adClkPriceTypeName = vo.getAdClkPriceType();
//
//			//互動率 = 互動次數 / 曝光數
//			if (pv>0 && click>0) {
//				ctr = (click / pv) * 100;
//			}
//
//			//單次互動費用 = 總費用 / 總互動次數
//			if (cost>0 && click>0) {
//				costAvg = cost / click;
//			}
//
//			//千次曝光費用 = 總費用*1000 / 曝光數
//			if(cost>0 && pv>0){
//				kiloCost = (cost * 1000) / pv;
//			}
//			//轉換率
//			if(convertCount > 0 && click > 0){
//				convertCTR  = (convertCount / click) * 100;
//			}
//			//平均轉換成本
//			if(cost > 0 && convertCount > 0){
//				convertCost = cost / convertCount;
//			}
//			//廣告投資報酬率
//			if(convertPriceCount > 0 && cost > 0){
//				convertInvestmentCost = (convertPriceCount / cost) * 100;
//			}
//			if (StringUtils.isNotEmpty(downloadFlag) && downloadFlag.equals("yes")) {
//				tableInDataList.addLast(adGroupName);
//			} else {
//				//tableInDataList.addLast("<a href=\"javascript:adIdSearch('" + EnumReport.ADTYPE_KEYWORD.getTextValue() + "','" + adGroupSeq + "')\">" + adGroupName + "</a>");	
//				tableInDataList.addLast("<a href=\"javascript:adIdSearch('" + EnumReport.ADTYPE_AD.getTextValue() + "','" + adGroupSeq + "')\">" + adGroupName + "</a>");	
//			}
//
//			tableInDataList.addLast(adType);
//			tableInDataList.addLast(adOperatingRuleName);
//			tableInDataList.addLast(getAdStatusMap().get(Integer.toString(adGroupStatus)));
//			tableInDataList.addLast(adClkPriceTypeName);
//			tableInDataList.addLast(adDevice);
//
//			if(!tableHeadShowList.isEmpty()){
//				String mapKey;
//				for(String s:tableHeadShowList){
//					mapKey=tableHeadNameMap.get(s);
//					if(mapKey.equals(EnumReport.REPORT_CHART_CONVERT_CTR.getTextValue())){
//						tableInDataList.addLast(doubleFormat3.format(convertCTR));
//					}else if(mapKey.equals(EnumReport.REPORT_CHART_CONVERT_COST.getTextValue())){
//						tableInDataList.addLast(doubleFormat3.format(convertCost));
//					}else if(mapKey.equals(EnumReport.REPORT_CHART_CONVERT_INVESTMENT.getTextValue())){
//						tableInDataList.addLast(doubleFormat3.format(convertInvestmentCost));
//					}else if(mapKey.equals(EnumReport.REPORT_CHART_CONVERT.getTextValue())){
//						tableInDataList.addLast(intFormat.format(convertCount));
//					}else if(mapKey.equals(EnumReport.REPORT_CHART_CONVERT_PRICE.getTextValue())){
//						tableInDataList.addLast(intFormat.format(convertPriceCount));
//					}else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_PV.getTextValue())) {
//						tableInDataList.addLast(intFormat.format(pv));
//					} else if (mapKey.trim().equals(EnumReport.REPORT_CHART_TYPE_CLICK.getTextValue())) {
//						tableInDataList.addLast(intFormat.format(click));
//					} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_CTR.getTextValue())) {
//						tableInDataList.addLast(doubleFormat3.format(ctr));
//					} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_INVALID.getTextValue())) {
//						tableInDataList.addLast(intFormat.format(invClick));
//					} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_AVGCOST.getTextValue())) {
//						tableInDataList.addLast(doubleFormat3.format(costAvg));
//					} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_KILOCOST.getTextValue())) {
//						tableInDataList.addLast(doubleFormat3.format(kiloCost));
//					} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_COST.getTextValue())) {
//						tableInDataList.addLast(doubleFormat3.format(cost));
//					}
//				}
//			}
//
//			tableDataList.addLast(tableInDataList);
//
//		}
//	}
//
//	private void resultDataTrans_kw(List<AdKeywordReportVO> resultData) {
//
//		String adActionName = "";
//		String adGroupName = "";
//		String adKeyword = "";
//		String adKeywordRankAvg = ""; 	 //廣泛比對平均廣告排名
//		String adKeywordPhrRankAvg = ""; //詞組比對平均廣告排名
//		String adKeywordPreRankAvg = ""; //精準比對平均廣告排名
//
//		int adActionStatus = 0;
//		int adGroupStatus = 0;
//		int adKeywordStatus = 0;
//		int adKeywordpoen = 0;
//		int adKeywordPhrpoen = 0;
//		int adKeywordPrepoen = 0;
//
//		//AdKeywordReportVOList = new ArrayList<AdKeywordReportVO>();
//		
//		for (int i=0; i<resultData.size(); i++) {
//
//			//廣泛比對
//			double pv = 0;
//			double click = 0;
//			double cost = 0;
//			double invClick = 0;
//			double ctr = 0;
//			double costAvg = 0;
//			
//			//詞組比對
//			double phrPv = 0;
//			double phrClick = 0;
//			double phrCost = 0;
//			double phrInvClick = 0;
//			double phrCtr = 0;
//			double phrCostAvg = 0;
//			
//			//精準比對
//			double prePv = 0;
//			double preClick = 0;
//			double preCost = 0;
//			double preInvClick = 0;
//			double preCtr = 0;
//			double preCostAvg = 0;
//			
//			//總計
//			double pvTotal = 0;
//			double clickTotal = 0;
//			double costTotal = 0;
//			double invClickTotal = 0;
//			double ctrTotal = 0;
//			double costAvgTotal = 0;
//
//			AdKeywordReportVO keywordReportVO = resultData.get(i);
//
//			adKeywordRankAvg = keywordReportVO.getAdRankAvg();
//			adKeywordPhrRankAvg = keywordReportVO.getAdPhrRankAvg();
//			adKeywordPreRankAvg = keywordReportVO.getAdPreRankAvg();
//			keywordReportVO.setAdRankAvg(doubleFormat.format(new Double(adKeywordRankAvg)));
//			keywordReportVO.setAdPhrRankAvg(doubleFormat.format(new Double(adKeywordPhrRankAvg)));
//			keywordReportVO.setAdPreRankAvg(doubleFormat.format(new Double(adKeywordPreRankAvg)));
//
//			//廣泛比對
//			pv = new Double(keywordReportVO.getKwPvSum());
//			click = new Double(keywordReportVO.getKwClkSum());
//			cost = new Double(keywordReportVO.getKwPriceSum());
//			invClick = new Double(keywordReportVO.getKwInvClkSum());
//
//			//點選率 = 點選次數 / 曝光數
//			if (pv>0 && click>0) {
//				ctr = (click / pv) * 100;
//			}
//
//			//平均點選費用 = 總費用 / 總點選次數
//			if (cost>0 && click>0) {
//				costAvg = cost / click;
//			}
//			
//			keywordReportVO.setKwPvSum(intFormat.format(pv));
//			keywordReportVO.setKwClkSum(intFormat.format(click));
//			keywordReportVO.setKwCtrSum(doubleFormat.format(ctr));
//			keywordReportVO.setKwPriceSum(intFormat.format(cost));
//			keywordReportVO.setKwPriceAvgSum(doubleFormat.format(costAvg));
//			keywordReportVO.setKwInvClkSum(intFormat.format(invClick));
//
//			
//			//詞組比對
//			phrPv = new Double(keywordReportVO.getKwPhrPvSum());
//			phrClick = new Double(keywordReportVO.getKwPhrClkSum());
//			phrCost = new Double(keywordReportVO.getKwPhrPriceSum());
//			phrInvClick = new Double(keywordReportVO.getKwPhrInvClkSum());
//
//			//點選率 = 點選次數 / 曝光數
//			if (phrPv>0 && phrClick>0) {
//				phrCtr = (phrClick / phrPv) * 100;
//			}
//
//			//平均點選費用 = 總費用 / 總點選次數
//			if (phrCost>0 && phrClick>0) {
//				phrCostAvg = phrCost / phrClick;
//			}
//			
//			keywordReportVO.setKwPhrPvSum(intFormat.format(phrPv));
//			keywordReportVO.setKwPhrClkSum(intFormat.format(phrClick));
//			keywordReportVO.setKwPhrCtrSum(doubleFormat.format(phrCtr));
//			keywordReportVO.setKwPhrPriceSum(intFormat.format(phrCost));
//			keywordReportVO.setKwPhrPriceAvgSum(doubleFormat.format(phrCostAvg));
//			keywordReportVO.setKwPhrInvClkSum(intFormat.format(phrInvClick));
//			
//			
//			//精準比對
//			prePv = new Double(keywordReportVO.getKwPrePvSum());
//			preClick = new Double(keywordReportVO.getKwPreClkSum());
//			preCost = new Double(keywordReportVO.getKwPrePriceSum());
//			preInvClick = new Double(keywordReportVO.getKwPreInvClkSum());
//
//			//點選率 = 點選次數 / 曝光數
//			if (prePv>0 && preClick>0) {
//				preCtr = (preClick / prePv) * 100;
//			}
//
//			//平均點選費用 = 總費用 / 總點選次數
//			if (preCost>0 && preClick>0) {
//				preCostAvg = preCost / preClick;
//			}
//			
//			keywordReportVO.setKwPrePvSum(intFormat.format(prePv));
//			keywordReportVO.setKwPreClkSum(intFormat.format(preClick));
//			keywordReportVO.setKwPreCtrSum(doubleFormat.format(preCtr));
//			keywordReportVO.setKwPrePriceSum(intFormat.format(preCost));
//			keywordReportVO.setKwPrePriceAvgSum(doubleFormat.format(preCostAvg));
//			keywordReportVO.setKwPreInvClkSum(intFormat.format(preInvClick));
//			
//			
//			//總計
//			pvTotal = pv + phrPv + prePv;
//			clickTotal = click + phrClick + preClick;
//			costTotal = cost + phrCost + preCost;
//			invClickTotal = invClick + phrInvClick + preInvClick;;
//
//			//點選率 = 點選次數 / 曝光數
//			if (pvTotal>0 && clickTotal>0) {
//				ctrTotal = (clickTotal / pvTotal) * 100;
//			}
//
//			//平均點選費用 = 總費用 / 總點選次數
//			if (costTotal>0 && clickTotal>0) {
//				costAvgTotal = costTotal / clickTotal;
//			}
//			
//			keywordReportVO.setKwPvTotal(intFormat.format(pvTotal));
//			keywordReportVO.setKwClkTotal(intFormat.format(clickTotal));
//			keywordReportVO.setKwCtrTotal(doubleFormat.format(ctrTotal));
//			keywordReportVO.setKwPriceTotal(intFormat.format(costTotal));
//			keywordReportVO.setKwPriceAvgTotal(doubleFormat.format(costAvgTotal));
//			keywordReportVO.setKwInvClkTotal(intFormat.format(invClickTotal));
//			
//			
//			PfpAdAction pfpAdAction = new PfpAdAction();
//			try {
//				pfpAdAction = adActionService.getPfpAdActionBySeq(keywordReportVO.getAdActionSeq());
//				adActionName = pfpAdAction.getAdActionName();
//				adActionStatus = pfpAdAction.getAdActionStatus();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//			PfpAdGroup pfpAdGroup = new PfpAdGroup();
//			try {
//				pfpAdGroup = adGroupService.getPfpAdGroupBySeq(keywordReportVO.getAdGroupSeq());
//				adGroupName = pfpAdGroup.getAdGroupName();
//				adGroupStatus = pfpAdGroup.getAdGroupStatus();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//			PfpAdKeyword pfpAdKeyword = new PfpAdKeyword();
//			try {
//				pfpAdKeyword = pfpAdKeywordDAO.getPfpAdKeywordBySeq(keywordReportVO.getAdKeywordSeq());
//				adKeywordStatus = pfpAdKeyword.getAdKeywordStatus();
//				adKeywordpoen = pfpAdKeyword.getAdKeywordOpen();
//				adKeywordPhrpoen = pfpAdKeyword.getAdKeywordPhraseOpen();
//				adKeywordPrepoen = pfpAdKeyword.getAdKeywordPrecisionOpen();
//				adKeyword = pfpAdKeyword.getAdKeyword();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//				
//			if(adKeywordpoen == 1){
//				keywordReportVO.setKwOpen("開啟");
//			} else {
//				keywordReportVO.setKwOpen("關閉");
//			}
//			if(adKeywordPhrpoen == 1){
//				keywordReportVO.setKwPhrOpen("開啟");
//			} else {
//				keywordReportVO.setKwPhrOpen("關閉");
//			}
//			if(adKeywordPrepoen == 1){
//				keywordReportVO.setKwPreOpen("開啟");
//			} else {
//				keywordReportVO.setKwPreOpen("關閉");
//			}
//			
//			
//			keywordReportVO.setAdKeyword(adKeyword);
//			keywordReportVO.setAdGroupName(adGroupName);
//			keywordReportVO.setAdActionName(adActionName);
//			keywordReportVO.setAdStatus(getAdStatusMap().get(Integer.toString(adKeywordStatus)));
//			
//			AdKeywordReportVOList.add(keywordReportVO);
//		
//		
//		/*LinkedList<String> tableInDataList;
//
//		String adActionName = "";
//		String adGroupName = "";
//		String adKeyword = "";
//		String adKeywordRankAvg = ""; //平均廣告排名
//		int adActionStatus = 0;
//		int adGroupStatus = 0;
//		int adKeywordStatus = 0;
//
//		NumberFormat intFormat = new DecimalFormat("###,###,###,###");
//		NumberFormat doubleFormat = new DecimalFormat("###,###,###,###.##");
//
//		for (int i=0; i<resultData.size(); i++) {
//
//			AdKeywordReportVO keywordReportVO = resultData.get(i);
//
//			tableInDataList = new LinkedList<String>();
//
//			adKeyword = keywordReportVO.getAdKeyword();
//			adKeywordRankAvg = keywordReportVO.getAdRankAvg();
//
//			double pv = new Double(keywordReportVO.getKwPvSum());
//			double click = new Double(keywordReportVO.getKwClkSum());
//			double cost = new Double(keywordReportVO.getKwPriceSum());
//			double invClick = new Double(keywordReportVO.getKwInvClkSum());
//			double ctr = 0;
//			double costAvg = 0;
//			String kwDevice = keywordReportVO.getKwDevice();
//			String kwAdType = keywordReportVO.getKwAdType();
//
//			//點選率 = 點選次數 / 曝光數
//			if (pv>0 && click>0) {
//				ctr = (click / pv) * 100;
//			}
//
//			//平均點選費用 = 總費用 / 總點選次數
//			if (cost>0 && click>0) {
//				costAvg = cost / click;
//			}
//
//			PfpAdAction pfpAdAction = new PfpAdAction();
//			try {
//				pfpAdAction = adActionService.getPfpAdActionBySeq(keywordReportVO.getAdActionSeq());
//				adActionName = pfpAdAction.getAdActionName();
//				adActionStatus = pfpAdAction.getAdActionStatus();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//			PfpAdGroup pfpAdGroup = new PfpAdGroup();
//			try {
//				pfpAdGroup = adGroupService.getPfpAdGroupBySeq(keywordReportVO.getAdGroupSeq());
//				adGroupName = pfpAdGroup.getAdGroupName();
//				adGroupStatus = pfpAdGroup.getAdGroupStatus();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//			PfpAdKeyword pfpAdKeyword = new PfpAdKeyword();
//			try {
//				pfpAdKeyword = pfpAdKeywordDAO.getPfpAdKeywordBySeq(keywordReportVO.getAdKeywordSeq());
//				adKeywordStatus = pfpAdKeyword.getAdKeywordStatus();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//
////			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////			DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
////
////			long nowTime = new Date().getTime();
////
////			//廣告狀態為開啟的話必須判斷走期( 待播放 or 走期中 or 已結束 )
////			try {
////				String adActionStartDate = dateFormat2.format(pfpAdAction.getAdActionStartDate());
////				String adActionEndDate = dateFormat2.format(pfpAdAction.getAdActionEndDate());
////				if (adActionStatus == EnumStatus.Open.getStatusId()) {
////					long _startDate = (dateFormat.parse(adActionStartDate + " 00:00:00")).getTime();
////					long _endDate = (dateFormat.parse(adActionEndDate + " 23:59:59")).getTime();
////					if (nowTime < _startDate) {
////						adActionStatus = EnumStatus.Waitbroadcast.getStatusId();
////					} else if (nowTime > _endDate) {
////						adActionStatus = EnumStatus.End.getStatusId();
////					} else {
////						adActionStatus = EnumStatus.Broadcast.getStatusId();
////					}
////				}
////
////			} catch (Exception e) {
////				log.error(e.getMessage(), e);
////			}
//
//			tableInDataList.addLast(adKeyword);
//			tableInDataList.addLast(kwAdType);
//			tableInDataList.addLast(getAdStatusMap().get(Integer.toString(adKeywordStatus)));
//			tableInDataList.addLast(kwDevice);
//
//			if(!tableHeadShowList.isEmpty()){
//				String mapKey;
//				for(String s:tableHeadShowList){
//					mapKey=tableHeadNameMap.get(s);
//					if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_PV.getTextValue())) {
//						tableInDataList.addLast(intFormat.format(pv));
//					} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_CLICK.getTextValue())) {
//						tableInDataList.addLast(intFormat.format(click));
//					} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_CTR.getTextValue())) {
//						tableInDataList.addLast(doubleFormat.format(ctr));
//					} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_INVALID.getTextValue())) {
//						tableInDataList.addLast(intFormat.format(invClick));
//					} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_AVGCOST.getTextValue())) {
//						tableInDataList.addLast(doubleFormat.format(costAvg));
//					} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_COST.getTextValue())) {
//						tableInDataList.addLast(intFormat.format(cost));
//					}
//				}
//			}
//
//			tableDataList.addLast(tableInDataList);*/
//		}
//	}
//
//	private void resultDataTrans_ad(List<AdReportVO> resultData) {
//
//		LinkedList<String> tableInDataList;
//		int adActionStatus = 0;
//		int adGroupStatus = 0;
//		int adStatus = 0;
//		for (int i=0; i<resultData.size(); i++) {
//
//			tableInDataList = new LinkedList<String>();
//
//			AdReportVO adReportVO = resultData.get(i);
//
//			double pv = new Double(adReportVO.getAdPvSum());
//			double click = new Double(adReportVO.getAdClkSum());
//			double cost = new Double(adReportVO.getAdPriceSum());
//			double invClick = new Double(adReportVO.getAdInvClkSum());
//			double ctr = 0;
//			double costAvg = 0;
//			double kiloCost = 0;
//			double convertCount = adReportVO.getConvertCount().doubleValue();
//			double convertPriceCount = adReportVO.getConvertPriceCount().doubleValue();
//			double convertCTR = 0;
//			double convertCost = 0;
//			double convertInvestmentCost = 0;
//			String adDevice = adReportVO.getAdDevice();
//			String adType = adReportVO.getAdType();
//			String adOperatingRuleName = adReportVO.getAdOperatingRuleDesc();
//			String adClkPriceTypeName = adReportVO.getAdClkPriceType();
//
//			//互動率 = 互動次數 / 曝光數
//			if (pv>0 && click>0) {
//				ctr = (click / pv) * 100;
//			}
//
//			//單次互動費用 = 總費用 / 總互動次數
//			if (cost>0 && click>0) {
//				costAvg = cost / click;
//			}
//
//			//千次曝光費用 = 總費用*1000 / 曝光數
//			if(cost>0 && pv>0){
//				kiloCost = (cost * 1000) / pv;
//			}
//			//轉換率
//			if(convertCount > 0 && click > 0){
//				convertCTR  = (convertCount / click) * 100;
//			}
//			//平均轉換成本
//			if(cost > 0 && convertCount > 0){
//				convertCost = cost / convertCount;
//			}
//			//廣告投資報酬率
//			if(convertPriceCount > 0 && cost > 0){
//				convertInvestmentCost = (convertPriceCount / cost) * 100;
//			}
//			if(downloadFlag.equals("yes")){
//				if(adReportVO.getAdOperatingRule().equals("MEDIA")){
//					tableInDataList.addLast(adReportVO.getTitle());
//					tableInDataList.addLast(adReportVO.getContent());
//					tableInDataList.addLast(adReportVO.getShowUrl());
//					tableInDataList.addLast(adReportVO.getRealUrl());
//				}else if(adReportVO.getAdOperatingRule().equals("VIDEO")){
//					tableInDataList.addLast(adReportVO.getTitle());
//					tableInDataList.addLast(adReportVO.getContent());
//					tableInDataList.addLast(adReportVO.getAdVideoUrl());
//					tableInDataList.addLast(adReportVO.getRealUrl());
//				}else if(adReportVO.getAdOperatingRule().equals("PROD")){
//					tableInDataList.addLast(adReportVO.getTitle());
//					tableInDataList.addLast("");
//					tableInDataList.addLast("");
//					tableInDataList.addLast(adReportVO.getRealUrl());
//				}
//			} else {
//				tableInDataList.addLast(adReportVO.getAdPreview());
//			}
//
//
//			PfpAdAction pfpAdAction = new PfpAdAction();
//			try {
//				pfpAdAction = adActionService.getPfpAdActionBySeq(adReportVO.getAdActionSeq());
//				adActionStatus = pfpAdAction.getAdActionStatus();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//			PfpAdGroup pfpAdGroup = new PfpAdGroup();
//			try {
//				pfpAdGroup = adGroupService.getPfpAdGroupBySeq(adReportVO.getAdGroupSeq());
//				adGroupStatus = pfpAdGroup.getAdGroupStatus();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//			PfpAd pfpAd = new PfpAd();
//			try {
//				pfpAd = pfpAdDAO.getPfpAdBySeq(adReportVO.getAdSeq());
//				adStatus = pfpAd.getAdStatus();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
//
//			long nowTime = new Date().getTime();
//
//			//廣告狀態為開啟的話必須判斷走期( 待播放 or 走期中 or 已結束 )
//			try {
//				String adActionStartDate = dateFormat2.format(pfpAdAction.getAdActionStartDate());
//				String adActionEndDate = dateFormat2.format(pfpAdAction.getAdActionEndDate());
//				if (adActionStatus == EnumStatus.Open.getStatusId()) {
//					long _startDate = (dateFormat.parse(adActionStartDate + " 00:00:00")).getTime();
//					long _endDate = (dateFormat.parse(adActionEndDate + " 23:59:59")).getTime();
//					if (nowTime < _startDate) {
//						adActionStatus = EnumStatus.Waitbroadcast.getStatusId();
//					} else if (nowTime > _endDate) {
//						adActionStatus = EnumStatus.End.getStatusId();
//					} else {
//						adActionStatus = EnumStatus.Broadcast.getStatusId();
//					}
//				}
//
//			} catch (Exception e) {
//				log.error(e.getMessage(), e);
//			}
//
//			//播放狀態
//			String alter = "";
//			String icon = "icon_adclose.gif";
//			if (adActionStatus == EnumStatus.Broadcast.getStatusId() &&
//				adGroupStatus == EnumStatus.Open.getStatusId() &&
//				adStatus == EnumStatus.Open.getStatusId()) {
//
//				alter = "走期中";
//				icon = "icon_adopen.gif";
//
//			} else if (adActionStatus == EnumStatus.Broadcast.getStatusId() &&
//						adGroupStatus == EnumStatus.Open.getStatusId() &&
//						adStatus != EnumStatus.Open.getStatusId()) {
//
//				alter = "廣告明細" + getAdStatusMap().get(Integer.toString(adStatus));;
//
//			} else if (adActionStatus == EnumStatus.Broadcast.getStatusId() &&
//						adGroupStatus != EnumStatus.Open.getStatusId() &&
//						adStatus != EnumStatus.Open.getStatusId()) {
//
//				alter = "分類" + getAdStatusMap().get(Integer.toString(adGroupStatus)) + "，廣告明細" + getAdStatusMap().get(Integer.toString(adStatus));
//
//			} else if (adActionStatus == EnumStatus.Broadcast.getStatusId() &&
//						adGroupStatus != EnumStatus.Open.getStatusId() &&
//						adStatus == EnumStatus.Open.getStatusId()) {
//
//				alter = "分類" + getAdStatusMap().get(Integer.toString(adGroupStatus));
//
//			} else if (adActionStatus != EnumStatus.Broadcast.getStatusId() &&
//						adGroupStatus == EnumStatus.Open.getStatusId() &&
//						adStatus == EnumStatus.Open.getStatusId()) {
//
//				alter = "廣告" + getAdStatusMap().get(Integer.toString(adActionStatus));
//
//			} else if (adActionStatus != EnumStatus.Broadcast.getStatusId() &&
//						adGroupStatus == EnumStatus.Open.getStatusId() &&
//						adStatus != EnumStatus.Open.getStatusId()) {
//
//				alter = "廣告" + getAdStatusMap().get(Integer.toString(adActionStatus)) + "，廣告明細" + getAdStatusMap().get(Integer.toString(adStatus));
//			
//			} else if (adActionStatus != EnumStatus.Broadcast.getStatusId() &&
//						adGroupStatus != EnumStatus.Open.getStatusId() &&
//						adStatus == EnumStatus.Open.getStatusId()) {
//
//				alter = "廣告" + getAdStatusMap().get(Integer.toString(adActionStatus)) + "，分類" + getAdStatusMap().get(Integer.toString(adGroupStatus));
//
//			} else if (adActionStatus != EnumStatus.Broadcast.getStatusId() &&
//						adGroupStatus != EnumStatus.Open.getStatusId() &&
//						adStatus != EnumStatus.Open.getStatusId()) {
//
//				alter = "廣告" + getAdStatusMap().get(Integer.toString(adActionStatus)) + "，分類" + getAdStatusMap().get(Integer.toString(adGroupStatus)) + "，廣告明細" + getAdStatusMap().get(Integer.toString(adStatus));
//
//			}
//			tableInDataList.addLast(adType);
//			tableInDataList.addLast(adOperatingRuleName);
//			if(downloadFlag.equals("yes")){
//				tableInDataList.addLast(alter);
//			} else {
//				tableInDataList.addLast("<img src=\"./html/img/" + icon + "\" alt=\"" + alter + "\" title=\"" + alter + "\">");
//			}
//			tableInDataList.addLast(adClkPriceTypeName);
//			tableInDataList.addLast(adDevice);
//
//			if(!tableHeadShowList.isEmpty()){
//				String mapKey;
//				for(String s:tableHeadShowList){
//					mapKey=tableHeadNameMap.get(s);
//					if(mapKey.equals(EnumReport.REPORT_CHART_CONVERT_CTR.getTextValue())){
//						tableInDataList.addLast(doubleFormat3.format(convertCTR));
//					}else if(mapKey.equals(EnumReport.REPORT_CHART_CONVERT_COST.getTextValue())){
//						tableInDataList.addLast(doubleFormat3.format(convertCost));
//					}else if(mapKey.equals(EnumReport.REPORT_CHART_CONVERT_INVESTMENT.getTextValue())){
//						tableInDataList.addLast(doubleFormat3.format(convertInvestmentCost));
//					}else if(mapKey.equals(EnumReport.REPORT_CHART_CONVERT.getTextValue())){
//						tableInDataList.addLast(intFormat.format(convertCount));
//					}else if(mapKey.equals(EnumReport.REPORT_CHART_CONVERT_PRICE.getTextValue())){
//						tableInDataList.addLast(intFormat.format(convertPriceCount));
//					}else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_PV.getTextValue())) {
//						tableInDataList.addLast(intFormat.format(pv));
//					} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_CLICK.getTextValue())) {
//						tableInDataList.addLast(intFormat.format(click));
//					} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_CTR.getTextValue())) {
//						tableInDataList.addLast(doubleFormat3.format(ctr));
//					} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_INVALID.getTextValue())) {
//						tableInDataList.addLast(intFormat.format(invClick));
//					} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_AVGCOST.getTextValue())) {
//						tableInDataList.addLast(doubleFormat3.format(costAvg));
//					} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_KILOCOST.getTextValue())) {
//						tableInDataList.addLast(doubleFormat3.format(kiloCost));
//					} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_COST.getTextValue())) {
//						tableInDataList.addLast(doubleFormat3.format(cost));
//					}
//				}
//			}
//
//			tableDataList.addLast(tableInDataList);
//		}
//	}
//
//	
//	
//	//商品成效報表
//	public String adProdDetailReport() throws Exception{
//		String prodData = com.pchome.soft.depot.utils.HttpUtil.getInstance().getResult(admServer+"adDataRMIAction.html?pfpCustomerInfoId="+pfpCustomerInfoId+"&adSeq="+adSeq+"&startDate="+startDate+"&endDate="+endDate,"UTF-8");
//		returnData = new ByteArrayInputStream(prodData.getBytes("UTF-8"));
//		return SUCCESS;
//	}
//	
//	public LinkedList<String> getTableHeadList() {
//		return tableHeadList;
//	}
//
//	public void setAdActionReportService(IAdActionReportService adActionReportService) {
//		this.adActionReportService = adActionReportService;
//	}
//
//	public LinkedList<LinkedList<String>> getTableDataList() {
//		return tableDataList;
//	}
//
//	public String getStartDate() {
//		return startDate;
//	}
//
//	public void setStartDate(String startDate) {
//		this.startDate = startDate;
//	}
//
//	public String getEndDate() {
//		return endDate;
//	}
//
//	public void setEndDate(String endDate) {
//		this.endDate = endDate;
//	}
//
//	public int getPage() {
//		return page;
//	}
//
//	public void setPage(int page) {
//		this.page = page;
//	}
//
//	public int getPageSize() {
//		return pageSize;
//	}
//
//	public void setPageSize(int pageSize) {
//		this.pageSize = pageSize;
//	}
//
//	public int getTotalPage() {
//		return totalPage;
//	}
//
//	public String getOptionSelect() {
//		return optionSelect;
//	}
//
//	public void setOptionSelect(String optionSelect) {
//		this.optionSelect = optionSelect;
//	}
//
//	public String getOptionNotSelect() {
//		return optionNotSelect;
//	}
//
//	public void setOptionNotSelect(String optionNotSelect) {
//		this.optionNotSelect = optionNotSelect;
//	}
//
//	public LinkedList<String> getTableHeadShowList() {
//		return tableHeadShowList;
//	}
//
//	public LinkedList<String> getTableHeadNotShowList() {
//		return tableHeadNotShowList;
//	}
//
//	public String getAdPvclkDevice() {
//		return adPvclkDevice;
//	}
//
//	public void setAdPvclkDevice(String adPvclkDevice) {
//		this.adPvclkDevice = adPvclkDevice;
//	}
//
//	public String getAdType() {
//		return adType;
//	}
//
//	public void setAdType(String adType) {
//		this.adType = adType;
//	}
//
//	public String getSearchText() {
//		return searchText;
//	}
//
//	public void setSearchText(String searchText) {
//		this.searchText = searchText;
//	}
//
//	public String getAdShowWay() {
//		return adShowWay;
//	}
//
//	public void setAdShowWay(String adShowWay) {
//		this.adShowWay = adShowWay;
//	}
//
//	public String getAdSearchWay() {
//		return adSearchWay;
//	}
//
//	public void setAdSearchWay(String adSearchWay) {
//		this.adSearchWay = adSearchWay;
//	}
//
//	public void setAdGroupReportService(IAdGroupReportService adGroupReportService) {
//		this.adGroupReportService = adGroupReportService;
//	}
//
//	public void setAdKeywordReportService(
//			IAdKeywordReportService adKeywordReportService) {
//		this.adKeywordReportService = adKeywordReportService;
//	}
//
//	public String getSearchId() {
//		return searchId;
//	}
//
//	public void setSearchId(String searchId) {
//		this.searchId = searchId;
//	}
//
//	public String getStepStr() {
//		return stepStr;
//	}
//
//	public void setAdActionService(IPfpAdActionService adActionService) {
//		this.adActionService = adActionService;
//	}
//
//	public void setAdGroupService(IPfpAdGroupService adGroupService) {
//		this.adGroupService = adGroupService;
//	}
//
//	public void setPfpAdKeywordDAO(IPfpAdKeywordDAO pfpAdKeywordDAO) {
//		this.pfpAdKeywordDAO = pfpAdKeywordDAO;
//	}
//
//	public void setPfpAdDAO(IPfpAdDAO pfpAdDAO) {
//		this.pfpAdDAO = pfpAdDAO;
//	}
//
//	public InputStream getDownloadFileStream() {
//		return downloadFileStream;
//	}
//
//	public String getDownloadFileName() {
//		return downloadFileName;
//	}
//
//	public String getDownloadFlag() {
//		return downloadFlag;
//	}
//
//	public void setDownloadFlag(String downloadFlag) {
//		log.info("value set="+downloadFlag);
//		this.downloadFlag = downloadFlag;
//	}
//
//	public void setCustomerInfoService(IPfpCustomerInfoService customerInfoService) {
//		this.customerInfoService = customerInfoService;
//	}
//
//	public void setOpenFlashUtil(SpringOpenFlashUtil openFlashUtil) {
//		this.openFlashUtil = openFlashUtil;
//	}
//
//	public String getFlashData() {
//		return flashData;
//	}
//
//	public LinkedHashMap<String, String> getDateSelectMap() {
//		return dateSelectMap;
//	}
//
//	public LinkedList<String> getTableDataTotalList() {
//		return tableDataTotalList;
//	}
//
//	public String getReportTitle() {
//		return reportTitle;
//	}
//
//	public void setAdReportService(IAdReportService adReportService) {
//		this.adReportService = adReportService;
//	}
//
//	public String[] getAlign_data() {
//		return align_data;
//	}
//
//	public String[] getAlign_sum() {
//		return align_sum;
//	}
//
//	public String getShowSearchBarFlag() {
//		return showSearchBarFlag;
//	}
//
//	public void setShowSearchBarFlag(String showSearchBarFlag) {
//		this.showSearchBarFlag = showSearchBarFlag;
//	}
//
//	public String getCharPic() {
//		return charPic;
//	}
//
//	public void setCharPic(String charPic) {
//		this.charPic = charPic;
//	}
//
//	public String getCharType() {
//		return charType;
//	}
//
//	public void setCharType(String charType) {
//		this.charType = charType;
//	}
//
//	public List<AdKeywordReportVO> getAdKeywordReportVOList() {
//		return AdKeywordReportVOList;
//	}
//
//	public AdKeywordReportVO getAdKeywordReportDataTotal() {
//		return AdKeywordReportDataTotal;
//	}
//
//	public String getAdOperatingRule() {
//		return adOperatingRule;
//	}
//
//	public void setAdOperatingRule(String adOperatingRule) {
//		this.adOperatingRule = adOperatingRule;
//	}
//
//	public IPfpCodeService getPfpCodeService() {
//		return pfpCodeService;
//	}
//
//	public void setPfpCodeService(IPfpCodeService pfpCodeService) {
//		this.pfpCodeService = pfpCodeService;
//	}
//
//	public InputStream getReturnData() {
//		return returnData;
//	}
//
//	public void setReturnData(InputStream returnData) {
//		this.returnData = returnData;
//	}
//
//	public String getAdSeq() {
//		return adSeq;
//	}
//
//	public void setAdSeq(String adSeq) {
//		this.adSeq = adSeq;
//	}
//
//	public String getPfpCustomerInfoId() {
//		return pfpCustomerInfoId;
//	}
//
//	public void setPfpCustomerInfoId(String pfpCustomerInfoId) {
//		this.pfpCustomerInfoId = pfpCustomerInfoId;
//	}
//
//	public String getAdmServer() {
//		return admServer;
//	}
//
//	public void setAdmServer(String admServer) {
//		this.admServer = admServer;
//	}
	
}