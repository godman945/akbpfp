package com.pchome.akbpfp.struts2.action.factory.ad;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.pchome.akbpfp.db.pojo.AdmTemplateProduct;
import com.pchome.akbpfp.db.pojo.PfpAdDetail;
import com.pchome.akbpfp.db.pojo.PfpAdGroup;
import com.pchome.akbpfp.db.pojo.PfpCatalog;
import com.pchome.akbpfp.db.pojo.PfpCatalogLogo;
import com.pchome.akbpfp.db.pojo.PfpCatalogLogoDetail;
import com.pchome.akbpfp.db.service.accesslog.AdmAccesslogService;
import com.pchome.akbpfp.db.service.catalog.IPfpCatalogService;
import com.pchome.akbpfp.db.service.catalog.prod.IPfpCatalogLogoService;
import com.pchome.akbpfp.db.service.catalog.prod.IPfpCatalogSetupService;
import com.pchome.akbpfp.db.service.catalog.prodGroup.IPfpCatalogGroupService;
import com.pchome.akbpfp.db.service.template.ITemplateProductService;
import com.pchome.akbpfp.godutil.CommonUtilModel;
import com.pchome.akbpfp.struts2.action.ad.AdAddAction;
import com.pchome.akbpfp.struts2.action.ad.AdEditAction;
import com.pchome.akbpfp.struts2.action.intfc.ad.IAd;
import com.pchome.enumerate.ad.EnumAdStyle;
import com.pchome.enumerate.ad.EnumProdAdBtnText;
import com.pchome.enumerate.ad.EnumProdAdDetail;
import com.pchome.enumerate.sequence.EnumSequenceTableName;
import com.pchome.rmi.accesslog.EnumAccesslogAction;

public class ProdAd implements IAd {

	protected Logger log = LogManager.getRootLogger();

	private IPfpCatalogService pfpCatalogService;
	private AdmAccesslogService admAccesslogService;
	private IPfpCatalogLogoService pfpCatalogLogoService;
	private IPfpCatalogSetupService pfpCatalogSetupService;
	private ITemplateProductService admTemplateProductService;
	private List<PfpCatalog> catalogList;
	private String templateStr;
	private String photoClonePath;
	private AdAddAction adAddAction;
	private AdEditAction adEditAction;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	private IPfpCatalogGroupService pfpCatalogGroupService;
	private CommonUtilModel commonUtilModel = new CommonUtilModel();
	private String photoPath;
	private String pfpCustomerInfoId = "";
	private String photoDBPath;
	
	public String AdAdAddInit(AdAddAction adAddAction) throws Exception {
		log.info(">>>>>> process ProdAd");
		catalogList = pfpCatalogService.getPfpCatalogByCustomerInfoId(adAddAction.getCustomer_info_id());
		//全部商品未審核不顯示
		Iterator<PfpCatalog> iterator = catalogList.iterator();
		while (iterator.hasNext()) {
			PfpCatalog pfpCatalog = iterator.next();
			int status = pfpCatalogService.checkCatalogProdStatus(pfpCatalog.getCatalogSeq());
			if(status == 0){
				iterator.remove();
			}
		}
		
		List<PfpCatalogLogo> pfpCatalogLogoList = pfpCatalogLogoService.findCatalogLogoByCustomerInfoId(adAddAction.getCustomer_info_id());
		//檢查logo是否審核通過
		if(pfpCatalogLogoList == null || pfpCatalogLogoList.size() < 2){
			return "logo";
		}else{
			boolean flag = false;
			for (PfpCatalogLogo pfpCatalogLogo : pfpCatalogLogoList) {
				if(!pfpCatalogLogo.getStatus().equals("1")){
					flag = true;
					break;
				}
			}
			if(flag){
				return "logo";
			}
		}
		
		List<String> xTypeList = new ArrayList<String>();
		xTypeList.add("x04");
		xTypeList.add("x05");
		List<AdmTemplateProduct> admTemplateProductList = admTemplateProductService.getTemplateProductByXType(xTypeList);
		JSONObject admTemplateJson = new JSONObject(new LinkedHashMap<String,String>());
		for (AdmTemplateProduct admTemplateProduct : admTemplateProductList) {
			String templateSize = admTemplateProduct.getTemplateProductWidth()+"_"+admTemplateProduct.getTemplateProductHeight();
			String tproName = "";
			if(admTemplateJson.has(templateSize)){
				tproName = admTemplateJson.getString(templateSize);
				tproName = tproName+admTemplateProduct.getTemplateProductSeq()+",";
			}else{
				tproName =  admTemplateProduct.getTemplateProductSeq()+",";
			}
			admTemplateJson.put(templateSize, tproName);
		}
		templateStr = admTemplateJson.toString();
		
		
		JSONObject json = new JSONObject();
		if(pfpCatalogLogoList != null){
			for (PfpCatalogLogo pfpCatalogLogo : pfpCatalogLogoList) {
				JSONObject catalogLogoUrlJson = new JSONObject();
				catalogLogoUrlJson.put("logoPath", pfpCatalogLogo.getCatalogLogoUrl());
				catalogLogoUrlJson.put("logoStatus", pfpCatalogLogo.getStatus());
				List<String> colorList = new ArrayList<String>();
				Set<PfpCatalogLogoDetail> pfpCatalogLogoDetailSet = pfpCatalogLogo.getPfpCatalogLogoDetails();
				for (PfpCatalogLogoDetail pfpCatalogLogoDetail : pfpCatalogLogoDetailSet) {
					String color = pfpCatalogLogoDetail.getCatalogLogoHexColor();
					colorList.add(color);
				}
				catalogLogoUrlJson.put("color", colorList);
				if(pfpCatalogLogo.getCatalogLogoType().equals("0")){
					json.put("square", catalogLogoUrlJson);
				}
				if(pfpCatalogLogo.getCatalogLogoType().equals("1")){
					json.put("rectangle", catalogLogoUrlJson);
				}
			}
		}
		adAddAction.setUserLogoPath(json.toString());
		adAddAction.getRequest().setAttribute("catalogList", catalogList);
		adAddAction.getRequest().setAttribute("templateStr", templateStr);
		return "adProdAdd";
	}

