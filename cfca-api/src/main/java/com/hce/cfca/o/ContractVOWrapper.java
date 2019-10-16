package com.hce.cfca.o;

import cfca.trustsign.common.vo.cs.ContractVO;
import cfca.trustsign.common.vo.cs.HeadVO;
import lombok.Data;

@Data
public class ContractVOWrapper {
	private HeadVO head;
	private ContractVO contract;
}
