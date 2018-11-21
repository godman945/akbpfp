package com.pchome.akbpfp.struts2.ajax.codeManage.remarketingTracking;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

import com.pchome.akbpfp.db.pojo.PfpCode;
import com.pchome.akbpfp.db.pojo.PfpCodeTracking;
import com.pchome.akbpfp.db.service.codeManage.IPfpCodeService;
import com.pchome.akbpfp.db.service.codeManage.IPfpCodeTrackingService;
import com.pchome.akbpfp.db.vo.codeManage.RetargetingTrackingVO;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.codeManage.EnumRetargetingCodeType;
import com.pchome.enumerate.codeManage.EnumTrackingStatusType;
import com.pchome.service.portalcms.bean.Mail;
import com.pchome.soft.util.SpringEmailUtil;

public class RetargetingTrackingAjax extends BaseCookieAction {

	private static final long serialVersionUID = 1L;

	private String trackingSeq;
	private String paId;
	private String codeType;
	private int trackingRangeDate;
	
	// private String[] filterContentMap;
	private String mailFrom;  
	private String mailReceivers;
	private String result;
	private Map<String, Object> resultMap;

	private IPfpCodeService pfpCodeService;
	private IPfpCodeTrackingService pfpCodeTrackingService;
//	private IPfpCustomerInfoService pfpCustomerInfoService;
	private SpringEmailUtil springEmailUtil;

	private int currentPage = 1; // 第幾頁(初始預設第1頁)
	private int pageSizeSelected = 10; // 每頁筆數(初始預設每頁10筆)
	private String trackingName;
	private List<Object> retargetingList;
	private int pageCount = 0; //總頁數
	private int totalCount = 0; //資料總筆數
	
	private String retargetingIdArray;
	
	/**
	 * 取得再行銷清單Ajax
	 */
	public String queryRetargetingListAjax(){
		try{
			log.info(">>> currentPage: " + currentPage);
			log.info(">>> pageSizeSelected: " + pageSizeSelected);
			log.info(">>> trackingName: " + trackingName);
			
			resultMap = new HashMap<String, Object>();
	
			RetargetingTrackingVO retargetingTrackingVO = new RetargetingTrackingVO();
			retargetingTrackingVO.setPage(currentPage);
			retargetingTrackingVO.setPageSize(pageSizeSelected);
			retargetingTrackingVO.setRetargetingName(trackingName);
			retargetingTrackingVO.setPfpCustomerInfoId(super.getCustomer_info_id());
			
			retargetingList = pfpCodeTrackingService.getRetargetingTrackingList(retargetingTrackingVO);
			
			log.info(">>> retargetingList size: " + retargetingList.size());
			if(retargetingList.size() <= 0){
				resultMap = returnErrorMsgMap("沒有再行銷資料");
				return SUCCESS;
			}
			
			//商品清單資料總筆數
			totalCount =  Integer.valueOf(pfpCodeTrackingService.getRetargetingTrackingCount(retargetingTrackingVO));
			//總頁數
			pageCount = (int)Math.ceil((float)totalCount / pageSizeSelected);
			
			resultMap.put("currentPage", currentPage);
			resultMap.put("pageSizeSelected", pageSizeSelected);
			resultMap.put("trackingName", trackingName);
			resultMap.put("totalCount", totalCount);
			resultMap.put("pageCount", pageCount);
			resultMap.put("retargetingList", retargetingList);
			resultMap.put("status", "SUCCESS");
			
		} catch (Exception e) {
			log.error("error:" + e);
			resultMap = returnErrorMsgMap("系統忙碌中，請稍後再試，如仍有問題請洽相關人員。");
			return SUCCESS;
		}
		
		return SUCCESS;
	}
	
	
	/**
	 * 回傳錯誤訊息
	 */
	public Map<String,Object> returnErrorMsgMap(String errorMsg) {
		
		Map<String,Object> errorMsgMap = new HashMap<String, Object>();
		errorMsgMap.put("currentPage", 1);
		errorMsgMap.put("pageCount", 1);
		errorMsgMap.put("totalCount", 0);
		errorMsgMap.put("status", "ERROR");
		errorMsgMap.put("msg", errorMsg);
		
		return errorMsgMap;
	}
	
