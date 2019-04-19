package com.pchome.akbpfp.db.service.codeManage;

import java.util.List;

import com.pchome.akbpfp.db.pojo.PfpCodeAdactionMerge;
import com.pchome.akbpfp.db.service.IBaseService;

public interface IPfpCodeAdActionMergeService extends IBaseService<PfpCodeAdactionMerge,Integer>{
	
	public List<PfpCodeAdactionMerge> findProdCodeByAdactionSeq(String adActionSeq) throws Exception;
	
	public int deleteProdCodeByCodeType(String codeType,String adActionSeq)throws Exception;
	
}