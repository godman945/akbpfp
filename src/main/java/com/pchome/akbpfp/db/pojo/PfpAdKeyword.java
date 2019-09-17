package com.pchome.akbpfp.db.pojo;
// Generated 2019/3/6 �U�� 01:55:32 by Hibernate Tools 3.5.0.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * PfpAdKeyword generated by hbm2java
 */
@Entity
@Table(name = "pfp_ad_keyword")
public class PfpAdKeyword implements java.io.Serializable {

	private String adKeywordSeq;
	private PfpAdGroup pfpAdGroup;
	private String adKeyword;
	private float adKeywordSearchPrice;
	private float adKeywordSearchPhrasePrice;
	private float adKeywordSearchPrecisionPrice;
	private float adKeywordChannelPrice;
	private float adKeywordOrder;
	private float adKeywordPhraseOrder;
	private float adKeywordPrecisionOrder;
	private int adKeywordOpen;
	private int adKeywordPhraseOpen;
	private int adKeywordPrecisionOpen;
	private int adKeywordStatus;
	private Date adKeywordCreateTime;
	private Date adKeywordUpdateTime;
	private Set<PfpAdKeywordPvclk> pfpAdKeywordPvclks = new HashSet<PfpAdKeywordPvclk>(0);
	private Set<PfpAdRank> pfpAdRanks = new HashSet<PfpAdRank>(0);
	private Set<PfpAdKeywordInvalid> pfpAdKeywordInvalids = new HashSet<PfpAdKeywordInvalid>(0);

	public PfpAdKeyword() {
	}

	public PfpAdKeyword(String adKeywordSeq, PfpAdGroup pfpAdGroup, String adKeyword, float adKeywordSearchPrice,
			float adKeywordSearchPhrasePrice, float adKeywordSearchPrecisionPrice, float adKeywordChannelPrice,
			float adKeywordOrder, float adKeywordPhraseOrder, float adKeywordPrecisionOrder, int adKeywordOpen,
			int adKeywordPhraseOpen, int adKeywordPrecisionOpen, int adKeywordStatus, Date adKeywordCreateTime,
			Date adKeywordUpdateTime) {
		this.adKeywordSeq = adKeywordSeq;
		this.pfpAdGroup = pfpAdGroup;
		this.adKeyword = adKeyword;
		this.adKeywordSearchPrice = adKeywordSearchPrice;
		this.adKeywordSearchPhrasePrice = adKeywordSearchPhrasePrice;
		this.adKeywordSearchPrecisionPrice = adKeywordSearchPrecisionPrice;
		this.adKeywordChannelPrice = adKeywordChannelPrice;
		this.adKeywordOrder = adKeywordOrder;
		this.adKeywordPhraseOrder = adKeywordPhraseOrder;
		this.adKeywordPrecisionOrder = adKeywordPrecisionOrder;
		this.adKeywordOpen = adKeywordOpen;
		this.adKeywordPhraseOpen = adKeywordPhraseOpen;
		this.adKeywordPrecisionOpen = adKeywordPrecisionOpen;
		this.adKeywordStatus = adKeywordStatus;
		this.adKeywordCreateTime = adKeywordCreateTime;
		this.adKeywordUpdateTime = adKeywordUpdateTime;
	}

	public PfpAdKeyword(String adKeywordSeq, PfpAdGroup pfpAdGroup, String adKeyword, float adKeywordSearchPrice,
			float adKeywordSearchPhrasePrice, float adKeywordSearchPrecisionPrice, float adKeywordChannelPrice,
			float adKeywordOrder, float adKeywordPhraseOrder, float adKeywordPrecisionOrder, int adKeywordOpen,
			int adKeywordPhraseOpen, int adKeywordPrecisionOpen, int adKeywordStatus, Date adKeywordCreateTime,
			Date adKeywordUpdateTime, Set<PfpAdKeywordPvclk> pfpAdKeywordPvclks, Set<PfpAdRank> pfpAdRanks,
			Set<PfpAdKeywordInvalid> pfpAdKeywordInvalids) {
		this.adKeywordSeq = adKeywordSeq;
		this.pfpAdGroup = pfpAdGroup;
		this.adKeyword = adKeyword;
		this.adKeywordSearchPrice = adKeywordSearchPrice;
		this.adKeywordSearchPhrasePrice = adKeywordSearchPhrasePrice;
		this.adKeywordSearchPrecisionPrice = adKeywordSearchPrecisionPrice;
		this.adKeywordChannelPrice = adKeywordChannelPrice;
		this.adKeywordOrder = adKeywordOrder;
		this.adKeywordPhraseOrder = adKeywordPhraseOrder;
		this.adKeywordPrecisionOrder = adKeywordPrecisionOrder;
		this.adKeywordOpen = adKeywordOpen;
		this.adKeywordPhraseOpen = adKeywordPhraseOpen;
		this.adKeywordPrecisionOpen = adKeywordPrecisionOpen;
		this.adKeywordStatus = adKeywordStatus;
		this.adKeywordCreateTime = adKeywordCreateTime;
		this.adKeywordUpdateTime = adKeywordUpdateTime;
		this.pfpAdKeywordPvclks = pfpAdKeywordPvclks;
		this.pfpAdRanks = pfpAdRanks;
		this.pfpAdKeywordInvalids = pfpAdKeywordInvalids;
	}

	@Id

	@Column(name = "ad_keyword_seq", unique = true, nullable = false, length = 20)
	public String getAdKeywordSeq() {
		return this.adKeywordSeq;
	}

