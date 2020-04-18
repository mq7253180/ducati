package com.hce.ducati.controller;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hce.cfca.CommonHelper;
import com.hce.ducati.entity.OAuth2InfoEntity;
import com.hce.ducati.entity.UserEntity;
import com.hce.ducati.o.OAuth2DTO;
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
		if(user!=null) {
			oauth2Info.setUserId(user.getId());
			OAuth2InfoEntity oauth2InfoEntity = userService.findOAuth2Info(user.getId(), clientSystemId, scope);
			if(oauth2InfoEntity!=null)
				oauth2Info.setAuthorizationCode(CommonHelper.trim(oauth2InfoEntity.getAuthorizationCode()));
		}
		return oauth2Info;
	}

	@Override
	protected String saveOAuth2Info(String oauth2Id, String authorizationCode) {
		OAuth2InfoEntity oauth2InfoEntity = userService.saveOAuth2Info(Long.valueOf(oauth2Id), authorizationCode);
		return oauth2InfoEntity.getId().toString();
	}

	@Override
	protected String saveOAuth2Info(Long clientSystemId, String username, String scope) {
		OAuth2InfoEntity oauth2InfoEntity = userService.saveOAuth2Info(username, clientSystemId, scope, null);
		return oauth2InfoEntity.getId().toString();
	}

	@Override
	protected ModelAndView signinView(HttpServletRequest request, String oauth2Id) {
		OAuth2DTO oauth2DTO = userService.findOAuth2(Long.valueOf(oauth2Id));
		String scope = SCOPES.get(oauth2DTO.getScope());
		return new ModelAndView("/oauth2_login")
				.addObject("client", oauth2DTO.getClientName())
				.addObject("scope", scope==null?oauth2DTO.getScope():scope)
				.addObject("userInfo", oauth2DTO.getUName()+", "+oauth2DTO.getMobilePhone()+", "+oauth2DTO.getEmail());
	}

	@RequestMapping("/signin/do")
	public ResponseEntity<?> doSignin(HttpServletRequest request, @RequestParam(required = true, value = "oauth2_id")Long oauth2Id) throws URISyntaxException, OAuthSystemException {
		return this.generateAuthorizationCode(request, oauth2Id.toString());
	}

	private final static Map<String, String> SCOPES = new HashMap<String, String>();
	static {
		SCOPES.put("xxx", "某权限");
		SCOPES.put("www", "甲权限");
		SCOPES.put("ooo", "个人信息");
	}
}