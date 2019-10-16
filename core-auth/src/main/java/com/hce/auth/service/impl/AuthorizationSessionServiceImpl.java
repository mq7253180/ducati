package com.hce.auth.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hce.auth.AuthConstants;
import com.hce.auth.entity.User;
import com.hce.auth.o.DSession;
import com.hce.auth.service.UserService;
import com.hce.global.Constants;

@Service("authorizationSessionServiceImpl")
public class AuthorizationSessionServiceImpl extends AuthorizationAbstract {
	@Override
	protected Object getUserObject(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		return session==null?null:session.getAttribute(Constants.ATTR_SESSION);
	}

	@Override
	public DSession setSession(HttpServletRequest request, User user) {
		String originalJsessionid = user.getJsessionid();
		if(originalJsessionid!=null&&originalJsessionid.length()>0) {//同一user不同客户端登录互踢
			HttpSession httpSession = AuthConstants.SESSIONS.get(originalJsessionid);
			if(httpSession!=null) {
				DSession session = (DSession)httpSession.getAttribute(Constants.ATTR_SESSION);
				if(session.getUser().getId().intValue()==user.getId().intValue())
					httpSession.invalidate();
			}
		}
		HttpSession httpSession = request.getSession();
		String jsessionid = httpSession.getId();
		user.setPassword(null);
		user.setJsessionid(jsessionid);
		DSession session = userService.getSession(user.getId());
		session.setUser(user);
		httpSession.setAttribute(Constants.ATTR_SESSION, session);
		this.updateLastLogined(session.getUser().getId(), jsessionid);
		return session;
	}

	public void logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session!=null) {
			session.invalidate();
		}
	}

	@Override
	protected void saveVcode(HttpServletRequest request, String vcode) {
		request.getSession(true).setAttribute(Constants.ATTR_VCODE, vcode);
	}

	@Override
	public String getCachedVcode(HttpServletRequest request) {
		return this.getCachedStr(request, Constants.ATTR_VCODE);
	}

	private String getCachedStr(HttpServletRequest request, String attrName) {
		HttpSession session = request.getSession();
		if(session!=null) {
			Object obj = session.getAttribute(attrName);
			return obj==null?null:obj.toString();
		}
		return null;
	}

	@Autowired
	private UserService userService;

	@Override
	protected void updateSession(User user) {
		HttpSession httpSession = AuthConstants.SESSIONS.get(user.getJsessionid());
		DSession dSession = userService.getSession(user.getId());
		dSession.setUser(user);
		httpSession.setAttribute(Constants.ATTR_SESSION, dSession);
	}

	@Override
	protected void updateSession(List<User> users) {
		for(User user:users) {
			this.updateSession(user);
		}
	}
}