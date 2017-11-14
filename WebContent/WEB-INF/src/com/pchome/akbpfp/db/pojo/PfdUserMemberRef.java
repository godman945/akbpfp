package com.pchome.akbpfp.db.pojo;
// Generated 2017/11/1 �U�� 02:28:26 by Hibernate Tools 3.4.0.CR1

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
 * PfdUserMemberRef generated by hbm2java
 */
@Entity
@Table(name = "pfd_user_member_ref")
public class PfdUserMemberRef implements java.io.Serializable {

	private PfdUserMemberRefId id;
	private PfdUser pfdUser;

	public PfdUserMemberRef() {
	}

	public PfdUserMemberRef(PfdUserMemberRefId id, PfdUser pfdUser) {
		this.id = id;
		this.pfdUser = pfdUser;
	}

	@EmbeddedId

	@AttributeOverrides({
			@AttributeOverride(name = "userId", column = @Column(name = "user_id", nullable = false, length = 20)),
			@AttributeOverride(name = "memberId", column = @Column(name = "member_id", nullable = false, length = 20)) })
	public PfdUserMemberRefId getId() {
		return this.id;
	}

	public void setId(PfdUserMemberRefId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
	public PfdUser getPfdUser() {
		return this.pfdUser;
	}

	public void setPfdUser(PfdUser pfdUser) {
		this.pfdUser = pfdUser;
	}

}
