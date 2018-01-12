package com.pchome.akbpfp.struts2.action.ad;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.pchome.akbpfp.api.SyspriceOperaterAPI;
import com.pchome.akbpfp.db.pojo.PfpAdAction;
import com.pchome.akbpfp.db.pojo.PfpAdGroup;
import com.pchome.akbpfp.db.pojo.PfpAdSysprice;
import com.pchome.akbpfp.db.service.ad.PfpAdActionService;
import com.pchome.akbpfp.db.service.ad.PfpAdGroupService;
import com.pchome.akbpfp.db.service.customerInfo.PfpCustomerInfoService;
import com.pchome.akbpfp.db.service.sequence.ISequenceService;
import com.pchome.akbpfp.db.service.sysprice.IPfpAdSyspriceService;
import com.pchome.akbpfp.db.vo.ad.PfpAdPriceTypeVO;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.ad.EnumAdPriceType;
import com.pchome.enumerate.ad.EnumAdStyleType;
import com.pchome.enumerate.ad.EnumAdType;
import com.pchome.enumerate.cookie.EnumCookieConstants;
import com.pchome.enumerate.sequence.EnumSequenceTableName;
import com.pchome.enumerate.utils.EnumStatus;
import com.pchome.soft.depot.utils.CookieUtil;
import com.pchome.soft.depot.utils.EncodeUtil;

public class AdGroupAddAction extends BaseCookieAction{

	private static final long serialVersionUID = 1L;

	private String message = "";
	private String adActionSeq;
	private String adActionName;
	private String adOperatingRule;
	private String adPrice;
	private String adPriceType;
	
	private int adActionMax;	//每日預算

	private String adGroupSeq;
	private String adGroupName;
	private String defSearchPrice = "3";	//搜尋廣告系統預設出價
	private String adGroupSearchPrice;		//搜尋廣告出價
	private String adGroupSearchPriceType;	//搜尋廣告出價類別。1:系統建議、2:系統預設
	private String sysChannelPrice = "3";	//內容廣告系統建議出價
	private String adGroupChannelPrice;		//內容廣告出價
	private String AdAsideRate;				//播放率
	private String backPage;				// 取消的返回頁面
	private String showSearchPrice;			//顯示搜尋廣告設定
	private String showChannelPrice;		//顯示內容廣告設定
	LinkedList<PfpAdPriceTypeVO> pfpAdPriceTypeVOList; 
	
	private String sysPriceAdPoolSeq;       //廣告建議價取得 pool from api prop 注入
	private PfpCustomerInfoService pfpCustomerInfoService;
	private ISequenceService sequenceService;
	private PfpAdActionService pfpAdActionService;
	private PfpAdGroupService pfpAdGroupService;
	private SyspriceOperaterAPI syspriceOperaterAPI;
	private IPfpAdSyspriceService pfpAdSyspriceService;
	private float sysprice;
	private int adUserAmount;
	private int adPriceTypeValue;
	
