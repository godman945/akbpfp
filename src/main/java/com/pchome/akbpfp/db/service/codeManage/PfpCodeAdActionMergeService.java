package com.pchome.akbpfp.db.service.codeManage;

import java.util.List;

import com.pchome.akbpfp.db.dao.codeManage.IPfpCodeAdActionMergeDAO;
import com.pchome.akbpfp.db.pojo.PfpCodeAdactionMerge;
import com.pchome.akbpfp.db.service.BaseService;

public class PfpCodeAdActionMergeService extends BaseService<PfpCodeAdactionMerge,Integer> implements IPfpCodeAdActionMergeService{
	
	public List<PfpCodeAdactionMerge> findProdCodeByAdactionSeq(String adActionSeq) throws Exception{
		return ((IPfpCodeAdActionMergeDAO)dao).findProdCodeByAdactionSeq(adActionSeq);
	}
	
	public int deleteProdCodeByCodeType(String codeType,String adActionSeq)throws Exception{
		return ((IPfpCodeAdActionMergeDAO)dao).deleteProdCodeByCodeType(codeType,adActionSeq);
	}
	
}