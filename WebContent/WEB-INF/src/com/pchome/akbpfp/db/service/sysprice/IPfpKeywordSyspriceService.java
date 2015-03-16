package com.pchome.akbpfp.db.service.sysprice;

import com.pchome.akbpfp.db.pojo.PfpKeywordSysprice;
import com.pchome.akbpfp.db.service.IBaseService;

public interface IPfpKeywordSyspriceService extends IBaseService<PfpKeywordSysprice, String>{

	public PfpKeywordSysprice getKeywordSysprice(String keyword) throws Exception;
	public Integer getKeywordSearchPriceRangeCount(String keyword) throws Exception;
	
}
