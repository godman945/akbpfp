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

public class ProdLogoAction extends BaseCookieAction{

	private static final long serialVersionUID = 1L;
	private ISequenceService sequenceService;
	private IPfpCatalogLogoDetailService pfpCatalogLogoDetailService;
	private IPfpCatalogLogoService pfpCatalogLogoService;
	private String result;
	private String logoDataObj;
	private String photoDbPathNew;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	
	
	
	public String logoInit() throws Exception{
		List<PfpCatalogLogo> pfpCatalogLogoList = pfpCatalogLogoService.findCatalogLogoByCustomerInfoId(super.getCustomer_info_id());
		JSONObject imgJson = new JSONObject();
		if(pfpCatalogLogoList != null){
			for (PfpCatalogLogo pfpCatalogLogo : pfpCatalogLogoList) {
				String path = pfpCatalogLogo.getCatalogLogoUrl();
				File file = new File(path);
				if(file.exists()){
					String fileExtensionName = path.split("\\.")[1];
					String imgBase64 = imgBase64(file,fileExtensionName);
					JSONObject data = new JSONObject();
					System.out.println(imgBase64);
					data.put("base64", imgBase64);
					data.put("file_name", file.getName().split("\\.")[0]);
					data.put("file_extension_name", fileExtensionName);
					System.out.println(file.getName().split("\\.")[0]);
					if(pfpCatalogLogo.getCatalogLogoType().equals("0")){
						imgJson.put("square", data);
					}
					if(pfpCatalogLogo.getCatalogLogoType().equals("1")){
						imgJson.put("rectangle", data);
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
		saveImgPathBuffer.append(photoDbPathNew).append(super.getCustomer_info_id()).append("/").append(sdf.format(date)).append("/logo/");
		if(pfpCatalogLogoList == null){
			JSONObject imgJson = new JSONObject(logoDataObj);
			Iterator<String> keys = imgJson.keys();
			while(keys.hasNext()) {
			    String logoType = keys.next();
			    JSONObject imgData =  (JSONObject) imgJson.get(logoType);
			    String fileName = imgData.getString("file_name");
			    String base64Img = imgData.getString("base64").split(",")[1];
			    String fileExtensionName = imgData.getString("file_extension_name");
			    BufferedImage image = null;
		        byte[] imageByte = Base64.decodeBase64(base64Img.getBytes());
	            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
	            image = ImageIO.read(bis);
			    //1.寫入圖片
	            String logoSeq = sequenceService.getId(EnumSequenceTableName.PFP_CATALOG_LOG_SEQ, "_");
	            File path = new File(saveImgPathBuffer.toString()+logoSeq+"/"+fileName);
	            if(!path.exists()){
	            	path.mkdirs();
	            }
	            ImageIO.write(image, fileExtensionName.replace("image/", ""), path);
	            PfpCatalogLogo pfpCatalogLogo = new PfpCatalogLogo();
	            pfpCatalogLogo.setCatalogLogoSeq(logoSeq);
	            if(logoType.equals("rectangle")){
	            	 pfpCatalogLogo.setCatalogLogoType("1");
	            }
	            if(logoType.equals("square")){
	            	 pfpCatalogLogo.setCatalogLogoType("0");
	            }
	            pfpCatalogLogo.setPfpCustomerInfoId(super.getCustomer_info_id());
	            pfpCatalogLogo.setCatalogLogoUrl(path.getPath());
	            pfpCatalogLogo.setCreateDate(date);
	            pfpCatalogLogo.setUpdateDate(date);
	            pfpCatalogLogoService.saveOrUpdate(pfpCatalogLogo);
			    //2.推薦顏色
	            Map<String, Integer> colormap = color(image);
	            for (Entry<String, Integer> entry : colormap.entrySet()) {
	            	PfpCatalogLogoDetail pfpCatalogLogoDetail = new PfpCatalogLogoDetail();
	            	pfpCatalogLogoDetail.setPfpCatalogLogo(pfpCatalogLogo);
	            	pfpCatalogLogoDetail.setCatalogLogoHexColor(entry.getKey());
	            	pfpCatalogLogoDetail.setCreateDate(date);
	            	pfpCatalogLogoDetail.setUpdateDate(date);
	            	pfpCatalogLogoDetailService.saveOrUpdate(pfpCatalogLogoDetail);
				}
			}
		}else{
			JSONObject imgJson = new JSONObject(logoDataObj);
			for (PfpCatalogLogo pfpCatalogLogo : pfpCatalogLogoList) {
				String type = "";
				if(pfpCatalogLogo.getCatalogLogoType().equals("0")){
					type = "square";
				}
				if(pfpCatalogLogo.getCatalogLogoType().equals("1")){
					type = "rectangle";
				}
				
				JSONObject imgData =  (JSONObject) imgJson.get(type);
			    String fileName = imgData.getString("file_name").split("\\.")[0];
			    String base64Img = imgData.getString("base64").split(",")[1];
			    String fileExtensionName = imgData.getString("file_extension_name");
				
				String existfileName = "";
				String path = pfpCatalogLogo.getCatalogLogoUrl();
				File file = new File(path);
				if(file.exists()){
					existfileName = file.getName().split("\\.")[0];
				}
				
				if(StringUtils.isNotBlank(existfileName) && fileName.equals(existfileName)){
					continue;
				}
				
			    BufferedImage image = null;
		        byte[] imageByte = Base64.decodeBase64(base64Img.getBytes());
	            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
	            image = ImageIO.read(bis);
	            //1.寫入圖片
	            ImageIO.write(image, fileExtensionName.replace("image/", ""), new File(path));
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
	
}

