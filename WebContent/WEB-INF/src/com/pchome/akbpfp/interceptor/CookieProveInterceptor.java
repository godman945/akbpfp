package com.pchome.akbpfp.interceptor;



import java.security.interfaces.RSAPrivateKey;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import com.pchome.enumerate.bu.EnumBuType;
import com.pchome.enumerate.cookie.EnumCookieConstants;
import com.pchome.enumerate.sequence.EnumSequenceTableName;
import com.pchome.soft.depot.utils.CookieFunction;
import com.pchome.soft.depot.utils.CookieUtil;
import com.pchome.soft.depot.utils.EncodeUtil;
import com.pchome.soft.depot.utils.RSAUtils;
import com.pchome.utils.HttpUtil;

public class CookieProveInterceptor extends AbstractInterceptor{

	private static final long serialVersionUID = -6002551278284848164L;	
	private CookieProccessAPI cookieProccessAPI;
	private CookieFunction cookieUtils;
	
	protected static final Log log = LogFactory.getLog(CookieProveInterceptor.class);

	private PfpBuService pfpBuService;
	private PfpCustomerInfoService pfpCustomerInfoService;
	private PfdUserAdAccountRefService pfdUserAdAccountRefService;
	private SequenceService sequenceService;
	private String buPcstoreReferer;
	private String memberServer;
	private String buPortalPfdc;
	private String pcstoreName;
	private String rutenName;
	
	/**
	 * 檢查 id_pchome 是否被更改過
	 */
	@Override
	public String intercept(ActionInvocation invocation) throws Exception{
		String result = "index";
		
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		
		/*BU LOGIN START*/
		String buKey = request.getParameter(EnumBuType.BU_LOGIN_KEY.getKey());
		if(StringUtils.isNotBlank(buKey)){
			log.info(">>>>>> CALL BU LOGIN REFERER:"+request.getHeader("referer"));
//			if(request.getHeader("referer") == null || request.getHeader("referer").indexOf("adm.pcstore.com.tw") < 0){
//				return "index";
//			}
			
			
			RSAPrivateKey privateKey = (RSAPrivateKey)RSAUtils.getPrivateKey(RSAUtils.PRIVATE_KEY_2048);
			byte[] decBytes = RSAUtils.decrypt(privateKey, Base64.decodeBase64(buKey));
			JSONObject buInfoJson = new JSONObject(new String(decBytes));
			
			String buId = buInfoJson.getString(EnumBuType.BU_ID.getKey());
			String pfdc = buInfoJson.getString(EnumBuType.BU_PFD_CUSTOMER.getKey());
			String url = buInfoJson.getString(EnumBuType.BU_URL.getKey());
			String buName = buInfoJson.getString(EnumBuType.BU_NAME.getKey());
			
			if(StringUtils.isBlank(buId) || StringUtils.isBlank(pfdc) || StringUtils.isBlank(url) || StringUtils.isBlank(buName)){
				result = invocation.invoke();
				return result;
			}
			
			if(!buName.equals(rutenName) && !buName.equals(pcstoreName)){
				result = invocation.invoke();
				return result;
			}
			
			if(buName.equals(this.pcstoreName) && !pfdc.equals(this.buPortalPfdc)){
				result = invocation.invoke();
				return result;
			}

			if(buName.equals(pcstoreName)){
				boolean pcstoreFlag = false;
				String [] buPcstoreRefererArray = buPcstoreReferer.trim().split(",");
				for (String referer : buPcstoreRefererArray) {
					if(request.getHeader("referer").indexOf(referer) >= 0){
						pcstoreFlag = true;
						break;
					}
				}
				if(!pcstoreFlag){
					result = invocation.invoke();
					return result;
				}	
			}
			
			//1.查詢資料庫是否有此資料
			//2.存在則查詢pfp資訊是否存在
			List<PfpBuAccount> pfpBuAccountList = pfpBuService.findPfpBuAccountByBuId(buId);
			PfpBuAccount pfpBuAccount = pfpBuAccountList.size() > 0 ? pfpBuAccountList.get(0) : null;
			if(pfpBuAccount != null){
				PfpCustomerInfo pfpCustomerInfo = pfpCustomerInfoService.findCustomerInfoByMmeberId(pfpBuAccount.getPcId());
				if(pfpCustomerInfo != null){
					createBuCookie(pfpBuAccount.getPcId(),response,true,pfpCustomerInfo);
				}else{
					cookieProccessAPI.deleteAllCookie(response);
					createBuCookie(pfpBuAccount.getPcId(),response,false,pfpCustomerInfo);
				}
			}else{
				String pcId = createBuUser(buInfoJson);
				if(StringUtils.isNotBlank(pcId)){
					createBuCookie(pcId,response,false,null);
				}
			}
			result = invocation.invoke();
			return result;
		}
		/*BU LOGIN END*/
		
		String id_pchome = CookieUtil.getCookie(request, EnumCookieConstants.COOKIE_MEMBER_ID_PCHOME.getValue(), 
							EnumCookieConstants.COOKIE_USING_CODE.getValue());
		String dna_pchome = CookieUtil.getCookie(request, EnumCookieConstants.COOKIE_MEMBER_DNA_PCHOME.getValue(), 
							EnumCookieConstants.COOKIE_USING_CODE.getValue());
		
		//log.info(" id_pchome: "+id_pchome);
		
		if(StringUtils.isNotEmpty(id_pchome) && StringUtils.isNotEmpty(dna_pchome)){
		
			String decode_dna_pchome = cookieUtils.Simple_Decode(dna_pchome);
			//log.info(" dna_pchome: "+decode_dna_pchome);
			
			if(decode_dna_pchome.equals(id_pchome)){
			
				result = invocation.invoke();
			}
		
		}
		
		//log.info(" result: "+result);		
		
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

	public String getBuPortalPfdc() {
		return buPortalPfdc;
	}

	public void setBuPortalPfdc(String buPortalPfdc) {
		this.buPortalPfdc = buPortalPfdc;
	}

	public String getPcstoreName() {
		return pcstoreName;
	}

	public void setPcstoreName(String pcstoreName) {
		this.pcstoreName = pcstoreName;
	}

	public String getRutenName() {
		return rutenName;
	}

	public void setRutenName(String rutenName) {
		this.rutenName = rutenName;
	}

	public String getBuPcstoreReferer() {
		return buPcstoreReferer;
	}

	public void setBuPcstoreReferer(String buPcstoreReferer) {
		this.buPcstoreReferer = buPcstoreReferer;
	}
	
}
