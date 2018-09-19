package com.pchome.akbpfp.struts2.action.intfc.ad;

import com.pchome.akbpfp.struts2.action.ad.AdAddAction;

public interface  IAd {
	
	//進入新增廣告頁面
	public String AdAdAddInit(AdAddAction adAddAction) throws Exception;
	//新增廣告
	public String doAdAdAdd(AdAddAction adAddAction) throws Exception;
}