	public String adGroupAdd() throws Exception {
		log.info("AdGroupAdd => adActionSeq = " + adActionSeq + "; adGroupSeq = " + adGroupSeq);
//		String adCustomerInfoId = "";
		String referer = request.getHeader("Referer");
//		PfpCustomerInfo pfpCustomerInfo = pfpCustomerInfoService.findCustomerInfo(super.getCustomer_info_id());
		String tmpSeq = "";		// 回  adActionAdd.html 讀取資料用的 adActionSeq
		adGroupSearchPriceType = "2";
		showSearchPrice = "yes";
		showChannelPrice = "yes";
//		float adGroupChannelPriceDefault = 0;
		
		// 由 adAddAction 取消回來時，會帶 adGroupSeq 的參數，但是不會帶 adActionSeq
		if(StringUtils.isNotBlank(adGroupSeq)) {
			PfpAdGroup pfpAdGroup = pfpAdGroupService.getPfpAdGroupBySeq(adGroupSeq);
//			adGroupChannelPriceDefault = pfpAdGroup.getAdGroupChannelPrice();
			adGroupName = pfpAdGroup.getAdGroupName();
			//多媒體廣告
			if(pfpAdGroup.getAdGroupPriceType().equals("CPC")){
				adGroupSearchPrice = Integer.toString((int)pfpAdGroup.getAdGroupSearchPrice());
				adGroupChannelPrice = Integer.toString((int)pfpAdGroup.getAdGroupChannelPrice());
				adGroupSearchPriceType = Integer.toString((int)pfpAdGroup.getAdGroupSearchPriceType());
			}else{
				adGroupSearchPrice = String.valueOf(pfpAdGroup.getAdGroupSearchPrice());
				adGroupChannelPrice = String.valueOf(pfpAdGroup.getAdGroupChannelPrice());
				adGroupSearchPriceType = String.valueOf(pfpAdGroup.getAdGroupSearchPriceType());
				adOperatingRule = pfpAdGroup.getAdGroupPriceType().equals("CPC") ? "MEDIA" : "VIDEO";
				adPriceType = pfpAdGroup.getAdGroupPriceType();
				for(EnumAdPriceType enumAdPriceType: EnumAdPriceType.values()){
					if(adPriceType.equals(enumAdPriceType.getDbTypeName())){
						adPriceTypeValue = enumAdPriceType.getType();
						break;
					}
				}
			}
			adActionSeq = pfpAdGroup.getPfpAdAction().getAdActionSeq();
			adActionName  = pfpAdGroup.getPfpAdAction().getAdActionName();
			adActionMax = (int)pfpAdGroup.getPfpAdAction().getAdActionMax();
			tmpSeq = pfpAdGroup.getPfpAdAction().getAdActionSeq();
			defSearchPrice = adGroupSearchPrice;
			sysChannelPrice = Integer.toString((int)syspriceOperaterAPI.getAdSuggestPrice(sysPriceAdPoolSeq));
			
			PfpAdAction pfpAdAction = pfpAdGroup.getPfpAdAction();
			if(EnumAdType.AD_CHANNEL.getType() == pfpAdAction.getAdType()){			//隱藏搜尋廣告設定
				showSearchPrice = "no";
			} else if(EnumAdType.AD_SEARCH.getType() == pfpAdAction.getAdType()){	//隱藏內容廣告設定
				showChannelPrice = "no";
			}
			
		}
		// 由 adActionAdd 下一步時，會帶 adActionSeq 的參數，但是不會帶 adGroupSeq
		else if(StringUtils.isNotBlank(adActionSeq)) {
			adGroupSeq = "";
			tmpSeq = adActionSeq;
			PfpAdAction pfpAdAction = pfpAdActionService.getPfpAdActionBySeq(adActionSeq);
			adGroupName = chkAdGroupName(pfpAdAction.getAdActionName());
			if(pfpAdAction.getAdOperatingRule().equals(EnumAdStyleType.AD_STYLE_MULTIMEDIA.getTypeName())){
//				adActionName  = pfpAdAction.getAdActionName();
				adActionMax = (int)pfpAdAction.getAdActionMax();
//				adCustomerInfoId = pfpAdAction.getPfpCustomerInfo().getCustomerInfoId();
				adOperatingRule = pfpAdAction.getAdOperatingRule();
				adGroupSearchPrice = defSearchPrice;
				sysChannelPrice = Integer.toString((int)syspriceOperaterAPI.getAdSuggestPrice(sysPriceAdPoolSeq));
				adGroupChannelPrice = "3";
			}
			if(pfpAdAction.getAdOperatingRule().equals(EnumAdStyleType.AD_STYLE_VIDEO.getTypeName())){
				adOperatingRule = pfpAdAction.getAdOperatingRule();
			}
			if(EnumAdType.AD_CHANNEL.getType() == pfpAdAction.getAdType()){			//隱藏搜尋廣告設定
				showSearchPrice = "no";
			} else if(EnumAdType.AD_SEARCH.getType() == pfpAdAction.getAdType()){	//隱藏內容廣告設定
				showChannelPrice = "no";
			}
		}
//		String customerInfoId = pfpCustomerInfo.getCustomerInfoId();
//		System.out.println(customerInfoId);
//		System.out.println(pfpCustomerInfo.getCustomerInfoId());
//		if(!customerInfoId.equals(adCustomerInfoId)) {
//			return "notOwner";
//		}

		// 讀取cookie
		if(referer.indexOf("adActionAdd") > 0 ) {
			backPage="adActionAdd.html?adActionSeq="+tmpSeq;
			String encodeCookie = EncodeUtil.getInstance().encryptAES(backPage, EnumCookieConstants.COOKIE_PFP_SECRET_KEY.getValue());
			CookieUtil.writeCookie(response, "preGroup", encodeCookie, EnumCookieConstants.COOKIE_PCHOME_DOMAIN.getValue(),
									EnumCookieConstants.COOKIE_ONE_HOUR_AGE, EnumCookieConstants.COOKIE_USING_CODE.getValue());
			log.info("write cookie data="+backPage);
		}else if(referer.indexOf("adActionView") > 0 || referer.indexOf("adGroupView") > 0 ) {
			backPage = referer;
            String encodeCookie = EncodeUtil.getInstance().encryptAES(backPage, EnumCookieConstants.COOKIE_PFP_SECRET_KEY.getValue());
			CookieUtil.writeCookie(response, "preGroup", encodeCookie, EnumCookieConstants.COOKIE_PCHOME_DOMAIN.getValue(),	EnumCookieConstants.COOKIE_ONE_HOUR_AGE, EnumCookieConstants.COOKIE_USING_CODE.getValue());
			log.info("write cookie data="+backPage);
			//backPage="adActionAdd.html?adActionSeq="+tmpSeq;
		} else {
			//由廣告上稿頁取消時
			String cookieData = CookieUtil.getCookie(request, "preGroup", EnumCookieConstants.COOKIE_USING_CODE.getValue());
			String decodePreGroup = EncodeUtil.getInstance().decryptAES(cookieData, EnumCookieConstants.COOKIE_PFP_SECRET_KEY.getValue());
			backPage = decodePreGroup;
			log.info("backPage from cookie  = " + backPage);
		}
		
		//影音廣告
		if(StringUtils.isNotBlank(adOperatingRule) && adOperatingRule.equals(EnumAdStyleType.AD_STYLE_VIDEO.getTypeName())){
			pfpAdPriceTypeVOList = new LinkedList<>();
			for(EnumAdPriceType enumAdPriceType: EnumAdPriceType.values()){
				PfpAdPriceTypeVO pfpAdPriceTypeVO = new PfpAdPriceTypeVO();
				pfpAdPriceTypeVO.setType(enumAdPriceType.getType());
				pfpAdPriceTypeVO.setTypeName(enumAdPriceType.getTypeName());
				pfpAdPriceTypeVO.setPrice(enumAdPriceType.getPrice());
				pfpAdPriceTypeVOList.add(pfpAdPriceTypeVO);
			}
			//預設CPV
			/*系統建議出價為各最低出價 + 昨日總家數量*/
			PfpAdSysprice pfpAdSysprice = pfpAdSyspriceService.get(3);
			adUserAmount = (int)pfpAdSysprice.getSysprice();
			sysprice = (float) (0.5 + ((float)adUserAmount / (float)100));
			sysprice = (new BigDecimal(String.valueOf(sysprice)).setScale(1, BigDecimal.ROUND_HALF_UP)).floatValue();
			float userprice = (sysprice * 10) + 10;
			AdAsideRate = String.format("%,3.2f", syspriceOperaterAPI.getAdAsideRate(userprice));
			return "success_video";
		}else{
			AdAsideRate = String.format("%,3.2f", syspriceOperaterAPI.getAdAsideRate(Float.parseFloat(adGroupChannelPrice)));
			return SUCCESS;
		}
	}

