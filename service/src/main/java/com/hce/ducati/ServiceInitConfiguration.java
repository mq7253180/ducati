package com.hce.ducati;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import com.quincy.auth.AuthContext;
import com.quincy.auth.AuthHandler;
import com.quincy.auth.interceptor.OAuth2ResourceInterceptor;
import com.quincy.core.web.SignatureInterceptor;
import com.quincy.core.web.SignaturePubKeyExchanger;
import com.quincy.sdk.DTransactionContext;
import com.quincy.sdk.DTransactionFailure;
import com.quincy.sdk.EmailService;
import com.quincy.sdk.RedisProcessor;
import com.quincy.sdk.WebMvcConfiguration;

import jakarta.servlet.http.HttpServletRequest;

@PropertySource(value = {"classpath:application-core.properties", "classpath:application-auth.properties", "classpath:application-service.properties", "classpath:application-oauth2.properties"})
@Configuration("sssiiiccc")
public class ServiceInitConfiguration extends WebMvcConfiguration {
	@Autowired
	private DTransactionContext transactionContext;
	@Autowired
	private EmailService emailService;
	@Autowired
	private AuthContext authContext;
	@Autowired
	private OAuth2ResourceInterceptor oauth2ResourceInterceptor;

	@Scheduled(cron = "0 0/2 * * * ?")
	public void retry() throws ClassNotFoundException, NoSuchMethodException, SecurityException, IOException, InterruptedException {
		transactionContext.compensate("xxx");
	}

	@PostConstruct
	public void init() throws Exception {
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
			/**
			 * 只有auth.loginRequired.root=true时才有用
			 */
			@Override
			public Map<String, ?> rootViewObjects(HttpServletRequest request) throws Exception {
				return null;
			}
		});
	}

	@Autowired
	private RedisProcessor redisProcessor;

	@Override
	protected void addInterceptors(InterceptorRegistry registry) {
		super.addInterceptors(registry);
		registry.addInterceptor((HandlerInterceptor)redisProcessor).addPathPatterns("/**");
		registry.addInterceptor(new SignatureInterceptor(new SignaturePubKeyExchanger() {
			@Override
			public String getPublicKeyById(String id) {
//				return null;
				return "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCMYDMqMFSJL+nUMzF7MQjCYe/Y3P26wjVn90CdrSE8H9Ed4dg0/BteWn5+ZK65DwWev2F79hBIpprPrtVe+wplCTkpyR+mPiNL+WKkvo7miMegRYJFZLvh9QrFuDzMJZ+rAiu4ldxkVB0CMKfYEWbukKGmAinxVAqUr/HcW2mWjwIDAQAB";
			}
		})).addPathPatterns("/**");
		registry.addInterceptor(oauth2ResourceInterceptor).addPathPatterns("/**");
	}

	@PreDestroy
	private void destroy() {
		
	}

	@Bean("ducatiThreadPoolExecutor")
	public ThreadPoolExecutor threadPoolExecutor() {
		BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<Runnable>(100);
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 20, 60, TimeUnit.SECONDS, blockingQueue);
		return threadPoolExecutor;
	}
}