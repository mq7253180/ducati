package com.quincy.core.test.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class NIOClient {
	public void test(String request) throws IOException {
		Selector selector = null;
		Socket socket = null;
		SocketChannel socketChannel = null;
		try {
			socket = new Socket("192.168.76.23", 8080);
			socketChannel = socket.getChannel();

//			SocketAddress socketAddress = new InetSocketAddress("192.168.1.101", 8080);
//			socketChannel = SocketChannel.open();
//			socketChannel.connect(socketAddress);

//			selector = Selector.open();
//			socketChannel.configureBlocking(false);
//			socketChannel.register(selector, SelectionKey.OP_CONNECT);
			
			byte[] bytes = request.getBytes();
			ByteBuffer buffer = ByteBuffer.wrap(bytes);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			socketChannel.write(buffer);
			socketChannel.socket().shutdownOutput();

			// Register accepts on the server socket with the selector. This
			// step tells the selector that the socket wants to be put on the
			// ready list when accept operations occur, so allowing multiplexed
			// non-blocking I/O to take place.
//			socketChannel.register(selector, SelectionKey.OP_ACCEPT);
			// Here's where everything happens. The select method will
			// return when any operations registered above have occurred, the
			// thread has been interrupted, etc.
			/*while (selector.select() > 0) {
				// Someone is ready for I/O, get the ready keys
				Iterator<SelectionKey> it = selector.selectedKeys().iterator();
				// Walk through the ready keys collection and process date requests.
				while (it.hasNext()) {
					SelectionKey readyKey = it.next();
					it.remove();
					// The key indexes into the selector so you
					// can retrieve the socket that's ready for I/O
					String rs = handleChannel((ServerSocketChannel)readyKey.channel());
					System.out.println("------------"+rs);
				}
			}*/
		} catch (IOException e) {
			throw e;
		} finally {
			if(socketChannel!=null)
				socketChannel.close();
			if(socket!=null)
				socket.close();
		}
	}

	public static void main(String[] args) {
		NIOClient client = new NIOClient();
		String request = "a=a1&b=b2";
//		String request = "stop";
		try {
			client.test(request);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
