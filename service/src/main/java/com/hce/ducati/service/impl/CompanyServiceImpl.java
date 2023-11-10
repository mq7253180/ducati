package com.hce.ducati.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hce.ducati.dao.CompanyRepository;
import com.hce.ducati.entity.Company;
import com.hce.ducati.service.CompanyService;
import com.quincy.sdk.annotation.ReadOnly;

@Service
public class CompanyServiceImpl implements CompanyService {
	@Autowired
	private CompanyRepository companyRepository;
	/*@Autowired
	private CFCAService cfcaService;*/
	@Value("${location.accounts}")
	private String accountsLocation;
	@Value("${env}")
	private String env;

	private final static Integer STATUS_ADD = 1;
	private final static Integer STATUS_NORMAL = STATUS_ADD+1;
	private final static Integer STATUS_FOCUS = STATUS_NORMAL+1;

	public final static Map<String, Object> MAPPING = new HashMap<String, Object>(12);
	static {
		MAPPING.put("01", "0");
		MAPPING.put("02", "1");
		MAPPING.put("10", "Z");
		MAPPING.put("11", "8");
		MAPPING.put("13", "Z");
		MAPPING.put("20", "Z");
		MAPPING.put("逾期", -1);
		MAPPING.put("冻结", 0);
		MAPPING.put("新增", STATUS_ADD);
		MAPPING.put("正常", STATUS_NORMAL);
		MAPPING.put("关注", STATUS_FOCUS);
	}

	@ReadOnly
	@Override
	public List<Company> findAll() {
		return companyRepository.findAll();
	}

	@ReadOnly
	@Override
	public Company find(Long id) {
		return companyRepository.getOne(id);
	}
}
