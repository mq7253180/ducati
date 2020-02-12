package com.quincy.core.test.net;

import java.nio.channels.SelectionKey;

public abstract class NIOServerHandlerLong extends NIOServerHandler {
	protected boolean running = true;

	protected void after(SelectionKey key) {
		key.interestOps(SelectionKey.OP_ACCEPT);
	}

}
