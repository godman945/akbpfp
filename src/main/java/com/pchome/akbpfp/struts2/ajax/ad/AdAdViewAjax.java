package com.pchome.akbpfp.struts2.ajax.ad;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.pchome.akbpfp.db.dao.report.AdReportVO;
import com.pchome.akbpfp.db.service.ad.IPfpAdService;
import com.pchome.akbpfp.db.vo.ad.PfpAdAdViewConditionVO;
import com.pchome.akbpfp.db.vo.ad.PfpAdAdViewVO;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.ad.EnumAdType;
import com.pchome.soft.util.DateValueUtil;
import com.pchome.utils.CommonUtils;

public class AdAdViewAjax extends BaseCookieAction{
	
	private static final long serialVersionUID = 1L;
	private AdReportVO adReportVO;
	private String previewUrl;
	private IPfpAdService pfpAdService;
	private String adGroupSeq;
	private String keyword;
	private String searchType;
	private String startDate;
	private String endDate;
	private List<PfpAdAdViewVO> adAdViewVOList;
	private List<AdReportVO> adReportVOList;
	private int pageNo = 1;       				// 初始化目前頁數
	private int pageSize = 20;     				// 初始化每頁幾筆
	private int pageCount = 0;    				// 初始化共幾頁
	private long totalCount = 0;   				// 初始化共幾筆
	private int totalSize = 0;						
	private int totalPv = 0;						
	private int totalClk = 0;						
	private float totalClkRate = 0;
	private float totalAvgCost = 0;
	private float totalThousandsCost = 0;
	private int totalCost = 0;
	private int totalInvalidClk = 0;
	private String jdbcEnvironment;
	private String adType;
	
