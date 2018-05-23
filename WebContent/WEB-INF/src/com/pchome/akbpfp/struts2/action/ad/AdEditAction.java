package com.pchome.akbpfp.struts2.action.ad;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.pchome.akbpfp.godutil.CommonUtilModel;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.ad.EnumAdStyle;
import com.pchome.enumerate.ad.EnumExcludeKeywordStatus;
import com.pchome.enumerate.sequence.EnumSequenceTableName;
import com.pchome.enumerate.utils.EnumStatus;
import com.pchome.rmi.accesslog.EnumAccesslogAction;
import com.pchome.soft.depot.utils.HttpUtil;
import com.pchome.utils.CommonUtils;

public class AdEditAction extends BaseCookieAction{

	private static final long serialVersionUID = 1L;

	private String message = "";
	private String result;

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
	private String adLinkURL;
	private String adVideoURL;
	private String imgWidth = "";
	private String imgHeight = "";
	private String imgSize = "";
	private String imgTypeName = "";
	private String html5Flag;
	private String zipTitle = "";
	private String photoTmpPath;
	private String photoPath;
	private String photoDbPath;
	private String photoDbPathNew;

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
	private String adHiddenType;	//已建立的分類關鍵字欄位隱藏設定
	private String adDetailTitleSeq;
	private String imgTitle;
	private String adType;
	
	private int adGroupStatus;
	private int adActionStatus;
	
	private String adKeywordOpen;			//廣泛比對
	private String adKeywordPhraseOpen;		//詞組比對
	private String adKeywordPrecisionOpen;	//精準比對
	
