package com.pchome.akbpfp.db.service.report;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import com.pchome.akbpfp.db.dao.ad.IPfpAdActionDAO;
import com.pchome.akbpfp.db.dao.ad.IPfpAdDAO;
import com.pchome.akbpfp.db.dao.ad.IPfpAdGroupDAO;
import com.pchome.akbpfp.db.dao.ad.PfpAdDetailDAO;
import com.pchome.akbpfp.db.dao.report.AdReportVO;
import com.pchome.akbpfp.db.dao.report.AdvertiseReportVO;
import com.pchome.akbpfp.db.dao.report.IAdReportDAO;
import com.pchome.akbpfp.db.dao.template.IAdmTemplateProductDAO;
import com.pchome.akbpfp.db.pojo.AdmTemplateProduct;
import com.pchome.akbpfp.db.pojo.PfpAd;
import com.pchome.akbpfp.db.pojo.PfpAdAction;
import com.pchome.akbpfp.db.pojo.PfpAdDetail;
import com.pchome.akbpfp.db.pojo.PfpAdGroup;
import com.pchome.akbpfp.db.pojo.PfpCatalogLogo;
import com.pchome.akbpfp.db.pojo.PfpCatalogSetup;
import com.pchome.akbpfp.db.service.catalog.prod.IPfpCatalogLogoService;
import com.pchome.akbpfp.db.service.catalog.prod.IPfpCatalogSetupService;
import com.pchome.enumerate.report.EnumReport;
import com.pchome.enumerate.report.EnumReportAdType;
import com.pchome.enumerate.report.EnumReportDevice;
import com.pchome.enumerate.utils.EnumStatus;
import com.pchome.utils.CommonUtils;

public class AdReportService implements IAdReportService {

	private IAdReportDAO adReportDAO;
	private PfpAdDetailDAO pfpAdDetailDAO;
	private IPfpCatalogSetupService pfpCatalogSetupService;
	private IPfpCatalogLogoService pfpCatalogLogoService;
	
	private IPfpAdDAO pfpAdDAO;
	private IPfpAdGroupDAO pfpAdGroupDAO;
	private IPfpAdActionDAO pfpAdActionDAO;
	private IAdmTemplateProductDAO admTemplateProductDAO;
	
