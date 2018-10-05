package com.pchome.akbpfp.struts2.action.factory.ad;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import com.pchome.akbpfp.db.pojo.PfpAdDetail;
import com.pchome.akbpfp.db.pojo.PfpAdGroup;
import com.pchome.akbpfp.db.pojo.PfpCatalog;
import com.pchome.akbpfp.db.pojo.PfpCatalogLogo;
import com.pchome.akbpfp.db.pojo.PfpCatalogSetup;
import com.pchome.akbpfp.db.service.catalog.TMP.IPfpCatalogService;
import com.pchome.akbpfp.db.service.catalog.prod.IPfpCatalogLogoService;
import com.pchome.akbpfp.db.service.catalog.prod.IPfpCatalogSetupService;
import com.pchome.akbpfp.db.service.catalog.prod.PfpCatalogLogoService;
import com.pchome.akbpfp.struts2.action.ad.AdAddAction;
import com.pchome.akbpfp.struts2.action.ad.AdEditAction;
import com.pchome.akbpfp.struts2.action.intfc.ad.IAd;
import com.pchome.enumerate.ad.EnumAdStyle;
import com.pchome.enumerate.ad.EnumProdAdBtnText;
import com.pchome.enumerate.ad.EnumProdAdDetail;
import com.pchome.enumerate.sequence.EnumSequenceTableName;

public class ProdAd implements IAd {

	protected Log log = LogFactory.getLog(getClass());

	private  IPfpCatalogService pfpCatalogService;
	
	private IPfpCatalogLogoService pfpCatalogLogoService;
	private IPfpCatalogSetupService pfpCatalogSetupService;
	private List<PfpCatalog> alex;
	
	
	private AdAddAction adAddAction;
	private AdEditAction adEditAction;
	
	public String AdAdAddInit(AdAddAction adAddAction) throws Exception {
		
		
		System.out.println(adAddAction.getRequest().getAttribute("webAppRootKey"));
		
		log.info(">>>>>> process ProdAd");
		alex = pfpCatalogService.getPfpCatalogByCustomerInfoId(adAddAction.getCustomer_info_id());
		PfpCatalogSetup pfpCatalogSetup = pfpCatalogSetupService.findSetupByCatalogSeq(adAddAction.getCatalogGroupId());
		if(pfpCatalogSetup != null){
			adAddAction.setImgProportiona(pfpCatalogSetup.getCatalogSetupValue());
		}
		List<PfpCatalogLogo> pfpCatalogLogoList = pfpCatalogLogoService.findCatalogLogoByCustomerInfoId(adAddAction.getCustomer_info_id());
		JSONObject json = new JSONObject();
		if(pfpCatalogLogoList != null){
			for (PfpCatalogLogo pfpCatalogLogo : pfpCatalogLogoList) {
				JSONObject catalogLogoUrlJson = new JSONObject();
				catalogLogoUrlJson.put("logoPath", adAddAction.getPhotoDbPathPrefix()+pfpCatalogLogo.getCatalogLogoUrl());
				catalogLogoUrlJson.put("logoStatus", pfpCatalogLogo.getStatus());
				if(pfpCatalogLogo.getCatalogLogoType().equals("0")){
					json.put("square", catalogLogoUrlJson);
				}
				if(pfpCatalogLogo.getCatalogLogoType().equals("1")){
					json.put("rectangle", catalogLogoUrlJson);
				}
			}
		}
		adAddAction.setUserLogoPath(json.toString());
		adAddAction.getRequest().setAttribute("alex", alex);
		return "adProdAdd";
	}

