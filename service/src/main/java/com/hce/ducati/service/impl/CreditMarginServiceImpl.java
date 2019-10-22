package com.hce.ducati.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hce.ducati.dao.CreditMarginRepository;
import com.hce.ducati.dao.CreditRepository;
import com.hce.ducati.dao.PriceRepository;
import com.hce.ducati.entity.Credit;
import com.hce.ducati.entity.CreditMargin;
import com.hce.ducati.entity.Price;
import com.hce.ducati.mapper.CreditMapper;
import com.hce.ducati.o.CreditMarginDTO;
import com.hce.ducati.service.CreditMarginService;
import com.quincy.sdk.Pagination;
import com.quincy.sdk.Result;
import com.quincy.sdk.annotation.ReadOnly;

@Service
public class CreditMarginServiceImpl implements CreditMarginService {
	@Autowired
	private CreditRepository creditRepository;
	@Autowired
	private CreditMarginRepository creditMarginRepository;
	@Autowired
	private PriceRepository priceRepository;
	@Autowired
	private CreditMapper creditMapper;

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	@Override
	public void updateCreditAmount(Credit credit) {
		List<CreditMargin> margins = creditMarginRepository.findByCreditId(credit.getId());
		for(CreditMargin margin:margins) {
			this.calculateAndSave(credit, margin);
		}
	}

	/*@Override
	public void updateCreditAmount(Long creditId) {
		Credit credit = creditRepository.getOne(creditId);
		this.updateCreditAmount(credit);
	}*/

	private CreditMargin calculateAndSave(Credit credit, CreditMargin margin) {
		String symbol = credit.getCurrency()+"/"+margin.getCurrency();
		Price price = priceRepository.findBySymbol(symbol);
		BigDecimal creditAmount = margin.getAmount().multiply(new BigDecimal("100"));
		creditAmount = creditAmount.divide(new BigDecimal(credit.getRatio()));
		creditAmount = creditAmount.divide(price.getMid(), 6, RoundingMode.HALF_UP);
		margin.setCreditAmount(creditAmount);
		margin.setRatio(credit.getRatio());
		margin.setPrice(price.getMid());
		return creditMarginRepository.save(margin);
	}

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	@Override
	public CreditMargin add(CreditMargin margin) {
		margin.setId(null);
		Credit credit = creditRepository.getOne(margin.getCreditId());
		return this.calculateAndSave(credit, margin);
	}

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	@Override
	public Result update(CreditMargin margin) {
		Optional<CreditMargin> optional = creditMarginRepository.findById(margin.getId());
		if(!optional.isPresent()) {
			return new Result(0, "不存在该记录, 或已被删除!");
		}
		CreditMargin permanent = optional.get();
		if(permanent.getStatus()==1)
			return new Result(-1, "已生效不能修改!");
		else if(permanent.getStatus()==-1)
			return new Result(-2, "已失效不能修改!");
		permanent.setCurrency(margin.getCurrency());
		permanent.setAmount(margin.getAmount());
		permanent.setUserId(margin.getUserId());
		Credit credit = creditRepository.getOne(permanent.getCreditId());
		this.calculateAndSave(credit, permanent);
		return new Result(1, "成功");
	}

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	@Override
	public Result audit(Long id, Long approverUserId, String currency, BigDecimal amount) {
		int effected = creditMapper.auditCreditMargin(id, approverUserId, currency, amount);
		if(effected<1) {//已被审核
			return new Result(0, "已被审核生效、或已被删除、或信息已被修改，请重新审核! 已失效不能被审核！");
		} else {
			CreditMargin margin = creditMarginRepository.getOne(id);
			List<CreditMargin> list = creditMarginRepository.findByCreditIdAndCurrencyAndStatus(margin.getCreditId(), margin.getCurrency(), 1);
			for(CreditMargin validMargin:list) {
				if(validMargin.getId().longValue()!=id.longValue()) {
					validMargin.setStatus(-1);
					creditMarginRepository.save(validMargin);
				}
			}
			return new Result(1, "成功");
		}
	}

	@ReadOnly
	@Override
	public Pagination find(int page, int size, Date from, Date to, Long companyId, String currency) {
		Integer count = creditMapper.countCreditMargins(from, to, companyId, currency);
		Pagination pagination = new Pagination(count, page, size);
		List<CreditMarginDTO> list = creditMapper.findCreditMargins(pagination.getFrom()-1, size, from, to, companyId, currency);
		pagination.setData(list);
		return pagination;
	}

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	@Override
	public Result delete(Long id) {
		Integer status = null;
		String msg = null;
		int effected = creditMapper.deleteCreditMargin(id);
		if(effected==0) {
			Optional<CreditMargin> o = creditMarginRepository.findById(id);
			if(o.isPresent()) {
				status = 0;
				msg = "已生效不能删除!";
			} else {
				status = -1;
				msg = "已被其他人删除!";
			}
		} else {
			status = 1;
			msg = "成功";
		}
		return new Result(status, msg);
	}
}