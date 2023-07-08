package com.hce.ducati.dao;

import java.util.List;

import com.hce.ducati.dto.UserDto;
import com.quincy.sdk.MasterOrSlave;
import com.quincy.sdk.annotation.AllShardDao;
import com.quincy.sdk.annotation.ExecuteQuery;

@AllShardDao
public interface TestDao {
	@ExecuteQuery(sql = "SELECT id AS id_str,id,name,mobile_phone,creation_time FROM s_user;", masterOrSlave = MasterOrSlave.SLAVE, returnItemType = UserDto.class)
	public List<UserDto>[] findAllUsers();
	@ExecuteQuery(sql = "SELECT id AS id_str,id,name,mobile_phone,creation_time FROM s_user WHERE name LIKE ?;", masterOrSlave = MasterOrSlave.SLAVE, returnItemType = UserDto.class)
	public List<UserDto>[] findUsersBySurname(String keyWord);
	@ExecuteQuery(sql = "SELECT id AS id_str,id,name,mobile_phone,creation_time FROM s_user WHERE id=?;", masterOrSlave = MasterOrSlave.SLAVE, returnItemType = UserDto.class)
	public UserDto findUser(Long id);
}