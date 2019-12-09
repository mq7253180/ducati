package com.hce.ducati;

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DubboBootstrap {
	public static void main(String[] args) throws IOException {
//		System.setProperty("dubbo.application.logger", "slf4j");
		ClassPathXmlApplicationContext context = null;
		try {
			long start = System.currentTimeMillis();
//			context = new ClassPathXmlApplicationContext("applicationContext.xml");
			context = new ClassPathXmlApplicationContext("applicationContext-dubbo.xml");
			context.start();
			System.out.println("服务已经启动("+(System.currentTimeMillis()-start)+"ms)...");
			System.in.read();
		} finally {
			if(context!=null)
				context.close();
		}
	}
}
