package com.pchome.akbpfp.struts2.ajax.codeManage.convertTracking;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.pchome.akbpfp.db.pojo.PfpCode;
import com.pchome.akbpfp.db.pojo.PfpCodeConvert;
import com.pchome.akbpfp.db.pojo.PfpCodeConvertRule;
import com.pchome.akbpfp.db.service.codeManage.IPfpCodeConvertRuleService;
import com.pchome.akbpfp.db.service.codeManage.IPfpCodeConvertService;
import com.pchome.akbpfp.db.service.codeManage.IPfpCodeService;
import com.pchome.akbpfp.db.service.sequence.ISequenceService;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.codeManage.EnumConvertBelongType;
import com.pchome.enumerate.codeManage.EnumConvertNumType;
import com.pchome.enumerate.codeManage.EnumConvertStatusType;
import com.pchome.enumerate.codeManage.EnumConvertType;
import com.pchome.enumerate.codeManage.EnumRetargetingCodeType;
import com.pchome.enumerate.sequence.EnumSequenceTableName;
import com.pchome.service.portalcms.bean.Mail;
import com.pchome.soft.util.SpringEmailUtil;

public class ConvertTrackingAjax extends BaseCookieAction {

	private static final long serialVersionUID = 1L;

	private String convertSeq;
	private String convertName;
	private String paId;
	private String convertType;
	private int clickRangeDate;
	private int impRangeDate;
	private String convertClass;
	private String convertPriceType;
	private String convertPrice;
	private String convertRuleNum;
	private String[] convertConditionMap;
	
	// private String[] filterContentMap;
	private String mailFrom;  
	private String mailReceivers;
	private String result;
	private Map<String, Object> resultMap;

	private IPfpCodeService pfpCodeService;
	private IPfpCodeConvertService pfpCodeConvertService;
	private IPfpCodeConvertRuleService pfpCodeConvertRuleService;
	
	private ISequenceService sequenceService;
//	private IPfpCustomerInfoService pfpCustomerInfoService;
	private SpringEmailUtil springEmailUtil;

	private int currentPage = 1; // 第幾頁(初始預設第1頁)
	private int pageSizeSelected = 10; // 每頁筆數(初始預設每頁10筆)
	
	private List<Object> retargetingList;
	private int pageCount = 0; //總頁數
	private int totalCount = 0; //資料總筆數
	
	private String retargetingIdArray;
	
//	/**
//	 * 取得再行銷清單Ajax
//	 */
//	public String queryRetargetingListAjax(){
//		try{
//			log.info(">>> currentPage: " + currentPage);
//			log.info(">>> pageSizeSelected: " + pageSizeSelected);
//			log.info(">>> trackingName: " + trackingName);
//			
//			resultMap = new HashMap<String, Object>();
//	
//			RetargetingTrackingVO retargetingTrackingVO = new RetargetingTrackingVO();
//			retargetingTrackingVO.setPage(currentPage);
//			retargetingTrackingVO.setPageSize(pageSizeSelected);
//			retargetingTrackingVO.setRetargetingName(trackingName);
//			retargetingTrackingVO.setPfpCustomerInfoId(super.getCustomer_info_id());
//			
//			retargetingList = pfpCodeTrackingService.getRetargetingTrackingList(retargetingTrackingVO);
//			
//			log.info(">>> retargetingList size: " + retargetingList.size());
//			if(retargetingList.size() <= 0){
//				resultMap = returnErrorMsgMap("沒有再行銷資料");
//				return SUCCESS;
//			}
//			
//			//商品清單資料總筆數
//			totalCount =  Integer.valueOf(pfpCodeTrackingService.getRetargetingTrackingCount(retargetingTrackingVO));
//			//總頁數
//			pageCount = (int)Math.ceil((float)totalCount / pageSizeSelected);
//			
//			resultMap.put("currentPage", currentPage);
//			resultMap.put("pageSizeSelected", pageSizeSelected);
//			resultMap.put("trackingName", trackingName);
//			resultMap.put("totalCount", totalCount);
//			resultMap.put("pageCount", pageCount);
//			resultMap.put("retargetingList", retargetingList);
//			resultMap.put("status", "SUCCESS");
//			
//		} catch (Exception e) {
//			log.error("error:" + e);
//			resultMap = returnErrorMsgMap("系統忙碌中，請稍後再試，如仍有問題請洽相關人員。");
//			return SUCCESS;
//		}
//		
//		return SUCCESS;
//	}
//	
//	
//	/**
//	 * 回傳錯誤訊息
//	 */
//	public Map<String,Object> returnErrorMsgMap(String errorMsg) {
//		
//		Map<String,Object> errorMsgMap = new HashMap<String, Object>();
//		errorMsgMap.put("currentPage", 1);
//		errorMsgMap.put("pageCount", 1);
//		errorMsgMap.put("totalCount", 0);
//		errorMsgMap.put("status", "ERROR");
//		errorMsgMap.put("msg", errorMsg);
//		
//		return errorMsgMap;
//	}
	
	
	
