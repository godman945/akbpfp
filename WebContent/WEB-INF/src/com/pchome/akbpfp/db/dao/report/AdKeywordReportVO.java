package com.pchome.akbpfp.db.dao.report;

public class AdKeywordReportVO {

	private String reportDate; //報表日期

	private String kwSeq; //關鍵字序號
	private String keyward; //關鍵字
	private String adType; //廣告方式
	private String adStatus; //廣告狀態
	private String adRankAvg; //廣泛比對排名
	private String adPhrRankAvg; //詞組比對排名
	private String adPreRankAvg; //精準比對排名
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
	
	private String customerInfoId; // 帳號
	private String kwDevice; // 裝置
	private String kwAdType; //廣告類型
	private String dataTotal;	//總比數
	
	//廣泛比對
	private String kwOpen;	//比對方式開啟狀態
	private String kwPvSum; //關鍵字PV總和
	private String kwClkSum; //關鍵字點擊總和
	private String kwCtrSum; //關鍵字點擊率總和
	private String kwPriceSum; //關鍵字價格總和
	private String kwPriceAvgSum; //關鍵字平均點選費用總和
	private String kwInvClkSum; //關鍵字無效點擊總和
	
	//詞組比對
	private String kwPhrOpen;	//比對方式開啟狀態
	private String kwPhrPvSum; //關鍵字PV總和
	private String kwPhrClkSum; //關鍵字點擊總和
	private String kwPhrCtrSum; //關鍵字點擊率總和
	private String kwPhrPriceSum; //關鍵字價格總和
	private String kwPhrPriceAvgSum; //關鍵字平均點選費用總和
	private String kwPhrInvClkSum; //關鍵字無效點擊總和
	
	//精準比對
	private String kwPreOpen;	//比對方式開啟狀態
	private String kwPrePvSum; //關鍵字PV總和
	private String kwPreClkSum; //關鍵字點擊總和
	private String kwPreCtrSum; //關鍵字點擊率總和
	private String kwPrePriceSum; //關鍵字價格總和
	private String kwPrePriceAvgSum; //關鍵字平均點選費用總和
	private String kwPreInvClkSum; //關鍵字無效點擊總和
	
	//總計
	private String kwPvTotal; //關鍵字PV總和
	private String kwClkTotal; //關鍵字點擊總和
	private String kwCtrTotal; //關鍵字點擊率總和
	private String kwPriceTotal; //關鍵字價格總和
	private String kwPriceAvgTotal; //關鍵字平均點選費用總和
	private String kwInvClkTotal; //關鍵字無效點擊總和

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

	public String getKwPhrPvSum() {
		return kwPhrPvSum;
	}

	public void setKwPhrPvSum(String kwPhrPvSum) {
		this.kwPhrPvSum = kwPhrPvSum;
	}

	public String getKwPhrClkSum() {
		return kwPhrClkSum;
	}

	public void setKwPhrClkSum(String kwPhrClkSum) {
		this.kwPhrClkSum = kwPhrClkSum;
	}

	public String getKwPhrPriceSum() {
		return kwPhrPriceSum;
	}

	public void setKwPhrPriceSum(String kwPhrPriceSum) {
		this.kwPhrPriceSum = kwPhrPriceSum;
	}

	public String getKwPhrInvClkSum() {
		return kwPhrInvClkSum;
	}

	public void setKwPhrInvClkSum(String kwPhrInvClkSum) {
		this.kwPhrInvClkSum = kwPhrInvClkSum;
	}

	public String getKwPrePvSum() {
		return kwPrePvSum;
	}

	public void setKwPrePvSum(String kwPrePvSum) {
		this.kwPrePvSum = kwPrePvSum;
	}

	public String getKwPreClkSum() {
		return kwPreClkSum;
	}

	public void setKwPreClkSum(String kwPreClkSum) {
		this.kwPreClkSum = kwPreClkSum;
	}

	public String getKwPrePriceSum() {
		return kwPrePriceSum;
	}

	public void setKwPrePriceSum(String kwPrePriceSum) {
		this.kwPrePriceSum = kwPrePriceSum;
	}

