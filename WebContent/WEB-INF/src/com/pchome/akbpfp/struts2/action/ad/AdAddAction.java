package com.pchome.akbpfp.struts2.action.ad;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.oscache.util.StringUtil;
import com.pchome.akbpfp.api.ControlPriceAPI;
import com.pchome.akbpfp.api.RedisAPI;
import com.pchome.akbpfp.api.SyspriceOperaterAPI;
import com.pchome.akbpfp.db.pojo.AdmDefineAd;
import com.pchome.akbpfp.db.pojo.PfbxSize;
import com.pchome.akbpfp.db.pojo.PfpAd;
import com.pchome.akbpfp.db.pojo.PfpAdAction;
import com.pchome.akbpfp.db.pojo.PfpAdDetail;
import com.pchome.akbpfp.db.pojo.PfpAdExcludeKeyword;
import com.pchome.akbpfp.db.pojo.PfpAdGroup;
import com.pchome.akbpfp.db.pojo.PfpAdKeyword;
import com.pchome.akbpfp.db.pojo.PfpAdVideoSource;
import com.pchome.akbpfp.db.pojo.PfpCustomerInfo;
import com.pchome.akbpfp.db.service.ad.DefineAdService;
import com.pchome.akbpfp.db.service.ad.IPfpAdActionService;
import com.pchome.akbpfp.db.service.ad.PfpAdDetailService;
import com.pchome.akbpfp.db.service.ad.PfpAdExcludeKeywordService;
import com.pchome.akbpfp.db.service.ad.PfpAdGroupService;
import com.pchome.akbpfp.db.service.ad.PfpAdKeywordService;
import com.pchome.akbpfp.db.service.ad.PfpAdService;
import com.pchome.akbpfp.db.service.admanyurlsearch.IPfpAdManyURLSearchService;
import com.pchome.akbpfp.db.service.advideo.IPfpAdVideoSourceService;
import com.pchome.akbpfp.db.service.customerInfo.PfpCustomerInfoService;
import com.pchome.akbpfp.db.service.pfbx.IPfbSizeService;
import com.pchome.akbpfp.db.service.sequence.ISequenceService;
import com.pchome.akbpfp.db.vo.ad.PfpAdDetailVO;
import com.pchome.akbpfp.db.vo.ad.PfpAdManyURLVO;
import com.pchome.akbpfp.godutil.CommonUtilModel;
import com.pchome.akbpfp.godutil.ImageVO;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.akbpfp.struts2.action.factory.ad.AdFactory;
import com.pchome.akbpfp.struts2.action.intfc.ad.IAd;
import com.pchome.enumerate.ad.EnumAdCannelMobileSize;
import com.pchome.enumerate.ad.EnumAdChannelPCSize;
import com.pchome.enumerate.ad.EnumAdDetail;
import com.pchome.enumerate.ad.EnumAdDevice;
import com.pchome.enumerate.ad.EnumAdPriceType;
import com.pchome.enumerate.ad.EnumAdSearchMobileSize;
import com.pchome.enumerate.ad.EnumAdSearchPCSize;
import com.pchome.enumerate.ad.EnumAdSize;
import com.pchome.enumerate.ad.EnumAdStyle;
import com.pchome.enumerate.ad.EnumAdStyleType;
import com.pchome.enumerate.ad.EnumAdType;
import com.pchome.enumerate.ad.EnumAdVideoDownloadStatus;
import com.pchome.enumerate.ad.EnumAdVideoSizePoolType;
import com.pchome.enumerate.ad.EnumExcludeKeywordStatus;
import com.pchome.enumerate.cookie.EnumCookieConstants;
import com.pchome.enumerate.sequence.EnumSequenceTableName;
import com.pchome.enumerate.utils.EnumStatus;
import com.pchome.soft.depot.utils.CookieUtil;
import com.pchome.soft.depot.utils.HttpUtil;
import com.pchome.soft.depot.utils.SpringZipCompress;






public class AdAddAction extends BaseCookieAction{

	private static final long serialVersionUID = 1L;
	private float adActionMax;
	private String message = "";
	private String adActionSeq;
	private String adActionStartDate;
	private String adActionEndDate;
	private String adActionName;
	private String adGroupSeq;
	private String adGroupName;
	public String adOperatingRule;
	private String adSeq;
	private String adClass;
	public String adStyle;
	private String[] adDetailID;
	private String[] adDetailContent;
	private String[] videoDetailMap;
	private String adVideoURL;
	private String adTitle;
	private String admenuul;
	private String videoTime;
	private String adDetailSeq;
	private String adPoolSeq;
	private String templateProductSeq;
	public List<PfpAdKeyword> pfpAdKeywords;
	public List<PfpAdExcludeKeyword> pfpAdExcludeKeywords;
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
	private String adGroupSearchPriceType;
	private String adGroupSearchPrice;
	private PfpCustomerInfoService pfpCustomerInfoService;
	private ISequenceService sequenceService;
	private PfpAdGroupService pfpAdGroupService;
	private IPfpAdActionService pfpAdActionService;
	private PfpAdService pfpAdService;
	private PfpAdDetailService pfpAdDetailService;
	private PfpAdKeywordService pfpAdKeywordService;
	private PfpAdExcludeKeywordService pfpAdExcludeKeywordService;
	private DefineAdService defineAdService;
	private SyspriceOperaterAPI syspriceOperaterAPI;
	private IPfbSizeService pfbSizeService;
	private IPfpAdVideoSourceService pfpAdVideoSourceService;
	
	//廣告支援尺寸表
	private List<PfbxSize> searchPCSizeList = new ArrayList<PfbxSize>();
	private List<PfbxSize> searchMobileSizeList = new ArrayList<PfbxSize>();
	private List<PfbxSize> channelPCSizeList = null;
	private List<PfbxSize> channelMobileSizeList = new ArrayList<PfbxSize>();
	//影音廣告支援尺寸
	public Map<String,String> adVideoSizeMap;
	//圖片上傳位置
	private String imgUploadPath;
	private ControlPriceAPI controlPriceAPI;
	private String seqArray;
	private File[] fileupload;
	private String fileuploadFileName;
	private String adLinkURL;
	private String photoDbPathNew;
	private String adHiddenType;	//已建立的分類關鍵字欄位隱藏設定
	private String adType;			//廣告類型
	private String adDevice;		//廣告播放裝置
	private String adKeywordOpen;			//廣泛比對
	private String adKeywordPhraseOpen;		//詞組比對
	private String adKeywordPrecisionOpen;	//精準比對
	private String adGroupChannelPrice;
	//是否直立影音
	private boolean verticalAd;
	
	private String bookmark; //圖文廣告選擇哪個頁籤
	private RedisAPI redisAPI;
	private String manyURLRediskey;
	//新增Ad成功的seq
	private Map<String,Object> deleteAdMap = new HashMap<>();
	private List<String> deleteAdList = new ArrayList<>();
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	Map<String,Object> dataMap;
	private String adFastPublishUrlInfo; //多網址上稿，勾選的資料flag=Y
	private IPfpAdManyURLSearchService pfpAdManyURLSearchService;
	
	private AdFactory adFactory;
	private CookieUtil cookieUtil;
	private PfpAdGroup pfpAdGroup;
	
	/* 商品廣告用參數 START*/
	//行銷結尾圖
	private String uploadLog;
	//logo圖
	private String uploadLogoLog;
	//廣告名稱
	private String adName;
	//商品目錄ID
	private String catalogId;
	//商品群組ID
	private String catalogGroupId;
	//logo類型
	private String logoType;
	//logo標題文字
	private String logoText;
	//logo背景顏色
	private String logoBgColor;
	//logo文字顏色
	private String logoFontColor;
	//按鈕文字
	private String btnTxt;
	//按鈕文字顏色
	private String btnFontColor;
	//按鈕背景顏色
	private String btnBgColor;
	//標籤文字
	private String disTxtType;
	//標籤背景顏色
	private String disBgColor;
	//標籤文字顏色
	private String disFontColor;
	/* 商品廣告用參數 END*/
	
	//新增廣告
	public String AdAdAdd() throws Exception {
		log.info("AdAdAdd => adGroupSeq = " + adGroupSeq);
		IAd adObject = null;
		for (EnumAdStyleType enumAdStyleType : EnumAdStyleType.values()) {
			if(enumAdStyleType.getTypeName().equals(adOperatingRule)){
				adObject = adFactory.getaAdObject(enumAdStyleType);
				break;
			}
		}
		//無廣告類型
		if(adObject == null){
			return "notOwner";
		}
		pfpAdGroup = pfpAdGroupService.getPfpAdGroupBySeq(adGroupSeq);
		if(!pfpAdGroup.getPfpAdAction().getPfpCustomerInfo().getCustomerInfoId().equals(super.getCustomer_info_id())){
			log.info(">>>>>> GROUP USER NOT EQUAL LOGIN USER");
			backPage = "adActionView.html";
			return "notOwner";
		}
		adActionName  = pfpAdGroup.getPfpAdAction().getAdActionName();
		adGroupName  = pfpAdGroup.getAdGroupName();
		adType = pfpAdGroup.getPfpAdAction().getAdType().toString();
		adGroupChannelPrice = String.valueOf(pfpAdGroup.getAdGroupChannelPrice());
		//重新定向上一頁來源
		String referer = request.getHeader("Referer");
		if(StringUtils.isNotEmpty(referer)) {
			backPage = referer.replace(referer.substring(0, referer.indexOf("pfp/") + 4), "");
			if(referer.indexOf("adGroupAdd.html") >= 0 ){
				// 重算調控金額
				controlPriceAPI.countProcess(pfpCustomerInfoService.findCustomerInfo(super.getCustomer_info_id()));
			}
		}else{
			backPage = "adActionView.html";
		}
		//根據廣告類型個別處理顯示新增廣告畫面需要行為
		return adObject.AdAdAddInit(this);
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
		addAd(pfpAdGroup, null);
		
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
						String fileType = imgFile.substring(imgFile.lastIndexOf(".") +1);
						File adFile = null;	// 上傳圖片的檔名
						if("GIF".equals(fileType.toUpperCase())){	//只有GIF存原副檔名
							adFile = new File(photoPath, adSeq + "." + fileType);
						}else {
							adFile = new File(photoPath, adSeq + ".jpg");
						}
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
		    	adDetailContent[i] = adDetailContent[i].trim();
			}
			
			if(adDetailID[i].equals("show_url")) {
			    if(adDetailContent[i].indexOf("http://") < 0 ) {
			    	adDetailContent[i] = HttpUtil.getInstance().getRealUrl("http://" +adDetailContent[i]);
			    }else{
			    	adDetailContent[i] = HttpUtil.getInstance().getRealUrl(adDetailContent[i]);
			    }
//			    adDetailContent[i] = (HttpUtil.getInstance().getRealUrl(adDetailContent[i]).replace("http://", ""));
			    adDetailContent[i] = HttpUtil.getInstance().convertRealUrl(adDetailContent[i]);
			    adDetailContent[i] = adDetailContent[i].trim();
			}

