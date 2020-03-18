package com.hce.ducati.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import com.hce.ducati.entity.UserEntity;
import com.hce.ducati.service.UserService;
import com.quincy.auth.VCodeAuthControllerSupport;
import com.quincy.auth.o.User;
import com.quincy.sdk.Client;

@Controller
public class AuthorizationController extends VCodeAuthControllerSupport {
	@Autowired
	private UserService userService;

	@Override
	protected User findUser(String username, Client client) {
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

	@Override
	protected ModelAndView signinView(HttpServletRequest request) {
		return null;
	}
}