	/**
	 * 新增轉換追蹤
	 */
	public String addConvertTrackingAjax() {
		log.info(">>> convertSeq: " + convertSeq);
		log.info(">>> convertName: " + convertName);
		log.info(">>> paId: " + paId);
		log.info(">>> convertType: " + convertType);
		log.info(">>> clickRangeDate: " + clickRangeDate);
		log.info(">>> impRangeDate: " + impRangeDate);
		log.info(">>> convertClass: " + convertClass);
		log.info(">>> convertPriceType: " + convertPriceType);
		log.info(">>> convertPrice: " + convertPrice);
		log.info(">>> convertRuleNum: " + convertRuleNum);
		log.info(">>> pfpCustomerInfoId: " + super.getCustomer_info_id());
		log.info(">>> convertConditionMap: " + convertConditionMap);
		
		try {
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
			
			
			//新增轉換追蹤
			PfpCodeConvert pfpCodeConvert = new PfpCodeConvert();
			pfpCodeConvert.setConvertSeq(convertSeq);
			pfpCodeConvert.setConvertName(convertName);
			pfpCodeConvert.setPfpCode(pfpCode);
			pfpCodeConvert.setPfpCustomerInfoId(super.getCustomer_info_id());
			pfpCodeConvert.setConvertType(convertType);
			pfpCodeConvert.setClickRangeDate(clickRangeDate);
			pfpCodeConvert.setImpRangeDate(impRangeDate);
			pfpCodeConvert.setConvertClass(convertClass);
			pfpCodeConvert.setConvertPriceType(convertPriceType);
			pfpCodeConvert.setConvertPrice(Integer.parseInt(convertPrice));
			pfpCodeConvert.setConvertStatus(EnumConvertStatusType.Open.getType());
			pfpCodeConvert.setConvertBelong(EnumConvertBelongType.FinalClick.getType());
			pfpCodeConvert.setConvertNumType(EnumConvertNumType.EveryTime.getType());
			pfpCodeConvert.setConvertRuleNum(Integer.parseInt(convertRuleNum));
			pfpCodeConvert.setUpdateDate(new Date());
			pfpCodeConvert.setCreateDate(new Date());
			pfpCodeConvertService.saveOrUpdateWithCommit(pfpCodeConvert);
			
			
			//新增自訂轉換追蹤條件
			if (StringUtils.equals(convertType, EnumConvertType.CustomConvert.getType())){
				pfpCodeConvert = pfpCodeConvertService.get(convertSeq);
				JSONArray convertConditionArray = new JSONArray(convertConditionMap[0].toString());
				// 寫入轉換追蹤條件
				for (int i = 0; i < convertConditionArray.length(); i++) {
					JSONObject convertConditionObj = new JSONObject(convertConditionArray.get(i).toString());
					String newCondition = convertConditionObj.getString("condition");
					String newValue = convertConditionObj.getString("value");
					
					//取轉換追蹤條件流水號
					String convertRuleSeq = sequenceService.getSerialNumberByLength20(EnumSequenceTableName.PFP_CODE_CONVERT_RULE);
					
					PfpCodeConvertRule pfpCodeConvertRule = new PfpCodeConvertRule();
					pfpCodeConvertRule.setConvertRuleId(convertRuleSeq);
					pfpCodeConvertRule.setPfpCodeConvert(pfpCodeConvert);
					pfpCodeConvertRule.setConvertRuleWay(newCondition);
					pfpCodeConvertRule.setConvertRuleValue(newValue);
					pfpCodeConvertRule.setUpdateDate(new Date());
					pfpCodeConvertRule.setCreateDate(new Date());
					pfpCodeConvertRuleService.saveOrUpdateWithCommit(pfpCodeConvertRule);
					
				}
			}
			
			resultMap.put("msg", "轉換追蹤新增成功");
			resultMap.put("status", "SUCCESS");

		} catch (Exception e) {
			log.error("error:" + e);
			resultMap.put("msg", "系統忙碌中，請稍後再試，如仍有問題請洽相關人員。");
			resultMap.put("status", "ERROR");
			
			try {
				pfpCodeConvertRuleService.deletePfpCodeConvertRule(convertSeq);
			} catch (Exception e1) {
				log.error("delete pfpCodeConvertRule error>>> convertSeq : " + convertSeq);
			}
			
			PfpCodeConvert pfpCodeConvert = new PfpCodeConvert();
			pfpCodeConvert.setConvertSeq(convertSeq);
			try {
				pfpCodeConvertService.deletePfpCodeConvert(pfpCodeConvert);
			} catch (Exception e1) {
				log.error("delete pfpCodeConvert error>>> convertSeq : " + convertSeq);
			}
		
			
			return SUCCESS;
		}

		return SUCCESS;
	}
	
	
	/**
	 * 編輯儲存轉換追蹤
	 */
	public String editConvertTrackingAjax() {
		log.info(">>> convertSeq: " + convertSeq);
		log.info(">>> convertName: " + convertName);
		log.info(">>> paId: " + paId);
		log.info(">>> convertType: " + convertType);
		log.info(">>> clickRangeDate: " + clickRangeDate);
		log.info(">>> impRangeDate: " + impRangeDate);
		log.info(">>> convertClass: " + convertClass);
		log.info(">>> convertPriceType: " + convertPriceType);
		log.info(">>> convertPrice: " + convertPrice);
		log.info(">>> convertRuleNum: " + convertRuleNum);
		log.info(">>> pfpCustomerInfoId: " + super.getCustomer_info_id());
		log.info(">>> convertConditionMap: " + convertConditionMap);
		
		try {
			resultMap = new HashMap<String, Object>();
			
			//更新轉換追蹤資料
			PfpCodeConvert pfpCodeConvert = pfpCodeConvertService.get(convertSeq);
			pfpCodeConvert.setConvertName(convertName);
			pfpCodeConvert.setConvertType(convertType);
			pfpCodeConvert.setClickRangeDate(clickRangeDate);
			pfpCodeConvert.setImpRangeDate(impRangeDate);
			pfpCodeConvert.setConvertClass(convertClass);
			pfpCodeConvert.setConvertPriceType(convertPriceType);
			pfpCodeConvert.setConvertPrice(Integer.parseInt(convertPrice));
			pfpCodeConvert.setConvertStatus(EnumConvertStatusType.Open.getType());
			pfpCodeConvert.setConvertBelong(EnumConvertBelongType.FinalClick.getType());
			pfpCodeConvert.setConvertNumType(EnumConvertNumType.EveryTime.getType());
			pfpCodeConvert.setConvertRuleNum(Integer.parseInt(convertRuleNum));
			pfpCodeConvert.setUpdateDate(new Date());
			pfpCodeConvertService.saveOrUpdateWithCommit(pfpCodeConvert);
			
			//刪除轉換追蹤條件
			pfpCodeConvertRuleService.deletePfpCodeConvertRule(convertSeq);
			
			//新增自訂轉換追蹤條件
			if (StringUtils.equals(convertType, EnumConvertType.CustomConvert.getType())){
				pfpCodeConvert = pfpCodeConvertService.get(convertSeq);
				JSONArray convertConditionArray = new JSONArray(convertConditionMap[0].toString());
				// 寫入轉換追蹤條件
				for (int i = 0; i < convertConditionArray.length(); i++) {
					JSONObject convertConditionObj = new JSONObject(convertConditionArray.get(i).toString());
					String newCondition = convertConditionObj.getString("condition");
					String newValue = convertConditionObj.getString("value");
					
					//取轉換追蹤條件流水號
					String convertRuleSeq = sequenceService.getSerialNumberByLength20(EnumSequenceTableName.PFP_CODE_CONVERT_RULE);
					
					PfpCodeConvertRule pfpCodeConvertRule = new PfpCodeConvertRule();
					pfpCodeConvertRule.setConvertRuleId(convertRuleSeq);
					pfpCodeConvertRule.setPfpCodeConvert(pfpCodeConvert);
					pfpCodeConvertRule.setConvertRuleWay(newCondition);
					pfpCodeConvertRule.setConvertRuleValue(newValue);
					pfpCodeConvertRule.setUpdateDate(new Date());
					pfpCodeConvertRule.setCreateDate(new Date());
					pfpCodeConvertRuleService.saveOrUpdateWithCommit(pfpCodeConvertRule);
					
				}
			}
			
			resultMap.put("msg", "轉換追蹤儲存成功");
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
	 * 轉換追蹤sendmail
	 */
	public String sendConvertTrackingMailAjax() {
		try {
			log.info(">>> mailReceivers: " + mailReceivers);
			log.info(">>> paId: " + paId);
			log.info(">>> convertSeq: " + convertSeq);
			
			
			resultMap = new HashMap<String, Object>();
			String[] mailReceiversAry= mailReceivers.split(";");

			//send mail
			Mail mail = new Mail();
			String subject = "［PChome聯播網］轉換追蹤代碼";
			mail.setMailTo(mailReceiversAry);
			String mailContent=
			"<html><body>"+
			"請複製下方轉換追蹤代碼，並貼到欲追蹤網頁中，以利完成相關設定。<br/>"+
			"&#60;script  id='pcadscript' language='javascript' async src='https://kdpic.pchome.com.tw/js/ptag.js'&#62;&#60;/script&#62;<br/>"+
			"&#60;script&#62;<br/>";					
				mailContent = mailContent+
						"  window.dataLayer = window.dataLayer || [];<br/>"+
						"  function ptag(){dataLayer.push(arguments);}<br/>"+
						"  ptag({'paid':"+paId+"});<br/>"+
						"  ptag('event','convert',{<br/>"+
						"  'convert_id':"+convertSeq+",<br/>"+
						"  'convert_price':'',<br/>"+
						"  'op1':'',<br/>"+
						"  'op2':''});<br/>";
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
//
//	
//	
//	/**
//	 * 刪除再行銷目錄
//	 */
//	public String deleteRetargetingAjax(){
//		try{
//			log.info(">>> currentPage: " + currentPage);
//			log.info(">>> pageSizeSelected: " + pageSizeSelected);
//			log.info(">>> retargetingIdArray: " + retargetingIdArray.toString());
//			log.info(">>> trackingName: " + trackingName);
//			
//			
//			resultMap = new HashMap<String, Object>();
//	
//			JSONObject retargetingIdJson = new JSONObject(retargetingIdArray);
//			Iterator<String> keys = retargetingIdJson.keys();
//			List<String> retargetingIdList = new ArrayList<String>();
//			while(keys.hasNext()) {
//			    String key = keys.next();
//			    System.out.println(key);
//			    System.out.println(retargetingIdJson.get(key));
//			    String prodId = ((JSONObject)retargetingIdJson.get(key)).getString("retargetingId");
//			    retargetingIdList.add(prodId);
//			}
//			pfpCodeTrackingService.updateTrackingStatus(super.getCustomer_info_id(),retargetingIdList,EnumTrackingStatusType.Delete.getType());
//			
//			resultMap.put("currentPage", 1);
//			resultMap.put("pageSizeSelected", pageSizeSelected);
//			resultMap.put("trackingName", trackingName);
//			resultMap.put("msg", "再行銷目錄刪除成功");
//			resultMap.put("status", "SUCCESS");
//			
//		} catch (Exception e) {
//			log.error("error:" + e);
//			resultMap = returnErrorMsgMap("系統忙碌中，請稍後再試，如仍有問題請洽相關人員。");
//			return SUCCESS;
//		}
//		
//		return SUCCESS;
//	}
	
	
	
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

	
	
	
	



	public String getPaId() {
		return paId;
	}

	public void setPaId(String paId) {
		this.paId = paId;
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


	public String getConvertSeq() {
		return convertSeq;
	}


	public void setConvertSeq(String convertSeq) {
		this.convertSeq = convertSeq;
	}


	public String getConvertName() {
		return convertName;
	}


	public void setConvertName(String convertName) {
		this.convertName = convertName;
	}


	public String getConvertType() {
		return convertType;
	}


	public void setConvertType(String convertType) {
		this.convertType = convertType;
	}


	public int getClickRangeDate() {
		return clickRangeDate;
	}


	public void setClickRangeDate(int clickRangeDate) {
		this.clickRangeDate = clickRangeDate;
	}


	public int getImpRangeDate() {
		return impRangeDate;
	}


	public void setImpRangeDate(int impRangeDate) {
		this.impRangeDate = impRangeDate;
	}


	public String getConvertClass() {
		return convertClass;
	}


	public void setConvertClass(String convertClass) {
		this.convertClass = convertClass;
	}


	public String getConvertPriceType() {
		return convertPriceType;
	}


	public void setConvertPriceType(String convertPriceType) {
		this.convertPriceType = convertPriceType;
	}


	public String getConvertPrice() {
		return convertPrice;
	}


	public void setConvertPrice(String convertPrice) {
		this.convertPrice = convertPrice;
	}


	public String getConvertRuleNum() {
		return convertRuleNum;
	}


	public void setConvertRuleNum(String convertRuleNum) {
		this.convertRuleNum = convertRuleNum;
	}


	public IPfpCodeConvertService getPfpCodeConvertService() {
		return pfpCodeConvertService;
	}


	public void setPfpCodeConvertService(IPfpCodeConvertService pfpCodeConvertService) {
		this.pfpCodeConvertService = pfpCodeConvertService;
	}


	public String[] getConvertConditionMap() {
		return convertConditionMap;
	}


	public void setConvertConditionMap(String[] convertConditionMap) {
		this.convertConditionMap = convertConditionMap;
	}


	public ISequenceService getSequenceService() {
		return sequenceService;
	}


	public void setSequenceService(ISequenceService sequenceService) {
		this.sequenceService = sequenceService;
	}

	public IPfpCodeConvertRuleService getPfpCodeConvertRuleService() {
		return pfpCodeConvertRuleService;
	}
	public void setPfpCodeConvertRuleService(IPfpCodeConvertRuleService pfpCodeConvertRuleService) {
		this.pfpCodeConvertRuleService = pfpCodeConvertRuleService;
	}
	
	
	
}