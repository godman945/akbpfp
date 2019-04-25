package com.pchome.akbpfp.db.dao.report;

import java.math.BigDecimal;

public class AdOsReportVO {

	private String customerInfoId;		//使用者帳號
	private String searchText = ""; // 搜尋文字
	private String startDate = ""; // 查詢開始日期
	private String endDate = ""; // 查詢結束日期
	private int page = 1; // 第幾頁
	private int pageSize = 10; // 每頁筆數
	private String sortBy = ""; // 排序欄位
	private boolean isDownloadOrIsNotCuttingPagination = false; // 是否為下載，或是否切分頁FLAG
	private int rowCount = 0; // 總計幾筆
	
	private String adPvclkOs;			//裝置作業系統
	private String adPvclkDevice;		//裝置設備
	private String adPvclkDate;			//廣告曝光點擊數日期
	private String adPvclkTime;			//廣告曝光點擊數時間
	private String adPv;				//曝光數
	private String adClk;				//點擊數
	private String adInvalidClk;		//無效點擊次數
	private BigDecimal adPvSum;			//曝光數總和
	private BigDecimal adClkSum;		//點擊數總和
	private String adInvalidClkSum;		//無效點擊次數總和
	private String adPvPrice;			//曝光數費用
	private String adClkPrice;			//點擊數費用
	private String adInvalidClkPrice;	//無效點擊費用
	private String adPvPriceSum;		//曝光數費用總和
	private String adClkPriceSum;		//點擊費用總和
	private String adInvalidClkPriceSum;//無效點擊費用總和
	private String adClkRate;			//點擊率
	private String adClkAvgPrice;		//平均點擊費用
	
	private Double adPriceSum; // 廣告費用總和
	//轉換數
	private BigDecimal convertCount;
	//轉換價值
	private BigDecimal convertPriceCount;
	private Double ctr; // 互動率
	private Double avgCost; // 單次互動費用
	private Double kiloCost; // 千次曝光費用
	private Double convertCTR; // 轉換率
	private Double convertCost; // 平均轉換成本
	private Double convertInvestmentCost; // 廣告投資報酬率
	
	public String getAdPvclkOs() {
		return adPvclkOs;
	}

	public void setAdPvclkOs(String adPvclkOs) {
		this.adPvclkOs = adPvclkOs;
	}

	public String getAdPvclkDevice() {
		return adPvclkDevice;
	}

	public void setAdPvclkDevice(String adPvclkDevice) {
		this.adPvclkDevice = adPvclkDevice;
	}

	public String getAdPvclkDate() {
		return adPvclkDate;
	}

	public void setAdPvclkDate(String adPvclkDate) {
		this.adPvclkDate = adPvclkDate;
	}

	public String getAdPvclkTime() {
		return adPvclkTime;
	}

	public void setAdPvclkTime(String adPvclkTime) {
		this.adPvclkTime = adPvclkTime;
	}

	public String getAdPv() {
		return adPv;
	}

	public void setAdPv(String adPv) {
		this.adPv = adPv;
	}

	public String getAdClk() {
		return adClk;
	}

	public void setAdClk(String adClk) {
		this.adClk = adClk;
	}

	public String getAdInvalidClk() {
		return adInvalidClk;
	}

	public void setAdInvalidClk(String adInvalidClk) {
		this.adInvalidClk = adInvalidClk;
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

	public String getAdInvalidClkSum() {
		return adInvalidClkSum;
	}

	public void setAdInvalidClkSum(String adInvalidClkSum) {
		this.adInvalidClkSum = adInvalidClkSum;
	}

	public String getAdPvPrice() {
		return adPvPrice;
	}

	public void setAdPvPrice(String adPvPrice) {
		this.adPvPrice = adPvPrice;
	}

	public String getAdClkPrice() {
		return adClkPrice;
	}

	public void setAdClkPrice(String adClkPrice) {
		this.adClkPrice = adClkPrice;
	}

	public String getAdInvalidClkPrice() {
		return adInvalidClkPrice;
	}

	public void setAdInvalidClkPrice(String adInvalidClkPrice) {
		this.adInvalidClkPrice = adInvalidClkPrice;
	}

	public String getAdPvPriceSum() {
		return adPvPriceSum;
	}

	public void setAdPvPriceSum(String adPvPriceSum) {
		this.adPvPriceSum = adPvPriceSum;
	}

	public String getAdClkPriceSum() {
		return adClkPriceSum;
	}

	public void setAdClkPriceSum(String adClkPriceSum) {
		this.adClkPriceSum = adClkPriceSum;
	}

	public String getAdInvalidClkPriceSum() {
		return adInvalidClkPriceSum;
	}

	public void setAdInvalidClkPriceSum(String adInvalidClkPriceSum) {
		this.adInvalidClkPriceSum = adInvalidClkPriceSum;
	}

	public String getAdClkRate() {
		return adClkRate;
	}

	public void setAdClkRate(String adClkRate) {
		this.adClkRate = adClkRate;
	}

	public String getAdClkAvgPrice() {
		return adClkAvgPrice;
	}

	public void setAdClkAvgPrice(String adClkAvgPrice) {
		this.adClkAvgPrice = adClkAvgPrice;
	}

	public String getCustomerInfoId() {
		return customerInfoId;
	}

	public void setCustomerInfoId(String customerInfoId) {
		this.customerInfoId = customerInfoId;
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

	public Double getAdPriceSum() {
		return adPriceSum;
	}

	public void setAdPriceSum(Double adPriceSum) {
		this.adPriceSum = adPriceSum;
	}

	public boolean isDownloadOrIsNotCuttingPagination() {
		return isDownloadOrIsNotCuttingPagination;
	}

	public void setDownloadOrIsNotCuttingPagination(boolean isDownloadOrIsNotCuttingPagination) {
		this.isDownloadOrIsNotCuttingPagination = isDownloadOrIsNotCuttingPagination;
	}
	
}