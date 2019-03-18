package com.pchome.akbpfp.struts2.action.ad;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.pchome.akbpfp.api.ControlPriceAPI;
import com.pchome.akbpfp.api.SyspriceOperaterAPI;
import com.pchome.akbpfp.db.pojo.AdmAccesslog;
import com.pchome.akbpfp.db.pojo.PfpAdAction;
import com.pchome.akbpfp.db.pojo.PfpAdGroup;
import com.pchome.akbpfp.db.pojo.PfpAdKeyword;
import com.pchome.akbpfp.db.pojo.PfpBoard;
import com.pchome.akbpfp.db.service.accesslog.IAdmAccesslogService;
import com.pchome.akbpfp.db.service.ad.IPfpAdActionService;
import com.pchome.akbpfp.db.service.ad.IPfpAdGroupService;
import com.pchome.akbpfp.db.service.ad.IPfpAdKeywordService;
import com.pchome.akbpfp.db.service.board.IPfpBoardService;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.ad.EnumAdSearchPriceType;
import com.pchome.enumerate.ad.EnumAdType;
import com.pchome.enumerate.utils.EnumStatus;
import com.pchome.rmi.accesslog.EnumAccesslogAction;
import com.pchome.rmi.accesslog.EnumAccesslogChannel;
import com.pchome.rmi.accesslog.EnumAccesslogEmailStatus;
import com.pchome.rmi.board.EnumBoardType;
import com.pchome.rmi.mailbox.EnumCategory;
import com.pchome.soft.util.DateValueUtil;

public class AdGroupViewAction extends BaseCookieAction{	
    	private static final long serialVersionUID = 1L;
	private IPfpAdActionService pfpAdActionService;
	private IPfpAdGroupService pfpAdGroupService;
	private SyspriceOperaterAPI syspriceOperaterAPI;
	private IPfpBoardService pfpBoardService;
	private IAdmAccesslogService admAccesslogService;
	private IPfpAdKeywordService pfpAdKeywordService;
	private ControlPriceAPI controlPriceAPI;
	private String result;
	private LinkedHashMap<String,String> dateSelectMap; // 查詢日期頁面顯示
	private String startDate;							// 開始日期
	private String endDate;								// 結束日期
	private PfpBoard board;
	private EnumBoardType[] enumBoardType;
	private String groupMaxPrice;
	
	private String sysPriceAdPoolSeq;       	//廣告建議價取得 pool from api prop 注入
	
	private String adActionSeq;
	private PfpAdAction adAction;
	private EnumAdType[] searchAdType;
	private String adGroupSeq;
	private String status;
	private String userPrice;
	private String searchPriceType;
	private String adGroupSearchPrice;
	
	public String execute() throws Exception{
	    System.out.println(">>>"+groupMaxPrice);
		searchAdType = EnumAdType.values();
		adAction = pfpAdActionService.getPfpAdActionBySeq(adActionSeq);
		dateSelectMap = DateValueUtil.getInstance().getDateRangeMap();
		startDate = this.getChoose_start_date();											
		endDate = this.getChoose_end_date();
		enumBoardType = EnumBoardType.values();
		board = pfpBoardService.findAccountRemainBoard(EnumBoardType.FINANCE, 
														super.getCustomer_info_id(), 
														EnumCategory.REMAIN_NOT_ENOUGH);
		
		return SUCCESS;
	}
	
