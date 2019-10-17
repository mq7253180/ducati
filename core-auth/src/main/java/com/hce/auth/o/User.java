package com.hce.auth.o;

import java.util.Date;

public interface User {
	public void setId(Long id);
	public Long getId();
	public void setCreationTime(Date creationTime);
	public Date getCreationTime();
	public void setUsername(String username);
	public String getUsername();
	public void setName(String name);
	public String getName();
	public void setPassword(String password);
	public String getPassword();
	public void setEmail(String email);
	public String getEmail();
	public void setMobilePhone(String mobilePhone);
	public String getMobilePhone();
	public void setJsessionid(String jsessionid);
	public String getJsessionid();
	public void setLastLogined(Date lastLogined);
	public Date getLastLogined();
}
