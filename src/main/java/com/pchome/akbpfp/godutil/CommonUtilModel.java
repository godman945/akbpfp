package com.pchome.akbpfp.godutil;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.pchome.akbpfp.struts2.BaseCookieAction;

public class CommonUtilModel extends BaseCookieAction{

    private static final long serialVersionUID = 1L;
    //正規表示
    private Pattern pattern = Pattern.compile("[0-9]+");
    //command line stringbuffer
    private StringBuffer stringBuffer = new StringBuffer();
    private String result = "";
    
    /**
     * 使用mozJpeg進行壓縮
     * */
    public synchronized void  mozJpegCompression(String filePath) throws Exception{
    	try {
    		log.info(">>>>>> start mozJpeg compression:"+filePath);
        	File file = new File(filePath);
        	log.info(">>>>>> start mozJpeg compression file:"+file.length());
        	if(file.exists()) {
        		Process process = null;
        		stringBuffer.setLength(0);
    			stringBuffer.append(" /opt/mozjpeg/bin/cjpeg  -quality 75 -tune-ms-ssim   -quant-table 0  ").append(file.getAbsolutePath()).append(" > ").append(file.getAbsolutePath().replace(file.getName(), "")).append(file.getName().replace(".jpg", "[PCHOME_RESIZE].jpg"));
    			log.info(">>>>>>>>>>>4:"+stringBuffer.toString());
    			
    			process = Runtime.getRuntime().exec(new String[] { "bash", "-c", stringBuffer.toString()  });
    			result = IOUtils.toString(process.getInputStream(), "UTF-8");
    			stringBuffer.setLength(0);
    			stringBuffer.append(" mv ").append(file.getAbsolutePath().replace(file.getName(), "")).append(file.getName().replace(".jpg", "[PCHOME_RESIZE].jpg")).append(" ").append(file.getAbsolutePath());
    			log.info(">>>>>>>>>>>5:"+stringBuffer.toString());
    			process = Runtime.getRuntime().exec(new String[] { "bash", "-c", stringBuffer.toString()  });
    			result = IOUtils.toString(process.getInputStream(), "UTF-8");
        	}else {
        		log.info(">>>>>> file not exist:"+filePath);
        	}
        	log.info(">>>>>> end mozJpeg compression");
    	}catch(Exception e) {
    		log.error(e.getMessage());
    	}
    	
    }
    
	/**
	 * 使用File寫入圖片
	 */
	public String  writeImg(File originalImgFile,String userImgPath,String custimerInfoid,String date,String adSeq,String fileType) throws Exception{
		log.info(">>>>>> start write img: [originalImgFile:"+originalImgFile+"]"+"[userImgPath:"+userImgPath+"]"+"[fileType:"+fileType+"]");
		if(fileType.toUpperCase().equals("GIF")) {
			FileUtils.copyFile(originalImgFile, new File(userImgPath+custimerInfoid+"/"+date+"/original/"+adSeq+".gif"));
			FileUtils.copyFile(originalImgFile, new File(userImgPath+custimerInfoid+"/"+date+"/temporal/"+adSeq+".gif"));
		}else {
			
			FileOutputStream out = new FileOutputStream(new File(userImgPath+custimerInfoid+"/"+date+"/original/"+adSeq+".jpg"));
			InputStream input = new FileInputStream(originalImgFile);
			byte[] byt = new byte[input.available()];
			input.read(byt);
			out.write(byt);
			FileUtils.copyFile(new File(userImgPath+custimerInfoid+"/"+date+"/original/"+adSeq+".jpg"), new File(userImgPath+custimerInfoid+"/"+date+"/temporal/"+adSeq+".jpg"));
			//進行壓縮
	        mozJpegCompression(userImgPath+custimerInfoid+"/"+date+"/original/"+adSeq+".jpg");
		}
	    return "img\\"+userImgPath+custimerInfoid+"\\"+date+"\\"+adSeq+"." + fileType;
	}
	
	/**
	 * 使用stream寫入圖片
	 * */
	public synchronized void writeImgByStream(ByteArrayInputStream imageStream,String fileExtensionName,String outPath,String filename) throws Exception{
		log.info(">>>>>>start write img: [outPath:"+outPath+"]"+"[fileExtensionName:"+fileExtensionName+"]"+"[filename:"+filename+"]");
		if(fileExtensionName.toUpperCase().equals("PNG") || fileExtensionName.toUpperCase().equals("JPG") || fileExtensionName.toUpperCase().equals("JPEG")) {
			File file = new File(outPath);
			if(!file.exists()) {
				file.mkdirs();
			}
			FileOutputStream output = new FileOutputStream(new File(outPath+filename+"."+fileExtensionName));
			output.write(IOUtils.toByteArray(imageStream));
			output.close();
			//複製至備份區
			FileUtils.copyFile(new File(outPath+filename+"."+fileExtensionName), new File(outPath.replace("original", "temporal")+filename+"."+fileExtensionName));
			//針對original路徑圖片進行mozJpeg壓縮 temporal中保存原圖檔不需壓縮
			if(outPath.contains("original")) {
				mozJpegCompression(outPath+filename+"."+fileExtensionName);
			}
		}else if(fileExtensionName.toUpperCase().equals("GIF")) {
			File file = new File(outPath);
			if(!file.exists()) {
				file.mkdirs();
			}
			FileOutputStream output = new FileOutputStream(new File(outPath+filename+"."+fileExtensionName));
			output.write(IOUtils.toByteArray(imageStream));
			output.close();
			//複製至備份區
			FileUtils.copyFile(new File(outPath+filename+"."+fileExtensionName), new File(outPath.replace("original", "temporal")+filename+"."+fileExtensionName));
		}
		log.info(">>>>>>end write img");
	}
	