	public String doAdAdAdd(AdAddAction adAddAction) throws Exception {
		log.info(">>>>>> process do add prod ad");
		this.adAddAction = adAddAction;
		String adSeq = adAddAction.getSequenceService().getId(EnumSequenceTableName.PFP_AD, "_");
		pfpCustomerInfoId = adAddAction.getCustomer_info_id();
		PfpAdGroup pfpAdGroup = adAddAction.getPfpAdGroup();
		adAddAction.setAdSeq(adSeq);
		adAddAction.setTemplateProductSeq(EnumAdStyle.TMG.getTproSeq());
		adAddAction.setAdStyle("PROD");
		adAddAction.setAdClass("1");
		adAddAction.addAd(pfpAdGroup,null);
		
		for (EnumProdAdDetail enumProdAdDetail : EnumProdAdDetail.values()) {
			switch(enumProdAdDetail) {
	        case PROD_REPORT_NAME:
	        	adAddAction.saveAdDetail(adAddAction.getAdName(),enumProdAdDetail.getAdDetailId(),enumProdAdDetail.getAdPoolSeq(),enumProdAdDetail.getDefineAdSeq());
	        	break;
		 	case PROD_LIST:
		 		adAddAction.saveAdDetail(adAddAction.getCatalogId(),enumProdAdDetail.getAdDetailId(),enumProdAdDetail.getAdPoolSeq(),enumProdAdDetail.getDefineAdSeq());
		 		break;
			case PROD_GROUP:
				adAddAction.saveAdDetail(adAddAction.getCatalogGroupId(),enumProdAdDetail.getAdDetailId(),enumProdAdDetail.getAdPoolSeq(),enumProdAdDetail.getDefineAdSeq());
		 		break;
			case PROD_AD_URL:
				//商品連結網址
				adAddAction.saveAdDetail(adAddAction.getAdLinkURL(),enumProdAdDetail.getAdDetailId(),enumProdAdDetail.getAdPoolSeq(),enumProdAdDetail.getDefineAdSeq());
		 		break;
			case LOGO_TYPE:
				adAddAction.saveAdDetail(adAddAction.getLogoType(),enumProdAdDetail.getAdDetailId(),enumProdAdDetail.getAdPoolSeq(),enumProdAdDetail.getDefineAdSeq());
		 		break;
			case LOGO_TXT:
				//logo標題文字
				adAddAction.saveAdDetail(adAddAction.getLogoText(),enumProdAdDetail.getAdDetailId(),enumProdAdDetail.getAdPoolSeq(),enumProdAdDetail.getDefineAdSeq());
		 		break;
			case LOGO_FONT_COLOR:
				//logo標題文字顏色
				adAddAction.saveAdDetail(adAddAction.getLogoFontColor(),enumProdAdDetail.getAdDetailId(),enumProdAdDetail.getAdPoolSeq(),enumProdAdDetail.getDefineAdSeq());
		 		break;
			case LOGO_BG_COLOR:
				//logo背景顏色
				adAddAction.saveAdDetail(adAddAction.getLogoBgColor(),enumProdAdDetail.getAdDetailId(),enumProdAdDetail.getAdPoolSeq(),enumProdAdDetail.getDefineAdSeq());
		 		break;
			case BTN_TXT:
				//按鈕文字
				for (EnumProdAdBtnText enumProdAdBtnText : EnumProdAdBtnText.values()) {
					if(adAddAction.getBtnTxt().equals(enumProdAdBtnText.getBtnType())){
						adAddAction.saveAdDetail(enumProdAdBtnText.getBtnText(),enumProdAdDetail.getAdDetailId(),enumProdAdDetail.getAdPoolSeq(),enumProdAdDetail.getDefineAdSeq());
						break;
					}
				}
		 		break;
			case BTN_FONT_COLOR:
				//按鈕文字顏色
				adAddAction.saveAdDetail(adAddAction.getBtnFontColor(),enumProdAdDetail.getAdDetailId(),enumProdAdDetail.getAdPoolSeq(),enumProdAdDetail.getDefineAdSeq());
		 		break;
			case BTN_BG_COLOR:
				//按鈕背景顏色
				adAddAction.saveAdDetail(adAddAction.getBtnBgColor(),enumProdAdDetail.getAdDetailId(),enumProdAdDetail.getAdPoolSeq(),enumProdAdDetail.getDefineAdSeq());
				break;
			case DIS_TXT_TYPE:
				//標籤文字
				adAddAction.saveAdDetail(adAddAction.getDisTxtType(),enumProdAdDetail.getAdDetailId(),enumProdAdDetail.getAdPoolSeq(),enumProdAdDetail.getDefineAdSeq());
		 		break;
			case DIS_FONT_COLOR:
				//標籤文字顏色
				adAddAction.saveAdDetail(adAddAction.getDisFontColor(),enumProdAdDetail.getAdDetailId(),enumProdAdDetail.getAdPoolSeq(),enumProdAdDetail.getDefineAdSeq());
		 		break;
			case DIS_BG_COLOR:
				//標籤背景顏色
				adAddAction.saveAdDetail(adAddAction.getDisBgColor(),enumProdAdDetail.getAdDetailId(),enumProdAdDetail.getAdPoolSeq(),enumProdAdDetail.getDefineAdSeq());
		 		break;
			case PROD_RADIO_LOGO_TYPE:
				adAddAction.saveAdDetail(adAddAction.getProdLogoType(),enumProdAdDetail.getAdDetailId(),enumProdAdDetail.getAdPoolSeq(),enumProdAdDetail.getDefineAdSeq());
		 		break;
			case LOGO_IMG_URL:
				adAddAction.saveAdDetail(adAddAction.getLogoPath(),enumProdAdDetail.getAdDetailId(),enumProdAdDetail.getAdPoolSeq(),enumProdAdDetail.getDefineAdSeq());
		 		break;
			case THIRD_DETECTION:
				adAddAction.saveAdDetail("",enumProdAdDetail.getAdDetailId(),enumProdAdDetail.getAdPoolSeq(),enumProdAdDetail.getDefineAdSeq());
		 		break;
			}
		}
		Date date = new Date();
		StringBuffer saveImgPathBuffer = new StringBuffer();
		saveImgPathBuffer.append(adAddAction.getPhotoDbPath()).append("user/").append(adAddAction.getCustomer_info_id()).append("/").append(adAddAction.getSdf().format(date)).append("/original/").append(adSeq).append("/");
		JSONObject uploadLogJson = new JSONObject(adAddAction.getUploadLog());
		saveImg(uploadLogJson,"salesEngImg",saveImgPathBuffer,adSeq,"add");
		JSONObject uploadLogoLogJson = new JSONObject(adAddAction.getUploadLogoLog());
		saveImg(uploadLogoLogJson,"logoImg",saveImgPathBuffer,adSeq,"add");
		return null;
	}

	
	@Override
	public String adAdEdit(AdEditAction adEditAction) throws Exception {
		List<PfpCatalogLogo> pfpCatalogLogoList = pfpCatalogLogoService.findCatalogLogoByCustomerInfoId(adEditAction.getCustomer_info_id());
		//檢查logo是否審核通過
		if(pfpCatalogLogoList == null || pfpCatalogLogoList.size() < 2){
			return "logo";
		}else{
			boolean flag = false;
			for (PfpCatalogLogo pfpCatalogLogo : pfpCatalogLogoList) {
				if(!pfpCatalogLogo.getStatus().equals("1")){
					flag = true;
					break;
				}
			}
			if(flag){
				return "logo";
			}
		}
		
		List<String> xTypeList = new ArrayList<String>();
		xTypeList.add("x04");
		xTypeList.add("x05");
		List<AdmTemplateProduct> admTemplateProductList = admTemplateProductService.getTemplateProductByXType(xTypeList);
		JSONObject admTemplateJson = new JSONObject();
		for (AdmTemplateProduct admTemplateProduct : admTemplateProductList) {
			String templateSize = admTemplateProduct.getTemplateProductWidth()+"_"+admTemplateProduct.getTemplateProductHeight();
			String tproName = "";
			if(admTemplateJson.has(templateSize)){
				tproName = admTemplateJson.getString(templateSize);
				tproName = tproName+admTemplateProduct.getTemplateProductSeq()+",";
			}else{
				tproName =  admTemplateProduct.getTemplateProductSeq()+",";
			}
			admTemplateJson.put(templateSize, tproName);
		}
		templateStr = admTemplateJson.toString();
		adEditAction.getRequest().setAttribute("templateStr", templateStr);
		
		
		JSONObject json = new JSONObject();
		if(pfpCatalogLogoList != null){
			for (PfpCatalogLogo pfpCatalogLogo : pfpCatalogLogoList) {
				JSONObject catalogLogoUrlJson = new JSONObject();
				catalogLogoUrlJson.put("logoPath", pfpCatalogLogo.getCatalogLogoUrl());
				catalogLogoUrlJson.put("logoStatus", pfpCatalogLogo.getStatus());
				List<String> colorList = new ArrayList<String>();
				Set<PfpCatalogLogoDetail> pfpCatalogLogoDetailSet = pfpCatalogLogo.getPfpCatalogLogoDetails();
				for (PfpCatalogLogoDetail pfpCatalogLogoDetail : pfpCatalogLogoDetailSet) {
					String color = pfpCatalogLogoDetail.getCatalogLogoHexColor();
					colorList.add(color);
				}
				catalogLogoUrlJson.put("color", colorList);
				if(pfpCatalogLogo.getCatalogLogoType().equals("0")){
					json.put("square", catalogLogoUrlJson);
				}
				if(pfpCatalogLogo.getCatalogLogoType().equals("1")){
					json.put("rectangle", catalogLogoUrlJson);
				}
			}
		}
		adEditAction.setUserLogoPath(json.toString());
		
		Set<PfpAdDetail> detailSet = adEditAction.getPfpAd().getPfpAdDetails();
		JSONArray uploadLogJsonArray = new JSONArray();
		JSONArray uploadLogoLogJsonArray = new JSONArray();
		for (PfpAdDetail pfpAdDetail : detailSet) {
			if(EnumProdAdDetail.PROD_REPORT_NAME.getAdDetailId().equals(pfpAdDetail.getAdDetailId())){
				adEditAction.setAdName(pfpAdDetail.getAdDetailContent());
			}
			if(EnumProdAdDetail.PROD_LIST.getAdDetailId().equals(pfpAdDetail.getAdDetailId())){
				adEditAction.setCatalogId(pfpAdDetail.getAdDetailContent());
			}
			if(EnumProdAdDetail.PROD_GROUP.getAdDetailId().equals(pfpAdDetail.getAdDetailId())){
				adEditAction.setCatalogGroupId(pfpAdDetail.getAdDetailContent());
			}
			if(EnumProdAdDetail.PROD_AD_URL.getAdDetailId().equals(pfpAdDetail.getAdDetailId())){
				adEditAction.setAdLinkURL(pfpAdDetail.getAdDetailContent());
			}
			if(EnumProdAdDetail.LOGO_TYPE.getAdDetailId().equals(pfpAdDetail.getAdDetailId())){
				adEditAction.setLogoType(pfpAdDetail.getAdDetailContent());
			}
			if(EnumProdAdDetail.LOGO_TXT.getAdDetailId().equals(pfpAdDetail.getAdDetailId())){
				adEditAction.setLogoText(pfpAdDetail.getAdDetailContent());
			}
			if(EnumProdAdDetail.LOGO_FONT_COLOR.getAdDetailId().equals(pfpAdDetail.getAdDetailId())){
				adEditAction.setLogoFontColor(pfpAdDetail.getAdDetailContent());
			}
			if(EnumProdAdDetail.LOGO_BG_COLOR.getAdDetailId().equals(pfpAdDetail.getAdDetailId())){
				adEditAction.setLogoBgColor(pfpAdDetail.getAdDetailContent());
			}
			if(EnumProdAdDetail.BTN_TXT.getAdDetailId().equals(pfpAdDetail.getAdDetailId())){
				
				for (EnumProdAdBtnText enumProdAdBtnText : EnumProdAdBtnText.values()) {
					if(enumProdAdBtnText.getBtnText().equals(pfpAdDetail.getAdDetailContent())){
						adEditAction.setBtnTxt(enumProdAdBtnText.getBtnType());
						break;
					}
				}
			}
			if(EnumProdAdDetail.BTN_FONT_COLOR.getAdDetailId().equals(pfpAdDetail.getAdDetailId())){
				adEditAction.setBtnFontColor(pfpAdDetail.getAdDetailContent());
			}
			if(EnumProdAdDetail.BTN_BG_COLOR.getAdDetailId().equals(pfpAdDetail.getAdDetailId())){
				adEditAction.setBtnBgColor(pfpAdDetail.getAdDetailContent());
			}
			if(EnumProdAdDetail.DIS_TXT_TYPE.getAdDetailId().equals(pfpAdDetail.getAdDetailId())){
				adEditAction.setDisTxtType(pfpAdDetail.getAdDetailContent());
			}
			if(EnumProdAdDetail.DIS_FONT_COLOR.getAdDetailId().equals(pfpAdDetail.getAdDetailId())){
				adEditAction.setDisFontColor(pfpAdDetail.getAdDetailContent());
			}
			if(EnumProdAdDetail.DIS_BG_COLOR.getAdDetailId().equals(pfpAdDetail.getAdDetailId())){
				adEditAction.setDisBgColor(pfpAdDetail.getAdDetailContent());
			}
			if(EnumProdAdDetail.PROD_RADIO_LOGO_TYPE.getAdDetailId().equals(pfpAdDetail.getAdDetailId())){
				adEditAction.setProdLogoType(pfpAdDetail.getAdDetailContent());
			}
			
			String imgPath = "";
			String[] fileExtensionNameArray = null;
			String fileExtensionName = "";
			 //結尾行銷圖
			if(pfpAdDetail.getDefineAdSeq().contains("dad_sale_img")){
				JSONObject uploadLogJson = new JSONObject();
				imgPath = pfpAdDetail.getAdDetailContent();
				fileExtensionNameArray = imgPath.split("\\.");
				fileExtensionName = fileExtensionNameArray[fileExtensionNameArray.length-1];
				File file = new File(photoClonePath+imgPath);
				if(file.exists()){
					String fileNameArray[] = imgPath.split("/");
					fileNameArray = fileNameArray[fileNameArray.length - 1].split("_"+adEditAction.getAdSeq()+"_");
					String fileName = fileNameArray[0];
					int width = Integer.parseInt((fileNameArray[1].split("\\.")[0]).split("x")[0]);
					int heigth = Integer.parseInt((fileNameArray[1].split("\\.")[0]).split("x")[1]);
					long fileSize = file.length() / 1024;
					uploadLogJson.put("width", width);
					uploadLogJson.put("heigth", heigth);
					uploadLogJson.put("fileExtensionName", fileExtensionName.toUpperCase());
					uploadLogJson.put("previewSrc", imgPath);
					uploadLogJson.put("fileName", fileName);
					uploadLogJson.put("fileSize", fileSize);
					uploadLogJsonArray.put(uploadLogJson);
					continue;
				}
			}else if(pfpAdDetail.getDefineAdSeq().contains("dad_logo_sale_img")){ //行銷圖
				JSONObject uploadLogoLogJson = new JSONObject();
				imgPath = pfpAdDetail.getAdDetailContent();
				fileExtensionNameArray = imgPath.split("\\.");
				fileExtensionName = fileExtensionNameArray[fileExtensionNameArray.length-1];
				File file = new File(photoClonePath+imgPath);
				if(file.exists()){
					String fileNameArray[] = imgPath.split("/");
					fileNameArray = fileNameArray[fileNameArray.length - 1].split("_"+adEditAction.getAdSeq()+"_");
					String fileName = fileNameArray[0];
					int width = Integer.parseInt((fileNameArray[1].split("\\.")[0]).split("x")[0]);
					int heigth = Integer.parseInt((fileNameArray[1].split("\\.")[0]).split("x")[1]);
					long fileSize = file.length() / 1024;
					uploadLogoLogJson.put("width", width);
					uploadLogoLogJson.put("heigth", heigth);
					uploadLogoLogJson.put("fileExtensionName", fileExtensionName.toUpperCase());
					uploadLogoLogJson.put("previewSrc", imgPath);
					uploadLogoLogJson.put("fileName", fileName);
					uploadLogoLogJson.put("fileSize", fileSize);
					uploadLogoLogJsonArray.put(uploadLogoLogJson);
					continue;
				}
			}
		}
		adEditAction.setUploadLogoLog(uploadLogoLogJsonArray.toString());
		adEditAction.setUploadLog(uploadLogJsonArray.toString());
		catalogList = pfpCatalogService.getPfpCatalogByCustomerInfoId(adEditAction.getCustomer_info_id());
		//全部商品未審核不顯示
		Iterator<PfpCatalog> iterator = catalogList.iterator();
		while (iterator.hasNext()) {
			PfpCatalog pfpCatalog = iterator.next();
			int status = pfpCatalogService.checkCatalogProdStatus(pfpCatalog.getCatalogSeq());
			if(status == 0){
				iterator.remove();
			}
		}
		adEditAction.getRequest().setAttribute("catalogList", catalogList);
		return "SUCCESS";
	}
	