	public String getKwPreInvClkSum() {
		return kwPreInvClkSum;
	}

	public void setKwPreInvClkSum(String kwPreInvClkSum) {
		this.kwPreInvClkSum = kwPreInvClkSum;
	}

	public String getKwPvTotal() {
		return kwPvTotal;
	}

	public void setKwPvTotal(String kwPvTotal) {
		this.kwPvTotal = kwPvTotal;
	}

	public String getKwClkTotal() {
		return kwClkTotal;
	}

	public void setKwClkTotal(String kwClkTotal) {
		this.kwClkTotal = kwClkTotal;
	}

	public String getKwPriceTotal() {
		return kwPriceTotal;
	}

	public void setKwPriceTotal(String kwPriceTotal) {
		this.kwPriceTotal = kwPriceTotal;
	}

	public String getKwInvClkTotal() {
		return kwInvClkTotal;
	}

	public void setKwInvClkTotal(String kwInvClkTotal) {
		this.kwInvClkTotal = kwInvClkTotal;
	}

	public String getKwOpen() {
		return kwOpen;
	}

	public void setKwOpen(String kwOpen) {
		this.kwOpen = kwOpen;
	}

	public String getKwCtrSum() {
		return kwCtrSum;
	}

	public void setKwCtrSum(String kwCtrSum) {
		this.kwCtrSum = kwCtrSum;
	}

	public String getKwPriceAvgSum() {
		return kwPriceAvgSum;
	}

	public void setKwPriceAvgSum(String kwPriceAvgSum) {
		this.kwPriceAvgSum = kwPriceAvgSum;
	}

	public String getKwPhrOpen() {
		return kwPhrOpen;
	}

	public void setKwPhrOpen(String kwPhrOpen) {
		this.kwPhrOpen = kwPhrOpen;
	}

	public String getKwPhrCtrSum() {
		return kwPhrCtrSum;
	}

	public void setKwPhrCtrSum(String kwPhrCtrSum) {
		this.kwPhrCtrSum = kwPhrCtrSum;
	}

	public String getKwPhrPriceAvgSum() {
		return kwPhrPriceAvgSum;
	}

	public void setKwPhrPriceAvgSum(String kwPhrPriceAvgSum) {
		this.kwPhrPriceAvgSum = kwPhrPriceAvgSum;
	}

	public String getKwPreOpen() {
		return kwPreOpen;
	}

	public void setKwPreOpen(String kwPreOpen) {
		this.kwPreOpen = kwPreOpen;
	}

	public String getKwPreCtrSum() {
		return kwPreCtrSum;
	}

	public void setKwPreCtrSum(String kwPreCtrSum) {
		this.kwPreCtrSum = kwPreCtrSum;
	}

	public String getKwPrePriceAvgSum() {
		return kwPrePriceAvgSum;
	}

	public void setKwPrePriceAvgSum(String kwPrePriceAvgSum) {
		this.kwPrePriceAvgSum = kwPrePriceAvgSum;
	}

	public String getKwCtrTotal() {
		return kwCtrTotal;
	}

	public void setKwCtrTotal(String kwCtrTotal) {
		this.kwCtrTotal = kwCtrTotal;
	}

	public String getKwPriceAvgTotal() {
		return kwPriceAvgTotal;
	}

	public void setKwPriceAvgTotal(String kwPriceAvgTotal) {
		this.kwPriceAvgTotal = kwPriceAvgTotal;
	}

	public String getAdPhrRankAvg() {
		return adPhrRankAvg;
	}

	public void setAdPhrRankAvg(String adPhrRankAvg) {
		this.adPhrRankAvg = adPhrRankAvg;
	}

	public String getAdPreRankAvg() {
		return adPreRankAvg;
	}

	public void setAdPreRankAvg(String adPreRankAvg) {
		this.adPreRankAvg = adPreRankAvg;
	}

	public String getAdStatus() {
		return adStatus;
	}

	public void setAdStatus(String adStatus) {
		this.adStatus = adStatus;
	}

	public String getDataTotal() {
		return dataTotal;
	}

	public void setDataTotal(String dataTotal) {
		this.dataTotal = dataTotal;
	}
	
}
