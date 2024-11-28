package com.hce.ducati.service.impl;

import java.net.UnknownHostException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.hce.ducati.o.UestDto;
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

	@Override
	public List<UestDto> findUest(int start, int end) {
		return performanceServiceShardingProxy.findUest(0, start, end);
	}

	@Override
	public UestDto findUest(Long id) {
		return performanceServiceShardingProxy.findUest(0, id);
	}

	@Override
	public String test(int a, String b) throws UnknownHostException {
		return performanceServiceShardingProxy.test(0, a, b);
	}
}