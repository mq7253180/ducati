package com.hce.ducati.service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import com.hce.ducati.entity.Credit;
import com.hce.ducati.o.CreditDTO;
import com.quincy.sdk.Pagination;

public interface CreditService {
	public Credit add(Credit credit);
	public Credit update(Credit credit);
	public void update(Long id, BigDecimal amount, Long userId);
	public List<CreditDTO> findCredits();
	public List<Credit> findByCompanyId(Long companyId);
	public Pagination findHistorys(Long creditId, int page, int size, Date from, Date to, DateFormat dateFormat);
	public Credit find(Long id);
}
