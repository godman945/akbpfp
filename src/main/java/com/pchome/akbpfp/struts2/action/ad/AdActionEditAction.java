package com.pchome.akbpfp.struts2.action.ad;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

import com.pchome.akbpfp.api.ControlPriceAPI;
import com.pchome.akbpfp.db.pojo.PfbxWebsiteCategory;
import com.pchome.akbpfp.db.pojo.PfdContract;
import com.pchome.akbpfp.db.pojo.PfdCustomerInfo;
import com.pchome.akbpfp.db.pojo.PfdUserAdAccountRef;
import com.pchome.akbpfp.db.pojo.PfpAdAction;
import com.pchome.akbpfp.db.pojo.PfpAdSpecificWebsite;
import com.pchome.akbpfp.db.pojo.PfpCodeAdactionMerge;
import com.pchome.akbpfp.db.pojo.PfpCodeConvert;
import com.pchome.akbpfp.db.pojo.PfpCodeTracking;
import com.pchome.akbpfp.db.pojo.PfpCustomerInfo;
import com.pchome.akbpfp.db.service.accesslog.AdmAccesslogService;
import com.pchome.akbpfp.db.service.ad.IPfbxWebsiteCategoryService;
import com.pchome.akbpfp.db.service.ad.IPfpAdSpecificWebsiteService;
import com.pchome.akbpfp.db.service.ad.PfpAdActionService;
import com.pchome.akbpfp.db.service.codeManage.IPfpCodeAdActionMergeService;
import com.pchome.akbpfp.db.service.codeManage.IPfpCodeConvertService;
import com.pchome.akbpfp.db.service.codeManage.IPfpCodeTrackingService;
import com.pchome.akbpfp.db.service.customerInfo.PfpCustomerInfoService;
import com.pchome.akbpfp.db.service.sequence.ISequenceService;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.ad.EnumAdCountry;
import com.pchome.enumerate.ad.EnumAdDevice;
import com.pchome.enumerate.ad.EnumAdPvLimitPeriod;
import com.pchome.enumerate.ad.EnumAdPvLimitStyle;
import com.pchome.enumerate.ad.EnumAdType;
import com.pchome.enumerate.pfd.EnumContractStatus;
import com.pchome.enumerate.sequence.EnumSequenceTableName;
import com.pchome.enumerate.utils.EnumStatus;
import com.pchome.rmi.accesslog.EnumAccesslogAction;


