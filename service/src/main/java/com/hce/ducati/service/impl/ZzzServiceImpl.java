package com.hce.ducati.service.impl;

import java.io.IOException;
import java.lang.reflect.Method;

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
	@Override
	public void callHttp(int i, int[] ii, Params[] ps) {
		log.info("==============CALL_HTTP");
	}

	@AtomicOperational(confirm = "confirmUpdateDB")
	@Override
	public void updateDB(String s, Params p) {
		log.info("==============UPDATE_DB");
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
		log.info("CONFIRM_UPDATE_DB=============={}--------{}", s, p.getB());
		Company c= companyRepository.findById(1l).get();
		c.setCnName(c.getCnName()+"-"+p.getA()+"-"+p.getB());
		companyRepository.save(c);
	}
}