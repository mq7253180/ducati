package com.hce.ducati.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.hce.ducati.entity.Enterprise;

@Repository
public interface EnterpriseMapper {
	public int update(@Param("id")Long id, @Param("mobilePhone")String mobilePhone);
	public List<Enterprise> findAll();
	public Enterprise findOne(@Param("id")Long id);
}