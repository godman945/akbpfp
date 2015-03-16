package com.pchome.akbpfp.struts2.action.account;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.pchome.akbpfp.api.CookieContentAPI;
import com.pchome.akbpfp.api.CookieProccessAPI;
import com.pchome.akbpfp.api.RedirectBillingAPI;
import com.pchome.akbpfp.db.pojo.PfpCustomerInfo;
import com.pchome.akbpfp.db.pojo.PfpOrder;
import com.pchome.akbpfp.db.pojo.PfpOrderDetail;
import com.pchome.akbpfp.db.pojo.PfpOrderDetailId;
import com.pchome.akbpfp.db.pojo.PfpUser;
import com.pchome.akbpfp.db.pojo.PfpUserMemberRef;
import com.pchome.akbpfp.db.service.accesslog.AdmAccesslogService;
import com.pchome.akbpfp.db.service.customerInfo.PfpCustomerInfoService;
import com.pchome.akbpfp.db.service.order.PfpOrderDetailService;
import com.pchome.akbpfp.db.service.order.PfpOrderService;
import com.pchome.akbpfp.db.service.sequence.SequenceService;
import com.pchome.akbpfp.db.service.user.PfpUserService;
import com.pchome.akbpfp.db.vo.account.AccountVO;
import com.pchome.akbpfp.struts2.BaseSSLAction;
import com.pchome.enumerate.apply.EnumSaveMoney;
import com.pchome.enumerate.billing.EnumBillingStatus;
import com.pchome.enumerate.cookie.EnumCookieConstants;
import com.pchome.enumerate.sequence.EnumSequenceTableName;
import com.pchome.rmi.accesslog.EnumAccesslogAction;
import com.pchome.soft.depot.utils.CookieUtil;
import com.pchome.soft.depot.utils.EncodeUtil;

public class AccountRemainAction extends BaseSSLAction{

	private PfpCustomerInfoService pfpCustomerInfoService;
	private PfpUserService pfpUserService;
	private SequenceService sequenceService;
	private PfpOrderService pfpOrderService;
	private PfpOrderDetailService pfpOrderDetailService;
	private RedirectBillingAPI redirectBillingAPI;
	private AdmAccesslogService admAccesslogService;
	
	private String billingProductId;
	private String billingProductName;
//	private float defaultPrice;
//	private float defaultTax;
//	private float minPrice;
	
	private String billingUrl;
	private AccountVO accountVO;
	private float addMoney = 0;
//	private float addTax = 0;			// 稅自行吸收, 所以無實值扣稅問題
	
	public String execute() throws Exception{
		
		this.checkRedirectSSLUrl();
		if(StringUtils.isNotBlank(this.resultType)){
			return this.resultType;
		}
		
		// 取帳戶資料
		accountVO = pfpCustomerInfoService.getAccountVO(super.getCustomer_info_id());
		// 預設資料
		accountVO.setAddMoney(EnumSaveMoney.Default.getPrice());
		accountVO.setAddTax(EnumSaveMoney.Default.getTaxMoney());	
		
		return SUCCESS;
	}
	
