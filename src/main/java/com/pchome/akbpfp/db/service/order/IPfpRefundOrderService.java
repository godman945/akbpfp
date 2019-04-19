package com.pchome.akbpfp.db.service.order;

import java.util.List;

import com.pchome.akbpfp.db.pojo.PfpRefundOrder;
import com.pchome.akbpfp.db.service.IBaseService;

public interface IPfpRefundOrderService extends IBaseService<PfpRefundOrder,String> {
	public List<PfpRefundOrder> findPfpRefundOrder(String customerInfoId, String refundStatus) throws Exception;
}
