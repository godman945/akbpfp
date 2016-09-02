package com.pchome.akbpfp.db.pojo;

// Generated 2016/9/2 �W�� 10:32:07 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * PfbxSize generated by hbm2java
 */
@Entity
@Table(name = "pfbx_size", catalog = "akb")
public class PfbxSize implements java.io.Serializable {

	private Integer id;
	private Integer width;
	private Integer height;

	public PfbxSize() {
	}

	public PfbxSize(Integer width, Integer height) {
		this.width = width;
		this.height = height;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "width")
	public Integer getWidth() {
		return this.width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	@Column(name = "height")
	public Integer getHeight() {
		return this.height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

}
