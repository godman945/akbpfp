package com.pchome.akbpfp.db.service.order;

import java.util.List;

import com.pchome.akbpfp.db.dao.order.IPfpRefundOrderDAO;
import com.pchome.akbpfp.db.pojo.PfpRefundOrder;
import com.pchome.akbpfp.db.service.BaseService;

public class PfpRefundOrderService extends BaseService<PfpRefundOrder,String> implements IPfpRefundOrderService {

	@Override
	public List<PfpRefundOrder> findPfpRefundOrder(String customerInfoId, String refundStatus) throws Exception {
		return ((IPfpRefundOrderDAO) dao).findPfpRefundOrder(customerInfoId, refundStatus);
	}

}
