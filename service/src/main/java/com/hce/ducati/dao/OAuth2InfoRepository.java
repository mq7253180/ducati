package com.hce.ducati.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.hce.ducati.entity.OAuth2InfoEntity;

@Repository
public interface OAuth2InfoRepository extends JpaRepository<OAuth2InfoEntity, Long>, JpaSpecificationExecutor<OAuth2InfoEntity> {
	public OAuth2InfoEntity findByUserIdAndClientSystemIdAndScope(Long userId, Long clientSystemId, String scope);
}