	@Override
    public List<AdReportVO> loadReportDate(String sqlType, String adGroupSeq, String adSeq, String searchText, String adSearchWay, String adShowWay, String adPvclkDevice, String customerInfoId, String adOperatingRule, String startDate, String endDate,int page,int pageSize) throws Exception {

		List<AdReportVO> dataList = adReportDAO.getReportList(sqlType, adGroupSeq, adSeq, searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, adOperatingRule, startDate, endDate, page, pageSize);
		//補上預覽 html code
		if (sqlType.trim().equals(EnumReport.REPORT_HQLTYPE_ADVERTISE.getTextValue())){
		    List<PfpAdDetail> pfpAdDetailList = null;
		    String realUrl = null;
		    String img = null;
		    String title = null;
		    String adStyle = null;
		    String adSize = null;
		    String html5Title = null;
		    String html5Flag = "N";
		    String videoSeconds = null;
		    String showUrl = null ;
		    String videoUrl = null;
		    String content = null;
		    String pfpCustomerInfoId = null;
		    String dbAdseq = null;
			for (int i = 0; i< dataList.size(); i++) {
				AdReportVO adReportVO = dataList.get(i);
				pfpCustomerInfoId = adReportVO.getCustomerInfoId();
				dbAdseq = adReportVO.getAdSeq();
				pfpAdDetailList = pfpAdDetailDAO.getPfpAdDetailByAdSeq(adReportVO.getAdSeq());
				showUrl = null;
				realUrl = null;
	            img = null;
	            title = null;
	            adStyle = null;
	            adSize = null;
			    html5Title = null;
			    html5Flag = "N";
			    videoSeconds = null;
			    videoUrl = null;
			    content = null;
			    
			    
			    //商品廣告參數內容
			    String prodGroup = "";
			    String logoText = "";
			    String logoFontColor = "";
			    String logoBgColor = "";
			    String btnTxt = "";
			    String btnFontColor = "";
			    String btnBgColor = "";
			    String disTxtType = "";
			    String disFontColor = "";
			    String disBgColor = "";
			    String saleImg = "";
			    String saleEndImg = "";
			    String catalogSeq = "";
			    String adName = "";
			    String prodRadioLogoType ="";
			    //以下程式自行判斷參數
			    //預設不呈現底圖
			    String adbgType = "noposter";
			    //根據setup決定是否滿版
			    String imgProportiona = "";
			    //根據有無行銷圖決定
			    String logoType = "";
			    String userLogoPath = "";
			    String previewTpro = "c_x05_pad_tpro_0145";
			   
			    		
				for (PfpAdDetail pfpAdDetail: pfpAdDetailList) {
					if("video_seconds".equals(pfpAdDetail.getAdDetailId())){
						videoSeconds = pfpAdDetail.getAdDetailContent();
					}
					if("video_url".equals(pfpAdDetail.getAdDetailId())){
						videoUrl = pfpAdDetail.getAdDetailContent();
					}
					if("video_size".equals(pfpAdDetail.getAdDetailId())){
						adSize = pfpAdDetail.getAdDetailContent();
					}
                    if ("real_url".equals(pfpAdDetail.getAdDetailId())) {
                        realUrl = pfpAdDetail.getAdDetailContent();
                    }
                    if ("img".equals(pfpAdDetail.getAdDetailId())) {
                        img = pfpAdDetail.getAdDetailContent();
                    }
                    if ("title".equals(pfpAdDetail.getAdDetailId())) {
                    	title = pfpAdDetail.getAdDetailContent();
                    }
                    if ("content".equals(pfpAdDetail.getAdDetailId())) {
                    	content = pfpAdDetail.getAdDetailContent();
                    }
                    if("size".equals(pfpAdDetail.getAdDetailId())){
                    	adSize = pfpAdDetail.getAdDetailContent();
                    }
                    if("zip".equals(pfpAdDetail.getAdDetailId())){
                    	html5Title = pfpAdDetail.getAdDetailContent();
                    }
                    if (StringUtils.isBlank(adStyle)) {
                        adStyle = pfpAdDetail.getPfpAd().getAdStyle();
                    }
                    if(StringUtils.equals("c_x05_po_tad_0059", pfpAdDetail.getPfpAd().getAdAssignTadSeq()) || StringUtils.equals("c_x03_po_tad_0167", pfpAdDetail.getPfpAd().getAdAssignTadSeq()) || StringUtils.equals("c_x03_po_tad_0168", pfpAdDetail.getPfpAd().getAdAssignTadSeq()) ){
                    	html5Flag = "Y";
                    }
                    if("show_url".equals(pfpAdDetail.getAdDetailId())){
                    	showUrl = pfpAdDetail.getAdDetailContent();
                    }
                    
                    
                    //商品廣告
                    if("prod_report_name".equals(pfpAdDetail.getAdDetailId())){
                    	adName = pfpAdDetail.getAdDetailContent();
                    }
                    if("prod_group".equals(pfpAdDetail.getAdDetailId())){
                    	prodGroup = pfpAdDetail.getAdDetailContent();
                    }
                    if("prod_ad_url".equals(pfpAdDetail.getAdDetailId())){
                    	realUrl = pfpAdDetail.getAdDetailContent();
                    }
                    if("logo_type".equals(pfpAdDetail.getAdDetailId())){
                    	logoType = pfpAdDetail.getAdDetailContent();
                    }
                    if("logo_txt".equals(pfpAdDetail.getAdDetailId())){
                    	logoText = pfpAdDetail.getAdDetailContent();
                    }
                    if("logo_font_color".equals(pfpAdDetail.getAdDetailId())){
                    	logoFontColor = pfpAdDetail.getAdDetailContent();
                    }
                    if("logo_bg_color".equals(pfpAdDetail.getAdDetailId())){
                    	logoBgColor = pfpAdDetail.getAdDetailContent();
                    }
                    if("buybtn_txt".equals(pfpAdDetail.getAdDetailId())){
                    	btnTxt = pfpAdDetail.getAdDetailContent();
                    }
                    if("buybtn_font_color".equals(pfpAdDetail.getAdDetailId())){
                    	btnFontColor = pfpAdDetail.getAdDetailContent();
                    }
                    if("buybtn_bg_color".equals(pfpAdDetail.getAdDetailId())){
                    	btnBgColor = pfpAdDetail.getAdDetailContent();
                    }
                    if("dis_txt_type".equals(pfpAdDetail.getAdDetailId())){
                    	disTxtType = pfpAdDetail.getAdDetailContent();
                    }
                    if("dis_font_color".equals(pfpAdDetail.getAdDetailId())){
                    	disFontColor = pfpAdDetail.getAdDetailContent();
                    }
                    if("dis_bg_color".equals(pfpAdDetail.getAdDetailId())){
                    	disBgColor = pfpAdDetail.getAdDetailContent();
                    }
                    if("sale_img_300x250".equals(pfpAdDetail.getAdDetailId())){
                    	saleEndImg = pfpAdDetail.getAdDetailContent();
                    }
                    if("logo_sale_img_300x55".equals(pfpAdDetail.getAdDetailId())){
                    	saleImg = pfpAdDetail.getAdDetailContent();
                    }
                    if("prod_list".equals(pfpAdDetail.getAdDetailId())){
                    	catalogSeq = pfpAdDetail.getAdDetailContent();
                    }
                    if("prod_radio_logo_type".equals(pfpAdDetail.getAdDetailId())){
                    	prodRadioLogoType = pfpAdDetail.getAdDetailContent();
                    }
                    if("prod_report_name".equals(pfpAdDetail.getAdDetailId())){
                    	adName = pfpAdDetail.getAdDetailContent();
                    }
                    
				}
				String htmlCode = "";
				if ("IMG".equals(adStyle)) {
					//取得圖片尺寸
					String imgWidth = "0";
					String imgHeight = "0";
					showUrl = realUrl;
					showUrl = showUrl.replaceAll("http://", "");
					showUrl = showUrl.replaceAll("https://", "");
					if(showUrl.lastIndexOf(".com/") != -1){
						showUrl = showUrl.substring(0, showUrl.lastIndexOf(".com/") + 4);
					}
					if(showUrl.lastIndexOf(".tw/") != -1){
						showUrl = showUrl.substring(0, showUrl.lastIndexOf(".tw/") + 3);
					}
					
					if(StringUtils.equals("Y", html5Flag)){
						String[] sizeArray = adSize.split("x");
						imgWidth = sizeArray[0].trim();
						imgHeight = sizeArray[1].trim();
						
						//組html畫面
						htmlCode = "<div class=\"adreportdv\">";
						htmlCode += "<span class=\"adboxdvimg\"><span>" + html5Title + "</span></a></span>";
						htmlCode += "</span>";
						htmlCode += "<span class=\"adboxdvinf\"><span>";
						if(title != null){
							htmlCode += "<b>" + title + "</b><br>";
						}
						htmlCode += "<i>尺寸</i><b>" + imgWidth + " x " + imgHeight + "</b><br>";
						htmlCode += ("<span>" + showUrl + "</span><br><a style=\"cursor:pointer\" onclick=\"previewHtml5('" + imgWidth + "','" + imgHeight + "','" + img + "','" + realUrl + "')\">預覽</a>");
						htmlCode += "</span></span></div>";
						
						adReportVO.setContent(content);
					} else {
						File picture = null;
						FileInputStream is = null;
						BufferedImage sourceImg = null;
						try{
							picture = new File("/home/webuser/akb/pfp/" +  img.replace("\\", "/"));
							if(picture != null){
//								is = new FileInputStream(picture);
//								sourceImg = javax.imageio.ImageIO.read(is);
//								imgWidth = Integer.toString(sourceImg.getWidth());
//								imgHeight = Integer.toString(sourceImg.getHeight());
								ImageInputStream stream = new FileImageInputStream(picture);
				                Iterator<ImageReader> readers = ImageIO.getImageReaders(stream);
				                if (readers.hasNext()) {
				                    ImageReader reader = readers.next();
				                    reader.setInput(stream, true);
				                    imgWidth = String.valueOf(reader.getWidth(0));
				                    imgHeight = String.valueOf(reader.getHeight(0));
				                 }
				                 stream.close();
							}
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}catch (IOException e) {
							e.printStackTrace();
						} finally {
							if(is != null){
								is.close();
							}
						}
						//組html畫面
						htmlCode = "<div class=\"adreportdv\">";
						htmlCode += "<span class=\"adboxdvimg\"><a href=\"" + realUrl + "\" target=\"_blank\"><img src=\"" + img + "\" /></a></span>";
						htmlCode += "<span class=\"adboxdvinf\"><span>";
						if(title != null){
							htmlCode += "<b>" + title + "</b><br>";
						}
						htmlCode += "<i>尺寸</i><b>" + imgWidth + " x " + imgHeight + "</b><br>";
						htmlCode += ("<span>" + showUrl + "</span><br><a style=\"cursor:pointer\" onclick=\"preview('" + img + "')\">預覽</a>");
						htmlCode += "</span></span></div>";
					}
					
					adReportVO.setContent("尺寸：" + imgWidth + " x " + imgHeight);
				}else if("VIDEO".equals(adStyle)){
					String size[] = adSize.split("_");
					String width = "";
					String height =  "";
					String adPreviewVideoBgImg = img;
					String adPreviewVideoURL = videoUrl;
					String style = "";
					if(size.length == 2){
						width = size[0];
						height = size[1];
						double adratio = Double.parseDouble(height) / Double.parseDouble(width);
						double adh = 250 * adratio;
						style = "margin-top:"+ String.valueOf((adh/2) - 45) +"px;";
					}
					videoSeconds = videoSeconds.length() == 2 ?videoSeconds : "0"+videoSeconds;
					String realUrlPath = adPreviewVideoBgImg.equals("img/public/na.gif\" style=\"display:none") ? "" : adPreviewVideoBgImg;
					htmlCode = "<div style=\"display:flex;\"><div> ";
					htmlCode = htmlCode + "<iframe class=\"akb_iframe\" scrolling=\"no\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" vspace=\"0\" hspace=\"0\" id=\"pchome8044_ad_frame1\" width=\""+width+"\" height=\""+height+"\" allowtransparency=\"true\" allowfullscreen=\"true\" src=\"adVideoModel.html?adPreviewVideoURL="+adPreviewVideoURL+"&adPreviewVideoBgImg="+realUrlPath+"&realUrl="+realUrl+"&resize=true\"></iframe>";
					htmlCode = htmlCode + "</div><div style=\" word-break:break-all; text-align: left; line-height: 20px; padding: 10px;"+style+"\">";
					htmlCode = htmlCode + content+"<br>";
					htmlCode = htmlCode + "<div class='ad_size'>尺寸 "+width+" x "+height+"</div>";
					htmlCode = htmlCode + "時間 00:"+videoSeconds+"<br>";
					htmlCode = htmlCode + "<a href=\""+realUrl+"\" target=\"_blank\" >"+realUrl;
					htmlCode = htmlCode+"</div></div>";
					
					adReportVO.setTitle(content);
					adReportVO.setContent("尺寸 :"+width+"x"+height);
					adReportVO.setAdVideoSec("00 : "+videoSeconds);
					adReportVO.setAdVideoUrl(adPreviewVideoURL);
				}else if("PROD".equals(adStyle)){
					adReportVO.setTitle(adName);
					if(StringUtils.isNotBlank(saleEndImg)){
						adbgType = "hasposter";
					}
					PfpCatalogSetup pfpCatalogSetup = getPfpCatalogSetupService().findSetupByCatalogSeq(catalogSeq);
					if(pfpCatalogSetup != null){
						imgProportiona = pfpCatalogSetup.getCatalogSetupValue();
					}
					
					List<PfpCatalogLogo> pfpCatalogLogoList = pfpCatalogLogoService.findCatalogLogoByCustomerInfoId(customerInfoId);
					for (PfpCatalogLogo pfpCatalogLogo : pfpCatalogLogoList) {
						if(logoType.equals("type1") && pfpCatalogLogo.getCatalogLogoType().equals("1")){
							userLogoPath = pfpCatalogLogo.getCatalogLogoUrl();
						}
						if(logoType.equals("type2") && pfpCatalogLogo.getCatalogLogoType().equals("0")){
							userLogoPath = pfpCatalogLogo.getCatalogLogoUrl();
						}
					}
					
					if(StringUtils.isNotBlank(saleImg)){
						logoType = "type3";
					}
					
					String posterType = "noposter";
					if(StringUtils.isNotBlank(saleEndImg)){
						posterType = "hasposter";
						
					}
					htmlCode = "<div style='float: left;'> <div>   <iframe height='250' width='300' class='akb_iframe' scrolling='no' frameborder='0' marginwidth='0' marginheight='0' vspace='0' hspace='0' id='pchome8044_ad_frame1' allowtransparency='true' allowfullscreen='true'"
					+"src='adProdModel.html"
					+"?catalogGroupId="+URLEncoder.encode(prodGroup,"UTF-8")
					+"&disTxtType="+URLEncoder.encode(disTxtType,"UTF-8")
					+"&disBgColor="+URLEncoder.encode(disBgColor,"UTF-8")
					+"&disFontColor="+URLEncoder.encode(disFontColor,"UTF-8")
					+"&btnTxt="+URLEncoder.encode(URLEncoder.encode(btnTxt,"UTF-8"),"UTF-8")
					+"&btnFontColor="+URLEncoder.encode(btnFontColor,"UTF-8")
					+"&btnBgColor="+URLEncoder.encode(btnBgColor,"UTF-8")
					+"&logoType="+URLEncoder.encode(logoType,"UTF-8")
					+"&logoText="+URLEncoder.encode(URLEncoder.encode(logoText,"UTF-8"),"UTF-8")
					+"&logoBgColor="+URLEncoder.encode(logoBgColor,"UTF-8")
					+"&logoFontColor="+URLEncoder.encode(logoFontColor,"UTF-8")
					+"&prodLogoType="+URLEncoder.encode(prodRadioLogoType,"UTF-8")
					+"&adbgType="+URLEncoder.encode(adbgType,"UTF-8")
					+"&imgProportiona="+URLEncoder.encode(imgProportiona,"UTF-8")
					+"&userLogoPath="+URLEncoder.encode(userLogoPath,"UTF-8")
					+"&previewTpro="+URLEncoder.encode(previewTpro,"UTF-8")
					+"&saleImg="+URLEncoder.encode(saleImg,"UTF-8")
					+"&saleEndImg="+URLEncoder.encode(saleEndImg,"UTF-8")
					+"&posterType="+posterType
					+"&realUrl="+URLEncoder.encode(realUrl,"UTF-8")
					+"'></iframe>"
					+"</div>"
					+"<div style='margin-top: 5%;word-break:break-all;'>廣告名稱："+adName+"<br>連結網址：<a href='"+realUrl+"' target='_blank' title=''>"+realUrl+"</a><br>"
					+"<a href='javascript:void(0)' onclick=\"previewProdAdDetail('"+pfpCustomerInfoId+"','"+dbAdseq+"')\" >查看商品成效</a>" 
					+"</div></div>";
				}else{
					htmlCode =  "<span><iframe height=\"120\" width=\"350\" src=\"adModel.html?adNo=" + adReportVO.getAdSeq() + "&tproNo=tpro_201406300001\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" frameborder=\"0\" align=\"ceneter\" class=\"akb_iframe\"></iframe></span>";
					adReportVO.setContent(content);
					adReportVO.setTitle(title);
					
				}
				
				adReportVO.setShowUrl(showUrl);
				adReportVO.setRealUrl(realUrl);
				adReportVO.setAdPreview(htmlCode);
			}
		}

		return dataList;
	}


