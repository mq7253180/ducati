package com.hce.ducati.service.impl;

import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hce.ducati.service.ZzzzService;
import com.quincy.sdk.DistributedLock;
import com.quincy.sdk.annotation.Synchronized;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ZzzzServiceImpl implements ZzzzService {
	@Autowired
	private DistributedLock distributedLock;

	@Override
	@Synchronized("xxx")
	public void test() throws InterruptedException {
		long currentThreadId = Thread.currentThread().getId();
		log.warn("BBB=========BEFORE====={}", currentThreadId);
		Thread.sleep(8000);
		log.warn("BBB=========AFTER====={}", currentThreadId);
	}

	@Override
	public void test2() throws InterruptedException, UnknownHostException {
		try {
			distributedLock.lock("xxx");
			this.test();
		} finally {
			distributedLock.unlock();
		}
	}
}