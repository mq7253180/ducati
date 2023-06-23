package com.hce.ducati.controller;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.quincy.auth.annotation.LoginRequired;
import com.quincy.auth.controller.RootController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("")
public class TestController {
	@Value("${spring.pid.file}")
	private String pid;
//	@Value("${spring.mvc.pathmatch.matching-strategy}")
//	private String matchingStrategy;
//	@Value("${spring.freemarker.template-loader-path}")
//	private String templateLoaderPath;
//	@Value("${spring.freemarker.suffix}")
//	private String springFreemarkerSuffix;

	@RequestMapping("/pid")
	@ResponseBody
	public String pid() {
		return pid;
	}

	@RequestMapping("/value")
	@ResponseBody
	public String value() {
		return null;
	}

	@RequestMapping("/pathmatch")
	@ResponseBody
	public String pathmatch() {
//		if(true)
//			throw new RuntimeException("pathmatch");
		return null;
	}

	@Autowired
	private RootController rootController;

	@LoginRequired
	@RequestMapping("index")
	public ModelAndView root(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return rootController.root(request, response);
	}
	
	@Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

	@RequestMapping("/mapinfo")
	public void reqinfo() {
		Map<RequestMappingInfo, HandlerMethod> methods = requestMappingHandlerMapping.getHandlerMethods();
		Set<Entry<RequestMappingInfo, HandlerMethod>> set = methods.entrySet();
		for(Entry<RequestMappingInfo, HandlerMethod> e:set) {
			RequestMappingInfo info = e.getKey();
			HandlerMethod method = e.getValue();
			System.out.println(info.getName()+"==="+method.getBean().getClass().getName()+"---"+method.getBeanType()+"---"+method.getMethod().getName());
			if(method.getBeanType().getName().indexOf("RootController")>=0) {
				Set<String> s = info.getPatternValues();
				for(String pv:s) {
					System.out.println("-----"+pv);
				}
			}
		}
	}
}
