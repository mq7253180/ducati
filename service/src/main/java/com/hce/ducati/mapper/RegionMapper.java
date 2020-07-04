package com.hce.ducati.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.quincy.sdk.entity.Region;

@Repository
public interface RegionMapper {
	public List<Region> find(@Param("suffix")String suffix);
	public List<Region> findByRange(@Param("start")Integer start, @Param("end")Integer end);
	public Region findByRangeAndId(@Param("id")Long id, @Param("start")Integer start, @Param("end")Integer end);
	public Region findById(@Param("id")Long id);
	public Region findByCnName(@Param("cnName")String cnName);
	public int update(@Param("id")Long id, @Param("cnName")String cnName);
	public int update2(@Param("enName")String enName, @Param("cnName")String cnName);
	public int update3();
	public int update4(@Param("enName")String enName);
	public int update4x(@Param("enName")String enName);
	public int update4xx(@Param("enName")String enName);
	public int update5(@Param("enName")String enName);
}