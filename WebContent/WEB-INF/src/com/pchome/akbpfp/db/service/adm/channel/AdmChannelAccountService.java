package com.pchome.akbpfp.db.service.adm.channel;

import com.pchome.akbpfp.db.dao.adm.channel.IAdmChannelAccountDAO;
import com.pchome.akbpfp.db.pojo.AdmChannelAccount;
import com.pchome.akbpfp.db.service.BaseService;
import com.pchome.akbpfp.db.vo.adm.channel.AdmChannelAccountVO;

public class AdmChannelAccountService extends BaseService <AdmChannelAccount, String> implements IAdmChannelAccountService {
	
	public void InsertData(AdmChannelAccountVO admChannelAccountVO){
		((IAdmChannelAccountDAO)dao).InsertData(admChannelAccountVO);
	}
	
	public Integer getNewId(){
		return ((IAdmChannelAccountDAO)dao).getNewId();
	}
}
