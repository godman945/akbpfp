package com.pchome.akbpfp.struts2.action.account;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.pchome.akbpfp.db.pojo.PfdBoard;
import com.pchome.akbpfp.db.pojo.PfdUserAdAccountRef;
import com.pchome.akbpfp.db.pojo.PfpCustomerInfo;
import com.pchome.akbpfp.db.pojo.PfpRefundOrder;
import com.pchome.akbpfp.db.service.board.IPfdBoardService;
import com.pchome.akbpfp.db.service.customerInfo.PfpCustomerInfoService;
import com.pchome.akbpfp.db.service.order.IPfpRefundOrderService;
import com.pchome.akbpfp.db.vo.account.AccountVO;
import com.pchome.akbpfp.struts2.BaseSSLAction;
import com.pchome.enumerate.account.EnumAccountIndustry;
import com.pchome.enumerate.account.EnumAccountStatus;
import com.pchome.enumerate.account.EnumPfdAccountPayType;
import com.pchome.enumerate.order.EnumRefundOrderStatus;
import com.pchome.enumerate.pfd.EnumPfdBoardType;
import com.pchome.enumerate.pfd.EnumPfdUserPrivilege;
import com.pchome.rmi.board.EnumBoardType;
import com.pchome.rmi.board.IBoardProvider;
import com.pchome.rmi.mailbox.EnumCategory;


public class AccountInfoAction extends BaseSSLAction{
	
	private PfpCustomerInfoService pfpCustomerInfoService;
	private IBoardProvider boardProvider;
	private IPfdBoardService pfdBoardService;
	private IPfpRefundOrderService pfpRefundOrderService;
	
	private PfpCustomerInfo pfpCustomerInfo;
	private AccountVO accountVO;
	private List<String> industryList;
	
	private String category;
	private String accountTitle;
	private String companyTitle;
	private String registration;
	private String industry;
	private String urlAddress;
	private String status;
	private EnumPfdAccountPayType[] payType = EnumPfdAccountPayType.values();
	private String changeStatusFlag;
	
	public String execute() throws Exception{
		
		this.checkRedirectSSLUrl();
		if(StringUtils.isNotBlank(this.resultType)){
			return this.resultType;
		}
		
		// 取帳戶資料
		pfpCustomerInfo = pfpCustomerInfoService.findCustomerInfo(super.getCustomer_info_id());
		
		// 讀取產業類別資料
		industryList = new ArrayList<String>();
		
		for(EnumAccountIndustry industry:EnumAccountIndustry.values()){
			industryList.add(industry.getName());
		}
		
		//判斷有無尚未退款的退款單
		changeStatusFlag = "Y";
		List<PfpRefundOrder> notRefundList = pfpRefundOrderService.findPfpRefundOrder(super.getCustomer_info_id(), EnumRefundOrderStatus.NOT_REFUND.getStatus());
		
		if(!notRefundList.isEmpty()){
			changeStatusFlag = "N";
		}
		
		return SUCCESS;
	}
	

