package com.pchome.akbpfp.struts2.ajax.account;

import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.pchome.akbpfp.api.InviteMailAPI;
import com.pchome.akbpfp.api.MemberAPI;
import com.pchome.akbpfp.db.pojo.PfpUser;
import com.pchome.akbpfp.db.pojo.PfpUserMemberRef;
import com.pchome.akbpfp.db.service.user.IPfpUserMemberRefService;
import com.pchome.akbpfp.db.service.user.IPfpUserService;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.redirect.EnumRedirectAction;
import com.pchome.enumerate.user.EnumUserStatus;
import com.pchome.soft.depot.utils.RSAUtils;
import com.pchome.soft.util.DateValueUtil;

public class AccountUserAjax extends BaseCookieAction{
	
	private static final long serialVersionUID = 1L;

	private MemberAPI memberAPI;
	private InviteMailAPI inviteMailAPI;
	private IPfpUserMemberRefService pfpUserMemberRefService;
	private IPfpUserService pfpUserService;
	private String akbPfpServer;
	
	private String userId;
	private String email;
	private String accountId;
	private String checkUserEmailResult = "";

	private boolean sendMail;
	
	public String checkUserEmailAjax() throws Exception{

		if (StringUtils.isBlank(email)) {
			this.checkUserEmailResult = "請填寫電子信箱！";
			return SUCCESS;
		}
		email = email.trim();

		// 確認信箱是否在會員中心認證過
		String memberId = memberAPI.checkAvailableEmail(email);

		// 若尚未在會員中心認證過 -> 不可使用 PChome 信箱綁定
		if(StringUtils.isBlank(memberId)){
			if (email.indexOf("@pchome.com.tw")!=-1) {
				this.checkUserEmailResult = "此 PChome Mail 無法新增為帳戶使用者，請重新輸入！";
				return SUCCESS;
			}
		}

		if (memberId != null) {

			// 檢查所屬帳戶 
			List<PfpUserMemberRef> refs = pfpUserMemberRefService.activateUserMemberRef(memberId);
			
			if (refs.size() > 0) {
				this.checkUserEmailResult = "此使用者已被其他帳戶邀請，請重新輸入！";
			}

			// 檢查是否邀請過
			List<PfpUser> users = pfpUserService.checkInviteStatus(super.getCustomer_info_id(), email);
			
			if (users.size() > 0) {
				this.checkUserEmailResult = "您已邀請過該使用者了，請重新輸入！";
			}
		}
		
		return SUCCESS;
	}

	public String deleteAccountUserAjax() throws Exception{

		if(StringUtils.isNotBlank(userId)){
			
			PfpUser user = pfpUserService.findUser(userId);
			
			user.setStatus(EnumUserStatus.DELETE.getStatusId());
			user.setUpdateDate(new Date());
			
			pfpUserService.saveOrUpdateWithAccesslog(user, 
														super.getId_pchome(), 
														super.getCustomer_info_id(), 
														request.getRemoteAddr());
		}
		
		
		return SUCCESS;
	}

	/**
	 * 重寄邀請信件
	 */
	public String sendInviteMailAjax() throws Exception{
		
		sendMail = true;
		
		Date today = new Date();
		PfpUser user = pfpUserService.findUser(userId);

		Map<String, String> conditionMap = new HashMap<String, String>();
		conditionMap.put("userEmail", user.getUserEmail());

		List<PfpUser> userList = pfpUserService.findUserByConditions(conditionMap);
		
		// 檢查 email 是否已被其他帳戶邀請
		for(PfpUser pfpUser: userList){
			
			if(pfpUser.getStatus().equals(EnumUserStatus.CLOSE.getStatusId()) ||
					pfpUser.getStatus().equals(EnumUserStatus.START.getStatusId()) ||
					pfpUser.getStatus().equals(EnumUserStatus.STOP.getStatusId())){
				
				sendMail = false;
				user.setStatus(EnumUserStatus.INVALID.getStatusId());
				user.setUpdateDate(today);
				pfpUserService.saveOrUpdate(user);

				break;
			}
		}

		String keyValue = "";

		if(user != null && sendMail && user.getActivateDate() == null){
			
			if(user.getStatus().equals(EnumUserStatus.INVITE_PCID.getStatusId())){

				// 有PCId ==> key = userId,mail,pcid
				for(PfpUserMemberRef ref:user.getPfpUserMemberRefs()){
					keyValue = user.getUserId()+","+user.getUserEmail()+","+ref.getId().getMemberId();
				}
				
			}
			
			if(user.getStatus().equals(EnumUserStatus.INVITE_NOT_PCID.getStatusId())){

				// 無PCId ==> key = userId,mail
				keyValue = user.getUserId()+","+user.getUserEmail();
			}
			
			String inviteUrl = akbPfpServer+EnumRedirectAction.PFP_API_ACTIVATE_USER.getAction()+
								"?key="+URLEncoder.encode(RSAUtils.encode(keyValue),"UTF-8");
			
			// 發送邀請信件
			String mailContent = inviteMailAPI.inviteMailContent();
			
			if(StringUtils.isNotBlank(mailContent)){
				
				String endDate = DateValueUtil.getInstance().getDateValue(7, DateValueUtil.DBPATH);
				
				mailContent = mailContent.replace("{:accountId:}", super.getCustomer_info_id());
				mailContent = mailContent.replace("{:inviteUrl:}", inviteUrl);
				mailContent = mailContent.replace("{:endDate:}", endDate);
				
				inviteMailAPI.sendInviteMail(new String[]{user.getUserEmail()}, mailContent);
				
			}

			// 記錄邀請日期
			user.setInviteDate(today);
			user.setUpdateDate(today);
			pfpUserService.saveOrUpdate(user);
			
		}

		return SUCCESS;
	}

	public void setMemberAPI(MemberAPI memberAPI) {
		this.memberAPI = memberAPI;
	}

	public void setInviteMailAPI(InviteMailAPI inviteMailAPI) {
		this.inviteMailAPI = inviteMailAPI;
	}

	public void setPfpUserMemberRefService(IPfpUserMemberRefService pfpUserMemberRefService) {
		this.pfpUserMemberRefService = pfpUserMemberRefService;
	}

	public void setPfpUserService(IPfpUserService pfpUserService) {
		this.pfpUserService = pfpUserService;
	}

	public void setAkbPfpServer(String akbPfpServer) {
		this.akbPfpServer = akbPfpServer;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public boolean isSendMail() {
		return sendMail;
	}

	public String getCheckUserEmailResult() {
		return checkUserEmailResult;
	}
}
