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

import com.pchome.akbpfp.db.dao.report.AdKeywordReportVO;
import com.pchome.akbpfp.db.pojo.PfpCustomerInfo;
import com.pchome.akbpfp.db.service.customerInfo.IPfpCustomerInfoService;
import com.pchome.akbpfp.db.service.report.IAdKeywordReportService;
import com.pchome.enumerate.report.EnumReport;
import com.pchome.soft.depot.utils.DateValueUtil;
import com.pchome.soft.depot.utils.SpringOpenFlashUtil;
import com.pchome.utils.CommonUtils;

public class ReportKeywordAction extends BaseReportAction {

	private static final long serialVersionUID = -8461736631135913196L;

	private IAdKeywordReportService adKeywordReportService;
	
	private LinkedHashMap<String, String> dateSelectMap; // 查詢日期的 rang map,查詢日期頁面顯示
	
	private String startDate = ""; // 查詢開始日期
	private String endDate = ""; // 查詢結束日期
	private String searchText = ""; // 搜尋文字
	private String whereMap; // 篩選
	private String sortBy = ""; // 排序
	private int page = 1; // 第幾頁
	private int pageSize = 10; // 每頁筆數
	private int totalPage = 0; //總頁數
	
	private List<AdKeywordReportVO> resultData = new ArrayList<>(); // 查詢結果
	private List<AdKeywordReportVO> resultSumData = new ArrayList<>(); // 查詢結果加總
	
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
		
		AdKeywordReportVO vo = new AdKeywordReportVO();
		vo.setCustomerInfoId(super.getCustomer_info_id());
		vo.setStartDate(startDate);
		vo.setEndDate(endDate);
		vo.setSearchText(searchText);
		vo.setWhereMap(whereMap);
		vo.setSortBy(sortBy);
		vo.setPage(page);
		vo.setPageSize(pageSize);
		vo.setDownloadOrIsNotCuttingPagination(Boolean.parseBoolean(isDownload));
		resultData = adKeywordReportService.queryReportAdKeywordData(vo);
		resultSumData = adKeywordReportService.queryReportAdKeywordSumData(vo);
		
		totalPage = CommonUtils.getTotalPage(vo.getRowCount(), pageSize);
		
		if (Boolean.parseBoolean(isDownload)) {
			makeDownloadReportData();
		}
		
