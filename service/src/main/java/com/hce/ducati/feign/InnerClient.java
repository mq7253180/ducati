package com.hce.ducati.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hce.ducati.o.RegionResultDTO;

@FeignClient("HCE-DUCATI")
public interface InnerClient {
	@RequestMapping(method = RequestMethod.GET, value = "/region/all2")
    public RegionResultDTO getRegions();
}
