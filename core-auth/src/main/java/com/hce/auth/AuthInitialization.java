package com.hce.auth;

import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.hce.auth.dao.PermissionRepository;
import com.hce.auth.entity.Permission;

@PropertySource("classpath:application-auth.properties")
@Configuration
public class AuthInitialization {
	@PostConstruct
	public void init() {
		this.loadPermissions();
	}

	@PreDestroy
	private void destroy() {
		
	}

	@Autowired
	private  PermissionRepository permissionRepository;

	private void loadPermissions() {
		List<Permission> permissoins = permissionRepository.findAll();
		AuthConstants.PERMISSIONS = new HashMap<String, String>(permissoins.size());
		for(Permission permission:permissoins) {
			AuthConstants.PERMISSIONS.put(permission.getName(), permission.getDes());
		}
	}

	@Bean
    public HttpSessionListener httpSessionListener() {
		return new HttpSessionListener() {
			@Override
			public void sessionCreated(HttpSessionEvent hse) {
				AuthConstants.SESSIONS.put(hse.getSession().getId(), hse.getSession());
			}

			@Override
			public void sessionDestroyed(HttpSessionEvent hse) {
				AuthConstants.SESSIONS.remove(hse.getSession().getId());
			}
		};
	}
}
