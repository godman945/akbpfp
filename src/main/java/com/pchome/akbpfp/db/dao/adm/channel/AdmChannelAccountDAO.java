package com.pchome.akbpfp.db.dao.adm.channel;


import org.hibernate.Session;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.AdmChannelAccount;
import com.pchome.akbpfp.db.vo.adm.channel.AdmChannelAccountVO;

public class AdmChannelAccountDAO  extends BaseDAO <AdmChannelAccount, String> implements IAdmChannelAccountDAO {

	public void InsertData(AdmChannelAccountVO admChannelAccountVO){
		final StringBuffer sql = new StringBuffer()
		.append("INSERT INTO adm_channel_account(member_id,account_id,channel_category,update_date,create_date) ")
		.append("VALUES ( :memberId")
		.append(", :accountId")
		.append(", :channelCategory")
		.append(", :updateDate")
		.append(", :createDate)");
		
		Session session = super.getHibernateTemplate().getSessionFactory().getCurrentSession();
        session.createSQLQuery(sql.toString())
        		.setString("memberId", admChannelAccountVO.getMemberId())
        		.setString("accountId", admChannelAccountVO.getAccountId())
        		.setString("channelCategory", admChannelAccountVO.getChannelCategory())
        		.setString("updateDate", admChannelAccountVO.getUpdateDate())
        		.setString("createDate", admChannelAccountVO.getCreateDate())
        		.executeUpdate();
        session.flush();
	}
}
