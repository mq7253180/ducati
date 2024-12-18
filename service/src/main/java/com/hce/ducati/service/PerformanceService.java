package com.hce.ducati.service;

import java.net.UnknownHostException;
import java.util.List;

import com.hce.ducati.o.UestDto;

public interface PerformanceService {
	public int updateUest(Long id);
	public int singleUpdateUest(Long id);
	public int insertUest();
	public List<UestDto> findUest(int start, int end);
	public UestDto findUest(Long id);
	public String test(int a, String b) throws UnknownHostException;
}