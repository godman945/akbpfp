package com.pchome.akbpfp.struts2.action.ad;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.oscache.util.StringUtil;
import com.pchome.akbpfp.api.ControlPriceAPI;
import com.pchome.akbpfp.api.SyspriceOperaterAPI;
import com.pchome.akbpfp.db.pojo.AdmDefineAd;
import com.pchome.akbpfp.db.pojo.PfbxSize;
import com.pchome.akbpfp.db.pojo.PfpAd;
import com.pchome.akbpfp.db.pojo.PfpAdDetail;
import com.pchome.akbpfp.db.pojo.PfpAdExcludeKeyword;
import com.pchome.akbpfp.db.pojo.PfpAdGroup;
import com.pchome.akbpfp.db.pojo.PfpAdKeyword;
import com.pchome.akbpfp.db.pojo.PfpCustomerInfo;
import com.pchome.akbpfp.db.service.ad.DefineAdService;
import com.pchome.akbpfp.db.service.ad.PfpAdDetailService;
import com.pchome.akbpfp.db.service.ad.PfpAdExcludeKeywordService;
import com.pchome.akbpfp.db.service.ad.PfpAdGroupService;
import com.pchome.akbpfp.db.service.ad.PfpAdKeywordService;
import com.pchome.akbpfp.db.service.ad.PfpAdService;
import com.pchome.akbpfp.db.service.customerInfo.PfpCustomerInfoService;
import com.pchome.akbpfp.db.service.pfbx.IPfbSizeService;
import com.pchome.akbpfp.db.service.sequence.ISequenceService;
import com.pchome.akbpfp.db.vo.ad.PfpAdDetailVO;
import com.pchome.akbpfp.godutil.CommonUtilModel;
import com.pchome.akbpfp.godutil.ImageVO;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.ad.EnumAdDetail;
import com.pchome.enumerate.ad.EnumAdSize;
import com.pchome.enumerate.ad.EnumAdStyle;
import com.pchome.enumerate.ad.EnumExcludeKeywordStatus;
import com.pchome.enumerate.sequence.EnumSequenceTableName;
import com.pchome.enumerate.utils.EnumStatus;
import com.pchome.soft.depot.utils.HttpUtil;


public class AdAddAction extends BaseCookieAction{

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
	private String[] adDetailID;
	private String[] adDetailContent;
	private String adDetailSeq;
	private String adPoolSeq;
	private String templateProductSeq;
	private List<PfpAdKeyword> pfpAdKeywords;
	private List<PfpAdExcludeKeyword> pfpAdExcludeKeywords;
	private String[] keywords;
	private String[] excludeKeywords;
	private String saveAndNew;
	private String ulTmpName;
	private String imgFile;
	private String backPage;				// 取消的返回頁面
	private String divBatchWord = "display:none;";		// 為了配合 adFreeAdAddKeyword 做的設定
	private String batchkeywords = "";					// 為了配合 adFreeAdAddKeyword 做的設定

	private String fileContentType;
	private String photoTmpPath;
	private String photoPath;
	private String photoDbPath;
	// return data
	private InputStream msg;
	private String result;

	private PfpCustomerInfoService pfpCustomerInfoService;
	private ISequenceService sequenceService;
	private PfpAdGroupService pfpAdGroupService;
	private PfpAdService pfpAdService;
	private PfpAdDetailService pfpAdDetailService;
	private PfpAdKeywordService pfpAdKeywordService;
	private PfpAdExcludeKeywordService pfpAdExcludeKeywordService;
	private DefineAdService defineAdService;
	private SyspriceOperaterAPI syspriceOperaterAPI;
	private IPfbSizeService pfbSizeService;
	//廣告支援尺寸表
	private List<PfbxSize> pfbSizeList = new ArrayList<PfbxSize>();
	//圖片上傳位置
	private String imgUploadPath;
	private ControlPriceAPI controlPriceAPI;
	private String seqArray;
	private File[] fileupload;
	private String adLinkURL;
	private String photoDbPathNew;

