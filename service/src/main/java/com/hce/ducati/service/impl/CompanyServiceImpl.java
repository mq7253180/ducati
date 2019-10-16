package com.hce.ducati.service.impl;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import com.hce.cfca.service.CFCAService;
import com.hce.ducati.dao.CompanyRepository;
import com.hce.ducati.dao.EnterpriseRepository;
import com.hce.ducati.dao.IndividualRepository;
import com.hce.ducati.entity.Company;
import com.hce.ducati.entity.Enterprise;
import com.hce.ducati.entity.Individual;
import com.hce.ducati.service.CompanyService;
import com.hce.global.Constants;
import com.hce.global.annotation.ReadOnly;
import com.hce.global.helper.CommonHelper;

import cfca.sadk.algorithm.common.PKIException;
import cfca.sadk.org.bouncycastle.util.encoders.Base64;
import cfca.trustsign.common.vo.cs.PersonVO;
import cfca.trustsign.common.vo.cs.SealAddVO;
import cfca.trustsign.common.vo.cs.SealUpdateVO;
import cfca.trustsign.common.vo.cs.SealVO;
import cfca.trustsign.common.vo.request.tx3.Tx3001ReqVO;
import cfca.trustsign.common.vo.request.tx3.Tx3002ReqVO;
import cfca.trustsign.common.vo.request.tx3.Tx3011ReqVO;
import cfca.trustsign.common.vo.request.tx3.Tx3012ReqVO;

@Service
public class CompanyServiceImpl implements CompanyService {
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private EnterpriseRepository enterpriseRepository;
	@Autowired
	private IndividualRepository individualRepository;
	@Autowired
	private CFCAService cfcaService;
	@Value("${location.accounts}")
	private String accountsLocation;
	@Value("${env}")
	private String env;

