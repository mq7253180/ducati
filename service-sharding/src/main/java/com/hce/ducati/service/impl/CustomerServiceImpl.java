package com.hce.ducati.service.impl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hce.ducati.dao.UserRepository;
import com.hce.ducati.entity.UserEntity;
import com.hce.ducati.service.CustomerService;
import com.quincy.sdk.annotation.ReadOnly;
import com.quincy.sdk.annotation.ShardingKey;
import com.quincy.sdk.helper.CommonHelper;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private UserRepository userRepository;

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	@Override
	public void add(@ShardingKey Long userId, UserEntity e) {
		userRepository.save(e);
	}

	@Transactional
	@Override
	public UserEntity update(@ShardingKey Long userId, UserEntity vo) {
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
	public UserEntity find(@ShardingKey Long userId) {
		Optional<UserEntity> optional = userRepository.findById(userId);
		return optional.isPresent()?optional.get():null;
	}
}