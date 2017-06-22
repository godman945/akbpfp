package com.pchome.akbpfp.db.pojo;

// Generated 2017/6/22 �W�� 10:22:48 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * PfpAdSpecificWebsite generated by hbm2java
 */
@Entity
@Table(name = "pfp_ad_specific_website", catalog = "akb")
public class PfpAdSpecificWebsite implements java.io.Serializable {

	private String specificWebsiteSeq;
	private PfpAdAction pfpAdAction;
	private PfbxWebsiteCategory pfbxWebsiteCategory;

	public PfpAdSpecificWebsite() {
	}

	public PfpAdSpecificWebsite(String specificWebsiteSeq,
			PfpAdAction pfpAdAction, PfbxWebsiteCategory pfbxWebsiteCategory) {
		this.specificWebsiteSeq = specificWebsiteSeq;
		this.pfpAdAction = pfpAdAction;
		this.pfbxWebsiteCategory = pfbxWebsiteCategory;
	}

	@Id
	@Column(name = "specific_website_seq", unique = true, nullable = false, length = 20)
	public String getSpecificWebsiteSeq() {
		return this.specificWebsiteSeq;
	}

	public void setSpecificWebsiteSeq(String specificWebsiteSeq) {
		this.specificWebsiteSeq = specificWebsiteSeq;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ad_action_seq", nullable = false)
	public PfpAdAction getPfpAdAction() {
		return this.pfpAdAction;
	}

	public void setPfpAdAction(PfpAdAction pfpAdAction) {
		this.pfpAdAction = pfpAdAction;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "website_ref", nullable = false)
	public PfbxWebsiteCategory getPfbxWebsiteCategory() {
		return this.pfbxWebsiteCategory;
	}

	public void setPfbxWebsiteCategory(PfbxWebsiteCategory pfbxWebsiteCategory) {
		this.pfbxWebsiteCategory = pfbxWebsiteCategory;
	}

}
