package com.pchome.akbpfp.db.service.ad;

import java.util.List;

import com.pchome.akbpfp.db.dao.ad.IPfpAdRateDAO;
import com.pchome.akbpfp.db.dao.ad.PfpAdKeywordDAO;
import com.pchome.akbpfp.db.dao.ad.PfpAdRateDAO;
import com.pchome.akbpfp.db.pojo.PfpAdRate;
import com.pchome.akbpfp.db.service.BaseService;


public class PfpAdRateService extends BaseService<PfpAdRate,String> implements IPfpAdRateService{

	@Override
	public Long getDateAdCount(String date) throws Exception {
		// TODO Auto-generated method stub
		return ((PfpAdRateDAO)dao).getDateAdCount(date);
	}

	


	
}
