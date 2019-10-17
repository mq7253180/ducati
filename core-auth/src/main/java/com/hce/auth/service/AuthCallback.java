package com.hce.auth.service;

import com.hce.auth.o.DSession;

public interface AuthCallback {
	public void updateLastLogined(String jsessionid);
	public DSession createSession();
}
