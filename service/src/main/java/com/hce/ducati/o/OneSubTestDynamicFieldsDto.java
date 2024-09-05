package com.hce.ducati.o;

import java.util.List;

import com.quincy.sdk.annotation.jdbc.DynamicColumnQueryDTO;
import com.quincy.sdk.annotation.jdbc.DynamicFields;
import com.quincy.sdk.annotation.jdbc.Result;

import lombok.Data;

@DynamicColumnQueryDTO
@Data
public class OneSubTestDynamicFieldsDto {
	@DynamicFields
	private List<String> dynamicFieldNames;
	@Result
	private SubTestDto result;
}