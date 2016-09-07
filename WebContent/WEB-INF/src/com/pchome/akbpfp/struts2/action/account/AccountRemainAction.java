package com.pchome.akbpfp.struts2.action.account;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;


import com.pchome.akbpfp.api.RedirectBillingAPI;
import com.pchome.akbpfp.db.pojo.AdmFreeGift;
import com.pchome.akbpfp.db.pojo.AdmFreeRecord;
import com.pchome.akbpfp.db.pojo.PfpCustomerInfo;
import com.pchome.akbpfp.db.pojo.PfpOrder;
import com.pchome.akbpfp.db.pojo.PfpOrderDetail;
import com.pchome.akbpfp.db.pojo.PfpOrderDetailId;
import com.pchome.akbpfp.db.pojo.PfpTransDetail;
import com.pchome.akbpfp.db.pojo.PfpUser;
import com.pchome.akbpfp.db.service.accesslog.AdmAccesslogService;
import com.pchome.akbpfp.db.service.bill.IPfpTransDetailService;
import com.pchome.akbpfp.db.service.customerInfo.PfpCustomerInfoService;
import com.pchome.akbpfp.db.service.freeAction.IAdmFreeGiftService;
import com.pchome.akbpfp.db.service.freeAction.IAdmFreeRecordService;
import com.pchome.akbpfp.db.service.order.PfpOrderDetailService;
import com.pchome.akbpfp.db.service.order.PfpOrderService;
import com.pchome.akbpfp.db.service.sequence.SequenceService;
import com.pchome.akbpfp.db.service.user.PfpUserService;
import com.pchome.akbpfp.db.vo.account.AccountVO;
import com.pchome.akbpfp.struts2.BaseSSLAction;
import com.pchome.enumerate.apply.EnumSaveMoney;
import com.pchome.enumerate.billing.EnumBillingStatus;
import com.pchome.enumerate.cookie.EnumCookieConstants;
import com.pchome.enumerate.freeAction.EnumGiftSnoPayment;
import com.pchome.enumerate.freeAction.EnumGiftSnoStyle;
import com.pchome.enumerate.freeAction.EnumGiftSnoUsed;
import com.pchome.enumerate.sequence.EnumSequenceTableName;
import com.pchome.enumerate.trans.EnumTransType;
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
	private IAdmFreeGiftService admFreeGiftService;
	private IAdmFreeRecordService admFreeRecordService;
	private IPfpTransDetailService transDetailService;
	
	private String billingProductId;
	private String billingProductName;
//	private float defaultPrice;
//	private float defaultTax;
//	private float minPrice;
	
	private String billingUrl;
	private AccountVO accountVO;
	private float addMoney = 0;
