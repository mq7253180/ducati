package com.hce.ducati.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;

import org.dom4j.DocumentException;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/*import com.hce.cfca.service.CFCAService;
import com.hce.cfca.service.ContractService;*/

import cfca.sadk.algorithm.common.PKIException;
//import cfca.trustsign.common.vo.cs.ContractVO;
import cfca.trustsign.common.vo.cs.HeadVO;
import cfca.trustsign.common.vo.cs.UserInfoVO;
import cfca.trustsign.common.vo.request.tx3.Tx3006ReqVO;

@Controller
@RequestMapping("/cfca")
public class CFCAController {
	/*@Autowired
	private CFCAService cfcaService;
	@Autowired
	private ContractService contractService;*/

	private String getCurrentTime() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis());
    }

	@GetMapping(value = "/q/user/{id}")
	@ResponseBody
	public String queryUser(@PathVariable(required = true, name = "id")String id) throws IOException, MalformedURLException, DocumentException, PKIException {
		Tx3006ReqVO tx3006ReqVO = new Tx3006ReqVO();
        HeadVO head = new HeadVO();
        head.setTxTime(getCurrentTime());
        tx3006ReqVO.setHead(head);
        UserInfoVO queryUserInfo = new UserInfoVO();
        queryUserInfo.setUserId(id);
        tx3006ReqVO.setQueryUserInfo(queryUserInfo);
//		return new String(cfcaService.send(tx3006ReqVO, 3006));
		return null;
	}

	@GetMapping(value = "/q/contract/{no}")
	@ResponseBody
	public cfca.trustsign.common.vo.multiplat.ContractVO queryContract(@PathVariable(required = true, name = "no")String no) throws IOException, MalformedURLException, DocumentException, PKIException {
//		return contractService.query(no);
		return null;
	}

	@GetMapping(value = "/01")
	@ResponseBody
	public String test01() throws IOException, MalformedURLException, DocumentException, PKIException {
		File file = new File("D:/seal/01.pdf");
		/*ContractVO vo = contractService.sign01("TW-HCE-DK-17002894_SIGN", file, "8C2FC88F74EE678BE05312016B0A10BB");
		return contractService.getDownloadUrl(vo.getContractNo());*/
		return null;
	}

	@GetMapping(value = "/02")
	@ResponseBody
	public String test02() throws IOException, MalformedURLException, DocumentException, PKIException {
		File file = new File("D:/seal/02.pdf");
		/*ContractVO vo = contractService.sign02Or04("TW-HCE-DK-17002894_SIGN", file, new String[] {"8C499228654E2711E05312016B0A4965", "骆驼国际贸易有限公司", "8CA46F29428B1C25E05311016B0A31DC", "55", null, "83", "83"});
		return contractService.getDownloadUrl(vo.getContractNo());*/
		return null;
	}

	@GetMapping(value = "/03")
	@ResponseBody
	public String test03() throws IOException, MalformedURLException, DocumentException, PKIException {
		File file = new File("D:/seal/03.pdf");
		/*ContractVO vo = contractService.sign03("TW-HCE-DK-17002894_SIGN", file, "8C2FC88F74EE678BE05312016B0A10BB");
		return contractService.getDownloadUrl(vo.getContractNo());*/
		return null;
	}

	@GetMapping(value = "/04")
	@ResponseBody
	public String test04() throws IOException, MalformedURLException, DocumentException, PKIException {
		File file = new File("D:/seal/04.pdf");
		/*ContractVO vo = contractService.sign02Or04("TW-HCE-DK-17002894_SIGN", file, new String[] {"8C499228654E2711E05312016B0A4965", "骆驼国际贸易有限公司", "8CA46F29428B1C25E05311016B0A31DC", "60", null, "83", "83"});
		return contractService.getDownloadUrl(vo.getContractNo());*/
		return null;
	}

	@GetMapping(value = "/05")
	@ResponseBody
	public String test05() throws IOException, MalformedURLException, DocumentException, PKIException {
		File file = new File("D:/seal/05.pdf");
		/*ContractVO vo = contractService.sign05("TW-HCE-LOAN-20190516-55920_SIGN", file);
		return contractService.getDownloadUrl(vo.getContractNo());*/
		return null;
	}

	@GetMapping(value = "/06")
	@ResponseBody
	public String test06() throws IOException, MalformedURLException, DocumentException, PKIException {
		File file = new File("D:/seal/06.pdf");
		/*ContractVO vo = contractService.sign06("TW-HCE-EXCHANGE-20190516-34900_SIGN", file, 
				new String[][] {
			{"8C307E2D6C9C2280E05311016B0A9C34", "驼铃商业保理(广州)有限公司(公章)", "8C3FA71246D00F29E05312016B0ACA49", "100", null}, 
			{"8C499228654E2711E05312016B0A4965", "【骆驼国际贸易有限公司】", "8CA46F29428B1C25E05311016B0A31DC", "100", null, "83", "83"}
		}, "8C2FC88F74EE678BE05312016B0A10BB");
		return contractService.getDownloadUrl(vo.getContractNo());*/
		return null;
	}

	@GetMapping(value = "/07")
	@ResponseBody
	public String test07() throws IOException, MalformedURLException, DocumentException, PKIException {
		File file = new File("D:/seal/07.pdf");
		/*ContractVO vo = contractService.sign07("TW-HCE-DK-17002894_SIGN", file, "8C2FC88F74EE678BE05312016B0A10BB");
		return contractService.getDownloadUrl(vo.getContractNo());*/
		return null;
	}

	@GetMapping(value = "/08")
	@ResponseBody
	public String test08() throws IOException, MalformedURLException, DocumentException, PKIException {
		File file = new File("D:/seal/08.pdf");
		/*ContractVO vo = contractService.sign08("TW-HCE-DK-17002894_SIGN", file, "8C499228654E2711E05312016B0A4965", "8CA46F29428B1C25E05311016B0A31DC");
		return contractService.getDownloadUrl(vo.getContractNo());*/
		return null;
	}
}
