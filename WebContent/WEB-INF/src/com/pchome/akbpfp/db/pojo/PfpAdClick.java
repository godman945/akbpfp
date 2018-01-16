package com.pchome.akbpfp.db.pojo;
// Generated 2017/11/1 �U�� 02:28:26 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * PfpAdClick generated by hbm2java
 */
@Entity
@Table(name = "pfp_ad_click")
public class PfpAdClick implements java.io.Serializable {

	private Long adClickId;
	private PfpCustomerInfo pfpCustomerInfo;
	private String memId;
	private String uuid;
	private String remoteIp;
	private String referer;
	private String userAgent;
	private String pfbxCustomerInfoId;
	private String pfbxPositionId;
	private String pfdCustomerInfoId;
	private String pfdUserId;
	private String styleId;
	private String tproId;
	private String tadId;
	private String poolId;
	private String adId;
	private String keywordId;
	private String adType;
	private String adClass;
	private Float adRank;
	private Float adPrice;
	private Integer adClk;
	private Float adActionControlPrice;
	private Float adActionMaxPrice;
	private Integer maliceType;
	private Date recordDate;
	private Integer recordTime;
	private Date recordMinute;
	private String mouseMoveFlag;
	private Integer mouseAreaWidth;
	private Integer mouseAreaHeight;
	private Integer mouseDownX;
	private Integer mouseDownY;
	private Date updateDate;
	private Date createDate;

	public PfpAdClick() {
	}

	public PfpAdClick(Date updateDate, Date createDate) {
		this.updateDate = updateDate;
		this.createDate = createDate;
	}

