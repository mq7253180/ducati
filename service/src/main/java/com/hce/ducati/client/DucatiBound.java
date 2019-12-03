package com.hce.ducati.client;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface DucatiBound {
	String NAME = "ducati.stream";

	@Output(NAME)
	MessageChannel output();
}
