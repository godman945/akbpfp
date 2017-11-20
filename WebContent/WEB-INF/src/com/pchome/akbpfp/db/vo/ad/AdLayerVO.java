package com.pchome.akbpfp.db.vo.ad;

public class AdLayerVO {

	private String adType = "";
	private String adOperatingRule = "";
	private String seq = "";
	private String name = "";
	private String templateNo = "";	
	private int statusId = 0;
	private String statusChName = "";
	private float pv = 0;
	private float clk = 0;
	private float clkCost = 0;
	private float clkRate = 0;
	private float avgClkCost = 0;	
	private float invalidClk = 0;
	private float thousandsCost = 0;
	//圖像廣告
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
	
	//關鍵字廣泛比對
	private String widOpen = "";
	private float widPv = 0;
	private float widClk = 0;
	private float widClkCost = 0;
	private float widClkRate = 0;
	private float widAvgClkCost = 0;	
	private float widInvalidClk = 0;
	
	//關鍵字詞組比對
	private String phrOpen = "";
	private float phrPv = 0;
	private float phrClk = 0;
	private float phrClkCost = 0;
	private float phrClkRate = 0;
	private float phrAvgClkCost = 0;	
	private float phrInvalidClk = 0;
	
	//關鍵字精準比對
	private String preOpen = "";
	private float prePv = 0;
	private float preClk = 0;
	private float preClkCost = 0;
	private float preClkRate = 0;
	private float preAvgClkCost = 0;	
	private float preInvalidClk = 0;
	
	/*影音廣告*/
	//尺寸寬度
	private String adWidth;
	//尺寸高度
	private String adHeight;
	//影片網址
	private String videoUrl;
	//影片連結
	private String adLinkUrl;
	//影片秒數
	private String adVideoSec;
	//計價方式
	private String adPriceType;
		
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTemplateNo() {
		return templateNo;
	}
	public void setTemplateNo(String templateNo) {
		this.templateNo = templateNo;
	}
	public int getStatusId() {
		return statusId;
	}
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	public String getStatusChName() {
		return statusChName;
	}
	public void setStatusChName(String statusChName) {
		this.statusChName = statusChName;
	}
	public float getPv() {
		return pv;
	}
	public void setPv(float pv) {
		this.pv = pv;
	}
	public float getClk() {
		return clk;
	}
	public void setClk(float clk) {
		this.clk = clk;
	}
	public float getClkCost() {
		return clkCost;
	}
	public void setClkCost(float clkCost) {
		this.clkCost = clkCost;
	}
	public float getClkRate() {
		return clkRate;
	}
	public void setClkRate(float clkRate) {
		this.clkRate = clkRate;
	}
	public float getAvgClkCost() {
		return avgClkCost;
	}
	public void setAvgClkCost(float avgClkCost) {
		this.avgClkCost = avgClkCost;
	}
	public float getInvalidClk() {
		return invalidClk;
	}
	public void setInvalidClk(float invalidClk) {
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
	public String getWidOpen() {
		return widOpen;
	}
	public void setWidOpen(String widOpen) {
		this.widOpen = widOpen;
	}
	public float getWidPv() {
		return widPv;
	}
	public void setWidPv(float widPv) {
		this.widPv = widPv;
	}
	public float getWidClk() {
		return widClk;
	}
	public void setWidClk(float widClk) {
		this.widClk = widClk;
	}
	public float getWidClkCost() {
		return widClkCost;
	}
	public void setWidClkCost(float widClkCost) {
		this.widClkCost = widClkCost;
	}
	public float getWidClkRate() {
		return widClkRate;
	}
	public void setWidClkRate(float widClkRate) {
		this.widClkRate = widClkRate;
	}
	public float getWidAvgClkCost() {
		return widAvgClkCost;
	}
	public void setWidAvgClkCost(float widAvgClkCost) {
		this.widAvgClkCost = widAvgClkCost;
	}
	public float getWidInvalidClk() {
		return widInvalidClk;
	}
	public void setWidInvalidClk(float widInvalidClk) {
		this.widInvalidClk = widInvalidClk;
	}
	public String getPhrOpen() {
		return phrOpen;
	}
	public void setPhrOpen(String phrOpen) {
		this.phrOpen = phrOpen;
	}
	public float getPhrPv() {
		return phrPv;
	}
	public void setPhrPv(float phrPv) {
		this.phrPv = phrPv;
	}
	public float getPhrClk() {
		return phrClk;
	}
	public void setPhrClk(float phrClk) {
		this.phrClk = phrClk;
	}
	public float getPhrClkCost() {
		return phrClkCost;
	}
	public void setPhrClkCost(float phrClkCost) {
		this.phrClkCost = phrClkCost;
	}
	public float getPhrClkRate() {
		return phrClkRate;
	}
	public void setPhrClkRate(float phrClkRate) {
		this.phrClkRate = phrClkRate;
	}
	public float getPhrAvgClkCost() {
		return phrAvgClkCost;
	}
	public void setPhrAvgClkCost(float phrAvgClkCost) {
		this.phrAvgClkCost = phrAvgClkCost;
	}
	public float getPhrInvalidClk() {
		return phrInvalidClk;
	}
	public void setPhrInvalidClk(float phrInvalidClk) {
		this.phrInvalidClk = phrInvalidClk;
	}
	public String getPreOpen() {
		return preOpen;
	}
	public void setPreOpen(String preOpen) {
		this.preOpen = preOpen;
	}
	public float getPrePv() {
		return prePv;
	}
	public void setPrePv(float prePv) {
		this.prePv = prePv;
	}
	public float getPreClk() {
		return preClk;
	}
	public void setPreClk(float preClk) {
		this.preClk = preClk;
	}
	public float getPreClkCost() {
		return preClkCost;
	}
	public void setPreClkCost(float preClkCost) {
		this.preClkCost = preClkCost;
	}
	public float getPreClkRate() {
		return preClkRate;
	}
	public void setPreClkRate(float preClkRate) {
		this.preClkRate = preClkRate;
	}
	public float getPreAvgClkCost() {
		return preAvgClkCost;
	}
	public void setPreAvgClkCost(float preAvgClkCost) {
		this.preAvgClkCost = preAvgClkCost;
	}
	public float getPreInvalidClk() {
		return preInvalidClk;
	}
	public void setPreInvalidClk(float preInvalidClk) {
		this.preInvalidClk = preInvalidClk;
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
	public float getThousandsCost() {
		return thousandsCost;
	}
	public void setThousandsCost(float thousandsCost) {
		this.thousandsCost = thousandsCost;
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
	public String getVideoUrl() {
		return videoUrl;
	}
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	public String getAdLinkUrl() {
		return adLinkUrl;
	}
	public void setAdLinkUrl(String adLinkUrl) {
		this.adLinkUrl = adLinkUrl;
	}
	public String getAdVideoSec() {
		return adVideoSec;
	}
	public void setAdVideoSec(String adVideoSec) {
		this.adVideoSec = adVideoSec;
	}
	public String getAdPriceType() {
		return adPriceType;
	}
	public void setAdPriceType(String adPriceType) {
		this.adPriceType = adPriceType;
	}
	
}
