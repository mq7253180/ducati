package com.quincy.core.test.net;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public abstract class NIOServerHandlerShort extends NIOServerHandler {
	protected void after(SelectionKey key) throws IOException {
		SocketChannel socketChannel = (SocketChannel)key.channel();
		if(socketChannel!=null)
			socketChannel.close();
	}
}
