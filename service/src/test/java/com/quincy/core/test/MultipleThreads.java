package com.quincy.core.test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MultipleThreads {
	public static void main(String[] args) {
		BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<Runnable>(3);
		ExecutorService threadPoolExecutor = new ThreadPoolExecutor(2, 4, 3, 
				TimeUnit.SECONDS, blockingQueue, 
				new ThreadPoolExecutor.CallerRunsPolicy());
		threadPoolExecutor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("111");
			}
		});
		threadPoolExecutor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("222");
			}
		});
		threadPoolExecutor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("333");
			}
		});
		threadPoolExecutor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("444");
			}
		});
		threadPoolExecutor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("555");
			}
		});
		threadPoolExecutor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("666");
			}
		});
		threadPoolExecutor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("777");
			}
		});
		threadPoolExecutor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("888");
			}
		});
		threadPoolExecutor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("999");
			}
		});
		
		/*ThreadFactory threadFactory = new ThreadFactory() {
			public Thread newThread(Runnable r) {
				return new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							Thread.sleep(2000);
							System.out.println("xxxxx");
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				});
			}
		};
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3, threadFactory);
		for(int i=0;i<5;i++) {
			fixedThreadPool.execute(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(2000);
						System.out.println("wwww");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}*/
	}
	public static void main() {
		int corePoolSize = 4;
		Runnable runnable = new Runnable(){
			public void run() {
				
			};
		};
		Callable callable = new Callable() {
			public Object call() throws Exception {
				return null;
			}
		};
		ThreadFactory threadFactory = new ThreadFactory() {
			public Thread newThread(Runnable r) {
				return null;
			}
		};
		Thread thread = new Thread(runnable);
		//固定大小线程池
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(corePoolSize, threadFactory);
		//无界线程池，可以进行自动线程回收
		ExecutorService cachedThreadPool = Executors.newCachedThreadPool(threadFactory);
		//单个后台线程
		ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor(threadFactory);
		//
		ExecutorService singleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor(threadFactory);
		//
		BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<Runnable>(3);
		ExecutorService threadPoolExecutor = new ThreadPoolExecutor(2, 4, 3, 
				TimeUnit.SECONDS, blockingQueue, 
				threadFactory, 
				new ThreadPoolExecutor.DiscardOldestPolicy());

		ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(corePoolSize, threadFactory);
		// 1秒钟后运行，并每隔2秒运行一次
		ScheduledFuture scheduleAtFixedRate = scheduledThreadPool.scheduleAtFixedRate(runnable, 1, 2, TimeUnit.SECONDS);
		// 2秒钟后运行，并每次在上次任务运行完后等待5秒后重新运行
		ScheduledFuture scheduleWithFixedDelay = scheduledThreadPool.scheduleWithFixedDelay(runnable, 2, 5, TimeUnit.SECONDS);
		// 30秒后结束关闭任务，并且关闭Scheduler
		scheduledThreadPool.schedule(new Runnable() {
			public void run() {
				/*beeperHandle.cancel(true);
				beeperHandle2.cancel(true);
				scheduler.shutdown();*/
			}
		}, 30, TimeUnit.SECONDS);
		Future future = fixedThreadPool.submit(callable);
		Lock lock = new ReentrantLock();
//		lock.newCondition().
	}
}
