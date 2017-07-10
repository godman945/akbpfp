package com.pchome.akbpfp.godutil;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.pchome.akbpfp.struts2.BaseCookieAction;

public class CommonUtilModel extends BaseCookieAction{

    private static final long serialVersionUID = 1L;
    //保留小數點格式
    DecimalFormat decimalFormat1 = new DecimalFormat("#,##0.00");
    //不保留小數點格式
    DecimalFormat decimalFormat2 = new DecimalFormat("###,###");
    //正規表示
    Pattern pattern = Pattern.compile("[0-9]+");
    //時間格式取年月日
    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");

    	/**
	 * 輸出圖片
	 * */
	public String  writeImg(File originalImgFile,String userImgPath,String custimerInfoid,String date,String adSeq,String fileType) throws Exception{
	    log.info("開始處理圖片:"+adSeq);
	    //Date date2 = new Date();
	    /*BufferedImage bufferedImage = null;
	    if("JPG".equals(fileType.toUpperCase()) || "PNG".equals(fileType.toUpperCase())){
	    	bufferedImage = ImageIO.read(originalImgFile);
	    	int w = bufferedImage.getWidth();
	    	int h = bufferedImage.getHeight();
	    	BufferedImage newImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
	    	int[] rgb = bufferedImage.getRGB(0, 0, w, h, null, 0, w);
	    	newImage.setRGB(0, 0, w, h, rgb, 0, w);
	    	//SimpleDateFormat sdf = new SimpleDateFormat("HHmmssSSS");
	    	ImageIO.write(newImage, fileType, new File(userImgPath+custimerInfoid+"/"+date+"/original/"+adSeq+"." + fileType));
	    	ImageIO.write(newImage, fileType, new File(userImgPath+custimerInfoid+"/"+date+"/temporal/"+adSeq+"." + fileType));
	    } else if("GIF".equals(fileType.toUpperCase())){
            File file1 = new File(userImgPath+custimerInfoid+"/"+date+"/original/"+adSeq+"." + fileType);
            File file2 = new File(userImgPath+custimerInfoid+"/"+date+"/temporal/"+adSeq+"." + fileType);
            FileOutputStream output1 = new FileOutputStream(file1);
            FileOutputStream output2 = new FileOutputStream(file2);
            InputStream input = new FileInputStream(originalImgFile);
            byte[] byt = new byte[input.available()];
            input.read(byt);
            output1.write(byt);
            output2.write(byt);
            
            input.close();
            output1.close();
            output2.close();
	    }*/
	    
	    //2017.01.04 圖片失真，暫時先用gif處理方式
	    
	    File file1 = new File(userImgPath+custimerInfoid+"/"+date+"/original/"+adSeq+"." + fileType);
        File file2 = new File(userImgPath+custimerInfoid+"/"+date+"/temporal/"+adSeq+"." + fileType);
        FileOutputStream output1 = new FileOutputStream(file1);
        FileOutputStream output2 = new FileOutputStream(file2);
        InputStream input = new FileInputStream(originalImgFile);
        byte[] byt = new byte[input.available()];
        input.read(byt);
        output1.write(byt);
        output2.write(byt);
        
        input.close();
        output1.close();
        output2.close();
	    
        if("JPG".equals(fileType.toUpperCase())){
        	log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>   squeezeJPG start ");
        	this.squeezeJPG(userImgPath+custimerInfoid+"/"+date+"/original/"+adSeq+"." + fileType);
        	this.squeezeJPG(userImgPath+custimerInfoid+"/"+date+"/temporal/"+adSeq+"." + fileType);
        } else if("PNG".equals(fileType.toUpperCase())){
        	log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>   squeezePNG start ");
        	this.squeezePNG(userImgPath+custimerInfoid+"/"+date+"/original/"+adSeq+"." + fileType);
        	this.squeezePNG(userImgPath+custimerInfoid+"/"+date+"/temporal/"+adSeq+"." + fileType);
        }
        
	    return "img\\"+userImgPath+custimerInfoid+"\\"+date+"\\"+adSeq+"." + fileType;
	}

