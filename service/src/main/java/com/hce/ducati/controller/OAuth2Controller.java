package com.hce.ducati.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import com.hce.ducati.entity.UserEntity;
import com.hce.ducati.service.UserService;
import com.quincy.auth.controller.OAuth2ControllerSupport;
import com.quincy.auth.o.OAuth2Info;

@Controller
public class OAuth2Controller extends OAuth2ControllerSupport {
	@Autowired
	private UserService userService;

	@Override
	protected OAuth2Info getOAuth2Info(Long clientSystemId, String username, String scope) {
		OAuth2Info oauth2Info = new OAuth2Info();
		UserEntity user = userService.find(username);
		if(user!=null)
			oauth2Info.setUserId(user.getId());
		return oauth2Info;
	}

	@Override
	public void saveInfo(Long clientSystemId, Long userId, String scope, String authorizationCode) {
		
	}

	@Override
	protected ModelAndView signinView(HttpServletRequest request) {
		return null;
	}
}