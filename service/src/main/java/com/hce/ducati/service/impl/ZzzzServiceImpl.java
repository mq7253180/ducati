package com.hce.ducati.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hce.ducati.dao.TestDao;
import com.hce.ducati.mapper.TestMapper;
import com.hce.ducati.o.Params;
import com.hce.ducati.o.UserDto;
import com.hce.ducati.service.ZzzzService;
import com.quincy.sdk.annotation.Synchronized;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ZzzzServiceImpl implements ZzzzService {
	@Autowired
	private TestDao testDao;
	@Autowired
	private TestMapper testMapper;

	@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	@Override
	public UserDto testTxQuery(Long id) {
		return testDao.find(id);
	}

	@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	@Override
	public int testTxUpdate(Long id, String enName) {
		int effected =  testMapper.updateRegion(id, enName);
		if(true)
			throw new RuntimeException("xxx");
		return effected;
	}

	@Override
	public void callHttp(int i, int[] ii, Params[] ps) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateDB(String s, Params p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void callDubbo(Long id, String val) {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Synchronized("xxx")
	public void test() throws InterruptedException {
		log.warn("BBB=========BEFORE");
		Thread.sleep(8000);
		log.warn("BBB=========AFTER");
	}

	public void testUpdate() {
		
	}

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
	@Override
	public void test2() {
		testMapper.updateTest(2l, "www");
		throw new RuntimeException("zxcsfsd");
	}
}