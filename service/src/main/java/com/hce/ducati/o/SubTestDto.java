package com.hce.ducati.o;

import java.util.List;

import com.quincy.sdk.DynamicColumn;
import com.quincy.sdk.annotation.Column;
import com.quincy.sdk.annotation.DTO;
import com.quincy.sdk.annotation.DynamicColumns;

import lombok.Data;

@DTO
@Data
public class SubTestDto {
	@Column("id")
	private String id;
	@Column("eee")
	private String eee;
	@Column("fff")
	private Long fff;
	@DynamicColumns
	private List<DynamicColumn> dynamicFields;
}