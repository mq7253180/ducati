package com.hce.ducati.o;

import java.util.List;

import com.quincy.sdk.Result;
import com.quincy.sdk.entity.Region;

public class RegionResultDTO extends Result {
	private List<Region> data;

	public List<Region> getData() {
		return data;
	}
	public void setData(List<Region> data) {
		this.data = data;
	}
}
