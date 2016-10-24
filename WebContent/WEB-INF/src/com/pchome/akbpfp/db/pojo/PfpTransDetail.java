package com.pchome.akbpfp.db.pojo;

// Generated 2016/10/24 �U�� 12:22:48 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * PfpTransDetail generated by hbm2java
 */
@Entity
@Table(name = "pfp_trans_detail", catalog = "akb")
public class PfpTransDetail implements java.io.Serializable {

	private Integer transId;
	private PfpCustomerInfo pfpCustomerInfo;
	private Date transDate;
	private String transContent;
	private String transType;
	private String incomeExpense;
	private float transPrice;
	private float totalSavePrice;
	private float totalSpendPrice;
	private float totalRetrievePrice;
	private float tax;
	private float remain;
	private float totalLaterSpend;
	private float totalLaterSave;
	private float laterRemain;
	private float totalLaterRetrieve;
	private Date updateDate;
	private Date createDate;

	public PfpTransDetail() {
	}

	public PfpTransDetail(PfpCustomerInfo pfpCustomerInfo, Date transDate,
			String transContent, String transType, String incomeExpense,
			float transPrice, float totalSavePrice, float totalSpendPrice,
			float totalRetrievePrice, float tax, float remain,
			float totalLaterSpend, float totalLaterSave, float laterRemain,
			float totalLaterRetrieve, Date updateDate, Date createDate) {
		this.pfpCustomerInfo = pfpCustomerInfo;
		this.transDate = transDate;
		this.transContent = transContent;
		this.transType = transType;
		this.incomeExpense = incomeExpense;
		this.transPrice = transPrice;
		this.totalSavePrice = totalSavePrice;
		this.totalSpendPrice = totalSpendPrice;
		this.totalRetrievePrice = totalRetrievePrice;
		this.tax = tax;
		this.remain = remain;
		this.totalLaterSpend = totalLaterSpend;
		this.totalLaterSave = totalLaterSave;
		this.laterRemain = laterRemain;
		this.totalLaterRetrieve = totalLaterRetrieve;
		this.updateDate = updateDate;
		this.createDate = createDate;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "trans_id", unique = true, nullable = false)
	public Integer getTransId() {
		return this.transId;
	}

	public void setTransId(Integer transId) {
		this.transId = transId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_info_id", nullable = false)
	public PfpCustomerInfo getPfpCustomerInfo() {
		return this.pfpCustomerInfo;
	}

	public void setPfpCustomerInfo(PfpCustomerInfo pfpCustomerInfo) {
		this.pfpCustomerInfo = pfpCustomerInfo;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "trans_date", nullable = false, length = 10)
	public Date getTransDate() {
		return this.transDate;
	}

	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}

	@Column(name = "trans_content", nullable = false, length = 50)
	public String getTransContent() {
		return this.transContent;
	}

	public void setTransContent(String transContent) {
		this.transContent = transContent;
	}

	@Column(name = "trans_type", nullable = false, length = 2)
	public String getTransType() {
		return this.transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	@Column(name = "income_expense", nullable = false, length = 1)
	public String getIncomeExpense() {
		return this.incomeExpense;
	}

	public void setIncomeExpense(String incomeExpense) {
		this.incomeExpense = incomeExpense;
	}

	@Column(name = "trans_price", nullable = false, precision = 10)
	public float getTransPrice() {
		return this.transPrice;
	}

	public void setTransPrice(float transPrice) {
		this.transPrice = transPrice;
	}

	@Column(name = "total_save_price", nullable = false, precision = 10)
	public float getTotalSavePrice() {
		return this.totalSavePrice;
	}

	public void setTotalSavePrice(float totalSavePrice) {
		this.totalSavePrice = totalSavePrice;
	}

	@Column(name = "total_spend_price", nullable = false, precision = 10)
	public float getTotalSpendPrice() {
		return this.totalSpendPrice;
	}

	public void setTotalSpendPrice(float totalSpendPrice) {
		this.totalSpendPrice = totalSpendPrice;
	}

	@Column(name = "total_retrieve_price", nullable = false, precision = 10)
	public float getTotalRetrievePrice() {
		return this.totalRetrievePrice;
	}

	public void setTotalRetrievePrice(float totalRetrievePrice) {
		this.totalRetrievePrice = totalRetrievePrice;
	}

	@Column(name = "tax", nullable = false, precision = 10)
	public float getTax() {
		return this.tax;
	}

	public void setTax(float tax) {
		this.tax = tax;
	}

	@Column(name = "remain", nullable = false, precision = 10)
	public float getRemain() {
		return this.remain;
	}

	public void setRemain(float remain) {
		this.remain = remain;
	}

	@Column(name = "total_later_spend", nullable = false, precision = 10)
	public float getTotalLaterSpend() {
		return this.totalLaterSpend;
	}

	public void setTotalLaterSpend(float totalLaterSpend) {
		this.totalLaterSpend = totalLaterSpend;
	}

	@Column(name = "total_later_save", nullable = false, precision = 10)
	public float getTotalLaterSave() {
		return this.totalLaterSave;
	}

	public void setTotalLaterSave(float totalLaterSave) {
		this.totalLaterSave = totalLaterSave;
	}

	@Column(name = "later_remain", nullable = false, precision = 10)
	public float getLaterRemain() {
		return this.laterRemain;
	}

	public void setLaterRemain(float laterRemain) {
		this.laterRemain = laterRemain;
	}

	@Column(name = "total_later_retrieve", nullable = false, precision = 10)
	public float getTotalLaterRetrieve() {
		return this.totalLaterRetrieve;
	}

	public void setTotalLaterRetrieve(float totalLaterRetrieve) {
		this.totalLaterRetrieve = totalLaterRetrieve;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_date", nullable = false, length = 19)
	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", nullable = false, length = 19)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
