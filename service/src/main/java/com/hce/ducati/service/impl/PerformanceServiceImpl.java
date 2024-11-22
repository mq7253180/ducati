package com.hce.ducati.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hce.ducati.dao.TestDao;
import com.hce.ducati.service.PerformanceService;

@Service
public class PerformanceServiceImpl implements PerformanceService {
	@Autowired
	private TestDao testDao;

	@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	@Override
	public int updateUest(Long id) {
		return testDao.updateUest(id);
	}

	@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	@Override
	public int insertUest() {
		return testDao.insertUest();
	}
}