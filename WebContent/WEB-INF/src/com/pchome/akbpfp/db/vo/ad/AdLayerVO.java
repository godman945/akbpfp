package com.pchome.akbpfp.db.vo.ad;

public class AdLayerVO {

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
	//圖像廣告
	private String realUrl = "";
	private String img = "";
	private String originalImg = "";
	private String adStyle = "";
	private String imgWidth = "";
	private String imgHeight = "";
	private String showUrl = "";
	private String title = "";
	
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
	
}
