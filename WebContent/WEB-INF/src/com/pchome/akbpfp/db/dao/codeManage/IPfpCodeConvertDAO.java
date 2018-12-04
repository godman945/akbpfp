package com.pchome.akbpfp.db.dao.codeManage;

import java.util.List;
import java.util.Map;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpCodeConvert;
import com.pchome.akbpfp.db.vo.codeManage.ConvertTrackingVO;


public interface IPfpCodeConvertDAO extends IBaseDAO<PfpCodeConvert,String>{
		
	public void saveOrUpdateWithCommit(PfpCodeConvert pfpCodeConvert) throws Exception;
	
	public String getConvertTrackingCount(ConvertTrackingVO convertTrackingVO) throws Exception;
	
	public void deletePfpCodeConvert(PfpCodeConvert pfpCodeConvert) throws Exception;
	
	public List<Map<String,Object>> getPfpCodeConvertByCondition(ConvertTrackingVO convertTrackingVO) throws Exception;
}
