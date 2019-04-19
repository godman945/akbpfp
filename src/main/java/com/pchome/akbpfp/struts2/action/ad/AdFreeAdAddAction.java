package com.pchome.akbpfp.struts2.action.ad;

import java.awt.Color;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

import com.pchome.akbpfp.api.ControlPriceAPI;
import com.pchome.akbpfp.api.SyspriceOperaterAPI;
import com.pchome.akbpfp.db.pojo.AdmDefineAd;
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
import com.pchome.akbpfp.db.service.sequence.ISequenceService;
import com.pchome.akbpfp.db.vo.ad.AdFreeVO;
import com.pchome.akbpfp.db.vo.ad.KWVO;
import com.pchome.akbpfp.db.vo.ad.PfpAdDetailVO;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.ad.EnumAdStyle;
import com.pchome.enumerate.ad.EnumExcludeKeywordStatus;
import com.pchome.enumerate.cookie.EnumCookieConstants;
import com.pchome.enumerate.sequence.EnumSequenceTableName;
import com.pchome.enumerate.utils.EnumStatus;
import com.pchome.soft.depot.utils.CookieUtil;
import com.pchome.soft.depot.utils.EncodeUtil;
import com.pchome.soft.depot.utils.HttpUtil;


public class AdFreeAdAddAction extends BaseCookieAction{

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
	private String imghead;
	private String imgFile;
	private String backPage;				// 取消的返回頁面
	private String classifiedsServer;
	private String divBatchWord = "display:none;";
	private String batchkeywords = "";
	private List<KWVO> likw;

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
	private ControlPriceAPI controlPriceAPI;

	public String getAid() {
		// 讀取cookie
		String cookieData = CookieUtil.getCookie(request, "aid", EnumCookieConstants.COOKIE_USING_CODE.getValue());
		String decodeAid = EncodeUtil.getInstance().decryptAES(cookieData, EnumCookieConstants.COOKIE_PFP_SECRET_KEY.getValue());
		return decodeAid;
	}

