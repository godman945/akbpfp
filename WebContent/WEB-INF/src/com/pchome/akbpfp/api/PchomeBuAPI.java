package com.pchome.akbpfp.api;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

import com.pchome.akbpfp.db.pojo.PfdUserAdAccountRef;
import com.pchome.akbpfp.db.pojo.PfpBuAccount;
import com.pchome.akbpfp.db.pojo.PfpCustomerInfo;
import com.pchome.akbpfp.db.pojo.PfpUser;
import com.pchome.akbpfp.db.service.bu.IPfpBuService;
import com.pchome.akbpfp.db.service.customerInfo.IPfpCustomerInfoService;
import com.pchome.akbpfp.db.service.pfd.user.PfdUserAdAccountRefService;
import com.pchome.akbpfp.db.service.sequence.SequenceService;
import com.pchome.akbpfp.db.vo.account.AccountVO;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.bu.EnumBuType;
import com.pchome.enumerate.cookie.EnumCookieConstants;
import com.pchome.enumerate.sequence.EnumSequenceTableName;
import com.pchome.soft.depot.utils.CookieFunction;
import com.pchome.soft.depot.utils.CookieUtil;
import com.pchome.soft.depot.utils.EncodeUtil;
import com.pchome.soft.depot.utils.RSAUtils;
import com.pchome.utils.HttpUtil;
public class PchomeBuAPI extends BaseCookieAction {

	private static final long serialVersionUID = 1L;
	private PfdUserAdAccountRefService pfdUserAdAccountRefService;
	private CookieProccessAPI cookieProccessAPI;
	private CookieFunction cookieUtils;
	private InputStream returnLifeCheck;
	private String encodeData;
	private String shopUrl;
	private String buIp;
	private IPfpCustomerInfoService pfpCustomerInfoService;
	private IPfpBuService pfpBuService;
	private SequenceService sequenceService;
	
	private String buSuccessUrl;
	
	private String buErrorUrl;
	
	private String memberServer;
	//商店街bu對應經銷商
	private String buPortalPfdc;
	//商店街bu名稱
	private String pcstoreName;
	//商店街bu Referer來源
	private String buPcstoreReferer;
	
	
	
	/*
	 * 3.BU加密後回傳
	 * */
	public String buKeyEncode() throws Exception{
		log.info(">>>>>> CALL BU ENCODE API IP:"+request.getRemoteAddr());
		
		String buIpArray[] = buIp.trim().split(",");
		boolean apiFlag = false;
		for (String ip : buIpArray) {
			if(ip.equals(request.getRemoteAddr())){
				apiFlag = true;
				break;
			}
		}
		
		if(!apiFlag){
			return "input";
		}
//		if(!request.getRemoteAddr().equals("220.228.8.21") && !request.getRemoteAddr().equals("220.132.64.177") && !request.getRemoteAddr().equals("113.196.35.80")){
//			return "input";
//		}
		JSONObject json = new JSONObject(encodeData);
		RSAPublicKey publicKey = (RSAPublicKey)RSAUtils.getPublicKey(RSAUtils.PUBLIC_KEY_2048);
		byte[] srcBytes = json.toString().getBytes();
	    byte[] resultBytes = RSAUtils.encrypt(publicKey, srcBytes);
	    String encodeStr = new String(Base64.encodeBase64URLSafe(resultBytes));
	    
	    JSONObject resultJson = new JSONObject();
	    resultJson.put("result", encodeStr);
		returnLifeCheck = new ByteArrayInputStream(resultJson.toString().getBytes("UTF-8"));
		return SUCCESS;
	}
	
