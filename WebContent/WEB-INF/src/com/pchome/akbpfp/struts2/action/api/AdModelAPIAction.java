package com.pchome.akbpfp.struts2.action.api;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.ad.EnumProdAdBtnText;
import com.pchome.rmi.api.IAPIProvider;
import com.pchome.soft.util.HttpUtil;

public class AdModelAPIAction extends BaseCookieAction{
	
	private IAPIProvider admAPI;
	
	private String tproNo;
	private String adNo;
	private InputStream returnAdHtml;			// 回傳廣告
	
	private String adPreviewVideoURL;
	private String adPreviewVideoBgImg="";
	private String realUrl = "";
	
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
	
	private String logoImg;
	
	private String adProdgroupId;
	
	/* 商品廣告用參數 END*/
	
	
	
	
	/**
	 * 吐Html廣告
	 */
	public String adModelAction() throws Exception{
		
	    //log.info(" tproNo = "+tproNo+"  , adNo = "+adNo);
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	    Date date = new Date();
	    String adHtml = admAPI.getAdContent(tproNo, adNo);
	    adHtml = adHtml.replaceAll("jpg", "jpg?time="+sdf.format(date));
	    log.info(adHtml);
	    returnAdHtml = new ByteArrayInputStream(adHtml.getBytes("UTF-8"));
	    return SUCCESS;
	}
	
	
	/**
	 * 吐影音廣告
	 */
	public String adModelVideoAction() throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	    Date date = new Date();
	    String adHtml = admAPI.getAdVideoContent(adPreviewVideoURL,adPreviewVideoBgImg,realUrl);
//	    log.info(adHtml);
	    returnAdHtml = new ByteArrayInputStream(adHtml.getBytes("UTF-8"));
	    return SUCCESS;
	}
	
	/**
	 * 吐商品廣告
	 */
	public String adModelProdAction() throws Exception{
		log.info(">>>>>PROD DATA API:"+"http://showstg.pchome.com.tw/pfp/prodGroupListAPI.html?groupId="+adProdgroupId+"&prodNum=10");
		String prodData = com.pchome.soft.depot.utils.HttpUtil.getInstance().getResult("http://showstg.pchome.com.tw/pfp/prodGroupListAPI.html?groupId="+adProdgroupId+"&prodNum=10", "UTF-8");
		
		log.info(">>>>>>DATA:"+prodData);
		
//		String prodData = com.pchome.soft.depot.utils.HttpUtil.getInstance().getResult("http://alex.pchome.com.tw:8080/akbpfp//prodGroupListAPI.html?groupId="+adProdgroupId+"&prodNum=10", "UTF-8");
		JSONObject json = new JSONObject(prodData);
		JSONArray prodArray = (JSONArray) json.get("prodGroupList");
		System.out.println(prodArray.length());
		System.out.println(prodArray.get(0));
		System.out.println(prodArray.get(1));
			
		int tadIndex = 0;
		InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(new File("/home/webuser/akb/adm/data/tpro/c_x05_pad_tpro_0099.def")), "UTF-8"); 
		BufferedReader fileReader = new BufferedReader(inputStreamReader);
