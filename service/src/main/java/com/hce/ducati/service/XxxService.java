package com.hce.ducati.service;

import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

import com.hce.ducati.o.Params;
import com.quincy.sdk.entity.Region;

import redis.clients.jedis.JedisCluster;

public interface XxxService {
	public String testZk(String arg, ZooKeeper zk, long duration) throws KeeperException, InterruptedException;
	public String testTx(String s, Params p);
	public String testTx0(String s, Params p);
	public void testRedisCluster(String arg0, JedisCluster jedis, String arg1, JedisCluster jedis2, String arg2);
	public void testDeprecatedSynchronized(long millis) throws InterruptedException;
	public List<Region> findRegions();
	public String classInfo();
}
