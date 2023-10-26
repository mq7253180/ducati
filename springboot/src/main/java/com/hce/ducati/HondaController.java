package com.hce.ducati;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/honda")
@RefreshScope
public class HondaController {
	@Value("${ducati.host}")
	private String value;

	@RequestMapping("/host")
	@ResponseBody
	public String get() {
		return this.value;
	}
}