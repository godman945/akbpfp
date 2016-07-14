package com.pchome.akbpfp.struts2.action.ad;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.pchome.akbpfp.api.ControlPriceAPI;
import com.pchome.akbpfp.db.pojo.PfdContract;
import com.pchome.akbpfp.db.pojo.PfdCustomerInfo;
import com.pchome.akbpfp.db.pojo.PfdUserAdAccountRef;
import com.pchome.akbpfp.db.pojo.PfpAdAction;
import com.pchome.akbpfp.db.pojo.PfpCustomerInfo;
import com.pchome.akbpfp.db.service.accesslog.AdmAccesslogService;
import com.pchome.akbpfp.db.service.ad.PfpAdActionService;
import com.pchome.akbpfp.db.service.customerInfo.PfpCustomerInfoService;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.ad.EnumAdDevice;
import com.pchome.enumerate.ad.EnumAdType;
import com.pchome.enumerate.pfd.EnumContractStatus;
import com.pchome.enumerate.utils.EnumStatus;
import com.pchome.rmi.accesslog.EnumAccesslogAction;


public class AdActionEditAction extends BaseCookieAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
		adActionStartAgeMap.put("0", "18歲以下");
		getAgeMap();
		adActionEndAgeMap.put("99", "75歲以上");
		
		ageType = "A";
		timeType = "A";
		
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
		if(!StringUtils.equals("0", adActionStartAge) && !StringUtils.equals("99", adActionEndAge)){
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
		tue = Integer.toBinaryString(pfpAdAction.getAdActionTueTime());
		tue = String.format("%024d",new BigInteger(tue));
		wed = Integer.toBinaryString(pfpAdAction.getAdActionWedTime());
		wed = String.format("%024d",new BigInteger(wed));
		thu = Integer.toBinaryString(pfpAdAction.getAdActionThuTime());
		thu = String.format("%024d",new BigInteger(thu));
		fri = Integer.toBinaryString(pfpAdAction.getAdActionFriTime());
		fri = String.format("%024d",new BigInteger(fri));
		sat = Integer.toBinaryString(pfpAdAction.getAdActionSatTime());
		sat = String.format("%024d",new BigInteger(sat));
		sun = Integer.toBinaryString(pfpAdAction.getAdActionSunTime());
		sun = String.format("%024d",new BigInteger(sun));
		
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
		
		// 確認每日預算不一樣才更新
		if(iAdActionMax != pfpAdAction.getAdActionMax()){
			String accesslogAction_Money = EnumAccesslogAction.AD_MONEY_MODIFY.getMessage();
			String accesslogMessage_Money = accesslogAction_Money + ":" + adActionSeq + ", 活動名稱:" + adActionName + ", 每日預算:" + pfpAdAction.getAdActionMax() + " => " + iAdActionMax;
			admAccesslogService.recordAdLog(EnumAccesslogAction.AD_MONEY_MODIFY, accesslogMessage_Money, super.getId_pchome(), super.getCustomer_info_id(), super.getUser_id(), request.getRemoteAddr());
			pfpAdAction.setAdActionMax(iAdActionMax);
			pfpAdAction.setAdActionControlPrice(iAdActionMax);

			if(pfpAdAction.getAdActionStatus() == EnumStatus.Open.getStatusId()) {
				// 重算調控金額
				controlPriceAPI.countProcess(pfpAdAction.getPfpCustomerInfo());
			}
		}
		
		if(StringUtils.isNotEmpty(adActionSex)){
			pfpAdAction.setAdActionSex(adActionSex);
		}
		pfpAdAction.setAdActionStartAge(Integer.parseInt(adActionStartAge));
		pfpAdAction.setAdActionEndAge(Integer.parseInt(adActionEndAge));
		
		String mon = timeCode.substring(0,24);
		String tue = timeCode.substring(24,48);
		String wed = timeCode.substring(48,72);
		String thu = timeCode.substring(72,96);
		String fri = timeCode.substring(96,120);
		String sat = timeCode.substring(120,144);
		String sun = timeCode.substring(144);
		pfpAdAction.setAdActionMonTime(Integer.parseInt(mon, 2));
		pfpAdAction.setAdActionTueTime(Integer.parseInt(tue, 2));
		pfpAdAction.setAdActionWedTime(Integer.parseInt(wed, 2));
		pfpAdAction.setAdActionThuTime(Integer.parseInt(thu, 2));
		pfpAdAction.setAdActionFriTime(Integer.parseInt(fri, 2));
		pfpAdAction.setAdActionSatTime(Integer.parseInt(sat, 2));
		pfpAdAction.setAdActionSunTime(Integer.parseInt(sun, 2));
		
		pfpAdAction.setUserId(super.getUser_id());
		pfpAdAction.setPfpCustomerInfo(pfpCustomerInfo);
		pfpAdAction.setAdActionUpdateTime(new Date());

		pfpAdActionService.savePfpAdAction(pfpAdAction);
		return SUCCESS;
	}

	private void getAgeMap(){
		for(int i=18;i<=75;i++){
			adActionStartAgeMap.put(String.valueOf(i),i + "歲");
			adActionEndAgeMap.put(String.valueOf(i),i + "歲");
		}
	}
	
	public void setPfpCustomerInfoService(
			PfpCustomerInfoService pfpCustomerInfoService) {
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
}
