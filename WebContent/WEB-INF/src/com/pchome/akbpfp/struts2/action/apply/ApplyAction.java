package com.pchome.akbpfp.struts2.action.apply;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.pchome.akbpfd.db.vo.user.PfdUserAdAccountRefVO;
import com.pchome.akbpfp.api.MemberAPI;
import com.pchome.akbpfp.api.RedirectBillingAPI;
import com.pchome.akbpfp.db.pojo.AdmFreeGift;
import com.pchome.akbpfp.db.pojo.AdmFreeRecord;
import com.pchome.akbpfp.db.pojo.PfdUserAdAccountRef;
import com.pchome.akbpfp.db.pojo.PfpBuAccount;
import com.pchome.akbpfp.db.pojo.PfpCustomerInfo;
import com.pchome.akbpfp.db.pojo.PfpOrder;
import com.pchome.akbpfp.db.pojo.PfpOrderDetail;
import com.pchome.akbpfp.db.pojo.PfpOrderDetailId;
import com.pchome.akbpfp.db.pojo.PfpTransDetail;
import com.pchome.akbpfp.db.pojo.PfpUser;
import com.pchome.akbpfp.db.pojo.PfpUserMemberRef;
import com.pchome.akbpfp.db.pojo.PfpUserMemberRefId;
import com.pchome.akbpfp.db.service.accesslog.IAdmAccesslogService;
import com.pchome.akbpfp.db.service.adm.channel.IAdmChannelAccountService;
import com.pchome.akbpfp.db.service.bill.IPfpTransDetailService;
import com.pchome.akbpfp.db.service.bu.IPfpBuService;
import com.pchome.akbpfp.db.service.customerInfo.IPfpCustomerInfoService;
import com.pchome.akbpfp.db.service.freeAction.IAdmFreeGiftService;
import com.pchome.akbpfp.db.service.freeAction.IAdmFreeRecordService;
import com.pchome.akbpfp.db.service.order.IPfpOrderDetailService;
import com.pchome.akbpfp.db.service.order.IPfpOrderService;
import com.pchome.akbpfp.db.service.pfd.user.IPfdUserAdAccountRefService;
import com.pchome.akbpfp.db.service.sequence.SequenceService;
import com.pchome.akbpfp.db.service.user.IPfpUserMemberRefService;
import com.pchome.akbpfp.db.service.user.IPfpUserService;
import com.pchome.akbpfp.db.vo.account.AccountVO;
import com.pchome.akbpfp.db.vo.account.BuAccountVO;
import com.pchome.akbpfp.db.vo.adm.channel.AdmChannelAccountVO;
import com.pchome.akbpfp.db.vo.member.MemberVO;
import com.pchome.akbpfp.struts2.BaseSSLAction;
import com.pchome.enumerate.account.EnumAccountIndustry;
import com.pchome.enumerate.account.EnumAccountStatus;
import com.pchome.enumerate.account.EnumPfdAccountPayType;
import com.pchome.enumerate.account.EnumPfpAuthorizAtion;
import com.pchome.enumerate.apply.EnumSaveMoney;
import com.pchome.enumerate.billing.EnumBillingStatus;
import com.pchome.enumerate.freeAction.EnumGiftSnoPayment;
import com.pchome.enumerate.freeAction.EnumGiftSnoStyle;
import com.pchome.enumerate.freeAction.EnumGiftSnoUsed;
import com.pchome.enumerate.privilege.EnumPrivilegeModel;
import com.pchome.enumerate.recognize.EnumRecognizeStatus;
import com.pchome.enumerate.sequence.EnumSequenceTableName;
import com.pchome.enumerate.trans.EnumTransType;
import com.pchome.enumerate.user.EnumUserStatus;
import com.pchome.rmi.accesslog.EnumAccesslogAction;


public class ApplyAction extends BaseSSLAction{
	
	private static final long serialVersionUID = 1L;
	
