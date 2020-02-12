package com.quincy.core.test.net.impl;

import java.io.IOException;
import java.nio.channels.SocketChannel;

import com.quincy.core.test.net.NIOServerHandlerLong;

public class NIOServerHandlerNettyImpl extends NIOServerHandlerLong {
	protected void handle(SocketChannel socketChannel) throws IOException {
		boolean first = true;
		if(first) {
			this.doHandShake(socketChannel);
			first = false;
		} else {
			
		}
	}
	
	private void doHandShake(SocketChannel socketChannel) {
		
	}

}
