package com.pchome.akbpfp.struts2.action.report;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.pchome.akbpfp.db.dao.report.AdActionReportVO;
import com.pchome.akbpfp.db.pojo.PfpCode;
import com.pchome.akbpfp.db.pojo.PfpCustomerInfo;
import com.pchome.akbpfp.db.service.codeManage.IPfpCodeService;
import com.pchome.akbpfp.db.service.customerInfo.IPfpCustomerInfoService;
import com.pchome.akbpfp.db.service.report.IAdActionReportService;
import com.pchome.enumerate.report.EnumReport;
import com.pchome.soft.depot.utils.DateValueUtil;
import com.pchome.soft.depot.utils.SpringOpenFlashUtil;
import com.pchome.utils.CommonUtils;

public class ReportAdDailyAction extends BaseReportAction {

	private static final long serialVersionUID = 1L;

	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	NumberFormat intFormat = new DecimalFormat("###,###,###,###");
	NumberFormat doubleFormat = new DecimalFormat("###,###,###,###.##");
	
	private IPfpCodeService pfpCodeService;
	private IAdActionReportService adActionReportService;
	
	private boolean hasPfpCodeflag = false; // 是否有使用轉換追蹤的PFP帳號
	
	private String startDate = ""; // 查詢開始日期
	private String endDate = ""; // 查詢結束日期
	private String searchText = ""; // 搜尋文字
	private String whereMap; // 篩選
	private String sortBy = ""; // 排序
	private int page = 1; // 第幾頁
	private int pageSize = 10; // 每頁筆數
	private int totalPage = 0; //總頁數
	
	private List<AdActionReportVO> resultData = new ArrayList<>(); // 查詢結果
	private List<AdActionReportVO> resultSumData = new ArrayList<>(); // 查詢結果加總
	
	// 下載報表
	private String isDownload = "false";
	private IPfpCustomerInfoService customerInfoService;
	private InputStream downloadFileStream; // 下載報表的 input stream
	private String downloadFileName; // 下載顯示名
	private String showHideColumn = ""; // 哪些欄位顯示或隱藏
	
	// 圖表
	private SpringOpenFlashUtil openFlashUtil;
	private String flashData;
	private String charType = ""; // 度量
	private String charPic = ""; // 圖表格式
	
