package com.hce.ducati.dto;

import com.quincy.sdk.annotation.Column;
import com.quincy.sdk.annotation.DTO;

import lombok.Data;

@DTO
@Data
public class UserDto {
	@Column("id")
	private Long id;
	@Column("id_str")
	private String idStr;
	@Column("name")
	private String name;
	@Column("nickname")
	private String nickName;
	@Column("mobile_phone")
	private String mobilePhone;
	@Column("creation_time")
	private String creationTime;
}