package com.hce.ducati.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hce.ducati.dao.OAuth2InfoRepository;
import com.hce.ducati.dao.UserRepository;
import com.hce.ducati.entity.OAuth2InfoEntity;
import com.hce.ducati.entity.UserEntity;
import com.hce.ducati.service.UserService;
import com.quincy.auth.dao.RoleRepository;
import com.quincy.auth.entity.Role;
import com.quincy.sdk.annotation.ReadOnly;
import com.quincy.sdk.helper.CommonHelper;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private OAuth2InfoRepository oauth2InfoRepository;

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
	public OAuth2InfoEntity findOAuth2Info(Long userId, Long clientSystemId, String scope) {
		return oauth2InfoRepository.findByUserIdAndClientSystemIdAndScope(userId, clientSystemId, scope);
	}
}