package com.pchome.akbpfp.db.pojo;

// Generated 2016/7/11 �W�� 11:35:46 by Hibernate Tools 3.4.0.CR1

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
 * PfpOrderDetail generated by hbm2java
 */
@Entity
@Table(name = "pfp_order_detail", catalog = "akb")
public class PfpOrderDetail implements java.io.Serializable {

	private PfpOrderDetailId id;
	private PfpOrder pfpOrder;
	private String productName;
	private float producPrice;

	public PfpOrderDetail() {
	}

	public PfpOrderDetail(PfpOrderDetailId id, PfpOrder pfpOrder,
			String productName, float producPrice) {
		this.id = id;
		this.pfpOrder = pfpOrder;
		this.productName = productName;
		this.producPrice = producPrice;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "orderId", column = @Column(name = "order_id", nullable = false, length = 20)),
			@AttributeOverride(name = "productId", column = @Column(name = "product_id", nullable = false, length = 10)) })
	public PfpOrderDetailId getId() {
		return this.id;
	}

	public void setId(PfpOrderDetailId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id", nullable = false, insertable = false, updatable = false)
	public PfpOrder getPfpOrder() {
		return this.pfpOrder;
	}

	public void setPfpOrder(PfpOrder pfpOrder) {
		this.pfpOrder = pfpOrder;
	}

	@Column(name = "product_name", nullable = false, length = 10)
	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@Column(name = "produc_price", nullable = false, precision = 10)
	public float getProducPrice() {
		return this.producPrice;
	}

	public void setProducPrice(float producPrice) {
		this.producPrice = producPrice;
	}

}
