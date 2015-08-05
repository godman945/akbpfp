package com.pchome.akbpfp.struts2.action.ad;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.oscache.util.StringUtil;
import com.pchome.akbpfp.api.SyspriceOperaterAPI;
import com.pchome.akbpfp.db.pojo.PfpAd;
import com.pchome.akbpfp.db.pojo.PfpAdDetail;
import com.pchome.akbpfp.db.pojo.PfpAdExcludeKeyword;
import com.pchome.akbpfp.db.pojo.PfpAdGroup;
import com.pchome.akbpfp.db.pojo.PfpAdKeyword;
import com.pchome.akbpfp.db.pojo.PfpCustomerInfo;
import com.pchome.akbpfp.db.service.accesslog.AdmAccesslogService;
import com.pchome.akbpfp.db.service.ad.PfpAdDetailService;
import com.pchome.akbpfp.db.service.ad.PfpAdExcludeKeywordService;
import com.pchome.akbpfp.db.service.ad.PfpAdGroupService;
import com.pchome.akbpfp.db.service.ad.PfpAdKeywordService;
import com.pchome.akbpfp.db.service.ad.PfpAdService;
import com.pchome.akbpfp.db.service.customerInfo.PfpCustomerInfoService;
import com.pchome.akbpfp.db.service.sequence.ISequenceService;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.ad.EnumAdStyle;
import com.pchome.enumerate.ad.EnumExcludeKeywordStatus;
import com.pchome.enumerate.sequence.EnumSequenceTableName;
import com.pchome.enumerate.utils.EnumStatus;
import com.pchome.rmi.accesslog.EnumAccesslogAction;
import com.pchome.soft.depot.utils.HttpUtil;

public class AdEditAction extends BaseCookieAction{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String message = "";

	private String adActionName;
	private String adGroupSeq;
	private String adGroupName;

	private String adSeq;
	private String adClass;
	private String adStyle;
	private int adStatus;
	private String adStatusDesc;
	private String adVerifyRejectReason;
	private String[] adDetailID;
	private String[] adDetailSeq;
	private String[] adDetailContent;
	private String adPoolSeq;
	private String templateProductSeq;
	private List<PfpAdDetail> pfpAdDetails;
	private List<PfpAdKeyword> pfpAdKeywords;
	private List<PfpAdExcludeKeyword> pfpAdExcludeKeywords;
	private String[] keywords;
	private String[] excludeKeywords;
	private String ulTmpName;
	private String imgFile;
	private String divBatchWord = "display:none;";		// 為了配合 adFreeAdAddKeyword 做的設定
	private String batchkeywords = "";					// 為了配合 adFreeAdAddKeyword 做的設定

	private String photoTmpPath;
	private String photoPath;
	private String photoDbPath;

	private PfpCustomerInfoService pfpCustomerInfoService;
	private ISequenceService sequenceService;
	private PfpAdGroupService pfpAdGroupService;
	private PfpAdService pfpAdService;
	private PfpAdDetailService pfpAdDetailService;
	private PfpAdKeywordService pfpAdKeywordService;
	private PfpAdExcludeKeywordService pfpAdExcludeKeywordService;
	private AdmAccesslogService admAccesslogService;
	private SyspriceOperaterAPI syspriceOperaterAPI;
	private HttpUtil httpUtil;

