package com.quincy.core.test.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class NIOServer extends IOAbstract {
	private LinkedList<NIOServerHandler> idleThreads = new LinkedList<NIOServerHandler>();
	private Map<NIOServerHandler, Thread> allThreads = new HashMap<NIOServerHandler, Thread>();
	private int port = -1;
	protected boolean running = true;

	public NIOServer(int port, int threads, Class<? extends NIOServerHandler> clazzHandler) throws ReflectiveOperationException  {
		this.port = port;
		for(int i=0;i<threads;i++) {
			Object obj = clazzHandler.newInstance();
			NIOServerHandler handler = (NIOServerHandler)obj;
			handler.setId("第"+(i+1)+"个线程");
			Thread thread = new Thread(handler);
			this.idleThreads.add(handler);
			this.allThreads.put(handler, thread);
		}
	}

	public void doServer() throws IOException, InterruptedException {
		for(Thread thread:allThreads.values()) {
			thread.start();
		}
		Selector selector = null;
		ServerSocketChannel serverSocketChannel = null;
		try {
			serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.socket().setReuseAddress(true);
			serverSocketChannel.socket().bind(new InetSocketAddress(this.port));
			serverSocketChannel.configureBlocking(false);
			selector = Selector.open();
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
			while(running) {
				int select = selector.select();
				if(select>0) {
					Set<SelectionKey> set = selector.selectedKeys();
					Iterator<SelectionKey> it = set.iterator();
					while(it.hasNext()) {
						SelectionKey key = it.next();
						it.remove();
//						System.out.println(set.hashCode()+"--"+set.size()+"=========="+key.isValid()+"-"+key.isConnectable()+"-"+key.isAcceptable()+"-"+key.isReadable()+"-"+key.isWritable());
						SelectableChannel selectableChannel = key.channel();
						SocketChannel socketChannel = null;
						if(key.isAcceptable()) {
							socketChannel = ((ServerSocketChannel)selectableChannel).accept();
							socketChannel.configureBlocking(false);
							socketChannel.register(selector, SelectionKey.OP_READ);
						} else if(key.isReadable()) {
							key.cancel();
							this.handle(key);
						} else if(key.isWritable()) {
							
						}
					}
				}
			}
		} catch (IOException e) {
			throw e;
		} finally {
			if(selector!=null)
				selector.close();
			if(serverSocketChannel!=null)
				serverSocketChannel.close();
		}
	}

	private void handle(SelectionKey key) throws InterruptedException {
		NIOServerHandler handler = null;
		synchronized(idleThreads) {
			while(idleThreads.isEmpty()) {
				idleThreads.wait();
			}
			handler = idleThreads.removeFirst();
		}
		Thread thread = this.allThreads.get(handler);
		while(handler.getKey()!=null||thread.getState()!=Thread.State.WAITING) {
			Thread.sleep(10);
		}
		handler.trigger(key, this);
	}

	public void returnHandler(NIOServerHandler handler) {
		synchronized(idleThreads) {
			this.idleThreads.addLast(handler);
			this.idleThreads.notifyAll();
		}
	}

}
