package com.hce.ducati.controller;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.support.RequestContext;

import com.quincy.auth.AuthHelper;
import com.quincy.auth.annotation.LoginRequired;
import com.quincy.auth.controller.RootController;
import com.quincy.auth.o.XSession;
import com.quincy.core.InnerConstants;
import com.quincy.sdk.annotation.DoNotWrap;
import com.quincy.sdk.helper.CommonHelper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RefreshScope
@Controller
@RequestMapping("")
public class TestController {
	@Value("${spring.pid.file}")
	private String pid;
	@Value("${ducati.host}")
	private String host;
//	@Value("${spring.mvc.pathmatch.matching-strategy}")
//	private String matchingStrategy;
//	@Value("${spring.freemarker.template-loader-path}")
//	private String templateLoaderPath;
//	@Value("${spring.freemarker.suffix}")
//	private String springFreemarkerSuffix;

	@DoNotWrap
	@RequestMapping("/test/callback")
	@ResponseBody
	public String callback() {
		return "OK";
	}

	@RequestMapping("/test/callback/wrap")
	@ResponseBody
	public String callbackWrap() {
		return "OK";
	}

	@RequestMapping("/test/honda")
	@ResponseBody
	public String get() {
		return this.host;
	}

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

	@ResponseBody
	@RequestMapping("/testx")
	public ModelAndView testx() {
//		if(true)
//			throw new RuntimeException("xxx");
		return new ModelAndView("test");
	}

	@ResponseBody
	@RequestMapping("/testxx")
	public ModelAndView testxx() {
		if(true)
			throw new RuntimeException("xxx");
		return new ModelAndView("test");
	}

	@RequestMapping("/testxxx")
	public ModelAndView testxxx() {
		if(true)
			throw new RuntimeException("xxx");
		return new ModelAndView("test");
	}

	@RequestMapping("/testw")
	public String testw(HttpServletRequest request) {
		RequestContext requestContext = new RequestContext(request);
		return requestContext.getMessage("status.error.500");
	}

	@Autowired
	private ApplicationContext applicationContext;

	@RequestMapping("/testww")
	public String testww() {
		return applicationContext.getMessage("status.error.500", null, CommonHelper.getLocale());
	}

	@LoginRequired
	@ResponseBody
	@RequestMapping("/test/update/{name}")
	public void update(HttpServletRequest request, @PathVariable(required = true, name = "name")String name) {
		XSession xs = AuthHelper.getSession(request);
		xs.getUser().setName(name);
		request.getSession().setAttribute(InnerConstants.ATTR_SESSION, xs);
	}
}
