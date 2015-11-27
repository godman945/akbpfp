package com.pchome.akbpfp.db.service.adm.channel;

import com.pchome.akbpfp.db.pojo.AdmChannelAccount;
import com.pchome.akbpfp.db.service.IBaseService;
import com.pchome.akbpfp.db.vo.adm.channel.AdmChannelAccountVO;

public interface IAdmChannelAccountService extends IBaseService<AdmChannelAccount, String>  {
	
	public void InsertData(AdmChannelAccountVO admChannelAccountVO);
	
	public Integer getNewId();
}
