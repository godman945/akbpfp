package com.pchome.akbpfp.db.dao.report;

import java.math.BigDecimal;

public class AdVideoPerformanceReportVO {
	
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
	
	private boolean adStatusOnOff = false; // 狀態on或off，預設off
	
	// 影音廣告內容
	private String title; // 影片名稱
	private String templateProductWidth; // 尺寸寬度
	private String templateProductHeight; // 尺寸高度
	private String adVideoSec; // 影片秒數
	private String adLinkUrl; // 廣告連結
    // 預覽畫面要再用參數
	private String adImg; // 廣套圖片
	private String videoUrl; // 影片網址
	
	private String adActionName; // 廣告活動名稱
	private String adGroupName; // 廣告分類名稱
	private String adClkPriceType; // 廣告計費方式
	private String adDevice; // 裝置
	private BigDecimal adPvSum; // 曝光數
	private BigDecimal adViewSum; // 收視數總數
	private Double adViewRatings; // 收視率
	private Double singleAdViewCost; // 單次收視費用
	private Double kiloCost; // 千次曝光費用
	private Double adPriceSum; // 費用
	private BigDecimal adVideoProcess25Sum; // 影片播放進度 25%總數
	private BigDecimal adVideoProcess50Sum; // 影片播放進度 50%總數
	private BigDecimal adVideoProcess75Sum; // 影片播放進度 75%總數
	private BigDecimal adVideoProcess100Sum; // 影片播放進度 100%總數
	private Double adVideoProcess100Ratings; // 影片完整播放率
	private BigDecimal adClkSum; // 點選次數總數
	private BigDecimal adVideoUniqSum; // 收視人數總數(不重複)
	private BigDecimal adVideoMusicSum; // 聲音開啟次數總數
	private BigDecimal adVideoReplaySum; // 重播次數總數
	
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

	public String getCharType() {
		return charType;
	}

	public void setCharType(String charType) {
		this.charType = charType;
	}

	public boolean isAdStatusOnOff() {
		return adStatusOnOff;
	}

	public void setAdStatusOnOff(boolean adStatusOnOff) {
		this.adStatusOnOff = adStatusOnOff;
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

	public String getAdVideoSec() {
		return adVideoSec;
	}

	public void setAdVideoSec(String adVideoSec) {
		this.adVideoSec = adVideoSec;
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

	public String getAdGroupName() {
		return adGroupName;
	}

	public void setAdGroupName(String adGroupName) {
		this.adGroupName = adGroupName;
	}

	public String getAdClkPriceType() {
		return adClkPriceType;
	}

	public void setAdClkPriceType(String adClkPriceType) {
		this.adClkPriceType = adClkPriceType;
	}

	public String getAdDevice() {
		return adDevice;
	}

	public void setAdDevice(String adDevice) {
		this.adDevice = adDevice;
	}

	public BigDecimal getAdPvSum() {
		return adPvSum;
	}

	public void setAdPvSum(BigDecimal adPvSum) {
		this.adPvSum = adPvSum;
	}

	public BigDecimal getAdViewSum() {
		return adViewSum;
	}

	public void setAdViewSum(BigDecimal adViewSum) {
		this.adViewSum = adViewSum;
	}

	public Double getAdViewRatings() {
		return adViewRatings;
	}

	public void setAdViewRatings(Double adViewRatings) {
		this.adViewRatings = adViewRatings;
	}

	public Double getSingleAdViewCost() {
		return singleAdViewCost;
	}

	public void setSingleAdViewCost(Double singleAdViewCost) {
		this.singleAdViewCost = singleAdViewCost;
	}

	public Double getKiloCost() {
		return kiloCost;
	}

	public void setKiloCost(Double kiloCost) {
		this.kiloCost = kiloCost;
	}

	public Double getAdPriceSum() {
		return adPriceSum;
	}

	public void setAdPriceSum(Double adPriceSum) {
		this.adPriceSum = adPriceSum;
	}

	public BigDecimal getAdVideoProcess25Sum() {
		return adVideoProcess25Sum;
	}

	public void setAdVideoProcess25Sum(BigDecimal adVideoProcess25Sum) {
		this.adVideoProcess25Sum = adVideoProcess25Sum;
	}

	public BigDecimal getAdVideoProcess50Sum() {
		return adVideoProcess50Sum;
	}

	public void setAdVideoProcess50Sum(BigDecimal adVideoProcess50Sum) {
		this.adVideoProcess50Sum = adVideoProcess50Sum;
	}

	public BigDecimal getAdVideoProcess75Sum() {
		return adVideoProcess75Sum;
	}

	public void setAdVideoProcess75Sum(BigDecimal adVideoProcess75Sum) {
		this.adVideoProcess75Sum = adVideoProcess75Sum;
	}

	public BigDecimal getAdVideoProcess100Sum() {
		return adVideoProcess100Sum;
	}

	public void setAdVideoProcess100Sum(BigDecimal adVideoProcess100Sum) {
		this.adVideoProcess100Sum = adVideoProcess100Sum;
	}

	public Double getAdVideoProcess100Ratings() {
		return adVideoProcess100Ratings;
	}

	public void setAdVideoProcess100Ratings(Double adVideoProcess100Ratings) {
		this.adVideoProcess100Ratings = adVideoProcess100Ratings;
	}

	public BigDecimal getAdClkSum() {
		return adClkSum;
	}

	public void setAdClkSum(BigDecimal adClkSum) {
		this.adClkSum = adClkSum;
	}

	public BigDecimal getAdVideoUniqSum() {
		return adVideoUniqSum;
	}

	public void setAdVideoUniqSum(BigDecimal adVideoUniqSum) {
		this.adVideoUniqSum = adVideoUniqSum;
	}

	public BigDecimal getAdVideoMusicSum() {
		return adVideoMusicSum;
	}

	public void setAdVideoMusicSum(BigDecimal adVideoMusicSum) {
		this.adVideoMusicSum = adVideoMusicSum;
	}

	public BigDecimal getAdVideoReplaySum() {
		return adVideoReplaySum;
	}

	public void setAdVideoReplaySum(BigDecimal adVideoReplaySum) {
		this.adVideoReplaySum = adVideoReplaySum;
	}

	public String getAdImg() {
		return adImg;
	}

	public void setAdImg(String adImg) {
		this.adImg = adImg;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

}