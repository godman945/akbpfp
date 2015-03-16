package com.pchome.akbpfp.db.dao.freeAction;

import java.util.Date;
import java.util.List;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.AdmFreeGift;

public interface IAdmFreeGiftDAO extends IBaseDAO<AdmFreeGift, Integer> {

	public List<AdmFreeGift> findAdmFreeGiftSno(String sno);
	
	public List<AdmFreeGift> findUnusedAdmFreeGiftSno(String sno, Date today);
}