	public String AdAdEdit() throws Exception {
		log.info("AdAdEdit => adSeq = " + adSeq);

		PfpAd pfpAd = pfpAdService.getPfpAdBySeq(adSeq);
		adActionName = pfpAd.getPfpAdGroup().getPfpAdAction().getAdActionName();
		adGroupSeq = pfpAd.getPfpAdGroup().getAdGroupSeq();
		adGroupName  = pfpAd.getPfpAdGroup().getAdGroupName();
		adStyle = pfpAd.getAdStyle();
		adStatus = pfpAd.getAdStatus();
		adVerifyRejectReason = "";

		// ad Status
		for(EnumStatus status:EnumStatus.values()){
			if(status.getStatusId() == adStatus){
				adStatusDesc = status.getStatusRemark();
			}
		}
		if((adStatus == 3 || adStatus == 6) && StringUtils.isNotEmpty(pfpAd.getAdVerifyRejectReason())) {
			adVerifyRejectReason = "說明：" + pfpAd.getAdVerifyRejectReason();
		}

		pfpAdDetails = pfpAdDetailService.getPfpAdDetails(null, adSeq, null, null);
		adDetailSeq = new String[5];
		adDetailContent = new String[5];

		PfpCustomerInfo pfpCustomerInfo = pfpCustomerInfoService.findCustomerInfo(super.getCustomer_info_id());
		String customerInfoId = pfpCustomerInfo.getCustomerInfoId();
		String adCustomerInfoId = pfpAd.getPfpAdGroup().getPfpAdAction().getPfpCustomerInfo().getCustomerInfoId();
		if(!customerInfoId.equals(adCustomerInfoId)) {
			return "notOwner";
		}

		for (int i = 0; i < pfpAdDetails.size(); i++) {
			//log.info("pfpAdDetails.get(i).getAdDetailContent() = " + pfpAdDetails.get(i).getAdDetailContent());
			String adDetailId = pfpAdDetails.get(i).getAdDetailId();
			if(adDetailId != null && adDetailId.equals("img")) {
				adDetailSeq[0] = pfpAdDetails.get(i).getAdDetailSeq();;
				adDetailContent[0] = pfpAdDetails.get(i).getAdDetailContent() + "?" + RandomStringUtils.randomAlphanumeric(10);
				if(adDetailContent[0].indexOf("display:none") > 0) {
					adDetailContent[0] = pfpAdDetails.get(i).getAdDetailContent();
					imgFile = "";
				} else {
					imgFile = photoPath + adDetailContent[0].substring(adDetailContent[0].lastIndexOf(photoDbPath) + 4);
				}
			} else if(adDetailId != null && adDetailId.equals("title")) {
				adDetailSeq[1] = pfpAdDetails.get(i).getAdDetailSeq();;
				adDetailContent[1] = pfpAdDetails.get(i).getAdDetailContent();
			} else if(adDetailId != null && adDetailId.equals("content")) {
				adDetailSeq[2] = pfpAdDetails.get(i).getAdDetailSeq();;
				adDetailContent[2] = pfpAdDetails.get(i).getAdDetailContent();
			} else if(adDetailId != null && adDetailId.equals("real_url")) {
				adDetailSeq[3] = pfpAdDetails.get(i).getAdDetailSeq();
				String deCodeUrl = pfpAdDetails.get(i).getAdDetailContent();
				try {
				    deCodeUrl = HttpUtil.getInstance().convertRealUrl(deCodeUrl);
				}
				catch (Exception e) {
				    log.error(deCodeUrl, e);
				}
				adDetailContent[3] = deCodeUrl.replaceAll("http://", "");
			} else if(adDetailId != null && adDetailId.equals("show_url")) {
				adDetailSeq[4] = pfpAdDetails.get(i).getAdDetailSeq();
				String show_url = pfpAdDetails.get(i).getAdDetailContent();
				String deCodeUrl="";
				if(show_url.indexOf("http://") < 0){
				    deCodeUrl = HttpUtil.getInstance().convertRealUrl("http://" + show_url);
			    	}else{
				    deCodeUrl =HttpUtil.getInstance().convertRealUrl(show_url);
				}
				adDetailContent[4] = deCodeUrl;
//				String show_url = HttpUtil.getInstance().getUnicode(pfpAdDetails.get(i).getAdDetailContent());
//				adDetailContent[4] = show_url.replaceAll("http://", "");
//			    	adDetailSeq[4] = pfpAdDetails.get(i).getAdDetailSeq();
//				adDetailContent[4] = deCodeUrl.replaceAll("http://", "");
			}
		}

		if(adDetailSeq[0] == null) {
			adDetailSeq[0] = "";
			adDetailContent[0] = "img/public/na.gif\" style=\"display:none";
			imgFile = "";
		}

		// 取出分類所屬關鍵字
		pfpAdKeywords = pfpAdKeywordService.findAdKeywords(null, adGroupSeq, null, null, null, "10");
		// 取出分類所屬排除關鍵字
		pfpAdExcludeKeywords = pfpAdExcludeKeywordService.getPfpAdExcludeKeywords(adGroupSeq, pfpCustomerInfo.getCustomerInfoId());

		// 上傳圖片暫存檔名(亂數產生)
		ulTmpName = RandomStringUtils.randomAlphanumeric(30);

		return SUCCESS;
	}

