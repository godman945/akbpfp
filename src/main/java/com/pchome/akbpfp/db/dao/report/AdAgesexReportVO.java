package com.pchome.akbpfp.db.dao.report;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class AdAgesexReportVO {

	private String viewType = "age"; // 預設年齡 (年齡:age、性別:sex)
	private String customerInfoId; // 使用者帳號
	private String searchText = ""; // 搜尋文字
	private String startDate = ""; // 查詢開始日期
	private String endDate = ""; // 查詢結束日期
	private int page = 1; // 第幾頁
	private int pageSize = 10; // 每頁筆數
	private String whereMap = ""; // sql篩選條件
	private String sortBy = ""; // 排序欄位
	private boolean isDownloadOrIsNotCuttingPagination = false; // 是否為下載，或是否切分頁FLAG
	private int rowCount = 0; // 總計幾筆
	private String charType = ""; // 圖表度量
	
	private Date reportDate; //報表日期
	
	private String ageCode; // 年齡代碼
	private String age; // 年齡中文
	private String sexCode; // 性別代碼
	private String sex; // 性別中文
	
	private String adGroupSeq; //廣告群組序號
	private String adActionSeq; //廣告活動序號

	private BigDecimal adInvClkSum; //廣告無效點擊總和
	private BigInteger count; //資料筆數(用於計算平均每日花費上限)
	
	private boolean adStatusOnOff = false; // 狀態on或off，預設off
	private String adStatusName; // 狀態中文，產excel用
	private String adActionName; // 廣告活動
	private String adGroupName; // 廣告分類
	private String adType; // 廣告播放類型
	private String adOperatingRule; // 廣告樣式
	private String adClkPriceType; // 廣告計費方式
	private String adDevice; // 裝置
	private BigDecimal adPvSum; // 廣告PV總和(曝光數)
	private BigDecimal adClkSum; // 廣告Click總和(互動數)
	private Double ctr; // 互動率
	private Double avgCost; // 單次互動費用
	private Double kiloCost; // 千次曝光費用
	private Double adPriceSum; // 廣告價格總和(費用)
	private BigDecimal convertCount; //轉換數
	private BigDecimal convertPriceCount; //轉換價值
	private Double convertCTR; // 轉換率
	private Double convertCost; // 平均轉換成本
	private Double convertInvestmentCost; // 廣告投資報酬率
	
	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAdGroupSeq() {
		return adGroupSeq;
	}

	public void setAdGroupSeq(String adGroupSeq) {
		this.adGroupSeq = adGroupSeq;
	}

	public String getAdActionSeq() {
		return adActionSeq;
	}

	public void setAdActionSeq(String adActionSeq) {
		this.adActionSeq = adActionSeq;
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

	public String getViewType() {
		return viewType;
	}

	public void setViewType(String viewType) {
		this.viewType = viewType;
	}

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

	public String getAdGroupName() {
		return adGroupName;
	}

	public void setAdGroupName(String adGroupName) {
		this.adGroupName = adGroupName;
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

	public String getAgeCode() {
		return ageCode;
	}

	public void setAgeCode(String ageCode) {
		this.ageCode = ageCode;
	}

	public String getSexCode() {
		return sexCode;
	}

	public void setSexCode(String sexCode) {
		this.sexCode = sexCode;
	}

	public String getCharType() {
		return charType;
	}

	public void setCharType(String charType) {
		this.charType = charType;
	}
	
}