package com.pchome.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CommonUtils {

	protected static final Log log = LogFactory.getLog(CommonUtils.class);
	
	private static CommonUtils instance = new CommonUtils();
	
	public static synchronized CommonUtils getInstance() {
		return instance;
	}
	
	/**
	 * 濾掉攻擊資料庫語法
	 */
	public static String filterSqlInjection(String str) {

		if (str!=null) {
			str = str.trim();
			str = str.replaceAll("=", "");
			str = str.replaceAll(">", "");
			str = str.replaceAll("<", "");
			str = str.replaceAll(";", "");
			str = str.replaceAll("'", "");
			str = str.replaceAll("%", "");
			str = str.replaceAll("#", "");
			str = str.replaceAll("/", "");
			str = str.replaceAll("\\(", "");
			str = str.replaceAll("\\)", "");
			str = str.replaceAll("\\[", "");
			str = str.replaceAll("\\]", "");
			str = str.replaceAll("\\\\", "");
			
		} else {
			str = "";
		}

		return str;
	}

	/*
	 * 取得影像寬高
	 * */
	public Map<String,String> getImgInfo(File file)  {
		Map<String,String> imgInfoMap = new HashMap<String, String>();
		try {
			ImageInputStream stream = new FileImageInputStream(file);
            Iterator<ImageReader> readers = ImageIO.getImageReaders(stream);
            String imgWidth = "";
            String imgHeight = "";
            if (readers.hasNext()) {
                ImageReader reader = readers.next();
                reader.setInput(stream, true);
                imgWidth = String.valueOf(reader.getWidth(0));
                imgHeight = String.valueOf(reader.getHeight(0));
                
                imgInfoMap.put("imgWidth", imgWidth);
                imgInfoMap.put("imgHeight", imgHeight);
                System.out.println(reader.getFormatName());
                if(reader.getFormatName().toUpperCase().equals("JPG") || reader.getFormatName().toUpperCase().equals("GIF") || reader.getFormatName().toUpperCase().equals("PNG")){
                    imgInfoMap.put("imgFileType", reader.getFormatName());
                }
             }
             stream.close();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return imgInfoMap;
	}
}
