package com.hce.ducati;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hce.ducati.client.DucatiClient;
import com.hce.ducati.client.o.Region;

public class DubboTest {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = null;
		try {
			context = new ClassPathXmlApplicationContext("dubbo-client.xml");
			context.start();
			System.out.println("==================INITED");
			DucatiClient client = context.getBean(DucatiClient.class);
			System.out.println("==================GOT_BEAN");
			String s = client.test();
			/*List<Region> regions = client.fineAllZones();
			System.out.println("RESULT=================="+s+"----------------"+regions.size());
			for(Region g:regions) {
				System.out.println(g.getCnName()+"---"+g.getEnName());
			}*/
		} finally {
			context.close();
		}
	}
}
