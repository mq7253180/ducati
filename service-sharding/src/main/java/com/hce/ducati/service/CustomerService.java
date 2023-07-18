package com.hce.ducati.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import com.hce.ducati.dto.UserDto;
import com.hce.ducati.entity.UserEntity;

public interface CustomerService {
	public void add(Long userId, UserEntity e);
	public UserEntity update(Long userId, UserEntity vo);
	public UserDto find(Long userId)  throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, SQLException, IOException;
}