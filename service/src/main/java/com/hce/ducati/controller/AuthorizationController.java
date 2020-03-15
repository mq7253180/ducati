package com.hce.ducati.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.hce.ducati.entity.UserEntity;
import com.hce.ducati.service.UserService;
import com.quincy.auth.controller.AuthorizationControllerSupport;
import com.quincy.auth.o.User;
import com.quincy.sdk.Client;

@Controller
public class AuthorizationController extends AuthorizationControllerSupport {
	@Autowired
	private UserService userService;

	@Override
	protected User findUser(String username) {
		UserEntity user = userService.find(username);
		return user;
	}

	@Override
	protected void updateLastLogin(Long userId, Client client, String jsessionid) {
		UserEntity vo = new UserEntity();
		vo.setId(userId);
		vo.setJsessionid(jsessionid);
		vo.setLastLogined(new Date());
		userService.update(vo);
	}
}