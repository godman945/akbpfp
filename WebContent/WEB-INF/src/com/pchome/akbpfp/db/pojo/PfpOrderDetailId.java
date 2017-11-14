package com.pchome.akbpfp.db.pojo;
// Generated 2017/11/1 �U�� 02:28:26 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * PfpOrderDetailId generated by hbm2java
 */
@Embeddable
public class PfpOrderDetailId implements java.io.Serializable {

	private String orderId;
	private String productId;

	public PfpOrderDetailId() {
	}

	public PfpOrderDetailId(String orderId, String productId) {
		this.orderId = orderId;
		this.productId = productId;
	}

	@Column(name = "order_id", nullable = false, length = 20)
	public String getOrderId() {
		return this.orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Column(name = "product_id", nullable = false, length = 10)
	public String getProductId() {
		return this.productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PfpOrderDetailId))
			return false;
		PfpOrderDetailId castOther = (PfpOrderDetailId) other;

		return ((this.getOrderId() == castOther.getOrderId()) || (this.getOrderId() != null
				&& castOther.getOrderId() != null && this.getOrderId().equals(castOther.getOrderId())))
				&& ((this.getProductId() == castOther.getProductId()) || (this.getProductId() != null
						&& castOther.getProductId() != null && this.getProductId().equals(castOther.getProductId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getOrderId() == null ? 0 : this.getOrderId().hashCode());
		result = 37 * result + (getProductId() == null ? 0 : this.getProductId().hashCode());
		return result;
	}

}
