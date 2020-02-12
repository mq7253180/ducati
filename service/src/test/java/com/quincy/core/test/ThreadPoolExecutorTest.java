package com.quincy.core.test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorTest {
	public void test() {
		int corePoolSize = 2;
        int maximumPoolSize = 4;
        long keepAliveTime = 10;
		BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<Runnable>(3);
		ExecutorService threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, blockingQueue, 
			new ThreadFactory() {
				public Thread newThread(Runnable r) {
					System.out.println("ThreadFactory");
					return new Thread(r);
//					return null;
				}
			}, 
//			new ThreadPoolExecutor.DiscardOldestPolicy());
			new RejectedExecutionHandler() {
				@Override
				public void rejectedExecution(Runnable arg0, ThreadPoolExecutor arg1) {
					System.out.println("RejectedExecutionHandler: "+arg0+"---"+arg1);
				}
			}
		);
		for(int i=0;i<10;i++) {
			threadPoolExecutor.execute(new Runnable() {
				@Override
				public void run() {
					System.out.println("============");
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}
	}

	public static void main(String[] args) {
		new ThreadPoolExecutorTest().test();
	}
}
