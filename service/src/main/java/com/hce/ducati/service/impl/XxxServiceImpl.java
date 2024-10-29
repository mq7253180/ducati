package com.hce.ducati.service.impl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

//import org.apache.dubbo.config.annotation.DubboReference;
//import org.apache.zookeeper.CreateMode;
//import org.apache.zookeeper.KeeperException;
//import org.apache.zookeeper.ZooDefs;
//import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

//import com.hce.ducati.client.DucatiClient;
import com.hce.ducati.client.InnerFeign;
import com.hce.ducati.dao.TestDao;
import com.hce.ducati.dao.ZelationRepository;
import com.hce.ducati.entity.Enterprise;
import com.hce.ducati.entity.Zelation;
import com.hce.ducati.mapper.EnterpriseMapper;
import com.hce.ducati.mapper.TestMapper;
import com.hce.ducati.o.Params;
import com.hce.ducati.o.SubTestDto;
import com.hce.ducati.o.SubTestDynamicFieldsDto;
import com.hce.ducati.service.XxxService;
import com.hce.ducati.service.ZzzService;
import com.hce.ducati.service.ZzzzService;
//import com.quincy.sdk.annotation.ZooKeeperInjector;
import com.quincy.sdk.annotation.transaction.DTransactional;
import com.quincy.sdk.DynamicField;
import com.quincy.sdk.JdbcDao;
import com.quincy.sdk.annotation.L2Cache;
//import com.quincy.sdk.ZKContext;
//import com.quincy.sdk.annotation.ZkSynchronized;
import com.quincy.sdk.annotation.DurationLog;
import com.quincy.sdk.annotation.JedisSupport;
import com.quincy.sdk.annotation.Cache;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.JedisCluster;

@Slf4j
@Service
public class XxxServiceImpl implements XxxService {
//	@Autowired
//	private ZKContext zkContext;
	@Value("${spring.application.name}")
	private String appName;
	@Autowired
	private InnerFeign innerFeign;
//	@DubboReference(version = "1.0.0")
//	private DucatiClient ducatiClient;
	@Autowired
	private TestMapper testMapper;
	@Autowired
	private TestDao testDao;

//	@DurationLog
//	@Cache(expire = 30)
//	@Synchronized("xxx")
//	@DeprecatedSynchronized("xxx")
//	@ZooKeeperInjector
//	@Override
//	public String testZk(String arg, ZooKeeper zk, long duration) throws KeeperException, InterruptedException {
//		String result = zk.create("/qqq", arg.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
//		log.info("==============={}", result);
//		List<String> list = zk.getChildren(zkContext.getSynPath(), false);
//		for(String path:list) {
//			log.info("---------------{}", path);
//		}
//		Thread.sleep(duration);
////		zk.delete("/quincy-ducati/synchronization/xxx/execution1", -1);
//		return "sss";
//	}

	@Autowired
	private ZzzService zzzService;
	@Autowired
	private ZzzzService zzzzService;

	@Override
	public String testTx0(String s, Params p) {
		return this.testTx(s, p);
	}

	@Override
	public String classInfo() {
		return new StringBuilder(1000)
				.append(this.classInfo(zzzService, "WITH_AOP"))
				.append("\r\n")
				.append(this.classInfo(zzzzService, "NO_AOP_AOP"))
				.append("\r\n")
				.append(this.classInfo(innerFeign, "SPRINGCLOUD_FEIGN"))
//				.append("\r\n")
//				.append(this.classInfo(ducatiClient, "DUBBO_CLIENT"))
				.toString();
	}

	private final static String CLASS_INFO = "====================Name: %s---CanonicalName: %s---SimpleName: %s---TypeName: %s";

	private String classInfo(Object obj, String flag) {
		Class<?> clazz = obj.getClass();
		Class<?> superClass = clazz.getSuperclass();
		StringBuilder sb = new StringBuilder(300)
				.append("%s_THIS")
				.append(CLASS_INFO)
				.append("\r\n%s_SUPER")
				.append(CLASS_INFO);
		return String.format(sb.toString(), flag, clazz.getName(), clazz.getCanonicalName(), clazz.getSimpleName(), clazz.getTypeName(), flag, superClass.getName(), superClass.getCanonicalName(), superClass.getSimpleName(), superClass.getTypeName());
	}

