package com.hce.ducati.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.hce.ducati.o.RegionDto;

@Repository
public interface TestMapper {
	public RegionDto findRegion(@Param("id")Long id);
	public int updateRegion(@Param("id")Long id, @Param("enName")String enName);
	public int updateTest(@Param("id")Long id, @Param("aaa")String aaa);
}