//	private float addTax = 0;			// 稅自行吸收, 所以無實值扣稅問題
	
	private String giftSno;				// 填序號送廣告金
	private float giftMoney;			// 填序號送廣告金
	
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

		Date today = new Date();
		AdmFreeGift admFreeGift = null;			
		
		log.info(">>> giftSno: "+giftSno);
		
		if(StringUtils.isNotBlank(giftSno)){
			// 檢查活動序號
			admFreeGift = admFreeGiftService.findUnusedAdmFreeGiftSno(giftSno, today,EnumGiftSnoStyle.GIFT.getStatus());
			log.info(">>> admFreeGiftId: "+admFreeGift);
			
			AdmFreeGift useHistory = admFreeGiftService.findUsedHistory(admFreeGift.getAdmFreeAction().getActionId(), super.getCustomer_info_id());
			
			if(admFreeGift != null && useHistory != null){
				return "summary";
			}
		}
		
		PfpCustomerInfo pfpCustomerInfo = pfpCustomerInfoService.findCustomerInfo(super.getCustomer_info_id());
		
		//檢查是否為共用序號
		if(StringUtils.isNotBlank(giftSno)){
			if(StringUtils.equals(admFreeGift.getAdmFreeAction().getShared(), "Y") && pfpCustomerInfo != null){
				AdmFreeRecord userRecord = admFreeRecordService.findUserRecord(admFreeGift.getAdmFreeAction().getActionId(), pfpCustomerInfo.getCustomerInfoId());
				
				//若使用者已經使用過該組序號不得再重複用
				if(userRecord != null){
					admFreeGift = null;
				}
			}
		}
		
		//禮金序號為要儲值或無禮金序號時
		if(admFreeGift == null || admFreeGift.getAdmFreeAction().getPayment().equals(EnumGiftSnoPayment.YES.getStatus())){
			if(addMoney >= EnumSaveMoney.Default.getMin()){
				
				//Date today = new Date();
				log.info(" infoId: "+super.getCustomer_info_id());
				
				// 帳戶加值
				PfpUser pfpUser = pfpUserService.findUser(super.getUser_id());
				log.info(" userId: "+super.getUser_id());
				
				float taxMoney = addMoney * (EnumSaveMoney.Default.getTaxPercent()/100);
				PfpOrder pfpOrder = new PfpOrder();
				String orderId = sequenceService.getSerialNumber(EnumSequenceTableName.ORDER);		
				
				// 更新序號使用狀態(未付款狀態先未啟用)
				if(admFreeGift != null){
					String shared = admFreeGift.getAdmFreeAction().getShared();
					
					//判斷活動是不是共用序號，不是則寫入資料到該筆序號
					if(!StringUtils.equals(shared, "Y")){
						admFreeGift.setCustomerInfoId(pfpCustomerInfo.getCustomerInfoId());
						admFreeGift.setGiftSnoStatus(EnumGiftSnoUsed.NO.getStatus());
						admFreeGift.setUpdateDate(today);
						admFreeGift.setOrderId(orderId);
						admFreeGiftService.update(admFreeGift);
					}
					
					String dataGiftSno = admFreeGift.getGiftSno();
					float dataGiftMoney = admFreeGift.getAdmFreeAction().getGiftMoney();
					
					pfpOrder.setGiftSno(dataGiftSno);
					pfpOrder.setGiftMoney(dataGiftMoney);
				}
				
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
		} else if(admFreeGift != null && StringUtils.equals(admFreeGift.getAdmFreeAction().getPayment(), EnumGiftSnoPayment.NO.getStatus()) ){
			
			//更新帳戶餘額
			float remaim = pfpCustomerInfo.getRemain() + admFreeGift.getAdmFreeAction().getGiftMoney();
			
			pfpCustomerInfo.setRemain(remaim);
			pfpCustomerInfo.setUpdateDate(today);
			pfpCustomerInfoService.saveOrUpdate(pfpCustomerInfo);
			
			// 更新序號使用狀態
			String shared = admFreeGift.getAdmFreeAction().getShared();
			
			//判斷活動是不是共用序號，不是則寫入資料到該筆序號
			if(!StringUtils.equals(shared, "Y")){
				admFreeGift.setCustomerInfoId(pfpCustomerInfo.getCustomerInfoId());
				admFreeGift.setOpenDate(today);
				admFreeGift.setGiftSnoStatus(EnumGiftSnoUsed.YES.getStatus());
				admFreeGift.setUpdateDate(today);
				admFreeGiftService.update(admFreeGift);
			}
			
			// 參與活動記錄
			AdmFreeRecord admFreeRecord = new AdmFreeRecord();
			admFreeRecord.setAdmFreeAction(admFreeGift.getAdmFreeAction());
			admFreeRecord.setCustomerInfoId(pfpCustomerInfo.getCustomerInfoId());
			admFreeRecord.setRecordDate(today);
			admFreeRecord.setUpdateDate(today);
			admFreeRecord.setCreateDate(today);
			
			admFreeRecordService.saveOrUpdate(admFreeRecord);
			
			this.createTransDetail(pfpCustomerInfo,admFreeGift.getAdmFreeAction().getGiftMoney());
			
			return "summary";
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

	/**
	 * 建立禮金交易明細
	 */
	private void createTransDetail(PfpCustomerInfo customerInfo, float giftMoney){
		
		PfpTransDetail transDetail = new PfpTransDetail();
		Date today = new Date();
		
		transDetail.setPfpCustomerInfo(customerInfo);
		transDetail.setTransDate(today);
		transDetail.setTransContent(EnumTransType.GIFT.getChName());
		transDetail.setTransType(EnumTransType.GIFT.getTypeId());
		transDetail.setIncomeExpense("+");
		transDetail.setTransPrice(giftMoney);
		transDetail.setTotalSavePrice(customerInfo.getTotalAddMoney());
		transDetail.setTotalSpendPrice(customerInfo.getTotalSpend());
		transDetail.setRemain(customerInfo.getRemain());
		transDetail.setTotalRetrievePrice(customerInfo.getTotalRetrieve());
		transDetail.setTax(0);	
		
		transDetail.setUpdateDate(today);
		transDetail.setCreateDate(today);
		
		transDetailService.saveOrUpdate(transDetail);
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

	public void setAdmFreeGiftService(IAdmFreeGiftService admFreeGiftService) {
		this.admFreeGiftService = admFreeGiftService;
	}

	public void setAdmFreeRecordService(IAdmFreeRecordService admFreeRecordService) {
		this.admFreeRecordService = admFreeRecordService;
	}

	public void setTransDetailService(IPfpTransDetailService transDetailService) {
		this.transDetailService = transDetailService;
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

	public String getGiftSno() {
		return giftSno;
	}

	public void setGiftSno(String giftSno) {
		this.giftSno = giftSno;
	}

	public float getGiftMoney() {
		return giftMoney;
	}

	public void setGiftMoney(float giftMoney) {
		this.giftMoney = giftMoney;
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
