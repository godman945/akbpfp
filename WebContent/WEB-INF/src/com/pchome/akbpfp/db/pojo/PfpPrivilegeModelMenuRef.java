package com.pchome.akbpfp.db.pojo;
// Generated 2018/9/28 �W�� 10:58:33 by Hibernate Tools 3.4.0.CR1

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * PfpPrivilegeModelMenuRef generated by hbm2java
 */
@Entity
@Table(name = "pfp_privilege_model_menu_ref")
public class PfpPrivilegeModelMenuRef implements java.io.Serializable {

	private PfpPrivilegeModelMenuRefId id;

	public PfpPrivilegeModelMenuRef() {
	}

	public PfpPrivilegeModelMenuRef(PfpPrivilegeModelMenuRefId id) {
		this.id = id;
	}

	@EmbeddedId

	@AttributeOverrides({ @AttributeOverride(name = "modelId", column = @Column(name = "model_id", nullable = false)),
			@AttributeOverride(name = "menuId", column = @Column(name = "menu_id", nullable = false)) })
	public PfpPrivilegeModelMenuRefId getId() {
		return this.id;
	}

	public void setId(PfpPrivilegeModelMenuRefId id) {
		this.id = id;
	}

}