	public void syn(boolean init) throws IOException, ParseException, JsonParseException, JsonMappingException, DocumentException, InvalidFormatException, PKIException {
		File rootDir = new File(accountsLocation);
		File[] companyFiles = rootDir.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.isDirectory();
			}
		});
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(File companyFile:companyFiles) {
			String[] companyFields = companyFile.getName().split("-");
			Long companyId = Long.valueOf(companyFields[0]);
			String companyEnName = companyFields[1];
			String companyCnName = companyFields[2];
			Optional<Company> optional = companyRepository.findById(companyId);
			Company company = null;
			if(optional.isPresent()) {
				company = optional.get();
			} else {
				company = new Company();
				company.setId(companyId);
			}
			company.setEnName(companyEnName);
			company.setCnName(companyCnName);
			company = companyRepository.save(company);
			File[] enterpriseFiles = companyFile.listFiles(new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					return pathname.getName().startsWith("enterprise-")&&pathname.getName().endsWith(".txt");
				}
			});
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
			objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			objectMapper.setSerializationInclusion(Include.NON_NULL);
			for(File enterpriseFile:enterpriseFiles) {
				Integer type = Integer.valueOf(enterpriseFile.getName().split("-")[1].split("\\.")[0]);
				Tx3002ReqVO tx3002ReqVO = objectMapper.readValue(enterpriseFile, Tx3002ReqVO.class);
				Enterprise enterprise = enterpriseRepository.findByIdNoAndIdTypeAndName(tx3002ReqVO.getEnterprise().getIdentNo(), tx3002ReqVO.getEnterprise().getIdentTypeCode(), tx3002ReqVO.getEnterprise().getEnterpriseName());
				if(enterprise==null) {
					enterprise = new Enterprise();
					enterprise.setName(tx3002ReqVO.getEnterprise().getEnterpriseName());
					enterprise.setIdType(tx3002ReqVO.getEnterprise().getIdentTypeCode());
					enterprise.setIdNo(tx3002ReqVO.getEnterprise().getIdentNo());
				}
				if(enterprise.getUserid()==null) {
					byte[] b = cfcaService.send(tx3002ReqVO, 3002);
					tx3002ReqVO = objectMapper.readValue(b, Tx3002ReqVO.class);
					enterprise.setUserid(tx3002ReqVO.getEnterprise().getUserId());
				}
				String sealImgPath = accountsLocation+"/"+companyFile.getName()+"/"+enterpriseFile.getName().substring(0, enterpriseFile.getName().indexOf("."))+".png";
			    File sealImgFile = new File(sealImgPath);
			    if(sealImgFile.exists()) {
			    	SealVO sealVO = new SealVO();
			    	sealVO.setImageData(Base64.toBase64String(Files.readAllBytes(Paths.get(sealImgPath))));
			    	if(enterprise.getSealid()==null) {
			    		Tx3011ReqVO tx3011ReqVO = new Tx3011ReqVO();
			    		SealAddVO sealAddVO = new SealAddVO();
			    		sealAddVO.setUserId(enterprise.getUserid());
			    		sealAddVO.setSeal(sealVO);
			    		tx3011ReqVO.setSealAdd(sealAddVO);
			    		byte[] b = cfcaService.send(tx3011ReqVO, 3011);
			    		tx3011ReqVO = objectMapper.readValue(b, Tx3011ReqVO.class);
			    		enterprise.setSealid(tx3011ReqVO.getSealAdd().getSeal().getSealId());
			    	} else {
			    		sealVO.setSealId(enterprise.getSealid());
			    		Tx3012ReqVO tx3012ReqVO = new Tx3012ReqVO();
			    		SealUpdateVO sealUpdateVO = new SealUpdateVO();
			    		sealUpdateVO.setUserId(enterprise.getUserid());
			    		sealUpdateVO.setSeal(sealVO);
			    		tx3012ReqVO.setSealUpdate(sealUpdateVO);
			    		cfcaService.send(tx3012ReqVO, 3012);
			    	}
			    }
				enterprise.setCompanyId(company.getId());
				enterprise.setType(type);
				enterprise.setEmail(tx3002ReqVO.getEnterprise().getEmail());
				enterprise.setMobilePhone(tx3002ReqVO.getEnterprise().getMobilePhone());
				enterprise.setLandlinePhone(tx3002ReqVO.getEnterprise().getLandlinePhone());
				enterprise.setTransactorName(tx3002ReqVO.getEnterpriseTransactor().getTransactorName());
				enterprise.setTransactorIdType(tx3002ReqVO.getEnterpriseTransactor().getIdentTypeCode());
				enterprise.setTransactorIdNo(tx3002ReqVO.getEnterpriseTransactor().getIdentNo());
				enterprise = enterpriseRepository.save(enterprise);
			}
			String parentPath = companyFile.getAbsolutePath().replaceAll("\\\\", "/")+"/individual.xlsx";
			File excel = new File(parentPath);
			if(excel.exists()) {
				Map<String, Individual> map = new HashMap<String, Individual>(15000);
				Workbook workbook = null;
				try {
					workbook = new XSSFWorkbook(excel);
					Sheet sheet = workbook.getSheetAt(0);
					int index = Constants.ENV_PRO.equals(env)?1:10989;
					while(true) {
						Row row = sheet.getRow(index++);
						Cell cell = row==null?null:row.getCell(0);
						String idTypeCamel = cell==null?null:CommonHelper.trim(cell.getStringCellValue());
						if(idTypeCamel!=null) {
							String idTypeCfca = String.valueOf(MAPPING.get(idTypeCamel));
							String idNo = this.extractStringFromCell(row, 1);
							String master = this.extractStringFromCell(row, 4);
							Individual individual = individualRepository.findByIdNoAndIdTypeCfcaAndMasterAndCompanyId(idNo, idTypeCfca, master, company.getId());
							String _creditRating = this.extractStringFromCell(row, 21);
							Integer creditRating = _creditRating==null?0:Integer.valueOf(_creditRating);
							if(individual==null) {
								individual = new Individual();
								individual.setIdTypeCamel(idTypeCamel);
								individual.setIdTypeCfca(idTypeCfca);
								individual.setIdNo(idNo);
								individual.setMaster(master);
								if(init) {
									String status = this.extractStringFromCell(row, 33);
									individual.setStatus(Integer.valueOf(String.valueOf(MAPPING.get(status))));
								} else
									individual.setStatus(STATUS_ADD);
							} else {//业务需求
								if(individual.getStatus().intValue()==STATUS_ADD.intValue()||individual.getStatus().intValue()==0)
									individual.setStatus(STATUS_NORMAL);
								Integer result = creditRating-individual.getCreditRating();
								result = result<0?-1*result:result;
								if(individual.getStatus().intValue()==STATUS_NORMAL&&result>=100)
									individual.setStatus(STATUS_FOCUS);
								else if(individual.getStatus().intValue()==STATUS_FOCUS&&result<100)
									individual.setStatus(STATUS_NORMAL);
							}
							String mobilePhone = this.extractStringFromCell(row, 8);
							if(individual.getUserid()==null) {
								Tx3001ReqVO tx3001ReqVO = new Tx3001ReqVO();
								tx3001ReqVO.setNotSendPwd(1);
						        PersonVO person = new PersonVO();
						        person.setPersonName(master);
						        person.setIdentTypeCode(idTypeCfca);
						        person.setIdentNo(idNo);
						        person.setMobilePhone("18000000000");
						        person.setAuthenticationMode("线下人工审核");
						        tx3001ReqVO.setPerson(person);
						        byte[] b = cfcaService.send(tx3001ReqVO, 3001);
						        tx3001ReqVO = objectMapper.readValue(b, Tx3001ReqVO.class);
						        individual.setUserid(tx3001ReqVO.getPerson().getUserId());
							}
							individual.setCompanyId(company.getId());
							individual.setMobilePhone(mobilePhone);
							String name = this.extractStringFromCell(row, 2);
							String companyName = this.extractStringFromCell(row, 3);
							String gender = this.extractStringFromCell(row, 5);
							String birthDate = this.extractStringFromCell(row, 6);
							String country = this.extractStringFromCell(row, 7);
							String addr = this.extractStringFromCell(row, 9);
							String productCategory = this.extractStringFromCell(row, 10);
							String creditCurrency = this.extractStringFromCell(row, 11);
							String totalCreditAmount = this.extractStringFromCell(row, 12);
							String availableCreditAmount = this.extractStringFromCell(row, 13);
							String creditFrom = this.extractStringFromCell(row, 14);
							String creditTo = this.extractStringFromCell(row, 15);
							String bankName = this.extractStringFromCell(row, 16);
							String bankCardType = this.extractStringFromCell(row, 17);
							String bankAccount = this.extractStringFromCell(row, 18);
							String bankCurrency = this.extractStringFromCell(row, 19);
							String swift = this.extractStringFromCell(row, 20);
							String discount = this.extractStringFromCell(row, 22);
							String recent3mDealAmount = this.extractStringFromCell(row, 23);
							String recent6mDealAmount = this.extractStringFromCell(row, 24);
							String commissionRate = this.extractStringFromCell(row, 25);
							String exchangeRate = this.extractStringFromCell(row, 26);
							String dealDuration = this.extractStringFromCell(row, 27);
							String recent3mAvgOrders = this.extractStringFromCell(row, 28);
							String recent90dRefundRate = this.extractStringFromCell(row, 29);
							String recent90dDishonourRate = this.extractStringFromCell(row, 30);
							String recent90dAvgShippingDuration = this.extractStringFromCell(row, 31);
							String recent90dOrdersIncrRate = this.extractStringFromCell(row, 32);
							individual.setName(name);
							individual.setCompany(companyName);
							individual.setGender(gender);
							individual.setBirthDate(birthDate);
							individual.setCountry(country);
							individual.setAddr(addr);
							individual.setProductCategory(productCategory);
							individual.setCreditCurrency(creditCurrency);
							individual.setTotalCreditAmount(totalCreditAmount==null?null:new BigDecimal(totalCreditAmount));
							individual.setAvailableCreditAmount(availableCreditAmount==null?null:new BigDecimal(availableCreditAmount));
							individual.setCreditFrom(df.parse(creditFrom+" 00:00:00"));
							individual.setCreditTo(df.parse(creditTo+" 23:59:59"));
							individual.setSwift(swift);
							individual.setBankName(bankName);
							individual.setBankCardType(bankCardType);
							individual.setBankCurrency(bankCurrency);
							individual.setBankAccount(bankAccount);
							individual.setCreditRating(creditRating);
							individual.setDiscount(Integer.valueOf(discount));
							individual.setRecent3mDealAmount(recent3mDealAmount==null?null:new BigDecimal(recent3mDealAmount));
							individual.setRecent6mDealAmount(recent6mDealAmount==null?null:new BigDecimal(recent6mDealAmount));
							individual.setCommissionRate(commissionRate==null?null:new BigDecimal(commissionRate));
							individual.setExchangeRate(exchangeRate==null?null:new BigDecimal(exchangeRate));
							individual.setDealDuration(dealDuration==null?null:Integer.valueOf(dealDuration));
							individual.setRecent3mAvgOrders(recent3mAvgOrders);
							individual.setRecent90dRefundRate(recent90dRefundRate);
							individual.setRecent90dDishonourRate(recent90dDishonourRate);
							individual.setRecent90dAvgShippingDuration(recent90dAvgShippingDuration);
							individual.setRecent90dOrdersIncrRate(recent90dOrdersIncrRate);
							individual = individualRepository.save(individual);
							String unq = idNo+"_"+idTypeCamel+"_"+master;
							map.put(unq, individual);
						} else 
							break;
					}
				} finally {
					if(workbook!=null)
						workbook.close();
				}
				List<Individual> list = individualRepository.findAll();
				for(Individual individual:list) {
					String unq = individual.getIdNo()+"_"+individual.getIdTypeCamel()+"_"+individual.getMaster();
					if(map.get(unq)==null&&individual.getStatus().intValue()>0) {
						individual.setStatus(0);
						individualRepository.save(individual);
					}
				}
			}
		}
	}

	private String extractStringFromCell(Row row, int cellnum) {
		Cell cell = row.getCell(cellnum);
		if(cell==null)
			return null;
		else {
			cell.setCellType(CellType.STRING);
			return CommonHelper.trim(cell.getStringCellValue());
		}
	}

	private final static Integer STATUS_ADD = 1;
	private final static Integer STATUS_NORMAL = STATUS_ADD+1;
	private final static Integer STATUS_FOCUS = STATUS_NORMAL+1;

	public final static Map<String, Object> MAPPING = new HashMap<String, Object>(12);
	static {
		MAPPING.put("01", "0");
		MAPPING.put("02", "1");
		MAPPING.put("10", "Z");
		MAPPING.put("11", "8");
		MAPPING.put("13", "Z");
		MAPPING.put("20", "Z");
		MAPPING.put("逾期", -1);
		MAPPING.put("冻结", 0);
		MAPPING.put("新增", STATUS_ADD);
		MAPPING.put("正常", STATUS_NORMAL);
		MAPPING.put("关注", STATUS_FOCUS);
	}

	@ReadOnly
	@Override
	public List<Company> findAll() {
		return companyRepository.findAll();
	}

	@ReadOnly
	@Override
	public Company find(Long id) {
		return companyRepository.getOne(id);
	}
}