	public String doAdAdEdit(AdEditAction adEditAction) throws Exception {
		log.info(">>>>>> process do edit prod ad");
		this.adEditAction = adEditAction;
		pfpCustomerInfoId = adEditAction.getCustomer_info_id();
		//1.刪除所有detail
		Set<PfpAdDetail> detailSet = adEditAction.getPfpAd().getPfpAdDetails();
		String beforeProdReportName = "";
		String afterProdReportName = "";
		String beforeProdGroupName = "";
		String afterProdGroupName = "";
		String beforeProdListName = "";
		String afterProdListName = "";
		String beforeAdLinkURL = "";
		String afterAdLinkURL = "";
		String beforeLogoTxt = "";
		String afterLogoTxt = "";
		String beforeDadSaleImg = "";
		String afterDadSaleImg = "";
		String beforeDadLogoSaleImg = "";
		String afterDadLogoSaleImg = "";
		
		for (PfpAdDetail pfpAdDetail : detailSet) {
			if(pfpAdDetail.getAdDetailId().equals(EnumProdAdDetail.PROD_REPORT_NAME.getAdDetailId())) {
				beforeProdReportName = pfpAdDetail.getAdDetailContent();
				afterProdReportName = adEditAction.getAdName();
			}
			if(pfpAdDetail.getAdDetailId().equals(EnumProdAdDetail.PROD_GROUP.getAdDetailId())) {
				beforeProdGroupName = pfpCatalogGroupService.getPfpCatalogGroup(pfpAdDetail.getAdDetailContent()).getCatalogGroupName();
				afterProdGroupName = pfpCatalogGroupService.getPfpCatalogGroup(adEditAction.getCatalogGroupId()).getCatalogGroupName();
				
			}
			if(pfpAdDetail.getAdDetailId().equals(EnumProdAdDetail.PROD_LIST.getAdDetailId())) {
				beforeProdListName = pfpCatalogService.getPfpCatalog(pfpAdDetail.getAdDetailContent()).getCatalogName();
				afterProdListName = pfpCatalogService.getPfpCatalog(adEditAction.getCatalogId()).getCatalogName();
			}
			
			if(pfpAdDetail.getAdDetailId().equals(EnumProdAdDetail.PROD_AD_URL.getAdDetailId())) {
				beforeAdLinkURL = pfpAdDetail.getAdDetailContent();
				afterAdLinkURL = adEditAction.getAdLinkURL();
				
			}
			
			if(pfpAdDetail.getAdDetailId().equals(EnumProdAdDetail.LOGO_TXT.getAdDetailId())) {
				beforeLogoTxt = pfpAdDetail.getAdDetailContent();
				afterLogoTxt = adEditAction.getLogoText();
			}
			if(pfpAdDetail.getDefineAdSeq().contains("dad_sale_img_")) {
				String size = pfpAdDetail.getAdDetailContent().split("_")[2]+"_"+pfpAdDetail.getAdDetailContent().split("_")[3];
				if(StringUtils.isBlank(beforeDadSaleImg)) {
					beforeDadSaleImg = beforeDadSaleImg + size;
				}else {
					beforeDadSaleImg = beforeDadSaleImg + ","+size;
				}
			}else if(pfpAdDetail.getDefineAdSeq().contains("dad_logo_sale_img_")) {
				String size = pfpAdDetail.getAdDetailContent().split("_")[2]+"_"+pfpAdDetail.getAdDetailContent().split("_")[3];
				if(StringUtils.isBlank(beforeDadLogoSaleImg)) {
					beforeDadLogoSaleImg = beforeDadLogoSaleImg + size;
				}else {
					beforeDadLogoSaleImg = beforeDadLogoSaleImg + ","+size;
				}
			}
			
		}
		for (PfpAdDetail pfpAdDetail : detailSet) {
			adEditAction.getPfpAdDetailService().deletePfpAdDetail(pfpAdDetail.getAdDetailSeq());
		}
		for (EnumProdAdDetail enumProdAdDetail : EnumProdAdDetail.values()) {
			switch(enumProdAdDetail) {
	        case PROD_REPORT_NAME:
	        	adEditAction.saveAdDetail(adEditAction.getAdName(),enumProdAdDetail.getAdDetailId(),enumProdAdDetail.getAdPoolSeq(),enumProdAdDetail.getDefineAdSeq());
	        	break;
		 	case PROD_LIST:
		 		adEditAction.saveAdDetail(adEditAction.getCatalogId(),enumProdAdDetail.getAdDetailId(),enumProdAdDetail.getAdPoolSeq(),enumProdAdDetail.getDefineAdSeq());
		 		break;
			case PROD_GROUP:
				adEditAction.saveAdDetail(adEditAction.getCatalogGroupId(),enumProdAdDetail.getAdDetailId(),enumProdAdDetail.getAdPoolSeq(),enumProdAdDetail.getDefineAdSeq());
		 		break;
			case PROD_AD_URL:
				//商品連結網址
				adEditAction.saveAdDetail(adEditAction.getAdLinkURL(),enumProdAdDetail.getAdDetailId(),enumProdAdDetail.getAdPoolSeq(),enumProdAdDetail.getDefineAdSeq());
		 		break;
			case LOGO_TYPE:
				adEditAction.saveAdDetail(adEditAction.getLogoType(),enumProdAdDetail.getAdDetailId(),enumProdAdDetail.getAdPoolSeq(),enumProdAdDetail.getDefineAdSeq());
		 		break;
			case LOGO_TXT:
				//logo標題文字
				adEditAction.saveAdDetail(adEditAction.getLogoText(),enumProdAdDetail.getAdDetailId(),enumProdAdDetail.getAdPoolSeq(),enumProdAdDetail.getDefineAdSeq());
		 		break;
			case LOGO_FONT_COLOR:
				//logo標題文字顏色
				adEditAction.saveAdDetail(adEditAction.getLogoFontColor(),enumProdAdDetail.getAdDetailId(),enumProdAdDetail.getAdPoolSeq(),enumProdAdDetail.getDefineAdSeq());
		 		break;
			case LOGO_BG_COLOR:
				//logo背景顏色
				adEditAction.saveAdDetail(adEditAction.getLogoBgColor(),enumProdAdDetail.getAdDetailId(),enumProdAdDetail.getAdPoolSeq(),enumProdAdDetail.getDefineAdSeq());
		 		break;
			case BTN_TXT:
				//按鈕文字
				for (EnumProdAdBtnText enumProdAdBtnText : EnumProdAdBtnText.values()) {
					if(adEditAction.getBtnTxt().equals(enumProdAdBtnText.getBtnType())){
						adEditAction.saveAdDetail(enumProdAdBtnText.getBtnText(),enumProdAdDetail.getAdDetailId(),enumProdAdDetail.getAdPoolSeq(),enumProdAdDetail.getDefineAdSeq());
						break;
					}
				}
		 		break;
			case BTN_FONT_COLOR:
				//按鈕文字顏色
				adEditAction.saveAdDetail(adEditAction.getBtnFontColor(),enumProdAdDetail.getAdDetailId(),enumProdAdDetail.getAdPoolSeq(),enumProdAdDetail.getDefineAdSeq());
		 		break;
			case BTN_BG_COLOR:
				//按鈕背景顏色
				adEditAction.saveAdDetail(adEditAction.getBtnBgColor(),enumProdAdDetail.getAdDetailId(),enumProdAdDetail.getAdPoolSeq(),enumProdAdDetail.getDefineAdSeq());
				break;
			case DIS_TXT_TYPE:
				//標籤文字
				adEditAction.saveAdDetail(adEditAction.getDisTxtType(),enumProdAdDetail.getAdDetailId(),enumProdAdDetail.getAdPoolSeq(),enumProdAdDetail.getDefineAdSeq());
		 		break;
			case DIS_FONT_COLOR:
				//標籤文字顏色
				adEditAction.saveAdDetail(adEditAction.getDisFontColor(),enumProdAdDetail.getAdDetailId(),enumProdAdDetail.getAdPoolSeq(),enumProdAdDetail.getDefineAdSeq());
		 		break;
			case DIS_BG_COLOR:
				//標籤背景顏色
				adEditAction.saveAdDetail(adEditAction.getDisBgColor(),enumProdAdDetail.getAdDetailId(),enumProdAdDetail.getAdPoolSeq(),enumProdAdDetail.getDefineAdSeq());
		 		break;
			case PROD_RADIO_LOGO_TYPE:
				adEditAction.saveAdDetail(adEditAction.getProdLogoType(),enumProdAdDetail.getAdDetailId(),enumProdAdDetail.getAdPoolSeq(),enumProdAdDetail.getDefineAdSeq());
		 		break;
			case LOGO_IMG_URL:
				adEditAction.saveAdDetail(adEditAction.getLogoPath(),enumProdAdDetail.getAdDetailId(),enumProdAdDetail.getAdPoolSeq(),enumProdAdDetail.getDefineAdSeq());
		 		break;
			}
		}
		
		Date date = new Date();
		StringBuffer saveImgPathBuffer = new StringBuffer();
		saveImgPathBuffer.append(adEditAction.getPhotoDbPath()).append("user/").append(adEditAction.getCustomer_info_id()).append("/").append(adEditAction.getSdf().format(date)).append("/original/").append(adEditAction.getAdSeq()).append("/");
		JSONObject uploadLogJson = new JSONObject(adEditAction.getUploadLog());
		
		Iterator iter = uploadLogJson.keys();
		while(iter.hasNext()){
			String key = (String)iter.next();
			JSONObject data = (JSONObject) uploadLogJson.get(key);
			if(data.getString("fileName").contains("salesEngImg")) {
				String size  = data.getString("fileName").split("_")[1]+"_"+data.getString("fileName").split("_")[2];
				if(StringUtils.isBlank(afterDadSaleImg)) {
					afterDadSaleImg = afterDadSaleImg + size;
				}else {
					afterDadSaleImg = afterDadSaleImg + ","+size;
				}
			}else {
				String size  = data.getString("width")+"_"+data.getString("height");
				if(StringUtils.isBlank(afterDadSaleImg)) {
					afterDadSaleImg = afterDadSaleImg + "update_"+size;
				}else {
					afterDadSaleImg = afterDadSaleImg + ",update_"+size;
				}
			}
		}
		
		saveImg(uploadLogJson,"salesEngImg",saveImgPathBuffer,adEditAction.getAdSeq(),"edit");
		JSONObject uploadLogoLogJson = new JSONObject(adEditAction.getUploadLogoLog());
		
		iter = uploadLogoLogJson.keys();
		while(iter.hasNext()){
			String key = (String)iter.next();
			JSONObject data = (JSONObject) uploadLogoLogJson.get(key);
			if(data.getString("fileName").contains("logoImg")) {
				String size  = data.getString("fileName").split("_")[1]+"_"+data.getString("fileName").split("_")[2];
				if(StringUtils.isBlank(afterDadLogoSaleImg)) {
					afterDadLogoSaleImg = afterDadLogoSaleImg + size;
				}else {
					afterDadLogoSaleImg = afterDadLogoSaleImg + ","+size;
				}
			}else {
				String size  = data.getString("width")+"_"+data.getString("height");
				if(StringUtils.isBlank(afterDadLogoSaleImg)) {
					afterDadLogoSaleImg = afterDadLogoSaleImg + "update_"+size;
				}else {
					afterDadLogoSaleImg = afterDadLogoSaleImg + ",update_"+size;
				}
			}
		 }
		
		saveImg(uploadLogoLogJson,"logoImg",saveImgPathBuffer,adEditAction.getAdSeq(),"edit");
		//accesslog
		String actionName = adEditAction.getPfpAd().getPfpAdGroup().getPfpAdAction().getAdActionName();
		String groupName = adEditAction.getPfpAd().getPfpAdGroup().getAdGroupName();
		
		if(!beforeProdReportName.equals(afterProdReportName)) {
			String message = "商品廣告："+actionName+"；"+ groupName+"；"+beforeProdReportName+"；廣告明細名稱修改："+beforeProdReportName+"=>"+afterProdReportName.trim();
			admAccesslogService.recordAdLog(EnumAccesslogAction.PLAY_MODIFY, message, adEditAction.getId_pchome(), adEditAction.getCustomer_info_id(), adEditAction.getUser_id(), adEditAction.getRequest().getRemoteAddr());
		}
		
		if(!beforeProdGroupName.equals(afterProdGroupName)) {
			String message = "商品廣告："+actionName+"；"+ groupName+"；"+afterProdReportName+"；修改商品組合："+beforeProdGroupName+"=>"+afterProdGroupName;
			admAccesslogService.recordAdLog(EnumAccesslogAction.PLAY_MODIFY, message, adEditAction.getId_pchome(), adEditAction.getCustomer_info_id(), adEditAction.getUser_id(), adEditAction.getRequest().getRemoteAddr());
		}
		if(!beforeProdListName.equals(afterProdListName)) {
			String message = "商品廣告："+actionName+"；"+ groupName+"；"+afterProdReportName+"；修改商品目錄："+beforeProdListName+"=>"+afterProdListName;
			admAccesslogService.recordAdLog(EnumAccesslogAction.PLAY_MODIFY, message, adEditAction.getId_pchome(), adEditAction.getCustomer_info_id(), adEditAction.getUser_id(), adEditAction.getRequest().getRemoteAddr());
		}
		if(!beforeAdLinkURL.equals(afterAdLinkURL)) {
			String message = "商品廣告："+actionName+"；"+ groupName+"；"+afterProdReportName+"；修改連結網址："+beforeAdLinkURL+"=>"+afterAdLinkURL;
			admAccesslogService.recordAdLog(EnumAccesslogAction.PLAY_MODIFY, message, adEditAction.getId_pchome(), adEditAction.getCustomer_info_id(), adEditAction.getUser_id(), adEditAction.getRequest().getRemoteAddr());
		}
		if(!beforeLogoTxt.equals(afterLogoTxt)) {
			String message = "商品廣告："+actionName+"；"+ groupName+"；"+afterProdReportName+"；修改LOGO標題文字："+beforeLogoTxt+"=>"+afterLogoTxt;
			admAccesslogService.recordAdLog(EnumAccesslogAction.PLAY_MODIFY, message, adEditAction.getId_pchome(), adEditAction.getCustomer_info_id(), adEditAction.getUser_id(), adEditAction.getRequest().getRemoteAddr());
		}
		String message = "";
		if(StringUtils.isNotBlank(beforeDadSaleImg) || StringUtils.isNotBlank(afterDadSaleImg)) {
			message = "商品廣告："+actionName+"；"+ groupName+"；"+afterProdReportName+"；修改結尾行銷圖像："+"["+beforeDadSaleImg+"]=>["+afterDadSaleImg+"]";
			admAccesslogService.recordAdLog(EnumAccesslogAction.PLAY_MODIFY, message, adEditAction.getId_pchome(), adEditAction.getCustomer_info_id(), adEditAction.getUser_id(), adEditAction.getRequest().getRemoteAddr());
		}
		if(StringUtils.isNotBlank(beforeDadLogoSaleImg) || StringUtils.isNotBlank(afterDadLogoSaleImg)) {
			message = "商品廣告："+actionName+"；"+ groupName+"；"+afterProdReportName+"；修改行銷圖像："+"["+beforeDadLogoSaleImg+"]=>["+afterDadLogoSaleImg+"]";
			admAccesslogService.recordAdLog(EnumAccesslogAction.PLAY_MODIFY, message, adEditAction.getId_pchome(), adEditAction.getCustomer_info_id(), adEditAction.getUser_id(), adEditAction.getRequest().getRemoteAddr());
		}
		message = "商品廣告："+actionName+"；"+ groupName+"；"+afterProdReportName+"=>送出審核";
		admAccesslogService.recordAdLog(EnumAccesslogAction.AD_STATUS_MODIFY, message, adEditAction.getId_pchome(), adEditAction.getCustomer_info_id(), adEditAction.getUser_id(), adEditAction.getRequest().getRemoteAddr());
		return null;
	}
	