	public String AdAdEdit() throws Exception {
		log.info("AdAdEdit => adSeq = " + adSeq);

		PfpAd pfpAd = pfpAdService.getPfpAdBySeq(adSeq);
		adActionName = pfpAd.getPfpAdGroup().getPfpAdAction().getAdActionName();
		adGroupSeq = pfpAd.getPfpAdGroup().getAdGroupSeq();
		adGroupName  = pfpAd.getPfpAdGroup().getAdGroupName();
		adStyle = pfpAd.getAdStyle();
		adStatus = pfpAd.getAdStatus();
		adGroupStatus = pfpAd.getPfpAdGroup().getAdGroupStatus();
		adActionStatus = pfpAd.getPfpAdGroup().getPfpAdAction().getAdActionStatus();
		adType = pfpAd.getPfpAdGroup().getPfpAdAction().getAdType().toString();
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
		adDetailSeq = new String[7];
		adDetailContent = new String[7];

		PfpCustomerInfo pfpCustomerInfo = pfpCustomerInfoService.findCustomerInfo(super.getCustomer_info_id());
		String customerInfoId = pfpCustomerInfo.getCustomerInfoId();
		String adCustomerInfoId = pfpAd.getPfpAdGroup().getPfpAdAction().getPfpCustomerInfo().getCustomerInfoId();
		if(!customerInfoId.equals(adCustomerInfoId)) {
			return "notOwner";
		}

		for (int i = 0; i < pfpAdDetails.size(); i++) {
			//log.info("pfpAdDetails.get(i).getAdDetailContent() = " + pfpAdDetails.get(i).getAdDetailContent());
			String adDetailId = pfpAdDetails.get(i).getAdDetailId();
			if (adDetailId != null && adDetailId.equals("img")) {
				adDetailSeq[0] = pfpAdDetails.get(i).getAdDetailSeq();
				adDetailContent[0] = pfpAdDetails.get(i).getAdDetailContent() + "?" + RandomStringUtils.randomAlphanumeric(10);
				if (adDetailContent[0].indexOf("display:none") > 0) {
					adDetailContent[0] = pfpAdDetails.get(i).getAdDetailContent();
					imgFile = "";
				} else {
					imgFile = photoPath + adDetailContent[0].substring(adDetailContent[0].lastIndexOf(photoDbPath) + 4);
				}
			} else if (adDetailId != null && adDetailId.equals("title")) {
				adDetailSeq[1] = pfpAdDetails.get(i).getAdDetailSeq();
				adDetailContent[1] = pfpAdDetails.get(i).getAdDetailContent();
			} else if (adDetailId != null && adDetailId.equals("content")) {
				adDetailSeq[2] = pfpAdDetails.get(i).getAdDetailSeq();
				adDetailContent[2] = pfpAdDetails.get(i).getAdDetailContent();
			} else if (adDetailId != null && adDetailId.equals("sales_price")) {
				adDetailSeq[3] = pfpAdDetails.get(i).getAdDetailSeq();
				adDetailContent[3] = pfpAdDetails.get(i).getAdDetailContent();
			} else if (adDetailId != null && adDetailId.equals("promotional_price")) {
				adDetailSeq[4] = pfpAdDetails.get(i).getAdDetailSeq();
				adDetailContent[4] = pfpAdDetails.get(i).getAdDetailContent();
			} else if (adDetailId != null && adDetailId.equals("real_url")) {
				adDetailSeq[5] = pfpAdDetails.get(i).getAdDetailSeq();
				String deCodeUrl = pfpAdDetails.get(i).getAdDetailContent();
				try {
					deCodeUrl = HttpUtil.getInstance().convertRealUrl(deCodeUrl);
				} catch (Exception e) {
					log.error(deCodeUrl, e);
				}
				adDetailContent[5] = deCodeUrl.replaceAll("http://", "");
			} else if (adDetailId != null && adDetailId.equals("show_url")) {
				adDetailSeq[6] = pfpAdDetails.get(i).getAdDetailSeq();
				String show_url = pfpAdDetails.get(i).getAdDetailContent();
				String deCodeUrl = "";
				if (show_url.indexOf("http://") < 0) {
					deCodeUrl = HttpUtil.getInstance().convertRealUrl("http://" + show_url);
				} else {
					deCodeUrl = HttpUtil.getInstance().convertRealUrl(show_url);
				}
				adDetailContent[6] = deCodeUrl;
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

		if(pfpAdKeywords.isEmpty() && pfpAdExcludeKeywords.isEmpty()){
			adHiddenType = "YES";
		}
		
		return SUCCESS;
	}

	// 修改圖文式廣告
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
		String detailLAccesslogTitle = "廣告：" + pfpAdGroup.getPfpAdAction().getAdActionName() + "；" + pfpAdGroup.getAdGroupName() + "；" + adSeq + "==>";
		addAccesslog(EnumAccesslogAction.AD_STATUS_MODIFY, detailLAccesslogTitle + "送出審核");
		String detailAccesslogMessage = "";
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
			}else if(StringUtils.isEmpty(adDetailSeq[i])){ // 不管後面新增幾個欄位
				pfpAdDetail = new PfpAdDetail();
				pfpAdDetail.setAdDetailSeq(sequenceService.getId(EnumSequenceTableName.PFP_AD_DETAIL, "_"));
				pfpAdDetail.setPfpAd(pfpAd);
				if (i == 3) {
					pfpAdDetail.setAdDetailId("sales_price"); // 商品原價
					pfpAdDetail.setDefineAdSeq("dad_201303070015");
				} else if (i == 4) {
					pfpAdDetail.setAdDetailId("promotional_price"); // 商品促銷價
					pfpAdDetail.setDefineAdSeq("dad_201303070016");
				}
				pfpAdDetail.setAdDetailContent(adDetailContent[i]);
				pfpAdDetail.setAdPoolSeq("adp_201303070003");
				pfpAdDetail.setVerifyFlag("y");
				pfpAdDetail.setVerifyStatus("n");
				pfpAdDetail.setAdDetailCreateTime(new Date());
				pfpAdDetail.setAdDetailUpdateTime(new Date());
			}
			pfpAdDetail.setAdDetailUpdateTime(new Date()); // 更新更新時間
			
			if ("img".equals(pfpAdDetail.getAdDetailId())) {
				try {
					log.info(imgFile);
					if (StringUtils.isNotBlank(imgFile)) {
						
						String oldImgDteail = pfpAdDetail.getAdDetailContent();
						if(oldImgDteail.length() >= 0){ // 避免如果原本就沒圖的，截字串出錯
							oldImgDteail = oldImgDteail.substring(3);
						}
						
						if (imgFile.indexOf(oldImgDteail) == -1) { // 有更改圖片
							detailAccesslogMessage += "廣告圖片；";

							/* 新的路徑:img/user/AC2013071700005/20180323/original/ad_201803230005.jpg
							 * 舊的路徑:img/ad_201801290001.jpg
							 * 沒圖片的路徑:img/public/na.gif" style="display:none
							 * */
							String oldImgDate = "";
							if (oldImgDteail.split("/").length > 3) { // 如果已經是新的路徑的話,切出來的長度超過3個
								oldImgDate = oldImgDteail.split("/")[3];
							} else {
								SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
								oldImgDate = sdf.format(new Date());
							}

							String photoPath = photoDbPathNew + super.getCustomer_info_id() + "/" + oldImgDate + "/original/";
							CommonUtilModel.checkFolderCreation(photoPath); // 檢查有無資料夾，沒有則新建資料夾

							// 判斷gif還是jpg
							String fileType = imgFile.substring(imgFile.lastIndexOf(".") + 1);
							if (fileType.indexOf("?") != -1) {
								fileType = fileType.substring(0, fileType.indexOf("?"));
							}

							// 上傳圖片的檔名
							File adFile = null;
							String imgName = "";
							if ("GIF".equalsIgnoreCase(fileType)) {
								// 只有GIF存原副檔名
								imgName = adSeq + "." + fileType;
								adFile = new File(photoPath, imgName);
							} else {
								imgName = adSeq + ".jpg";
								adFile = new File(photoPath, adSeq + ".jpg");
							}
							File tmpFile = new File(imgFile); // 設定圖片的 File 元件
							tmpFile.renameTo(adFile); // 把暫存圖片搬到存放區
							
							String imgPath = "img/user/" + super.getCustomer_info_id() + "/" + oldImgDate + "/original/" + imgName;
							pfpAdDetail.setAdDetailContent(imgPath);
						}
						
					} else {
						if (StringUtils.isBlank(adDetailContent[0])) {
							imgDetail = "img/public/na.gif\" style=\"display:none";
							String oldImgDteail = pfpAdDetail.getAdDetailContent();
							pfpAdDetail.setAdDetailContent(imgDetail);
							if (checkDetailChange(oldImgDteail, imgDetail)) {
								detailAccesslogMessage += "廣告圖片；";
							}
						}
					}
				} catch (Exception ex) {
					log.info("ex : " + ex);
				}
			} else {
				if ("real_url".equals(pfpAdDetail.getAdDetailId()) && !StringUtils.isBlank(adDetailContent[i])) {
					if (adDetailContent[i].indexOf("http") < 0) {
						adDetailContent[i] = HttpUtil.getInstance().getRealUrl("http://" + adDetailContent[i]);
						// adDetailContent[i] = "http://" + adDetailContent[i];
					} else {
						adDetailContent[i] = adDetailContent[i];
					}
					adDetailContent[i] = adDetailContent[i].trim();

					if (checkDetailChange(pfpAdDetail.getAdDetailContent(), adDetailContent[i])) {
						detailAccesslogMessage += "廣告連結網址；";
					}
				} else if ("show_url".equals(pfpAdDetail.getAdDetailId())) {
					if (adDetailContent[i].indexOf("http://") < 0) {
						adDetailContent[i] = HttpUtil.getInstance().getRealUrl("http://" + adDetailContent[i]);
					} else {
						adDetailContent[i] = HttpUtil.getInstance().getRealUrl(adDetailContent[i]);
					}
					adDetailContent[i] = HttpUtil.getInstance().convertRealUrl(adDetailContent[i]);
					adDetailContent[i] = adDetailContent[i].trim();

					if (checkDetailChange(pfpAdDetail.getAdDetailContent(), adDetailContent[i])) {
						detailAccesslogMessage += "廣告顯示網址；";
					}
				} else if ("title".equals(pfpAdDetail.getAdDetailId())) {
					adDetailContent[i] = adDetailContent[i].replaceAll("\n", "");
					adDetailContent[i] = adDetailContent[i].replaceAll("\r", "");

					if (checkDetailChange(pfpAdDetail.getAdDetailContent(), adDetailContent[i])) {
						detailAccesslogMessage += "廣告標題；";
					}
				} else if ("content".equals(pfpAdDetail.getAdDetailId())) {
					adDetailContent[i] = adDetailContent[i].replaceAll("\n", "");
					adDetailContent[i] = adDetailContent[i].replaceAll("\r", "");

					if (checkDetailChange(pfpAdDetail.getAdDetailContent(), adDetailContent[i])) {
						detailAccesslogMessage += "廣告內容；";
					}
				} else if ("sales_price".equals(pfpAdDetail.getAdDetailId())) {
					if (checkDetailChange(pfpAdDetail.getAdDetailContent(), adDetailContent[i])) {
						detailAccesslogMessage += "商品原價；";
					}
				} else if ("promotional_price".equals(pfpAdDetail.getAdDetailId())) {
					if (checkDetailChange(pfpAdDetail.getAdDetailContent(), adDetailContent[i])) {
						detailAccesslogMessage += "商品促銷價；";
					}
				}

				pfpAdDetail.setAdDetailContent(adDetailContent[i]);
			}

			// 如果沒有圖檔資料，要用新增，有資料的部分，就用修改
			if((i == 0 && StringUtils.isEmpty(adDetailSeq[i])) || StringUtils.isEmpty(adDetailSeq[i])) {
			    pfpAdDetailService.insertPfpAdDetail(pfpAdDetail);
			} else {
			    pfpAdDetailService.updatePfpAdDetail(pfpAdDetail);
			}
			
		}
		
