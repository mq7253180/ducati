package com.hce.ducati;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource(value = {"classpath:application-sharding.properties"})
@Configuration
public class DucatiShardingConfiguration {

}
