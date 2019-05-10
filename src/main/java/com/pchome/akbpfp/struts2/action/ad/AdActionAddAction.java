package com.pchome.akbpfp.struts2.action.ad;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

import com.pchome.akbpfp.api.SyspriceOperaterAPI;
import com.pchome.akbpfp.db.pojo.PfbxWebsiteCategory;
import com.pchome.akbpfp.db.pojo.PfdUserAdAccountRef;
import com.pchome.akbpfp.db.pojo.PfpAdAction;
import com.pchome.akbpfp.db.pojo.PfpAdGroup;
import com.pchome.akbpfp.db.pojo.PfpAdSpecificWebsite;
import com.pchome.akbpfp.db.pojo.PfpCatalogLogo;
import com.pchome.akbpfp.db.pojo.PfpCodeAdactionMerge;
import com.pchome.akbpfp.db.pojo.PfpCodeConvert;
import com.pchome.akbpfp.db.pojo.PfpCodeTracking;
import com.pchome.akbpfp.db.pojo.PfpCustomerInfo;
import com.pchome.akbpfp.db.service.ad.IPfbxWebsiteCategoryService;
import com.pchome.akbpfp.db.service.ad.IPfpAdActionService;
import com.pchome.akbpfp.db.service.ad.IPfpAdSpecificWebsiteService;
import com.pchome.akbpfp.db.service.ad.PfpAdActionService;
import com.pchome.akbpfp.db.service.catalog.IPfpCatalogService;
import com.pchome.akbpfp.db.service.catalog.prod.IPfpCatalogLogoService;
import com.pchome.akbpfp.db.service.codeManage.IPfpCodeAdActionMergeService;
import com.pchome.akbpfp.db.service.codeManage.IPfpCodeConvertService;
import com.pchome.akbpfp.db.service.codeManage.IPfpCodeTrackingService;
import com.pchome.akbpfp.db.service.customerInfo.IPfpCustomerInfoService;
import com.pchome.akbpfp.db.service.customerInfo.PfpCustomerInfoService;
import com.pchome.akbpfp.db.service.pfd.user.IPfdUserAdAccountRefService;
import com.pchome.akbpfp.db.service.sequence.ISequenceService;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.ad.EnumAdCountry;
import com.pchome.enumerate.ad.EnumAdDevice;
import com.pchome.enumerate.ad.EnumAdPvLimitPeriod;
import com.pchome.enumerate.ad.EnumAdPvLimitStyle;
import com.pchome.enumerate.ad.EnumAdStyleType;
import com.pchome.enumerate.ad.EnumAdType;
import com.pchome.enumerate.ad.EnumCatalogLogoStatus;
import com.pchome.enumerate.sequence.EnumSequenceTableName;
import com.pchome.enumerate.utils.EnumStatus;


public class AdActionAddAction extends BaseCookieAction{

	private static final long serialVersionUID = 1L;
	private String userCategory;
	private String message = "";
	private String customerInfoId;
	private String adActionSeq;
	private String adActionName;
	private String adGroupName;
	private String adActionDesc;
	private String adActionStartDate;
	private String adActionEndDate;
	private String selAdActionEndDate;
	private String adActionMax;
	private String adActionStatus;
	private String adType;
	private String adDevice;
	private float remain;
	private int tmpRemain;
	private String backPage;
	private String adOperatingRule;
	private String sysChannelPrice;
	private String sysPriceAdPoolSeq;
	private String adAsideRate;
	private String adCountry;
	private String adCity[];
	private IPfpCustomerInfoService pfpCustomerInfoService;
	private ISequenceService sequenceService;
	private IPfpAdActionService pfpAdActionService;
	private IPfdUserAdAccountRefService pfdUserAdAccountRefService;
	private IPfpAdSpecificWebsiteService pfpAdSpecificWebsiteService;
	private IPfbxWebsiteCategoryService pfbxWebsiteCategoryService;
	private IPfpCatalogLogoService pfpCatalogLogoService;
	private IPfpCatalogService pfpCatalogService;
	private IPfpCodeTrackingService pfpCodeTrackingService;
	private IPfpCodeConvertService pfpCodeConvertService;
	private IPfpCodeAdActionMergeService pfpCodeAdActionMergeService;
	private String hasActionRecord;
	
	//key:0  搜尋廣告+聯播網廣告(觸及廣告族群最廣泛),1 搜尋廣告(PChome找東西搜尋和搜尋夥伴),2 聯播網廣告(PChome的合作網站聯播網)
	private Map<String,String> adTypeMap;
	private Map<String,Integer> adStyleTypeMap;
	
