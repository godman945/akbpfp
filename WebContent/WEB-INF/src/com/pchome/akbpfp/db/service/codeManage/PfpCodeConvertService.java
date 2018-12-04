package com.pchome.akbpfp.db.service.codeManage;

import java.util.List;
import java.util.Map;

import com.pchome.akbpfp.api.RedisAPI;
import com.pchome.akbpfp.db.dao.codeManage.IPfpCodeConvertDAO;
import com.pchome.akbpfp.db.pojo.PfpCodeConvert;
import com.pchome.akbpfp.db.service.BaseService;
import com.pchome.akbpfp.db.vo.codeManage.ConvertTrackingVO;

public class PfpCodeConvertService extends BaseService<PfpCodeConvert,String> implements IPfpCodeConvertService{
	private RedisAPI redisAPI;
	String codeManageRediskey;
	
	
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