	public void setPfpAdDAO(IPfpAdDAO pfpAdDAO) {
		this.pfpAdDAO = pfpAdDAO;
	}

	public void setPfpAdGroupDAO(IPfpAdGroupDAO pfpAdGroupDAO) {
		this.pfpAdGroupDAO = pfpAdGroupDAO;
	}

	public void setPfpAdActionDAO(IPfpAdActionDAO pfpAdActionDAO) {
		this.pfpAdActionDAO = pfpAdActionDAO;
	}

	public void setAdmTemplateProductDAO(IAdmTemplateProductDAO admTemplateProductDAO) {
		this.admTemplateProductDAO = admTemplateProductDAO;
	}

	public IPfpCatalogSetupService getPfpCatalogSetupService() {
		return pfpCatalogSetupService;
	}

	public void setPfpCatalogSetupService(IPfpCatalogSetupService pfpCatalogSetupService) {
		this.pfpCatalogSetupService = pfpCatalogSetupService;
	}

	public IPfpCatalogLogoService getPfpCatalogLogoService() {
		return pfpCatalogLogoService;
	}

	public void setPfpCatalogLogoService(IPfpCatalogLogoService pfpCatalogLogoService) {
		this.pfpCatalogLogoService = pfpCatalogLogoService;
	}

	public void setAdReportDAO(IAdReportDAO adReportDAO) {
		this.adReportDAO = adReportDAO;
	}

	public void setPfpAdDetailDAO(PfpAdDetailDAO pfpAdDetailDAO) {
		this.pfpAdDetailDAO = pfpAdDetailDAO;
	}

