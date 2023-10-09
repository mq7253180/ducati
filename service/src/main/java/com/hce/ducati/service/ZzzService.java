package com.hce.ducati.service;

import com.hce.ducati.o.Params;

public interface ZzzService {
	public void callHttp(int i, int[] ii, Params[] ps);
	public void updateDB(String s, Params p);
	public void callDubbo(Long id, String val);
	public void callDubbo();
	public void test() throws InterruptedException;
}