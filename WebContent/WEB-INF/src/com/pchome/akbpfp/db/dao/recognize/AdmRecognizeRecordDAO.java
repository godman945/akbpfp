package com.pchome.akbpfp.db.dao.recognize;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.AdmRecognizeRecord;
import com.pchome.enumerate.recognize.EnumOrderType;

public class AdmRecognizeRecordDAO extends BaseDAO <AdmRecognizeRecord,Integer> implements IAdmRecognizeRecordDAO{
	
	@SuppressWarnings("unchecked")
	public List<AdmRecognizeRecord> findRecognizeRecords(String customerInfoId,	String recognizeOrderId, String orderType, int orderPrice){
		
		StringBuffer hql = new StringBuffer();

		hql.append(" from AdmRecognizeRecord ");
		hql.append(" where pfpCustomerInfo.customerInfoId = ? ");
		if(StringUtils.isNotBlank(recognizeOrderId)) {
			hql.append(" and recognizeOrderId = ? ");
		}
		hql.append(" and orderType = ? ");
		hql.append(" and orderPrice >= ? ");
		hql.append(" order by recognizeRecordId ");
		System.out.println("hql = " + hql);

		Object[] ob = new Object[]{customerInfoId, orderType, (float)orderPrice};
		if(StringUtils.isNotBlank(recognizeOrderId)) {
			ob = new Object[]{customerInfoId, recognizeOrderId, orderType, (float)orderPrice};
		}

		return super.getHibernateTemplate().find(hql.toString(), ob);
	}

	public void saveOrUpdateAdmRecognizeRecord(AdmRecognizeRecord admRecognizeRecord) throws Exception{
		super.getHibernateTemplate().saveOrUpdate(admRecognizeRecord);
	}

	public void insertAdmRecognizeRecord(AdmRecognizeRecord admRecognizeRecord) throws Exception {
		this.saveOrUpdate(admRecognizeRecord);
	}

	public void updateAdmRecognizeRecord(AdmRecognizeRecord admRecognizeRecord) throws Exception {
		this.update(admRecognizeRecord);
	}
	
}
