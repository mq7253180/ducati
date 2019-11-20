package com.hce.ducati.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hce.ducati.feign.QuincyClient;
import com.hce.ducati.o.RegionResultDTO;
import com.quincy.sdk.entity.Region;

@Controller
@RequestMapping("/xxx")
public class XxxController {
	@Autowired
	private QuincyClient quincyClient;

	@GetMapping("/regions")
	@ResponseBody
	public List<Region> feign() {
		RegionResultDTO dto = quincyClient.getRegions();
		return dto.getData();
	}
}
