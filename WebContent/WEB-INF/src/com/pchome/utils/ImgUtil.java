package com.pchome.utils;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ImgUtil {
	private static final Log log = LogFactory.getLog(ImgUtil.class);
	
	private static ImgUtil instance = new ImgUtil();
	
	public static synchronized ImgUtil getInstance() {
		return instance;
	}
	
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

		createFolder(photoPath);
		
        String filenameExtension = getImgURLFilenameExtension(imgURL);
		
		log.info("下載圖片網址:" + imgURL);
        URL url = new URL(imgURL.replaceFirst("https", "http")); // 將https網址改成http
        String imgPathAndName = photoPath + "/" + imgFileName + "." + filenameExtension; // 存放路徑 + 檔名

        // 處理圖片下載
        if ("gif".equalsIgnoreCase(filenameExtension)) { // gif圖片下載方式，此方式圖片才有動畫
            InputStream in = url.openStream();
            Files.copy(in, new File(imgPathAndName).toPath(), StandardCopyOption.REPLACE_EXISTING);
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
	 * 將Base64圖片產生後，取得圖片存放路徑
	 * @param imgBase64String 圖片的Base64字串
	 * @param photoPath 資料夾位置
	 * @param imgFileName 檔名
	 * @return
	 * @throws IOException 
	 */
	public static String processImgBase64StringToImage(String imgBase64String, String photoPath, String imgFileName) throws IOException {
		log.info("開始將Base64圖片產生存放。");
		createFolder(photoPath);
		
		String filenameExtension = getImgBase64FilenameExtension(imgBase64String);
		if ("jpeg".equalsIgnoreCase(filenameExtension)) {
			filenameExtension = "jpg";
		}
		String imgPathAndName = photoPath + "/" + imgFileName + "." + filenameExtension; // 存放路徑 + 檔名

		byte[] bytes = Base64.decodeBase64(imgBase64String.split(",")[1].getBytes());
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
		if ("gif".equalsIgnoreCase(filenameExtension)) {// gif圖片產生方式，此方式圖片才有動畫
			Files.copy(byteArrayInputStream, new File(imgPathAndName).toPath(), StandardCopyOption.REPLACE_EXISTING);
		} else { // jpg、png圖片產生方式
			BufferedImage bufferedImage = ImageIO.read(byteArrayInputStream);
			File file = new File(imgPathAndName);
			ImageIO.write(bufferedImage, filenameExtension, file);
		}
		byteArrayInputStream.close();
		
		String imgPath = photoPath.substring(photoPath.indexOf("img/")) + "/" + imgFileName + "." + filenameExtension;
		log.info("Base64圖片存檔完成。");
		return imgPath;
	}

	/**
	 * 判斷圖片長寬，回傳相對應代碼
	 * @param imgPath 圖片完整路徑
	 * ex:
	 * 本機 D:/home/webuser/akb/pfp/img/user/img/user/AC2013071700005/catalogProd/PC201810050000000001/ccc.gif
	 * stg、prd /export/home/webuser/akb/pfp/img/user/AC2013071700005/catalogProd/PC201810050000000001/ccc.gif
	 * @return 橫&正方形:H、直:V
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static String getImgLongWidthCode(String imgPath) throws FileNotFoundException, IOException {
		File imgFile = new File(imgPath);
		BufferedImage bufferedImage = ImageIO.read(new FileInputStream(imgFile));
		if (bufferedImage.getWidth() >= bufferedImage.getHeight()) {
			return "H";
		} else {
			return "V";
		}
	}
	
	/**
	 * 取得圖片MD5值
	 * @param imgPath
	 * @return
	 * @throws NoSuchAlgorithmException 
	 * @throws IOException 
	 */
	public static String getImgMD5Code(String imgPath) throws NoSuchAlgorithmException, IOException {
		log.info("開始取得圖片MD5值，圖片路徑:" + imgPath);
		File file = new File(imgPath);
		FileInputStream fis = new FileInputStream(file);
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] buffer = new byte[1024];
		int length = -1;
		while ((length = fis.read(buffer, 0, 1024)) != -1) {
			md.update(buffer, 0, length);
		}
		BigInteger bigInt = new BigInteger(1, md.digest());
		log.info("圖片MD5值處理完成，MD5值:" + bigInt.toString(16));
		fis.close();
		return bigInt.toString(16);
	}
	
	
	/**
	 * 圖片resize
	 * */
	public BufferedImage imgResize(BufferedImage img,int resizeWidth,int resizeHeight) throws Exception{
		// 進行圖片縮放
		Image image = new ImageIcon(img).getImage();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gs = ge.getDefaultScreenDevice();
		GraphicsConfiguration gc = gs.getDefaultConfiguration();
		int transparency = Transparency.OPAQUE;
		BufferedImage bimage = gc.createCompatibleImage(resizeWidth, resizeHeight, transparency);
		Graphics g = bimage.createGraphics();
		g.drawImage(image, 0, 0, null);
        g.dispose();
		return bimage;
	}
	
	/**
	 * 從圖片網址取得附檔名
	 * @param imgURL
	 * @return
	 */
	public static String getImgURLFilenameExtension(String imgURL) {
		// 處理圖片如果有被加timestamp等參數從?位置抓取副檔名，沒被加參數則直接依長度取最後3碼
		int startLength = (imgURL.indexOf("?") > -1 ? imgURL.indexOf("?") - 3 : imgURL.length() - 3);
		int endLength = (imgURL.indexOf("?") > -1 ? imgURL.indexOf("?") : imgURL.length());
		return imgURL.substring(startLength, endLength);
	}
	
	/**
	 * 從圖片Base64取得附檔名
	 * @param imgBase64String
	 * @return
	 */
	public static String getImgBase64FilenameExtension(String imgBase64String) {
		return imgBase64String.substring(imgBase64String.indexOf("/") + 1, imgBase64String.indexOf(";"));
	}
	
	/**
	 * 如果此目錄路徑沒有資料夾，則建立資料夾
	 * @param path
	 */
	private static void createFolder(String path) {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs(); // 建立資料夾
		}
	}
}