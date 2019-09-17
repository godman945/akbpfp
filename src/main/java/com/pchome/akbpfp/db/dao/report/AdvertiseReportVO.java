package com.pchome.akbpfp.db.dao.report;

import java.math.BigDecimal;
import java.util.Date;

public class AdvertiseReportVO {

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
	private String adSeq; //廣告序號
	private String adGroupSeq; //廣告群組序號
	private String adActionSeq; //廣告活動序號

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
	private BigDecimal adInvClkSum; // 廣告無效點擊總和 (總廣告成效用)
	private Double avgCost; // 單次互動費用
	private Double kiloCost; // 千次曝光費用
	private Double adPriceSum; // 廣告價格總和(費用)
	private BigDecimal convertCount; // 轉換數
	private BigDecimal convertPriceCount; // 轉換價值
	private Double convertCTR; // 轉換率
	private Double convertCost; // 平均轉換成本
	private Double convertInvestmentCost; // 廣告投資報酬率
	
	// 廣告明細用到參數
	String videoSeconds;
	String videoUrl;
	String adSize;
	String realUrl;
	String realUrlEncode;
	String img;
	String title;
	String content = "";
	String html5Title;
	String adStyle; // 廣告類型
	String html5Flag = "N";
	String showUrl = "";
	String imgWidth; // 圖片寬
	String imgHeight; // 圖片高
	String salesPrice = ""; // 原價
	String promotionalPrice = ""; // 促銷價
	
	// 影音廣告
	String videoName;
	String videoSec = "";
	String videoWidth; // 影片寬
	String videoHeight; // 影片高

	// 商品廣告參數內容
	String adName = "";
	String prodAdSizeWidth = "";
	String prodAdSizeHeight = "";
	String prodGroup = "";
	String logoText = "";
	String logoFontColor = "";
	String logoBgColor = "";
	String btnTxt = "";
	String btnFontColor = "";
	String btnBgColor = "";
	String disTxtType = "";
	String disFontColor = "";
	String disBgColor = "";
	String saleEndImg = "";
	String saleImg = "";
	String catalogSeq = "";
	String prodRadioLogoType = "";
	// 預設不呈現底圖
	String adbgType = "noposter";
	String posterType = "noposter";
	// 根據setup決定是否滿版
	String imgProportiona = "";
	// 根據有無行銷圖決定
	String logoType = "";
	String userLogoPath = "";
	String productTemplateStr = "";
	String previewTpro = "";
	
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

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public String getAdSeq() {
		return adSeq;
	}