		if(StringUtils.isNotBlank(detailAccesslogMessage)){ // 紀錄更新log
			detailAccesslogMessage = detailAccesslogMessage.substring(0,detailAccesslogMessage.length() -1);
			addAccesslog(EnumAccesslogAction.PLAY_MODIFY, detailLAccesslogTitle + "修改：" + detailAccesslogMessage);
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
			addAccesslog(EnumAccesslogAction.AD_STATUS_MODIFY, accesslogMessage_Status);
			pfpAdGroup.setAdGroupStatus(4);
		}
		pfpAdGroupService.save(pfpAdGroup);

		return SUCCESS;
	}
	
	/*
	 * 儲存影音上稿資料
	 * 
	 * */
	public String doAdAdEditVideo() {
		try{
			System.out.println(adSeq);
			System.out.println(adLinkURL);
			List<PfpAdDetail> pfpAdDetailList = pfpAdDetailService.getPfpAdDetailByAdSeq(adSeq);
			Date date = new Date();
			for (PfpAdDetail pfpAdDetail : pfpAdDetailList) {
				if(pfpAdDetail.getAdDetailId().equals("real_url")){
					pfpAdDetail.setAdDetailContent(adLinkURL);
					pfpAdDetail.setAdDetailUpdateTime(date);
					
					PfpAd pfpAd = pfpAdService.getPfpAdBySeq(adSeq);
					pfpAd.setAdStatus(EnumStatus.NoVerify.getStatusId());
					pfpAd.setAdSendVerifyTime(date);
					pfpAd.setAdUpdateTime(date);
					pfpAdService.updatePfpAd(pfpAd);
					break;
				}
			}
			result = "success";
			return SUCCESS;
		}catch(Exception e){
			e.getMessage();
			result = "error";
			return SUCCESS;
		}
	}
	
	//圖像廣告
	public String AdAdEditImg() throws Exception {
		log.info("AdAdEditImg => adSeq = " + adSeq);

		PfpAd pfpAd = pfpAdService.getPfpAdBySeq(adSeq);
		adActionName = pfpAd.getPfpAdGroup().getPfpAdAction().getAdActionName();
		adGroupSeq = pfpAd.getPfpAdGroup().getAdGroupSeq();
		adGroupName  = pfpAd.getPfpAdGroup().getAdGroupName();
		adStyle = pfpAd.getAdStyle();
		adStatus = pfpAd.getAdStatus();
		adVerifyRejectReason = "";
		adGroupStatus = pfpAd.getPfpAdGroup().getAdGroupStatus();
		adActionStatus = pfpAd.getPfpAdGroup().getPfpAdAction().getAdActionStatus();
		adType = pfpAd.getPfpAdGroup().getPfpAdAction().getAdType().toString();
		html5Flag = "N";
		String adAssignTadSeq = pfpAd.getAdAssignTadSeq();
		if(StringUtils.equals("c_x05_po_tad_0059", adAssignTadSeq)){
			html5Flag = "Y";
		}

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
		adDetailSeq = new String[2];
		adDetailContent = new String[2];

		PfpCustomerInfo pfpCustomerInfo = pfpCustomerInfoService.findCustomerInfo(super.getCustomer_info_id());
		String customerInfoId = pfpCustomerInfo.getCustomerInfoId();
		String adCustomerInfoId = pfpAd.getPfpAdGroup().getPfpAdAction().getPfpCustomerInfo().getCustomerInfoId();
		if(!customerInfoId.equals(adCustomerInfoId)) {
			return "notOwner";
		}
		
		for (int i = 0; i < pfpAdDetails.size(); i++) {
			//log.info("pfpAdDetails.get(i).getAdDetailContent() = " + pfpAdDetails.get(i).getAdDetailContent());
			String adDetailId = pfpAdDetails.get(i).getAdDetailId();
			
			if(adDetailId != null && adDetailId.equals("real_url")) {
				adDetailSeq[0] = pfpAdDetails.get(i).getAdDetailSeq();
				String deCodeUrl = pfpAdDetails.get(i).getAdDetailContent();
				/*try {
				    deCodeUrl = HttpUtil.getInstance().convertRealUrl(deCodeUrl);
				}
				catch (Exception e) {
				    log.error(deCodeUrl, e);
				}
				adDetailContent[0] = deCodeUrl.replaceAll("http://", "");*/
				 if(deCodeUrl.indexOf("http") < 0 ) {
					 adDetailContent[0] = "http://" + deCodeUrl;
				 } else {
					 adDetailContent[0] = deCodeUrl;
				 }
			} else if(adDetailId != null && adDetailId.equals("img")) {
				if(StringUtils.equals("Y", html5Flag)){
					adDetailSeq[1] = pfpAdDetails.get(i).getAdDetailSeq();
					adDetailContent[1] = pfpAdDetails.get(i).getAdDetailContent();
					imgFile = adDetailContent[1];
				} else {
					adDetailSeq[1] = pfpAdDetails.get(i).getAdDetailSeq();
					String imgFilename = pfpAdDetails.get(i).getAdDetailContent();
					imgFilename = imgFilename.substring(imgFilename.lastIndexOf("/"));
					if(pfpAdDetails.get(i).getAdDetailContent().indexOf("original") == -1){
						adDetailContent[1] = pfpAdDetails.get(i).getAdDetailContent().replace(imgFilename, "/original" + imgFilename);	
					}else {
						adDetailContent[1] = pfpAdDetails.get(i).getAdDetailContent();
					}
					getImgSize(adDetailContent[1]);
					if(adDetailContent[1].indexOf("display:none") > 0) {
						adDetailContent[1] = pfpAdDetails.get(i).getAdDetailContent();
						imgFile = "";
					} else {
						imgFile = adDetailContent[1];
					}
				}
			} else if(adDetailId != null && adDetailId.equals("title")){
				adDetailTitleSeq = pfpAdDetails.get(i).getAdDetailSeq();
				imgTitle = pfpAdDetails.get(i).getAdDetailContent();
				if(imgTitle.length() > 8){
					imgTitle = imgTitle.substring(0, 8) + "...";
				}
			} else if(adDetailId != null && adDetailId.equals("size")){
				String[] sizeArray = pfpAdDetails.get(i).getAdDetailContent().split("x");
				imgWidth = sizeArray[0].trim();
				imgHeight = sizeArray[1].trim();
				imgSize = "180K以下";
				imgTypeName = "ZIP";
			} else if(adDetailId != null && adDetailId.equals("zip")){
				zipTitle = pfpAdDetails.get(i).getAdDetailContent();
				zipTitle = zipTitle.replace("(html5)", "");
				if(zipTitle.length() > 8){
					zipTitle = zipTitle.substring(0, 8) + "...";
				}
				zipTitle = zipTitle + "(html5)";
			}
		}

		if(adDetailSeq[1] == null) {
			adDetailSeq[1] = "";
			adDetailContent[1] = "img/public/na.gif\" style=\"display:none";
			imgFile = "";
		}
		
		// 取出分類所屬關鍵字
		pfpAdKeywords = pfpAdKeywordService.findAdKeywords(null, adGroupSeq, null, null, null, "10");
		// 取出分類所屬排除關鍵字
		pfpAdExcludeKeywords = pfpAdExcludeKeywordService.getPfpAdExcludeKeywords(adGroupSeq, pfpCustomerInfo.getCustomerInfoId());
		
		if(pfpAdKeywords.isEmpty() && pfpAdExcludeKeywords.isEmpty()){
			adHiddenType = "YES";
		}
		
		return SUCCESS;
	}
	
	//修改圖像式廣告
	@Transactional
	public String doAdAdEditImg() throws Exception {
		log.info("doAdAdEditImg => adSeq = " + adSeq);
		log.info("doAdAdEditImg => adStyle = " + adStyle);
		
		adStyle = "IMG";
		
		// 檢查 adStyle 是否正確，正確的話，設定 adPoolSeq、templateProductSeq
		chkAdStyle();

		// 檢查 網址資料是否正確
		if(StringUtils.isBlank(adLinkURL)){
            result = "請填寫廣告連結網址.";
            return SUCCESS;
        }
        if(adLinkURL.length() > 1024) {
            result = "廣告連結網址不可超過 1024字！";
            return SUCCESS;
        }
        if (adLinkURL.indexOf("http") != 0) {
            adLinkURL = "http://" + adLinkURL;
        }
        int urlState = HttpUtil.getInstance().getStatusCode(adLinkURL);
        if(urlState < 200 && urlState >= 300) {
            result = "請輸入正確的廣告連結網址！";
            return SUCCESS;
        }
		
        /*if(StringUtils.isBlank(imgTitle)){
            result = "請填寫圖片名稱！";
            return SUCCESS;
        }
        if(imgTitle.length() > 1024){
			result = "輸入的廣告名稱不可超過 1024字！";
            return SUCCESS;
		}*/
        
        PfpAd pfpAd = pfpAdService.getPfpAdBySeq(adSeq);
		adGroupSeq = pfpAd.getPfpAdGroup().getAdGroupSeq();

		PfpAdGroup pfpAdGroup = pfpAd.getPfpAdGroup();
		
		//建立關鍵字
    	List<String> keyWordList = new ArrayList<String>();
    	if (!keywords[0].equals("[]")) {
    	    String data = "";
    	    for (char a : keywords[0].toCharArray()) {
        		if (String.valueOf(a).equals("[") || String.valueOf(a).equals("\"")) {
        		    continue;
        		}
        		if (String.valueOf(a).equals("]")) {
        		    keyWordList.add(data);
        		} else {
        		    if (String.valueOf(a).equals(",")) {
            			keyWordList.add(data);
            			data = "";
        		    } else {
        		        data = data + String.valueOf(a);
        		    }
        		}
    	    }
    	    keywords = keyWordList.toArray(new String[keyWordList.size()]);

    	    if (keywords.length != 0 && StringUtils.isBlank(adKeywordOpen) && StringUtils.isBlank(adKeywordPhraseOpen)
					&& StringUtils.isBlank(adKeywordPrecisionOpen)) {
    		    result = "請選擇關鍵字比對方式！";
    		    return SUCCESS;
    		}
    	    
    	    //新增關鍵字
    	    addKeywords(pfpAdGroup);
    	}

    	//建立排除關鍵字
    	List<String> excludeKeyWordList = new ArrayList<String>();
    	if (!excludeKeywords[0].equals("[]")) {
    	    String data = "";
    	    for (char a : excludeKeywords[0].toCharArray()) {
        		if (String.valueOf(a).equals("[") || String.valueOf(a).equals("\"")) {
        		    continue;
        		}
        		if (String.valueOf(a).equals("]")) {
        		    excludeKeyWordList.add(data);
        		} else {
        		    if (String.valueOf(a).equals(",")) {
            			excludeKeyWordList.add(data);
            			data = "";
        		    } else {
        		        data = data + String.valueOf(a);
        		    }
        		}
    	    }
    	    excludeKeywords = excludeKeyWordList.toArray(new String[excludeKeyWordList.size()]);

    	    //新增排除關鍵字
    	    addExcludeKeywords(pfpAdGroup);
    	}
		
		// 修改廣告
		editAd();
		String adImgPoolSeq = "";
		String detailLAccesslogTitle = "廣告：" + pfpAdGroup.getPfpAdAction().getAdActionName() + "；" + pfpAdGroup.getAdGroupName() + "；" + adSeq + "==>";
		addAccesslog(EnumAccesslogAction.AD_STATUS_MODIFY, detailLAccesslogTitle + "送出審核");
		if(adDetailSeq[0] != null && adDetailSeq[0] != ""){
			PfpAdDetail pfpAdDetail = pfpAdDetailService.getPfpAdDetailBySeq(adDetailSeq[0]);
			adImgPoolSeq = pfpAdDetail.getAdPoolSeq();
			String oldRealUrl = pfpAdDetail.getAdDetailContent();
			pfpAdDetail.setAdDetailContent(adLinkURL.trim());
			pfpAdDetailService.updatePfpAdDetail(pfpAdDetail);
			
			if (checkDetailChange(oldRealUrl, adLinkURL.trim())) {
				addAccesslog(EnumAccesslogAction.PLAY_MODIFY, detailLAccesslogTitle + "修改：廣告連結網址");
			}
		}

		//修改名稱
		/*if(StringUtils.isBlank(adDetailTitleSeq)){
			String seq = sequenceService.getId(EnumSequenceTableName.PFP_AD_DETAIL, "_");
		    PfpAdDetail pfpAdDetail = new PfpAdDetail();
		    pfpAdDetail.setAdDetailSeq(seq);
		    pfpAdDetail.setPfpAd(pfpAd);
		    pfpAdDetail.setAdDetailId(EnumAdDetail.title.name());
		    pfpAdDetail.setAdDetailContent(imgTitle);
		    pfpAdDetail.setAdPoolSeq(adImgPoolSeq);
		    pfpAdDetail.setDefineAdSeq(EnumAdDetail.define_ad_seq_title.getAdDetailName());
		    pfpAdDetail.setVerifyFlag("y");
		    pfpAdDetail.setVerifyStatus("n");
		    pfpAdDetail.setAdDetailCreateTime(new Date());
		    pfpAdDetail.setAdDetailUpdateTime(new Date());
		    pfpAdDetailService.savePfpAdDetail(pfpAdDetail);
		} else {
			PfpAdDetail pfpAdDetail = pfpAdDetailService.getPfpAdDetailBySeq(adDetailTitleSeq);
			pfpAdDetail.setAdDetailContent(imgTitle);
			pfpAdDetailService.updatePfpAdDetail(pfpAdDetail);
		}*/
		
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
			addAccesslog(EnumAccesslogAction.AD_STATUS_MODIFY, accesslogMessage_Status);
			//admAccesslogService.recordAdLog(EnumAccesslogAction.AD_STATUS_MODIFY, accesslogMessage_Status, super.getId_pchome(), super.getCustomer_info_id(), super.getUser_id(), request.getRemoteAddr());
			pfpAdGroup.setAdGroupStatus(4);
		}
		pfpAdGroupService.save(pfpAdGroup);
		
		result = "success";
		return SUCCESS;
	}
	
	private void chkAdStyle() {
		if (StringUtils.isEmpty(adStyle) || (!adStyle.equals("TXT") && !adStyle.equals("TMG") && !adStyle.equals("IMG"))) {
			message = "請選擇廣告樣式！";
		} else if (!adStyle.equals("TXT") && !adStyle.equals("TMG") && !adStyle.equals("IMG")) {
			message = "請選擇廣告樣式！";
		} else {
			if(adStyle.equals("TXT")) {
				adPoolSeq = EnumAdStyle.TXT.getAdPoolSeq();
				templateProductSeq = EnumAdStyle.TXT.getTproSeq();
			} else if(adStyle.equals("TMG")) {
				adPoolSeq = EnumAdStyle.TMG.getAdPoolSeq();
				templateProductSeq = EnumAdStyle.TMG.getTproSeq();
			} else if(adStyle.equals("IMG")) {
				adPoolSeq = EnumAdStyle.IMG.getAdPoolSeq();
				templateProductSeq = EnumAdStyle.IMG.getTproSeq();
			}
		}
	}

	private void chkAdData1() {
		try {
			if (StringUtils.isEmpty(adClass)) {
				message = "請選擇廣告分類！";
			}

			if(keywords.length != 0 && StringUtils.isBlank(adKeywordOpen) && StringUtils.isBlank(adKeywordPhraseOpen)
					&& StringUtils.isBlank(adKeywordPrecisionOpen)){
				message = "請選擇關鍵字比對方式！";
			}
			
			for (int i = 0; i < adDetailID.length; i++) {
				if (StringUtils.isEmpty(adDetailContent[i])) {
					if (i == 0 && adStyle.equals("TMG")) {
						// message = "請輸入" + adDetailName[i] + " ！";
					}
				} else {
					if (adDetailID[i].equals("title")) {
						if (adDetailContent[i].length() > 17) {
							message = "廣告標題不可超過 17  字！";
						}
					} else if (adDetailID[i].equals("content")) {
						if (adDetailContent[i].length() > 38) {
							message = "廣告內容不可超過 38  字！";
						}
					} else if (adDetailID[i].equals("real_url")) {
						if (StringUtil.isEmpty(adDetailContent[i])) {
							message = "請填寫廣告連結網址.";
						}
						if (adDetailContent[i].length() > 1024) {
							message = "廣告連結網址不可超過 1024  字！";
						} else {
							String url = adDetailContent[i];
							int urlState = 0;
							if (url.indexOf("http") != 0) {
								url = "http://" + url;
							}
							urlState = HttpUtil.getInstance().getStatusCode(HttpUtil.getInstance().getRealUrl(url));
							if (urlState < 200 && urlState >= 300) {
								message = "請輸入正確的廣告連結網址！";
							}
						}
					} else if (adDetailID[i].equals("show_url")) {
						if (StringUtil.isEmpty(adDetailContent[i])) {
							message = "請填寫廣告顯示網址.";
						}
						String url = adDetailContent[i];
						int urlState = 0;
						if (url.indexOf("http://") < 0) {
							urlState = HttpUtil.getInstance().getStatusCode("http://" + url);
						} else {
							urlState = HttpUtil.getInstance().getStatusCode(url);
						}

						if (urlState < 200 && urlState >= 300) {
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
						//廣泛比對設定
						if(StringUtils.isNotBlank(adKeywordOpen)){
							pfpAdKeyword.setAdKeywordSearchPrice(pfpAdGroup.getAdGroupSearchPrice());
							pfpAdKeyword.setAdKeywordOpen(1);
						} else {
							pfpAdKeyword.setAdKeywordSearchPrice(0);
							pfpAdKeyword.setAdKeywordOpen(0);
						}
						//詞組比對設定
						if(StringUtils.isNotBlank(adKeywordPhraseOpen)){
							pfpAdKeyword.setAdKeywordSearchPhrasePrice(pfpAdGroup.getAdGroupSearchPrice());
							pfpAdKeyword.setAdKeywordPhraseOpen(1);
						} else {
							pfpAdKeyword.setAdKeywordSearchPhrasePrice(0);
							pfpAdKeyword.setAdKeywordPhraseOpen(0);
						}
						//精準比對設定
						if(StringUtils.isNotBlank(adKeywordPrecisionOpen)){
							pfpAdKeyword.setAdKeywordSearchPrecisionPrice(pfpAdGroup.getAdGroupSearchPrice());
							pfpAdKeyword.setAdKeywordPrecisionOpen(1);
						} else {
							pfpAdKeyword.setAdKeywordSearchPrecisionPrice(0);
							pfpAdKeyword.setAdKeywordPrecisionOpen(0);
						}
						pfpAdKeyword.setAdKeywordChannelPrice(pfpAdGroup.getAdGroupChannelPrice());
						pfpAdKeyword.setAdKeywordOrder(0);
						pfpAdKeyword.setAdKeywordPhraseOrder(0);
						pfpAdKeyword.setAdKeywordPrecisionOrder(0);
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

	private void getImgSize(String originalImg) throws Exception {
		log.info(">>>>>>>>>>>>>>>originalImg:"+originalImg);
		System.out.println(">>>>>>>>>>>>>>>originalImg:" + originalImg);
		String path = (originalImg.indexOf("D:/") >= 0) ? originalImg : "/home/webuser/akb/pfp/" +  originalImg.replace("\\", "/");
		File picture = null;
		picture = new File(path);
		if(picture != null){
			log.info(">>>>>>>>>>>>>>>picture:"+picture);
			Map<String,String> imgInfo = CommonUtils.getInstance().getImgInfo(picture);
			
			log.info(">>>>>>>>>>>>>>>imgInfo:"+imgInfo);
			
			imgWidth = imgInfo.get("imgWidth");
			imgHeight = imgInfo.get("imgHeight");
			imgTypeName = imgInfo.get("imgFileType").toUpperCase();
		}
	}
	
	/**
	 * 編輯影音廣告
	 * */
	public String adAdEditVideo() throws Exception{
		log.info("AdAdVideoEdit => adSeq = " + adSeq);

		PfpAd pfpAd = pfpAdService.getPfpAdBySeq(adSeq);
		adActionName = pfpAd.getPfpAdGroup().getPfpAdAction().getAdActionName();
		adGroupSeq = pfpAd.getPfpAdGroup().getAdGroupSeq();
		adGroupName  = pfpAd.getPfpAdGroup().getAdGroupName();
		adStyle = pfpAd.getAdStyle();
		adStatus = pfpAd.getAdStatus();
		adGroupStatus = pfpAd.getPfpAdGroup().getAdGroupStatus();
		adActionStatus = pfpAd.getPfpAdGroup().getPfpAdAction().getAdActionStatus();
		adType = pfpAd.getPfpAdGroup().getPfpAdAction().getAdType().toString();
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
				adDetailSeq[0] = pfpAdDetails.get(i).getAdDetailSeq();
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
			}else if(adDetailId.equals("video_url")){
				adVideoURL = pfpAdDetails.get(i).getAdDetailContent();
			}else if(adDetailId != null && adDetailId.equals("real_url")) {
				adDetailSeq[3] = pfpAdDetails.get(i).getAdDetailSeq();
				String deCodeUrl = pfpAdDetails.get(i).getAdDetailContent();
				try {
				    deCodeUrl = HttpUtil.getInstance().convertRealUrl(deCodeUrl);
				}
				catch (Exception e) {
				    log.error(deCodeUrl, e);
				}
				adDetailContent[3] = deCodeUrl.replaceAll("http://", "");
				adLinkURL = deCodeUrl;
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
			}
		}

		if(adDetailSeq[0] == null) {
			adDetailSeq[0] = "";
			adDetailContent[0] = "img/public/na.gif\" style=\"display:none";
			imgFile = "";
		}	
		
		return SUCCESS;
	}
	
	private void addAccesslog(EnumAccesslogAction enumAccesslogAction,String accesslogMessage) throws Exception{
		admAccesslogService.recordAdLog(enumAccesslogAction, accesslogMessage, super.getId_pchome(), super.getCustomer_info_id(), super.getUser_id(), request.getRemoteAddr());
	}
	
	private boolean checkDetailChange(String oldDetail, String newDetail){
		boolean clk = false;
		if(!StringUtils.equals(oldDetail, newDetail)){
			clk = true;
		}
		return clk;
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

	public String getAdLinkURL() {
		return adLinkURL;
	}

	public void setAdLinkURL(String adLinkURL) {
		this.adLinkURL = adLinkURL;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getImgWidth() {
		return imgWidth;
	}

	public void setImgWidth(String imgWidth) {
		this.imgWidth = imgWidth;
	}

	public String getImgHeight() {
		return imgHeight;
	}

	public void setImgHeight(String imgHeight) {
		this.imgHeight = imgHeight;
	}

	public String getImgSize() {
		return imgSize;
	}

	public void setImgSize(String imgSize) {
		this.imgSize = imgSize;
	}

	public String getImgTypeName() {
		return imgTypeName;
	}

	public void setImgTypeName(String imgTypeName) {
		this.imgTypeName = imgTypeName;
	}

	public String getAdHiddenType() {
		return adHiddenType;
	}

	public void setAdHiddenType(String adHiddenType) {
		this.adHiddenType = adHiddenType;
	}

	public String getAdDetailTitleSeq() {
		return adDetailTitleSeq;
	}

	public void setAdDetailTitleSeq(String adDetailTitleSeq) {
		this.adDetailTitleSeq = adDetailTitleSeq;
	}

	public String getImgTitle() {
		return imgTitle;
	}

	public void setImgTitle(String imgTitle) {
		this.imgTitle = imgTitle;
	}

	public int getAdGroupStatus() {
		return adGroupStatus;
	}

	public void setAdGroupStatus(int adGroupStatus) {
		this.adGroupStatus = adGroupStatus;
	}

	public int getAdActionStatus() {
		return adActionStatus;
	}

	public void setAdActionStatus(int adActionStatus) {
		this.adActionStatus = adActionStatus;
	}

	public String getAdType() {
		return adType;
	}

	public void setAdType(String adType) {
		this.adType = adType;
	}

	public String getAdKeywordOpen() {
		return adKeywordOpen;
	}

	public void setAdKeywordOpen(String adKeywordOpen) {
		this.adKeywordOpen = adKeywordOpen;
	}

	public String getAdKeywordPhraseOpen() {
		return adKeywordPhraseOpen;
	}

	public void setAdKeywordPhraseOpen(String adKeywordPhraseOpen) {
		this.adKeywordPhraseOpen = adKeywordPhraseOpen;
	}

	public String getAdKeywordPrecisionOpen() {
		return adKeywordPrecisionOpen;
	}

	public void setAdKeywordPrecisionOpen(String adKeywordPrecisionOpen) {
		this.adKeywordPrecisionOpen = adKeywordPrecisionOpen;
	}

	public String getHtml5Flag() {
		return html5Flag;
	}

	public String getZipTitle() {
		return zipTitle;
	}

	public String getAdVideoURL() {
		return adVideoURL;
	}

	public void setAdVideoURL(String adVideoURL) {
		this.adVideoURL = adVideoURL;
	}

	public String getPhotoDbPathNew() {
		return photoDbPathNew;
	}

	public void setPhotoDbPathNew(String photoDbPathNew) {
		this.photoDbPathNew = photoDbPathNew;
	}

}
