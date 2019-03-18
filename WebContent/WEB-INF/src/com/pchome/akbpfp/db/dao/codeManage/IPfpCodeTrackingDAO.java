package com.pchome.akbpfp.db.dao.codeManage;

import java.util.List;
import java.util.Map;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpCodeTracking;
import com.pchome.akbpfp.db.vo.codeManage.RetargetingTrackingVO;


public interface IPfpCodeTrackingDAO extends IBaseDAO<PfpCodeTracking,String>{
		
//	public void saveOrUpdateWithCommit(PfpCode pfpCode) throws Exception;
	public List<Map<String,Object>> getRetargetingTrackingList(RetargetingTrackingVO retargetingTrackingVO) throws Exception;
	
	public String getRetargetingTrackingCount(RetargetingTrackingVO retargetingTrackingVO) throws Exception;
	
	public void updateTrackingStatus(String pfpCustomerInfoId, String trackingSeq,String trackingStatus) throws Exception;
	
	public List<Map<String,Object>> getPfpCodeTrackingByCondition(RetargetingTrackingVO retargetingTrackingVO) throws Exception;
	
}
