package com.hce.ducati;

import java.io.IOException;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.List;
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

import com.quincy.auth.entity.Permission;
import com.quincy.auth.entity.Role;
import com.quincy.core.PublicKeyGetter;
import com.quincy.sdk.AuthServerActions;
import com.quincy.sdk.DTransactionOptRegistry;
import com.quincy.sdk.DTransactionFailure;
import com.quincy.sdk.EmailService;
import com.quincy.sdk.PwdRestEmailInfo;
import com.quincy.sdk.RootControllerHandler;
import com.quincy.sdk.TempPwdLoginEmailInfo;
import com.quincy.sdk.o.Menu;
import com.quincy.sdk.o.User;

import jakarta.servlet.http.HttpServletRequest;

@PropertySource(value = {"classpath:application-core.properties", "classpath:application-auth.properties", "classpath:application-service.properties", "classpath:application-sensitiveness.properties"})
@Configuration("sssiiiccc")
public class ServiceInitConfiguration {
	@Autowired
	private DTransactionOptRegistry dTransactionOptRegistry;
	@Autowired
	private EmailService emailService;

//	@Scheduled(cron = "0 0/2 * * * ?")
	public void retry() throws ClassNotFoundException, NoSuchMethodException, SecurityException, IOException, InterruptedException {
		dTransactionOptRegistry.resume("xxx");
	}

	@PostConstruct
	public void init() throws Exception {
		dTransactionOptRegistry.setTransactionFailure(new DTransactionFailure() {
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

	@Bean
	public RootControllerHandler rootControllerHandler() {
		return new RootControllerHandler() {
			@Override
			public boolean loginRequired() {
				return true;
			}

			@Override
			public Map<String, ?> viewObjects(HttpServletRequest request) throws Exception {
				return null;
			}
		};
	}

	@Bean
	public PublicKeyGetter publicKeyGetter() {
		return new PublicKeyGetter() {
			@Override
			public String getById(String id) {
//				return null;
				return "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCMYDMqMFSJL+nUMzF7MQjCYe/Y3P26wjVn90CdrSE8H9Ed4dg0/BteWn5+ZK65DwWev2F79hBIpprPrtVe+wplCTkpyR+mPiNL+WKkvo7miMegRYJFZLvh9QrFuDzMJZ+rAiu4ldxkVB0CMKfYEWbukKGmAinxVAqUr/HcW2mWjwIDAQAB";
			}
		};
	}

	@Bean
	public TempPwdLoginEmailInfo tempPwdLoginEmailInfo() {
		return new TempPwdLoginEmailInfo() {
			@Override
			public String getSubject() {
				return "临时密码";
			}

			@Override
			public String getContent() {
				return "临时密码为: {0}, {1}分钟内有效";
			}
		};
	}

	@Bean
	public PwdRestEmailInfo pwdRestEmailInfo() {
		return new PwdRestEmailInfo() {
			@Override
			public String getSubject() {
				return "密码重置";
			}

			@Override
			public String getContent(String uri, int timeoutSeconds) {
				return new StringBuilder(500)
						.append("<html>")
						.append("\n\t<head>")
						.append("\n\t\t<meta charset=\"UTF-8\">")
						.append("\n\t\t<title>密码重置</title>")
						.append("\n\t</head>")
						.append("\n\t<body>")
						.append("\n\t\t<font color=\"purple\">请</font>")
						.append("<a href=\"")
						.append(uri)
						.append("\">点击</a>")
						.append("<font color=\"purple\">继续操作，")
						.append(timeoutSeconds/60)
						.append("分钟内失效，如果无法自动打开，请复制此链接至浏览器地址栏敲回车: ")
						.append(uri)
						.append("</font>")
						.append("\n\t</body>")
						.append("\n</html>").toString();
			}
		};
	}

	@Bean
	public AuthServerActions authActions() {
		return new AuthServerActions() {
			@Override
			public void sms(String mobilePhone, String vcode, int expireMinuts) {
				System.out.println(MessageFormat.format("已通过阿里云短信接口向{0}发送验证码: {1}, 失效时间{2}分钟", mobilePhone, new String(vcode), expireMinuts));
			}

			@Override
			public List<Role> findRoles(Long userId) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public List<Permission> findPermissions(Long userId) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public List<Menu> findMenus(Long userId) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Object userExt(User user) {
				// TODO Auto-generated method stub
				return null;
			}
		};
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