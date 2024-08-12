package com.hce.ducati.o;

import java.util.List;

import com.quincy.sdk.annotation.DynamicColumnQueryDTO;
import com.quincy.sdk.annotation.DynamicFields;
import com.quincy.sdk.annotation.Result;

import lombok.Data;

@DynamicColumnQueryDTO
@Data
public class OneSubTestDynamicFieldsDto {
	@DynamicFields
	private List<String> dynamicFieldNames;
	@Result
	private SubTestDto result;
}