//		fileReader = new FileReader(new File("/home/webuser/akb/adm/data/tad/c_x05_pad_tpro_0099.def"));
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String sCurrentLine;
		StringBuffer adHtml = new StringBuffer();
		boolean start = false;
		while ((sCurrentLine = bufferedReader.readLine()) != null) {
			if(!start){
				if("html:".equals(sCurrentLine)){
					start = true;
				}
				continue;
			}
			if(start){
				if(sCurrentLine.indexOf("<#dad_logo_type>") >= 0){
					sCurrentLine = sCurrentLine.replace("<#dad_logo_type>", "type1");
				}
				
				if(sCurrentLine.indexOf("<#c_x05_pad_tad_0111>") >= 0){
					InputStreamReader inputStreamReaderTad = new InputStreamReader(new FileInputStream(new File("/home/webuser/akb/adm/data/tad/c_x05_pad_tad_0111.def")), "UTF-8");
					BufferedReader fileReaderTad = new BufferedReader(inputStreamReaderTad);
					BufferedReader bufferedReaderTad = new BufferedReader(fileReaderTad);
					String sCurrentLineTad;
					StringBuffer tad = new StringBuffer();
					boolean tadStart = false;
					while ((sCurrentLineTad = bufferedReaderTad.readLine()) != null) {
						if(sCurrentLineTad.indexOf("<!--product -->") >=0){
							tadStart = true;
							continue;
						}
						if(tadStart){
							JSONObject prodDataInfo = (JSONObject) prodArray.get(tadIndex);
							if(sCurrentLineTad.indexOf("<#pad_ec_prod_img>") >= 0){
								String img = "http://showstg.pchome.com.tw/pfp/";
								img += prodDataInfo.getString("ec_img");
								sCurrentLineTad = sCurrentLineTad.replace("<#pad_ec_prod_img>", img);
							}
							
							if(sCurrentLineTad.indexOf("<#pad_ec_prod_price_dis>") >= 0){
								String ecProdPriceDis = prodDataInfo.getString("ec_discount_price");
								sCurrentLineTad = sCurrentLineTad.replace("<#pad_ec_prod_price_dis>", ecProdPriceDis);
							}
							if(sCurrentLineTad.indexOf("<#pad_ec_prod_price>") >= 0){
								String ecProdPrice = prodDataInfo.getString("ec_price");
								sCurrentLineTad = sCurrentLineTad.replace("<#pad_ec_prod_price>", ecProdPrice);
							}
							if(sCurrentLineTad.indexOf("<#pad_ec_prod_price>") >= 0){
								String ecProdPrice = prodDataInfo.getString("ec_price");
								sCurrentLineTad = sCurrentLineTad.replace("<#pad_ec_prod_price>", ecProdPrice);
							}
							if(sCurrentLineTad.indexOf("<#pad_ec_prod_name>") >= 0){
								String ecProdName = prodDataInfo.getString("ec_name");
								sCurrentLineTad = sCurrentLineTad.replace("<#pad_ec_prod_name>", ecProdName);
							}
							
							if(sCurrentLineTad.indexOf("#dad_buybtn_txt") >= 0){
								for (EnumProdAdBtnText enumProdAdBtnText : EnumProdAdBtnText.values()) {
									if(btnTxt.equals(enumProdAdBtnText.getBtnType())){
										sCurrentLineTad = sCurrentLineTad.replace("<#dad_buybtn_txt>", enumProdAdBtnText.getBtnText());
										break;
									}
								}
							}
							if(sCurrentLineTad.indexOf("<#pad_ec_prod_url>") >= 0){
								String ecUrl = prodDataInfo.getString("ec_url");
								sCurrentLineTad = sCurrentLineTad.replace("<#pad_ec_prod_url>", ecUrl);
							}
							
							
							tad.append(sCurrentLineTad);
						}
					}
					sCurrentLine = sCurrentLine.replace("<#c_x05_pad_tad_0111>", tad.toString());
					tadIndex = tadIndex + 1;
				}
				
				
				if(sCurrentLine.indexOf("<#dad_logo_sale_img_300x55>") >= 0){
					sCurrentLine = sCurrentLine.replace("<#dad_logo_sale_img_300x55>", "https://scontent.ftpe8-2.fna.fbcdn.net/v/t1.0-1/p160x160/25446438_1796465407031788_5980907003955603333_n.jpg?_nc_cat=110&oh=c23ff86f03d09665cd8d50bda91d3d1b&oe=5C353FE2");
				}
				if(sCurrentLine.indexOf("<#dad_logo_img_url>") >= 0){
					sCurrentLine = sCurrentLine.replace("<#dad_logo_img_url>", "https://scontent.ftpe8-2.fna.fbcdn.net/v/t1.0-1/p160x160/25446438_1796465407031788_5980907003955603333_n.jpg?_nc_cat=110&oh=c23ff86f03d09665cd8d50bda91d3d1b&oe=5C353FE2");
				}
				if(sCurrentLine.indexOf("<#dad_logo_txt>") >= 0){
					sCurrentLine = sCurrentLine.replace("<#dad_logo_txt>", "");
				}
				
				adHtml.append(sCurrentLine).append("\n");
			}
			
		}
		
//		System.out.println(adHtml.toString());
		
		returnAdHtml = new ByteArrayInputStream(adHtml.toString().getBytes("UTF-8"));
		return SUCCESS;
	}
	
	
	public void setAdmAPI(IAPIProvider admAPI) {
		this.admAPI = admAPI;
	}

	public InputStream getReturnAdHtml() {
		return returnAdHtml;
	}

	public void setTproNo(String tproNo) {
		this.tproNo = tproNo;
	}

	public void setAdNo(String adNo) {
		this.adNo = adNo;
	}

	public void setAdPreviewVideoURL(String adPreviewVideoURL) {
		this.adPreviewVideoURL = adPreviewVideoURL;
	}


	public void setAdPreviewVideoBgImg(String adPreviewVideoBgImg) {
		this.adPreviewVideoBgImg = adPreviewVideoBgImg;
	}


	public void setRealUrl(String realUrl) {
		this.realUrl = realUrl;
	}


	public void setAdName(String adName) {
		this.adName = adName;
	}


	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}


	public void setCatalogGroupId(String catalogGroupId) {
		this.catalogGroupId = catalogGroupId;
	}


	public void setLogoType(String logoType) {
		this.logoType = logoType;
	}


	public void setLogoText(String logoText) {
		this.logoText = logoText;
	}


	public void setLogoBgColor(String logoBgColor) {
		this.logoBgColor = logoBgColor;
	}


	public void setLogoFontColor(String logoFontColor) {
		this.logoFontColor = logoFontColor;
	}


	public void setBtnTxt(String btnTxt) {
		this.btnTxt = btnTxt;
	}


	public void setBtnFontColor(String btnFontColor) {
		this.btnFontColor = btnFontColor;
	}


	public void setBtnBgColor(String btnBgColor) {
		this.btnBgColor = btnBgColor;
	}


	public void setDisTxtType(String disTxtType) {
		this.disTxtType = disTxtType;
	}


	public void setDisBgColor(String disBgColor) {
		this.disBgColor = disBgColor;
	}


	public void setDisFontColor(String disFontColor) {
		this.disFontColor = disFontColor;
	}


	public String getLogoImg() {
		return logoImg;
	}


	public void setLogoImg(String logoImg) {
		this.logoImg = logoImg;
	}


	public String getAdProdgroupId() {
		return adProdgroupId;
	}


	public void setAdProdgroupId(String adProdgroupId) {
		this.adProdgroupId = adProdgroupId;
	}

}
