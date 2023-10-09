package com.hce.ducati;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.quincy.sdk.WebMvcConfiguration;

@PropertySource(value = {"classpath:application-core.properties", "classpath:application-sensitiveness.properties"})
@Configuration("sssiiiccc")
public class ShardingServiceInitConfiguration extends WebMvcConfiguration {
	
}