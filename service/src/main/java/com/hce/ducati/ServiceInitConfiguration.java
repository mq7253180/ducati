package com.hce.ducati;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.quincy.auth.AuthContext;
import com.quincy.auth.AuthHandler;
import com.quincy.sdk.DTransactionContext;
import com.quincy.sdk.DTransactionFailure;
import com.quincy.sdk.EmailService;
import com.quincy.sdk.RedisProcessor;
import com.quincy.sdk.WebMvcConfiguration;

@PropertySource(value = {"classpath:application-core.properties", "classpath:application-auth.properties", "classpath:application-service.properties"})
@Configuration
public class ServiceInitConfiguration extends WebMvcConfiguration {
	@Autowired
	private DTransactionContext transactionContext;
	@Autowired
	private EmailService emailService;
	@Autowired
	private AuthContext authContext;

	@Scheduled(cron = "0 0/2 * * * ?")
	public void retry() throws ClassNotFoundException, NoSuchMethodException, SecurityException, IOException {
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
		authContext.setAuthHandler(new AuthHandler() {
			@Override
			public ModelAndView indexView(HttpServletRequest request, HttpServletResponse response) {
				return new ModelAndView("/index");
			}
		});
	}

	@Autowired
	private RedisProcessor redisProcessor;

	@Override
	protected void addInterceptors(InterceptorRegistry registry) {
		super.addInterceptors(registry);
		registry.addInterceptor((HandlerInterceptorAdapter)redisProcessor).addPathPatterns("/**");
	}

	@PreDestroy
	private void destroy() {
		
	}
}