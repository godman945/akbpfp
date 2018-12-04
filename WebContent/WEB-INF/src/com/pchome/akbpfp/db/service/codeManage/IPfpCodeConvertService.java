package com.pchome.akbpfp.db.service.codeManage;

import com.pchome.akbpfp.db.pojo.PfpCodeConvert;
import com.pchome.akbpfp.db.service.IBaseService;
import com.pchome.akbpfp.db.vo.codeManage.ConvertTrackingVO;

public interface IPfpCodeConvertService extends IBaseService<PfpCodeConvert,String>{
	
	public void saveOrUpdateWithCommit(PfpCodeConvert pfpCodeConvert)throws Exception;
	
	public String getConvertTrackingCount(ConvertTrackingVO convertTrackingVO) throws Exception;
	
	public void deletePfpCodeConvert(PfpCodeConvert pfpCodeConvert) throws Exception;
	
	public ConvertTrackingVO getPfpCodeConvertByCondition(ConvertTrackingVO convertTrackingVO) throws Exception;
}