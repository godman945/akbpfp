package com.pchome.akbpfp.struts2.action.ad;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.pchome.akbpfp.api.SyspriceOperaterAPI;
import com.pchome.akbpfp.db.pojo.PfpAd;
import com.pchome.akbpfp.db.pojo.PfpAdExcludeKeyword;
import com.pchome.akbpfp.db.pojo.PfpAdGroup;
import com.pchome.akbpfp.db.pojo.PfpAdKeyword;
import com.pchome.akbpfp.db.pojo.PfpCustomerInfo;
import com.pchome.akbpfp.db.service.ad.IPfpAdExcludeKeywordService;
import com.pchome.akbpfp.db.service.ad.IPfpAdKeywordService;
import com.pchome.akbpfp.db.service.ad.IPfpAdService;
import com.pchome.akbpfp.db.service.ad.PfpAdGroupService;
import com.pchome.akbpfp.db.service.customerInfo.PfpCustomerInfoService;
import com.pchome.akbpfp.db.service.sequence.ISequenceService;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.sequence.EnumSequenceTableName;
import com.pchome.enumerate.utils.EnumStatus;


public class AdKeywordAddAction extends BaseCookieAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String message = "";

	private String adActionSeq;
	private String adActionName;
	private String adGroupSeq;
	private String adGroupName;
	private List<PfpAdKeyword> pfpAdKeywords;
	private List<PfpAdExcludeKeyword> pfpAdExcludeKeywords;
	private String[] keywords;
	private String[] excludeKeywords;
	private String backPage;
	private String divBatchWord = "display:none;";		// 為了配合 adFreeAdAddKeyword 做的設定
	private String batchkeywords = "";					// 為了配合 adFreeAdAddKeyword 做的設定

	private PfpCustomerInfoService pfpCustomerInfoService;
	private ISequenceService sequenceService;
	private PfpAdGroupService pfpAdGroupService;
	private IPfpAdKeywordService pfpAdKeywordService;
	private IPfpAdExcludeKeywordService pfpAdExcludeKeywordService;
	private SyspriceOperaterAPI syspriceOperaterAPI;
	private IPfpAdService pfpAdService;
	
	private String adKeywordOpen;			//廣泛比對
	private String adKeywordPhraseOpen;		//詞組比對
	private String adKeywordPrecisionOpen;	//精準比對

	public String AdKeywordAdd() throws Exception {
		
		backPage = request.getHeader("Referer") == null? "adKeywordView.html": request.getHeader("Referer");
		

		PfpAdGroup pfpAdGroup = pfpAdGroupService.getPfpAdGroupBySeq(adGroupSeq);
		adActionSeq = pfpAdGroup.getPfpAdAction().getAdActionSeq();
		adActionName = pfpAdGroup.getPfpAdAction().getAdActionName();
		adGroupName  = pfpAdGroup.getAdGroupName();

		PfpCustomerInfo pfpCustomerInfo = pfpCustomerInfoService.findCustomerInfo(super.getCustomer_info_id());
		String customerInfoId = pfpCustomerInfo.getCustomerInfoId();
		String adCustomerInfoId = pfpAdGroup.getPfpAdAction().getPfpCustomerInfo().getCustomerInfoId();
		if(!customerInfoId.equals(adCustomerInfoId)) {
			return "notOwner";
		}
		
		pfpAdKeywords = pfpAdKeywordService.findAdKeywords(null, adGroupSeq, null, null, null, "10");
		//for(int i = 0; i < pfpAdKeywords.size(); i++) {
		//	System.out.println(pfpAdKeywords.get(i).getAdKeyword());
		//}
		// 取出分類所屬排除關鍵字
		pfpAdExcludeKeywords = pfpAdExcludeKeywordService.getPfpAdExcludeKeywords(adGroupSeq, pfpCustomerInfo.getCustomerInfoId());
		return SUCCESS;
	}

	public String doAdKeywordAdd() throws Exception {
	
		log.info(" adGroupSeq: "+adGroupSeq);
		log.info(" keywords: "+keywords);
		log.info(" excludeKeywords: "+excludeKeywords);
		
		if(StringUtils.isNotBlank(adGroupSeq)){
			
			PfpAdGroup pfpAdGroup = pfpAdGroupService.getPfpAdGroupBySeq(adGroupSeq);
			
			// 新增關鍵字
			if(keywords != null && StringUtils.isNotBlank(keywords.toString())){
				if(keywords.length != 0 && StringUtils.isBlank(adKeywordOpen) && StringUtils.isBlank(adKeywordPhraseOpen)
						&& StringUtils.isBlank(adKeywordPrecisionOpen)){
					message = "請選擇關鍵字比對方式！";
					return INPUT;
				}
				
				List<String> adKeywords = this.checkKeywords(adGroupSeq, keywords);
				
				if(!adKeywords.isEmpty()){
					
					for(String keyword:adKeywords){
						
						String adKeywordSeq = sequenceService.getId(EnumSequenceTableName.PFP_AD_KEYWORD, "_");
						PfpAdKeyword pfpAdKeyword = new PfpAdKeyword();
						pfpAdKeyword.setAdKeywordSeq(adKeywordSeq);
						pfpAdKeyword.setPfpAdGroup(pfpAdGroup);
						pfpAdKeyword.setAdKeyword(keyword);
						//廣泛比對設定
						if(StringUtils.isNotBlank(adKeywordOpen)){
							pfpAdKeyword.setAdKeywordSearchPrice(pfpAdGroup.getAdGroupSearchPrice());
							pfpAdKeyword.setAdKeywordOpen(1);
						} else {
							pfpAdKeyword.setAdKeywordSearchPrice(0);
							pfpAdKeyword.setAdKeywordOpen(0);
						}
						//詞組比對設定
						if(StringUtils.isNotBlank(adKeywordPhraseOpen)){
							pfpAdKeyword.setAdKeywordSearchPhrasePrice(pfpAdGroup.getAdGroupSearchPrice());
							pfpAdKeyword.setAdKeywordPhraseOpen(1);
						} else {
							pfpAdKeyword.setAdKeywordSearchPhrasePrice(0);
							pfpAdKeyword.setAdKeywordPhraseOpen(0);
						}
						//精準比對設定
						if(StringUtils.isNotBlank(adKeywordPrecisionOpen)){
							pfpAdKeyword.setAdKeywordSearchPrecisionPrice(pfpAdGroup.getAdGroupSearchPrice());
							pfpAdKeyword.setAdKeywordPrecisionOpen(1);
						} else {
							pfpAdKeyword.setAdKeywordSearchPrecisionPrice(0);
							pfpAdKeyword.setAdKeywordPrecisionOpen(0);
						}
						pfpAdKeyword.setAdKeywordChannelPrice(pfpAdGroup.getAdGroupChannelPrice());
						pfpAdKeyword.setAdKeywordOrder(0);
						pfpAdKeyword.setAdKeywordPhraseOrder(0);
						pfpAdKeyword.setAdKeywordPrecisionOrder(0);
						pfpAdKeyword.setAdKeywordStatus(EnumStatus.Open.getStatusId());
						pfpAdKeyword.setAdKeywordCreateTime(new Date());
						pfpAdKeyword.setAdKeywordUpdateTime(new Date());
						pfpAdKeywordService.saveOrUpdate(pfpAdKeyword);
						
						syspriceOperaterAPI.addKeywordSysprice(keyword, pfpAdGroup.getAdGroupSearchPrice());
					}
					
					// adGroup 下層關鍵字或播放明細都有, 此分類改為完成
					List<PfpAd> adAd = pfpAdService.validAdAd(adGroupSeq);
					
					if(adAd.size() > 0 ){
						pfpAdGroup.setAdGroupStatus(EnumStatus.Open.getStatusId());
						pfpAdGroup.setAdGroupUpdateTime(new Date());
						pfpAdGroupService.saveOrUpdate(pfpAdGroup);
					}
					else{
						log.info(" adAd.size is zero!!");
					}
					
				}else{
					log.info(" adKeywords is null!!");
				}
				
			}else{
				log.info(" keywords is null!!");
			}
			
			// 新增排除關鍵字
			if(excludeKeywords != null && StringUtils.isNotBlank(excludeKeywords.toString())){
				List<String> adExcludeKeywords = this.checkKeywords(adGroupSeq, excludeKeywords);
				
				if(!adExcludeKeywords.isEmpty()){
					
					for(String keyword:adExcludeKeywords){
						
						String adExcludeKeywordSeq = sequenceService.getId(EnumSequenceTableName.PFP_AD_EXCLUDE_KEYWORD, "_");
						PfpAdExcludeKeyword pfpAdExcludeKeyword = new PfpAdExcludeKeyword();
						pfpAdExcludeKeyword.setAdExcludeKeywordSeq(adExcludeKeywordSeq);
						pfpAdExcludeKeyword.setPfpAdGroup(pfpAdGroup);
						pfpAdExcludeKeyword.setAdExcludeKeyword(keyword);
						pfpAdExcludeKeyword.setAdExcludeKeywordStatus(EnumStatus.NoVerify.getStatusId());
						pfpAdExcludeKeyword.setAdExcludeKeywordCreateTime(new Date());
						pfpAdExcludeKeyword.setAdExcludeKeywordUpdateTime(new Date());
						
						pfpAdExcludeKeywordService.saveOrUpdate(pfpAdExcludeKeyword);
					}

				}else{
					log.info(" adExcludeKeywords is null!!");
				}

			}else{
				log.info(" excludeKeywords is null!!");
			}
			
			
			adActionSeq = pfpAdGroup.getPfpAdAction().getAdActionSeq();
		}
		
//		String printKW = "";
//		if(keywords!= null && keywords.length > 0) {
//			
//			for(int i = 0; i < keywords.length; i++) {
//				if(printKW == "")		printKW += keywords[i];
//				else					printKW += ";" + keywords[i];
//				//System.out.println("keywords["+i+"] = " + keywords[i]);
//			}
//		}
//		
//		String[] countKW = printKW.split(";");
//		
//
//		//if(excludeKeywords != null && excludeKeywords.length > 0) {
//		//	System.out.println("excludeKeywords.length = " + excludeKeywords.length);
//		//	for(int i = 0; i < excludeKeywords.length; i++) {
//		//		System.out.println("excludeKeywords["+i+"] = " + excludeKeywords[i]);
//		//	}
//		//}
//		
//		PfpAdGroup pfpAdGroup = pfpAdGroupService.getPfpAdGroupBySeq(adGroupSeq);
//
//		if(keywords != null && keywords.length > 0) {
//			for(int i = 0; i < keywords.length; i++) {
//				List<PfpAdKeyword> KWExist = pfpAdKeywordService.findAdKeywords(null, adGroupSeq, keywords[i], null, null, "10");
//				
//				if(KWExist.size() == 0) {
//					String adKeywordSeq = sequenceService.getId(EnumSequenceTableName.PFP_AD_KEYWORD, "_");
//					PfpAdKeyword pfpAdKeyword = new PfpAdKeyword();
//					pfpAdKeyword.setAdKeywordSeq(adKeywordSeq);
//					pfpAdKeyword.setPfpAdGroup(pfpAdGroup);
//					pfpAdKeyword.setAdKeyword(keywords[i]);
//					pfpAdKeyword.setAdKeywordSearchPrice(pfpAdGroup.getAdGroupSearchPrice());
//					pfpAdKeyword.setAdKeywordChannelPrice(pfpAdGroup.getAdGroupChannelPrice());
//					pfpAdKeyword.setAdKeywordOrder(0);
//					pfpAdKeyword.setAdKeywordStatus(EnumStatus.Open.getStatusId());
//					pfpAdKeyword.setAdKeywordCreateTime(new Date());
//					pfpAdKeyword.setAdKeywordUpdateTime(new Date());
//					pfpAdKeywordService.savePfpAdKeyword(pfpAdKeyword);
//					syspriceOperaterAPI.addKeywordSysprice(keywords[i], pfpAdGroup.getAdGroupSearchPrice());
//				} 
//			}
//		}
//
//		if(excludeKeywords != null && excludeKeywords.length > 0) {
//			for(int i = 0; i < excludeKeywords.length; i++) {
//				
//				String adExcludeKeywordSeq = sequenceService.getId(EnumSequenceTableName.PFP_AD_EXCLUDE_KEYWORD, "_");
//				PfpAdExcludeKeyword pfpAdExcludeKeyword = new PfpAdExcludeKeyword();
//				pfpAdExcludeKeyword.setAdExcludeKeywordSeq(adExcludeKeywordSeq);
//				pfpAdExcludeKeyword.setPfpAdGroup(pfpAdGroup);
//				pfpAdExcludeKeyword.setAdExcludeKeyword(excludeKeywords[i]);
//				pfpAdExcludeKeyword.setAdExcludeKeywordStatus(EnumStatus.NoVerify.getStatusId());
//				pfpAdExcludeKeyword.setAdExcludeKeywordCreateTime(new Date());
//				pfpAdExcludeKeyword.setAdExcludeKeywordUpdateTime(new Date());
//				pfpAdExcludeKeywordService.savePfpAdExcludeKeyword(pfpAdExcludeKeyword);
//			}
//		}
//
//		// 開啟廣告分類
//		pfpAdGroup.setAdGroupStatus(4);
//		pfpAdGroupService.save(pfpAdGroup);
//
		if(backPage == null) {
			backPage = "adActionView.html";
		} else {
			backPage = backPage.substring(backPage.lastIndexOf("/") + 1);
		}
		
		
		
		return SUCCESS;
	}
	
	/**
	 * 檢查是否重覆新增
	 * 1. 分類下已新增關鍵字
	 * 2. 分類下已排除關鍵字
	 */
	private List<String> checkKeywords(String adGroupSeq, String[] keywords){
		
		List<String> list = null;
		
		if(StringUtils.isNotBlank(adGroupSeq) && keywords != null && StringUtils.isNotBlank(keywords.toString())){
			
			
			
			list = new ArrayList<String>();
			
			List<String> existKeywords = new ArrayList<String>();
			
			// 分類下已新增關鍵字
			List<PfpAdKeyword> adKeywords = pfpAdKeywordService.findAdKeywords(adGroupSeq);
			
			for(PfpAdKeyword keyword:adKeywords){
				existKeywords.add(keyword.getAdKeyword());
			}
			
			// 分類下已排除關鍵字
			List<PfpAdExcludeKeyword> adExcludeKeywords =pfpAdExcludeKeywordService.findAdExcludeKeywords(adGroupSeq);
			
			for(PfpAdExcludeKeyword keyword:adExcludeKeywords){
				existKeywords.add(keyword.getAdExcludeKeyword());
			}
			
			log.info(" keywords size: "+keywords.length);
			log.info(" existKeywords size: "+existKeywords.size());
			
			if(existKeywords.isEmpty()){
				
				for(int i=0;i<keywords.length;i++){
					list.add(keywords[i].trim());
				}
				
			}else{
				
				for(int i=0;i<keywords.length;i++){
					
					for(String keyword:existKeywords){
						
						if(!keyword.equals(keywords[i].trim())){
							list.add(keywords[i].trim());
							break;
						}else{
							log.info(" keywords[i]: "+keywords[i].trim());
						}
					}
				}
			}
			

		}
		else{
			log.info(" check keywords is null");
		}
		
		return list;
	}

	public void setPfpCustomerInfoService(PfpCustomerInfoService pfpCustomerInfoService) {
		this.pfpCustomerInfoService = pfpCustomerInfoService;
	}

	public void setPfpAdGroupService(PfpAdGroupService pfpAdGroupService) {
		this.pfpAdGroupService = pfpAdGroupService;
	}

	public void setSequenceService(ISequenceService sequenceService) {
		this.sequenceService = sequenceService;
	}

	public void setPfpAdKeywordService(IPfpAdKeywordService pfpAdKeywordService) {
		this.pfpAdKeywordService = pfpAdKeywordService;
	}

	public void setPfpAdExcludeKeywordService(
			IPfpAdExcludeKeywordService pfpAdExcludeKeywordService) {
		this.pfpAdExcludeKeywordService = pfpAdExcludeKeywordService;
	}

	public void setSyspriceOperaterAPI(SyspriceOperaterAPI syspriceOperaterAPI) {
		this.syspriceOperaterAPI = syspriceOperaterAPI;
	}

	public String getAdActionSeq() {
		return adActionSeq;
	}

	public void setAdActionSeq(String adActionSeq) {
		this.adActionSeq = adActionSeq;
	}

	public String getAdActionName() {
		return adActionName;
	}

	public void setAdActionName(String adActionName) {
		this.adActionName = adActionName;
	}

	public String getAdGroupSeq() {
		return adGroupSeq;
	}

	public void setAdGroupSeq(String adGroupSeq) {
		this.adGroupSeq = adGroupSeq;
	}

	public String getAdGroupName() {
		return adGroupName;
	}

	public void setAdGroupName(String adGroupName) {
		this.adGroupName = adGroupName;
	}

	public List<PfpAdKeyword> getPfpAdKeywords() {
		return pfpAdKeywords;
	}

	public List<PfpAdExcludeKeyword> getPfpAdExcludeKeywords() {
		return pfpAdExcludeKeywords;
	}

	public String[] getKeywords() {
		return keywords;
	}

	public void setKeywords(String[] keywords) {
		this.keywords = keywords;
	}

	public String[] getExcludeKeywords() {
		return excludeKeywords;
	}

	public void setExcludeKeywords(String[] excludeKeywords) {
		this.excludeKeywords = excludeKeywords;
	}

	public void setBackPage(String backPage) {
		this.backPage = backPage;
	}

	public String getBackPage() {
		return backPage;
	}

	public String getDivBatchWord() {
		return divBatchWord;
	}

	public String getBatchkeywords() {
		return batchkeywords;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setPfpAdService(IPfpAdService pfpAdService) {
		this.pfpAdService = pfpAdService;
	}

	public String getAdKeywordOpen() {
		return adKeywordOpen;
	}

	public void setAdKeywordOpen(String adKeywordOpen) {
		this.adKeywordOpen = adKeywordOpen;
	}

	public String getAdKeywordPhraseOpen() {
		return adKeywordPhraseOpen;
	}

	public void setAdKeywordPhraseOpen(String adKeywordPhraseOpen) {
		this.adKeywordPhraseOpen = adKeywordPhraseOpen;
	}

	public String getAdKeywordPrecisionOpen() {
		return adKeywordPrecisionOpen;
	}

	public void setAdKeywordPrecisionOpen(String adKeywordPrecisionOpen) {
		this.adKeywordPrecisionOpen = adKeywordPrecisionOpen;
	}
	
}
