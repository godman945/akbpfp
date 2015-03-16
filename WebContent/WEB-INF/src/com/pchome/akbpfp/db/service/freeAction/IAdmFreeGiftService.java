package com.pchome.akbpfp.db.service.freeAction;

import java.util.Date;

import com.pchome.akbpfp.db.pojo.AdmFreeGift;
import com.pchome.akbpfp.db.service.IBaseService;

public interface IAdmFreeGiftService extends IBaseService<AdmFreeGift, Integer> {

	// 確認是否有這序號
	public AdmFreeGift findAdmFreeGiftSno(String sno);
	// 未使用且未過期序號
	public AdmFreeGift findUnusedAdmFreeGiftSno(String sno, Date today);
}
