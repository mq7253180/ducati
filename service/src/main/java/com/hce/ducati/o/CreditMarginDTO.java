package com.hce.ducati.o;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CreditMarginDTO {
	private Long id;
	private String companyCnName;
	private String companyEnName;
	private String validFrom;
	private String currency;//保证金币种
	private BigDecimal amount;//保证金量
	private BigDecimal price;//汇率
	private String creditCurrency;//合同授信币种
	private BigDecimal creditAmount;//合同授信额度
	private BigDecimal marginAmount;//保证金折算额度
	private Integer ratio;
	private Integer status;
	private String displayedStatus;
	private String userName;
	private String approverUserName;
}
