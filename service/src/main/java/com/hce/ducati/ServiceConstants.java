package com.hce.ducati;

import java.util.ArrayList;
import java.util.List;

public class ServiceConstants {
	public final static int PAGE_SIZE = 5;
	public final static List<String> CURRENCIES = new ArrayList<String>(5);
	static {
		CURRENCIES.add("USD");
		CURRENCIES.add("EUR");
		CURRENCIES.add("GBP");
		CURRENCIES.add("JPY");
		CURRENCIES.add("HKD");
		CURRENCIES.add("CNY");
		CURRENCIES.add("CNH");
	}
}
