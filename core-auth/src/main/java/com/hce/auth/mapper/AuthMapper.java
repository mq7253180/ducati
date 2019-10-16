package com.hce.auth.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.hce.auth.entity.Menu;
import com.hce.auth.entity.Permission;
import com.hce.auth.entity.Role;
import com.hce.auth.entity.User;

@Repository
public interface AuthMapper {
	public List<Role> findRolesByUserId(@Param("userId")Long userId);
	public List<Permission> findPermissionsByUserId(@Param("userId")Long userId);
	public List<Menu> findMenusByUserId(@Param("userId")Long userId);
	public int updateLastLogined(@Param("id")Long id, @Param("jsessionid")String jsessionid);
	public List<User> findUsers(@Param("roleId")Long roleId);
}