	private void saveImg(JSONObject uploadImgJson,String uploadType,StringBuffer saveImgPathBuffer,String adSeq,String type) throws Exception{
		log.info("###########################################################1");
		Iterator keys = uploadImgJson.keys();
		while(keys.hasNext()) {
			String saveImgPath = "";
		    String key = (String)keys.next();
		    JSONObject data = (JSONObject) uploadImgJson.get(key);
		    ByteArrayInputStream bis = null;
		    if(data.getString("previewSrc").contains("/img/user/")) {
		    	bis = new ByteArrayInputStream(FileUtils.readFileToByteArray(new File(photoClonePath+data.getString("previewSrc"))));
		    }else {
		    	String bessie64ImgArray[] = data.getString("previewSrc").split(",");
			    String bessie64Img = bessie64ImgArray[1];
			    byte[] imageByte = Base64.decodeBase64(bessie64Img.getBytes());
			    bis = new ByteArrayInputStream(imageByte);
		    }
		    
		    String width = data.getString("width");
		    String height = data.getString("height");
		    String fileName = uploadType+"_"+width+"_"+height;
		    String fileExtensionName = data.getString("fileExtensionName").toLowerCase();
           
            String adDetailId = "";
            String defineAdSeq ="";
            if(uploadType.equals("logoImg")){
            	adDetailId = "logo_sale_img_"+width+"x"+height;
            	defineAdSeq = "dad_"+adDetailId;
            }else if(uploadType.equals("salesEngImg")){
            	adDetailId = "sale_img_"+width+"x"+height;
            	defineAdSeq = "dad_"+adDetailId;
            }
            log.info("###########################################################2");
            if(StringUtils.isNotBlank(adDetailId) && StringUtils.isNotBlank(defineAdSeq)){
            	if(fileExtensionName.toUpperCase().equals("PNG") || fileExtensionName.toUpperCase().equals("JPG") || fileExtensionName.toUpperCase().equals("JPEG")) {
            		commonUtilModel.writeImgByStream(bis, fileExtensionName, photoPath+"user/"+pfpCustomerInfoId+"/"+sdf.format(new Date())+"/original/",fileName+"_"+adSeq+"_"+width+"x"+height);
            		saveImgPath = photoDBPath+"user/"+pfpCustomerInfoId+"/"+sdf.format(new Date())+"/original/"+fileName+"_"+adSeq+"_"+width+"x"+height+".jpg";
            	}else {
            		commonUtilModel.writeImgByStream(bis, fileExtensionName, photoPath+"user/"+pfpCustomerInfoId+"/"+sdf.format(new Date())+"/original/",fileName+"_"+adSeq+"_"+width+"x"+height);
            		saveImgPath = photoDBPath+"user/"+pfpCustomerInfoId+"/"+sdf.format(new Date())+"/original/"+fileName+"_"+adSeq+"_"+width+"x"+height+"."+fileExtensionName;
            	}
            	if(type.equals("add")){
            		adAddAction.saveAdDetail(saveImgPath,adDetailId,EnumProdAdDetail.PROD_REPORT_NAME.getAdPoolSeq(),defineAdSeq);	
            	}else if(type.equals("edit")){
            		adEditAction.saveAdDetail(saveImgPath,adDetailId,EnumProdAdDetail.PROD_REPORT_NAME.getAdPoolSeq(),defineAdSeq);
            	}
            }
            bis.close();
		}
	}
	