	public void setAdKeywordSeq(String adKeywordSeq) {
		this.adKeywordSeq = adKeywordSeq;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ad_group_seq", nullable = false)
	public PfpAdGroup getPfpAdGroup() {
		return this.pfpAdGroup;
	}

	public void setPfpAdGroup(PfpAdGroup pfpAdGroup) {
		this.pfpAdGroup = pfpAdGroup;
	}

	@Column(name = "ad_keyword", nullable = false, length = 50)
	public String getAdKeyword() {
		return this.adKeyword;
	}

	public void setAdKeyword(String adKeyword) {
		this.adKeyword = adKeyword;
	}

	@Column(name = "ad_keyword_search_price", nullable = false, precision = 10)
	public float getAdKeywordSearchPrice() {
		return this.adKeywordSearchPrice;
	}

	public void setAdKeywordSearchPrice(float adKeywordSearchPrice) {
		this.adKeywordSearchPrice = adKeywordSearchPrice;
	}

	@Column(name = "ad_keyword_search_phrase_price", nullable = false, precision = 10)
	public float getAdKeywordSearchPhrasePrice() {
		return this.adKeywordSearchPhrasePrice;
	}

	public void setAdKeywordSearchPhrasePrice(float adKeywordSearchPhrasePrice) {
		this.adKeywordSearchPhrasePrice = adKeywordSearchPhrasePrice;
	}

	@Column(name = "ad_keyword_search_precision_price", nullable = false, precision = 10)
	public float getAdKeywordSearchPrecisionPrice() {
		return this.adKeywordSearchPrecisionPrice;
	}

	public void setAdKeywordSearchPrecisionPrice(float adKeywordSearchPrecisionPrice) {
		this.adKeywordSearchPrecisionPrice = adKeywordSearchPrecisionPrice;
	}

	@Column(name = "ad_keyword_channel_price", nullable = false, precision = 10)
	public float getAdKeywordChannelPrice() {
		return this.adKeywordChannelPrice;
	}

	public void setAdKeywordChannelPrice(float adKeywordChannelPrice) {
		this.adKeywordChannelPrice = adKeywordChannelPrice;
	}

	@Column(name = "ad_keyword_order", nullable = false, precision = 10)
	public float getAdKeywordOrder() {
		return this.adKeywordOrder;
	}

	public void setAdKeywordOrder(float adKeywordOrder) {
		this.adKeywordOrder = adKeywordOrder;
	}

	@Column(name = "ad_keyword_phrase_order", nullable = false, precision = 10)
	public float getAdKeywordPhraseOrder() {
		return this.adKeywordPhraseOrder;
	}

	public void setAdKeywordPhraseOrder(float adKeywordPhraseOrder) {
		this.adKeywordPhraseOrder = adKeywordPhraseOrder;
	}

	@Column(name = "ad_keyword_precision_order", nullable = false, precision = 10)
	public float getAdKeywordPrecisionOrder() {
		return this.adKeywordPrecisionOrder;
	}

	public void setAdKeywordPrecisionOrder(float adKeywordPrecisionOrder) {
		this.adKeywordPrecisionOrder = adKeywordPrecisionOrder;
	}

	@Column(name = "ad_keyword_open", nullable = false)
	public int getAdKeywordOpen() {
		return this.adKeywordOpen;
	}

	public void setAdKeywordOpen(int adKeywordOpen) {
		this.adKeywordOpen = adKeywordOpen;
	}

	@Column(name = "ad_keyword_phrase_open", nullable = false)
	public int getAdKeywordPhraseOpen() {
		return this.adKeywordPhraseOpen;
	}

	public void setAdKeywordPhraseOpen(int adKeywordPhraseOpen) {
		this.adKeywordPhraseOpen = adKeywordPhraseOpen;
	}

	@Column(name = "ad_keyword_precision_open", nullable = false)
	public int getAdKeywordPrecisionOpen() {
		return this.adKeywordPrecisionOpen;
	}

	public void setAdKeywordPrecisionOpen(int adKeywordPrecisionOpen) {
		this.adKeywordPrecisionOpen = adKeywordPrecisionOpen;
	}

	@Column(name = "ad_keyword_status", nullable = false)
	public int getAdKeywordStatus() {
		return this.adKeywordStatus;
	}

	public void setAdKeywordStatus(int adKeywordStatus) {
		this.adKeywordStatus = adKeywordStatus;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ad_keyword_create_time", nullable = false, length = 19)
	public Date getAdKeywordCreateTime() {
		return this.adKeywordCreateTime;
	}

	public void setAdKeywordCreateTime(Date adKeywordCreateTime) {
		this.adKeywordCreateTime = adKeywordCreateTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ad_keyword_update_time", nullable = false, length = 19)
	public Date getAdKeywordUpdateTime() {
		return this.adKeywordUpdateTime;
	}

	public void setAdKeywordUpdateTime(Date adKeywordUpdateTime) {
		this.adKeywordUpdateTime = adKeywordUpdateTime;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pfpAdKeyword")
	public Set<PfpAdKeywordPvclk> getPfpAdKeywordPvclks() {
		return this.pfpAdKeywordPvclks;
	}

	public void setPfpAdKeywordPvclks(Set<PfpAdKeywordPvclk> pfpAdKeywordPvclks) {
		this.pfpAdKeywordPvclks = pfpAdKeywordPvclks;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pfpAdKeyword")
	public Set<PfpAdRank> getPfpAdRanks() {
		return this.pfpAdRanks;
	}

	public void setPfpAdRanks(Set<PfpAdRank> pfpAdRanks) {
		this.pfpAdRanks = pfpAdRanks;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pfpAdKeyword")
	public Set<PfpAdKeywordInvalid> getPfpAdKeywordInvalids() {
		return this.pfpAdKeywordInvalids;
	}

	public void setPfpAdKeywordInvalids(Set<PfpAdKeywordInvalid> pfpAdKeywordInvalids) {
		this.pfpAdKeywordInvalids = pfpAdKeywordInvalids;
	}

}