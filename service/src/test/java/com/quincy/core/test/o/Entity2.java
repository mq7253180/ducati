package com.quincy.core.test.o;

import java.io.Serializable;

public class Entity2 implements Serializable {
	private static final long serialVersionUID = -4341546027315064200L;
	private String aaa;
	private String bbb;
	public String getAaa() {
		return aaa;
	}
	public void setAaa(String aaa) {
		this.aaa = aaa;
	}
	public String getBbb() {
		return bbb;
	}
	public void setBbb(String bbb) {
		this.bbb = bbb;
	}
}