	@Transactional
	public String updateAdGroupStatusAction() throws Exception{
		
		//log.info(" adGroupSeq = "+adGroupSeq);
		//log.info(" status = "+status);
		
		String[] adGroupSeqs = adGroupSeq.split(",");
		int statusId = Integer.parseInt(status);
		
		for(int i=0;i<adGroupSeqs.length;i++){
			
			PfpAdGroup adGroup = pfpAdGroupService.getPfpAdGroupBySeq(adGroupSeqs[i]);
			
			if(adGroup != null && StringUtils.isNotBlank(status)){
				
				StringBuffer msg = new StringBuffer();
				msg.append("狀態異動：");
				msg.append("檢視廣告 ==> ");
				msg.append(adGroup.getPfpAdAction().getAdActionName()).append(" ==> ");
				msg.append(adGroup.getAdGroupName()).append("(").append(adGroup.getAdGroupSeq()).append(")").append("：");

				
				for(EnumStatus adStatus:EnumStatus.values()){
					if(adStatus.getStatusId() == adGroup.getAdGroupStatus()){
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
				
				adGroup.setAdGroupStatus(statusId);
				adGroup.setAdGroupCreateTime(new Date());
				pfpAdGroupService.saveOrUpdate(adGroup);
				
				adActionSeq = adGroup.getPfpAdAction().getAdActionSeq();
				
			}
		}		

		// adAction 下層分類都被關閉, 此廣告改為未完成
		if(StringUtils.isNotBlank(adActionSeq)){
			List<PfpAdGroup> adGroups = pfpAdGroupService.validAdGroup(adActionSeq);

			if(adGroups.size() <= 0 || adGroups == null ){
				PfpAdAction adAction = pfpAdActionService.getPfpAdActionBySeq(adActionSeq);
				
				adAction.setAdActionStatus(EnumStatus.UnDone.getStatusId());
				adAction.setAdActionUpdateTime(new Date());
				pfpAdActionService.saveOrUpdate(adAction);

				// 重算調控金額
				controlPriceAPI.countProcess(adAction.getPfpCustomerInfo());
			}
		}
		
		return SUCCESS;
	}

	public String updateAdGroupChannelPriceAction() throws Exception{
		
		//log.info(" adGroupSeq = "+adGroupSeq);
		//log.info(" searchPriceType = "+searchPriceType);
		//log.info("updateAdGroupChannelPriceAction");
		
		if(StringUtils.isNotBlank(adGroupSeq) && StringUtils.isNotBlank(userPrice)){
			
			PfpAdGroup adGroup = pfpAdGroupService.getPfpAdGroupBySeq(adGroupSeq);
			
			StringBuffer msg = new StringBuffer();
			msg.append("聯播廣告金額異動：");
			msg.append("檢視廣告 ==> ");
			msg.append(adGroup.getPfpAdAction().getAdActionName()).append(" ==> ");
			msg.append(adGroup.getAdGroupName()).append("(").append(adGroupSeq).append(")").append("：");
			msg.append(adGroup.getAdGroupChannelPrice());
			msg.append(" ==> ");
			msg.append(userPrice);
			
			admAccesslogService.recordAdLog(EnumAccesslogAction.AD_MONEY_MODIFY, 
											msg.toString(), 
											super.getId_pchome(), 
											super.getCustomer_info_id(), 
											super.getUser_id(), 
											super.request.getRemoteAddr());
			adGroup.setAdGroupChannelPrice(Float.valueOf(userPrice));
			adGroup.setAdGroupUpdateTime(new Date());
			pfpAdGroupService.saveOrUpdate(adGroup);
			
			adActionSeq = adGroup.getPfpAdAction().getAdActionSeq();
			
			//系統價更新 2018-01-12 停止更新價格出價以JOB為主
//			syspriceOperaterAPI.addAdSysprice(sysPriceAdPoolSeq, Float.valueOf(userPrice));
		}
		
		return SUCCESS;
	}
	
	public String updateAdGroupSearchPriceAction() throws Exception{
	    	long  startTime = System.currentTimeMillis(); 
		PfpAdGroup adGroup = pfpAdGroupService.getPfpAdGroupBySeq(adGroupSeq);
		if(!StringUtils.isNotBlank(adGroupSeq) && !StringUtils.isNotBlank(searchPriceType)){
		    return SUCCESS;
		}
		Date today = new Date();
		int type = Integer.parseInt(searchPriceType);
		adGroup.setAdGroupSearchPriceType(type);
		List<AdmAccesslog> admAccesslogList = new ArrayList<AdmAccesslog>();
		if( StringUtils.isNotBlank(adGroupSearchPrice) && type == EnumAdSearchPriceType.MYSELF_PRICE.getTypeId()){
		    StringBuffer msg = new StringBuffer();
		    msg.append("收尋廣告金額異動(自行出價):");
		    msg.append("檢視廣告 ==> ");
		    msg.append(adGroup.getPfpAdAction().getAdActionName()).append(" ==> ");
		    msg.append(adGroup.getAdGroupName()).append("(").append(adGroupSeq).append(")").append("：");
		    msg.append(adGroup.getAdGroupSearchPrice());
		    msg.append(" ==> ");
		    msg.append(adGroupSearchPrice);
			
		    AdmAccesslog admAccesslog = new AdmAccesslog();
		    admAccesslog.setMessage(msg.toString());
		    admAccesslog.setMemberId(super.getUser_id());
		    admAccesslog.setCustomerInfoId(super.getCustomer_info_id());
		    admAccesslog.setClientIp(super.request.getRemoteAddr());
		    admAccesslog.setAction(EnumAccesslogAction.AD_MONEY_MODIFY.getAction());
		    admAccesslog.setChannel(EnumAccesslogChannel.PFP.getChannel());
		    admAccesslog.setMemberId(super.getId_pchome());
		    admAccesslog.setCreateDate(today);
		    admAccesslog.setMailSend(EnumAccesslogEmailStatus.NO.getStatus());
		    admAccesslogList.add(admAccesslog);
		    
		    List<PfpAdKeyword> pfpAdKeywordList = new ArrayList<PfpAdKeyword>();
		    //連帶異動 keyword 收尋金額
		    for(PfpAdKeyword adKeyword:adGroup.getPfpAdKeywords()){
//			if(adGroupSearchPrice.equals(Float.toString(adKeyword.getAdKeywordSearchPrice()))){
//			    continue;
//			}
			AdmAccesslog admKeyWordSearchPriceAccesslog = new AdmAccesslog();
			StringBuffer keywordMsg = new StringBuffer();
			keywordMsg.append("收尋廣告金額異動(自行出價):");
			keywordMsg.append("檢視廣告 ==> ");
			keywordMsg.append(adKeyword.getPfpAdGroup().getPfpAdAction().getAdActionName()).append(" ==> ");
			keywordMsg.append(adKeyword.getPfpAdGroup().getAdGroupName()).append(" ==> ");
			keywordMsg.append(adKeyword.getAdKeyword()).append("(").append(adKeyword.getAdKeywordSeq()).append(")").append("：");
			keywordMsg.append(adKeyword.getAdKeywordSearchPrice());
			keywordMsg.append(" ==> ");
			keywordMsg.append(adGroupSearchPrice);
				
			admKeyWordSearchPriceAccesslog.setMessage(keywordMsg.toString());
			admKeyWordSearchPriceAccesslog.setMemberId(super.getUser_id());
			admKeyWordSearchPriceAccesslog.setCustomerInfoId(super.getCustomer_info_id());
			admKeyWordSearchPriceAccesslog.setClientIp(super.request.getRemoteAddr());
			admKeyWordSearchPriceAccesslog.setAction(EnumAccesslogAction.AD_MONEY_MODIFY.getAction());
			admKeyWordSearchPriceAccesslog.setChannel(EnumAccesslogChannel.PFP.getChannel());
			admKeyWordSearchPriceAccesslog.setMemberId(super.getId_pchome());
			admKeyWordSearchPriceAccesslog.setCreateDate(today);
			admKeyWordSearchPriceAccesslog.setMailSend(EnumAccesslogEmailStatus.NO.getStatus());
			admAccesslogList.add(admKeyWordSearchPriceAccesslog);
			
			adKeyword.setAdKeywordSearchPrice(Float.parseFloat(adGroupSearchPrice));
			adKeyword.setAdKeywordUpdateTime(today);
//			pfpAdKeywordService.saveOrUpdate(adKeyword);
			pfpAdKeywordList.add(adKeyword);
			}
		    	if(pfpAdKeywordList.size()>0){
		    	    pfpAdKeywordService.savePfpAdKeywordList(pfpAdKeywordList);
		    	}
		}
		
		
		
		
		if(type == EnumAdSearchPriceType.SUGGEST_PRICE.getTypeId()){
		    List<PfpAdKeyword> pfpAdKeyWordList = new ArrayList<PfpAdKeyword>();
		    //連帶異動 keyword 收尋金額
		    for(PfpAdKeyword adKeyword:adGroup.getPfpAdKeywords()){
			float suggestPrice = syspriceOperaterAPI.getKeywordSuggesPrice(adKeyword.getAdKeyword());
			if(suggestPrice >= Float.valueOf(groupMaxPrice)){
			    suggestPrice = Float.valueOf(groupMaxPrice);
			}
			adKeyword.setAdKeywordSearchPrice(suggestPrice);
			adKeyword.setAdKeywordUpdateTime(today);
			pfpAdKeyWordList.add(adKeyword);
				
			StringBuffer keywordMsg = new StringBuffer();
			keywordMsg.append("收尋廣告金額異動(系統建議價格):");
			keywordMsg.append("檢視廣告 ==> ");
			keywordMsg.append(adKeyword.getPfpAdGroup().getPfpAdAction().getAdActionName()).append(" ==> ");
			keywordMsg.append(adKeyword.getPfpAdGroup().getAdGroupName()).append(" ==> ");
			keywordMsg.append(adKeyword.getAdKeyword()).append("(").append(adKeyword.getAdKeywordSeq()).append(")").append("：");
			keywordMsg.append(adKeyword.getAdKeywordSearchPrice());
			keywordMsg.append(" ==> ");
			keywordMsg.append(suggestPrice);
				
			AdmAccesslog admKeyWordSuggestPriceAccesslog = new AdmAccesslog();
			admKeyWordSuggestPriceAccesslog.setMessage(keywordMsg.toString());
			admKeyWordSuggestPriceAccesslog.setMemberId(super.getUser_id());
			admKeyWordSuggestPriceAccesslog.setCustomerInfoId(super.getCustomer_info_id());
			admKeyWordSuggestPriceAccesslog.setClientIp(super.request.getRemoteAddr());
			admKeyWordSuggestPriceAccesslog.setAction(EnumAccesslogAction.AD_MONEY_MODIFY.getAction());
			admKeyWordSuggestPriceAccesslog.setChannel(EnumAccesslogChannel.PFP.getChannel());
			admKeyWordSuggestPriceAccesslog.setMemberId(super.getId_pchome());
			admKeyWordSuggestPriceAccesslog.setCreateDate(today);
			admKeyWordSuggestPriceAccesslog.setMailSend(EnumAccesslogEmailStatus.NO.getStatus());
			admAccesslogList.add(admKeyWordSuggestPriceAccesslog);
		    }
		    if(pfpAdKeyWordList.size()>0){
			pfpAdKeywordService.savePfpAdKeywordList(pfpAdKeyWordList);
		    }
		}
		if(admAccesslogList.size()>0){
		    admAccesslogService.recordAdLogList(admAccesslogList); 
		}
		adGroup.setAdGroupSearchPrice(Float.parseFloat(adGroupSearchPrice));
//		pfpAdGroupService.getPfpAdGroupBySeq(adGroupSeq);
		
//		pfpAdKeywordService.savePfpAdKeywordList(pfpAdKeyWordList);
//		admAccesslogService.recordAdLogList(admAccesslogList);
//	    	adGroup.setAdGroupSearchPrice(Float.parseFloat(adGroupSearchPrice));
//	    	pfpAdGroupService.getPfpAdGroupBySeq(adGroupSeq);
		
//		if(type == EnumAdSearchPriceType.SUGGEST_PRICE.getTypeId()){
//		    //連帶異動 keyword 收尋金額
//		    for(PfpAdKeyword adKeyword:adGroup.getPfpAdKeywords()){
//			float suggestPrice = syspriceOperaterAPI.getKeywordSuggesPrice(adKeyword.getAdKeyword());
////			if(adGroupSearchPrice.equals(Float.toString(suggestPrice))){
////			    continue;
////			}
//			StringBuffer keywordMsg = new StringBuffer();
//			keywordMsg.append("收尋廣告金額異動(系統建議價格):");
//			keywordMsg.append("檢視廣告 ==> ");
//			keywordMsg.append(adKeyword.getPfpAdGroup().getPfpAdAction().getAdActionName()).append(" ==> ");
//			keywordMsg.append(adKeyword.getPfpAdGroup().getAdGroupName()).append(" ==> ");
//			keywordMsg.append(adKeyword.getAdKeyword()).append("(").append(adKeyword.getAdKeywordSeq()).append(")").append("：");
//			keywordMsg.append(adKeyword.getAdKeywordSearchPrice());
//			keywordMsg.append(" ==> ");
//			keywordMsg.append(suggestPrice);
//			adKeyword.setAdKeywordSearchPrice(suggestPrice);
//			adKeyword.setAdKeywordUpdateTime(today);
//			
//			AdmAccesslog admKeyWordSuggestPriceAccesslog = new AdmAccesslog();
//			admKeyWordSuggestPriceAccesslog.setMessage(keywordMsg.toString());
//			admKeyWordSuggestPriceAccesslog.setMemberId(super.getUser_id());
//			admKeyWordSuggestPriceAccesslog.setCustomerInfoId(super.getCustomer_info_id());
//			admKeyWordSuggestPriceAccesslog.setClientIp(super.request.getRemoteAddr());
//			admKeyWordSuggestPriceAccesslog.setAction(EnumAccesslogAction.AD_MONEY_MODIFY.getAction());
//			admKeyWordSuggestPriceAccesslog.setChannel(EnumAccesslogChannel.PFP.getChannel());
//			admKeyWordSuggestPriceAccesslog.setMemberId(super.getId_pchome());
//			admKeyWordSuggestPriceAccesslog.setCreateDate(today);
//			admKeyWordSuggestPriceAccesslog.setMailSend(EnumAccesslogEmailStatus.NO.getStatus());
//			admAccesslogList.add(admKeyWordSuggestPriceAccesslog);
//			
//			adKeyword.setAdKeywordSearchPrice(Float.parseFloat(adGroupSearchPrice));
//			adKeyword.setAdKeywordUpdateTime(today);
//			pfpAdKeywordService.saveOrUpdate(adKeyword);
//		    }
//		}
//	    	admAccesslogService.recordAdLogList(admAccesslogList);
//	    	adGroup.setAdGroupSearchPrice(Float.parseFloat(adGroupSearchPrice));
//	    	pfpAdGroupService.getPfpAdGroupBySeq(adGroupSeq);
	    	
	    	
	    	
	    	
//	    	PfpAdGroup adGroup = pfpAdGroupService.getPfpAdGroupBySeq(adGroupSeq);
//		if(StringUtils.isNotBlank(adGroupSeq) && 
//				StringUtils.isNotBlank(searchPriceType)){
//			
//			Date today = new Date();
//			
//			int type = Integer.parseInt(searchPriceType);
//			
//			adGroup.setAdGroupSearchPriceType(type);
//			
//			if(type == EnumAdSearchPriceType.MYSELF_PRICE.getTypeId() && 
//					StringUtils.isNotBlank(adGroupSearchPrice)){
//				
//				StringBuffer msg = new StringBuffer();
//				msg.append("收尋廣告金額異動(自行出價):");
//				msg.append("檢視廣告 ==> ");
//				msg.append(adGroup.getPfpAdAction().getAdActionName()).append(" ==> ");
//				msg.append(adGroup.getAdGroupName()).append("(").append(adGroupSeq).append(")").append("：");
//				msg.append(adGroup.getAdGroupSearchPrice());
//				msg.append(" ==> ");
//				msg.append(adGroupSearchPrice);
//				
//				admAccesslogService.recordAdLog(EnumAccesslogAction.AD_MONEY_MODIFY, 
//												msg.toString(), 
//												super.getId_pchome(), 
//												super.getCustomer_info_id(), 
//												super.getUser_id(), 
//												super.request.getRemoteAddr());
//				
//				adGroup.setAdGroupSearchPrice(Float.parseFloat(adGroupSearchPrice));
//				
//				// 連帶異動 keyword 收尋金額
//				for(PfpAdKeyword adKeyword:adGroup.getPfpAdKeywords()){
//					
//					if(adKeyword.getAdKeywordSearchPrice() != Float.parseFloat(adGroupSearchPrice)){
//						
//						StringBuffer keywordMsg = new StringBuffer();
//						
//						keywordMsg.append("收尋廣告金額異動(自行出價):");
//						keywordMsg.append("檢視廣告 ==> ");
//						keywordMsg.append(adKeyword.getPfpAdGroup().getPfpAdAction().getAdActionName()).append(" ==> ");
//						keywordMsg.append(adKeyword.getPfpAdGroup().getAdGroupName()).append(" ==> ");
//						keywordMsg.append(adKeyword.getAdKeyword()).append("(").append(adKeyword.getAdKeywordSeq()).append(")").append("：");
//						keywordMsg.append(adKeyword.getAdKeywordSearchPrice());
//						keywordMsg.append(" ==> ");
//						keywordMsg.append(adGroupSearchPrice);
//						
//						admAccesslogService.recordAdLog(EnumAccesslogAction.AD_MONEY_MODIFY, 
//														keywordMsg.toString(), 
//														super.getId_pchome(), 
//														super.getCustomer_info_id(), 
//														super.getUser_id(), 
//														super.request.getRemoteAddr());
//					}
//					
//					
//					adKeyword.setAdKeywordSearchPrice(Float.parseFloat(adGroupSearchPrice));
//					adKeyword.setAdKeywordUpdateTime(today);
//					
//					pfpAdKeywordService.saveOrUpdate(adKeyword);
//				}
//			}
//			
//			if(type == EnumAdSearchPriceType.SUGGEST_PRICE.getTypeId()){
//				
//				// 連帶異動 keyword 收尋金額
//				for(PfpAdKeyword adKeyword:adGroup.getPfpAdKeywords()){
//				    
//					float suggestPrice = syspriceOperaterAPI.getKeywordSuggesPrice(adKeyword.getAdKeyword());
//						
//					StringBuffer keywordMsg = new StringBuffer();
//					keywordMsg.append("收尋廣告金額異動(系統建議價格):");
//					keywordMsg.append("檢視廣告 ==> ");
//					keywordMsg.append(adKeyword.getPfpAdGroup().getPfpAdAction().getAdActionName()).append(" ==> ");
//					keywordMsg.append(adKeyword.getPfpAdGroup().getAdGroupName()).append(" ==> ");
//					keywordMsg.append(adKeyword.getAdKeyword()).append("(").append(adKeyword.getAdKeywordSeq()).append(")").append("：");
//					keywordMsg.append(adKeyword.getAdKeywordSearchPrice());
//					keywordMsg.append(" ==> ");
//					keywordMsg.append(suggestPrice);
//					
//					admAccesslogService.recordAdLog(EnumAccesslogAction.AD_MONEY_MODIFY, 
//													keywordMsg.toString(), 
//													super.getId_pchome(), 
//													super.getCustomer_info_id(), 
//													super.getUser_id(), 
//													super.request.getRemoteAddr());
//					
//					adKeyword.setAdKeywordSearchPrice(suggestPrice);
//					adKeyword.setAdKeywordUpdateTime(today);
//					
//					pfpAdKeywordService.saveOrUpdate(adKeyword);
//				}
//				
//				
//			}
//			
//			adGroup.setAdGroupCreateTime(today);
//			pfpAdGroupService.saveOrUpdate(adGroup);
//			
//		}
//		adActionSeq = adGroup.getPfpAdAction().getAdActionSeq();
	
		long endTime = System.currentTimeMillis();
	    	long elapsedTime = endTime - startTime;  
	    	log.info("執行了：" + (elapsedTime)/1000 + "秒");
	    	
		return SUCCESS;
	}	

	public void setPfpAdActionService(IPfpAdActionService pfpAdActionService) {
		this.pfpAdActionService = pfpAdActionService;
	}

	public void setPfpAdGroupService(IPfpAdGroupService pfpAdGroupService) {
		this.pfpAdGroupService = pfpAdGroupService;
	}
	
	public void setSyspriceOperaterAPI(SyspriceOperaterAPI syspriceOperaterAPI) {
		this.syspriceOperaterAPI = syspriceOperaterAPI;
	}

	public void setSysPriceAdPoolSeq(String sysPriceAdPoolSeq) {
		this.sysPriceAdPoolSeq = sysPriceAdPoolSeq;
	}

	public void setPfpBoardService(IPfpBoardService pfpBoardService) {
		this.pfpBoardService = pfpBoardService;
	}

	public void setAdmAccesslogService(IAdmAccesslogService admAccesslogService) {
		this.admAccesslogService = admAccesslogService;
	}

	public void setPfpAdKeywordService(IPfpAdKeywordService pfpAdKeywordService) {
		this.pfpAdKeywordService = pfpAdKeywordService;
	}

	public void setControlPriceAPI(ControlPriceAPI controlPriceAPI) {
		this.controlPriceAPI = controlPriceAPI;
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
	
	public PfpBoard getBoard() {
		return board;
	}

	public EnumBoardType[] getEnumBoardType() {
		return enumBoardType;
	}

	public String getAdActionSeq() {
		return adActionSeq;
	}

	public void setAdActionSeq(String adActionSeq) {
		this.adActionSeq = adActionSeq;
	}
	
	public PfpAdAction getAdAction() {
		return adAction;
	}

	public EnumAdType[] getSearchAdType() {
		return searchAdType;
	}

	public void setAdGroupSeq(String adGroupSeq) {
		this.adGroupSeq = adGroupSeq;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setUserPrice(String userPrice) {
		this.userPrice = userPrice;
	}

	public void setSearchPriceType(String searchPriceType) {
		this.searchPriceType = searchPriceType;
	}

	public void setAdGroupSearchPrice(String adGroupSearchPrice) {
		this.adGroupSearchPrice = adGroupSearchPrice;
	}

	public IPfpAdActionService getPfpAdActionService() {
	    return pfpAdActionService;
	}

	public IPfpAdGroupService getPfpAdGroupService() {
	    return pfpAdGroupService;
	}

	public SyspriceOperaterAPI getSyspriceOperaterAPI() {
	    return syspriceOperaterAPI;
	}

	public IPfpBoardService getPfpBoardService() {
	    return pfpBoardService;
	}

	public IAdmAccesslogService getAdmAccesslogService() {
	    return admAccesslogService;
	}

	public IPfpAdKeywordService getPfpAdKeywordService() {
	    return pfpAdKeywordService;
	}

	public ControlPriceAPI getControlPriceAPI() {
	    return controlPriceAPI;
	}

	public String getSysPriceAdPoolSeq() {
	    return sysPriceAdPoolSeq;
	}

	public String getAdGroupSeq() {
	    return adGroupSeq;
	}

	public String getStatus() {
	    return status;
	}

	public String getUserPrice() {
	    return userPrice;
	}

	public String getSearchPriceType() {
	    return searchPriceType;
	}

	public String getAdGroupSearchPrice() {
	    return adGroupSearchPrice;
	}

	public void setDateSelectMap(LinkedHashMap<String, String> dateSelectMap) {
	    this.dateSelectMap = dateSelectMap;
	}

	public void setStartDate(String startDate) {
	    this.startDate = startDate;
	}

	public void setEndDate(String endDate) {
	    this.endDate = endDate;
	}

	public void setBoard(PfpBoard board) {
	    this.board = board;
	}

	public void setEnumBoardType(EnumBoardType[] enumBoardType) {
	    this.enumBoardType = enumBoardType;
	}

	public void setAdAction(PfpAdAction adAction) {
	    this.adAction = adAction;
	}

	public void setSearchAdType(EnumAdType[] searchAdType) {
	    this.searchAdType = searchAdType;
	}

	public String getResult() {
	    return result;
	}

	public void setResult(String result) {
	    this.result = result;
	}

	public String getGroupMaxPrice() {
	    return groupMaxPrice;
	}

	public void setGroupMaxPrice(String groupMaxPrice) {
	    this.groupMaxPrice = groupMaxPrice;
	}



	

	
	
}
