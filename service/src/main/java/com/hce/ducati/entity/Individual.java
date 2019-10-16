package com.hce.ducati.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@DynamicInsert
@DynamicUpdate
@EntityListeners({AuditingEntityListener.class})
@Entity(name = "b_individual")
public class Individual {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@CreatedDate
	@Column(name="creation_time")
	private Date creationTime;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@LastModifiedDate
	@Column(name="updated_at")
	private Date updatedAt;
	@Column(name="company_id")
	private Long companyId;
	@Column(name="status")
	private Integer status;
	@Column(name="userid")
	private String userid;
	@Column(name="id_type_camel")
	private String idTypeCamel;
	@Column(name="id_type_cfca")
	private String idTypeCfca;
	@Column(name="id_no")
	private String idNo;
	@Column(name="master")
	private String master;
	@Column(name="name")
	private String name;
	@Column(name="company")
	private String company;
	@Column(name="gender")
	private String gender;
	@Column(name="birth_date")
	private String birthDate;
	@Column(name="country")
	private String country;
	@Column(name="mobile_phone")
	private String mobilePhone;
	@Column(name="addr")
	private String addr;
	@Column(name="product_category")
	private String productCategory;
	@Column(name="credit_currency")
	private String creditCurrency;
	@Column(name="total_credit_amount")
	private BigDecimal totalCreditAmount;
	@Column(name="available_credit_amount")
	private BigDecimal availableCreditAmount;
	@Column(name="credit_from")
	private Date creditFrom;
	@Column(name="credit_to")
	private Date creditTo;
	@Column(name="swift")
	private String swift;
	@Column(name="bank_name")
	private String bankName;
	@Column(name="bank_card_type")
	private String bankCardType;
	@Column(name="bank_account")
	private String bankAccount;
	@Column(name="bank_currency")
	private String bankCurrency;
	@Column(name="credit_rating")
	private Integer creditRating;
	@Column(name="discount")
	private Integer discount;
	@Column(name="recent_3m_deal_amount")
	private BigDecimal recent3mDealAmount;
	@Column(name="recent_6m_deal_amount")
	private BigDecimal recent6mDealAmount;
	@Column(name="commission_rate")
	private BigDecimal commissionRate;
	@Column(name="exchange_rate")
	private BigDecimal exchangeRate;
	@Column(name="deal_duration")
	private Integer dealDuration;
	@Column(name="recent_3m_avg_orders")
	private String recent3mAvgOrders;
	@Column(name="recent_90d_refund_rate")
	private String recent90dRefundRate;
	@Column(name="recent_90d_dishonour_rate")
	private String recent90dDishonourRate;
	@Column(name="recent_90d_avg_shipping_duration")
	private String recent90dAvgShippingDuration;
	@Column(name="recent_90d_orders_incr_rate")
	private String recent90dOrdersIncrRate;
}
