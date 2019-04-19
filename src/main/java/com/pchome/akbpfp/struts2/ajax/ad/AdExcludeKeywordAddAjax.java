package com.pchome.akbpfp.struts2.ajax.ad;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.pchome.akbpfp.db.pojo.PfpAdGroup;
import com.pchome.akbpfp.db.pojo.PfpAdExcludeKeyword;
import com.pchome.akbpfp.db.service.ad.PfpAdGroupService;
import com.pchome.akbpfp.db.service.ad.PfpAdExcludeKeywordService;
import com.pchome.akbpfp.db.service.sequence.ISequenceService;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.sequence.EnumSequenceTableName;

public class AdExcludeKeywordAddAjax extends BaseCookieAction {
	private ISequenceService sequenceService;
	private PfpAdGroupService pfpAdGroupService;
	private PfpAdExcludeKeywordService pfpAdExcludeKeywordService;

	private String adExcludeKeywordSeq;
	private String adExcludeKeyword;
	private String adGroupSeq;
	private String status;

	public String doExcludeKeywordAdd() throws Exception {
		//System.out.println("adGroupSeq = " + adGroupSeq + "; adExcludeKeyword = " + adExcludeKeyword);
		if (StringUtils.isEmpty(adExcludeKeyword)) {
			status = "noExcludeKeyword";
			return ERROR;
		}
		try {
			PfpAdGroup pfpAdGroup = pfpAdGroupService.getPfpAdGroupBySeq(adGroupSeq);
			adExcludeKeywordSeq = sequenceService.getId(EnumSequenceTableName.PFP_AD_EXCLUDE_KEYWORD, "_");
			PfpAdExcludeKeyword pfpAdExcludeKeyword = new PfpAdExcludeKeyword();
			pfpAdExcludeKeyword.setAdExcludeKeywordSeq(adExcludeKeywordSeq);
			pfpAdExcludeKeyword.setPfpAdGroup(pfpAdGroup);
			pfpAdExcludeKeyword.setAdExcludeKeyword(adExcludeKeyword);
			pfpAdExcludeKeyword.setAdExcludeKeywordCreateTime(new Date());
			pfpAdExcludeKeyword.setAdExcludeKeywordUpdateTime(new Date());
				
			pfpAdExcludeKeywordService.savePfpAdExcludeKeyword(pfpAdExcludeKeyword);
		} catch (Exception ex) {
			System.out.println("AdExcludeKeywordAddAjax.doKeywordAdd.exception : " + ex);
		}

		return SUCCESS;
	}

	public void setSequenceService(ISequenceService sequenceService) {
		this.sequenceService = sequenceService;
	}

	public void setPfpAdGroupService(PfpAdGroupService pfpAdGroupService) {
		this.pfpAdGroupService = pfpAdGroupService;
	}

	public void setPfpAdExcludeKeywordService(PfpAdExcludeKeywordService pfpAdExcludeKeywordService) {
		this.pfpAdExcludeKeywordService = pfpAdExcludeKeywordService;
	}

	public String getAdExcludeKeywordSeq() {
		return adExcludeKeywordSeq;
	}

	public void setAdExcludeKeywordSeq(String adExcludeKeywordSeq) {
		this.adExcludeKeywordSeq = adExcludeKeywordSeq;
	}

	public String getAdExcludeKeyword() {
		return adExcludeKeyword;
	}

	public void setAdExcludeKeyword(String adExcludeKeyword) {
		this.adExcludeKeyword = adExcludeKeyword;
	}

	public String getAdGroupSeq() {
		return adGroupSeq;
	}

	public void setAdGroupSeq(String adGroupSeq) {
		this.adGroupSeq = adGroupSeq;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
