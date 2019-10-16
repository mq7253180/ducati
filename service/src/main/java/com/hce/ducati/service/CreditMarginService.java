package com.hce.ducati.service;

import java.math.BigDecimal;
import java.util.Date;

import com.hce.ducati.entity.Credit;
import com.hce.ducati.entity.CreditMargin;
import com.quincy.global.Pagination;
import com.quincy.global.Result;

public interface CreditMarginService {
	public void updateCreditAmount(Credit credit);
	public CreditMargin add(CreditMargin margin);
	public Result update(CreditMargin margin);
	public Result audit(Long id, Long approverUserId, String currency, BigDecimal amount);
	public Pagination find(int page, int size, Date from, Date to, Long companyId, String currency);
	public Result delete(Long id);
}