	public String AdFreeAdAdd() throws Exception {
		log.info("AdFreeAdAdd => adGroupSeq = " + adGroupSeq);
		String referer = request.getHeader("Referer");
		backPage = "adActionView.html";
		if(StringUtils.isNotEmpty(referer)) {
			if(referer.indexOf("adFreeGroupAdd.html") >= 0 || referer.indexOf("AdFreeAdAdd.html") >= 0) {
				backPage = "adFreeGroupAdd.html?adGroupSeq=" + adGroupSeq;
			} else {
				backPage = referer;
			}
			if(referer.indexOf("adFreeGroupAdd.html") >= 0){
			// 重算調控金額
			controlPriceAPI.countProcess(pfpCustomerInfoService.findCustomerInfo(super.getCustomer_info_id()));
			}
		}

		saveAndNew = "";
		if(adStyle == null)		adStyle = "TMG";

		PfpAdGroup pfpAdGroup = pfpAdGroupService.getPfpAdGroupBySeq(adGroupSeq);
		adActionName  = pfpAdGroup.getPfpAdAction().getAdActionName();
		adGroupName  = pfpAdGroup.getAdGroupName();
		String aid = getAid();
		log.info("aid = " + aid);

		PfpCustomerInfo pfpCustomerInfo = pfpCustomerInfoService.findCustomerInfo(super.getCustomer_info_id());
		String customerInfoId = pfpCustomerInfo.getCustomerInfoId();
		String adCustomerInfoId = pfpAdGroup.getPfpAdAction().getPfpCustomerInfo().getCustomerInfoId();
		if(!customerInfoId.equals(adCustomerInfoId)) {
			return "notOwner";
		}

        // 上傳圖片暫存檔名(亂數產生)
		ulTmpName = RandomStringUtils.randomAlphanumeric(30);
        //log.info("ulTmpName = " + ulTmpName);

        // aid不為 null 時，才會有資料
		if(StringUtils.isNotBlank(aid)) {
			String member_id =  super.getId_pchome();
			log.info("memberID = " + member_id);

			Map<String, AdFreeVO> adFrees = adUtils.getAdFree(classifiedsServer, member_id, aid, null);
			AdFreeVO adFreeVO = adFrees.get(aid);
			adDetailContent = new String[5];
			if(adFreeVO != null && adFreeVO.getStatus() != null && adFreeVO.getStatus().equals("success")) {
				adDetailContent[0] = "img/public/na.gif\" style=\"display:none";
				imgFile = "";
				log.info("adFreeVO.getImg_path() = " + adFreeVO.getImg_path());
	
				if(StringUtils.isNotEmpty(adFreeVO.getImg_path())) {
					String img_path = StringUtils.isNotEmpty(adFreeVO.getImg_path())?adFreeVO.getImg_path():adDetailContent[0];
					//adDetailContent[0] = StringUtils.isNotEmpty(adFreeVO.getImg_path())?adFreeVO.getImg_path():adDetailContent[0];
	
					BufferedImage image = null;
					try {
			        	URL url = new URL(img_path);
			            image = ImageIO.read(url);
			            log.info("image type = " + image.getType());
			            
			            String imgName = url.getFile().substring(url.getFile().indexOf("/") + 1);
			            log.info("imgName = " + imgName);
			            int loc = imgName.indexOf(".");
			            String imgType = "jpg";
	
			            if(loc >= 0) {
				            imgType = imgName.substring(url.getFile().indexOf(".") + 1);
			            }
			            log.info("imgType = " + imgType);
	
						// 處理完成暫存檔
						File tmpFile = new File(photoTmpPath, ulTmpName + "." + imgType);
						log.info("tmpFile = " + tmpFile.getAbsolutePath());
	
				    	if( image.getColorModel().getTransparency() != Transparency.OPAQUE) {
				    		image = fillTransparentPixels(image, Color.WHITE);
				    	}
		            
			            ImageIO.write(image, "jpg", tmpFile);
		
			            imgFile = tmpFile.getAbsolutePath();
			            log.info("imgFile = " + imgFile);
			            imghead = "img/tmp/" + tmpFile.getName();
						log.info("imghead = " + imghead);
		            	
						adDetailContent[0] = StringUtils.isNotEmpty(adFreeVO.getImg_path())?imgFile:adDetailContent[0];
			        } catch (Exception e) {
			        	e.printStackTrace();
			        }
					
				}
				// 廣告標題
				adDetailContent[1] = adFreeVO.getTitle();		// adTitle / title
				// 廣告內容
				adDetailContent[2] = StringUtils.isNotEmpty(adFreeVO.getContent())?adFreeVO.getContent():adFreeVO.getTitle();		// adContent / content
				// 如果有網址的話，就填入資料頁面
				if(StringUtils.isNotEmpty(adFreeVO.getUrl())) {
					String urlstr = adFreeVO.getUrl();
					//log.info("urlstr = " + urlstr);
					adDetailContent[3] = urlstr;			// adLinkURL / real_url
					adDetailContent[4] = urlstr.substring(urlstr.indexOf("://") + 3);			// adShowURL / show_url
					if(adDetailContent[4].indexOf("/") > 0) {
						adDetailContent[4] = adDetailContent[4].substring(0, adDetailContent[4].indexOf("/"));
					}
				}
	
				// 如果來源資料的 tags 有資料的話，就放入大量新增關鍵字來進行處理 
				//log.info("adFreeVO.getTags() = " + adFreeVO.getTags());
				if(StringUtils.isNotEmpty(adFreeVO.getTags())) {
					try {
						likw = new ArrayList<KWVO>();
						String batchKWs[] = adFreeVO.getTags().split(",");
						for(int i = 0; i < batchKWs.length; i++) {
							if(StringUtils.isNotBlank(batchKWs[i])){  
								KWVO kwvo = new KWVO();
								kwvo.setKw_no(Integer.toString(i));
								kwvo.setKw_id("keywords_"+i);
								kwvo.setKw_name("keywords");
								kwvo.setKw_value(batchKWs[i]);
								likw.add(kwvo);
							}
						}
					}catch(Exception ex){
						log.info("ex = " + ex.toString());
					}
				}
			}
		}

		// 取出分類所屬關鍵字
		pfpAdKeywords = pfpAdKeywordService.findAdKeywords(null, adGroupSeq, null, null, null, "10");
		// 取出分類所屬排除關鍵字
		pfpAdExcludeKeywords = pfpAdExcludeKeywordService.getPfpAdExcludeKeywords(adGroupSeq, pfpCustomerInfo.getCustomerInfoId());
		
		return SUCCESS;
	}

