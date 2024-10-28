package com.hce.ducati.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.quincy.sdk.annotation.auth.PermissionNeeded;

@Controller
@RequestMapping("/admin")
public class AdminController {
//	@Autowired
//	private UserMapper userMapper;
//	@Autowired
//	private UserService userService;

	@PermissionNeeded("reloadSessionByUserId")
	@GetMapping("/reloadSession/user/id/{id}")
	@ResponseBody
	public void updateSessionByUserId(@PathVariable(required = true, name = "id")Long id) throws ClassNotFoundException, IOException {
//		UserEntity userEntity = userService.find(id);
//		authorizationServerService.updateSession(ControllerUtils.toUser(userEntity));
	}

	@PermissionNeeded("reloadSessionsByRole")
	@GetMapping("/reloadSession/role/{roleId}")
	@ResponseBody
	public void updateSessionByRole(@PathVariable(required = true, name = "roleId")Long roleId) throws ClassNotFoundException, IOException {
//		List<User> users = userMapper.findUsers(roleId);
//		authorizationServerService.updateSession(users);s
	}
}