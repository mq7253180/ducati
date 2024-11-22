package com.hce.ducati.service;

public interface PerformanceServiceShardingProxy {
	public int updateUest(long shardingKey, Long id);
	public int insertUest(long shardingKey);
}