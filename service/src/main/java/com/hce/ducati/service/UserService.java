package com.hce.ducati.service;

import java.util.List;

import com.hce.ducati.entity.OAuth2Code;
import com.hce.ducati.entity.OAuth2Scope;
import com.hce.ducati.entity.UserEntity;
import com.hce.ducati.o.OAuth2DTO;
import com.quincy.auth.entity.Role;
import com.quincy.auth.o.OAuth2Info;

public interface UserService {
	public UserEntity update(UserEntity vo);
	public UserEntity find(String loginName);
	public UserEntity find(Long id);
//	public XSession getSession(Long userId);
	public List<Role> findAllRoles();
	public List<UserEntity> findAllUsers();
	public OAuth2Code findOAuth2Info(Long userId, Long clientSystemId);
	public OAuth2DTO findOAuth2(Long id);
	public OAuth2Info findOAuth2(String authorizationCode);
	public OAuth2Info findOAuth2(String clientId, String username);
	public OAuth2Code saveOAuth2Info(Long clientSystemId, Long userId, String authorizationCode);
	public List<OAuth2Scope> findOAuth2Scopes(Long codeId);
	public OAuth2Scope saveOAuth2Scope(Long codeId, String scope);
}