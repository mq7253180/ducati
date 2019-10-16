package com.hce.ducati.o;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CreditDTO {
	private Long id;
	private String companyCnName;
	private String companyEnName;
	private String validFrom;
	private String currency;
	private BigDecimal amount;
	private BigDecimal maxLimitOneDay;
	private BigDecimal usedAmount;
	private Integer ratio;
	private String userName;
}