	private String adAllDevice;
	private String adSearchDevice;
	private String adChannelDevice;
	
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
	
	private String adSpecificPlayType;
	private String adPvLimitStyle;
	private String adPvLimitPeriod;
	private String adPvLimitAmount;
	private String pvLimitSelect;
	private String[] websiteAddCategory;
	private String oldWebsiteCategory;
	//廣告形式
	private int defaultAdType;
	//廣告樣式
	private String defaultAdOperatingRule;
	//廣告播放裝置
	private int defaultAdDevice;
	
	private int defaultAdGroupSearchPriceType;
	private int defaultAdGroupSearchPrice;
	private int defaultAdGroupChannelPrice;
	
	private List<PfpCodeTracking> pfpCodeTrackingList;
	private JSONObject pfpCodeTrackingJson = new JSONObject();
	
	private List<PfpCodeConvert> pfpCodeConvertList;
	private JSONObject pfpCodeConvertJson = new JSONObject();
	
	private List<PfpAdAction> pfpAdActionList;
	private List<PfpAdGroup> pfpAdGroupList;
	
	private Map<String,String> adPvLimitStyleMap;
	private Map<String,String> adPvLimitPeriodMap;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private SyspriceOperaterAPI syspriceOperaterAPI;
	private String prodAdMsg ="";
	private String prodCodeInfo;
	private Map<String,String> adCountryMap = new LinkedHashMap<String,String>();
	
