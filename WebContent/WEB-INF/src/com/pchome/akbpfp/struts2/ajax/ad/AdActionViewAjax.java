package com.pchome.akbpfp.struts2.ajax.ad;

import java.util.List;

import com.pchome.akbpfp.db.service.ad.IPfpAdActionService;
import com.pchome.akbpfp.db.vo.ad.PfpAdActionViewVO;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.soft.util.DateValueUtil;

public class AdActionViewAjax extends BaseCookieAction{

	private static final long serialVersionUID = 1L;

	private IPfpAdActionService pfpAdActionService;
	
	private String keyword;
	private String searchType;
	private String startDate;
	private String endDate;
	private List<PfpAdActionViewVO> adActionViewVO;
	
	private int pageNo = 1;       				// 初始化目前頁數
	private int pageSize = 20;     				// 初始化每頁幾筆
	private int pageCount = 0;    				// 初始化共幾頁
	private long totalCount = 0;   				// 初始化共幾筆
	
	private int totalSize = 0;						
	private int totalPv = 0;						
	private int totalClk = 0;						
	private float totalClkRate = 0;
	private float thousandsCost = 0;
	private float totalAvgCost = 0;
	private int totalCost = 0;
	private int totalInvildClk = 0;
	
	public String execute() throws Exception{
		
		return SUCCESS;
	}
	
	/**
	 * 廣告管理-檢視廣告
	 * */
	public String adActionViewTableAjax() throws Exception{
		//int type = Integer.parseInt(searchType);
		long allAdActionViews = 0;
		
		allAdActionViews = pfpAdActionService.getPfpAdActionCount(super.getCustomer_info_id(), keyword, searchType);
		adActionViewVO = pfpAdActionService.getAdActionView(super.getCustomer_info_id(), 
																	keyword, 
																	searchType, 
																	DateValueUtil.getInstance().stringToDate(startDate), 
																	DateValueUtil.getInstance().stringToDate(endDate),
																	pageNo, 
																	pageSize);
				
		
		if(allAdActionViews > 0) {
			totalCount = allAdActionViews;
			pageCount = (int) Math.ceil(((float)totalCount / pageSize));

			if(adActionViewVO != null && adActionViewVO.size() > 0){
				totalSize = adActionViewVO.size();		
				for(PfpAdActionViewVO vo:adActionViewVO){
					totalPv += vo.getAdPv();
					totalClk += vo.getAdClk();		
					totalCost += vo.getAdClkPrice();
					totalInvildClk += vo.getInvalidClk();
				}
				
				if(totalClk > 0 || totalPv > 0){
					totalClkRate = (float)totalClk / (float)totalPv*100;
				}
				
				if(totalCost > 0 || totalClk > 0){
					totalAvgCost = (float)totalCost / (float)totalClk;	
				}
				
				if(totalCost > 0){
					thousandsCost = (float)totalCost / ((float)totalPv / 1000);
					System.out.println(thousandsCost);
				}
				
			}
		}
			
		// 查詢日期寫進cookie
		this.setChooseDate(startDate, endDate);
		
		return SUCCESS;
	}
	

	public void setPfpAdActionService(IPfpAdActionService pfpAdActionService) {
		this.pfpAdActionService = pfpAdActionService;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public List<PfpAdActionViewVO> getAdActionViewVO() {
		return adActionViewVO;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageCount() {
		return pageCount;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public int getTotalSize() {
		return totalSize;
	}

	public int getTotalPv() {
		return totalPv;
	}

	public int getTotalClk() {
		return totalClk;
	}

	public float getTotalClkRate() {
		return totalClkRate;
	}

	public float getTotalAvgCost() {
		return totalAvgCost;
	}

	public int getTotalCost() {
		return totalCost;
	}

	public int getTotalInvildClk() {
		return totalInvildClk;
	}

	public float getThousandsCost() {
		return thousandsCost;
	}

	public void setThousandsCost(float thousandsCost) {
		this.thousandsCost = thousandsCost;
	}
	
	
}
