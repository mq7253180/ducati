package com.hce.ducati.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hce.ducati.dao.TestDao;
import com.quincy.sdk.Result;
import com.quincy.sdk.helper.HttpClientHelper;

@RefreshScope
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
	private TestDao testDao;
	private static volatile int finished = 0;

	@RequestMapping("/uuu")
	@ResponseBody
	public Result uuu(@RequestParam(required = true, value = "id")Long id, @RequestParam(required = true, value = "c")int c) throws InterruptedException {
		long duration = multiThreads(c, (index)->{
			testDao.updateUest(id);
		});
		Result result = new Result();
		result.setData(duration);
		return result;
	}

	@RequestMapping("/uuu2")
	@ResponseBody
	public Result uuu2(@RequestParam(required = true, value = "id")Long id, @RequestParam(required = true, value = "c")int c) throws InterruptedException {
		long duration = multiThreads(c, (index)->{
			testDao.updateUest(id+index);
		});
		Result result = new Result();
		result.setData(duration);
		return result;
	}

	@RequestMapping("/iii")
	@ResponseBody
	public Result iii(@RequestParam(required = true, value = "c")int c) throws InterruptedException {
		long duration = multiThreads(c, (index)->{
			testDao.insertUest();
		});
		Result result = new Result();
		result.setData(duration);
		return result;
	}

	private static long multiThreads(int count, Task task) throws InterruptedException {
		List<Thread> threads = new ArrayList<Thread>(count);
		long start = System.currentTimeMillis();
		finished = 0;
		Object lock = new Object();
		for(int i=0;i<count;i++) {
			int index = i;
			threads.add(new Thread(()->{
				task.run(index);
				finished++;
				synchronized(lock) {
					lock.notifyAll();
				}
			}));
		}
		for(Thread thread:threads)
			thread.start();
		while(finished<count)
			synchronized(lock) {
				lock.wait(100);
			}
		return System.currentTimeMillis()-start;
	}

	private static interface Task {
		public void run(int index);
	}

	public static void main(String[] args) throws InterruptedException, IOException {
		long duration = multiThreads(20, (index)->{
			try {
				System.out.println("---"+index);
				HttpClientHelper.get("https://demo.jep8566.com/api/ppp/uuu?id=12345&c=1", null);
				System.out.println("==="+index);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		System.out.println(duration);
	}
}