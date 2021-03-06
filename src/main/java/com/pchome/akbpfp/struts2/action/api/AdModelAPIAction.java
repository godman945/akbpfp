package com.pchome.akbpfp.struts2.action.api;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.rmi.api.IAPIProvider;

public class AdModelAPIAction extends BaseCookieAction{
	private static final long serialVersionUID = 1L;

	private IAPIProvider admAPI;
	
	private String tproNo;
	private String adNo;
	private InputStream returnAdHtml;			// 回傳廣告
	private String returnAdHtmlStr;	
	private String adPreviewVideoURL;
	private String adPreviewVideoBgImg="";
	private String realUrl = "";
	
	/* 商品廣告用參數 START*/
	//行銷結尾圖
	private String uploadLog ="";
	//logo圖
	private String uploadLogoLog="";
	//廣告名稱
	private String adName="";
	//商品目錄ID
	private String catalogId="";
	//商品群組ID
	private String catalogGroupId="";
	//logo標題文字
	private String logoText="";
	//logo背景顏色
	private String logoBgColor="";
	//logo文字顏色
	private String logoFontColor="";
	//按鈕文字
	private String btnTxt="";
	//按鈕文字顏色
	private String btnFontColor="";
	//按鈕背景顏色
	private String btnBgColor="";
	//標籤文字
	private String disTxtType="";
	//標籤背景顏色
	private String disBgColor="";
	//標籤文字顏色
	private String disFontColor="";
	
	private String logoImg="";
	
	private String adProdgroupId="";
	
	private String logoType="";
	//logo類型
	private String prodLogoType;
	
	private String imgProportiona;
	
	private String userLogoPath;
	
	private String previewTpro;
	
	private String saleImg;
	
	private String saleEndImg;
	
	private String posterType;
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
//	    log.info(adHtml);
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
		JSONObject pfpProdAdPreviewJson = new JSONObject();
		pfpProdAdPreviewJson.put("adName",adName);
		pfpProdAdPreviewJson.put("catalogId", catalogId);
		pfpProdAdPreviewJson.put("catalogGroupId", catalogGroupId);
		pfpProdAdPreviewJson.put("logoBgColor",logoBgColor);
		pfpProdAdPreviewJson.put("logoFontColor", logoFontColor);
		pfpProdAdPreviewJson.put("logoText",URLDecoder.decode(logoText,"UTF-8"));
		pfpProdAdPreviewJson.put("btnBgColor",btnBgColor);
		pfpProdAdPreviewJson.put("btnFontColor", btnFontColor);
		pfpProdAdPreviewJson.put("btnTxt",URLDecoder.decode(btnTxt, "UTF-8"));
		pfpProdAdPreviewJson.put("disBgColor",disBgColor);
		pfpProdAdPreviewJson.put("disFontColor",disFontColor);
		pfpProdAdPreviewJson.put("disTxtType", disTxtType);
		pfpProdAdPreviewJson.put("prodLogoType", prodLogoType);
		pfpProdAdPreviewJson.put("imgProportiona", imgProportiona);
		pfpProdAdPreviewJson.put("userLogoPath", userLogoPath);
		pfpProdAdPreviewJson.put("realUrl", realUrl);
		pfpProdAdPreviewJson.put("previewTpro", previewTpro);
		pfpProdAdPreviewJson.put("saleImg", saleImg);
		pfpProdAdPreviewJson.put("saleEndImg", saleEndImg);
		pfpProdAdPreviewJson.put("posterType", posterType);
		String adHtml = admAPI.adProdContent(pfpProdAdPreviewJson.toString());
		
		
		
		
		
		
//		log.info("adHtml:"+adHtml);
//		returnAdHtml = new ByteArrayInputStream(adHtml.toString().getBytes("UTF-8"));
//		
//		System.out.println(adHtml);
//		InputStream stream = new ByteArrayInputStream(adHtml.getBytes(StandardCharsets.UTF_8));
		
		
//		adHtml ="alex";
		returnAdHtml = new ByteArrayInputStream(adHtml.toString().getBytes("UTF-8"));
		
//		returnAdHtmlStr = adHtml.toString();
//		returnAdHtml = new ByteArrayInputStream(adHtml.toString().getBytes());
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
	
	public String getLogoType() {
		return logoType;
	}

	public void setLogoType(String logoType) {
		this.logoType = logoType;
	}

	public void setProdLogoType(String prodLogoType) {
		this.prodLogoType = prodLogoType;
	}

	public String getImgProportiona() {
		return imgProportiona;
	}

	public void setImgProportiona(String imgProportiona) {
		this.imgProportiona = imgProportiona;
	}

	public String getUserLogoPath() {
		return userLogoPath;
	}

	public void setUserLogoPath(String userLogoPath) {
		this.userLogoPath = userLogoPath;
	}

	public String getPreviewTpro() {
		return previewTpro;
	}

	public void setPreviewTpro(String previewTpro) {
		this.previewTpro = previewTpro;
	}

	public String getSaleImg() {
		return saleImg;
	}

	public void setSaleImg(String saleImg) {
		this.saleImg = saleImg;
	}

	public String getSaleEndImg() {
		return saleEndImg;
	}

	public void setSaleEndImg(String saleEndImg) {
		this.saleEndImg = saleEndImg;
	}


	public String getReturnAdHtmlStr() {
		return returnAdHtmlStr;
	}


	public void setReturnAdHtmlStr(String returnAdHtmlStr) {
		this.returnAdHtmlStr = returnAdHtmlStr;
	}


	public String getPosterType() {
		return posterType;
	}


	public void setPosterType(String posterType) {
		this.posterType = posterType;
	}

	
	
	
//	public static void main(String[] args) throws Exception{
//		ApplicationContext context = new FileSystemXmlApplicationContext(TestConfig.path);
//		AdModelAPIAction adModelAPIAction = (AdModelAPIAction) context.getBean("AdModelAPIAction");
//		adModelAPIAction.adModelProdAction();
//	}
}
