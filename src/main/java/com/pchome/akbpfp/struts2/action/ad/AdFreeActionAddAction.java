package com.pchome.akbpfp.struts2.action.ad;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.apache.commons.lang.StringUtils;

import com.pchome.akbpfp.db.pojo.PfpAdAction;
import com.pchome.akbpfp.db.service.ad.PfpAdActionService;
import com.pchome.akbpfp.db.service.customerInfo.PfpCustomerInfoService;
import com.pchome.akbpfp.db.service.sequence.ISequenceService;
import com.pchome.akbpfp.db.vo.ad.AdFreeVO;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.cookie.EnumCookieConstants;
import com.pchome.enumerate.sequence.EnumSequenceTableName;
import com.pchome.enumerate.utils.EnumStatus;
import com.pchome.soft.depot.utils.CookieUtil;
import com.pchome.soft.depot.utils.EncodeUtil;


public class AdFreeActionAddAction extends BaseCookieAction{

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
	private float remain;
	private int tmpRemain;
	private String backPage;
	private String classifiedsServer;
	private List<PfpAdAction> pfpAdActions;

	// get data
	private String aid;

	private PfpCustomerInfoService pfpCustomerInfoService;
	private ISequenceService sequenceService;
	private PfpAdActionService pfpAdActionService;

	public String adFreeActionAdd() throws Exception{
		log.info("adFreeActionAdd => aid= " + aid);
		String referer = request.getHeader("Referer");
		log.info("referer = " + referer);
		backPage = "adActionView.html";
		if(StringUtils.isNotEmpty(referer) && referer.indexOf("adFreeGroupAdd.html") < 0) {
			backPage = referer;
		}

		// 設定預設值
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		adActionName = "";
		adActionDesc = "";
		adActionStartDate = sdf.format(date);
		adActionEndDate = "";
		selAdActionEndDate = "N";
		adActionMax = "";
		remain = pfpCustomerInfoService.findCustomerInfo(super.getCustomer_info_id()).getRemain();
		tmpRemain = (int)remain;
		
		// 由 adFreeGroupAction 取消回來時，會帶 adActionSeq 的參數
		if(adActionSeq != null && StringUtils.isNotBlank(adActionSeq)) {
			PfpAdAction pfpAdAction = pfpAdActionService.getPfpAdActionBySeq(adActionSeq);
			String customerInfoId = pfpCustomerInfoService.findCustomerInfo(super.getCustomer_info_id()).getCustomerInfoId();
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
			tmpRemain = (int)pfpCustomerInfoService.findCustomerInfo(super.getCustomer_info_id()).getRemain();
			
			// 讀取cookie
			String cookieData = CookieUtil.getCookie(request, "aid", EnumCookieConstants.COOKIE_USING_CODE.getValue());
			aid = EncodeUtil.getInstance().decryptAES(cookieData, EnumCookieConstants.COOKIE_PFP_SECRET_KEY.getValue());
		}
		// 初始呼叫時，不會有 adActionSeq，要經由呼叫 api 取得免費刊登廣告資料
		else {
			// 預設為空值
			adActionSeq = "";

			// 呼叫 api 取得免費刊登廣告資料
			if(aid != null && !aid.trim().equals("")) {
				// 將 aid 設定進 cookie 中
				setCookieAid(aid);
				//PfpCustomerInfo pfpCustomerInfo = pfpCustomerInfoService.findCustomerInfo(super.getCustomer_info_id());
				//String member_id = pfpCustomerInfo.getMemberId();
				String member_id =  super.getId_pchome();
				log.info("memberID = " + member_id);
				
				String key = "ad_no,title,st_date,ed_date";
				Map<String, AdFreeVO> adFrees = adUtils.getAdFree(classifiedsServer, member_id, aid, key);
				AdFreeVO adFreeVO = adFrees.get(aid);

				if(adFreeVO != null && adFreeVO.getStatus() != null && adFreeVO.getStatus().equals("success")) {
					// 廣告名稱
					adActionName = adFreeVO.getTitle();
					// 廣告內容描述
					adActionDesc = adFreeVO.getTitle();
					// 廣告開始時間
					if(adFreeVO.getdSt_date() != null) {
						if(date.before(adFreeVO.getdSt_date())) {
							adActionStartDate = adFreeVO.getSt_date();
						}
					}
					// 廣告結束時間
					if(adFreeVO.getdEd_date() != null) {
						adActionEndDate = adFreeVO.getEd_date();
						selAdActionEndDate = adFreeVO.getHaveEndDate();
					}
					// 每日預算
					adActionMax = "100";
				}
			}
		}

		// 已存在的廣告活動
		pfpAdActions = pfpAdActionService.getAdActionByCustomerInfoId(super.getCustomer_info_id());
		if(pfpAdActions.size() == 0) {
			pfpAdActions = null;
		}
		
		return SUCCESS;
	}

