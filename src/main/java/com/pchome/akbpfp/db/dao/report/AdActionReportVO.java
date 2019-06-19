package com.pchome.akbpfp.db.dao.report;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class AdActionReportVO {

	private String customerInfoId;
	private String searchText = ""; // 搜尋文字
	private String startDate = ""; // 查詢開始日期
	private String endDate = ""; // 查詢結束日期
	private int page = 1; // 第幾頁
	private int pageSize = 10; // 每頁筆數
	private String whereMap = ""; // sql篩選條件
	private String sortBy = ""; // 排序欄位
	private boolean isDownloadOrIsNotCuttingPagination = false; // 是否為下載，或用來當SQL是否切分頁FLAG
	private String charType = ""; // 圖表度量
	
	private Date reportDate; //報表日期

	private String adActionSeq; //廣告活動序號

	private BigDecimal adPvSum; // 廣告PV總和
	private BigDecimal adClkSum; // 廣告Click總和
	private Double adPriceSum; // 廣告價格總和
	private BigDecimal adInvClkSum; // 廣告無效點擊總和
	private Double adActionMaxPriceSum; // 每日花費上限總和
	private BigInteger count; // 資料筆數(用於計算平均每日花費上限)
	private String adDevice; // 裝置
	private String adType;
	private String adOperatingRule; // 廣告樣式
	private String adClkPriceType; // 廣告計費方式
	private String adOperatingRuleDesc;
	private BigDecimal convertCount; // 轉換數
	private BigDecimal convertPriceCount; // 轉換價值
	
	private Double ctr; // 互動率
	private Double avgCost; // 單次互動費用
	private Double kiloCost; // 千次曝光費用
	private Double convertCTR; // 轉換率
	private Double convertCost; // 平均轉換成本
	private Double convertInvestmentCost; // 廣告投資報酬率
	private int rowCount = 0; // 總計幾筆
	
	public String getAdActionSeq() {
		return adActionSeq;
	}

	public void setAdActionSeq(String adActionSeq) {
		this.adActionSeq = adActionSeq;
	}

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
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

	public Double getAdPriceSum() {
		return adPriceSum;
	}

	public void setAdPriceSum(Double adPriceSum) {
		this.adPriceSum = adPriceSum;
	}

	public BigDecimal getAdInvClkSum() {
		return adInvClkSum;
	}

	public void setAdInvClkSum(BigDecimal adInvClkSum) {
		this.adInvClkSum = adInvClkSum;
	}

	public Double getAdActionMaxPriceSum() {
		return adActionMaxPriceSum;
	}

	public void setAdActionMaxPriceSum(Double adActionMaxPriceSum) {
		this.adActionMaxPriceSum = adActionMaxPriceSum;
	}

	public BigInteger getCount() {
		return count;
	}

	public void setCount(BigInteger count) {
		this.count = count;
	}

	public String getAdDevice() {
		return adDevice;
	}

	public void setAdDevice(String adDevice) {
		this.adDevice = adDevice;
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

	public String getAdClkPriceType() {
		return adClkPriceType;
	}

	public void setAdClkPriceType(String adClkPriceType) {
		this.adClkPriceType = adClkPriceType;
	}

	public String getAdOperatingRuleDesc() {
		return adOperatingRuleDesc;
	}

	public void setAdOperatingRuleDesc(String adOperatingRuleDesc) {
		this.adOperatingRuleDesc = adOperatingRuleDesc;
	}

	public BigDecimal getConvertCount() {
		return convertCount;
	}

	public void setConvertCount(BigDecimal convertCount) {
		this.convertCount = convertCount;
	}

	public BigDecimal getConvertPriceCount() {
		return convertPriceCount;
	}

	public void setConvertPriceCount(BigDecimal convertPriceCount) {
		this.convertPriceCount = convertPriceCount;
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

	public String getCustomerInfoId() {
		return customerInfoId;
	}

	public void setCustomerInfoId(String customerInfoId) {
		this.customerInfoId = customerInfoId;
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

	public Double getConvertCTR() {
		return convertCTR;
	}

	public void setConvertCTR(Double convertCTR) {
		this.convertCTR = convertCTR;
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

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public boolean isDownloadOrIsNotCuttingPagination() {
		return isDownloadOrIsNotCuttingPagination;
	}

	public void setDownloadOrIsNotCuttingPagination(boolean isDownloadOrIsNotCuttingPagination) {
		this.isDownloadOrIsNotCuttingPagination = isDownloadOrIsNotCuttingPagination;
	}

	public String getCharType() {
		return charType;
	}

	public void setCharType(String charType) {
		this.charType = charType;
	}

}