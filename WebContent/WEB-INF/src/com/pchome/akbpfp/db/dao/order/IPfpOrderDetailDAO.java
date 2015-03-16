package com.pchome.akbpfp.db.dao.order;

import java.util.List;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpOrderDetail;


public interface IPfpOrderDetailDAO extends IBaseDAO<PfpOrderDetail,String>{
	
	public void saveOrUpdatePfpOrderDetail(PfpOrderDetail pfpOrderDetail) throws Exception;
	
	public List<PfpOrderDetail> findPfpOrderDetail(String orderId) throws Exception;
	
	//public void deleteOrderDetail(String orderId) throws Exception;
}
