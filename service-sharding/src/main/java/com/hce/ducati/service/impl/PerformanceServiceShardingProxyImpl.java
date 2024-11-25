package com.hce.ducati.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hce.ducati.o.UestDto;
import com.hce.ducati.service.PerformanceServiceShardingProxy;
import com.quincy.sdk.annotation.jdbc.ReadOnly;
import com.quincy.sdk.annotation.sharding.ShardingKey;

@Service
public class PerformanceServiceShardingProxyImpl extends PerformanceServiceImpl implements PerformanceServiceShardingProxy {
	@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	@Override
	public int updateUest(@ShardingKey long shardingKey, Long id) {
		return this.updateUest(id);
	}

	@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	@Override
	public int insertUest(@ShardingKey long shardingKey) {
		return this.insertUest();
	}

	@ReadOnly
	@Override
	public List<UestDto> findUest(@ShardingKey long shardingKey, int start, int end) {
		return this.findUest(start, end);
	}

	@ReadOnly
	@Override
	public UestDto findUest(@ShardingKey long shardingKey, Long id) {
		return this.findUest(id);
	}
}