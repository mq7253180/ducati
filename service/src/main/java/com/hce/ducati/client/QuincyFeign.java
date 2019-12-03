package com.hce.ducati.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hce.ducati.o.RegionResultDTO;

@FeignClient(name="quincy", url="https://ducati.maqiangcgq.com")
public interface QuincyFeign {
	@RequestMapping(method = RequestMethod.GET, value = "/region/all2")
    public RegionResultDTO getRegions();
}
