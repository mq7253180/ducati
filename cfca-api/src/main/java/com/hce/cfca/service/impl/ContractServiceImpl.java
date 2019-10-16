package com.hce.cfca.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.hce.cfca.o.ContractVO2Wrapper;
import com.hce.cfca.o.ContractVOWrapper;
import com.hce.cfca.service.CFCAService;
import com.hce.cfca.service.ContractService;
import com.hce.global.helper.CommonHelper;

import cfca.sadk.algorithm.common.PKIException;
import cfca.trustsign.common.vo.cs.ContractVO;
import cfca.trustsign.common.vo.cs.SignKeywordVO;
import cfca.trustsign.common.vo.cs.SignLocationVO;
import cfca.trustsign.common.vo.cs.UploadContractVO;
import cfca.trustsign.common.vo.cs.UploadSignInfoVO;
import cfca.trustsign.common.vo.request.tx3.Tx3203ReqVO;
import cfca.trustsign.common.vo.request.tx3.Tx3210ReqVO;

@Service
public class ContractServiceImpl implements ContractService {
	@Autowired
	private CFCAService cfcaService;
	@Value("${cfca.platid}")
	private String cfcaPlatId;
	@Value("${cfca.addr}")
	private String cfcaHttpAddr;
	@Value("${cfca.sealid}")
	private String cfcaSealid;

	@Override
	public String getDownloadUrl(String contractNo) {
		return "https://"+cfcaHttpAddr+"/FEP/platId/"+cfcaPlatId+"/contractNo/"+contractNo+"/downloading";
	}

	private ContractVO getContractVO(Tx3203ReqVO vo, String contractName, InputStream in, String fileName, File file) throws JsonParseException, JsonMappingException, IOException, PKIException, DocumentException {
		byte[] b = null;
		if(in!=null) {
			b = cfcaService.signContract(vo, in, fileName);
		} else if(file!=null) {
			b = cfcaService.signContract(vo, file);
		}
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.setSerializationInclusion(Include.NON_NULL);
        ContractVOWrapper wrapper = objectMapper.readValue(b, ContractVOWrapper.class);
        if(!"60000000".equals(wrapper.getHead().getRetCode())) {
        	throw new PKIException(wrapper.getHead().getRetCode()+"-"+wrapper.getHead().getRetMessage());
        }
        return wrapper.getContract();
	}

	@Override
	public ContractVO sign(String contractName, File file, int isSign, String[][] enterpriInfos, String[] userInfo) throws JsonParseException, JsonMappingException, IOException, PKIException, DocumentException {
		return this.sign(contractName, null, null, file, isSign, enterpriInfos, userInfo);
	}

	@Override
	public ContractVO sign(String contractName, InputStream in, String fileName, int isSign, String[][] enterpriInfos, String[] userInfo) throws JsonParseException, JsonMappingException, IOException, PKIException, DocumentException {
		return this.sign(contractName, in, fileName, null, isSign, enterpriInfos, userInfo);
	}

