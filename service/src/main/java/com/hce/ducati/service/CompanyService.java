package com.hce.ducati.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.dom4j.DocumentException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.hce.ducati.entity.Company;

import cfca.sadk.algorithm.common.PKIException;

public interface CompanyService {
	public void syn(boolean init) throws IOException, ParseException, JsonParseException, JsonMappingException, DocumentException, InvalidFormatException, PKIException;
	public List<Company> findAll();
	public Company find(Long id);
}
