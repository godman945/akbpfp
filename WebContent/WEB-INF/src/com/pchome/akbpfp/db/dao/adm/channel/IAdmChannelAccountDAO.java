package com.pchome.akbpfp.db.dao.adm.channel;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.AdmChannelAccount;


public interface IAdmChannelAccountDAO extends IBaseDAO<AdmChannelAccount, String> {
	
	public Integer getNewId();
	
}