	/**
	 * 刪除暫存圖片
	 * */
	public void deleteTemporalImg(String userImgPath, String custimerInfoid, String date, String adSeq) throws Exception {
		log.info("開始刪除暫存圖片:" + adSeq + ".jpg");
		File folder = new File(userImgPath + custimerInfoid + "/" + date + "/temporal/");
		String[] list = folder.list();
		for (int i = 0; i < list.length; i++) {
			if (!list[i].equals(adSeq + ".jpg")) {
				File file = new File(userImgPath + custimerInfoid + "/" + date + "/temporal/" + list[i]);
				file.delete();
			}
		}
	}

	/**
	 * 刪除全部暫存圖片
	 * */
	public void deleteAllTemporalImg(String userImgPath, String custimerInfoid, String date) throws Exception {
		log.info("開始刪除全部暫存圖片");
		File folder = new File(userImgPath + custimerInfoid + "/" + date + "/temporal/");
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
	    ImageVO imageVO = new ImageVO();
	    for(int i = 0; i < list.length; i++){
			File file = new File(userImgPath+custimerInfoid+"/"+date+"/temporal/"+list[i]);
			if(file.getName().indexOf(adSeq) != -1){
				String imgType = file.getName().substring(file.getName().indexOf(".") + 1);
			    ImageInputStream stream = new FileImageInputStream(file);
	            Iterator<ImageReader> readers = ImageIO.getImageReaders(stream);
	            if (readers.hasNext()) {
	                ImageReader reader = readers.next();
	                reader.setInput(stream, true);
	    		    imageVO.setImgWidth(String.valueOf(reader.getWidth(0)));
	    		    imageVO.setImgHeight(String.valueOf(reader.getHeight(0)));
	             }
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
	    
	    //覆寫檔案至original
	    saveOriginalFile(userImgPath+custimerInfoid+"/"+date+"/temporal/"+adSeq);
	    
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
		    indexHtmFilePath = indexHtmFilePath.replaceAll("\\\\", "/");
	    	imageVO.setImgPath(indexHtmFilePath.substring(0, indexHtmFilePath.lastIndexOf("/")) + "/index.htm");
	    }
	    
	    return imageVO;
	}
	
	private String getIndexHtmlPath(String path) {
		String indexPath = "";

		File checkFlie = new File(path);
		File[] files = checkFlie.listFiles(); // 獲取資料夾下面的所有檔
		for (File f : files) {
			// 判斷是否為資料夾
			if (f.isDirectory()) {
				indexPath = getIndexHtmlPath(f.getPath()); // 如果是資料夾，檢查該資料夾內檔案
			} else {
				if (f.getPath().indexOf("index.htm") != -1) {
					indexPath = f.getPath();
					break;
				}
			}
		}

		return indexPath;
	}
	
	private void saveOriginalFile(String path) throws IOException{
		
		String originalPath = path.replace("temporal", "original");
		
		//判斷路徑是否存在,不存在則創建文件路徑
		File pathFile = new File(originalPath);
		if(!pathFile.exists()){
			pathFile.mkdirs();
		}
		
		File temporalFlie = new File(path);
		File[] files = temporalFlie.listFiles(); //獲取資料夾下面的所有檔
		for (File f : files) {
			//判斷是否為資料夾
			if (f.isDirectory()) {
				String filePath = f.getPath();
				filePath.replace("temporal", "original");
				
				File disectory = new File(filePath);
				if(!disectory.exists()){
					disectory.mkdirs();
				}
				
				saveOriginalFile(f.getPath()); //如果是資料夾，檢查該資料夾內檔案
			} else {
				InputStream input = null;
				OutputStream output = null;
				
				String newFilePath = f.getPath();
				newFilePath = newFilePath.replace("temporal", "original");
				
				try{
					input = new FileInputStream(f);
		            output = new FileOutputStream(new File(newFilePath));        
		            byte[] buf = new byte[1024];        
		            int bytesRead;        
		            while ((bytesRead = input.read(buf)) > 0) {
		               output.write(buf, 0, bytesRead);
		            }
					
		            input.close();
			        output.close();
				} catch(Exception e){
					input.close();
			        output.close();
				} finally {
			        input.close();
			        output.close();
			    }
			}
		}
	}
	
	public String getDecimalFormat1(String data) throws Exception {
		// for (EnumCommon enumData : EnumCommon.values()) {
		// if(data.getClass().getName().equals(enumData.getStatusDesc())){
		// System.out.println(enumData.name());
		// String methodName = "get"+enumData.name();
		// CommonDataModel commonDataModel = new CommonDataModel();
		// commonDataModel.getClass().getMethod(methodName).invoke(commonDataModel);
		//// CommonDataModel commonDataModel = new CommonDataModel();
		//// commonDataModel.getClass().getMethod(a);
		//// Method method = c.getDeclaredMethod ("method name", parameterTypes)
		//// method.invoke (objectToInvokeOn, params)
		// }
		// }
		if (!pattern.matcher(data).matches()) {
			return null;
		}
		return null;
	}

    /**
     * 檢查有無資料夾，沒有則新建資料夾
     * @param folderPath 資料夾路徑
     */
	public static void checkFolderCreation(String folderPath) {
		File folder = new File(folderPath);
		if (!folder.exists()) {
			folder.mkdirs();
		}
	}

}
