package com.hce.ducati.service.impl;

import org.springframework.stereotype.Service;

import com.hce.ducati.service.ZzzzService;
import com.quincy.sdk.annotation.Synchronized;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ZzzzServiceImpl implements ZzzzService {
	@Override
	@Synchronized("xxx")
	public void test() throws InterruptedException {
		log.warn("BBB=========BEFORE");
		Thread.sleep(8000);
		log.warn("BBB=========AFTER");
	}
}