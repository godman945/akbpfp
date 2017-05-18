package com.pchome.akbpfp.db.service.sysprice;

import java.util.List;

import com.pchome.akbpfp.db.dao.sysprice.PfpSyspriceRateDAO;
import com.pchome.akbpfp.db.pojo.PfpSyspriceRate;
import com.pchome.akbpfp.db.service.BaseService;

public class PfpSyspriceRateService extends BaseService <PfpSyspriceRate,String> implements IPfpSyspriceRateService{

	public List<Object> getPoolSyspriceRate(String date) throws Exception{
		
		return ((PfpSyspriceRateDAO)dao).getPoolSyspriceRate(date);
	}
	
	public List<Object> getSyspriceRate(String date) throws Exception{
		
		return ((PfpSyspriceRateDAO)dao).getSyspriceRate(date);
	}
	
	public float getNewSyspriceRate(String date) throws Exception {
		return ((PfpSyspriceRateDAO)dao).getNewSyspriceRate(date);
	}
}
