package com.churen;

import org.springframework.context.annotation.Configuration;

@Configuration
public class QuincyRedisConfiguration {
	private QuincyRedisProperties quincyRedisProperties;

	QuincyRedisConfiguration(QuincyRedisProperties quincyRedisProperties) {
		this.quincyRedisProperties = quincyRedisProperties;
	}

	public QuincyRedisProperties getQuincyRedisProperties() {
		return quincyRedisProperties;
	}
}
