package com.pchome.akbpfp.db.service.order;

import java.util.LinkedHashMap;
import java.util.List;

import com.pchome.akbpfp.db.pojo.PfpOrder;
import com.pchome.akbpfp.db.pojo.PfpTransDetail;
import com.pchome.akbpfp.db.service.IBaseService;
import com.pchome.akbpfp.db.vo.member.MemberVO;
import com.pchome.akbpfp.db.vo.order.OrderVO;

public interface IPfpOrderService extends IBaseService<PfpOrder,String>{
	
	public PfpOrder getOrder(String orderId) throws Exception;
	
	public LinkedHashMap<String, Object> orderInfoForBilling(String orderId) throws Exception;
	
	public List<PfpTransDetail> getPfpOrderCost(String customerInfoId, String yesterday) throws Exception;
	
	public PfpOrder latestOrder(String customerInfoId) throws Exception;
	
}
