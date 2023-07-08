package com.hce.ducati.dao;

import java.util.List;

import com.hce.ducati.dto.UserDto;
import com.quincy.sdk.MasterOrSlave;
import com.quincy.sdk.annotation.AllShardDao;
import com.quincy.sdk.annotation.ExecuteQuery;
import com.quincy.sdk.annotation.ExecuteUpdate;

@AllShardDao
public interface TestDao {
	@ExecuteQuery(sql = "SELECT id AS id_str,id,name,nickname,mobile_phone,creation_time FROM s_user;", masterOrSlave = MasterOrSlave.SLAVE, returnItemType = UserDto.class)
	public List<UserDto>[] findAllUsers();
	@ExecuteQuery(sql = "SELECT id AS id_str,id,name,nickname,mobile_phone,creation_time FROM s_user WHERE name LIKE ?;", masterOrSlave = MasterOrSlave.SLAVE, returnItemType = UserDto.class)
	public List<UserDto>[] findUsersBySurname(String keyWord);
	@ExecuteQuery(sql = "SELECT id AS id_str,id,name,nickname,mobile_phone,creation_time FROM s_user WHERE mobile_phone=?;", masterOrSlave = MasterOrSlave.SLAVE, returnItemType = UserDto.class)
	public UserDto findUser(String mobilePhone);
	@ExecuteUpdate(sql = "UPDATE s_user SET nickname=?", masterOrSlave = MasterOrSlave.MASTER)
	public int[] updateNickName(String nickName);
}