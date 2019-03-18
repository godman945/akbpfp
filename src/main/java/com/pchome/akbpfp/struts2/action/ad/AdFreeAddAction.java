package com.pchome.akbpfp.struts2.action.ad;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pchome.akbpfp.api.SyspriceOperaterAPI;
import com.pchome.akbpfp.db.pojo.PfpAdAction;
import com.pchome.akbpfp.db.pojo.PfpAdGroup;
import com.pchome.akbpfp.db.pojo.PfpCustomerInfo;
import com.pchome.akbpfp.db.service.ad.DefineAdService;
import com.pchome.akbpfp.db.service.ad.PfpAdActionService;
import com.pchome.akbpfp.db.service.ad.PfpAdDetailService;
import com.pchome.akbpfp.db.service.ad.PfpAdExcludeKeywordService;
import com.pchome.akbpfp.db.service.ad.PfpAdGroupService;
import com.pchome.akbpfp.db.service.ad.PfpAdKeywordService;
import com.pchome.akbpfp.db.service.ad.PfpAdService;
import com.pchome.akbpfp.db.service.customerInfo.PfpCustomerInfoService;
import com.pchome.akbpfp.db.service.sequence.ISequenceService;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.sequence.EnumSequenceTableName;
import com.pchome.enumerate.utils.EnumStatus;
import com.pchome.soft.depot.utils.HttpUtil;


