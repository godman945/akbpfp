package com.pchome.akbpfp.struts2.ajax.ad;

import java.util.Date;
import java.util.List;

import com.pchome.akbpfp.db.dao.ad.PfpAdKeywordDAO;
import com.pchome.akbpfp.db.service.ad.IPfpAdKeywordService;
import com.pchome.akbpfp.db.vo.ad.PfpAdKeywordViewVO;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.soft.util.DateValueUtil;

public class AdKeywordViewAjax extends BaseCookieAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private IPfpAdKeywordService pfpAdKeywordService;
	
	private String keyword;
	private String startDate;
	private String endDate;
	private String adGroupSeq;
	private List<PfpAdKeywordViewVO> adKeywordViewVO;

	private int pageNo = 1;       				// 初始化目前頁數
	private int pageSize = 20;     				// 初始化每頁幾筆
	private int pageCount = 0;    				// 初始化共幾頁
	private long totalCount = 0;   				// 初始化共幾筆
	
	private int totalSize = 0;						
	private int totalPv = 0;						
	private int totalClk = 0;						
	private float totalClkRate = 0;
	private float totalAvgCost = 0;
	private int totalCost = 0;
	private int totalInvalidClk = 0;
	
	public String execute() throws Exception{
		return SUCCESS;
	}

	public String adKeywordViewTableAjax() throws Exception{
		long allAdKeywordViews = 0;
		allAdKeywordViews = pfpAdKeywordService.getPfpAdKeywordCount(super.getCustomer_info_id(), 
																	adGroupSeq, 
																	keyword);
		adKeywordViewVO = pfpAdKeywordService.getAdKeywordView(super.getCustomer_info_id(), 
																adGroupSeq, 
																keyword, 
																DateValueUtil.getInstance().stringToDate(startDate), 
																DateValueUtil.getInstance().stringToDate(endDate),  
																pageNo, 
																pageSize);
		if(allAdKeywordViews > 0) {
			totalCount = allAdKeywordViews;
			pageCount = (int) Math.ceil(((float)totalCount / pageSize));
					
			if(adKeywordViewVO != null && adKeywordViewVO.size() > 0){
				totalSize = adKeywordViewVO.size();		
				for(PfpAdKeywordViewVO vo:adKeywordViewVO){
					totalPv += vo.getAdKeywordPv();
					totalClk += vo.getAdKeywordClk();		
					totalCost += vo.getAdKeywordClkPrice();
				}
				
				if(totalClk > 0 || totalPv > 0){
					totalClkRate = (float)totalClk / (float)totalPv*100;
				}
				
				if(totalCost > 0 || totalClk > 0){
					totalAvgCost = (float)totalCost / (float)totalClk;	
				}
			}
		}
		
		// 查詢日期寫進cookie
		this.setChooseDate(startDate, endDate);
		//log.info(" pageNo = "+pageNo+" pageSize = "+pageSize+" totalCount = "+totalCount+" pageCount = "+pageCount);
		
		return SUCCESS;
	}
	
	public void setPfpAdKeywordService(IPfpAdKeywordService pfpAdKeywordService) {
		this.pfpAdKeywordService = pfpAdKeywordService;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public void setAdGroupSeq(String adGroupSeq) {
		this.adGroupSeq = adGroupSeq;
	}

	public String getAdGroupSeq() {
		return adGroupSeq;
	}

	public List<PfpAdKeywordViewVO> getAdKeywordViewVO() {
		return adKeywordViewVO;
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

	public int getTotalInvalidClk() {
		return totalInvalidClk;
	}
	
}
