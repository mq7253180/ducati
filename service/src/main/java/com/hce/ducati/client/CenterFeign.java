package com.hce.ducati.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("quincy-center")
public interface CenterFeign {
	@RequestMapping(method = RequestMethod.GET, value = "/actuator")
    public String actuator();
}