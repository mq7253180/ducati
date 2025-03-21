package com.hce.ducati.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

//import org.apache.dubbo.config.annotation.DubboReference;
//import org.apache.zookeeper.KeeperException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ApplicationContext;
import org.springframework.context.Lifecycle;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hce.ducati.ServiceInitConfiguration;
import com.hce.ducati.client.CenterFeign;
//import com.hce.ducati.client.DucatiClient;
//import com.hce.ducati.client.DucatiSpringCloudClient;
import com.hce.ducati.client.InnerFeign;
import com.hce.ducati.client.QuincyFeign;
import com.hce.ducati.entity.Enterprise;
import com.hce.ducati.o.Params;
import com.hce.ducati.o.SubTestDto;
import com.hce.ducati.service.XxxService;
import com.hce.ducati.service.ZzzService;
import com.hce.ducati.service.impl.A;
import com.hce.ducati.service.impl.ABCdefg;
import com.hce.ducati.service.impl.ABdefgh;
import com.hce.ducati.service.impl.Abefghi;
import com.hce.ducati.service.impl.aBcd;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.quincy.auth.service.UserService;
import com.quincy.auth.service.impl.UserServiceImpl;
import com.quincy.core.InnerConstants;
import com.quincy.sdk.DTransactionOptRegistry;
import com.quincy.sdk.DynamicField;
import com.quincy.sdk.Result;
import com.quincy.sdk.VCodeCharsFrom;
import com.quincy.sdk.VCodeOpsRgistry;
import com.quincy.sdk.VCodeSender;
import com.quincy.sdk.annotation.JedisSupport;
import com.quincy.sdk.annotation.SignatureRequired;
import com.quincy.sdk.annotation.VCodeRequired;
import com.quincy.sdk.annotation.auth.PermissionNeeded;
import com.quincy.sdk.annotation.transaction.DTransactional;
import com.quincy.sdk.helper.AopHelper;
import com.quincy.sdk.helper.HttpClientHelper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
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
//	@Autowired(required = false)
//	private DucatiSpringCloudClient ducatiSpringCloudClient;
	@Autowired
	private UserService userService;

	@RequestMapping("/stop")
	@ResponseBody
	public void stop() {
		((Lifecycle)applicationContext).stop();
	}

	@RequestMapping("/testtxu")
	@ResponseBody
	public void testTxu() {
		zzzService.testTxUpdate();
	}

	@RequestMapping("/testtxq")
	@ResponseBody
	public void testTxq() {
		zzzService.testTxQuery();
	}

	@Autowired
	private CenterFeign centerFeign;

	@RequestMapping("/testf")
	@ResponseBody
	public String testf() {
		return centerFeign.actuator();
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

	@HystrixCommand(fallbackMethod = "hystrixFailure", commandProperties = {
			@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="1000"), 
			@HystrixProperty(name="execution.isolation.thread.interruptOnTimeout", value="false"), 
			@HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value="20"), 
			@HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value="50"), 
			@HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds", value="5000")
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
/*
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
*/

//	@DubboReference(version = "1.0.0")
//	private DucatiClient ducatiClient;

//	@GetMapping("/regions/dubbo")
//	@ResponseBody
//	public List<com.hce.ducati.client.o.Region> findRegionsViaDubbo() {
//		log.info("============================SPRINGBOOT_DUBBO");
//		return ducatiClient.fineAllZones();
//	}

	@Autowired
	private XxxService xxxService;

