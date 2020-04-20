package com.hce.ducati.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.hce.ducati.entity.OAuth2Code;

@Repository
public interface OAuth2CodeRepository extends JpaRepository<OAuth2Code, Long>, JpaSpecificationExecutor<OAuth2Code> {
	public OAuth2Code findByUserIdAndClientSystemId(Long userId, Long clientSystemId);
}