package com.pchome.akbpfp.db.service.ad;

import java.util.List;

import com.pchome.akbpfp.db.dao.ad.IPfpAdExcludeKeywordDAO;
import com.pchome.akbpfp.db.dao.ad.PfpAdExcludeKeywordDAO;
import com.pchome.akbpfp.db.pojo.PfpAdExcludeKeyword;
import com.pchome.akbpfp.db.service.BaseService;

public class PfpAdExcludeKeywordService extends BaseService<PfpAdExcludeKeyword,String> implements IPfpAdExcludeKeywordService{

	
	public List<PfpAdExcludeKeyword> getAllPfpAdExcludeKeywords() throws Exception{
		return ((PfpAdExcludeKeywordDAO)dao).loadAll();
	}
	
	public List<PfpAdExcludeKeyword> getPfpAdExcludeKeywords(String adExcludeKeywordSeq, String adGroupSeq, String adExcludeKeyword) throws Exception{
		return ((PfpAdExcludeKeywordDAO)dao).getPfpAdExcludeKeywords(adExcludeKeywordSeq, adGroupSeq, adExcludeKeyword);
	}
	
	public List<PfpAdExcludeKeyword> getPfpAdExcludeKeywords(String adGroupSeq, String customerInfoId) throws Exception{
		return ((IPfpAdExcludeKeywordDAO)dao).getPfpAdExcludeKeywords(adGroupSeq,customerInfoId);
	}
	
	public PfpAdExcludeKeyword getPfpAdExcludeKeywordBySeq(String adExcludeKeywordSeq) throws Exception {
		return ((PfpAdExcludeKeywordDAO)dao).getPfpAdExcludeKeywordBySeq(adExcludeKeywordSeq);
	}

	public List<PfpAdExcludeKeyword> findAdExcludeKeywords(String adGroupSeq) {
		return ((IPfpAdExcludeKeywordDAO)dao).findAdExcludeKeywords(adGroupSeq);
	}
	
	public void insertPfpAdExcludeKeyword(PfpAdExcludeKeyword pfpAdExcludeKeyword) throws Exception {
		((PfpAdExcludeKeywordDAO)dao).insertPfpAdExcludeKeyword(pfpAdExcludeKeyword);
	}

	public void updatePfpAdExcludeKeyword(PfpAdExcludeKeyword pfpAdExcludeKeyword) throws Exception {
		((PfpAdExcludeKeywordDAO)dao).updatePfpAdExcludeKeyword(pfpAdExcludeKeyword);
	}

	public void deletePfpAdExcludeKeyword(String adExcludeKeywordSeq) throws Exception {
		((PfpAdExcludeKeywordDAO)dao).deletePfpAdExcludeKeyword(adExcludeKeywordSeq);
	}

	public void savePfpAdExcludeKeyword(PfpAdExcludeKeyword pfpAdExcludeKeyword) throws Exception {
		((PfpAdExcludeKeywordDAO)dao).saveOrUpdatePfpAdExcludeKeyword(pfpAdExcludeKeyword);
	}
	
	public static void main(String arg[]) throws Exception{
	}
}
