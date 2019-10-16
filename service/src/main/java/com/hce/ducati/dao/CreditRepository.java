package com.hce.ducati.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.hce.ducati.entity.Credit;

@Repository
public interface CreditRepository extends JpaRepository<Credit, Long>, JpaSpecificationExecutor<Credit> {
	public List<Credit> findByCompanyId(Long companyId);
	public Credit findByCompanyIdAndCurrency(Long companyId, String currency);
}
