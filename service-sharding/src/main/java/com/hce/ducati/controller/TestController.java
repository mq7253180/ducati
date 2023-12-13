package com.hce.ducati.controller;

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
	public void test() throws InterruptedException {
		zzzService.test();
	}

	@RequestMapping("/honda")
	@ResponseBody
	public String get() {
		return this.host;
	}
}