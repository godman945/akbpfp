package com.pchome.akbpfp.db.pojo;
// Generated 2017/9/22 �W�� 10:31:43 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * PfdKeywordReport generated by hbm2java
 */
@Entity
@Table(name = "pfd_keyword_report")
public class PfdKeywordReport implements java.io.Serializable {

	private Integer keywordReportSeq;
	private Date adPvclkDate;
	private String adPvclkDevice;
	private String pfdCustomerInfoId;
	private String pfdUserId;
	private String pfpCustomerInfoId;
	private String keywordSeq;
	private String adGroupSeq;
	private String adActionSeq;
	private int adType;
	private int adPv;
	private int adPhrasePv;
	private int adPrecisionPv;
	private int adClk;
	private int adPhraseClk;
	private int adPrecisionClk;
	private int adInvalidClk;
	private int adPhraseInvalidClk;
	private int adPrecisionInvalidClk;
	private float adPvPrice;
	private float adClkPrice;
	private float adPhraseClkPrice;
	private float adPrecisionClkPrice;
	private float adInvalidClkPrice;
	private float adPhraseInvalidClkPrice;
	private float adPrecisionInvalidClkPrice;
	private Date createDate;
	private Date updateDate;

	public PfdKeywordReport() {
	}

	public PfdKeywordReport(Date adPvclkDate, String pfpCustomerInfoId, String keywordSeq, String adGroupSeq,
			String adActionSeq, int adType, int adPv, int adPhrasePv, int adPrecisionPv, int adClk, int adPhraseClk,
			int adPrecisionClk, int adInvalidClk, int adPhraseInvalidClk, int adPrecisionInvalidClk, float adPvPrice,
			float adClkPrice, float adPhraseClkPrice, float adPrecisionClkPrice, float adInvalidClkPrice,
			float adPhraseInvalidClkPrice, float adPrecisionInvalidClkPrice, Date createDate, Date updateDate) {
		this.adPvclkDate = adPvclkDate;
		this.pfpCustomerInfoId = pfpCustomerInfoId;
		this.keywordSeq = keywordSeq;
		this.adGroupSeq = adGroupSeq;
		this.adActionSeq = adActionSeq;
		this.adType = adType;
		this.adPv = adPv;
		this.adPhrasePv = adPhrasePv;
		this.adPrecisionPv = adPrecisionPv;
		this.adClk = adClk;
		this.adPhraseClk = adPhraseClk;
		this.adPrecisionClk = adPrecisionClk;
		this.adInvalidClk = adInvalidClk;
		this.adPhraseInvalidClk = adPhraseInvalidClk;
		this.adPrecisionInvalidClk = adPrecisionInvalidClk;
		this.adPvPrice = adPvPrice;
		this.adClkPrice = adClkPrice;
		this.adPhraseClkPrice = adPhraseClkPrice;
		this.adPrecisionClkPrice = adPrecisionClkPrice;
		this.adInvalidClkPrice = adInvalidClkPrice;
		this.adPhraseInvalidClkPrice = adPhraseInvalidClkPrice;
		this.adPrecisionInvalidClkPrice = adPrecisionInvalidClkPrice;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}

