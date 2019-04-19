package com.pchome.akbpfp.db.service.codeManage;

import java.util.List;

import com.pchome.akbpfp.db.pojo.PfpCodeTracking;
import com.pchome.akbpfp.db.service.IBaseService;
import com.pchome.akbpfp.db.vo.codeManage.RetargetingTrackingVO;

public interface IPfpCodeTrackingService extends IBaseService<PfpCodeTracking,String>{
	
	public List<Object> getRetargetingTrackingList(RetargetingTrackingVO retargetingTrackingVO) throws Exception;
	
	public String getRetargetingTrackingCount(RetargetingTrackingVO retargetingTrackingVO) throws Exception;
	
	public void updateTrackingStatus(String pfpCustomerInfoId, String trackingSeq,String trackingStatus) throws Exception;
	
	public RetargetingTrackingVO getPfpCodeTrackingByCondition(RetargetingTrackingVO retargetingTrackingVO) throws Exception;
	
	public List<PfpCodeTracking> findTrackingCodeByCustomerInfoId(String pfpCustomerInfoId) throws Exception;
	
	public List<PfpCodeTracking> findPfpCodeTrackingList(String pfpCustomerInfoId) throws Exception;
	
}