	public String AccountInfoUpdateAction() throws Exception{
		
		this.checkRedirectSSLUrl();
		if(StringUtils.isNotBlank(this.resultType)){
			return this.resultType;
		}
		
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
		
		PfpCustomerInfo pfpCustomerInfo = pfpCustomerInfoService.findCustomerInfo(super.getCustomer_info_id());
		
		if(pfpCustomerInfo != null){
			
			if(StringUtils.isBlank(status)){
				status = pfpCustomerInfo.getStatus();
			}
			
			if(StringUtils.isNotBlank(category.trim()) && StringUtils.isNotBlank(accountTitle.trim()) &&
					StringUtils.isNotBlank(industry.trim()) && StringUtils.isNotBlank(urlAddress.trim()) &&
					StringUtils.isNotBlank(status.trim())){
				
				String oldStatus = pfpCustomerInfo.getStatus();
				
				pfpCustomerInfo.setCategory(category.trim());
				pfpCustomerInfo.setCustomerInfoTitle(accountTitle.trim());
				
				if(category.trim().equals("2")){
					pfpCustomerInfo.setCompanyTitle(companyTitle.trim());
					pfpCustomerInfo.setRegistration(registration.trim());
				}else{
					pfpCustomerInfo.setCompanyTitle("");
					pfpCustomerInfo.setRegistration("");
				}
				
				pfpCustomerInfo.setIndustry(industry.trim());
				pfpCustomerInfo.setUrlAddress(urlAddress.trim());
				pfpCustomerInfo.setStatus(status.trim());
				pfpCustomerInfo.setUpdateDate(new Date());
				
				// 公告通知及刪除
				if(EnumAccountStatus.CLOSE.getStatus().equals(status) && !StringUtils.equals(EnumAccountStatus.CLOSE.getStatus(), oldStatus)){

					boardProvider.add(pfpCustomerInfo.getCustomerInfoId(), 
										EnumCategory.ACCOUNT_CLOSED.getBoardContent(),
										EnumCategory.ACCOUNT_CLOSED.getUrlAddress(),
										EnumBoardType.ACCOUNT, 
										EnumCategory.ACCOUNT_CLOSED,
										null);
					
					// pfd經銷商也要發公告
					Set<PfdUserAdAccountRef> set = pfpCustomerInfo.getPfdUserAdAccountRefs();
					if(set.size() > 0){
						List<PfdUserAdAccountRef> list = new ArrayList<PfdUserAdAccountRef>(set);
						String pfdCustomerInfoId = list.get(0).getPfdCustomerInfo().getCustomerInfoId();
						String pfdUserId = list.get(0).getPfdUser().getUserId();
						
						SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

						Date now = new Date();
						Date startDate = sdf.parse(sdf.format(now));
						Calendar c = Calendar.getInstance();
						c.add(Calendar.MONTH, 6);
						Date endDate = sdf.parse(sdf.format(c.getTime()));
						
						String content = "廣告帳戶 <a href=\"./adAccountList.html\">" + pfpCustomerInfo.getCustomerInfoTitle() + "</a> 已被關閉，故無法播放廣告。如要繼續播出廣告，請重新開啟此帳戶狀態。";

						PfdBoard board = new PfdBoard();
						board.setBoardType(EnumPfdBoardType.REMIND.getType());
						board.setBoardContent(content);
						board.setPfdCustomerInfoId(pfdCustomerInfoId);
						board.setIsSysBoard("n");
						//board.setPfdUserId(pfdUserId);
						board.setStartDate(startDate);
						board.setEndDate(endDate);
						board.setHasUrl("n");
						board.setUrlAddress(null);
						board.setDeleteId(pfpCustomerInfo.getCustomerInfoId() + "/close");
						
						//觀看權限(總管理者/帳戶管理/行政管理)
						String msgPrivilege = EnumPfdUserPrivilege.ROOT_USER.getPrivilege() + "||" + EnumPfdUserPrivilege.ACCOUNT_MANAGER.getPrivilege();
						board.setMsgPrivilege(msgPrivilege);
						
						board.setUpdateDate(now);
						board.setCreateDate(now);

						pfdBoardService.save(board);
						
						//給行政管理/業務管理看的公告
	            		PfdBoard board2 = new PfdBoard();
	            		board2.setBoardType(EnumPfdBoardType.REMIND.getType());
						board2.setBoardContent("廣告帳戶 <span style=\"color:#1d5ed6;\">" + pfpCustomerInfo.getCustomerInfoTitle() + "</span> 已被關閉，故無法播放廣告。如要繼續播出廣告，請重新開啟此帳戶狀態。");
						board2.setPfdCustomerInfoId(pfdCustomerInfoId);
						board2.setIsSysBoard("n");
						board2.setPfdUserId(pfdUserId);
						board2.setStartDate(startDate);
						board2.setEndDate(endDate);
						board2.setHasUrl("n");
						board2.setUrlAddress(null);
						board2.setDeleteId(pfpCustomerInfo.getCustomerInfoId() + "/close");
						board2.setMsgPrivilege(EnumPfdUserPrivilege.REPORT_MANAGER.getPrivilege() + "||" + EnumPfdUserPrivilege.SALES_MANAGER.getPrivilege());
						board2.setUpdateDate(now);
						board2.setCreateDate(now);

						pfdBoardService.save(board2);
					}
					
				}
				
				if(EnumAccountStatus.START.getStatus().equals(status)){
					
					boardProvider.delete(pfpCustomerInfo.getCustomerInfoId(), EnumCategory.ACCOUNT_CLOSED);
					
					//pfd經銷商公告也要刪除
					pfdBoardService.deletePfdBoardByDeleteId(pfpCustomerInfo.getCustomerInfoId() + "/close");
				}
				
				pfpCustomerInfoService.saveOrUpdateWithAccesslog(pfpCustomerInfo, super.getId_pchome(), super.getUser_id(), request.getRemoteAddr());
			}
		}
		
		return SUCCESS;
	}
	
	public void setPfpCustomerInfoService(PfpCustomerInfoService pfpCustomerInfoService) {
		this.pfpCustomerInfoService = pfpCustomerInfoService;
	}

	public void setBoardProvider(IBoardProvider boardProvider) {
		this.boardProvider = boardProvider;
	}
	
	public void setPfpRefundOrderService(IPfpRefundOrderService pfpRefundOrderService) {
		this.pfpRefundOrderService = pfpRefundOrderService;
	}
	
	public PfpCustomerInfo getPfpCustomerInfo() {
		return pfpCustomerInfo;
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


	public void setAccountTitle(String accountTitle) {
		this.accountTitle = accountTitle;
	}


	public void setCompanyTitle(String companyTitle) {
		this.companyTitle = companyTitle;
	}


	public void setRegistration(String registration) {
		this.registration = registration;
	}


	public void setIndustry(String industry) {
		this.industry = industry;
	}


	public void setUrlAddress(String urlAddress) {
		this.urlAddress = urlAddress;
	}


	public void setStatus(String status) {
		this.status = status;
	}

	public EnumPfdAccountPayType[] getPayType() {
		return payType;
	}

	public void setPfdBoardService(IPfdBoardService pfdBoardService) {
		this.pfdBoardService = pfdBoardService;
	}
	public String getChangeStatusFlag() {
		return changeStatusFlag;
	}
	
}
