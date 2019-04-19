package com.pchome.akbpfp.db.service.freeAction;

import java.util.Date;

import com.pchome.akbpfp.db.pojo.AdmFreeGift;
import com.pchome.akbpfp.db.service.IBaseService;

public interface IAdmFreeGiftService extends IBaseService<AdmFreeGift, Integer> {

	// 確認是否有這序號
	public AdmFreeGift findAdmFreeGiftSno(String sno);
	// 未使用且未過期序號
	public AdmFreeGift findUnusedAdmFreeGiftSno(String sno, Date today, String snoStyle);
	// 依訂單編號找廣告金序號
	public AdmFreeGift findAdmFreeGiftSnoByOrderId(String orderId);
	// 確認使用者是否已有使用過該活動的序號
	public AdmFreeGift findUsedHistory(String actionId, String customerInfoId);
	// 依序號找廣告金序號
	public AdmFreeGift findAdmFreeGiftBySno(String giftSno);
}