	public String AdAdAdd() throws Exception {
		log.info("AdAdAdd => adGroupSeq = " + adGroupSeq);
		String referer = request.getHeader("Referer");
		backPage = "adActionView.html";
		if(StringUtils.isNotEmpty(referer)) {
			if(referer.indexOf("adGroupAdd.html") >= 0 || referer.indexOf("adAdAdd.html") >= 0) {
				backPage = "adGroupAdd.html?adGroupSeq=" + adGroupSeq;
			} else {
				backPage = referer;
			}
			if(referer.indexOf("adGroupAdd.html") >= 0 ){
				// 重算調控金額
				controlPriceAPI.countProcess(pfpCustomerInfoService.findCustomerInfo(super.getCustomer_info_id()));
			}
		}

		saveAndNew = "";
		if(adStyle == null)		adStyle = "TMG";

		PfpAdGroup pfpAdGroup = pfpAdGroupService.getPfpAdGroupBySeq(adGroupSeq);
		adActionName  = pfpAdGroup.getPfpAdAction().getAdActionName();
		adGroupName  = pfpAdGroup.getAdGroupName();

		PfpCustomerInfo pfpCustomerInfo = pfpCustomerInfoService.findCustomerInfo(super.getCustomer_info_id());
		String customerInfoId = pfpCustomerInfo.getCustomerInfoId();
		String adCustomerInfoId = pfpAdGroup.getPfpAdAction().getPfpCustomerInfo().getCustomerInfoId();
		if(!customerInfoId.equals(adCustomerInfoId)) {
			return "notOwner";
		}

		// 取出分類所屬關鍵字
		pfpAdKeywords = pfpAdKeywordService.findAdKeywords(null, adGroupSeq, null, null, null, "10");
		// 取出分類所屬排除關鍵字
		pfpAdExcludeKeywords = pfpAdExcludeKeywordService.getPfpAdExcludeKeywords(adGroupSeq, pfpCustomerInfo.getCustomerInfoId());

		// 上傳圖片暫存檔名(亂數產生)
		ulTmpName = RandomStringUtils.randomAlphanumeric(30);
		imgFile = "";

		return SUCCESS;
	}

