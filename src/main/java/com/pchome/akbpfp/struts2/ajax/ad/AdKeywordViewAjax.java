package com.pchome.akbpfp.struts2.ajax.ad;

import java.util.List;

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
	
	//廣泛比對
	private int totalPv = 0;						
	private int totalClk = 0;						
	private float totalClkRate = 0;
	private float totalAvgCost = 0;
	private int totalCost = 0;
	private int totalInvalidClk = 0;
	
	//詞組比對
	private int totalPhrasePv = 0;						
	private int totalPhraseClk = 0;						
	private float totalPhraseClkRate = 0;
	private float totalPhraseAvgCost = 0;
	private int totalPhraseCost = 0;
	private int totalPhraseInvalidClk = 0;
	
	//精準比對
	private int totalPrecisionPv = 0;						
	private int totalPrecisionClk = 0;						
	private float totalPrecisionClkRate = 0;
	private float totalPrecisionAvgCost = 0;
	private int totalPrecisionCost = 0;
	private int totalPrecisionInvalidClk = 0;
	
	//總計
	private int totalPvSum = 0;						
	private int totalClkSum = 0;						
	private float totalClkRateSum = 0;
	private float totalAvgCostSum = 0;
	private int totalCostSum = 0;
	private int totalInvalidClkSum = 0;
	
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
					//廣泛比對
					totalPv += vo.getAdKeywordPv();
					totalClk += vo.getAdKeywordClk();		
					totalCost += vo.getAdKeywordClkPrice();
					
					//詞組比對
					totalPhrasePv += vo.getAdKeywordPhrasePv();
					totalPhraseClk += vo.getAdKeywordPhraseClk();		
					totalPhraseCost += vo.getAdKeywordPhraseClkPrice();
					
					//精準比對
					totalPrecisionPv += vo.getAdKeywordPrecisionPv();
					totalPrecisionClk += vo.getAdKeywordPrecisionClk();		
					totalPrecisionCost += vo.getAdKeywordPrecisionClkPrice();
					
					//總計
					totalPvSum += vo.getAdKeywordPvSum();
					totalClkSum += vo.getAdKeywordClkSum();		
					totalCostSum += vo.getAdKeywordClkPriceSum();
				}
				
				//廣泛比對
				if(totalClk > 0 || totalPv > 0){
					totalClkRate = (float)totalClk / (float)totalPv*100;
				}
				if(totalCost > 0 || totalClk > 0){
					totalAvgCost = (float)totalCost / (float)totalClk;	
				}
				
				//詞組比對
				if(totalPhraseClk > 0 || totalPhrasePv > 0){
					totalPhraseClkRate = (float)totalPhraseClk / (float)totalPhrasePv*100;
				}
				if(totalPhraseCost > 0 || totalPhraseClk > 0){
					totalPhraseAvgCost = (float)totalPhraseCost / (float)totalPhraseClk;	
				}
				
				//精準比對
				if(totalPrecisionClk > 0 || totalPrecisionPv > 0){
					totalPrecisionClkRate = (float)totalPrecisionClk / (float)totalPrecisionPv*100;
				}
				if(totalPrecisionCost > 0 || totalPrecisionClk > 0){
					totalPrecisionAvgCost = (float)totalPrecisionCost / (float)totalPrecisionClk;	
				}
				
				//總計
				if(totalClkSum > 0 || totalPvSum > 0){
					totalClkRateSum = (float)totalClkSum / (float)totalPvSum*100;
				}
				if(totalCostSum > 0 || totalClkSum > 0){
					totalAvgCostSum = (float)totalCostSum / (float)totalClkSum;	
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

	public int getTotalPhrasePv() {
		return totalPhrasePv;
	}

	public int getTotalPhraseClk() {
		return totalPhraseClk;
	}

	public float getTotalPhraseClkRate() {
		return totalPhraseClkRate;
	}

	public float getTotalPhraseAvgCost() {
		return totalPhraseAvgCost;
	}

	public int getTotalPhraseCost() {
		return totalPhraseCost;
	}

	public int getTotalPhraseInvalidClk() {
		return totalPhraseInvalidClk;
	}

	public int getTotalPrecisionPv() {
		return totalPrecisionPv;
	}

	public int getTotalPrecisionClk() {
		return totalPrecisionClk;
	}

	public float getTotalPrecisionClkRate() {
		return totalPrecisionClkRate;
	}

	public float getTotalPrecisionAvgCost() {
		return totalPrecisionAvgCost;
	}

	public int getTotalPrecisionCost() {
		return totalPrecisionCost;
	}

	public int getTotalPrecisionInvalidClk() {
		return totalPrecisionInvalidClk;
	}

	public int getTotalPvSum() {
		return totalPvSum;
	}

	public int getTotalClkSum() {
		return totalClkSum;
	}

	public float getTotalClkRateSum() {
		return totalClkRateSum;
	}

	public float getTotalAvgCostSum() {
		return totalAvgCostSum;
	}

	public int getTotalCostSum() {
		return totalCostSum;
	}

	public int getTotalInvalidClkSum() {
		return totalInvalidClkSum;
	}
	
}
