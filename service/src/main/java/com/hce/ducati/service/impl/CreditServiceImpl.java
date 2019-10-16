package com.hce.ducati.service.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hce.ducati.dao.CreditHistoryRepository;
import com.hce.ducati.dao.CreditRepository;
import com.hce.ducati.entity.Credit;
import com.hce.ducati.entity.CreditHistory;
import com.hce.ducati.mapper.CreditMapper;
import com.hce.ducati.o.CreditDTO;
import com.hce.ducati.service.CreditMarginService;
import com.hce.ducati.service.CreditService;
import com.hce.global.Pagination;
import com.hce.global.annotation.ReadOnly;
import com.hce.global.helper.CommonHelper;

@Service
public class CreditServiceImpl implements CreditService {
	@Autowired
	private CreditRepository creditRepository;
	@Autowired
	private CreditHistoryRepository creditHistoryRepository;
	@Autowired
	private CreditMarginService creditMarginService;
	@Autowired
	private CreditMapper creditMapper;

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	@Override
	public Credit add(Credit credit) {
		credit.setId(null);
		Credit permanentCredit = creditRepository.findByCompanyIdAndCurrency(credit.getCompanyId(), credit.getCurrency());
		return permanentCredit==null?creditRepository.save(credit):null;
	}
	/**
	 * 更新合同授信额
	 */
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	@Override
	public void update(Long id, BigDecimal amount, Long userId) {
		Credit permanentCredit = creditRepository.getOne(id);
		CreditHistory history = new CreditHistory();
		history.setCreditId(id);
		history.setAmount(permanentCredit.getAmount());
		history.setValidFrom(permanentCredit.getValidFrom());
		history.setUserId(permanentCredit.getUserId());
		permanentCredit.setAmount(amount);
		permanentCredit.setValidFrom(new Date());
		permanentCredit.setUserId(userId);
		creditRepository.save(permanentCredit);
		creditHistoryRepository.save(history);
	}
	/**
	 * 更新币种、最大日限额、保证金比例
	 */
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	@Override
	public Credit update(Credit credit) {
		credit.setCurrency(CommonHelper.trim(credit.getCurrency()));
		Credit permanentCredit = creditRepository.getOne(credit.getId());
		Credit validatingCredit = null;
		if(credit.getCurrency()!=null&&!credit.getCurrency().equals(permanentCredit.getCurrency())) {
			validatingCredit = creditRepository.findByCompanyIdAndCurrency(permanentCredit.getCompanyId(), credit.getCurrency());
		}
		if(validatingCredit!=null)
			return null;
		else {
			permanentCredit.setMaxLimitOneDay(credit.getMaxLimitOneDay());
			if(permanentCredit.getRatio().intValue()!=credit.getRatio().intValue()) {
				permanentCredit.setRatio(credit.getRatio());
				creditMarginService.updateCreditAmount(permanentCredit);
			}
			return creditRepository.save(permanentCredit);
		}
	}
	/**
	 *查询页面
	 */
	@ReadOnly
	@Override
	public List<CreditDTO> findCredits() {
		return creditMapper.findCredits();
	}
	/**
	 *分页查询历史
	 */
	@ReadOnly
	@Override
	public Pagination findHistorys(Long creditId, int page, int size, Date from, Date to, DateFormat dateFormat) {
		Integer count = creditMapper.countCreditHisotrys(creditId, from, to);
		Pagination pagination = new Pagination(count, page, size);
		List<CreditDTO> list = creditMapper.findCreditHisotrys(creditId, pagination.getFrom()-1, size, from, to);
		pagination.setData(list);
		return pagination;
	}
	/**
	 * 保证金管理下拉框用
	 */
	@ReadOnly
	@Override
	public List<Credit> findByCompanyId(Long companyId) {
		return creditRepository.findByCompanyId(companyId);
	}
	@ReadOnly
	@Override
	public Credit find(Long id) {
		return creditRepository.getOne(id);
	}
}
