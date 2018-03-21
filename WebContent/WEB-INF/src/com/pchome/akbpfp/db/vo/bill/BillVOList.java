package com.pchome.akbpfp.db.vo.bill;

import java.util.List;

public class BillVOList {

	private List<BillVO> billVOs;				// 交易明細
	private float totalSaveMoney;				// 儲值總額
	private float totalTaxMoney;				// 稅金總額
	private float totalReturnMoney;				// 退回總額
	private float totalAdSpentMoney;			// 花費總額
	private float remain;
	private float totalRefundMoney;				// 預付、後付退款總額
	

	public List<BillVO> getBillVOs() {
		return billVOs;
	}
	public void setBillVOs(List<BillVO> billVOs) {
		this.billVOs = billVOs;
	}
	public float getTotalSaveMoney() {
		return totalSaveMoney;
	}
	public void setTotalSaveMoney(float totalSaveMoney) {
		this.totalSaveMoney = totalSaveMoney;
	}
	public float getTotalTaxMoney() {
		return totalTaxMoney;
	}
	public void setTotalTaxMoney(float totalTaxMoney) {
		this.totalTaxMoney = totalTaxMoney;
	}
	public float getTotalReturnMoney() {
		return totalReturnMoney;
	}
	public void setTotalReturnMoney(float totalReturnMoney) {
		this.totalReturnMoney = totalReturnMoney;
	}
	public float getTotalAdSpentMoney() {
		return totalAdSpentMoney;
	}
	public void setTotalAdSpentMoney(float totalAdSpentMoney) {
		this.totalAdSpentMoney = totalAdSpentMoney;
	}
	public float getRemain() {
		return remain;
	}
	public void setRemain(float remain) {
		this.remain = remain;
	}
	public float getTotalRefundMoney() {
		return totalRefundMoney;
	}
	public void setTotalRefundMoney(float totalRefundMoney) {
		this.totalRefundMoney = totalRefundMoney;
	}
	
}
