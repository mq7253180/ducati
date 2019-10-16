package com.hce.auth.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hce.auth.dao.RoleRepository;
import com.hce.auth.dao.UserRepository;
import com.hce.auth.entity.Menu;
import com.hce.auth.entity.Permission;
import com.hce.auth.entity.Role;
import com.hce.auth.entity.User;
import com.hce.auth.mapper.AuthMapper;
import com.hce.auth.o.DSession;
import com.hce.auth.service.UserService;
import com.quincy.global.annotation.ReadOnly;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AuthMapper authMapper;
	@Autowired
	private RoleRepository roleRepository;

	@Override
	@ReadOnly
	public User find(String loginName) {
		User user = userRepository.findByUsernameOrEmailOrMobilePhone(loginName, loginName, loginName);
		return user;
	}

	@Override
	@ReadOnly
	public User find(Long id) {
		User user = null;
		Optional<User> optional = userRepository.findById(id);
		if(optional.isPresent())
			user = optional.get();
		return user;
	}

	@Override
	@ReadOnly
	public DSession getSession(Long userId) {
		DSession session = new DSession();
		//角色
		List<Role> roleList = authMapper.findRolesByUserId(userId);
		Map<Long, String> roleMap = new HashMap<Long, String>(roleList.size());
		for(Role role:roleList) {//去重
			roleMap.put(role.getId(), role.getName());
		}
		List<String> roles = new ArrayList<String>(roleMap.size());
		roles.addAll(roleMap.values());
		session.setRoles(roles);
		//权限
		List<Permission> permissionList = authMapper.findPermissionsByUserId(userId);
		Map<Long, String> permissionMap = new HashMap<Long, String>(permissionList.size());
		for(Permission permission:permissionList) {//去重
			permissionMap.put(permission.getId(), permission.getName());
		}
		List<String> permissions = new ArrayList<String>(permissionMap.size());
		permissions.addAll(permissionMap.values());
		session.setPermissions(permissions);
		//菜单
		List<Menu> rootMenus = this.findMenusByUserId(userId);
		session.setMenus(rootMenus);
		return session;
	}

	private List<Menu> findMenusByUserId(Long userId) {
		List<Menu> allMenus = authMapper.findMenusByUserId(userId);
		Map<Long, Menu> duplicateRemovedMenus = new HashMap<Long, Menu>(allMenus.size());
		for(Menu menu:allMenus) {
			duplicateRemovedMenus.put(menu.getId(), menu);
		}
		List<Menu> rootMenus = new ArrayList<Menu>(duplicateRemovedMenus.size());
		Set<Entry<Long, Menu>> entrySet = duplicateRemovedMenus.entrySet();
		for(Entry<Long, Menu> entry:entrySet) {
			Menu menu = entry.getValue();
			if(menu.getPId()==null) {
				rootMenus.add(menu);
				this.loadChildrenMenus(menu, entrySet);
			}
		}
		return rootMenus;
	}

	private void loadChildrenMenus(Menu parent, Set<Entry<Long, Menu>> entrySet) {
		for(Entry<Long, Menu> entry:entrySet) {
			Menu menu = entry.getValue();
			if(parent.getId()==menu.getPId()) {
				if(parent.getChildren()==null)
					parent.setChildren(new ArrayList<Menu>(10));
				parent.getChildren().add(menu);
			}
		}
		if(parent.getChildren()!=null&&parent.getChildren().size()>0) {
			for(Menu child:parent.getChildren()) {
				this.loadChildrenMenus(child, entrySet);
			}
		}
	}

	@Override
	@ReadOnly
	public List<Role> findAllRoles() {
		return roleRepository.findAll();
	}

	@Override
	@ReadOnly
	public List<User> findAllUsers() {
		return userRepository.findAll();
	}
}