	public static void main(String[] args) {
		System.out.println(String.format(CLASS_INFO, "xxx", "www", "xxx", "www"));
	}

//	@DTransactional(flagForCronJob = "xxx", executor = "ducatiThreadPoolExecutor")
	@DTransactional(flagForCronJob = "xxx")
	@Override
	public String testTx(String s, Params p) {
		Params[] pp = new Params[5];
		pp[0] = p;
		pp[2] = p;
		pp[4] = p;
		zzzService.callDubbo();
		zzzService.callDubbo(987l, null);
		zzzService.updateDB(s, p);
		zzzService.callHttp(321, new int[] {1, 5, 8}, pp);
		return "XXX";
	}

	@JedisSupport
	@Override
	public void testRedisCluster(String arg0, JedisCluster jedis, String arg1, JedisCluster jedis2, String arg2) {
		String key = "kkk";
		String value = "vvv";
		jedis.set(key, value);
		jedis.expire(key, 3);
		log.info("testRedisCluster==================={}", jedis2.get(key));
	}

//	@ZkSynchronized(value = "ttt")
//	@Override
//	public void testDeprecatedSynchronized(long millis) throws InterruptedException {
//		Thread.sleep(millis);
//	}

	@Autowired
	private EnterpriseMapper enterpriseMapper;

	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	@Override
	public int update(Long id, String mobilePhone) {
		int effected = enterpriseMapper.update(id, mobilePhone);
		log.info("==============NOT_COMMITED");
		return effected;
	}

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	@Override
	public List<Enterprise> select(Long id) {
		Enterprise enterprise = enterpriseMapper.findOne(id);
		log.info("===================={}", enterprise.getMobilePhone());
		List<Enterprise> list = enterpriseMapper.findAll();
		for(Enterprise e:list)
			log.info("---------------{}", e.getMobilePhone());
		return list;
	}

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	@Override
	public int updateIndividualBatch(Long companyId, String region, long delay) throws InterruptedException {
		int effected = enterpriseMapper.updateIndividualBatch(companyId, region);
		Thread.sleep(delay);
		return effected;
	}

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	@Override
	public int updateIndividualOne(Long id1, Long id2, String region, long delay) throws InterruptedException {
		enterpriseMapper.updateIndividualOne(id1, region);
		Thread.sleep(delay);
		enterpriseMapper.updateIndividualOne(id2, region);
		Thread.sleep(delay);
		return 1;
	}

	@Autowired
	private ZelationRepository zelationRepository;

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	@Override
	public Zelation saveZelation() {
		Zelation z = new Zelation();
		z.setTestId(4);
		z.setUestId(6);
		z.setGgg(92);
		z = zelationRepository.save(z);
		System.out.println("ID=============="+z.getId());
		return z;
	}

//	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	@Override
	public void test1() {
		testMapper.updateTest(1l, "xxx");
//		test2();
		zzzzService.test2();
//		throw new RuntimeException("asfsf");
	}

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
	@Override
	public void test2() {
		testMapper.updateTest(2l, "zzz");
		throw new RuntimeException("zxcsfsd");
	}

	@Override
	public void testUpdation() {
		testDao.testUpateRecord3("www", "xxx", 3, 21);
	}

	@Override
	public void testUpdation2() {
		testDao.testUpateRecord2("www", 21);
	}

	@Override
	public void testUpdation3() {
		testDao.testUpateRecord(78, "www", 21);
	}

	@Override
	public void testUpdation4() {
		Date date = new Date();
		testDao.testUpateRecord4("www", 6, "xxx", -1.23f, date, date, date, 21);
	}

	@Autowired
	private JdbcDao jdbcDao;

	@DurationLog
	@Cache(expire = 60, retries = 10, millisBetweenRetries = 200)
	@L2Cache(expire = 90)
	@Override
	public Object findSubTests(int limit, int offset) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, SQLException, IOException, CloneNotSupportedException {
		try {
			Thread.sleep(700);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return jdbcDao.executeQueryWithDynamicColumns("SELECT s.id,s.eee,s.fff,f.id,f.name,f.sort,v.value_decimal,v.value_str FROM (SELECT * FROM sub_test LIMIT "+limit+" OFFSET "+offset+")", "sub_test", SubTestDto.class, SubTestDynamicFieldsDto.class);
	}

	@Override
	public List<SubTestDto> findSubTest2(int limit, int offset) {
		return testDao.findSubTest();
	}

	@Override
	public Object findOneSubTest(String id) {
		return testDao.findOneSubTest(id);
	}

	@Override
	public SubTestDto findOneSubTest2(String id) {
		return testDao.findOneSubTest2(id);
	}

	@Override
	public List<DynamicField> findSubTestDynamicFields() {
		return testDao.findSubTestDynamicFields();
	}
}