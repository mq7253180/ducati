package com.quincy.core.test.o;

import java.io.Serializable;

public class Entity implements Serializable {
	private static final long serialVersionUID = 8441842191751665124L;
//	private static final long serialVersionUID = -4341546027315064200L;
	private String aaa;
	public String getAaa() {
		return aaa;
	}
	public void setAaa(String aaa) {
		this.aaa = aaa;
	}
	private String bbb;
	public String getBbb() {
		return bbb;
	}
	public void setBbb(String bbb) {
		this.bbb = bbb;
	}
	/*private String ccc;
	public String getCcc() {
		return ccc;
	}
	public void setCcc(String ccc) {
		this.ccc = ccc;
	}*/
}