	private MemberAPI memberAPI;
	private IPfpCustomerInfoService pfpCustomerInfoService;
	private SequenceService sequenceService;
	private IPfpUserService pfpUserService;
	private IAdmFreeGiftService admFreeGiftService;	
	private IPfpUserMemberRefService pfpUserMemberRefService;
	private IPfpOrderService pfpOrderService;
	private IPfpOrderDetailService pfpOrderDetailService;
	private RedirectBillingAPI redirectBillingAPI;
	private IAdmAccesslogService admAccesslogService;
	private IAdmFreeRecordService admFreeRecordService;
	private IPfdUserAdAccountRefService pfdUserAdAccountRefService;
	private IAdmChannelAccountService admChannelAccountService;
	private IPfpTransDetailService transDetailService;
	private IPfpBuService pfpBuService;
	
	private EnumBillingStatus[] enumBillingStatus  = EnumBillingStatus.values();
	private AccountVO accountVO;
	private BuAccountVO buAccountVO;
	
	private MemberVO memberVO;
	private String category;
	private String companyName;
	private String registration;
	private String industry;
	private String urlYN;	
	private String urlAddress;
	private String memberName;
	private String memberSex;	
	private String memberBirthday;	
	private String memberTelephone;
	private String memberMobile;
	private String zipcode;
	private String address;
	private float addMoney = 0;
	private String billingUrl;
	private String giftSno;				// 填序號送廣告金
	private float giftMoney;			// 填序號送廣告金	
	private String billingProductId;
	private String billingProductName;
	private List<String> industryList;
	private String billingService;	
	private String channelId;						// 金流訂單查詢	
	private String accountId;					// 帳戶編號
	private String buPortalPfdu;	
	private String buPortalPfdc;
	
	
	public String execute() throws Exception{
		
		this.checkRedirectSSLUrl();
		
		if(StringUtils.isNotBlank(this.resultType)){
			return this.resultType;
		}
		
		String result = "success";		
		
		// 讀取產業類別資料
		this.industryList = new ArrayList<String>();
		
		for(EnumAccountIndustry industry:EnumAccountIndustry.values()){
			this.industryList.add(industry.getName());
		}
		
		String userMemberId = null; 			// 使用者登入帳號 memberId
		PfpCustomerInfo pfpCustomerInfo = null;	// 使用者申請帳戶
		
		// 申請 PFP 帳戶
		if(StringUtils.isNotBlank(super.getCustomer_info_id())){
			// PFD 介面登入進來 PFP 帳戶
			pfpCustomerInfo = pfpCustomerInfoService.findCustomerInfo(super.getCustomer_info_id());
			
			if(pfpCustomerInfo != null){
				userMemberId = pfpCustomerInfo.getMemberId();	
			}
		}else{
			// 一般申請 PFP 帳戶
			userMemberId = super.getId_pchome();
			
			// 取帳戶目前狀態
			pfpCustomerInfo = this.checkAccountExist(userMemberId);
			
		}
		
		// 從會員中心取會員資料
		this.memberVO = memberAPI.getMemberVOData(userMemberId);
		
		
		if(pfpCustomerInfo == null){
			// 新申請帳戶：預設資料
			this.accountVO = new AccountVO();
			this.accountVO.setCategory("1");
			this.accountVO.setUrlYN("1");
			this.accountVO.setIndustry(EnumAccountIndustry.政府機關.getName());
			this.accountVO.setAddMoney(EnumSaveMoney.Default.getPrice());
			this.accountVO.setAddTax(EnumSaveMoney.Default.getTaxMoney());			
			this.accountVO.setMemberVO(memberVO);
			this.accountVO.setGiftMoney(0);
			
			// BU資料
			List<PfpBuAccount> pfpBuAccountList = pfpBuService.findPfpBuAccountByMemberId(userMemberId);
			if(pfpBuAccountList.size() > 0){
				PfpBuAccount pfpBuAccount = pfpBuAccountList.get(0);
				this.buAccountVO = new BuAccountVO();
				buAccountVO.setBuUrl(pfpBuAccount.getBuUrl());
				buAccountVO.setBuId(pfpBuAccount.getBuId());
				buAccountVO.setGiftSno("STTBDAEFJ8");
			}
		}
		// 帳戶申請中
		else if(pfpCustomerInfo.getStatus().equals(EnumAccountStatus.APPLY.getStatus())) {
			
			this.accountId = pfpCustomerInfo.getCustomerInfoId();
			
			// 註冊資料
			this.accountVO = pfpCustomerInfoService.getAccountVO(this.accountId);
			
			// 確認上一筆訂單
			this.checkOrderStatus(this.accountId);

			//若會員中心的 電話、手機、地址 被清掉了 -> 要帶自己的 (2015/01/13 debug)
			if (StringUtils.isBlank(memberVO.getMemberTelephone())) {
				memberVO.setMemberTelephone(pfpCustomerInfo.getTelephone());
			}
			if (StringUtils.isBlank(memberVO.getMemberMobile())) {
				memberVO.setMemberMobile(pfpCustomerInfo.getMobile());
			}
			if (StringUtils.isBlank(memberVO.getMemberZip())) {
				memberVO.setMemberZip(pfpCustomerInfo.getZip());
			}
			if (StringUtils.isBlank(memberVO.getMemberAddress())) {
				memberVO.setMemberAddress(pfpCustomerInfo.getAddress());
			}

			this.accountVO.setMemberVO(memberVO);
			
			
			// BU資料
			List<PfpBuAccount> pfpBuAccountList = pfpBuService.findPfpBuAccountByMemberId(userMemberId);
			if(pfpBuAccountList.size() > 0){
				PfpBuAccount pfpBuAccount = pfpBuAccountList.get(0);
				this.buAccountVO = new BuAccountVO();
				buAccountVO.setBuUrl(pfpBuAccount.getBuUrl());
				buAccountVO.setBuId(pfpBuAccount.getBuId());
				buAccountVO.setGiftSno("STTBDAEFJ8");
				return "success";
			}
			
			result = "wait";
		}else{
			// 帳戶已啟用、停權、關閉
			result = "summary";
		}		
		
		log.info(" result = "+result);
		
		
		
		
		
		return result;
	}
	/**
	 * 註冊流程
	 */
	@Transactional
	public String registerAction() throws Exception{
		
//		// 註冊流程		
//		this.checkRedirectSSLUrl();
//		
//		if(StringUtils.isNotBlank(this.resultType)){
//			return this.resultType;
//		}
		
		Date today = new Date();
		String result = "success";		
		String orderId = null;
		PfpUser user = null;
		String userMemberId = null; 			// 使用者登入帳號 memberId
		PfpCustomerInfo pfpCustomerInfo = null;	// 使用者申請帳戶
		String userStyle = "normal";			// 使用者註記
		
		// 開通 PFP 帳戶
		if(StringUtils.isNotBlank(super.getCustomer_info_id())){
			// PFD 介面登入進來 PFP 帳戶
			pfpCustomerInfo = pfpCustomerInfoService.findCustomerInfo(super.getCustomer_info_id());
			
			if(pfpCustomerInfo != null){
				userMemberId = pfpCustomerInfo.getMemberId();
				
			}
			userStyle = "pfdUser";
		}else{
			// 一般申請 PFP 帳戶
			userMemberId = super.getId_pchome();
			
			// 取帳戶目前狀態
			pfpCustomerInfo = this.checkAccountExist(userMemberId);

			userStyle = "normal";
		}
		log.info("-------------------userStyle=" + userStyle);
		// 從會員中心取會員資料
		this.memberVO = memberAPI.getMemberVOData(userMemberId);
		
		AdmFreeGift admFreeGift = null;			
		
		log.info(">>> giftSno: "+giftSno);
		
		if(StringUtils.isNotBlank(giftSno)){
			// 檢查活動序號
			admFreeGift = admFreeGiftService.findUnusedAdmFreeGiftSno(giftSno, today,EnumGiftSnoStyle.REGISTER.getStatus());
			log.info(">>> admFreeGiftSno: "+admFreeGift.getGiftSno());
			//檢查是否為共用序號
			if(StringUtils.equals(admFreeGift.getAdmFreeAction().getShared(), "Y") && pfpCustomerInfo != null){
				AdmFreeRecord userRecord = admFreeRecordService.findUserRecord(admFreeGift.getAdmFreeAction().getActionId(), pfpCustomerInfo.getCustomerInfoId());
				
				//若使用者已經使用過該組序號不得再重複用
				if(userRecord != null){
					admFreeGift = null;
				}
			}
		}		
		
		if(pfpCustomerInfo == null){
			// 建立新帳戶
			pfpCustomerInfo = this.createNewCustomerInfo();
			
			// 建立使用者帳號
			user = this.createNewUser(pfpCustomerInfo);
			
			// 建立訂單
			orderId = this.createNewOrder(pfpCustomerInfo, user, admFreeGift);
			
			// 一般申請 PFP 帳戶的使用者要設定經銷商為PCHOME經銷商
			if("normal".equals(userStyle)){
				List<PfpBuAccount> pfpBuAccountList = pfpBuService.findPfpBuAccountByMemberId(userMemberId);
				if(pfpBuAccountList.size() > 0){
					log.info(">>> PCHOME BU PFD PROCESS:"+userMemberId);
					PfpBuAccount pfpBuAccount = pfpBuAccountList.get(0);
					this.buAccountVO = new BuAccountVO();
					buAccountVO.setBuUrl(pfpBuAccount.getBuUrl());
					buAccountVO.setBuId(pfpBuAccount.getBuId());
					
					String pfpId = pfpCustomerInfo.getCustomerInfoId();
					String userId = user.getUserId();
					//經銷商設定
					processPfdUser(pfpId,userId,this.buPortalPfdc,this.buPortalPfdu);
					if(admFreeGift != null){
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
						}
						// 轉址至金流儲值  		
						billingUrl = redirectBillingAPI.redirectUrl(orderId);
						// 金流介接  Accesslog
						String message = EnumAccesslogAction.ACCOUNT_ADD_MONEY.getMessage()+" URL："+billingUrl;
						admAccesslogService.recordBillingLog(EnumAccesslogAction.ACCOUNT_ADD_MONEY, 
															message,  
															super.getId_pchome(), 
															orderId,											
															super.getCustomer_info_id(), 
															super.getUser_id(), 
															request.getRemoteAddr());
					}
				}else{
					log.info(">>> PCHOME PFD PROCESS:"+userMemberId);
					//經銷商設定
					processPfdUser(pfpCustomerInfo.getCustomerInfoId(),user.getUserId(),"PFDC20140520001","PFDU20140520001");
					//負責業務員設定
					processBussinessUser(pfpCustomerInfo);
				}
			}
			
		}
		else if(pfpCustomerInfo.getStatus().equals(EnumAccountStatus.APPLY.getStatus())){
			
			this.accountId = pfpCustomerInfo.getCustomerInfoId();
			
			// 申請中帳戶
			//customerInfo = pfpCustomerInfoService.findCustomerInfo(this.accountId);
			
			// 申請中使用者
			user = pfpUserService.findApplyUser(this.accountId);
			
			// 重新付款開通帳戶
			orderId = this.createNewOrder(pfpCustomerInfo, user, admFreeGift);			
		}
		else{
			log.info(">>> Account Status is not Apply or Account is null!!");
		}
		