	public String adActionAdd() throws Exception{
		log.info("adActionAdd => adActionSeq = " + adActionSeq);
		
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
		
		
		
		
		//商品廣告權限檢查START
		if(pfpCatalogService.getPfpCatalogList(super.getCustomer_info_id()).size() == 0){
			prodAdMsg = "商品目錄未上傳";
		}
		List<PfpCatalogLogo> pfpCatalogLogoList = pfpCatalogLogoService.findCatalogLogoByCustomerInfoId(super.getCustomer_info_id());
		if(pfpCatalogLogoList == null){
			prodAdMsg = "商品LOGO未上傳";
		}else{
			for (PfpCatalogLogo pfpCatalogLogo : pfpCatalogLogoList) {
				if(pfpCatalogLogo.getStatus().equals(EnumCatalogLogoStatus.LOGO_STATUS_NOVERIFY.getStatus())){
					prodAdMsg = "LOGO等待審核中";
					break;
				}
			}
		}
		
		
		String referer = request.getHeader("Referer");
		log.info("referer = " + referer);
		backPage = "adActionView.html";
		if(StringUtils.isNotEmpty(referer) && referer.indexOf("adGroupAdd.html") < 0) {
			backPage = referer;
		}
		PfpCustomerInfo pfpCustomerInfo = pfpCustomerInfoService.findCustomerInfo(super.getCustomer_info_id());

		// 設定預設值
		Date date = new Date();
		adActionName = "";
		adActionDesc = "";
		adActionStartDate = sdf.format(date);
		adActionEndDate = "";
		selAdActionEndDate = "N";
		adActionMax = "";
		remain = pfpCustomerInfo.getRemain();
		tmpRemain = (int)remain;
		adSpecificPlayType = "0";
		adPvLimitStyle = "0";
		adPvLimitPeriod = "0";
		adPvLimitAmount = "5";
		pvLimitSelect = "N";
		oldWebsiteCategory = "";
		adOperatingRule = "0";
		
		
		if(StringUtils.isNotBlank(adActionSeq)){
			PfpAdAction pfpAdAction = pfpAdActionService.get(adActionSeq);
			adOperatingRule = pfpAdAction.getAdOperatingRule().equals("VIDEO") ? "1" : "0"; 
		}
		
		PfdUserAdAccountRef pfdUserAdAccountRef = pfdUserAdAccountRefService.findPfdUserAdAccountRef(super.getCustomer_info_id());
		String pfpAdTypeSelect = pfdUserAdAccountRef.getPfdCustomerInfo().getPfpAdtypeSelect();
		
		//廣告播放類型下拉選項
		String array[] = {pfpAdTypeSelect.substring(8,9),pfpAdTypeSelect.substring(0,1),pfpAdTypeSelect.substring(4,5)};
		int number = 0;
		adTypeMap = new LinkedHashMap<String,String>();
		for(EnumAdType enumAdType: EnumAdType.values()){
			if("1".equals(array[number])){
				adTypeMap.put(String.valueOf(enumAdType.getType()), enumAdType.getTypeName() + "(" + enumAdType.getExplanation() + ")");
			}
			number++;
		}
		//廣告樣式下拉選單
		adStyleTypeMap = new LinkedHashMap<String,Integer>();
		for(EnumAdStyleType enumAdStyleType: EnumAdStyleType.values()){
			adStyleTypeMap.put(enumAdStyleType.getType(), enumAdStyleType.getValue());
		}
		
		//廣告播放裝置下拉選項
		String allDeviceArray[] = {pfpAdTypeSelect.substring(1,2),pfpAdTypeSelect.substring(2,3),pfpAdTypeSelect.substring(3,4)};
		String searchDeviceArray[] = {pfpAdTypeSelect.substring(5,6),pfpAdTypeSelect.substring(6,7),pfpAdTypeSelect.substring(7,8)};
		String channelDeviceArray[] = {pfpAdTypeSelect.substring(9,10),pfpAdTypeSelect.substring(10,11),pfpAdTypeSelect.substring(11)};
		int deviceNumber = 0;
		Map<String,String> adAllDeviceMap = new LinkedHashMap<String,String>();
		Map<String,String> adSearchDeviceMap = new LinkedHashMap<String,String>();
		Map<String,String> adChannelDeviceMap = new LinkedHashMap<String,String>();
		for(EnumAdDevice enumAdDevice:EnumAdDevice.values()){
			if("1".equals(allDeviceArray[deviceNumber])){
				adAllDeviceMap.put(String.valueOf(enumAdDevice.getDevType()), enumAdDevice.getDevTypeName());
			}
			if("1".equals(searchDeviceArray[deviceNumber])){
				adSearchDeviceMap.put(String.valueOf(enumAdDevice.getDevType()), enumAdDevice.getDevTypeName());
			}
			if("1".equals(channelDeviceArray[deviceNumber])){
				adChannelDeviceMap.put(String.valueOf(enumAdDevice.getDevType()), enumAdDevice.getDevTypeName());
			}
			deviceNumber++;
		}
		
		for(EnumAdCountry enumAdCountry:EnumAdCountry.values()){
			adCountryMap.put(enumAdCountry.getCountryType(), enumAdCountry.getCountryName());
		}
		
		JSONObject jsonObj1 = new JSONObject(adAllDeviceMap);
		JSONObject jsonObj2 = new JSONObject(adSearchDeviceMap);
		JSONObject jsonObj3 = new JSONObject(adChannelDeviceMap);
		adAllDevice = jsonObj1.toString(); 
		adSearchDevice = jsonObj2.toString(); 
		adChannelDevice = jsonObj3.toString(); 

		//設定年齡下拉選單
		adActionStartAgeMap = new LinkedHashMap<String,String>();
		adActionEndAgeMap = new LinkedHashMap<String,String>();
		adPvLimitStyleMap = new LinkedHashMap<String,String>();
		adPvLimitPeriodMap = new LinkedHashMap<String,String>();
		adActionStartAgeMap.put("0", "18歲以下");
		getAgeMap();
		adActionEndAgeMap.put("99", "75歲以上");
		ageType = "A";
		timeType = "A";
		adActionStartAge = "0";
		adActionEndAge = "99";
		adActionSex = "";
		openDetail = "N";
		
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
		
		//設定播放時間初始化
		String mon = " 1111111111111111111111111";
		String tue = " 1111111111111111111111111";
		String wed = " 1111111111111111111111111";
		String thu = " 1111111111111111111111111";
		String fri = " 1111111111111111111111111";
		String sat = " 1111111111111111111111111";
		String sun = " 1111111111111111111111111";
		
		
		// 廣告分類點選取消回來活動頁的時候，會帶adActionSeq
		if(adActionSeq != null && StringUtils.isNotEmpty(adActionSeq)) {
			PfpAdAction pfpAdAction = pfpAdActionService.getPfpAdActionBySeq(adActionSeq);
			String customerInfoId = pfpCustomerInfo.getCustomerInfoId();
			if(!customerInfoId.equals(pfpAdAction.getPfpCustomerInfo().getCustomerInfoId())) {
				return "notOwner";
			}

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
			adActionMax = Integer.toString((int)pfpAdAction.getAdActionMax());
			adType = Integer.toString(pfpAdAction.getAdType());
			adDevice = Integer.toString(pfpAdAction.getAdDevice());
			tmpRemain = (int)pfpCustomerInfo.getRemain();
			adActionStartAge = String.valueOf(pfpAdAction.getAdActionStartAge());
			adActionEndAge = String.valueOf(pfpAdAction.getAdActionEndAge());
			if(!StringUtils.equals("0", adActionStartAge) || !StringUtils.equals("99", adActionEndAge)){
				ageType = "S";
			}
			
			if(StringUtils.isNotEmpty(pfpAdAction.getAdActionSex())){
				adActionSex = pfpAdAction.getAdActionSex();
			}
			
			//將時間數字轉換二進位字串
			mon = Integer.toBinaryString(pfpAdAction.getAdActionMonTime());
			mon = String.format("%024d",new BigInteger(mon));
			mon = reversionString(mon);
			tue = Integer.toBinaryString(pfpAdAction.getAdActionTueTime());
			tue = String.format("%024d",new BigInteger(tue));
			tue = reversionString(tue);
			wed = Integer.toBinaryString(pfpAdAction.getAdActionWedTime());
			wed = String.format("%024d",new BigInteger(wed));
			wed = reversionString(wed);
			thu = Integer.toBinaryString(pfpAdAction.getAdActionThuTime());
			thu = String.format("%024d",new BigInteger(thu));
			thu = reversionString(thu);
			fri = Integer.toBinaryString(pfpAdAction.getAdActionFriTime());
			fri = String.format("%024d",new BigInteger(fri));
			fri = reversionString(fri);
			sat = Integer.toBinaryString(pfpAdAction.getAdActionSatTime());
			sat = String.format("%024d",new BigInteger(sat));
			sat = reversionString(sat);
			sun = Integer.toBinaryString(pfpAdAction.getAdActionSunTime());
			sun = String.format("%024d",new BigInteger(sun));
			sun = reversionString(sun);
			
			adSpecificPlayType = pfpAdAction.getAdSpecificPlayType();
			adPvLimitStyle = pfpAdAction.getAdPvLimitStyle();
			adPvLimitPeriod = pfpAdAction.getAdPvLimitPeriod();
			adPvLimitAmount = String.valueOf(pfpAdAction.getAdPvLimitAmount());
			
			if(!StringUtils.equals(EnumAdPvLimitStyle.NO_STYLE_LIMIT.getStyle(), pfpAdAction.getAdPvLimitStyle())){
				pvLimitSelect = "Y";
			}
			
			oldWebsiteCategory = "";
			Set<PfpAdSpecificWebsite> fpAdSpecificWebsiteSet = pfpAdAction.getPfpAdSpecificWebsites();
			for(PfpAdSpecificWebsite pfpAdSpecificWebsite:fpAdSpecificWebsiteSet){
				oldWebsiteCategory += pfpAdSpecificWebsite.getPfbxWebsiteCategory().getId() + ",";
			}
			
		} else {
			adActionSeq = "";
			adType = "2";
			adDevice = "0";
		}
		
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
		
		if(StringUtils.equals(timeType, "S") || StringUtils.isNotEmpty(adActionSex) || StringUtils.equals(ageType, "S") || StringUtils.equals(pvLimitSelect, "Y") ||
				StringUtils.isNotEmpty(oldWebsiteCategory)){
			openDetail = "Y";
		}
		
		return SUCCESS;
	}

