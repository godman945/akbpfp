package com.pchome.akbpfp.db.pojo;

// Generated Jul 14, 2015 12:06:31 PM by Hibernate Tools 3.4.0.CR1

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
import javax.persistence.UniqueConstraint;

/**
 * PfpAdGroup generated by hbm2java
 */
@Entity
@Table(name = "pfp_ad_group", catalog = "akb", uniqueConstraints = @UniqueConstraint(columnNames = {
	"ad_action_seq", "ad_group_name" }))
public class PfpAdGroup implements java.io.Serializable {

    private String adGroupSeq;
    private PfpAdAction pfpAdAction;
    private String adGroupName;
    private int adGroupSearchPriceType;
    private float adGroupSearchPrice;
    private float adGroupChannelPrice;
    private int adGroupStatus;
    private Date adGroupCreateTime;
    private Date adGroupUpdateTime;
    private Set<PfpAd> pfpAds = new HashSet<PfpAd>(0);
    private Set<PfpAdKeyword> pfpAdKeywords = new HashSet<PfpAdKeyword>(0);
    private Set<PfpAdExcludeKeyword> pfpAdExcludeKeywords = new HashSet<PfpAdExcludeKeyword>(
	    0);

    public PfpAdGroup() {
    }

    public PfpAdGroup(String adGroupSeq, PfpAdAction pfpAdAction,
	    String adGroupName, int adGroupSearchPriceType,
	    float adGroupSearchPrice, float adGroupChannelPrice,
	    int adGroupStatus, Date adGroupCreateTime, Date adGroupUpdateTime) {
	this.adGroupSeq = adGroupSeq;
	this.pfpAdAction = pfpAdAction;
	this.adGroupName = adGroupName;
	this.adGroupSearchPriceType = adGroupSearchPriceType;
	this.adGroupSearchPrice = adGroupSearchPrice;
	this.adGroupChannelPrice = adGroupChannelPrice;
	this.adGroupStatus = adGroupStatus;
	this.adGroupCreateTime = adGroupCreateTime;
	this.adGroupUpdateTime = adGroupUpdateTime;
    }

    public PfpAdGroup(String adGroupSeq, PfpAdAction pfpAdAction,
	    String adGroupName, int adGroupSearchPriceType,
	    float adGroupSearchPrice, float adGroupChannelPrice,
	    int adGroupStatus, Date adGroupCreateTime, Date adGroupUpdateTime,
	    Set<PfpAd> pfpAds, Set<PfpAdKeyword> pfpAdKeywords,
	    Set<PfpAdExcludeKeyword> pfpAdExcludeKeywords) {
	this.adGroupSeq = adGroupSeq;
	this.pfpAdAction = pfpAdAction;
	this.adGroupName = adGroupName;
	this.adGroupSearchPriceType = adGroupSearchPriceType;
	this.adGroupSearchPrice = adGroupSearchPrice;
	this.adGroupChannelPrice = adGroupChannelPrice;
	this.adGroupStatus = adGroupStatus;
	this.adGroupCreateTime = adGroupCreateTime;
	this.adGroupUpdateTime = adGroupUpdateTime;
	this.pfpAds = pfpAds;
	this.pfpAdKeywords = pfpAdKeywords;
	this.pfpAdExcludeKeywords = pfpAdExcludeKeywords;
    }

    @Id
    @Column(name = "ad_group_seq", unique = true, nullable = false, length = 20)
    public String getAdGroupSeq() {
	return this.adGroupSeq;
    }

    public void setAdGroupSeq(String adGroupSeq) {
	this.adGroupSeq = adGroupSeq;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ad_action_seq", nullable = false)
    public PfpAdAction getPfpAdAction() {
	return this.pfpAdAction;
    }

    public void setPfpAdAction(PfpAdAction pfpAdAction) {
	this.pfpAdAction = pfpAdAction;
    }

    @Column(name = "ad_group_name", nullable = false, length = 20)
    public String getAdGroupName() {
	return this.adGroupName;
    }

    public void setAdGroupName(String adGroupName) {
	this.adGroupName = adGroupName;
    }

    @Column(name = "ad_group_search_price_type", nullable = false)
    public int getAdGroupSearchPriceType() {
	return this.adGroupSearchPriceType;
    }

    public void setAdGroupSearchPriceType(int adGroupSearchPriceType) {
	this.adGroupSearchPriceType = adGroupSearchPriceType;
    }

    @Column(name = "ad_group_search_price", nullable = false, precision = 10)
    public float getAdGroupSearchPrice() {
	return this.adGroupSearchPrice;
    }

    public void setAdGroupSearchPrice(float adGroupSearchPrice) {
	this.adGroupSearchPrice = adGroupSearchPrice;
    }

    @Column(name = "ad_group_channel_price", nullable = false, precision = 10)
    public float getAdGroupChannelPrice() {
	return this.adGroupChannelPrice;
    }

    public void setAdGroupChannelPrice(float adGroupChannelPrice) {
	this.adGroupChannelPrice = adGroupChannelPrice;
    }

    @Column(name = "ad_group_status", nullable = false)
    public int getAdGroupStatus() {
	return this.adGroupStatus;
    }

    public void setAdGroupStatus(int adGroupStatus) {
	this.adGroupStatus = adGroupStatus;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ad_group_create_time", nullable = false, length = 19)
    public Date getAdGroupCreateTime() {
	return this.adGroupCreateTime;
    }

    public void setAdGroupCreateTime(Date adGroupCreateTime) {
	this.adGroupCreateTime = adGroupCreateTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ad_group_update_time", nullable = false, length = 19)
    public Date getAdGroupUpdateTime() {
	return this.adGroupUpdateTime;
    }

    public void setAdGroupUpdateTime(Date adGroupUpdateTime) {
	this.adGroupUpdateTime = adGroupUpdateTime;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pfpAdGroup")
    public Set<PfpAd> getPfpAds() {
	return this.pfpAds;
    }

    public void setPfpAds(Set<PfpAd> pfpAds) {
	this.pfpAds = pfpAds;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pfpAdGroup")
    public Set<PfpAdKeyword> getPfpAdKeywords() {
	return this.pfpAdKeywords;
    }

    public void setPfpAdKeywords(Set<PfpAdKeyword> pfpAdKeywords) {
	this.pfpAdKeywords = pfpAdKeywords;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pfpAdGroup")
    public Set<PfpAdExcludeKeyword> getPfpAdExcludeKeywords() {
	return this.pfpAdExcludeKeywords;
    }

    public void setPfpAdExcludeKeywords(
	    Set<PfpAdExcludeKeyword> pfpAdExcludeKeywords) {
	this.pfpAdExcludeKeywords = pfpAdExcludeKeywords;
    }

}
