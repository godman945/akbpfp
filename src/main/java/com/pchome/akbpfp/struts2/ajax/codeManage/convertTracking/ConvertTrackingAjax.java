package com.pchome.akbpfp.struts2.ajax.codeManage.convertTracking;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.pchome.akbpfp.db.service.accesslog.AdmAccesslogService;
import com.pchome.akbpfp.db.service.codeManage.IPfpCodeConvertRuleService;
import com.pchome.akbpfp.db.service.codeManage.IPfpCodeConvertService;
import com.pchome.akbpfp.db.service.codeManage.IPfpCodeService;
import com.pchome.akbpfp.db.service.sequence.ISequenceService;
import com.pchome.akbpfp.db.vo.codeManage.ConvertTrackingVO;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.codeManage.EnumConvertBelongType;
import com.pchome.enumerate.codeManage.EnumConvertClassType;
import com.pchome.enumerate.codeManage.EnumConvertCodeType;
import com.pchome.enumerate.codeManage.EnumConvertNumType;
import com.pchome.enumerate.codeManage.EnumConvertPriceType;
import com.pchome.enumerate.codeManage.EnumConvertStatusType;
import com.pchome.enumerate.codeManage.EnumConvertType;
import com.pchome.enumerate.sequence.EnumSequenceTableName;
import com.pchome.rmi.accesslog.EnumAccesslogAction;
import com.pchome.service.portalcms.bean.Mail;
import com.pchome.soft.util.SpringEmailUtil;

public class ConvertTrackingAjax extends BaseCookieAction {

	private static final long serialVersionUID = 1L;

	private String convertSeq;
	private String convertName;
	private String convertStatus;
	private String paId;
	private String convertCodeType;
	private String convertType;
	private int clickRangeDate;
	private int impRangeDate;
	private String convertClass;
	private String convertPriceType;
	private String convertPrice;
	private String convertRuleNum;
	private String[] convertConditionMap;
	
	private String mailFrom;  
	private String mailReceivers;
	private String result;
	private Map<String, Object> resultMap;

	private IPfpCodeService pfpCodeService;
	private IPfpCodeConvertService pfpCodeConvertService;
	private IPfpCodeConvertRuleService pfpCodeConvertRuleService;
	private AdmAccesslogService admAccesslogService;
	private ISequenceService sequenceService;
	private SpringEmailUtil springEmailUtil;

	private int currentPage = 1; // 第幾頁(初始預設第1頁)
	private int pageSizeSelected = 10; // 每頁筆數(初始預設每頁10筆)
	
	private List<Object> convertList;
	private int pageCount = 0; //總頁數
	private int totalCount = 0; //資料總筆數
	
	private ConvertTrackingVO  sumConvertCount;
	
