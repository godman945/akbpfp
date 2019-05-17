package com.pchome.akbpfp.db.dao.report;

import java.math.BigDecimal;
import java.util.Date;

public class AdCampaginReportVO {

	private String customerInfoId;
	private String searchText = ""; // 搜尋文字
	private String startDate = ""; // 查詢開始日期
	private String endDate = ""; // 查詢結束日期
	private int page = 1; // 第幾頁
	private int pageSize = 10; // 每頁筆數
	private String whereMap = ""; // sql篩選條件
	private String sortBy = ""; // 排序欄位
	private boolean isDownloadOrIsNotCuttingPagination = false; // 是否為下載，或用來當SQL是否切分頁FLAG
	
	private Date reportDate; //報表日期
	
	private boolean adStatusOnOff = false; // 狀態on或off，預設off
	private String adStatusName; // 狀態中文，產excel用
	private String adActionName; // 廣告活動
	private String adType; // 廣告播放類型
	private String adOperatingRule; // 廣告樣式
	private String adActionStartDate; // 走期開始日
	private String adActionEndDate; // 走期結束日
	private String adDevice; // 裝置
	private Double adActionMaxPriceAvg;// 每日預算
	private BigDecimal adPvSum; // 廣告PV總和(曝光數)
	private BigDecimal adClkSum; // 廣告Click總和(互動數)
	private Double ctr; // 互動率
	private Double avgCost; // 單次互動費用
	private Double kiloCost; // 千次曝光費用
	private Double adPriceSum; // 廣告價格總和(費用)
	private BigDecimal convertCount; // 轉換數
	private Double convertCTR; // 轉換率
	private BigDecimal convertPriceCount; // 轉換價值
	private Double convertCost; // 平均轉換成本
	private Double convertInvestmentCost; // 廣告投資報酬率
	private int rowCount = 0; // 總計幾筆

	public String getCustomerInfoId() {
		return customerInfoId;
	}

	public void setCustomerInfoId(String customerInfoId) {
		this.customerInfoId = customerInfoId;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
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

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getWhereMap() {
		return whereMap;
	}

	public void setWhereMap(String whereMap) {
		this.whereMap = whereMap;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public boolean isDownloadOrIsNotCuttingPagination() {
		return isDownloadOrIsNotCuttingPagination;
	}

	public void setDownloadOrIsNotCuttingPagination(boolean isDownloadOrIsNotCuttingPagination) {
		this.isDownloadOrIsNotCuttingPagination = isDownloadOrIsNotCuttingPagination;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public boolean isAdStatusOnOff() {
		return adStatusOnOff;
	}

	public void setAdStatusOnOff(boolean adStatusOnOff) {
		this.adStatusOnOff = adStatusOnOff;
	}

	public String getAdStatusName() {
		return adStatusName;
	}

	public void setAdStatusName(String adStatusName) {
		this.adStatusName = adStatusName;
	}

	public String getAdActionName() {
		return adActionName;
	}

	public void setAdActionName(String adActionName) {
		this.adActionName = adActionName;
	}

	public String getAdType() {
		return adType;
	}

	public void setAdType(String adType) {
		this.adType = adType;
	}

	public String getAdOperatingRule() {
		return adOperatingRule;
	}

	public void setAdOperatingRule(String adOperatingRule) {
		this.adOperatingRule = adOperatingRule;
	}

	public String getAdDevice() {
		return adDevice;
	}

	public void setAdDevice(String adDevice) {
		this.adDevice = adDevice;
	}

	public BigDecimal getAdPvSum() {
		return adPvSum;
	}

	public void setAdPvSum(BigDecimal adPvSum) {
		this.adPvSum = adPvSum;
	}

	public BigDecimal getAdClkSum() {
		return adClkSum;
	}

	public void setAdClkSum(BigDecimal adClkSum) {
		this.adClkSum = adClkSum;
	}

	public Double getCtr() {
		return ctr;
	}

	public void setCtr(Double ctr) {
		this.ctr = ctr;
	}

	public Double getAvgCost() {
		return avgCost;
	}

	public void setAvgCost(Double avgCost) {
		this.avgCost = avgCost;
	}

	public Double getKiloCost() {
		return kiloCost;
	}

	public void setKiloCost(Double kiloCost) {
		this.kiloCost = kiloCost;
	}

	public Double getAdPriceSum() {
		return adPriceSum;
	}

	public void setAdPriceSum(Double adPriceSum) {
		this.adPriceSum = adPriceSum;
	}

	public BigDecimal getConvertCount() {
		return convertCount;
	}

	public void setConvertCount(BigDecimal convertCount) {
		this.convertCount = convertCount;
	}

	public Double getConvertCTR() {
		return convertCTR;
	}

	public void setConvertCTR(Double convertCTR) {
		this.convertCTR = convertCTR;
	}

	public BigDecimal getConvertPriceCount() {
		return convertPriceCount;
	}

	public void setConvertPriceCount(BigDecimal convertPriceCount) {
		this.convertPriceCount = convertPriceCount;
	}

	public Double getConvertCost() {
		return convertCost;
	}

	public void setConvertCost(Double convertCost) {
		this.convertCost = convertCost;
	}

	public Double getConvertInvestmentCost() {
		return convertInvestmentCost;
	}

	public void setConvertInvestmentCost(Double convertInvestmentCost) {
		this.convertInvestmentCost = convertInvestmentCost;
	}

	public String getAdActionStartDate() {
		return adActionStartDate;
	}

	public void setAdActionStartDate(String adActionStartDate) {
		this.adActionStartDate = adActionStartDate;
	}

	public String getAdActionEndDate() {
		return adActionEndDate;
	}

	public void setAdActionEndDate(String adActionEndDate) {
		this.adActionEndDate = adActionEndDate;
	}

	public Double getAdActionMaxPriceAvg() {
		return adActionMaxPriceAvg;
	}

	public void setAdActionMaxPriceAvg(Double adActionMaxPriceAvg) {
		this.adActionMaxPriceAvg = adActionMaxPriceAvg;
	}

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

}