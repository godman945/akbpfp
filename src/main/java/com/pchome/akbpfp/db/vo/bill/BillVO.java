package com.pchome.akbpfp.db.vo.bill;

public class BillVO {

	private String transDate;					// 交易日期
	private String transContents;				// 交易內容
	private float saveMoney; 					// 加值金額
	private float taxMoney; 					// 稅金金額
	private float returnMoney;					// 退回金額
	private float adSpentMoney;					// 廣告花費
	private float remain;						// 帳戶餘額
	private float refundMoney;			     	// 預付、後付退款總額
	
	public String getTransDate() {
		return transDate;
	}
	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}
	public String getTransContents() {
		return transContents;
	}
	public void setTransContents(String transContents) {
		this.transContents = transContents;
	}
	public float getSaveMoney() {
		return saveMoney;
	}
	public void setSaveMoney(float saveMoney) {
		this.saveMoney = saveMoney;
	}
	public float getTaxMoney() {
		return taxMoney;
	}
	public void setTaxMoney(float taxMoney) {
		this.taxMoney = taxMoney;
	}
	public float getReturnMoney() {
		return returnMoney;
	}
	public void setReturnMoney(float returnMoney) {
		this.returnMoney = returnMoney;
	}
	public float getAdSpentMoney() {
		return adSpentMoney;
	}
	public void setAdSpentMoney(float adSpentMoney) {
		this.adSpentMoney = adSpentMoney;
	}
	public float getRemain() {
		return remain;
	}
	public void setRemain(float remain) {
		this.remain = remain;
	}
	public float getRefundMoney() {
		return refundMoney;
	}
	public void setRefundMoney(float refundMoney) {
		this.refundMoney = refundMoney;
	}
	
}