	// 設定 cookie
	private void setCookieAid(String aid) {
		boolean aidExist = false;
		Cookie cookies[] = request.getCookies() ;
		if (cookies != null && cookies.length > 0) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie tmpCookie = cookies[i];
				// 處理 aid 的 cookie 值
				if(tmpCookie.getName().equals("aid") ) {
					log.info("aid's cookieValue = " + tmpCookie.getValue());
					// 解碼後的 cookie 值，預設為傳入的 aid 值
					String decodeData = aid;
					// 進行字串比對
					String regex = "^(2[01]?[0-9][0-9])(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[01])$";
					// 判定 cookie 值是否為日期開頭，若不是，則進行解碼
					if(!tmpCookie.getValue().matches(regex)) {
						decodeData = EncodeUtil.getInstance().decryptAES(tmpCookie.getValue(), EnumCookieConstants.COOKIE_PFP_SECRET_KEY.getValue());
					}
					//log.info("decodeData = " + decodeData);

					// 如果傳入的 aid 值不等於 cookie 中的值，則將 cookie 中的值修改為傳入的 aid 值
					if(!aid.equals(decodeData)) {
						// 將傳入的 aid 值進行編碼
						String encodeCookie = EncodeUtil.getInstance().encryptAES(aid, EnumCookieConstants.COOKIE_PFP_SECRET_KEY.getValue());
						// 將編碼過後的 aid 值設定進 cookie 中
						tmpCookie.setValue(encodeCookie);
						response.addCookie(tmpCookie);
					}
					aidExist = true;
				}
			}
		}

		// 如果 cookie 中不存在 aid，則將 aid 設定進 cookie 中
		if(!aidExist) {
			String encodeCookie = EncodeUtil.getInstance().encryptAES(aid, EnumCookieConstants.COOKIE_PFP_SECRET_KEY.getValue());
			Cookie addCookieAid = new Cookie("aid", encodeCookie);
			response.addCookie(addCookieAid);
		}
	}

	public String doFreeAdActionAdd() throws Exception {
		log.info("doAdActionAdd => adActionSeq = " + adActionSeq);
		String customerInfoId = pfpCustomerInfoService.findCustomerInfo(super.getCustomer_info_id()).getCustomerInfoId();
		log.info("customerInfoId  = " + customerInfoId);
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

		PfpAdAction pfpAdAction = new PfpAdAction();
		if(StringUtils.isNotEmpty(adActionSeq)) {
			pfpAdAction = pfpAdActionService.getPfpAdActionBySeq(adActionSeq);
		} else {
			adActionSeq = sequenceService.getId(EnumSequenceTableName.PFP_AD_ACTION, "_");
			pfpAdAction.setAdActionSeq(adActionSeq);
			pfpAdAction.setAdActionCreatTime(new Date());
		}
		pfpAdAction.setAdActionName(adActionName);
		pfpAdAction.setAdActionDesc(adActionDesc);
		pfpAdAction.setAdActionStartDate(ActionStartDate);
		pfpAdAction.setAdActionEndDate(ActionEndDate);
		pfpAdAction.setAdActionMax(iAdActionMax);
		pfpAdAction.setAdActionControlPrice(iAdActionMax);
		pfpAdAction.setUserId(super.getUser_id());
		pfpAdAction.setPfpCustomerInfo(pfpCustomerInfoService.findCustomerInfo(super.getCustomer_info_id()));
		pfpAdAction.setAdActionUpdateTime(new Date());
		pfpAdAction.setAid(aid);
		if(pfpAdActionService.getAdGroupCounts(adActionSeq) <= 0) {
			pfpAdAction.setAdActionStatus(EnumStatus.UnDone.getStatusId());		// 新增廣告時，status 設定為未完成
		}

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

	public float getRemain() {
		return remain;
	}

	public int getTmpRemain() {
		return tmpRemain;
	}

	public String getBackPage() {
		return backPage;
	}

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public void setClassifiedsServer(String classifiedsServer) {
		this.classifiedsServer = classifiedsServer;
	}

	public List<PfpAdAction> getPfpAdActions() {
		return pfpAdActions;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
