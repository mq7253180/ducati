package com.churen;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties.Sentinel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisIndexedHttpSession;
import org.springframework.util.ClassUtils;

//import jakarta.annotation.PostConstruct;
//import jakarta.annotation.Priority;

//@Priority(1)
@Order(1)
//@Configuration
class AestConfiguration {//implements BeanDefinitionRegistryPostProcessor {
	@Autowired
	private QuincyRedisProperties quincyRedisProperties;
	@Autowired
	private RedisProperties redisProperties;

	AestConfiguration(RedisProperties redisProperties, QuincyRedisProperties quincyRedisProperties) {
		this.redisProperties = redisProperties;
		this.quincyRedisProperties = quincyRedisProperties;
	}

	@Autowired
	private JedisConnectionFactory f;
	@Autowired
	private ConfigurableListableBeanFactory beanFactory;

//	@PostConstruct
	public void init() {
		System.out.println("beanFactory========="+beanFactory);
		QuincyRedisProperties qp = beanFactory.getBean(QuincyRedisProperties.class);
		JedisConnectionFactory f = beanFactory.getBean(JedisConnectionFactory.class);
		RedisConnection conn = f.getConnection();
	}

//	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
//		JedisConnectionFactory f = beanFactory.getBean(JedisConnectionFactory.class);
//		f.getConnection();
		RedisProperties p = beanFactory.getBean(RedisProperties.class);
		QuincyRedisProperties qp = beanFactory.getBean(QuincyRedisProperties.class);
		Sentinel s = p.getSentinel();
		if(s==null) {
			s = new Sentinel();
			p.setSentinel(s);
		}
		s.setNodes(Arrays.asList(new String[] {"47.93.89.0:26379", "47.93.89.0:26389", "47.93.89.0:26399"}));
//		this.load(p);
		System.out.println("JedisConnectionFactory==========CLASS==========FINISHED=========="+p);
//		f = (JedisConnectionFactory)beanFactory.getBean("jedisConnectionFactory");
//		f.getConnection();
		System.out.println("JedisConnectionFactory===========NAME=========FINISHED");
	}

	@Value("#{'${spring.redis.nodes}'.split(',')}")
	private String[] _clusterNodes;
	@Value("${spring.data.redis.sentinel.master:#{null}}")
	private String sentinelMaster;

	private void load(RedisProperties properties) {
		String _nodes = quincyRedisProperties.getNodes();
		if(_clusterNodes.length>1) {
			List<String> nodes = Arrays.asList(_clusterNodes);
			if(sentinelMaster!=null) {//哨兵
				properties.getSentinel().setNodes(nodes);
			} else {//集群
				properties.getCluster().setNodes(nodes);
			}
		} else {//单机
			String[] ss = _clusterNodes[0].split(":");
			String redisHost = ss[0];
			int redisPort = Integer.parseInt(ss[1]);
			properties.setHost(redisHost);
			properties.setPort(redisPort);
		}
	}

//	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		// TODO Auto-generated method stub
		
	}

	public void huti() {
//		ImportRegistry i;
//		ImportRegistry ir = this.beanFactory.getBean(ConfigurationClassPostProcessor.class.getName() + ".importRegistry", ImportRegistry.class);
//		AnnotationMetadata importingClass = ir.getImportingClassFor(ClassUtils.getUserClass(bean).getName());
//		Map<String, Object> attributeMap = importingClass
//				.getAnnotationAttributes(EnableRedisIndexedHttpSession.class.getName());
//		AnnotationAttributes attributes = AnnotationAttributes.fromMap(attributeMap);
//		String redisNamespaceValue = attributes.getString("redisNamespace");
	}
}