	/**
	 * 新增再行銷追蹤
	 */
	public String addRetargetingTrackingAjax() {
		try {
			
			log.info(">>> trackingSeq: " + trackingSeq);
			log.info(">>> trackingName: " + trackingName);
			log.info(">>> paId: " + paId);
			log.info(">>> codeType: " + codeType);
			log.info(">>> trackingRangeDate: " + trackingRangeDate);
			log.info(">>> pfpCustomerInfoId: " + super.getCustomer_info_id());

			resultMap = new HashMap<String, Object>();
			
			PfpCode pfpCode = pfpCodeService.getPfpCode(super.getCustomer_info_id());
			if (pfpCode == null){
//				PfpCustomerInfo pfpCustomerInfo = pfpCustomerInfoService.findCustomerInfo(super.getCustomer_info_id());
				//新增 PfpCode
				PfpCode addPfpCode = new PfpCode();
				addPfpCode.setPaId(paId);
				addPfpCode.setPfpCustomerInfoId(super.getCustomer_info_id());
				addPfpCode.setUpdateDate(new Date());
				addPfpCode.setCreateDate(new Date());
				pfpCodeService.saveOrUpdateWithCommit(addPfpCode);
				
				pfpCode = pfpCodeService.getPfpCode(super.getCustomer_info_id());
			}
			
			
			//新增pfpCodeTrackingService
			PfpCodeTracking pfpCodeTracking = new PfpCodeTracking();
			pfpCodeTracking.setTrackingSeq(trackingSeq);
			pfpCodeTracking.setTrackingName(trackingName);
			pfpCodeTracking.setPfpCode(pfpCode);
			pfpCodeTracking.setPfpCustomerInfoId(super.getCustomer_info_id());
			pfpCodeTracking.setTrackingStatus(EnumTrackingStatusType.Open.getType());
			pfpCodeTracking.setCodeType(codeType);
			pfpCodeTracking.setTrackingRangeDate(trackingRangeDate);
			pfpCodeTracking.setUpdateDate(new Date());
			pfpCodeTracking.setCreateDate(new Date());
			pfpCodeTrackingService.save(pfpCodeTracking);
			
			resultMap.put("msg", "再行銷追蹤新增成功");
			resultMap.put("status", "SUCCESS");

		} catch (Exception e) {
			log.error("error:" + e);
			resultMap.put("msg", "系統忙碌中，請稍後再試，如仍有問題請洽相關人員。");
			resultMap.put("status", "ERROR");
			return SUCCESS;
		}

		return SUCCESS;
	}
	
	
	
	
	/**
	 * 再行銷sendmail
	 */
	public String sendRetargetingTrackingMailAjax() {
		try {
			log.info(">>> mailReceivers: " + mailReceivers);
			log.info(">>> paId: " + paId);
			log.info(">>> trackingSeq: " + trackingSeq);
			log.info(">>> codeType: " + codeType);
			
			
			resultMap = new HashMap<String, Object>();
			
			String[] mailReceiversAry= mailReceivers.split(";");

			// 出錯時寄信
//			Mail mail = null;
//			mail = PortalcmsUtil.getInstance().getMail("P098");
//			if (mail == null) {
//				throw new Exception("Mail Object is null.");
//			}
			Mail mail = new Mail();
			String subject = "［PChome聯播網］再行銷代碼 ";
//			mail.setMailFrom("adcl@msx.pchome.com.tw");
			mail.setMailTo(mailReceiversAry);
			
			String mailContent=
			"<html><body>"+
			"請複製下方再行銷代碼，並貼到欲追蹤網頁中，以利完成相關設定。<br/>"+
			"&#60;script  id='pcadscript' language='javascript' async src='https://kdpic.pchome.com.tw/js/ptag.js'&#62;&#60;/script&#62;<br/>"+
			"&#60;script&#62;<br/>";					
			if (StringUtils.equals(codeType, EnumRetargetingCodeType.GeneralWebTracking.getType())){
				mailContent = mailContent+
						"  window.dataLayer = window.dataLayer || [];<br/>"+
						"  function ptag(){dataLayer.push(arguments);}<br/>"+
						"  ptag({'paid':"+paId+"});<br/>"+
						"  ptag('event','tracking',{<br/>"+
						"  'tracking_id':"+trackingSeq+"<br/>"+
						"  '});<br/>";
			}else if (StringUtils.equals(codeType, EnumRetargetingCodeType.DynamicProductAdTracking.getType())){
				mailContent = mailContent+
						"  window.dataLayer = window.dataLayer || [];<br/>"+
						"  function ptag(){dataLayer.push(arguments);}<br/>"+
						"  ptag({'paid':"+paId+"});<br/>"+
						"  ptag('event','tracking',{<br/>"+
						"  'tracking_id':"+trackingSeq+",<br/>"+
						"  'prod_id':'',<br/>"+
						"  'prod_price':'',<br/>"+
						"  'prod_dis':'',<br/>"+
						"  'ec_stock_status':'',<br/>"+
						"  'op1':'',<br/>"+
						"  'op2':''<br/>"+
						"  '});<br/>";
			}
			mailContent=mailContent+"&#60;/script&#62;<br/>"+
									"若有相關問題，請聯絡客服中心。<br/>PChome聯播網小組 敬上</body></html>";
			
			mail.setMsg(mailContent);
			springEmailUtil.sendHtmlEmail(subject, mailFrom, mail.getMailTo(), mail.getMailBcc(), mail.getMsg());

			resultMap.put("msg", "mail發送成功");
			resultMap.put("status", "SUCCESS");

		} catch (Exception e) {
			log.error("error:" + e);
			resultMap.put("msg", "mail發送失敗，系統忙碌中，請稍後再試，如仍有問題請洽相關人員。");
			resultMap.put("status", "ERROR");
			return SUCCESS;
		}

		return SUCCESS;
	}

	
	
