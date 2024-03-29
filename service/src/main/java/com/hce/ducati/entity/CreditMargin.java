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
@Entity(name = "b_credit_margin")
public class CreditMargin {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	@Column(name="credit_id")
	private Long creditId;
	@Column(name="currency")
	private String currency;
	@Column(name="amount")
	private BigDecimal amount;
	@Column(name="ratio")
	private Integer ratio;
	@Column(name="price")
	private BigDecimal price;
	@Column(name="credit_amount")
	private BigDecimal creditAmount;
	@Column(name="valid_from")
	private Date validFrom;
	@Column(name="status")
	private Integer status;
	@Column(name="user_id")
	private Long userId;
	@Column(name="approver_user_id")
	private Long approverUserId;
}
