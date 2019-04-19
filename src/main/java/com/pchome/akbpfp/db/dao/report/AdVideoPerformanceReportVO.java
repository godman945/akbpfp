package com.pchome.akbpfp.db.dao.report;

import java.util.Date;

public class AdVideoPerformanceReportVO {
	//報表日期
	private Date reportDate;
	//廣告狀態
	private String adStatus;
	//廣套圖片
	private String adImg;
	//影片名稱
	private String title;
	//分類名稱
	private String adActionName;
	//尺寸寬度
	private String templateProductWidth;
	//尺寸高度
	private String templateProductHeight;
	//影片網址
	private String videoUrl;
	//影片連結
	private String adLinkUrl;
	//計價方式
	private String adPriceType;
	//裝置
	private String adPvClkDevice;
	//曝光數總數
	private String adPvSum;
	//收視數總數
	private String adViewSum;
	//收視率
	private String adViewRatings;
	//單次收視費用
	private String singleAdViewCost;
	//千次曝光費用
	private String thousandsCost;
	//費用總數
	private String costSum;
	//影片播放進度 25%總數
	private String adVideoProcess25Sum;
	//影片播放進度 50%總數
	private String adVideoProcess50Sum;
	//影片播放進度 75%總數
	private String adVideoProcess75Sum;
	//影片播放進度 100%總數
	private String adVideoProcess100Sum;
	//影片完整播放率
	private String adVideoProcess100Ratings;
	//點選次數總數
	private String adClkSum;
	//收視人數總數(不重複)
	private String adVideoUniqSum;
	//聲音開啟次數總數
	private String adVideoMusicSum;
	//重播次數總數
	private String adVideoReplaySum;
	//影片秒數
	private String adVideoSec;
	//總筆數
	private int totalSize;
	//互動率
	private String engagementRate;
	//廣告序號
	private String adSeq;
	//裝置
	private String device;
	
	public Date getReportDate() {
		return reportDate;
	}
	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}
	public String getAdStatus() {
		return adStatus;
	}
	public void setAdStatus(String adStatus) {
		this.adStatus = adStatus;
	}
	public String getAdImg() {
		return adImg;
	}
	public void setAdImg(String adImg) {
		this.adImg = adImg;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTemplateProductWidth() {
		return templateProductWidth;
	}
	public void setTemplateProductWidth(String templateProductWidth) {
		this.templateProductWidth = templateProductWidth;
	}
	public String getTemplateProductHeight() {
		return templateProductHeight;
	}
	public void setTemplateProductHeight(String templateProductHeight) {
		this.templateProductHeight = templateProductHeight;
	}
	public String getVideoUrl() {
		return videoUrl;
	}
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	public String getAdPriceType() {
		return adPriceType;
	}
	public void setAdPriceType(String adPriceType) {
		this.adPriceType = adPriceType;
	}
	public String getAdPvClkDevice() {
		return adPvClkDevice;
	}
	public void setAdPvClkDevice(String adPvClkDevice) {
		this.adPvClkDevice = adPvClkDevice;
	}
	public String getAdPvSum() {
		return adPvSum;
	}
	public void setAdPvSum(String adPvSum) {
		this.adPvSum = adPvSum;
	}
	
	public String getAdViewSum() {
		return adViewSum;
	}
	public void setAdViewSum(String adViewSum) {
		this.adViewSum = adViewSum;
	}
	public String getAdViewRatings() {
		return adViewRatings;
	}
	public void setAdViewRatings(String adViewRatings) {
		this.adViewRatings = adViewRatings;
	}
	public String getSingleAdViewCost() {
		return singleAdViewCost;
	}
	public void setSingleAdViewCost(String singleAdViewCost) {
		this.singleAdViewCost = singleAdViewCost;
	}
	public String getThousandsCost() {
		return thousandsCost;
	}
	public void setThousandsCost(String thousandsCost) {
		this.thousandsCost = thousandsCost;
	}
	public String getCostSum() {
		return costSum;
	}
	public void setCostSum(String costSum) {
		this.costSum = costSum;
	}
	public String getAdVideoProcess25Sum() {
		return adVideoProcess25Sum;
	}
	public void setAdVideoProcess25Sum(String adVideoProcess25Sum) {
		this.adVideoProcess25Sum = adVideoProcess25Sum;
	}
	public String getAdVideoProcess50Sum() {
		return adVideoProcess50Sum;
	}
	public void setAdVideoProcess50Sum(String adVideoProcess50Sum) {
		this.adVideoProcess50Sum = adVideoProcess50Sum;
	}
	public String getAdVideoProcess75Sum() {
		return adVideoProcess75Sum;
	}
	public void setAdVideoProcess75Sum(String adVideoProcess75Sum) {
		this.adVideoProcess75Sum = adVideoProcess75Sum;
	}
	public String getAdVideoProcess100Sum() {
		return adVideoProcess100Sum;
	}
	public void setAdVideoProcess100Sum(String adVideoProcess100Sum) {
		this.adVideoProcess100Sum = adVideoProcess100Sum;
	}
	public String getAdVideoProcess100Ratings() {
		return adVideoProcess100Ratings;
	}
	public void setAdVideoProcess100Ratings(String adVideoProcess100Ratings) {
		this.adVideoProcess100Ratings = adVideoProcess100Ratings;
	}
	public String getAdClkSum() {
		return adClkSum;
	}
	public void setAdClkSum(String adClkSum) {
		this.adClkSum = adClkSum;
	}
	public String getAdVideoUniqSum() {
		return adVideoUniqSum;
	}
	public void setAdVideoUniqSum(String adVideoUniqSum) {
		this.adVideoUniqSum = adVideoUniqSum;
	}
	public String getAdVideoMusicSum() {
		return adVideoMusicSum;
	}
	public void setAdVideoMusicSum(String adVideoMusicSum) {
		this.adVideoMusicSum = adVideoMusicSum;
	}
	public String getAdVideoReplaySum() {
		return adVideoReplaySum;
	}
	public void setAdVideoReplaySum(String adVideoReplaySum) {
		this.adVideoReplaySum = adVideoReplaySum;
	}
	public String getAdLinkUrl() {
		return adLinkUrl;
	}
	public void setAdLinkUrl(String adLinkUrl) {
		this.adLinkUrl = adLinkUrl;
	}
	public String getAdActionName() {
		return adActionName;
	}
	public void setAdActionName(String adActionName) {
		this.adActionName = adActionName;
	}
	public String getAdVideoSec() {
		return adVideoSec;
	}
	public void setAdVideoSec(String adVideoSec) {
		this.adVideoSec = adVideoSec;
	}
	public int getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}
	public String getEngagementRate() {
		return engagementRate;
	}
	public void setEngagementRate(String engagementRate) {
		this.engagementRate = engagementRate;
	}
	public String getAdSeq() {
		return adSeq;
	}
	public void setAdSeq(String adSeq) {
		this.adSeq = adSeq;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	
}