	@Transactional
	public String accountAddMoneyAction() throws Exception{
		
		log.info(" addMoney: "+addMoney);

		
		if(addMoney >= EnumSaveMoney.Default.getMin()){
			
			Date today = new Date();
			log.info(" infoId: "+super.getCustomer_info_id());
			
			// 帳戶加值
			PfpCustomerInfo pfpCustomerInfo = pfpCustomerInfoService.findCustomerInfo(super.getCustomer_info_id());
			
			PfpUser pfpUser = pfpUserService.findUser(super.getUser_id());
			log.info(" userId: "+super.getUser_id());
			
			float taxMoney = addMoney * (EnumSaveMoney.Default.getTaxPercent()/100);
			PfpOrder pfpOrder = new PfpOrder();
			String orderId = sequenceService.getSerialNumber(EnumSequenceTableName.ORDER);		
			
			pfpOrder.setOrderId(orderId);
			pfpOrder.setPfpCustomerInfo(pfpCustomerInfo);		
			pfpOrder.setPfpUser(pfpUser);
			pfpOrder.setOrderPrice(Float.valueOf(addMoney));
			pfpOrder.setTax(taxMoney);
			pfpOrder.setStatus(EnumBillingStatus.B101.toString());
			pfpOrder.setUpdateDate(today);
			pfpOrder.setCreateDate(today);
			pfpOrderService.saveOrUpdate(pfpOrder);
			
			PfpOrderDetailId id = new PfpOrderDetailId();
			id.setOrderId(orderId);
			id.setProductId(billingProductId);
			
			// 目前訂單只會有一項產品
			PfpOrderDetail pfpOrderDetail = new PfpOrderDetail();
			
			pfpOrderDetail.setId(id);
			pfpOrderDetail.setProducPrice(Float.valueOf(addMoney));
			pfpOrderDetail.setProductName(billingProductName);
			
			pfpOrderDetailService.saveOrUpdate(pfpOrderDetail);

			this.redirectBilling(orderId);					
		}
		
		
		return SUCCESS;
	}

	private void redirectBilling(String orderId) throws Exception{
		
		// 轉址至金流儲值  
		billingUrl = redirectBillingAPI.redirectUrl(orderId);
		
		// 金流介接  Accesslog
		String message = EnumAccesslogAction.ACCOUNT_ADD_MONEY.getMessage()+" URL："+billingUrl;
		
		// decode
		String billing = CookieUtil.getCookie(request, EnumCookieConstants.COOKIE_BILLING_BILLING_PCHOME.getValue(), 
							EnumCookieConstants.COOKIE_USING_CODE.getValue());
		String billingItem = EncodeUtil.getInstance().decryptAES(billing, EnumCookieConstants.COOKIE_BILLING_SECRET_KEY.getValue());
		
		log.info("billingItem: " + billingItem);
		
		admAccesslogService.recordBillingLog(EnumAccesslogAction.ACCOUNT_ADD_MONEY, 
												message, 
												super.getId_pchome(), 
												orderId, 
												super.getCustomer_info_id(), 
												super.getUser_id(), 
												request.getRemoteAddr());

	}

	public void setPfpCustomerInfoService(PfpCustomerInfoService pfpCustomerInfoService) {
		this.pfpCustomerInfoService = pfpCustomerInfoService;
	}

	public void setPfpUserService(PfpUserService pfpUserService) {
		this.pfpUserService = pfpUserService;
	}

	public void setSequenceService(SequenceService sequenceService) {
		this.sequenceService = sequenceService;
	}

	public void setPfpOrderService(PfpOrderService pfpOrderService) {
		this.pfpOrderService = pfpOrderService;
	}
	
	public void setPfpOrderDetailService(PfpOrderDetailService pfpOrderDetailService) {
		this.pfpOrderDetailService = pfpOrderDetailService;
	}

	public void setRedirectBillingAPI(RedirectBillingAPI redirectBillingAPI) {
		this.redirectBillingAPI = redirectBillingAPI;
	}

	public void setAdmAccesslogService(AdmAccesslogService admAccesslogService) {
		this.admAccesslogService = admAccesslogService;
	}

	public void setBillingProductId(String billingProductId) {
		this.billingProductId = billingProductId;
	}

	public void setBillingProductName(String billingProductName) {
		this.billingProductName = billingProductName;
	}

	public AccountVO getAccountVO() {
		return accountVO;
	}

	public void setAddMoney(float addMoney) {
		this.addMoney = addMoney;
	}

	public String getBillingUrl() {
		return billingUrl;
	}

//	public void setDefaultPrice(float defaultPrice) {
//		this.defaultPrice = defaultPrice;
//	}
//
//	public void setDefaultTax(float defaultTax) {
//		this.defaultTax = defaultTax;
//	}
//	
//	public void setMinPrice(float minPrice) {
//		this.minPrice = minPrice;
//	}
	
}
