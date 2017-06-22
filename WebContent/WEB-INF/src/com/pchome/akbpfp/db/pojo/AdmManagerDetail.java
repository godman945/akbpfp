package com.pchome.akbpfp.db.pojo;

// Generated 2017/6/22 �W�� 10:22:48 by Hibernate Tools 3.4.0.CR1

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
 * AdmManagerDetail generated by hbm2java
 */
@Entity
@Table(name = "adm_manager_detail", catalog = "akb")
public class AdmManagerDetail implements java.io.Serializable {

	private Integer id;
	private String memberId;
	private String managerName;
	private String managerStatus;
	private String managerPrivilege;
	private String managerChannel;
	private Date updateDate;
	private Date createDate;

	public AdmManagerDetail() {
	}

	public AdmManagerDetail(String memberId, String managerName,
			String managerStatus, String managerPrivilege,
			String managerChannel, Date updateDate, Date createDate) {
		this.memberId = memberId;
		this.managerName = managerName;
		this.managerStatus = managerStatus;
		this.managerPrivilege = managerPrivilege;
		this.managerChannel = managerChannel;
		this.updateDate = updateDate;
		this.createDate = createDate;
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

	@Column(name = "member_id", nullable = false, length = 20)
	public String getMemberId() {
		return this.memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	@Column(name = "manager_name", nullable = false, length = 20)
	public String getManagerName() {
		return this.managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	@Column(name = "manager_status", nullable = false, length = 1)
	public String getManagerStatus() {
		return this.managerStatus;
	}

	public void setManagerStatus(String managerStatus) {
		this.managerStatus = managerStatus;
	}

	@Column(name = "manager_privilege", nullable = false, length = 1)
	public String getManagerPrivilege() {
		return this.managerPrivilege;
	}

	public void setManagerPrivilege(String managerPrivilege) {
		this.managerPrivilege = managerPrivilege;
	}

	@Column(name = "manager_channel", nullable = false, length = 1)
	public String getManagerChannel() {
		return this.managerChannel;
	}

	public void setManagerChannel(String managerChannel) {
		this.managerChannel = managerChannel;
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
