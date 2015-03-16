package com.pchome.akbpfp.struts2.action.ad;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

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
	private int tmpRemain;
	private String backPage;
	private String addFlag = "y"; //是否顯示新增廣告子選單

	private PfpCustomerInfoService pfpCustomerInfoService;
	private PfpAdActionService pfpAdActionService;
	private AdmAccesslogService admAccesslogService;
	private ControlPriceAPI controlPriceAPI;

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
			if (pfdCustomerInfo.getMixFlag().equals("y")) { //綜合經銷商
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
		adActionMax = Integer.toString((int)pfpAdAction.getAdActionMax());
		tmpRemain = (int)pfpCustomerInfo.getRemain();
		
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

		if (StringUtils.isEmpty(adActionDesc)) {
			message = "請輸入廣告內容簡述！";
			return INPUT;
		} else {
			adActionDesc = adActionDesc.trim();
			if (adActionDesc.length() > 50) {
				message = "廣告內容簡述不可超過 50 字！";
				return INPUT;
			}
		}

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
		pfpAdAction.setAdActionDesc(adActionDesc);
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
		
		pfpAdAction.setUserId(super.getUser_id());
		pfpAdAction.setPfpCustomerInfo(pfpCustomerInfo);
		pfpAdAction.setAdActionUpdateTime(new Date());

		pfpAdActionService.savePfpAdAction(pfpAdAction);
		return SUCCESS;
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
}
