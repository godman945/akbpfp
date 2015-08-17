package com.pchome.akbpfp.struts2.ajax.ad;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import com.pchome.akbpfp.db.service.ad.IPfpAdService;
import com.pchome.akbpfp.db.vo.ad.PfpAdAdViewVO;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.ad.EnumAdType;
import com.pchome.soft.util.DateValueUtil;

public class AdAdViewAjax extends BaseCookieAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private IPfpAdService pfpAdService;
	
	private String adGroupSeq;
	private String keyword;
	private String searchType;
	private String startDate;
	private String endDate;
	private List<PfpAdAdViewVO> adAdViewVO;

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
	private String photoPath;
	
	public String adAdViewTableAjax() throws Exception{
		int type = Integer.parseInt(searchType);
		long allAdActionViews = 0;
		for(EnumAdType adType:EnumAdType.values()){
			if(adType.getType() == type){
				allAdActionViews = pfpAdService.getPfpAdCount(super.getCustomer_info_id(), 
																adGroupSeq, 
																keyword);
				adAdViewVO = pfpAdService.getAdAdView(super.getCustomer_info_id(), 
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

			if(adAdViewVO != null && adAdViewVO.size() > 0){
				totalSize = adAdViewVO.size();		
				for(PfpAdAdViewVO vo:adAdViewVO){
					Map<String,String> imgmap = new HashMap<String,String>();
					imgmap = getImgSize(vo.getOriginalImg());
					vo.setImgWidth(imgmap.get("imgWidth"));
					vo.setImgHeight(imgmap.get("imgHeight"));
					
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
	
	public Map<String,String> getImgSize(String originalImg){
		Map<String,String> imgmap = new HashMap<String,String>();
		File picture = new File(photoPath,originalImg.substring(originalImg.lastIndexOf("/") +1));
		String imgWidth = "0";
		String imgHeight = "0";
		log.info("------------------1.originalImg=" + photoPath + originalImg.substring(originalImg.lastIndexOf("/") +1));
		if(picture != null){
			FileInputStream is = null;
			BufferedImage sourceImg = null;
			try {
				is = new FileInputStream(picture);
				sourceImg = javax.imageio.ImageIO.read(is);
				imgWidth = Integer.toString(sourceImg.getWidth());
				imgHeight = Integer.toString(sourceImg.getHeight());
				log.info("------------------2.imgWidth=" + sourceImg.getWidth());
				log.info("------------------3.imgHeight=" + sourceImg.getHeight());
				log.info("------------------4.String(imgWidth)=" + imgWidth);
				log.info("------------------5.String(imgHeight)=" + imgHeight);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		imgmap.put("imgWidth", imgWidth);
		imgmap.put("imgHeight", imgHeight);
		
		return imgmap;
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
		return adAdViewVO;
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

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}
	
}
