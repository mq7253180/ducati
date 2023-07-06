package com.hce.ducati.mapper;

import java.util.List;

import com.hce.ducati.dto.UserDto;
import com.quincy.sdk.MasterOrSlave;
import com.quincy.sdk.annotation.AllShardSQL;
import com.quincy.sdk.annotation.ExecuteQuery;

@AllShardSQL
public interface TestMapper {
	@ExecuteQuery(sql = "SELECT id AS id_str,id,name,mobile_phone,creation_time FROM s_user;", masterOrSlave = MasterOrSlave.SLAVE, returnItemType = UserDto.class)
	public List<UserDto> findAllUsers();
}