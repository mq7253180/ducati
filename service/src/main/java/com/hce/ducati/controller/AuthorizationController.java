package com.hce.ducati.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.hce.auth.controller.AuthorizationControllerAbstract;
import com.hce.auth.o.User;
import com.hce.ducati.entity.UserEntity;
import com.hce.ducati.service.UserService;

public class AuthorizationController extends AuthorizationControllerAbstract {
	@Autowired
	private UserService userService;

	@Override
	protected User findUser(String username) {
		UserEntity user = userService.find(username);
		return user;
	}

	@Override
	protected void updateLastLogin(Long userId, String jsessionid) {
		UserEntity vo = new UserEntity();
		vo.setId(userId);
		vo.setJsessionid(jsessionid);
		vo.setLastLogined(new Date());
		userService.update(vo);
	}
}
