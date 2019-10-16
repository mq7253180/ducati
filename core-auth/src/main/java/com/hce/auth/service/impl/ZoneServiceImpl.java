package com.hce.auth.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hce.auth.dao.CountryRepository;
import com.hce.auth.entity.Country;
import com.hce.auth.service.ZoneService;
import com.hce.global.annotation.Cache;

@Service
public class ZoneServiceImpl implements ZoneService {
	@Autowired
	private CountryRepository countryRepository;

	@Cache(expire = 3600)
	@Override
	public List<Country> findCountries() {
		return countryRepository.findAll();
	}
}
