package com.hce.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.quincy.core.db.DataSourceHolder;
import com.quincy.core.db.RoutingDataSource;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Slf4j
@Configuration
public class CoreApplicationContext {//implements TransactionManagementConfigurer {
	@Bean
    public MessageSource messageSource() throws IOException {
		Map<String, String> map = new HashMap<String, String>();
		new ClassPathHandler() {
			@Override
			protected void run(List<Resource> resources) {
				for(int i=0;i<resources.size();i++) {
					Resource resource = resources.get(i);
					String name = resource.getFilename().substring(0, resource.getFilename().indexOf("_"));
					map.put(name, "classpath:i18n/"+name);
				}
			}
		}.start("classpath*:i18n/*");
		String[] basenames = new String[map.size()];
		basenames = map.values().toArray(basenames);
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setCacheSeconds(1800);
        messageSource.setBasenames(basenames);
        return messageSource;
    }

	@Bean("propertiesFactoryBean")
	public PropertiesFactoryBean properties() throws IOException {
		List<Resource> resourceList = new ArrayList<Resource>();
		new ClassPathHandler() {
			@Override
			protected void run(List<Resource> resources) {
				resourceList.addAll(resources);
			}
		}.start("classpath*:application.properties", "classpath*:application-*.properties");
		Resource[] locations = new Resource[resourceList.size()];
		locations = resourceList.toArray(locations);
		PropertiesFactoryBean bean = new PropertiesFactoryBean();
		bean.setLocations(locations);
		bean.afterPropertiesSet();
		return bean;
	}

	private abstract class ClassPathHandler {
		protected abstract void run(List<Resource> resources);

		public void start(String... locationPatterns) throws IOException {
			PathMatchingResourcePatternResolver r = new PathMatchingResourcePatternResolver();
			List<Resource> resourceList = new ArrayList<Resource>(50);
			for(String locationPattern:locationPatterns) {
				Resource[] resources = r.getResources(locationPattern);
				for(Resource resource:resources) {
					resourceList.add(resource);
				}
			}
			this.run(resourceList);
		}
	}

	@Value("${spring.redis.host}")
	private String redisHost;
	@Value("${spring.redis.port}")
	private int redisPort;
	@Value("${spring.redis.password}")
	private String redisPwd;
	@Value("${spring.redis.timeout}")
	private int redisTimeout;
	@Value("${spring.redis.pool.max-active}")
	private int redisMaxActive;
	@Value("${spring.redis.pool.max-wait}")
	private long redisMaxWait;
	@Value("${spring.redis.pool.max-total}")
	private int redisMaxTotal;
	@Value("${spring.redis.pool.max-idle}")
	private int redisMaxIdle;
	@Value("${spring.redis.pool.min-idle}")
	private int redisMinIdle;

	@Bean
    public JedisPool jedisPool() {
		log.warn("===============Initializing Jedis================");
		GenericObjectPoolConfig<Jedis> cfg = new GenericObjectPoolConfig<Jedis>();
		cfg.setMaxTotal(redisMaxTotal);
		cfg.setMaxIdle(redisMaxIdle);
		cfg.setMinIdle(redisMinIdle);
		cfg.setMaxWaitMillis(redisMaxWait);
		JedisPool pool = new JedisPool(cfg, redisHost, redisPort, redisTimeout, redisPwd);
		log.warn("===============Initialized Jedis================");
		return pool;
	}

	@Value("${spring.datasource.driver-class-name}")
	private String driverClassName;
	@Value("${spring.datasource.url}")
	private String masterUrl;
	@Value("${spring.datasource.username}")
	private String masterUserName;
	@Value("${spring.datasource.password}")
	private String masterPassword;

	@Value("${spring.datasource.url.slave}")
	private String slaveUrl;
	@Value("${spring.datasource.username.slave}")
	private String slaveUserName;
	@Value("${spring.datasource.password.slave}")
	private String slavePassword;

	@Value("${spring.datasource.dbcp2.defaultAutoCommit}")
	private Boolean defaultAutoCommit;
	@Value("${spring.datasource.dbcp2.maxIdle}")
	private int maxIdle;
	@Value("${spring.datasource.dbcp2.minIdle}")
	private int minIdle;
	@Value("${spring.datasource.dbcp2.poolPreparedStatements}")
	private boolean poolPreparedStatements;
	@Value("${spring.datasource.dbcp2.maxOpenPreparedStatements}")
	private int maxOpenPreparedStatements;
	@Value("${spring.datasource.dbcp2.removeAbandonedTimeout}")
	private int removeAbandonedTimeout;
	@Value("${spring.datasource.dbcp2.timeBetweenEvictionRunsMillis}")
	private long timeBetweenEvictionRunsMillis;
	@Value("${spring.datasource.dbcp2.minEvictableIdleTimeMillis}")
	private long minEvictableIdleTimeMillis;
	@Value("${spring.datasource.dbcp2.testOnBorrow}")
	private boolean testOnBorrow;
	@Value("${spring.datasource.dbcp2.testWhileIdle}")
	private boolean testWhileIdle;
	@Value("${spring.datasource.dbcp2.testOnReturn}")
	private boolean testOnReturn;

