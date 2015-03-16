package com.pchome.akbpfp.db.service.order;


import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import com.pchome.akbpfp.api.MemberAPI;
import com.pchome.akbpfp.db.dao.bill.PfpTransDetailDAO;
import com.pchome.akbpfp.db.dao.order.IPfpOrderDAO;
import com.pchome.akbpfp.db.dao.order.PfpOrderDAO;
import com.pchome.akbpfp.db.dao.user.PfpUserDAO;
import com.pchome.akbpfp.db.pojo.PfpOrder;
import com.pchome.akbpfp.db.pojo.PfpOrderDetail;
import com.pchome.akbpfp.db.pojo.PfpTransDetail;
import com.pchome.akbpfp.db.pojo.PfpUserMemberRef;
import com.pchome.akbpfp.db.service.BaseService;
import com.pchome.akbpfp.db.vo.member.MemberVO;
import com.pchome.config.TestConfig;
import com.pchome.enumerate.account.EnumAccountFinance;
import com.pchome.enumerate.billing.EnumBillingField;
import com.pchome.enumerate.trans.EnumTransType;
import com.pchome.soft.util.DateValueUtil;

@Transactional
public class PfpOrderService extends BaseService<PfpOrder,String> implements IPfpOrderService{

	private MemberAPI memberAPI;
	private PfpUserDAO pfpUserDAO;
	
	private PfpTransDetailDAO pfpTransDetailDAO;
	private String akbPfpServerUrl;
	
	public PfpOrder getOrder(String orderId) throws Exception{
		return ((IPfpOrderDAO)dao).getOrder(orderId);
	}
	
	public LinkedHashMap<String, Object> orderInfoForBilling(String orderId) throws Exception{
		
		/**
		 * 1. 訂單 userId 是帳戶擁有者Id, 並不是業務id
		 * 2. 接金流時, 給訂單資料要給業務的 memberId, 否則無法介接
		 */
		
		List<PfpOrder> orders = ((IPfpOrderDAO)dao).findPfpOrder(orderId);
		LinkedHashMap<String, Object> data = null;
		
		log.info(" orderId = "+orderId);		
		log.info(" orders = "+orders);
		
		if(!orders.isEmpty()){
			
			PfpOrder order = orders.get(0);
			
			// 取業務的UserId
			String memberId = null;
			for(PfpUserMemberRef ref:order.getPfpUser().getPfpUserMemberRefs()){
				memberId = ref.getId().getMemberId();
			}
			
			LinkedHashMap<String, Object> detailMap = new LinkedHashMap<String, Object>();
			
			String productId = "";
			
			// 訂單產品編號
			for(PfpOrderDetail detail:order.getPfpOrderDetails()){
				productId = detail.getId().getProductId();
			}
			
			detailMap.put(EnumBillingField.BPD_ID.toString(), productId);
			detailMap.put(EnumBillingField.CPD_ID.toString(), productId);
			detailMap.put(EnumBillingField.PD_ATTB.toString(), EnumBillingField.PD_ATTB.getValue());
			detailMap.put(EnumBillingField.PD_QTY.toString(), EnumBillingField.PD_QTY.getValue());
			detailMap.put(EnumBillingField.PD_UNTPRI.toString(), order.getOrderPrice()+order.getTax());
			detailMap.put(EnumBillingField.PD_TOTPRI.toString(), order.getOrderPrice()+order.getTax());
			detailMap.put(EnumBillingField.MONEY.toString(), new LinkedHashMap[]{});
			
			// 取會員資料
			MemberVO memberVO = null;
			try {
				memberVO = memberAPI.getMemberVOData(memberId);
			} catch (Exception e) {
				log.info(" error = "+e);
			}
			
			if(memberVO != null){
				
				data = new LinkedHashMap<String, Object>();
				data.put(EnumBillingField.MEM_ID.toString(), memberId);
				data.put(EnumBillingField.TOTAL_PRICE.toString(), order.getOrderPrice()+order.getTax());
				data.put(EnumBillingField.USER_NAME.toString(), memberVO.getMemberName());
				data.put(EnumBillingField.USER_MAIL.toString(), memberVO.getMemberCheckMail());
				data.put(EnumBillingField.USER_SEX.toString(), memberVO.getMemberSex());
				data.put(EnumBillingField.USER_TEL.toString(), memberVO.getMemberTelephone());
				data.put(EnumBillingField.USER_MOBILE.toString(), memberVO.getMemberMobile()); 
				data.put(EnumBillingField.USER_ZIP.toString(), memberVO.getMemberZip());
				data.put(EnumBillingField.USER_ADDR.toString(), memberVO.getMemberAddress());
				data.put(EnumBillingField.CH_BTN_TEXT.toString(), EnumBillingField.CH_BTN_TEXT.getValue());
				data.put(EnumBillingField.CH_URL.toString(), akbPfpServerUrl);
				data.put(EnumBillingField.DETAIL.toString(), new LinkedHashMap[]{detailMap});
				
			}
		}
		
		
		
//		LinkedHashMap<String, Object> data = null;
//		
//		PfpOrder order = ((PfpOrderDAO)dao).getOrder(orderId);
//		log.info(" orderId = "+orderId);
//		log.info(" order = "+order);
//		if(order != null){
//			
//			LinkedHashMap<String, Object> detailMap = new LinkedHashMap<String, Object>();
//			
//			String productId = "";
//			// 訂單產品編號
//			for(PfpOrderDetail detail:order.getPfpOrderDetails()){
//				productId = detail.getId().getProductId();
//			}
//			
//			detailMap.put(EnumBillingField.BPD_ID.toString(), productId);
//			detailMap.put(EnumBillingField.CPD_ID.toString(), productId);
//			detailMap.put(EnumBillingField.PD_ATTB.toString(), EnumBillingField.PD_ATTB.getValue());
//			detailMap.put(EnumBillingField.PD_QTY.toString(), EnumBillingField.PD_QTY.getValue());
//			detailMap.put(EnumBillingField.PD_UNTPRI.toString(), order.getOrderPrice()+order.getTax());
//			detailMap.put(EnumBillingField.PD_TOTPRI.toString(), order.getOrderPrice()+order.getTax());
//			detailMap.put(EnumBillingField.MONEY.toString(), new LinkedHashMap[]{});
//			
//			data = new LinkedHashMap<String, Object>();
//			
//			List<PfpUser> users = pfpUserDAO.findUser(order.getPfpUser().getUserId());
//			PfpUser user = null;
//			if(users.size() > 0){
//				user = users.get(0);
//
//				String memberId = "";
//				// 使用者會員中心帳號
//				for(PfpUserMemberRef userRef:user.getPfpUserMemberRefs()){
//					memberId = userRef.getId().getMemberId();
//				}
//				// 會員中心資訊
//				MemberVO memberVO = memberAPI.getMemberVOData(memberId);
//				
//				data.put(EnumBillingField.MEM_ID.toString(), memberId);
//				data.put(EnumBillingField.TOTAL_PRICE.toString(), order.getOrderPrice()+order.getTax());
//				data.put(EnumBillingField.USER_NAME.toString(), memberVO.getMemberName());
//				data.put(EnumBillingField.USER_MAIL.toString(), memberVO.getMemberCheckMail());
//				data.put(EnumBillingField.USER_SEX.toString(), memberVO.getMemberSex());
//				data.put(EnumBillingField.USER_TEL.toString(), memberVO.getMemberTelephone());
//				data.put(EnumBillingField.USER_MOBILE.toString(), memberVO.getMemberMobile()); 
//				data.put(EnumBillingField.USER_ZIP.toString(), memberVO.getMemberZip());
//				data.put(EnumBillingField.USER_ADDR.toString(), memberVO.getMemberAddress());
//				data.put(EnumBillingField.CH_BTN_TEXT.toString(), EnumBillingField.CH_BTN_TEXT.getValue());
//				data.put(EnumBillingField.CH_URL.toString(), akbPfpServerUrl);
//				data.put(EnumBillingField.DETAIL.toString(), new LinkedHashMap[]{detailMap});
//			}
//		}
			
		return data;
	}
	
