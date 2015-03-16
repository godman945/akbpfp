package com.pchome.akbpfp.struts2.action.api;

import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

import com.pchome.akbpfp.api.MemberAPI;
import com.pchome.akbpfp.db.pojo.PfpUser;
import com.pchome.akbpfp.db.pojo.PfpUserMemberRef;
import com.pchome.akbpfp.db.pojo.PfpUserMemberRefId;
import com.pchome.akbpfp.db.service.user.IPfpUserMemberRefService;
import com.pchome.akbpfp.db.service.user.IPfpUserService;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.redirect.EnumRedirectAction;
import com.pchome.enumerate.user.EnumUserStatus;
import com.pchome.soft.depot.utils.RSAUtils;
import com.pchome.soft.util.DateValueUtil;

public class InviteAPIAction extends BaseCookieAction{

	private IPfpUserService pfpUserService;
	private IPfpUserMemberRefService pfpUserMemberRefService;
	private MemberAPI memberAPI;
	
	private String key;
	private String memberServer;
	private String akbPfpServer;
	private String memberAPIUrl;	
	private String data;
	
	public String activateUserAction() throws Exception{
		
		String result = "success";
		
		String[] userData = RSAUtils.decode(key).split(",");		
		String userId = userData[0];
		String email = userData[1];
		String inviteDate = "";
		String pcId = "";
		
		PfpUser user = pfpUserService.findUser(userId);
		
		if(user == null){
			// 邀請信失效 
			result = "inviteFail";
		}else if(user.getActivateDate() != null){
			// 已經開通過帳戶，邀請信失效
			result = "haveActivate";
		}else{
			
			if(userData.length >= 3){
				pcId = userData[2];			
			}
			
			inviteDate = DateValueUtil.getInstance().dateToString(user.getInviteDate());
			String today = DateValueUtil.getInstance().dateToString(new Date());				
			long diffDay = DateValueUtil.getInstance().getDateDiffDay(inviteDate, today);
			StringBuffer key = new StringBuffer();
			
			if(diffDay > 7){
				// 邀請過期
				return "expired";
			}
			else{
				// 返回路徑
				String backUrl = akbPfpServer+EnumRedirectAction.PFP_INDEX.getAction(); 
				// 成功回打路徑
				String apiUrl = "";	
				
				Map<String,String> memberData = new HashMap<String,String>();
				
				if(StringUtils.isNotBlank(pcId)){
					
					key.append(userId);
					apiUrl = akbPfpServer+EnumRedirectAction.PFP_API_INVITE_PCID_USER.getAction()+
								"?key="+URLEncoder.encode(RSAUtils.encode(key.toString()),"UTF-8");	
		
					// 已經是會員				
					memberData.put("webApp", "pfp");
					memberData.put("backUrl", backUrl);
					memberData.put("id", pcId);
					memberData.put("apiUrl", apiUrl);
					// 會員中心登入
					memberAPIUrl = memberServer+EnumRedirectAction.MEMBER_API_INVITE_MEMBER_LOGIN.getAction();
					
				}
				else{
					
					key.append(userId).append(",").append(email);
					apiUrl = akbPfpServer+EnumRedirectAction.PFP_API_INVITE_EMAIL_USER.getAction()+
								"?key="+URLEncoder.encode(RSAUtils.encode(key.toString()),"UTF-8");						
		
					// 還不是會員
					memberData.put("webApp", "pfp");
					memberData.put("backUrl", backUrl);
					memberData.put("email", email);
					memberData.put("apiUrl", apiUrl);
					// 會員中心註冊
					memberAPIUrl = memberServer+EnumRedirectAction.MEMBER_API_INVITE_MEMBER_REGISTER.getAction();
				}
				
				JSONObject json = new JSONObject(memberData);
				
				// 網頁 post 傳送出去 
				data = RSAUtils.encode(json.toString());

			}
						
		}
		//log.info(" result = "+result);
		
		return result;
	}
	
