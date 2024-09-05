package com.hce.ducati;

import com.hce.ducati.entity.UserEntity;
import com.quincy.sdk.o.User;

public class ControllerUtils {
	public static User toUser(UserEntity entity) {
		User user = new User();
		user.setId(entity.getId());
		user.setCreationTime(entity.getCreationTime());
		user.setName(entity.getName());
		user.setUsername(entity.getUsername());
		user.setMobilePhone(entity.getMobilePhone());
		user.setEmail(entity.getEmail());
		user.setPassword(entity.getPassword());
		user.setJsessionid(entity.getJsessionid());
		return user;
	}
}