	public List<PfpTransDetail> getPfpOrderCost(String customerInfoId, String yesterday) throws Exception{
		
		List<PfpOrder> pfpOrders = ((PfpOrderDAO)dao).findPfpOrder(customerInfoId, yesterday);
		List<PfpTransDetail> transDetails = null;
		
		if(pfpOrders != null){
			
			transDetails = new ArrayList<PfpTransDetail>();
			
			// 取上一筆交易餘額
			PfpTransDetail lastTransDetail = pfpTransDetailDAO.findLastTransDetail(customerInfoId);
			float lastRemain = 0;
			if(lastTransDetail != null){
				lastRemain = lastTransDetail.getRemain();
			}
			
			
			for(PfpOrder order:pfpOrders){
				
				PfpTransDetail transDetail = new PfpTransDetail();
				//DateValueUtil.getInstance().getDateValue(DateValueUtil.YESTERDAY, DateValueUtil.DBPATH);
				transDetail.setTransDate(DateValueUtil.getInstance().stringToDate(yesterday));
				transDetail.setPfpCustomerInfo(order.getPfpCustomerInfo());
				transDetail.setTransPrice(order.getOrderPrice());
				transDetail.setTransType(EnumTransType.ADD_MONEY.getTypeId());
				transDetail.setIncomeExpense(EnumAccountFinance.INCOME.getType());
				//transDetail.setIsCount("N");
				lastRemain += order.getOrderPrice();
				transDetail.setRemain(lastRemain);
				
				transDetail.setTax(order.getTax());
				
				for(PfpOrderDetail orderDetail:order.getPfpOrderDetails()){
					transDetail.setTransContent(orderDetail.getProductName());
				}
				
				Date today = new Date();
				transDetail.setCreateDate(today);
				transDetail.setUpdateDate(today);
				
				transDetails.add(transDetail);
			}
		}
		
		return transDetails;
	}

	public PfpOrder latestOrder(String customerInfoId) throws Exception{
		PfpOrder order = null;
		List<PfpOrder> orders = ((PfpOrderDAO)dao).latestOrder(customerInfoId);
		
		if(orders.size() > 0){
			order = orders.get(0);
		}
		return order;
	}
	
	public void setMemberAPI(MemberAPI memberAPI) {
		this.memberAPI = memberAPI;
	}

	public void setPfpUserDAO(PfpUserDAO pfpUserDAO) {
		this.pfpUserDAO = pfpUserDAO;
	}

	public void setPfpTransDetailDAO(PfpTransDetailDAO pfpTransDetailDAO) {
		this.pfpTransDetailDAO = pfpTransDetailDAO;
	}

	public void setAkbPfpServerUrl(String akbPfpServerUrl) {
		this.akbPfpServerUrl = akbPfpServerUrl;
	}

	public static void main(String arg[]) throws Exception{

		ApplicationContext context = new FileSystemXmlApplicationContext(TestConfig.path);

		Logger log = Logger.getLogger(PfpOrderService.class);

		PfpOrderService service = (PfpOrderService) context.getBean("PfpOrderService");
		
		String date = DateValueUtil.getInstance().getDateValue(DateValueUtil.YESTERDAY, DateValueUtil.DBPATH);
		
	
		//AccountVO vo = service .findRegisterDataById("reantoilpc");
		//log.info("\n   size  =  "+service.checkOrderDetail("AC201303060000000023", date).size());
	}
	

}
