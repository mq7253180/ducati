package com.hce.ducati.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

//import org.apache.zookeeper.KeeperException;
//import org.apache.zookeeper.ZooKeeper;

import com.hce.ducati.entity.Enterprise;
import com.hce.ducati.entity.Zelation;
import com.hce.ducati.o.Params;
import com.hce.ducati.o.SubTestDto;
import com.quincy.sdk.DynamicField;

import redis.clients.jedis.JedisCluster;

public interface XxxService {
//	public String testZk(String arg, ZooKeeper zk, long duration) throws KeeperException, InterruptedException;
	public String testTx(String s, Params p);
	public String testTx0(String s, Params p);
	public void testRedisCluster(String arg0, JedisCluster jedis, String arg1, JedisCluster jedis2, String arg2);
//	public void testDeprecatedSynchronized(long millis) throws InterruptedException;
	public String classInfo();
	public int update(Long id, String mobilePhone);
	public List<Enterprise> select(Long id);
	public int updateIndividualBatch(Long companyId, String region, long delay) throws InterruptedException;
	public int updateIndividualOne(Long id1, Long id2, String region, long delay) throws InterruptedException;
	public Zelation saveZelation();
	public void test1();
	public void test2();
	public void testUpdation();
	public void testUpdation2();
	public void testUpdation3();
	public void testUpdation4();
	public Object findSubTests(int limit, int offset) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, SQLException, IOException, CloneNotSupportedException;
	public List<SubTestDto> findSubTest2(int limit, int offset);
	public Object findOneSubTest(String id);
	public SubTestDto findOneSubTest2(String id);
	public List<DynamicField> findSubTestDynamicFields();
}