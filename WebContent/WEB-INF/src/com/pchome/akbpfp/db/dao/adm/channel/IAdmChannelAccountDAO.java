package com.pchome.akbpfp.db.dao.adm.channel;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.AdmChannelAccount;
import com.pchome.akbpfp.db.vo.adm.channel.AdmChannelAccountVO;


public interface IAdmChannelAccountDAO extends IBaseDAO<AdmChannelAccount, String> {
	
	public void InsertData(AdmChannelAccountVO AdmChannelAccountVO);
	
	public Integer getNewId();
	
}
