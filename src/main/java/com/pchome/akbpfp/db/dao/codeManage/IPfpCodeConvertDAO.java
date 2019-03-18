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
	
	public List<Map<String,Object>> getConvertTrackingList(ConvertTrackingVO convertTrackingVO) throws Exception;
	
	public List<PfpCodeConvert> findPfpCodeConvertList(ConvertTrackingVO convertTrackingVO) throws Exception;
	
	public List<PfpCodeConvert> findPfpCodeConvertListAll(ConvertTrackingVO convertTrackingVO) throws Exception;
	
	public void updateConvertStatus(String pfpCustomerInfoId, String convertSeq,String convertStatus) throws Exception;
	
	public List<PfpCodeConvert> findConvertCodeByCustomerInfoId(String pfpCustomerInfoId) throws Exception;
	
	public List<PfpCodeConvert> findPfpCodeConvertListByCustomerInfoId(String pfpCustomerInfoId) throws Exception;
}
