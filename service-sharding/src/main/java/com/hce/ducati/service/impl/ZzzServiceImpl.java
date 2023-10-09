package com.hce.ducati.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hce.ducati.service.ZzzService;
import com.hce.ducati.service.ZzzzService;
import com.quincy.sdk.annotation.Synchronized;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ZzzServiceImpl implements ZzzService {
	@Autowired
	private ZzzzService zzzzService;

	@Synchronized("xxx")
	public void test() throws InterruptedException {
		Thread.sleep(5000);
		log.warn("AAA=========BEFORE");
		zzzzService.test();
		log.warn("AAA=========AFTER");
		Thread.sleep(3000);
	}
}