	public static BufferedImage fillTransparentPixels( BufferedImage image, Color fillColor ) {
		int w = image.getWidth();
		int h = image.getHeight();
		int [] pixels = new int[w * h];
		PixelGrabber pg = new PixelGrabber(image,0,0,w,h,pixels,0,w);
		try {
			pg.grabPixels();
		} catch(InterruptedException ie) {
			ie.printStackTrace();
		}
		
		BufferedImage image2 = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		image2.setRGB(0,0,w,h,pixels,0,w);

		return image2;
	}

	
	// 新增圖文式廣告
	public String doAdFreeAdAddTmg() throws Exception {
		log.info("doAdFreeAdAddTmg => adGroupSeq = " + adGroupSeq + "; saveAndNew = '" + saveAndNew + "'");
		result = "noAdd";

		// 檢查 adStyle 是否正確，正確的話，設定 adPoolSeq、templateProductSeq
		chkAdStyle();
		
		// 檢查 Form 資料是否正確
		chkAdData1();

		if(message != null && !message.equals("")) {
			msg = new ByteArrayInputStream(message.getBytes());
			return SUCCESS;
		}

		PfpAdGroup pfpAdGroup = pfpAdGroupService.getPfpAdGroupBySeq(adGroupSeq);

		// 新增廣告
		addAd(pfpAdGroup);

		String imgDetail = "";
		PfpAdDetailVO pfpAdDetailVO = new PfpAdDetailVO();
		for(int i = 0; i < adDetailID.length; i++) {
			if(i == 0 && adStyle.equals("TMG")) {
				try {
					log.info("imgFile = " + imgFile);
					if(StringUtils.isNotBlank(imgFile)) {
						File iPath = new File(photoPath);		// 圖片的存放路徑
						File iTmpPath = new File(photoTmpPath);	// 暫存圖片的路徑
						if(!iPath.exists())			iPath.mkdirs();
						if(!iTmpPath.exists())		iTmpPath.mkdirs();
						
						File adFile = new File(photoPath, adSeq + ".jpg");	// 上傳圖片的檔名
						File tmpFile = new File(imgFile);	// 設定圖片的 File 元件
						tmpFile.renameTo(adFile);			// 把暫存圖片搬到存放區
						
						imgDetail = photoDbPath + adFile.getName();	// 設定圖片檔存放在 DB 的路徑
						System.out.println("imgDetail = " + imgDetail);
					} else {
						if(StringUtils.isBlank(adDetailContent[0])) {
							imgDetail = "img/public/na.gif\" style=\"display:none";
						}
					}
				} catch (Exception ex) {
					System.out.println("ex : " + ex);
				}

			}

			adDetailSeq = sequenceService.getId(EnumSequenceTableName.PFP_AD_DETAIL, "_");
			List<AdmDefineAd> admDefineAd = defineAdService.getDefineAdByCondition(null, adDetailID[i], null, adPoolSeq);
			String defineAdSeq = admDefineAd.get(0).getDefineAdSeq();
			if(adDetailID[i].equals("real_url")) {
				if(adDetailContent[i].indexOf("http") !=0 ) {
					adDetailContent[i] = "http://" + adDetailContent[i];
				}
			}
			if(adDetailID[i].equals("show_url")) {
				String tmpHttp = adDetailContent[i];
				if(adDetailContent[i].indexOf("http") == 0 || adDetailContent[i].indexOf("file") == 0) {
					adDetailContent[i] = tmpHttp.substring(tmpHttp.indexOf("://") + 3);
					//System.out.println("adDetailContent["+i+"] = " + adDetailContent[i]);
				}
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
			//return "saveNew";
		} else {
			result = "saveOK";
		}
		msg = new ByteArrayInputStream(result.getBytes());
		return SUCCESS;
	}

	// 新增文字式廣告
	public String doAdAdAddTxt() throws Exception {
		System.out.println("doAdAdAdd");
		System.out.println("adGroupSeq = " + adGroupSeq);
		System.out.println("saveAndNew = '" + saveAndNew + "'");

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
			System.out.println("adDetailSeq = " + adDetailSeq);
			System.out.println("adDetailID[" + i + "] = " + adDetailID[i]);
			List<AdmDefineAd> admDefineAd = defineAdService.getDefineAdByCondition(null, adDetailID[i], null, adPoolSeq);
			String defineAdSeq = admDefineAd.get(0).getDefineAdSeq();
			System.out.println("defineAdSeq = " + defineAdSeq);
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
						if(adDetailContent[i].length() > 1024) {
							message = "廣告連結網址不可超過 1024  字！";
						} else {
							String url = adDetailContent[i];
							int urlState = 0;
							if(url.indexOf("http") != 0) {
								url = "http://"+url;
							}
							urlState = HttpUtil.getInstance().getStatusCode(url);
							//System.out.println("real_url = " + url + "; urlState = " + urlState);
							if(urlState < 200 && urlState >= 300) {
								message = "請輸入正確的廣告連結網址！";
							}
						}
					} else if(adDetailID[i].equals("show_url")) {
						int maxleng = 30;
						if(adDetailContent[i].indexOf("://") > 0) {
							maxleng = 37;
						}
								
						if(adDetailContent[i].length() > maxleng) {
							message = "廣告顯示網址不可超過 "+maxleng+"  字！";
						} else {
							String url = adDetailContent[i];
							int urlState = 0;
							if(url.indexOf("http") != 0) {
								url = "http://"+url;
							}
							urlState = HttpUtil.getInstance().getStatusCode(url);
							//System.out.println("show_url = " + url + "; urlState = " + urlState);
							if(urlState < 200 && urlState >= 300) {
								message = "請輸入正確的廣告顯示網址！";
							}
						}
					}
				}
				//System.out.println(adDetailID[i] + "=>" + adDetailContent[i]);
			}
		} catch(Exception ex) {
			System.out.println("Exception ex :" + ex);
		}
	}

	//新增廣告
	private void addAd(PfpAdGroup pfpAdGroup) {
		try {
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
			System.out.println("Exception ex" + ex);
		}
	}
	
	// 新增關鍵字
	private void addKeywords(PfpAdGroup pfpAdGroup) {
		try {
			if(keywords != null && keywords.length > 0) {
				for(int i = 0; i < keywords.length; i++) {
					List<PfpAdKeyword> KWExist = pfpAdKeywordService.findAdKeywords(null, adGroupSeq, keywords[i], null, null, "10");
					//System.out.println("keywords["+i+"] = " + keywords[i] + "; existCount =" + KWExist.size());
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
			System.out.println("Exception(addKeywords) : " + ex);
		}
	}

	// 新增排除關鍵字
	private void addExcludeKeywords(PfpAdGroup pfpAdGroup) {
		try {
			if(excludeKeywords != null && excludeKeywords.length > 0) {
				for(int i = 0; i < excludeKeywords.length; i++) {
					//System.out.println("excludeKeywords["+i+"] = " + excludeKeywords[i]);
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
			System.out.println("Exception(addExcludeKeywords):" + ex);
		}
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

	public String getImghead() {
		return imghead;
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

	public void setClassifiedsServer(String classifiedsServer) {
		this.classifiedsServer = classifiedsServer;
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

	public List<KWVO> getLikw() {
		return likw;
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

}
