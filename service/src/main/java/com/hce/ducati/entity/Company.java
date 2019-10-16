package com.hce.ducati.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@Data
@DynamicInsert
@DynamicUpdate
@EntityListeners({AuditingEntityListener.class})
@Entity(name = "b_company")
public class Company {
	@Id
	@Column(name="id")
	private Long id;
	@Column(name="en_name")
	private String enName;
	@Column(name="cn_name")
	private String cnName;
}
