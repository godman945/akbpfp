package com.pchome.akbpfp.db.dao.ad;

import java.util.List;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpAdKeywordPvclk;

public interface IPfpAdKeywordPvclkDAO extends IBaseDAO<PfpAdKeywordPvclk,String>{
	
	public List<PfpAdKeywordPvclk> getPfpAdKeywordPvclks(String adKeywordPvclkSeq, String adKeywordSeq, String adKeywordPvclkDate, String adKeywordPv, String adKeywordClk) throws Exception;
	
	public PfpAdKeywordPvclk getPfpAdKeywordPvclkBySeq(String adKeywordPvclkSeq) throws Exception;
	
	public void saveOrUpdatePfpAdKeywordPvclk(PfpAdKeywordPvclk pfpAdKeywordPvclk) throws Exception;

	public void insertPfpAdKeywordPvclk(PfpAdKeywordPvclk pfpAdKeywordPvclk) throws Exception;
	
	public void updatePfpAdKeywordPvclk(PfpAdKeywordPvclk pfpAdKeywordPvclk) throws Exception;
	
	public void deletePfpAdKeywordPvclk(String adKeywordPvclkSeq) throws Exception;
}
