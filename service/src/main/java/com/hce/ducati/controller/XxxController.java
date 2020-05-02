package com.hce.ducati.controller;

import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.dubbo.config.annotation.Reference;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.zookeeper.KeeperException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hce.ducati.client.InnerFeign;
import com.hce.ducati.client.QuincyFeign;
import com.hce.ducati.entity.Enterprise;
import com.hce.ducati.entity.UserEntity;
import com.hce.ducati.ServiceInitConfiguration;
import com.hce.ducati.client.DucatiClient;
import com.hce.ducati.client.DucatiSpringCloudClient;
import com.hce.ducati.o.AccountO;
import com.hce.ducati.o.Params;
import com.hce.ducati.o.RegionResultDTO;
import com.hce.ducati.service.UserService;
import com.hce.ducati.service.XxxService;
import com.hce.ducati.service.ZzzService;
import com.hce.ducati.service.impl.A;
import com.hce.ducati.service.impl.ABCdefg;
import com.hce.ducati.service.impl.ABdefgh;
import com.hce.ducati.service.impl.Abefghi;
import com.hce.ducati.service.impl.UserServiceImpl;
import com.hce.ducati.service.impl.aBcd;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.quincy.auth.annotation.OAuth2Resource;
import com.quincy.auth.annotation.PermissionNeeded;
import com.quincy.sdk.RedisProcessor;
import com.quincy.sdk.Result;
import com.quincy.sdk.VCcodeSender;
import com.quincy.sdk.VCodeCharsFrom;
import com.quincy.sdk.annotation.Cache;
import com.quincy.sdk.annotation.JedisInjector;
import com.quincy.sdk.annotation.SignatureRequired;
import com.quincy.sdk.annotation.VCodeRequired;
import com.quincy.sdk.annotation.transaction.DTransactional;
import com.quincy.sdk.entity.Region;
import com.quincy.sdk.helper.AopHelper;
import com.quincy.sdk.service.RegionService;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

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
	@Autowired
	private UserService userService;

	@OAuth2Resource("usrInfo")
	@RequestMapping("/usrinfo")
	@ResponseBody
	public UserEntity getUserInfo(@RequestParam(required = true, value = OAuth.OAUTH_USERNAME)String username) {
		UserEntity user = userService.find(username);
		user.setPassword(null);
		return user;
	}

	@GetMapping("/error/json")
	@ResponseBody
	public String testErrJson() {
		throw new RuntimeException("WWWWXXXXX");
	}

	@GetMapping("/error/page")
	public String testErrPage() {
		throw new RuntimeException("WWWWXXXXX");
	}

	@GetMapping("/proxy")
	@ResponseBody
	public String testProxy() {
		String info = xxxService.classInfo();
		log.info("\r\n"+info);
		return info.replaceAll("\r\n", "<br/>");
	}

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
		log.info("============================SPRINGBOOT_DUBBO");
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

	@DTransactional
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

	@VCodeRequired(clientTokenName = "mobilePhone")
	@GetMapping("/vcode/do")
	@ResponseBody
	public String testRequireVcode() {
		return "xxxx";
	}

