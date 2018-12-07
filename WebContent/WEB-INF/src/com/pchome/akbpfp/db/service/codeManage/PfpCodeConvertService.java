package com.pchome.akbpfp.db.service.codeManage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.pchome.akbpfp.db.dao.codeManage.IPfpCodeConvertDAO;
import com.pchome.akbpfp.db.pojo.PfpCodeConvert;
import com.pchome.akbpfp.db.service.BaseService;
import com.pchome.akbpfp.db.vo.codeManage.ConvertTrackingRuleVO;
import com.pchome.akbpfp.db.vo.codeManage.ConvertTrackingVO;
import com.pchome.enumerate.codeManage.EnumConvertClassType;
import com.pchome.enumerate.codeManage.EnumConvertType;
import com.pchome.enumerate.codeManage.EnumVerifyStatusType;
import com.pchome.soft.depot.utils.RedisUtil;

public class PfpCodeConvertService extends BaseService<PfpCodeConvert,String> implements IPfpCodeConvertService{
	String codeManageRediskey;
	private IPfpCodeConvertRuleService pfpCodeConvertRuleService;
	
	
	public void saveOrUpdateWithCommit(PfpCodeConvert pfpCodeConvert) throws Exception{
    	((IPfpCodeConvertDAO) dao).saveOrUpdateWithCommit(pfpCodeConvert);
	}
	
	public String getConvertTrackingCount(ConvertTrackingVO convertTrackingVO) throws Exception{
		return ((IPfpCodeConvertDAO)dao).getConvertTrackingCount(convertTrackingVO);
	}
	
	public void deletePfpCodeConvert(PfpCodeConvert pfpCodeConvert) throws Exception{
		((IPfpCodeConvertDAO)dao).deletePfpCodeConvert(pfpCodeConvert);
	}
	
	
	public ConvertTrackingVO getPfpCodeConvertByCondition(ConvertTrackingVO convertTrackingVO) throws Exception{
		List<Map<String,Object>> convertTrackingList = ((IPfpCodeConvertDAO)dao).getPfpCodeConvertByCondition(convertTrackingVO);
		ConvertTrackingVO convertTrackingBean = null;
		
		if( (!convertTrackingList.isEmpty()) && (convertTrackingList.size()>0) ){
			for (Object object : convertTrackingList) {
				convertTrackingBean = new ConvertTrackingVO();
				Map obj = (Map) object;
				
				convertTrackingBean.setConvertSeq(obj.get("convert_seq").toString());	//轉換ID
				convertTrackingBean.setConvertName(obj.get("convert_name").toString());	//轉換名稱
				convertTrackingBean.setPaId(obj.get("pa_id").toString());	//pa_id 代碼
				convertTrackingBean.setPfpCustomerInfoId(obj.get("pfp_customer_info_id").toString());	//pfp_id
				convertTrackingBean.setConvertType(obj.get("convert_type").toString());	//轉換類型(1.標準轉換追蹤(預設)2.自訂轉換追蹤條件)
				convertTrackingBean.setClickRangeDate(obj.get("click_range_date").toString());	//點擊追蹤天數
				convertTrackingBean.setImpRangeDate(obj.get("imp_range_date").toString());	//曝光追蹤天數
				convertTrackingBean.setConvertClass(obj.get("convert_class").toString());	//轉換分類(1.查看內容 2.搜尋 3.加到購物車 4.加到購物清單 5.開始結帳 6.新增付款資料 7.購買 8.完成註冊)
				convertTrackingBean.setConvertPriceType(obj.get("convert_price_type").toString());	//轉換價值紀錄方式(1.不指定價值2.統一轉換價值3.自訂轉換價值)
				convertTrackingBean.setConvertPrice( String.valueOf(Math.round(Double.parseDouble(obj.get("convert_price").toString()))) );	//轉換價格
				convertTrackingBean.setConvertStatus(obj.get("convert_status").toString());	//轉換狀態(0:關閉;1:開啟;2:刪除)
				convertTrackingBean.setConvertBelong(obj.get("convert_belong").toString());	//歸因模式(1.最終點擊 2.最初點擊)
				convertTrackingBean.setConvertNumType(obj.get("convert_num_type").toString());	//轉換計算方式 1:每次,2:一次
				convertTrackingBean.setConvertRuleNum(obj.get("convert_rule_num").toString());	//轉換條件數量(0 沒有條件;有條件是 count(rule 數量))
			}
		}
		
		return convertTrackingBean;
	}
	
	
	
