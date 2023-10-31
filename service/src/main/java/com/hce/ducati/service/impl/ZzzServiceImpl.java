package com.hce.ducati.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hce.ducati.dao.CompanyRepository;
import com.hce.ducati.dao.TestDao;
import com.hce.ducati.mapper.TestMapper;
import com.hce.ducati.o.Params;
import com.hce.ducati.o.RegionDto;
import com.hce.ducati.o.UserDto;
import com.hce.ducati.service.ZzzService;
import com.hce.ducati.service.ZzzzService;
import com.quincy.sdk.annotation.Synchronized;
import com.quincy.sdk.annotation.transaction.AtomicOperational;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ZzzServiceImpl implements ZzzService {
	@Autowired
	private TestDao testDao;
	@Autowired
	private TestMapper testMapper;
	@Autowired
	private ZzzzService zzzzService;

	@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	@Override
	public void testTxUpdate() {
		testDao.upate("13800138000", 1l);
		zzzzService.testTxUpdate(1l, "anihC");
	}

	@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	@Override
	public void testTxQuery() {
		RegionDto regionDto = testMapper.findRegion(1l);
		log.warn("EN_NAME======================{}", regionDto.getEnName());
		UserDto userDto = zzzzService.testTxQuery(1l);
		log.warn("MOBILE_PHONE======================{}", userDto.getMobilePhone());
	}

//	@AtomicOperational(confirm = "confirmCallHttp")
	@AtomicOperational(confirm = "confirmCallHttp", cancel = "cancelCallHttp")
	@Override
	public void callHttp(int i, int[] ii, Params[] ps) {
		log.info("==============CALL_HTTP");
	}

//	@AtomicOperational(confirm = "confirmUpdateDB")
	@AtomicOperational(confirm = "confirmUpdateDB", cancel = "cancelUpdateDB")
	@Override
	public void updateDB(String s, Params p) {
		log.info("==============UPDATE_DB");
	}

//	@AtomicOperational(confirm = "confirmCallDubbo")
	@AtomicOperational(confirm = "confirmCallDubbo", cancel = "cancelCallDubbo")
	@Override
	public void callDubbo(Long id, String val) {
		try {
			Thread.sleep(600);
		} catch (InterruptedException e) {
			log.error("TX_TEST_SLEEP================", e);
		}
		log.info("==============CALL_DUBBO");
	}

//	@AtomicOperational(confirm = "confirmCallDubbo")
	@AtomicOperational(confirm = "confirmCallDubbo", cancel = "cancelCallDubbo")
	@Override
	public void callDubbo() {
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			log.error("TX_TEST_SLEEP================", e);
		}
		log.info("==============CALL_DUBBO");
	}

	@Autowired
	private CompanyRepository companyRepository;

	public void confirmCallHttp(int _i, int[] ii, Params[] ps) throws IOException {
		try {
			Thread.sleep(600);
		} catch (InterruptedException e) {
			log.error("TX_TEST_SLEEP================", e);
		}
		log.info("CONFIRM_CALL_HTTP=============={}", _i);
		for(int i:ii)
			log.info("CONFIRM_CALL_HTTP--------------{}", i);
		for(Params p:ps) {
			if(p!=null)
				log.info("CONFIRM_CALL_HTTP--------------{}---{}", p.getA(), p.getB());
		}
//		String html = HttpClientHelper.get("http://jlcedu.maqiangcgq.com", null);
//		log.info(html);
	}

	public void confirmUpdateDB(String s, Params p) {
		try {
			Thread.sleep(600);
		} catch (InterruptedException e) {
			log.error("TX_TEST_SLEEP================", e);
		}
		log.info("CONFIRM_UPDATE_DB=============={}--------{}", s, p.getB());
//		if(true)
//			throw new RuntimeException("测试提交失败");
		/*Company c= companyRepository.findById(1l).get();
		c.setCnName(c.getCnName()+"-"+p.getA()+"-"+p.getB());
		companyRepository.save(c);*/
	}

	public void confirmCallDubbo(Long id, String val) {
		try {
			Thread.sleep(600);
		} catch (InterruptedException e) {
			log.error("TX_TEST_SLEEP================", e);
		}
		/*if(true)
			throw new RuntimeException("测试提交失败");*/
		log.info("==============CONFIRM_CALL_DUBBO");
	}

	public void confirmCallDubbo() {
		try {
			Thread.sleep(600);
		} catch (InterruptedException e) {
			log.error("TX_TEST_SLEEP================", e);
		}
//		if(true)
//			throw new RuntimeException("测试提交失败wwwww");
		log.info("==============CONFIRM_CALL_DUBBO");
	}

	public void cancelCallHttp(int _i, int[] ii, Params[] ps) {
		log.info("CANCEL_CALL_HTTP=============={}", _i);
		for(int i:ii)
			log.info("CANCEL_CALL_HTTP--------------{}", i);
		for(Params p:ps) {
			if(p!=null)
				log.info("CANCEL_CALL_HTTP--------------{}---{}", p.getA(), p.getB());
		}
	}

	public void cancelUpdateDB(String s, Params p) {
		/*if(true)
			throw new RuntimeException("测试撤消失败");*/
		log.info("CANCEL_UPDATE_DB=============={}--------{}", s, p.getB());
		/*Company c= companyRepository.findById(1l).get();
		c.setCnName(c.getCnName().substring(0, 2));
		companyRepository.save(c);*/
	}

	public void cancelCallDubbo(Long id, String val) {
		log.info("==============CANCEL_CALL_DUBBO_WITH_PARAMS");
//		if(true)
//			throw new RuntimeException("测试撤消失败");
	}

	public void cancelCallDubbo() {
		log.info("==============CANCEL_CALL_DUBBO");
		if(true)
			throw new RuntimeException("测试撤消失败");
	}

	@Synchronized("xxx")
	public void test() throws InterruptedException {
		Thread.sleep(5000);
		log.warn("AAA=========BEFORE");
		zzzzService.test();
		log.warn("AAA=========AFTER");
		Thread.sleep(3000);
	}
}