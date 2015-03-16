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
	
	public AdmFreeGift findUnusedAdmFreeGiftSno(String sno, Date today) {
		
		List<AdmFreeGift> list = ((IAdmFreeGiftDAO)dao).findUnusedAdmFreeGiftSno(sno, today);
		
		if(list.isEmpty()){
			return null;
		}else{
			return list.get(0);
		}
	}
}
