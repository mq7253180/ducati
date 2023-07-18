package com.hce.ducati.service.impl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hce.ducati.dao.UserDao;
import com.hce.ducati.dao.UserRepository;
import com.hce.ducati.dto.UserDto;
import com.hce.ducati.entity.UserEntity;
import com.hce.ducati.service.CustomerService;
import com.quincy.sdk.AllShardsDaoSupport;
import com.quincy.sdk.DaoSupport;
import com.quincy.sdk.annotation.ReadOnly;
import com.quincy.sdk.annotation.sharding.ShardingKey;
import com.quincy.sdk.helper.CommonHelper;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserDao userDao;

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
		return userRepository.save(po);
	}

	@Autowired
	private DaoSupport daoSupport;
	@Autowired
	private AllShardsDaoSupport allShardsDaoSupport;

	@Override
//	@ReadOnly
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public UserDto find(@ShardingKey Long userId) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, SQLException, IOException {
//		Optional<UserEntity> optional = userRepository.findById(userId);
//		return optional.isPresent()?optional.get():null;
//		System.out.println("find================by method");
//		UserDto userDto = (UserDto)daoSupport.executeQuery("SELECT id AS id_str,id,name,nickname,mobile_phone,creation_time FROM s_user WHERE id=?", UserDto.class, UserDto.class, userId);
		System.out.println("find================by proxy");
		UserDto userDto = userDao.find(userId);
		return userDto;
	}
}