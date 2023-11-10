package com.hce.ducati.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hce.ducati.entity.UserEntity;
import com.hce.ducati.service.UserService;
import com.quincy.auth.annotation.PermissionNeeded;
import com.quincy.auth.entity.Role;

@Controller("sssccc")
@RequestMapping("/sys")
public class SystemController {
	@Autowired
	private UserService userService;
	@Value("${location.accounts}")
	private String accountsLocation;

	@PermissionNeeded("sysMenu")
	@GetMapping(value = "/admin")
	public ModelAndView menu() {
		List<Role> roles = userService.findAllRoles();
		List<UserEntity> users = userService.findAllUsers();
		return new ModelAndView("/content/root").addObject("roles", roles).addObject("users", users);
	}
}