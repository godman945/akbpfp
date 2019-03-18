package com.pchome.akbpfp.db.service.sysprice;

import com.pchome.akbpfp.db.pojo.PfpAdSysprice;
import com.pchome.akbpfp.db.service.IBaseService;

public interface IPfpAdSyspriceService extends IBaseService<PfpAdSysprice, String>{

	public PfpAdSysprice getAdSysprice(String adPoolSeq) throws Exception;
	
	public Integer getAdChannelPriceRangeCount() throws Exception;

}
