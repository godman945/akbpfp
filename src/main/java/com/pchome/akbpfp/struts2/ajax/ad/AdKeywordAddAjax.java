package com.pchome.akbpfp.struts2.ajax.ad;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.pchome.akbpfp.db.pojo.PfpAdGroup;
import com.pchome.akbpfp.db.pojo.PfpAdKeyword;
import com.pchome.akbpfp.db.service.ad.PfpAdGroupService;
import com.pchome.akbpfp.db.service.ad.PfpAdKeywordService;
import com.pchome.akbpfp.db.service.sequence.ISequenceService;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.sequence.EnumSequenceTableName;
import com.pchome.enumerate.utils.EnumStatus;

public class AdKeywordAddAjax extends BaseCookieAction {
	private ISequenceService sequenceService;
	private PfpAdGroupService pfpAdGroupService;
	private PfpAdKeywordService pfpAdKeywordService;

	private String adKeywordSeq;
	private String adKeyword;
	private String adGroupSeq;
	private String status;

	public String doKeywordAdd() throws Exception {
		//System.out.println("adGroupSeq = " + adGroupSeq + "; adKeyword = " + adKeyword);
		if (StringUtils.isEmpty(adKeyword)) {
			status = "noKeyword";
			return ERROR;
		}
		try {
			PfpAdGroup pfpAdGroup = pfpAdGroupService.getPfpAdGroupBySeq(adGroupSeq);
			adKeywordSeq = sequenceService.getId(EnumSequenceTableName.PFP_AD_KEYWORD, "_");
			//int SearchPrice = pfpAdGroup.getAdGroupSearchPrice();
			//int ChannelPrice = pfpAdGroup.getAdGroupChannelPrice();
			PfpAdKeyword pfpAdKeyword = new PfpAdKeyword();
			pfpAdKeyword.setAdKeywordSeq(adKeywordSeq);
			pfpAdKeyword.setPfpAdGroup(pfpAdGroup);
			pfpAdKeyword.setAdKeyword(adKeyword);
			//pfpAdKeyword.setAdKeywordSearchPrice(SearchPrice);
			//pfpAdKeyword.setAdKeywordChannelPrice(ChannelPrice);
			pfpAdKeyword.setAdKeywordStatus(EnumStatus.Open.getStatusId());
			pfpAdKeyword.setAdKeywordCreateTime(new Date());
			pfpAdKeyword.setAdKeywordUpdateTime(new Date());
				
			pfpAdKeywordService.savePfpAdKeyword(pfpAdKeyword);
		} catch (Exception ex) {
			System.out.println("AdKeywordAddAjax.doKeywordAdd.exception : " + ex);
		}

		return SUCCESS;
	}

	public void setSequenceService(ISequenceService sequenceService) {
		this.sequenceService = sequenceService;
	}

	public void setPfpAdGroupService(PfpAdGroupService pfpAdGroupService) {
		this.pfpAdGroupService = pfpAdGroupService;
	}

	public void setPfpAdKeywordService(PfpAdKeywordService pfpAdKeywordService) {
		this.pfpAdKeywordService = pfpAdKeywordService;
	}

	public String getAdKeywordSeq() {
		return adKeywordSeq;
	}

	public void setAdKeywordSeq(String adKeywordSeq) {
		this.adKeywordSeq = adKeywordSeq;
	}

	public String getAdKeyword() {
		return adKeyword;
	}

	public void setAdKeyword(String adKeyword) {
		this.adKeyword = adKeyword;
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
