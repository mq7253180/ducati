package com.hce.ducati.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.hce.ducati.client.DucatiClient;
//import com.quincy.sdk.entity.Region;
//import com.quincy.sdk.service.RegionService;

@Service(version = "1.0.0", interfaceClass = DucatiClient.class, timeout = 2000, retries = 3)
public class DucatiClientImpl implements DucatiClient {
	/*@Autowired
	private RegionService regionService;*/

	@Override
	public List<com.hce.ducati.client.o.Region> fineAllZones() {
		/*List<Region> list = regionService.findAll();
		List<com.hce.ducati.client.o.Region> toReturn = new ArrayList<com.hce.ducati.client.o.Region>(list.size());*/
		List<com.hce.ducati.client.o.Region> toReturn = new ArrayList<com.hce.ducati.client.o.Region>();
		return toReturn;
	}

	@Override
	public String test() {
		return "XXX";
	}
}
