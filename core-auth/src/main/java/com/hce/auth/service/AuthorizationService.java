package com.hce.auth.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hce.auth.o.DSession;
import com.hce.auth.o.User;

public interface AuthorizationService {
	public DSession getSession(HttpServletRequest request) throws Exception;
	public void setSession(String jsessionid, String originalJsessionid, Long userId, AuthCallback callback) throws IOException, ClassNotFoundException;
	/**
	 * @return: 返回cookie值
	 * @throws Exception
	 */
	public String setSession(HttpServletRequest request, String originalJsessionid, Long userId, AuthCallback callback) throws Exception;
	public void vcode(HttpServletRequest request, HttpServletResponse response, int length) throws Exception;
	public void logout(HttpServletRequest request) throws Exception;
	public String getCachedVcode(HttpServletRequest request) throws Exception;
	public abstract void updateSession(User user) throws IOException;
	public abstract <T extends User> void updateSession(List<T> users) throws IOException;
}
