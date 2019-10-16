package com.hce.ducati.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@Data
@DynamicInsert
@DynamicUpdate
@EntityListeners({AuditingEntityListener.class})
@Entity(name = "b_enterprise")
public class Enterprise {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	@Column(name="company_id")
	private Long companyId;
	@Column(name="type")
	private Integer type;
	@Column(name="userid")
	private String userid;
	@Column(name="sealid")
	private String sealid;
	@Column(name="name")
	private String name;
	@Column(name="id_type")
	private String idType;
	@Column(name="id_no")
	private String idNo;
	@Column(name="email")
	private String email;
	@Column(name="mobile_phone")
	private String mobilePhone;
	@Column(name="landline_phone")
	private String landlinePhone;
	@Column(name="transactor_name")
	private String transactorName;
	@Column(name="transactor_id_type")
	private String transactorIdType;
	@Column(name="transactor_id_no")
	private String transactorIdNo;
}
