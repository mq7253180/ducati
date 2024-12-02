package com.hce.ducati.service.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hce.ducati.dao.TestDao;
import com.hce.ducati.o.UestDto;
import com.hce.ducati.service.PerformanceService;
import com.quincy.sdk.annotation.DurationLog;
import com.quincy.sdk.annotation.jdbc.ReadOnly;

@Service
public class PerformanceServiceImpl implements PerformanceService {
	@Autowired
	private TestDao testDao;

	@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	@Override
	public int updateUest(Long id) {
		UestDto uestDto = testDao.findUest(id);
		testDao.insertWest(uestDto.getCcc(), uestDto.getDdd());
		return testDao.updateUest(id);
	}

	@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	@Override
	public int insertUest() {
		return testDao.insertUest();
	}

	@ReadOnly
	@Override
	public List<UestDto> findUest(int start, int end) {
		return testDao.findUest(start, end);
	}

	@ReadOnly
	@Override
	public UestDto findUest(Long id) {
		return testDao.findUest(id);
	}

	@DurationLog
	@Override
	public String test(int a, String b) throws UnknownHostException {
		InetAddress localHost = InetAddress.getLocalHost();
		return localHost.getHostAddress();
	}
}