package com.pchome.akbpfp.struts2.action.ad;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.pchome.akbpfp.struts2.BaseCookieAction;


public class AdUtilAction extends BaseCookieAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// setting data
	private int allowSize = 1024000;	// 最大允許檔案大小
	private String photoPath;
	private String photoTmpPath;

	//recieve data
	private File uploadFile;
	private String ulTmpName;
	private String imgType;
	
	// return data
	private InputStream msg;
	private String result;
	private String imgFile;
	
	
	private String  time;
	public String fileUpload() throws Exception{
	    log.info("alex fileUpload");
		result = "noFile";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		time = sdf.format(date);
		try {
			System.out.println("ulTmpName = " + ulTmpName);
			if(uploadFile != null) {
				InputStream is = new FileInputStream(uploadFile);
				if(uploadFile.length()/1024 > 1024){
				    result = "overSize";
				    return SUCCESS;
				}

				// 存放截圖的暫存目錄
				File iCutPath = new File(photoTmpPath + "cut/");
				if(!iCutPath.exists())		iCutPath.mkdirs();
				iCutPath.setWritable(true);

				// 存放縮圖的暫存目錄
				File iResizePath = new File(photoTmpPath + "resize/");
				if(!iResizePath.exists())		iResizePath.mkdirs();
				iResizePath.setWritable(true);

				// 截圖、縮圖處裡完成的暫存目錄
				File iTmpPath = new File(photoTmpPath);
				if(!iTmpPath.exists())		iTmpPath.mkdirs();
				iTmpPath.setWritable(true);

				// 截圖暫存檔
				File cutFile = new File(photoTmpPath + "cut/", ulTmpName+time+ "." + imgType);
				// 縮圖暫存檔
				File resizeFile = new File(photoTmpPath + "resize/", ulTmpName+time + "." + imgType);
				// 處理完成暫存檔
				File tmpFile = new File(photoTmpPath, ulTmpName+time + "." + imgType);
				
				// 先將圖片存成原圖暫存檔
				OutputStream os = new FileOutputStream(cutFile);
				byte[] buffer = new byte[1024];
				int length = 0;
				while((length = is.read(buffer)) > 0) {
					os.write(buffer, 0, length);
				}
				is.close();
				os.close();

				log.info("cutFile.length() = " + cutFile.length());
				log.info("allowSize = " + allowSize);
				if(cutFile.length() < allowSize) {
					
					// 進行圖片縮放
					if("GIF".equals(imgType.toUpperCase())){
						if(cutFile != null){
							InputStream input = new FileInputStream(cutFile);
							FileOutputStream output1 = new FileOutputStream(tmpFile);
							byte[] byt = new byte[input.available()];
							input.read(byt);
							output1.write(byt);
							output1.close();
							input.close();
							log.info(cutFile.getAbsolutePath());
							cutFile.delete();
						}
					}else {
						// 進行圖片截取
						ImageUtil imgCutUtil = new ImageUtil();
						if(imgCutUtil.scissor(cutFile.getAbsolutePath(), resizeFile.getAbsolutePath()))
							cutFile.delete();
						
						ImageUtil imgResizeUtil = new ImageUtil();
						imgResizeUtil.resizeImage(resizeFile, tmpFile, 90, imgType);	
					}
					imgFile = tmpFile.getAbsolutePath();
					imgFile = imgFile.indexOf("\\") > 0?imgFile.replaceAll("\\\\", "/"):imgFile;
					log.info("imgFile = " + imgFile);

					resizeFile.delete();
					result = "resizeOK";
				} else {
					result = "overSize";
				}
			}

			result = "resizeOK";
			log.info("result = " + result);
		} catch(Exception ex) {
			log.info("Exception(AdUtilAjax.checkUrl) : " + ex.toString());
			result = "resizeErr";
		}
		msg = new ByteArrayInputStream(result.getBytes());
		return SUCCESS;
	}

	public String deleteIMG() {
		String sMSG = "nofile";
		if(StringUtils.isNotEmpty(imgFile)) {
			if(imgFile.indexOf("?") > 0) {
				imgFile = imgFile.substring(0, imgFile.indexOf("?"));
			}
			//System.out.println("imgFile = " + imgFile);
			File delImgFile = new File(imgFile);
			if(delImgFile.exists())		delImgFile.delete();
			sMSG = "delFinish";
		}
		msg = new ByteArrayInputStream(sMSG.toString().getBytes());
		return SUCCESS;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public String getPhotoTmpPath() {
		return photoTmpPath;
	}

	public void setPhotoTmpPath(String photoTmpPath) {
		this.photoTmpPath = photoTmpPath;
	}

	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}

	public void setUlTmpName(String ulTmpName) {
		this.ulTmpName = ulTmpName;
	}

	public String getUlTmpName() {
		return ulTmpName;
	}

	public String getImgType() {
		return imgType;
	}

	public void setImgType(String imgType) {
		this.imgType = imgType;
	}

	public InputStream getMsg() {
		return msg;
	}

	public void setMsg(InputStream msg) {
		this.msg = msg;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public void setImgFile(String imgFile) {
		this.imgFile = imgFile;
	}

	public String getImgFile() {
		return imgFile;
	}

	public String getTime() {
	    return time;
	}

	public void setTime(String time) {
	    this.time = time;
	}
	
}