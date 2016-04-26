package com.pchome.akbpfp.db.vo.bill;

public class AdmFreeVO {

	private String recordDate;			//記錄日期
	private String actionStartDate;		//禮金活動開始日期
	private String actionEndDate;		//禮金活動結束日期
	private String actionName;			//禮金活動名稱
	private String note;				//禮金活動說明
	private float giftMoney;			//禮金金額
	private String inviledDate;			//禮金失效日期
	public String getRecordDate() {
		return recordDate;
	}
	public void setRecordDate(String recordDate) {
		this.recordDate = recordDate;
	}
	public String getActionStartDate() {
		return actionStartDate;
	}
	public void setActionStartDate(String actionStartDate) {
		this.actionStartDate = actionStartDate;
	}
	public String getActionEndDate() {
		return actionEndDate;
	}
	public void setActionEndDate(String actionEndDate) {
		this.actionEndDate = actionEndDate;
	}
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public float getGiftMoney() {
		return giftMoney;
	}
	public void setGiftMoney(float giftMoney) {
		this.giftMoney = giftMoney;
	}
	public String getInviledDate() {
		return inviledDate;
	}
	public void setInviledDate(String inviledDate) {
		this.inviledDate = inviledDate;
	}
	
}