public class AdFreeAddAction extends BaseCookieAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PfpCustomerInfoService pfpCustomerInfoService;
	private ISequenceService sequenceService;
	private PfpAdActionService pfpAdActionService;
	private PfpAdGroupService pfpAdGroupService;
	private SyspriceOperaterAPI syspriceOperaterAPI;
	private PfpAdService pfpAdService;
	private PfpAdDetailService pfpAdDetailService;
	private PfpAdKeywordService pfpAdKeywordService;
	private PfpAdExcludeKeywordService pfpAdExcludeKeywordService;
	private DefineAdService defineAdService;
	
	private PfpAdAction pfpAdAction;
	private String adGroupSeq;
	private String adAdSeq;
	private String sysPriceAdPoolSeq;       //廣告建議價取得 pool from api prop 注入

	// get data
	private String aid;

	// return data
	private InputStream msg;

	/**
	 * @return
	 * @throws Exception
	 */
	public String adFreeAdd() throws Exception{
		System.out.println("adFreeAdd");
		System.out.println("aid= " + aid);
		
		String result = "";
		if(aid != null && !aid.trim().equals("")) {
			PfpCustomerInfo pfpCustomerInfo = pfpCustomerInfoService.findCustomerInfo(super.getCustomer_info_id());
			String customerInfoId = pfpCustomerInfoService.findCustomerInfo(super.getCustomer_info_id()).getCustomerInfoId();
			String member_id = pfpCustomerInfo.getMemberId();
			System.out.println("memberID = " + pfpCustomerInfo.getMemberId());
			String kwApi = "http://classifiedsstg.pchome.com.tw/api/showad/get_ad_info.php?member_id="+member_id+"&aid="+aid;
			System.out.println("kwApi = " + kwApi);
			result = HttpUtil.getInstance().getResult(kwApi, "UTF-8");
			System.out.println("result = " + result);

	        try {
	            JSONObject json = new JSONObject(result);
	            System.out.println("json = " + json);
	            System.out.println("status = " + json.getString("status"));
	            if(json.getString("status").equals("success")) {
	            	JSONArray jsonArray = json.getJSONArray("data");
	            
		            for (int i = 0; i < jsonArray.length(); i++) {
		                //取得jsonArray的物件轉成JSONObject
		                JSONObject jsonItem = jsonArray.getJSONObject(i);
		                
		                System.out.println("ad_no = " + jsonItem.getString("ad_no"));
		                System.out.println("title = " + jsonItem.getString("title"));
		                System.out.println("img_path = " + jsonItem.getString("img_path"));
		                System.out.println("va_1 = " + jsonItem.getString("va_1"));
		                System.out.println("va_2 = " + jsonItem.getString("va_2"));
		                System.out.println("va_3 = " + jsonItem.getString("va_3"));
		                System.out.println("va_4 = " + jsonItem.getString("va_4"));
		                System.out.println("url = " + jsonItem.getString("url"));
		                System.out.println("content = " + jsonItem.getString("content"));
		                System.out.println("tags = " + jsonItem.getString("tags"));
		                System.out.println("first_class = " + jsonItem.getString("first_class"));
		                System.out.println("sec_class = " + jsonItem.getString("sec_class"));
		                System.out.println("st_date = " + jsonItem.getString("st_date"));
		                System.out.println("ed_date = " + jsonItem.getString("ed_date"));
		                System.out.println("ad_name = " + jsonItem.getString("ad_name"));
		                
//		                AdFreeVO adFreeVo = new AdFreeVO();
//		                adFreeVo.setAd_no(jsonItem.getString("ad_no"));
//		                adFreeVo.setTitle(jsonItem.getString("title"));
//		                adFreeVo.setImg_path(jsonItem.getString("img_path"));
//		                adFreeVo.setVa_1(jsonItem.getString("va_1"));
//		                adFreeVo.setVa_2(jsonItem.getString("va_2"));
//		                adFreeVo.setVa_3(jsonItem.getString("va_3"));
//		                adFreeVo.setVa_4(jsonItem.getString("va_4"));
//		                adFreeVo.setUrl(jsonItem.getString("url"));
//		                adFreeVo.setContent(jsonItem.getString("content"));
//		                adFreeVo.setTags(jsonItem.getString("tags"));
//		                adFreeVo.setFirst_class(jsonItem.getString("first_class"));
//		                adFreeVo.setSec_class(jsonItem.getString("sec_class"));
//	
//		                if(adUtils.CheckDate(jsonItem.getString("st_date"))) {
//			                adFreeVo.setSt_date(adUtils.DateFormat(jsonItem.getString("st_date")));
//		                }
//		                if(adUtils.CheckDate(jsonItem.getString("ed_date"))) {
//			                adFreeVo.setEd_date(adUtils.DateFormat(jsonItem.getString("ed_date")));
//		                } else {
//		                	adFreeVo.setEd_date(adUtils.DateFormat("3000-12-31"));
//		                }
//		                adFreeVo.setAd_name(jsonItem.getString("ad_name"));
		                
		                //// 新增廣告活動
		                //String actionMsg = addPfpAdAction(adFreeVo);
		                
		                //// 新增廣告分類
		                //addPfpAdGroup(adFreeVo);
		            }
	            }

	        } catch (JSONException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
			
			msg = new ByteArrayInputStream(result.toString().getBytes());
		} else {
			msg = new ByteArrayInputStream("noAid".getBytes());
		}
		return SUCCESS;
	}

	private String addPfpAdAction(JSONObject jsonItem) throws Exception {
		String actionMsg = "";
		String adActionSeq = "";
		boolean actionExist = pfpAdActionService.chkAdActionNameByCustomerInfoId(jsonItem.getString("title"), null, super.getCustomer_info_id());
		if(!actionExist) {
			pfpAdAction = new PfpAdAction();
			adActionSeq = sequenceService.getId(EnumSequenceTableName.PFP_AD_ACTION, "_");
			pfpAdAction.setAdActionSeq(adActionSeq);
			pfpAdAction.setAdActionCreatTime(new Date());
			pfpAdAction.setAdActionName(jsonItem.getString("title"));
			pfpAdAction.setAdActionDesc(jsonItem.getString("title"));
			pfpAdAction.setAdActionStartDate(adUtils.DateFormat(jsonItem.getString("st_date")));
			pfpAdAction.setAdActionEndDate(adUtils.DateFormat(jsonItem.getString("ed_date")));
			pfpAdAction.setAdActionMax(100);
			pfpAdAction.setAdActionControlPrice(100f);
			pfpAdAction.setAdActionStatus(EnumStatus.UnDone.getStatusId());		// 新增廣告時，status 設定為未完成
			pfpAdAction.setUserId(super.getUser_id());
			pfpAdAction.setPfpCustomerInfo(pfpCustomerInfoService.findCustomerInfo(super.getCustomer_info_id()));
			pfpAdAction.setAdActionUpdateTime(new Date());
	
			pfpAdActionService.savePfpAdAction(pfpAdAction);
		} else {
			actionMsg = "AdActionName is exist";
			pfpAdAction = pfpAdActionService.getAdActionByAdActionName(jsonItem.getString("title"), super.getCustomer_info_id());
			adActionSeq = pfpAdAction.getAdActionSeq();
		}
		return actionMsg;
	}
	
	private void addPfpAdGroup(JSONObject jsonItem) throws Exception  {
		PfpAdGroup pfpAdGroup = new PfpAdGroup();
		adGroupSeq = sequenceService.getId(EnumSequenceTableName.PFP_AD_GROUP, "_");
		System.out.println("adActionSeq = " + pfpAdAction.getAdActionSeq());
		if(pfpAdAction != null) {
			pfpAdGroup.setAdGroupSeq(adGroupSeq);
			pfpAdGroup.setPfpAdAction(pfpAdAction);
			pfpAdGroup.setAdGroupCreateTime(new Date());

			pfpAdGroup.setAdGroupName(jsonItem.getString("title"));
			pfpAdGroup.setAdGroupSearchPriceType(1);
			pfpAdGroup.setAdGroupSearchPrice(3f);
			pfpAdGroup.setAdGroupChannelPrice(3f);
			pfpAdGroup.setAdGroupStatus(EnumStatus.UnDone.getStatusId());	// 新增廣告分類時，status 設定為未完成
			pfpAdGroup.setAdGroupUpdateTime(new Date());
			
			pfpAdGroupService.savePfpAdGroup(pfpAdGroup);
			pfpAdActionService.updatePfpAdActionStatus(Integer.toString(EnumStatus.Open.getStatusId()), pfpAdAction.getAdActionSeq());
			
			//系統價更新 2018-01-12 停止更新價格出價以JOB為主
//			syspriceOperaterAPI.addAdSysprice(sysPriceAdPoolSeq, 3f);
		}
	}
	
	public void setPfpCustomerInfoService(PfpCustomerInfoService pfpCustomerInfoService) {
		this.pfpCustomerInfoService = pfpCustomerInfoService;
	}

	public void setSequenceService(ISequenceService sequenceService) {
		this.sequenceService = sequenceService;
	}

	public void setPfpAdActionService(PfpAdActionService pfpAdActionService) {
		this.pfpAdActionService = pfpAdActionService;
	}

	public void setPfpAdGroupService(PfpAdGroupService pfpAdGroupService) {
		this.pfpAdGroupService = pfpAdGroupService;
	}

	public void setSyspriceOperaterAPI(SyspriceOperaterAPI syspriceOperaterAPI) {
		this.syspriceOperaterAPI = syspriceOperaterAPI;
	}

	public void setPfpAdService(PfpAdService pfpAdService) {
		this.pfpAdService = pfpAdService;
	}

	public void setPfpAdDetailService(PfpAdDetailService pfpAdDetailService) {
		this.pfpAdDetailService = pfpAdDetailService;
	}

	public void setPfpAdKeywordService(PfpAdKeywordService pfpAdKeywordService) {
		this.pfpAdKeywordService = pfpAdKeywordService;
	}

	public void setPfpAdExcludeKeywordService(
			PfpAdExcludeKeywordService pfpAdExcludeKeywordService) {
		this.pfpAdExcludeKeywordService = pfpAdExcludeKeywordService;
	}

	public void setDefineAdService(DefineAdService defineAdService) {
		this.defineAdService = defineAdService;
	}

	public void setSysPriceAdPoolSeq(String sysPriceAdPoolSeq) {
		this.sysPriceAdPoolSeq = sysPriceAdPoolSeq;
	}

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public InputStream getMsg() {
		return msg;
	}

	public void setMsg(InputStream msg) {
		this.msg = msg;
	}

}
