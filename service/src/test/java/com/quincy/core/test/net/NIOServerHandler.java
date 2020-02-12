package com.quincy.core.test.net;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public abstract class NIOServerHandler implements Runnable {
	protected String id;
	private SelectionKey key;
	private NIOServer server;

	protected abstract void handle(SocketChannel socketChannel) throws IOException;
	protected abstract void after(SelectionKey key) throws IOException;

	@Override
	public void run() {
		synchronized(this) {
			while(true) {
				try {
					this.wait();
					SocketChannel socketChannel = (SocketChannel)key.channel();
					this.handle(socketChannel);
					this.after(key);
					this.key = null;
				} catch(Throwable e) {
					e.printStackTrace();
				} finally {
					this.server.returnHandler(this);
				}
			}
		}
	}

	public void trigger(SelectionKey key, NIOServer server) {
		synchronized(this) {
			this.key = key;
			this.server = server;
			this.notifyAll();
		}
	}

	public SelectionKey getKey() {
		return key;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
