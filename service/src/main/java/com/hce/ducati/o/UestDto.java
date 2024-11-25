package com.hce.ducati.o;

import java.io.Serializable;

import com.quincy.sdk.annotation.jdbc.Column;
import com.quincy.sdk.annotation.jdbc.DTO;

import lombok.Data;

@DTO
@Data
public class UestDto implements Serializable {
	private static final long serialVersionUID = 3532812116338857252L;
	@Column("id")
	private String id;
	@Column("ccc")
	private String ccc;
	@Column("ddd")
	private Long ddd;
}