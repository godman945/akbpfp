package com.pchome.akbpfp.struts2.action.ad;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.pchome.akbpfp.db.pojo.PfpAdAction;
import com.pchome.akbpfp.db.pojo.PfpCustomerInfo;
import com.pchome.akbpfp.db.service.ad.PfpAdActionService;
import com.pchome.akbpfp.db.service.customerInfo.PfpCustomerInfoService;
import com.pchome.akbpfp.db.service.sequence.ISequenceService;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.ad.EnumAdDevice;
import com.pchome.enumerate.ad.EnumAdType;
import com.pchome.enumerate.sequence.EnumSequenceTableName;
import com.pchome.enumerate.utils.EnumStatus;


public class AdActionAddAction extends BaseCookieAction{

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
	private float remain;
	private int tmpRemain;
	private String backPage;

	private PfpCustomerInfoService pfpCustomerInfoService;
	private ISequenceService sequenceService;
	private PfpAdActionService pfpAdActionService;
	
	private EnumAdType[] adTypeList;
	private EnumAdDevice[] adDeviceList;

	public String adActionAdd() throws Exception{
		log.info("adActionAdd => adActionSeq = " + adActionSeq);
		String referer = request.getHeader("Referer");
		log.info("referer = " + referer);
		backPage = "adActionView.html";
		if(StringUtils.isNotEmpty(referer) && referer.indexOf("adGroupAdd.html") < 0) {
			backPage = referer;
		}
		PfpCustomerInfo pfpCustomerInfo = pfpCustomerInfoService.findCustomerInfo(super.getCustomer_info_id());

		// 設定預設值
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		adActionName = "";
		adActionDesc = "";
		adActionStartDate = sdf.format(date);
		adActionEndDate = "";
		selAdActionEndDate = "N";
		adActionMax = "";
		remain = pfpCustomerInfo.getRemain();
		tmpRemain = (int)remain;
		adTypeList = EnumAdType.values();
		adDeviceList = EnumAdDevice.values();

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
		} else {
			adActionSeq = "";
			adType = "0";
			adDevice = "0";
		}
		
		return SUCCESS;
	}

	public String doAdActionAdd() throws Exception {
		log.info("doAdActionAdd => adActionSeq = " + adActionSeq);
		PfpCustomerInfo pfpCustomerInfo = pfpCustomerInfoService.findCustomerInfo(super.getCustomer_info_id());
		String customerInfoId = pfpCustomerInfo.getCustomerInfoId();

		if(StringUtils.isEmpty(customerInfoId)) {
			message = "請登入！";
			return INPUT;
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
		if(pfpAdActionService.getAdGroupCounts(adActionSeq) <= 0) {
			pfpAdAction.setAdActionStatus(EnumStatus.UnDone.getStatusId());		// 新增廣告時，status 設定為未完成
		}
		pfpAdAction.setAdType(Integer.parseInt(adType));
		pfpAdAction.setAdDevice(Integer.parseInt(adDevice));

		pfpAdActionService.savePfpAdAction(pfpAdAction);
		return SUCCESS;
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

	public EnumAdType[] getAdTypeList() {
		return adTypeList;
	}

	public EnumAdDevice[] getAdDeviceList() {
		return adDeviceList;
	}

}