	/**
	 * 刪除暫存圖片
	 * */
	public void deleteTemporalImg(String userImgPath,String custimerInfoid,String date,String adSeq) throws Exception{
	    log.info("開始刪除暫存圖片:"+adSeq+".jpg");
	    File folder = new File(userImgPath+custimerInfoid+"/"+date+"/temporal/");
            String[] list = folder.list();
            for(int i = 0; i < list.length; i++){
        	if(!list[i].equals(adSeq+".jpg")){
        	    File file = new File(userImgPath+custimerInfoid+"/"+date+"/temporal/"+list[i]);
        	    file.delete();
        	}
            }
	}

	/**
	 * 壓縮JPG圖檔
	 * */
	private void squeezeJPG(String pathFile){
	    try {
	    	Runtime rt = Runtime.getRuntime();
	    	//Process proc = rt.exec("/usr/bin/jpegoptim " + pathFile);
	    	Process proc = rt.exec("convert -verbose " + pathFile + " -strip -quality 95 " + pathFile);
		} catch (Exception e) {
			log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>   squeezeJPG error ");
			log.info(e);

			e.printStackTrace();
		}
	}
	
	/**
	 * 壓縮PNG圖檔
	 * */
	private void squeezePNG(String pathFile){
	    try {
	    	Runtime rt = Runtime.getRuntime();
	    	//Process proc = rt.exec("/usr/bin/optipng " + pathFile);
	    	Process proc = rt.exec("convert -verbose " + pathFile + " -strip -quality 95 " + pathFile);
		} catch (Exception e) {
			log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>   squeezePNG error ");
			log.info(e);
			e.printStackTrace();
		}
	}

	/**
	 * 刪除全部暫存圖片
	 * */
	public void deleteAllTemporalImg(String userImgPath,String custimerInfoid,String date) throws Exception{
	    log.info("開始刪除全部暫存圖片");
	    File folder = new File(userImgPath+custimerInfoid+"/"+date+"/temporal/");
           /* String[] list = folder.list();
            for(int i = 0; i < list.length; i++){
        	File file = new File(userImgPath+custimerInfoid+"/"+date+"/temporal/"+list[i]);
        	file.delete();
            }*/
	    File[] files = folder.listFiles();
        for (int i = 0; i < files.length; i++) {
            deleteAll(files[i]);
        }
	}

	public void deleteAll(File path) {
        if (!path.exists()) {
            return;
        }
        if (path.isFile()) {
            path.delete();
            return;
        }
        File[] files = path.listFiles();
        for (int i = 0; i < files.length; i++) {
            deleteAll(files[i]);
        }
        path.delete();
    }

	/**
	 * 1.建立此次上傳圖檔
	 * 2.進行縮圖
	 * */
	public ImageVO createAdImg(String userImgPath,String custimerInfoid,String date,String adSeq) throws Exception{
	    log.info("開始產生廣告圖片:"+adSeq);
	    log.info(">>>>>"+userImgPath+custimerInfoid+"/"+date+"/temporal/");
	    File folder = new File(userImgPath+custimerInfoid+"/"+date+"/temporal/");
	    String[] list = folder.list();
	    String imgUploadPath= "";
	    ImageVO imageVO = new ImageVO();
	    for(int i = 0; i < list.length; i++){
		File file = new File(userImgPath+custimerInfoid+"/"+date+"/temporal/"+list[i]);
		if(file.getName().indexOf(adSeq) != -1){
		//if(adSeq.equals(file.getName().substring(0,file.getName().indexOf(".")))){
			String imgType = file.getName().substring(file.getName().indexOf(".") + 1);
			BufferedImage bufferedImage = ImageIO.read(file);
		    imageVO.setImgWidth(String.valueOf(bufferedImage.getWidth()));
		    imageVO.setImgHeight(String.valueOf(bufferedImage.getHeight()));
		 // 2015.08.27  先把產生縮圖註解掉 by tim
		    /*int type = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();
		    BufferedImage resizedImage = new BufferedImage(90, 90, type);
		    Graphics2D graphics2D = resizedImage.createGraphics();
		    graphics2D.drawImage(bufferedImage, 0, 0, 90, 90, Color.WHITE,null);
		    graphics2D.dispose();
		    ImageIO.write(resizedImage, "jpg", new File(userImgPath+custimerInfoid+"/"+date+"/"+adSeq+".jpg"));*/
		    imageVO.setImgPath(userImgPath+custimerInfoid+"\\"+date+"\\original\\"+adSeq+"." + imgType);

		}
	    }
	    return imageVO;
	}

