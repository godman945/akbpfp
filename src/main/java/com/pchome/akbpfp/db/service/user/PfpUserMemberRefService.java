package com.pchome.akbpfp.db.service.user;

import java.util.List;

import com.pchome.akbpfp.db.dao.user.IPfpUserMemberRefDAO;
import com.pchome.akbpfp.db.pojo.PfpUserMemberRef;
import com.pchome.akbpfp.db.service.BaseService;

public class PfpUserMemberRefService extends BaseService<PfpUserMemberRef,String> implements IPfpUserMemberRefService{
	
	public List<PfpUserMemberRef> activateUserMemberRef(String memberId) {
		// 取已被啟用的帳號：開啟、關閉、停權
		return ((IPfpUserMemberRefDAO)dao).activateUserMemberRef(memberId);
	}

	/**
	 * 取帳戶狀態為 開啟or關閉，帳號狀態為  開啟or關閉or停權
	 */
	public List<PfpUserMemberRef> activateUserMemberRef2(String memberId) throws Exception {
		return ((IPfpUserMemberRefDAO)dao).activateUserMemberRef2(memberId);
	}
	
	public List<PfpUserMemberRef> validUserMemberRef(String memberId) throws Exception{
		//取刪除以外的狀態
		return((IPfpUserMemberRefDAO)dao).validUserMemberRef(memberId);
	}
	
	public List<PfpUserMemberRef> applyUserMemberRef(String memberId) throws Exception{		
		return ((IPfpUserMemberRefDAO)dao).applyUserMemberRef(memberId);
	}
}
