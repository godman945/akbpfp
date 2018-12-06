package com.pchome.akbpfp.db.service.codeManage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.pchome.akbpfp.api.RedisAPI;
import com.pchome.akbpfp.db.dao.catalog.prod.IPfpCatalogProdEcDAO;
import com.pchome.akbpfp.db.dao.codeManage.IPfpCodeTrackingDAO;
import com.pchome.akbpfp.db.pojo.PfpCodeTracking;
import com.pchome.akbpfp.db.service.BaseService;
import com.pchome.akbpfp.db.vo.codeManage.RetargetingTrackingVO;
import com.pchome.enumerate.codeManage.EnumVerifyStatusType;

public class PfpCodeTrackingService extends BaseService<PfpCodeTracking,String> implements IPfpCodeTrackingService{
	private RedisAPI redisAPI;
	String codeManageRediskey;
	
	
	
//	public void saveOrUpdateWithCommit(PfpCode pfpCode) throws Exception{
//	    	((IPfpCodeDAO) dao).saveOrUpdateWithCommit(pfpCode);
//	}
	
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
				retargetingTrackingBean.setCodeType(obj.get("code_type").toString());		
				retargetingTrackingBean.setTrackingRangeDate(obj.get("tracking_range_date").toString());	
				
				
				//stg:pa:codecheck:traceId002
				String redisKey =codeManageRediskey+retargetingTrackingBean.getTrackingSeq();
				String redisData = redisAPI.getRedisData(redisKey); // 查詢此客戶redis是否有資料
				if (redisData == null){
					retargetingTrackingBean.setVerifyStatus(EnumVerifyStatusType.Unverified.getType());
				}else{
					retargetingTrackingBean.setVerifyStatus(EnumVerifyStatusType.Verified.getType());
				}
					
				
				
//				//商品庫存中文
//				for(EnumEcStockStatusType ecStockStatusType:EnumEcStockStatusType.values()){
//					if( StringUtils.equals(ecStockStatusType.getType(), obj.get("ec_stock_status").toString()) ){
//						prodListBean.setEcStockStatusDesc(ecStockStatusType.getChName());
//						break;
//					}
//				}
				
				retargetingTrackingBeanLists.add(retargetingTrackingBean);
			}
		}
		return retargetingTrackingBeanLists;
	}
	
	public String getRetargetingTrackingCount(RetargetingTrackingVO retargetingTrackingVO) throws Exception{
		return ((IPfpCodeTrackingDAO)dao).getRetargetingTrackingCount(retargetingTrackingVO);
	}
	
	
	public void updateTrackingStatus(String pfpCustomerInfoId, List<String> retargetingIdArray,String trackingStatus) throws Exception{
		((IPfpCodeTrackingDAO)dao).updateTrackingStatus(pfpCustomerInfoId,retargetingIdArray,trackingStatus);
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
	
	
	
	
	
	
	



	public RedisAPI getRedisAPI() {
		return redisAPI;
	}

	public void setRedisAPI(RedisAPI redisAPI) {
		this.redisAPI = redisAPI;
	}



	public String getCodeManageRediskey() {
		return codeManageRediskey;
	}



	public void setCodeManageRediskey(String codeManageRediskey) {
		this.codeManageRediskey = codeManageRediskey;
	}
	
	
	
	
	
}