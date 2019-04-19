package com.pchome.akbpfp.struts2.action.msg;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.pchome.akbpfp.db.pojo.PfpAdExcludeKeyword;
import com.pchome.akbpfp.db.pojo.PfpAdGroup;
import com.pchome.akbpfp.db.service.ad.IPfpAdExcludeKeywordService;
import com.pchome.akbpfp.db.service.ad.IPfpAdGroupService;
import com.pchome.akbpfp.struts2.BaseCookieAction;

public class AdExcludeKeywordAction extends BaseCookieAction{
	
	private IPfpAdGroupService pfpAdGroupService;
	private IPfpAdExcludeKeywordService pfpAdExcludeKeywordService;
	
	
	private String adGroupSeq;
	private PfpAdGroup adGroup;
	private List<PfpAdExcludeKeyword> adExcludeKeywords;
	private String adExcludeKeywordSeq;	
	private String adExcludeKeywordStatus;
	
	
	public String execute() throws Exception{
		
		adGroup = pfpAdGroupService.getPfpAdGroupBySeq(adGroupSeq);
		adExcludeKeywords = pfpAdExcludeKeywordService.getPfpAdExcludeKeywords(adGroupSeq, super.getCustomer_info_id());
		
		//log.info(" adExcludeKeywords "+adExcludeKeywords);
		
		return SUCCESS;
	}
	
	public void updateAdExcludeKeywordStatusAction() throws Exception{
		
		String[] adExcludeKeywordSeqs = adExcludeKeywordSeq.split(",");
		int statusId = Integer.parseInt(adExcludeKeywordStatus);
		
		for(int i=0;i<adExcludeKeywordSeqs.length;i++){
			
			//log.info(" adKeywordSeqs[i] = "+adKeywordSeqs[i]);
			
			PfpAdExcludeKeyword adExcludeKeyword = pfpAdExcludeKeywordService.getPfpAdExcludeKeywordBySeq(adExcludeKeywordSeqs[i]);
			
			//log.info(" adGroup = "+adGroup);
			
			if(adExcludeKeyword != null && StringUtils.isNotBlank(adExcludeKeywordStatus)){
				
				adExcludeKeyword.setAdExcludeKeywordStatus(statusId);
				adExcludeKeyword.setAdExcludeKeywordCreateTime(new Date());
				pfpAdExcludeKeywordService.saveOrUpdate(adExcludeKeyword);
				
				adGroupSeq = adExcludeKeyword.getPfpAdGroup().getAdGroupSeq();
				
			}
		}	

	}
	
	public String closeAdExcludeKeywordAction() throws Exception{
		return SUCCESS;
	}
	
	public void setPfpAdGroupService(IPfpAdGroupService pfpAdGroupService) {
		this.pfpAdGroupService = pfpAdGroupService;
	}
	
	public void setPfpAdExcludeKeywordService(IPfpAdExcludeKeywordService pfpAdExcludeKeywordService) {
		this.pfpAdExcludeKeywordService = pfpAdExcludeKeywordService;
	}
	
	public void setAdGroupSeq(String adGroupSeq) {
		this.adGroupSeq = adGroupSeq;
	}

	public String getAdGroupSeq() {
		return adGroupSeq;
	}

	public PfpAdGroup getAdGroup() {
		return adGroup;
	}

	public List<PfpAdExcludeKeyword> getAdExcludeKeywords() {
		return adExcludeKeywords;
	}

	public void setAdExcludeKeywordSeq(String adExcludeKeywordSeq) {
		this.adExcludeKeywordSeq = adExcludeKeywordSeq;
	}

	public void setAdExcludeKeywordStatus(String adExcludeKeywordStatus) {
		this.adExcludeKeywordStatus = adExcludeKeywordStatus;
	}


	
	
	
	
	
}
