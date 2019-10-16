package com.hce.ducati.mapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.hce.ducati.o.CreditDTO;
import com.hce.ducati.o.CreditMarginDTO;

@Repository
public interface CreditMapper {
	public List<CreditDTO> findCredits();
	public Integer countCreditHisotrys(@Param("creditId")Long creditId, @Param("from")Date from, @Param("to")Date to);
	public List<CreditDTO> findCreditHisotrys(@Param("creditId")Long creditId, @Param("start")int start, @Param("size")int size, @Param("from")Date from, @Param("to")Date to);
	public int auditCreditMargin(@Param("id")Long id, @Param("approverUserId")Long approverUserId, @Param("currency")String currency, @Param("amount")BigDecimal amount);
	public int deleteCreditMargin(@Param("id")Long id);
	public Integer countCreditMargins(@Param("from")Date from, @Param("to")Date to, @Param("companyId")Long companyId, @Param("currency")String currency);
	public List<CreditMarginDTO> findCreditMargins(@Param("start")int start, @Param("size")int size, @Param("from")Date from, @Param("to")Date to, @Param("companyId")Long companyId, @Param("currency")String currency);
}