	public String invitePCIdUserAction() throws Exception{
		
		String result = "fail";		
		String userId = RSAUtils.decode(key);
		PfpUser user = pfpUserService.findUser(userId);		
		
		if(user != null && user.getActivateDate() == null){
			//開通帳號
			Date today = new Date();
			user.setStatus(EnumUserStatus.START.getStatusId());
			user.setActivateDate(today);
			user.setUpdateDate(today);
			pfpUserService.saveOrUpdate(user);
			
			result = "success";
		}
				
		return result;
	}
	
	public String inviteEmailUserAction() throws Exception{
		
		String result = "fail";
		
		String userItem = RSAUtils.decode(key);	
		String userId = userItem.split(",")[0];
		String userEmail = userItem.split(",")[1];
		
		PfpUser user = pfpUserService.findUser(userId);
		
		if(user != null && user.getActivateDate() == null){
			Date today = new Date();
			String pcId = memberAPI.checkAvailableEmail(user.getUserEmail());
			
			boolean haveInviteUser = false;
			// PCId 有對應帳號是開啟、關閉或停權，變更認證信箱
			List<PfpUserMemberRef> userMemberRefs = pfpUserMemberRefService.validUserMemberRef(pcId);
			for(PfpUserMemberRef ref:userMemberRefs){
				if(ref.getPfpUser().getStatus().equals(EnumUserStatus.CLOSE.getStatusId()) ||
						ref.getPfpUser().getStatus().equals(EnumUserStatus.START.getStatusId()) ||
						ref.getPfpUser().getStatus().equals(EnumUserStatus.STOP.getStatusId())){
					
					ref.getPfpUser().setUserEmail(userEmail);
					ref.getPfpUser().setUpdateDate(today);
					pfpUserMemberRefService.saveOrUpdate(ref);
					
					haveInviteUser = true;
				}
			}
			
			if(!haveInviteUser){
				
				PfpUserMemberRefId pfpUserMemberRefId = new PfpUserMemberRefId();
				pfpUserMemberRefId.setMemberId(pcId);
				pfpUserMemberRefId.setUserId(userId);
				
				PfpUserMemberRef ref = new PfpUserMemberRef();
				ref.setId(pfpUserMemberRefId);
				pfpUserMemberRefService.saveOrUpdate(ref);
				
				//開通帳號			
				user.setStatus(EnumUserStatus.START.getStatusId());
				user.setActivateDate(today);
				user.setUpdateDate(today);
				pfpUserService.saveOrUpdate(user);	
				
				result = "success";
			}else{
				// 邀請失敗
				user.setStatus(EnumUserStatus.INVALID.getStatusId());
				user.setUpdateDate(today);
				pfpUserService.saveOrUpdate(user);	
			}
			
		}
		
		return result;
	}

	public void setPfpUserService(IPfpUserService pfpUserService) {
		this.pfpUserService = pfpUserService;
	}

	public void setPfpUserMemberRefService(IPfpUserMemberRefService pfpUserMemberRefService) {
		this.pfpUserMemberRefService = pfpUserMemberRefService;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setMemberServer(String memberServer) {
		this.memberServer = memberServer;
	}

	public void setAkbPfpServer(String akbPfpServer) {
		this.akbPfpServer = akbPfpServer;
	}

	public String getMemberAPIUrl() {
		return memberAPIUrl;
	}

	public void setMemberAPI(MemberAPI memberAPI) {
		this.memberAPI = memberAPI;
	}

	public String getData() {
		return data;
	}
	
	
	public static void main(String[] arg) throws Exception{
		
		String test = "XrSMukiWXi4Apy3dpfhjasvGCf3U9f3NLS1dRMFtNutxD4NJmiM+diwT2cWglg2ZMhJdQ4hcCAq00QpBL2h83jKfV7o7FNYrtkKflvJm7sc4oHmvxVtYibZ5ai05PkdiOT6gUUvNcMCdhlKKiV8jsHEfdfKutSuWB0CPKnHLyOk=";
		System.out.println("\n  "+RSAUtils.decode(test).split(",").length);
	}
	
	
}
