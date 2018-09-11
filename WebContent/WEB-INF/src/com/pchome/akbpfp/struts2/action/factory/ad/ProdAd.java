package com.pchome.akbpfp.struts2.action.factory.ad;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionSupport;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.akbpfp.struts2.action.ad.AdAddAction;
import com.pchome.akbpfp.struts2.action.intfc.ad.IAd;

public class ProdAd implements IAd {

	protected Log log = LogFactory.getLog(getClass());

	public String alex;
	
	public String AdAdAddInit(AdAddAction adAddAction) throws Exception {
		log.info(">>>>>> process ProdAd");
		System.out.println(adAddAction.getRequest());
		adAddAction.getRequest().setAttribute("alex", "FFFFF");
		
		alex ="DDDDDDDAAAAA";
		
		
		
		System.out.println(alex);
		
		
		
		return "adProdAdd";
	}

	public String getAlex() {
		return alex;
	}

	public void setAlex(String alex) {
		this.alex = alex;
	}
	
}
