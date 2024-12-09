package com.hce.ducati.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hce.ducati.service.PerformanceService;
import com.quincy.sdk.Result;
import com.quincy.sdk.helper.HttpClientHelper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/ppp")
public class PerformaceController {
	@RequestMapping("/qqq")
	@ResponseBody
	public Result qqq() throws UnknownHostException {
		InetAddress localHost = InetAddress.getLocalHost();
		Result result = new Result();
		result.setData(localHost.getHostAddress());
		return result;
	}

	@Autowired
	private PerformanceService performanceService;
	private static int finished = 0;

	@RequestMapping("/uuu")
	@ResponseBody
	public Result uuu(@RequestParam(required = true, value = "id")Long id, @RequestParam(required = true, value = "c")int c) throws InterruptedException {
		long duration = multiThreads(c, (index)->{
			performanceService.updateUest(id);
		});
		Result result = new Result();
		result.setData(duration);
		return result;
	}

	@RequestMapping("/uuu2")
	@ResponseBody
	public Result uuu2(@RequestParam(required = true, value = "id")Long id, @RequestParam(required = true, value = "c")int c) throws InterruptedException {
		long duration = multiThreads(c, (index)->{
			performanceService.updateUest(id+index);
		});
		Result result = new Result();
		result.setData(duration);
		return result;
	}

	@RequestMapping("/uuu3")
	@ResponseBody
	public Result uuu3(@RequestParam(required = true, value = "id")Long id, @RequestParam(required = true, value = "o")int o, @RequestParam(required = true, value = "i")int i) throws InterruptedException {
		long duration = multiThreads(o, i, (index)->{
			performanceService.singleUpdateUest(id+index);
		});
		Result result = new Result();
		result.setData(duration);
		return result;
	}

	@RequestMapping("/all")
	@ResponseBody
	public Result all(@RequestParam(required = true, value = "id")Long id) throws InterruptedException {
		long start = System.currentTimeMillis();
		performanceService.updateUest(id);
		Result result = new Result();
		result.setData(System.currentTimeMillis()-start);
		return result;
	}

