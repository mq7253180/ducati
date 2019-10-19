package com.hce.auth.service;

import com.hce.auth.o.User;

public interface AuthCallback {
	public void updateLastLogined(String jsessionid);
	public User getUser();
}
