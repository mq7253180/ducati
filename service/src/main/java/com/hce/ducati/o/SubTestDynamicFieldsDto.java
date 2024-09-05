package com.hce.ducati.o;

import java.io.Serializable;
import java.util.List;

import com.quincy.sdk.annotation.jdbc.DynamicColumnQueryDTO;
import com.quincy.sdk.annotation.jdbc.DynamicFields;
import com.quincy.sdk.annotation.jdbc.Result;

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