	/**
	 * 取得轉換清單Ajax
	 */
	public String queryConvertListAjax(){
		try{
			log.info(">>> currentPage: " + currentPage);
			log.info(">>> pageSizeSelected: " + pageSizeSelected);
			log.info(">>> convertName: " + convertName);
			
			resultMap = new HashMap<String, Object>();
	
			ConvertTrackingVO convertTrackingVO = new ConvertTrackingVO();
			convertTrackingVO.setPage(currentPage);
			convertTrackingVO.setPageSize(pageSizeSelected);
			convertTrackingVO.setConvertName(convertName);
			convertTrackingVO.setPfpCustomerInfoId(super.getCustomer_info_id());
			
			
			//撈單頁轉換資料
			List<PfpCodeConvert> pfpCodeConvertList =pfpCodeConvertService.findPfpCodeConvertList(convertTrackingVO);
			convertList = new ArrayList<>();
			
			for (PfpCodeConvert pfpCodeConvert : pfpCodeConvertList) {
				convertTrackingVO.setConvertSeq(pfpCodeConvert.getConvertSeq());
				//ck
				int clickRangeDate = pfpCodeConvert.getClickRangeDate();
				convertTrackingVO.setCkStartDate(calculateConvertDay(clickRangeDate));
				convertTrackingVO.setCkEndDate(calculateConvertDay(1));
				//pv
				int impRangeDate = pfpCodeConvert.getImpRangeDate();
				convertTrackingVO.setPvStartDate(calculateConvertDay(impRangeDate));
				convertTrackingVO.setPvEndDate(calculateConvertDay(1));
				
				ConvertTrackingVO convertTransVO = pfpCodeConvertService.getConvertTrackingList(convertTrackingVO);
				convertList.add(convertTransVO);
			}
			
			
			log.info(">>> convertList size: " + convertList.size());
			if(convertList.size() <= 0){
				resultMap = returnErrorMsgMap("沒有轉換資料");
				return SUCCESS;
			}
			
			int SumCKConvertCount = 0;
			int SumPVConvertCount = 0;
			//撈全部轉換做所有轉換加總
			List<PfpCodeConvert> pfpCodeConvertListAll =pfpCodeConvertService.findPfpCodeConvertListAll(convertTrackingVO);
			for (PfpCodeConvert pfpCodeConvertAll : pfpCodeConvertListAll) {
				convertTrackingVO.setConvertSeq(pfpCodeConvertAll.getConvertSeq());
				//ck
				int clickRangeDate = pfpCodeConvertAll.getClickRangeDate();
				convertTrackingVO.setCkStartDate(calculateConvertDay(clickRangeDate));
				convertTrackingVO.setCkEndDate(calculateConvertDay(1));
				//pv
				int impRangeDate = pfpCodeConvertAll.getImpRangeDate();
				convertTrackingVO.setPvStartDate(calculateConvertDay(impRangeDate));
				convertTrackingVO.setPvEndDate(calculateConvertDay(1));
				
				ConvertTrackingVO convertTransAllVO = pfpCodeConvertService.getConvertTrackingList(convertTrackingVO);
				SumCKConvertCount = SumCKConvertCount + Integer.parseInt(convertTransAllVO.getTransCKConvertCount());
				SumPVConvertCount = SumPVConvertCount + Integer.parseInt(convertTransAllVO.getTransPVConvertCount());
			}
			sumConvertCount = new ConvertTrackingVO();
			sumConvertCount.setTransSumCKConvertCount(Integer.toString(SumCKConvertCount));	//加總ck點擊後轉換數
			sumConvertCount.setTransSumPVConvertCount(Integer.toString(SumPVConvertCount));	//加總pv瀏覽後轉換數
			sumConvertCount.setTransSumAllConvertCount(Integer.toString(SumCKConvertCount + SumPVConvertCount));//加總所有轉換(ck+pv)
		      
			
			//商品清單資料總筆數
			totalCount =  Integer.valueOf(pfpCodeConvertService.getConvertTrackingCount(convertTrackingVO));
			//總頁數
			pageCount = (int)Math.ceil((float)totalCount / pageSizeSelected);
			
			resultMap.put("currentPage", currentPage);
			resultMap.put("pageSizeSelected", pageSizeSelected);
			resultMap.put("convertName", convertName);
			resultMap.put("totalCount", totalCount);
			resultMap.put("pageCount", pageCount);
			resultMap.put("convertList", convertList);
			resultMap.put("sumConvertCount", sumConvertCount);
			resultMap.put("status", "SUCCESS");
			
		} catch (Exception e) {
			log.error("error:" + e);
			resultMap = returnErrorMsgMap("系統忙碌中，請稍後再試，如仍有問題請洽相關人員。");
			return SUCCESS;
		}
		
		return SUCCESS;
	}
	