	public void setAdSeq(String adSeq) {
		this.adSeq = adSeq;
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

	public BigDecimal getAdClkSum() {
		return adClkSum;
	}

	public void setAdClkSum(BigDecimal adClkSum) {
		this.adClkSum = adClkSum;
	}

	public Double getCtr() {
		return ctr;
	}

	public void setCtr(Double ctr) {
		this.ctr = ctr;
	}

	public BigDecimal getAdInvClkSum() {
		return adInvClkSum;
	}

	public void setAdInvClkSum(BigDecimal adInvClkSum) {
		this.adInvClkSum = adInvClkSum;
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

	public Double getAdPriceSum() {
		return adPriceSum;
	}

	public void setAdPriceSum(Double adPriceSum) {
		this.adPriceSum = adPriceSum;
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

	public String getVideoSeconds() {
		return videoSeconds;
	}

	public void setVideoSeconds(String videoSeconds) {
		this.videoSeconds = videoSeconds;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getAdSize() {
		return adSize;
	}

	public void setAdSize(String adSize) {
		this.adSize = adSize;
	}

	public String getRealUrl() {
		return realUrl;
	}

	public void setRealUrl(String realUrl) {
		this.realUrl = realUrl;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
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

	public String getHtml5Title() {
		return html5Title;
	}

	public void setHtml5Title(String html5Title) {
		this.html5Title = html5Title;
	}

	public String getAdStyle() {
		return adStyle;
	}

	public void setAdStyle(String adStyle) {
		this.adStyle = adStyle;
	}

	public String getHtml5Flag() {
		return html5Flag;
	}

	public void setHtml5Flag(String html5Flag) {
		this.html5Flag = html5Flag;
	}

	public String getShowUrl() {
		return showUrl;
	}

	public void setShowUrl(String showUrl) {
		this.showUrl = showUrl;
	}

	public String getAdName() {
		return adName;
	}

	public void setAdName(String adName) {
		this.adName = adName;
	}

	public String getProdGroup() {
		return prodGroup;
	}

	public void setProdGroup(String prodGroup) {
		this.prodGroup = prodGroup;
	}

	public String getLogoText() {
		return logoText;
	}

	public void setLogoText(String logoText) {
		this.logoText = logoText;
	}

	public String getLogoFontColor() {
		return logoFontColor;
	}

	public void setLogoFontColor(String logoFontColor) {
		this.logoFontColor = logoFontColor;
	}

	public String getLogoBgColor() {
		return logoBgColor;
	}

	public void setLogoBgColor(String logoBgColor) {
		this.logoBgColor = logoBgColor;
	}

	public String getBtnTxt() {
		return btnTxt;
	}

	public void setBtnTxt(String btnTxt) {
		this.btnTxt = btnTxt;
	}

	public String getBtnFontColor() {
		return btnFontColor;
	}

	public void setBtnFontColor(String btnFontColor) {
		this.btnFontColor = btnFontColor;
	}

	public String getBtnBgColor() {
		return btnBgColor;
	}

	public void setBtnBgColor(String btnBgColor) {
		this.btnBgColor = btnBgColor;
	}

	public String getDisTxtType() {
		return disTxtType;
	}

	public void setDisTxtType(String disTxtType) {
		this.disTxtType = disTxtType;
	}

	public String getDisFontColor() {
		return disFontColor;
	}

	public void setDisFontColor(String disFontColor) {
		this.disFontColor = disFontColor;
	}

	public String getDisBgColor() {
		return disBgColor;
	}

	public void setDisBgColor(String disBgColor) {
		this.disBgColor = disBgColor;
	}

	public String getSaleEndImg() {
		return saleEndImg;
	}

	public void setSaleEndImg(String saleEndImg) {
		this.saleEndImg = saleEndImg;
	}

	public String getSaleImg() {
		return saleImg;
	}

	public void setSaleImg(String saleImg) {
		this.saleImg = saleImg;
	}

	public String getCatalogSeq() {
		return catalogSeq;
	}

	public void setCatalogSeq(String catalogSeq) {
		this.catalogSeq = catalogSeq;
	}

	public String getProdRadioLogoType() {
		return prodRadioLogoType;
	}

	public void setProdRadioLogoType(String prodRadioLogoType) {
		this.prodRadioLogoType = prodRadioLogoType;
	}

	public String getLogoType() {
		return logoType;
	}

	public void setLogoType(String logoType) {
		this.logoType = logoType;
	}

	public String getImgWidth() {
		return imgWidth;
	}

	public void setImgWidth(String imgWidth) {
		this.imgWidth = imgWidth;
	}

	public String getImgHeight() {
		return imgHeight;
	}

	public void setImgHeight(String imgHeight) {
		this.imgHeight = imgHeight;
	}

	public String getVideoWidth() {
		return videoWidth;
	}

	public void setVideoWidth(String videoWidth) {
		this.videoWidth = videoWidth;
	}

	public String getVideoHeight() {
		return videoHeight;
	}

	public void setVideoHeight(String videoHeight) {
		this.videoHeight = videoHeight;
	}

	public String getSalesPrice() {
		return salesPrice;
	}

	public void setSalesPrice(String salesPrice) {
		this.salesPrice = salesPrice;
	}

	public String getPromotionalPrice() {
		return promotionalPrice;
	}

	public void setPromotionalPrice(String promotionalPrice) {
		this.promotionalPrice = promotionalPrice;
	}

	public String getVideoSec() {
		return videoSec;
	}

	public void setVideoSec(String videoSec) {
		this.videoSec = videoSec;
	}

	public String getVideoName() {
		return videoName;
	}

	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}

	public String getProductTemplateStr() {
		return productTemplateStr;
	}

	public void setProductTemplateStr(String productTemplateStr) {
		this.productTemplateStr = productTemplateStr;
	}

	public String getRealUrlEncode() {
		return realUrlEncode;
	}

	public void setRealUrlEncode(String realUrlEncode) {
		this.realUrlEncode = realUrlEncode;
	}

	public String getAdbgType() {
		return adbgType;
	}

	public void setAdbgType(String adbgType) {
		this.adbgType = adbgType;
	}

	public String getPosterType() {
		return posterType;
	}

	public void setPosterType(String posterType) {
		this.posterType = posterType;
	}

	public String getPreviewTpro() {
		return previewTpro;
	}

	public void setPreviewTpro(String previewTpro) {
		this.previewTpro = previewTpro;
	}

	public String getImgProportiona() {
		return imgProportiona;
	}

	public void setImgProportiona(String imgProportiona) {
		this.imgProportiona = imgProportiona;
	}

	public String getUserLogoPath() {
		return userLogoPath;
	}

	public void setUserLogoPath(String userLogoPath) {
		this.userLogoPath = userLogoPath;
	}

	public String getProdAdSizeWidth() {
		return prodAdSizeWidth;
	}

	public void setProdAdSizeWidth(String prodAdSizeWidth) {
		this.prodAdSizeWidth = prodAdSizeWidth;
	}

	public String getProdAdSizeHeight() {
		return prodAdSizeHeight;
	}

	public void setProdAdSizeHeight(String prodAdSizeHeight) {
		this.prodAdSizeHeight = prodAdSizeHeight;
	}
	
}