	private String chkAdGroupName(String adGroupName) {
		String tmpAdGroupName = adGroupName;
		try {
			if(pfpAdGroupService.chkAdGroupNameByAdActionSeq(tmpAdGroupName, null, adActionSeq)) {
				List<PfpAdGroup> pfpAdGroups = pfpAdGroupService.getPfpAdGroups(null, adActionSeq, null, null, null, null);
				tmpAdGroupName = tmpAdGroupName + "-" + (pfpAdGroups.size() + 1);
			}
		} catch(Exception ex) {
			log.info("Exception(AdGroupAddAction.chkAdGroupName):" + ex);
		}
		return tmpAdGroupName;
	}
	
	@Transactional
	public String doAdGroupAdd() throws Exception {
		log.info("doAdGroupAdd");
		if(super.getCustomer_info_id() == null) {
			message = "請登入帳號！";
			return INPUT;
		}
		
		if (StringUtils.isEmpty(adGroupName)) {
			message = "請輸入分類名稱！";
			return INPUT;
		} else {
			adGroupName = adGroupName.trim();
			if (adGroupName.length() > 20) {
				message = "分類名稱不可超過 20 字！";
				return INPUT;
			}

			if(pfpAdGroupService.chkAdGroupNameByAdActionSeq(adGroupName, adGroupSeq, adActionSeq)) {
				message = "分類名稱已存在";
				return INPUT;
			}
		}

		
		if(StringUtils.isBlank(adOperatingRule)){
			message = "廣告類型不可為空";
			return INPUT;
		}
		
		if(!adOperatingRule.equals(EnumAdStyleType.AD_STYLE_VIDEO.getTypeName()) && !adOperatingRule.equals(EnumAdStyleType.AD_STYLE_MULTIMEDIA.getTypeName())){
			message = "廣告類型錯誤";
			return INPUT;
		}
		
		//影音上稿檢查
		if(adOperatingRule.equals(EnumAdStyleType.AD_STYLE_VIDEO.getTypeName())){
			if(StringUtils.isBlank(adPrice)){
				message = "影音廣告出價不可為空";
				return INPUT;
			}
		}
		
		//多媒體上稿檢查
		if(adOperatingRule.equals(EnumAdStyleType.AD_STYLE_MULTIMEDIA.getTypeName())){
			if (StringUtils.isEmpty(adGroupSearchPriceType)) {
				message = "請選擇找東西廣告出價";
				return INPUT;
			} else {
				//一開始沒有關鍵字，所以建議出價跟預設出價相同，等廣告建完有關鍵字，再去呼叫api更新
				if(adGroupSearchPriceType.equals("1")) {
					adGroupSearchPrice = defSearchPrice;
				} else if(adGroupSearchPriceType.equals("2")) {
					log.info("adGroupSearchPrice = " + adGroupSearchPrice);
				} else {
					message = "請選擇找東西廣告出價";
					return INPUT;
				}
			}
			
			if (StringUtils.isEmpty(adGroupChannelPrice)) {
				message = "請輸入PChome頻道廣告出價！";
				return INPUT;
			} else {
				adGroupChannelPrice = adGroupChannelPrice.trim();
				if (adGroupChannelPrice.length() > 6) {
					message = "PChome頻道廣告出價不可超過 6 位數！";
					return INPUT;
				}
			}
		}
		
		
		PfpAdGroup pfpAdGroup = new PfpAdGroup();
		if(StringUtils.isNotBlank(adGroupSeq)) {
			pfpAdGroup = pfpAdGroupService.getPfpAdGroupBySeq(adGroupSeq);
		} else {
			adGroupSeq = sequenceService.getId(EnumSequenceTableName.PFP_AD_GROUP, "_");
			//log.info("new adGroupSeq = " + adGroupSeq);
			PfpAdAction pfpAdAction = pfpAdActionService.getPfpAdActionBySeq(adActionSeq);
			pfpAdGroup.setAdGroupSeq(adGroupSeq);
			pfpAdGroup.setPfpAdAction(pfpAdAction);
			pfpAdGroup.setAdGroupCreateTime(new Date());
		}
		pfpAdGroup.setAdGroupName(adGroupName);
		if(adOperatingRule.equals(EnumAdStyleType.AD_STYLE_MULTIMEDIA.getTypeName())){
			pfpAdGroup.setAdGroupSearchPriceType(Integer.parseInt(adGroupSearchPriceType));
			pfpAdGroup.setAdGroupSearchPrice(Float.parseFloat(adGroupSearchPrice));
			pfpAdGroup.setAdGroupChannelPrice(Float.parseFloat(adGroupChannelPrice));
			pfpAdGroup.setAdGroupPriceType(EnumAdPriceType.AD_PRICE_CPC.getDbTypeName());
			
			
		}
		if(adOperatingRule.equals(EnumAdStyleType.AD_STYLE_VIDEO.getTypeName())){
			pfpAdGroup.setAdGroupSearchPriceType(1);
			pfpAdGroup.setAdGroupSearchPrice(3);
			pfpAdGroup.setAdGroupChannelPrice(Float.parseFloat(adPrice));
			
			for(EnumAdPriceType enumAdPriceType: EnumAdPriceType.values()){
				if(Integer.parseInt(adPriceType) == enumAdPriceType.getType()){
					pfpAdGroup.setAdGroupPriceType(enumAdPriceType.getDbTypeName());
					break;
				}
			}
		}
		
		pfpAdGroup.setAdGroupStatus(EnumStatus.UnDone.getStatusId());	// 新增廣告分類時，status 設定為未完成
		pfpAdGroup.setAdGroupUpdateTime(new Date());
		
		//更新廣告活動狀態為已完成(開啟)
		pfpAdGroup.getPfpAdAction().setAdActionStatus(EnumStatus.Open.getStatusId());
		
		pfpAdGroupService.savePfpAdGroup(pfpAdGroup);

		//系統價更新 2018-01-12 停止更新價格出價以JOB為主
//		syspriceOperaterAPI.addAdSysprice(sysPriceAdPoolSeq, pfpAdGroup.getAdGroupChannelPrice());
		
		return SUCCESS;
	}