	public PfdKeywordReport(Date adPvclkDate, String adPvclkDevice, String pfdCustomerInfoId, String pfdUserId,
			String pfpCustomerInfoId, String keywordSeq, String adGroupSeq, String adActionSeq, int adType, int adPv,
			int adPhrasePv, int adPrecisionPv, int adClk, int adPhraseClk, int adPrecisionClk, int adInvalidClk,
			int adPhraseInvalidClk, int adPrecisionInvalidClk, float adPvPrice, float adClkPrice,
			float adPhraseClkPrice, float adPrecisionClkPrice, float adInvalidClkPrice, float adPhraseInvalidClkPrice,
			float adPrecisionInvalidClkPrice, Date createDate, Date updateDate) {
		this.adPvclkDate = adPvclkDate;
		this.adPvclkDevice = adPvclkDevice;
		this.pfdCustomerInfoId = pfdCustomerInfoId;
		this.pfdUserId = pfdUserId;
		this.pfpCustomerInfoId = pfpCustomerInfoId;
		this.keywordSeq = keywordSeq;
		this.adGroupSeq = adGroupSeq;
		this.adActionSeq = adActionSeq;
		this.adType = adType;
		this.adPv = adPv;
		this.adPhrasePv = adPhrasePv;
		this.adPrecisionPv = adPrecisionPv;
		this.adClk = adClk;
		this.adPhraseClk = adPhraseClk;
		this.adPrecisionClk = adPrecisionClk;
		this.adInvalidClk = adInvalidClk;
		this.adPhraseInvalidClk = adPhraseInvalidClk;
		this.adPrecisionInvalidClk = adPrecisionInvalidClk;
		this.adPvPrice = adPvPrice;
		this.adClkPrice = adClkPrice;
		this.adPhraseClkPrice = adPhraseClkPrice;
		this.adPrecisionClkPrice = adPrecisionClkPrice;
		this.adInvalidClkPrice = adInvalidClkPrice;
		this.adPhraseInvalidClkPrice = adPhraseInvalidClkPrice;
		this.adPrecisionInvalidClkPrice = adPrecisionInvalidClkPrice;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "keyword_report_seq", unique = true, nullable = false)
	public Integer getKeywordReportSeq() {
		return this.keywordReportSeq;
	}

	public void setKeywordReportSeq(Integer keywordReportSeq) {
		this.keywordReportSeq = keywordReportSeq;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "ad_pvclk_date", nullable = false, length = 0)
	public Date getAdPvclkDate() {
		return this.adPvclkDate;
	}

	public void setAdPvclkDate(Date adPvclkDate) {
		this.adPvclkDate = adPvclkDate;
	}

	@Column(name = "ad_pvclk_device", length = 20)
	public String getAdPvclkDevice() {
		return this.adPvclkDevice;
	}

