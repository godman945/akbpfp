package com.pchome.akbpfp.db.service.sysprice;

import java.util.List;

import com.pchome.akbpfp.db.pojo.PfpSyspriceRate;
import com.pchome.akbpfp.db.service.IBaseService;

public interface IPfpSyspriceRateService extends IBaseService<PfpSyspriceRate, String>{

	public List<Object> getPoolSyspriceRate(String date) throws Exception;
	
	public List<Object> getSyspriceRate(String date) throws Exception;
	
	public float getNewSyspriceRate(String date) throws Exception;
}
