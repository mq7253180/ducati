package com.hce.ducati.controller;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hce.cfca.CommonHelper;
import com.hce.ducati.entity.OAuth2Code;
import com.hce.ducati.entity.OAuth2Scope;
import com.hce.ducati.entity.UserEntity;
import com.hce.ducati.o.OAuth2DTO;
import com.hce.ducati.service.UserService;
import com.quincy.auth.controller.OAuth2TokenControllerSupport;
import com.quincy.auth.o.OAuth2Info;

@Controller
public class OAuth2Controller extends OAuth2TokenControllerSupport {
	@Autowired
	private UserService userService;

	@Override
	protected OAuth2Info getOAuth2Info(Long clientSystemId, String username, HttpServletRequest request) {
		OAuth2Info oauth2Info = new OAuth2Info();
		UserEntity user = userService.find(username);
		if(user!=null) {
			oauth2Info.setUserId(user.getId());
			OAuth2Code oauth2Code = userService.findOAuth2Info(user.getId(), clientSystemId);
			if(oauth2Code!=null) {
				oauth2Info.setId(oauth2Code.getId().toString());
				oauth2Info.setAuthorizationCode(CommonHelper.trim(oauth2Code.getCode()));
			}
		}
		return oauth2Info;
	}

	@Override
	protected String saveOAuth2Info(Long clientSystemId, Long userId, String authorizationCode) {
		OAuth2Code oauth2InfoEntity = userService.saveOAuth2Info(clientSystemId, userId, authorizationCode);
		return oauth2InfoEntity.getId().toString();
	}

	@Override
	protected List<String> notAuthorizedScopes(String codeId, Set<String> scopes) {
		List<OAuth2Scope> authorizedScopes = userService.findOAuth2Scopes(Long.valueOf(codeId));
		List<String> notAuthorizedScopes = new ArrayList<String>(authorizedScopes.size());
		for(String scope:scopes) {
			boolean authorized = false;
			for(OAuth2Scope authorizedScope:authorizedScopes) {
				if(scope.equals(authorizedScope.getScope())) {
					authorized = true;
					break;
				}
			}
			if(!authorized)
				notAuthorizedScopes.add(scope);
		}
		return notAuthorizedScopes;
	}

	@Override
	protected ModelAndView signinView(HttpServletRequest request, String codeId, String _scopes) {
		OAuth2DTO oauth2DTO = userService.findOAuth2(Long.valueOf(codeId));
		String[] scopes = _scopes.split(",");
		StringBuilder displayedScopes = new StringBuilder(100);
		for(String scope:scopes) {
			String displayedScope = SCOPES.get(scope);
			displayedScopes.append("、").append(displayedScope==null?scope:displayedScope);
		}
		return new ModelAndView("/oauth2_login")
				.addObject("client", oauth2DTO.getClientName())
				.addObject("displayedScopes", displayedScopes.substring(1, displayedScopes.length()))
				.addObject("userInfo", oauth2DTO.getUName()+", "+oauth2DTO.getMobilePhone()+", "+oauth2DTO.getEmail());
	}

	@Override
	protected ModelAndView signinView(HttpServletRequest request, String clientId, String username, String scopes) {
		OAuth2Info info = userService.findOAuth2(clientId, username);
		return this.signinView(request, info.getId(), scopes)
				.addObject("codeId", info.getId());
	}

	@RequestMapping("/signin/do")
	public ResponseEntity<?> doSignin(HttpServletRequest request, 
			@RequestParam(required = true, value = "code_id")Long codeId, 
			@RequestParam(required = true, value = "scope")String scope) throws URISyntaxException, OAuthSystemException {
		userService.saveOAuth2Scope(codeId, scope);
		OAuth2DTO dto = userService.findOAuth2(codeId);
		return this.buildResponse(request, dto.getAuthCode());
	}

	@Override
	protected long accessTokenExpireMillis() {
		return 120000;
	}

	@Override
	protected int refreshTokenExpireDays() {
		return 1;
	}

	private final static Map<String, String> SCOPES = new HashMap<String, String>();
	static {
		SCOPES.put("xxx", "某权限");
		SCOPES.put("www", "甲权限");
		SCOPES.put("usrInfo", "个人信息");
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