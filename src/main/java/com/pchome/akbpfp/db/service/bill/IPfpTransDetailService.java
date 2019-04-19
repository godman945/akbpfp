package com.pchome.akbpfp.db.service.bill;

import java.util.Date;
import java.util.List;

import com.pchome.akbpfp.db.pojo.PfpTransDetail;
import com.pchome.akbpfp.db.service.IBaseService;
import com.pchome.akbpfp.db.vo.bill.BillVOList;

public interface IPfpTransDetailService extends IBaseService <PfpTransDetail, String>{
	
	public List<PfpTransDetail> findAccountTransDetails(String customerInfoId, Date startDate, Date endDate) throws Exception;
	
	public BillVOList findPfpTransDetail(String customerInfoId, String startDate, String endDate);
	
}
