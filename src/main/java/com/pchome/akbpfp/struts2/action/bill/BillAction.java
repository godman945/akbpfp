package com.pchome.akbpfp.struts2.action.bill;

import java.util.LinkedHashMap;

import org.apache.commons.lang.StringUtils;

import com.pchome.akbpfp.api.CookieContentAPI;
import com.pchome.akbpfp.db.pojo.PfpCustomerInfo;
import com.pchome.akbpfp.db.service.customerInfo.PfpCustomerInfoService;
import com.pchome.akbpfp.struts2.BaseSSLAction;
import com.pchome.soft.util.DateValueUtil;

public class BillAction extends BaseSSLAction{

	private PfpCustomerInfoService pfpCustomerInfoService; 
	private CookieContentAPI cookieContentAPI;
	
	private String startDate;										// 開始日期
	private String endDate;											// 結束日期
	private LinkedHashMap<String,String> dateSelectMap;				// 查詢日期頁面顯示
	
	private PfpCustomerInfo pfpCustomerInfo;
	private String channelId;
	private String billingService;
	
	private BillAction(){
		
	}
	
	public String execute() throws Exception {
		this.checkRedirectSSLUrl();
		if(StringUtils.isNotBlank(this.resultType)){
			return this.resultType;
		}
		String startDate_cookie = this.getChoose_start_date();
		String endDate_cookie = this.getChoose_end_date();
		if (StringUtils.isEmpty(startDate)) {
			if (StringUtils.isNotEmpty(startDate_cookie)) {
				startDate = startDate_cookie;
			} else {
				startDate = DateValueUtil.getInstance().dateToString(DateValueUtil.getInstance().getDateForStartDateAddDay(DateValueUtil.getInstance().getDateValue(DateValueUtil.TODAY, DateValueUtil.DBPATH), -30));
			}
		}
		dateSelectMap = DateValueUtil.getInstance().getDateRangeMap();
		endDate = DateValueUtil.getInstance().getDateValue(DateValueUtil.YESTERDAY, DateValueUtil.DBPATH);
		pfpCustomerInfo = pfpCustomerInfoService.findCustomerInfo(super.getCustomer_info_id());
		
		return SUCCESS;
	}
	
	public String paySearchAction() throws Exception{		
		return SUCCESS;
	}
	
	public String freeSearchAction() throws Exception {
		
		this.checkRedirectSSLUrl();
		if(StringUtils.isNotBlank(this.resultType)){
			return this.resultType;
		}
		
		dateSelectMap = DateValueUtil.getInstance().getDateRangeMap();
		startDate = DateValueUtil.getInstance().getDateValue(-30, DateValueUtil.DBPATH);
		endDate = DateValueUtil.getInstance().getDateValue(DateValueUtil.YESTERDAY, DateValueUtil.DBPATH);
		
		pfpCustomerInfo = pfpCustomerInfoService.findCustomerInfo(super.getCustomer_info_id());
		
		return SUCCESS;
	}
	
	public void setPfpCustomerInfoService(
			PfpCustomerInfoService pfpCustomerInfoService) {
		this.pfpCustomerInfoService = pfpCustomerInfoService;
	}

	public void setCookieContentAPI(CookieContentAPI cookieContentAPI) {
		this.cookieContentAPI = cookieContentAPI;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public LinkedHashMap<String, String> getDateSelectMap() {
		return dateSelectMap;
	}

	public PfpCustomerInfo getPfpCustomerInfo() {
		return pfpCustomerInfo;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getBillingService() {
		return billingService;
	}

	public void setBillingService(String billingService) {
		this.billingService = billingService;
	}

}
