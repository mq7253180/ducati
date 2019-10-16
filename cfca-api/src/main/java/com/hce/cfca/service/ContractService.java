package com.hce.cfca.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import org.dom4j.DocumentException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import cfca.sadk.algorithm.common.PKIException;
import cfca.trustsign.common.vo.cs.ContractVO;

public interface ContractService {
	public String getDownloadUrl(String contractNo);
	public cfca.trustsign.common.vo.multiplat.ContractVO query(String no) throws MalformedURLException, PKIException, IOException, DocumentException;
	public ContractVO sign(String contractName, InputStream in, String fileName, int isSign, String[][] enterpriInfos, String[] userInfo) throws JsonParseException, JsonMappingException, IOException, PKIException, DocumentException;
	public ContractVO sign(String contractName, File file, int isSign, String[][] enterpriInfos, String[] userInfo) throws JsonParseException, JsonMappingException, IOException, PKIException, DocumentException;
	public ContractVO sign01(String contractName, InputStream in, String fileName, String userid) throws JsonParseException, JsonMappingException, IOException, PKIException, DocumentException;
	public ContractVO sign01(String contractName, File file, String userid) throws JsonParseException, JsonMappingException, IOException, PKIException, DocumentException;
	public ContractVO sign02Or04(String contractName, InputStream in, String fileName, String[] enterpriseInfo) throws JsonParseException, JsonMappingException, IOException, PKIException, DocumentException;
	public ContractVO sign02Or04(String contractName, File file, String[] enterpriseInfo) throws JsonParseException, JsonMappingException, IOException, PKIException, DocumentException;
	public ContractVO sign03(String contractName, InputStream in, String fileName, String userid) throws JsonParseException, JsonMappingException, IOException, PKIException, DocumentException;
	public ContractVO sign03(String contractName, File file, String userid) throws JsonParseException, JsonMappingException, IOException, PKIException, DocumentException;
	public ContractVO sign05(String contractName, InputStream in, String fileName) throws JsonParseException, JsonMappingException, IOException, PKIException, DocumentException;
	public ContractVO sign05(String contractName, File file) throws JsonParseException, JsonMappingException, IOException, PKIException, DocumentException;
	public ContractVO sign06(String contractName, InputStream in, String fileName, String[][] enterpriseInfos, String userid) throws JsonParseException, JsonMappingException, IOException, PKIException, DocumentException;
	public ContractVO sign06(String contractName, File file, String[][] enterpriseInfos, String userid) throws JsonParseException, JsonMappingException, IOException, PKIException, DocumentException;
	public ContractVO sign07(String contractName, InputStream in, String fileName, String userid) throws JsonParseException, JsonMappingException, IOException, PKIException, DocumentException;
	public ContractVO sign07(String contractName, File file, String userid) throws JsonParseException, JsonMappingException, IOException, PKIException, DocumentException;
	public ContractVO sign08(String contractName, InputStream in, String fileName, String userid, String sealid) throws JsonParseException, JsonMappingException, IOException, PKIException, DocumentException;
	public ContractVO sign08(String contractName, File file, String userid, String sealid) throws JsonParseException, JsonMappingException, IOException, PKIException, DocumentException;
}
