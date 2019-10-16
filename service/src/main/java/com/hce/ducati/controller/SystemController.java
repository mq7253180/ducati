package com.hce.ducati.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.hce.auth.annotation.PermissionNeeded;
import com.hce.auth.entity.Role;
import com.hce.auth.entity.User;
import com.hce.auth.service.UserService;
import com.hce.ducati.service.CompanyService;
import com.quincy.global.helper.CommonHelper;

import cfca.sadk.algorithm.common.PKIException;

@Controller
@RequestMapping("/sys")
public class SystemController {
	@Autowired
	private UserService userService;
	@Autowired
	private CompanyService companyService;
	@Value("${location.accounts}")
	private String accountsLocation;

	@PermissionNeeded("sysMenu")
	@GetMapping(value = "/admin")
	public ModelAndView menu() {
		List<Role> roles = userService.findAllRoles();
		List<User> users = userService.findAllUsers();
		ModelAndView mv = new ModelAndView("/content/root");
		mv.addObject("roles", roles);
		mv.addObject("users", users);
		return mv;
	}

	private final static int BUFFER = 2*1024*1024;

	@PermissionNeeded("init")
	@RequestMapping(value = "/init")
	@ResponseBody
	public void init(@RequestParam("files")MultipartFile[] files) throws IOException, InvalidFormatException, PKIException, ParseException, DocumentException {
		this.doSyn(files, true);
	}

	@PermissionNeeded("syn")
	@RequestMapping(value = "/syn")
	@ResponseBody
	public void syn(@RequestParam("files")MultipartFile[] files) throws JsonParseException, JsonMappingException, InvalidFormatException, PKIException, IOException, ParseException, DocumentException {
		this.doSyn(files, false);
	}

	private void doSyn(MultipartFile[] files, boolean init) throws IOException, InvalidFormatException, PKIException, ParseException, DocumentException {
		File rootDir = new File(accountsLocation);
		CommonHelper.deleteFileOrDir(rootDir);
		rootDir.mkdirs();
		Charset gbk = Charset.forName("GBK");
		for(MultipartFile file:files) {
			InputStream in = null;
			try {
				in = file.getInputStream();
				CommonHelper.unzip(in, gbk, BUFFER, accountsLocation);
			} finally {
				if(in!=null)
					in.close();
			}
		}
		companyService.syn(init);
	}
}