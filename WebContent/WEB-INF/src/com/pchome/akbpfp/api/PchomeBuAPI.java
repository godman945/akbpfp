package com.pchome.akbpfp.api;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.interfaces.RSAPublicKey;
import java.util.Enumeration;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;

import com.pchome.akbpfp.db.service.bu.IPfpBuService;
import com.pchome.akbpfp.db.service.customerInfo.IPfpCustomerInfoService;
import com.pchome.akbpfp.db.service.sequence.SequenceService;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.soft.depot.utils.RSAUtils;

public class PchomeBuAPI extends BaseCookieAction {

	private static final long serialVersionUID = 1L;
	
	
	private InputStream returnLifeCheck;
	
	private String encodeData;
	private String shopUrl;
	private String buIp;
	
	private IPfpCustomerInfoService pfpCustomerInfoService;
	private IPfpBuService pfpBuService;
	private SequenceService sequenceService;
	
	
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


}