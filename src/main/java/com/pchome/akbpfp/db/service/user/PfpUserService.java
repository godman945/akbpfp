package com.pchome.akbpfp.db.service.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.pchome.akbpfp.db.dao.user.IPfpUserDAO;
import com.pchome.akbpfp.db.dao.user.IPfpUserMemberRefDAO;
import com.pchome.akbpfp.db.pojo.PfpUser;
import com.pchome.akbpfp.db.pojo.PfpUserMemberRef;
import com.pchome.akbpfp.db.service.BaseService;
import com.pchome.akbpfp.db.vo.user.UserVO;
import com.pchome.enumerate.user.EnumUserStatus;
import com.pchome.rmi.accesslog.EnumAccesslogAction;
import com.pchome.rmi.accesslog.EnumAccesslogChannel;
import com.pchome.rmi.accesslog.EnumAccesslogEmailStatus;
import com.pchome.rmi.accesslog.IAccesslogProvider;
import com.pchome.soft.util.DateValueUtil;

public class PfpUserService extends BaseService<PfpUser,String> implements IPfpUserService{
	
	private IPfpUserMemberRefDAO pfpUserMemberRefDAO;
	private IAccesslogProvider accesslogProvider;
	
	public List<UserVO> findAccountUsers(String customerInfoId) throws Exception{

		List<PfpUser> users = ((IPfpUserDAO)dao).findAccountUsers(customerInfoId);
		
		return this.getUserVOs(users, customerInfoId);
	}
	
	public UserVO findUser(String userId, String status) throws Exception{
		
		UserVO vo = new UserVO();
		
		List<PfpUser> users = ((IPfpUserDAO)dao).findUser(userId, status);
		
		for(PfpUser user:users){
			vo.setUserId(user.getUserId());
			vo.setPrivilegeId(user.getPrivilegeId());
			vo.setCustomerInfoId(user.getPfpCustomerInfo().getCustomerInfoId());
			vo.setStatus(user.getStatus());
			vo.setUserName(user.getUserName());
			vo.setUserEmail(user.getUserEmail());
			vo.setCreateDate(DateValueUtil.getInstance().dateToString(user.getCreateDate()));
			
			for(PfpUserMemberRef ref:user.getPfpUserMemberRefs()){
				vo.setMemberId(ref.getId().getMemberId());
			}
		}
		
		return vo;
	}
		
	public PfpUser findUser(String userId) throws Exception{
		
		List<PfpUser> users = ((IPfpUserDAO)dao).findUser(userId);
		
		if(users.isEmpty()){
			return null;
		}else{
			return users.get(0);			
		}
	}
	
	public List<UserVO> searchAccountUsers(String customerInfoId, String nickName, String status) throws Exception{

		
		List<PfpUser> users = ((IPfpUserDAO)dao).searchAccountUsers(customerInfoId, nickName, status);
		
		return this.getUserVOs(users, customerInfoId);
	}

