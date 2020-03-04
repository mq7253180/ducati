package com.hce.ducati;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.quincy.sdk.DTransactionContext;
import com.quincy.sdk.DTransactionFailure;
import com.quincy.sdk.EmailService;
import com.quincy.sdk.WebMvcConfiguration;

@PropertySource(value = {"classpath:application-core.properties", "classpath:application-auth.properties", "classpath:application-service.properties"})
@Configuration
public class ServiceInitConfiguration extends WebMvcConfiguration {
	@Autowired
	private DTransactionContext transactionContext;
	@Autowired
	private EmailService emailService;

	@Scheduled(cron = "0 0/2 * * * ?")
	public void retry() throws JsonMappingException, ClassNotFoundException, JsonProcessingException, NoSuchMethodException, SecurityException {
		transactionContext.compensate();
	}

	@PostConstruct
	public void init() {
		transactionContext.setTransactionFailure(new DTransactionFailure() {
			@Override
			public int retriesBeforeInform() {
				return 0;
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
