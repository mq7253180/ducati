package com.hce.cfca.o;

import cfca.trustsign.common.vo.cs.HeadVO;
import cfca.trustsign.common.vo.multiplat.ContractVO;
import lombok.Data;

@Data
public class ContractVO2Wrapper {
	private HeadVO head;
	private ContractVO contract;
}
