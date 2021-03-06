package com.pchome.akbpfp.struts2.action.ad;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.pchome.akbpfp.db.pojo.PfpAd;
import com.pchome.akbpfp.db.pojo.PfpAdGroup;
import com.pchome.akbpfp.db.pojo.PfpBoard;
import com.pchome.akbpfp.db.service.accesslog.IAdmAccesslogService;
import com.pchome.akbpfp.db.service.ad.IPfpAdGroupService;
import com.pchome.akbpfp.db.service.ad.IPfpAdService;
import com.pchome.akbpfp.db.service.advideo.IPfpAdVideoSourceService;
import com.pchome.akbpfp.db.service.board.IPfpBoardService;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.ad.EnumAdType;
import com.pchome.enumerate.utils.EnumStatus;
import com.pchome.rmi.accesslog.EnumAccesslogAction;
import com.pchome.rmi.board.EnumBoardType;
import com.pchome.rmi.mailbox.EnumCategory;
import com.pchome.soft.util.DateValueUtil;

public class AdAdViewAction extends BaseCookieAction{

	private static final long serialVersionUID = 1L;
	private IPfpAdGroupService pfpAdGroupService;
	private IPfpAdService pfpAdService;
	private IPfpBoardService pfpBoardService;
	private IAdmAccesslogService admAccesslogService;
	private IPfpAdVideoSourceService pfpAdVideoSourceService;
	private LinkedHashMap<String,String> dateSelectMap; // 查詢日期頁面顯示
	private String startDate;							// 開始日期
	private String endDate;								// 結束日期
	private PfpBoard board;
	private EnumBoardType[] enumBoardType;

	
	private String adGroupSeq;
	private PfpAdGroup adGroup;
	private EnumAdType[] searchAdType;
	private String adAdSeq;
	private String status;
	private String groupMaxPrice;
	private String adType;
	private String adOperatingRule;
	private String adPreviewVideoURL="";
	private String previewHtml = "";
	private String adPreviewVideoBgImg="";
	private String realUrl = "";
	
	
	public String execute() throws Exception{
		
		searchAdType = EnumAdType.values();
		adGroup = pfpAdGroupService.getPfpAdGroupBySeq(adGroupSeq);
		dateSelectMap = DateValueUtil.getInstance().getDateRangeMap();
		startDate = this.getChoose_start_date();											
		endDate = this.getChoose_end_date();
		
		enumBoardType = EnumBoardType.values();
		board = pfpBoardService.findAccountRemainBoard(EnumBoardType.FINANCE, 
														super.getCustomer_info_id(), 
														EnumCategory.REMAIN_NOT_ENOUGH);
		
		adType = adGroup.getPfpAdAction().getAdType().toString();
		adOperatingRule = adGroup.getPfpAdAction().getAdOperatingRule();
		return SUCCESS;
	}
	
	public String updateAdAdStatusAction() throws Exception{
		
		String[] adAdSeqs = adAdSeq.split(",");
		int statusId = Integer.parseInt(status);
		String adOperatingRule = "";
		for(int i=0;i<adAdSeqs.length;i++){
//			//log.info(" adGroupSeqs[i] = "+adGroupSeqs[i]);
			PfpAd ad = pfpAdService.getPfpAdBySeq(adAdSeqs[i]);
			adOperatingRule = ad.getPfpAdGroup().getPfpAdAction().getAdOperatingRule();
			//log.info(" adGroup = "+adGroup);
			if(ad != null && StringUtils.isNotBlank(status)){
				StringBuffer msg = new StringBuffer();
				msg.append("狀態異動：");
				msg.append("檢視廣告 ==> ");
				msg.append(ad.getPfpAdGroup().getPfpAdAction().getAdActionName()).append(" ==> ");
				msg.append(ad.getPfpAdGroup().getAdGroupName()).append(" ==> ");
				msg.append(ad.getAdSeq()).append("(").append(ad.getAdSeq()).append(")").append("：");
				
				for(EnumStatus adStatus:EnumStatus.values()){
					if(adStatus.getStatusId() == ad.getAdStatus()){
						msg.append(" ");
						msg.append(adStatus.getStatusDesc());
						msg.append(" ==> ");
					}
				}
				
				for(EnumStatus adStatus:EnumStatus.values()){
					if(adStatus.getStatusId() == statusId){
						msg.append(" ");
						msg.append(adStatus.getStatusDesc());
					}
				}

				admAccesslogService.recordAdLog(EnumAccesslogAction.AD_STATUS_MODIFY, 
												msg.toString(), 
												super.getId_pchome(), 
												super.getCustomer_info_id(), 
												super.getUser_id(), 
												super.request.getRemoteAddr());
				
				ad.setAdStatus(statusId);
				ad.setAdCreateTime(new Date());
				pfpAdService.saveOrUpdateWithCommit(ad);
				
				adGroupSeq = ad.getPfpAdGroup().getAdGroupSeq();
			}
		}	
		
		// adGroup 下層關鍵字或播放明細都被關閉, 此分類改為未完成
		if(StringUtils.isNotBlank(adGroupSeq)){
			//List<PfpAdKeyword> adKeyword = pfpAdKeywordService.validAdKeyword(adGroupSeq);
			List<PfpAd> adAd = pfpAdService.validAdAd(adGroupSeq);
			
			if(adAd.size() <= 0 ){
				PfpAdGroup adGroup = pfpAdGroupService.getPfpAdGroupBySeq(adGroupSeq);
				adGroup.setAdGroupStatus(EnumStatus.UnDone.getStatusId());
				adGroup.setAdGroupUpdateTime(new Date());
				pfpAdGroupService.saveOrUpdate(adGroup);
			}
		}
		
		
		if(adOperatingRule.equals("VIDEO")){
			return "adAdVideoView";
		}
		return SUCCESS;
	}
	
	
	/**
	 * 
	 * */
	public String adAdVideoView() throws Exception{
		searchAdType = EnumAdType.values();
		adGroup = pfpAdGroupService.getPfpAdGroupBySeq(adGroupSeq);
		dateSelectMap = DateValueUtil.getInstance().getDateRangeMap();
		startDate = this.getChoose_start_date();											
		endDate = this.getChoose_end_date();
		enumBoardType = EnumBoardType.values();
		board = pfpBoardService.findAccountRemainBoard(EnumBoardType.FINANCE, 
														super.getCustomer_info_id(), 
														EnumCategory.REMAIN_NOT_ENOUGH);
		adType = adGroup.getPfpAdAction().getAdType().toString();
		
		return SUCCESS;
	}
	
