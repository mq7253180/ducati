package com.hce.ducati.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.hce.ducati.service.UserService;
import com.quincy.auth.controller.OAuth2ControllerSupport;
import com.quincy.auth.o.OAuth2Info;

@Controller
public class OAuth2Controller extends OAuth2ControllerSupport {
	@Autowired
	private UserService userService;

	@Override
	protected long accessTokenExpireMillis() {
		return 120000;
	}

	@Override
	protected int refreshTokenExpireDays() {
		return 1;
	}

	@Override
	protected boolean authenticateSecret(String inputed, String dbStored, String content) {
		return inputed.equals(dbStored);
	}

	@Override
	protected OAuth2Info getOAuth2Info(String authorizationCode) {
		return userService.findOAuth2(authorizationCode);
	}
}