package com.pchome.akbpfp.db.service.report;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.pchome.akbpfp.db.dao.ad.PfpAdDetailDAO;
import com.pchome.akbpfp.db.dao.report.AdReportVO;
import com.pchome.akbpfp.db.dao.report.IAdReportDAO;
import com.pchome.akbpfp.db.pojo.PfpAdDetail;
import com.pchome.akbpfp.db.service.ad.IPfpAdGroupService;
import com.pchome.enumerate.report.EnumReport;

public class AdReportService implements IAdReportService {

	private IAdReportDAO adReportDAO;
	private PfpAdDetailDAO pfpAdDetailDAO;

    public void setAdReportDAO(IAdReportDAO adReportDAO) {
		this.adReportDAO = adReportDAO;
	}

	public void setPfpAdDetailDAO(PfpAdDetailDAO pfpAdDetailDAO) {
		this.pfpAdDetailDAO = pfpAdDetailDAO;
	}

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
		    String videoUrl = null;
			for (int i=0; i<dataList.size(); i++) {
				AdReportVO adReportVO = dataList.get(i);
				pfpAdDetailList = pfpAdDetailDAO.getPfpAdDetailByAdSeq(adReportVO.getAdSeq());
	            realUrl = null;
	            img = null;
	            title = null;
	            adStyle = null;
	            adSize = null;
			    html5Title = null;
			    html5Flag = "N";
			    videoSeconds = null;
			    videoUrl = null;
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
				}

				String htmlCode = "";
				if ("IMG".equals(adStyle)) {
					//取得圖片尺寸
					String imgWidth = "0";
					String imgHeight = "0";
					String showUrl = realUrl;
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
					} else {
						File picture = null;
						FileInputStream is = null;
						BufferedImage sourceImg = null;
						try{
							picture = new File("/home/webuser/akb/pfp/" +  img.replace("\\", "/"));
							if(picture != null){
								is = new FileInputStream(picture);
								sourceImg = javax.imageio.ImageIO.read(is);
								imgWidth = Integer.toString(sourceImg.getWidth());
								imgHeight = Integer.toString(sourceImg.getHeight());	
							}
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}catch (IOException e) {
							// TODO Auto-generated catch block
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
						if(Integer.parseInt(size[1]) == 480){
							style = "margin-top:35%;";
						}else if(Integer.parseInt(size[1]) == 600){
							style = "margin-top:49.1%;";
						}else if(Integer.parseInt(size[0]) >= 900){
							style = "";
						}else if(Integer.parseInt(size[0]) == 640){
							style = "margin-top:11%;";
						}else{
							style = "margin-top:17%;";
						}
					}
					videoSeconds = videoSeconds.length() ==2 ?videoSeconds : "0"+videoSeconds;
					htmlCode = "<div style=\"display:flex;\"><div> ";
					htmlCode = htmlCode + "<iframe class=\"akb_iframe\" scrolling=\"no\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" vspace=\"0\" hspace=\"0\" id=\"pchome8044_ad_frame1\" width=\""+width+"\" height=\""+height+"\" allowtransparency=\"true\" allowfullscreen=\"true\" src=\"adVideoModel.html?adPreviewVideoURL="+adPreviewVideoURL+"&adPreviewVideoBgImg=http://showstg.pchome.com.tw/pfp/"+adPreviewVideoBgImg+"&realUrl="+realUrl+"\"></iframe>";
					htmlCode = htmlCode + "</div><div style=\" text-align: left; line-height: 20px; padding: 10px;"+style+"\">";
					htmlCode = htmlCode + adReportVO.getAdActionName()+"<br>";
					htmlCode = htmlCode + "尺寸 "+width+" x "+height+"<br>";
					htmlCode = htmlCode + "時間 00:"+videoSeconds+"<br>";
					htmlCode = htmlCode + "<a href=\""+realUrl+"\" target=\"_blank\" >"+realUrl;
					htmlCode = htmlCode+"</div></div>";
					adReportVO.setContent("尺寸:"+width+"x"+height);
					adReportVO.setAdVideoSec("00:"+videoSeconds);
					adReportVO.setAdVideoUrl(adPreviewVideoURL);
				}else {
					htmlCode =  "<span><iframe height=\"120\" width=\"350\" src=\"adModel.html?adNo=" + adReportVO.getAdSeq() + "&tproNo=tpro_201406300001\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" frameborder=\"0\" align=\"ceneter\" class=\"akb_iframe\"></iframe></span>";
				}

				adReportVO.setAdPreview(htmlCode);

				String adSeqCode = adReportVO.getAdSeq();
				List<PfpAdDetail> adPropertiesList = pfpAdDetailDAO.getPfpAdDetails(null, adSeqCode, null, null);
				for (int k=0; k<adPropertiesList.size(); k++) {
					PfpAdDetail adDetail = adPropertiesList.get(k);
					String adPropertiesName = adDetail.getAdDetailId();
					String adPropertiesValue = adDetail.getAdDetailContent();
					if (adPropertiesName.equals("title")) {
						adReportVO.setTitle(adPropertiesValue);
					} else if (adPropertiesName.equals("content")) {
						adReportVO.setContent(adPropertiesValue);
					} else if (adPropertiesName.equals("real_url")) {
						adReportVO.setRealUrl(adPropertiesValue);
						adReportVO.setAdGroupName(adDetail.getPfpAd().getPfpAdGroup().getAdGroupName());
						adReportVO.setAdGroupStatus(adDetail.getPfpAd().getPfpAdGroup().getAdGroupStatus());
					} else if (adPropertiesName.equals("show_url")) {
						adReportVO.setShowUrl(adPropertiesValue);
					}
				}
			}
		}

		return dataList;
	}
	
}
