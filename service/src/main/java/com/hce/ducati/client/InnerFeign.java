package com.hce.ducati.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hce.ducati.o.RegionResultDTO;

@FeignClient("${spring.application.name}")
public interface InnerFeign {
	@RequestMapping(method = RequestMethod.GET, value = "/region/all2")
    public RegionResultDTO getRegions();
}