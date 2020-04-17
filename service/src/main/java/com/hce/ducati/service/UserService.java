package com.hce.ducati.service;

import java.util.List;

import com.hce.ducati.entity.OAuth2InfoEntity;
import com.hce.ducati.entity.UserEntity;
import com.quincy.auth.entity.Role;

public interface UserService {
	public UserEntity update(UserEntity vo);
	public UserEntity find(String loginName);
	public UserEntity find(Long id);
//	public DSession getSession(Long userId);
	public List<Role> findAllRoles();
	public List<UserEntity> findAllUsers();
	public OAuth2InfoEntity findOAuth2Info(Long userId, Long clientSystemId, String scope);
	public OAuth2InfoEntity saveOAuth2Info(Long userId, Long clientSystemId, String scope, String authorizationCode);
}