package com.pchome.akbpfp.struts2.ajax.ad;

import java.util.List;

import com.pchome.akbpfp.db.service.ad.IPfpAdGroupService;
import com.pchome.akbpfp.db.vo.ad.PfpAdGroupViewVO;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.ad.EnumAdSearchPriceType;
import com.pchome.enumerate.ad.EnumAdType;
import com.pchome.soft.util.DateValueUtil;

public class AdGroupViewAjax extends BaseCookieAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private IPfpAdGroupService pfpAdGroupService;
	
	private String keyword;
	private String searchType;
	private String startDate;
	private String endDate;
	private String adActionSeq;
	private List<PfpAdGroupViewVO> adGroupViewVO;

	private int pageNo = 1;       				// 初始化目前頁數
	private int pageSize = 20;     				// 初始化每頁幾筆
	private int pageCount = 0;    				// 初始化共幾頁
	private long totalCount = 0;   				// 初始化共幾筆

	private EnumAdSearchPriceType[] adSearchPriceType;
	private int totalSize = 0;						
	private int totalPv = 0;						
	private int totalClk = 0;						
	private float totalClkRate = 0;
	private float totalAvgCost = 0;
	private int totalCost = 0;
	private int totalInvalidClk = 0;
	private String groupMaxPrice;
	public String adGroupViewTableAjax() throws Exception{
		int type = Integer.parseInt(searchType);
		//List<PfpAdGroupViewVO> allAdGroupViews = null;
		long allAdGroupViews = 0;
		
		//adActionSeq = "aa_201306180015";
		//log.info(" adActionSeq = "+adActionSeq);
		
		for(EnumAdType adType:EnumAdType.values()){
			if(adType.getType() == type){
				allAdGroupViews = pfpAdGroupService.getPfpAdGroupCount(super.getCustomer_info_id(), 
																	adActionSeq, 
																	keyword);
				adGroupViewVO = pfpAdGroupService.getAdGroupView(super.getCustomer_info_id(), 
																	adActionSeq, 
																	keyword, 
																	adType, 
																	DateValueUtil.getInstance().stringToDate(startDate), 
																	DateValueUtil.getInstance().stringToDate(endDate), 
																	pageNo, 
																	pageSize);
			}
		}
		
		adSearchPriceType = EnumAdSearchPriceType.values();
		
		if(allAdGroupViews > 0){
			totalCount = allAdGroupViews;
			pageCount = (int) Math.ceil(((float)totalCount / pageSize));

			if(adGroupViewVO != null && adGroupViewVO.size() > 0){
				totalSize = adGroupViewVO.size();
				
				for(PfpAdGroupViewVO vo:adGroupViewVO){
					totalPv += vo.getAdPv();
					totalClk += vo.getAdClk();		
					totalCost += vo.getAdClkPrice();
					totalInvalidClk += vo.getInvalidClk();
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
			
		return SUCCESS;
	}
	
//	public String adGroupSuggestPriceAjax() throws Exception{
//
//		PfpAdSysprice adSysprice = pfpAdSyspriceService.getAdSysprice(adPoolSeq);
//
//		sysprice = adSysprice.getSysprice();
//		
//		adAsideRate = syspriceOperaterAPI.getAdAsideRate(userprice);
//
//		
//		return SUCCESS;
//	}
	

	public void setPfpAdGroupService(IPfpAdGroupService pfpAdGroupService) {
		this.pfpAdGroupService = pfpAdGroupService;
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

	public void setAdActionSeq(String adActionSeq) {
		this.adActionSeq = adActionSeq;
	}
	
	public String getAdActionSeq() {
		return adActionSeq;
	}

	public List<PfpAdGroupViewVO> getAdGroupViewVO() {
		return adGroupViewVO;
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

	public EnumAdSearchPriceType[] getAdSearchPriceType() {
		return adSearchPriceType;
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

	public String getGroupMaxPrice() {
	    return groupMaxPrice;
	}

	public void setGroupMaxPrice(String groupMaxPrice) {
	    this.groupMaxPrice = groupMaxPrice;
	}
	
}
