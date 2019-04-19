package com.pchome.akbpfp.db.dao.user;

import java.util.List;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpUserMemberRef;

public interface IPfpUserMemberRefDAO extends IBaseDAO<PfpUserMemberRef,String>{
	
	public List<PfpUserMemberRef> activateUserMemberRef(String memberId);

	public List<PfpUserMemberRef> activateUserMemberRef2(String memberId) throws Exception;

	public List<PfpUserMemberRef> validUserMemberRef(String memberId) throws Exception;
	
	public List<PfpUserMemberRef> applyUserMemberRef(String memberId) throws Exception;

	//public List<PfpUserMemberRef> checkAccountUserStauts(String memberId) throws Exception;
}