	public String calculateConvertDay(int day) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, -day);
		Date date = cal.getTime();
		String dateStr = sdf.format(date);
		
		return dateStr;
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
	 * 新增轉換追蹤
	 */
	public String addConvertTrackingAjax() {
		log.info(">>> convertSeq: " + convertSeq);
		log.info(">>> convertName: " + convertName.trim());
		log.info(">>> paId: " + paId);
		log.info(">>> convertCodeType: " + convertCodeType);
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
				//新增 PfpCode
				PfpCode addPfpCode = new PfpCode();
				addPfpCode.setPaId(paId);
				addPfpCode.setPfpCustomerInfoId(super.getCustomer_info_id());
				addPfpCode.setUpdateDate(new Date());
				addPfpCode.setCreateDate(new Date());
				pfpCodeService.saveOrUpdateWithCommit(addPfpCode);
				
				pfpCode = pfpCodeService.getPfpCode(super.getCustomer_info_id());
			}
			
			
			//如果轉換名稱重覆，不可新增
			List<PfpCodeConvert> pfpCodeConvertList= pfpCodeConvertService.findPfpCodeConvertListByCustomerInfoId(super.getCustomer_info_id());
			for (PfpCodeConvert pfpCodeConvertPojo : pfpCodeConvertList) {
				if(StringUtils.equals(pfpCodeConvertPojo.getConvertName().trim(), convertName.trim())){
					resultMap.put("msg", "轉換名稱不可重覆！");
					resultMap.put("status", "ERROR");
					return SUCCESS;
				}
			}
			
			//新增轉換追蹤
			PfpCodeConvert pfpCodeConvert = new PfpCodeConvert();
			pfpCodeConvert.setConvertSeq(convertSeq);
			pfpCodeConvert.setConvertName(convertName.trim());
			pfpCodeConvert.setPfpCode(pfpCode);
			pfpCodeConvert.setPfpCustomerInfoId(super.getCustomer_info_id());
			pfpCodeConvert.setConvertCodeType(convertCodeType);
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

			//accesslog
			String message = "新增=>轉換追縱：" + convertName.trim();
			admAccesslogService.recordAdLog(EnumAccesslogAction.PFP_CODE_MODIFY, message, super.getId_pchome(), super.getCustomer_info_id(), super.getUser_id(), request.getRemoteAddr());
			
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
		log.info(">>> convertCodeType: " + convertCodeType);
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
			
			
			//如果轉換名稱重覆，不可儲存
			List<PfpCodeConvert> pfpCodeConvertList= pfpCodeConvertService.findPfpCodeConvertListByCustomerInfoId(super.getCustomer_info_id());
			for (PfpCodeConvert pfpCodeConvertPojo : pfpCodeConvertList) {
				if( (StringUtils.equals(pfpCodeConvertPojo.getConvertName().trim(), convertName.trim())) &&  
						(!StringUtils.equals(pfpCodeConvertPojo.getConvertSeq(), convertSeq)) ){
					resultMap.put("msg", "轉換名稱不可重覆！");
					resultMap.put("status", "ERROR");
					return SUCCESS;
				}
			}
			
			
			//更新轉換追蹤資料
			PfpCodeConvert pfpCodeConvert = pfpCodeConvertService.get(convertSeq);
			
			
			
			String beforeConvertName = pfpCodeConvert.getConvertName();
			String beforeConvertType = pfpCodeConvert.getConvertType();
			String beforeConvertTypeStr = "";
			String saveConvertTypeStr = "";
			for (EnumConvertType enumConvertType : EnumConvertType.values()) {
				if(beforeConvertType.equals(enumConvertType.getType())) {
					beforeConvertTypeStr = enumConvertType.getChName();
				}
				if(convertType.equals(enumConvertType.getType())) {
					saveConvertTypeStr = enumConvertType.getChName();
				}
			}
			
			String beforeConvertClass = pfpCodeConvert.getConvertClass();
			String beforeConvertClassStr = "";
			String saveConvertClassStr = "";
			for (EnumConvertClassType enumConvertClassType : EnumConvertClassType.values()) {
				if(beforeConvertClass.equals(enumConvertClassType.getType())) {
					beforeConvertClassStr = enumConvertClassType.getChName();
				}
				if(convertClass.equals(enumConvertClassType.getType())) {
					saveConvertClassStr = enumConvertClassType.getChName();
				}
			}
			
			String beforeConvertPriceType = pfpCodeConvert.getConvertPriceType();
			float beforeConvertPrice = pfpCodeConvert.getConvertPrice();
			String beforeConvertPriceStr = "";
			String saveConvertPriceStr = "";
			for (EnumConvertPriceType enumConvertPriceType : EnumConvertPriceType.values()) {
				if(beforeConvertPriceType.equals(enumConvertPriceType.getType())) {
					beforeConvertPriceStr = enumConvertPriceType.getChName();
				}
				if(convertPriceType.equals(enumConvertPriceType.getType())) {
					saveConvertPriceStr = enumConvertPriceType.getChName();
				}
			}
			
			int beforeClickRangeDate = pfpCodeConvert.getClickRangeDate();
			int beforeImpRangeDate = pfpCodeConvert.getImpRangeDate();
			
			
			
			
			
			pfpCodeConvert.setConvertName(convertName);
			pfpCodeConvert.setConvertCodeType(convertCodeType);
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

			//accesslog
			if(!beforeConvertName.equals(convertName)) {
				String message = "轉換追蹤：代碼名稱=>代碼名稱修改：" + beforeConvertName+"=>"+convertName;
				admAccesslogService.recordAdLog(EnumAccesslogAction.PFP_CODE_MODIFY, message, super.getId_pchome(), super.getCustomer_info_id(), super.getUser_id(), request.getRemoteAddr());	
			}
			if(!beforeConvertTypeStr.equals(saveConvertTypeStr)) {
				String message = "轉換追蹤："+convertName+"=>選擇轉換追蹤條件：" + beforeConvertTypeStr+"=>修改："+saveConvertTypeStr;
				admAccesslogService.recordAdLog(EnumAccesslogAction.PFP_CODE_MODIFY, message, super.getId_pchome(), super.getCustomer_info_id(), super.getUser_id(), request.getRemoteAddr());
			}
			if(!beforeConvertClassStr.equals(saveConvertClassStr)) {
				String message = "轉換追蹤："+convertName+"=>選擇轉換類型：" + beforeConvertClassStr+"=>修改："+saveConvertClassStr;
				admAccesslogService.recordAdLog(EnumAccesslogAction.PFP_CODE_MODIFY, message, super.getId_pchome(), super.getCustomer_info_id(), super.getUser_id(), request.getRemoteAddr());
			}
			if((!beforeConvertPriceStr.equals(saveConvertPriceStr)) || (beforeConvertPrice != Integer.parseInt(convertPrice))) {
				String message = "";
				message = "轉換追蹤："+convertName+"=>轉換價值：" + beforeConvertPriceStr+"["+(int)beforeConvertPrice+"元]=>"+saveConvertPriceStr+"["+convertPrice+"元]";
				admAccesslogService.recordAdLog(EnumAccesslogAction.PFP_CODE_MODIFY, message, super.getId_pchome(), super.getCustomer_info_id(), super.getUser_id(), request.getRemoteAddr());
			}
			
			if(beforeClickRangeDate != clickRangeDate) {
				String message = "";
				message = "轉換追蹤："+convertName+"=>互動後轉換追溯修改：" +beforeClickRangeDate +"天=>"+clickRangeDate+"天";
				admAccesslogService.recordAdLog(EnumAccesslogAction.PFP_CODE_MODIFY, message, super.getId_pchome(), super.getCustomer_info_id(), super.getUser_id(), request.getRemoteAddr());
			}
			
			if(beforeImpRangeDate != impRangeDate) {
				String message = "";
				message = "轉換追蹤："+convertName+"=>瀏覽後轉換追溯修改：" +beforeImpRangeDate +"天=>"+impRangeDate+"天";
				admAccesslogService.recordAdLog(EnumAccesslogAction.PFP_CODE_MODIFY, message, super.getId_pchome(), super.getCustomer_info_id(), super.getUser_id(), request.getRemoteAddr());
			}
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
			log.info(">>> convertCodeType: " + convertCodeType);
			
			if(StringUtils.isBlank(convertCodeType)){
				PfpCodeConvert pfpCodeConvert = pfpCodeConvertService.get(convertSeq);
				convertCodeType = pfpCodeConvert.getConvertCodeType();
			}
			
			
			resultMap = new HashMap<String, Object>();
			String[] mailReceiversAry= mailReceivers.split(";");

			//send mail
			Mail mail = new Mail();
			String subject = "［PChome聯播網］轉換追蹤代碼";
			mail.setMailTo(mailReceiversAry);
			
			
			String mailContent=
					
			"<html><body>"+
			"請複製下方轉換追蹤代碼，並貼到欲追蹤網頁中，以利完成相關設定。<br/><br/>"+
			"&#60;script  id='pcadscript' language='javascript' async src='https://pacl.pchome.com.tw/js/ptag.js'&#62;&#60;/script&#62;<br/>"+
			"&#60;script&#62;<br/>";					
				mailContent = mailContent+
						"  window.dataLayer = window.dataLayer || [];<br/>"+
						"  function ptag(){dataLayer.push(arguments);}<br/>"+
						"  ptag({'paid':'"+paId+"'});<br/>"+
						"  ptag('event','convert',{<br/>"+
						"  'convert_id':'"+convertSeq+"',<br/>";
				
				if(StringUtils.equals(convertCodeType, EnumConvertCodeType.WebImpConvertTracking.getType())){
					mailContent=mailContent+"  'convert_price':''});<br/>"+
											"&#60;/script&#62;<br/>"+
											"若有相關問題，請聯絡客服中心。<br/>PChome聯播網小組 敬上</body></html>";
				}else if(StringUtils.equals(convertCodeType, EnumConvertCodeType.WebClickConvertTracking.getType())){
					mailContent=mailContent+"  'convert_price':''},'click');<br/>"+
											"&#60;/script&#62;<br/><br/><br/>"+
											"&bull;將追蹤碼：pchome_click('到達網址',true/false) 套入您網頁上的按鈕或連結 (true：開新頁籤，false：本頁開啟)<br/>"+
											"範例：< input type='button' value='TEST' onclick='pchome_click('http://www.pchome.com.tw',true);'/><br/><br/>"+
											"若有相關問題，請聯絡客服中心。<br/>PChome聯播網小組 敬上</body></html>";
				}
				
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
	 * 刪除轉換目錄
	 */
	public String deleteConvertAjax(){
		try{
			log.info(">>> currentPage: " + currentPage);
			log.info(">>> pageSizeSelected: " + pageSizeSelected);
			log.info(">>> convertSeq: " + convertSeq);
			PfpCodeConvert pfpCodeConvert = pfpCodeConvertService.get(convertSeq);
			convertName = pfpCodeConvert.getConvertName();
			log.info(">>> convertName: " + convertName);
			
			resultMap = new HashMap<String, Object>();
	
			pfpCodeConvertService.updateConvertStatus(super.getCustomer_info_id(),convertSeq,EnumConvertStatusType.Delete.getType());
			
			resultMap.put("currentPage", 1);
			resultMap.put("pageSizeSelected", pageSizeSelected);
			resultMap.put("convertName", convertName);
			resultMap.put("msg", "轉換目錄刪除成功");
			resultMap.put("status", "SUCCESS");
			//accesslog
			String message = "轉換追蹤："+convertName+"=>刪除";
			admAccesslogService.recordAdLog(EnumAccesslogAction.PFP_CODE_MODIFY, message, super.getId_pchome(), super.getCustomer_info_id(), super.getUser_id(), request.getRemoteAddr());
			
		} catch (Exception e) {
			log.error("error:" + e);
			resultMap = returnErrorMsgMap("系統忙碌中，請稍後再試，如仍有問題請洽相關人員。");
			return SUCCESS;
		}
		
		return SUCCESS;
	}
	
	
	/**
	 * 更新轉換目錄狀態
	 */
	public String updateConvertStatusAjax(){
		try{
			log.info(">>> convertSeq: " + convertSeq);
			log.info(">>> convertStatus: " + convertStatus);

			resultMap = new HashMap<String, Object>();
			
			
			PfpCodeConvert pfpCodeConvert = pfpCodeConvertService.get(convertSeq);
			String beforeConvertStatus = pfpCodeConvert.getConvertStatus();
			
			pfpCodeConvertService.updateConvertStatus(super.getCustomer_info_id(),convertSeq,convertStatus);
			resultMap.put("msg", "轉換目錄更新成功");
			resultMap.put("status", "SUCCESS");

			//accesslog
			if(!beforeConvertStatus.equals(convertStatus)) {
				String beforeConvertStatusStr = "";
				String saveConvertStatusStr = "";
				for (EnumConvertStatusType enumConvertStatusType : EnumConvertStatusType.values()) {
					if(enumConvertStatusType.getType().equals(beforeConvertStatus)) {
						beforeConvertStatusStr = enumConvertStatusType.getChName();
					}
					if(enumConvertStatusType.getType().equals(convertStatus)) {
						saveConvertStatusStr = enumConvertStatusType.getChName();
					}
				}
				String message = "轉換追蹤："+pfpCodeConvert.getConvertName()+"=>狀態修改：" +beforeConvertStatusStr +"=>"+saveConvertStatusStr;
				admAccesslogService.recordAdLog(EnumAccesslogAction.PFP_CODE_MODIFY, message, super.getId_pchome(), super.getCustomer_info_id(), super.getUser_id(), request.getRemoteAddr());
			}
		} catch (Exception e) {
			log.error("error:" + e);
			resultMap = returnErrorMsgMap("系統忙碌中，請稍後再試，如仍有問題請洽相關人員。");
			return SUCCESS;
		}
		
		return SUCCESS;
	}
	
	
	
	
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
	public List<Object> getConvertList() {
		return convertList;
	}
	public void setConvertList(List<Object> convertList) {
		this.convertList = convertList;
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


	public ConvertTrackingVO getSumConvertCount() {
		return sumConvertCount;
	}


	public void setSumConvertCount(ConvertTrackingVO sumConvertCount) {
		this.sumConvertCount = sumConvertCount;
	}


	public String getConvertStatus() {
		return convertStatus;
	}


	public void setConvertStatus(String convertStatus) {
		this.convertStatus = convertStatus;
	}

	public String getConvertCodeType() {
		return convertCodeType;
	}

	public void setConvertCodeType(String convertCodeType) {
		this.convertCodeType = convertCodeType;
	}

	public AdmAccesslogService getAdmAccesslogService() {
		return admAccesslogService;
	}

	public void setAdmAccesslogService(AdmAccesslogService admAccesslogService) {
		this.admAccesslogService = admAccesslogService;
	}
	
	
	
}