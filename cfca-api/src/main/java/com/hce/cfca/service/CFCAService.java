package com.hce.cfca.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import org.dom4j.DocumentException;

import cfca.sadk.algorithm.common.PKIException;
import cfca.trustsign.common.vo.request.tx3.Tx3203ReqVO;
import cfca.trustsign.common.vo.request.tx3.Tx3ReqVO;

public interface CFCAService {
	public byte[] send(Tx3ReqVO vo, int txCode) throws IOException, MalformedURLException, DocumentException, PKIException;
	public byte[] signContract(Tx3203ReqVO vo, InputStream contract, String fileName) throws IOException, MalformedURLException, DocumentException, PKIException;
	public byte[] signContract(Tx3203ReqVO vo, File contract) throws IOException, MalformedURLException, DocumentException, PKIException;
}
