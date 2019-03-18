package com.pchome.akbpfp.api;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.pchome.soft.util.SpringECcoderUtil;

public class RedirectBillingAPI {

	protected Logger log = LogManager.getRootLogger();
	
	private SpringECcoderUtil springECcoderUtil;
	
	private String billingOrderAction;
	private String channelId;
	private String password;
	private String pfpServer;


	public String redirectUrl(String orderId) throws Exception{
		
		StringBuffer pkey = new StringBuffer();
		
		log.info(" channelId = "+channelId);
		log.info(" password = "+password);
		log.info(" orderId = "+orderId);
		
		pkey.append(channelId).append(password).append(orderId);
		
		String encoder = springECcoderUtil.encoderEcString(pkey.toString());
		
		log.info(" encoder = "+encoder);
		
		this.findOrderMap(encoder);
		
		String url = billingOrderAction+"?pkey="+encoder;	
		
		log.info(" redirect billing url = "+url);
		
		return url;
	}
	
	public void findOrderMap(String pkey) throws Exception{
		
		log.info(" pkey = "+pkey);
		
		String decoder = springECcoderUtil.decoderEcString(pkey);
		log.info(" decoder = "+decoder);
		
		String orderId = decoder.substring((channelId.length()+password.length()), decoder.length());		
		log.info(" orderId = "+orderId);
		
		String url = pfpServer+"checkBillingData.html?pkey="+pkey;		
		log.info(" check billing url = "+url);
	}


	public void setSpringECcoderUtil(SpringECcoderUtil springECcoderUtil) {
		this.springECcoderUtil = springECcoderUtil;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setBillingOrderAction(String billingOrderAction) {
		this.billingOrderAction = billingOrderAction;
	}

	public void setPfpServer(String pfpServer) {
		this.pfpServer = pfpServer;
	}
	
	
}
