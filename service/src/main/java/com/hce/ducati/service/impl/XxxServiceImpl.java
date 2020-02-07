package com.hce.ducati.service.impl;

import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hce.ducati.o.Params;
import com.hce.ducati.service.XxxService;
import com.hce.ducati.service.ZzzService;
import com.quincy.sdk.annotation.Cache;
import com.quincy.sdk.annotation.ZooKeeperInjector;
import com.quincy.sdk.annotation.transaction.DistributedTransactional;
import com.quincy.sdk.annotation.Synchronized;
import com.quincy.sdk.annotation.DeprecatedSynchronized;
import com.quincy.sdk.annotation.DurationLog;
import com.quincy.sdk.zookeeper.Context;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class XxxServiceImpl implements XxxService {
	@Autowired
	private Context zkContext;
	@Value("${spring.application.name}")
	private String appName;

	@DurationLog
	@Cache(expire = 30)
//	@Synchronized("xxx")
//	@DeprecatedSynchronized("xxx")
	@ZooKeeperInjector
	@Override
	public String testZk(String arg, ZooKeeper zk, long duration) throws KeeperException, InterruptedException {
//		String result = zk.create(path, arg.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
		/*List<String> list = zk.getChildren(zkContext.getSynPath(), false);
		for(String path:list) {
			log.info("---------------{}", path);
		}*/
		Thread.sleep(duration);
//		zk.delete("/quincy-ducati/synchronization/xxx/execution1", -1);
		return "sss";
	}

	@Autowired
	private ZzzService zzzService;

	@DistributedTransactional
	@Override
	public String testTx(String s, Params p) {
		log.info("====================DO_TX");
		Params[] pp = new Params[5];
		pp[0] = p;
		pp[2] = p;
		pp[4] = p;
		zzzService.updateDB(s, p);
		zzzService.callHttp(321, new int[] {1, 5, 8}, pp);
		return "XXX";
	}
}