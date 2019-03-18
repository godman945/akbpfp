package com.pchome.akbpfp.db.dao.user;

import java.util.List;
import java.util.Map;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpUser;

public interface IPfpUserDAO extends IBaseDAO<PfpUser, String>{
	
	public List<PfpUser> findAccountUsers(String customerInfoId) throws Exception;
	
	public List<PfpUser> findUser(String userId) throws Exception;
	
	public List<PfpUser> findUser(String userId, String status) throws Exception;
	
	public List<PfpUser> searchAccountUsers(String customerInfoId, String nickName, String status) throws Exception;
	
	public List<PfpUser> checkInviteStatus(String customerInfoId, String email) throws Exception;
	
	public List<PfpUser> findApplyUser(String customerInfoId) throws Exception;
	
	public List<PfpUser> findRootUser(String customerInfoId);
	
	public List<PfpUser> findUserByConditions(Map<String, String> conditionMap) throws Exception;
}