	public List<Object> getConvertTrackingList(ConvertTrackingVO convertTrackingVO) throws Exception{
		List<Map<String,Object>> convertTrackingLists = ((IPfpCodeConvertDAO)dao).getConvertTrackingList(convertTrackingVO);
		List<Object> convertTrackingBeanLists = new ArrayList<Object>();
		
		if( (!convertTrackingLists.isEmpty()) && (convertTrackingLists.size()>0) ){
			for (Object object : convertTrackingLists) {
				ConvertTrackingVO convertTrackingBean = new ConvertTrackingVO();
				Map obj = (Map) object;
				convertTrackingBean.setConvertSeq(obj.get("convert_seq").toString());	
				convertTrackingBean.setConvertName(obj.get("convert_name").toString());
				convertTrackingBean.setPaId(obj.get("pa_id").toString());	
				convertTrackingBean.setConvertType(obj.get("convert_type").toString());		
				convertTrackingBean.setConvertClass(obj.get("convert_class").toString());		
				convertTrackingBean.setClickRangeDate(obj.get("click_range_date").toString());
				convertTrackingBean.setImpRangeDate(obj.get("imp_range_date").toString());
				convertTrackingBean.setTransConvertPrice(obj.get("trans_convert_price").toString());		//轉換價值
				convertTrackingBean.setTransCKConvertCount(obj.get("trans_ck_convert_count").toString());	//點擊後轉換數
				convertTrackingBean.setTransPVConvertCount(obj.get("trans_pv_convert_count").toString());	//瀏覽後轉換數
				convertTrackingBean.setTransAllConvertCount( Integer.toString(Integer.parseInt(obj.get("trans_ck_convert_count").toString())+Integer.parseInt(obj.get("trans_pv_convert_count").toString())) );//所有轉換(點擊後轉換數+瀏覽後轉換數)
				convertTrackingBean.setConvertRuleNum(obj.get("convert_rule_num").toString());//轉換條件數量(0 沒有條件;有條件是 count(rule 數量))
				
				
				
				int convertRuleNum = Integer.parseInt(convertTrackingBean.getConvertRuleNum());
				if ( convertRuleNum > 0){
					//認證狀態(已認證、未認證)prd:pa:codecheck:CAC20181112000000001:RLE20180724000000001
					int count =0;
					List<ConvertTrackingRuleVO>convertTrackingRuleList = pfpCodeConvertRuleService.getPfpCodeConvertRuleByCondition(convertTrackingVO);
					for (ConvertTrackingRuleVO convertTrackingRuleVO : convertTrackingRuleList) {
						String redisKey =codeManageRediskey+convertTrackingBean.getConvertSeq()+":"+convertTrackingRuleVO.getConvertRuleId();
						String redisData = RedisUtil.getInstance().getKey(redisKey); // 查詢此客戶redis是否有資料
						if (StringUtils.isNotBlank(redisData)){
							count = count+1;
						}
					}
					
					if (convertRuleNum == count){
						convertTrackingBean.setVerifyStatus(EnumVerifyStatusType.Verified.getType());
					}else{
						convertTrackingBean.setVerifyStatus(EnumVerifyStatusType.Unverified.getType());						
					}
				}else{
					//認證狀態(已認證、未認證)prd:pa:codecheck:CAC20181112000000001
					String redisKey =codeManageRediskey+convertTrackingBean.getConvertSeq();
					String redisData = RedisUtil.getInstance().getKey(redisKey); // 查詢此客戶redis是否有資料
					if (StringUtils.isBlank(redisData)){
						convertTrackingBean.setVerifyStatus(EnumVerifyStatusType.Unverified.getType());
					}else{
						convertTrackingBean.setVerifyStatus(EnumVerifyStatusType.Verified.getType());
					}
				}
				
				//轉換類型中文(1.標準轉換追蹤(預設)2.自訂轉換追蹤條件)
				for(EnumConvertType convertType:EnumConvertType.values()){
					if( StringUtils.equals(convertType.getType(), obj.get("convert_type").toString()) ){
						convertTrackingBean.setConvertTypeDesc(convertType.getChName());	
						break;
					}
				}
				
				//轉換分類(1.查看內容 2.搜尋 3.加到購物車 4.加到購物清單 5.開始結帳 6.新增付款資料 7.購買 8.完成註冊)
				for(EnumConvertClassType convertClassType:EnumConvertClassType.values()){
					if( StringUtils.equals(convertClassType.getType(), obj.get("convert_class").toString()) ){
						convertTrackingBean.setConvertClassDesc(convertClassType.getChName());	
						break;
					}
				}
				
				
					
				convertTrackingBeanLists.add(convertTrackingBean);
			}
		}
		return convertTrackingBeanLists;
	}
	
	
	public ConvertTrackingVO getSumConvertCount(ConvertTrackingVO convertTrackingVO) throws Exception{
		List<Map<String,Object>> convertTrackingList = ((IPfpCodeConvertDAO)dao).getSumConvertCount(convertTrackingVO);
		ConvertTrackingVO convertTrackingBean = new ConvertTrackingVO();
		
		for (Object object : convertTrackingList) {
		Map obj = (Map) object;
			convertTrackingBean.setTransSumCKConvertCount(obj.get("sum_ck_convert_count").toString());	//加總ck點擊後轉換數
			convertTrackingBean.setTransSumPVConvertCount(obj.get("sum_pv_convert_count").toString());	//加總pv瀏覽後轉換數
			convertTrackingBean.setTransSumAllConvertCount(Integer.toString(Integer.parseInt(obj.get("sum_ck_convert_count").toString())+Integer.parseInt(obj.get("sum_pv_convert_count").toString())) );//加總所有轉換(ck+pv)
		}
		
		return convertTrackingBean;
	}
	
	
	public void updateConvertStatus(String pfpCustomerInfoId, List<String> convertIdArray,String convertStatus) throws Exception{
		((IPfpCodeConvertDAO)dao).updateConvertStatus(pfpCustomerInfoId,convertIdArray,convertStatus);
	}
	
	
	
	


	public String getCodeManageRediskey() {
		return codeManageRediskey;
	}

	public void setCodeManageRediskey(String codeManageRediskey) {
		this.codeManageRediskey = codeManageRediskey;
	}

	public IPfpCodeConvertRuleService getPfpCodeConvertRuleService() {
		return pfpCodeConvertRuleService;
	}

	public void setPfpCodeConvertRuleService(IPfpCodeConvertRuleService pfpCodeConvertRuleService) {
		this.pfpCodeConvertRuleService = pfpCodeConvertRuleService;
	}
	
	
}