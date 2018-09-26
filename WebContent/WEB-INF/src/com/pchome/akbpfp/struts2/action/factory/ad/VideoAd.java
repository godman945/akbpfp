package com.pchome.akbpfp.struts2.action.factory.ad;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pchome.akbpfp.struts2.action.ad.AdAddAction;
import com.pchome.akbpfp.struts2.action.ad.AdEditAction;
import com.pchome.akbpfp.struts2.action.intfc.ad.IAd;
import com.pchome.enumerate.ad.EnumAdVideoSizePoolType;

public class VideoAd  implements IAd {
	protected Log log = LogFactory.getLog(getClass());

	public String AdAdAddInit(AdAddAction adAddAction) throws Exception {
		log.info(">>>>>> process VideoAd");
		adAddAction.adVideoSizeMap = new HashMap<String, String>();
		for(EnumAdVideoSizePoolType enumAdVideoSize : EnumAdVideoSizePoolType.values()){
			adAddAction.adVideoSizeMap.put(enumAdVideoSize.name(), enumAdVideoSize.getWidh()+enumAdVideoSize.getHeight());
		}
		adAddAction.adStyle = adAddAction.adOperatingRule;
		return "adVideoAdd";
	}

	@Override
	public String doAdAdAdd(AdAddAction adAddAction) throws Exception {
		return null;
	}

	@Override
	public String adAdEdit(AdEditAction adEditAction) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
