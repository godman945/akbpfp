package com.pchome.akbpfp.db.dao.adm.channel;

import java.util.List;

import org.hibernate.Query;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.AdmChannelAccount;

public class AdmChannelAccountDAO  extends BaseDAO <AdmChannelAccount, String> implements IAdmChannelAccountDAO {

	@SuppressWarnings("unchecked")
	public Integer getNewId(){
		final StringBuffer hql = new StringBuffer()
		.append("select max(id) + 1 ")
		.append(" from AdmChannelAccount ");
		
		Query q = getSession().createQuery(hql.toString());
		
		Integer id = 0;
		List<Object> resultData = q.list();
		if(resultData != null) {
			id = Integer.parseInt(resultData.get(0).toString());
		}
		return id;
	}
}
