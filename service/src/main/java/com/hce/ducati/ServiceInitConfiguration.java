package com.hce.ducati;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.quincy.sdk.DistributedTransactionContext;
import com.quincy.sdk.DistributedTransactionFailure;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@PropertySource(value = {"classpath:application-core.properties", "classpath:application-auth.properties", "classpath:application-service.properties"})
@Configuration
public class ServiceInitConfiguration {
	@Autowired
	private DistributedTransactionContext transactionContext;

	@PostConstruct
	public void init() {
		transactionContext.setTransactionFailure(new DistributedTransactionFailure() {
			@Override
			public int retriesBeforeInform() {
				return 3;
			}

			@Override
			public void inform(String message) {
				log.info("DISTRIBUTED_TRANSACTION_MESSAGE==================Size: {}, Content: \r\n{}", message.length(), message);
			}
		});
	}

	@PreDestroy
	private void destroy() {
		
	}
}
