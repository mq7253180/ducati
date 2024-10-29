package com.hce.ducati.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hce.ducati.service.XxxService;
import com.quincy.sdk.annotation.auth.LoginRequired;

@Controller
@RequestMapping("/aaa")
public class XxxShardingController {
	@Autowired
	private XxxService xxxService;

	@LoginRequired
	@GetMapping("/ddd/{limit}/{offset}")
	@ResponseBody
	public Object findSubTest(@PathVariable(required = true, name = "limit")int limit, @PathVariable(required = true, name = "offset")int offset) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, SQLException, IOException, CloneNotSupportedException {
		return xxxService.findSubTests(limit, offset);
	}
}