	// 新增圖文式廣告
	@Transactional
	public String doAdAdEditTmg() throws Exception {
		log.info("doAdAdAddTmg => adSeq = " + adSeq);

		// 檢查 adStyle 是否正確，正確的話，設定 adPoolSeq、templateProductSeq
		chkAdStyle();

		// 檢查 Form 資料是否正確
		chkAdData1();
		if(message != null && !message.equals("")) {
		    return INPUT;
		}

		PfpAd pfpAd = pfpAdService.getPfpAdBySeq(adSeq);
		adGroupSeq = pfpAd.getPfpAdGroup().getAdGroupSeq();

		PfpAdGroup pfpAdGroup = pfpAd.getPfpAdGroup();

		// 修改廣告
		editAd();

		String imgDetail = "";
		for(int i = 0; i < adDetailSeq.length; i++) {
			PfpAdDetail pfpAdDetail = pfpAdDetailService.getPfpAdDetailBySeq(adDetailSeq[i]);
			// 此為防止圖檔沒有資料的時候，會出現錯誤的漏洞，直接新建一筆資料
			if(i == 0 && StringUtils.isEmpty(adDetailSeq[i])) {
				pfpAdDetail = new PfpAdDetail();
				pfpAdDetail.setAdDetailSeq(sequenceService.getId(EnumSequenceTableName.PFP_AD_DETAIL, "_"));
				pfpAdDetail.setPfpAd(pfpAd);
				pfpAdDetail.setAdDetailId("img");
				pfpAdDetail.setAdDetailContent("img/public/na.gif\" style=\"display:none");
				pfpAdDetail.setAdPoolSeq(pfpAdDetailService.getPfpAdDetailBySeq(adDetailSeq[i + 1]).getAdPoolSeq());
				pfpAdDetail.setDefineAdSeq(pfpAdDetailService.getPfpAdDetailBySeq(adDetailSeq[i + 1]).getDefineAdSeq());
				pfpAdDetail.setVerifyFlag("y");
				pfpAdDetail.setVerifyStatus("n");
				pfpAdDetail.setAdDetailCreateTime(new Date());
				pfpAdDetail.setAdDetailUpdateTime(new Date());
			}

			if(pfpAdDetail.getAdDetailId().equals("img")) {
				try {
					log.info(imgFile);
					if(StringUtils.isNotBlank(imgFile)) {
						File iPath = new File(photoPath);		// 圖片的存放路徑
						File iTmpPath = new File(photoTmpPath);	// 暫存圖片的路徑
						if(!iPath.exists())			iPath.mkdirs();
						if(!iTmpPath.exists())		iTmpPath.mkdirs();

						File adFile = new File(photoPath, adSeq + ".jpg");	// 上傳圖片的檔名
						File tmpFile = new File(imgFile);	// 設定圖片的 File 元件
						tmpFile.renameTo(adFile);			// 把暫存圖片搬到存放區

						imgDetail = photoDbPath + adFile.getName();	// 設定圖片檔存放在 DB 的路徑
						pfpAdDetail.setAdDetailContent(imgDetail);
					} else {
						if(StringUtils.isBlank(adDetailContent[0])) {
							imgDetail = "img/public/na.gif\" style=\"display:none";
							pfpAdDetail.setAdDetailContent(imgDetail);
						}
					}
				} catch (Exception ex) {
					log.info("ex : " + ex);
				}
			} else {
			    if(pfpAdDetail.getAdDetailId().equals("real_url") && !StringUtils.isBlank(adDetailContent[i])) {
				 if(adDetailContent[i].indexOf("http") < 0 ) {
				     adDetailContent[i] = HttpUtil.getInstance().getRealUrl("http://" + adDetailContent[i]);
				    }else{
				    	adDetailContent[i] = HttpUtil.getInstance().getRealUrl(adDetailContent[i]);
				    }
			    }
//				if(adDetailContent[i] != null && !adDetailContent[i].equals(pfpAdDetail.getAdDetailContent())) {
//					if(pfpAdDetail.getAdDetailId().equals("real_url")) {
//					    if(adDetailContent[i].indexOf("http") <=0 ) {
//						adDetailContent[i] = "http://" + adDetailContent[i];
//					    }else{

//
//					    	adDetailContent[i] = HttpUtil.getInstance().getRealUrl(adDetailContent[i]);
//					    }
//					}
					if(pfpAdDetail.getAdDetailId().equals("show_url")) {
//						String tmpHttp = adDetailContent[i];
//						if(adDetailContent[i].indexOf("http") == 0 || adDetailContent[i].indexOf("file") == 0) {
//							adDetailContent[i] = tmpHttp.substring(tmpHttp.indexOf("://") + 3);
//						}
					    if(adDetailContent[i].indexOf("http://") < 0 ) {
						adDetailContent[i] = HttpUtil.getInstance().getRealUrl("http://" + adDetailContent[i]);
					    }else{
						adDetailContent[i] = HttpUtil.getInstance().getRealUrl(adDetailContent[i]);
					    }
					    adDetailContent[i] = HttpUtil.getInstance().convertRealUrl(adDetailContent[i]);
					}
					pfpAdDetail.setAdDetailContent(adDetailContent[i]);
//				}
			}

			// 如果沒有圖檔資料，要用新增，有資料的部分，就用修改
			if(i == 0 && StringUtils.isEmpty(adDetailSeq[i])) {
			    pfpAdDetailService.insertPfpAdDetail(pfpAdDetail);
			} else {
			    pfpAdDetailService.updatePfpAdDetail(pfpAdDetail);
			}
		}

		// 新增關鍵字
		addKeywords(pfpAdGroup);
		//新增排除關鍵字
		addExcludeKeywords(pfpAdGroup);

		// 開啟廣告分類
		if(pfpAdGroup.getAdGroupStatus() != 4) {
			String oldStatus = "";
			// 原本的廣告狀態
			for(EnumStatus status:EnumStatus.values()){
				if(status.getStatusId() == pfpAdGroup.getAdGroupStatus()){
					oldStatus = status.getStatusDesc();
				}
			}
			String accesslogAction_Stauts = EnumAccesslogAction.AD_STATUS_MODIFY.getMessage();
			String accesslogMessage_Status = accesslogAction_Stauts + ":" + adGroupSeq + ",廣告分類狀態異動:" + oldStatus + "=>" + EnumStatus.Open.getStatusDesc();
			admAccesslogService.recordAdLog(EnumAccesslogAction.AD_STATUS_MODIFY, accesslogMessage_Status, super.getId_pchome(), super.getCustomer_info_id(), super.getUser_id(), request.getRemoteAddr());
			pfpAdGroup.setAdGroupStatus(4);
		}
		pfpAdGroupService.save(pfpAdGroup);

		return SUCCESS;
	}

