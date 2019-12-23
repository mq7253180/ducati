package com.hce.ducati.controller;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.MessageProperties;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/amqp")
public class AmqpController {
	private final static ConnectionFactory connectionFactory = new ConnectionFactory();
	@Value("${spring.rabbitmq.host}")
	private String host;
	@Value("${spring.rabbitmq.username}")
	private String username;
	@Value("${spring.rabbitmq.password}")
	private String password;
	private static Connection conn = null;
	private static Channel channel = null;
	private final static String QUEUE_NAME = "ducati.origin";
	private final static String ROUTING_KEY = QUEUE_NAME.substring(QUEUE_NAME.indexOf("_")+1, QUEUE_NAME.length());
	private final static String EXCHANGE_NAME = "ducati.origin";

	@RequestMapping("/send")
	@ResponseBody
	public void send(@RequestParam(required = true, name = "content")String content) throws IOException, TimeoutException {
		Channel channel = null;
		try {
			channel = conn.createChannel(2);
//			channel.basicPublish("", QUEUE_NAME, null, content.getBytes());
//			channel.basicPublish(EXCHANGE_NAME, "xxx", MessageProperties.PERSISTENT_TEXT_PLAIN, content.getBytes());
			channel.basicPublish(EXCHANGE_NAME, "xxx", null, content.getBytes());
		} finally {
			if(channel!=null)
				channel.close();
		}
	}

	@PostConstruct
	public void init() throws IOException, TimeoutException {
		connectionFactory.setHost(host);
		connectionFactory.setUsername(username);
		connectionFactory.setPassword(password);
		log.info("DEFAULT_RQBBITMQ_PORT================{}", connectionFactory.getPort());
		conn = connectionFactory.newConnection();
		channel = conn.createChannel(1);
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body) throws IOException {
				super.handleDelivery(consumerTag, envelope, properties, body);
				log.warn("CONSUMER_MSG======================={}", new String(body, "UTF-8"));
			}
		};
		channel.basicConsume(QUEUE_NAME, true, consumer);
	}

	@PreDestroy
	public void destroy() {
		try {
			if(channel!=null) {
				channel.queuePurge(QUEUE_NAME);
				channel.exchangeDelete(EXCHANGE_NAME);
				channel.close();
			}
			if(conn!=null)
				conn.close();
			log.warn("========================AMQP RELEASED");
		} catch (Exception e) {
			log.error("AMQP_ERR: ", e);
		}
	}
}
