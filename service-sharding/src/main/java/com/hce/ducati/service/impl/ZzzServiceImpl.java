package com.hce.ducati.service.impl;

import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hce.ducati.service.ZzzService;
import com.hce.ducati.service.ZzzzService;
import com.quincy.sdk.DistributedLock;
import com.quincy.sdk.annotation.Synchronized;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ZzzServiceImpl implements ZzzService {
	@Autowired
	private DistributedLock distributedLock;
	@Autowired
	private ZzzzService zzzzService;

	@Synchronized("xxx")
	public void test() throws InterruptedException, UnknownHostException {
		long currentThreadId = Thread.currentThread().getId();
		Thread.sleep(5000);
		log.warn("AAA=========BEFORE====={}", currentThreadId);
		zzzzService.test2();
		log.warn("AAA=========AFTER====={}", currentThreadId);
		Thread.sleep(3000);
	}

	@Override
	public void test2() throws InterruptedException, UnknownHostException {
		try {
			distributedLock.lock("xxx");
			long currentThreadId = Thread.currentThread().getId();
			Thread.sleep(5000);
			log.warn("AAA=========BEFORE====={}", currentThreadId);
			zzzzService.test();
			log.warn("AAA=========AFTER====={}", currentThreadId);
			Thread.sleep(3000);
		} finally {
			distributedLock.unlock();
		}
	}
}