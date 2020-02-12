package com.quincy.core.test.net;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import com.quincy.core.test.net.impl.BIOServerHttpImpl;
import com.quincy.core.test.net.impl.BIOServerNettyImpl;

public class BIOServerLauncher {
	private static Map<Integer, BIOServer> allServers = new HashMap<Integer, BIOServer>();

	private static void launch(int port, int threads, Class<? extends BIOServer> clazz) throws Exception {
		try {
			Constructor<? extends BIOServer> constructor = clazz.getConstructor(new Class[]{int.class});
			Object object = constructor.newInstance(new Object[]{port});
			BIOServer server = (BIOServer)object;
			allServers.put(port, server);
			server.start(threads);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static void stop(int port) throws IOException {
		allServers.get(port).stop();
	}

	public static void launchHttp(int port, int threads) throws Exception {
		launch(port, threads, BIOServerHttpImpl.class);
	}

	public static void launchNetty(int port, int threads) throws Exception {
		launch(port, threads, BIOServerNettyImpl.class);
	}

	public static void main(String[] args) {
		try {
			BIOServerLauncher.launchHttp(8443, 5);
//			BIOServerLauncher.stop(8080);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