	public void setAdPvclkDevice(String adPvclkDevice) {
		this.adPvclkDevice = adPvclkDevice;
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

	@Column(name = "pfp_customer_info_id", nullable = false, length = 20)
	public String getPfpCustomerInfoId() {
		return this.pfpCustomerInfoId;
	}

	public void setPfpCustomerInfoId(String pfpCustomerInfoId) {
		this.pfpCustomerInfoId = pfpCustomerInfoId;
	}

	@Column(name = "keyword_seq", nullable = false, length = 20)
	public String getKeywordSeq() {
		return this.keywordSeq;
	}

	public void setKeywordSeq(String keywordSeq) {
		this.keywordSeq = keywordSeq;
	}

	@Column(name = "ad_group_seq", nullable = false, length = 20)
	public String getAdGroupSeq() {
		return this.adGroupSeq;
	}

	public void setAdGroupSeq(String adGroupSeq) {
		this.adGroupSeq = adGroupSeq;
	}

	@Column(name = "ad_action_seq", nullable = false, length = 20)
	public String getAdActionSeq() {
		return this.adActionSeq;
	}

	public void setAdActionSeq(String adActionSeq) {
		this.adActionSeq = adActionSeq;
	}

	@Column(name = "ad_type", nullable = false)
	public int getAdType() {
		return this.adType;
	}

	public void setAdType(int adType) {
		this.adType = adType;
	}

	@Column(name = "ad_pv", nullable = false)
	public int getAdPv() {
		return this.adPv;
	}

	public void setAdPv(int adPv) {
		this.adPv = adPv;
	}

	@Column(name = "ad_phrase_pv", nullable = false)
	public int getAdPhrasePv() {
		return this.adPhrasePv;
	}

	public void setAdPhrasePv(int adPhrasePv) {
		this.adPhrasePv = adPhrasePv;
	}

	@Column(name = "ad_precision_pv", nullable = false)
	public int getAdPrecisionPv() {
		return this.adPrecisionPv;
	}

	public void setAdPrecisionPv(int adPrecisionPv) {
		this.adPrecisionPv = adPrecisionPv;
	}

	@Column(name = "ad_clk", nullable = false)
	public int getAdClk() {
		return this.adClk;
	}

	public void setAdClk(int adClk) {
		this.adClk = adClk;
	}

	@Column(name = "ad_phrase_clk", nullable = false)
	public int getAdPhraseClk() {
		return this.adPhraseClk;
	}

	public void setAdPhraseClk(int adPhraseClk) {
		this.adPhraseClk = adPhraseClk;
	}

	@Column(name = "ad_precision_clk", nullable = false)
	public int getAdPrecisionClk() {
		return this.adPrecisionClk;
	}

	public void setAdPrecisionClk(int adPrecisionClk) {
		this.adPrecisionClk = adPrecisionClk;
	}

	@Column(name = "ad_invalid_clk", nullable = false)
	public int getAdInvalidClk() {
		return this.adInvalidClk;
	}

	public void setAdInvalidClk(int adInvalidClk) {
		this.adInvalidClk = adInvalidClk;
	}

	@Column(name = "ad_phrase_invalid_clk", nullable = false)
	public int getAdPhraseInvalidClk() {
		return this.adPhraseInvalidClk;
	}

	public void setAdPhraseInvalidClk(int adPhraseInvalidClk) {
		this.adPhraseInvalidClk = adPhraseInvalidClk;
	}

	@Column(name = "ad_precision_invalid_clk", nullable = false)
	public int getAdPrecisionInvalidClk() {
		return this.adPrecisionInvalidClk;
	}

	public void setAdPrecisionInvalidClk(int adPrecisionInvalidClk) {
		this.adPrecisionInvalidClk = adPrecisionInvalidClk;
	}

	@Column(name = "ad_pv_price", nullable = false, precision = 10)
	public float getAdPvPrice() {
		return this.adPvPrice;
	}

	public void setAdPvPrice(float adPvPrice) {
		this.adPvPrice = adPvPrice;
	}

	@Column(name = "ad_clk_price", nullable = false, precision = 10)
	public float getAdClkPrice() {
		return this.adClkPrice;
	}

	public void setAdClkPrice(float adClkPrice) {
		this.adClkPrice = adClkPrice;
	}

	@Column(name = "ad_phrase_clk_price", nullable = false, precision = 10)
	public float getAdPhraseClkPrice() {
		return this.adPhraseClkPrice;
	}

	public void setAdPhraseClkPrice(float adPhraseClkPrice) {
		this.adPhraseClkPrice = adPhraseClkPrice;
	}

	@Column(name = "ad_precision_clk_price", nullable = false, precision = 10)
	public float getAdPrecisionClkPrice() {
		return this.adPrecisionClkPrice;
	}

	public void setAdPrecisionClkPrice(float adPrecisionClkPrice) {
		this.adPrecisionClkPrice = adPrecisionClkPrice;
	}

	@Column(name = "ad_invalid_clk_price", nullable = false, precision = 10)
	public float getAdInvalidClkPrice() {
		return this.adInvalidClkPrice;
	}

	public void setAdInvalidClkPrice(float adInvalidClkPrice) {
		this.adInvalidClkPrice = adInvalidClkPrice;
	}

	@Column(name = "ad_phrase_invalid_clk_price", nullable = false, precision = 10)
	public float getAdPhraseInvalidClkPrice() {
		return this.adPhraseInvalidClkPrice;
	}

	public void setAdPhraseInvalidClkPrice(float adPhraseInvalidClkPrice) {
		this.adPhraseInvalidClkPrice = adPhraseInvalidClkPrice;
	}

	@Column(name = "ad_precision_invalid_clk_price", nullable = false, precision = 10)
	public float getAdPrecisionInvalidClkPrice() {
		return this.adPrecisionInvalidClkPrice;
	}

	public void setAdPrecisionInvalidClkPrice(float adPrecisionInvalidClkPrice) {
		this.adPrecisionInvalidClkPrice = adPrecisionInvalidClkPrice;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", nullable = false, length = 0)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_date", nullable = false, length = 0)
	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}
