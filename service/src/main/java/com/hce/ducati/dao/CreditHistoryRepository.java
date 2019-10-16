package com.hce.ducati.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.hce.ducati.entity.CreditHistory;

@Repository
public interface CreditHistoryRepository extends JpaRepository<CreditHistory, Long>, JpaSpecificationExecutor<CreditHistory> {
	
}
