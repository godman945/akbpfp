package com.pchome.akbpfp.struts2.action.account;

import java.io.File;
import java.net.URLEncoder;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.pchome.akbpfp.api.InviteMailAPI;
import com.pchome.akbpfp.api.MemberAPI;
import com.pchome.akbpfp.db.pojo.PfpCustomerInfo;
import com.pchome.akbpfp.db.pojo.PfpUser;
import com.pchome.akbpfp.db.pojo.PfpUserMemberRef;
import com.pchome.akbpfp.db.pojo.PfpUserMemberRefId;
import com.pchome.akbpfp.db.service.customerInfo.PfpCustomerInfoService;
import com.pchome.akbpfp.db.service.sequence.SequenceService;
import com.pchome.akbpfp.db.service.user.PfpUserMemberRefService;
import com.pchome.akbpfp.db.service.user.PfpUserService;
import com.pchome.akbpfp.db.vo.user.UserVO;
import com.pchome.akbpfp.struts2.BaseSSLAction;
import com.pchome.enumerate.privilege.EnumPrivilegeModel;
import com.pchome.enumerate.redirect.EnumRedirectAction;
import com.pchome.enumerate.sequence.EnumSequenceTableName;
import com.pchome.enumerate.user.EnumUserStatus;
import com.pchome.soft.depot.utils.RSAUtils;
import com.pchome.soft.util.DateValueUtil;
import com.pchome.soft.util.SpringEmailUtil;

@Transactional
public class AccountUserAction extends BaseSSLAction{
	
	private static final long serialVersionUID = 1L;

	private SequenceService sequenceService;
	private MemberAPI memberAPI;	
	private InviteMailAPI inviteMailAPI;
	
	private PfpUserService pfpUserService;
	private PfpUserMemberRefService pfpUserMemberRefService;
	private PfpCustomerInfoService pfpCustomerInfoService;
	private String akbPfpServer;

	private List<UserVO> userList;
	private PfpUser pfpUser;
	
	private String nickName;
	private String memberEmail;
	private String privilege = "1";
	private String userId;
	private String status;
	private String keyword;
	private EnumPrivilegeModel[] privilegeModel = EnumPrivilegeModel.values();
	private EnumUserStatus[] userStatus = EnumUserStatus.values();
	
	private String message;
	
	public String execute() throws Exception{		
		
		this.checkRedirectSSLUrl();
		if(StringUtils.isNotBlank(this.resultType)){
			return this.resultType;
		}
		
		// 列出帳戶所有使用者
		userList = pfpUserService.findAccountUsers(super.getCustomer_info_id());
		
		return SUCCESS;
	}