			if(adDetailID[i].equals("title") || adDetailID[i].equals("content") ) {
				adDetailContent[i] = adDetailContent[i].replaceAll("\n", "");
				adDetailContent[i] = adDetailContent[i].replaceAll("\r", "");
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

	/**
	 * 新增圖文廣告-多網址上搞(畫面雙頁籤部分)
	 * @return
	 * @throws Exception 
	 */
	public String doAdAdAddTmgManyURL() throws Exception {
		//TODO 多網址上搞
		log.info("doAdAdAddTmgManyURL => adGroupSeq = " + adGroupSeq + ",=> adFastPublishUrlInfo =" + adFastPublishUrlInfo + ";");
		
		dataMap = new HashMap<String, Object>();

		PfpAdManyURLVO vo = new PfpAdManyURLVO();
		vo.setId(super.getCustomer_info_id());
		vo.setRedisCookieVal(CookieUtil.getCookie(request, EnumCookieConstants.COOKIE_PFPCART.getValue(), null));
		
		processKeyWordList();
		processExcludeKeyWordList();
		
		//檢查相關資料是否正確
		doAdAdAddTmgManyURLCheckData(vo);
		if (StringUtils.isNotEmpty(vo.getMessage())) {
			dataMap.put("status", "ERROR");
			dataMap.put("msg", vo.getMessage());
			return SUCCESS;
		}
		
		//設定一些參數值
		adPoolSeq = EnumAdStyle.TMG.getAdPoolSeq();
		templateProductSeq = EnumAdStyle.TMG.getTproSeq();
		adClass = "1";   // 廣告分類
		adStyle = "TMG"; // 廣告型態
		
		// 新增資料到table
		// 取得此客編在Redis的資料
		pfpAdManyURLSearchService.getRedisURLData(vo);
		
		JSONObject redisJsonObject = vo.getRedisJsonObject();
		String products = redisJsonObject.getJSONArray("products").toString();
		
		PfpAdGroup pfpAdGroup = null;
		JSONObject adFastPublishUrlInfoJson = new JSONObject(adFastPublishUrlInfo); 
		Iterator adFastPublishUrlInfoJsoIterator = adFastPublishUrlInfoJson.keys();
        while (adFastPublishUrlInfoJsoIterator.hasNext()) {
        	String key = adFastPublishUrlInfoJsoIterator.next().toString();
			if ("Y".equals(adFastPublishUrlInfoJson.get(key))) { // 是選取的資料
        		
        		pfpAdGroup = pfpAdGroupService.getPfpAdGroupBySeq(adGroupSeq);
        		adSeq = null; //清除adSeq再建立一筆
        		//開始新增資料到pfp_ad table
        		addAd(pfpAdGroup, null);
        		
        		//找到URL位置
        		int index = products.indexOf(key.replace("_ckeck_flag", ""));
        		//取得URL前後段字串
        		String startStr = products.substring(0, index);
        		String endStr = products.substring(index, products.length());
        		//由前後段字串找到包url那一段的位置
        		int startStrIndex = startStr.lastIndexOf("{\"");
        		int endStrIndex = endStr.indexOf("\"}");
        		//取得所需要斷落字串
        		String data = products.substring(startStrIndex, (startStr.length() + endStrIndex + 2));
        		//取得資料
        		JSONObject redisJsonObjectDetail = new JSONObject(data.toString());
        		
        		//新增資料到pfp_ad_detail table
        		//新增圖片資料
        		List<AdmDefineAd> admDefineAd = defineAdService.getDefineAdByCondition(null, "img", null, adPoolSeq);
        		String defineAdSeq = admDefineAd.get(0).getDefineAdSeq();
        		log.info("開始新增pfp_ad_detail資料，ad_seq:" + adSeq);
        		
        		String imgPath = redisJsonObjectDetail.get("pic_url").toString();
        		imgPath = processImgPath(imgPath);
        		newSaveAdDetail(imgPath, "img", adPoolSeq, defineAdSeq);
        		
        		//新增標題資料
        		admDefineAd = defineAdService.getDefineAdByCondition(null, "title", null, adPoolSeq);
        		defineAdSeq = admDefineAd.get(0).getDefineAdSeq();
        		newSaveAdDetail(redisJsonObjectDetail.get("title").toString(), "title", adPoolSeq, defineAdSeq);
        		
        		//新增完整標題資料
        		admDefineAd = defineAdService.getDefineAdByCondition(null, "intact_title", null, adPoolSeq);
        		defineAdSeq = admDefineAd.get(0).getDefineAdSeq();
        		newSaveAdDetail(redisJsonObjectDetail.get("intact_title").toString(), "intact_title", adPoolSeq, defineAdSeq);
        		
        		//新增內文資料
        		admDefineAd = defineAdService.getDefineAdByCondition(null, "content", null, adPoolSeq);
        		defineAdSeq = admDefineAd.get(0).getDefineAdSeq();
        		newSaveAdDetail(redisJsonObjectDetail.get("description").toString(), "content", adPoolSeq, defineAdSeq);
        		
        		//新增完整內文資料
        		admDefineAd = defineAdService.getDefineAdByCondition(null, "intact_content", null, adPoolSeq);
        		defineAdSeq = admDefineAd.get(0).getDefineAdSeq();
        		newSaveAdDetail(redisJsonObjectDetail.get("intact_description").toString(), "intact_content", adPoolSeq, defineAdSeq);
        		
        		//新增原價資料
        		admDefineAd = defineAdService.getDefineAdByCondition(null, "sales_price", null, adPoolSeq);
        		defineAdSeq = admDefineAd.get(0).getDefineAdSeq();
        		newSaveAdDetail(redisJsonObjectDetail.get("sp_price").toString(), "sales_price", adPoolSeq, defineAdSeq);
        		
        		//新增促銷價資料
        		admDefineAd = defineAdService.getDefineAdByCondition(null, "promotional_price", null, adPoolSeq);
        		defineAdSeq = admDefineAd.get(0).getDefineAdSeq();
        		newSaveAdDetail(redisJsonObjectDetail.get("price").toString(), "promotional_price", adPoolSeq, defineAdSeq);
        		
        		//新增實際網址資料
        		admDefineAd = defineAdService.getDefineAdByCondition(null, "real_url", null, adPoolSeq);
        		defineAdSeq = admDefineAd.get(0).getDefineAdSeq();
        		newSaveAdDetail(redisJsonObjectDetail.get("link_url").toString(), "real_url", adPoolSeq, defineAdSeq);
        		
        		//新增顯示網址資料
        		admDefineAd = defineAdService.getDefineAdByCondition(null, "show_url", null, adPoolSeq);
        		defineAdSeq = admDefineAd.get(0).getDefineAdSeq();
        		newSaveAdDetail(redisJsonObjectDetail.get("show_url").toString(), "show_url", adPoolSeq, defineAdSeq);
        		
        		// 新增關鍵字
        		addKeywords(pfpAdGroup);
        		//新增排除關鍵字
        		addExcludeKeywords(pfpAdGroup);
        		
        		log.info("新增ad_seq:" + adSeq + "完成。");
        	}
        }
        
        // 開啟廣告分類(僅更新一次)
 		pfpAdGroup.setAdGroupStatus(4);
 		pfpAdGroupService.save(pfpAdGroup);
 		
		return SUCCESS;
	}
	
	/**
	 * 處理關鍵字
	 */
	private void processKeyWordList() {
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
		} else { //無輸入則清空
			keywords = new String[] {};
		}
	}