	public String doAdActionAdd() throws Exception {
		log.info("doAdActionAdd => adActionSeq = " + adActionSeq);
		PfpCustomerInfo pfpCustomerInfo = pfpCustomerInfoService.findCustomerInfo(super.getCustomer_info_id());
		String customerInfoId = pfpCustomerInfo.getCustomerInfoId();

		if(StringUtils.isBlank(adOperatingRule)) {
			message = "廣告類型不可為空！";
			return INPUT;
		}
		
		if(StringUtils.isEmpty(customerInfoId)) {
			message = "請登入！";
			return INPUT;
		}
		
		if(StringUtils.isBlank(adCountry)) {
			message = "請選擇廣告播放國家!";
			return INPUT;
		}
		if(adCountry.equals("NULL")) {
			adCountry = null;
		}
		
		String saveAdCity = null;
		if(adCountry.equals("Taiwan")) {
			if(adCity != null) {
				if(adCity.length == 5) {
					saveAdCity = null;
				}else{
					saveAdCity = "";
					for (int i = 0; i < adCity.length; i++) {
						if(i == (adCity.length - 1)) {
							saveAdCity = saveAdCity + adCity[i];
						}else {
							saveAdCity = saveAdCity + adCity[i] + ",";
						}
					}
				}
			}
		}
		
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
		
		PfpAdAction pfpAdAction = new PfpAdAction();
		if(StringUtils.isNotEmpty(adActionSeq)) {
			pfpAdAction = pfpAdActionService.getPfpAdActionBySeq(adActionSeq);
		} else {
			adActionSeq = sequenceService.getId(EnumSequenceTableName.PFP_AD_ACTION, "_");
			pfpAdAction.setAdActionSeq(adActionSeq);
			pfpAdAction.setAdActionCreatTime(new Date());
		}
		
		pfpAdAction.setAdActionName(adActionName);
		pfpAdAction.setAdActionDesc(adActionName);
		pfpAdAction.setAdActionStartDate(ActionStartDate);
		pfpAdAction.setAdActionEndDate(ActionEndDate);
		pfpAdAction.setAdActionMax(iAdActionMax);
		pfpAdAction.setAdActionControlPrice(iAdActionMax);
		pfpAdAction.setUserId(super.getUser_id());
		pfpAdAction.setPfpCustomerInfo(pfpCustomerInfo);
		pfpAdAction.setAdActionUpdateTime(new Date());
		pfpAdAction.setAdActionCountry(adCountry);
		pfpAdAction.setAdActionCity(saveAdCity);
		
		if(pfpAdActionService.getAdGroupCounts(adActionSeq) <= 0) {
			pfpAdAction.setAdActionStatus(EnumStatus.UnDone.getStatusId());		// 新增廣告時，status 設定為未完成
		}
		for (EnumAdStyleType enumAdStyleType : EnumAdStyleType.values()) {
			if(Integer.parseInt(adOperatingRule) == enumAdStyleType.getValue()){
				pfpAdAction.setAdOperatingRule(enumAdStyleType.getTypeName());
				break;
			}
		}
		pfpAdAction.setAdType(Integer.parseInt(adType));
		pfpAdAction.setAdDevice(Integer.parseInt(adDevice));	
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

		if(StringUtils.equals(pvLimitSelect, "Y")){
			pfpAdAction.setAdPvLimitStyle(adPvLimitStyle);
			pfpAdAction.setAdPvLimitPeriod(adPvLimitPeriod);
			pfpAdAction.setAdPvLimitAmount(Integer.parseInt(adPvLimitAmount));
		} else {
			pfpAdAction.setAdPvLimitStyle("0");
			pfpAdAction.setAdPvLimitPeriod("0");
			pfpAdAction.setAdPvLimitAmount(0);
		}
		
		pfpAdAction.setAdSpecificPlayType(adSpecificPlayType);
		List<PfpAdSpecificWebsite> pfpAdSpecificWebsiteList = pfpAdSpecificWebsiteService.findPfpAdSpecificWebsiteByAdActionSeq(pfpAdAction.getAdActionSeq());
		for(PfpAdSpecificWebsite pfpAdSpecificWebsite:pfpAdSpecificWebsiteList){
			pfpAdSpecificWebsiteService.delete(pfpAdSpecificWebsite);
		}
		
		List<PfpAdSpecificWebsite> addPfpAdSpecificWebsiteList = new ArrayList<PfpAdSpecificWebsite>();
		
		if(StringUtils.equals(adSpecificPlayType, "0")){
			
			if(StringUtils.isNotEmpty(adActionSex)){
				pfpAdAction.setAdActionSex(adActionSex);
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
		
		pfpAdActionService.savePfpAdAction(pfpAdAction);
		
		for(PfpAdSpecificWebsite websiteData:addPfpAdSpecificWebsiteList){
			websiteData.setPfpAdAction(pfpAdAction);
			pfpAdSpecificWebsiteService.saveOrUpdate(websiteData);
		}
		

		//儲存商品廣告代碼對應
		Date date = new Date();
		JSONObject prodCodeInfoJson = new JSONObject(prodCodeInfo);
		Iterator<String> prodCodeInfoJsonkeys = prodCodeInfoJson.keys();
		while (prodCodeInfoJsonkeys.hasNext()) {
			String key = prodCodeInfoJsonkeys.next();
			Object jsonbValue = prodCodeInfoJson.get(key);
			if (key.equals("convert_code") && jsonbValue != null) {
				Iterator<String> trackingCodeJsonkeys = ((JSONObject) jsonbValue).keys();
				while (trackingCodeJsonkeys.hasNext()) {
					String convertJsonKey = trackingCodeJsonkeys.next();
					String convertSeq = (String) ((JSONObject) jsonbValue).get(convertJsonKey);
					PfpCodeAdactionMerge pfpCodeAdActionMerge = new PfpCodeAdactionMerge();
					pfpCodeAdActionMerge.setAdActionSeq(adActionSeq);
					pfpCodeAdActionMerge.setCodeType("C");
					pfpCodeAdActionMerge.setCodeId(convertSeq);
					pfpCodeAdActionMerge.setUpdateDate(date);
					pfpCodeAdActionMerge.setCreateDate(date);
					pfpCodeAdActionMergeService.saveOrUpdate(pfpCodeAdActionMerge);
				}
			} else if (key.equals("tracking_code") && jsonbValue != null) {
				Iterator<String> trackingCodeJsonkeys = ((JSONObject) jsonbValue).keys();
				while (trackingCodeJsonkeys.hasNext()) {
					String trackingJsonKey = trackingCodeJsonkeys.next();
					String trackingSeq = (String) ((JSONObject) jsonbValue).get(trackingJsonKey);
					PfpCodeAdactionMerge pfpCodeAdActionMerge = new PfpCodeAdactionMerge();
					pfpCodeAdActionMerge.setAdActionSeq(adActionSeq);
					pfpCodeAdActionMerge.setCodeType("T");
					pfpCodeAdActionMerge.setCodeId(trackingSeq);
					pfpCodeAdActionMerge.setUpdateDate(date);
					pfpCodeAdActionMerge.setCreateDate(date);
					pfpCodeAdActionMergeService.saveOrUpdate(pfpCodeAdActionMerge);
					
					
				}
			}
		}
		return SUCCESS;
	}

	
	/*
	 * 快速上稿活動起始畫面
	 */
	public String adActionFastPublishUrlView() throws Exception{
		pfpAdActionList = pfpAdActionService.getAdActionByCustomerInfoIdAndMediaAd(super.getCustomer_info_id());
		adActionName = "PCHOME聯播網廣告";
		adGroupName = "PCHOME聯播網廣告";
		customerInfoId = super.getCustomer_info_id();
		
		sysChannelPrice = Integer.toString((int)syspriceOperaterAPI.getAdSuggestPrice(sysPriceAdPoolSeq));
		float adAsideRate = syspriceOperaterAPI.getAdAsideRate(Float.valueOf(sysChannelPrice));
		adAsideRate = adAsideRate == 0 ? 0 : adAsideRate;
		this.adAsideRate = adAsideRate == 0 ? "0" : String.valueOf(adAsideRate);
		this.userCategory = pfpCustomerInfoService.get(super.getCustomer_info_id()).getCategory();
		if(pfpAdActionList.size() > 0){
			PfpAdAction pfpAdAction = pfpAdActionList.get(0);
			defaultAdType = pfpAdAction.getAdType();
			defaultAdOperatingRule = pfpAdAction.getAdOperatingRule();
			defaultAdDevice = pfpAdAction.getAdDevice();
			adActionMax = String.valueOf((int)pfpAdAction.getAdActionMax());
			adActionStartDate = sdf.format(pfpAdAction.getAdActionStartDate());
			adActionEndDate = sdf.format(pfpAdAction.getAdActionEndDate());
			
			List<PfpAdGroup> list = new ArrayList<PfpAdGroup>(pfpAdAction.getPfpAdGroups());
			if(list.size() > 0){
				PfpAdGroup pfpAdGroup = list.get(0);
				System.out.println(pfpAdGroup.getAdGroupName());
				defaultAdGroupSearchPriceType = (int)pfpAdGroup.getAdGroupSearchPriceType();
				defaultAdGroupSearchPrice = (int)pfpAdGroup.getAdGroupSearchPrice();
				defaultAdGroupChannelPrice = (int)pfpAdGroup.getAdGroupChannelPrice();
			}
			pfpAdGroupList = new ArrayList<>();
			pfpAdGroupList.addAll(pfpAdAction.getPfpAdGroups());
			hasActionRecord = "Y";
		}else{
			hasActionRecord = "N";
		}
		return SUCCESS;
	}
	
	private String reversionString(String timeString) {
		String time = "";
		String[] timeArray = timeString.split("");
		for (int i = 0; i < timeArray.length; i++) {
			time = timeArray[i] + time;
		}

		return time;
	}

	private void getAgeMap() {
		for (int i = 18; i <= 75; i++) {
			adActionStartAgeMap.put(String.valueOf(i), i + "歲");
			adActionEndAgeMap.put(String.valueOf(i), i + "歲");
		}
	}
	
	public void setPfpCustomerInfoService(PfpCustomerInfoService pfpCustomerInfoService) {
		this.pfpCustomerInfoService = pfpCustomerInfoService;
	}

	public void setPfpAdActionService(PfpAdActionService pfpAdActionService) {
		this.pfpAdActionService = pfpAdActionService;
	}

	public void setSequenceService(ISequenceService sequenceService) {
		this.sequenceService = sequenceService;
	}
	
	public void setPfdUserAdAccountRefService(IPfdUserAdAccountRefService pfdUserAdAccountRefService) {
		this.pfdUserAdAccountRefService = pfdUserAdAccountRefService;
	}

	public void setPfpAdSpecificWebsiteService(IPfpAdSpecificWebsiteService pfpAdSpecificWebsiteService) {
		this.pfpAdSpecificWebsiteService = pfpAdSpecificWebsiteService;
	}

	public void setPfbxWebsiteCategoryService(IPfbxWebsiteCategoryService pfbxWebsiteCategoryService) {
		this.pfbxWebsiteCategoryService = pfbxWebsiteCategoryService;
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

	public void setRemain(float remain) {
		this.remain = remain;
	}

	public float getRemain() {
		return remain;
	}

	public int getTmpRemain() {
		return tmpRemain;
	}

	public void setBackPage(String backPage) {
		this.backPage = backPage;
	}

	public String getBackPage() {
		return backPage;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public Map<String, String> getAdTypeMap() {
		return adTypeMap;
	}

	public String getAdAllDevice() {
		return adAllDevice;
	}

	public String getAdSearchDevice() {
		return adSearchDevice;
	}

	public String getAdChannelDevice() {
		return adChannelDevice;
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

	public Map<String, String> getAdPvLimitStyleMap() {
		return adPvLimitStyleMap;
	}

	public Map<String, String> getAdPvLimitPeriodMap() {
		return adPvLimitPeriodMap;
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

	public Map<String, Integer> getAdStyleTypeMap() {
		return adStyleTypeMap;
	}

	public String getAdOperatingRule() {
		return adOperatingRule;
	}

	public void setAdOperatingRule(String adOperatingRule) {
		this.adOperatingRule = adOperatingRule;
	}

	public String getAdGroupName() {
		return adGroupName;
	}

	public void setAdGroupName(String adGroupName) {
		this.adGroupName = adGroupName;
	}

	public List<PfpAdAction> getPfpAdActionList() {
		return pfpAdActionList;
	}

	public void setPfpAdActionList(List<PfpAdAction> pfpAdActionList) {
		this.pfpAdActionList = pfpAdActionList;
	}

	public String getCustomerInfoId() {
		return customerInfoId;
	}

	public void setCustomerInfoId(String customerInfoId) {
		this.customerInfoId = customerInfoId;
	}

	public int getDefaultAdType() {
		return defaultAdType;
	}

	public void setDefaultAdType(int defaultAdType) {
		this.defaultAdType = defaultAdType;
	}

	public String getDefaultAdOperatingRule() {
		return defaultAdOperatingRule;
	}

	public void setDefaultAdOperatingRule(String defaultAdOperatingRule) {
		this.defaultAdOperatingRule = defaultAdOperatingRule;
	}

	public int getDefaultAdDevice() {
		return defaultAdDevice;
	}

	public void setDefaultAdDevice(int defaultAdDevice) {
		this.defaultAdDevice = defaultAdDevice;
	}

	public List<PfpAdGroup> getPfpAdGroupList() {
		return pfpAdGroupList;
	}

	public void setPfpAdGroupList(List<PfpAdGroup> pfpAdGroupList) {
		this.pfpAdGroupList = pfpAdGroupList;
	}

	public String getSysChannelPrice() {
		return sysChannelPrice;
	}

	public void setSysChannelPrice(String sysChannelPrice) {
		this.sysChannelPrice = sysChannelPrice;
	}

	public SyspriceOperaterAPI getSyspriceOperaterAPI() {
		return syspriceOperaterAPI;
	}

	public void setSyspriceOperaterAPI(SyspriceOperaterAPI syspriceOperaterAPI) {
		this.syspriceOperaterAPI = syspriceOperaterAPI;
	}

	public String getSysPriceAdPoolSeq() {
		return sysPriceAdPoolSeq;
	}

	public void setSysPriceAdPoolSeq(String sysPriceAdPoolSeq) {
		this.sysPriceAdPoolSeq = sysPriceAdPoolSeq;
	}

	public String getAdAsideRate() {
		return adAsideRate;
	}

	public void setAdAsideRate(String adAsideRate) {
		this.adAsideRate = adAsideRate;
	}

	public String getHasActionRecord() {
		return hasActionRecord;
	}

	public void setHasActionRecord(String hasActionRecord) {
		this.hasActionRecord = hasActionRecord;
	}

	public String getUserCategory() {
		return userCategory;
	}

	public void setUserCategory(String userCategory) {
		this.userCategory = userCategory;
	}

	public int getDefaultAdGroupSearchPriceType() {
		return defaultAdGroupSearchPriceType;
	}

	public void setDefaultAdGroupSearchPriceType(int defaultAdGroupSearchPriceType) {
		this.defaultAdGroupSearchPriceType = defaultAdGroupSearchPriceType;
	}

	public int getDefaultAdGroupSearchPrice() {
		return defaultAdGroupSearchPrice;
	}

	public void setDefaultAdGroupSearchPrice(int defaultAdGroupSearchPrice) {
		this.defaultAdGroupSearchPrice = defaultAdGroupSearchPrice;
	}

	public int getDefaultAdGroupChannelPrice() {
		return defaultAdGroupChannelPrice;
	}

	public void setDefaultAdGroupChannelPrice(int defaultAdGroupChannelPrice) {
		this.defaultAdGroupChannelPrice = defaultAdGroupChannelPrice;
	}

	public IPfpCatalogLogoService getPfpCatalogLogoService() {
		return pfpCatalogLogoService;
	}

	public void setPfpCatalogLogoService(IPfpCatalogLogoService pfpCatalogLogoService) {
		this.pfpCatalogLogoService = pfpCatalogLogoService;
	}

	public IPfpCatalogService getPfpCatalogService() {
		return pfpCatalogService;
	}

	public void setPfpCatalogService(IPfpCatalogService pfpCatalogService) {
		this.pfpCatalogService = pfpCatalogService;
	}

	public String getProdAdMsg() {
		return prodAdMsg;
	}

	public void setProdAdMsg(String prodAdMsg) {
		this.prodAdMsg = prodAdMsg;
	}

	public IPfpCodeTrackingService getPfpCodeTrackingService() {
		return pfpCodeTrackingService;
	}

	public void setPfpCodeTrackingService(IPfpCodeTrackingService pfpCodeTrackingService) {
		this.pfpCodeTrackingService = pfpCodeTrackingService;
	}

	public List<PfpCodeTracking> getPfpCodeTrackingList() {
		return pfpCodeTrackingList;
	}

	public void setPfpCodeTrackingList(List<PfpCodeTracking> pfpCodeTrackingList) {
		this.pfpCodeTrackingList = pfpCodeTrackingList;
	}

	public IPfpCodeConvertService getPfpCodeConvertService() {
		return pfpCodeConvertService;
	}

	public void setPfpCodeConvertService(IPfpCodeConvertService pfpCodeConvertService) {
		this.pfpCodeConvertService = pfpCodeConvertService;
	}

	public List<PfpCodeConvert> getPfpCodeConvertList() {
		return pfpCodeConvertList;
	}

	public void setPfpCodeConvertList(List<PfpCodeConvert> pfpCodeConvertList) {
		this.pfpCodeConvertList = pfpCodeConvertList;
	}

	public String getProdCodeInfo() {
		return prodCodeInfo;
	}

	public void setProdCodeInfo(String prodCodeInfo) {
		this.prodCodeInfo = prodCodeInfo;
	}

	public IPfpCodeAdActionMergeService getPfpCodeAdActionMergeService() {
		return pfpCodeAdActionMergeService;
	}

	public void setPfpCodeAdActionMergeService(IPfpCodeAdActionMergeService pfpCodeAdActionMergeService) {
		this.pfpCodeAdActionMergeService = pfpCodeAdActionMergeService;
	}

	public Map<String, String> getAdCountryMap() {
		return adCountryMap;
	}

	public void setAdCountryMap(Map<String, String> adCountryMap) {
		this.adCountryMap = adCountryMap;
	}

	public String getAdCountry() {
		return adCountry;
	}

	public void setAdCountry(String adCountry) {
		this.adCountry = adCountry;
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

	public String[] getAdCity() {
		return adCity;
	}

	public void setAdCity(String[] adCity) {
		this.adCity = adCity;
	}




}
