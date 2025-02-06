package com.hce.ducati.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public interface XxxServiceShardingProxy {
	public Object findSubTests(long shardingKey, int limit, int offset) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, SQLException, IOException, CloneNotSupportedException;
	public String classInfo();
}