	/**
	 * 總廣告成效-明細、廣告明細成效共用(明細)
	 * @throws Exception 
	 */
	@Override
	public List<AdvertiseReportVO> queryReportAdvertiseData(AdvertiseReportVO vo) throws Exception {
		List<Map<String, Object>> advertiseList = adReportDAO.getAdvertiseList(vo);
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
		long nowTime = new Date().getTime();
		
		Map<String, String> adStatusMap = CommonUtils.getInstance().getAdStatusMap();
		Map<String, String> adStyleTypeMap = CommonUtils.getInstance().getAdStyleTypeMap();
		Map<String, String> adPriceTypeMap = CommonUtils.getInstance().getAdPriceTypeMap();
		String productTemplateStr = processProdSelectAdSize();
		
		// 檢查前端畫面選擇的篩選條件
		JSONObject tempJSONObject = new JSONObject();
		if(vo.getWhereMap() != null) {
			tempJSONObject = new JSONObject(vo.getWhereMap());
		}
		String selectAdType = tempJSONObject.optString("adType"); // 播放類型
		String selectAdDevice = tempJSONObject.optString("adDevice"); // 裝置
		
		List<AdvertiseReportVO> advertiseVOList = new ArrayList<>();
		for (Map<String, Object> dataMap : advertiseList) {
			AdvertiseReportVO advertiseReportVO = new AdvertiseReportVO();
			
			advertiseReportVO.setCustomerInfoId((String) dataMap.get("customer_info_id"));
			
			String adSeq = (String) dataMap.get("ad_seq");
			advertiseReportVO.setAdSeq(adSeq);
			PfpAd pfpAd = pfpAdDAO.getPfpAdBySeq(adSeq);
			int adStatus = pfpAd.getAdStatus();
			
			String adGroupSeq = (String) dataMap.get("ad_group_seq");
			PfpAdGroup pfpAdGroup = pfpAdGroupDAO.getPfpAdGroupBySeq(adGroupSeq);
			String adGroupName = pfpAdGroup.getAdGroupName();
			int adGroupStatus = pfpAdGroup.getAdGroupStatus();
			
			String adActionSeq = (String) dataMap.get("ad_action_seq");
			PfpAdAction pfpAdAction = pfpAdActionDAO.getPfpAdActionBySeq(adActionSeq);
			String adActionName = pfpAdAction.getAdActionName();
			int adActionStatus = pfpAdAction.getAdActionStatus();
			
			// 廣告狀態為開啟的話必須判斷走期( 待播放 or 走期中 or 已結束 )
			String adActionStartDate = dateFormat2.format(pfpAdAction.getAdActionStartDate());
			String adActionEndDate = dateFormat2.format(pfpAdAction.getAdActionEndDate());
			if (adActionStatus == EnumStatus.Open.getStatusId()) {
				long startDate = (dateFormat.parse(adActionStartDate + " 00:00:00")).getTime();
				long endDate = (dateFormat.parse(adActionEndDate + " 23:59:59")).getTime();
				if (nowTime < startDate) {
					adActionStatus = EnumStatus.Waitbroadcast.getStatusId();
				} else if (nowTime > endDate) {
					adActionStatus = EnumStatus.End.getStatusId();
				} else {
					adActionStatus = EnumStatus.Broadcast.getStatusId();
				}
			}
			
			// 播放狀態
			String alter = "";
			if (adActionStatus == EnumStatus.Broadcast.getStatusId() 
					&& adGroupStatus == EnumStatus.Open.getStatusId()
					&& adStatus == EnumStatus.Open.getStatusId()) {
				alter = "走期中";
				advertiseReportVO.setAdStatusOnOff(true); // on亮綠燈
			} else if (adActionStatus == EnumStatus.Broadcast.getStatusId()
					&& adGroupStatus == EnumStatus.Open.getStatusId() 
					&& adStatus != EnumStatus.Open.getStatusId()) {
				alter = "廣告明細" + adStatusMap.get(Integer.toString(adStatus));
			} else if (adActionStatus == EnumStatus.Broadcast.getStatusId()
					&& adGroupStatus != EnumStatus.Open.getStatusId() 
					&& adStatus != EnumStatus.Open.getStatusId()) {
				alter = "分類" + adStatusMap.get(Integer.toString(adGroupStatus)) 
						+ "，廣告明細" + adStatusMap.get(Integer.toString(adStatus));
			} else if (adActionStatus == EnumStatus.Broadcast.getStatusId()
					&& adGroupStatus != EnumStatus.Open.getStatusId() 
					&& adStatus == EnumStatus.Open.getStatusId()) {
				alter = "分類" + adStatusMap.get(Integer.toString(adGroupStatus));
			} else if (adActionStatus != EnumStatus.Broadcast.getStatusId()
					&& adGroupStatus == EnumStatus.Open.getStatusId() 
					&& adStatus == EnumStatus.Open.getStatusId()) {
				alter = "廣告" + adStatusMap.get(Integer.toString(adActionStatus));
			} else if (adActionStatus != EnumStatus.Broadcast.getStatusId()
					&& adGroupStatus == EnumStatus.Open.getStatusId() 
					&& adStatus != EnumStatus.Open.getStatusId()) {
				alter = "廣告" + adStatusMap.get(Integer.toString(adActionStatus)) 
						+ "，廣告明細" + adStatusMap.get(Integer.toString(adStatus));
			} else if (adActionStatus != EnumStatus.Broadcast.getStatusId()
					&& adGroupStatus != EnumStatus.Open.getStatusId() 
					&& adStatus == EnumStatus.Open.getStatusId()) {
				alter = "廣告" + adStatusMap.get(Integer.toString(adActionStatus)) 
						+ "，分類" + adStatusMap.get(Integer.toString(adGroupStatus));
			} else if (adActionStatus != EnumStatus.Broadcast.getStatusId()
					&& adGroupStatus != EnumStatus.Open.getStatusId() 
					&& adStatus != EnumStatus.Open.getStatusId()) {
				alter = "廣告" + adStatusMap.get(Integer.toString(adActionStatus)) 
						+ "，分類" + adStatusMap.get(Integer.toString(adGroupStatus)) 
						+ "，廣告明細" + adStatusMap.get(Integer.toString(adStatus));
			}
			
			advertiseReportVO.setAdStatusName(alter); // 產excel報表使用
			
			// 廣告明細
			processAdDetail(advertiseReportVO);
			advertiseReportVO.setProductTemplateStr(productTemplateStr); // 商品廣告 所有尺寸與同尺寸不同樣板
			
			advertiseReportVO.setAdActionName(adActionName); // 廣告活動
			advertiseReportVO.setAdGroupName(adGroupName); // 廣告分類
			
			// 播放類型
			if (EnumReportAdType.SEARCHANDCHANNEL.getAdType().equalsIgnoreCase(selectAdType)) {
				advertiseReportVO.setAdType(EnumReportAdType.SEARCHANDCHANNEL.getAdTypeName());
			} else {
				int adType = (int) dataMap.get("ad_type");
				String adTypeName = "";
				if (EnumReportAdType.SEARCH.getAdType().equalsIgnoreCase(String.valueOf(adType))) {
					adTypeName = EnumReportAdType.SEARCH.getAdTypeName();
				} else if (EnumReportAdType.CHANNEL.getAdType().equalsIgnoreCase(String.valueOf(adType))) {
					adTypeName = EnumReportAdType.CHANNEL.getAdTypeName();
				}
				advertiseReportVO.setAdType(adTypeName);
			}
			
			advertiseReportVO.setAdOperatingRule(adStyleTypeMap.get(dataMap.get("ad_operating_rule"))); // 廣告樣式
			advertiseReportVO.setAdClkPriceType(adPriceTypeMap.get(dataMap.get("ad_clk_price_type"))); // 廣告計費方式
			
			// 裝置
			if (EnumReportDevice.PCANDMOBILE.getDevType().equalsIgnoreCase(selectAdDevice)) {
				advertiseReportVO.setAdDevice(EnumReportDevice.PCANDMOBILE.getDevTypeName());
			} else {
				String adDevice = (String) dataMap.get("ad_device");
				if (EnumReportDevice.PC.getDevType().equalsIgnoreCase(adDevice)) {
					adDevice = EnumReportDevice.PC.getDevTypeName();
				} else if (EnumReportDevice.MOBILE.getDevType().equalsIgnoreCase(adDevice)) {
					adDevice = EnumReportDevice.MOBILE.getDevTypeName();
				}
				advertiseReportVO.setAdDevice(adDevice);
			}

			// 曝光數
			BigDecimal adPvSum = (BigDecimal) dataMap.get("ad_pv_sum");
			advertiseReportVO.setAdPvSum(adPvSum);
			
			// 互動數
			BigDecimal adClkSum = (BigDecimal) dataMap.get("ad_clk_sum");
			advertiseReportVO.setAdClkSum(adClkSum);
			
			// 互動率 = 總互動數 / 總曝光數 * 100
			advertiseReportVO.setCtr(CommonUtils.getInstance().getCalculateDivisionValue(adClkSum, adPvSum, 100));
			
			// 無效點選次數(總廣告成效用)
			advertiseReportVO.setAdInvClkSum((BigDecimal) dataMap.get("ad_invalid_clk_sum"));
			
			// 費用
			BigDecimal adPriceSum = BigDecimal.valueOf((Double) dataMap.get("ad_price_sum"));
			advertiseReportVO.setAdPriceSum(adPriceSum.doubleValue());
						
			// 單次互動費用 = 總費用 / 總互動次數
			advertiseReportVO.setAvgCost(CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, adClkSum));
			
			// 千次曝光費用 = 總費用 / 曝光數 * 1000
			Double kiloCost = CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, adPvSum, 1000);
			BigDecimal bigDecimal = BigDecimal.valueOf(kiloCost); // 算完千次曝光費用後，再處理小數至第二位，然後四捨五入
			advertiseReportVO.setKiloCost(bigDecimal.setScale(2, RoundingMode.HALF_UP).doubleValue());
			
