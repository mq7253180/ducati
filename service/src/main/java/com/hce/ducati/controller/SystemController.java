package com.hce.ducati.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.quincy.sdk.annotation.auth.PermissionNeeded;

@Controller("sssccc")
@RequestMapping("/sys")
public class SystemController {
//	@Autowired
//	private UserService userService;
	@Value("${location.accounts}")
	private String accountsLocation;

	@PermissionNeeded("sysMenu")
	@GetMapping(value = "/admin")
	public ModelAndView menu() {
//		List<Role> roles = userService.findAllRoles();
//		List<UserEntity> users = userService.findAllUsers();
//		return new ModelAndView("/content/root").addObject("roles", roles).addObject("users", users);
		return null;
	}
}