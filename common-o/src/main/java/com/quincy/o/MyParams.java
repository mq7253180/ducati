package com.quincy.o;

import java.io.Serializable;

public class MyParams implements Serializable {
	private static final long serialVersionUID = -6777676707992118751L;
	private String xxx;

	public String getXxx() {
		return xxx;
	}
	public MyParams setXxx(String xxx) {
		this.xxx = xxx;
		return this;
	}
}