//	@GetMapping("/zk/{arg}/{duration}")
//	@ResponseBody
//	public String testZk(@PathVariable(required = true, name = "arg")String arg, @PathVariable(required = true, name = "duration")long duration) throws KeeperException, InterruptedException {
//		return xxxService.testZk(arg, null, duration);
//	}

	@Autowired
	private DTransactionOptRegistry transactionContext;

	@GetMapping("/test/tx/resume")
	@ResponseBody
	public String testResume() throws ClassNotFoundException, NoSuchMethodException, IOException, InterruptedException {
		transactionContext.resume("xxx");
		return "xxx";
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

	@DTransactional()
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

	@JedisSupport
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

//	@GetMapping("/redis3")
//	@ResponseBody
//	public void testRedisCluster3() throws InterruptedException {
//		xxxService.testDeprecatedSynchronized(3000);
//	}

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

	@GetMapping("/transaction")
	@ResponseBody
	public String test() {
		xxxService.test1();
		return "测试事务";
	}

	@GetMapping("/u")
	@ResponseBody
	public String testUpdation() {
		xxxService.testUpdation();
		return "测试记录数据版本1";
	}

	@GetMapping("/uu")
	@ResponseBody
	public String testUpdation2() {
		xxxService.testUpdation2();
		return "测试记录数据版本2";
	}

	@GetMapping("/uuu")
	@ResponseBody
	public String testUpdation3() {
		xxxService.testUpdation3();
		return "测试记录数据版本3";
	}

	@GetMapping("/uuuu")
	@ResponseBody
	public String testUpdation4() {
		xxxService.testUpdation4();
		return "测试记录数据版本4";
	}

	@GetMapping("/dd/{id}")
	@ResponseBody
	public Object findOneSubTest(@PathVariable(required = true, name = "id")String id) {
		return xxxService.findOneSubTest(id);
	}

	@GetMapping("/dddd/{limit}/{offset}")
	@ResponseBody
	public List<SubTestDto> findSubTest2(@PathVariable(required = true, name = "limit")int limit, @PathVariable(required = true, name = "offset")int offset) {
		return xxxService.findSubTest2(limit, offset);
	}

	@GetMapping("/dddd/{id}")
	@ResponseBody
	public SubTestDto findOneSubTest2(@PathVariable(required = true, name = "id")String id) {
		return xxxService.findOneSubTest2(id);
	}

	@GetMapping("/subtest/dynamicfields")
	@ResponseBody
	public List<DynamicField> findSubTestDynamicFields() {
		return xxxService.findSubTestDynamicFields();
	}

	@VCodeRequired
	@RequestMapping("/test")
	public ModelAndView test(@RequestParam(required = true, value = "status")int status) {
		return new ModelAndView(status==1?InnerConstants.VIEW_PATH_SUCCESS:InnerConstants.VIEW_PATH_FAILURE)
				.addObject("status", status)
				.addObject("msg", status==1?"成功":"失败");
	}

	@RequestMapping("/zelation")
	public void saveZelation() {
		xxxService.saveZelation();
	}

//	@VCodeRequired
	@SignatureRequired
	@GetMapping("/testsign")
	@ResponseBody
	public String testSignature() {
		return "wwwww";
	}

//	@Autowired
//	private AuthorizationCommonController authorizationCommonController;
//	@Value("${vcode.expire}")
//	private int vcodeExpire;
	@Autowired
	private VCodeOpsRgistry vCodeOpsRgistry;

	@GetMapping("/vcode")
	@ResponseBody
	public Result vcodeAsMobile(HttpServletRequest request) throws Exception {
		String token = vCodeOpsRgistry.genAndSend(request, VCodeCharsFrom.DIGITS, 6, new VCodeSender() {
			@Override
			public void send(char[] vcode, int expireMinuts) throws Exception {
				log.info("已通过阿里云短信接口发送验证码: {}, 失效时间{}分钟", new String(vcode), expireMinuts);
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

	@RequestMapping("/ttt")
	@ResponseBody
	public Result ttt() {
		Result result = new Result();
		result.setData("DUCATI");
		return result;
	}

//	@LoginRequired
	@PostMapping("/upload")
	@ResponseBody
	public void upload(HttpServletRequest request) throws IOException, ServletException {
		HttpClientHelper.handleUpload(request, new HttpClientHelper.UploadHandler() {
			@Override
			public void handle(String name, InputStream in) throws IOException {
				OutputStream out = null;
				try {
					out = new FileOutputStream("/Users/maqiang/tmp/upload/"+name);
					byte[] temp = new byte[in.available()];
					in.read(temp);
					out.write(temp);
				} finally {
					if(out!=null)
						out.close();
				}
			}
		});
	}

	@GetMapping("/test/request")
	@ResponseBody
	public void testRequest(HttpServletRequest request) {
		log.warn(new StringBuilder(500)
				.append("\r\ngetRemoteAddr: ")
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
//				.append("\r\ngetRealPath: ")
//				.append(request.getRealPath("*"))
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
	}
}