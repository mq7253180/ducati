package com.hce.ducati.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.hce.ducati.service.PerformanceService;
import com.hce.ducati.service.PerformanceServiceShardingProxy;

@Primary
@Service
public class PerformanceServiceShardingImpl implements PerformanceService {
	@Autowired
	private PerformanceServiceShardingProxy performanceServiceShardingProxy;

	@Override
	public int updateUest(Long id) {
		return performanceServiceShardingProxy.updateUest(0, id);
	}

	@Override
	public int insertUest() {
		return performanceServiceShardingProxy.insertUest(0);
	}
}