		// 開通帳戶是否介接金流
		if(admFreeGift == null || admFreeGift.getAdmFreeAction().getPayment().equals(EnumGiftSnoPayment.YES.getStatus())){
			
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
			}
			
			// 轉址至金流儲值  		
			billingUrl = redirectBillingAPI.redirectUrl(orderId);
			
			// 金流介接  Accesslog
			String message = EnumAccesslogAction.ACCOUNT_ADD_MONEY.getMessage()+" URL："+billingUrl;

			admAccesslogService.recordBillingLog(EnumAccesslogAction.ACCOUNT_ADD_MONEY, 
												message,  
												super.getId_pchome(), 
												orderId,											
												super.getCustomer_info_id(), 
												super.getUser_id(), 
												request.getRemoteAddr());
		}
		else{
			// 直接開通帳戶
			if(pfpCustomerInfo != null && user != null && admFreeGift != null){
				
				// 開通帳戶權限
				pfpCustomerInfo.setStatus(EnumAccountStatus.START.getStatus());
				pfpCustomerInfo.setActivateDate(today);
				pfpCustomerInfo.setRemain(admFreeGift.getAdmFreeAction().getGiftMoney());
				pfpCustomerInfo.setUpdateDate(today);
				pfpCustomerInfoService.saveOrUpdate(pfpCustomerInfo);
				
				// 開通使用者權限
				user.setStatus(EnumUserStatus.START.getStatusId());
				user.setActivateDate(today);
				user.setUpdateDate(today);
				pfpUserService.saveOrUpdate(user);				
				
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
				
				// 直接轉址到首頁
				result = "index";
			}else{
				log.info(">>> customerInfo: "+pfpCustomerInfo);
				log.info(">>> user: "+user);
				log.info(">>> admFreeGift: "+admFreeGift);
			}
			
		}
		
		return result;
	}
	
	
	/**
	 * 經銷商設定
	 * */
	public boolean processPfdUser(String pfpId,String userId,String buPortalPfdc,String buPortalPfdu) throws Exception{
		System.out.println(pfpId);
		System.out.println(pfdUserAdAccountRefService.getNewRefId());
		System.out.println(userId);
		System.out.println(buPortalPfdc);
		System.out.println(buPortalPfdu);
		PfdUserAdAccountRefVO pfdUserAdAccountRefVO = new PfdUserAdAccountRefVO();
		pfdUserAdAccountRefVO.setRefId(pfdUserAdAccountRefService.getNewRefId());
		pfdUserAdAccountRefVO.setPfdCustomerInfoId(buPortalPfdc);
		pfdUserAdAccountRefVO.setPfdUserId(buPortalPfdu);
		pfdUserAdAccountRefVO.setPfpCustomerInfoId(pfpId);
		pfdUserAdAccountRefVO.setPfpUserId(userId);
		pfdUserAdAccountRefVO.setPfpPayType("1");
		pfdUserAdAccountRefVO.setProof("");
		pfdUserAdAccountRefService.savePfdUserAdAccountRef(pfdUserAdAccountRefVO);
		return true;
	}
	
	
	
	/**
	 * 負責業務員設定
	 * */
	public boolean processBussinessUser(PfpCustomerInfo pfpCustomerInfo) throws Exception{
		DateFormat dmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		log.info(dmt.format(new Date()));
		AdmChannelAccountVO admChannelAccountVO = new AdmChannelAccountVO();
		admChannelAccountVO.setMemberId("portalpfb");
		admChannelAccountVO.setAccountId(pfpCustomerInfo.getCustomerInfoId());
		admChannelAccountVO.setChannelCategory("0");
		admChannelAccountVO.setCreateDate(dmt.format(new Date()));
		admChannelAccountVO.setUpdateDate(dmt.format(new Date()));
		
		admChannelAccountService.InsertData(admChannelAccountVO);
		return true;
	}
	
	
	/**
	 * 訂單查詢
	 */
	public String orderSearchAction() throws Exception{
		
		super.checkRedirectSSLUrl();
		if(StringUtils.isNotBlank(super.resultType)){
			return super.resultType;
		}
		
		log.info(">>> channelId: "+channelId);
		
		// 取帳戶狀態
		//this.checkAccountExist();
		
		PfpCustomerInfo pfpCustomerInfo = null;
		
		// 申請 PFP 帳戶
		if(StringUtils.isNotBlank(super.getCustomer_info_id())){
			// PFD 介面登入進來 PFP 帳戶
			this.accountId = super.getCustomer_info_id();

		}else{			
			// 取帳戶目前狀態
			pfpCustomerInfo = this.checkAccountExist(super.getId_pchome());
			
			if(pfpCustomerInfo != null){
				this.accountId = pfpCustomerInfo.getCustomerInfoId();
			}
			
		}
		
		return SUCCESS;
	}
	
	/**
	 * 依 memberId 檢查帳戶
	 */
	private PfpCustomerInfo checkAccountExist(String memberId) throws Exception{				
		return pfpCustomerInfoService.findCustomerInfoByMmeberId(memberId);
	}

	/**
	 * 最新一筆訂單狀態
	 */
	private void checkOrderStatus(String customerInfoId) throws Exception{
		
		// 申請中帳戶最新一筆訂單狀態
		PfpOrder order = pfpOrderService.latestOrder(customerInfoId);
		
		this.accountVO.setOrderStatus(order.getStatus());
		this.accountVO.setAddMoney(order.getOrderPrice());
		this.accountVO.setAddTax(order.getTax());			
		this.accountVO.setGiftSno(order.getGiftSno());		
		this.accountVO.setGiftMoney(order.getGiftMoney());
		
	}
	
	/**
	 * 建立新帳戶
	 */
	private PfpCustomerInfo createNewCustomerInfo() throws Exception{
		
		PfpCustomerInfo customerInfo = null;
		
		// 新註冊帳戶
		if(StringUtils.isNotBlank(super.getId_pchome()) && StringUtils.isNotBlank(category) &&
				StringUtils.isNotBlank(industry) && StringUtils.isNotBlank(urlYN) && 
				StringUtils.isNotBlank(urlAddress) && StringUtils.isNotBlank(memberName) && 
				StringUtils.isNotBlank(memberSex) && StringUtils.isNotBlank(memberBirthday) && 
				StringUtils.isNotBlank(memberTelephone) && StringUtils.isNotBlank(memberMobile) && 
				StringUtils.isNotBlank(zipcode) && StringUtils.isNotBlank(address) && 
				addMoney >= EnumSaveMoney.Default.getMin()){	
						
			// 從會員中心取會員資料
			this.memberVO = memberAPI.getMemberVOData(super.getId_pchome());
			String customerInfoId = "";
			// 建立新帳戶,先判斷是否為BU帳戶，若是取BU帳戶memid當帳戶編號
			PfpBuAccount pfpBuAccount = null;
			List<PfpBuAccount> pfpBuAccountList = pfpBuService.findPfpBuAccountByMemberId(super.getId_pchome());
			if(pfpBuAccountList.size() > 0){
				pfpBuAccount =  pfpBuAccountList.get(0);
			}
			if(pfpBuAccount != null){
				customerInfoId = pfpBuAccount.getPcId().toUpperCase();
			}else{
				customerInfoId = sequenceService.getSerialNumber(EnumSequenceTableName.ACCOUNT);
			}
			
			customerInfo = new PfpCustomerInfo();
			//網址去除http://及https://
			urlAddress = urlAddress.trim();
			if(urlAddress.length() >= 7){
				String urlHead = urlAddress.substring(0,7);
				if("http://".equals(urlHead)){
					urlAddress = urlAddress.substring(7);
				}
			}
			
			if(urlAddress.length() >= 8){
				String urlHead = urlAddress.substring(0,8);
				if("https://".equals(urlHead)){
					urlAddress = urlAddress.substring(8);
				}
			}
			
			customerInfo.setCustomerInfoId(customerInfoId);
			customerInfo.setMemberId(this.memberVO.getMemberId());
			customerInfo.setCategory(category);
			
			if(category.trim().equals("1")){
				customerInfo.setCustomerInfoTitle(memberName);
			}else{
				customerInfo.setCustomerInfoTitle(companyName);
			}
			customerInfo.setCompanyTitle(companyName);
			customerInfo.setRegistration(registration);
			customerInfo.setIndustry(industry);
			customerInfo.setUrl(urlYN);
			customerInfo.setUrlAddress(urlAddress);
			customerInfo.setStatus(EnumAccountStatus.APPLY.getStatus());				
			customerInfo.setTelephone(memberTelephone);
			customerInfo.setMobile(memberMobile);
			customerInfo.setZip(zipcode);
			customerInfo.setAddress(address);
			customerInfo.setPayType(EnumPfdAccountPayType.ADVANCE.getPayType());
			customerInfo.setAuthorizedPage(EnumPfpAuthorizAtion.NO.getStatus());;
			customerInfo.setRecognize(EnumRecognizeStatus.YES.getStatus());		
			Date today = new Date();
			customerInfo.setUpdateDate(today);
			customerInfo.setCreateDate(today);
			
			pfpCustomerInfoService.saveOrUpdate(customerInfo);
			
			// 資料要回寫到 member
			this.memberVO.setMemberName(memberName);
			this.memberVO.setMemberSex(memberSex);
			this.memberVO.setMemberBirthday(memberBirthday);
			this.memberVO.setMemberTelephone(memberTelephone);
			this.memberVO.setMemberMobile(memberMobile);
			this.memberVO.setMemberZip(zipcode);
			this.memberVO.setMemberAddress(address);
					
			this.memberAPI.updateMemberData(this.memberVO);
		}
			
		return customerInfo;
	}
	
	/**
	 * 建立帳戶使用者
	 */
	private PfpUser createNewUser(PfpCustomerInfo customerInfo) throws Exception{
		
		PfpUser user = null;
		
		if(customerInfo != null){
			
			// 建立使用者帳號

			String userId = sequenceService.getSerialNumber(EnumSequenceTableName.USER);
			
			user = new PfpUser();
			Date today = new Date();
			
			user.setUserId(userId);
			user.setPfpCustomerInfo(customerInfo);
			user.setUserName(this.memberVO.getMemberName());
			user.setUserEmail(this.memberVO.getMemberCheckMail());
			user.setPrivilegeId(EnumPrivilegeModel.ROOT_USER.getPrivilegeId());
			user.setStatus(EnumUserStatus.APPLY.getStatusId());
			user.setInviteDate(today);
			user.setUpdateDate(today);
			user.setCreateDate(today);
			pfpUserService.saveOrUpdate(user);
			
			// 會員中心與帳號關聯		
			PfpUserMemberRefId userMemberRefId = new PfpUserMemberRefId();
			userMemberRefId.setMemberId(customerInfo.getMemberId());
			userMemberRefId.setUserId(userId);
			PfpUserMemberRef pfpUserMemberRef = new PfpUserMemberRef();
			pfpUserMemberRef.setId(userMemberRefId);
			pfpUserMemberRefService.saveOrUpdate(pfpUserMemberRef);
			
		}

		return user;
	}
	
	/**
	 * 建立帳戶訂單
	 */
	private String createNewOrder(PfpCustomerInfo customerInfo, PfpUser user, AdmFreeGift admFreeGift) throws Exception{
		
		String orderId = null;
		String giftSno = null;
		float giftMoney = 0;
		
		if(customerInfo != null && user != null ){
			
			if(admFreeGift != null && admFreeGift.getAdmFreeAction().getPayment().equals(EnumGiftSnoPayment.NO.getStatus())){
				log.info(">>> actionId: "+admFreeGift.getAdmFreeAction().getActionId());
			}else{
				
				Date today = new Date();

				// 訂單資料
				float taxMoney = addMoney * (EnumSaveMoney.Default.getTaxPercent()/100);
				
				orderId = sequenceService.getSerialNumber(EnumSequenceTableName.ORDER);
				PfpOrder order = new PfpOrder();
				
				order.setOrderId(orderId);
				order.setPfpCustomerInfo(customerInfo);
				order.setPfpUser(user);				
				order.setTax(taxMoney);
				order.setStatus(EnumBillingStatus.B101.toString());
				order.setUpdateDate(today);
				order.setCreateDate(today);
				pfpOrderService.saveOrUpdate(order);
				
				// 訂單明細
				PfpOrderDetailId id = new PfpOrderDetailId();
				
				id.setOrderId(orderId);
				id.setProductId(billingProductId);	
				
				PfpOrderDetail orderDetail = new PfpOrderDetail();
				orderDetail.setId(id);		
				
				orderDetail.setProductName(billingProductName);
				pfpOrderDetailService.saveOrUpdate(orderDetail);
				
				// 活動序號產生訂單
				if(admFreeGift != null){
					giftSno = admFreeGift.getGiftSno();
					giftMoney = admFreeGift.getAdmFreeAction().getGiftMoney();
					
					order.setGiftSno(giftSno);
					order.setGiftMoney(giftMoney);
				}
				
				order.setOrderPrice(addMoney);
				orderDetail.setProducPrice(addMoney);
			}		
			
			//log.info(" orderId: "+orderId);
		}
		
		return orderId;
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
	
	public void setMemberAPI(MemberAPI memberAPI) {
		this.memberAPI = memberAPI;
	}
	
	public void setPfpCustomerInfoService(
			IPfpCustomerInfoService pfpCustomerInfoService) {
		this.pfpCustomerInfoService = pfpCustomerInfoService;
	}

	public void setSequenceService(SequenceService sequenceService) {
		this.sequenceService = sequenceService;
	}	
	
	public void setPfpUserService(IPfpUserService pfpUserService) {
		this.pfpUserService = pfpUserService;
	}

	public void setPfpUserMemberRefService(
			IPfpUserMemberRefService pfpUserMemberRefService) {
		this.pfpUserMemberRefService = pfpUserMemberRefService;
	}

	public void setPfpOrderService(IPfpOrderService pfpOrderService) {
		this.pfpOrderService = pfpOrderService;
	}

	public void setPfpOrderDetailService(
			IPfpOrderDetailService pfpOrderDetailService) {
		this.pfpOrderDetailService = pfpOrderDetailService;
	}

	public void setRedirectBillingAPI(RedirectBillingAPI redirectBillingAPI) {
		this.redirectBillingAPI = redirectBillingAPI;
	}

	public void setAdmAccesslogService(IAdmAccesslogService admAccesslogService) {
		this.admAccesslogService = admAccesslogService;
	}

	public AccountVO getAccountVO() {
		return accountVO;
	}

	public List<String> getIndustryList() {
		return industryList;
	}	
	
	public void setCategory(String category) {
		this.category = category;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setRegistration(String registration) {
		this.registration = registration;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public void setUrlYN(String urlYN) {
		this.urlYN = urlYN;
	}

	public void setUrlAddress(String urlAddress) {
		this.urlAddress = urlAddress;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public void setMemberSex(String memberSex) {
		this.memberSex = memberSex;
	}

	public void setMemberBirthday(String memberBirthday) {
		this.memberBirthday = memberBirthday;
	}

	public void setMemberTelephone(String memberTelephone) {
		this.memberTelephone = memberTelephone;
	}

	public void setMemberMobile(String memberMobile) {
		this.memberMobile = memberMobile;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public void setAddMoney(float addMoney) {
		this.addMoney = addMoney;
	}

	public String getBillingUrl() {
		return billingUrl;
	}

	public void setBillingProductId(String billingProductId) {
		this.billingProductId = billingProductId;
	}

	public void setBillingProductName(String billingProductName) {
		this.billingProductName = billingProductName;
	}

	public String getAccountId() {
		return accountId;
	}
	
	public String getBillingService() {
		return billingService;
	}

	public void setBillingService(String billingService) {
		this.billingService = billingService;
	}

	public EnumBillingStatus[] getEnumBillingStatus() {
		return enumBillingStatus;
	}

//	public void setMinPrice(float minPrice) {
//		this.minPrice = minPrice;
//	}

	public String getGiftSno() {
		return giftSno;
	}

	public void setGiftSno(String giftSno) {
		this.giftSno = giftSno.trim();
	}

	public float getGiftMoney() {
		return giftMoney;
	}

	public void setGiftMoney(float giftMoney) {
		this.giftMoney = giftMoney;
	}

	public void setAdmFreeGiftService(IAdmFreeGiftService admFreeGiftService) {
		this.admFreeGiftService = admFreeGiftService;
	}

	public void setAdmFreeRecordService(IAdmFreeRecordService admFreeRecordService) {
		this.admFreeRecordService = admFreeRecordService;
	}
	
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	
	public String getChannelId() {
		return channelId;
	}
	public void setPfdUserAdAccountRefService(
			IPfdUserAdAccountRefService pfdUserAdAccountRefService) {
		this.pfdUserAdAccountRefService = pfdUserAdAccountRefService;
	}
	public void setAdmChannelAccountService(
			IAdmChannelAccountService admChannelAccountService) {
		this.admChannelAccountService = admChannelAccountService;
	}
	public void setTransDetailService(IPfpTransDetailService transDetailService) {
		this.transDetailService = transDetailService;
	}
	public IPfpBuService getPfpBuService() {
		return pfpBuService;
	}
	public void setPfpBuService(IPfpBuService pfpBuService) {
		this.pfpBuService = pfpBuService;
	}
	public BuAccountVO getBuAccountVO() {
		return buAccountVO;
	}
	public void setBuAccountVO(BuAccountVO buAccountVO) {
		this.buAccountVO = buAccountVO;
	}
	public String getBuPortalPfdu() {
		return buPortalPfdu;
	}
	public void setBuPortalPfdu(String buPortalPfdu) {
		this.buPortalPfdu = buPortalPfdu;
	}
	public String getBuPortalPfdc() {
		return buPortalPfdc;
	}
	public void setBuPortalPfdc(String buPortalPfdc) {
		this.buPortalPfdc = buPortalPfdc;
	}

}
