package com.hce.ducati.dao;

import com.hce.ducati.o.UserDto;
import com.quincy.sdk.annotation.ExecuteQuery;
import com.quincy.sdk.annotation.ExecuteUpdate;
import com.quincy.sdk.annotation.JDBCDao;

@JDBCDao
public interface TestDao {
	@ExecuteQuery(sql = "SELECT id AS id_str,id,name,mobile_phone,creation_time FROM s_user WHERE id=?", returnItemType=UserDto.class)
	public UserDto find(Long id);
	@ExecuteUpdate(sql = "UPDATE s_user SET mobile_phone=? WHERE id=?")
	public int upate(String mobilePhone, Long id);
}