	public String adAdViewTableAjax() throws Exception{
		int type = Integer.parseInt(searchType);
		long allAdActionViews = 0;
		for(EnumAdType adType:EnumAdType.values()){
			if(adType.getType() == type){
				allAdActionViews = pfpAdService.getPfpAdCount(super.getCustomer_info_id(), 
																adGroupSeq, 
																keyword);
				adAdViewVOList = pfpAdService.getAdAdView(super.getCustomer_info_id(), 
																adGroupSeq, 
																keyword, 
																adType, 
																DateValueUtil.getInstance().stringToDate(startDate), 
																DateValueUtil.getInstance().stringToDate(endDate),
																pageNo, 
																pageSize);
			}
		}

		if(allAdActionViews > 0) {
			totalCount = allAdActionViews;
			pageCount = (int) Math.ceil(((float)totalCount / pageSize));

			if(adAdViewVOList != null && adAdViewVOList.size() > 0){
				totalSize = adAdViewVOList.size();		
				for(PfpAdAdViewVO vo:adAdViewVOList){
					if(StringUtils.equals("N", vo.getHtml5Tag())){
						Map<String,String> imgmap = new HashMap<String,String>();
						imgmap = getImgSize(vo.getOriginalImg());
						vo.setImgWidth(imgmap.get("imgWidth"));
						vo.setImgHeight(imgmap.get("imgHeight"));
					}
					
					if("IMG".equals(vo.getAdStyle())){
						String showUrl = vo.getRealUrl();
						showUrl = showUrl.replaceAll("http://", "");
						showUrl = showUrl.replaceAll("https://", "");
		            	if(showUrl.lastIndexOf(".com/") != -1){
		            		showUrl = showUrl.substring(0, showUrl.lastIndexOf(".com/") + 4);
		            	}
		            	if(showUrl.lastIndexOf(".tw/") != -1){
		            		showUrl = showUrl.substring(0, showUrl.lastIndexOf(".tw/") + 3);
		            	}
		            	vo.setShowUrl(showUrl);
					}
					
					totalPv += vo.getAdPv();
					totalClk += vo.getAdClk();		
					totalCost += vo.getAdClkPrice();
					totalInvalidClk += vo.getInvalidClk();
					if(totalCost > 0 && ((float)totalPv / 1000) > 0){
						this.totalThousandsCost += (float)totalCost / ((float)totalPv / 1000);
					}
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
	
	public Map<String,String> getImgSize(String originalImg) throws Exception {
		Map<String,String> imgmap = new HashMap<String,String>();
		imgmap.put("imgWidth", null);
		imgmap.put("imgHeight", null);
		if(StringUtils.isNotBlank(originalImg)){
			File picture = null;
			String path = (originalImg.indexOf("D:/") >= 0) ? originalImg : "/home/webuser/akb/pfp/" +  originalImg.replace("\\", "/");
			picture = new File(path);
			if(picture != null){
				Map<String,String> imgInfo = CommonUtils.getInstance().getImgInfo(picture);
				imgmap.put("imgWidth", imgInfo.get("imgWidth"));
				imgmap.put("imgHeight", imgInfo.get("imgHeight"));
			}
		}
		return imgmap;
	}

	
	/**
	 * 影音廣告明細
	 * */
	public String adAdVideoViewTableAjax() throws Exception{
		PfpAdAdViewConditionVO pfpAdAdViewConditionVO = new PfpAdAdViewConditionVO();
		pfpAdAdViewConditionVO.setCustomerInfoId(super.getCustomer_info_id());
		pfpAdAdViewConditionVO.setAdGroupSeq(adGroupSeq);
		pfpAdAdViewConditionVO.setKeyword(keyword);
		pfpAdAdViewConditionVO.setAdType(adType);
		pfpAdAdViewConditionVO.setStartDate(startDate);
		pfpAdAdViewConditionVO.setEndDate(endDate);
		pfpAdAdViewConditionVO.setPage(pageNo);
		pfpAdAdViewConditionVO.setPageSize(pageSize);
		int limit = (pageNo - 1) * pageSize;
		int max = pageNo * pageSize;
		pfpAdAdViewConditionVO.setLimit(limit);
		pfpAdAdViewConditionVO.setMax(max);
		
		this.adReportVO = pfpAdService.getAdAdVideoDetailViewCount(pfpAdAdViewConditionVO);
		this.adReportVOList = pfpAdService.getAdAdVideoDetailView(pfpAdAdViewConditionVO);
		
		totalSize = adReportVOList!= null ? adReportVOList.size() : 0;
		pageCount = (int) Math.ceil(((double)totalSize / (double)pageSize));
		
		
		
		/**多執行緒*/
//		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(500);
//		ThreadServiceBean threadServiceBean = new ThreadServiceBean();
//		threadServiceBean.setPfpAdService(pfpAdService);
//		JSONObject conditionJson = JSONObject.fromObject(pfpAdAdViewConditionVO);
		
//		//第一條執行緒查詢總數
//		boolean adViewVideoCountResultFlag = true;
//		Future<String> pfpThreadProcessAdViewVideoCountResult = null;
//		PfpThreadProcess pfpThreadProcessAdViewVideoCount = new PfpThreadProcess(conditionJson,EnumAdThreadType.AD_VIEW_VIDEO_COUNT,threadServiceBean);
//		pfpThreadProcessAdViewVideoCountResult = executor.submit(pfpThreadProcessAdViewVideoCount);
//		
//		//第二條執行緒查詢總數
//		boolean adViewVideoDetailResultFlag = true;
//		Future<String> pfpThreadProcessAdViewVideoDetailResult = null;
//		PfpThreadProcess pfpThreadProcessAdViewVideoDetail = new PfpThreadProcess(conditionJson,EnumAdThreadType.AD_VIEW_VIDEO_DETAIL,threadServiceBean);
//		pfpThreadProcessAdViewVideoDetailResult = executor.submit(pfpThreadProcessAdViewVideoDetail);
//		
//		
//		//1.第一條執行緒查詢總數結果
//		while (adViewVideoCountResultFlag) {
//			if (pfpThreadProcessAdViewVideoCountResult.isDone()) {
//				String result = pfpThreadProcessAdViewVideoCountResult.get();
//				this.pfpAdAdVideoViewSumVO = (PfpAdAdVideoViewSumVO) JSONObject.toBean(JSONObject.fromObject(result), PfpAdAdVideoViewSumVO.class);
//				totalSize = pfpAdAdVideoViewSumVO.getTotalSize();
//				pageCount = (int) Math.ceil(((double)totalSize / (double)pageSize));
//				adViewVideoCountResultFlag = false;
//			}
//		}
//		
//		//2.第二條執行緒查詢明細結果
//		this.pfpAdAdVideoViewVOList = new ArrayList<>();
//		while (adViewVideoDetailResultFlag) {
//			if (pfpThreadProcessAdViewVideoDetailResult.isDone()) {
//				String result = pfpThreadProcessAdViewVideoDetailResult.get();
//				org.json.JSONArray jsonArray = new org.json.JSONArray(result);
//				for (int i = 0; i < jsonArray.length(); i++) {
//					org.json.JSONObject json =  (org.json.JSONObject) jsonArray.get(i);
//					PfpAdAdVideoViewVO pfpAdAdVideoViewVO = (PfpAdAdVideoViewVO) JSONObject.toBean(JSONObject.fromObject(json.toString()), PfpAdAdVideoViewVO.class);
//					if(i==0){
//						previewUrl = pfpAdAdVideoViewVO.getVideoUrl();
//					}
//					pfpAdAdVideoViewVOList.add(pfpAdAdVideoViewVO);
//				}
//				adViewVideoDetailResultFlag = false;
//			}
//		}
//		
//		//3.執行緒全部執行完畢
//		if(!adViewVideoCountResultFlag && !adViewVideoDetailResultFlag){
//			executor.shutdown();
//		}
		
		// 查詢日期寫進cookie
		this.setChooseDate(startDate, endDate);
		return SUCCESS;
	}
	
	
	public void setPfpAdService(IPfpAdService pfpAdService) {
		this.pfpAdService = pfpAdService;
	}

	public void setAdGroupSeq(String adGroupSeq) {
		this.adGroupSeq = adGroupSeq;
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
	
	public List<PfpAdAdViewVO> getAdAdViewVO() {
		return adAdViewVOList;
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

	public String getAdType() {
		return adType;
	}

	public void setAdType(String adType) {
		this.adType = adType;
	}

	public AdReportVO getAdReportVO() {
		return adReportVO;
	}

	public void setAdReportVO(AdReportVO adReportVO) {
		this.adReportVO = adReportVO;
	}

	public String getPreviewUrl() {
		return previewUrl;
	}

	public void setPreviewUrl(String previewUrl) {
		this.previewUrl = previewUrl;
	}

	public String getJdbcEnvironment() {
		return jdbcEnvironment;
	}

	public void setJdbcEnvironment(String jdbcEnvironment) {
		this.jdbcEnvironment = jdbcEnvironment;
	}

	public float getTotalThousandsCost() {
		return totalThousandsCost;
	}

	public List<PfpAdAdViewVO> getAdAdViewVOList() {
		return adAdViewVOList;
	}

	public void setAdAdViewVOList(List<PfpAdAdViewVO> adAdViewVOList) {
		this.adAdViewVOList = adAdViewVOList;
	}

	public List<AdReportVO> getAdReportVOList() {
		return adReportVOList;
	}

	public void setAdReportVOList(List<AdReportVO> adReportVOList) {
		this.adReportVOList = adReportVOList;
	}
}
