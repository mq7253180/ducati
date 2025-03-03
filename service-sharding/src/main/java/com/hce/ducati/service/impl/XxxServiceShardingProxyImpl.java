package com.hce.ducati.service.impl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import org.springframework.stereotype.Service;

import com.hce.ducati.service.XxxServiceShardingProxy;
import com.quincy.sdk.annotation.jdbc.ReadOnly;
import com.quincy.sdk.annotation.sharding.ShardingKey;

@Service
public class XxxServiceShardingProxyImpl extends XxxServiceImpl implements XxxServiceShardingProxy {
	@Override
	@ReadOnly
	public Object findSubTests(@ShardingKey long shardingKey, int limit, int offset)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, SQLException, IOException, CloneNotSupportedException {
		return this.findSubTests(limit, offset);
	}
}
