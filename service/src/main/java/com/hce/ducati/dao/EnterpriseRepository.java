package com.hce.ducati.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.hce.ducati.entity.Enterprise;

@Repository
public interface EnterpriseRepository extends JpaRepository<Enterprise, Long>, JpaSpecificationExecutor<Enterprise> {
	public Enterprise findByIdNoAndIdTypeAndName(String idNo, String idType, String name);
}
