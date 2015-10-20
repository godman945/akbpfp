package com.pchome.akbpfp.db.dao.report;

public class AdKeywordReportVO {

	private String reportDate; //報表日期

	private String kwSeq; //關鍵字序號
	private String keyward; //關鍵字
	private String adType; //廣告方式
	private String adRankAvg; //排名
	private String kwStatus; //關鍵字狀態
	private String kwPvclkDate; //關鍵字曝光點擊日期

	private String adGroupSeq; //廣告分類序號
	private String adGroupName; //廣告分類名稱
	private String adGroupStatus; //廣告分類狀態
	private String adGroupSearchPrice; //找東西廣告出價
	private String adGroupChannelPrice; //PChome頻道廣告出價

	private String adActionSeq; //廣告活動序號
	private String adActionName; //廣告活動名稱
	private String adActionStatus; //廣告活動狀態
	private String adActionStartDate; //廣告活動走期起
	private String adActionEndDate; //廣告活動走期迄

	private String adKeywordSeq; //關鍵字序號
	private String adKeyword; //關鍵字
	private String adKeywordStatus; //關鍵字狀態
	
	private String kwPvSum; //關鍵字PV總和
	private String kwClkSum; //關鍵字點擊總和
	private String kwPriceSum; //關鍵字價格總和
	private String kwInvClkSum; //關鍵字無效點擊總和
	private String customerInfoId; // 帳號
	private String kwDevice; // 裝置
	private String kwAdType; //廣告類型

	public String getKwSeq() {
		return kwSeq;
	}

	public void setKwSeq(String kwSeq) {
		this.kwSeq = kwSeq;
	}

	public String getKeyward() {
		return keyward;
	}

	public void setKeyward(String keyward) {
		this.keyward = keyward;
	}

	public String getKwPvSum() {
		return kwPvSum;
	}

	public void setKwPvSum(String kwPvSum) {
		this.kwPvSum = kwPvSum;
	}

	public String getKwClkSum() {
		return kwClkSum;
	}

	public void setKwClkSum(String kwClkSum) {
		this.kwClkSum = kwClkSum;
	}

	public String getKwPriceSum() {
		return kwPriceSum;
	}

	public void setKwPriceSum(String kwPriceSum) {
		this.kwPriceSum = kwPriceSum;
	}

	public String getReportDate() {
		return reportDate;
	}

	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}

	public String getAdGroupName() {
		return adGroupName;
	}

	public void setAdGroupName(String adGroupName) {
		this.adGroupName = adGroupName;
	}

	public String getAdActionName() {
		return adActionName;
	}

	public void setAdActionName(String adActionName) {
		this.adActionName = adActionName;
	}

	public String getAdGroupSearchPrice() {
		return adGroupSearchPrice;
	}

	public void setAdGroupSearchPrice(String adGroupSearchPrice) {
		this.adGroupSearchPrice = adGroupSearchPrice;
	}

	public String getAdGroupChannelPrice() {
		return adGroupChannelPrice;
	}

	public void setAdGroupChannelPrice(String adGroupChannelPrice) {
		this.adGroupChannelPrice = adGroupChannelPrice;
	}

	public String getAdType() {
		return adType;
	}

	public void setAdType(String adType) {
		this.adType = adType;
	}

	public String getAdRankAvg() {
		return adRankAvg;
	}

	public void setAdRankAvg(String adRankAvg) {
		this.adRankAvg = adRankAvg;
	}

	public String getKwStatus() {
		return kwStatus;
	}

	public void setKwStatus(String kwStatus) {
		this.kwStatus = kwStatus;
	}

	public String getAdGroupStatus() {
		return adGroupStatus;
	}

	public void setAdGroupStatus(String adGroupStatus) {
		this.adGroupStatus = adGroupStatus;
	}

	public String getAdActionStatus() {
		return adActionStatus;
	}

	public void setAdActionStatus(String adActionStatus) {
		this.adActionStatus = adActionStatus;
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

	public String getAdKeywordSeq() {
		return adKeywordSeq;
	}

	public void setAdKeywordSeq(String adKeywordSeq) {
		this.adKeywordSeq = adKeywordSeq;
	}

	public String getAdKeyword() {
		return adKeyword;
	}

	public void setAdKeyword(String adKeyword) {
		this.adKeyword = adKeyword;
	}

	public String getAdKeywordStatus() {
		return adKeywordStatus;
	}

	public void setAdKeywordStatus(String adKeywordStatus) {
		this.adKeywordStatus = adKeywordStatus;
	}

	public String getKwInvClkSum() {
		return kwInvClkSum;
	}

	public void setKwInvClkSum(String kwInvClkSum) {
		this.kwInvClkSum = kwInvClkSum;
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

	public String getKwPvclkDate() {
		return kwPvclkDate;
	}

	public void setKwPvclkDate(String kwPvclkDate) {
		this.kwPvclkDate = kwPvclkDate;
	}

	public String getCustomerInfoId() {
		return customerInfoId;
	}

	public void setCustomerInfoId(String customerInfoId) {
		this.customerInfoId = customerInfoId;
	}

	public String getKwDevice() {
		return kwDevice;
	}

	public void setKwDevice(String kwDevice) {
		this.kwDevice = kwDevice;
	}

	public String getKwAdType() {
		return kwAdType;
	}

	public void setKwAdType(String kwAdType) {
		this.kwAdType = kwAdType;
	}
	
}
