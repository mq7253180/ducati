package com.hce.ducati.o;

import java.io.Serializable;
import java.util.List;

import com.quincy.sdk.annotation.DynamicColumnQueryDTO;
import com.quincy.sdk.annotation.DynamicFields;
import com.quincy.sdk.annotation.Result;

import lombok.Data;

@DynamicColumnQueryDTO
@Data
public class SubTestDynamicFieldsDto implements Serializable {
	private static final long serialVersionUID = 7953404612544688506L;
	@DynamicFields
	private List<String> dynamicFieldNames;
	@Result
	private List<SubTestDto> result;
}