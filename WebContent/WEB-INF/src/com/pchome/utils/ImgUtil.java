package com.pchome.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;

import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sun.misc.BASE64Decoder;

public class ImgUtil {
	private static final Log log = LogFactory.getLog(ImgUtil.class);
	
	/**
	 * 處理廣告商品下載圖片
	 * @param imgURL 下載的圖片網址
	 * @param photoPath 資料夾位置
	 * @param customerInfoId pfp id
	 * @param catalogSeq 商品目錄ID
	 * @param imgFileName 檔名
	 * @return 取得圖片存放路徑
	 * @throws IOException
	 */
	public static String processImgPathForCatalogProd(String imgURL, String photoPath, String imgFileName) throws IOException {
		log.info("開始下載圖片。");

		File file = new File(photoPath);
		if (!file.exists()) {
			file.mkdirs(); // 建立資料夾
		}
		
		// 取得副檔名，處理圖片如果有被加timestamp等參數從?位置抓取副檔名，沒被加參數則直接依長度取最後3碼
		int startLength = (imgURL.indexOf("?") > -1 ? imgURL.indexOf("?") - 3 : imgURL.length() - 3);
		int endLength = (imgURL.indexOf("?") > -1 ? imgURL.indexOf("?") : imgURL.length());
		String filenameExtension = imgURL.substring(startLength, endLength);
        
		log.info("下載圖片網址:" + imgURL);
        URL url = new URL(imgURL.replaceFirst("https", "http")); // 將https網址改成http
        String imgPathAndName = photoPath + "/" + imgFileName + "." + filenameExtension; // 存放路徑 + 檔名

        // 處理圖片下載
        if ("gif".equalsIgnoreCase(filenameExtension)) { // gif圖片下載方式，此方式圖片才有動畫
            InputStream in = url.openStream();
            Files.copy(in, new File(imgPathAndName).toPath());
            in.close();
        } else { // jpg、png圖片下載方式
            BufferedImage img = ImageIO.read(url);
            ImageIO.write(img, filenameExtension, new File(imgPathAndName));
        }
        
		String imgPath = photoPath.substring(photoPath.indexOf("img/")) + "/" + imgFileName + "." + filenameExtension;
		log.info("下載圖片結束");
		return imgPath;
	}

	/**
	 * 
	 * @param imgBase64String 圖片的Base64字串
	 * @param photoPath 資料夾位置
	 * @param imgFileName 檔名
	 * @return
	 */
	public static String processImgBase64StringToImage(String imgBase64String, String photoPath, String imgFileName) {
		// TODO Auto-generated method stub
		try {
			String imgPathAndName = photoPath + "/" + imgFileName;
			BASE64Decoder decoder = new BASE64Decoder();
			byte[] bytes1 = decoder.decodeBuffer(imgBase64String);
			ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);
			BufferedImage bi1 = ImageIO.read(bais);
			File f1 = new File("d://out.jpg");
			ImageIO.write(bi1, "jpg", f1);
			bais.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}