	private List<UserVO> getUserVOs(List<PfpUser> users, String customerInfoId) throws Exception{
		
		List<UserVO> userVOs = null;
		
		if(users.size() > 0){
			
			userVOs = new ArrayList<UserVO>();
			
			for(PfpUser user:users){
				
				UserVO vo = new UserVO();
				
				vo.setUserId(user.getUserId());
				vo.setCustomerInfoId(user.getPfpCustomerInfo().getCustomerInfoId());
				vo.setUserName(user.getUserName());
				vo.setPrivilegeId(user.getPrivilegeId());
				vo.setStatus(user.getStatus());
				vo.setUserEmail(user.getUserEmail());
				vo.setCreateDate(DateValueUtil.getInstance().dateToString(user.getCreateDate()));
				
				for(PfpUserMemberRef ref:user.getPfpUserMemberRefs()){
					vo.setMemberId(ref.getId().getMemberId());
				}
				
				if(user.getLastLoginDate() != null){
					vo.setLoginDate(DateValueUtil.getInstance().dateToString(user.getLastLoginDate()));
				}
											
				String today = DateValueUtil.getInstance().dateToString(new Date());
				
				long diffDay = DateValueUtil.getInstance().getDateDiffDay(DateValueUtil.getInstance().dateToString(user.getInviteDate()), today);
					
				if(EnumUserStatus.INVITE_PCID.getStatusId().equals(user.getStatus()) ||
						EnumUserStatus.INVITE_NOT_PCID.getStatusId().equals(user.getStatus())){
					
					if(diffDay > 7){
						vo.setInviteFail("Y");
					}	
					
					// 判斷這mail是否已有關鍵字廣告帳戶
					String memberId = null;
					for(PfpUserMemberRef ref:user.getPfpUserMemberRefs()){ 
						memberId = ref.getId().getMemberId();						
					}
					List<PfpUserMemberRef> userMemberRef = pfpUserMemberRefDAO.activateUserMemberRef(memberId);
					
					if(userMemberRef.size() > 0){
						
						for(PfpUserMemberRef ref:userMemberRef){
							if(!ref.getPfpUser().getPfpCustomerInfo().getCustomerInfoId().equals(user.getPfpCustomerInfo().getCustomerInfoId())){
								vo.setOtherActivate(true);
							}
						}
					}
					
				}

				userVOs.add(vo);
				
			}
			
		}
		
		
		return userVOs;
	}

	public PfpUser findApplyUser(String customerInfoId) throws Exception{
		
		List<PfpUser> users = ((IPfpUserDAO)dao).findApplyUser(customerInfoId);
		
		if(users.isEmpty()){
			return null;
		}else{
			return users.get(0);
		}
	}
	
	public PfpUser findRootUser(String customerInfoId) {
		
		List<PfpUser> users = ((IPfpUserDAO)dao).findRootUser(customerInfoId);
		
		if(users.isEmpty()){
			return null;
		}else{
			return users.get(0);
		}
	}
	
	public void saveOrUpdateWithAccesslog(PfpUser user, String pcId, String customerInfoId, String  clientIp) throws Exception{

		// 帳號異者記錄 Accesslog
		StringBuffer msg = new StringBuffer();
		
		msg.append(EnumAccesslogAction.USER_MODIFY.getMessage()).append("：");	
		
		msg.append(user.getUserName()).append(",");
		msg.append(user.getPrivilegeId()).append(",");
		msg.append(user.getStatus());
		
		Integer id = accesslogProvider.addAccesslog(EnumAccesslogChannel.PFP, 
													EnumAccesslogAction.USER_MODIFY, 
													msg.toString(), 
													pcId, 
													null, 
													customerInfoId, 
													user.getUserId(), 
													clientIp, 
													EnumAccesslogEmailStatus.NO);
		
		((IPfpUserDAO)dao).saveOrUpdate(user);		
	}	

	public List<PfpUser> checkInviteStatus(String customerInfoId, String email) throws Exception{
		return ((IPfpUserDAO)dao).checkInviteStatus(customerInfoId,email);
	}
	
	public void setAccesslogProvider(IAccesslogProvider accesslogProvider) {
		this.accesslogProvider = accesslogProvider;
	}

	public void setPfpUserMemberRefDAO(IPfpUserMemberRefDAO pfpUserMemberRefDAO) {
		this.pfpUserMemberRefDAO = pfpUserMemberRefDAO;
	}

	public List<PfpUser> findUserByConditions(Map<String, String> conditionMap) throws Exception {
		return ((IPfpUserDAO)dao).findUserByConditions(conditionMap);
	}

	public static void main(String arg[]) throws Exception{

		//ApplicationContext context = new FileSystemXmlApplicationContext(TestConfig.path);

		//Logger log = Logger.getLogger(CustomerInfoService.class);

		//PfpUserService service = (PfpUserService) context.getBean("PfpUserService");
		
		//log.info(" userId   "+service.findUserById("reantoilpc"));
		
	}

}
