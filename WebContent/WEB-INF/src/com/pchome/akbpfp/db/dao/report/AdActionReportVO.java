package com.pchome.akbpfp.db.dao.report;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class AdActionReportVO {

	private Date reportDate; //報表日期

	private String adActionSeq; //廣告活動序號

	private BigDecimal adPvSum; //廣告PV總和
	private BigDecimal adClkSum; //廣告Click總和
	private Double adPriceSum; //廣告價格總和
	private BigDecimal adInvClkSum; //廣告無效點擊總和
	private Double adActionMaxPriceSum; //每日花費上限總和
	private BigInteger count; //資料筆數(用於計算平均每日花費上限)
	private String adDevice; //裝置

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
}
