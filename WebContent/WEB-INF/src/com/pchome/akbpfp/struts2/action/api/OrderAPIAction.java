package com.pchome.akbpfp.struts2.action.api;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.LinkedHashMap;

import com.pchome.akbpfp.api.ControlPriceAPI;
import com.pchome.akbpfp.db.pojo.AdmFreeGift;
import com.pchome.akbpfp.db.pojo.AdmFreeRecord;
import com.pchome.akbpfp.db.pojo.PfpCustomerInfo;
import com.pchome.akbpfp.db.pojo.PfpOrder;
import com.pchome.akbpfp.db.pojo.PfpTransDetail;
import com.pchome.akbpfp.db.service.accesslog.AdmAccesslogService;
import com.pchome.akbpfp.db.service.bill.IPfpTransDetailService;
import com.pchome.akbpfp.db.service.customerInfo.PfpCustomerInfoService;
import com.pchome.akbpfp.db.service.freeAction.IAdmFreeGiftService;
import com.pchome.akbpfp.db.service.freeAction.IAdmFreeRecordService;
import com.pchome.akbpfp.db.service.order.IPfpOrderService;
import com.pchome.akbpfp.db.service.recognize.IAdmRecognizeRecordService;
import com.pchome.akbpfp.db.service.user.PfpUserService;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.account.EnumAccountStatus;
import com.pchome.enumerate.billing.EnumBillingStatus;
import com.pchome.enumerate.freeAction.EnumGiftSnoUsed;
import com.pchome.enumerate.recognize.EnumOrderType;
import com.pchome.enumerate.trans.EnumTransType;
import com.pchome.enumerate.user.EnumUserStatus;
import com.pchome.rmi.accesslog.EnumAccesslogAction;
import com.pchome.rmi.board.IBoardProvider;
import com.pchome.rmi.mailbox.EnumCategory;
import com.pchome.soft.util.SpringECcoderUtil;


public class OrderAPIAction extends BaseCookieAction{
	
	private SpringECcoderUtil springECcoderUtil;
	private IPfpOrderService pfpOrderService;
	private PfpCustomerInfoService pfpCustomerInfoService;
	private PfpUserService pfpUserService;
	private AdmAccesslogService admAccesslogService;
	private IAdmFreeGiftService admFreeGiftService;
	private IAdmFreeRecordService admFreeRecordService;
	private IPfpTransDetailService transDetailService;
	//private IAdmRecognizeRecordService admRecognizeRecordService;
	private IBoardProvider boardProvider;
	private ControlPriceAPI controlPriceAPI;
	
	private String channelId;
	private String password;
	private String pkey;				
	private String from;							// 刷卡通知
	private String action;							// 金流刷卡狀態
	private String ordSno;							// 金流訂單編號
	private boolean STATUS = true;					// 金流必要參數
	private String MSG = "success";					// 金流必要參數
	private LinkedHashMap<String, Object> DATA;  	// 回傳訂單 json , 固定名稱不能修改
	private InputStream returnOrderStatus;			// 回傳訂單處理狀況
	
//	private PfpAdGiftService pfpAdGiftService;
//	private AdmFreeActionService admFreeActionService;
//	private AdmFreeRecordService admFreeRecordService;
	
	public String execute() throws Exception{
		return SUCCESS;
	}
	
	public String getOrderInfoAction() throws Exception{

		// 解析pkey
		String decoder = springECcoderUtil.decodeString(pkey);
		log.info(" decoder = "+decoder);

		String orderId = decoder.substring((channelId.length()+password.length()), decoder.length());		
		//log.info(" orderId = "+orderId);		
		
		// 取JSON格式訂單資料	
		DATA = pfpOrderService.orderInfoForBilling(orderId);		
		log.info(" DATA = "+DATA);
		
		// 金流取訂單資料  Accesslog 
		String message = EnumAccesslogAction.GET_JSON.getMessage()+"："+DATA;
		
		admAccesslogService.recordBillingLog(EnumAccesslogAction.GET_JSON, 
												message, 
												null, 
												orderId, 
												null, 
												null, 
												request.getRemoteAddr());

		return SUCCESS;
	}	
	
