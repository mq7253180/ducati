package org.springframework.boot.autoconfigure.data.redis;

import org.springframework.context.annotation.Configuration;

import com.hce.ducati.QuincyRedisProperties;

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
