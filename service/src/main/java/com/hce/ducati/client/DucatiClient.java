package com.hce.ducati.client;

//import org.springframework.amqp.core.MessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
//import org.springframework.cloud.stream.messaging.Processor;
//import org.springframework.cloud.stream.messaging.Sink;
//import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
//import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.GenericMessage;

import com.hce.ducati.o.Account2O;
import com.hce.ducati.o.AccountO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableBinding({DucatiBound.class})
public class DucatiClient {
	private final static String CHANNEL_NAME = "myChannel";
	@Autowired
	private DucatiBound source;
	/*@Autowired
	@Qualifier(CHANNEL_NAME)
	private MessageChannel output;*/

	@StreamListener(DucatiBound.NAME)
	public void sink(Account2O o) {
		log.warn("SINK_HANDLER===========ID: {}---------AMOUNT: {}", o.getId(), o.getAmount());
	}

//	@StreamListener(Processor.INPUT)
	@SendTo(DucatiBound.NAME)
	public AccountO process(AccountO o) {
		log.warn("PROCESSOR_HANDLER===========ID: {}---------AMOUNT: {}", o.getId(), o.getAmount());
		return o;
	}

	public String deduct(AccountO o) {
		Message<AccountO> m = MessageBuilder.withPayload(o).build();
		boolean r1 = source.output().send(m);
//		boolean r2 = output.send(m);
		return r1+", ";
	}

	/*@Bean
	@InboundChannelAdapter(value = Source.OUTPUT, poller = @Poller(fixedDelay = "10", maxMessagesPerPoll = "1"))
	public MessageSource<String> timerMessageSource() {
		return () -> new GenericMessage<>("Hello Spring Cloud Stream");
	}*/

	/*@Transformer(inputChannel = Processor.INPUT, outputChannel = Processor.OUTPUT)
	public Account2O transform(Account2O o) {
//		log.warn("TRANSFORM_HANDLER===========ID: {}---------AMOUNT: {}", o.getId(), o.getAmount());
		return o;
	}*/
}