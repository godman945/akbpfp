package com.pchome.akbpfp.db.service.ad;

import com.pchome.akbpfp.db.pojo.PfpAdRate;
import com.pchome.akbpfp.db.service.IBaseService;




public interface IPfpAdRateService extends IBaseService<PfpAdRate,String>{
	
	public Long getDateAdCount(String date) throws Exception;
	
	
}
