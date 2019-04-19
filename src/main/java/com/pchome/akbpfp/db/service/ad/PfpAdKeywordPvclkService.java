package com.pchome.akbpfp.db.service.ad;

import com.pchome.akbpfp.db.dao.ad.PfpAdKeywordPvclkDAO;
import com.pchome.akbpfp.db.pojo.PfpAdKeywordPvclk;
import com.pchome.akbpfp.db.service.BaseService;

public class PfpAdKeywordPvclkService extends BaseService<PfpAdKeywordPvclk,String> implements IPfpAdKeywordPvclkService{
	
	private PfpAdKeywordPvclkDAO pfpAdKeywordPvclkDAO;

	public void setPfpAdKeywordPvclkDAO(PfpAdKeywordPvclkDAO pfpAdKeywordPvclkDAO) throws Exception {
		this.pfpAdKeywordPvclkDAO = pfpAdKeywordPvclkDAO;
	}

	public static void main(String arg[]) throws Exception{
	}


}
