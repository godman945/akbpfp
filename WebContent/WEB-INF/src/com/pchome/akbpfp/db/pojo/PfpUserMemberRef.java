package com.pchome.akbpfp.db.pojo;

// Generated 2015/10/16 �W�� 10:15:06 by Hibernate Tools 3.4.0.CR1

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * PfpUserMemberRef generated by hbm2java
 */
@Entity
@Table(name = "pfp_user_member_ref", catalog = "akb")
public class PfpUserMemberRef implements java.io.Serializable {

	private PfpUserMemberRefId id;
	private PfpUser pfpUser;

	public PfpUserMemberRef() {
	}

	public PfpUserMemberRef(PfpUserMemberRefId id, PfpUser pfpUser) {
		this.id = id;
		this.pfpUser = pfpUser;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "userId", column = @Column(name = "user_id", nullable = false, length = 20)),
			@AttributeOverride(name = "memberId", column = @Column(name = "member_id", nullable = false, length = 20)) })
	public PfpUserMemberRefId getId() {
		return this.id;
	}

	public void setId(PfpUserMemberRefId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
	public PfpUser getPfpUser() {
		return this.pfpUser;
	}

	public void setPfpUser(PfpUser pfpUser) {
		this.pfpUser = pfpUser;
	}

}
