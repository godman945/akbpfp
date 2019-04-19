package com.pchome.akbpfp.db.dao.sysprice;

import java.util.List;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpSyspriceRate;

public interface IPfpSyspriceRateDAO extends IBaseDAO<PfpSyspriceRate, String>{

	public List<Object> getPoolSyspriceRate(String date) throws Exception;
		
	public List<Object> getSyspriceRate(String date) throws Exception;
	
	public float getNewSyspriceRate(String date) throws Exception;
}
