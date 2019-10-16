package com.hce.auth.controller;

import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hce.auth.annotation.PermissionNeeded;
import com.hce.auth.service.AuthorizationService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Resource(name = "${impl.auth.service}")
	private AuthorizationService authorizationService;

	@PermissionNeeded("reloadSessionByUserId")
	@GetMapping("/reloadSession/user/id/{id}")
	@ResponseBody
	public void updateSessionByUserId(@PathVariable(required = true, name = "id")Long id) throws ClassNotFoundException, IOException {
		authorizationService.updateSessionByUserId(id);
	}

	@PermissionNeeded("reloadSessionsByRole")
	@GetMapping("/reloadSession/role/{roleId}")
	@ResponseBody
	public void updateSessionByRole(@PathVariable(required = true, name = "roleId")Long roleId) throws ClassNotFoundException, IOException {
		authorizationService.updateSessionByRoleId(roleId);
	}
}