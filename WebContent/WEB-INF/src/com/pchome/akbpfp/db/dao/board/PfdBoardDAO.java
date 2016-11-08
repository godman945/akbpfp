package com.pchome.akbpfp.db.dao.board;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfdBoard;

public class PfdBoardDAO extends BaseDAO<PfdBoard,String> implements IPfdBoardDAO {

	@SuppressWarnings("unchecked")
	public List<PfdBoard> findPfdBoard(Map<String, String> conditionsMap) throws Exception {

		StringBuffer hql = new StringBuffer();
		hql.append(" from PfdBoard where 1=1");
		
		if (conditionsMap.containsKey("pfdCustomerInfoId")) {
			hql.append(" and pfdCustomerInfoId = :pfdCustomerInfoId");
		}
		if (conditionsMap.containsKey("deleteId")) {
			hql.append(" and deleteId = :deleteId");
		}
		if (conditionsMap.containsKey("content ")) {
			hql.append(" and content like :content");
		}
		

		String strHQL = hql.toString();
		log.info(">>> strHQL = " + strHQL);

		Session session = getSession();

		Query q = session.createQuery(strHQL);

		if (conditionsMap.containsKey("pfdCustomerInfoId")) {
			q.setString("pfdCustomerInfoId", conditionsMap.get("pfdCustomerInfoId"));
		}
		
		if (conditionsMap.containsKey("deleteId")) {
			q.setString("deleteId", conditionsMap.get("deleteId"));
		}
		
		if (conditionsMap.containsKey("content")) {
			q.setString("content", conditionsMap.get("content"));
		}
		
		
		return q.list();
	}
	
	public void deletePfdBoardByDeleteId(String deleteId) throws Exception {

		String hql = "delete from PfdBoard where deleteId = '" + deleteId + "'";
		Session session = getSession();
		session.createQuery(hql).executeUpdate();
		session.flush();
	}
	
}
