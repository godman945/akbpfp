package com.pchome.akbpfp.db.dao.adm.channel;

import org.hibernate.Session;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.AdmChannelAccount;

public class AdmChannelAccountDAO  extends BaseDAO <AdmChannelAccount, String> implements IAdmChannelAccountDAO {

	public void InsertData(AdmChannelAccount admChannelAccount){
		final StringBuffer sql = new StringBuffer()
		.append("INSERT INTO adm_channel_account(member_id,account_id,channel_category,update_date,create_date) ")
		.append("VALUES ( :memberId")
		.append(", :accountId")
		.append(", :channelCategory")
		.append(", :updateDate")
		.append(", :createDate)");
		
		Session session = getSession();
        session.createSQLQuery(sql.toString())
        		.setString("memberId", admChannelAccount.getMemberId())
        		.setString("accountId", admChannelAccount.getAccountId())
        		.setString("channelCategory", admChannelAccount.getChannelCategory())
        		.setDate("updateDate", admChannelAccount.getUpdateDate())
        		.setDate("createDate", admChannelAccount.getCreateDate());
        		
        session.flush();
	}
}
