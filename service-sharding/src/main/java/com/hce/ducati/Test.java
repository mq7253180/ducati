package com.hce.ducati;

import com.quincy.sdk.SnowFlake;

public class Test {

	public static void main(String[] args) {
		System.out.println("17810355544".hashCode()%8);
		System.out.println("mq7253180@126.com".hashCode()%8);
		System.out.println("maqiang".hashCode()%8);
		System.out.println(1235%8);
		System.out.println(SnowFlake.extractShardingKey(25143814434232384l));
//		System.out.println(SnowFlake.nextId(1235));
	}

}
