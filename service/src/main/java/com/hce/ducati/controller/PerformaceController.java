package com.hce.ducati.controller;

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
	public Result uuu(@RequestParam(required = true, value = "c") int c) throws InterruptedException {
		List<Thread> threads = new ArrayList<Thread>(c);
		long start = System.currentTimeMillis();
		finished = 0;
		Object lock = new Object();
		for(int i=0;i<c;i++) {
			threads.add(new Thread(()->{
				testDao.updateUest(1l);
				finished++;
				synchronized(lock) {
					lock.notifyAll();
				}
			}));
		}
		for(Thread thread:threads) {
			thread.start();
		}
		while(finished<c) {
			synchronized(lock) {
				lock.wait(100);
			}
		}
		Result result = new Result();
		result.setData(System.currentTimeMillis()-start);
		return result;
	}
}