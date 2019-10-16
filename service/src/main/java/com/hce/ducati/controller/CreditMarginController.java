package com.hce.ducati.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import com.hce.ducati.entity.CreditMargin;
import com.hce.ducati.service.CompanyService;
import com.hce.ducati.service.CreditMarginService;
import com.quincy.global.Pagination;
import com.quincy.global.Result;
import com.quincy.global.helper.CommonHelper;

@Controller
@RequestMapping("/credit/margin")
public class CreditMarginController {
	@Resource(name = "${impl.auth.service}")
	private AuthorizationService authorizationService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private CreditMarginService creditMarginService;

	@PermissionNeeded("creditMarginSeach")
	@RequestMapping(value = "/{size}/{page}")
	public ModelAndView search(@PathVariable(required = true, name = "page")int page, @PathVariable(required = true, name = "size")int size, @RequestParam(value = "from", required = false)String _from, @RequestParam(value = "to", required = false)String _to, Long companyId, String currency) throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		_from = CommonHelper.trim(_from);
		_to = CommonHelper.trim(_to);
		Date from = _from==null?null:df.parse(_from+" 00:00:00");
		Date to = _to==null?null:df.parse(_to+" 23:59:59");
		Pagination pagination = creditMarginService.find(page, size, from, to, companyId, currency);
		ModelAndView mv = new ModelAndView("/content/credit/margin");
		mv.addObject("pageSize", ServiceConstants.PAGE_SIZE);
		mv.addObject("currencies", ServiceConstants.CURRENCIES);
		mv.addObject("companies", companyService.findAll());
		mv.addObject("pagination", pagination);
		mv.addObject("criterionFrom", _from);
		mv.addObject("criterionTo", _to);
		mv.addObject("criterionCompanyId", companyId);
		mv.addObject("criterionCurrency", currency);
		return mv;
	}

	@PermissionNeeded("creditMarginAdd")
	@PostMapping(value = "/add")
	@ResponseBody
	public Result add(HttpServletRequest request, CreditMargin margin) throws Exception {
		DSession session = authorizationService.getSession(request);
		margin.setUserId(session.getUser().getId());
		CreditMargin permanent = creditMarginService.add(margin);
		Result result = new Result();
		if(permanent==null) {
			result.setStatus(0);
			result.setMsg("已经存在币种为"+margin.getCurrency()+"的保证金");
		} else {
			result.setStatus(1);
			result.setMsg("成功");
		}
		return result;
	}

	@PermissionNeeded("creditMarginUpdate")
	@PostMapping(value = "/update")
	@ResponseBody
	public Result update(HttpServletRequest request, CreditMargin margin) throws Exception {
		DSession session = authorizationService.getSession(request);
		margin.setUserId(session.getUser().getId());
		return creditMarginService.update(margin);
	}

	@PermissionNeeded("creditMarginAudit")
	@PostMapping(value = "/audit")
	@ResponseBody
	public Result audit(HttpServletRequest request, CreditMargin margin) throws Exception {
		DSession session = authorizationService.getSession(request);
		return creditMarginService.audit(margin.getId(), session.getUser().getId(), margin.getCurrency(), margin.getAmount());
	}

	@PermissionNeeded("creditMarginDelete")
	@GetMapping(value = "/delete/{id}")
	@ResponseBody
	public Result delete(HttpServletRequest request, @PathVariable(required = true, name = "id")Long id) {
		return creditMarginService.delete(id);
	}
}
