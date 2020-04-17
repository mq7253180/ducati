package com.hce.ducati.entity;

import java.io.Serializable;

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
@Entity(name = "s_oauth2")
public class OAuth2InfoEntity implements Serializable {
	private static final long serialVersionUID = 6829594433533198471L;
	@Id
	@Column(name="id")
	private Long id;
	@Column(name="user_id")
	private Long userId;
	@Column(name="client_system_id")
	private Long clientSystemId;
	@Column(name="scope")
	private String scope;
	@Column(name="auth_code")
	private String authorizationCode;
}