	@Bean(name = "dataSource")
    public DataSource routingDataSource() {
		log.warn("===============Initializing DB================");
		BasicDataSource masterDB = new BasicDataSource();
		masterDB.setDriverClassName(driverClassName);
		masterDB.setUrl(masterUrl);
		masterDB.setUsername(masterUserName);
		masterDB.setPassword(masterPassword);
		masterDB.setDefaultAutoCommit(defaultAutoCommit);
		masterDB.setMaxIdle(maxIdle);
		masterDB.setMinIdle(minIdle);
		masterDB.setPoolPreparedStatements(poolPreparedStatements);
		masterDB.setMaxOpenPreparedStatements(maxOpenPreparedStatements);
		masterDB.setRemoveAbandonedTimeout(removeAbandonedTimeout);
		masterDB.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		masterDB.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		masterDB.setTestOnBorrow(testOnBorrow);
		masterDB.setTestWhileIdle(testWhileIdle);
		masterDB.setTestOnReturn(testOnReturn);

		BasicDataSource slaveDB = new BasicDataSource();
		slaveDB.setDriverClassName(driverClassName);
		slaveDB.setUrl(slaveUrl);
		slaveDB.setUsername(slaveUserName);
		slaveDB.setPassword(slavePassword);
		slaveDB.setDefaultAutoCommit(defaultAutoCommit);
		slaveDB.setMaxIdle(maxIdle);
		slaveDB.setMinIdle(minIdle);
		slaveDB.setPoolPreparedStatements(poolPreparedStatements);
		slaveDB.setMaxOpenPreparedStatements(maxOpenPreparedStatements);
		slaveDB.setRemoveAbandonedTimeout(removeAbandonedTimeout);
		slaveDB.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		slaveDB.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		slaveDB.setTestOnBorrow(testOnBorrow);
		slaveDB.setTestWhileIdle(testWhileIdle);
		slaveDB.setTestOnReturn(testOnReturn);

		Map<Object, Object> targetDataSources = new HashMap<Object, Object>(2);
		targetDataSources.put(DataSourceHolder.MASTER, masterDB);
		targetDataSources.put(DataSourceHolder.SLAVE, slaveDB);
		RoutingDataSource  db = new RoutingDataSource();
		db.setTargetDataSources(targetDataSources);
		db.setDefaultTargetDataSource(masterDB);
		log.warn("===============Initialized DB================");
		return db;
	}
/*
	@Bean(name = "dataSourceMaster")
    public DataSource masterDataSource() {
		BasicDataSource db = new BasicDataSource();
		db.setDriverClassName(driverClassName);
		db.setUrl(masterUrl);
		db.setUsername(masterUserName);
		db.setPassword(masterPassword);
		db.setDefaultAutoCommit(defaultAutoCommit);
		db.setMaxIdle(maxIdle);
		db.setMinIdle(minIdle);
		db.setPoolPreparedStatements(poolPreparedStatements);
		db.setMaxOpenPreparedStatements(maxOpenPreparedStatements);
		db.setRemoveAbandonedTimeout(removeAbandonedTimeout);
		db.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		db.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		db.setTestOnBorrow(testOnBorrow);
		db.setTestWhileIdle(testWhileIdle);
		db.setTestOnReturn(testOnReturn);
		return db;
	}

	@Bean(name = "dataSourceSlave")
    public DataSource slaveDataSource() {
		BasicDataSource db = new BasicDataSource();
		db.setDriverClassName(driverClassName);
		db.setUrl(masterUrl);
		db.setUsername(masterUserName);
		db.setPassword(masterPassword);
		db.setDefaultAutoCommit(defaultAutoCommit);
		db.setMaxIdle(maxIdle);
		db.setMinIdle(minIdle);
		db.setPoolPreparedStatements(poolPreparedStatements);
		db.setMaxOpenPreparedStatements(maxOpenPreparedStatements);
		db.setRemoveAbandonedTimeout(removeAbandonedTimeout);
		db.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		db.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		db.setTestOnBorrow(testOnBorrow);
		db.setTestWhileIdle(testWhileIdle);
		db.setTestOnReturn(testOnReturn);
		return db;
	}

	@Resource(name = "dataSourceMaster")
	private DataSource masterDB;
	@Resource(name = "dataSourceSlave")
	private DataSource slaveDB;

	@Bean(name = "routingDataSource")
    public DataSource routingDataSource() {
		Map<Object, Object> targetDataSources = new HashMap<Object, Object>(2);
		targetDataSources.put(DataSourceHolder.MASTER, masterDB);
		targetDataSources.put(DataSourceHolder.SLAVE, slaveDB);
		RoutingDataSource  db = new RoutingDataSource();
		db.setTargetDataSources(targetDataSources);
		db.setDefaultTargetDataSource(null);
		return db;
	}

	@Bean
	public PlatformTransactionManager txManager(@Qualifier("routingDataSource")DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Autowired
	private PlatformTransactionManager platformTransactionManager;

	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return platformTransactionManager;
	}*/
	//**************mybatis********************//
	/*@Value("${mybatis.mapper-locations}")
	private String mybatisMapperLocations;

	@Bean(name="sqlSessionFactory")//name被设置在@MapperScan属性sqlSessionFactoryRef中
	public SqlSessionFactory sessionFactory(@Qualifier("routingDataSource")DataSource dataSource) throws Exception{
		SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
		sessionFactoryBean.setDataSource(dataSource);
		sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mybatisMapperLocations));
		return sessionFactoryBean.getObject();
	}

	@Bean
	public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory")SqlSessionFactory sqlSessionFactory) throws Exception {
		SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory);
		return template;
	}*/
	//**************mybatis********************//
}
