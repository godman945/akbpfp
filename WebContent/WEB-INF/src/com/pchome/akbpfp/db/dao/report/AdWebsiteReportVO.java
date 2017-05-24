package com.pchome.akbpfp.db.dao.report;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class AdWebsiteReportVO {

private Date reportDate; //報表日期
	
	private String websiteCategoryCode;		//網站類型代號
	private String websiteCategoryName;		//網站類型名稱
	
	private String adGroupSeq; //廣告群組序號
	private String adActionSeq; //廣告活動序號

	private BigDecimal adPvSum; //廣告PV總和
	private BigDecimal adClkSum; //廣告Click總和
	private Double adPriceSum; //廣告價格總和
	private BigDecimal adInvClkSum; //廣告無效點擊總和
	private BigInteger count; //資料筆數(用於計算平均每日花費上限)
	private String adDevice; //裝置
	private String adType;	 //廣告類型

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}


	public String getWebsiteCategoryCode() {
		return websiteCategoryCode;
	}

	public void setWebsiteCategoryCode(String websiteCategoryCode) {
		this.websiteCategoryCode = websiteCategoryCode;
	}

	public String getWebsiteCategoryName() {
		return websiteCategoryName;
	}

	public void setWebsiteCategoryName(String websiteCategoryName) {
		this.websiteCategoryName = websiteCategoryName;
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
	
}