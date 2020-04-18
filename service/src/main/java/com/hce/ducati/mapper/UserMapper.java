package com.hce.ducati.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.hce.ducati.entity.UserEntity;
import com.hce.ducati.o.OAuth2DTO;

@Repository
public interface UserMapper {
	public int updateLastLogined(@Param("id")Long id, @Param("jsessionid")String jsessionid);
	public List<UserEntity> findUsers(@Param("roleId")Long roleId);
	public OAuth2DTO findOAuth2(@Param("id")Long id);
}