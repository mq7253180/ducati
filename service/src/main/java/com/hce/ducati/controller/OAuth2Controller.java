package com.hce.ducati.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import com.quincy.auth.controller.OAuth2ControllerSupport;
import com.quincy.auth.o.OAuth2Info;
import com.quincy.auth.service.GeneralService;

@Controller
public class OAuth2Controller extends OAuth2ControllerSupport {
	@Autowired
	private GeneralService generalService;

	@Override
	public OAuth2Info getOAuth2Info(String clientId, String scope, String username) {
		return null;
	}

	@Override
	public void saveInfo(Long clientSystemId, Long userId, String scope, String authorizationCode) {
		
	}

	@Override
	protected ModelAndView signinView(HttpServletRequest request) {
		return null;
	}
}