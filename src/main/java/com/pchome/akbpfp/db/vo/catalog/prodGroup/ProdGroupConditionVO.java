package com.pchome.akbpfp.db.vo.catalog.prodGroup;

public class ProdGroupConditionVO {
	String catalogSeq;
	int page;	
	int pageSize;	
	String prodStatus;
	String prodName;
	String pfpCustomerInfoId;
	String filterSQL;
	
	public String getCatalogSeq() {
		return catalogSeq;
	}
	public void setCatalogSeq(String catalogSeq) {
		this.catalogSeq = catalogSeq;
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
	public String getProdStatus() {
		return prodStatus;
	}
	public void setProdStatus(String prodStatus) {
		this.prodStatus = prodStatus;
	}
	public String getProdName() {
		return prodName;
	}
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	public String getPfpCustomerInfoId() {
		return pfpCustomerInfoId;
	}
	public void setPfpCustomerInfoId(String pfpCustomerInfoId) {
		this.pfpCustomerInfoId = pfpCustomerInfoId;
	}
	public String getFilterSQL() {
		return filterSQL;
	}
	public void setFilterSQL(String filterSQL) {
		this.filterSQL = filterSQL;
	}
	
}
