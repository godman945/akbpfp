package com.pchome.akbpfp.db.service.codeManage;

import java.util.List;

import com.pchome.akbpfp.db.pojo.PfpCodeConvert;
import com.pchome.akbpfp.db.service.IBaseService;
import com.pchome.akbpfp.db.vo.codeManage.ConvertTrackingVO;

public interface IPfpCodeConvertService extends IBaseService<PfpCodeConvert,String>{
	
	public void saveOrUpdateWithCommit(PfpCodeConvert pfpCodeConvert)throws Exception;
	
	public String getConvertTrackingCount(ConvertTrackingVO convertTrackingVO) throws Exception;
	
	public void deletePfpCodeConvert(PfpCodeConvert pfpCodeConvert) throws Exception;
	
	public ConvertTrackingVO getPfpCodeConvertByCondition(ConvertTrackingVO convertTrackingVO) throws Exception;
	
	public List<Object> getConvertTrackingList(ConvertTrackingVO convertTrackingVO) throws Exception;
	
	public ConvertTrackingVO getSumConvertCount(ConvertTrackingVO convertTrackingVO) throws Exception;
	
	public void updateConvertStatus(String pfpCustomerInfoId, String convertSeq,String convertStatus) throws Exception;
}