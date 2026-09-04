package com.hce.ducati.service.impl;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.hce.ducati.entity.Enterprise;
import com.hce.ducati.entity.Zelation;
import com.hce.ducati.o.Params;
import com.hce.ducati.o.SubTestDto;
import com.hce.ducati.service.XxxService;
import com.quincy.sdk.DynamicField;

import redis.clients.jedis.JedisCluster;

@Primary
@Service
public class XxxServiceShardingImpl implements XxxService {

	@Override
	public String testTx(String s, Params p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String testTx0(String s, Params p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void testRedisCluster(String arg0, JedisCluster jedis, String arg1, JedisCluster jedis2, String arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int update(Long id, String mobilePhone) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Enterprise> select(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateIndividualBatch(Long companyId, String region, long delay) throws InterruptedException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateIndividualOne(Long id1, Long id2, String region, long delay) throws InterruptedException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Zelation saveZelation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void test1() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void test2() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void testUpdation() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void testUpdation2() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void testUpdation3() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void testUpdation4() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<SubTestDto> findSubTest2(int limit, int offset) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object findOneSubTest(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SubTestDto findOneSubTest2(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DynamicField> findSubTestDynamicFields() {
		// TODO Auto-generated method stub
		return null;
	}
}