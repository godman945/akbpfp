package com.pchome.akbpfp.struts2.action.summary;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.pchome.akbpfp.db.pojo.PfpAdAction;
import com.pchome.akbpfp.db.pojo.PfpCustomerInfo;
import com.pchome.akbpfp.db.service.ad.PfpAdActionService;
import com.pchome.akbpfp.db.service.ad.PfpAdPvclkService;
import com.pchome.akbpfp.db.service.customerInfo.PfpCustomerInfoService;
import com.pchome.akbpfp.struts2.BaseSSLAction;
import com.pchome.enumerate.account.EnumPfdAccountPayType;
import com.pchome.rmi.board.EnumBoardType;
import com.pchome.soft.depot.utils.SpringOpenFlashUtil;
import com.pchome.soft.util.DateValueUtil;

public class SummaryAction extends BaseSSLAction{	
	
	private PfpAdActionService pfpAdActionService;
	private PfpAdPvclkService pfpAdPvclkService;
	private PfpCustomerInfoService pfpCustomerInfoService;	
	private SpringOpenFlashUtil openFlashUtil;						// flash元件

	private EnumBoardType[] boardType;
	private PfpCustomerInfo customerInfo;	
	private List<PfpAdAction> adActions;
	private List<Float> totalAdPvclkCost;
	private String chartDate;			
	private LinkedHashMap<String,String> dateSelectMap;
	
	private String chartInputType;								// 圖表類型
	private String effectSDate;									// 成效開始日期
	private String effectEDate;									// 成效結束日期
	private String startDate ;									// 日曆開始日期
	private String endDate ;									// 日曆結束日期
	private EnumPfdAccountPayType[] payType = EnumPfdAccountPayType.values();

	
	public String execute() throws Exception{
		
		this.checkRedirectSSLUrl();
		if(StringUtils.isNotBlank(this.resultType)){
			return this.resultType;
		}
		
		effectSDate = DateValueUtil.getInstance().getThisMonthFirstDate();									// 取本月的第一天							
		effectEDate = DateValueUtil.getInstance().getDateValue(DateValueUtil.TODAY, DateValueUtil.DBPATH);	// 取今天
		
		// 查詢日期頁面顯示
		dateSelectMap = DateValueUtil.getInstance().getDateRangeMap();		
		
		// 公告類型
		boardType = EnumBoardType.values();		
		
		// 帳戶資訊
		customerInfo = pfpCustomerInfoService.findCustomerInfo(super.getCustomer_info_id());
		
		// 確認在走期中的廣告活動數量
		adActions = pfpAdActionService.getAdAction(super.getCustomer_info_id(), new Date());

		// 本月廣告成效	
		totalAdPvclkCost = pfpAdPvclkService.totalPvclkCost(super.getCustomer_info_id(), 
																DateValueUtil.getInstance().getDateForStartDateAddDay(effectSDate, 0), 
																DateValueUtil.getInstance().getDateForStartDateAddDay(effectEDate, 0));

		// 共用日期
		startDate = this.getChoose_start_date();											
		endDate = this.getChoose_end_date();						
		
		return SUCCESS;
	}
	
	public String openFlishAction() throws Exception{
		
		String startDate = DateValueUtil.getInstance().getThisMonthFirstDate();		// 取本月第一天
		String endDate = DateValueUtil.getInstance().getThisMonthLastDate();		// 取本月最後一天
		
		
		// 圖表區塊
    	String chartPic="";				// 圖表類型
    	String chartType="";			// 圖表顯示條件

    	
    	if(StringUtils.isNotEmpty(chartInputType)){
    		String chartValue[] = chartInputType.split("&");
    		chartPic = StringUtils.defaultIfEmpty(chartValue[0], "");
        	chartType = StringUtils.defaultIfEmpty(chartValue[1], "");
    	}
    	
		Map<Date,Float> flashDataMap = pfpAdPvclkService.chartPvclkCost(super.getCustomer_info_id(), 
																			chartType, 
																			DateValueUtil.getInstance().getDateForStartDateAddDay(startDate, 0), 
																			DateValueUtil.getInstance().getDateForStartDateAddDay(endDate, 0));

		// 圖表數據
		chartDate = openFlashUtil.getChartDataForMap(chartPic, chartType, startDate, endDate, flashDataMap);
		
		/*
		log.info(" chartPic = "+chartPic);	
		log.info(" chartType = "+chartType);
		log.info(" flashDataMap = "+flashDataMap);
		log.info(" startDate = "+this.startDate);	
		log.info(" endDate = "+this.endDate);
		*/
		
		return SUCCESS;
	}

	public void setPfpAdActionService(PfpAdActionService pfpAdActionService) {
		this.pfpAdActionService = pfpAdActionService;
	}
	
	public void setPfpAdPvclkService(PfpAdPvclkService pfpAdPvclkService) {
		this.pfpAdPvclkService = pfpAdPvclkService;
	}

	public void setPfpCustomerInfoService(PfpCustomerInfoService pfpCustomerInfoService) {
		this.pfpCustomerInfoService = pfpCustomerInfoService;
	}

	public void setOpenFlashUtil(SpringOpenFlashUtil openFlashUtil) {
		this.openFlashUtil = openFlashUtil;
	}

	public EnumBoardType[] getBoardType() {
		return boardType;
	}

	public PfpCustomerInfo getCustomerInfo() {
		return customerInfo;
	}

	public List<PfpAdAction> getAdActions() {
		return adActions;
	}

	public List<Float> getTotalAdPvclkCost() {
		return totalAdPvclkCost;
	}

	public String getChartDate() {
		return chartDate;
	}

	public void setChartInputType(String chartInputType) {
		this.chartInputType = chartInputType;
	}

	public LinkedHashMap<String, String> getDateSelectMap() {
		return dateSelectMap;
	}

	public String getEffectSDate() {
		return effectSDate;
	}

	public String getEffectEDate() {
		return effectEDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public EnumPfdAccountPayType[] getPayType() {
		return payType;
	}

}
