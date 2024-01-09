package com.hce.ducati.service.impl;

import java.net.UnknownHostException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hce.ducati.service.ZzzService;
import com.hce.ducati.service.ZzzzService;
import com.quincy.sdk.DistributedLock;
import com.quincy.sdk.annotation.Cache;
import com.quincy.sdk.annotation.JedisSupport;
import com.quincy.sdk.annotation.Synchronized;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

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

	@Cache(expire = 20, notExistRetries = 20)
	@Override
	public String chaxun(String s, int i) throws InterruptedException {
		Thread.sleep(5000);
		return ""+System.currentTimeMillis();
	}

	@JedisSupport
	@Override
	public Set<String> allKeys(Jedis jedis) {
		return jedis.keys("*");
	}
}