	public PfpAdClick(PfpCustomerInfo pfpCustomerInfo, String memId, String uuid, String remoteIp, String referer,
			String userAgent, String pfbxCustomerInfoId, String pfbxPositionId, String pfdCustomerInfoId,
			String pfdUserId, String styleId, String tproId, String tadId, String poolId, String adId, String keywordId,
			String adType, String adClass, Float adRank, Float adPrice, Integer adClk, Float adActionControlPrice,
			Float adActionMaxPrice, Integer maliceType, Date recordDate, Integer recordTime, Date recordMinute,
			String mouseMoveFlag, Integer mouseAreaWidth, Integer mouseAreaHeight, Integer mouseDownX,
			Integer mouseDownY, Date updateDate, Date createDate) {
		this.pfpCustomerInfo = pfpCustomerInfo;
		this.memId = memId;
		this.uuid = uuid;
		this.remoteIp = remoteIp;
		this.referer = referer;
		this.userAgent = userAgent;
		this.pfbxCustomerInfoId = pfbxCustomerInfoId;
		this.pfbxPositionId = pfbxPositionId;
		this.pfdCustomerInfoId = pfdCustomerInfoId;
		this.pfdUserId = pfdUserId;
		this.styleId = styleId;
		this.tproId = tproId;
		this.tadId = tadId;
		this.poolId = poolId;
		this.adId = adId;
		this.keywordId = keywordId;
		this.adType = adType;
		this.adClass = adClass;
		this.adRank = adRank;
		this.adPrice = adPrice;
		this.adClk = adClk;
		this.adActionControlPrice = adActionControlPrice;
		this.adActionMaxPrice = adActionMaxPrice;
		this.maliceType = maliceType;
		this.recordDate = recordDate;
		this.recordTime = recordTime;
		this.recordMinute = recordMinute;
		this.mouseMoveFlag = mouseMoveFlag;
		this.mouseAreaWidth = mouseAreaWidth;
		this.mouseAreaHeight = mouseAreaHeight;
		this.mouseDownX = mouseDownX;
		this.mouseDownY = mouseDownY;
		this.updateDate = updateDate;
		this.createDate = createDate;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ad_click_id", unique = true, nullable = false)
	public Long getAdClickId() {
		return this.adClickId;
	}

	public void setAdClickId(Long adClickId) {
		this.adClickId = adClickId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_info_id")
	public PfpCustomerInfo getPfpCustomerInfo() {
		return this.pfpCustomerInfo;
	}

	public void setPfpCustomerInfo(PfpCustomerInfo pfpCustomerInfo) {
		this.pfpCustomerInfo = pfpCustomerInfo;
	}

	@Column(name = "mem_id", length = 20)
	public String getMemId() {
		return this.memId;
	}

	public void setMemId(String memId) {
		this.memId = memId;
	}

	@Column(name = "uuid", length = 50)
	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Column(name = "remote_ip", length = 20)
	public String getRemoteIp() {
		return this.remoteIp;
	}

	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}

	@Column(name = "referer", length = 2000)
	public String getReferer() {
		return this.referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	@Column(name = "user_agent", length = 500)
	public String getUserAgent() {
		return this.userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	@Column(name = "pfbx_customer_info_id", length = 20)
	public String getPfbxCustomerInfoId() {
		return this.pfbxCustomerInfoId;
	}

	public void setPfbxCustomerInfoId(String pfbxCustomerInfoId) {
		this.pfbxCustomerInfoId = pfbxCustomerInfoId;
	}

	@Column(name = "pfbx_position_id", length = 20)
	public String getPfbxPositionId() {
		return this.pfbxPositionId;
	}

	public void setPfbxPositionId(String pfbxPositionId) {
		this.pfbxPositionId = pfbxPositionId;
	}

	@Column(name = "pfd_customer_info_id", length = 20)
	public String getPfdCustomerInfoId() {
		return this.pfdCustomerInfoId;
	}

	public void setPfdCustomerInfoId(String pfdCustomerInfoId) {
		this.pfdCustomerInfoId = pfdCustomerInfoId;
	}

	@Column(name = "pfd_user_id", length = 20)
	public String getPfdUserId() {
		return this.pfdUserId;
	}

	public void setPfdUserId(String pfdUserId) {
		this.pfdUserId = pfdUserId;
	}

	@Column(name = "style_id", length = 20)
	public String getStyleId() {
		return this.styleId;
	}

	public void setStyleId(String styleId) {
		this.styleId = styleId;
	}

	@Column(name = "tpro_id", length = 20)
	public String getTproId() {
		return this.tproId;
	}

	public void setTproId(String tproId) {
		this.tproId = tproId;
	}

	@Column(name = "tad_id", length = 20)
	public String getTadId() {
		return this.tadId;
	}

	public void setTadId(String tadId) {
		this.tadId = tadId;
	}

	@Column(name = "pool_id", length = 20)
	public String getPoolId() {
		return this.poolId;
	}

	public void setPoolId(String poolId) {
		this.poolId = poolId;
	}

	@Column(name = "ad_id", length = 20)
	public String getAdId() {
		return this.adId;
	}

	public void setAdId(String adId) {
		this.adId = adId;
	}

	@Column(name = "keyword_id", length = 20)
	public String getKeywordId() {
		return this.keywordId;
	}

	public void setKeywordId(String keywordId) {
		this.keywordId = keywordId;
	}

	@Column(name = "ad_type", length = 1)
	public String getAdType() {
		return this.adType;
	}

	public void setAdType(String adType) {
		this.adType = adType;
	}

	@Column(name = "ad_class", length = 20)
	public String getAdClass() {
		return this.adClass;
	}

	public void setAdClass(String adClass) {
		this.adClass = adClass;
	}

	@Column(name = "ad_rank", precision = 10)
	public Float getAdRank() {
		return this.adRank;
	}

	public void setAdRank(Float adRank) {
		this.adRank = adRank;
	}

	@Column(name = "ad_price", precision = 10)
	public Float getAdPrice() {
		return this.adPrice;
	}

	public void setAdPrice(Float adPrice) {
		this.adPrice = adPrice;
	}

	@Column(name = "ad_clk")
	public Integer getAdClk() {
		return this.adClk;
	}

	public void setAdClk(Integer adClk) {
		this.adClk = adClk;
	}

	@Column(name = "ad_action_control_price", precision = 10)
	public Float getAdActionControlPrice() {
		return this.adActionControlPrice;
	}

	public void setAdActionControlPrice(Float adActionControlPrice) {
		this.adActionControlPrice = adActionControlPrice;
	}

	@Column(name = "ad_action_max_price", precision = 10)
	public Float getAdActionMaxPrice() {
		return this.adActionMaxPrice;
	}

	public void setAdActionMaxPrice(Float adActionMaxPrice) {
		this.adActionMaxPrice = adActionMaxPrice;
	}

	@Column(name = "malice_type")
	public Integer getMaliceType() {
		return this.maliceType;
	}

	public void setMaliceType(Integer maliceType) {
		this.maliceType = maliceType;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "record_date", length = 10)
	public Date getRecordDate() {
		return this.recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	@Column(name = "record_time")
	public Integer getRecordTime() {
		return this.recordTime;
	}

	public void setRecordTime(Integer recordTime) {
		this.recordTime = recordTime;
	}

	@Temporal(TemporalType.TIME)
	@Column(name = "record_minute", length = 8)
	public Date getRecordMinute() {
		return this.recordMinute;
	}

	public void setRecordMinute(Date recordMinute) {
		this.recordMinute = recordMinute;
	}

	@Column(name = "mouse_move_flag", length = 2)
	public String getMouseMoveFlag() {
		return this.mouseMoveFlag;
	}

	public void setMouseMoveFlag(String mouseMoveFlag) {
		this.mouseMoveFlag = mouseMoveFlag;
	}

	@Column(name = "mouse_area_width")
	public Integer getMouseAreaWidth() {
		return this.mouseAreaWidth;
	}

	public void setMouseAreaWidth(Integer mouseAreaWidth) {
		this.mouseAreaWidth = mouseAreaWidth;
	}

	@Column(name = "mouse_area_height")
	public Integer getMouseAreaHeight() {
		return this.mouseAreaHeight;
	}

	public void setMouseAreaHeight(Integer mouseAreaHeight) {
		this.mouseAreaHeight = mouseAreaHeight;
	}

	@Column(name = "mouse_down_x")
	public Integer getMouseDownX() {
		return this.mouseDownX;
	}

	public void setMouseDownX(Integer mouseDownX) {
		this.mouseDownX = mouseDownX;
	}

	@Column(name = "mouse_down_y")
	public Integer getMouseDownY() {
		return this.mouseDownY;
	}

	public void setMouseDownY(Integer mouseDownY) {
		this.mouseDownY = mouseDownY;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_date", nullable = false, length = 19)
	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", nullable = false, length = 19)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
