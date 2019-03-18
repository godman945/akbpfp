package com.pchome.akbpfp.db.service.codeManage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.pchome.akbpfp.db.dao.codeManage.IPfpCodeTrackingDAO;
import com.pchome.akbpfp.db.pojo.PfpCodeTracking;
import com.pchome.akbpfp.db.service.BaseService;
import com.pchome.akbpfp.db.vo.codeManage.RetargetingTrackingVO;
import com.pchome.enumerate.codeManage.EnumRetargetingCodeType;
import com.pchome.enumerate.codeManage.EnumVerifyStatusType;
import com.pchome.soft.depot.utils.RedisUtil;

public class PfpCodeTrackingService extends BaseService<PfpCodeTracking,String> implements IPfpCodeTrackingService{
	
	String codeManageRediskey;
	
	
	public List<Object> getRetargetingTrackingList(RetargetingTrackingVO retargetingTrackingVO) throws Exception{
		List<Map<String,Object>> retargetingTrackingLists = ((IPfpCodeTrackingDAO)dao).getRetargetingTrackingList(retargetingTrackingVO);
		List<Object> retargetingTrackingBeanLists = new ArrayList<Object>();
		
		if( (!retargetingTrackingLists.isEmpty()) && (retargetingTrackingLists.size()>0) ){
			
			for (Object object : retargetingTrackingLists) {
				RetargetingTrackingVO retargetingTrackingBean = new RetargetingTrackingVO();
				
				Map obj = (Map) object;
				retargetingTrackingBean.setTrackingSeq(obj.get("tracking_seq").toString());	
				retargetingTrackingBean.setTrackingName(obj.get("tracking_name").toString());
				retargetingTrackingBean.setPaId(obj.get("pa_id").toString());	
				retargetingTrackingBean.setTrackingStatus(obj.get("tracking_status").toString());		
				retargetingTrackingBean.setCodeType(obj.get("code_type").toString());		
				retargetingTrackingBean.setTrackingRangeDate(obj.get("tracking_range_date").toString());	
				
				
				//stg:pa:codecheck:TAC20181114000000026
				String redisKey =codeManageRediskey+retargetingTrackingBean.getTrackingSeq();
				String redisData = RedisUtil.getInstance().getKey(redisKey); // 查詢此客戶redis是否有資料
				
				if ( redisData ==null ){
					retargetingTrackingBean.setVerifyStatus(EnumVerifyStatusType.Unverified.getType());
				}else{
					retargetingTrackingBean.setVerifyStatus(EnumVerifyStatusType.Verified.getType());
				}
					
				//代碼類型(0：一般網頁追蹤、1：動態商品廣告追蹤)
				for(EnumRetargetingCodeType ecCodeType:EnumRetargetingCodeType.values()){
					if( StringUtils.equals(ecCodeType.getType(), obj.get("code_type").toString()) ){
						retargetingTrackingBean.setCodeTypeDesc(ecCodeType.getChName());
						break;
					}
				}
				
				retargetingTrackingBeanLists.add(retargetingTrackingBean);
			}
		}
		return retargetingTrackingBeanLists;
	}
	
	
	public String getRetargetingTrackingCount(RetargetingTrackingVO retargetingTrackingVO) throws Exception{
		return ((IPfpCodeTrackingDAO)dao).getRetargetingTrackingCount(retargetingTrackingVO);
	}
	
	
	public void updateTrackingStatus(String pfpCustomerInfoId, String trackingSeq,String trackingStatus) throws Exception{
		((IPfpCodeTrackingDAO)dao).updateTrackingStatus(pfpCustomerInfoId,trackingSeq,trackingStatus);
	}
	
	public RetargetingTrackingVO getPfpCodeTrackingByCondition(RetargetingTrackingVO retargetingTrackingVO) throws Exception{
		List<Map<String,Object>> retargetingTrackingLists = ((IPfpCodeTrackingDAO)dao).getPfpCodeTrackingByCondition(retargetingTrackingVO);
		RetargetingTrackingVO retargetingTrackingBean = null;
		
		if( (!retargetingTrackingLists.isEmpty()) && (retargetingTrackingLists.size()>0) ){
			for (Object object : retargetingTrackingLists) {
				retargetingTrackingBean = new RetargetingTrackingVO();
				Map obj = (Map) object;
				retargetingTrackingBean.setTrackingSeq(obj.get("tracking_seq").toString());	
				retargetingTrackingBean.setTrackingName(obj.get("tracking_name").toString());
				retargetingTrackingBean.setPaId(obj.get("pa_id").toString());	
				retargetingTrackingBean.setPfpCustomerInfoId(obj.get("pfp_customer_info_id").toString());
				retargetingTrackingBean.setTrackingStatus(obj.get("tracking_status").toString());
				retargetingTrackingBean.setCodeType(obj.get("code_type").toString());		
				retargetingTrackingBean.setTrackingRangeDate(obj.get("tracking_range_date").toString());	
			}
		}
		
		return retargetingTrackingBean;
	}
	
	
	public List<PfpCodeTracking> findTrackingCodeByCustomerInfoId(String pfpCustomerInfoId) throws Exception {
		return ((IPfpCodeTrackingDAO)dao).findTrackingCodeByCustomerInfoId(pfpCustomerInfoId);
	}
	
	public List<PfpCodeTracking> findPfpCodeTrackingList(String pfpCustomerInfoId) throws Exception {
		return ((IPfpCodeTrackingDAO)dao).findPfpCodeTrackingList(pfpCustomerInfoId);
	}
	
	
	public String getCodeManageRediskey() {
		return codeManageRediskey;
	}

	public void setCodeManageRediskey(String codeManageRediskey) {
		this.codeManageRediskey = codeManageRediskey;
	}
	
}