	/**
	 * 刪除再行銷目錄
	 */
	public String deleteRetargetingAjax(){
		try{
			log.info(">>> currentPage: " + currentPage);
			log.info(">>> pageSizeSelected: " + pageSizeSelected);
			log.info(">>> retargetingIdArray: " + retargetingIdArray.toString());
			log.info(">>> trackingName: " + trackingName);
			
			
			resultMap = new HashMap<String, Object>();
	
			JSONObject retargetingIdJson = new JSONObject(retargetingIdArray);
			Iterator<String> keys = retargetingIdJson.keys();
			List<String> retargetingIdList = new ArrayList<String>();
			while(keys.hasNext()) {
			    String key = keys.next();
			    System.out.println(key);
			    System.out.println(retargetingIdJson.get(key));
			    String prodId = ((JSONObject)retargetingIdJson.get(key)).getString("retargetingId");
			    retargetingIdList.add(prodId);
			}
			pfpCodeTrackingService.updateTrackingStatus(super.getCustomer_info_id(),retargetingIdList,EnumTrackingStatusType.Delete.getType());
			
			resultMap.put("currentPage", 1);
			resultMap.put("pageSizeSelected", pageSizeSelected);
			resultMap.put("trackingName", trackingName);
			resultMap.put("msg", "再行銷目錄刪除成功");
			resultMap.put("status", "SUCCESS");
			
		} catch (Exception e) {
			log.error("error:" + e);
			resultMap = returnErrorMsgMap("系統忙碌中，請稍後再試，如仍有問題請洽相關人員。");
			return SUCCESS;
		}
		
		return SUCCESS;
	}
	
//	/**
//	 * 回傳錯誤訊息
//	 */
//	public Map<String, Object> returnErrorMsgMap(String errorMsg) {
//
//		Map<String, Object> errorMsgMap = new HashMap<String, Object>();
//		errorMsgMap.put("currentPage", 1);
//		errorMsgMap.put("pageCount", 1);
//		errorMsgMap.put("totalCount", 0);
//		errorMsgMap.put("status", "ERROR");
//		errorMsgMap.put("msg", errorMsg);
//
//		return errorMsgMap;
//	}

	
	
	
	


	public String getTrackingSeq() {
		return trackingSeq;
	}

	public void setTrackingSeq(String trackingSeq) {
		this.trackingSeq = trackingSeq;
	}

	public String getTrackingName() {
		return trackingName;
	}

	public void setTrackingName(String trackingName) {
		this.trackingName = trackingName;
	}

	public String getPaId() {
		return paId;
	}

	public void setPaId(String paId) {
		this.paId = paId;
	}

	public String getCodeType() {
		return codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	public int getTrackingRangeDate() {
		return trackingRangeDate;
	}

	public void setTrackingRangeDate(int trackingRangeDate) {
		this.trackingRangeDate = trackingRangeDate;
	}

	public String getMailFrom() {
		return mailFrom;
	}

	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}

	public String getMailReceivers() {
		return mailReceivers;
	}

	public void setMailReceivers(String mailReceivers) {
		this.mailReceivers = mailReceivers;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Map<String, Object> getResultMap() {
		return resultMap;
	}

	public void setResultMap(Map<String, Object> resultMap) {
		this.resultMap = resultMap;
	}

	public SpringEmailUtil getSpringEmailUtil() {
		return springEmailUtil;
	}

	public void setSpringEmailUtil(SpringEmailUtil springEmailUtil) {
		this.springEmailUtil = springEmailUtil;
	}

	public IPfpCodeService getPfpCodeService() {
		return pfpCodeService;
	}

	public void setPfpCodeService(IPfpCodeService pfpCodeService) {
		this.pfpCodeService = pfpCodeService;
	}

	public IPfpCodeTrackingService getPfpCodeTrackingService() {
		return pfpCodeTrackingService;
	}

	public void setPfpCodeTrackingService(IPfpCodeTrackingService pfpCodeTrackingService) {
		this.pfpCodeTrackingService = pfpCodeTrackingService;
	}


	public int getCurrentPage() {
		return currentPage;
	}


	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}


	public int getPageSizeSelected() {
		return pageSizeSelected;
	}


	public void setPageSizeSelected(int pageSizeSelected) {
		this.pageSizeSelected = pageSizeSelected;
	}


	public List<Object> getRetargetingList() {
		return retargetingList;
	}
	public void setRetargetingList(List<Object> retargetingList) {
		this.retargetingList = retargetingList;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public String getRetargetingIdArray() {
		return retargetingIdArray;
	}
	public void setRetargetingIdArray(String retargetingIdArray) {
		this.retargetingIdArray = retargetingIdArray;
	}
	
	
}