	/**
	 * BU帳號登入用
	 * */
	public String callBulogin() throws Exception{
		log.info(">>>>>> CALL BU LOGIN API REFERER:" +request.getHeader("referer"));
		//檢查來源
		if(request.getHeader("referer") == null){
			return INPUT;
		}
		boolean pcstoreFlag = false;
		String [] buPcstoreRefererArray = buPcstoreReferer.trim().split(",");
		for (String referer : buPcstoreRefererArray) {
			if(request.getHeader("referer").contains(referer)){
				pcstoreFlag = true;
				break;
			}
		}
		if(!pcstoreFlag){
			return INPUT;
		}
		
		//檢查格式
		String buKey = request.getParameter(EnumBuType.BU_LOGIN_KEY.getKey());
		if(StringUtils.isNotBlank(buKey)){
			RSAPrivateKey privateKey = (RSAPrivateKey)RSAUtils.getPrivateKey(RSAUtils.PRIVATE_KEY_2048);
			byte[] decBytes = RSAUtils.decrypt(privateKey, Base64.decodeBase64(buKey));
			JSONObject buInfoJson = new JSONObject(new String(decBytes));
				
			String buId = buInfoJson.getString(EnumBuType.BU_ID.getKey());
			String pfdc = buInfoJson.getString(EnumBuType.BU_PFD_CUSTOMER.getKey());
			String url = buInfoJson.getString(EnumBuType.BU_URL.getKey());
			String buName = buInfoJson.getString(EnumBuType.BU_NAME.getKey());
				
			if(StringUtils.isBlank(buId) || StringUtils.isBlank(pfdc) || StringUtils.isBlank(url) || StringUtils.isBlank(buName) ){
				return INPUT;
			}else if(buName.equals(this.pcstoreName) && !pfdc.equals(this.buPortalPfdc)){
				return INPUT;
			}else if(!buName.equals(pcstoreName)){
				return INPUT;
			}
				
			//1.查詢資料庫是否有此資料
			//2.存在則查詢pfp資訊是否存在
			List<PfpBuAccount> pfpBuAccountList = pfpBuService.findPfpBuAccountByBuId(buId);
			PfpBuAccount pfpBuAccount = pfpBuAccountList.size() > 0 ? pfpBuAccountList.get(0) : null;
			if(pfpBuAccount != null){
				//檢查是pfp否開通
				AccountVO accountVO = pfpCustomerInfoService.existentAccount(pfpBuAccount.getPcId());
				if(accountVO != null && pfpBuAccount.getPfpStatus() == 0){
					pfpBuAccount.setPfpStatus(1);
					pfpBuAccount.setUpdateDate(new Date());
					pfpBuService.saveOrUpdate(pfpBuAccount);
				}
				PfpCustomerInfo pfpCustomerInfo = pfpCustomerInfoService.findCustomerInfoByMmeberId(pfpBuAccount.getPcId());
				if(pfpCustomerInfo != null){
					createBuCookie(pfpBuAccount.getPcId(),response,true,pfpCustomerInfo,buId);
					}else{
						cookieProccessAPI.deleteAllCookie(response);
						createBuCookie(pfpBuAccount.getPcId(),response,false,pfpCustomerInfo,buId);
					}
				}else{
					String pcId = createBuUser(buInfoJson);
					log.info(">>>>>bu pcId:"+pcId);
					if(StringUtils.isNotBlank(pcId)){
						createBuCookie(pcId,response,false,null,buId);
					}
				}
			}
			return SUCCESS;
		}
	
	public String bulogin() throws Exception{
		AccountVO accountVO = pfpCustomerInfoService.existentAccount(super.getId_pchome());
		if(accountVO == null){
			return "apply";
		}
		return SUCCESS;
	}
	
	/*
	 * 建立BU cookie登入資料
	 * */
	public boolean createBuCookie(String pcId,HttpServletResponse response,boolean pfpIsExist,PfpCustomerInfo pfpCustomerInfo,String buId) throws Exception{
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
			pfbInfoMap.put("PFB_BU_ACCOUND", buId);
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
	
	
	
	
	
	public InputStream getReturnLifeCheck() {
		return returnLifeCheck;
	}
	
	public void setReturnLifeCheck(InputStream returnLifeCheck) {
		this.returnLifeCheck = returnLifeCheck;
	}

	public String getEncodeData() {
		return encodeData;
	}

	public void setEncodeData(String encodeData) {
		this.encodeData = encodeData;
	}

	public IPfpBuService getPfpBuService() {
		return pfpBuService;
	}

	public void setPfpBuService(IPfpBuService pfpBuService) {
		this.pfpBuService = pfpBuService;
	}

	public String getShopUrl() {
		return shopUrl;
	}

	public void setShopUrl(String shopUrl) {
		this.shopUrl = shopUrl;
	}

	public SequenceService getSequenceService() {
		return sequenceService;
	}

	public void setSequenceService(SequenceService sequenceService) {
		this.sequenceService = sequenceService;
	}


	public IPfpCustomerInfoService getPfpCustomerInfoService() {
		return pfpCustomerInfoService;
	}

	public void setPfpCustomerInfoService(IPfpCustomerInfoService pfpCustomerInfoService) {
		this.pfpCustomerInfoService = pfpCustomerInfoService;
	}

	public String getBuIp() {
		return buIp;
	}

	public void setBuIp(String buIp) {
		this.buIp = buIp;
	}

	public String getMemberServer() {
		return memberServer;
	}


	public void setMemberServer(String memberServer) {
		this.memberServer = memberServer;
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


	public String getBuPcstoreReferer() {
		return buPcstoreReferer;
	}


	public void setBuPcstoreReferer(String buPcstoreReferer) {
		this.buPcstoreReferer = buPcstoreReferer;
	}


	public CookieProccessAPI getCookieProccessAPI() {
		return cookieProccessAPI;
	}


	public void setCookieProccessAPI(CookieProccessAPI cookieProccessAPI) {
		this.cookieProccessAPI = cookieProccessAPI;
	}


	public CookieFunction getCookieUtils() {
		return cookieUtils;
	}


	public void setCookieUtils(CookieFunction cookieUtils) {
		this.cookieUtils = cookieUtils;
	}


	public PfdUserAdAccountRefService getPfdUserAdAccountRefService() {
		return pfdUserAdAccountRefService;
	}


	public void setPfdUserAdAccountRefService(PfdUserAdAccountRefService pfdUserAdAccountRefService) {
		this.pfdUserAdAccountRefService = pfdUserAdAccountRefService;
	}


	public String getBuSuccessUrl() {
		return buSuccessUrl;
	}


	public void setBuSuccessUrl(String buSuccessUrl) {
		this.buSuccessUrl = buSuccessUrl;
	}


	public String getBuErrorUrl() {
		return buErrorUrl;
	}


	public void setBuErrorUrl(String buErrorUrl) {
		this.buErrorUrl = buErrorUrl;
	}




}