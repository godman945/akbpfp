package com.pchome.akbpfp.db.service.freeAction;

import java.util.Date;
import java.util.List;

import com.pchome.akbpfp.db.dao.freeAction.IAdmFreeGiftDAO;
import com.pchome.akbpfp.db.pojo.AdmFreeGift;
import com.pchome.akbpfp.db.service.BaseService;

public class AdmFreeGiftService extends BaseService<AdmFreeGift, Integer> implements IAdmFreeGiftService{

	public AdmFreeGift findAdmFreeGiftSno(String sno){
		List<AdmFreeGift> list = ((IAdmFreeGiftDAO)dao).findAdmFreeGiftSno(sno);
		
		if(list.isEmpty()){
			return null;
		}else{
			return list.get(0);
		}
	}
	
	public AdmFreeGift findUnusedAdmFreeGiftSno(String sno, Date today, String snoStyle) {
		
		List<AdmFreeGift> list = ((IAdmFreeGiftDAO)dao).findUnusedAdmFreeGiftSno(sno, today, snoStyle);
		
		if(list.isEmpty()){
			return null;
		}else{
			return list.get(0);
		}
	}
	
	public AdmFreeGift findAdmFreeGiftSnoByOrderId(String orderId) {
		List<AdmFreeGift> list = ((IAdmFreeGiftDAO)dao).findAdmFreeGiftSnoByOrderId(orderId);
		
		if(list.isEmpty()){
			return null;
		}else{
			return list.get(0);
		}
	}
	
	public AdmFreeGift findUsedHistory(String actionId, String customerInfoId) {
		List<AdmFreeGift> list = ((IAdmFreeGiftDAO)dao).findUsedHistory(actionId, customerInfoId);
		
		if(list.isEmpty()){
			return null;
		}else{
			return list.get(0);
		}
	}
	
	public AdmFreeGift findAdmFreeGiftBySno(String giftSno) {
		List<AdmFreeGift> list = ((IAdmFreeGiftDAO)dao).findAdmFreeGiftBySno(giftSno);
		
		if(list.isEmpty()){
			return null;
		}else{
			return list.get(0);
		}
	}
}