	// 新增圖文式廣告
	@Transactional
	public String doAdAdAddTmg() throws Exception {
		log.info("doAdAdAddTmg => adGroupSeq = " + adGroupSeq + "; saveAndNew = '" + saveAndNew + "'");
//		 result = "noAdd";

		// 檢查 adStyle 是否正確，正確的話，設定 adPoolSeq、templateProductSeq
		chkAdStyle();

		// 檢查 Form 資料是否正確
		chkAdData1();
		if(message != null && !message.equals("")) {
		    msg = new ByteArrayInputStream(message.getBytes());
		    return INPUT;
		}

		PfpAdGroup pfpAdGroup = pfpAdGroupService.getPfpAdGroupBySeq(adGroupSeq);

		// 新增廣告
		addAd(pfpAdGroup);

		String imgDetail = "";
		PfpAdDetailVO pfpAdDetailVO = new PfpAdDetailVO();
		for(int i = 0; i < adDetailID.length; i++) {
		    if(i == 0 && adStyle.equals("TMG")) {
			try {
			    if(StringUtils.isNotBlank(imgFile)) {
				File iPath = new File(photoPath);		// 圖片的存放路徑
				File iTmpPath = new File(photoTmpPath);	// 暫存圖片的路徑
				if(!iPath.exists())			iPath.mkdirs();
				if(!iTmpPath.exists())		iTmpPath.mkdirs();

						File adFile = new File(photoPath, adSeq + ".jpg");	// 上傳圖片的檔名
						File tmpFile = new File(imgFile);	// 設定圖片的 File 元件
						tmpFile.renameTo(adFile);			// 把暫存圖片搬到存放區

						imgDetail = photoDbPath + adFile.getName();	// 設定圖片檔存放在 DB 的路徑
					} else {
						if(StringUtils.isBlank(adDetailContent[0])) {
							imgDetail = "img/public/na.gif\" style=\"display:none";
						}
					}
				} catch (Exception ex) {
					log.info("ex : " + ex);
				}

			}

			adDetailSeq = sequenceService.getId(EnumSequenceTableName.PFP_AD_DETAIL, "_");
			List<AdmDefineAd> admDefineAd = defineAdService.getDefineAdByCondition(null, adDetailID[i], null, adPoolSeq);
			String defineAdSeq = admDefineAd.get(0).getDefineAdSeq();
			if(adDetailID[i].equals("real_url")) {
				if(adDetailContent[i].indexOf("http") < 0 ) {
					adDetailContent[i] = HttpUtil.getInstance().getRealUrl("http://" +adDetailContent[i]);
				}else{
				    	adDetailContent[i] = HttpUtil.getInstance().getRealUrl(adDetailContent[i]);
				}
			    	adDetailContent[i] = HttpUtil.getInstance().getRealUrl(adDetailContent[i]);
			}
			if(adDetailID[i].equals("show_url")) {
			    if(adDetailContent[i].indexOf("http://") < 0 ) {
				adDetailContent[i] = HttpUtil.getInstance().getRealUrl("http://" +adDetailContent[i]);
			    }else{
				adDetailContent[i] = HttpUtil.getInstance().getRealUrl(adDetailContent[i]);
			    }
//			    adDetailContent[i] = (HttpUtil.getInstance().getRealUrl(adDetailContent[i]).replace("http://", ""));
			    adDetailContent[i] = HttpUtil.getInstance().convertRealUrl(adDetailContent[i]);
			}

			String detailContent = i == 0?imgDetail:adDetailContent[i];
			pfpAdDetailVO.setAdDetailSeq(adDetailSeq);
			pfpAdDetailVO.setAdSeq(adSeq);
			pfpAdDetailVO.setAdPoolSeq(adPoolSeq);
			pfpAdDetailVO.setAdDetailId(adDetailID[i]);
			pfpAdDetailVO.setAdDetailContent(detailContent);
			if(adDetailID[i].equals("img") || adDetailID[i].equals("title") || adDetailID[i].equals("content")) {
			    pfpAdDetailVO.setVerifyFlag("y");
			} else {
			    pfpAdDetailVO.setVerifyFlag("n");
			}
			pfpAdDetailVO.setDefineAdSeq(defineAdSeq);
			pfpAdDetailVO.setAdDetailCreateTime(new Date());
			pfpAdDetailVO.setAdDetailUpdateTime(new Date());
			pfpAdDetailService.savePfpAdDetail(pfpAdDetailVO);
		}

		// 新增關鍵字
		addKeywords(pfpAdGroup);
		//新增排除關鍵字
		addExcludeKeywords(pfpAdGroup);

		// 開啟廣告分類
		pfpAdGroup.setAdGroupStatus(4);
		pfpAdGroupService.save(pfpAdGroup);

		// 是否為 "儲存後再新增廣告"
		if(saveAndNew != null && saveAndNew.equals("save+new")) {
		    result = "saveNew";
		} else {
		    result = "saveOK";
		}
		msg = new ByteArrayInputStream(result.getBytes());
//		return INPUT;
		return SUCCESS;
	}