	public void setPfpCustomerInfoService(PfpCustomerInfoService pfpCustomerInfoService) {
		this.pfpCustomerInfoService = pfpCustomerInfoService;
	}

	public void setPfpAdActionService(PfpAdActionService pfpAdActionService) {
		this.pfpAdActionService = pfpAdActionService;
	}

	public void setPfpAdGroupService(PfpAdGroupService pfpAdGroupService) {
		this.pfpAdGroupService = pfpAdGroupService;
	}

	public void setSequenceService(ISequenceService sequenceService) {
		this.sequenceService = sequenceService;
	}

	public void setSyspriceOperaterAPI(SyspriceOperaterAPI syspriceOperaterAPI) {
		this.syspriceOperaterAPI = syspriceOperaterAPI;
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

	public int getAdActionMax() {
		return adActionMax;
	}

	public String getAdGroupSeq() {
		return adGroupSeq;
	}

	public void setAdGroupSeq(String adGroupSeq) {
		this.adGroupSeq = adGroupSeq;
	}

	public String getAdGroupName() {
		return adGroupName;
	}

	public void setAdGroupName(String adGroupName) {
		this.adGroupName = adGroupName;
	}

	public String getAdGroupSearchPriceType() {
		return adGroupSearchPriceType;
	}

	public void setAdGroupSearchPriceType(String adGroupSearchPriceType) {
		this.adGroupSearchPriceType = adGroupSearchPriceType;
	}

	public String getAdGroupSearchPrice() {
		return adGroupSearchPrice;
	}

	public void setAdGroupSearchPrice(String adGroupSearchPrice) {
		this.adGroupSearchPrice = adGroupSearchPrice;
	}

	public String getAdGroupChannelPrice() {
		return adGroupChannelPrice;
	}

	public void setAdGroupChannelPrice(String adGroupChannelPrice) {
		this.adGroupChannelPrice = adGroupChannelPrice;
	}

	public String getDefSearchPrice() {
		return defSearchPrice;
	}

	public String getSysChannelPrice() {
		return sysChannelPrice;
	}

	public String getAdAsideRate() {
		return AdAsideRate;
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

	public void setSysPriceAdPoolSeq(String sysPriceAdPoolSeq) {
		this.sysPriceAdPoolSeq = sysPriceAdPoolSeq;
	}

	public String getShowSearchPrice() {
		return showSearchPrice;
	}

	public String getShowChannelPrice() {
		return showChannelPrice;
	}

	public String getAdOperatingRule() {
		return adOperatingRule;
	}

	public void setAdOperatingRule(String adOperatingRule) {
		this.adOperatingRule = adOperatingRule;
	}
	public void setAdActionMax(int adActionMax) {
		this.adActionMax = adActionMax;
	}

	public void setBackPage(String backPage) {
		this.backPage = backPage;
	}

	public LinkedList<PfpAdPriceTypeVO> getPfpAdPriceTypeVOList() {
		return pfpAdPriceTypeVOList;
	}

	public void setPfpAdPriceTypeVOList(LinkedList<PfpAdPriceTypeVO> pfpAdPriceTypeVOList) {
		this.pfpAdPriceTypeVOList = pfpAdPriceTypeVOList;
	}

	public String getAdPrice() {
		return adPrice;
	}

	public void setAdPrice(String adPrice) {
		this.adPrice = adPrice;
	}

	public String getAdPriceType() {
		return adPriceType;
	}

	public void setAdPriceType(String adPriceType) {
		this.adPriceType = adPriceType;
	}

	public IPfpAdSyspriceService getPfpAdSyspriceService() {
		return pfpAdSyspriceService;
	}

	public void setPfpAdSyspriceService(IPfpAdSyspriceService pfpAdSyspriceService) {
		this.pfpAdSyspriceService = pfpAdSyspriceService;
	}

	public float getSysprice() {
		return sysprice;
	}

	public void setSysprice(float sysprice) {
		this.sysprice = sysprice;
	}

	public int getAdUserAmount() {
		return adUserAmount;
	}

	public void setAdUserAmount(int adUserAmount) {
		this.adUserAmount = adUserAmount;
	}

	public int getAdPriceTypeValue() {
		return adPriceTypeValue;
	}

	public void setAdPriceTypeValue(int adPriceTypeValue) {
		this.adPriceTypeValue = adPriceTypeValue;
	}
}
