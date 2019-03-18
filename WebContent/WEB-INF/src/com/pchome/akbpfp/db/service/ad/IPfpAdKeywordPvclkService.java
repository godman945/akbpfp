package com.pchome.akbpfp.db.service.ad;

import com.pchome.akbpfp.db.dao.ad.PfpAdKeywordPvclkDAO;
import com.pchome.akbpfp.db.pojo.PfpAdKeywordPvclk;
import com.pchome.akbpfp.db.service.IBaseService;


public interface IPfpAdKeywordPvclkService extends IBaseService<PfpAdKeywordPvclk,String>{

	public void setPfpAdKeywordPvclkDAO(PfpAdKeywordPvclkDAO pfpAdKeywordPvclkDAO) throws Exception;

}
