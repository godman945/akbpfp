package com.pchome.akbpfp.db.service.user;

import java.util.List;
import java.util.Map;

import com.pchome.akbpfp.db.pojo.PfpUser;
import com.pchome.akbpfp.db.service.IBaseService;
import com.pchome.akbpfp.db.vo.user.UserVO;

public interface IPfpUserService extends IBaseService<PfpUser,String>{

	public List<UserVO> findAccountUsers(String customerInfoId) throws Exception;  
	
	public UserVO findUser(String userId, String status) throws Exception;
	
	public PfpUser findUser(String userId) throws Exception;
	
	public List<UserVO> searchAccountUsers(String customerInfoId, String nickName, String status) throws Exception;
	
	public void saveOrUpdateWithAccesslog(PfpUser user, String pcId, String customerInfoId, String  clientIp) throws Exception;
	
	public List<PfpUser> checkInviteStatus(String customerInfoId, String email) throws Exception;
	
	public PfpUser findApplyUser(String customerInfoId) throws Exception;

	public PfpUser findRootUser(String customerInfoId);

	public List<PfpUser> findUserByConditions(Map<String, String> conditionMap) throws Exception;
}
