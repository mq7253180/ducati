package com.hce.ducati.controller;

import java.math.BigDecimal;
import java.util.List;

import org.apache.dubbo.config.annotation.Reference;
import org.apache.zookeeper.KeeperException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hce.ducati.client.InnerFeign;
import com.hce.ducati.client.QuincyFeign;
import com.hce.ducati.mapper.RegionMapper;
import com.hce.ducati.client.DucatiClient;
import com.hce.ducati.client.DucatiSpringCloudClient;
import com.hce.ducati.o.AccountO;
import com.hce.ducati.o.Params;
import com.hce.ducati.o.RegionResultDTO;
import com.hce.ducati.service.XxxService;
import com.hce.ducati.service.ZzzService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.quincy.sdk.annotation.Cache;
import com.quincy.sdk.annotation.JedisInjector;
import com.quincy.sdk.annotation.transaction.DistributedTransactional;
import com.quincy.sdk.entity.Region;
import com.quincy.sdk.service.RegionService;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

@Slf4j
@RefreshScope
@Controller
@RequestMapping("/xxx")
public class XxxController {
	@Autowired
	private QuincyFeign quincyFeign;
	@Autowired
	private InnerFeign innerFeign;
	@Autowired
	private DucatiSpringCloudClient ducatiSpringCloudClient;

	@GetMapping("/regions/quincy")
	@ResponseBody
	public List<Region> finaRegionsByQuincy() {
		RegionResultDTO dto = quincyFeign.getRegions();
		return dto.getData();
	}

	@GetMapping("/regions/inner")
	@ResponseBody
	public List<Region> finaRegionsByInner() {
		RegionResultDTO dto = innerFeign.getRegions();
		return dto.getData();
	}

	@HystrixCommand(fallbackMethod = "hystrixFailure", commandProperties = {
//			@HystrixProperty(name="execution.isolation.strategy", value="SEMAPHORE"), 
			@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="2000"), 
			@HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value="60")
	})
	@GetMapping("/hystrix/normal")
	@ResponseBody
	public String hystrixNormal() throws InterruptedException {
		/*if(true)
			throw new RuntimeException("wwwww");*/
		Thread.sleep(1500);
		return "NORMAL";
	}

	public String hystrixFailure() {
		return "FAILURE";
	}

	@Value("${ducati.host}")
	private String host;

	@GetMapping("/host")
	@ResponseBody
	public String host() {
		return host;
	}

	@GetMapping("/stream/output/{id}/{amount}")
	@ResponseBody
	public String streamSource(@PathVariable(required = true, name = "id")String id, @PathVariable(required = true, name = "amount")BigDecimal amount) {
		AccountO o = new AccountO();
		o.setId(id);
		o.setAmount(amount);
		String r = ducatiSpringCloudClient.output(o);
		return r;
	}

	@GetMapping("/stream/sendto/{id}/{amount}")
	@ResponseBody
	public AccountO streamProcess(@PathVariable(required = true, name = "id")String id, @PathVariable(required = true, name = "amount")BigDecimal amount) {
		AccountO o = new AccountO();
		o.setId(id);
		o.setAmount(amount);
		return ducatiSpringCloudClient.sendTo(o);
	}

	@GetMapping("/regions")
	@ResponseBody
	public List<Region> findRegions() {
		return xxxService.findRegions();
	}

	@Reference(version = "1.0.0")
	private DucatiClient ducatiClient;

	@GetMapping("/regions/dubbo")
	@ResponseBody
	public List<com.hce.ducati.client.o.Region> findRegionsViaDubbo() {
		return ducatiClient.fineAllZones();
	}

	@Autowired
	private XxxService xxxService;

	@GetMapping("/zk/{arg}/{duration}")
	@ResponseBody
	public String testZk(@PathVariable(required = true, name = "arg")String arg, @PathVariable(required = true, name = "duration")long duration) throws KeeperException, InterruptedException {
		return xxxService.testZk(arg, null, duration);
	}

	@GetMapping("/testTx")
	@ResponseBody
	public String testTx() {
		String s = "sss";
		Params p = new Params("ABC", "DEF");
		return xxxService.testTx(s, p);
	}

	@GetMapping("/testTx0")
	@ResponseBody
	public String testTx0() {
		String s = "sss";
		Params p = new Params("ABC", "DEF");
		return xxxService.testTx0(s, p);
	}

	@Autowired
	private ZzzService zzzService;

	@DistributedTransactional
	@GetMapping("/testTx1")
	@ResponseBody
	public String testTx1() {
		String s = "sss";
		Params p = new Params("ABC", "DEF");
		Params[] pp = new Params[5];
		pp[0] = p;
		pp[2] = p;
		pp[4] = p;
		zzzService.callDubbo(987l, null);
		zzzService.updateDB(s, p);
		zzzService.callHttp(321, new int[] {1, 5, 8}, pp);
		return "XXX";
	}

	@Autowired
	private RegionService regionService;

	@Cache(expire = 30)
	@GetMapping("/region/all")
	@ResponseBody
	public List<Region> findAllRegions() {
		log.info("===============findAllRegions");
		return regionService.findAll();
	}

	@Cache(expire = 15)
	@GetMapping("/region/countries")
	@ResponseBody
	public List<Region> findCountries() {
		log.info("===============findCountries");
		return regionService.findCountries();
	}

	@JedisInjector
	@GetMapping("/redis")
	@ResponseBody
	public void testRedis(Jedis jedis) {
		String key = "www";
		String value = "xxxx";
		jedis.set(key, value);
		jedis.expire(key, 5);
		log.info("testRedis==============={}", jedis.get(key));
	}

//	@JedisInjector
	@GetMapping("/redis2")
	@ResponseBody
	public void testRedisCluster2() {
	/*public void testRedisCluster(JedisCluster jedis) {
		String key = "kkk";
		String value = "vvv";
		jedis.set(key, value);
		jedis.expire(key, 2);*/
		xxxService.testRedisCluster("aaa", null, "bbb", null, "ccc");
	}

	@GetMapping("/redis3")
	@ResponseBody
	public void testRedisCluster3() throws InterruptedException {
		xxxService.testDeprecatedSynchronized(3000);
	}

	private String sss;

	@GetMapping("/ttt/{arg}")
	@ResponseBody
	public void testSingleton(@PathVariable(required = true, name = "arg")String arg) {
		this.sss = arg;
	}

	@GetMapping("/ttt")
	@ResponseBody
	public String testSingleton() {
		return this.sss;
	}
}