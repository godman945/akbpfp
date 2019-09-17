package com.pchome.akbpfp.db.pojo;
// Generated 2019/3/6 �U�� 01:55:32 by Hibernate Tools 3.5.0.Final

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * PfpPrivilegeModelMenuRefId generated by hbm2java
 */
@Embeddable
public class PfpPrivilegeModelMenuRefId implements java.io.Serializable {

	private int modelId;
	private int menuId;

	public PfpPrivilegeModelMenuRefId() {
	}

	public PfpPrivilegeModelMenuRefId(int modelId, int menuId) {
		this.modelId = modelId;
		this.menuId = menuId;
	}

	@Column(name = "model_id", nullable = false)
	public int getModelId() {
		return this.modelId;
	}

	public void setModelId(int modelId) {
		this.modelId = modelId;
	}

	@Column(name = "menu_id", nullable = false)
	public int getMenuId() {
		return this.menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PfpPrivilegeModelMenuRefId))
			return false;
		PfpPrivilegeModelMenuRefId castOther = (PfpPrivilegeModelMenuRefId) other;

		return (this.getModelId() == castOther.getModelId()) && (this.getMenuId() == castOther.getMenuId());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getModelId();
		result = 37 * result + this.getMenuId();
		return result;
	}

}