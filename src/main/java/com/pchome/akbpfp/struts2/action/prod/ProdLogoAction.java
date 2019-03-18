package com.pchome.akbpfp.struts2.action.prod;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.pchome.akbpfp.db.pojo.PfpCatalogLogo;
import com.pchome.akbpfp.db.pojo.PfpCatalogLogoDetail;
import com.pchome.akbpfp.db.service.catalog.prod.IPfpCatalogLogoDetailService;
import com.pchome.akbpfp.db.service.catalog.prod.IPfpCatalogLogoService;
import com.pchome.akbpfp.db.service.sequence.ISequenceService;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.sequence.EnumSequenceTableName;
import com.pchome.utils.ImgUtil;

public class ProdLogoAction extends BaseCookieAction{

	private static final long serialVersionUID = 1L;
	private ISequenceService sequenceService;
	private IPfpCatalogLogoDetailService pfpCatalogLogoDetailService;
	private IPfpCatalogLogoService pfpCatalogLogoService;
	private String result;
	private String logoDataObj;
	private String photoDbPathNew;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	private final int square_width = 250;
	private final int square_height = 250;
	private final int rectangle_width = 1000;
	private final int rectangle_height = 250;
	private String photoClonePath;
	private String deleteLogoType;
	
	
	/**
	 * LOGO初始化畫面
	 * 圖片狀態 0:審核中 1:審核成功 2:上傳成功
	 * */
	public String logoInit() throws Exception{
		List<PfpCatalogLogo> pfpCatalogLogoList = pfpCatalogLogoService.findCatalogLogoByCustomerInfoId(super.getCustomer_info_id());
		JSONObject imgJson = new JSONObject();
		JSONObject rectangle = new JSONObject();
		rectangle.put("base64", "");
		rectangle.put("file_name", "");
		rectangle.put("status", "");
		rectangle.put("reject_reason", "");
		JSONObject square = new JSONObject();
		square.put("base64", "");
		square.put("file_name", "");
		square.put("status", "");
		square.put("reject_reason", "");
		imgJson.put("square", square);
		imgJson.put("rectangle", rectangle);
		
		if(pfpCatalogLogoList != null){
			for (PfpCatalogLogo pfpCatalogLogo : pfpCatalogLogoList) {
				String path = photoClonePath+pfpCatalogLogo.getCatalogLogoUrl();
				File file = new File(path);
				if(file.exists()){
					String fileExtensionName = path.split("\\.")[1];
					String imgBase64 = imgBase64(file,fileExtensionName);
					if(pfpCatalogLogo.getCatalogLogoType().equals("0")){
						JSONObject data = imgJson.getJSONObject("square");
						data.put("base64", imgBase64);
						data.put("file_name", file.getName().split("\\.")[0]);
						data.put("file_extension_name", fileExtensionName);
						data.put("status", pfpCatalogLogo.getStatus());
						data.put("reject_reason", pfpCatalogLogo.getLogoVerifyRejectReason());
					}
					if(pfpCatalogLogo.getCatalogLogoType().equals("1")){
						JSONObject data = imgJson.getJSONObject("rectangle");
						data.put("base64", imgBase64);
						data.put("file_name", file.getName().split("\\.")[0]);
						data.put("file_extension_name", fileExtensionName);
						data.put("status", pfpCatalogLogo.getStatus());
						data.put("reject_reason", pfpCatalogLogo.getLogoVerifyRejectReason());
					}
				}
			}
		}
		logoDataObj = imgJson.toString();
		return SUCCESS;
	}
	
	
	public String saveLogoAjax() throws Exception{
		Date date = new Date();
		List<PfpCatalogLogo> pfpCatalogLogoList = pfpCatalogLogoService.findCatalogLogoByCustomerInfoId(super.getCustomer_info_id());
		StringBuffer saveImgPathBuffer = new StringBuffer();
		saveImgPathBuffer.append(photoClonePath).append("img/").append("user/").append(super.getCustomer_info_id()).append("/catalog/").append("logo/");
		if(pfpCatalogLogoList == null){
			JSONObject imgJson = new JSONObject(logoDataObj);
			Iterator<String> keys = imgJson.keys();
			while(keys.hasNext()) {
			    String logoType = keys.next();
			    JSONObject imgData =  (JSONObject) imgJson.get(logoType);
			    System.out.println(imgData);
			    int status = imgData.getInt("status");
			    String fileName = imgData.getString("file_name");
			    String base64Img = imgData.getString("base64").split(",")[1];
			    String fileExtensionName = imgData.getString("file_extension_name");
			    if(status == 1){
			    	continue;
			    }
			    BufferedImage image = null;
		        byte[] imageByte = Base64.decodeBase64(base64Img.getBytes());
	            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
	            image = ImageIO.read(bis);
			    //1.寫入圖片
	            String logoSeq = sequenceService.getId(EnumSequenceTableName.PFP_CATALOG_LOG_SEQ, "_");
	            File path = new File(saveImgPathBuffer.toString());
	            log.info(">>>>>>save path 1:"+path);
	            if(!path.exists()){
	            	path.mkdirs();
	            }
	            String writeImgPath = path+"/"+fileName+"."+fileExtensionName.replace("image/", "");
	            String dbImgPath = "img/user/"+super.getCustomer_info_id()+"/catalog/logo/"+fileName+"."+fileExtensionName.replace("image/", "");
	            log.info(">>>>>>writeImgPath:"+writeImgPath);
	            log.info(">>>>>>dbImgPath:"+dbImgPath);
	           
	            PfpCatalogLogo pfpCatalogLogo = new PfpCatalogLogo();
	            pfpCatalogLogo.setCatalogLogoSeq(logoSeq);
	            int imgWidth = 0;
				int imgHeight = 0;
	            if(logoType.equals("rectangle")){
	            	 pfpCatalogLogo.setCatalogLogoType("1");
	            	 imgWidth = rectangle_width;
	            	 imgHeight = rectangle_height;
	            }
	            if(logoType.equals("square")){
	            	 pfpCatalogLogo.setCatalogLogoType("0");
	            	 imgWidth = square_width;
	            	 imgHeight = square_height;
	            }
	            image = ImgUtil.getInstance().imgResize(image,imgWidth,imgHeight);
	            ImageIO.write(image, fileExtensionName.replace("image/", ""), new File(writeImgPath));
	            
	            pfpCatalogLogo.setPfpCustomerInfoId(super.getCustomer_info_id());
	            pfpCatalogLogo.setCatalogLogoUrl(dbImgPath);
	            pfpCatalogLogo.setCreateDate(date);
	            pfpCatalogLogo.setUpdateDate(date);
	            pfpCatalogLogo.setLogoSendVerifyTime(date);
	            pfpCatalogLogo.setStatus("0");
	            pfpCatalogLogoService.saveOrUpdate(pfpCatalogLogo);
			    //2.推薦顏色
	            Map<String, Integer> colormap = color(image);
	            for (Entry<String, Integer> data : colormap.entrySet()) {
	            	PfpCatalogLogoDetail pfpCatalogLogoDetail = new PfpCatalogLogoDetail();
	            	pfpCatalogLogoDetail.setCatalogLogoHexColor(data.getKey());
	            	pfpCatalogLogoDetail.setCreateDate(date);
	            	pfpCatalogLogoDetail.setUpdateDate(date);
	            	pfpCatalogLogoDetail.setPfpCatalogLogo(pfpCatalogLogo);
	            	pfpCatalogLogoDetailService.saveOrUpdate(pfpCatalogLogoDetail);
				}
			}
		}else{
			JSONObject imgJson = new JSONObject(logoDataObj);
			Iterator<String> keys = imgJson.keys();
			while(keys.hasNext()) {
			    String logoType = keys.next();
			    JSONObject imgData =  (JSONObject) imgJson.get(logoType);
			    int status = imgData.getInt("status");
			   
			    //狀態為審核中不可變更
			    if(status != -1){
			    	continue;
			    }
			    
			    String fileName = imgData.getString("file_name");
			    String base64Img = imgData.getString("base64").split(",")[1];
			    String fileExtensionName = imgData.getString("file_extension_name");
			    
			    PfpCatalogLogo pfpCatalogLogo = null;
			    int imgWidth = 0;
				int imgHeight = 0;
				String catalogLogoType= "";
			    if(logoType.equals("rectangle")){
			    	pfpCatalogLogo = pfpCatalogLogoService.findCatalogLogoByLogoType(super.getCustomer_info_id(),"1");
			    	imgWidth = rectangle_width;
	            	imgHeight = rectangle_height;
	            	catalogLogoType ="1";
			    }else if(logoType.equals("square")){
	            	pfpCatalogLogo = pfpCatalogLogoService.findCatalogLogoByLogoType(super.getCustomer_info_id(),"0");
	            	imgWidth = square_width;
	            	imgHeight = square_height;
	            	catalogLogoType ="0";
			    }
			    
			    
			    if(pfpCatalogLogo == null){
				    BufferedImage image = null;
			        byte[] imageByte = Base64.decodeBase64(base64Img.getBytes());
		            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
		            image = ImageIO.read(bis);
				    //1.寫入圖片
		            String logoSeq = sequenceService.getId(EnumSequenceTableName.PFP_CATALOG_LOG_SEQ, "_");
		            File path = new File(saveImgPathBuffer.toString());
		            log.info(">>>>>>save path 1:"+path);
		            if(!path.exists()){
		            	path.mkdirs();
		            }
		            String writeImgPath = path+"/"+fileName+"."+fileExtensionName.replace("image/", "");
		            String dbImgPath = "img/user/"+super.getCustomer_info_id()+"/catalog/logo/"+fileName+"."+fileExtensionName.replace("image/", "");
		            log.info(">>>>>>writeImgPath:"+writeImgPath);
		            log.info(">>>>>>dbImgPath:"+dbImgPath);
		            PfpCatalogLogo pfpCatalogLogoSave = new PfpCatalogLogo();
		            pfpCatalogLogoSave.setCatalogLogoSeq(logoSeq);
		            image = ImgUtil.getInstance().imgResize(image,imgWidth,imgHeight);
		            ImageIO.write(image, fileExtensionName.replace("image/", ""), new File(writeImgPath));
		            pfpCatalogLogoSave.setPfpCustomerInfoId(super.getCustomer_info_id());
		            pfpCatalogLogoSave.setCatalogLogoUrl(dbImgPath);
		            pfpCatalogLogoSave.setCreateDate(date);
		            pfpCatalogLogoSave.setUpdateDate(date);
		            pfpCatalogLogoSave.setLogoSendVerifyTime(date);
		            pfpCatalogLogoSave.setStatus("0");
		            pfpCatalogLogoSave.setCatalogLogoType(catalogLogoType);
		            pfpCatalogLogoService.saveOrUpdate(pfpCatalogLogoSave);
				    //2.推薦顏色
		            Map<String, Integer> colormap = color(image);
		            for (Entry<String, Integer> data : colormap.entrySet()) {
		            	PfpCatalogLogoDetail pfpCatalogLogoDetail = new PfpCatalogLogoDetail();
		            	pfpCatalogLogoDetail.setCatalogLogoHexColor(data.getKey());
		            	pfpCatalogLogoDetail.setCreateDate(date);
		            	pfpCatalogLogoDetail.setUpdateDate(date);
		            	pfpCatalogLogoDetail.setPfpCatalogLogo(pfpCatalogLogoSave);
		            	pfpCatalogLogoDetailService.saveOrUpdate(pfpCatalogLogoDetail);
					}
			    }else{
					String path = photoClonePath+pfpCatalogLogo.getCatalogLogoUrl();
				    BufferedImage image = null;
			        byte[] imageByte = Base64.decodeBase64(base64Img.getBytes());
		            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
		            image = ImageIO.read(bis);
		            image = ImgUtil.getInstance().imgResize(image,imgWidth,imgHeight);
		            //1.寫入圖片
		            log.info(">>>>>>save path 2:"+path);
		            ImageIO.write(image, fileExtensionName.replace("image/", ""), new File(path));
		            //2.更新審核狀態
		            pfpCatalogLogo.setStatus("0");
		            pfpCatalogLogo.setLogoSendVerifyTime(date);
		            pfpCatalogLogoService.saveOrUpdate(pfpCatalogLogo);
		            //推薦顏色
		            Map<String, Integer> colormap = color(image);
		            List colorKeys = new ArrayList(colormap.keySet());
		            Set<PfpCatalogLogoDetail> set =  pfpCatalogLogo.getPfpCatalogLogoDetails();
		            int index = 0;
		            for (PfpCatalogLogoDetail pfpCatalogLogoDetail : set) {
		            	pfpCatalogLogoDetail.setCatalogLogoHexColor(colorKeys.get(index).toString());
		            	pfpCatalogLogoDetail.setUpdateDate(date);
		            	pfpCatalogLogoDetailService.saveOrUpdate(pfpCatalogLogoDetail);
		            	index = index + 1;
					}
			    }
			}
		}
		result = "success";
		return SUCCESS;
	}
	
	
	public String deleteLogoAjax() throws Exception{
		List<PfpCatalogLogo> pfpCatalogLogoList = pfpCatalogLogoService.findCatalogLogoByCustomerInfoId(super.getCustomer_info_id());
		for (PfpCatalogLogo pfpCatalogLogo : pfpCatalogLogoList) {
			if(pfpCatalogLogo.getCatalogLogoType().equals(deleteLogoType)){
				pfpCatalogLogoService.delete(pfpCatalogLogo);
				break;
			}
		}
		result = "success";
		return SUCCESS;
	}
	
	
	private Map<String, Integer> color(BufferedImage image){
		int w = image.getWidth();
		int h = image.getHeight();
		LinkedHashMap<String, Integer> colormap = new LinkedHashMap<String, Integer>();
		String colorKey = "";
		int colorValue = 0;
		int clr = 0;
		int red = 0;
		int green = 0;
		int blue = 0;

		for (int x = 2; x < w - 2; x += 2) {
			for (int y = 2; y < h - 2; y += 2) {
				clr = image.getRGB(x, y);
				red = (clr & 0x00ff0000) >> 16;
				green = (clr & 0x0000ff00) >> 8;
				blue = clr & 0x000000ff;
				colorKey = String.valueOf(red) + "," + String.valueOf(green) + "," + String.valueOf(blue);

				if (colormap.get(colorKey) == null) {
					colormap.put(colorKey, 1);
				} else {
					colorValue = colormap.get(colorKey);
					colorValue++;
					colormap.put(colorKey, colorValue);

				}
			}
		}

		Comparator<Map.Entry<String, Integer>> valueComparator = new Comparator<Map.Entry<String,Integer>>() {
	        public int compare(Entry<String, Integer> o1,Entry<String, Integer> o2) {
	            return o2.getValue() - o1.getValue();
	        }
	    };
		List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String,Integer>>(colormap.entrySet());
		Collections.sort(list,valueComparator);
		colormap.clear();
		int index = 0;
		for (Entry<String, Integer> entry : list) {
			if(index < 6){
//				colormap.put(entry.getKey(), entry.getValue());
				String array[] = entry.getKey().split(",");
				StringBuffer colorHexCode = new StringBuffer();
				String r = Integer.toHexString(Integer.parseInt(array[0])).toUpperCase();
				String g = Integer.toHexString(Integer.parseInt(array[1])).toUpperCase();
				String b = Integer.toHexString(Integer.parseInt(array[2])).toUpperCase();
				colorHexCode.append("#").append(r.length() == 1 ? "0" + r : r).append(g.length() == 1 ? "0" + g : g).append(b.length() == 1 ? "0" + b : b);
				colormap.put(colorHexCode.toString(), entry.getValue());
				index = index + 1;
			}
			if(index == 6){
				break;
			}
		}
		return colormap;
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
	
	public String getResult() {
		return result;
	}


	public void setResult(String result) {
		this.result = result;
	}


	public String getLogoDataObj() {
		return logoDataObj;
	}


	public void setLogoDataObj(String logoDataObj) {
		this.logoDataObj = logoDataObj;
	}


	public String getPhotoDbPathNew() {
		return photoDbPathNew;
	}


	public void setPhotoDbPathNew(String photoDbPathNew) {
		this.photoDbPathNew = photoDbPathNew;
	}


	public ISequenceService getSequenceService() {
		return sequenceService;
	}


	public void setSequenceService(ISequenceService sequenceService) {
		this.sequenceService = sequenceService;
	}


	public IPfpCatalogLogoDetailService getPfpCatalogLogoDetailService() {
		return pfpCatalogLogoDetailService;
	}


	public void setPfpCatalogLogoDetailService(IPfpCatalogLogoDetailService pfpCatalogLogoDetailService) {
		this.pfpCatalogLogoDetailService = pfpCatalogLogoDetailService;
	}


	public IPfpCatalogLogoService getPfpCatalogLogoService() {
		return pfpCatalogLogoService;
	}


	public void setPfpCatalogLogoService(IPfpCatalogLogoService pfpCatalogLogoService) {
		this.pfpCatalogLogoService = pfpCatalogLogoService;
	}


	public String getPhotoClonePath() {
		return photoClonePath;
	}

	public void setPhotoClonePath(String photoClonePath) {
		this.photoClonePath = photoClonePath;
	}


	public String getDeleteLogoType() {
		return deleteLogoType;
	}


	public void setDeleteLogoType(String deleteLogoType) {
		this.deleteLogoType = deleteLogoType;
	}
	
}