	/**
	 * 查詢
	 */
	@Override
	public String execute() throws Exception {
		
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
		
		// 查詢明細與總計資料
		AdActionReportVO vo = new AdActionReportVO();
		vo.setCustomerInfoId(super.getCustomer_info_id());
		vo.setStartDate(startDate);
		vo.setEndDate(endDate);
		vo.setSearchText(searchText);
		vo.setWhereMap(whereMap);
		vo.setSortBy(sortBy);
		vo.setPage(page);
		vo.setPageSize(pageSize);
		vo.setDownloadOrIsNotCuttingPagination(Boolean.parseBoolean(isDownload));
		resultData = adActionReportService.queryReportAdDailyData(vo);
		resultSumData = adActionReportService.queryReportAdDailySumData(vo);
		
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
		String filename = "每日花費成效報表_" + dformat.format(new Date()) + FILE_TYPE;
		PfpCustomerInfo customerInfo = customerInfoService.findCustomerInfo(super.getCustomer_info_id());

		StringBuffer content = new StringBuffer();
		content.append("帳戶," + customerInfo.getCustomerInfoTitle());
		content.append("\n\n");
		content.append("報表名稱,PChome 每日花費成效");
		content.append("\n\n");
		content.append("裝置," + CommonUtils.getInstance().getAdDeviceName(whereMap));
		content.append("\n");
		content.append("日期範圍," + startDate + " 到 " + endDate);
		content.append("\n\n");

		content.append("日期,裝置,曝光數,互動數,互動率,單次互動費用,千次曝光費用,費用,");
		
		Map<String, Boolean> showHideColumnMap = new HashMap<>(); // 紀錄顯示其他欄位值
		String[] showHideColumnArr = showHideColumn.split(",");
		for (int i = 0; i < showHideColumnArr.length; i++) {
			String mapKey = showHideColumnArr[i].split("-")[0];
			boolean mapVal = Boolean.parseBoolean(showHideColumnArr[i].split("-")[1]);
			showHideColumnMap.put(mapKey, mapVal);
			if(mapVal) { // 勾選顯示，則加入title
				if (mapKey.equalsIgnoreCase(EnumReport.CONVERT_COUNT.getTextValue())) {
					content.append("轉換次數,");
				} else if (mapKey.equalsIgnoreCase(EnumReport.CONVERT_CTR.getTextValue())) {
					content.append("轉換率,");
				} else if (mapKey.equalsIgnoreCase(EnumReport.CONVERT_PRICE_COUNT.getTextValue())) {
					content.append("總轉換價值,");
				} else if (mapKey.equalsIgnoreCase(EnumReport.CONVERT_COST.getTextValue())) {
					content.append("平均轉換成本,");
				} else if (mapKey.equalsIgnoreCase(EnumReport.CONVERT_INVESTMENT_COST.getTextValue())) {
					content.append("廣告投資報酬率,");
				}
			}
		}
		content.append("\n");
		
		if (!resultData.isEmpty()) {
			// 明細資料
			for (int i = 0; i < resultData.size(); i++) {
				content.append("\"" + dateFormat.format(resultData.get(i).getReportDate()) + "\",");
				content.append("\"" + resultData.get(i).getAdDevice() + "\",");
				content.append("\"" + intFormat.format(resultData.get(i).getAdPvSum()) + "\",");
				content.append("\"" + doubleFormat.format(resultData.get(i).getAdClkSum()) + "\",");
				content.append("\"" + doubleFormat.format(resultData.get(i).getCtr()) + "%\",");
				content.append("\"NT$ " + doubleFormat.format(resultData.get(i).getAvgCost()) + "\",");
				content.append("\"NT$ " + doubleFormat.format(resultData.get(i).getKiloCost()) + "\",");
				content.append("\"NT$ " + doubleFormat.format(resultData.get(i).getAdPriceSum()) + "\",");
				
				if (showHideColumnMap.get(EnumReport.CONVERT_COUNT.getTextValue())) {
					content.append("\"" + doubleFormat.format(resultData.get(i).getConvertCount()) + "\",");
				}
				if (showHideColumnMap.get(EnumReport.CONVERT_CTR.getTextValue())) {
					content.append("\"" + doubleFormat.format(resultData.get(i).getConvertCTR()) + "%\",");
				}
				if (showHideColumnMap.get(EnumReport.CONVERT_PRICE_COUNT.getTextValue())) {
					content.append("\"NT$ " + doubleFormat.format(resultData.get(i).getConvertPriceCount()) + "\",");
				}
				if (showHideColumnMap.get(EnumReport.CONVERT_COST.getTextValue())) {
					content.append("\"NT$ " + doubleFormat.format(resultData.get(i).getConvertCost()) + "\",");
				}
				if (showHideColumnMap.get(EnumReport.CONVERT_INVESTMENT_COST.getTextValue())) {
					content.append("\"" + doubleFormat.format(resultData.get(i).getConvertInvestmentCost()) + "%\",");
				}
				content.append("\n");
			}
			
			content.append("\n");
			
			// 總計資料
			for (int i = 0; i < resultSumData.size(); i++) {
				content.append("\"總計：" + intFormat.format(resultSumData.get(i).getRowCount()) + "筆廣告活動\",");
				content.append("\"\",");
				content.append("\"" + doubleFormat.format(resultSumData.get(i).getAdPvSum()) + "\",");
				content.append("\"" + doubleFormat.format(resultSumData.get(i).getAdClkSum()) + "\",");
				content.append("\"" + doubleFormat.format(resultSumData.get(i).getCtr()) + "%\",");
				content.append("\"NT$ " + doubleFormat.format(resultSumData.get(i).getAvgCost()) + "\",");
				content.append("\"NT$ " + doubleFormat.format(resultSumData.get(i).getKiloCost()) + "\",");
				content.append("\"NT$ " + doubleFormat.format(resultSumData.get(i).getAdPriceSum()) + "\",");
				
				if (showHideColumnMap.get(EnumReport.CONVERT_COUNT.getTextValue())) {
					content.append("\"" + doubleFormat.format(resultSumData.get(i).getConvertCount()) + "\",");
				}
				if (showHideColumnMap.get(EnumReport.CONVERT_CTR.getTextValue())) {
					content.append("\"" + doubleFormat.format(resultSumData.get(i).getConvertCTR()) + "%\",");
				}
				if (showHideColumnMap.get(EnumReport.CONVERT_PRICE_COUNT.getTextValue())) {
					content.append("\"NT$ " + doubleFormat.format(resultSumData.get(i).getConvertPriceCount()) + "\",");
				}
				if (showHideColumnMap.get(EnumReport.CONVERT_COST.getTextValue())) {
					content.append("\"NT$ " + doubleFormat.format(resultSumData.get(i).getConvertCost()) + "\",");
				}
				if (showHideColumnMap.get(EnumReport.CONVERT_INVESTMENT_COST.getTextValue())) {
					content.append("\"" + doubleFormat.format(resultSumData.get(i).getConvertInvestmentCost()) + "%\",");
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

		AdActionReportVO chartVo = new AdActionReportVO();
		chartVo.setCustomerInfoId(super.getCustomer_info_id());
		chartVo.setStartDate(startDate);
		chartVo.setEndDate(endDate);
		chartVo.setSearchText(searchText);
		chartVo.setWhereMap(whereMap);
		chartVo.setDownloadOrIsNotCuttingPagination(true); // 不切分頁flag
		
		List<AdActionReportVO> resultChartData = adActionReportService.queryReportAdDailyChartData(chartVo);
		
		Map<Date, Float> flashDataMap = new HashMap<>();

		for (int i = 0; i < resultChartData.size(); i++) {
			AdActionReportVO vo = resultChartData.get(i);
			Date reportDate = vo.getReportDate();
			
			if (charType.equals(EnumReport.REPORT_CHART_TYPE_PV.getTextValue())) {
				flashDataMap.put(reportDate, vo.getAdPvSum().floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_CLICK.getTextValue())) {
				flashDataMap.put(reportDate, vo.getAdClkSum().floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_CTR.getTextValue())) {
				flashDataMap.put(reportDate, vo.getCtr().floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_AVGCOST.getTextValue())) {
				flashDataMap.put(reportDate, vo.getAvgCost().floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_KILOCOST.getTextValue())) {
				flashDataMap.put(reportDate, vo.getKiloCost().floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_COST.getTextValue())) {
				flashDataMap.put(reportDate, vo.getAdPriceSum().floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_CONVERT.getTextValue())) {
				flashDataMap.put(reportDate, vo.getConvertCount().floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_CONVERT_CTR.getTextValue())) {
				flashDataMap.put(reportDate, vo.getConvertCTR().floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_CONVERT_PRICE.getTextValue())) {
				flashDataMap.put(reportDate, vo.getConvertPriceCount().floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_CONVERT_COST.getTextValue())) {
				flashDataMap.put(reportDate, vo.getConvertCost().floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_CONVERT_INVESTMENT.getTextValue())) {
				flashDataMap.put(reportDate, vo.getConvertInvestmentCost().floatValue());
			}
		}
		
		flashData = openFlashUtil.getChartDataForArray(charType, startDate, endDate, flashDataMap);

		return SUCCESS;
	}
	
	public IPfpCodeService getPfpCodeService() {
		return pfpCodeService;
	}

	public void setPfpCodeService(IPfpCodeService pfpCodeService) {
		this.pfpCodeService = pfpCodeService;
	}

	public IAdActionReportService getAdActionReportService() {
		return adActionReportService;
	}

	public void setAdActionReportService(IAdActionReportService adActionReportService) {
		this.adActionReportService = adActionReportService;
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

	public List<AdActionReportVO> getResultData() {
		return resultData;
	}

	public List<AdActionReportVO> getResultSumData() {
		return resultSumData;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPage() {
		return page;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setWhereMap(String whereMap) {
		this.whereMap = whereMap;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setIsDownload(String isDownload) {
		this.isDownload = isDownload;
	}

	public InputStream getDownloadFileStream() {
		return downloadFileStream;
	}

	public String getDownloadFileName() {
		return downloadFileName;
	}

	public void setCustomerInfoService(IPfpCustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}

	public void setShowHideColumn(String showHideColumn) {
		this.showHideColumn = showHideColumn;
	}

	public String getFlashData() {
		return flashData;
	}

	public void setCharType(String charType) {
		this.charType = charType;
	}

	public void setCharPic(String charPic) {
		this.charPic = charPic;
	}

	public void setOpenFlashUtil(SpringOpenFlashUtil openFlashUtil) {
		this.openFlashUtil = openFlashUtil;
	}

}