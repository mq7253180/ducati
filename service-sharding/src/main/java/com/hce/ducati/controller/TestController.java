package com.hce.ducati.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hce.ducati.service.ZzzService;

@Controller
@RequestMapping("/test")
public class TestController {
	@Autowired
	private ZzzService zzzService;

	@RequestMapping("/test")
	@ResponseBody
	public void test() throws InterruptedException {
		zzzService.test();
	}
}