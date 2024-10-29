package com.hce.ducati.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import com.quincy.sdk.annotation.sharding.ShardingKey;

public interface XxxServiceShardingProxy {
	public Object findSubTests(@ShardingKey long shardingKey, int limit, int offset) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, SQLException, IOException, CloneNotSupportedException;
}
