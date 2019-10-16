package com.hce.ducati.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.hce.ducati.entity.CreditMargin;

@Repository
public interface CreditMarginRepository extends JpaRepository<CreditMargin, Long>, JpaSpecificationExecutor<CreditMargin> {
	public List<CreditMargin> findByCreditId(Long creditId);
	public List<CreditMargin> findByCreditIdAndCurrencyAndStatus(Long creditId, String currency, Integer status);
}
