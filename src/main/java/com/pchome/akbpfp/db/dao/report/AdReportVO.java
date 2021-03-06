package com.pchome.akbpfp.db.dao.report;

import java.math.BigDecimal;

public class AdReportVO {

	private String reportDate; //報表日期

	private String adSeq; //廣告序號
	private String templateProductSeq; //版型序號
	private String adPreview; //廣告預覽
	private String title;
	private String content = "";
	private String realUrl = "";
	private String showUrl = "";
	private String adStatus; //廣告明細狀態
	private String adStatusDesc;
	private String adGroupSeq; //廣告分類序號
	private String adGroupName; //廣告分類名稱
	private int adGroupStatus; //廣告分類狀態

	private String adActionSeq; //廣告活動序號
	private String adActionName; //廣告活動名稱
	private String adActionStatus; //廣告活動狀態
	private String adActionStartDate; //廣告活動走期起
	private String adActionEndDate; //廣告活動走期迄

	private String adPvclkDate; //廣告曝光點擊日期
	private String adPvSum; //廣告PV總和
	private String adClkSum; //廣告Click總和
	private String adPriceSum; //廣告價格總和
	private String adInvClkSum; //廣告無效點擊總和
	private String customerInfoId; //帳號
	private String adDevice;
	private String adType;
	private String adOperatingRuleDesc;		//廣告樣式
	private String adClkPriceType;		//廣告計費方式
	private String adOperatingRule;
	
	private String adClickRatings;
	private String thousandsCost;
	private String singleCost;
	private String img;
	private String adWidth;
	private String adHeight;
	private String adRejectReason;
	//影片長度
	private String adVideoSec;
	//影片網址
	private String adVideoUrl = "";
	//轉換數
	private BigDecimal convertCount;
	//轉換價值
	private BigDecimal convertPriceCount;
	
	public String getAdSeq() {
		return adSeq;
	}

	public void setAdSeq(String adSeq) {
		this.adSeq = adSeq;
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

	public String getAdPvSum() {
		return adPvSum;
	}

	public void setAdPvSum(String adPvSum) {
		this.adPvSum = adPvSum;
	}

	public String getAdClkSum() {
		return adClkSum;
	}

	public void setAdClkSum(String adClkSum) {
		this.adClkSum = adClkSum;
	}

	public String getAdPriceSum() {
		return adPriceSum;
	}

	public void setAdPriceSum(String adPriceSum) {
		this.adPriceSum = adPriceSum;
	}

	public String getTemplateProductSeq() {
		return templateProductSeq;
	}

	public void setTemplateProductSeq(String templateProductSeq) {
		this.templateProductSeq = templateProductSeq;
	}

	public String getAdPreview() {
		return adPreview;
	}

	public void setAdPreview(String adPreview) {
		this.adPreview = adPreview;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRealUrl() {
		return realUrl;
	}

	public void setRealUrl(String realUrl) {
		this.realUrl = realUrl;
	}

	public String getShowUrl() {
		return showUrl;
	}

	public void setShowUrl(String showUrl) {
		this.showUrl = showUrl;
	}

	public String getAdStatus() {
		return adStatus;
	}

	public void setAdStatus(String adStatus) {
		this.adStatus = adStatus;
	}


	public int getAdGroupStatus() {
		return adGroupStatus;
	}

	public void setAdGroupStatus(int adGroupStatus) {
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

	public String getAdPvclkDate() {
		return adPvclkDate;
	}

	public void setAdPvclkDate(String adPvclkDate) {
		this.adPvclkDate = adPvclkDate;
	}

	public String getAdInvClkSum() {
		return adInvClkSum;
	}

	public void setAdInvClkSum(String adInvClkSum) {
		this.adInvClkSum = adInvClkSum;
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

	public String getCustomerInfoId() {
		return customerInfoId;
	}

	public void setCustomerInfoId(String customerInfoId) {
		this.customerInfoId = customerInfoId;
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

	public String getAdStatusDesc() {
		return adStatusDesc;
	}

	public void setAdStatusDesc(String adStatusDesc) {
		this.adStatusDesc = adStatusDesc;
	}

	public String getAdVideoUrl() {
		return adVideoUrl;
	}

	public void setAdVideoUrl(String adVideoUrl) {
		this.adVideoUrl = adVideoUrl;
	}

	public String getAdVideoSec() {
		return adVideoSec;
	}

	public void setAdVideoSec(String adVideoSec) {
		this.adVideoSec = adVideoSec;
	}

	public String getAdOperatingRuleDesc() {
		return adOperatingRuleDesc;
	}

	public void setAdOperatingRuleDesc(String adOperatingRuleDesc) {
		this.adOperatingRuleDesc = adOperatingRuleDesc;
	}

	public String getAdClickRatings() {
		return adClickRatings;
	}

	public void setAdClickRatings(String adClickRatings) {
		this.adClickRatings = adClickRatings;
	}

	public String getThousandsCost() {
		return thousandsCost;
	}

	public void setThousandsCost(String thousandsCost) {
		this.thousandsCost = thousandsCost;
	}

	public String getSingleCost() {
		return singleCost;
	}

	public void setSingleCost(String singleCost) {
		this.singleCost = singleCost;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getAdWidth() {
		return adWidth;
	}

	public void setAdWidth(String adWidth) {
		this.adWidth = adWidth;
	}

	public String getAdHeight() {
		return adHeight;
	}

	public void setAdHeight(String adHeight) {
		this.adHeight = adHeight;
	}

	public String getAdRejectReason() {
		return adRejectReason;
	}

	public void setAdRejectReason(String adRejectReason) {
		this.adRejectReason = adRejectReason;
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
}