	private void chkAdStyle() {
		if (StringUtils.isEmpty(adStyle) || (!adStyle.equals("TXT") && !adStyle.equals("TMG"))) {
			message = "請選擇廣告樣式！";
		} else if (!adStyle.equals("TXT") && !adStyle.equals("TMG")) {
			message = "請選擇廣告樣式！";
		} else {
			if(adStyle.equals("TXT")) {
				adPoolSeq = EnumAdStyle.TXT.getAdPoolSeq();
				templateProductSeq = EnumAdStyle.TXT.getTproSeq();
			} else if(adStyle.equals("TMG")) {
				adPoolSeq = EnumAdStyle.TMG.getAdPoolSeq();
				templateProductSeq = EnumAdStyle.TMG.getTproSeq();
			}
		}
	}

	private void chkAdData1() {
		try {
			if (StringUtils.isEmpty(adClass)) {
				message = "請選擇廣告分類！";
			}

			for(int i = 0; i < adDetailID.length; i++) {
				if (StringUtils.isEmpty(adDetailContent[i])) {
					if(i == 0 && adStyle.equals("TMG")) {
						//message = "請輸入" + adDetailName[i] + " ！";
					}
				} else {
					if(adDetailID[i].equals("title")) {
						if(adDetailContent[i].length() > 17) {
							message = "廣告標題不可超過 17  字！";
						}
					} else if(adDetailID[i].equals("content")) {
						if(adDetailContent[i].length() > 38) {
							message = "廣告內容不可超過 38  字！";
						}
					} else if(adDetailID[i].equals("real_url")) {
					    	if(StringUtil.isEmpty(adDetailContent[i])){
						    message = "請填寫廣告連結網址.";
						}
						if(adDetailContent[i].length() > 1024) {
							message = "廣告連結網址不可超過 1024  字！";
						} else {
							String url = adDetailContent[i];
							int urlState = 0;
							if(url.indexOf("http") != 0) {
								url = "http://"+url;
							}
							urlState = HttpUtil.getInstance().getStatusCode(HttpUtil.getInstance().getRealUrl(url));
							if(urlState < 200 && urlState >= 300) {
								message = "請輸入正確的廣告連結網址！";
							}
						}
					} else if(adDetailID[i].equals("show_url")) {
					    	if(StringUtil.isEmpty(adDetailContent[i])){
					    	    message = "請填寫廣告顯示網址.";
					    	}
					    	String url = adDetailContent[i];
					    	int urlState = 0;
					    	if(url.indexOf("http://") < 0) {
					    	    urlState = HttpUtil.getInstance().getStatusCode("http://"+url);
					    	}else{
					    	    urlState = HttpUtil.getInstance().getStatusCode(url);
					    	}

					    	if(urlState < 200 && urlState >= 300) {
					    	    message = "請輸入正確的廣告顯示網址！";
					    	}
					}
				}
			}
		} catch(Exception ex) {
			log.info("Exception ex :" + ex);
		}
	}

