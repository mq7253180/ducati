package com.hce.ducati;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.quincy.sdk.DistributedTransactionContext;
import com.quincy.sdk.DistributedTransactionFailure;
import com.quincy.sdk.EmailService;

import lombok.extern.slf4j.Slf4j;

@PropertySource(value = {"classpath:application-core.properties", "classpath:application-auth.properties", "classpath:application-service.properties"})
@Configuration
public class ServiceInitConfiguration {
	@Autowired
	private DistributedTransactionContext transactionContext;
	@Autowired
	private EmailService emailService;

	@PostConstruct
	public void init() {
		transactionContext.setTransactionFailure(new DistributedTransactionFailure() {
			@Override
			public int retriesBeforeInform() {
				return 3;
			}

			@Override
			public void inform(String message) {
				emailService.send("mq7253180@126.com", "转账失败", message, "", null, "UTF-8", null, null);
			}
		});
	}

	@PreDestroy
	private void destroy() {
		
	}
}
