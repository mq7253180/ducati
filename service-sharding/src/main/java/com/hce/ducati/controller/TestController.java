package com.hce.ducati.controller;

import java.net.UnknownHostException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hce.ducati.service.ZzzService;

@RefreshScope
@Controller
@RequestMapping("/test")
public class TestController {
	@Autowired
	private ZzzService zzzService;
	@Value("${ducati.host}")
	private String host;

	@RequestMapping("/test")
	@ResponseBody
	public void test() throws InterruptedException, UnknownHostException {
		zzzService.test();
	}

	@RequestMapping("/chaxun")
	@ResponseBody
	public String chaxun() throws InterruptedException, UnknownHostException {
		return zzzService.chaxun("asfa", 324);
	}

	@RequestMapping("/keys")
	@ResponseBody
	public Set<String> allKeys() {
		return zzzService.allKeys(null);
	}

	@RequestMapping("/honda")
	@ResponseBody
	public String get() {
		return this.host;
	}
}