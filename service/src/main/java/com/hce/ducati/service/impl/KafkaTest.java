package com.hce.ducati.service.impl;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

public class KafkaTest implements Partitioner {

	public static void main(String[] args) {
		pub();
//		cosume();
	}

	private final static String TOPIC_NAME = "aaa";
	private final static String KEY = "bcde";

	public static void pub() {
		Properties p = new Properties();
		p.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, "47.93.89.0:9092,47.93.89.0:9092");
//		p.put(ProducerConfig.ACKS_CONFIG, "0");
//		p.put(CommonClientConfigs.RETRIES_CONFIG, "3");
//		p.put(ProducerConfig.LINGER_MS_CONFIG, "1");
//		p.put(ProducerConfig.BUFFER_MEMORY_CONFIG, "33554432");
//		p.put(CommonClientConfigs.REQUEST_TIMEOUT_MS_CONFIG, "180000");
		p.put(ProducerConfig.BATCH_SIZE_CONFIG, "1");
		p.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		p.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
//		p.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, KafkaTest.class.getName());
		Producer<String, String> producer = new KafkaProducer<String, String>(p);
		for(int i=0;i<8;i++) {
			producer.send(new ProducerRecord<String, String>(TOPIC_NAME, KEY, "sadfasdfsa"+i), new Callback() {
				@Override
				public void onCompletion(RecordMetadata metadata, Exception exception) {
					if(exception==null) {
						System.out.println("SUCCESS======partition: "+metadata.partition()+"---offset: "+metadata.offset());
					} else {
						exception.printStackTrace();
					}
				}
			});
			System.out.println("-------------"+i);
		}
		producer.close();
		System.out.println("==============Finished");
	}

	public static void cosume() {
		Properties p = new Properties();
		p.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "47.93.89.0:9092");
		p.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
		p.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
		p.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		p.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		p.put(ConsumerConfig.GROUP_ID_CONFIG, "quincy_test");
		p.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
//		p.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, "");
		Consumer<String, String>  consumer = new KafkaConsumer<String, String>(p);
		/*
		consumer.subscribe(Collections.singletonList(TOPIC_NAME), new ConsumerRebalanceListener() {
			@Override
			public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
				System.out.println("onPartitionsRevoked============"+partitions.size()+"---"+consumer.beginningOffsets(partitions));
				for(TopicPartition partition:partitions) {
					System.out.println(partition.topic()+"-"+partition.partition());
				}
			}

			@Override
			public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
				System.out.println("onPartitionsAssigned============"+partitions.size()+"---"+consumer.beginningOffsets(partitions));
				for(TopicPartition partition:partitions) {
					System.out.println(partition.topic()+"-"+partition.partition());
				}
			}
		});
		*/
		consumer.assign(Collections.singletonList(new TopicPartition(TOPIC_NAME, 4)));
		Duration timeout = Duration.ofSeconds(3);
		ConsumerRecords<String, String> records = consumer.poll(timeout);
		int i = 0;
		for(ConsumerRecord<String, String> r:records) {
			System.out.println((i++)+"-"+r.key()+"---"+r.value()+"---"+r.partition()+"---"+r.offset());
		}
		consumer.commitSync();
		consumer.close();
	}

	@Override
	public void configure(Map<String, ?> configs) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
		// TODO Auto-generated method stub
		return 9;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}
}
