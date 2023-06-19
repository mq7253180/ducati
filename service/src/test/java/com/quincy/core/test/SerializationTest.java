package com.quincy.core.test;

import java.io.IOException;

//import com.quincy.core.test.o.Entity;

public class SerializationTest {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		/*Entity e = new Entity();
		e.setAaa("xxx");
		e.setBbb("www");
		byte[] bb = CommonHelper.serialize(e);
		StringBuilder s = new StringBuilder();
		for(byte b:bb) {
			s.append(b);
			s.append(",");
		}
		System.out.println(s.toString());*/
//		byte[] bb = {-84,-19,0,5,115,114,0,29,99,111,109,46,113,117,105,110,99,121,46,99,111,114,101,46,116,101,115,116,46,111,46,69,110,116,105,116,121,117,39,114,-65,33,67,85,-28,2,0,2,76,0,3,97,97,97,116,0,18,76,106,97,118,97,47,108,97,110,103,47,83,116,114,105,110,103,59,76,0,3,98,98,98,113,0,126,0,1,120,112,116,0,3,120,120,120,116,0,3,119,119,119};
//		byte[] bb = {-84,-19,0,5,115,114,0,29,99,111,109,46,113,117,105,110,99,121,46,99,111,114,101,46,116,101,115,116,46,111,46,69,110,116,105,116,121,-78,-75,-52,76,-82,-71,78,-103,2,0,2,76,0,3,97,97,97,116,0,18,76,106,97,118,97,47,108,97,110,103,47,83,116,114,105,110,103,59,76,0,3,98,98,98,113,0,126,0,1,120,112,116,0,3,120,120,120,116,0,3,119,119,119};
		/*
		 * Entity2 eX = (Entity2)CommonHelper.unSerialize(bb);
		 * 抛异常: java.lang.ClassCastException: com.quincy.core.test.o.Entity cannot be cast to com.quincy.core.test.o.Entity2
		 *
		 * 换serialVersionUID: java.io.InvalidClassException: com.quincy.core.test.o.Entity; local class incompatible: stream classdesc serialVersionUID = 8441842191751665124, local class serialVersionUID = -4341546027315064200
		 * 
		 * 删一字段: 没问题
		 * 
		 * 加一字段: 也没问题，但新加字段值为空
		 */
//		Entity eX = (Entity)CommonHelper.unSerialize(bb);
//		System.out.println("E2-------------"+eX.getAaa());
//		System.out.println("E2-------------"+eX.getBbb());
//		System.out.println("E2-------------"+eX.getCcc());
	}
}