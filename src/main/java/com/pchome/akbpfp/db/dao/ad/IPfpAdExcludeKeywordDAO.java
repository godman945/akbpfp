package com.pchome.akbpfp.db.dao.ad;

import java.util.List;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpAdExcludeKeyword;

public interface IPfpAdExcludeKeywordDAO extends IBaseDAO<PfpAdExcludeKeyword,String>{
	
	public List<PfpAdExcludeKeyword> getPfpAdExcludeKeywords(String adExcludeKeywordSeq, String adGroupSeq, String adExcludeKeyword) throws Exception;

	public PfpAdExcludeKeyword getPfpAdExcludeKeywordBySeq(String adExcludeKeywordSeq) throws Exception;
	
	public void saveOrUpdatePfpAdExcludeKeyword(PfpAdExcludeKeyword pfpAdExcludeKeyword) throws Exception;
	
	public void insertPfpAdExcludeKeyword(PfpAdExcludeKeyword pfpAdExcludeKeyword) throws Exception;
	
	public void updatePfpAdExcludeKeyword(PfpAdExcludeKeyword pfpAdExcludeKeyword) throws Exception;
	
	public void deletePfpAdExcludeKeyword(String adExcludeKeywordSeq) throws Exception;
	
	public List<PfpAdExcludeKeyword> getPfpAdExcludeKeywords(String adGroupSeq, String customerInfoId) throws Exception;
	
	public List<PfpAdExcludeKeyword> findAdExcludeKeywords(String adGroupSeq);
}
