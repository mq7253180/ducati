package com.hce.ducati.service;

import java.net.UnknownHostException;
import java.util.List;

import com.hce.ducati.o.UestDto;

public interface PerformanceServiceShardingProxy {
	public int updateUest(long shardingKey, Long id);
	public int insertUest(long shardingKey);
	public List<UestDto> findUest(long shardingKey, int start, int end);
	public UestDto findUest(long shardingKey, Long id);
	public String test(long shardingKey, int a, String b) throws UnknownHostException;
}