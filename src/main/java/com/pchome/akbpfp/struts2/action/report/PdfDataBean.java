package com.pchome.akbpfp.struts2.action.report;

import java.util.LinkedList;

public class PdfDataBean {
	private String dateRange="";
	private String custName="";
	private String tax="";
	private String preMonthReminePrice;
	private String thisMonthReminePrice;
	private LinkedList<String> contentList;
	private String logoPath="";
	private String pchomeTitle="";
	private String pchomeAddress="";
	private String pchomeTel="";
	private String reportTitle="";
	
	
	public PdfDataBean(){
		contentList=new LinkedList<String>();
	}
	
	public String getDateRange() {
		return dateRange;
	}
	public void setDateRange(String dateRange) {
		this.dateRange = dateRange;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getTax() {
		return tax;
	}
	public void setTax(String tax) {
		this.tax = tax;
	}
	public String getPreMonthReminePrice() {
		return preMonthReminePrice;
	}
	public void setPreMonthReminePrice(String preMonthReminePrice) {
		this.preMonthReminePrice = preMonthReminePrice;
	}
	public String getThisMonthReminePrice() {
		return thisMonthReminePrice;
	}
	public void setThisMonthReminePrice(String thisMonthReminePrice) {
		this.thisMonthReminePrice = thisMonthReminePrice;
	}
	public LinkedList<String> getContentList() {
		return contentList;
	}
	public void setContentList(LinkedList<String> contentList) {
		this.contentList = contentList;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public String getPchomeTitle() {
		return pchomeTitle;
	}

	public void setPchomeTitle(String pchomeTitle) {
		this.pchomeTitle = pchomeTitle;
	}

	public String getPchomeAddress() {
		return pchomeAddress;
	}

	public void setPchomeAddress(String pchomeAddress) {
		this.pchomeAddress = pchomeAddress;
	}

	public String getPchomeTel() {
		return pchomeTel;
	}

	public void setPchomeTel(String pchomeTel) {
		this.pchomeTel = pchomeTel;
	}

	public String getReportTitle() {
		return reportTitle;
	}

	public void setReportTitle(String reportTitle) {
		this.reportTitle = reportTitle;
	}



}
