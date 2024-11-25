package com.hce.ducati.service;

import java.util.List;

import com.hce.ducati.o.UestDto;

public interface PerformanceService {
	public int updateUest(Long id);
	public int insertUest();
	public List<UestDto> findUest(int start, int end);
	public UestDto findUest(Long id);
}