package com.hce.ducati.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@DynamicInsert
@DynamicUpdate
@EntityListeners({AuditingEntityListener.class})
@Entity(name = "b_credit")
public class Credit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	@Column(name="company_id")
	private Long companyId;
	@Column(name="currency")
	private String currency;
	@Column(name="amount")
	private BigDecimal amount;
	@Column(name="ratio")
	private Integer ratio;
	@Column(name="max_limit_one_day")
	private BigDecimal maxLimitOneDay;
	@Column(name="valid_from")
	private Date validFrom;
	@Column(name="user_id")
	private Long userId;
}
