package com.hce.ducati.controller;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hce.auth.annotation.PermissionNeeded;
import com.hce.auth.o.DSession;
import com.hce.auth.service.AuthorizationService;
import com.hce.ducati.ServiceConstants;
import com.hce.ducati.entity.Company;
import com.hce.ducati.entity.Credit;
import com.hce.ducati.o.CreditDTO;
import com.hce.ducati.service.CompanyService;
import com.hce.ducati.service.CreditService;
import com.quincy.global.Result;
import com.quincy.global.helper.CommonHelper;

@Controller
@RequestMapping("/credit")
public class CreditController {
	@Autowired
	private CompanyService companyService;
	@Autowired
	private CreditService creditService;
	@Resource(name = "${impl.auth.service}")
	private AuthorizationService authorizationService;

	@PermissionNeeded("creditSeach")
	@GetMapping(value = "")
	public ModelAndView menu(HttpServletRequest request) {
		List<CreditDTO> credits = creditService.findCredits();
		ModelAndView mv = new ModelAndView("/content/credit/index");
		mv.addObject("currencies", ServiceConstants.CURRENCIES);
		mv.addObject("credits", credits);
		mv.addObject("companies", companyService.findAll());
		mv.addObject("pageSize", ServiceConstants.PAGE_SIZE);
		return mv;
	}

	@PermissionNeeded("creditAdd")
	@PostMapping(value = "/add")
	@ResponseBody
	public Result add(HttpServletRequest request, Credit credit) throws Exception {
		DSession session = authorizationService.getSession(request);
		credit.setUserId(session.getUser().getId());
		Credit permanent = creditService.add(credit);
		Result result = new Result();
		if(permanent==null) {
			result.setStatus(0);
			result.setMsg("已经存在币种为"+credit.getCurrency()+"的合同授信");
		} else {
			result.setStatus(1);
			result.setMsg("成功");
		}
		return result;
	}

	@PermissionNeeded("creditUpdate")
	@PostMapping(value = "/update")
	@ResponseBody
	public Result update(Credit credit) {
		Credit permanent = creditService.update(credit);
		Result result = new Result();
		if(permanent==null) {
			result.setStatus(0);
			result.setMsg("已经存在币种为"+credit.getCurrency()+"的合同授信");
		} else {
			result.setStatus(1);
			result.setMsg("成功");
		}
		return result;
	}

	@PermissionNeeded("creditUpdateAmount")
	@PostMapping(value = "/update/amount")
	@ResponseBody
	public void updateAmount(HttpServletRequest request, Long id, BigDecimal amount) throws Exception {
		DSession session = authorizationService.getSession(request);
		creditService.update(id, amount, session.getUser().getId());
	}

	@PermissionNeeded("viewCreditHistory")
	@RequestMapping(value = "/view/{id}/{size}/{page}")
	public ModelAndView view(@PathVariable(required = true, name = "id")Long id, @PathVariable(required = true, name = "page")int page, @PathVariable(required = true, name = "size")int size, @RequestParam(value = "from", required = false)String _from, @RequestParam(value = "to", required = false)String _to) throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		_from = CommonHelper.trim(_from);
		_to = CommonHelper.trim(_to);
		Date from = _from==null?null:df.parse(_from+" 00:00:00");
		Date to = _to==null?null:df.parse(_to+" 23:59:59");
		Credit credit = creditService.find(id);
		Company company = companyService.find(credit.getCompanyId());
		CreditDTO creditDTO = new CreditDTO();
		creditDTO.setId(credit.getId());
		creditDTO.setCompanyCnName(company.getCnName());
		creditDTO.setCurrency(credit.getCurrency());
		creditDTO.setRatio(credit.getRatio());
		ModelAndView mv = new ModelAndView("/content/credit/history");
		mv.addObject("pagination", creditService.findHistorys(id, page, size, from, to, df));
		mv.addObject("credit", creditDTO);
		mv.addObject("criterionFrom", _from);
		mv.addObject("criterionTo", _to);
		return mv;
	}
}
