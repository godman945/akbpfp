package com.pchome.akbpfp.db.service.report;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.lang3.StringUtils;

import com.pchome.akbpfp.db.dao.ad.PfpAdDetailDAO;
import com.pchome.akbpfp.db.dao.report.AdReportVO;
import com.pchome.akbpfp.db.dao.report.IAdReportDAO;
import com.pchome.akbpfp.db.pojo.PfpAdDetail;
import com.pchome.akbpfp.db.pojo.PfpCatalogLogo;
import com.pchome.akbpfp.db.pojo.PfpCatalogSetup;
import com.pchome.akbpfp.db.service.catalog.prod.IPfpCatalogLogoService;
import com.pchome.akbpfp.db.service.catalog.prod.IPfpCatalogSetupService;
import com.pchome.enumerate.report.EnumReport;

public class AdReportService implements IAdReportService {

	private IAdReportDAO adReportDAO;
	private PfpAdDetailDAO pfpAdDetailDAO;
	private IPfpCatalogSetupService pfpCatalogSetupService;
	private IPfpCatalogLogoService pfpCatalogLogoService;
	private String photoClonePath;
	
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
			for (int i=0; i< dataList.size(); i++) {
				AdReportVO adReportVO = dataList.get(i);
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
			    
			    
			    //以下程式自行判斷參數
			    //預設不呈現底圖
			    String adbgType = "noposter";
			    //根據setup決定是否滿版
			    String imgProportiona = "";
			    //根據有無行銷圖決定
			    String logoType = "";
			    
			    String userLogoPath = "";
			   
			    String previewTpro = "c_x04_pad_tpro_0100";
			   
			    		
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
                    if(StringUtils.equals("c_x05_po_tad_0059", pfpAdDetail.getPfpAd().getAdAssignTadSeq())){
                    	html5Flag = "Y";
                    }
                    if("show_url".equals(pfpAdDetail.getAdDetailId())){
                    	showUrl = pfpAdDetail.getAdDetailContent();
                    }
                    
                    
                    //商品廣告
                    if("prod_report_name".equals(pfpAdDetail.getAdDetailId())){
                    	pfpAdDetail.getAdDetailContent();
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
					
					htmlCode = "<iframe height='250' width='300' class='akb_iframe' scrolling='no' frameborder='0' marginwidth='0' marginheight='0' vspace='0' hspace='0' id='pchome8044_ad_frame1' allowtransparency='true' allowfullscreen='true'"
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
					+"&prodLogoType="
					+"&adbgType="+URLEncoder.encode(adbgType,"UTF-8")
					+"&imgProportiona="+URLEncoder.encode(imgProportiona,"UTF-8")
					+"&userLogoPath="+URLEncoder.encode(userLogoPath,"UTF-8")
					+"&previewTpro="+URLEncoder.encode(previewTpro,"UTF-8")
					+"&saleImg="+URLEncoder.encode(saleImg,"UTF-8")
					+"&saleEndImg="+URLEncoder.encode(saleEndImg,"UTF-8")
					+"'></iframe>";
					System.out.println(htmlCode);
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
}
