package com.hce.ducati.mapper;

import java.util.List;

import com.quincy.sdk.MasterOrSlave;
import com.quincy.sdk.annotation.AllShardSQL;
import com.quincy.sdk.annotation.ExecuteQuery;

@AllShardSQL
public interface TestMapper {
	@ExecuteQuery(sql = "SELECT * FROM s_user;", masterOrSlave = MasterOrSlave.SLAVE)
	public List<?> findAllUsers();
//	@SelectSQL("SELECT * FROM b_order;")
//	public List<?> findAllOrders();
}