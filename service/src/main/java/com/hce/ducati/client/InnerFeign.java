package com.hce.ducati.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("${spring.application.name}")
public interface InnerFeign {
//	@RequestMapping(method = RequestMethod.GET, value = "/region/all2")
//    public RegionResultDTO getRegions();
}