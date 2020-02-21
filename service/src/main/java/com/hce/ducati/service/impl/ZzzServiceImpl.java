package com.hce.ducati.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hce.ducati.dao.CompanyRepository;
import com.hce.ducati.entity.Company;
import com.hce.ducati.o.Params;
import com.hce.ducati.service.ZzzService;
import com.quincy.sdk.annotation.transaction.AtomicOperational;
import com.quincy.sdk.helper.HttpClientHelper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ZzzServiceImpl implements ZzzService {
	@AtomicOperational(confirm = "confirmCallHttp")
//	@AtomicOperational(confirm = "confirmCallHttp", cancel = "cancelCallHttp")
	@Override
	public void callHttp(int i, int[] ii, Params[] ps) {
		log.info("==============CALL_HTTP");
	}

	@AtomicOperational(confirm = "confirmUpdateDB")
//	@AtomicOperational(confirm = "confirmUpdateDB", cancel = "cancelUpdateDB")
	@Override
	public void updateDB(String s, Params p) {
		log.info("==============UPDATE_DB");
	}

	@AtomicOperational(confirm = "confirmCallDubbo")
//	@AtomicOperational(confirm = "confirmCallDubbo", cancel = "cancelCallDubbo")
	@Override
	public void callDubbo(Long id, String val) {
		log.info("==============CALL_DUBBO");
	}

	@AtomicOperational(confirm = "confirmCallDubbo")
	@Override
	public void callDubbo() {
		log.info("==============CALL_DUBBO");
	}

	@Autowired
	private CompanyRepository companyRepository;

	public void confirmCallHttp(int _i, int[] ii, Params[] ps) throws IOException {
		log.info("CONFIRM_CALL_HTTP=============={}", _i);
		for(int i:ii)
			log.info("CONFIRM_CALL_HTTP--------------{}", i);
		for(Params p:ps) {
			if(p!=null)
				log.info("CONFIRM_CALL_HTTP--------------{}---{}", p.getA(), p.getB());
		}
		String html = HttpClientHelper.get("http://jlcedu.maqiangcgq.com", null);
		log.info(html);
	}

	public void confirmUpdateDB(String s, Params p) {
		/*if(true)
			throw new RuntimeException("测试提交失败");*/
		log.info("CONFIRM_UPDATE_DB=============={}--------{}", s, p.getB());
		Company c= companyRepository.findById(1l).get();
		c.setCnName(c.getCnName()+"-"+p.getA()+"-"+p.getB());
		companyRepository.save(c);
	}

	public void confirmCallDubbo(Long id, String val) {
		/*if(true)
			throw new RuntimeException("测试提交失败");*/
		log.info("==============CONFIRM_CALL_DUBBO");
	}

	public void confirmCallDubbo() {
		/*if(true)
			throw new RuntimeException("测试提交失败wwwww");*/
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
		Company c= companyRepository.findById(1l).get();
		c.setCnName(c.getCnName().substring(0, 2));
		companyRepository.save(c);
	}

	public void cancelCallDubbo(Long id, String val) {
		log.info("==============CONFIRM_CALL_DUBBO");
		/*if(true)
			throw new RuntimeException("测试撤消失败");*/
	}
}