	/**
	 * 邀請使用者 
	 */
	@Transactional 
	public String inviteUserAction() throws Exception {

		log.info(">>> memberEmail = " + memberEmail);
		log.info(">>> nickName = " + nickName);
		log.info(">>> privilege = " + privilege);

		if (StringUtils.isBlank(memberEmail)) {
			this.message = "請輸入使用者mail信箱！";
			return INPUT;
		}
		memberEmail = memberEmail.trim();

		if (StringUtils.isBlank(nickName)) {
			this.message = "請輸入使用者暱稱！";
			return INPUT;
		}
		nickName = nickName.trim();

		memberEmail = memberEmail.trim();		
		log.info(" memberEmail = "+memberEmail);
		
		// 確認在會員中心是否認證過
		String memberId = memberAPI.checkAvailableEmail(memberEmail);
		log.info(" memberId = "+memberId);

		// 若尚未在會員中心認證過 -> 不可使用 PChome 信箱綁定
		if(StringUtils.isBlank(memberId)){
			if (memberEmail.indexOf("@pchome.com.tw")!=-1) {
				this.message = "此 PChome Mail 無法新增為帳戶使用者，請重新輸入！";
				return INPUT;
			}
		}

		String userId = sequenceService.getSerialNumber(EnumSequenceTableName.USER);
		PfpCustomerInfo pfpCustomerInfo = pfpCustomerInfoService.findCustomerInfo(super.getCustomer_info_id());
		Date today = new Date();

		//新增使用者
		PfpUser user = new PfpUser();
		user.setUserId(userId);
		user.setPfpCustomerInfo(pfpCustomerInfo);
		user.setUserName(nickName);
		user.setUserEmail(memberEmail);
		user.setPrivilegeId(Integer.valueOf(privilege));
		if (StringUtils.isNotBlank(memberId)) { // 已經在會員中心認證過
			user.setStatus(EnumUserStatus.INVITE_PCID.getStatusId());
		} else { // 尚未在會員中心認證
			user.setStatus(EnumUserStatus.INVITE_NOT_PCID.getStatusId());
		}
		user.setInviteDate(today);
		user.setUpdateDate(today);
		user.setCreateDate(today);						
		pfpUserService.saveOrUpdate(user);

		StringBuffer keyValue = new StringBuffer();

		if (StringUtils.isNotBlank(memberId)) { // 已經在會員中心認證過

			// 新增關聯
			PfpUserMemberRefId userMemberRefId = new PfpUserMemberRefId();
			userMemberRefId.setMemberId(memberId);
			userMemberRefId.setUserId(userId);				
			PfpUserMemberRef ref = new PfpUserMemberRef();
			ref.setId(userMemberRefId);				
			pfpUserMemberRefService.saveOrUpdate(ref);

			// key = userId,mail,pcid	
			keyValue.append(user.getUserId()).append(",");
			keyValue.append(user.getUserEmail()).append(",");				
			keyValue.append(memberId);

		} else { // 尚未在會員中心認證

			pfpUserService.saveOrUpdate(user);
			
			// key = userId,mail
			keyValue.append(user.getUserId()).append(",");
			keyValue.append(user.getUserEmail());

		}
		
		log.info(">>> keyValue = " + keyValue.toString());

		StringBuffer inviteUrl = new StringBuffer();
		inviteUrl.append(akbPfpServer);
		inviteUrl.append(EnumRedirectAction.PFP_API_ACTIVATE_USER.getAction());
		inviteUrl.append("?key=");
		inviteUrl.append(URLEncoder.encode(RSAUtils.encode(keyValue.toString()),"UTF-8"));
		
		log.info(">>> inviteUrl  = " + inviteUrl.toString());
		
		// 發送邀請信件
		String mailContent = inviteMailAPI.inviteMailContent();
		log.info("mailContent>>>"+mailContent);
		try {
			if(StringUtils.isNotBlank(mailContent)){
				
				String endDate = DateValueUtil.getInstance().getDateValue(7, DateValueUtil.DBPATH);
				
				mailContent = mailContent.replace("{:accountId:}", super.getCustomer_info_id());
				mailContent = mailContent.replace("{:inviteUrl:}", inviteUrl);
				mailContent = mailContent.replace("{:endDate:}", endDate);
				
				inviteMailAPI.sendInviteMail(new String[]{user.getUserEmail()}, mailContent);
			}
		}catch(Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		

		return SUCCESS;
	}

	/**
	 * 修改管理者
	 */
	public String accountAdmModifyAction() throws Exception{
		
		if(StringUtils.isNotBlank(userId)){
			pfpUser = pfpUserService.findUser(userId);
		}
		
		return SUCCESS;
	}
	
	public String accountAdmUpdateAction() throws Exception{		
		
		if(StringUtils.isNotBlank(nickName) && StringUtils.isNotBlank(userId)){
			
			PfpUser pfpUser = pfpUserService.findUser(userId);
			
			pfpUser.setUserName(nickName);
			pfpUser.setUpdateDate(new Date());

			pfpUserService.saveOrUpdateWithAccesslog(pfpUser, super.getId_pchome(), super.getCustomer_info_id(), request.getRemoteAddr());

		}
		
		return SUCCESS;
	}

	/**
	 * 修改使用者
	 */
	public String accountUserModifyAction() throws Exception{
		
		if(StringUtils.isNotBlank(userId)){
			pfpUser = pfpUserService.findUser(userId);
		}
		
		return SUCCESS;
	}
	
	public String accountUserUpdateAction() throws Exception{		
		
		if(StringUtils.isNotBlank(nickName) && StringUtils.isNotBlank(userId) &&
				StringUtils.isNotBlank(status) &&
				StringUtils.isNotBlank(privilege)){
			
			PfpUser pfpUser = pfpUserService.findUser(userId);
			
			pfpUser.setUserName(nickName);		
			pfpUser.setStatus(status);
			pfpUser.setPrivilegeId(Integer.valueOf(privilege));
			
			pfpUser.setUpdateDate(new Date());
			pfpUserService.saveOrUpdateWithAccesslog(pfpUser, super.getId_pchome(), super.getCustomer_info_id(), request.getRemoteAddr());
		}
				
		return SUCCESS;
	}

	/**
	 * 找尋帳戶使用者
	 */
	public String accountUserSerachAction() throws Exception{

		userList = pfpUserService.searchAccountUsers(super.getCustomer_info_id(), keyword, status);
		
		return SUCCESS;
	}
	
	public void setSequenceService(SequenceService sequenceService) {
		this.sequenceService = sequenceService;
	}

	public void setMemberAPI(MemberAPI memberAPI) {
		this.memberAPI = memberAPI;
	}

	public void setInviteMailAPI(InviteMailAPI inviteMailAPI) {
		this.inviteMailAPI = inviteMailAPI;
	}

	public void setPfpUserService(PfpUserService pfpUserService) {
		this.pfpUserService = pfpUserService;
	}
	
	public void setPfpUserMemberRefService(PfpUserMemberRefService pfpUserMemberRefService) {
		this.pfpUserMemberRefService = pfpUserMemberRefService;
	}

	public void setPfpCustomerInfoService(PfpCustomerInfoService pfpCustomerInfoService) {
		this.pfpCustomerInfoService = pfpCustomerInfoService;
	}

	public void setAkbPfpServer(String akbPfpServer) {
		this.akbPfpServer = akbPfpServer;
	}

	public List<UserVO> getUserList() {
		return userList;
	}

	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public PfpUser getPfpUser() {
		return pfpUser;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public EnumPrivilegeModel[] getPrivilegeModel() {
		return privilegeModel;
	}

	public EnumUserStatus[] getUserStatus() {
		return userStatus;
	}

	public static void main(String arg[]) throws Exception{
		
		SpringEmailUtil.getInstance().setHost("staff.pchome.com.tw");
		String subject = "PChome 廣告刊登 - 帳戶邀請信件";
		String from = "rean@staff.pchome.com.tw";
		//String mail = "reantoilpc@gmail.com";
		String path = "/workspace/akbpfp/WebContent/WEB-INF/email/invite.html";
		File mailFile = new File(path);
		String mailContent = FileUtils.readFileToString(mailFile, "UTF-8");
		
		SpringEmailUtil.getInstance().sendHtmlEmail(subject, from, new String[]{"rean@staff.pchome.com.tw"}, null, mailContent);
		//System.out.println(" send "+DateValueUtil.getInstance().getDateValue(7, DateValueUtil.DBPATH));
		
	}

	public String getMessage() {
		return message;
	}

	public String getNickName() {
		return nickName;
	}

	public String getMemberEmail() {
		return memberEmail;
	}

	public String getPrivilege() {
		return privilege;
	}

	public Map<String, String> getPrivilegeOptionsMap() {

		//不需要總管理者選項
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(EnumPrivilegeModel.ADM_USER.getPrivilegeId().toString(), EnumPrivilegeModel.ADM_USER.getChName());
		map.put(EnumPrivilegeModel.AD_USER.getPrivilegeId().toString(), EnumPrivilegeModel.AD_USER.getChName());
		map.put(EnumPrivilegeModel.REPORT_USER.getPrivilegeId().toString(), EnumPrivilegeModel.REPORT_USER.getChName());
		map.put(EnumPrivilegeModel.BILL_USER.getPrivilegeId().toString(), EnumPrivilegeModel.BILL_USER.getChName());

		return map;
	}
}
