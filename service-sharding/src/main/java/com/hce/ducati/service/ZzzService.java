package com.hce.ducati.service;

import java.net.UnknownHostException;
import java.util.Set;

import redis.clients.jedis.Jedis;

public interface ZzzService {
	public void test() throws InterruptedException, UnknownHostException;
	public void test2() throws InterruptedException, UnknownHostException;
	public String chaxun(String s, int i) throws InterruptedException;
	public Set<String> allKeys(Jedis jedis);
}