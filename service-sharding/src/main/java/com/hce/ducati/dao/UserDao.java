package com.hce.ducati.dao;

import com.hce.ducati.dto.UserDto;
import com.quincy.sdk.annotation.jdbc.ExecuteQuery;
import com.quincy.sdk.annotation.jdbc.JDBCDao;

@JDBCDao
public interface UserDao {
	@ExecuteQuery(sql = "SELECT id AS id_str,id,name,nickname,mobile_phone,creation_time FROM s_user WHERE id=?", returnItemType=UserDto.class)
	public UserDto find(Long id);
}