	// 修改廣告
	private void editAd() {
		try {
			PfpAd pfpAd = pfpAdService.getPfpAdBySeq(adSeq);
			pfpAd.setAdStyle(adStyle);
			pfpAd.setTemplateProductSeq(templateProductSeq);
			pfpAd.setAdStatus(EnumStatus.NoVerify.getStatusId());
			pfpAd.setAdSendVerifyTime(new Date());
			pfpAd.setAdUpdateTime(new Date());
			pfpAdService.updatePfpAd(pfpAd);
		} catch(Exception ex) {
			log.info("Exception ex" + ex);
		}
	}

	// 新增關鍵字
	private void addKeywords(PfpAdGroup pfpAdGroup) {
		try {
			if(keywords != null && keywords.length > 0) {
				for(int i = 0; i < keywords.length; i++) {
					List<PfpAdKeyword> KWExist = pfpAdKeywordService.findAdKeywords(null, adGroupSeq, keywords[i], null, null, "10");
					if(KWExist.size() == 0) {
						String adKeywordSeq = sequenceService.getId(EnumSequenceTableName.PFP_AD_KEYWORD, "_");
						PfpAdKeyword pfpAdKeyword = new PfpAdKeyword();
						pfpAdKeyword.setAdKeywordSeq(adKeywordSeq);
						pfpAdKeyword.setPfpAdGroup(pfpAdGroup);
						pfpAdKeyword.setAdKeyword(keywords[i]);
						pfpAdKeyword.setAdKeywordSearchPrice(pfpAdGroup.getAdGroupSearchPrice());
						pfpAdKeyword.setAdKeywordChannelPrice(pfpAdGroup.getAdGroupChannelPrice());
						pfpAdKeyword.setAdKeywordOrder(0);
						pfpAdKeyword.setAdKeywordStatus(EnumStatus.Open.getStatusId());
						pfpAdKeyword.setAdKeywordCreateTime(new Date());
						pfpAdKeyword.setAdKeywordUpdateTime(new Date());
						pfpAdKeywordService.savePfpAdKeyword(pfpAdKeyword);
						syspriceOperaterAPI.addKeywordSysprice(keywords[i], pfpAdGroup.getAdGroupSearchPrice());
					}
				}
			}
		} catch(Exception ex) {
			log.info("Exception(addKeywords) : " + ex);
		}
	}

	// 新增排除關鍵字
	private void addExcludeKeywords(PfpAdGroup pfpAdGroup) {
		try {
			if(excludeKeywords != null && excludeKeywords.length > 0) {
				for(int i = 0; i < excludeKeywords.length; i++) {
					String adExcludeKeywordSeq = sequenceService.getId(EnumSequenceTableName.PFP_AD_EXCLUDE_KEYWORD, "_");
					PfpAdExcludeKeyword pfpAdExcludeKeyword = new PfpAdExcludeKeyword();
					pfpAdExcludeKeyword.setAdExcludeKeywordSeq(adExcludeKeywordSeq);
					pfpAdExcludeKeyword.setPfpAdGroup(pfpAdGroup);
					pfpAdExcludeKeyword.setAdExcludeKeyword(excludeKeywords[i]);
					pfpAdExcludeKeyword.setAdExcludeKeywordStatus(EnumExcludeKeywordStatus.START.getStatusId());
					pfpAdExcludeKeyword.setAdExcludeKeywordCreateTime(new Date());
					pfpAdExcludeKeyword.setAdExcludeKeywordUpdateTime(new Date());
					pfpAdExcludeKeywordService.savePfpAdExcludeKeyword(pfpAdExcludeKeyword);
				}
			}

		} catch(Exception ex) {
			log.info("Exception(addExcludeKeywords):" + ex);
		}
	}

