package com.hce.ducati;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:application-service.properties")
@Configuration
public class ServiceInitConfiguration {
	@PostConstruct
	public void init() {
		
	}

	@PreDestroy
	private void destroy() {
		
	}
}
