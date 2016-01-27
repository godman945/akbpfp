package com.pchome.akbpfp.struts2.action.account;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.pchome.akbpfp.db.pojo.PfpCustomerInfo;
import com.pchome.akbpfp.db.service.customerInfo.PfpCustomerInfoService;
import com.pchome.akbpfp.db.vo.account.AccountVO;
import com.pchome.akbpfp.struts2.BaseSSLAction;
import com.pchome.enumerate.account.EnumAccountIndustry;
import com.pchome.enumerate.account.EnumAccountStatus;
import com.pchome.enumerate.account.EnumPfdAccountPayType;
import com.pchome.rmi.board.EnumBoardType;
import com.pchome.rmi.board.IBoardProvider;
import com.pchome.rmi.mailbox.EnumCategory;


public class AccountInfoAction extends BaseSSLAction{
	
	private PfpCustomerInfoService pfpCustomerInfoService;
	private IBoardProvider boardProvider;
	
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
			
			if(StringUtils.isNotBlank(category.trim()) && StringUtils.isNotBlank(accountTitle.trim()) &&
					StringUtils.isNotBlank(industry.trim()) && StringUtils.isNotBlank(urlAddress.trim()) &&
					StringUtils.isNotBlank(status.trim())){
				
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
				if(EnumAccountStatus.CLOSE.getStatus().equals(status)){

					boardProvider.add(pfpCustomerInfo.getCustomerInfoId(), 
										EnumCategory.ACCOUNT_CLOSED.getBoardContent(),
										EnumCategory.ACCOUNT_CLOSED.getUrlAddress(),
										EnumBoardType.ACCOUNT, 
										EnumCategory.ACCOUNT_CLOSED,
										null);
				}
				
				if(EnumAccountStatus.START.getStatus().equals(status)){
					
					boardProvider.delete(pfpCustomerInfo.getCustomerInfoId(), EnumCategory.ACCOUNT_CLOSED);
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
	
}
