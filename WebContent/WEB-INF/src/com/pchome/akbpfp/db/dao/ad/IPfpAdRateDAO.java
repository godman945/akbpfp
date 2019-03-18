package com.pchome.akbpfp.db.dao.ad;

import java.util.List;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpAdExcludeKeyword;
import com.pchome.akbpfp.db.pojo.PfpAdRate;

public interface IPfpAdRateDAO extends IBaseDAO<PfpAdRate,String>{
	
	public Long getDateAdCount(String date) throws Exception;


}
