package com.hce.ducati;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.SpringSessionRedisConnectionFactory;

@Configuration
public class TestConfiguration {
	@SpringSessionRedisConnectionFactory
	@Bean
	public Test test1() {
		Test t = new Test();
		t.setValue("aaaaaaa");
		return t;
	}

	@Bean
	public Test test2() {
		Test t = new Test();
		t.setValue("bbbbbbb");
		return t;
	}
}