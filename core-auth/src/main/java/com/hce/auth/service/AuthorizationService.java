package com.hce.auth.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hce.auth.entity.User;
import com.hce.auth.o.DSession;

public interface AuthorizationService {
	public DSession getSession(HttpServletRequest request) throws Exception;
	/**
	 * @return: 返回cookie值
	 * @throws Exception
	 */
	public DSession setSession(HttpServletRequest request, User user) throws Exception;
	public void vcode(HttpServletRequest request, HttpServletResponse response, int length) throws Exception;
	public void logout(HttpServletRequest request) throws Exception;
	public String getCachedVcode(HttpServletRequest request) throws Exception;
	public void updateSessionByUserId(Long userId) throws IOException;
	public void updateSessionByRoleId(Long roleId) throws IOException;
}