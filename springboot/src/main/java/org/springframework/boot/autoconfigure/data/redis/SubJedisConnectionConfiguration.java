package org.springframework.boot.autoconfigure.data.redis;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Priority;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import redis.clients.jedis.Jedis;

//@Priority(1)
//@Configuration(proxyBeanMethods = false)
//@ConditionalOnClass({ GenericObjectPool.class, JedisConnection.class, Jedis.class })
//@ConditionalOnMissingBean({RedisConnectionFactory.class, JedisConnectionConfiguration.class})
public class SubJedisConnectionConfiguration extends JedisConnectionConfiguration {
	@Value("#{'${spring.redis.nodes}'.split(',')}")
	private String[] _clusterNodes;
	@Value("${spring.data.redis.sentinel.master:#{null}}")
	private String sentinelMaster;
	@Autowired
	private RedisProperties properties;

	SubJedisConnectionConfiguration(RedisProperties properties,
			ObjectProvider<RedisStandaloneConfiguration> standaloneConfigurationProvider,
			ObjectProvider<RedisSentinelConfiguration> sentinelConfiguration,
			ObjectProvider<RedisClusterConfiguration> clusterConfiguration) {
		super(properties, standaloneConfigurationProvider, sentinelConfiguration, clusterConfiguration);
		System.out.println("SubJedisConnectionConfiguration=======================");
	}

//	@Bean
//	JedisConnectionFactory redisConnectionFactory(
//			ObjectProvider<JedisClientConfigurationBuilderCustomizer> builderCustomizers) {
//		System.out.println("SubJedisConnectionConfiguration=======================redisConnectionFactory");
//		load();
//		return super.redisConnectionFactory(builderCustomizers);
//	}

	private void load() {
		if(_clusterNodes.length>1) {
			List<String> nodes = Arrays.asList(_clusterNodes);
			if(sentinelMaster!=null) {//哨兵
				this.properties.getSentinel().setNodes(nodes);
			} else {//集群
				this.properties.getCluster().setNodes(nodes);
			}
		} else {//单机
			String[] ss = _clusterNodes[0].split(":");
			String redisHost = ss[0];
			int redisPort = Integer.parseInt(ss[1]);
			this.properties.setHost(redisHost);
			this.properties.setPort(redisPort);
		}
	}
}