package com.pchome.akbpfp.db.dao.adm.channel;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.AdmChannelAccount;

public class AdmChannelAccountDAO  extends BaseDAO <AdmChannelAccount, String> implements IAdmChannelAccountDAO {

	public void InsertData(AdmChannelAccount admChannelAccount){
		final StringBuffer sql = new StringBuffer()
		.append("INSERT INTO adm_channel_account(id,member_id,account_id,channel_category,update_date,create_date) ")
		.append("VALUES ( :id")
		.append(", :memberId")
		.append(", :accountId")
		.append(", :channelCategory")
		.append(", :updateDate")
		.append(", :createDate)");
		
		Session session = getSession();
        session.createSQLQuery(sql.toString())
        		.setInteger("id", admChannelAccount.getId())
        		.setString("memberId", admChannelAccount.getMemberId())
        		.setString("accountId", admChannelAccount.getAccountId())
        		.setString("channelCategory", admChannelAccount.getChannelCategory())
        		.setDate("updateDate", admChannelAccount.getUpdateDate())
        		.setDate("createDate", admChannelAccount.getCreateDate());
        		
        session.flush();
	}
	
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
