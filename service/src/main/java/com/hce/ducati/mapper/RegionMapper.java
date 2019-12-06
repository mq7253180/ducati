package com.hce.ducati.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.quincy.sdk.entity.Region;

@Repository
public interface RegionMapper {
	public List<Region> find(@Param("suffix")String suffix);
}
