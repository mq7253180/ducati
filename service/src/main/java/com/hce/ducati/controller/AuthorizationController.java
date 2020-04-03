package com.hce.ducati.controller;

import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hce.ducati.entity.UserEntity;
import com.hce.ducati.service.UserService;
import com.quincy.auth.VCodeAuthControllerSupport;
import com.quincy.auth.annotation.LoginRequired;
import com.quincy.auth.o.User;
import com.quincy.core.InnerConstants;
import com.quincy.sdk.Client;
import com.quincy.sdk.RedisProcessor;
import com.quincy.sdk.Result;
import com.quincy.sdk.VCodeCharsFrom;
import com.quincy.sdk.annotation.VCodeRequired;

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

	@VCodeRequired(timeoutForwardTo = "/auth/resetpwd/timeout")
	@RequestMapping("/signin/vcode2")
	public ModelAndView doLogin2(HttpServletRequest request, 
			@RequestParam(required = false, value = "username")String username, 
			@RequestParam(required = false, value = InnerConstants.PARAM_REDIRECT_TO)String redirectTo) throws Exception {
		return this.doLogin(request, username, redirectTo);
	}

	@RequestMapping("/resetpwd/timeout")
	public String pwdResetTimeout() {
		return "/content/pwdreset_timeout";
	}

	/*@Value("${vcode.expire}")
	private int vcodeExpire;*/
	@Autowired
	private RedisProcessor redisProcessor;
	@Value("${clientTokenName}")
	private String clientTokenName;

	@RequestMapping("/vcode")
	@ResponseBody
	public Result vcode(HttpServletRequest request, @RequestParam(required = true, name = "email")String email) throws Exception {
//		String token = redisProcessor.vcode(request, VCodeCharsFrom.MIXED, 6, null, "mq7253180@126.com", "验证码", "验证码为{0}，"+vcodeExpire+"分钟后失效，请尽快操作！");
		String url = "http://127.0.0.1:12081/auth/signin/vcode2?username="+URLEncoder.encode(email, "UTF-8")+"&"+clientTokenName+"={1}&vcode={0}&"+InnerConstants.PARAM_REDIRECT_TO+"="+URLEncoder.encode("/auth/pwd/set", "UTF-8");
		String token = redisProcessor.vcode(request, VCodeCharsFrom.MIXED, 32, null, email, "密码重置", url);
		Result result = new Result(1, "验证码发送成功，请查收邮件", token);
		return result;
	}

	@LoginRequired
	@GetMapping("/pwd/set")
	public String setPwd() {
		return "/content/password";
	}
}