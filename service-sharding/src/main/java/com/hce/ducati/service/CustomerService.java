package com.hce.ducati.service;

import java.sql.SQLException;

import com.hce.ducati.entity.UserEntity;

public interface CustomerService {
	public void add(Long userId, UserEntity e);
	public UserEntity update(Long userId, UserEntity vo);
	public UserEntity find(Long userId) throws SQLException;
}