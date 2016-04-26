package com.pchome.akbpfp.db.dao.freeAction;

import java.util.Date;
import java.util.List;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.AdmFreeRecord;

public interface IAdmFreeRecordDAO extends IBaseDAO<AdmFreeRecord, Integer>{
	public List<AdmFreeRecord> findAccountFree(String customerInfoId, Date startDate, Date endDate);
}
