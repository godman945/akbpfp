package com.pchome.akbpfp.db.dao.bill;

import java.util.Date;
import java.util.List;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpTransDetail;

public interface IPfpTransDetailDAO extends IBaseDAO<PfpTransDetail, String>{
	
	public PfpTransDetail findLastTransDetail(String customerInfoId) throws Exception;
	
	public List<PfpTransDetail> findAccountTransDetails(String customerInfoId, Date startDate, Date endDate);
	

}
