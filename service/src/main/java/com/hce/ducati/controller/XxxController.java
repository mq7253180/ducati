package com.hce.ducati.controller;

import java.math.BigDecimal;
import java.util.List;

import org.apache.dubbo.config.annotation.Reference;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
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
import com.hce.ducati.o.RegionResultDTO;
import com.hce.ducati.service.XxxService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.quincy.sdk.annotation.OriginalZooKeeperInjector;
import com.quincy.sdk.entity.Region;
import com.quincy.sdk.zookeeper.Context;
import com.quincy.sdk.zookeeper.Handler;

import lombok.extern.slf4j.Slf4j;

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

	@Autowired
	private RegionMapper regionMapper;

	@GetMapping("/regions")
	@ResponseBody
	public List<Region> findRegions() {
		return regionMapper.find("on");
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
}
