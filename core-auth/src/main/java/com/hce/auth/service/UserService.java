package com.hce.auth.service;

import java.util.List;

import com.hce.auth.entity.Role;
import com.hce.auth.entity.User;
import com.hce.auth.o.DSession;

public interface UserService {
	public User find(String loginName);
	public User find(Long id);
	public DSession getSession(Long userId);
	public List<Role> findAllRoles();
	public List<User> findAllUsers();
}