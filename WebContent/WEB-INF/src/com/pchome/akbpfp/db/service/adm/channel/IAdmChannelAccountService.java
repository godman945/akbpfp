package com.pchome.akbpfp.db.service.adm.channel;

import com.pchome.akbpfp.db.pojo.AdmChannelAccount;
import com.pchome.akbpfp.db.service.IBaseService;

public interface IAdmChannelAccountService extends IBaseService<AdmChannelAccount, String>  {
	
	public void InsertData(AdmChannelAccount admChannelAccount);
	
	public Integer getNewId();
}
