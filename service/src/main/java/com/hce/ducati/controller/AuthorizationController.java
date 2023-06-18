package com.hce.ducati.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import com.hce.ducati.ControllerUtils;
import com.hce.ducati.entity.UserEntity;
import com.hce.ducati.service.UserService;
import com.quincy.auth.controller.AuthActions;
import com.quincy.auth.controller.PwdRestEmailInfo;
import com.quincy.auth.o.User;
import com.quincy.o.AttributeKeys;
import com.quincy.o.MyParams;
import com.quincy.sdk.Client;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AuthorizationController {
	@Autowired
	private UserService userService;

	@Bean
	public AuthActions authActions() {
		return new AuthActions() {
			@Override
			public User findUser(String username, Client client) {
				UserEntity userEntity = userService.find(username);
				User user = ControllerUtils.toUser(userEntity);
//				user.setCurrencyAccounts(new HashMap<String, BigDecimal>(2));
				user.setAttributes(new HashMap<String, Serializable>(2));
				user.getAttributes().put(AttributeKeys.MY_PARAMS, new MyParams().setXxx("wwwqqq"));
				return user;
			}

			@Override
			public void updateLastLogin(Long userId, Client client, String jsessionid) {
				UserEntity vo = new UserEntity();
				vo.setId(userId);
				vo.setJsessionid(jsessionid);
				vo.setLastLogined(new Date());
				userService.update(vo);
			}

			@Override
			public ModelAndView signinView(HttpServletRequest request) {
				return null;
			}
		};
	}

	@Bean
	public PwdRestEmailInfo pwdRestEmailInfo() {
		return new PwdRestEmailInfo() {
			@Override
			public String getSubject() {
				return "密码重置";
			}
			@Override
			public String getContent(String uri) {
				return new StringBuilder(500)
						.append("<html>")
						.append("\n\t<head>")
						.append("\n\t\t<meta charset=\"UTF-8\">")
						.append("\n\t\t<title>密码重置</title>")
						.append("\n\t</head>")
						.append("\n\t<body>")
						.append("\n\t\t<font color=\"purple\">请</font>")
						.append("<a href=\"")
						.append("http://127.0.0.1:12081")
						.append(uri)
						.append("\">点击</a>")
						.append("<font color=\"purple\">继续操作，如果无法自动打开，请复制此链接至浏览器地址栏敲回车: ")
						.append("http://127.0.0.1:12081")
						.append(uri)
						.append("</font>")
						.append("\n\t</body>")
						.append("\n</html>").toString();
			}
		};
	}
}