package com.hce.ducati.o;

import lombok.Data;

@Data
public class RegionDto {
	private Long id;
	private String enName;
	private String cnName;
	private String telPrefix;
	private String code;
	private String code2;
	private String currency;
	private String locale;
}