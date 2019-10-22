package com.hce.ducati.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.quincy.auth.o.User;

import lombok.Data;

@Data
@DynamicInsert
@DynamicUpdate
@EntityListeners({AuditingEntityListener.class})
@Entity(name = "s_user")
public class UserEntity implements Serializable, User {
	private static final long serialVersionUID = 3068671906589197352L;
	@Id
	@Column(name="id")
	private Long id;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	@CreatedDate
	@Column(name="creation_time")
	private Date creationTime;
//	@ManyToMany(cascade = CascadeType.ALL)
//	@JoinTable(name = "s_role_user_rel", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
//	private Set<Role> roles;
	@Column(name="username")
	private String username;
	@Column(name="name")
	private String name;
	@Column(name="password")
	private String password;
	@Column(name="email")
	private String email;
	@Column(name="mobile_phone")
	private String mobilePhone;
	@Column(name="jsessionid")
	private String jsessionid;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	@Column(name="last_logined")
	private Date lastLogined;
}
