package com.quincy.core.test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

//import com.rabbitmq.client.Channel;
//import com.rabbitmq.client.ConfirmListener;
//import com.rabbitmq.client.Connection;
//import com.rabbitmq.client.ConnectionFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RabbitMQTest {
//	private final static String QUEUE_NAME = "benelli.test.pull";
	private final static String QUEUE_NAME = "benelli.test.push";
	private final static String EXCHANGE_NAME = "benelli.direct";
	private final static String ROUTING_KEY = QUEUE_NAME.substring(QUEUE_NAME.lastIndexOf(".")+1, QUEUE_NAME.length());
//	private final static String EXCHANGE_NAME = "benelli.fanout";
//	private final static String ROUTING_KEY = "xxx";
//	private final static String EXCHANGE_NAME = "benelli.topic";
//	private final static String ROUTING_KEY = "benelli.test.#";
//	private final static ConnectionFactory connectionFactory = new ConnectionFactory();
/*
	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		connectionFactory.setHost("47.93.89.0");
		connectionFactory.setUsername("guest");
		connectionFactory.setPassword("guest");
		Connection conn = null;
		Channel channel = null;
		try {
			conn = connectionFactory.newConnection();
			channel = conn.createChannel();
			//开启发送方确认模式
			channel.confirmSelect();
			//获取broker传回的信息
			channel.addConfirmListener(new ConfirmListener() {
				@Override
				public void handleAck(long deliveryTag, boolean multiple) throws IOException {
					log.warn("======================已确认消息，标识：{}，多个消息：{}", deliveryTag, multiple);
				}

				@Override
				public void handleNack(long deliveryTag, boolean multiple) throws IOException {
					log.warn("======================未确认消息，标识：{}", deliveryTag);
				}
			});
			boolean waitForConfirms = true;
			do {
				if(!waitForConfirms)
					Thread.sleep(500);
				channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, "wwwttt".getBytes());
			} while(!(waitForConfirms = channel.waitForConfirms()));
			//批量Confirm模式
			channel.waitForConfirmsOrDie(); //直到所有信息都发布，只要有一个未确认就会IOException
			log.warn("======================全部执行完成");
		} finally {
			if(channel!=null)
				channel.close();
			if(conn!=null)
				conn.close();
		}
	}
*/
}