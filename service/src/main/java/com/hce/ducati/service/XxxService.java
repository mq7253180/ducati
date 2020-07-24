package com.hce.ducati.service;

import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

import com.hce.ducati.entity.Enterprise;
import com.hce.ducati.o.Params;
import com.quincy.sdk.entity.Region;

import redis.clients.jedis.JedisCluster;

public interface XxxService {
	public int updateResion(Long id, String cnName);
	public int updateResion2(String enName, String cnName);
	public int updateResion3();
	public int updateResion4(String enName);
	public int updateResion4x(String enName);
	public int updateResion4xx(String enName);
	public int updateResion5(Integer sort);
	public String testZk(String arg, ZooKeeper zk, long duration) throws KeeperException, InterruptedException;
	public String testTx(String s, Params p);
	public String testTx0(String s, Params p);
	public void testRedisCluster(String arg0, JedisCluster jedis, String arg1, JedisCluster jedis2, String arg2);
	public void testDeprecatedSynchronized(long millis) throws InterruptedException;
	public List<Region> findRegions();
	public List<Region> findRegions2();
	public Region findRegion2(Long id);
	public Region findRegion2(String cnName);
	public List<Region> findRegions4(Long id);
	public String classInfo();
	public int update(Long id, String mobilePhone);
	public List<Enterprise> select(Long id);
	public int updateIndividualBatch(Long companyId, String region, long delay) throws InterruptedException;
	public int updateIndividualOne(Long id1, Long id2, String region, long delay) throws InterruptedException;
}