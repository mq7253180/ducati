package com.hce.ducati.controller;

import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;

import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse.OAuthResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hce.cfca.CommonHelper;
import com.hce.ducati.entity.OAuth2InfoEntity;
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
		OAuth2InfoEntity oauth2InfoEntity = userService.findOAuth2Info(user.getId(), clientSystemId, scope);
		if(oauth2InfoEntity!=null)
			oauth2Info.setAuthorizationCode(CommonHelper.trim(oauth2InfoEntity.getAuthorizationCode()));
		return oauth2Info;
	}

	@Override
	protected void saveOAuth2Info(Long clientSystemId, String username, String scope, String authorizationCode) {
		userService.saveOAuth2Info(username, clientSystemId, scope, authorizationCode);
	}

	@Override
	protected ModelAndView signinView(HttpServletRequest request) {
		return null;
	}

	@RequestMapping("/signin/do")
	public void doSignin(HttpServletRequest request) {
//		OAuthResponseBuilder builder = this.generateAuthorizationCode(request, userId, scope);
	}
}