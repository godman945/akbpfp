package com.pchome.akbpfp.db.dao.adm.channel;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.AdmChannelAccount;
import com.pchome.akbpfp.db.vo.adm.channel.AdmChannelAccountVO;

public class AdmChannelAccountDAO  extends BaseDAO <AdmChannelAccount, String> implements IAdmChannelAccountDAO {

	public void InsertData(AdmChannelAccountVO admChannelAccountVO){
		final StringBuffer sql = new StringBuffer()
		.append("INSERT INTO adm_channel_account(id,member_id,account_id,channel_category,update_date,create_date) ")
		.append("VALUES ( :memberId")
		.append(", :accountId")
		.append(", :channelCategory")
		.append(", :updateDate")
		.append(", :createDate)");
		
		Session session = getSession();
        session.createSQLQuery(sql.toString())
        		.setString("memberId", admChannelAccountVO.getMemberId())
        		.setString("accountId", admChannelAccountVO.getAccountId())
        		.setString("channelCategory", admChannelAccountVO.getChannelCategory())
        		.setString("updateDate", admChannelAccountVO.getUpdateDate())
        		.setString("createDate", admChannelAccountVO.getCreateDate());
        		
        session.flush();
	}
	
	@SuppressWarnings("unchecked")
	public Integer getNewId(){
		final StringBuffer sql = new StringBuffer()
		.append("select max(id) + 1 ")
		.append(" from adm_channel_account ");
		
		Query q = getSession().createSQLQuery(sql.toString());
		
		Integer id = 0;
		List<Object> resultData = q.list();
		if(resultData != null) {
			id = Integer.parseInt(resultData.get(0).toString());
		}
		return id;
	}
}