	private ContractVO sign(String contractName, InputStream in, String fileName, File file, int isSign, String[][] enterpriInfos, String[] userInfo) throws JsonParseException, JsonMappingException, IOException, PKIException, DocumentException {
		int count = enterpriInfos==null?0:enterpriInfos.length;
		List<UploadSignInfoVO> signInfoVOList = new ArrayList<UploadSignInfoVO>(++count);
		String authorizationTime = new SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis());
		Tx3203ReqVO tx3203ReqVO = new Tx3203ReqVO();
		UploadContractVO uploadContract = new UploadContractVO();
		tx3203ReqVO.setUploadContract(uploadContract);
		uploadContract.setContractTypeCode("QT");
        uploadContract.setContractName(contractName);
		uploadContract.setIsSign(isSign);
		if(isSign==1) {
	        SignKeywordVO signKeyword = new SignKeywordVO();
	        signKeyword.setKeyword("海云汇国际服务有限公司");
	        signKeyword.setOffsetCoordX("-70");
	        signKeyword.setOffsetCoordY("10");
	        signKeyword.setImageWidth("399");
	        signKeyword.setImageHeight("133");
	        uploadContract.setSignKeyword(signKeyword);
	        String sealid = CommonHelper.trim(cfcaSealid);
	        if(sealid!=null)
	        	uploadContract.setSealId(sealid);
		}
		if(userInfo!=null&&userInfo.length>1) {
			UploadSignInfoVO signInfoVO = new UploadSignInfoVO();
			signInfoVO.setUserId(userInfo[0]);
	        signInfoVO.setIsProxySign(1);
	        //isProxySign传1返回的是60030205: ID为8C307E2D6C9C2280E05311016B0A9C34的用户此项目的代签信息不存在
	        signInfoVO.setIsCheckProjectCode(0);
	        //CertType是证书类型的意思。我们系统默认是给用户发RSA证书的，如果标记“2”是发国密证书，您这边用不到，所以直接注释掉就可以了
//	        signInfoVO.setCertType(2);
	        signInfoVO.setLocation("210.74.41.0");
	        signInfoVO.setProjectCode("001");
	        signInfoVO.setAuthorizationTime(authorizationTime);
			if(userInfo.length<=4) {//关键字
				String x = null;
				String y = null;
				if(userInfo.length==4) {
					x = userInfo[2];
					y = userInfo[3];
				} else {
					x = "55";
					y = "0";
				}
				SignKeywordVO signKeyword = new SignKeywordVO();
		        signKeyword.setKeyword(userInfo[1]);
		        signKeyword.setOffsetCoordX(x);
		        signKeyword.setOffsetCoordY(y);
		        signKeyword.setImageWidth("80");
		        signKeyword.setImageHeight("40");
				signInfoVO.setSignKeyword(signKeyword);
			} else {//坐标
				int locationCount = (userInfo.length-1)/5;
		        SignLocationVO[] locations = new SignLocationVO[locationCount];
		        for(int i=0;i<locationCount;i++) {
		        	int ii = 1+(5*i);
		        	SignLocationVO signLocationPlat = new SignLocationVO();
			        signLocationPlat.setSignOnPage(userInfo[ii]);
			        signLocationPlat.setSignLocationLBX(userInfo[ii+1]);
			        signLocationPlat.setSignLocationLBY(userInfo[ii+2]);
			        signLocationPlat.setSignLocationRUX(userInfo[ii+3]);
			        signLocationPlat.setSignLocationRUY(userInfo[ii+4]);
			        locations[i] = signLocationPlat;
		        }
		        signInfoVO.setSignLocations(locations);
			}
	        signInfoVOList.add(signInfoVO);
		}
		if(enterpriInfos!=null&&enterpriInfos.length>0) {
			for(String[] enterpriInfo:enterpriInfos) {
				String x = null;
				String y = null;
				String w = null;
				String h = null;
				if(enterpriInfo.length>=5) {
					x = enterpriInfo[3];
					y = enterpriInfo[4];
					if(enterpriInfo.length>=7) {
						w = enterpriInfo[5];
						h = enterpriInfo[6];
					}
				}
				x = x==null?"0":x;
				y = y==null?"0":y;
				w = w==null?"160":w;
				h = h==null?"160":h;
				SignKeywordVO signKeyword = new SignKeywordVO();
		        signKeyword.setKeyword(enterpriInfo[1]);
		        signKeyword.setOffsetCoordX(x);
		        signKeyword.setOffsetCoordY(y);
		        signKeyword.setImageWidth(w);
		        signKeyword.setImageHeight(h);
		        UploadSignInfoVO signInfoVO = new UploadSignInfoVO();
		        signInfoVO.setUserId(enterpriInfo[0]);
		        signInfoVO.setIsProxySign(1);
		        //isProxySign传1返回的是60030205: ID为8C307E2D6C9C2280E05311016B0A9C34的用户此项目的代签信息不存在
		        signInfoVO.setIsCheckProjectCode(0);
		        signInfoVO.setSignKeyword(signKeyword);
		        //CertType是证书类型的意思。我们系统默认是给用户发RSA证书的，如果标记“2”是发国密证书，您这边用不到，所以直接注释掉就可以了
//		        signInfoVO.setCertType(2);
		        signInfoVO.setLocation("210.74.41.0");
		        signInfoVO.setProjectCode("001");
		        signInfoVO.setAuthorizationTime(authorizationTime);
		        String sealid = enterpriInfo[2];
		        if(sealid!=null) {
		        	sealid = sealid.trim();
		        	if(sealid.length()==0)
		        		sealid = null;
		        }
		        if(sealid!=null)
		        	signInfoVO.setSealId(sealid);
		        signInfoVOList.add(signInfoVO);
			}
		}
		if(signInfoVOList.size()>0) {
			UploadSignInfoVO[] signInfoVOs = new UploadSignInfoVO[signInfoVOList.size()];
	        signInfoVOs = signInfoVOList.toArray(signInfoVOs);
	        uploadContract.setSignInfos(signInfoVOs);
		}
        return this.getContractVO(tx3203ReqVO, contractName, in, fileName, file);
	}

	@Override
	public cfca.trustsign.common.vo.multiplat.ContractVO query(String no) throws MalformedURLException, PKIException, IOException, DocumentException {
		Tx3210ReqVO tx3210ReqVO = new Tx3210ReqVO();
        ContractVO contract = new ContractVO();
        contract.setContractNo(no);
        tx3210ReqVO.setContract(contract);
        byte[] b = cfcaService.send(tx3210ReqVO, 3210);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.setSerializationInclusion(Include.NON_NULL);
        ContractVO2Wrapper wrapper = objectMapper.readValue(b, ContractVO2Wrapper.class);
		return wrapper.getContract();
	}

	private int[] getCoordinatesAs07() {
		int x = 70;
		int y = 405;
		int xx = x+60;
		int yy = y+25;
		int x2 = 300;
		int y2 = 200;
		int xx2 = x2+60;
		int yy2 = y2+25;
		return new int[] {x, y, xx, yy, x2, y2, xx2, yy2};
	}

	@Override
	public ContractVO sign01(String contractName, InputStream in, String fileName, String userid) throws JsonParseException, JsonMappingException, IOException, PKIException, DocumentException {
		return this.sign(contractName, in, fileName, 0, null, new String[] {userid, "申 请 人："});
	}

	@Override
	public ContractVO sign01(String contractName, File file, String userid) throws JsonParseException, JsonMappingException, IOException, PKIException, DocumentException {
		return this.sign(contractName, file, 0, null, new String[] {userid, "申 请 人："});
	}

	@Override
	public ContractVO sign02Or04(String contractName, InputStream in, String fileName, String[] enterpriseInfo) throws JsonParseException, JsonMappingException, IOException, PKIException, DocumentException {
		return this.sign(contractName, in, fileName, 0, new String[][] {enterpriseInfo}, null);
	}

	@Override
	public ContractVO sign02Or04(String contractName, File file, String[] enterpriseInfo) throws JsonParseException, JsonMappingException, IOException, PKIException, DocumentException {
		return this.sign(contractName, file, 0, new String[][] {enterpriseInfo}, null);
	}

	@Override
	public ContractVO sign03(String contractName, InputStream in, String fileName, String userid) throws JsonParseException, JsonMappingException, IOException, PKIException, DocumentException {
		return this.sign(contractName, in, fileName, 0, null, new String[] {userid, "转 让 人："});
	}

	@Override
	public ContractVO sign03(String contractName, File file, String userid) throws JsonParseException, JsonMappingException, IOException, PKIException, DocumentException {
		return this.sign(contractName, file, 0, null, new String[] {userid, "转 让 人："});
	}

	@Override
	public ContractVO sign05(String contractName, InputStream in, String fileName) throws JsonParseException, JsonMappingException, IOException, PKIException, DocumentException {
		return this.sign(contractName, in, fileName, 1, null, null);
	}

	@Override
	public ContractVO sign05(String contractName, File file) throws JsonParseException, JsonMappingException, IOException, PKIException, DocumentException {
		return this.sign(contractName, file, 1, null, null);
	}

	@Override
	public ContractVO sign06(String contractName, InputStream in, String fileName, String[][] enterpriseInfos, String userid) throws JsonParseException, JsonMappingException, IOException, PKIException, DocumentException {
		return this.sign(contractName, in, fileName, 0, enterpriseInfos, new String[] {userid, "【出口商户全称】(公章)：", "170", "-93"});
	}

	@Override
	public ContractVO sign06(String contractName, File file, String[][] enterpriseInfos, String userid) throws JsonParseException, JsonMappingException, IOException, PKIException, DocumentException {
		return this.sign(contractName, file, 0, enterpriseInfos, new String[] {userid, "【出口商户全称】(公章)：", "170", "-93"});
	}

	@Override
	public ContractVO sign07(String contractName, InputStream in, String fileName, String userid) throws JsonParseException, JsonMappingException, IOException, PKIException, DocumentException {
		int[] coordinates = this.getCoordinatesAs07();
		return this.sign(contractName, in, fileName, 0, null, new String[] {userid, "3", String.valueOf(coordinates[0]), String.valueOf(coordinates[1]), String.valueOf(coordinates[2]), String.valueOf(coordinates[3]), "8", String.valueOf(coordinates[4]), String.valueOf(coordinates[5]), String.valueOf(coordinates[6]), String.valueOf(coordinates[7])});
	}

	@Override
	public ContractVO sign07(String contractName, File file, String userid) throws JsonParseException, JsonMappingException, IOException, PKIException, DocumentException {
		int[] coordinates = this.getCoordinatesAs07();
		return this.sign(contractName, file, 0, null, new String[] {userid, "3", String.valueOf(coordinates[0]), String.valueOf(coordinates[1]), String.valueOf(coordinates[2]), String.valueOf(coordinates[3]), "8", String.valueOf(coordinates[4]), String.valueOf(coordinates[5]), String.valueOf(coordinates[6]), String.valueOf(coordinates[7])});
	}

	@Override
	public ContractVO sign08(String contractName, InputStream in, String fileName, String userid, String sealid) throws JsonParseException, JsonMappingException, IOException, PKIException, DocumentException {
		return this.sign(contractName, in, fileName, 0, new String[][] {{userid, "客户签名:", sealid, "60", "20"}}, null);
	}

	@Override
	public ContractVO sign08(String contractName, File file, String userid, String sealid) throws JsonParseException, JsonMappingException, IOException, PKIException, DocumentException {
		return this.sign(contractName, file, 0, new String[][] {{userid, "客户签名:", sealid, "60", "20"}}, null);
	}
}
