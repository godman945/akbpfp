package com.pchome.akbpfp.db.service.user;

import java.util.List;

import com.pchome.akbpfp.db.pojo.PfpUserMemberRef;
import com.pchome.akbpfp.db.service.IBaseService;

public interface IPfpUserMemberRefService extends IBaseService<PfpUserMemberRef,String>{

	public List<PfpUserMemberRef> activateUserMemberRef(String memberId);
	
	public List<PfpUserMemberRef> validUserMemberRef(String memberId) throws Exception;
	
	public List<PfpUserMemberRef> applyUserMemberRef(String memberId) throws Exception;

	public List<PfpUserMemberRef> activateUserMemberRef2(String memberId) throws Exception;
}