	public String getTemplateStr() {
		return templateStr;
	}

	public void setTemplateStr(String templateStr) {
		this.templateStr = templateStr;
	}

	public IPfpCatalogService getPfpCatalogService() {
		return pfpCatalogService;
	}

	public void setPfpCatalogService(IPfpCatalogService pfpCatalogService) {
		this.pfpCatalogService = pfpCatalogService;
	}

	public List<PfpCatalog> getCatalogList() {
		return catalogList;
	}

	public void setCatalogList(List<PfpCatalog> catalogList) {
		this.catalogList = catalogList;
	}

	public IPfpCatalogLogoService getPfpCatalogLogoService() {
		return pfpCatalogLogoService;
	}

	public void setPfpCatalogLogoService(IPfpCatalogLogoService pfpCatalogLogoService) {
		this.pfpCatalogLogoService = pfpCatalogLogoService;
	}

	public IPfpCatalogSetupService getPfpCatalogSetupService() {
		return pfpCatalogSetupService;
	}

	public void setPfpCatalogSetupService(IPfpCatalogSetupService pfpCatalogSetupService) {
		this.pfpCatalogSetupService = pfpCatalogSetupService;
	}

	public ITemplateProductService getAdmTemplateProductService() {
		return admTemplateProductService;
	}

	public void setAdmTemplateProductService(ITemplateProductService admTemplateProductService) {
		this.admTemplateProductService = admTemplateProductService;
	}

	public String getPhotoClonePath() {
		return photoClonePath;
	}

	public void setPhotoClonePath(String photoClonePath) {
		this.photoClonePath = photoClonePath;
	}
	
	public AdmAccesslogService getAdmAccesslogService() {
		return admAccesslogService;
	}

	public void setAdmAccesslogService(AdmAccesslogService admAccesslogService) {
		this.admAccesslogService = admAccesslogService;
	}
	
	public IPfpCatalogGroupService getPfpCatalogGroupService() {
		return pfpCatalogGroupService;
	}

	public void setPfpCatalogGroupService(IPfpCatalogGroupService pfpCatalogGroupService) {
		this.pfpCatalogGroupService = pfpCatalogGroupService;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public String getPhotoDBPath() {
		return photoDBPath;
	}

	public void setPhotoDBPath(String photoDBPath) {
		this.photoDBPath = photoDBPath;
	}

}