public class AdActionEditAction extends BaseCookieAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String adCity;
	private String adEditCity [];
	private String message = "";
	private String adActionSeq;
	private String adActionName;
	private String adActionDesc;
	private String adActionStartDate;
	private String adActionEndDate;
	private String selAdActionEndDate;
	private String adActionMax;
	private String adActionStatus;
	private String adType;
	private String adDevice;
	private int tmpRemain;
	private String backPage;
	private String addFlag = "y"; //是否顯示新增廣告子選單
	private PfpCustomerInfoService pfpCustomerInfoService;
	private PfpAdActionService pfpAdActionService;
	private AdmAccesslogService admAccesslogService;
	private ControlPriceAPI controlPriceAPI;
	private IPfpAdSpecificWebsiteService pfpAdSpecificWebsiteService;
	private IPfbxWebsiteCategoryService pfbxWebsiteCategoryService;
	private IPfpCodeTrackingService pfpCodeTrackingService;
	private IPfpCodeConvertService pfpCodeConvertService;
	private IPfpCodeAdActionMergeService pfpCodeAdActionMergeService;
	private ISequenceService sequenceService;
	private EnumAdType[] adTypeList;
	private EnumAdDevice[] adDeviceList;
	private Map<String,String> adActionStartAgeMap;
	private Map<String,String> adActionEndAgeMap;
	private String ageType;
	private String adActionStartAge;
	private String adActionEndAge;
	private String adActionSex;
	private String timeCode;
	private String timeType;
	private Map<String,String> timeCodeMap;
	private String openDetail;
	private String prodCodeInfo;
	private String adSpecificPlayType;
	private String adPvLimitStyle;
	private String adPvLimitPeriod;
	private String adPvLimitAmount;
	private String pvLimitSelect;
	private String[] websiteAddCategory;
	private String oldWebsiteCategory;
	private Map<String,String> adPvLimitStyleMap;
	private Map<String,String> adPvLimitPeriodMap;
	private List<PfpCodeTracking> pfpCodeTrackingList;
	private JSONObject pfpCodeTrackingJson = new JSONObject();
	private List<PfpCodeConvert> pfpCodeConvertList;
	private JSONObject pfpCodeConvertJson = new JSONObject();
	private String adCountry;
	private Map<String,String> adCountryMap = new LinkedHashMap<String,String>();
	private JSONObject adCountryMapJson = new JSONObject(new LinkedHashMap<String, String>());
	
	public String adActionEdit() throws Exception{
		
		
		
		log.info("adActionEdit => adActionSeq = " + adActionSeq);
		log.info("Referer = " + request.getHeader("Referer"));
		String referer = request.getHeader("Referer");
		backPage = (referer != null && StringUtils.isNotEmpty(referer))?referer:"adActionView.html";

		PfpAdAction pfpAdAction = pfpAdActionService.getPfpAdActionBySeq(adActionSeq);
		PfpCustomerInfo pfpCustomerInfo = pfpCustomerInfoService.findCustomerInfo(super.getCustomer_info_id());
		String customerInfoId = pfpCustomerInfo.getCustomerInfoId();
		if(!customerInfoId.equals(pfpAdAction.getPfpCustomerInfo().getCustomerInfoId())) {
			return "notOwner";
		}
		
		if(!StringUtils.isBlank(pfpAdAction.getAdActionCity())) {
			adCity = pfpAdAction.getAdActionCity();
		}

		//商品廣告追蹤代碼
		pfpCodeTrackingList = pfpCodeTrackingService.findTrackingCodeByCustomerInfoId(super.getCustomer_info_id());
		for (PfpCodeTracking pfpCodeTracking : pfpCodeTrackingList) {
			JSONObject json = new JSONObject();
			json.put("trackingStatus", pfpCodeTracking.getTrackingStatus());
			json.put("trackingName", pfpCodeTracking.getTrackingName());
			pfpCodeTrackingJson.put(pfpCodeTracking.getTrackingSeq(), json);
			
		}
		
		pfpCodeConvertList = pfpCodeConvertService.findConvertCodeByCustomerInfoId(super.getCustomer_info_id());
		for (PfpCodeConvert pfpCodeConvert : pfpCodeConvertList) {
			JSONObject json = new JSONObject();
			json.put("convertName", pfpCodeConvert.getConvertName());
			pfpCodeConvertJson.put(pfpCodeConvert.getConvertSeq(), json);
		}
		
		
		
		
		//判斷合約到期的綜合經銷商規則 -> 若為綜合經銷商，合約到期後不可新增廣告活動
		if (pfpCustomerInfo.getPfdUserAdAccountRefs().size()>0) {
			PfdUserAdAccountRef ref = pfpCustomerInfo.getPfdUserAdAccountRefs().iterator().next();
			PfdCustomerInfo pfdCustomerInfo = ref.getPfdCustomerInfo();
			if ("y".equals(pfdCustomerInfo.getMixFlag())) { //綜合經銷商
				//若無有效合約 -> 鎖掉新增按鈕
				if (pfdCustomerInfo.getPfdContracts().size()>0) {
					boolean haveActiveContract = false;
					Iterator<PfdContract> it = pfdCustomerInfo.getPfdContracts().iterator();
					while (it.hasNext()) {
						if (it.next().getStatus().equals(EnumContractStatus.USE.getStatusId())) {
							haveActiveContract = true;
						}
					}

					if (!haveActiveContract) {
						this.addFlag = "n";
					}
				}
			}
		}

		//設定年齡下拉選單
		adActionStartAgeMap = new LinkedHashMap<String,String>();
		adActionEndAgeMap = new LinkedHashMap<String,String>();
		adPvLimitStyleMap = new LinkedHashMap<String,String>();
		adPvLimitPeriodMap = new LinkedHashMap<String,String>();
		adActionStartAgeMap.put("0", "18歲以下");
		getAgeMap();
		adActionEndAgeMap.put("99", "75歲以上");
		
		//曝光頻率限制下拉選項
		for(EnumAdPvLimitStyle enumAdPvLimitStyle: EnumAdPvLimitStyle.values()){
			if(!StringUtils.equals(enumAdPvLimitStyle.getStyle(), "0")){
				adPvLimitStyleMap.put(enumAdPvLimitStyle.getStyle(), "針對此" + enumAdPvLimitStyle.getName());
			}
		}
		
		for(EnumAdPvLimitPeriod enumAdPvLimitPeriod: EnumAdPvLimitPeriod.values()){
			if(!StringUtils.equals(enumAdPvLimitPeriod.getPeriod(), "0")){
				adPvLimitPeriodMap.put(enumAdPvLimitPeriod.getPeriod(), enumAdPvLimitPeriod.getName());
			}
		}
		
		ageType = "A";
		timeType = "A";
		openDetail = "N";
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		adActionName = pfpAdAction.getAdActionName();
		adActionDesc = pfpAdAction.getAdActionDesc();
		adActionStartDate = sdf.format(pfpAdAction.getAdActionStartDate());
		adActionEndDate = sdf.format(pfpAdAction.getAdActionEndDate());
		if(adActionEndDate != null && !adActionEndDate.equals("3000-12-31")) {
			selAdActionEndDate = "Y";
		} else {
			selAdActionEndDate = "N";
			adActionEndDate = "";
		}
		adType = Integer.toString(pfpAdAction.getAdType());
		adDevice = Integer.toString(pfpAdAction.getAdDevice());
		adActionMax = Integer.toString((int)pfpAdAction.getAdActionMax());
		tmpRemain = (int)pfpCustomerInfo.getRemain();
		
		adTypeList = EnumAdType.values();
		adDeviceList = EnumAdDevice.values();
		
		adActionStartAge = String.valueOf(pfpAdAction.getAdActionStartAge());
		adActionEndAge = String.valueOf(pfpAdAction.getAdActionEndAge());
		if(!StringUtils.equals("0", adActionStartAge) || !StringUtils.equals("99", adActionEndAge)){
			ageType = "S";
		}
		
		adActionSex = "";
		if(StringUtils.isNotEmpty(pfpAdAction.getAdActionSex())){
			adActionSex = pfpAdAction.getAdActionSex();
		}
		
		//設定播放時間初始化
		String mon = "111111111111111111111111";
		String tue = "111111111111111111111111";
		String wed = "111111111111111111111111";
		String thu = "111111111111111111111111";
		String fri = "111111111111111111111111";
		String sat = "111111111111111111111111";
		String sun = "111111111111111111111111";
		
		//將時間數字轉換二進位字串
		mon = Integer.toBinaryString(pfpAdAction.getAdActionMonTime());
		mon = String.format("%024d",new BigInteger(mon));
		mon = " "+reversionString(mon);
		tue = Integer.toBinaryString(pfpAdAction.getAdActionTueTime());
		tue = String.format("%024d",new BigInteger(tue));
		tue = " "+reversionString(tue);
		wed = Integer.toBinaryString(pfpAdAction.getAdActionWedTime());
		wed = String.format("%024d",new BigInteger(wed));
		wed = " "+reversionString(wed);
		thu = Integer.toBinaryString(pfpAdAction.getAdActionThuTime());
		thu = String.format("%024d",new BigInteger(thu));
		thu = " "+reversionString(thu);
		fri = Integer.toBinaryString(pfpAdAction.getAdActionFriTime());
		fri = String.format("%024d",new BigInteger(fri));
		fri = " "+reversionString(fri);
		sat = Integer.toBinaryString(pfpAdAction.getAdActionSatTime());
		sat = String.format("%024d",new BigInteger(sat));
		sat = " "+reversionString(sat);
		sun = Integer.toBinaryString(pfpAdAction.getAdActionSunTime());
		sun = String.format("%024d",new BigInteger(sun));
		sun = " "+reversionString(sun);
		
		if(mon.indexOf("0") >= 0 || tue.indexOf("0") >= 0 || wed.indexOf("0") >= 0 || 
				thu.indexOf("0") >= 0 || fri.indexOf("0") >= 0 || sat.indexOf("0") >= 0 || 
				sun.indexOf("0") >= 0){
			timeType = "S";
		}
		
		timeCodeMap = new LinkedHashMap<String,String>();
		Object[][] object = {mon.split(""),tue.split(""),wed.split(""),thu.split(""),fri.split(""),sat.split(""),sun.split("")};
		
		for(int i=0;i<7;i++){
			for(int j=0;j<24;j++){
				String key = String.format("%01d",i+1) + String.format("%02d",j);
				String code = (String) object[i][j+1];
				
				if(StringUtils.equals(code, "1")){
					timeCodeMap.put(key, "checked");
				} else {
					timeCodeMap.put(key, " ");
				}
			}
		}
		
		adSpecificPlayType = pfpAdAction.getAdSpecificPlayType();
		adPvLimitStyle = pfpAdAction.getAdPvLimitStyle();
		adPvLimitPeriod = pfpAdAction.getAdPvLimitPeriod();
		//20180531 預設改為5次，須注意doAdActionEdit()中判斷式需要對應，全都寫死無法靈活使用
		adPvLimitAmount = String.valueOf(pfpAdAction.getAdPvLimitAmount() == 0 ? 5 : pfpAdAction.getAdPvLimitAmount());
				
		
		pvLimitSelect = "N";
		if(!StringUtils.equals(EnumAdPvLimitStyle.NO_STYLE_LIMIT.getStyle(), adPvLimitStyle)){
			pvLimitSelect = "Y";
		}
		oldWebsiteCategory = "";
		Set<PfpAdSpecificWebsite> fpAdSpecificWebsiteSet = pfpAdAction.getPfpAdSpecificWebsites();
		for(PfpAdSpecificWebsite pfpAdSpecificWebsite:fpAdSpecificWebsiteSet){
			oldWebsiteCategory += pfpAdSpecificWebsite.getPfbxWebsiteCategory().getId() + ",";
		}
		
		
		//商品廣告code對應查詢
		JSONObject prodCodeInfoJson = new JSONObject();
		prodCodeInfoJson.put("convert_code", new JSONObject());
		prodCodeInfoJson.put("tracking_code", new JSONObject());
		List<PfpCodeAdactionMerge> pfpCodeAdactionMergeList = pfpCodeAdActionMergeService.findProdCodeByAdactionSeq(adActionSeq);
		int convertCount = 0;
		int trackingCount = 0;
		for (PfpCodeAdactionMerge pfpCodeAdactionMerge : pfpCodeAdactionMergeList) {
			if (pfpCodeAdactionMerge.getCodeType().equals("T")) {
				JSONObject trackingJson = prodCodeInfoJson.getJSONObject("tracking_code");
				trackingJson.put(String.valueOf(trackingCount), pfpCodeAdactionMerge.getCodeId());
				trackingCount++;
			} else if (pfpCodeAdactionMerge.getCodeType().equals("C")) {
				JSONObject convertJson = prodCodeInfoJson.getJSONObject("convert_code");
				convertJson.put(String.valueOf(convertCount), pfpCodeAdactionMerge.getCodeId());
				convertCount++;
			}
		}
		prodCodeInfo = prodCodeInfoJson.toString();
		setProdCodeInfo(prodCodeInfoJson.toString());
		
		for(EnumAdCountry enumAdCountry:EnumAdCountry.values()){
			adCountryMapJson.put(enumAdCountry.getCountryType(), enumAdCountry.getCountryName());
		}
		adCountry = StringUtils.isBlank(pfpAdAction.getAdActionCountry()) ? "NULL" : pfpAdAction.getAdActionCountry();
		
		//展開進階設定
		if(StringUtils.equals(timeType, "S") 
				|| StringUtils.isNotEmpty(adActionSex) 
				|| StringUtils.equals(ageType, "S") 
				|| StringUtils.equals(pvLimitSelect, "Y") 
				|| StringUtils.isNotEmpty(oldWebsiteCategory)
				|| (pfpCodeAdactionMergeList.size() > 0)
			){
			openDetail = "Y";
		}
		
		return SUCCESS;
	}

	public String doAdActionEdit() throws Exception {
		log.info("doAdActionEdit => adActionSeq = " + adActionSeq);
		PfpCustomerInfo pfpCustomerInfo = pfpCustomerInfoService.findCustomerInfo(super.getCustomer_info_id());
		String customerInfoId = pfpCustomerInfo.getCustomerInfoId();

		if (StringUtils.isEmpty(adActionName)) {
			message = "請輸入廣告名稱！";
			return INPUT;
		} else {
			adActionName = adActionName.trim();
			if (adActionName.length() > 20) {
				message = "廣告名稱不可超過 20 字！";
				return INPUT;
			}

			if(pfpAdActionService.chkAdActionNameByCustomerInfoId(adActionName, adActionSeq, customerInfoId)) {
				message = "廣告名稱已存在";
				return INPUT;
			}
		}

		/*if (StringUtils.isEmpty(adActionDesc)) {
			message = "請輸入廣告內容簡述！";
			return INPUT;
		} else {
			adActionDesc = adActionDesc.trim();
			if (adActionDesc.length() > 50) {
				message = "廣告內容簡述不可超過 50 字！";
				return INPUT;
			}
		}*/

		//log.info(adActionStartDate.matches("^(19[0-9][0-9]|2[01]?[0-9][0-9])(/|-)([1-9]|0[1-9]|1[0-2])(/|-)([1-9]|0[1-9]|[1-2][0-9]|3[01])$"));
		Date ActionStartDate = new Date();
		if (StringUtils.isEmpty(adActionStartDate)) {
			message = "請輸入廣告開始日期！";
			return INPUT;
		} else if(!adUtils.CheckDate(adActionStartDate)) {
			message = "廣告開始日期格式錯誤，請重新輸入!";
			return INPUT;
		} else {
			adActionStartDate = adActionStartDate.trim();
			if (adActionStartDate.length() > 10) {
				message = "廣告開始日期不可超過 10 字！";
				return INPUT;
			}
			ActionStartDate = adUtils.DateFormat(adActionStartDate);
		}

		Date ActionEndDate = new Date();
		if (StringUtils.isEmpty(selAdActionEndDate)) {
			message = "請選擇是否有廣告結束日期！";
			return INPUT;
		} else {
			if (selAdActionEndDate.equals("Y")) {
				if (StringUtils.isEmpty(adActionEndDate)) {
					message = "請輸入廣告結束日期！";
					return INPUT;
				} else if(!adUtils.CheckDate(adActionEndDate)) {
					message = "廣告結束日期格式錯誤，請重新輸入!";
					return INPUT;
				} else {
					adActionEndDate = adActionEndDate.trim();
					if (adActionEndDate.length() > 10) {
						message = "廣告結束日期不可超過 10 字！";
						return INPUT;
					}
					ActionEndDate = adUtils.DateFormat(adActionEndDate);
				}
			} else {
				ActionEndDate = adUtils.DateFormat("3000-12-31");
			}
		}

		float iAdActionMax = 0;
		if (StringUtils.isEmpty(adActionMax)) {
			message = "請輸入每日預算！";
			return INPUT;
		} else {
			adActionMax = adActionMax.trim();
			iAdActionMax = Float.parseFloat(adActionMax);
			if(iAdActionMax < 100 && iAdActionMax > 999999) {
				message = "每日預算範圍為 100 ~ 999999！";
				return INPUT;
			}
		}

		if (StringUtils.equals(pvLimitSelect, "Y") && StringUtils.isEmpty(adPvLimitAmount)) {
			message = "請輸入曝光頻率限制次數！";
			return INPUT;
		}
		
		if(StringUtils.equals(adSpecificPlayType, "2") && websiteAddCategory == null){
			message = "請選擇投放網站類型！";
			return INPUT;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		PfpAdAction pfpAdAction = pfpAdActionService.getPfpAdActionBySeq(adActionSeq);
		pfpAdAction.setAdActionName(adActionName);
		pfpAdAction.setAdActionDesc(adActionName);
		// 廣告開始日期有異動
		if(!pfpAdAction.getAdActionStartDate().equals(ActionStartDate)) {
			String accesslogAction_Date = EnumAccesslogAction.AD_DATE_MODIFY.getMessage();
			String accesslogMessage_Date = accesslogAction_Date + ":" + adActionSeq + ", 活動名稱:" + adActionName + ", 廣告開始日期:" + sdf.format(pfpAdAction.getAdActionStartDate()) + " => " + sdf.format(ActionStartDate);
			admAccesslogService.recordAdLog(EnumAccesslogAction.AD_DATE_MODIFY, accesslogMessage_Date, super.getId_pchome(), super.getCustomer_info_id(), super.getUser_id(), request.getRemoteAddr());
			pfpAdAction.setAdActionStartDate(ActionStartDate);
		}
		// 廣告結束日期有異動
		if(!pfpAdAction.getAdActionEndDate().equals(ActionEndDate)) {
			String accesslogAction_Date = EnumAccesslogAction.AD_DATE_MODIFY.getMessage();
			String accesslogMessage_Date = accesslogAction_Date + ":" + adActionSeq + ", 活動名稱:" + adActionName + ", 廣告結束日期:" + sdf.format(pfpAdAction.getAdActionEndDate()) + " => " + sdf.format(ActionEndDate);
			admAccesslogService.recordAdLog(EnumAccesslogAction.AD_DATE_MODIFY, accesslogMessage_Date, super.getId_pchome(), super.getCustomer_info_id(), super.getUser_id(), request.getRemoteAddr());
			pfpAdAction.setAdActionEndDate(ActionEndDate);
		}
		
		pfpAdAction.setAdType(Integer.parseInt(adType));
		pfpAdAction.setAdDevice(Integer.parseInt(adDevice));
		if(adCountry.equals("NULL")) {
			adCountry = null;
		}
		
		String saveAdCity = null;
		if(adCountry != null && adCountry.equals("Taiwan")) {
			if(adEditCity != null) {
				if(adEditCity.length == 5) {
					saveAdCity = null;
				}else{
					saveAdCity = "";
					for (int i = 0; i < adEditCity.length; i++) {
						if(i == (adEditCity.length - 1)) {
							saveAdCity = saveAdCity + adEditCity[i];
						}else {
							saveAdCity = saveAdCity + adEditCity[i] + ",";
						}
					}
				}
			}
		}

		
		//修改廣告投放地區 access log
		if((adCountry == null &&  pfpAdAction.getAdActionCountry() != null) || (adCountry != null && !adCountry.equals(pfpAdAction.getAdActionCountry()))) {
			String message = "";
			String beforeCountry = "";
			if(pfpAdAction.getAdActionCountry() == null) {
				beforeCountry = EnumAdCountry.ALL.getCountryName();
			}else if(pfpAdAction.getAdActionCountry().equals(EnumAdCountry.TAIWAN.getCountryType())) {
				beforeCountry = EnumAdCountry.TAIWAN.getCountryName();
			}
			String afterCountry = "";
			if(adCountry == null) {
				afterCountry = EnumAdCountry.ALL.getCountryName();
			}else if(adCountry.equals(EnumAdCountry.TAIWAN.getCountryType())) {
				afterCountry = EnumAdCountry.TAIWAN.getCountryName();
			}
			message = "廣告：PFP廣告-" + pfpAdAction.getAdActionName()+" "+pfpAdAction.getAdActionSeq()+"指定廣告投放地區："+beforeCountry+"=>"+afterCountry;
			admAccesslogService.recordAdLog(EnumAccesslogAction.PLAY_MODIFY, message, super.getId_pchome(), super.getCustomer_info_id(), super.getUser_id(), request.getRemoteAddr());
		}
		
		
		pfpAdAction.setAdActionCountry(adCountry);
		pfpAdAction.setAdActionCity(saveAdCity);
		// 確認每日預算不一樣才更新
		if(iAdActionMax != pfpAdAction.getAdActionMax()){
			String accesslogAction_Money = EnumAccesslogAction.AD_MONEY_MODIFY.getMessage();
			String accesslogMessage_Money = accesslogAction_Money + ":" + adActionSeq + ", 活動名稱:" + adActionName + ", 每日預算:" + pfpAdAction.getAdActionMax() + " => " + iAdActionMax;
			admAccesslogService.recordAdLog(EnumAccesslogAction.AD_MONEY_MODIFY, accesslogMessage_Money, super.getId_pchome(), super.getCustomer_info_id(), super.getUser_id(), request.getRemoteAddr());
			pfpAdAction.setAdActionMax(iAdActionMax);
			pfpAdAction.setAdActionControlPrice(iAdActionMax);
			pfpAdAction.setChangeMax("Y");

			if(pfpAdAction.getAdActionStatus() == EnumStatus.Open.getStatusId()) {
				// 重算調控金額
				controlPriceAPI.countProcess(pfpAdAction.getPfpCustomerInfo());
			}
		}
		
		//設定播放時間初始化
		String oldMon = "111111111111111111111111";
		String oldTue = "111111111111111111111111";
		String oldWed = "111111111111111111111111";
		String oldThu = "111111111111111111111111";
		String oldFri = "111111111111111111111111";
		String oldsat = "111111111111111111111111";
		String oldSun = "111111111111111111111111";
		
		//將時間數字轉換二進位字串
		oldMon = Integer.toBinaryString(pfpAdAction.getAdActionMonTime());
		oldMon = String.format("%024d",new BigInteger(oldMon));
		oldMon = reversionString(oldMon);
		oldTue = Integer.toBinaryString(pfpAdAction.getAdActionTueTime());
		oldTue = String.format("%024d",new BigInteger(oldTue));
		oldTue = reversionString(oldTue);
		oldWed = Integer.toBinaryString(pfpAdAction.getAdActionWedTime());
		oldWed = String.format("%024d",new BigInteger(oldWed));
		oldWed = reversionString(oldWed);
		oldThu = Integer.toBinaryString(pfpAdAction.getAdActionThuTime());
		oldThu = String.format("%024d",new BigInteger(oldThu));
		oldThu = reversionString(oldThu);
		oldFri = Integer.toBinaryString(pfpAdAction.getAdActionFriTime());
		oldFri = String.format("%024d",new BigInteger(oldFri));
		oldFri = reversionString(oldFri);
		oldsat = Integer.toBinaryString(pfpAdAction.getAdActionSatTime());
		oldsat = String.format("%024d",new BigInteger(oldsat));
		oldsat = reversionString(oldsat);
		oldSun = Integer.toBinaryString(pfpAdAction.getAdActionSunTime());
		oldSun = String.format("%024d",new BigInteger(oldSun));
		oldSun = reversionString(oldSun);
		
		//新時間設定寫入
		String mon = reversionString(timeCode.substring(0,24));
		String tue = reversionString(timeCode.substring(24,48));
		String wed = reversionString(timeCode.substring(48,72));
		String thu = reversionString(timeCode.substring(72,96));
		String fri = reversionString(timeCode.substring(96,120));
		String sat = reversionString(timeCode.substring(120,144));
		String sun = reversionString(timeCode.substring(144));
		pfpAdAction.setAdActionMonTime(Integer.parseInt(mon, 2));
		pfpAdAction.setAdActionTueTime(Integer.parseInt(tue, 2));
		pfpAdAction.setAdActionWedTime(Integer.parseInt(wed, 2));
		pfpAdAction.setAdActionThuTime(Integer.parseInt(thu, 2));
		pfpAdAction.setAdActionFriTime(Integer.parseInt(fri, 2));
		pfpAdAction.setAdActionSatTime(Integer.parseInt(sat, 2));
		pfpAdAction.setAdActionSunTime(Integer.parseInt(sun, 2));
		
		//播放時間改變記錄log
		if(!StringUtils.equals(oldMon, timeCode.substring(0,24)) 
				|| !StringUtils.equals(oldTue, timeCode.substring(24,48)) 
				|| !StringUtils.equals(oldWed, timeCode.substring(48,72)) 
				|| !StringUtils.equals(oldThu, timeCode.substring(72,96)) 
				|| !StringUtils.equals(oldFri, timeCode.substring(96,120)) 
				|| !StringUtils.equals(oldsat, timeCode.substring(120,144)) 
				|| !StringUtils.equals(oldSun, timeCode.substring(144))){
			String accesslogMessage_time = "廣告：" + adActionName + " " + adActionSeq + "，廣告播放時段異動：";
			accesslogMessage_time += getTimeNote(oldMon,oldTue,oldWed,oldThu,oldFri,oldsat,oldSun);
			accesslogMessage_time += " => ";
			accesslogMessage_time += getTimeNote(timeCode.substring(0,24),timeCode.substring(24,48),timeCode.substring(48,72),timeCode.substring(72,96),timeCode.substring(96,120),timeCode.substring(120,144),timeCode.substring(144));
			
			admAccesslogService.recordAdLog(EnumAccesslogAction.PLAY_MODIFY, accesslogMessage_time, super.getId_pchome(), super.getCustomer_info_id(), super.getUser_id(), request.getRemoteAddr());
		}

		String oldAdSpecificPlayType = pfpAdAction.getAdSpecificPlayType();
		String oldPlayTypeMessage = "";
		
		if(StringUtils.equals("1", oldAdSpecificPlayType)){
			oldPlayTypeMessage += "指定廣告受眾性別/年齡：";
		} else {
			oldPlayTypeMessage += "指定投放網站類型：";
		}
		
		String oldSex = null;
		if(pfpAdAction.getAdActionSex() != null){
			oldSex = pfpAdAction.getAdActionSex();
		}
		int oldStartAge = pfpAdAction.getAdActionStartAge();
		int oldEndAge = pfpAdAction.getAdActionEndAge();
		
		pfpAdAction.setAdSpecificPlayType(adSpecificPlayType);
		List<PfpAdSpecificWebsite> pfpAdSpecificWebsiteList = pfpAdSpecificWebsiteService.findPfpAdSpecificWebsiteByAdActionSeq(pfpAdAction.getAdActionSeq());
		for(PfpAdSpecificWebsite pfpAdSpecificWebsite:pfpAdSpecificWebsiteList){
			pfpAdSpecificWebsiteService.delete(pfpAdSpecificWebsite);
		}
		
		List<PfpAdSpecificWebsite> addPfpAdSpecificWebsiteList = new ArrayList<PfpAdSpecificWebsite>();
		
		if(StringUtils.equals(adSpecificPlayType, "0")){
			
			if(StringUtils.isNotEmpty(adActionSex)){
				pfpAdAction.setAdActionSex(adActionSex);
			} else {
				pfpAdAction.setAdActionSex(null);
			}
			pfpAdAction.setAdActionStartAge(Integer.parseInt(adActionStartAge));
			pfpAdAction.setAdActionEndAge(Integer.parseInt(adActionEndAge));
		} else if(StringUtils.equals(adSpecificPlayType, "1")){
			
			pfpAdAction.setAdActionStartAge(0);
			pfpAdAction.setAdActionEndAge(99);
			pfpAdAction.setAdActionSex(null);
			
			Map<String, PfbxWebsiteCategory> PfbxWebsiteCategoryMap = new LinkedHashMap<String, PfbxWebsiteCategory>();
			PfbxWebsiteCategoryMap = pfbxWebsiteCategoryService.getPfbxWebsiteCategoryMap();
			for(String id:websiteAddCategory){
				if(PfbxWebsiteCategoryMap.get(id) != null){
					PfpAdSpecificWebsite pfpAdSpecificWebsite = new PfpAdSpecificWebsite();
					String specificWebsiteSeq = sequenceService.getId(EnumSequenceTableName.PFP_AD_SPECIFIC_WEBSITE, "_");
					pfpAdSpecificWebsite.setSpecificWebsiteSeq(specificWebsiteSeq);
					pfpAdSpecificWebsite.setPfbxWebsiteCategory(PfbxWebsiteCategoryMap.get(id));
					addPfpAdSpecificWebsiteList.add(pfpAdSpecificWebsite);
				}
			}
		}
		
		String accesslogMessage_playType = "廣告：" + adActionName + " " + adActionSeq + "，";
		if(StringUtils.equals(oldAdSpecificPlayType, adSpecificPlayType)){
			accesslogMessage_playType += getPlayTypeNote(oldAdSpecificPlayType) + "：";
			if(StringUtils.equals("0", oldAdSpecificPlayType)){
				boolean checklog = false;
				if(!StringUtils.equals(oldSex, adActionSex) && !(oldSex == null && StringUtils.isEmpty(adActionSex))){
					checklog = true;
				}
				if(!StringUtils.equals(String.valueOf(oldStartAge), adActionStartAge) || !StringUtils.equals(String.valueOf(oldEndAge), adActionEndAge)){
					checklog = true;
				}
				if(checklog){
					accesslogMessage_playType += getSexName(oldSex) + "、" + getAgeNote(oldStartAge,oldEndAge) + " => " + getSexName(adActionSex) + "、" + getAgeNote(Integer.parseInt(adActionStartAge),Integer.parseInt(adActionEndAge));
					admAccesslogService.recordAdLog(EnumAccesslogAction.PLAY_MODIFY, accesslogMessage_playType, super.getId_pchome(), super.getCustomer_info_id(), super.getUser_id(), request.getRemoteAddr());
				}
			} else if(StringUtils.equals("1", oldAdSpecificPlayType)){
				accesslogMessage_playType += getSpecificWebsiteNote(pfpAdSpecificWebsiteList) + " => " + getSpecificWebsiteNote(addPfpAdSpecificWebsiteList);
				admAccesslogService.recordAdLog(EnumAccesslogAction.PLAY_MODIFY, accesslogMessage_playType, super.getId_pchome(), super.getCustomer_info_id(), super.getUser_id(), request.getRemoteAddr());
			}
		} else {
			accesslogMessage_playType += "廣告指定投放：" + getPlayTypeNote(oldAdSpecificPlayType) + "：";
			if(StringUtils.equals("0", oldAdSpecificPlayType)){
				accesslogMessage_playType += getSexName(oldSex) + "、" + getAgeNote(oldStartAge,oldEndAge);
			} else if(StringUtils.equals("1", oldAdSpecificPlayType)){
				accesslogMessage_playType += getSpecificWebsiteNote(pfpAdSpecificWebsiteList);
			}
			accesslogMessage_playType += " => " + getPlayTypeNote(adSpecificPlayType) + "：";
			if(StringUtils.equals("0", adSpecificPlayType)){
				accesslogMessage_playType += getSexName(adActionSex) + "、" + getAgeNote(Integer.parseInt(adActionStartAge),Integer.parseInt(adActionEndAge));
			} else if(StringUtils.equals("1", adSpecificPlayType)){
				accesslogMessage_playType += getSpecificWebsiteNote(addPfpAdSpecificWebsiteList);
			}
			admAccesslogService.recordAdLog(EnumAccesslogAction.PLAY_MODIFY, accesslogMessage_playType, super.getId_pchome(), super.getCustomer_info_id(), super.getUser_id(), request.getRemoteAddr());
		}
		
		String oldPvLimitStyle = pfpAdAction.getAdPvLimitStyle();
		String oldPvLimitPeriod = pfpAdAction.getAdPvLimitPeriod();
		Integer oldPvLimitAmount = pfpAdAction.getAdPvLimitAmount();
		
		if(StringUtils.equals(pvLimitSelect, "Y")){
			pfpAdAction.setAdPvLimitStyle(adPvLimitStyle);
			pfpAdAction.setAdPvLimitPeriod(adPvLimitPeriod);
			pfpAdAction.setAdPvLimitAmount(Integer.parseInt(adPvLimitAmount));
			
			if(!StringUtils.equals(oldPvLimitStyle, adPvLimitStyle) || !StringUtils.equals(oldPvLimitPeriod, adPvLimitPeriod) || oldPvLimitAmount != Integer.parseInt(adPvLimitAmount)){
				String accesslogMessage_pvLimit = "廣告：" + adActionName + " " + adActionSeq + "，曝光頻率限制： " + getPvLimitNote(oldPvLimitStyle,oldPvLimitPeriod,oldPvLimitAmount) + " => " + getPvLimitNote(adPvLimitStyle,adPvLimitPeriod,Integer.parseInt(adPvLimitAmount));
				admAccesslogService.recordAdLog(EnumAccesslogAction.PLAY_MODIFY, accesslogMessage_pvLimit, super.getId_pchome(), super.getCustomer_info_id(), super.getUser_id(), request.getRemoteAddr());
			}
		} else {
			pfpAdAction.setAdPvLimitStyle("0");
			pfpAdAction.setAdPvLimitPeriod("0");
			pfpAdAction.setAdPvLimitAmount(5);
			
			if(!StringUtils.equals(oldPvLimitStyle, "0") || !StringUtils.equals(oldPvLimitPeriod, "0") || oldPvLimitAmount != 5){
				String accesslogMessage_pvLimit = "廣告：" + adActionName + " " + adActionSeq + "，曝光頻率限制： " + getPvLimitNote(oldPvLimitStyle,oldPvLimitPeriod,oldPvLimitAmount) + " => 無限制";
				admAccesslogService.recordAdLog(EnumAccesslogAction.PLAY_MODIFY, accesslogMessage_pvLimit, super.getId_pchome(), super.getCustomer_info_id(), super.getUser_id(), request.getRemoteAddr());
			}
		}
		
		pfpAdAction.setUserId(super.getUser_id());
		pfpAdAction.setPfpCustomerInfo(pfpCustomerInfo);
		pfpAdAction.setAdActionUpdateTime(new Date());
		
		pfpAdActionService.savePfpAdAction(pfpAdAction);
		
		for(PfpAdSpecificWebsite websiteData:addPfpAdSpecificWebsiteList){
			websiteData.setPfpAdAction(pfpAdAction);
			pfpAdSpecificWebsiteService.saveOrUpdate(websiteData);
		}
		
		//寫access 在行銷/轉換 log用
		List<String> trackingCodeList = new ArrayList<String>();
		List<String> converCodeList = new ArrayList<String>();
		List<PfpCodeAdactionMerge> pfpCodeAdactionMergeList = pfpCodeAdActionMergeService.findProdCodeByAdactionSeq(adActionSeq);
		for (PfpCodeAdactionMerge pfpCodeAdactionMerge : pfpCodeAdactionMergeList) {
			if(pfpCodeAdactionMerge.getCodeType().equals("C")) {
				converCodeList.add(pfpCodeAdactionMerge.getCodeId());
			}
			if(pfpCodeAdactionMerge.getCodeType().equals("T")) {
				trackingCodeList.add(pfpCodeAdactionMerge.getCodeId());
			}
		}
		
		//儲存商品廣告代碼對應
		pfpCodeAdActionMergeService.deleteProdCodeByCodeType("T", adActionSeq);
		pfpCodeAdActionMergeService.deleteProdCodeByCodeType("C", adActionSeq);
		Date date = new Date();
		JSONObject prodCodeInfoJson = new JSONObject(prodCodeInfo);
		Iterator<String> prodCodeInfoJsonkeys = prodCodeInfoJson.keys();
		while (prodCodeInfoJsonkeys.hasNext()) {
			String key = prodCodeInfoJsonkeys.next();
			Object jsonbValue = prodCodeInfoJson.get(key);
			String convertSeq = "";
			if (key.equals("convert_code") && jsonbValue != null) {
				Iterator<String> trackingCodeJsonkeys = ((JSONObject) jsonbValue).keys();
				while (trackingCodeJsonkeys.hasNext()) {
					String convertJsonKey = trackingCodeJsonkeys.next();
					convertSeq = (String) ((JSONObject) jsonbValue).get(convertJsonKey);
					PfpCodeAdactionMerge pfpCodeAdActionMerge = new PfpCodeAdactionMerge();
					pfpCodeAdActionMerge.setAdActionSeq(adActionSeq);
					pfpCodeAdActionMerge.setCodeType("C");
					pfpCodeAdActionMerge.setCodeId(convertSeq);
					pfpCodeAdActionMerge.setUpdateDate(date);
					pfpCodeAdActionMerge.setCreateDate(date);
					pfpCodeAdActionMergeService.saveOrUpdate(pfpCodeAdActionMerge);
				}
				
				//轉換access log
				if(converCodeList.size() == 0 && StringUtils.isNotBlank(convertSeq)) {
					var message = "";
					message = "廣告：PFP廣告-" + pfpAdAction.getAdActionName()+" "+pfpAdAction.getAdActionSeq()+"，轉換追蹤：不使用轉換追蹤 =>使用轉換追蹤："+convertSeq;
					admAccesslogService.recordAdLog(EnumAccesslogAction.PLAY_MODIFY, message, super.getId_pchome(), super.getCustomer_info_id(), super.getUser_id(), request.getRemoteAddr());
				}
				
				if(converCodeList.size() > 0 && StringUtils.isNotBlank(convertSeq) && !converCodeList.get(0).equals(convertSeq)) {
					var message = "";
					message = "廣告：PFP廣告-" + pfpAdAction.getAdActionName()+" "+pfpAdAction.getAdActionSeq()+"，轉換追蹤：使用轉換追蹤("+converCodeList.get(0)+") =>使用轉換追蹤："+convertSeq;
					admAccesslogService.recordAdLog(EnumAccesslogAction.PLAY_MODIFY, message, super.getId_pchome(), super.getCustomer_info_id(), super.getUser_id(), request.getRemoteAddr());
				}
				
				if(converCodeList.size() > 0 && StringUtils.isBlank(convertSeq)) {
					var message = "";
					message = "廣告：PFP廣告-" + pfpAdAction.getAdActionName()+" "+pfpAdAction.getAdActionSeq()+"，轉換追蹤：使用轉換追蹤("+converCodeList.get(0)+") =>不使用轉換追蹤";
					admAccesslogService.recordAdLog(EnumAccesslogAction.PLAY_MODIFY, message, super.getId_pchome(), super.getCustomer_info_id(), super.getUser_id(), request.getRemoteAddr());
				}
				
				
			}else if (key.equals("tracking_code") && jsonbValue != null) {
				Iterator<String> trackingCodeJsonkeys = ((JSONObject) jsonbValue).keys();
				String trackingCodeStr = "";
				while (trackingCodeJsonkeys.hasNext()) {
					String trackingJsonKey = trackingCodeJsonkeys.next();
					String trackingSeq = (String) ((JSONObject) jsonbValue).get(trackingJsonKey);
					if(StringUtils.isBlank(trackingCodeStr)) {
						trackingCodeStr = trackingCodeStr + trackingSeq;
					}else {
						trackingCodeStr = trackingCodeStr + "，"+trackingSeq;
					}
					PfpCodeAdactionMerge pfpCodeAdActionMerge = new PfpCodeAdactionMerge();
					pfpCodeAdActionMerge.setAdActionSeq(adActionSeq);
					pfpCodeAdActionMerge.setCodeType("T");
					pfpCodeAdActionMerge.setCodeId(trackingSeq);
					pfpCodeAdActionMerge.setUpdateDate(date);
					pfpCodeAdActionMerge.setCreateDate(date);
					pfpCodeAdActionMergeService.saveOrUpdate(pfpCodeAdActionMerge);
				}
				
				//追蹤access log
				if(trackingCodeList.size() == 0 && StringUtils.isNotBlank(trackingCodeStr)) {
					var message = "";
					message = "廣告：PFP廣告-" + pfpAdAction.getAdActionName()+" "+pfpAdAction.getAdActionSeq()+"，再行銷追蹤：不使用再行銷追蹤 =>使用再行銷追蹤："+trackingCodeStr;
					admAccesslogService.recordAdLog(EnumAccesslogAction.PLAY_MODIFY, message, super.getId_pchome(), super.getCustomer_info_id(), super.getUser_id(), request.getRemoteAddr());
				}
//				
				if(trackingCodeList.size() > 0 && StringUtils.isNotBlank(trackingCodeStr)) {
					List<String> trackingCodeSaveList= Arrays.asList(trackingCodeStr.split("，"));
					Collections.sort(trackingCodeList);
					Collections.sort(trackingCodeSaveList);
					boolean flag = true;
					for (int i = 0; i < trackingCodeSaveList.size(); i++) {
						if(!trackingCodeList.get(i).equals(trackingCodeSaveList.get(i))) {
							flag = false;
							break;
						}
					}
					
					if(!flag) {
						String beforeTrackingCodeStr = "";
						for (String trackingCode : trackingCodeList) {
							if(StringUtils.isBlank(beforeTrackingCodeStr)) {
								beforeTrackingCodeStr = beforeTrackingCodeStr + trackingCode;
							}else {
								beforeTrackingCodeStr = beforeTrackingCodeStr + "，"+trackingCode;
							}
						}
						var message = "";
						message = "廣告：PFP廣告-" + pfpAdAction.getAdActionName()+" "+pfpAdAction.getAdActionSeq()+"，再行銷追蹤：使用再行銷追蹤："+beforeTrackingCodeStr+" =>"+trackingCodeStr;
						admAccesslogService.recordAdLog(EnumAccesslogAction.PLAY_MODIFY, message, super.getId_pchome(), super.getCustomer_info_id(), super.getUser_id(), request.getRemoteAddr());
					}
				}
				
				if(trackingCodeList.size() > 0 && StringUtils.isBlank(trackingCodeStr)) {
					String beforeTrackingCodeStr = "";
					for (String trackingCode : trackingCodeList) {
						if(StringUtils.isBlank(beforeTrackingCodeStr)) {
							beforeTrackingCodeStr = beforeTrackingCodeStr + trackingCode;
						}else {
							beforeTrackingCodeStr = beforeTrackingCodeStr + "，"+trackingCode;
						}
					}
					var message = "";
					message = "廣告：PFP廣告-" + pfpAdAction.getAdActionName()+" "+pfpAdAction.getAdActionSeq()+"，再行銷追蹤：使用再行銷追蹤"+beforeTrackingCodeStr+" =>不使用使用再行銷追蹤";
					admAccesslogService.recordAdLog(EnumAccesslogAction.PLAY_MODIFY, message, super.getId_pchome(), super.getCustomer_info_id(), super.getUser_id(), request.getRemoteAddr());
				}
			}
		}
		
		
		
		
		
		
		return SUCCESS;
	}

	private String reversionString(String timeString){
		
		String time = "";
		String[] timeArray = timeString.split("");
		for(int i=0;i<timeArray.length;i++){
			time = timeArray[i] + time;
		}
		
		return time;
	}
	
	private void getAgeMap(){
		for(int i=18;i<=75;i++){
			adActionStartAgeMap.put(String.valueOf(i),i + "歲");
			adActionEndAgeMap.put(String.valueOf(i),i + "歲");
		}
	}
	
	private String getSexName(String sex){
		
		if(StringUtils.isEmpty(sex)){
			sex = "N";
		}
		
		String sexName = "不分性別";
		
		switch (sex) {
		case "M":
			sexName = "男性";
			break;
		case "F":
			sexName = "女性";
			break;
		default:
			sexName = "不分性別";
			break;
		}
		
		
		return sexName;
	}
	
	private String getAgeNote(int startAge, int endAge){
		String ageNote = "";
		
		if(startAge != 0 || endAge != 99){
			
			String startAgeName = "18歲以下";
			String endAgeName = "75歲以上";
			
			if(startAge != 0){
				startAgeName = String.valueOf(startAge);
			}
			if(endAge != 99){
				endAgeName = String.valueOf(endAge);
			}
			
			ageNote = "自訂(" + startAgeName + "-" + endAgeName + ")";
		} else {
			ageNote = "不分年齡";
		}
		
		return ageNote;
	}
	
	private String getTimeNote(String mon, String tue, String wed, String thu, String fri, String sat, String sun){
		String timeNote = "";
		
		if(!StringUtils.equals(mon, "111111111111111111111111") 
				|| !StringUtils.equals(tue, "111111111111111111111111") 
				|| !StringUtils.equals(wed, "111111111111111111111111") 
				|| !StringUtils.equals(thu, "111111111111111111111111") 
				|| !StringUtils.equals(fri, "111111111111111111111111") 
				|| !StringUtils.equals(sat, "111111111111111111111111") 
				|| !StringUtils.equals(sun, "111111111111111111111111")){
			
			timeNote = "自訂播放時段(";
			
			String[] weekArray = {"星期一","星期二","星期三","星期四","星期五","星期六","星期日"};
			String[] timeCodeArray = {mon,tue,wed,thu,fri,sat,sun};
			
			String weekNote = "";
			for(int i=0;i<7;i++){
				
				String week = weekArray[i];
				String timeCode = timeCodeArray[i];
				
				if(!StringUtils.equals(timeCode, "000000000000000000000000")){
					if(StringUtils.isNotEmpty(weekNote)){
						weekNote += "/";
					}
					weekNote += week + "：";
					if(!StringUtils.equals(timeCode, "111111111111111111111111")){
						weekNote += getTimeSet(timeCode);
					} else{
						weekNote += "全時段";
					}
				}
			}
			
			timeNote += weekNote + ")";
		} else {
			timeNote = "全天候播放廣告";
		}
		
		return timeNote;
	}
	
	private String getTimeSet(String timeSet){
		String setName = "";
		String[] timeArray = {"12","1","2","3","4","5","6","7","8","9","10","11"};
		String time1 = timeSet.substring(0, 12);
		String time2 = timeSet.substring(12);
		
		if(!StringUtils.equals(time1, "000000000000")){
			time1 = " "+time1;
			setName += "上午";
			String[] codeArray = time1.split("");
			String hourNote = "";
			for(int j=0;j<12;j++){
				String code = codeArray[j+1];
				if(StringUtils.equals(code, "1")){
					if(StringUtils.isNotEmpty(hourNote)){
						hourNote += "、";
					}
					hourNote += timeArray[j];
				}
			}
			setName += hourNote;
		}
		
		if(!StringUtils.equals(time2, "000000000000")){
			if(StringUtils.isNotEmpty(setName)){
				setName += "；";
			}
			setName += "下午";
			String[] codeArray = time2.split("");
			String hourNote = "";
			for(int j=0;j<12;j++){
				String code = codeArray[j];
				if(StringUtils.equals(code, "1")){
					if(StringUtils.isNotEmpty(hourNote)){
						hourNote += "、";
					}
					hourNote += timeArray[j];
				}
			}
			setName += hourNote;
		}
		
		return setName;
	}
	
	private String getPlayTypeNote(String adSpecificPlayType){
		String playTypeNote = "";
		
		if(StringUtils.equals("0", adSpecificPlayType)){
			playTypeNote = "指定廣告受眾性別/年齡";
		} else if(StringUtils.equals("1", adSpecificPlayType)){
			playTypeNote = "指定投放網站類型";
		}
		
		return playTypeNote;
	}
	
	private String getSpecificWebsiteNote(List<PfpAdSpecificWebsite> list) {
		String specificWebsiteNote = "";
		
		if(!list.isEmpty()){
			for(PfpAdSpecificWebsite data:list){
				specificWebsiteNote += data.getPfbxWebsiteCategory().getName() + "、";
			}
		} else {
			specificWebsiteNote = "未指定類別、";
		}
		
		specificWebsiteNote = specificWebsiteNote.substring(0, specificWebsiteNote.length()-1);
		return specificWebsiteNote;
	}
	
	private String getPvLimitNote(String pvLimitStyle, String pvLimitPeriod, Integer pvLimitAmount){
		String pvLimitNote = "";
		
		if(StringUtils.equals(EnumAdPvLimitStyle.NO_STYLE_LIMIT.getStyle(), pvLimitStyle) && 
				StringUtils.equals(EnumAdPvLimitPeriod.NO_PREIOD_LIMIT.getPeriod(), pvLimitPeriod) && pvLimitAmount == 0){
			pvLimitNote = "無限制";
		} else {
			for(EnumAdPvLimitStyle enumAdPvLimitStyle:EnumAdPvLimitStyle.values()){
				if(StringUtils.equals(enumAdPvLimitStyle.getStyle(),pvLimitStyle)){
					pvLimitNote += "針對此" + enumAdPvLimitStyle.getName() + "；";
					break;
				}
			}
			for(EnumAdPvLimitPeriod enumAdPvLimitPeriod:EnumAdPvLimitPeriod.values()){
				if(StringUtils.equals(enumAdPvLimitPeriod.getPeriod(),pvLimitPeriod)){
					pvLimitNote += enumAdPvLimitPeriod.getName() + "；";
					break;
				}
			}
			pvLimitNote += "曝光給同一廣告受眾" + pvLimitAmount.toString() + "次";
		}
		
		return pvLimitNote;
	}
	
	public void setPfpCustomerInfoService(PfpCustomerInfoService pfpCustomerInfoService) {
		this.pfpCustomerInfoService = pfpCustomerInfoService;
	}

	public void setPfpAdActionService(PfpAdActionService pfpAdActionService) {
		this.pfpAdActionService = pfpAdActionService;
	}

	public void setAdmAccesslogService(AdmAccesslogService admAccesslogService) {
		this.admAccesslogService = admAccesslogService;
	}

	public void setControlPriceAPI(ControlPriceAPI controlPriceAPI) {
		this.controlPriceAPI = controlPriceAPI;
	}

	public void setPfpAdSpecificWebsiteService(IPfpAdSpecificWebsiteService pfpAdSpecificWebsiteService) {
		this.pfpAdSpecificWebsiteService = pfpAdSpecificWebsiteService;
	}

	public void setPfbxWebsiteCategoryService(IPfbxWebsiteCategoryService pfbxWebsiteCategoryService) {
		this.pfbxWebsiteCategoryService = pfbxWebsiteCategoryService;
	}

	public void setSequenceService(ISequenceService sequenceService) {
		this.sequenceService = sequenceService;
	}

	public String getAdActionSeq() {
		return adActionSeq;
	}

	public void setAdActionSeq(String adActionSeq) {
		this.adActionSeq = adActionSeq;
	}

	public String getAdActionName() {
		return adActionName;
	}

	public void setAdActionName(String adActionName) {
		this.adActionName = adActionName;
	}

	public String getAdActionDesc() {
		return adActionDesc;
	}

	public void setAdActionDesc(String adActionDesc) {
		this.adActionDesc = adActionDesc;
	}

	public String getAdActionStartDate() {
		return adActionStartDate;
	}

	public void setAdActionStartDate(String adActionStartDate) {
		this.adActionStartDate = adActionStartDate;
	}

	public String getAdActionEndDate() {
		return adActionEndDate;
	}

	public void setAdActionEndDate(String adActionEndDate) {
		this.adActionEndDate = adActionEndDate;
	}

	public String getSelAdActionEndDate() {
		return selAdActionEndDate;
	}

	public void setSelAdActionEndDate(String selAdActionEndDate) {
		this.selAdActionEndDate = selAdActionEndDate;
	}

	public String getAdActionMax() {
		return adActionMax;
	}

	public void setAdActionMax(String adActionMax) {
		this.adActionMax = adActionMax;
	}

	public String getAdActionStatus() {
		return adActionStatus;
	}

	public void setAdActionStatus(String adActionStatus) {
		this.adActionStatus = adActionStatus;
	}

	public int getTmpRemain() {
		return tmpRemain;
	}

	public String getBackPage() {
		return backPage;
	}

	public void setBackPage(String backPage) {
		this.backPage = backPage;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getAddFlag() {
		return addFlag;
	}

	public String getAdType() {
		return adType;
	}

	public void setAdType(String adType) {
		this.adType = adType;
	}

	public String getAdDevice() {
		return adDevice;
	}

	public void setAdDevice(String adDevice) {
		this.adDevice = adDevice;
	}

	public EnumAdType[] getAdTypeList() {
		return adTypeList;
	}

	public EnumAdDevice[] getAdDeviceList() {
		return adDeviceList;
	}

	public String getAdActionStartAge() {
		return adActionStartAge;
	}

	public void setAdActionStartAge(String adActionStartAge) {
		this.adActionStartAge = adActionStartAge;
	}

	public String getAdActionEndAge() {
		return adActionEndAge;
	}

	public void setAdActionEndAge(String adActionEndAge) {
		this.adActionEndAge = adActionEndAge;
	}

	public Map<String, String> getAdActionStartAgeMap() {
		return adActionStartAgeMap;
	}

	public Map<String, String> getAdActionEndAgeMap() {
		return adActionEndAgeMap;
	}

	public String getAgeType() {
		return ageType;
	}

	public void setAgeType(String ageType) {
		this.ageType = ageType;
	}
	
	public String getAdActionSex() {
		return adActionSex;
	}

	public void setAdActionSex(String adActionSex) {
		this.adActionSex = adActionSex;
	}

	public void setTimeCode(String timeCode) {
		this.timeCode = timeCode;
	}

	public Map<String, String> getTimeCodeMap() {
		return timeCodeMap;
	}

	public String getTimeType() {
		return timeType;
	}

	public String getOpenDetail() {
		return openDetail;
	}

	public String getAdSpecificPlayType() {
		return adSpecificPlayType;
	}

	public void setAdSpecificPlayType(String adSpecificPlayType) {
		this.adSpecificPlayType = adSpecificPlayType;
	}

	public String getAdPvLimitStyle() {
		return adPvLimitStyle;
	}

	public void setAdPvLimitStyle(String adPvLimitStyle) {
		this.adPvLimitStyle = adPvLimitStyle;
	}

	public String getAdPvLimitPeriod() {
		return adPvLimitPeriod;
	}

	public void setAdPvLimitPeriod(String adPvLimitPeriod) {
		this.adPvLimitPeriod = adPvLimitPeriod;
	}

	public String getAdPvLimitAmount() {
		return adPvLimitAmount;
	}

	public void setAdPvLimitAmount(String adPvLimitAmount) {
		this.adPvLimitAmount = adPvLimitAmount;
	}

	public String getPvLimitSelect() {
		return pvLimitSelect;
	}

	public void setPvLimitSelect(String pvLimitSelect) {
		this.pvLimitSelect = pvLimitSelect;
	}

	public void setWebsiteAddCategory(String[] websiteAddCategory) {
		this.websiteAddCategory = websiteAddCategory;
	}

	public String getOldWebsiteCategory() {
		return oldWebsiteCategory;
	}

	public Map<String, String> getAdPvLimitStyleMap() {
		return adPvLimitStyleMap;
	}

	public Map<String, String> getAdPvLimitPeriodMap() {
		return adPvLimitPeriodMap;
	}

	public IPfpCodeTrackingService getPfpCodeTrackingService() {
		return pfpCodeTrackingService;
	}

	public void setPfpCodeTrackingService(IPfpCodeTrackingService pfpCodeTrackingService) {
		this.pfpCodeTrackingService = pfpCodeTrackingService;
	}

	public IPfpCodeConvertService getPfpCodeConvertService() {
		return pfpCodeConvertService;
	}

	public void setPfpCodeConvertService(IPfpCodeConvertService pfpCodeConvertService) {
		this.pfpCodeConvertService = pfpCodeConvertService;
	}

	public IPfpCodeAdActionMergeService getPfpCodeAdActionMergeService() {
		return pfpCodeAdActionMergeService;
	}

	public void setPfpCodeAdActionMergeService(IPfpCodeAdActionMergeService pfpCodeAdActionMergeService) {
		this.pfpCodeAdActionMergeService = pfpCodeAdActionMergeService;
	}

	public String getProdCodeInfo() {
		return prodCodeInfo;
	}

	public void setProdCodeInfo(String prodCodeInfo) {
		this.prodCodeInfo = prodCodeInfo;
	}

	public List<PfpCodeTracking> getPfpCodeTrackingList() {
		return pfpCodeTrackingList;
	}

	public void setPfpCodeTrackingList(List<PfpCodeTracking> pfpCodeTrackingList) {
		this.pfpCodeTrackingList = pfpCodeTrackingList;
	}

	public List<PfpCodeConvert> getPfpCodeConvertList() {
		return pfpCodeConvertList;
	}

	public void setPfpCodeConvertList(List<PfpCodeConvert> pfpCodeConvertList) {
		this.pfpCodeConvertList = pfpCodeConvertList;
	}

	public String getAdCountry() {
		return adCountry;
	}

	public void setAdCountry(String adCountry) {
		this.adCountry = adCountry;
	}

	public Map<String, String> getAdCountryMap() {
		return adCountryMap;
	}

	public void setAdCountryMap(Map<String, String> adCountryMap) {
		this.adCountryMap = adCountryMap;
	}

	public JSONObject getPfpCodeTrackingJson() {
		return pfpCodeTrackingJson;
	}

	public void setPfpCodeTrackingJson(JSONObject pfpCodeTrackingJson) {
		this.pfpCodeTrackingJson = pfpCodeTrackingJson;
	}

	public JSONObject getPfpCodeConvertJson() {
		return pfpCodeConvertJson;
	}

	public void setPfpCodeConvertJson(JSONObject pfpCodeConvertJson) {
		this.pfpCodeConvertJson = pfpCodeConvertJson;
	}

	public JSONObject getAdCountryMapJson() {
		return adCountryMapJson;
	}

	public void setAdCountryMapJson(JSONObject adCountryMapJson) {
		this.adCountryMapJson = adCountryMapJson;
	}

	public String getAdCity() {
		return adCity;
	}

	public void setAdCity(String adCity) {
		this.adCity = adCity;
	}

	public String[] getAdEditCity() {
		return adEditCity;
	}

	public void setAdEditCity(String[] adEditCity) {
		this.adEditCity = adEditCity;
	}


	
}
