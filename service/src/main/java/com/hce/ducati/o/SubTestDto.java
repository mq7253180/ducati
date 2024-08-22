package com.hce.ducati.o;

import java.io.Serializable;
import java.util.List;

import com.quincy.sdk.DynamicColumn;
import com.quincy.sdk.annotation.Column;
import com.quincy.sdk.annotation.DTO;
import com.quincy.sdk.annotation.DynamicColumns;

import lombok.Data;

@DTO
@Data
public class SubTestDto implements Serializable {
	private static final long serialVersionUID = -1603705627142574025L;
	@Column("id")
	private String id;
	@Column("eee")
	private String eee;
	@Column("fff")
	private Long fff;
	@DynamicColumns
	private List<DynamicColumn> dynamicFields;
}