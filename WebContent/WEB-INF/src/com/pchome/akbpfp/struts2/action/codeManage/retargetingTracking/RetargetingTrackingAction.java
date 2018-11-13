package com.pchome.akbpfp.struts2.action.codeManage.retargetingTracking;

import com.pchome.akbpfp.db.pojo.PfpCode;
import com.pchome.akbpfp.db.service.codeManage.code.IPfpCodeService;
import com.pchome.akbpfp.db.service.customerInfo.IPfpCustomerInfoService;
import com.pchome.akbpfp.db.service.sequence.ISequenceService;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.sequence.EnumSequenceTableName;
import com.pchome.soft.depot.utils.HttpUtil;

public class RetargetingTrackingAction  extends BaseCookieAction{

	private static final long serialVersionUID = 1L;
	
	private IPfpCustomerInfoService pfpCustomerInfoService;
	private IPfpCodeService pfpCodeService;
	private ISequenceService sequenceService;
	
	private String paId;
	private String trackingSeq;
	private String returnMsg;

	public String queryRetargetingTrackingView(){		
		try{
			
		} catch (Exception e) {
			log.error("error:" + e);
			returnMsg = "系統忙碌中，請稍後再試，如仍有問題請洽相關人員。";
			return ERROR;
		}
		
		return SUCCESS;
	}

	
	public String addRetargetingTrackingView(){		
		try{
			log.info(">>> pfpCustomerInfoId: " + super.getCustomer_info_id());
			
			PfpCode pfpCode = pfpCodeService.getPfpCode(super.getCustomer_info_id());
			log.info(" getpfpCodeDb >>> OK");
			
			//如尚未建立過paid者，打api建立
			if (pfpCode == null){
				log.info("addRetargetingTrackingView null");
//				System.out.println("pfp ID : "+super.getCustomer_info_id());
				String memberId = pfpCustomerInfoService.findCustomerInfo(super.getCustomer_info_id()).getMemberId();
//				System.out.println("memberID : "+memberId);
				log.info(" getPaIdCode >>> 1113");
				StringBuffer paIdSB= HttpUtil.getInstance().doGet("http://showstg.pchome.com.tw/paadm/api/getPaCode?memberId=" + memberId);
				log.info(" getPaIdCode >>> 222");
				if (paIdSB.toString().indexOf("status:200") == -1) { 
					log.error(" getPaIdCode Error >>> pfpCustomerInfoId : "+super.getCustomer_info_id());
					returnMsg = "系統忙碌中，請稍後再試，如仍有問題請洽相關人員。";
					return ERROR;
				}
				String[] paIdSBAry = paIdSB.toString().split(",");
				paId = paIdSBAry[0];
			}else{
				log.info("addRetargetingTrackingView not null");
				paId = pfpCode.getPaId();
			}
			
			
			//取流水號
			trackingSeq = sequenceService.getSerialNumberByLength20(EnumSequenceTableName.PFP_CODE_TRACKING);
			System.out.println("trackingSeq");
			
			System.out.println(trackingSeq);
			
			
			
			
			System.out.println("str----");
			System.out.println(paId.toString());
			
			
			
			
			
//			JSONObject apiJsonObject = new JSONObject(adCrawlerResult.toString());
//			if (adCrawlerResult.toString().indexOf("status:200") == -1) { // 檢查連線是否正常
//				log.error("getAdCrawlerAPIData error:status != 200");
//				vo.setMessage("系統忙碌中，請稍後再試。");
//			} else if (apiJsonObject.length() == 0) { // 檢查輸入網址是否正確
//				log.error("getAdCrawlerAPIData error:URL error " + vo.getSearchURL());
//				vo.setMessage("查無資料，請確認輸入網址是否正確。");
//			} else {
//				log.info("爬蟲已完成。");
//				// 正確，將資料寫入vo
//				vo.setApiJsonArray(apiJsonObject.getJSONArray("products"));
//			}
			
		} catch (Exception e) {
			log.error("error:" + e);
			returnMsg = "系統忙碌中，請稍後再試，如仍有問題請洽相關人員。";
			return ERROR;
		}
		
		return SUCCESS;
	}


	
	
	public IPfpCustomerInfoService getPfpCustomerInfoService() {
		return pfpCustomerInfoService;
	}
	public void setPfpCustomerInfoService(IPfpCustomerInfoService pfpCustomerInfoService) {
		this.pfpCustomerInfoService = pfpCustomerInfoService;
	}
	public IPfpCodeService getPfpCodeService() {
		return pfpCodeService;
	}
	public void setPfpCodeService(IPfpCodeService pfpCodeService) {
		this.pfpCodeService = pfpCodeService;
	}
	public ISequenceService getSequenceService() {
		return sequenceService;
	}
	public void setSequenceService(ISequenceService sequenceService) {
		this.sequenceService = sequenceService;
	}
	public String getTrackingSeq() {
		return trackingSeq;
	}
	public void setTrackingSeq(String trackingSeq) {
		this.trackingSeq = trackingSeq;
	}
	public String getPaId() {
		return paId;
	}
	public void setPaId(String paId) {
		this.paId = paId;
	}
	
	
	
}
