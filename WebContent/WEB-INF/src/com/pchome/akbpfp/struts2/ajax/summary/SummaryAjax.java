package com.pchome.akbpfp.struts2.ajax.summary;

import java.util.List;

import com.pchome.akbpfp.db.service.ad.IPfpAdPvclkService;
import com.pchome.akbpfp.db.vo.ad.AdLayerVO;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.ad.EnumAdLayer;
import com.pchome.soft.util.DateValueUtil;

public class SummaryAjax extends BaseCookieAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2115348763760269256L;
	
	private IPfpAdPvclkService pfpAdPvclkService;
	
	private String startDate;
	private String endDate;
	private String adLayerType;
	private List<AdLayerVO> adLayerVO;

	private int pageNo = 1;       				// 目前頁數
	private int pageSize = 20;     				// 每頁幾筆
	private int pageCount = 0;    				// 共幾頁
	private long totalCount = 0;   				// 共幾筆
	
	public String searchAdLayerAjax() throws Exception{
		long pvclkCosts = 0;		
		for(EnumAdLayer adLayer:EnumAdLayer.values()){
			if(adLayer.getType().equals(adLayerType)){
				pvclkCosts =  pfpAdPvclkService.detailResultCount(super.getCustomer_info_id(), 
																adLayer, 
																DateValueUtil.getInstance().getDateForStartDateAddDay(startDate, 0), 
																DateValueUtil.getInstance().getDateForStartDateAddDay(endDate, 0));
				adLayerVO = pfpAdPvclkService.detailResultCost(super.getCustomer_info_id(), 
																adLayer, 
																DateValueUtil.getInstance().getDateForStartDateAddDay(startDate, 0), 
																DateValueUtil.getInstance().getDateForStartDateAddDay(endDate, 0),
																pageNo, 
																pageSize);
			}
			
		}
		
		totalCount = pvclkCosts;
		pageCount = (int) Math.ceil(((float)totalCount / pageSize));
		
		// 查詢日期寫進cookie
		this.setChooseDate(startDate, endDate);
		
		return SUCCESS;
	}

	public void setPfpAdPvclkService(IPfpAdPvclkService pfpAdPvclkService) {
		this.pfpAdPvclkService = pfpAdPvclkService;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public void setAdLayerType(String adLayerType) {
		this.adLayerType = adLayerType;
	}

	public List<AdLayerVO> getAdLayerVO() {
		return adLayerVO;
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
}
