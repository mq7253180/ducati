package com.hce.ducati.service;

import java.util.List;

import com.hce.ducati.entity.Company;

public interface CompanyService {
	public List<Company> findAll();
	public Company find(Long id);
}