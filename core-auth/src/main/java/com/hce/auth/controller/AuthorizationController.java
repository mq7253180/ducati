package com.hce.auth.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContext;

import com.hce.auth.service.AuthorizationService;
import com.quincy.global.helper.CommonHelper;

@Controller
@RequestMapping("/auth")
public class AuthorizationController {
	@Resource(name = "${impl.auth.service}")
	private AuthorizationService authorizationService;
	/**
	 * 进登录页
	 */
	@RequestMapping("/signin")
	public ModelAndView toLogin(@RequestParam(required = false, value = "back")String _back) {
		ModelAndView mv = new ModelAndView("/login");
		String back = CommonHelper.trim(_back);
		if(back!=null)
			mv.addObject("back", back);
		return mv;
	}
	/**
	 * 进登录跳转页
	 */
	@RequestMapping("/signin/broker")
	public ModelAndView toLoginBroker(@RequestParam(required = false, value = "back")String _back) {
		ModelAndView mv = new ModelAndView("/login_broker");
		String back = CommonHelper.trim(_back);
		if(back!=null)
			mv.addObject("back", back);
		return mv;
	}
	/**
	 * 登出
	 */
	@RequestMapping("/signout")
	public ModelAndView logout(HttpServletRequest request) throws Exception {
		authorizationService.logout(request);
		RequestContext requestContext = new RequestContext(request);
		ModelAndView mv = new ModelAndView("/result");
		mv.addObject("status", 1);
		mv.addObject("msg", requestContext.getMessage("status.success"));
		return mv;
	}
	/**
	 * 点超链接没权限要进入的页面
	 */
	@RequestMapping("/deny")
	public String deny(HttpServletRequest request) throws Exception {
		return "/deny";
	}
}
