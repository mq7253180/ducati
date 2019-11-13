package com.hce.ducati.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class TestController {
	@Value("${spring.pid.file}")
	private String pid;

	@RequestMapping("/pid")
	@ResponseBody
	public String pid() {
		return pid;
	}
}