	public void setPfpCustomerInfoService(PfpCustomerInfoService pfpCustomerInfoService) {
		this.pfpCustomerInfoService = pfpCustomerInfoService;
	}

	public void setPfpAdGroupService(PfpAdGroupService pfpAdGroupService) {
		this.pfpAdGroupService = pfpAdGroupService;
	}

	public void setSequenceService(ISequenceService sequenceService) {
		this.sequenceService = sequenceService;
	}

	public void setPfpAdService(PfpAdService pfpAdService) {
		this.pfpAdService = pfpAdService;
	}

	public void setPfpAdDetailService(PfpAdDetailService pfpAdDetailService) {
		this.pfpAdDetailService = pfpAdDetailService;
	}

	public void setPfpAdKeywordService(PfpAdKeywordService pfpAdKeywordService) {
		this.pfpAdKeywordService = pfpAdKeywordService;
	}

	public void setPfpAdExcludeKeywordService(PfpAdExcludeKeywordService pfpAdExcludeKeywordService) {
		this.pfpAdExcludeKeywordService = pfpAdExcludeKeywordService;
	}

	public void setAdmAccesslogService(AdmAccesslogService admAccesslogService) {
		this.admAccesslogService = admAccesslogService;
	}

	public void setSyspriceOperaterAPI(SyspriceOperaterAPI syspriceOperaterAPI) {
		this.syspriceOperaterAPI = syspriceOperaterAPI;
	}

	public String getAdActionName() {
		return adActionName;
	}

	public void setAdActionName(String adActionName) {
		this.adActionName = adActionName;
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

	public String getAdSeq() {
		return adSeq;
	}

	public void setAdSeq(String adSeq) {
		this.adSeq = adSeq;
	}

	public String getAdClass() {
		return adClass;
	}

	public void setAdClass(String adClass) {
		this.adClass = adClass;
	}

	public String getAdStyle() {
		return adStyle;
	}

	public void setAdStyle(String adStyle) {
		this.adStyle = adStyle;
	}

	public int getAdStatus() {
		return adStatus;
	}

	public void setAdStatus(int adStatus) {
		this.adStatus = adStatus;
	}

	public String getAdStatusDesc() {
		return adStatusDesc;
	}

	public void setAdStatusDesc(String adStatusDesc) {
		this.adStatusDesc = adStatusDesc;
	}

	public String getAdVerifyRejectReason() {
		return adVerifyRejectReason;
	}

	public String[] getAdDetailID() {
		return adDetailID;
	}

	public void setAdDetailID(String[] adDetailID) {
		this.adDetailID = adDetailID;
	}

	public String[] getAdDetailSeq() {
		return adDetailSeq;
	}

	public void setAdDetailSeq(String[] adDetailSeq) {
		this.adDetailSeq = adDetailSeq;
	}

	public String[] getAdDetailContent() {
		return adDetailContent;
	}

	public void setAdDetailContent(String[] adDetailContent) {
		this.adDetailContent = adDetailContent;
	}

	public String getAdPoolSeq() {
		return adPoolSeq;
	}

	public void setAdPoolSeq(String adPoolSeq) {
		this.adPoolSeq = adPoolSeq;
	}

	public String getTemplateProductSeq() {
		return templateProductSeq;
	}

	public void setTemplateProductSeq(String templateProductSeq) {
		this.templateProductSeq = templateProductSeq;
	}

	public List<PfpAdKeyword> getPfpAdKeywords() {
		return pfpAdKeywords;
	}

	public List<PfpAdExcludeKeyword> getPfpAdExcludeKeywords() {
		return pfpAdExcludeKeywords;
	}

	public String[] getKeywords() {
		return keywords;
	}

	public void setKeywords(String[] keywords) {
		this.keywords = keywords;
	}

	public String[] getExcludeKeywords() {
		return excludeKeywords;
	}

	public void setExcludeKeywords(String[] excludeKeywords) {
		this.excludeKeywords = excludeKeywords;
	}

	public String getUlTmpName() {
		return ulTmpName;
	}

	public String getImgFile() {
		return imgFile;
	}

	public void setImgFile(String imgFile) {
		this.imgFile = imgFile;
	}

	public String getDivBatchWord() {
		return divBatchWord;
	}

	public String getBatchkeywords() {
		return batchkeywords;
	}

	public void setPhotoTmpPath(String photoTmpPath) {
		this.photoTmpPath = photoTmpPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public void setPhotoDbPath(String photoDbPath) {
		this.photoDbPath = photoDbPath;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