			// 轉換次數
			BigDecimal convertCount = (BigDecimal) dataMap.get("convert_count");
			advertiseReportVO.setConvertCount(convertCount);
			
			// 轉換率 = 轉換次數 / 互動數 * 100
			advertiseReportVO.setConvertCTR(CommonUtils.getInstance().getCalculateDivisionValue(convertCount, adClkSum, 100));
			
			// 總轉換價值
			BigDecimal convertPriceCount = (BigDecimal) dataMap.get("convert_price_count");
			advertiseReportVO.setConvertPriceCount(convertPriceCount);
			
			// 平均轉換成本 = 費用 / 轉換次數
			advertiseReportVO.setConvertCost(CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, convertCount));
			
			// 廣告投資報酬率 = (總轉換價值 / 費用) * 100
			advertiseReportVO.setConvertInvestmentCost(CommonUtils.getInstance().getCalculateDivisionValue(convertPriceCount, adPriceSum, 100));
			
			advertiseVOList.add(advertiseReportVO);
		}
		
		// 處理排序
		if (StringUtils.isNotBlank(vo.getSortBy())) {
			CommonUtils.getInstance().sort(advertiseVOList, vo.getSortBy().split("-")[0], vo.getSortBy().split("-")[1]);
		}
		return advertiseVOList;
	}

	/**
	 * 取得商品廣告預覽畫面下拉選單所有尺寸與同尺寸不同樣板，全部廣告相同，故不在迴圈內重複執行
	 * @return
	 * @throws Exception
	 */
	private String processProdSelectAdSize() throws Exception {
		List<String> xTypeList = new ArrayList<>();
		xTypeList.add("x04");
		xTypeList.add("x05");
		List<AdmTemplateProduct> admTemplateProductList = admTemplateProductDAO.getTemplateProductByXType(xTypeList);

		JSONObject admTemplateJson = new JSONObject(new LinkedHashMap<String, String>());
		for (AdmTemplateProduct admTemplateProduct : admTemplateProductList) {
			String templateSize = admTemplateProduct.getTemplateProductWidth() + "_"
					+ admTemplateProduct.getTemplateProductHeight();
			String tproName = "";
			if (admTemplateJson.has(templateSize)) {
				tproName = admTemplateJson.getString(templateSize);
				tproName = tproName + admTemplateProduct.getTemplateProductSeq() + ",";
			} else {
				tproName = admTemplateProduct.getTemplateProductSeq() + ",";
			}
			admTemplateJson.put(templateSize, tproName);
		}
		return admTemplateJson.toString();
	}

	/**
	 * 處理廣告明細
	 * @param advertiseReportVO
	 * @throws Exception 
	 */
	private void processAdDetail(AdvertiseReportVO advertiseReportVO) throws Exception {
		List<PfpAdDetail> pfpAdDetailList = pfpAdDetailDAO.getPfpAdDetailByAdSeq(advertiseReportVO.getAdSeq());

		String realUrl = null;
		String img = null;
		String title = null;
		String adStyle = null;
		String adSize = null;
		String html5Title = null;
		String html5Flag = "N";
		String videoSeconds = null;
		String showUrl = null;
		String videoUrl = null;
		String content = null;
		String salesPrice = "";
		String promotionalPrice = "";

		// 商品廣告參數內容
		String prodGroup = "";
		String logoText = "";
		String logoFontColor = "";
		String logoBgColor = "";
		String btnTxt = "";
		String btnFontColor = "";
		String btnBgColor = "";
		String disTxtType = "";
		String disFontColor = "";
		String disBgColor = "";
		String saleImg = "";
		String saleEndImg = "";
		String catalogSeq = "";
		String adName = "";
		String prodRadioLogoType = "";
		// 以下程式自行判斷參數
		// 根據有無行銷圖決定
		String logoType = "";
		
		for (PfpAdDetail pfpAdDetail : pfpAdDetailList) {
			if ("video_seconds".equals(pfpAdDetail.getAdDetailId())) {
				videoSeconds = pfpAdDetail.getAdDetailContent();
			}
			if ("video_url".equals(pfpAdDetail.getAdDetailId())) {
				videoUrl = pfpAdDetail.getAdDetailContent();
			}
			if ("video_size".equals(pfpAdDetail.getAdDetailId())) {
				adSize = pfpAdDetail.getAdDetailContent();
			}
			if ("real_url".equals(pfpAdDetail.getAdDetailId())) {
				realUrl = pfpAdDetail.getAdDetailContent();
			}
			if ("img".equals(pfpAdDetail.getAdDetailId())) {
				img = pfpAdDetail.getAdDetailContent();
			}
			if ("title".equals(pfpAdDetail.getAdDetailId())) {
				title = pfpAdDetail.getAdDetailContent();
			}
			if ("content".equals(pfpAdDetail.getAdDetailId())) {
				content = pfpAdDetail.getAdDetailContent();
			}
			if ("size".equals(pfpAdDetail.getAdDetailId())) {
				adSize = pfpAdDetail.getAdDetailContent();
			}
			if ("zip".equals(pfpAdDetail.getAdDetailId())) {
				html5Title = pfpAdDetail.getAdDetailContent();
			}
			if (StringUtils.isBlank(adStyle)) {
				adStyle = pfpAdDetail.getPfpAd().getAdStyle();
			}
			if (StringUtils.equals("c_x05_po_tad_0059", pfpAdDetail.getPfpAd().getAdAssignTadSeq())) {
				html5Flag = "Y";
			}
			if ("show_url".equals(pfpAdDetail.getAdDetailId())) {
				showUrl = pfpAdDetail.getAdDetailContent();
			}
			if ("sales_price".equals(pfpAdDetail.getAdDetailId())) {
				salesPrice = pfpAdDetail.getAdDetailContent();
			}
			if ("promotional_price".equals(pfpAdDetail.getAdDetailId())) {
				promotionalPrice = pfpAdDetail.getAdDetailContent();
			}

			// 商品廣告
			if ("prod_report_name".equals(pfpAdDetail.getAdDetailId())) {
				adName = pfpAdDetail.getAdDetailContent();
			}
			if ("prod_group".equals(pfpAdDetail.getAdDetailId())) {
				prodGroup = pfpAdDetail.getAdDetailContent();
			}
			if ("prod_ad_url".equals(pfpAdDetail.getAdDetailId())) {
				realUrl = pfpAdDetail.getAdDetailContent();
			}
			if ("logo_type".equals(pfpAdDetail.getAdDetailId())) {
				logoType = pfpAdDetail.getAdDetailContent();
			}
			if ("logo_txt".equals(pfpAdDetail.getAdDetailId())) {
				logoText = pfpAdDetail.getAdDetailContent();
			}
			if ("logo_font_color".equals(pfpAdDetail.getAdDetailId())) {
				logoFontColor = pfpAdDetail.getAdDetailContent();
			}
			if ("logo_bg_color".equals(pfpAdDetail.getAdDetailId())) {
				logoBgColor = pfpAdDetail.getAdDetailContent();
			}
			if ("buybtn_txt".equals(pfpAdDetail.getAdDetailId())) {
				btnTxt = pfpAdDetail.getAdDetailContent();
			}
			if ("buybtn_font_color".equals(pfpAdDetail.getAdDetailId())) {
				btnFontColor = pfpAdDetail.getAdDetailContent();
			}
			if ("buybtn_bg_color".equals(pfpAdDetail.getAdDetailId())) {
				btnBgColor = pfpAdDetail.getAdDetailContent();
			}
			if ("dis_txt_type".equals(pfpAdDetail.getAdDetailId())) {
				disTxtType = pfpAdDetail.getAdDetailContent();
			}
			if ("dis_font_color".equals(pfpAdDetail.getAdDetailId())) {
				disFontColor = pfpAdDetail.getAdDetailContent();
			}
			if ("dis_bg_color".equals(pfpAdDetail.getAdDetailId())) {
				disBgColor = pfpAdDetail.getAdDetailContent();
			}
			if ("sale_img_300x250".equals(pfpAdDetail.getAdDetailId())) {
				saleEndImg = pfpAdDetail.getAdDetailContent();
			}
			if ("logo_sale_img_300x55".equals(pfpAdDetail.getAdDetailId())) {
				saleImg = pfpAdDetail.getAdDetailContent();
			}
			if ("prod_list".equals(pfpAdDetail.getAdDetailId())) {
				catalogSeq = pfpAdDetail.getAdDetailContent();
			}
			if ("prod_radio_logo_type".equals(pfpAdDetail.getAdDetailId())) {
				prodRadioLogoType = pfpAdDetail.getAdDetailContent();
			}
		}
	    
		if ("IMG".equals(adStyle)) { // 圖像廣告
			// 取得圖片尺寸
			String imgWidth = "0";
			String imgHeight = "0";
			showUrl = realUrl;
			showUrl = showUrl.replaceAll("http://", "");
			showUrl = showUrl.replaceAll("https://", "");
			if (showUrl.lastIndexOf(".com/") != -1) {
				showUrl = showUrl.substring(0, showUrl.lastIndexOf(".com/") + 4);
			}
			if (showUrl.lastIndexOf(".tw/") != -1) {
				showUrl = showUrl.substring(0, showUrl.lastIndexOf(".tw/") + 3);
			}
			
			if (StringUtils.equals("Y", html5Flag)) {
				String[] sizeArray = adSize.split("x");
				imgWidth = sizeArray[0].trim();
				imgHeight = sizeArray[1].trim();
			} else {
				try {
					File picture = new File("/home/webuser/akb/pfp/" + img.replace("\\", "/"));
					if (picture != null) {
						ImageInputStream stream = new FileImageInputStream(picture);
						Iterator<ImageReader> readers = ImageIO.getImageReaders(stream);
						if (readers.hasNext()) {
							ImageReader reader = readers.next();
							reader.setInput(stream, true);
							imgWidth = String.valueOf(reader.getWidth(0));
							imgHeight = String.valueOf(reader.getHeight(0));
						}
						stream.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			// 將前端用到的參數放在各if內，比較好找
			advertiseReportVO.setHtml5Flag(html5Flag);
			advertiseReportVO.setTitle(title);
			advertiseReportVO.setImg(img);
			advertiseReportVO.setImgWidth(imgWidth);
			advertiseReportVO.setImgHeight(imgHeight);
			advertiseReportVO.setRealUrl(realUrl);
			// 報表用
			advertiseReportVO.setAdName(title);
			advertiseReportVO.setContent("尺寸：" + imgWidth + " x " + imgHeight);
			advertiseReportVO.setShowUrl(showUrl);
		} else if ("VIDEO".equals(adStyle)) { // 影音廣告
			String[] size = adSize.split("_");
			String width = "0";
			String height = "0";
			if(size.length == 2){
				width = size[0];
				height = size[1];
			}
			String adPreviewVideoBgImg = img.equals("img/public/na.gif\" style=\"display:none") ? "" : img;
			videoSeconds = videoSeconds.length() == 2 ? videoSeconds : "0" + videoSeconds;
			
			advertiseReportVO.setVideoName(content);
			advertiseReportVO.setVideoWidth(width);
			advertiseReportVO.setVideoHeight(height);
			advertiseReportVO.setVideoSec(videoSeconds);
			advertiseReportVO.setRealUrl(realUrl);
			advertiseReportVO.setVideoUrl(videoUrl);
			advertiseReportVO.setImg(adPreviewVideoBgImg);
			// 報表用
			advertiseReportVO.setAdName(content);
			advertiseReportVO.setShowUrl(videoUrl);
			advertiseReportVO.setContent("尺寸：" + width + " x " + height);
		} else if ("PROD".equals(adStyle)) { // 商品廣告
			// 預設不呈現底圖
			String adbgType = "noposter";
			String posterType = "noposter";
			if (StringUtils.isNotBlank(saleEndImg)) {
				adbgType = "hasposter";
				posterType = "hasposter";
			}
		    
			advertiseReportVO.setProdGroup(URLEncoder.encode(prodGroup, "UTF-8")); // 放入iframe參數值為catalogGroupId
			advertiseReportVO.setDisTxtType(URLEncoder.encode(disTxtType, "UTF-8")); // 放入iframe參數值為disTxtType
			advertiseReportVO.setDisBgColor(URLEncoder.encode(disBgColor, "UTF-8")); // 放入iframe參數值為disBgColor
			advertiseReportVO.setDisFontColor(URLEncoder.encode(disFontColor, "UTF-8")); // 放入iframe參數值為disFontColor
			advertiseReportVO.setBtnTxt(URLEncoder.encode(URLEncoder.encode(btnTxt, "UTF-8"), "UTF-8")); // 放入iframe參數值為btnTxt
			advertiseReportVO.setBtnFontColor(URLEncoder.encode(btnFontColor, "UTF-8")); // 放入iframe參數值為btnFontColor
			advertiseReportVO.setBtnBgColor(URLEncoder.encode(btnBgColor, "UTF-8")); // 放入iframe參數值為btnBgColor
			advertiseReportVO.setLogoText(URLEncoder.encode(URLEncoder.encode(logoText, "UTF-8"), "UTF-8")); // 放入iframe參數值為logoText
			advertiseReportVO.setLogoBgColor(URLEncoder.encode(logoBgColor, "UTF-8")); // 放入iframe參數值為logoBgColor
			advertiseReportVO.setLogoFontColor(URLEncoder.encode(logoFontColor, "UTF-8")); // 放入iframe參數值為logoFontColor
			advertiseReportVO.setProdRadioLogoType(URLEncoder.encode(prodRadioLogoType, "UTF-8")); // 放入iframe參數值為prodLogoType
			advertiseReportVO.setAdbgType(URLEncoder.encode(adbgType, "UTF-8")); // 放入iframe參數值為adbgType
			
			// 根據setup決定是否滿版
			String imgProportiona = "";
			PfpCatalogSetup pfpCatalogSetup = pfpCatalogSetupService.findSetupByCatalogSeq(catalogSeq);
			if (pfpCatalogSetup != null) {
				imgProportiona = pfpCatalogSetup.getCatalogSetupValue();
			}
			advertiseReportVO.setImgProportiona(URLEncoder.encode(imgProportiona, "UTF-8")); // 放入iframe參數值為imgProportiona
			
			String userLogoPath = "";
			List<PfpCatalogLogo> pfpCatalogLogoList = pfpCatalogLogoService.findCatalogLogoByCustomerInfoId(advertiseReportVO.getCustomerInfoId());
			for (PfpCatalogLogo pfpCatalogLogo : pfpCatalogLogoList) {
				if (logoType.equals("type1") && pfpCatalogLogo.getCatalogLogoType().equals("1")) {
					userLogoPath = pfpCatalogLogo.getCatalogLogoUrl();
				}
				if (logoType.equals("type2") && pfpCatalogLogo.getCatalogLogoType().equals("0")) {
					userLogoPath = pfpCatalogLogo.getCatalogLogoUrl();
				}
			}
			advertiseReportVO.setUserLogoPath(URLEncoder.encode(userLogoPath, "UTF-8")); // 放入iframe參數值為userLogoPath
			
			if (StringUtils.isNotBlank(saleImg)) {
				logoType = "type3";
			}
			advertiseReportVO.setLogoType(URLEncoder.encode(logoType, "UTF-8")); // 放入iframe參數值為logoType
			advertiseReportVO.setPreviewTpro(URLEncoder.encode("c_x05_pad_tpro_0145", "UTF-8")); // 放入iframe參數值為previewTpro，預設樣板c_x05_pad_tpro_0145
			advertiseReportVO.setSaleImg(URLEncoder.encode(saleImg, "UTF-8")); // 放入iframe參數值為saleImg
			advertiseReportVO.setSaleEndImg(URLEncoder.encode(saleEndImg, "UTF-8")); // 放入iframe參數值為saleEndImg
			advertiseReportVO.setPosterType(posterType); // 放入iframe參數值為posterType
			advertiseReportVO.setRealUrlEncode(URLEncoder.encode(realUrl, "UTF-8")); // 放入iframe參數值為realUrl
			advertiseReportVO.setAdName(adName);
			advertiseReportVO.setRealUrl(realUrl);
			advertiseReportVO.setProdAdSizeWidth("300"); // 預設300x250
			advertiseReportVO.setProdAdSizeHeight("250");
		} else if ("TMG".equals(adStyle)) { // 圖文廣告
			advertiseReportVO.setImg(img);
			advertiseReportVO.setTitle(title);
			advertiseReportVO.setContent(content);
			advertiseReportVO.setRealUrl(realUrl);
			advertiseReportVO.setSalesPrice(salesPrice);
			advertiseReportVO.setPromotionalPrice(promotionalPrice);
			// 報表用
			advertiseReportVO.setAdName(title);
			advertiseReportVO.setShowUrl(showUrl);
		}
	    
		advertiseReportVO.setAdStyle(adStyle);
	}

	/**
	 * 總廣告成效-明細、廣告明細成效共用(加總)
	 */
	public List<AdvertiseReportVO> queryReportAdvertiseSumData(AdvertiseReportVO vo) {
		List<Map<String, Object>> advertiseListSum = adReportDAO.getAdvertiseListSum(vo);
		
		// 曝光數
		BigDecimal adPvSum = new BigDecimal(0);
		// 互動數
		BigDecimal adClkSum = new BigDecimal(0);
		// 無效點選次數
		BigDecimal adInvClkSum = new BigDecimal(0);
		// 費用
		BigDecimal adPriceSum = new BigDecimal(0);
		// 轉換次數
		BigDecimal convertCount = new BigDecimal(0);
		// 總轉換價值
		BigDecimal convertPriceCount = new BigDecimal(0);
		
		List<AdvertiseReportVO> advertiseVOListSum = new ArrayList<>();
		// 加總
		for (Map<String, Object> dataMap : advertiseListSum) {
			adPvSum = adPvSum.add((BigDecimal) dataMap.get("ad_pv_sum"));
			adClkSum = adClkSum.add((BigDecimal) dataMap.get("ad_clk_sum"));
			adInvClkSum = adInvClkSum.add((BigDecimal) dataMap.get("ad_invalid_clk_sum"));
			adPriceSum = adPriceSum.add(BigDecimal.valueOf((Double) dataMap.get("ad_price_sum")));
			convertCount = convertCount.add((BigDecimal) dataMap.get("convert_count"));
			convertPriceCount = convertPriceCount.add((BigDecimal) dataMap.get("convert_price_count"));
		}
		
		AdvertiseReportVO advertiseReportVO = new AdvertiseReportVO();
		// 曝光數
		advertiseReportVO.setAdPvSum(adPvSum);
		
		// 互動數
		advertiseReportVO.setAdClkSum(adClkSum);
		
		// 互動率 = 總互動數 / 總曝光數 * 100
		advertiseReportVO.setCtr(CommonUtils.getInstance().getCalculateDivisionValue(adClkSum, adPvSum, 100));
		
		// 無效點選次數(總廣告成效用)
		advertiseReportVO.setAdInvClkSum(adInvClkSum);
		
		// 費用
		advertiseReportVO.setAdPriceSum(adPriceSum.doubleValue());
		
		// 單次互動費用 = 總費用 / 總互動次數
		advertiseReportVO.setAvgCost(CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, adClkSum));
		
		// 千次曝光費用 = 總費用 / 曝光數 * 1000
		Double kiloCost = CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, adPvSum, 1000);
		BigDecimal bg = BigDecimal.valueOf(kiloCost); // 算完千次曝光費用後，再處理小數至第二位，然後四捨五入
		advertiseReportVO.setKiloCost(bg.setScale(2, RoundingMode.HALF_UP).doubleValue());
		
		// 轉換次數
		advertiseReportVO.setConvertCount(convertCount);
		
		// 轉換率 = 轉換次數 / 互動數 * 100
		advertiseReportVO.setConvertCTR(CommonUtils.getInstance().getCalculateDivisionValue(convertCount, adClkSum, 100));
		
		// 總轉換價值
		advertiseReportVO.setConvertPriceCount(convertPriceCount);
		
		// 平均轉換成本 = 費用 / 轉換次數
		advertiseReportVO.setConvertCost(CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, convertCount));
		
		// 廣告投資報酬率 = (總轉換價值 / 費用) * 100
		advertiseReportVO.setConvertInvestmentCost(CommonUtils.getInstance().getCalculateDivisionValue(convertPriceCount, adPriceSum, 100));
		
		// 總計幾筆
		advertiseReportVO.setRowCount(advertiseListSum.size());
		vo.setRowCount(advertiseListSum.size()); // 計算底下頁碼用
		
		advertiseVOListSum.add(advertiseReportVO);
		
		return advertiseVOListSum;
	}

	/**
	 * 回傳List舊寫法
	 * 廣告明細成效(圖表)
	 * return List
	 */
	public List<AdvertiseReportVO> queryReportAdvertiseChartData(AdvertiseReportVO vo) {
		List<Map<String, Object>> advertiseList = adReportDAO.getAdvertiseListChart(vo);
		
		List<AdvertiseReportVO> advertiseVOList = new ArrayList<>();
		for (Map<String, Object> dataMap : advertiseList) {
			AdvertiseReportVO advertiseReportVO = new AdvertiseReportVO();
			
			// 日期
			advertiseReportVO.setReportDate((Date) dataMap.get("ad_pvclk_date"));

			// 曝光數
			BigDecimal adPvSum = (BigDecimal) dataMap.get("ad_pv_sum");
			advertiseReportVO.setAdPvSum(adPvSum);
			
			// 互動數
			BigDecimal adClkSum = (BigDecimal) dataMap.get("ad_clk_sum");
			advertiseReportVO.setAdClkSum(adClkSum);
			
			// 互動率 = 總互動數 / 總曝光數 * 100
			advertiseReportVO.setCtr(CommonUtils.getInstance().getCalculateDivisionValue(adClkSum, adPvSum, 100));
			
			// 費用
			BigDecimal adPriceSum = BigDecimal.valueOf((Double) dataMap.get("ad_price_sum"));
			advertiseReportVO.setAdPriceSum(adPriceSum.doubleValue());
						
			// 單次互動費用 = 總費用 / 總互動次數
			advertiseReportVO.setAvgCost(CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, adClkSum));
			
			// 千次曝光費用 = 總費用 / 曝光數 * 1000
			Double kiloCost = CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, adPvSum, 1000);
			BigDecimal bigDecimal = BigDecimal.valueOf(kiloCost); // 算完千次曝光費用後，再處理小數至第二位，然後四捨五入
			advertiseReportVO.setKiloCost(bigDecimal.setScale(2, RoundingMode.HALF_UP).doubleValue());
			
			// 轉換次數
			BigDecimal convertCount = (BigDecimal) dataMap.get("convert_count");
			advertiseReportVO.setConvertCount(convertCount);
			
			// 轉換率 = 轉換次數 / 互動數 * 100
			advertiseReportVO.setConvertCTR(CommonUtils.getInstance().getCalculateDivisionValue(convertCount, adClkSum, 100));
			
			// 總轉換價值
			BigDecimal convertPriceCount = (BigDecimal) dataMap.get("convert_price_count");
			advertiseReportVO.setConvertPriceCount(convertPriceCount);
			
			// 平均轉換成本 = 費用 / 轉換次數
			advertiseReportVO.setConvertCost(CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, convertCount));
			
			// 廣告投資報酬率 = (總轉換價值 / 費用) * 100
			advertiseReportVO.setConvertInvestmentCost(CommonUtils.getInstance().getCalculateDivisionValue(convertPriceCount, adPriceSum, 100));
			
			advertiseVOList.add(advertiseReportVO);
		}
		
		return advertiseVOList;
	}

	/**
	 * 回傳map新寫法
	 * 總廣告成效-明細、廣告明細成效共用(圖表)
	 * @param vo
	 * @return map
	 */
	public Map<Date, Float> queryReportAdvertiseChartDataMap(AdvertiseReportVO vo) {
		List<Map<String, Object>> advertiseList = adReportDAO.getAdvertiseListChart(vo);
		
		String charType = vo.getCharType();
		Map<Date, Float> flashDataMap = new HashMap<>();
		for (Map<String, Object> dataMap : advertiseList) {
			
			// 日期
			Date reportDate = (Date) dataMap.get("ad_pvclk_date");
			// 曝光數
			BigDecimal adPvSum = (BigDecimal) dataMap.get("ad_pv_sum");
			// 互動數
			BigDecimal adClkSum = (BigDecimal) dataMap.get("ad_clk_sum");
			// 無效點選次數(總廣告成效用)
			BigDecimal adInvClkSum = (BigDecimal) dataMap.get("ad_invalid_clk_sum");
			// 費用
			BigDecimal adPriceSum = BigDecimal.valueOf((Double) dataMap.get("ad_price_sum"));
			// 轉換次數
			BigDecimal convertCount = (BigDecimal) dataMap.get("convert_count");
			// 總轉換價值
			BigDecimal convertPriceCount = (BigDecimal) dataMap.get("convert_price_count");
			
			if (charType.equals(EnumReport.REPORT_CHART_TYPE_PV.getTextValue())) {
				flashDataMap.put(reportDate, adPvSum.floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_CLICK.getTextValue())) {
				flashDataMap.put(reportDate, adClkSum.floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_CTR.getTextValue())) {
				// 互動率 = 總互動數 / 總曝光數 * 100
				flashDataMap.put(reportDate, CommonUtils.getInstance().getCalculateDivisionValue(adClkSum, adPvSum, 100).floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_INV_CLK.getTextValue())) {
				flashDataMap.put(reportDate, adInvClkSum.floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_AVGCOST.getTextValue())) {
				// 單次互動費用 = 總費用 / 總互動次數
				flashDataMap.put(reportDate, CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, adClkSum).floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_KILOCOST.getTextValue())) {
				// 千次曝光費用 = 總費用 / 曝光數 * 1000
				Double kiloCost = CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, adPvSum, 1000);
				BigDecimal bigDecimal = BigDecimal.valueOf(kiloCost); // 算完千次曝光費用後，再處理小數至第二位，然後四捨五入
				flashDataMap.put(reportDate, bigDecimal.setScale(2, RoundingMode.HALF_UP).floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_COST.getTextValue())) {
				flashDataMap.put(reportDate, adPriceSum.floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_CONVERT.getTextValue())) {
				flashDataMap.put(reportDate, convertCount.floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_CONVERT_CTR.getTextValue())) {
				// 轉換率 = 轉換次數 / 互動數 * 100
				flashDataMap.put(reportDate, CommonUtils.getInstance().getCalculateDivisionValue(convertCount, adClkSum, 100).floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_CONVERT_PRICE.getTextValue())) {
				flashDataMap.put(reportDate, convertPriceCount.floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_CONVERT_COST.getTextValue())) {
				// 平均轉換成本 = 費用 / 轉換次數
				flashDataMap.put(reportDate, CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, convertCount).floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_CONVERT_INVESTMENT.getTextValue())) {
				// 廣告投資報酬率 = (總轉換價值 / 費用) * 100
				flashDataMap.put(reportDate, CommonUtils.getInstance().getCalculateDivisionValue(convertPriceCount, adPriceSum, 100).floatValue());
			}
		}
		
		return flashDataMap;
	}
	
}