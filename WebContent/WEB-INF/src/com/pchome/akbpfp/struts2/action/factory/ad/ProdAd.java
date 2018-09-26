package com.pchome.akbpfp.struts2.action.factory.ad;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
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
import org.json.JSONObject;

import com.pchome.akbpfp.db.pojo.PfpAdDetail;
import com.pchome.akbpfp.db.pojo.PfpAdGroup;
import com.pchome.akbpfp.db.pojo.PfpCatalog;
import com.pchome.akbpfp.db.service.catalog.TMP.IPfpCatalogService;
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
	
	private List<PfpCatalog> alex;
	
	
	private AdAddAction adAddAction;
	
	public String AdAdAddInit(AdAddAction adAddAction) throws Exception {
		log.info(">>>>>> process ProdAd");
		System.out.println(adAddAction.getCustomer_info_id());
		alex = pfpCatalogService.getPfpCatalogByCustomerInfoId(adAddAction.getCustomer_info_id());
		System.out.println(alex.size());
		adAddAction.getRequest().setAttribute("alex", alex);
		return "adProdAdd";
	}

	public String doAdAdAdd(AdAddAction adAddAction) throws Exception {
		log.info(">>>>>> process do add prod ad");
		this.adAddAction = adAddAction;
		String adSeq = adAddAction.getSequenceService().getId(EnumSequenceTableName.PFP_AD, "_");
		System.out.println(adSeq);
		System.out.println(adAddAction.getPhotoDbPathNew());
		PfpAdGroup pfpAdGroup = adAddAction.getPfpAdGroup();
		adAddAction.setAdSeq(adSeq);
		adAddAction.setTemplateProductSeq(EnumAdStyle.TMG.getTproSeq());
		adAddAction.setAdStyle("TMG");
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
			}
		}
		Date date = new Date();
		StringBuffer saveImgPathBuffer = new StringBuffer();
		saveImgPathBuffer.append(adAddAction.getPhotoDbPathNew()).append(adAddAction.getCustomer_info_id()).append("/").append(adAddAction.getSdf().format(date)).append("/original/").append(adSeq).append("/");
		JSONObject uploadLogJson = new JSONObject(adAddAction.getUploadLog());
		saveImg(uploadLogJson,"salesEngImg",saveImgPathBuffer);
		JSONObject uploadLogoLogJson = new JSONObject(adAddAction.getUploadLogoLog());
		saveImg(uploadLogoLogJson,"logoImg",saveImgPathBuffer);
		
		return null;
	}

	private void saveImg(JSONObject uploadImgJson,String uploadType,StringBuffer saveImgPathBuffer) throws Exception{
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
            String saveImgPath = saveImgPathBuffer.toString()+fileName+"-"+width+"x"+height+"."+fileExtensionName;
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
            	adAddAction.saveAdDetail(saveImgPath,adDetailId,"adp_201303070003",defineAdSeq);
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

	@Override
	public String adAdEdit(AdEditAction adEditAction) throws Exception {
		
		Set<PfpAdDetail> detailSet = adEditAction.getPfpAd().getPfpAdDetails();
		JSONObject uploadLogJson = new JSONObject();
		JSONObject uploadLogoLogJson = new JSONObject();
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
			
			if(pfpAdDetail.getAdDetailId().indexOf("logo_sale") >= 0){
				uploadLogJson.put(pfpAdDetail.getAdDetailId(), pfpAdDetail.getAdDetailContent());
				continue;
			}
			if(pfpAdDetail.getAdDetailId().indexOf("sale_img") >= 0){
				uploadLogoLogJson.put(pfpAdDetail.getAdDetailId(), pfpAdDetail.getAdDetailContent());
				continue;
			}
		}
		adEditAction.setUploadLogoLog(uploadLogoLogJson.toString());
		adEditAction.setUploadLog(uploadLogJson.toString());
		alex = pfpCatalogService.getPfpCatalogByCustomerInfoId(adEditAction.getCustomer_info_id());
		System.out.println(alex.size());
		adEditAction.getRequest().setAttribute("alex", alex);
		return null;
	}
}