	@RequestMapping("/lll")
	@ResponseBody
	public Result lll(@RequestParam(required = true, value = "id")Long id, @RequestParam(required = true, value = "c")int c) throws InterruptedException {
		long duration = multiThreads(c, (index)->{
			try {
				log.warn(index+"---"+HttpClientHelper.get("http://test.jep8566.com/api/ppp/all?id="+(id+index), null));
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		Result result = new Result();
		result.setData(duration);
		return result;
	}

	@RequestMapping("/iii")
	@ResponseBody
	public Result iii(@RequestParam(required = true, value = "c")int c) throws InterruptedException {
		long duration = multiThreads(c, (index)->{
			performanceService.insertUest();
		});
		Result result = new Result();
		result.setData(duration);
		return result;
	}

	@RequestMapping("/sss")
	@ResponseBody
	public Result sss(@RequestParam(required = true, value = "c")int c, @RequestParam(required = false, value = "id")Long id) throws InterruptedException  {
		long duration = multiThreads(c, (index)->{
//			List<UestDto> list = performanceService.findUest(5, 7);
//			log.warn("R----{}", list.size());
			performanceService.findUest(id);
		});
		Result result = new Result();
		result.setData(duration);
		return result;
	}

	private static BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<Runnable>(Integer.MAX_VALUE);
	private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1024, 1024, 5, TimeUnit.SECONDS, blockingQueue);

	private static long multiThreads(int count, Task task) throws InterruptedException {
		return multiThreads(1, count, task);
	}

	private static long multiThreads(int outerCount, int innerCount, Task task) throws InterruptedException {
		List<Runnable> tasks = new ArrayList<Runnable>(innerCount);
//		List<Thread> threads = new ArrayList<Thread>(count);
		finished = 0;
		Object lock = new Object();
		long start = System.currentTimeMillis();
		for(int i=0;i<outerCount;i++) {
			for(int j=0;j<innerCount;j++) {
				int index = j;
				tasks.add(()->{
					try {
						task.run(index);
					} catch(Throwable e) {
						log.error("ERROR===============", e);
					}
					synchronized(lock) {
						finished++;
						lock.notifyAll();
					}
				});
				/*threads.add(new Thread(()->{
					try {
						task.run(index);
					} catch(Throwable e) {
						log.error("ERROR===============", e);
					}
					synchronized(lock) {
						finished++;
						lock.notifyAll();
					}
				}));*/
			}
		}
		long creationDuration = System.currentTimeMillis()-start;
		start = System.currentTimeMillis();
		for(Runnable r:tasks)
			threadPoolExecutor.execute(r);
		/*for(Thread thread:threads)
			thread.start();*/
		while(finished<innerCount)
			synchronized(lock) {
				lock.wait(100);
			}
		long duration = System.currentTimeMillis()-start;
		log.warn("PERFORMANCE_TEST================{}---{}", creationDuration, duration);
		return duration;
	}

	private static interface Task {
		public void run(int index);
	}

	@RequestMapping("/ttt")
	@ResponseBody
	public Result ttt(@RequestParam(required = true, value = "c")int c) throws InterruptedException {
		long duration = multiThreads(c, (index)->{
			try {
				log.warn(index+"---"+HttpClientHelper.get("http://localhost:12080/ppp/qqq", null));
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		Result result = new Result();
		result.setData(duration);
		return result;
	}

	private final static String[] IPS = {"192.168.0.4", "192.168.0.2", "192.168.0.5", "192.168.0.3"};

	@RequestMapping("/ttt2")
	@ResponseBody
	public Result ttt2(@RequestParam(required = true, value = "c")int c, @RequestParam(required = true, value = "ip")int ip) throws InterruptedException {
		long duration = multiThreads(c, (index)->{
			try {
				log.warn(index+"---"+HttpClientHelper.get("http://"+IPS[ip]+":12080/ppp/qqq", null));
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		Result result = new Result();
		result.setData(duration);
		return result;
	}

	@RequestMapping("/hhh")
	@ResponseBody
	public Result hhh(@RequestParam(required = true, value = "c")int c) throws InterruptedException {
		long duration = multiThreads(c, (index)->{
			try {
				log.warn(index+"---"+HttpClientHelper.get("https://test.jep8566.com/api/ppp/qqq", null));
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		Result result = new Result();
		result.setData(duration);
		return result;
	}

	@RequestMapping("/hhh2")
	@ResponseBody
	public Result hhh2(@RequestParam(required = true, value = "c")int c) throws InterruptedException {
		long duration = multiThreads(c, (index)->{
			try {
				log.warn(index+"---"+HttpClientHelper.get("http://test.jep8566.com/api/ppp/qqq", null));
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		Result result = new Result();
		result.setData(duration);
		return result;
	}

	@RequestMapping("/xxx")
	@ResponseBody
	public Result xxx(@RequestParam(required = true, value = "c")int c) throws InterruptedException {
		long duration = multiThreads(c, (index)->{
			try {
				performanceService.test(c, "adsfas");
			} catch (UnknownHostException e) {
				log.error("XXX==========", e);
			}
		});
		Result result = new Result();
		result.setData(duration);
		return result;
	}

	public static void main(String[] args) throws InterruptedException, IOException {
		long duration = multiThreads(35, (index)->{
			try {
//				System.out.println(index+"---"+HttpClientHelper.get("https://demo.jep8566.com/api/ppp/uuu?id=12345&c=1", null));
//				System.out.println(index+"---"+HttpClientHelper.get("https://demo.jep8566.com/api/ppp/qqq", null));
				HttpClientHelper.get("https://demo.jep8566.com/api/ppp/qqq", null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		System.out.println(duration);
	}
}