//	@VCodeRequired
	@SignatureRequired
	@GetMapping("/testsign")
	@ResponseBody
	public String testSignature() {
		return "wwwww";
	}

	@Autowired
	private RedisProcessor redisProcessor;
	@Value("${vcode.expire}")
	private int vcodeExpire;

	@GetMapping("/vcode")
	@ResponseBody
	public Result vcodeAsMobile(HttpServletRequest request) throws Exception {
		String token = redisProcessor.vcode(request, VCodeCharsFrom.DIGITS, 6, "mobilePhone", new VCcodeSender() {
			@Override
			public void send(char[] vcode, String token) throws Exception {
				log.info("已通过阿里云短信接口发送验证码: {}", new String(vcode));
			}
		});
		Result result = new Result(1, "验证码发送成功", token);
		return result;
	}

	@Autowired
	private ApplicationContext applicationContext;

	@GetMapping("/bean")
	@ResponseBody
	public void testBean(HttpServletRequest request) {
		Enumeration<String> headerNames = request.getHeaderNames();
		log.info("==========================");
		while(headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			log.warn("{}-------{}", headerName, request.getHeader(headerName));
		}
		log.info("==========================");
		printBeanNameInfo(ABCdefg.class);
		printBeanNameInfo(ABdefgh.class);
		printBeanNameInfo(Abefghi.class);
		printBeanNameInfo(A.class);
		printBeanNameInfo(aBcd.class);
		printBeanNameInfo(UserServiceImpl.class);
		printBeanNameInfo(SystemController.class);
		printBeanNameInfo(ServiceInitConfiguration.class);
	}

	private void printBeanNameInfo(Class<?> clazz) {
		Map<String, ?> map = applicationContext.getBeansOfType(clazz);
		String beanName0 = map.keySet().iterator().next();
		String beanName1 = AopHelper.extractBeanName(clazz);
		log.info("BEAN_NAME============={}---------{}----------{}", beanName0, beanName1, beanName0.equals(beanName1));
	}

	@GetMapping("/tx/update/{id}/{mobilePhone}")
	@ResponseBody
	public int update(@PathVariable(required = true, name = "id")Long id, @PathVariable(required = true, name = "mobilePhone")String mobilePhone) {
		return xxxService.update(id, mobilePhone);
	}

	@GetMapping("/tx/select/{id}")
	@ResponseBody
	public List<Enterprise> select(@PathVariable(required = true, name = "id")Long id) {
		return xxxService.select(id);
	}

	@GetMapping("/concurrent/batch/{companyId}/{region}/{delay}")
	@ResponseBody
	public int concurrentBatch(@PathVariable(required = true, name = "companyId")Long companyId, @PathVariable(required = true, name = "region")String region, @PathVariable(required = true, name = "delay")long delay) throws InterruptedException {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					xxxService.updateIndividualBatch(companyId, region+"XXX", delay);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					xxxService.updateIndividualBatch(companyId, region+"WWW", delay);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
		return 1;
	}

	@GetMapping("/concurrent/one/{id1}/{id2}/{region}/{delay}")
	@ResponseBody
	public int concurrentOne(@PathVariable(required = true, name = "id1")Long id1, @PathVariable(required = true, name = "id2")Long id2, @PathVariable(required = true, name = "region")String region, @PathVariable(required = true, name = "delay")long delay) throws InterruptedException {
		return xxxService.updateIndividualOne(id1, id2, region, delay);
	}

	@PermissionNeeded(value = "test")
	@GetMapping("/test/permission")
	public String testPermission() {
		return "/content/test_permission";
	}

	@GetMapping("/test/request")
	@ResponseBody
	public HttpServletRequest testRequest(HttpServletRequest request) {
		log.warn(new StringBuilder(500)
				.append("getRemoteAddr: ")
				.append(request.getRemoteAddr())
				.append("\r\ngetRemoteHost: ")
				.append(request.getRemoteHost())
				.append("\r\ngetRemotePort: ")
				.append(request.getRemotePort())
				.append("\r\ngetRemoteUser: ")
				.append(request.getRemoteUser())
				.append("\r\ngetRequestURI: ")
				.append(request.getRequestURI())
				.append("\r\ngetRequestURL: ")
				.append(request.getRequestURL())
				.append("\r\ngetLocalAddr: ")
				.append(request.getLocalAddr())
				.append("\r\ngetLocalName: ")
				.append(request.getLocalName())
				.append("\r\ngetLocalPort: ")
				.append(request.getLocalPort())
				.append("\r\ngetPathInfo: ")
				.append(request.getPathInfo())
				.append("\r\ngetPathTranslated: ")
				.append(request.getPathTranslated())
				.append("\r\ngetProtocol: ")
				.append(request.getProtocol())
				.append("\r\ngetAuthType: ")
				.append(request.getAuthType())
				.append("\r\ngetMethod: ")
				.append(request.getMethod())
				.append("\r\ngetRealPath: ")
				.append(request.getRealPath("*"))
				.append("\r\ngetScheme: ")
				.append(request.getScheme())
				.append("\r\ngetServerName: ")
				.append(request.getServerName())
				.append("\r\ngetServerPort: ")
				.append(request.getServerPort())
				.append("\r\ngetServletPath: ")
				.append(request.getServletPath())
				.append("\r\ngetContextPath: ")
				.append(request.getContextPath())
				.toString());
		return request;
	}
}