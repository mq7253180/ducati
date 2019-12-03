package com.hce.ducati.client;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface DucatiBound {
	String OUTPUT = "ducati_output";
	String INPUT = "ducati_input";

	@Output(OUTPUT)
	MessageChannel output();

	@Input(INPUT)
	SubscribableChannel input();
}
