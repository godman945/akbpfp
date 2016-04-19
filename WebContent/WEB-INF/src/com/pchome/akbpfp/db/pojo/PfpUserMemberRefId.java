package com.pchome.akbpfp.db.pojo;

// Generated 2016/4/19 �U�� 02:15:49 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * PfpUserMemberRefId generated by hbm2java
 */
@Embeddable
public class PfpUserMemberRefId implements java.io.Serializable {

	private String userId;
	private String memberId;

	public PfpUserMemberRefId() {
	}

	public PfpUserMemberRefId(String userId, String memberId) {
		this.userId = userId;
		this.memberId = memberId;
	}

	@Column(name = "user_id", nullable = false, length = 20)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "member_id", nullable = false, length = 20)
	public String getMemberId() {
		return this.memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PfpUserMemberRefId))
			return false;
		PfpUserMemberRefId castOther = (PfpUserMemberRefId) other;

		return ((this.getUserId() == castOther.getUserId()) || (this
				.getUserId() != null && castOther.getUserId() != null && this
				.getUserId().equals(castOther.getUserId())))
				&& ((this.getMemberId() == castOther.getMemberId()) || (this
						.getMemberId() != null
						&& castOther.getMemberId() != null && this
						.getMemberId().equals(castOther.getMemberId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getUserId() == null ? 0 : this.getUserId().hashCode());
		result = 37 * result
				+ (getMemberId() == null ? 0 : this.getMemberId().hashCode());
		return result;
	}

}
