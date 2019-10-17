package com.hce.ducati.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hce.auth.annotation.PermissionNeeded;
import com.hce.auth.service.AuthorizationService;
import com.hce.ducati.entity.UserEntity;
import com.hce.ducati.mapper.UserMapper;
import com.hce.ducati.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Resource(name = "${impl.auth.service}")
	private AuthorizationService authorizationService;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserService userService;

	@PermissionNeeded("reloadSessionByUserId")
	@GetMapping("/reloadSession/user/id/{id}")
	@ResponseBody
	public void updateSessionByUserId(@PathVariable(required = true, name = "id")Long id) throws ClassNotFoundException, IOException {
		UserEntity user = userService.find(id);
		authorizationService.updateSession(user);
	}

	@PermissionNeeded("reloadSessionsByRole")
	@GetMapping("/reloadSession/role/{roleId}")
	@ResponseBody
	public void updateSessionByRole(@PathVariable(required = true, name = "roleId")Long roleId) throws ClassNotFoundException, IOException {
		List<UserEntity> users = userMapper.findUsers(roleId);
		authorizationService.updateSession(users);
	}
}