	public ImageVO createAdHtml5(String userImgPath,String custimerInfoid,String date,String adSeq) throws Exception{
		log.info("開始產生廣告圖片(html5):"+adSeq);
	    log.info(">>>>>"+userImgPath+custimerInfoid+"/"+date+"/temporal/");
	    ImageVO imageVO = new ImageVO();
	    
	    File folder = new File(getIndexHtmlPath(userImgPath+custimerInfoid+"/"+date+"/temporal/"+adSeq));
	    if(folder.exists()){
	    	
	    	String imgWidth ="0";
		    String imgHeight ="0";
	    	
	    	Document doc = Jsoup.parse(folder, "UTF-8");
			String docHtml = doc.html();
	    	
			Elements metaTag = doc.select("meta[name=ad.size][content]");
			if(!metaTag.isEmpty()){
				String content = metaTag.attr("content");
				content = content.replaceAll(";", "");
				
				String[] contentArray = content.split(",");
				try {
					for(String size:contentArray){
						if(size.indexOf("width=") != -1){
							imgWidth = size.replaceAll("width=", "").trim();
						}
						if(size.indexOf("height=") != -1){
							imgHeight = size.replaceAll("height=", "").trim();
						}
					}
					
					//驗證長、寬是否為數字
					Integer intWidth = Integer.parseInt(imgWidth);
					Integer intHeight = Integer.parseInt(imgHeight);
				} catch(Exception error) {
					imgWidth = "0";
					imgHeight = "0";
				}
			}
			
			imageVO.setImgWidth(imgWidth);
		    imageVO.setImgHeight(imgHeight);
		    
		    String indexHtmFilePath = folder.getPath().replaceAll("/", "\\\\\\\\");
		    indexHtmFilePath = indexHtmFilePath.replace("temporal", "original");
		    indexHtmFilePath = indexHtmFilePath.replaceAll("\\\\\\\\", "/");
	    	imageVO.setImgPath(indexHtmFilePath.substring(0, indexHtmFilePath.lastIndexOf("/")) + "/index.htm");
	    }
	    
	    return imageVO;
	}
	
	private String getIndexHtmlPath(String path){
		String indexPath = "";
		
		File checkFlie = new File(path);
		File[] files = checkFlie.listFiles(); //獲取資料夾下面的所有檔
		for (File f : files) {
			//判斷是否為資料夾
			if (f.isDirectory()) {
				indexPath = getIndexHtmlPath(f.getPath()); //如果是資料夾，檢查該資料夾內檔案
			} else {
				if(f.getPath().indexOf("index.htm") != -1){
					indexPath = f.getPath();
					break;
				}
			}
		}
		
		return indexPath;
	}
	
	
    public String getDecimalFormat1(String data) throws Exception{
//	for (EnumCommon enumData : EnumCommon.values()) {
//	    if(data.getClass().getName().equals(enumData.getStatusDesc())){
//		System.out.println(enumData.name());
//		String methodName = "get"+enumData.name();
//		CommonDataModel commonDataModel = new CommonDataModel();
//		commonDataModel.getClass().getMethod(methodName).invoke(commonDataModel);
////		CommonDataModel commonDataModel = new CommonDataModel();
////		commonDataModel.getClass().getMethod(a);
////		Method  method = c.getDeclaredMethod ("method name", parameterTypes)
////		method.invoke (objectToInvokeOn, params)
//	    }
//	}
	if(!pattern.matcher(data).matches()){
	    return null;
	}
	return null;
    }



}