	public String billingNotifyAction() throws Exception{
		
		log.info(" pkey = "+pkey);
		// 服務系統
		log.info(" from = "+from);		
		// 訂單狀態
		//action = "B301";
		log.info(" action = "+action);
		// 金流訂單編號
		log.info(" ordSno = "+ordSno);
		
		Date today = new Date();
		
		// 解析pkey
		String decoder = springECcoderUtil.decodeString(pkey);
		log.info(" decoder = "+decoder);
		
		String orderId = decoder.substring((channelId.length()+password.length()), decoder.length());	
		log.info(" orderId  = "+orderId);
		
		// 查詢訂單資料
		PfpOrder order = pfpOrderService.getOrder(orderId);
		
		if(order != null){
			
			AdmFreeGift admFreeGift = admFreeGiftService.findAdmFreeGiftSnoByOrderId(orderId);
			
			if(action.equals(EnumBillingStatus.B301.toString()) || action.equals(EnumBillingStatus.B302.toString())){
				// 交易成功
				PfpCustomerInfo customerInfo = pfpCustomerInfoService.findCustomerInfo(order.getPfpCustomerInfo().getCustomerInfoId());

				if(customerInfo != null){
					// 確認新註冊帳戶
					if(customerInfo.getStatus().equals(EnumAccountStatus.APPLY.getStatus())){						
						// 開通帳戶
						customerInfo.setStatus(EnumAccountStatus.START.getStatus());						
						customerInfo.setActivateDate(today);
						
						// 開通帳號					
						order.getPfpUser().setStatus(EnumUserStatus.START.getStatusId());
						order.getPfpUser().setInviteDate(today);
						order.getPfpUser().setActivateDate(today);
						order.getPfpUser().setUpdateDate(today);
						pfpUserService.saveOrUpdate(order.getPfpUser());
					}
					
					float giftMoney = 0;
					if(admFreeGift != null){
						giftMoney = admFreeGift.getAdmFreeAction().getGiftMoney();
						
						// 更新廣告金序號明細(寫入啟用日期)
						admFreeGift.setGiftSnoStatus(EnumGiftSnoUsed.YES.getStatus());
						admFreeGift.setOpenDate(today);
						admFreeGift.setUpdateDate(today);
						admFreeGiftService.update(admFreeGift);
						
						// 參與活動記錄
						AdmFreeRecord admFreeRecord = new AdmFreeRecord();
						admFreeRecord.setAdmFreeAction(admFreeGift.getAdmFreeAction());
						admFreeRecord.setCustomerInfoId(customerInfo.getCustomerInfoId());
						admFreeRecord.setRecordDate(today);
						admFreeRecord.setUpdateDate(today);
						admFreeRecord.setCreateDate(today);
						
						admFreeRecordService.saveOrUpdate(admFreeRecord);
						
						//建立交易明細
						this.createTransDetail(customerInfo,admFreeGift.getAdmFreeAction().getGiftMoney());
					}
					
					// 計算帳戶餘額
					float remain = customerInfo.getRemain() + order.getOrderPrice() + giftMoney;
					customerInfo.setRemain(remain);
					customerInfo.setLaterRemain(remain);
					customerInfo.setUpdateDate(today);
					pfpCustomerInfoService.saveOrUpdate(customerInfo);
				}
				
//				// 儲值成功需在攤提記錄一筆
//				admRecognizeRecordService.createRecognizeRecord(customerInfo.getCustomerInfoId(), 
//																orderId, 
//																EnumOrderType.SAVE, 
//																order.getOrderPrice());
				
				if(customerInfo.getRemain() > 3){
					
					// 刪除帳戶餘額不足公告
					boardProvider.delete(customerInfo.getCustomerInfoId(), EnumCategory.REMAIN_NOT_ENOUGH);
				}
				
				if(customerInfo.getRemain() > 300){
					
					// 刪除帳戶餘額偏低公告
					boardProvider.delete(customerInfo.getCustomerInfoId(), EnumCategory.REMAIN_TOO_LOW);
				}
				
				// 重算調控金額
				controlPriceAPI.countProcess(customerInfo);
			}
			
			//非交易成功要修改廣告金序號明細
			if(admFreeGift != null){
				//非B101狀況下
				if(!action.equals(EnumBillingStatus.B101.toString())){
					//付款失敗情況下
					if(action.equals(EnumBillingStatus.B401.toString()) ||action.equals(EnumBillingStatus.B402.toString()) ||
							action.equals(EnumBillingStatus.B403.toString()) ||action.equals(EnumBillingStatus.B404.toString()) ||
							action.equals(EnumBillingStatus.B405.toString()) ||action.equals(EnumBillingStatus.B406.toString()) ||
							action.equals(EnumBillingStatus.B407.toString()) ||action.equals(EnumBillingStatus.B408.toString())){
						// 恢復廣告金序號明細為未使用
						admFreeGift.setCustomerInfoId(null);
						admFreeGift.setOpenDate(null);
						admFreeGift.setOrderId(null);
						admFreeGift.setGiftSnoStatus(EnumGiftSnoUsed.NO.getStatus());
						admFreeGift.setUpdateDate(today);
						admFreeGiftService.update(admFreeGift);
					} else {
						// 更新廣告金序號明細使用狀態
						admFreeGift.setGiftSnoStatus(EnumGiftSnoUsed.YES.getStatus());
						admFreeGift.setUpdateDate(today);
						admFreeGiftService.update(admFreeGift);
					}
				}	
			}
			
			/*
			if(action.equals(EnumBillingStatus.B101.toString())){
				// B101訂單成立才會有金流訂單編號
				order.setBillingId(ordSno);
			}
			*/

			// 只要通知都會有金流訂單編號
			order.setBillingId(ordSno);
			
			// 交易狀態不是B301或B302都視為失敗			
			order.setStatus(action);
			order.setUpdateDate(today);
			order.setNotifyDate(today);
			pfpOrderService.saveOrUpdate(order);
			
		}
		
		// 金流通知 Accesslog	
		String message = EnumAccesslogAction.STATUS_NOTIFY.getMessage()+"："+action;
		for(EnumBillingStatus status:EnumBillingStatus.values()){
			if(action.equals(status.toString())){
				message +="﹝"+status.getMessage()+"﹞";
			}
		}
		
		admAccesslogService.recordBillingLog(EnumAccesslogAction.STATUS_NOTIFY, 
												message, 
												null, 
												orderId, 
												null, 
												null, 
												request.getRemoteAddr());		
		
		// 回傳狀態
		returnOrderStatus = new ByteArrayInputStream("ok".getBytes("UTF-8"));	
		
		
		return SUCCESS;
	}
	