	public void setPfpAdGroupService(IPfpAdGroupService pfpAdGroupService) {
		this.pfpAdGroupService = pfpAdGroupService;
	}
	
	public void setPfpAdService(IPfpAdService pfpAdService) {
		this.pfpAdService = pfpAdService;
	}

	public void setPfpBoardService(IPfpBoardService pfpBoardService) {
		this.pfpBoardService = pfpBoardService;
	}

	public void setAdmAccesslogService(IAdmAccesslogService admAccesslogService) {
		this.admAccesslogService = admAccesslogService;
	}

	public LinkedHashMap<String, String> getDateSelectMap() {
		return dateSelectMap;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setAdGroupSeq(String adGroupSeq) {
		this.adGroupSeq = adGroupSeq;
	}

	public EnumAdType[] getSearchAdType() {
		return searchAdType;
	}
	
	public PfpBoard getBoard() {
		return board;
	}

	public EnumBoardType[] getEnumBoardType() {
		return enumBoardType;
	}
	
	public String getAdGroupSeq() {
		return adGroupSeq;
	}

	public PfpAdGroup getAdGroup() {
		return adGroup;
	}

	public void setAdAdSeq(String adAdSeq) {
		this.adAdSeq = adAdSeq;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

	public String getGroupMaxPrice() {
	    return groupMaxPrice;
	}

	public void setGroupMaxPrice(String groupMaxPrice) {
	    this.groupMaxPrice = groupMaxPrice;
	}

	public String getAdType() {
		return adType;
	}

	public void setAdType(String adType) {
		this.adType = adType;
	}

	public String getAdOperatingRule() {
		return adOperatingRule;
	}

	public void setAdOperatingRule(String adOperatingRule) {
		this.adOperatingRule = adOperatingRule;
	}

	public String getAdPreviewVideoURL() {
		return adPreviewVideoURL;
	}

	public void setAdPreviewVideoURL(String adPreviewVideoURL) {
		this.adPreviewVideoURL = adPreviewVideoURL;
	}

	public String getPreviewHtml() {
		return previewHtml;
	}

	public void setPreviewHtml(String previewHtml) {
		this.previewHtml = previewHtml;
	}

	public String getAdPreviewVideoBgImg() {
		return adPreviewVideoBgImg;
	}

	public void setAdPreviewVideoBgImg(String adPreviewVideoBgImg) {
		this.adPreviewVideoBgImg = adPreviewVideoBgImg;
	}

	public IPfpAdVideoSourceService getPfpAdVideoSourceService() {
		return pfpAdVideoSourceService;
	}

	public void setPfpAdVideoSourceService(IPfpAdVideoSourceService pfpAdVideoSourceService) {
		this.pfpAdVideoSourceService = pfpAdVideoSourceService;
	}

	public String getRealUrl() {
		return realUrl;
	}

	public void setRealUrl(String realUrl) {
		this.realUrl = realUrl;
	}
	
}
