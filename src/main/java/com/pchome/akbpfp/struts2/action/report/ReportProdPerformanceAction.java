package com.pchome.akbpfp.struts2.action.report;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.pchome.akbpfp.db.dao.report.AdProdPerformanceReportVO;
import com.pchome.akbpfp.db.pojo.PfpCustomerInfo;
import com.pchome.akbpfp.db.service.customerInfo.IPfpCustomerInfoService;
import com.pchome.akbpfp.db.service.report.IAdProdPerformanceReportService;
import com.pchome.soft.depot.utils.DateValueUtil;
import com.pchome.soft.depot.utils.SpringOpenFlashUtil;
import com.pchome.utils.CommonUtils;

public class ReportProdPerformanceAction extends BaseReportAction {

	private static final long serialVersionUID = 1L;
	
	private IAdProdPerformanceReportService adProdPerformanceReportService;
	
	private LinkedHashMap<String, String> dateSelectMap; // 查詢日期的 rang map,查詢日期頁面顯示
	
	private String pfpCustomerInfoId = "";
	private String adSeq = ""; // 廣告序號
	private String startDate = ""; // 查詢開始日期
	private String endDate = ""; // 查詢結束日期
	private String searchText = ""; // 搜尋文字
	private String whereMap; // 篩選
	private String sortBy = ""; // 排序
	private int page = 1; // 第幾頁
	private int pageSize = 10; // 每頁筆數
	private int totalPage = 0; //總頁數
	private String prodAdName = ""; // 麵包屑顯示名稱
	private String breadcrumbsType = ""; // 麵包屑
	
	private List<AdProdPerformanceReportVO> resultData = new ArrayList<>(); // 查詢結果
	private List<AdProdPerformanceReportVO> resultSumData = new ArrayList<>(); // 查詢結果加總
	
	// 下載報表
	private String isDownload = "false";
	private IPfpCustomerInfoService customerInfoService;
	private InputStream downloadFileStream; // 下載報表的 input stream
	private String downloadFileName; // 下載顯示名
	
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
		
		AdProdPerformanceReportVO vo = new AdProdPerformanceReportVO();
		vo.setPfpCustomerInfoId(pfpCustomerInfoId);
		vo.setAdSeq(adSeq);
		vo.setStartDate(startDate);
		vo.setEndDate(endDate);
		vo.setSearchText(searchText);
		vo.setWhereMap(whereMap);
		vo.setSortBy(sortBy);
		vo.setPage(page);
		vo.setPageSize(pageSize);
		vo.setDownloadOrIsNotCuttingPagination(Boolean.parseBoolean(isDownload));
		resultData = adProdPerformanceReportService.queryReportAdProdPerformanceData(vo);
		resultSumData = adProdPerformanceReportService.queryReportAdProdPerformanceSumData(vo);
		
		totalPage = CommonUtils.getTotalPage(vo.getRowCount(), pageSize);
		prodAdName = vo.getProdAdName(); // 麵包屑顯示名稱
		
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
		String filename = "商品成效報表_" + dformat.format(new Date()) + FILE_TYPE;
		PfpCustomerInfo customerInfo = customerInfoService.findCustomerInfo(super.getCustomer_info_id());

		StringBuffer content = new StringBuffer();
		content.append("帳戶," + customerInfo.getCustomerInfoTitle());
		content.append("\n");
		content.append("搜尋內容," + searchText);
		content.append("\n");
		content.append("日期範圍," + startDate + " 到 " + endDate);
		content.append("\n\n");
		
		content.append("狀態,商品明細,陳列次數,商品點選數,商品點選率,");
		content.append("\n");
		
		if (!resultData.isEmpty()) {
			// 明細資料
			for (int i = 0; i < resultData.size(); i++) {
				content.append("\"" + resultData.get(i).getEcStatusName() + "\",");
				content.append("\"" + resultData.get(i).getEcName() + "\",");
				content.append("\"" + intFormat.format(resultData.get(i).getAdPvSum()) + "\",");
				content.append("\"" + doubleFormat.format(resultData.get(i).getAdClkSum()) + "\",");
				content.append("\"" + doubleFormat.format(resultData.get(i).getCtr()) + "%\",");
				content.append("\n");
			}
			
			content.append("\n");
			
			// 總計資料
			for (int i = 0; i < resultSumData.size(); i++) {
				content.append("\"總計：" + intFormat.format(resultSumData.get(i).getRowCount()) + "\",");
				content.append("\"\",");
				content.append("\"" + doubleFormat.format(resultSumData.get(i).getAdPvSum()) + "\",");
				content.append("\"" + doubleFormat.format(resultSumData.get(i).getAdClkSum()) + "\",");
				content.append("\"" + doubleFormat.format(resultSumData.get(i).getCtr()) + "%\",");
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
	 */
	public String flashDataDownLoad() {
		AdProdPerformanceReportVO chartVo = new AdProdPerformanceReportVO();
		chartVo.setCharType(charType);
		chartVo.setAdSeq(adSeq);
		chartVo.setPfpCustomerInfoId(pfpCustomerInfoId);
		chartVo.setStartDate(startDate);
		chartVo.setEndDate(endDate);
		chartVo.setSearchText(searchText);
		chartVo.setWhereMap(whereMap);
		Map<Date, Float> flashDataMap = adProdPerformanceReportService.queryReportAdProdPerformanceChartDataMap(chartVo);

		flashData = openFlashUtil.getChartDataForArray(charType, startDate, endDate, flashDataMap);
		return SUCCESS;
	}

	public void setAdProdPerformanceReportService(IAdProdPerformanceReportService adProdPerformanceReportService) {
		this.adProdPerformanceReportService = adProdPerformanceReportService;
	}

	public LinkedHashMap<String, String> getDateSelectMap() {
		return dateSelectMap;
	}

	public String getPfpCustomerInfoId() {
		return pfpCustomerInfoId;
	}

	public void setPfpCustomerInfoId(String pfpCustomerInfoId) {
		this.pfpCustomerInfoId = pfpCustomerInfoId;
	}

	public String getAdSeq() {
		return adSeq;
	}

	public void setAdSeq(String adSeq) {
		this.adSeq = adSeq;
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

	public List<AdProdPerformanceReportVO> getResultData() {
		return resultData;
	}

	public List<AdProdPerformanceReportVO> getResultSumData() {
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

	public void setOpenFlashUtil(SpringOpenFlashUtil openFlashUtil) {
		this.openFlashUtil = openFlashUtil;
	}

	public String getFlashData() {
		return flashData;
	}

	public void setCharType(String charType) {
		this.charType = charType;
	}

	public String getProdAdName() {
		return prodAdName;
	}

	public String getBreadcrumbsType() {
		return breadcrumbsType;
	}

	public void setBreadcrumbsType(String breadcrumbsType) {
		this.breadcrumbsType = breadcrumbsType;
	}
	
}