		return SUCCESS;
	}
	
	/**
	 * 下載報表
	 * 先執行execute()，是下載報表才再執行此方法
	 * @throws Exception
	 */
	private void makeDownloadReportData() throws Exception {
		SimpleDateFormat dformat = new SimpleDateFormat("yyyyMMddhhmmss");
		String filename = "關鍵字成效報表_" + dformat.format(new Date()) + FILE_TYPE;
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
		
		content.append("狀態,關鍵字,廣告,分類,裝置,廣泛比對方式,詞組比對方式,精準比對方式,廣泛比對曝光數,詞組比對曝光數,精準比對曝光數,總曝光數,");
		content.append("廣泛比對點選次數,詞組比對點選次數,精準比對點選次數,總點選次數,廣泛比對點選率,詞組比對點選率,精準比對點選率,總點選率,");
		content.append("廣泛比對平均點選費用,詞組比對平均點選費用,精準比對平均點選費用,總平均點選費用,廣泛比對費用,詞組比對費用,精準比對費用,總費用,");
		
		if (showHideColumnMap.get(EnumReport.RANK_AVG.getTextValue())) {
			content.append("廣泛比對平均廣告排名,詞組比對平均廣告排名,精準比對平均廣告排名,");
		}
		content.append("\n");
		
		if (!resultData.isEmpty()) {
			// 明細資料
			for (int i = 0; i < resultData.size(); i++) {
				content.append("\"" + resultData.get(i).getAdStatusName() + "\",");
				content.append("\"" + resultData.get(i).getAdKeyword() + "\",");
				content.append("\"" + resultData.get(i).getAdActionName() + "\",");
				content.append("\"" + resultData.get(i).getAdGroupName() + "\",");
				content.append("\"" + resultData.get(i).getAdDevice() + "\",");
				content.append("\"" + resultData.get(i).getKwOpenName() + "\",");
				content.append("\"" + resultData.get(i).getKwPhrOpenName() + "\",");
				content.append("\"" + resultData.get(i).getKwPreOpenName() + "\",");
				content.append("\"" + intFormat.format(resultData.get(i).getKwPvSum()) + "\",");
				content.append("\"" + intFormat.format(resultData.get(i).getKwPhrPvSum()) + "\",");
				content.append("\"" + intFormat.format(resultData.get(i).getKwPrePvSum()) + "\",");
				content.append("\"" + intFormat.format(resultData.get(i).getKwPvSubtotal()) + "\",");
				content.append("\"" + intFormat.format(resultData.get(i).getKwClkSum()) + "\",");
				content.append("\"" + intFormat.format(resultData.get(i).getKwPhrClkSum()) + "\",");
				content.append("\"" + intFormat.format(resultData.get(i).getKwPreClkSum()) + "\",");
				content.append("\"" + intFormat.format(resultData.get(i).getKwClkSubtotal()) + "\",");
				content.append("\"" + doubleFormat.format(resultData.get(i).getKwCtrSum()) + "\",");
				content.append("\"" + doubleFormat.format(resultData.get(i).getKwPhrCtrSum()) + "\",");
				content.append("\"" + doubleFormat.format(resultData.get(i).getKwPreCtrSum()) + "\",");
				content.append("\"" + doubleFormat.format(resultData.get(i).getKwCtrSubtotal()) + "\",");
				content.append("\"NT$ " + doubleFormat.format(resultData.get(i).getKwPriceAvgSum()) + "\",");
				content.append("\"NT$ " + doubleFormat.format(resultData.get(i).getKwPhrPriceAvgSum()) + "\",");
				content.append("\"NT$ " + doubleFormat.format(resultData.get(i).getKwPrePriceAvgSum()) + "\",");
				content.append("\"NT$ " + doubleFormat.format(resultData.get(i).getKwPriceAvgSubtotal()) + "\",");
				content.append("\"NT$ " + intFormat.format(resultData.get(i).getKwPriceSum()) + "\",");
				content.append("\"NT$ " + intFormat.format(resultData.get(i).getKwPhrPriceSum()) + "\",");
				content.append("\"NT$ " + intFormat.format(resultData.get(i).getKwPrePriceSum()) + "\",");
				content.append("\"NT$ " + intFormat.format(resultData.get(i).getKwPriceSubtotal()) + "\",");
				
				if (showHideColumnMap.get(EnumReport.RANK_AVG.getTextValue())) {
					content.append("\"" + doubleFormat.format(resultData.get(i).getKwRankAvg()) + "\",");
					content.append("\"" + doubleFormat.format(resultData.get(i).getKwPhrRankAvg()) + "\",");
					content.append("\"" + doubleFormat.format(resultData.get(i).getKwPreRankAvg()) + "\",");
				}
				content.append("\n");
			}
			
			content.append("\n");
			
			// 總計資料
			for (int i = 0; i < resultSumData.size(); i++) {
				content.append("\"\",");
				content.append("\"總計：" + intFormat.format(resultSumData.get(i).getRowCount()) + "\",");
				content.append("\"\",");
				content.append("\"\",");
				content.append("\"\",");
				content.append("\"\",");
				content.append("\"\",");
				content.append("\"\",");
				// 曝光數
				content.append("\"" + intFormat.format(resultSumData.get(i).getReportKwPvTotal()) + "\",");
				content.append("\"" + intFormat.format(resultSumData.get(i).getReportKwPhrPvTotal()) + "\",");
				content.append("\"" + intFormat.format(resultSumData.get(i).getReportKwPrePvTotal()) + "\",");
				content.append("\"" + intFormat.format(resultSumData.get(i).getPvTotal()) + "\",");
				// 互動數
				content.append("\"" + intFormat.format(resultSumData.get(i).getReportKwClkTotal()) + "\",");
				content.append("\"" + intFormat.format(resultSumData.get(i).getReportKwPhrClkTotal()) + "\",");
				content.append("\"" + intFormat.format(resultSumData.get(i).getReportKwPreClkTotal()) + "\",");
				content.append("\"" + intFormat.format(resultSumData.get(i).getClkTotal()) + "\",");
				// 互動率
				content.append("\"" + doubleFormat.format(resultSumData.get(i).getReportKwCtrTotal()) + "%\",");
				content.append("\"" + doubleFormat.format(resultSumData.get(i).getReportKwPhrCtrTotal()) + "%\",");
				content.append("\"" + doubleFormat.format(resultSumData.get(i).getReportKwPreCtrTotal()) + "%\",");
				content.append("\"" + doubleFormat.format(resultSumData.get(i).getCtrTotal()) + "%\",");
				// 單次互動費用
				content.append("\"NT$ " + doubleFormat.format(resultSumData.get(i).getReportKwPriceAvgTotal()) + "\",");
				content.append("\"NT$ " + doubleFormat.format(resultSumData.get(i).getReportKwPhrPriceAvgTotal()) + "\",");
				content.append("\"NT$ " + doubleFormat.format(resultSumData.get(i).getReportKwPrePriceAvgTotal()) + "\",");
				content.append("\"NT$ " + doubleFormat.format(resultSumData.get(i).getPriceAvgTotal()) + "\",");
				// 費用
				content.append("\"NT$ " + doubleFormat2.format(resultSumData.get(i).getReportKwPriceTotal()) + "\",");
				content.append("\"NT$ " + doubleFormat2.format(resultSumData.get(i).getReportKwPhrPriceTotal()) + "\",");
				content.append("\"NT$ " + doubleFormat2.format(resultSumData.get(i).getReportKwPrePriceTotal()) + "\",");
				content.append("\"NT$ " + doubleFormat2.format(resultSumData.get(i).getPriceTotal()) + "\",");
				
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
		AdKeywordReportVO chartVo = new AdKeywordReportVO();
		chartVo.setCustomerInfoId(super.getCustomer_info_id());
		chartVo.setStartDate(startDate);
		chartVo.setEndDate(endDate);
		chartVo.setSearchText(searchText);
		chartVo.setWhereMap(whereMap);
		List<AdKeywordReportVO> resultChartData = adKeywordReportService.queryReportAdKeywordChartData(chartVo);
		
		List<Map<Date, Float>> mapList = new ArrayList<>();
		Map<Date, Float> flashDataMap = new HashMap<>();
		Map<Date, Float> flashPhrDataMap = new HashMap<>();
		Map<Date, Float> flashPreDataMap = new HashMap<>();
		Map<Date, Float> flashTotalDataMap = new HashMap<>();
		
		for (int i = 0; i < resultChartData.size(); i++) {
			AdKeywordReportVO vo = resultChartData.get(i);
			Date reportDate = vo.getReportDate();
			
			if (charType.equals(EnumReport.REPORT_CHART_TYPE_PV.getTextValue())) {
				flashDataMap.put(reportDate, vo.getKwPvSum().floatValue());
				flashPhrDataMap.put(reportDate, vo.getKwPhrPvSum().floatValue());
				flashPreDataMap.put(reportDate, vo.getKwPrePvSum().floatValue());
				flashTotalDataMap.put(reportDate, vo.getKwPvSubtotal().floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_CLICK.getTextValue())) {
				flashDataMap.put(reportDate, vo.getKwClkSum().floatValue());
				flashPhrDataMap.put(reportDate, vo.getKwPhrClkSum().floatValue());
				flashPreDataMap.put(reportDate, vo.getKwPreClkSum().floatValue());
				flashTotalDataMap.put(reportDate, vo.getKwClkSubtotal().floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_CTR.getTextValue())) {
				flashDataMap.put(reportDate, vo.getKwCtrSum().floatValue());
				flashPhrDataMap.put(reportDate, vo.getKwPhrCtrSum().floatValue());
				flashPreDataMap.put(reportDate, vo.getKwPreCtrSum().floatValue());
				flashTotalDataMap.put(reportDate, vo.getKwCtrSubtotal().floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_AVGCOST.getTextValue())) {
				flashDataMap.put(reportDate, vo.getKwPriceAvgSum().floatValue());
				flashPhrDataMap.put(reportDate, vo.getKwPhrPriceAvgSum().floatValue());
				flashPreDataMap.put(reportDate, vo.getKwPrePriceAvgSum().floatValue());
				flashTotalDataMap.put(reportDate, vo.getKwPriceAvgSubtotal().floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_KILOCOST.getTextValue())) {
				flashDataMap.put(reportDate, vo.getKwKiloCost().floatValue());
				flashPhrDataMap.put(reportDate, vo.getKwPhrKiloCost().floatValue());
				flashPreDataMap.put(reportDate, vo.getKwPreKiloCost().floatValue());
				flashTotalDataMap.put(reportDate, vo.getKwKiloCostSubtotal().floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_COST.getTextValue())) {
				flashDataMap.put(reportDate, vo.getKwPriceSum().floatValue());
				flashPhrDataMap.put(reportDate, vo.getKwPhrPriceSum().floatValue());
				flashPreDataMap.put(reportDate, vo.getKwPrePriceSum().floatValue());
				flashTotalDataMap.put(reportDate, vo.getKwPriceSubtotal().floatValue());
			}
		}
		
		mapList.add(flashDataMap);
		mapList.add(flashPhrDataMap);
		mapList.add(flashPreDataMap);
		mapList.add(flashTotalDataMap);

		flashData = openFlashUtil.getKeywordChartDataForArray(charType, startDate, endDate, mapList);
		
		return SUCCESS;
	}
	
	public void setAdKeywordReportService(IAdKeywordReportService adKeywordReportService) {
		this.adKeywordReportService = adKeywordReportService;
	}

	public LinkedHashMap<String, String> getDateSelectMap() {
		return dateSelectMap;
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
	
	public List<AdKeywordReportVO> getResultData() {
		return resultData;
	}

	public List<AdKeywordReportVO> getResultSumData() {
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
	
}