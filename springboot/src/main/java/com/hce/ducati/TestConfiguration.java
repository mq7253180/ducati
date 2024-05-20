package com.hce.ducati;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.session.data.redis.config.annotation.SpringSessionRedisConnectionFactory;

@Configuration
public class TestConfiguration implements BeanDefinitionRegistryPostProcessor {
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

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
//		JedisConnectionFactory f = beanFactory.getBean(JedisConnectionFactory.class);
//		f.getConnection();
//		System.out.println("JedisConnectionFactory==========CLASS==========FINISHED");
//		f = (JedisConnectionFactory)beanFactory.getBean("jedisConnectionFactory");
//		f.getConnection();
//		System.out.println("JedisConnectionFactory===========NAME=========FINISHED");
	}

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		// TODO Auto-generated method stub
		
	}
}