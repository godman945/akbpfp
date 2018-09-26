package com.pchome.akbpfp.struts2.action.factory.ad;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pchome.akbpfp.struts2.action.ad.AdAddAction;
import com.pchome.akbpfp.struts2.action.ad.AdEditAction;
import com.pchome.akbpfp.struts2.action.intfc.ad.IAd;
import com.pchome.enumerate.cookie.EnumCookieConstants;

public class MultimediaAd implements IAd {

	protected Log log = LogFactory.getLog(getClass());

	public String AdAdAddInit(AdAddAction adAddAction) throws Exception {
		log.info(">>>>>> process MultimediaAd");
		if(adAddAction.getAdStyle() == null){
			adAddAction.setAdStyle("TMG");
			if ("fastURLAdAdd".equals(adAddAction.getBookmark())) {
				adAddAction.getCookieUtil().writeCookie(adAddAction.getResponse(), EnumCookieConstants.COOKIE_PFPCART.getValue(), String.valueOf(new Date().getTime()), EnumCookieConstants.COOKIE_PCHOME_DOMAIN.getValue(), null);
			}
		}
		// 取出分類所屬關鍵字
		adAddAction.pfpAdKeywords = adAddAction.getPfpAdKeywordService().findAdKeywords(null, adAddAction.getAdGroupSeq(), null, null, null, "10");
		// 取出分類所屬排除關鍵字 
		adAddAction.pfpAdExcludeKeywords = adAddAction.getPfpAdExcludeKeywordService().getPfpAdExcludeKeywords(adAddAction.getAdGroupSeq(), adAddAction.getCustomer_info_id());
		if(adAddAction.pfpAdKeywords.isEmpty() && adAddAction.pfpAdExcludeKeywords.isEmpty()){
			adAddAction.setAdHiddenType("YES");
		}
		return "success";
	}

	public String doAdAdAdd(AdAddAction adAddAction) throws Exception {
		return null;
	}

	@Override
	public String adAdEdit(AdEditAction adEditAction) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