	/**
	 * 處理排除關鍵字
	 */
	private void processExcludeKeyWordList() {
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
		} else { //無輸入則清空
			excludeKeywords = new String[] {};
		}
	}
	
	/**
	 * 處理下載圖片及路徑
	 * @param imgPath
	 * @return
	 * @throws IOException
	 */
	private String processImgPath(String imgPath) throws IOException {
		if (imgPath.indexOf("display:none") > -1) { // 沒有圖片，不做處理
			return imgPath;
		}
		
		log.info("開始下載圖片。");
		
		Date date = new Date();
		String photoPath = photoDbPathNew + super.getCustomer_info_id() + "/" + sdf.format(date) + "/original";
		File file = new File(photoPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		
		// 取得副檔名，處理圖片如果有被加timestamp等參數從?位置抓取副檔名，沒被加參數則直接依長度取最後3碼
		int startLength = (imgPath.indexOf("?") > -1 ? imgPath.indexOf("?") - 3 : imgPath.length() - 3);
		int endLength = (imgPath.indexOf("?") > -1 ? imgPath.indexOf("?") : imgPath.length());
		String filenameExtension = imgPath.substring(startLength, endLength);
        
		log.info("下載圖片網址:" + imgPath);
        URL url = new URL(imgPath.replaceFirst("https", "http"));
        String imgPathAndName = photoPath + "/" + adSeq + "." + filenameExtension; // 存放路徑 + 檔名

        // 處理圖片下載
        if ("gif".equalsIgnoreCase(filenameExtension)) { // gif圖片下載方式，此方式圖片才有動畫
            InputStream in = url.openStream();
            Files.copy(in, new File(imgPathAndName).toPath());
            in.close();
        } else { // jpg圖片下載方式
            BufferedImage img = ImageIO.read(url);
            int width = img.getWidth();
            int height = img.getHeight();
            if (width != height) { // 長寬不相同，為長方形。
                // url圖片要改成新的寬高
                int newWidth = 250;
                int newHeight = 188;
 
                // 底圖的寬、高、顏色
                int baseMapWidth = 250;
                int baseMapHeight = 250;
                Color baseMapWhite = new Color(255, 255, 255); // Color white
                int rgb = baseMapWhite.getRGB();
                BufferedImage baseMap = new BufferedImage(baseMapWidth, baseMapHeight, BufferedImage.TYPE_INT_RGB); // 建立一個空的圖片
                // 將每個像素調整為設定的顏色
                for (int i = 0; i < baseMapWidth; i++) {
                    for (int j = 0; j < baseMapHeight; j++) {
                        baseMap.setRGB(i, j, rgb);
                    }
                }
                
                // 建立url圖片區
                BufferedImage image = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_BGR);
                Graphics imageGraphics = image.createGraphics();
                imageGraphics.drawImage(img, 0, 0, newWidth, newHeight, null); // 畫入url圖片及設定新寬高
 
                // 取得底圖的繪圖區
                Graphics baseMapGraphics = baseMap.getGraphics();
                // 畫入調整好的新url圖片(就是底圖白色，圖片蓋在前面一層)，設定高從31px開始，0~31px則顯示成白色，呈現置中效果
                baseMapGraphics.drawImage(image, 0, 31, null);
                // 下載最後處理完成的基本圖
                ImageIO.write(baseMap, filenameExtension, new File(imgPathAndName));
                
            } else { // 相同為正方形，直接下載
                ImageIO.write(img, filenameExtension, new File(imgPathAndName));
            }
        }
        
		imgPath = "img/user/" + getCustomer_info_id() + "/" + sdf.format(date) + "/original/" + adSeq + "." + filenameExtension;
		log.info("下載圖片結束");
		return imgPath;
	}

	/**
	 * 檢查相關資料是否正確
	 * @param vo
	 * @throws JSONException 
	 */
	private void doAdAdAddTmgManyURLCheckData(PfpAdManyURLVO vo) throws JSONException {
		//檢查關鍵字比對方式是否選取。廣告類型0:全部 1:搜尋廣告+聯播網廣告 才有輸入關鍵字欄位部分 && keywords.length != 0 
		if (keywords.length != 0 && StringUtils.isBlank(adKeywordOpen) 
				&& StringUtils.isBlank(adKeywordPhraseOpen) && StringUtils.isBlank(adKeywordPrecisionOpen)) {
			vo.setMessage("請選擇關鍵字比對方式！");
		} else if (!adFastPublishUrlInfo.contains("\"Y\"")) { // 檢查是否選取物件
			vo.setMessage("尚未選擇商品物件");
		}
	}
	
	
	
	
	/*
	 * 儲存商品廣告上稿資料
	 * 
	 * */
	public String doAdAdAddProd() {
		try{
			log.info(">>>>>> doAdAdAddProd adGroupSeq:"+adGroupSeq);
			pfpAdGroup = pfpAdGroupService.getPfpAdGroupBySeq(adGroupSeq);
			if(pfpAdGroup == null){
				result = "分類錯誤";
			}
			System.out.println(super.getCustomer_info_id());
			System.out.println(pfpAdGroup.getPfpAdAction().getPfpCustomerInfo().getCustomerInfoId());
			if(!pfpAdGroup.getPfpAdAction().getPfpCustomerInfo().getCustomerInfoId().equals(super.getCustomer_info_id())){
				result = "分類帳戶錯誤";
			}
			
//			System.out.println(uploadLog);
//			System.out.println(uploadLogoLog);
			
			IAd adObject = adFactory.getaAdObject(EnumAdStyleType.AD_STYLE_PRODUCT);
			adObject.doAdAdAdd(this);
			return SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
			result = "error";
			return SUCCESS;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	 * 儲存影音上稿資料
	 * 
	 * */
	public String doAdAdAddVideo() {
		try{
			log.info(">>>>>> doAdAdAddVideo adGroupSeq:"+adGroupSeq);
			PfpAdGroup pfpAdGroup = pfpAdGroupService.getPfpAdGroupBySeq(adGroupSeq);
			
			if(StringUtils.isBlank(adVideoURL)){
				result = "adVideoURL is null";
			}else{
				if(adVideoURL.indexOf("&") >-1){
					adVideoURL = adVideoURL.substring(0,adVideoURL.indexOf("&"));
				}
			}
			
			templateProductSeq = EnumAdStyle.VIDEO.getTproSeq();
			File customerImgFile = null;
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			
			//1.儲存上傳圖片
			String originalPath = photoDbPathNew+super.getCustomer_info_id()+"/"+sdf.format(date)+"/original";
			String temporalPath = photoDbPathNew+super.getCustomer_info_id()+"/"+sdf.format(date)+"/temporal";
			
			log.info(">>>>originalPath:"+originalPath);
			JSONArray adDetailInfoArray = new JSONArray(videoDetailMap[0].toString());
			JSONObject picInfoJson = null;
			
			//檢查是否有下載過的影片
			PfpAdVideoSource pfpAdVideoSource = pfpAdVideoSourceService.getVideoUrl(adVideoURL);
			
			
			//根據尺寸建立明細
			for (int i = 0; i<adDetailInfoArray.length(); i++) {
				String adVideoSize = "";
				boolean isBannerSize = true;
				picInfoJson = new JSONObject(adDetailInfoArray.get(i).toString());
				adVideoSize = picInfoJson.getString("size");
				log.info("adVideoSize:"+adVideoSize);
				String adSize = adVideoSize.substring(0, 3)+"_"+adVideoSize.substring(3, adVideoSize.length());
				log.info("adSize:"+adSize);
				
				
				String pool = "";
				String templateAdSeq = "";
				for(EnumAdVideoSizePoolType enumAdVideoSize: EnumAdVideoSizePoolType.values()){
					if(enumAdVideoSize.getType().equals("DEFAULT") && adSize.equals(enumAdVideoSize.getRealWidth()+"_"+enumAdVideoSize.getRealHeight()) && !verticalAd){
						isBannerSize = false;
						pool = enumAdVideoSize.getPoolType();
						templateAdSeq = enumAdVideoSize.getTemplateAdSeq();
						break;
					}else if(enumAdVideoSize.getType().equals("BANNER") && adSize.equals(enumAdVideoSize.getRealWidth()+"_"+enumAdVideoSize.getRealHeight()) && !verticalAd){
						isBannerSize = true;
						pool = enumAdVideoSize.getPoolType();
						templateAdSeq = enumAdVideoSize.getTemplateAdSeq();
						break;
					}else if(enumAdVideoSize.getType().equals("VERTICAL") && adSize.equals(enumAdVideoSize.getRealWidth()+"_"+enumAdVideoSize.getRealHeight()) && verticalAd){
						isBannerSize = false;
						pool = enumAdVideoSize.getPoolType();
						templateAdSeq = enumAdVideoSize.getTemplateAdSeq();
						break;
					}
				}
				addAd(pfpAdGroup,templateAdSeq);
				PfpAd pfpAd = pfpAdService.get(adSeq);
				
				//變更暫存圖片檔名為此次上傳名細序號
				if(isBannerSize){
					customerImgFile = new File(originalPath + "/" + picInfoJson.getString("adSeq")+"."+picInfoJson.getString("format"));
					File originalRenameFile  = new File(originalPath+"/"+adSeq+"."+picInfoJson.getString("format"));
					if(originalRenameFile.exists()){
						originalRenameFile.delete();
					}
					customerImgFile.renameTo(originalRenameFile);
					customerImgFile = new File(temporalPath + "/" + picInfoJson.getString("adSeq")+"."+picInfoJson.getString("format"));
					File temporalRenameFile = new File(temporalPath+"/"+adSeq+"."+picInfoJson.getString("format"));
					if(temporalRenameFile.exists()){
						temporalRenameFile.delete();
					}
					customerImgFile.renameTo(temporalRenameFile);
				}
				
				//1.開始新增明細
				adDetailSeq = sequenceService.getId(EnumSequenceTableName.PFP_AD_DETAIL, "_");
				PfpAdDetail pfpAdDetail = new PfpAdDetail();
				pfpAdDetail.setAdDetailSeq(adDetailSeq);
				pfpAdDetail.setAdDetailId("img");
				pfpAdDetail.setPfpAd(pfpAd);
				if(isBannerSize){
					pfpAdDetail.setAdDetailContent("img/user/"+super.getCustomer_info_id()+"/"+sdf.format(date)+"/"+"original"+"/"+adSeq+"."+picInfoJson.getString("format"));	
				}else{
					pfpAdDetail.setAdDetailContent("img/public/na.gif\" style=\"display:none");
				}
				pfpAdDetail.setAdPoolSeq(pool);
				pfpAdDetail.setDefineAdSeq(EnumAdDetail.define_ad_seq_img.getAdDetailName());
				pfpAdDetail.setVerifyFlag("y");
				pfpAdDetail.setVerifyStatus("n");
				pfpAdDetail.setAdDetailUpdateTime(date);
				pfpAdDetail.setAdDetailCreateTime(date);
				pfpAdDetailService.savePfpAdDetail(pfpAdDetail);
				
				//2.儲存影片網址、影片連結網址、影片尺寸明細
				if(adLinkURL.indexOf("http") < 0){
					adLinkURL = "http://"+adLinkURL;
				}
				saveAdDetail(adLinkURL ,EnumAdDetail.real_url.getAdDetailName(),pool,EnumAdDetail.define_ad_seq_real_url.getAdDetailName());
				saveAdDetail(adVideoURL ,EnumAdDetail.video_url.getAdDetailName(),pool,"");
				saveAdDetail(adSize ,"video_size",pool,"dad_201303070012");
				saveAdDetail(adTitle.trim() ,"content",pool,"");
				if(verticalAd){
					saveAdDetail("Y" ,"video_vertical",pool,"dad_201303070013");
				}else{
					saveAdDetail("N" ,"video_vertical",pool,"dad_201303070013");
				}

				//3.儲存影片下載狀態與位置明細
				if(pfpAdVideoSource == null){
					saveAdDetail("尚未下載" ,"mp4_path",pool,EnumAdDetail.define_ad_pfp_mp4.getAdDetailName());
					saveAdDetail("尚未下載" ,"webm_path",pool,EnumAdDetail.define_ad_pfp_webm.getAdDetailName());
					saveAdDetail(adVideoURL ,"mp4_url",pool,EnumAdDetail.define_ad_seq_youtube_mp4.getAdDetailName());
					saveAdDetail(adVideoURL ,"webm_url",pool,EnumAdDetail.define_ad_seq_youtube_webm.getAdDetailName());
					saveAdDetail("尚未下載" ,"video_status",pool,"");
					saveAdDetail(videoTime ,"video_seconds",pool,"");
					String videpSeq = sequenceService.getId(EnumSequenceTableName.PFP_AD_VIDEO_SOURCE, "_");
					pfpAdVideoSource = new PfpAdVideoSource();
					pfpAdVideoSource.setAdVideoSeq(videpSeq);
					pfpAdVideoSource.setAdVideoStatus(0);
					pfpAdVideoSource.setAdVideoUrl(adVideoURL);
					pfpAdVideoSource.setAdVideoWebmPath("尚未下載");
					pfpAdVideoSource.setAdVideoMp4Path("尚未下載");
					if(verticalAd){
						pfpAdVideoSource.setVideoVertical("Y");						
					}else{
						pfpAdVideoSource.setVideoVertical("N");
					}
					pfpAdVideoSource.setCreateDate(date);
					pfpAdVideoSource.setUpdateDate(date);
					pfpAdVideoSourceService.saveOrUpdate(pfpAdVideoSource);
				}else{
					for (EnumAdVideoDownloadStatus enumAdVideoDownloadStatus : EnumAdVideoDownloadStatus.values()) {
						if(pfpAdVideoSource.getAdVideoStatus() == enumAdVideoDownloadStatus.getStatus()){
							saveAdDetail(pfpAdVideoSource.getAdVideoMp4Path() ,"mp4_path",pool,EnumAdDetail.define_ad_pfp_mp4.getAdDetailName());
							saveAdDetail(pfpAdVideoSource.getAdVideoWebmPath() ,"webm_path",pool,EnumAdDetail.define_ad_pfp_webm.getAdDetailName());
							saveAdDetail(enumAdVideoDownloadStatus.getDownloadValue() ,"video_status",pool,"");
							saveAdDetail(videoTime ,"video_seconds",pool,"");
							saveAdDetail(adVideoURL ,"mp4_url",pool,EnumAdDetail.define_ad_seq_youtube_mp4.getAdDetailName());
							saveAdDetail(adVideoURL ,"webm_url",pool,EnumAdDetail.define_ad_seq_youtube_webm.getAdDetailName());
							break;
						}
					}
				}
				adSeq = null;
			}
			
			// 開啟廣告分類
			pfpAdGroup.setAdGroupStatus(4);
			pfpAdGroupService.save(pfpAdGroup);
			
			result = "success";
			return SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
			result = "error";
			return SUCCESS;
		}
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
		addAd(pfpAdGroup,null);
		PfpAdDetailVO pfpAdDetailVO = new PfpAdDetailVO();
		for (int i = 0; i < adDetailID.length; i++) {
			adDetailSeq = sequenceService.getId(EnumSequenceTableName.PFP_AD_DETAIL, "_");
			List<AdmDefineAd> admDefineAd = defineAdService.getDefineAdByCondition(null, adDetailID[i], null, adPoolSeq);
			String defineAdSeq = admDefineAd.get(0).getDefineAdSeq();
			if (adDetailID[i].equals("real_url") || adDetailID[i].equals("show_url")) {
				if (adDetailContent[i].indexOf("http") != 0) {
					adDetailContent[i] = "http://" + adDetailContent[i];
				}
				adDetailContent[i] = adDetailContent[i].trim();
			}
			pfpAdDetailVO.setAdDetailSeq(adDetailSeq);
			pfpAdDetailVO.setAdSeq(adSeq);
			pfpAdDetailVO.setAdPoolSeq(adPoolSeq);
			pfpAdDetailVO.setAdDetailId(adDetailID[i]);
			pfpAdDetailVO.setAdDetailContent(adDetailContent[i]);
			if (adDetailID[i].equals("img") || adDetailID[i].equals("title") || adDetailID[i].equals("content")) {
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
		if (saveAndNew != null && saveAndNew.equals("save+new")) {
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

			if(keywords.length != 0 && StringUtils.isBlank(adKeywordOpen) && StringUtils.isBlank(adKeywordPhraseOpen)
					&& StringUtils.isBlank(adKeywordPrecisionOpen)){
				message = "請選擇關鍵字比對方式！";
			}
			
			for(int i = 0; i < adDetailID.length; i++) {
				if (StringUtils.isEmpty(adDetailContent[i])) {
					if(i == 0 && adStyle.equals("TMG")) {
						//message = "請輸入" + adDetailName[i] + " ！";
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
							message = "廣告連結網址不可超過 1024字！";
						} else {
							String url = adDetailContent[i];
							int urlState = 0;
							if (url.indexOf("http") != 0) {
								url = "http://" + url;
							}
							urlState = HttpUtil.getInstance().getStatusCode(url);
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
						if (url.indexOf("http") != 0) {
							url = "http://" + url;
						}
						urlState = HttpUtil.getInstance().getStatusCode(url);
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

	//新增廣告
	public void addAd(PfpAdGroup pfpAdGroup, String adAssignTadSeq) {
		try {
			log.info(">>>>> time: " + new Date());
			if(adSeq == null || StringUtils.isBlank(adSeq)){
				adSeq = sequenceService.getId(EnumSequenceTableName.PFP_AD, "_");
			}
			Date date = new Date();
			PfpAd pfpAd = new PfpAd();
			pfpAd.setAdSeq(adSeq);
			pfpAd.setPfpAdGroup(pfpAdGroup);
			pfpAd.setAdClass(adClass);
			pfpAd.setAdStyle(adStyle);
			pfpAd.setTemplateProductSeq(templateProductSeq);
			pfpAd.setAdAssignTadSeq(adAssignTadSeq);
			pfpAd.setAdSearchPrice(pfpAdGroup.getAdGroupSearchPrice());
			pfpAd.setAdChannelPrice(pfpAdGroup.getAdGroupChannelPrice());
			pfpAd.setAdStatus(EnumStatus.NoVerify.getStatusId());
			pfpAd.setAdSendVerifyTime(date);
			pfpAd.setAdCreateTime(date);
			pfpAd.setAdUpdateTime(date);
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
				for (String keyword : adKeywords) {
					String adKeywordSeq = sequenceService.getId(EnumSequenceTableName.PFP_AD_KEYWORD, "_");
					PfpAdKeyword pfpAdKeyword = new PfpAdKeyword();
					pfpAdKeyword.setAdKeywordSeq(adKeywordSeq);
					pfpAdKeyword.setPfpAdGroup(pfpAdGroup);
					pfpAdKeyword.setAdKeyword(keyword);
					// 廣泛比對設定
					if (StringUtils.isNotBlank(adKeywordOpen)) {
						pfpAdKeyword.setAdKeywordSearchPrice(pfpAdGroup.getAdGroupSearchPrice());
						pfpAdKeyword.setAdKeywordOpen(1);
					} else {
						pfpAdKeyword.setAdKeywordSearchPrice(0);
						pfpAdKeyword.setAdKeywordOpen(0);
					}
					// 詞組比對設定
					if (StringUtils.isNotBlank(adKeywordPhraseOpen)) {
						pfpAdKeyword.setAdKeywordSearchPhrasePrice(pfpAdGroup.getAdGroupSearchPrice());
						pfpAdKeyword.setAdKeywordPhraseOpen(1);
					} else {
						pfpAdKeyword.setAdKeywordSearchPhrasePrice(0);
						pfpAdKeyword.setAdKeywordPhraseOpen(0);
					}
					// 精準比對設定
					if (StringUtils.isNotBlank(adKeywordPrecisionOpen)) {
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
					pfpAdKeywordService.saveOrUpdate(pfpAdKeyword);
					syspriceOperaterAPI.addKeywordSysprice(keyword, pfpAdGroup.getAdGroupSearchPrice());
				}
			}
	
		} catch(Exception ex) {
		    log.info("Exception(addKeywords) : " + ex);
		}
	}

	/**
	 * 檢查是否重覆新增
	 * 1. 分類下已新增關鍵字
	 * 2. 分類下已排除關鍵字
	 */
	private List<String> checkKeywords(String adGroupSeq, String[] keywords) {
		List<String> list = null;
		if (StringUtils.isNotBlank(adGroupSeq) && keywords != null && StringUtils.isNotBlank(keywords.toString())) {
			list = new ArrayList<String>();
			List<String> existKeywords = new ArrayList<String>();
			// 分類下已新增關鍵字
			List<PfpAdKeyword> adKeywords = pfpAdKeywordService.findAdKeywords(adGroupSeq);
			for (PfpAdKeyword keyword : adKeywords) {
				existKeywords.add(keyword.getAdKeyword());
			}
			// 分類下已排除關鍵字
			List<PfpAdExcludeKeyword> adExcludeKeywords = pfpAdExcludeKeywordService.findAdExcludeKeywords(adGroupSeq);
			for (PfpAdExcludeKeyword keyword : adExcludeKeywords) {
				existKeywords.add(keyword.getAdExcludeKeyword());
			}
			log.info(" keywords size: " + keywords.length);
			log.info(" existKeywords size: " + existKeywords.size());
			if (existKeywords.isEmpty()) {
				for (int i = 0; i < keywords.length; i++) {
					list.add(keywords[i].trim());
				}
			} else {
				for (int i = 0; i < keywords.length; i++) {
					for (String keyword : existKeywords) {
						if (!keyword.equals(keywords[i].trim())) {
							list.add(keywords[i].trim());
							break;
						} else {
							log.info(" keywords[i]: " + keywords[i].trim());
						}
					}
				}
			}
		} else {
			log.info(" check keywords is null");
		}
		return list;
	}

	// 新增排除關鍵字
	private void addExcludeKeywords(PfpAdGroup pfpAdGroup) {
		try {
			if (excludeKeywords != null && excludeKeywords.length > 0) {
				for (int i = 0; i < excludeKeywords.length; i++) {
					// log.info("excludeKeywords["+i+"] = " + excludeKeywords[i]);
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

		} catch (Exception ex) {
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
	    adType = pfpAdGroup.getPfpAdAction().getAdType().toString();
	    adDevice = pfpAdGroup.getPfpAdAction().getAdDevice().toString();
	    List<PfbxSize> pfbSizeList = pfbSizeService.loadAll();
	    //搜索廣告(電腦)尺寸列表
	    if(Integer.parseInt(adType) == EnumAdType.AD_ALL.getType() || Integer.parseInt(adType) == EnumAdType.AD_SEARCH.getType()){
	    	for(EnumAdSearchPCSize enumAdSearchPCSize : EnumAdSearchPCSize.values()){
	    		for (PfbxSize pfbxSize : pfbSizeList) {
	    			if(String.valueOf(pfbxSize.getId()).equals(enumAdSearchPCSize.getName())){
	    				searchPCSizeList.add(pfbxSize);
	    		    }
	    		}
	    	}
	    }
	    //內容廣告(電腦)尺寸列表
	    Set<PfbxSize> pfbxSizeSet = new HashSet<>();
	    if(Integer.parseInt(adType) == EnumAdType.AD_ALL.getType() || Integer.parseInt(adType) == EnumAdType.AD_CHANNEL.getType()){
	    	for(EnumAdChannelPCSize enumAdChannelPCSize : EnumAdChannelPCSize.values()){
	    		for (PfbxSize pfbxSize : pfbSizeList) {
	    			if(String.valueOf(pfbxSize.getId()).equals(enumAdChannelPCSize.getName())){
	    				pfbxSizeSet.add(pfbxSize);
	    		    }
	    		}
	    	}
	    }
	    channelPCSizeList = new ArrayList<>(pfbxSizeSet);
	    
	    //搜索廣告(行動裝置)尺寸列表(目前僅測試時打開)
	    if(Integer.parseInt(adDevice) == EnumAdDevice.DEVICE_ALL.getDevType() || 
	    		Integer.parseInt(adDevice) == EnumAdDevice.DEVICE_MOBILE.getDevType()){
	    	for(EnumAdSearchMobileSize enumAdSearchMobileSize : EnumAdSearchMobileSize.values()){
	    		for (PfbxSize pfbxSize : pfbSizeList) {
	    			if(String.valueOf(pfbxSize.getId()).equals(enumAdSearchMobileSize.getName())){
	    				searchMobileSizeList.add(pfbxSize);
	    		    }
	    		}
	    	}
	    }
	    //內容廣告(行動裝置)尺寸列表
	    if(Integer.parseInt(adDevice) == EnumAdDevice.DEVICE_ALL.getDevType() || 
	    		Integer.parseInt(adDevice) == EnumAdDevice.DEVICE_MOBILE.getDevType()){
	    	for(EnumAdCannelMobileSize enumAdCannelMobileSize : EnumAdCannelMobileSize.values()){
	    		for (PfbxSize pfbxSize : pfbSizeList) {
	    			if(String.valueOf(pfbxSize.getId()).equals(enumAdCannelMobileSize.getName())){
	    				channelMobileSizeList.add(pfbxSize);
	    		    }
	    		}
	    	}
	    }	
	    
	    // 取出分類所屬關鍵字
	    pfpAdKeywords = pfpAdKeywordService.findAdKeywords(null, adGroupSeq, null, null, null, "10");

	    // 取出分類所屬排除關鍵字
	    pfpAdExcludeKeywords = pfpAdExcludeKeywordService.getPfpAdExcludeKeywords(adGroupSeq, super.getCustomer_info_id());

	    if(pfpAdKeywords.isEmpty() && pfpAdExcludeKeywords.isEmpty()){
			adHiddenType = "YES";
		}
	    
	    return SUCCESS;
	}

	/**
	 * 新增廣告明細
	 * @param content     廣告明細內容
	 * @param adDetailId  元件id
	 * @param adPoolSeq   資料來源序號
	 * @param defineAdSeq 廣告定義序號
	 * @throws Exception
	 */
	public void saveAdDetail(String content, String adDetailId, String adPoolSeq, String defineAdSeq) throws Exception {
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
	 * 新增廣告明細 - 沒有修改templateProductSeq
	 * @param content     廣告明細內容
	 * @param adDetailId  元件id
	 * @param adPoolSeq   資料來源序號
	 * @param defineAdSeq 廣告定義序號
	 * @throws Exception
	 */
	private void newSaveAdDetail(String content, String adDetailId, String adPoolSeq, String defineAdSeq) throws Exception {
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
	    
	    String imgWidth ="0";
	    String imgHeight ="0";
	    String fileSize= "0";
	    String imgMD5 = "";
	    String imgRepeat = "no";
	    String html5Repeat = "no";
	    String imgSrc = "";
	    imgUploadPath = "";
	    for (File file : fileupload) {
	    	File originalImgFile = file;
    		String fileType = fileuploadFileName.substring(fileuploadFileName.lastIndexOf(".") + 1);
    		
    		if(StringUtils.equals("zip", fileType)){
    			
    			adSeq = sequenceService.getId(EnumSequenceTableName.PFP_AD, "_");
    			
				//建立路徑
				log.info(">>>1.path>>"+photoDbPathNew+customerInfoId);
				log.info(">>>2.path>>"+customerImgFile.getPath());
				log.info(customerImgFile.exists());
				if(!customerImgFile.exists()){
				    log.info(">>>3.path>>"+photoDbPathNew+customerInfoId);
				    customerImgFile.mkdirs();
				}
				
				//SpringZipCompress.getInstance().openZip(file, photoDbPathNew+customerInfoId+"/"+sdf.format(date)+"/original/" + adSeq);
				File originalPathFile = new File(photoDbPathNew+customerInfoId+"/"+sdf.format(date)+"/original/" + adSeq);
				if(!originalPathFile.exists()){
					originalPathFile.mkdirs();
				}
				SpringZipCompress.getInstance().openZip(fileuploadFileName,file, photoDbPathNew+customerInfoId+"/"+sdf.format(date)+"/temporal/" + adSeq);
				
				int FileAmount = checkFileAmount(photoDbPathNew+customerInfoId+"/"+sdf.format(date)+"/temporal/" + adSeq);
				log.info(">>>>>>>>>>>>>>>>>>>>>   FileAmount  = " + FileAmount);
				
				//檢查index.html是否存在
				String errorMsg = "";
				File indexHtmlFile = new File(getIndexHtmlPath(photoDbPathNew+customerInfoId+"/"+sdf.format(date)+"/temporal/" + adSeq));
				
				if(!indexHtmlFile.exists()){
					errorMsg = "HTML檔名錯誤";
				}
				
				if(FileAmount > 40){
					errorMsg = "夾帶檔案超過40 個";
				}
				
				if(indexHtmlFile.exists() && FileAmount <= 40){
					Document doc = Jsoup.parse(indexHtmlFile, "UTF-8");
					String docHtml = doc.html();
					Elements htmlTag = doc.select("html");
					Elements headTag = doc.select("head");
					Elements bodyTag = doc.select("body");
					Elements metaTag = doc.select("meta[name=ad.size][content]");
					
					log.info(">>>>>>>>>>>>>>>>>>>>     htmlTag = " + !htmlTag.isEmpty());
					log.info(">>>>>>>>>>>>>>>>>>>>     headTag = " + !headTag.isEmpty());
					log.info(">>>>>>>>>>>>>>>>>>>>     bodyTag = " + !bodyTag.isEmpty());
					log.info(">>>>>>>>>>>>>>>>>>>>     metaTag = " + !metaTag.isEmpty());
					
					if(docHtml.indexOf("<!doctype html>") != -1 && !htmlTag.isEmpty() && !headTag.isEmpty() && !bodyTag.isEmpty() && !metaTag.isEmpty()){
						html5Repeat = "yes";
						
						//將index.html改為index.htm
						String indexHtmFilePath = indexHtmlFile.getPath().replaceAll("\\\\\\\\", "/");
						indexHtmFilePath = indexHtmFilePath.replaceAll("\\\\", "/");
						log.info(">>>>>>>>>>>>>>>>>>>>     indexHtmFilePath = " + indexHtmFilePath);
						
			            File indexHtmFile = new File(indexHtmFilePath.substring(0, indexHtmFilePath.lastIndexOf("/")) + "/index.htm");
			            indexHtmlFile.renameTo(indexHtmFile);
			           // File indexHtmlFile2 = new File(getIndexHtmlPath(photoDbPathNew+customerInfoId+"/"+sdf.format(date)+"/original/" + adSeq));
			           // String indexHtmFile2Path = indexHtmlFile2.getPath().replaceAll("\\\\\\\\", "/");
			           // File indexHtmFile2 = new File(indexHtmFile2Path.substring(0, indexHtmFile2Path.lastIndexOf("/")) + "/index.htm");
			           // indexHtmlFile2.renameTo(indexHtmFile2);
						
						imgSrc = indexHtmFile.getPath().replaceAll("\\\\\\\\", "/");
						imgSrc = imgSrc.replaceAll("\\\\", "/");
						imgSrc = imgSrc.replace("/export/home/webuser/akb/pfp/", "");
						String content = metaTag.attr("content");
						content = content.replaceAll(";", "");
						
						String[] contentArray = content.split(",");
						try {
							for(String size:contentArray){
								if(size.indexOf("width=") != -1){
									imgWidth = size.replaceAll("width=", "").trim();
								}
								if(size.indexOf("height=") != -1){
									imgHeight = size.replaceAll("height=", "").trim();
								}
							}
							
							//驗證長、寬是否為數字
							Integer intWidth = Integer.parseInt(imgWidth);
							Integer intHeight = Integer.parseInt(imgHeight);
						} catch(Exception error) {
							imgWidth = "0";
							imgHeight = "0";
						}
						
						log.info(">>>>>>>>>>>>>>>>>>>>     imgWidth = " + imgWidth);
						log.info(">>>>>>>>>>>>>>>>>>>>     imgHeight = " + imgHeight);
						
					} else {
						if(docHtml.indexOf("<!doctype html>") == -1){
							errorMsg = "&lt;!DOCTYPE html&gt;宣告錯誤";
						}
						if(htmlTag.isEmpty()){
							errorMsg = "&lt;html&gt;標籤錯誤";
						}
						if(headTag.isEmpty()){
							errorMsg = "&lt;head&gt;標籤錯誤";
						}
						if(bodyTag.isEmpty()){
							errorMsg = "&lt;body&gt;標籤錯誤";
						}
						if(metaTag.isEmpty()){
							errorMsg = "&lt;meta&gt;標籤錯誤";
						}
						//errorMsg = "與規範不符";
					}
				}
				
				result = "{\"adSeq\":\"" + adSeq + "\","+ "\"imgWidth\":\"" + imgWidth +"\"," +   "\"imgHeight\":\"" + imgHeight +"\",  " + "\"fileSize\":\"" + fileSize + "\"," + "\"imgMD5\":\"" + imgMD5 + "\"," + "\"imgRepeat\":\"" + imgRepeat + "\"," + "\"html5Repeat\":\"" + html5Repeat + "\"," + "\"imgSrc\":\"" + imgSrc + "\"," + "\"errorMsg\":\"" + errorMsg + "\" " + "}";
    		} else {
    			ImageInputStream stream = new FileImageInputStream(originalImgFile);
                Iterator<ImageReader> readers = ImageIO.getImageReaders(stream);
                String imgFileType = "";
                if (readers.hasNext()) {
                    ImageReader reader = readers.next();
                    reader.setInput(stream, true);
                    imgWidth = String.valueOf(reader.getWidth(0));
                    imgHeight = String.valueOf(reader.getHeight(0));
                    if(reader.getFormatName().equals("JPG") || reader.getFormatName().equals("GIF") || reader.getFormatName().equals("PNG")){
                        imgFileType = reader.getFormatName();
                    }
                 }
                 stream.close();
    	    	
    	    	//取得檔案的MD5
    	    	MessageDigest md = MessageDigest.getInstance("MD5");
    	        FileInputStream fis = new FileInputStream(file);
    	     
    	        byte[] dataBytes = new byte[1024];
    	     
    	        int nread = 0;
    	        while ((nread = fis.read(dataBytes)) != -1) {
    	            md.update(dataBytes, 0, nread);
    	        };
    	        byte[] mdbytes = md.digest();
    	        StringBuffer sb = new StringBuffer();
    	        for (int i = 0; i < mdbytes.length; i++) {
    	            sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
    	        }
    	        fis.close();
    	        imgMD5 = sb.toString();
        		
    	        List<PfpAdDetail> pfpadDetailList = pfpAdDetailService.getPfpAdDetailsForAdGroup(customerInfoId, adGroupSeq, "MD5", imgMD5);
    	        
    	        if(!pfpadDetailList.isEmpty()){
    	        	imgRepeat = "yes";
    	        }
    	        
    	        //建立圖片
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
        		    if(!customerImgFileOriginalDateFile.exists()){
        		    	customerImgFileOriginalDateFile.mkdirs();
        		    }
        		    customerImgFileTemporalDateFile = new File(photoDbPathNew+customerInfoId+"/"+sdf.format(date)+"/temporal");
        		    if(!customerImgFileTemporalDateFile.exists()){
        		        customerImgFileTemporalDateFile.mkdirs();
        		    }
        		}else{
        		    fileSize = String.valueOf(file.length() / 1024);
        		}

				while (StringUtils.isBlank(adSeq)) {
					try {
						adSeq = sequenceService.getId(EnumSequenceTableName.PFP_AD, "_");
					} catch (Exception e) {
						log.error(e.getMessage());
						Thread.sleep(100);
					}
				}
                commonUtilModel.writeImg(originalImgFile,photoDbPathNew,customerInfoId, sdf.format(date),adSeq,fileType);

        		result = "{\"adSeq\":\"" + adSeq + "\","+ "\"imgWidth\":\"" + imgWidth +"\"," +   "\"imgHeight\":\"" + imgHeight +"\",  " + "\"fileSize\":\"" + fileSize + "\"," + "\"imgMD5\":\"" + imgMD5 + "\"," + "\"imgRepeat\":\"" + imgRepeat + "\"," + "\"html5Repeat\":\"" + html5Repeat + "\"," + "\"imgSrc\":\"" + imgSrc + "\"," + "\"errorMsg\":\"\" " + "}";
    		}
    		
	    }

	    return SUCCESS;
	}

	/**
	 * 儲存圖像上稿資料
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

    	adClass = "1";
    	adStyle = "IMG";
    	templateProductSeq = EnumAdStyle.IMG.getTproSeq();
    	JSONObject seqArrayJsonObject = new JSONObject(seqArray.toString());
    	JSONArray seqArray = new JSONArray(seqArrayJsonObject.get("seqArray").toString());
    	JSONObject imgNameMap = new JSONObject(seqArrayJsonObject.get("imgNameMap").toString());
    	JSONObject imgMD5Map = new JSONObject(seqArrayJsonObject.get("imgMD5Map").toString());
    	
    	// 1.存廣告檔
    	// 2.刪暫存圖檔
    	CommonUtilModel commonUtilModel = new CommonUtilModel();
    	String customerInfoId = super.getCustomer_info_id();
    	Date date = new Date();
    	ImageVO imageVO = new ImageVO();
    	if (seqArray.length() > 0) {
    	    for (int i = 0; i < seqArray.length(); i++) {
        		adSeq = seqArray.get(i).toString();
        		String imgName = "";
        		String imgMD5 = "";
        		String adAssignTadSeq = null;
        		
        		if(imgMD5Map.get(adSeq + "_imgMD5") != null && StringUtils.equals(imgMD5Map.get(adSeq + "_imgMD5").toString(), "X")){
        			imageVO = commonUtilModel.createAdHtml5(photoDbPathNew,customerInfoId, sdf.format(date), seqArray.get(i).toString());
        			adAssignTadSeq = "c_x05_po_tad_0059";
        		} else {
        			imageVO = commonUtilModel.createAdImg(photoDbPathNew,customerInfoId, sdf.format(date), seqArray.get(i).toString());
        		}
        		
        		String adPoolSeq = "";
        		for (EnumAdSize enumAdSize : EnumAdSize.values()) {
        		    if (imageVO.getImgWidth().equals(enumAdSize.getWidh())  && imageVO.getImgHeight().equals(enumAdSize.getHeight())) {
            			adPoolSeq = enumAdSize.name();
            			break;
        		    }
        		}
        		if (StringUtil.isEmpty(adPoolSeq)) {
        		    result = "error";
        		    return SUCCESS;
        		}
        		addAd(pfpAdGroup,adAssignTadSeq);
        		String path = imageVO.getImgPath().replace("\\", "/");
        		
        		path = path.replace("/export/home/webuser/akb/pfp/", "");
        		saveAdDetail(path,EnumAdDetail.img.name(), adPoolSeq,EnumAdDetail.define_ad_seq_img.getAdDetailName());

        		// check adLinkURL
                if(StringUtils.isBlank(adLinkURL)){
                    result = "請填寫廣告連結網址.";
                    return SUCCESS;
                }
                if(adLinkURL.length() > 1024) {
                    result = "廣告連結網址不可超過 1024字！";
                    return SUCCESS;
                }
                if (adLinkURL.indexOf("http") <= -1) {
                    adLinkURL = "http://" + adLinkURL;
                }
                int urlState = HttpUtil.getInstance().getStatusCode(adLinkURL);
                if(urlState < 200 && urlState >= 300) {
                    result = "請輸入正確的廣告連結網址！";
                    return SUCCESS;
                }

        		saveAdDetail(adLinkURL.trim(),EnumAdDetail.real_url.getAdDetailName(), adPoolSeq,EnumAdDetail.define_ad_seq_real_url.getAdDetailName());
        		
        		if(imgNameMap.get(adSeq + "_title") != null){
        			imgName = imgNameMap.get(adSeq + "_title").toString();	
        		}
        		if(imgName.length() > 1024){
        			result = "輸入的廣告名稱不可超過 1024字！";
                    return SUCCESS;
        		}
        		saveAdDetail(imgName,EnumAdDetail.title.name(), adPoolSeq,EnumAdDetail.define_ad_seq_title.getAdDetailName());
        		
        		if(imgMD5Map.get(adSeq + "_imgMD5") != null && !StringUtils.equals(imgMD5Map.get(adSeq + "_imgMD5").toString(), "X")){
        			imgMD5 = imgMD5Map.get(adSeq + "_imgMD5").toString();	
        			saveAdDetail(imgMD5,"MD5", adPoolSeq,null);
        		}
        		
        		if(StringUtils.equals(imgMD5Map.get(adSeq + "_imgMD5").toString(), "X")){
        			String zip = imgName + "(html5)";
        			saveAdDetail(zip,"zip", adPoolSeq,null);
        			String size = imageVO.getImgWidth() + " x " + imageVO.getImgHeight();
        			saveAdDetail(size,"size", adPoolSeq,null);
        			String zipFile = photoDbPathNew+customerInfoId+"/"+sdf.format(date)+"/original/" + adSeq + "/" + imgName + ".zip";
        			zipFile = zipFile.replace("/export/home/webuser/akb/pfp/", "");
        			saveAdDetail(zipFile,"zipFile", adPoolSeq,null);
        		}
    	    }
    	}

    	// 刪除暫存檔
    	commonUtilModel.deleteAllTemporalImg(photoDbPathNew, customerInfoId,sdf.format(date));

        // 開啟廣告分類
        pfpAdGroup.setAdGroupStatus(4);
        pfpAdGroupService.save(pfpAdGroup);

    	result = "success";
    	return SUCCESS;
    }

	/**
	 * 快速上稿畫面
	 * */
	public String adFastPublishAdd() {
		Date d = new Date();
		CookieUtil.writeCookie(response, EnumCookieConstants.COOKIE_PFPCART.getValue(), String.valueOf(d.getTime()), EnumCookieConstants.COOKIE_PCHOME_DOMAIN.getValue(), null);
		log.info(">>>>>> init redis:" + manyURLRediskey + super.getCustomer_info_id() + String.valueOf(d.getTime()));
//		redisAPI.delRedisData(manyURLRediskey + super.getCustomer_info_id());
		adStyle = "TMG";
		adType = "";
		bookmark = "fastURLAdAdd";
		admenuul = "fastPublishAdd";
		adActionName = "PCHOME聯播網廣告";
		adGroupName = "PCHOME聯播網廣告";
		return SUCCESS;
	}
	
	/**
	 * 快速上稿新增廣告
	 * */
	public String doAdAdAddFastPublis() throws Exception{
		try{
			//先呼叫建立活動再呼叫建立廣告明細
			boolean doAdAdAddFastPublisDetailFlag = false;
			if(StringUtils.isNotBlank(adActionName)){
				doAdActionAdd();
				doAdAdAddFastPublisDetailFlag = doAdAdAddFastPublisDetail();
			}else{
				doAdAdAddFastPublisDetailFlag = doAdAdAddFastPublisDetail();
			}
			
			if(doAdAdAddFastPublisDetailFlag){
//				System.out.println("adActionName:"+adActionName);
//				System.out.println("adGroupName:"+adGroupName);
//				System.out.println("adActionStartDate:"+adActionStartDate);
//				System.out.println("adActionEndDate:"+adActionEndDate);
//				System.out.println("adGroupSeq:"+adGroupSeq);
//				System.out.println("adActionSeq:"+adActionSeq);
//				System.out.println("adType:"+adType);
//				System.out.println("adDevice:"+adDevice);
//				System.out.println("adActionMax:"+adActionMax);
//				System.out.println("adGroupChannelPrice:"+adGroupChannelPrice);
//				System.out.println("adOperatingRule:"+adOperatingRule);
				result = adGroupSeq;
				return SUCCESS;
			}
			result ="error";
			return SUCCESS;
		}catch(Exception e){
			//刪除新增的ad
			List<String> deleteAdList = (List<String>) deleteAdMap.get("adList");
			for (String adSeq : deleteAdList) {
				log.info(">>>>>> delete adSeq:"+adSeq);
				pfpAdService.delete(pfpAdService.get(adSeq));
			}
			
			String deleteAdGroupSeq = (String) deleteAdMap.get("adGroupSeq");
			if(StringUtils.isNotBlank(deleteAdGroupSeq)){
				log.info(">>>>>> delete adGroupSeq:"+deleteAdGroupSeq);
				pfpAdGroupService.delete(pfpAdGroupService.get(deleteAdGroupSeq));
			}
			
			String deleteAdActionSeq = (String) deleteAdMap.get("adActionSeq");
			if(StringUtils.isNotBlank(deleteAdActionSeq)){
				log.info(">>>>>> delete adActionSeq:"+deleteAdActionSeq);
				pfpAdActionService.delete(pfpAdActionService.get(deleteAdActionSeq));
			}
			e.printStackTrace();
			result ="error";
			return SUCCESS;
		}
		
	}
	
	/*
	 * 快速上稿新增廣告明細
	 * 1.不存在group時新增分類
	 * 2.建立Ad
	 * 3.建立明細
	 * 4.新增關鍵字  20180518 開會討論，先關閉新增關鍵字部分
	 * */
	private boolean doAdAdAddFastPublisDetail() throws Exception{
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
		
		Date date = new Date();
		
		PfpAdGroup pfpAdGroup = null;
		if(StringUtils.isNotBlank(adActionName)){
			pfpAdGroup = new PfpAdGroup();
			adGroupSeq = sequenceService.getId(EnumSequenceTableName.PFP_AD_GROUP, "_");
			PfpAdAction pfpAdAction = pfpAdActionService.getPfpAdActionBySeq(adActionSeq);
			pfpAdGroup.setAdGroupSeq(adGroupSeq);
			pfpAdGroup.setPfpAdAction(pfpAdAction);
			pfpAdGroup.setAdGroupCreateTime(date);
			pfpAdGroup.setAdGroupName(adGroupName);
			pfpAdGroup.setAdGroupSearchPriceType(Integer.parseInt(adGroupSearchPriceType));
			pfpAdGroup.setAdGroupSearchPrice(Float.parseFloat(adGroupSearchPrice));
			pfpAdGroup.setAdGroupChannelPrice(Float.parseFloat(adGroupChannelPrice));
			pfpAdGroup.setAdGroupPriceType(EnumAdPriceType.AD_PRICE_CPC.getDbTypeName());
			// 新增廣告分類時，status 設定為未完成
			pfpAdGroup.setAdGroupStatus(EnumStatus.UnDone.getStatusId());
			pfpAdGroup.setAdGroupUpdateTime(date);
			//更新廣告活動狀態為已完成(開啟)
			pfpAdGroup.getPfpAdAction().setAdActionStatus(EnumStatus.Open.getStatusId());
			pfpAdGroupService.savePfpAdGroup(pfpAdGroup);
			
			deleteAdMap.put("adGroupSeq", adGroupSeq);
		}else{
			pfpAdGroup = pfpAdGroupService.get(adGroupSeq);
		}
		
		if(pfpAdGroup == null){
			return false;
		}
		
		String redisKey = manyURLRediskey + super.getCustomer_info_id() + CookieUtil.getCookie(request, EnumCookieConstants.COOKIE_PFPCART.getValue(), null);
		String redisData = redisAPI.getRedisData(redisKey);
		if(StringUtils.isNotBlank(redisData)){
			String photoPath = photoDbPathNew + super.getCustomer_info_id()+"/"+sdf.format(date)+"/original";
			File file = new File(photoPath);
			if(!file.exists()){
				file.mkdirs();
			}
			
			JSONObject redisJson = new JSONObject(redisData);
			JSONArray  redisAdArrayJson = (JSONArray) redisJson.get("products");
			for (int i = 0; i < redisAdArrayJson.length(); i++) {
				JSONObject addAdJson = (JSONObject) redisAdArrayJson.get(i);
				if(addAdJson.get("add").equals("Y")){
					adSeq = sequenceService.getId(EnumSequenceTableName.PFP_AD, "_");
					PfpAd pfpAd = new PfpAd();
					pfpAd.setAdSeq(adSeq);
					pfpAd.setPfpAdGroup(pfpAdGroup);
					pfpAd.setAdClass("1");
					pfpAd.setAdStyle("TMG");
					pfpAd.setTemplateProductSeq("tpro_201306280001");
					pfpAd.setAdSearchPrice(pfpAdGroup.getAdGroupSearchPrice());
					pfpAd.setAdChannelPrice(pfpAdGroup.getAdGroupChannelPrice());
					pfpAd.setAdStatus(EnumStatus.NoVerify.getStatusId());
					pfpAd.setAdSendVerifyTime(date);
					pfpAd.setAdCreateTime(date);
					pfpAd.setAdUpdateTime(date);
					pfpAdService.save(pfpAd);
					deleteAdList.add(adSeq);
					
					deleteAdMap.put("adList", deleteAdList);
					
					//新增明細
					String imgPath = addAdJson.getString("pic_url");
					String title = addAdJson.getString("title");
					String intact_title = addAdJson.getString("intact_title");
					String content = addAdJson.getString("description");
					String intact_content = addAdJson.getString("intact_description");
					String sales_price = addAdJson.getString("sp_price");
					String promotional_price = addAdJson.getString("price");
					String real_url = addAdJson.getString("link_url");
					String show_url = addAdJson.getString("show_url");

					saveAdDetail(processImgPath(imgPath), "img", "adp_201303070003", "dad_201303070010");
					saveAdDetail(title, "title", "adp_201303070003", "dad_201303070011");
					saveAdDetail(intact_title, "intact_title", "adp_201303070003", "dad_201303070017");
					saveAdDetail(content, "content", "adp_201303070003", "dad_201303070012");
					saveAdDetail(intact_content, "intact_content", "adp_201303070003", "dad_201303070018");
					saveAdDetail(sales_price, "sales_price", "adp_201303070003", "dad_201303070015");
					saveAdDetail(promotional_price, "promotional_price", "adp_201303070003", "dad_201303070016");
					saveAdDetail(real_url, "real_url", "adp_201303070003", "dad_201303070014");
					saveAdDetail(show_url, "show_url", "adp_201303070003", "dad_201303070013");
				}
			}
			
			// 新增關鍵字。 20180518 開會討論，先關閉新增關鍵字部分
//			if(!adType.equals("2")){
//				saveKeyWord(adGroupSeq,redisAdArrayJson);
//			}
		}
		pfpAdGroup.setAdGroupStatus(4);
		pfpAdGroupService.save(pfpAdGroup);
		return true;
	}
	
	/*
	 * 快速上稿新增關鍵字
	 * */
	private void saveKeyWord(String groupSeq,JSONArray redisAdArrayJson) throws Exception{
		PfpAdGroup pfpAdGroup = pfpAdGroupService.get(adGroupSeq);
		Set<PfpAdKeyword> pfpAdKeywordSet = pfpAdGroup.getPfpAdKeywords();
		List<String> keywordList = new ArrayList<>();
		for (int i = 0; i < redisAdArrayJson.length(); i++) {
			JSONObject addAdJson = (JSONObject) redisAdArrayJson.get(i);
			boolean suggestFlag = addAdJson.isNull("suggest");
			if(!suggestFlag){
				JSONArray radisKeywords = (JSONArray) addAdJson.get("suggest");
				for (int j = 0; j < radisKeywords.length(); j++) {
					boolean isExist = false;
					for (PfpAdKeyword pfpAdKeyword : pfpAdKeywordSet) {
						if(pfpAdKeyword.getAdKeyword().equals(radisKeywords.get(j))){
							isExist = true;
							break;
						}
					}
					if(!isExist){
						keywordList.add(radisKeywords.get(j).toString());
					}
				}
			}
		}
		int keywordSize = keywordList.size();
		if(keywordSize > 0){
			this.keywords = new String[keywordSize];
			this.keywords = keywordList.toArray(keywords);
			//預設廣泛比對
			adKeywordPrecisionOpen = "on";
			addKeywords(pfpAdGroup);
		}
	}
	
	/*
	 * 快速上稿新增廣告活動
	 * */
	private void doAdActionAdd() throws Exception{
		log.info("fast publist adActionAdd");
		//預設全天時段
		String timeCode ="111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111";
		adActionSeq = sequenceService.getId(EnumSequenceTableName.PFP_AD_ACTION, "_");
		PfpAdAction pfpAdAction = new PfpAdAction();
		pfpAdAction.setAdActionSeq(adActionSeq);
		pfpAdAction.setAdActionCreatTime(new Date());
		pfpAdAction.setAdActionName(adActionName);
		pfpAdAction.setAdActionDesc(adActionName);
		pfpAdAction.setAdActionStartDate(adUtils.DateFormat(adActionStartDate));
		if(StringUtils.isBlank(adActionEndDate)) {
			pfpAdAction.setAdActionEndDate(adUtils.DateFormat("3000-12-31"));
		}else{
			pfpAdAction.setAdActionEndDate(adUtils.DateFormat(adActionEndDate));
		}
		pfpAdAction.setAdActionMax(adActionMax);
		pfpAdAction.setAdActionControlPrice(adActionMax);
		pfpAdAction.setUserId(super.getUser_id());
		pfpAdAction.setAdActionUpdateTime(new Date());
		pfpAdAction.setAdActionStatus(EnumStatus.UnDone.getStatusId());
		pfpAdAction.setAdOperatingRule(EnumAdStyleType.AD_STYLE_MULTIMEDIA.getTypeName());
		PfpCustomerInfo pfpCustomerInfo = pfpCustomerInfoService.findCustomerInfo(super.getCustomer_info_id());
		pfpAdAction.setPfpCustomerInfo(pfpCustomerInfo);
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
		//曝光限制-活動
		pfpAdAction.setAdPvLimitStyle("0");
		//曝光限制-每日
		pfpAdAction.setAdPvLimitPeriod("0");
		//曝光限制-同一受眾次數
		pfpAdAction.setAdPvLimitAmount(0);
		pfpAdAction.setAdActionStartAge(0);
		pfpAdAction.setAdActionEndAge(99);
		pfpAdAction.setAdSpecificPlayType("0");
		pfpAdActionService.save(pfpAdAction);
		
		deleteAdMap.put("adActionSeq", adActionSeq);
	}
	
	/**
	 * 檢查檔案數
	 * */
	private int checkFileAmount(String path) {
		int amount = 0;
		File checkFlie = new File(path);
		File[] files = checkFlie.listFiles(); // 獲取資料夾下面的所有檔
		for (File f : files) {
			// 判斷是否為資料夾
			if (f.isDirectory()) {
				amount += checkFileAmount(f.getPath()); // 如果是資料夾，檢查該資料夾內檔案數
			} else {
				amount++;
			}
		}

		return amount;
	}
	
	private String reversionString(String timeString) throws Exception {
		String time = "";
		String[] timeArray = timeString.split("");
		for (int i = 0; i < timeArray.length; i++) {
			time = timeArray[i] + time;
		}
		return time;
	}

	private String getIndexHtmlPath(String path) {
		String indexPath = "";

		File checkFlie = new File(path);
		File[] files = checkFlie.listFiles(); // 獲取資料夾下面的所有檔
		for (File f : files) {
			// 判斷是否為資料夾
			if (f.isDirectory()) {
				indexPath = getIndexHtmlPath(f.getPath()); // 如果是資料夾，檢查該資料夾內檔案
			} else {
				if (f.getPath().indexOf("index.htm") != -1) {
					indexPath = f.getPath();
					break;
				}
			}
		}

		return indexPath;
	}
	
	public List<PfbxSize> getSearchPCSizeList() {
		return searchPCSizeList;
	}

	public void setSearchPCSizeList(List<PfbxSize> searchPCSizeList) {
		this.searchPCSizeList = searchPCSizeList;
	}

	public List<PfbxSize> getChannelPCSizeList() {
		return channelPCSizeList;
	}

	public void setChannelPCSizeList(List<PfbxSize> channelPCSizeList) {
		this.channelPCSizeList = channelPCSizeList;
	}

	public List<PfbxSize> getSearchMobileSizeList() {
		return searchMobileSizeList;
	}

	public void setSearchMobileSizeList(List<PfbxSize> searchMobileSizeList) {
		this.searchMobileSizeList = searchMobileSizeList;
	}

	public List<PfbxSize> getChannelMobileSizeList() {
		return channelMobileSizeList;
	}

	public void setChannelMobileSizeList(List<PfbxSize> channelMobileSizeList) {
		this.channelMobileSizeList = channelMobileSizeList;
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

	public String getAdHiddenType() {
		return adHiddenType;
	}

	public void setAdHiddenType(String adHiddenType) {
		this.adHiddenType = adHiddenType;
	}

	public String getFileuploadFileName() {
		return fileuploadFileName;
	}

	public void setFileuploadFileName(String fileuploadFileName) {
		this.fileuploadFileName = fileuploadFileName;
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

	public String getAdOperatingRule() {
		return adOperatingRule;
	}

	public void setAdOperatingRule(String adOperatingRule) {
		this.adOperatingRule = adOperatingRule;
	}

	public String[] getVideoDetailMap() {
		return videoDetailMap;
	}

	public void setVideoDetailMap(String[] videoDetailMap) {
		this.videoDetailMap = videoDetailMap;
	}

	public String getAdVideoURL() {
		return adVideoURL;
	}

	public void setAdVideoURL(String adVideoURL) {
		this.adVideoURL = adVideoURL;
	}

	public IPfpAdVideoSourceService getPfpAdVideoSourceService() {
		return pfpAdVideoSourceService;
	}

	public void setPfpAdVideoSourceService(IPfpAdVideoSourceService pfpAdVideoSourceService) {
		this.pfpAdVideoSourceService = pfpAdVideoSourceService;
	}

	public Map<String, String> getAdVideoSizeMap() {
		return adVideoSizeMap;
	}

	public void setAdVideoSizeMap(Map<String, String> adVideoSizeMap) {
		this.adVideoSizeMap = adVideoSizeMap;
	}

	public String getVideoTime() {
		return videoTime;
	}

	public void setVideoTime(String videoTime) {
		this.videoTime = videoTime;
	}

	public String getAdTitle() {
		return adTitle;
	}

	public void setAdTitle(String adTitle) {
		this.adTitle = adTitle;
	}

	public String getAdGroupChannelPrice() {
		return adGroupChannelPrice;
	}

	public void setAdGroupChannelPrice(String adGroupChannelPrice) {
		this.adGroupChannelPrice = adGroupChannelPrice;
	}

	public boolean isVerticalAd() {
		return verticalAd;
	}

	public void setVerticalAd(boolean verticalAd) {
		this.verticalAd = verticalAd;
	}

	public String getBookmark() {
		return bookmark;
	}

	public void setBookmark(String bookmark) {
		this.bookmark = bookmark;
	}

	public RedisAPI getRedisAPI() {
		return redisAPI;
	}

	public void setRedisAPI(RedisAPI redisAPI) {
		this.redisAPI = redisAPI;
	}

	public String getAdmenuul() {
		return admenuul;
	}

	public void setAdmenuul(String admenuul) {
		this.admenuul = admenuul;
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

	public float getAdActionMax() {
		return adActionMax;
	}

	public void setAdActionMax(float adActionMax) {
		this.adActionMax = adActionMax;
	}

	public String getAdActionSeq() {
		return adActionSeq;
	}

	public void setAdActionSeq(String adActionSeq) {
		this.adActionSeq = adActionSeq;
	}

	public IPfpAdActionService getPfpAdActionService() {
		return pfpAdActionService;
	}

	public void setPfpAdActionService(IPfpAdActionService pfpAdActionService) {
		this.pfpAdActionService = pfpAdActionService;
	}

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	public String getAdFastPublishUrlInfo() {
		return adFastPublishUrlInfo;
	}

	public void setAdFastPublishUrlInfo(String adFastPublishUrlInfo) {
		this.adFastPublishUrlInfo = adFastPublishUrlInfo;
	}

	public IPfpAdManyURLSearchService getPfpAdManyURLSearchService() {
		return pfpAdManyURLSearchService;
	}

	public void setPfpAdManyURLSearchService(IPfpAdManyURLSearchService pfpAdManyURLSearchService) {
		this.pfpAdManyURLSearchService = pfpAdManyURLSearchService;
	}

	public void setManyURLRediskey(String manyURLRediskey) {
		this.manyURLRediskey = manyURLRediskey;
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

	public AdFactory getAdFactory() {
		return adFactory;
	}

	public void setAdFactory(AdFactory adFactory) {
		this.adFactory = adFactory;
	}

	public CookieUtil getCookieUtil() {
		return cookieUtil;
	}

	public void setCookieUtil(CookieUtil cookieUtil) {
		this.cookieUtil = cookieUtil;
	}

	public PfpCustomerInfoService getPfpCustomerInfoService() {
		return pfpCustomerInfoService;
	}

	public PfpAdGroup getPfpAdGroup() {
		return pfpAdGroup;
	}

	public void setPfpAdGroup(PfpAdGroup pfpAdGroup) {
		this.pfpAdGroup = pfpAdGroup;
	}

	public PfpAdKeywordService getPfpAdKeywordService() {
		return pfpAdKeywordService;
	}

	public void setBackPage(String backPage) {
		this.backPage = backPage;
	}

	public void setPfpAdKeywords(List<PfpAdKeyword> pfpAdKeywords) {
		this.pfpAdKeywords = pfpAdKeywords;
	}

	public PfpAdExcludeKeywordService getPfpAdExcludeKeywordService() {
		return pfpAdExcludeKeywordService;
	}
	

	public String getUploadLog() {
		return uploadLog;
	}

	public void setUploadLog(String uploadLog) {
		this.uploadLog = uploadLog;
	}

	public String getUploadLogoLog() {
		return uploadLogoLog;
	}

	public void setUploadLogoLog(String uploadLogoLog) {
		this.uploadLogoLog = uploadLogoLog;
	}

	public ISequenceService getSequenceService() {
		return sequenceService;
	}

	public String getAdName() {
		return adName;
	}

	public void setAdName(String adName) {
		this.adName = adName;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public String getCatalogGroupId() {
		return catalogGroupId;
	}

	public void setCatalogGroupId(String catalogGroupId) {
		this.catalogGroupId = catalogGroupId;
	}

	public String getLogoType() {
		return logoType;
	}

	public void setLogoType(String logoType) {
		this.logoType = logoType;
	}

	public String getLogoText() {
		return logoText;
	}

	public void setLogoText(String logoText) {
		this.logoText = logoText;
	}

	public String getLogoBgColor() {
		return logoBgColor;
	}

	public void setLogoBgColor(String logoBgColor) {
		this.logoBgColor = logoBgColor;
	}

	public String getLogoFontColor() {
		return logoFontColor;
	}

	public void setLogoFontColor(String logoFontColor) {
		this.logoFontColor = logoFontColor;
	}

	public String getBtnTxt() {
		return btnTxt;
	}

	public void setBtnTxt(String btnTxt) {
		this.btnTxt = btnTxt;
	}

	public String getBtnFontColor() {
		return btnFontColor;
	}

	public void setBtnFontColor(String btnFontColor) {
		this.btnFontColor = btnFontColor;
	}

	public String getBtnBgColor() {
		return btnBgColor;
	}

	public void setBtnBgColor(String btnBgColor) {
		this.btnBgColor = btnBgColor;
	}

	public String getDisTxtType() {
		return disTxtType;
	}

	public void setDisTxtType(String disTxtType) {
		this.disTxtType = disTxtType;
	}

	public String getDisFontColor() {
		return disFontColor;
	}

	public void setDisFontColor(String disFontColor) {
		this.disFontColor = disFontColor;
	}

	public void setDivBatchWord(String divBatchWord) {
		this.divBatchWord = divBatchWord;
	}

	public void setBatchkeywords(String batchkeywords) {
		this.batchkeywords = batchkeywords;
	}

	public String getDisBgColor() {
		return disBgColor;
	}

	public void setDisBgColor(String disBgColor) {
		this.disBgColor = disBgColor;
	}

	public SimpleDateFormat getSdf() {
		return sdf;
	}

	public static void main(String args[]){
		try {
			String url ="data:image/jpeg;base64,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";
//			String url ="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";
			String data[] = url.split(",");
			BufferedImage image = null;
	        byte[] imageByte;
			
	        
	        
	        byte[] imgByte = Base64.decodeBase64(data[1].getBytes());
	        ByteArrayInputStream bis = new ByteArrayInputStream(imgByte);
            image = ImageIO.read(bis);
            ImageIO.write(image, "png", new File("D:/img_output/alex.png"));
            bis.close();
			
			
			
//			String d = "d6022c051963fab9d531de59c8d93afc";
//			BASE64Decoder decoder = new BASE64Decoder();
//			decoder.decodeBuffer(new )(d.getBytes());
			
//			String a ="data:image/jpeg;base64,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";
//			String md5 = "";
//			MessageDigest digest = MessageDigest.getInstance("MD5");
//			digest.update(a.getBytes(), 0, a.length());
//			md5 = new BigInteger(1, digest.digest()).toString(16);
//			System.out.println(md5);
			
			
			
			
//			MessageDigest md56 =MessageDigest.getInstance("SHA");
//	        String messageStr=a;
//	        md56.update(messageStr.getBytes());
//	        byte[] summery= md56.digest();
//	        StringBuffer md5StrBuff = new StringBuffer();
//	        for (int i = 0; i < summery.length; i++) {  
//	                if (Integer.toHexString(0xFF & summery[i]).length() == 1){  
//	                    md5StrBuff.append("0").append(  
//	                            Integer.toHexString(0xFF & summery[i]));  
//	                }else{  
//	                    md5StrBuff.append(Integer.toHexString(0xFF & summery[i]));  
//	                }  
//	            } 
//	        
//	        System.out.println(md5StrBuff.toString());
			
			
			
			
//			AdAddAction AdAddAction = new AdAddAction();
			
//	        String prodData = "{\"prodGroupList\":[{\"ec_stock_status\":\"1\",\"id\":\"3\",\"ec_use_status\":\"1\",\"ec_title\":\"長洋\",\"ec_url\":\"http://www.pazzo.com.tw/Detail/P402018562000\",\"ec_name\":\"漫步小花園一字領荷葉長洋\",\"ec_img\":\"img/user/AC2013071700005/catalogProd/PC2018082\",\"catalog_seq\":\"PC201808210000000001\",\"catalog_prod_seq\":\"P003\",\"ec_category\":\"洋裝\",\"ec_discount_price\":\"NT.1200\",\"ec_price\":\"NT.1500\"},{\"ec_stock_status\":\"1\",\"id\":\"1\",\"ec_use_status\":\"1\",\"ec_title\":\"連身褲\",\"ec_url\":\"http://www.pazzo.com.tw/detail/P402017001303\",\"ec_name\":\"MIT 小性感露肩翻領連身褲\",\"ec_img\":\"img/user/AC2013071700005/catalogProd/PC2018082\",\"catalog_seq\":\"PC201808210000000001\",\"catalog_prod_seq\":\"P001\",\"ec_category\":\"褲子\",\"ec_discount_price\":\"NT.500\",\"ec_price\":\"NT.1000\"},{\"ec_stock_status\":\"1\",\"id\":\"4\",\"ec_use_status\":\"1\",\"ec_title\":\"洋裝\",\"ec_url\":\"http://www.pazzo.com.tw/Detail/P402018090304\",\"ec_name\":\"輕夏蕾絲簍空小花洋裝\",\"ec_img\":\"img/user/AC2013071700005/catalogProd/PC2018082\",\"catalog_seq\":\"PC201808210000000001\",\"catalog_prod_seq\":\"P004\",\"ec_category\":\"洋裝\",\"ec_discount_price\":\"NT.1000\",\"ec_price\":\"NT.2000\"}]}";
////			String prodData = com.pchome.soft.depot.utils.HttpUtil.getInstance().getResult("http://showstg.pchome.com.tw/pfp/prodGroupListAPI.html?groupId="+adProdgroupId+"&prodNum=10", "UTF-8");
//			JSONObject json = new JSONObject(prodData);
//			JSONArray array = (JSONArray) json.get("prodGroupList");
//			System.out.println(array.length());
//			System.out.println(array.get(0));
//			
//			
//			JSONObject obj = (JSONObject) array.get(0);
//			String obj.get("")
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		
	}
	
}

