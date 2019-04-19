package com.pchome.akbpfp.db.dao.codeManage;

import java.util.List;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpCodeAdactionMerge;


public interface IPfpCodeAdActionMergeDAO extends IBaseDAO<PfpCodeAdactionMerge,Integer>{
		
	public List<PfpCodeAdactionMerge> findProdCodeByAdactionSeq(String adActionSeq) throws Exception;

	public int deleteProdCodeByCodeType(String codeType,String adActionSeq)throws Exception;
}
