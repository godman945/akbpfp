package com.pchome.akbpfp.interceptor;



import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.json.JSONObject;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.pchome.akbpfp.api.CookieProccessAPI;
import com.pchome.akbpfp.db.pojo.PfdUserAdAccountRef;
import com.pchome.akbpfp.db.pojo.PfpBuAccount;
import com.pchome.akbpfp.db.pojo.PfpCustomerInfo;
import com.pchome.akbpfp.db.pojo.PfpUser;
import com.pchome.akbpfp.db.service.bu.PfpBuService;
import com.pchome.akbpfp.db.service.customerInfo.PfpCustomerInfoService;
import com.pchome.akbpfp.db.service.pfd.user.PfdUserAdAccountRefService;
import com.pchome.akbpfp.db.service.sequence.SequenceService;
import com.pchome.enumerate.cookie.EnumCookieConstants;
import com.pchome.enumerate.sequence.EnumSequenceTableName;
import com.pchome.soft.depot.utils.CookieFunction;
import com.pchome.soft.depot.utils.CookieUtil;
import com.pchome.soft.depot.utils.EncodeUtil;
import com.pchome.utils.HttpUtil;

public class CookieProveInterceptor extends AbstractInterceptor{

	private static final long serialVersionUID = -6002551278284848164L;	
	private CookieProccessAPI cookieProccessAPI;
	private CookieFunction cookieUtils;
	
	protected static final Logger log = LogManager.getRootLogger();

	private PfpBuService pfpBuService;
	private PfpCustomerInfoService pfpCustomerInfoService;
	private PfdUserAdAccountRefService pfdUserAdAccountRefService;
	private SequenceService sequenceService;
	private String memberServer;
	//商店街bu對應經銷商
	private String buPortalPfdc;
	//商店街bu名稱
	private String pcstoreName;
	//商店街bu Referer來源
	private String buPcstoreReferer;
	
	/**
	 * 檢查 id_pchome 是否被更改過
	 */
	@Override
	public String intercept(ActionInvocation invocation) throws Exception{
		String result = "index";
		HttpServletRequest request = ServletActionContext.getRequest();
		String id_pchome = CookieUtil.getCookie(request, EnumCookieConstants.COOKIE_MEMBER_ID_PCHOME.getValue(), EnumCookieConstants.COOKIE_USING_CODE.getValue());
		String dna_pchome = CookieUtil.getCookie(request, EnumCookieConstants.COOKIE_MEMBER_DNA_PCHOME.getValue(), EnumCookieConstants.COOKIE_USING_CODE.getValue());
		if(StringUtils.isNotEmpty(id_pchome) && StringUtils.isNotEmpty(dna_pchome)){
			String decode_dna_pchome = cookieUtils.Simple_Decode(dna_pchome);
			if(decode_dna_pchome.equals(id_pchome)){
				result = invocation.invoke();
			}
		}
		return result;
	}
	
	/*
	 * 建立BU cookie登入資料
	 * */
	public boolean createBuCookie(String pcId,HttpServletResponse response,boolean pfpIsExist,PfpCustomerInfo pfpCustomerInfo) throws Exception{
		if(pfpIsExist){
			Set<PfpUser> pfpUserSet = pfpCustomerInfo.getPfpUsers();
			String userId = "";
			String userStatus = "";
			int userPrivilege = 0;
			for (PfpUser pfpUser : pfpUserSet) {
				userId = pfpUser.getUserId();
				userStatus = pfpUser.getStatus();
				userPrivilege = pfpUser.getPrivilegeId();
			}
			PfdUserAdAccountRef pfdUserAdAccountRef = pfdUserAdAccountRefService.findPfdUserAdAccountRef(pfpCustomerInfo.getCustomerInfoId());
			String pfdc = pfdUserAdAccountRef.getPfdCustomerInfo().getCustomerInfoId();
			String pfdu = pfdUserAdAccountRef.getPfdUser().getUserId();
			Map<String,Object> pfbInfoMap = new LinkedHashMap<>();
			pfbInfoMap.put("PFP_REALITY_USER_TITLE", pfpCustomerInfo.getMemberId());
			pfbInfoMap.put("PFP_USER_ID", userId);
			pfbInfoMap.put("PFP_USER_STATUS", Integer.parseInt(userStatus));
			pfbInfoMap.put("PFP_USER_PRIVILEGE_ID", userPrivilege);
			pfbInfoMap.put("PFP_CUSTOMER_INFO_ID", pfpCustomerInfo.getCustomerInfoId());
			pfbInfoMap.put("PFP_CUSTOMER_TITLE", pfpCustomerInfo.getCustomerInfoTitle());
			pfbInfoMap.put("PFP_PAY_TYPE", Integer.parseInt(pfpCustomerInfo.getPayType()));
			pfbInfoMap.put("PFD_CUSTOMER_INFO_ID", pfdc);
			pfbInfoMap.put("PFD_USER_ID", pfdu);
			pfbInfoMap.put("MANAGER", "N");
			String encodePfpInfo = EncodeUtil.getInstance().encryptAES(pfbInfoMap.toString(), "pchomeakbpfp2012");
			//akb_pfp_user
			CookieUtil.writeCookie(response, EnumCookieConstants.COOKIE_AKBPFP_USER.getValue(), encodePfpInfo, ".pchome.com.tw", 5000, EnumCookieConstants.COOKIE_USING_CODE.getValue());
		}
		//id_pchome
		CookieUtil.writeCookie(response, EnumCookieConstants.COOKIE_MEMBER_ID_PCHOME.getValue(), pcId, ".pchome.com.tw", 5000, EnumCookieConstants.COOKIE_USING_CODE.getValue());
		//dna_pchome
		CookieFunction CookieFunction = new CookieFunction();
		String dnaValue = CookieFunction.Simple_Encode(pcId);
		CookieUtil.writeCookie(response, "dna_pchome", dnaValue, ".pchome.com.tw", 5000, EnumCookieConstants.COOKIE_USING_CODE.getValue());
		return true;
	}
	
