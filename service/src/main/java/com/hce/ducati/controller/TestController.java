package com.hce.ducati.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hce.ducati.feign.QuincyClient;

@Controller
@RequestMapping("/xxx")
public class TestController {
	/*@Autowired
	private QuincyClient quincyClient;*/

	@GetMapping("/test")
	@ResponseBody
	public void test() {
		
	}

	/*@GetMapping("/feign")
	@ResponseBody
	public String feign() {
		return quincyClient.getRegions();
	}*/
}