	// 新增文字式廣告
	@Transactional
	public String doAdAdAddTxt() throws Exception {
		log.info("doAdAdAdd => adGroupSeq = " + adGroupSeq + "; saveAndNew = '" + saveAndNew + "'");
		// 檢查 adStyle 是否正確，正確的話，設定 adPoolSeq、templateProductSeq
		chkAdStyle();
		// 檢查 Form 資料是否正確
		chkAdData1();
		if(message != null && !message.equals("")) {
			return INPUT;
		}
		PfpAdGroup pfpAdGroup = pfpAdGroupService.getPfpAdGroupBySeq(adGroupSeq);
		// 新增廣告
		addAd(pfpAdGroup);
		PfpAdDetailVO pfpAdDetailVO = new PfpAdDetailVO();
		for(int i = 0; i < adDetailID.length; i++) {
		    adDetailSeq = sequenceService.getId(EnumSequenceTableName.PFP_AD_DETAIL, "_");
		    List<AdmDefineAd> admDefineAd = defineAdService.getDefineAdByCondition(null, adDetailID[i], null, adPoolSeq);
		    String defineAdSeq = admDefineAd.get(0).getDefineAdSeq();
		    if(adDetailID[i].equals("real_url") || adDetailID[i].equals("show_url")) {
			if(adDetailContent[i].indexOf("http") !=0 ) {
			    adDetailContent[i] = "http://" + adDetailContent[i];
			}
		    }
		pfpAdDetailVO.setAdDetailSeq(adDetailSeq);
		pfpAdDetailVO.setAdSeq(adSeq);
		pfpAdDetailVO.setAdPoolSeq(adPoolSeq);
		pfpAdDetailVO.setAdDetailId(adDetailID[i]);
		pfpAdDetailVO.setAdDetailContent(adDetailContent[i]);
		if(adDetailID[i].equals("img") || adDetailID[i].equals("title") || adDetailID[i].equals("content")) {
		    pfpAdDetailVO.setVerifyFlag("y");
		} else {
		    pfpAdDetailVO.setVerifyFlag("n");
		}
		pfpAdDetailVO.setDefineAdSeq(defineAdSeq);
		pfpAdDetailVO.setAdDetailCreateTime(new Date());
		pfpAdDetailVO.setAdDetailUpdateTime(new Date());
		pfpAdDetailService.savePfpAdDetail(pfpAdDetailVO);
		}

		// 新增關鍵字
		addKeywords(pfpAdGroup);
		//新增排除關鍵字
		addExcludeKeywords(pfpAdGroup);

		// 開啟廣告分類
		pfpAdGroup.setAdGroupStatus(4);
		pfpAdGroupService.save(pfpAdGroup);

		// 是否為 "儲存後再新增廣告"
		if(saveAndNew != null && saveAndNew.equals("save+new")) {
			return "saveNew";
		}
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
							message = "廣告連結網址不可超過 1024字！";
						} else {
							String url = adDetailContent[i];
							int urlState = 0;
							if(url.indexOf("http") != 0) {
								url = "http://"+url;
							}
							urlState = HttpUtil.getInstance().getStatusCode(url);
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
					    	if(url.indexOf("http") != 0) {
					    	    url = "http://"+url;
					    	}
					    	urlState = HttpUtil.getInstance().getStatusCode(url);
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

	//新增廣告
	private void addAd(PfpAdGroup pfpAdGroup) {
		try {
		    	log.info(">>>>> time: " + new Date());

		    	if(adSeq == null || StringUtils.isBlank(adSeq)){
		    	    adSeq = sequenceService.getId(EnumSequenceTableName.PFP_AD, "_");
		    	}
			PfpAd pfpAd = new PfpAd();
			pfpAd.setAdSeq(adSeq);
			pfpAd.setPfpAdGroup(pfpAdGroup);
			pfpAd.setAdClass(adClass);
			pfpAd.setAdStyle(adStyle);
			pfpAd.setTemplateProductSeq(templateProductSeq);
			pfpAd.setAdSearchPrice(pfpAdGroup.getAdGroupSearchPrice());
			pfpAd.setAdChannelPrice(pfpAdGroup.getAdGroupChannelPrice());
			pfpAd.setAdStatus(EnumStatus.NoVerify.getStatusId());
			pfpAd.setAdSendVerifyTime(new Date());
			pfpAd.setAdCreateTime(new Date());
			pfpAd.setAdUpdateTime(new Date());
			pfpAdService.savePfpAd(pfpAd);
		} catch(Exception ex) {
			log.info("Exception ex" + ex);
		}
	}

	// 新增關鍵字
	private void addKeywords(PfpAdGroup pfpAdGroup) {
	    try {
		if(keywords.length == 0){
		    return;
		}
		List<String> adKeywords = this.checkKeywords(adGroupSeq, keywords);
		if(!adKeywords.isEmpty()){
		    for(String keyword:adKeywords){
			String adKeywordSeq = sequenceService.getId(EnumSequenceTableName.PFP_AD_KEYWORD, "_");
			PfpAdKeyword pfpAdKeyword = new PfpAdKeyword();
			pfpAdKeyword.setAdKeywordSeq(adKeywordSeq);
			pfpAdKeyword.setPfpAdGroup(pfpAdGroup);
			pfpAdKeyword.setAdKeyword(keyword);
			pfpAdKeyword.setAdKeywordSearchPrice(pfpAdGroup.getAdGroupSearchPrice());
			pfpAdKeyword.setAdKeywordChannelPrice(pfpAdGroup.getAdGroupChannelPrice());
			pfpAdKeyword.setAdKeywordOrder(0);
			pfpAdKeyword.setAdKeywordStatus(EnumStatus.Open.getStatusId());
			pfpAdKeyword.setAdKeywordCreateTime(new Date());
			pfpAdKeyword.setAdKeywordUpdateTime(new Date());
			pfpAdKeywordService.saveOrUpdate(pfpAdKeyword);
			syspriceOperaterAPI.addKeywordSysprice(keyword, pfpAdGroup.getAdGroupSearchPrice());
		    }
		}

//		if(keywords != null && keywords.length > 0) {
//		    for(int i = 0; i < keywords.length; i++) {
//			List<PfpAdKeyword> KWExist = pfpAdKeywordService.findAdKeywords(null, adGroupSeq, keywords[i], null, null, "10");
//			    //log.info("keywords["+i+"] = " + keywords[i] + "; existCount =" + KWExist.size());
//			if(KWExist.size() == 0) {
//			    String adKeywordSeq = sequenceService.getId(EnumSequenceTableName.PFP_AD_KEYWORD, "_");
//			    PfpAdKeyword pfpAdKeyword = new PfpAdKeyword();
//			    pfpAdKeyword.setAdKeywordSeq(adKeywordSeq);
//			    pfpAdKeyword.setPfpAdGroup(pfpAdGroup);
//			    pfpAdKeyword.setAdKeyword(keywords[i]);
//			    pfpAdKeyword.setAdKeywordSearchPrice(pfpAdGroup.getAdGroupSearchPrice());
//			    pfpAdKeyword.setAdKeywordChannelPrice(pfpAdGroup.getAdGroupChannelPrice());
//			    pfpAdKeyword.setAdKeywordOrder(0);
//			    pfpAdKeyword.setAdKeywordStatus(EnumStatus.Open.getStatusId());
//			    pfpAdKeyword.setAdKeywordCreateTime(new Date());
//			    pfpAdKeyword.setAdKeywordUpdateTime(new Date());
//			    pfpAdKeywordService.savePfpAdKeyword(pfpAdKeyword);
//			    syspriceOperaterAPI.addKeywordSysprice(keywords[i], pfpAdGroup.getAdGroupSearchPrice());
//			  }
//		    }
//		}
	} catch(Exception ex) {
	    log.info("Exception(addKeywords) : " + ex);
	}
	}




	/**
	 * 檢查是否重覆新增
	 * 1. 分類下已新增關鍵字
	 * 2. 分類下已排除關鍵字
	 */
	private List<String> checkKeywords(String adGroupSeq, String[] keywords){
		List<String> list = null;
		if(StringUtils.isNotBlank(adGroupSeq) && keywords != null && StringUtils.isNotBlank(keywords.toString())){
		    list = new ArrayList<String>();
		    List<String> existKeywords = new ArrayList<String>();
			// 分類下已新增關鍵字
			List<PfpAdKeyword> adKeywords = pfpAdKeywordService.findAdKeywords(adGroupSeq);
			for(PfpAdKeyword keyword:adKeywords){
			    existKeywords.add(keyword.getAdKeyword());
			}
			// 分類下已排除關鍵字
			List<PfpAdExcludeKeyword> adExcludeKeywords =pfpAdExcludeKeywordService.findAdExcludeKeywords(adGroupSeq);
			for(PfpAdExcludeKeyword keyword:adExcludeKeywords){
			    existKeywords.add(keyword.getAdExcludeKeyword());
			}
			log.info(" keywords size: "+keywords.length);
			log.info(" existKeywords size: "+existKeywords.size());
			if(existKeywords.isEmpty()){
			    for(int i=0;i<keywords.length;i++){
				list.add(keywords[i].trim());
			    }
			}else{
			    for(int i=0;i<keywords.length;i++){
				for(String keyword:existKeywords){
				    if(!keyword.equals(keywords[i].trim())){
					list.add(keywords[i].trim());
					break;
				    }else{
					log.info(" keywords[i]: "+keywords[i].trim());
				    }
				}
			    }
			}
		}
		else{
		    log.info(" check keywords is null");
		}
		return list;
	}

	// 新增排除關鍵字
	private void addExcludeKeywords(PfpAdGroup pfpAdGroup) {
		try {
			if(excludeKeywords != null && excludeKeywords.length > 0) {
				for(int i = 0; i < excludeKeywords.length; i++) {
					//log.info("excludeKeywords["+i+"] = " + excludeKeywords[i]);
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

	/**
	 * 批次上傳圖像廣告
	 * 1.取得初始畫面
	 * 2.取得尺寸列表
	 * @throws Exception
	 * */
	public String adAddImgView() throws Exception{
	    PfpAdGroup pfpAdGroup = pfpAdGroupService.getPfpAdGroupBySeq(adGroupSeq);
	    adActionName  = pfpAdGroup.getPfpAdAction().getAdActionName();
	    adGroupName  = pfpAdGroup.getAdGroupName();
	    adStyle = "TMG";
	    List<PfbxSize> pfbSizeList = pfbSizeService.loadAll();
	    for (EnumAdSize enumAdSize : EnumAdSize.values()) {
		for (PfbxSize pfbxSize : pfbSizeList) {
		    if(String.valueOf(pfbxSize.getId()).equals(enumAdSize.getName())){
			this.pfbSizeList.add(pfbxSize);
		    }
		}
	    }
	    // 取出分類所屬關鍵字
	    pfpAdKeywords = pfpAdKeywordService.findAdKeywords(null, adGroupSeq, null, null, null, "10");
	    // 取出分類所屬排除關鍵字
	    pfpAdExcludeKeywords = pfpAdExcludeKeywordService.getPfpAdExcludeKeywords(adGroupSeq, super.getCustomer_info_id());
	    return SUCCESS;
	}



	/**
	 * 新增廣告明細
	 * */
	public void saveAdDetail(String content ,String adDetailId,String adPoolSeq,String defineAdSeq) throws Exception{
	    templateProductSeq = EnumAdStyle.IMG.getTproSeq();
	    adDetailSeq = sequenceService.getId(EnumSequenceTableName.PFP_AD_DETAIL, "_");
	    PfpAdDetail pfpAdDetail = new PfpAdDetail();
	    pfpAdDetail.setAdDetailSeq(adDetailSeq);
	    pfpAdDetail.setPfpAd(pfpAdService.getPfpAdBySeq(adSeq));
	    pfpAdDetail.setAdDetailId(adDetailId);
	    pfpAdDetail.setAdDetailContent(content);
	    pfpAdDetail.setAdPoolSeq(adPoolSeq);
	    pfpAdDetail.setDefineAdSeq(defineAdSeq);
	    pfpAdDetail.setVerifyFlag("y");
	    pfpAdDetail.setVerifyStatus("n");
	    pfpAdDetail.setAdDetailCreateTime(new Date());
	    pfpAdDetail.setAdDetailUpdateTime(new Date());
	    pfpAdDetailService.savePfpAdDetail(pfpAdDetail);
	}

	/**
	 * 進行批次上傳圖像廣告
	 * 1.圖片存檔至暫存file
	 * */
	public String uploadImg() throws Exception{
	    log.info("Start multipart img upload");
	    adPoolSeq = EnumAdStyle.IMG.getAdPoolSeq();
	    templateProductSeq = EnumAdStyle.IMG.getTproSeq();
	    adClass = "1";
	    adStyle = "IMG";
	    String customerInfoId = super.getCustomer_info_id();
	    Date date = new Date();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	    File customerImgFile = null;
	    File customerImgFileDateFile = null;
	    File customerImgFileOriginalDateFile = null;
	    File customerImgFileTemporalDateFile = null;
	    customerImgFile = new File(photoDbPathNew+customerInfoId);
	    CommonUtilModel commonUtilModel = new CommonUtilModel();

	    if(fileupload == null){
		return SUCCESS;
	    }
	    String imgWidth ="";
	    String imgHeight ="";
	    String fileSize= "";
	    imgUploadPath = "";
	    for (File file : fileupload) {
		File originalImgFile = file;
		BufferedImage bufferedImage = ImageIO.read(originalImgFile);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(bufferedImage, "jpg", baos);
		baos.flush();
		baos.close();
		 log.info(">>>1.path>>"+photoDbPathNew+customerInfoId);
		 log.info(">>>2.path>>"+customerImgFile.getPath());
		 log.info(customerImgFile.exists());
		if(!customerImgFile.exists()){
		    log.info(">>>3.path>>"+photoDbPathNew+customerInfoId);
		    customerImgFile.mkdirs();
		}
		customerImgFileDateFile = new File(photoDbPathNew+customerInfoId+"/"+sdf.format(date));
		if(!customerImgFileDateFile.exists()){
		    customerImgFileDateFile.mkdirs();
		    customerImgFileOriginalDateFile = new File(photoDbPathNew+customerInfoId+"/"+sdf.format(date)+"/original");
		    customerImgFileOriginalDateFile.mkdirs();
		    customerImgFileTemporalDateFile = new File(photoDbPathNew+customerInfoId+"/"+sdf.format(date)+"/temporal");
		    if(!customerImgFileTemporalDateFile.exists()){
			customerImgFileTemporalDateFile.mkdirs();
		    }
		    adSeq = "";
		    adSeq = sequenceService.getId(EnumSequenceTableName.PFP_AD, "_");
		    commonUtilModel.writeImg(bufferedImage,photoDbPathNew,customerInfoId, sdf.format(date),adSeq);
		    imgWidth = String.valueOf(bufferedImage.getWidth());
		    imgHeight = String.valueOf(bufferedImage.getHeight());
		}else{
		    adSeq = "";
		    adSeq = sequenceService.getId(EnumSequenceTableName.PFP_AD, "_");
		    commonUtilModel.writeImg(bufferedImage,photoDbPathNew,customerInfoId, sdf.format(date),adSeq);
		    imgWidth = String.valueOf(bufferedImage.getWidth());
		    imgHeight = String.valueOf(bufferedImage.getHeight());
		    fileSize = String.valueOf(file.length() / 1024);
		}
		result = "{\"adSeq\":\"" + adSeq + "\","+ "\"imgWidth\":\"" + imgWidth +"\"," +   "\"imgHeight\":\"" + imgHeight +"\",  "+    "\"fileSize\":\"" + fileSize +"\" "+ "}";
	    }
	    return SUCCESS;
	}
	/**
	 * 儲存資料
	 * */
	public String uploadImgSave() throws Exception{
	PfpAdGroup pfpAdGroup = pfpAdGroupService.getPfpAdGroupBySeq(adGroupSeq);
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

	adClass = "1";
	adStyle = "IMG";
	templateProductSeq = EnumAdStyle.IMG.getTproSeq();
	JSONObject seqArrayJsonObject = new JSONObject(seqArray.toString());
	JSONArray seqArray = new JSONArray(seqArrayJsonObject.get("seqArray").toString());
	// 1.存廣告檔
	// 2.刪暫存圖檔
	CommonUtilModel commonUtilModel = new CommonUtilModel();
	String customerInfoId = super.getCustomer_info_id();
	Date date = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	ImageVO imageVO = new ImageVO();
	if (seqArray.length() > 0) {
	    for (int i = 0; i < seqArray.length(); i++) {
		adSeq = seqArray.get(i).toString();
		imageVO = commonUtilModel.createAdImg(photoDbPathNew,customerInfoId, sdf.format(date), seqArray.get(i).toString());
		String adPoolSeq = "";
		for (EnumAdSize enumAdSize : EnumAdSize.values()) {
		    if (imageVO.getImgWidth().equals(enumAdSize.getWidh())  && imageVO.getImgHeight().equals(enumAdSize.getHeight())) {
			adPoolSeq = enumAdSize.name();
			break;
		    }
		}
		if (StringUtil.isEmpty(adPoolSeq)) {
		    result = "error";
		    return "success";
		}
		addAd(pfpAdGroup);
		String path = imageVO.getImgPath().replace("\\", "/");
		path = path.replace("/export/home/webuser/akb/pfp/", "");
		saveAdDetail(path,EnumAdDetail.img.name(), adPoolSeq,EnumAdDetail.define_ad_seq_img.getAdDetailName());
		if (StringUtils.isNotBlank(adLinkURL) && (adLinkURL.indexOf("http://") != 0)) {
		    adLinkURL = "http://" + adLinkURL;
		}
		saveAdDetail(adLinkURL,EnumAdDetail.real_url.getAdDetailName(), adPoolSeq,EnumAdDetail.define_ad_seq_real_url.getAdDetailName());
	    }
	}
	// 刪除暫存檔
	commonUtilModel.deleteAllTemporalImg(photoDbPathNew, customerInfoId,sdf.format(date));
	result = "success";
	return "success";
    }

	public List<PfbxSize> getPfbSizeList() {
	    return pfbSizeList;
	}

	public void setPfbSizeList(List<PfbxSize> pfbSizeList) {
	    this.pfbSizeList = pfbSizeList;
	}

	public void setPfpCustomerInfoService(
			PfpCustomerInfoService pfpCustomerInfoService) {
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

	public void setDefineAdService(DefineAdService defineAdService) {
		this.defineAdService = defineAdService;
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

	public String[] getAdDetailID() {
		return adDetailID;
	}

	public void setAdDetailID(String[] adDetailID) {
		this.adDetailID = adDetailID;
	}

	public String[] getAdDetailContent() {
		return adDetailContent;
	}

	public void setAdDetailContent(String[] adDetailContent) {
		this.adDetailContent = adDetailContent;
	}

	public String getAdDetailSeq() {
		return adDetailSeq;
	}

	public void setAdDetailSeq(String adDetailSeq) {
		this.adDetailSeq = adDetailSeq;
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

	public String getSaveAndNew() {
		return saveAndNew;
	}

	public void setSaveAndNew(String saveAndNew) {
		this.saveAndNew = saveAndNew;
	}

	public String getUlTmpName() {
		return ulTmpName;
	}

	public void setUlTmpName(String ulTmpName) {
		this.ulTmpName = ulTmpName;
	}

	public String getImgFile() {
		return imgFile;
	}

	public void setImgFile(String imgFile) {
		this.imgFile = imgFile;
	}

	public String getBackPage() {
		return backPage;
	}

	public String getDivBatchWord() {
		return divBatchWord;
	}

	public String getBatchkeywords() {
		return batchkeywords;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String getPhotoTmpPath() {
		return photoTmpPath;
	}

	public void setPhotoTmpPath(String photoTmpPath) {
		this.photoTmpPath = photoTmpPath;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public String getPhotoDbPath() {
		return photoDbPath;
	}

	public void setPhotoDbPath(String photoDbPath) {
		this.photoDbPath = photoDbPath;
	}

	public InputStream getMsg() {
		return msg;
	}

	public void setMsg(InputStream msg) {
		this.msg = msg;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setControlPriceAPI(ControlPriceAPI controlPriceAPI) {
		this.controlPriceAPI = controlPriceAPI;
	}

	public IPfbSizeService getPfbSizeService() {
	    return pfbSizeService;
	}

	public void setPfbSizeService(IPfbSizeService pfbSizeService) {
	    this.pfbSizeService = pfbSizeService;
	}


	public String getImgUploadPath() {
	    return imgUploadPath;
	}

	public void setImgUploadPath(String imgUploadPath) {
	    this.imgUploadPath = imgUploadPath;
	}

	public File[] getFileupload() {
	    return fileupload;
	}

	public void setFileupload(File[] fileupload) {
	    this.fileupload = fileupload;
	}

	public String getSeqArray() {
	    return seqArray;
	}

	public void setSeqArray(String seqArray) {
	    this.seqArray = seqArray;
	}

	public String getAdLinkURL() {
	    return adLinkURL;
	}

	public void setAdLinkURL(String adLinkURL) {
	    this.adLinkURL = adLinkURL;
	}

	public String getPhotoDbPathNew() {
	    return photoDbPathNew;
	}

	public void setPhotoDbPathNew(String photoDbPathNew) {
	    this.photoDbPathNew = photoDbPathNew;
	}

}
