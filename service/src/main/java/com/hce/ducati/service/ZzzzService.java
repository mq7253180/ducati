package com.hce.ducati.service;

import com.hce.ducati.o.Params;
import com.hce.ducati.o.UserDto;

public interface ZzzzService {
	public UserDto testTxQuery(Long id);
	public int testTxUpdate(Long id, String enName);
	public void callHttp(int i, int[] ii, Params[] ps);
	public void updateDB(String s, Params p);
	public void callDubbo(Long id, String val);
	public void test() throws InterruptedException;
}