package com.pchome.akbpfp.db.dao.order;

import java.util.List;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpOrder;

public interface IPfpOrderDAO extends IBaseDAO<PfpOrder,String>{

	public PfpOrder getOrder(String orderId) throws Exception;
		
	public PfpOrder findApplyPfpOrder(String customerInfoId) throws Exception;
	
	public List<PfpOrder> findPfpOrder(String customerInfoId, String date) throws Exception;
	
	public List<PfpOrder> latestOrder(String customerInfoId) throws Exception;
	
	public List<PfpOrder> findPfpOrder(String orderId);
}
