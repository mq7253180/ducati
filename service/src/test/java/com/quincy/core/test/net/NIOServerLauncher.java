package com.quincy.core.test.net;

import java.util.HashMap;
import java.util.Map;

import com.quincy.core.test.net.impl.NIOServerHandlerHttpImpl;
import com.quincy.core.test.net.impl.NIOServerHandlerNettyImpl;

public class NIOServerLauncher {
	private static Map<Integer, NIOServer> allServers = new HashMap<Integer, NIOServer>();

	private static void launch(int port, int threads, Class<? extends NIOServerHandler> handlerClazz) throws Exception {
		try {
			NIOServer server = new NIOServer(port, threads, handlerClazz);
			synchronized(allServers) {
				allServers.put(port, server);
			}
			server.doServer();
		} catch (Exception e) {
			throw e;
		}
	}

	public static void launchHttp(int port, int threads) throws Exception {
		launch(port, threads, NIOServerHandlerHttpImpl.class);
	}

	public static void launchNetty(int port, int threads) throws Exception {
		launch(port, threads, NIOServerHandlerNettyImpl.class);
	}

	public static void main(String[] args) {
		try {
			NIOServerLauncher.launchHttp(8080, 7);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
