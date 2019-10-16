package com.hce.auth.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@DynamicInsert
@DynamicUpdate
@EntityListeners({AuditingEntityListener.class})
@Entity(name = "b_country")
public class Country implements Serializable {
	private static final long serialVersionUID = -7160794018694023343L;
	@Id
	@Column(name="id")
	private Long id;
	@Column(name="en_name")
	private String enName;
	@Column(name="cn_name")
	private String cnName;
	@JsonIgnore
	@Column(name="i18n_key")
	private String i18nKey;
	@JsonIgnore
	@Column(name="currency")
	private Integer currency;
}
