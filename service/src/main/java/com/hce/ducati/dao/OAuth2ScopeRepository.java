package com.hce.ducati.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.hce.ducati.entity.OAuth2Scope;

@Repository
public interface OAuth2ScopeRepository extends JpaRepository<OAuth2Scope, Long>, JpaSpecificationExecutor<OAuth2Scope> {
	public List<OAuth2Scope> findByCodeId(Long codeId);
}