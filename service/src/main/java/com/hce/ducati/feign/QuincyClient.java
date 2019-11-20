package com.hce.ducati.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//@FeignClient("ddd")
@FeignClient(name="test", url="https://ducati.maqiangcgq.com")
public interface QuincyClient {
	@RequestMapping(method = RequestMethod.GET, value = "/region/all2")
    public String getRegions();
}