	/*
	 * 建立BU使用者資料
	 * */
	public String createBuUser(JSONObject buInfoJson) throws Exception{
		log.info(">>>>>>>>>>create bu");
		String buId = buInfoJson.getString("buId");
		String pfdc = buInfoJson.getString("pfdc");
		String url = buInfoJson.getString("url");
		String buName = buInfoJson.getString("buName");
		
		String pfpCustomerInfoId = getPfpCustomerInfoId();
		String pcId = createPcMember(pfpCustomerInfoId);
		if(pcId.equals("ERROR")){
			return null;
		}
		Date date = new Date(); 
		PfpBuAccount pfpBuAccount = new PfpBuAccount();
		pfpBuAccount.setBuId(buId);
		pfpBuAccount.setPcId(pcId);
		pfpBuAccount.setPfdCustomerId(pfdc);
		pfpBuAccount.setBuName(buName);
		pfpBuAccount.setBuUrl(url);
		pfpBuAccount.setUpdateDate(date);
		pfpBuAccount.setCreateDate(date);
		pfpBuService.saveOrUpdate(pfpBuAccount);
		return pcId;
	}
	
	/**
	 * 取得PFP帳號
	 */
	public String getPfpCustomerInfoId() throws Exception{
		//取得pfpid
		String pfpCustomerInfoId = sequenceService.getSerialNumber(EnumSequenceTableName.ACCOUNT);
		return pfpCustomerInfoId;
	}
	
	
	/**
	 * 建立PC_ID帳號
	 */
	public String createPcMember(String pfpCustomerInfoId) throws Exception{
		String result = "ERROR";
		String jsonStr = HttpUtil.doGet(memberServer + "autoRegister4PFD.html", "pfpAcc=" + pfpCustomerInfoId.toLowerCase());
		JSONObject json = new JSONObject(jsonStr);
		log.info("Create bu >>>>>>>>>>>>>>"+json);
		if(StringUtils.isNotBlank(pfpCustomerInfoId)){
			if(StringUtils.equals(json.getString("status"), "success")){
				result = json.getString("memberId").trim();
			}
		}
		return result;
	}
	
	
	
	
	
	public void setCookieUtils(CookieFunction cookieUtils) {
		this.cookieUtils = cookieUtils;
	}


	public PfpBuService getPfpBuService() {
		return pfpBuService;
	}


	public void setPfpBuService(PfpBuService pfpBuService) {
		this.pfpBuService = pfpBuService;
	}


	public PfpCustomerInfoService getPfpCustomerInfoService() {
		return pfpCustomerInfoService;
	}


	public void setPfpCustomerInfoService(PfpCustomerInfoService pfpCustomerInfoService) {
		this.pfpCustomerInfoService = pfpCustomerInfoService;
	}


	public PfdUserAdAccountRefService getPfdUserAdAccountRefService() {
		return pfdUserAdAccountRefService;
	}


	public void setPfdUserAdAccountRefService(PfdUserAdAccountRefService pfdUserAdAccountRefService) {
		this.pfdUserAdAccountRefService = pfdUserAdAccountRefService;
	}


	public CookieProccessAPI getCookieProccessAPI() {
		return cookieProccessAPI;
	}

	public void setCookieProccessAPI(CookieProccessAPI cookieProccessAPI) {
		this.cookieProccessAPI = cookieProccessAPI;
	}

	public String getMemberServer() {
		return memberServer;
	}

	public void setMemberServer(String memberServer) {
		this.memberServer = memberServer;
	}

	public SequenceService getSequenceService() {
		return sequenceService;
	}

	public void setSequenceService(SequenceService sequenceService) {
		this.sequenceService = sequenceService;
	}

	public String getPcstoreName() {
		return pcstoreName;
	}

	public void setPcstoreName(String pcstoreName) {
		this.pcstoreName = pcstoreName;
	}

	public String getBuPortalPfdc() {
		return buPortalPfdc;
	}

	public void setBuPortalPfdc(String buPortalPfdc) {
		this.buPortalPfdc = buPortalPfdc;
	}

	public String getBuPcstoreReferer() {
		return buPcstoreReferer;
	}

	public void setBuPcstoreReferer(String buPcstoreReferer) {
		this.buPcstoreReferer = buPcstoreReferer;
	}

	
}
