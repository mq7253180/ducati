package com.hce.ducati.service;

import com.hce.ducati.o.Params;

public interface ZzzzService {
	public void callHttp(int i, int[] ii, Params[] ps);
	public void updateDB(String s, Params p);
	public void callDubbo(Long id, String val);
	public void test() throws InterruptedException;
}