	public String doAdAdAdd(AdAddAction adAddAction) throws Exception {
		log.info(">>>>>> process do add prod ad");
		this.adAddAction = adAddAction;
		String adSeq = adAddAction.getSequenceService().getId(EnumSequenceTableName.PFP_AD, "_");
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
			case SALE_IMG_SHOW_TYPE:
				adAddAction.saveAdDetail(adAddAction.getProdLogoType(),enumProdAdDetail.getAdDetailId(),enumProdAdDetail.getAdPoolSeq(),enumProdAdDetail.getDefineAdSeq());
		 		break;
			}

		}
		Date date = new Date();
		StringBuffer saveImgPathBuffer = new StringBuffer();
		saveImgPathBuffer.append(adAddAction.getPhotoDbPathNew()).append(adAddAction.getCustomer_info_id()).append("/").append(adAddAction.getSdf().format(date)).append("/original/").append(adSeq).append("/");
		JSONObject uploadLogJson = new JSONObject(adAddAction.getUploadLog());
		saveImg(uploadLogJson,"salesEngImg",saveImgPathBuffer,adSeq,"add");
		JSONObject uploadLogoLogJson = new JSONObject(adAddAction.getUploadLogoLog());
		saveImg(uploadLogoLogJson,"logoImg",saveImgPathBuffer,adSeq,"add");
		
		return null;
	}

	
	@Override
	public String adAdEdit(AdEditAction adEditAction) throws Exception {
		PfpCatalogSetup pfpCatalogSetup = pfpCatalogSetupService.findSetupByCatalogSeq(adEditAction.getCatalogGroupId());
		if(pfpCatalogSetup != null){
			adEditAction.setImgProportiona(pfpCatalogSetup.getCatalogSetupValue());
		}
		List<PfpCatalogLogo> pfpCatalogLogoList = pfpCatalogLogoService.findCatalogLogoByCustomerInfoId(adEditAction.getCustomer_info_id());
		JSONArray array = new JSONArray();
		if(pfpCatalogLogoList != null){
			for (PfpCatalogLogo pfpCatalogLogo : pfpCatalogLogoList) {
				JSONObject catalogLogoUrlJson = new JSONObject();
				catalogLogoUrlJson.put("logoPath", adEditAction.getPhotoDbPathPrefix()+pfpCatalogLogo.getCatalogLogoUrl());
				catalogLogoUrlJson.put("logoPath", pfpCatalogLogo.getStatus());
				if(pfpCatalogLogo.getCatalogLogoType().equals("0")){
					JSONObject json = new JSONObject();
					json.put("square", catalogLogoUrlJson);
					array.put(json);
				}
				if(pfpCatalogLogo.getCatalogLogoType().equals("1")){
					JSONObject json = new JSONObject();
					json.put("rectangle", catalogLogoUrlJson);
					array.put(json);
				}
			}
		}
		adAddAction.setUserLogoPath(array.toString());
		
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
			
			
			
			
			if(pfpAdDetail.getAdDetailId().indexOf("logo_sale") >= 0){
				JSONObject uploadLogoLogJson = new JSONObject();
				String imgPath = pfpAdDetail.getAdDetailContent();
				String fileExtensionNameArray[] = imgPath.split("\\.");
				String fileExtensionName = fileExtensionNameArray[fileExtensionNameArray.length-1];
				File file = new File(imgPath);
				String imgBase64 = "";
				if(file.exists()){
					imgBase64 = imgBase64(file,fileExtensionName);
				}
				String fileNameArray[] = imgPath.split("/");
				fileNameArray = fileNameArray[fileNameArray.length - 1].split("_"+adEditAction.getAdSeq()+"_");
				String fileName = fileNameArray[0];
				int width = Integer.parseInt((fileNameArray[1].split("\\.")[0]).split("x")[0]);
				int heigth = Integer.parseInt((fileNameArray[1].split("\\.")[0]).split("x")[1]);
				long fileSize = file.length() / 1024;
				
				uploadLogoLogJson.put("width", width);
				uploadLogoLogJson.put("heigth", heigth);
				uploadLogoLogJson.put("fileExtensionName", fileExtensionName.toUpperCase());
				uploadLogoLogJson.put("previewSrc", imgBase64);
				uploadLogoLogJson.put("fileName", fileName);
				uploadLogoLogJson.put("fileSize", fileSize);
				uploadLogoLogJsonArray.put(uploadLogoLogJson);
				continue;
			}
			if(pfpAdDetail.getAdDetailId().indexOf("sale_img") >= 0){
				JSONObject uploadLogJson = new JSONObject();
				String imgPath = pfpAdDetail.getAdDetailContent();
				String fileExtensionNameArray[] = imgPath.split("\\.");
				String fileExtensionName = fileExtensionNameArray[fileExtensionNameArray.length-1];
				File file = new File(imgPath);
				String imgBase64 = "";
				if(file.exists()){
					imgBase64 = imgBase64(file,fileExtensionName);
				}
				String fileNameArray[] = imgPath.split("/");
				fileNameArray = fileNameArray[fileNameArray.length - 1].split("_"+adEditAction.getAdSeq()+"_");
				String fileName = fileNameArray[0];
				int width = Integer.parseInt((fileNameArray[1].split("\\.")[0]).split("x")[0]);
				int heigth = Integer.parseInt((fileNameArray[1].split("\\.")[0]).split("x")[1]);
				long fileSize = file.length() / 1024;
				
				uploadLogJson.put("width", width);
				uploadLogJson.put("heigth", heigth);
				uploadLogJson.put("fileExtensionName", fileExtensionName.toUpperCase());
				uploadLogJson.put("previewSrc", imgBase64);
				uploadLogJson.put("fileName", fileName);
				uploadLogJson.put("fileSize", fileSize);
				uploadLogJsonArray.put(uploadLogJson);
				continue;
			}
		}
		adEditAction.setUploadLogoLog(uploadLogoLogJsonArray.toString());
		adEditAction.setUploadLog(uploadLogJsonArray.toString());
		alex = pfpCatalogService.getPfpCatalogByCustomerInfoId(adEditAction.getCustomer_info_id());
		adEditAction.getRequest().setAttribute("alex", alex);
		return null;
	}
	
	public String doAdAdEdit(AdEditAction adEditAction) throws Exception {
		log.info(">>>>>> process do edit prod ad");
		this.adEditAction = adEditAction;
		//1.刪除所有detail
		Set<PfpAdDetail> detailSet = adEditAction.getPfpAd().getPfpAdDetails();
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
			}
		}
		
		Date date = new Date();
		StringBuffer saveImgPathBuffer = new StringBuffer();
		saveImgPathBuffer.append(adEditAction.getPhotoDbPathNew()).append(adEditAction.getCustomer_info_id()).append("/").append(adEditAction.getSdf().format(date)).append("/original/").append(adEditAction.getAdSeq()).append("/");
		JSONObject uploadLogJson = new JSONObject(adEditAction.getUploadLog());
		saveImg(uploadLogJson,"salesEngImg",saveImgPathBuffer,adEditAction.getAdSeq(),"edit");
		JSONObject uploadLogoLogJson = new JSONObject(adEditAction.getUploadLogoLog());
		saveImg(uploadLogoLogJson,"logoImg",saveImgPathBuffer,adEditAction.getAdSeq(),"edit");
		return null;
	}
	
	private String imgBase64(File imgFile,String fileExtensionName) throws Exception{
		BufferedImage bi = ImageIO.read(imgFile);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(bi, fileExtensionName, baos);
		byte[] bytes = baos.toByteArray();
		String imgBase64 = "data:image/"+fileExtensionName+";base64,"+ new Base64().encodeToString(bytes);
		imgBase64 = imgBase64.replaceAll("\\s", "");
		return imgBase64;
	}
	
	private void saveImg(JSONObject uploadImgJson,String uploadType,StringBuffer saveImgPathBuffer,String adSeq,String type) throws Exception{
		File path = new File(saveImgPathBuffer.toString());
        if(!path.exists()){
        	path.mkdirs();
        }
		Iterator keys = uploadImgJson.keys();
		while(keys.hasNext()) {
		    String key = (String)keys.next();
		    JSONObject data = (JSONObject) uploadImgJson.get(key);
		    String bessie64ImgArray[] = data.getString("previewSrc").split(",");
		    String bessie64Img = bessie64ImgArray[1];
		    String fileName = data.getString("fileName");
		    String width = data.getString("width");
		    String height = data.getString("height");
		    String fileExtensionName = data.getString("fileExtensionName").toLowerCase();
		    
			BufferedImage image = null;
	        byte[] imageByte = Base64.decodeBase64(bessie64Img.getBytes());
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            String saveImgPath = saveImgPathBuffer.toString()+fileName+"_"+adSeq+"_"+width+"x"+height+"."+fileExtensionName;
            String adDetailId = "";
            String defineAdSeq ="";
            if(uploadType.equals("logoImg")){
            	adDetailId = "logo_sale_img_"+width+"x"+height;
            	defineAdSeq = "dad_"+adDetailId;
            }else if(uploadType.equals("salesEngImg")){
            	adDetailId = "sale_img_"+width+"x"+height;
            	defineAdSeq = "dad_"+adDetailId;
            }
            if(StringUtils.isNotBlank(adDetailId) && StringUtils.isNotBlank(defineAdSeq)){
            	ImageIO.write(image, fileExtensionName, new File(saveImgPath));
            	if(type.equals("add")){
            		adAddAction.saveAdDetail(saveImgPath,adDetailId,"adp_201809270001",defineAdSeq);	
            	}else if(type.equals("edit")){
            		adEditAction.saveAdDetail(saveImgPath,adDetailId,"adp_201809270001",defineAdSeq);	
            	}
            }
            bis.close();
		}
	}
	
	
	
	
	
	public IPfpCatalogService getPfpCatalogService() {
		return pfpCatalogService;
	}

	public void setPfpCatalogService(IPfpCatalogService pfpCatalogService) {
		this.pfpCatalogService = pfpCatalogService;
	}

	public List<PfpCatalog> getAlex() {
		return alex;
	}

	public void setAlex(List<PfpCatalog> alex) {
		this.alex = alex;
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
}
