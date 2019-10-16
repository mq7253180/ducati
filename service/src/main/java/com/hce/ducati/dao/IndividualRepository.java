package com.hce.ducati.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.hce.ducati.entity.Individual;

@Repository
public interface IndividualRepository extends JpaRepository<Individual, Long>, JpaSpecificationExecutor<Individual> {
	public Individual findByIdNoAndIdTypeCfcaAndMasterAndCompanyId(String idNo, String idTypeCfca, String master, Long companyId);
}
