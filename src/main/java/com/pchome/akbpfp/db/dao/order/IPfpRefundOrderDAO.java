package com.pchome.akbpfp.db.dao.order;

import java.util.List;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpRefundOrder;

public interface IPfpRefundOrderDAO extends IBaseDAO<PfpRefundOrder,String> {
	public List<PfpRefundOrder> findPfpRefundOrder(String customerInfoId, String refundStatus) throws Exception;
}
