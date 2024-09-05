package com.hce.ducati.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hce.ducati.dao.OAuth2CodeRepository;
import com.hce.ducati.dao.OAuth2ScopeRepository;
import com.hce.ducati.dao.UserRepository;
import com.hce.ducati.entity.OAuth2Code;
import com.hce.ducati.entity.OAuth2Scope;
import com.hce.ducati.entity.UserEntity;
import com.hce.ducati.mapper.UserMapper;
import com.hce.ducati.o.OAuth2DTO;
import com.hce.ducati.service.UserService;
import com.quincy.auth.dao.RoleRepository;
import com.quincy.auth.entity.Role;
import com.quincy.sdk.annotation.jdbc.ReadOnly;
import com.quincy.sdk.helper.CommonHelper;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private OAuth2CodeRepository oauth2CodeRepository;
	@Autowired
	private OAuth2ScopeRepository oauth2ScopeRepository;
	@Autowired
	private  UserMapper userMapper;

	@Transactional
	@Override
	public UserEntity update(UserEntity vo) {
		UserEntity po = userRepository.findById(vo.getId()).get();
		String username = CommonHelper.trim(vo.getUsername());
		if(username!=null)
			po.setUsername(username);
		String password = CommonHelper.trim(vo.getPassword());
		if(password!=null)
			po.setPassword(password);
		String email = CommonHelper.trim(vo.getEmail());
		if(email!=null)
			po.setEmail(email);
		String mobilePhone = CommonHelper.trim(vo.getMobilePhone());
		if(mobilePhone!=null)
			po.setMobilePhone(mobilePhone);
		String name = CommonHelper.trim(vo.getName());
		if(name!=null)
			po.setName(name);
		String jsessionid = CommonHelper.trim(vo.getJsessionid());
		if(jsessionid!=null)
			po.setJsessionid(jsessionid);
		Date lastLogined = vo.getLastLogined();
		if(lastLogined!=null)
			po.setLastLogined(lastLogined);
		userRepository.save(po);
		return po;
	}

	@Override
	@ReadOnly
	public UserEntity find(String loginName) {
		UserEntity user = userRepository.findByUsernameOrEmailOrMobilePhone(loginName, loginName, loginName);
		return user;
	}

	@Override
	@ReadOnly
	public UserEntity find(Long id) {
		UserEntity user = null;
		Optional<UserEntity> optional = userRepository.findById(id);
		if(optional.isPresent())
			user = optional.get();
		return user;
	}

	@Override
	@ReadOnly
	public List<Role> findAllRoles() {
		return roleRepository.findAll();
	}

	@Override
	@ReadOnly
	public List<UserEntity> findAllUsers() {
		return userRepository.findAll();
	}

	@Override
	@ReadOnly
	public OAuth2Code findOAuth2Info(Long userId, Long clientSystemId) {
		return oauth2CodeRepository.findByUserIdAndClientSystemId(userId, clientSystemId);
	}

	@Override
	public OAuth2Code saveOAuth2Info(Long clientSystemId, Long userId, String _authorizationCode) {
		OAuth2Code vo = oauth2CodeRepository.findByUserIdAndClientSystemId(userId, clientSystemId);
		if(vo==null) {
			vo = new OAuth2Code();
			vo.setUserId(userId);
			vo.setClientSystemId(clientSystemId);
		}
		String authorizationCode = CommonHelper.trim(_authorizationCode);
		if(authorizationCode!=null)
			vo.setCode(authorizationCode);
		return oauth2CodeRepository.save(vo);
	}

	@Override
	public OAuth2DTO findOAuth2(Long id) {
		return userMapper.findOAuth2ById(id);
	}

	@ReadOnly
	@Override
	public List<OAuth2Scope> findOAuth2Scopes(Long codeId) {
		return oauth2ScopeRepository.findByCodeId(codeId);
	}

	@Override
	public OAuth2Scope saveOAuth2Scope(Long codeId, String scope) {
		OAuth2Scope oauth2Scope = new OAuth2Scope();
		oauth2Scope.setCodeId(codeId);
		oauth2Scope.setScope(scope);
		return oauth2ScopeRepository.save(oauth2Scope);
	}
}