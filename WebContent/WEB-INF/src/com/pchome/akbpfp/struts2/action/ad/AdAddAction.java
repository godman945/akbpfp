package com.pchome.akbpfp.struts2.action.ad;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
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
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.oscache.util.StringUtil;
import com.pchome.akbpfp.api.ControlPriceAPI;
import com.pchome.akbpfp.api.SyspriceOperaterAPI;
import com.pchome.akbpfp.db.pojo.AdmDefineAd;
import com.pchome.akbpfp.db.pojo.PfbxSize;
import com.pchome.akbpfp.db.pojo.PfpAd;
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
import com.pchome.akbpfp.struts2.BaseCookieAction;
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
	//判斷是否多檔上傳
	private String multipartImgUupload;
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
	private ControlPriceAPI controlPriceAPI;
	
	private File[] uploadFile;
	
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
			adSeq = sequenceService.getId(EnumSequenceTableName.PFP_AD, "_");
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
	 * */
	public String adAddImgView(){
	    adStyle = "TMG";
	    multipartImgUupload = "YES";
	    pfbSizeList = pfbSizeService.loadAll();
	    return SUCCESS;
	}
	
	
	/**
	 * 進行批次上傳圖像廣告
	 * 
	 * */
	@SuppressWarnings("resource")
	public String uploadImg() throws Exception{
	    System.out.println("FFF");
	    result = "alex";
	    
	    String customerInfoId = super.getCustomer_info_id();
	    String userImgPath = "/home/webuser/akb/pfp/alex_test/";
	    BufferedOutputStream bufferOutput = null;  
	    
	    Date date = new Date();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	    File customerImgFile = null;
	    File customerImgFileDateFile = null;
	    File customerImgFileOriginalDateFile = null;
	    customerImgFile = new File(userImgPath+customerInfoId);
	    CommonUtilModel commonUtilModel = new CommonUtilModel();
	    for (File file : uploadFile) {
		File originalImgFile = file;
		BufferedImage bufferedImage = ImageIO.read(originalImgFile);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(bufferedImage, "jpg", baos);
		baos.flush();
		byte[] originalImgByte = baos.toByteArray();
		baos.close();
		
		if(!customerImgFile.exists()){
		    customerImgFile.mkdirs();
		}
		customerImgFileDateFile = new File(userImgPath+customerInfoId+"\\"+sdf.format(date));
		if(!customerImgFileDateFile.exists()){
		    customerImgFileDateFile.mkdirs();
		    customerImgFileOriginalDateFile = new File(userImgPath+customerInfoId+"\\"+sdf.format(date)+"\\original");
		    customerImgFileOriginalDateFile.mkdirs();
		    commonUtilModel.writeImg(bufferedImage,userImgPath,customerInfoId, sdf.format(date));
		}else{
		    commonUtilModel.writeImg(bufferedImage,userImgPath,customerInfoId, sdf.format(date));
		}
		
		
		
		
		
		
		
//		byte[] resizedBytes = baos.toByteArray();
//		File resizedImgFile = new File("/home/webuser/akb/pfp/alex_test/test.png");
//		FileOutputStream fos = new FileOutputStream(resizedImgFile);
//		fos.write(resizedBytes);
//		fos.close();
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
//		System.out.println(file.getName());
//		OutputStream os = new FileOutputStream(file);
//		byte[] buffer = new byte[1024];
//		int length = 0;
//		
//		InputStream is = new FileInputStream(file);
//		while((length = is.read(buffer)) > 0) {
//		    os.write(buffer, 0, length);
//		}
////		is.close();
////		os.close();
//		
//		
//		 FileOutputStream file3 =new FileOutputStream("D:/alex_test/upload_bed33ec_14e948f7bd2__7ff5_00000002.tmp");
//		 while((length = is.read(buffer)) > 0) {
//		     file3.write(buffer, 0, length);
//		 }
//		 file3.close();
//		 is.close();
//		 os.close();
			
		 
		 
		 
//		 BufferedInputStream bi= new BufferedInputStream(is);
//		 while((length = is.read(buffer)) > 0) {
//		     file3.write(buffer, 0, length);
//		 }
		 
//		 while(bi.available()>0){
//		     file3.write(bi.read());
//		 }
//		 file3.flush();
//		 file3.close();
//		 bi.close();
//		 is.close();
		
		
		
		
		
		
		
		
//		bufferOutput = new BufferedOutputStream(new FileOutputStream(new File(path)),1024);  
//		bufferOutput.write(buffer);  
//		bufferOutput.flush();  
	    }
	    return SUCCESS;
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

	public File[] getUploadFile() {
	    return uploadFile;
	}

	public void setUploadFile(File[] uploadFile) {
	    this.uploadFile = uploadFile;
	}




}
