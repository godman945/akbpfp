package com.pchome.akbpfp.db.dao.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfpUser;
import com.pchome.enumerate.privilege.EnumPrivilegeModel;
import com.pchome.enumerate.user.EnumUserStatus;

public class PfpUserDAO extends BaseDAO<PfpUser,String> implements IPfpUserDAO{

	@SuppressWarnings("unchecked")
	public List<PfpUser> findAccountUsers(String customerInfoId) throws Exception{
		
		StringBuffer hql = new StringBuffer();
		
		hql.append(" from PfpUser where pfpCustomerInfo.customerInfoId = ? ");
		hql.append(" and status != ? ");
		hql.append(" and status != ? ");
		hql.append(" order by userId asc ");
		
		Object[] ob = new Object[]{customerInfoId,
									EnumUserStatus.DELETE.getStatusId(),
									EnumUserStatus.APPLY.getStatusId()};
		
		return super.getHibernateTemplate().find(hql.toString(), ob);
	}

	@SuppressWarnings("unchecked")
	public List<PfpUser> findUserByConditions(Map<String, String> conditionMap) throws Exception {
		
		StringBuffer hql = new StringBuffer();
		hql.append(" from PfpUser where 1=1 ");
		if (conditionMap.containsKey("userId")) {
			hql.append(" and userId = '" + conditionMap.get("userId") + "'");
		}
		if (conditionMap.containsKey("customerInfoId")) {
			hql.append(" and pfpCustomerInfo.customerInfoId = '" + conditionMap.get("customerInfoId") + "'");
		}
		if (conditionMap.containsKey("userEmail")) {
			hql.append(" and userEmail = '" + conditionMap.get("userEmail") + "'");
		}
		if (conditionMap.containsKey("status")) {
			hql.append(" and status in (" + conditionMap.get("status") + ")");
		}
		
		return super.getHibernateTemplate().find(hql.toString());
	}

	@SuppressWarnings("unchecked")
	public List<PfpUser> findUser(String userId) throws Exception{
		
		StringBuffer hql = new StringBuffer();
		
		hql.append(" from PfpUser where userId = ? ");
		hql.append(" and status != ? ");
		hql.append(" order by userId desc ");
		
		return super.getHibernateTemplate().find(hql.toString(), userId, EnumUserStatus.DELETE.getStatusId());
	}
	
	@SuppressWarnings("unchecked")
	public List<PfpUser> findUser(String userId, String status) throws Exception{
		
		StringBuffer hql = new StringBuffer();
		
		hql.append(" from PfpUser where userId = ? ");
		hql.append(" where status = ? ");
		hql.append(" and status != ? ");
		
		return super.getHibernateTemplate().find(hql.toString(), userId, status, EnumUserStatus.DELETE.getStatusId());
	}
	
	@SuppressWarnings("unchecked")
	public List<PfpUser> searchAccountUsers(String customerInfoId, String nickName, String status) throws Exception{
		
		StringBuffer hql = new StringBuffer();
		List<Object> list = new ArrayList<Object>();
		
		hql.append(" from PfpUser ");
		hql.append(" where pfpCustomerInfo.customerInfoId = ? ");
		hql.append(" and status != ? ");
		
		list.add(customerInfoId);		
		list.add(EnumUserStatus.DELETE.getStatusId());
		
		if(StringUtils.isNotBlank(nickName)){
			hql.append(" and userName = ? ");
			list.add("%"+nickName+"%");
		}
		
		if(StringUtils.isNotBlank(status)){
			
			if(status.equals("2")){
				// 邀請已過期
				hql.append(" and (status = ? ");
				hql.append(" or status = ? ) ");
				hql.append(" and inviteDate <= ? ");
				
				list.add(EnumUserStatus.INVITE_NOT_PCID.getStatusId());
				list.add(EnumUserStatus.INVITE_PCID.getStatusId());
				list.add(new Date());
				
			}else if(status.equals("3")){
				// 尚未接受邀請
				hql.append(" and (status = ? ");
				hql.append(" or status = ? ) ");
				list.add(EnumUserStatus.INVITE_NOT_PCID.getStatusId());
				list.add(EnumUserStatus.INVITE_PCID.getStatusId());
			}else{
				hql.append(" and status = ? ");
				list.add(status);
			}
		}
		
		return super.getHibernateTemplate().find(hql.toString(), list.toArray());
	}
	
	@SuppressWarnings("unchecked")
	public List<PfpUser> checkInviteStatus(String customerInfoId, String email) throws Exception{
		
		StringBuffer hql = new StringBuffer();
		hql.append(" from PfpUser ");
		hql.append(" where pfpCustomerInfo.customerInfoId = ? ");
		hql.append(" and userEmail = ? ");
		hql.append(" and (status = '"+EnumUserStatus.INVITE_NOT_PCID.getStatusId()+"' ");
		hql.append(" or status = '"+EnumUserStatus.INVITE_PCID.getStatusId()+"') ");
		
		return super.getHibernateTemplate().find(hql.toString(),customerInfoId,email);
	}

	@SuppressWarnings("unchecked")
	public List<PfpUser> findApplyUser(String customerInfoId) throws Exception{
		
		StringBuffer hql = new StringBuffer();
		List<Object> list = new ArrayList<Object>();
		
		hql.append(" from PfpUser ");
		hql.append(" where pfpCustomerInfo.customerInfoId = ? ");
		hql.append(" and status = ? ");
		
		list.add(customerInfoId);		
		list.add(EnumUserStatus.APPLY.getStatusId());
		
		
		return super.getHibernateTemplate().find(hql.toString(), list.toArray());
	}
	
	@SuppressWarnings("unchecked")
	public List<PfpUser> findRootUser(String customerInfoId) {
		
		StringBuffer hql = new StringBuffer();
		List<Object> list = new ArrayList<Object>();
		
		hql.append(" from PfpUser ");
		hql.append(" where pfpCustomerInfo.customerInfoId = ? ");
		hql.append(" and privilegeId = ? ");
		hql.append(" order by userId ");
		
		list.add(customerInfoId);		
		list.add(EnumPrivilegeModel.ROOT_USER.getPrivilegeId());
		
		
		return super.getHibernateTemplate().find(hql.toString(), list.toArray());
	}
}
