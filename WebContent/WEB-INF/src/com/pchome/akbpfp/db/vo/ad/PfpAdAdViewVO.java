package com.pchome.akbpfp.db.vo.ad;

import java.util.List;

import com.pchome.akbpfp.db.pojo.PfpAdDetail;

public class PfpAdAdViewVO {

	private String adActionSeq = "";
	private String adActionName = "";
	private String adType = "";
	private String adGroupSeq = "";
	private String adGroupName = "";
	private String adSeq = "";
	private int adStatus = 0;
	private String adStatusDesc = "";
	private int adPv = 0;
	private int adClk = 0;
	private float adClkRate = 0;
	private float adClkPrice = 0;
	private float adClkPriceAvg = 0;
	private String adHtml = "";
	private String adTemplateNo = "";
	private String adRejectReason = "";
	private int invalidClk = 0 ;

	
	
	
	public String getAdActionSeq() {
		return adActionSeq;
	}
	public void setAdActionSeq(String adActionSeq) {
		this.adActionSeq = adActionSeq;
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
	public String getAdGroupSeq() {
		return adGroupSeq;
	}
	public void setAdGroupSeq(String adGroupSeq) {
		this.adGroupSeq = adGroupSeq;
	}
	public String getAdGroupName() {
		return adGroupName;
	}
	public void setAdGroupName(String adGroupName) {
		this.adGroupName = adGroupName;
	}
	public String getAdSeq() {
		return adSeq;
	}
	public void setAdSeq(String adSeq) {
		this.adSeq = adSeq;
	}
	public int getAdStatus() {
		return adStatus;
	}
	public void setAdStatus(int adStatus) {
		this.adStatus = adStatus;
	}
	public String getAdStatusDesc() {
		return adStatusDesc;
	}
	public void setAdStatusDesc(String adStatusDesc) {
		this.adStatusDesc = adStatusDesc;
	}
	public int getAdPv() {
		return adPv;
	}
	public void setAdPv(int adPv) {
		this.adPv = adPv;
	}
	public int getAdClk() {
		return adClk;
	}
	public void setAdClk(int adClk) {
		this.adClk = adClk;
	}
	public float getAdClkRate() {
		return adClkRate;
	}
	public void setAdClkRate(float adClkRate) {
		this.adClkRate = adClkRate;
	}
	public float getAdClkPrice() {
		return adClkPrice;
	}
	public void setAdClkPrice(float adClkPrice) {
		this.adClkPrice = adClkPrice;
	}
	public float getAdClkPriceAvg() {
		return adClkPriceAvg;
	}
	public void setAdClkPriceAvg(float adClkPriceAvg) {
		this.adClkPriceAvg = adClkPriceAvg;
	}
	public String getAdHtml() {
		return adHtml;
	}
	public void setAdHtml(String adHtml) {
		this.adHtml = adHtml;
	}
	public String getAdTemplateNo() {
		return adTemplateNo;
	}
	public void setAdTemplateNo(String adTemplateNo) {
		this.adTemplateNo = adTemplateNo;
	}
	public String getAdRejectReason() {
		return adRejectReason;
	}
	public void setAdRejectReason(String adRejectReason) {
		this.adRejectReason = adRejectReason;
	}
	public int getInvalidClk() {
		return invalidClk;
	}
	public void setInvalidClk(int invalidClk) {
		this.invalidClk = invalidClk;
	}

	
}
