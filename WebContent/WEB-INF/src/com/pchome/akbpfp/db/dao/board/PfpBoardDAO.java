package com.pchome.akbpfp.db.dao.board;

import java.util.List;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfpBoard;

public class PfpBoardDAO extends BaseDAO<PfpBoard,String> implements IPfpBoardDAO{
	
	@SuppressWarnings("unchecked")
	public List<PfpBoard> findLatestBoard(String boardType, String customerInfoId, String today) throws Exception {
		
		StringBuffer hql = new StringBuffer();
		
		hql.append(" from PfpBoard where (customerInfoId = ? or customerInfoId = 'ALL') ");
		
		if(boardType.equals("0")){
			hql.append(" and boardType != ? ");
		}
		else{
			hql.append(" and boardType = ? ");
		}

		hql.append(" and startDate <= ? ");		
		hql.append(" and endDate >= ? ");	
		
		hql.append(" order by startDate desc, boardId desc ");
		
		Object[] ob = new Object[]{customerInfoId,
									boardType,
									today,
									today};
		
		return super.getHibernateTemplate().find(hql.toString(), ob);
	}
	
	@SuppressWarnings("unchecked")
	public List<PfpBoard> findAccountRemainBoard(String boardType, String customerInfoId, String category) throws Exception {
		
		StringBuffer hql = new StringBuffer();
		
		hql.append(" from PfpBoard where customerInfoId = ? ");	
		hql.append(" and boardType = ? ");
		hql.append(" and category = ? ");	
		
		hql.append(" order by startDate desc, boardId desc ");
		
		Object[] ob = new Object[]{customerInfoId,
									boardType,
									category};
		
		return super.getHibernateTemplate().find(hql.toString(), ob);
	}
}
