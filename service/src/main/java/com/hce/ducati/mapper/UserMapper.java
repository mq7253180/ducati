package com.hce.ducati.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.hce.ducati.o.OAuth2DTO;
import com.quincy.sdk.o.User;

@Repository
public interface UserMapper {
	public int updateLastLogined(@Param("id")Long id, @Param("jsessionid")String jsessionid);
	public List<User> findUsers(@Param("roleId")Long roleId);
	public OAuth2DTO findOAuth2ById(@Param("id")Long id);
}