package com.pchome.akbpfp.struts2.ajax.summary;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pchome.akbpfp.db.service.ad.IPfpAdDetailService;
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
	private IPfpAdDetailService pfpAdDetailService;
	
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
				
				if("adAd".equals(adLayer.getType())){
					for(AdLayerVO AdData:adLayerVO){
						if("IMG".equals(AdData.getAdStyle())){
							Map<String,String> imgmap = new HashMap<String,String>();
							List<com.pchome.akbpfp.db.pojo.PfpAdDetail> pfpAdDetailList = pfpAdDetailService.getPfpAdDetails(null,AdData.getSeq(),null,null);
							if(pfpAdDetailList != null){
								for(com.pchome.akbpfp.db.pojo.PfpAdDetail pfpAdDetail:pfpAdDetailList){
									if("img".equals(pfpAdDetail.getAdDetailId())){
										AdData.setImg(pfpAdDetail.getAdDetailContent());
				                        if(AdData.getImg().indexOf("original") == -1){
				                        	if(AdData.getImg().lastIndexOf("/") >= 0){
				                        		String imgFilename = AdData.getImg().substring(AdData.getImg().lastIndexOf("/"));
				                        		AdData.setOriginalImg(AdData.getImg().replace(imgFilename, "/original" + imgFilename));	
				                        	}
				                        	AdData.setOriginalImg(AdData.getImg());
				                        } else {
				                        	AdData.setOriginalImg(AdData.getImg());
				                        }
				                        
				                        imgmap = getImgSize(AdData.getOriginalImg());
				                        AdData.setImgWidth(imgmap.get("imgWidth"));
				                        AdData.setImgHeight(imgmap.get("imgHeight"));
									} else if("real_url".equals(pfpAdDetail.getAdDetailId())){
										AdData.setRealUrl(pfpAdDetail.getAdDetailContent());
										String showUrl = pfpAdDetail.getAdDetailContent();
										showUrl = showUrl.replaceAll("http://", "");
										showUrl = showUrl.replaceAll("https://", "");
						            	if(showUrl.lastIndexOf(".com/") != -1){
						            		showUrl = showUrl.substring(0, showUrl.lastIndexOf(".com/") + 4);
						            	}
						            	if(showUrl.lastIndexOf(".tw/") != -1){
						            		showUrl = showUrl.substring(0, showUrl.lastIndexOf(".tw/") + 3);
						            	}
						            	
						            	AdData.setShowUrl(showUrl);
									}
								}	
							}
						}

					}
				}
				
			}
			
		}
		
		totalCount = pvclkCosts;
		pageCount = (int) Math.ceil(((float)totalCount / pageSize));
		
		// 查詢日期寫進cookie
		this.setChooseDate(startDate, endDate);
		
		return SUCCESS;
	}

	public Map<String,String> getImgSize(String originalImg) throws IOException {
		Map<String,String> imgmap = new HashMap<String,String>();
		String imgWidth = "0";
		String imgHeight = "0";
		File picture = null;
		FileInputStream is = null;
		BufferedImage sourceImg = null;
		try{
			picture = new File("/home/webuser/akb/pfp/" +  originalImg.replace("\\", "/"));
			if(picture != null){
				is = new FileInputStream(picture);
				sourceImg = javax.imageio.ImageIO.read(is);
				imgWidth = Integer.toString(sourceImg.getWidth());
				imgHeight = Integer.toString(sourceImg.getHeight());	
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(is != null){
				is.close();
			}
		}
		imgmap.put("imgWidth", imgWidth);
		imgmap.put("imgHeight", imgHeight);
		
		return imgmap;
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

	public void setPfpAdDetailService(IPfpAdDetailService pfpAdDetailService) {
		this.pfpAdDetailService = pfpAdDetailService;
	}

}
