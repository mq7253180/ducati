package com.hce.ducati.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
	public int updateLastLogined(@Param("id")Long id, @Param("jsessionid")String jsessionid);
}