package com.hce.auth.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hce.auth.annotation.LoginRequired;
import com.hce.auth.o.DSession;
import com.hce.auth.service.AuthorizationService;

@Controller
@RequestMapping("")
public class RootController {
	@Resource(name = "${impl.auth.service}")
	private AuthorizationService authorizationService;

	@RequestMapping("")
	public String root(HttpServletRequest request) throws Exception {
		DSession session = authorizationService.getSession(request);
		String uri = session==null?"/auth/signin":"/index";
		return "redirect:"+uri;
	}

	@LoginRequired
	@GetMapping(value = "/index")
	public String index() {
		return "/index";
	}

	@GetMapping(value = "/static/**")
	public void handleStatic() {
		
	}
}
