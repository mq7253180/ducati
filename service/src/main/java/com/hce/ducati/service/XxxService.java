package com.hce.ducati.service;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

import com.hce.ducati.o.Params;

public interface XxxService {
	public String testZk(String arg, ZooKeeper zk, long duration) throws KeeperException, InterruptedException;
	public String testTx(String s, Params p);
}
