package com.pchome.akbpfp.db.vo.ad;

public class PfpAdAdViewVO {
	private String adOperatingRule = "";
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
	private String realUrl = "";
	private String img = "";
	private String originalImg = "";
	private String adStyle = "";
	private String imgWidth = "";
	private String imgHeight = "";
	private String showUrl = "";
	private String title = "";
	private String html5Tag = "";
	private String zipTitle = "";
	private String adPriceType = "";
	private float thousandsCost;
	
	/* 商品廣告用參數 START*/
	//行銷結尾圖
	private String uploadLog;
	//logo圖
	private String uploadLogoLog;
	//廣告名稱
	private String adName;
	//商品目錄ID
	private String catalogId;
	//商品群組ID
	private String catalogGroupId;
	//logo類型
	private String logoType;
	//logo標題文字
	private String logoText;
	//logo背景顏色
	private String logoBgColor;
	//logo文字顏色
	private String logoFontColor;
	//按鈕文字
	private String btnTxt;
	//按鈕文字顏色
	private String btnFontColor;
	//按鈕背景顏色
	private String btnBgColor;
	//標籤文字
	private String disTxtType;
	//標籤背景顏色
	private String disBgColor;
	//標籤文字顏色
	private String disFontColor;
	//logo類型
	private String prodLogoType;
	
	private String userLogoType;
	
	private String userLogoPath;
	
	/* 商品廣告用參數 END*/
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
    public String getOriginalImg() {
        return originalImg;
    }
    public void setOriginalImg(String originalImg) {
        this.originalImg = originalImg;
    }
    public String getAdStyle() {
        return adStyle;
    }
    public void setAdStyle(String adStyle) {
        this.adStyle = adStyle;
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
	public String getShowUrl() {
		return showUrl;
	}
	public void setShowUrl(String showUrl) {
		this.showUrl = showUrl;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getHtml5Tag() {
		return html5Tag;
	}
	public void setHtml5Tag(String html5Tag) {
		this.html5Tag = html5Tag;
	}
	public String getZipTitle() {
		return zipTitle;
	}
	public void setZipTitle(String zipTitle) {
		this.zipTitle = zipTitle;
	}
	public float getThousandsCost() {
		return thousandsCost;
	}
	public void setThousandsCost(float thousandsCost) {
		this.thousandsCost = thousandsCost;
	}
	public String getAdPriceType() {
		return adPriceType;
	}
	public void setAdPriceType(String adPriceType) {
		this.adPriceType = adPriceType;
	}
	public String getAdOperatingRule() {
		return adOperatingRule;
	}
	public void setAdOperatingRule(String adOperatingRule) {
		this.adOperatingRule = adOperatingRule;
	}
	public String getUploadLog() {
		return uploadLog;
	}
	public void setUploadLog(String uploadLog) {
		this.uploadLog = uploadLog;
	}
	public String getUploadLogoLog() {
		return uploadLogoLog;
	}
	public void setUploadLogoLog(String uploadLogoLog) {
		this.uploadLogoLog = uploadLogoLog;
	}
	public String getAdName() {
		return adName;
	}
	public void setAdName(String adName) {
		this.adName = adName;
	}
	public String getCatalogId() {
		return catalogId;
	}
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
	public String getCatalogGroupId() {
		return catalogGroupId;
	}
	public void setCatalogGroupId(String catalogGroupId) {
		this.catalogGroupId = catalogGroupId;
	}
	public String getLogoType() {
		return logoType;
	}
	public void setLogoType(String logoType) {
		this.logoType = logoType;
	}
	public String getLogoText() {
		return logoText;
	}
	public void setLogoText(String logoText) {
		this.logoText = logoText;
	}
	public String getLogoBgColor() {
		return logoBgColor;
	}
	public void setLogoBgColor(String logoBgColor) {
		this.logoBgColor = logoBgColor;
	}
	public String getLogoFontColor() {
		return logoFontColor;
	}
	public void setLogoFontColor(String logoFontColor) {
		this.logoFontColor = logoFontColor;
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
	public String getDisBgColor() {
		return disBgColor;
	}
	public void setDisBgColor(String disBgColor) {
		this.disBgColor = disBgColor;
	}
	public String getDisFontColor() {
		return disFontColor;
	}
	public void setDisFontColor(String disFontColor) {
		this.disFontColor = disFontColor;
	}
	public String getProdLogoType() {
		return prodLogoType;
	}
	public void setProdLogoType(String prodLogoType) {
		this.prodLogoType = prodLogoType;
	}
	public String getUserLogoType() {
		return userLogoType;
	}
	public void setUserLogoType(String userLogoType) {
		this.userLogoType = userLogoType;
	}
	public String getUserLogoPath() {
		return userLogoPath;
	}
	public void setUserLogoPath(String userLogoPath) {
		this.userLogoPath = userLogoPath;
	}
    
}