	public String checkBillingJSON() throws Exception{		

		log.info(" pkey = "+pkey);

		String decoder = springECcoderUtil.decodeString(pkey);
		log.info(" decoder = "+decoder);

		String orderId = decoder.substring((channelId.length()+password.length()), decoder.length());		
		log.info(" orderId = "+orderId);		
		
		// 取JSON格式訂單資料	
		DATA = pfpOrderService.orderInfoForBilling(orderId);
		
		return SUCCESS;
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
	
	public void setSpringECcoderUtil(SpringECcoderUtil springECcoderUtil) {
		this.springECcoderUtil = springECcoderUtil;
	}

	public void setPfpOrderService(IPfpOrderService pfpOrderService) {
		this.pfpOrderService = pfpOrderService;
	}

	public void setPfpCustomerInfoService(PfpCustomerInfoService pfpCustomerInfoService) {
		this.pfpCustomerInfoService = pfpCustomerInfoService;
	}

	public void setPfpUserService(PfpUserService pfpUserService) {
		this.pfpUserService = pfpUserService;
	}

	public void setAdmAccesslogService(AdmAccesslogService admAccesslogService) {
		this.admAccesslogService = admAccesslogService;
	}

//	public void setAdmRecognizeRecordService(IAdmRecognizeRecordService admRecognizeRecordService) {
//		this.admRecognizeRecordService = admRecognizeRecordService;
//	}

	public void setBoardProvider(IBoardProvider boardProvider) {
		this.boardProvider = boardProvider;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPkey(String pkey) {
		this.pkey = pkey;
	}
	
	public void setFrom(String from) {
		this.from = from;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void setOrdSno(String ordSno) {
		this.ordSno = ordSno;
	}

	public boolean isSTATUS() {
		return STATUS;
	}

	public String getMSG() {
		return MSG;
	}

	public LinkedHashMap<String, Object> getDATA() {
		return DATA;
	}

	public InputStream getReturnOrderStatus() {
		return returnOrderStatus;
	}

	public void setControlPriceAPI(ControlPriceAPI controlPriceAPI) {
		this.controlPriceAPI = controlPriceAPI;
	}

//	public void setAdmFreeActionService(AdmFreeActionService admFreeActionService) {
//		this.admFreeActionService = admFreeActionService;
//	}
//
//	public void setAdmFreeRecordService(AdmFreeRecordService admFreeRecordService) {
//		this.admFreeRecordService = admFreeRecordService;
//	}
//
//	public void setPfpAdGiftService(PfpAdGiftService pfpAdGiftService) {
//		this.pfpAdGiftService = pfpAdGiftService;
//	}

	public void setAdmFreeGiftService(IAdmFreeGiftService admFreeGiftService) {
		this.admFreeGiftService = admFreeGiftService;
	}

	public void setAdmFreeRecordService(IAdmFreeRecordService admFreeRecordService) {
		this.admFreeRecordService = admFreeRecordService;
	}

	public void setTransDetailService(IPfpTransDetailService transDetailService) {
		this.transDetailService = transDetailService;
	}

	public static void main(String age[]) throws Exception{
		//NDExNHk%2BWUNaQFxCXkRgRnhgWz5aQVlAWURZPlk%2BWT5ZPllC%0D%0A
		//NDExNHk%2BWUNaQFxCXkRgRnhgWz5aQVlAWURZPlk%2BWT5ZPllC%0D%0A
		SpringECcoderUtil springECcoderUtil =  new SpringECcoderUtil();
		String decode = springECcoderUtil.decoderEcString("NDExNHk+WUNaQFxCXkRgRnhgWz5aQVlAWURZPlk+WT5ZPllC");
		System.out.println("\n decode = "+decode);
	}

}
