package com.hce.ducati.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import com.hce.ducati.client.DucatiClient;
import com.quincy.sdk.entity.Region;
import com.quincy.sdk.service.RegionService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@DubboService(version = "1.0.0", interfaceClass = DucatiClient.class, timeout = 2000, retries = 3)
public class DucatiClientImpl implements DucatiClient {
	@Autowired
	private RegionService regionService;

	@Override
	public List<com.hce.ducati.client.o.Region> fineAllZones() {
		List<Region> list = regionService.findAll();
		List<com.hce.ducati.client.o.Region> toReturn = new ArrayList<com.hce.ducati.client.o.Region>(list.size());
		for(Region r:list) {
			com.hce.ducati.client.o.Region tr = new com.hce.ducati.client.o.Region();
			tr.setId(r.getId());
			tr.setCnName(r.getCnName());
			tr.setEnName(r.getEnName());
			tr.setCode(r.getCode());
			tr.setCode2(r.getCode2());
			tr.setTelPrefix(r.getTelPrefix());
			tr.setCurrency(r.getCurrency());
			tr.setLocale(r.getLocale());
			toReturn.add(tr);
		}
//		List<com.hce.ducati.client.o.Region> toReturn = new ArrayList<com.hce.ducati.client.o.Region>();
		log.info("=======================FIND_ALL_REGIONS_BY_DUBBO");
		return toReturn;
	}

	@Override
	public String test() {
		return "XXX";
	}
}
