package com.pchome.akbpfp.db.dao.report;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class AdActionReportVO {

	private String customerInfoId;
	private String searchText = ""; // �j�M��r
	private String startDate = ""; // �d�߶}�l���
	private String endDate = ""; // �d�ߵ������
	private int page = 1; // �ĴX��
	private int pageSize = 10; // �C������
	private String whereMap = ""; // sql�z�����
	private String sortBy = ""; // �Ƨ����
	private boolean isDownloadOrIsNotCuttingPagination = false; // �O�_���U���A�ΥΨӷ�SQL�O�_������FLAG
	private String charType = ""; // �Ϫ�׶q
	
	private Date reportDate; //������

	private String adActionSeq; //�s�i���ʧǸ�

	private BigDecimal adPvSum; // �s�iPV�`�M
	private BigDecimal adClkSum; // �s�iClick�`�M
	private Double adPriceSum; // �s�i�����`�M
	private BigDecimal adInvClkSum; // �s�i�L���I���`�M
	private Double adActionMaxPriceSum; // �C���O�W���`�M
	private BigInteger count; // ��Ƶ���(�Ω�p�⥭���C���O�W��)
	private String adDevice; // �˸m
	private String adType;
	private String adOperatingRule; // �s�i�˦�
	private String adClkPriceType; // �s�i�p�O�覡
	private String adOperatingRuleDesc;
	private BigDecimal convertCount; // �ഫ��
	private BigDecimal convertPriceCount; // �ഫ����
	
	private Double ctr; // ���ʲv
	private Double avgCost; // �榸���ʶO��
	private Double kiloCost; // �d���n���O��
	private Double convertCTR; // �ഫ�v
	private Double convertCost; // �����ഫ����
	private Double convertInvestmentCost; // �s�i�����S�v
	private int rowCount = 0; // �`�p�X��
	
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

	public String getAdOperatingRuleDesc() {
		return adOperatingRuleDesc;
	}

	public void setAdOperatingRuleDesc(String adOperatingRuleDesc) {
		this.adOperatingRuleDesc = adOperatingRuleDesc;
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

	public String getCustomerInfoId() {
		return customerInfoId;
	}

	public void setCustomerInfoId(String customerInfoId) {
		this.customerInfoId = customerInfoId;
	}

	public Double getCtr() {
		return ctr;
	}

	public void setCtr(Double ctr) {
		this.ctr = ctr;
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

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public boolean isDownloadOrIsNotCuttingPagination() {
		return isDownloadOrIsNotCuttingPagination;
	}

	public void setDownloadOrIsNotCuttingPagination(boolean isDownloadOrIsNotCuttingPagination) {
		this.isDownloadOrIsNotCuttingPagination = isDownloadOrIsNotCuttingPagination;
	}

	public String getCharType() {
		return charType;
	}

	public void setCharType(String charType) {
		this.charType = charType;
	}

}