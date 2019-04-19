package com.pchome.akbpfp.struts2.action.ad;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.mortennobel.imagescaling.ResampleOp;

/**
 * image 的工具, 主要提供縮放的功能
 * 
 * @author MarkYeh
 * 
 */
public class ImageUtil {
	private static final Logger log = LogManager.getRootLogger();

	/**
     * 根據傳入的圖片座標進行圖片截取
     *
     * @param x1 X起點座標
     * @param x2 X終點座標
     * @param y1 Y起點座標
     * @param y2 Y終點座標
     * @param originPath 原始圖片的存放路徑
     * @param savePath 截取後圖片的存放路徑
     * @throws IOException
     */
	public boolean scissor(String originPath, String savePath) throws IOException{
		boolean backVal = false;
		FileInputStream is = null;
		ImageInputStream iis = null;
		int x1 = 0;
		int y1 = 0;
		int length = 0;

		try {
			BufferedImage inputBufImage = ImageIO.read(new File(originPath));
			inputBufImage.getType();

			// Original size
			int imageWidth = inputBufImage.getWidth(null);
			int imageHeight = inputBufImage.getHeight(null);

			if (imageWidth > imageHeight) {
				x1 = (imageWidth - imageHeight)/2;
				imageWidth = imageHeight;
				length = imageHeight;
			} else {
				y1 = (imageHeight - imageWidth)/2;
				imageHeight = imageWidth;
				length = imageWidth;
			}

			// 讀取圖片
			is = new FileInputStream(originPath);

			/*
			 * 返回包含所有當前已註冊 ImageReader 的 Iterator，
			 * 這些 ImageReader 聲稱能夠解碼指定格式。
			 * 參數：formatName - 包含非正式格式名稱 .（例如 "jpeg" 或 "tiff"）等 。
			 */
			Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName(getExtention(originPath).toLowerCase());
			ImageReader reader = it.next();

			// 獲取圖片流
            iis = ImageIO.createImageInputStream(new File(originPath));
            
  
            /* 
             * iis:讀取源.true:只向前搜索，將它標記為 ‘只向前搜索’。
             * 此設置意味著包含在輸入源中的圖像將只按順序讀取，可能允許
             * reader 避免緩存包含與以前已經讀取的圖像關聯的數據的那些那些輸入部分。
             */
            reader.setInput(iis, true);
  
            /*
             * 描述如何對流進行解碼的類，用於指定如何在輸入時從 Java Image I/O
             * 框架的上下文中的流轉換一幅圖像或一組圖像。用於特定圖像格式的插件
             * 將從其 ImageReader 實現的
             * getDefaultReadParam方法中返回 ImageReadParam 的實例。
             */
            ImageReadParam param = reader.getDefaultReadParam();
  
            /* 
             * 圖片裁剪區域。Rectangle 指定了座標空間中的一個區域，通過 Rectangle 對象
             * 的左上頂點的座標（x，y）、寬度和高度可以定義這個區域。
             */
            Rectangle rect = new Rectangle(x1, y1, length, length);

            // 提供一個 BufferedImage，將其用作解碼像素數據的目標。
            param.setSourceRegion(rect);
  
            /*
             * 使用所提供的 ImageReadParam 讀取通過索引 imageIndex 指定的對象，並將它做為一個完整的
             * BufferedImage 返回。
             */
            BufferedImage bi = reader.read(0, param);

            // 保存新圖片
            ImageIO.write(bi, getExtention(originPath).toLowerCase(), new File(savePath));
            System.out.println("savePath = " + savePath);
            System.out.println("getExtention(originPath).toLowerCase() = " + getExtention(originPath).toLowerCase());
            System.out.println("scissor finish");
            backVal = true;
		} catch (Exception ex) {
			System.out.println("Exception(scissor) : " + ex);
        } finally {
        	if (is != null)		is.close();
            if (iis != null)	iis.close();
        }
		System.out.println(backVal);
		return backVal;
	}
    
	/**
	 * 功能：提取文件的副檔名
	 *
	 * @param fileName
	 * @return
	 */
	private static String getExtention(String fileName) {
		int pos = fileName.lastIndexOf(".");
		return fileName.substring(pos + 1);
	}

	/** 
     * 接收輸入流輸出圖片 
     * @param input 
     * @param writePath 
     * @param width 
     * @param height 
     * @param format 
     * @return 
     */  
	public boolean resizeImage(InputStream input, String writePath, int maxLength, String format) {  
		try {
			ImageIO.read(input);
			System.out.println("ImageIO.read(iis) is OK");
			BufferedImage inputBufImage = ImageIO.read(input);
			//System.out.println("轉換前圖片高度和寬度：" + inputBufImage.getHeight() + ":"+ inputBufImage.getWidth());  

			// Original size
			int imageWidth = inputBufImage.getWidth(null);
			int imageHeight = inputBufImage.getHeight(null);

			if (imageWidth > imageHeight) {
				imageHeight = (maxLength * imageHeight) / imageWidth;
				imageWidth = maxLength;
			} else {
				imageWidth = (maxLength * imageWidth) / imageHeight;
				imageHeight = maxLength;
			}

			ResampleOp resampleOp = new ResampleOp(imageWidth, imageHeight);// 轉換  
			BufferedImage rescaledTomato = resampleOp.filter(inputBufImage, null);  
			ImageIO.write(rescaledTomato, format, new File(writePath));  
			//System.out.println("轉換後圖片高度和寬度：" + rescaledTomato.getHeight() + ":"+ rescaledTomato.getWidth());  
			return true;  
		} catch (IOException e) {  
			e.printStackTrace();  
			return false;  
        }  
    }  

	 /** 
    * 接收輸入流輸出圖片 
    * @param input 
    * @param writePath 
    * @param width 
    * @param height 
    * @param format 
    * @return 
    */  
	public boolean resizeImage(File resizeFile, File tmpFile, int maxLength, String format) {  
		try {
			BufferedImage inputBufImage = ImageIO.read(resizeFile);
			//System.out.println("轉換前圖片高度和寬度：" + inputBufImage.getHeight() + ":"+ inputBufImage.getWidth());  

			// Original size
			int imageWidth = inputBufImage.getWidth(null);
			int imageHeight = inputBufImage.getHeight(null);

			if (imageWidth > imageHeight) {
				imageHeight = (maxLength * imageHeight) / imageWidth;
				imageWidth = maxLength;
			} else {
				imageWidth = (maxLength * imageWidth) / imageHeight;
				imageHeight = maxLength;
			}

			ResampleOp resampleOp = new ResampleOp(imageWidth, imageHeight);// 轉換  
			BufferedImage rescaledTomato = resampleOp.filter(inputBufImage, null);  
			ImageIO.write(rescaledTomato, format, tmpFile);
			//System.out.println("轉換後圖片高度和寬度：" + rescaledTomato.getHeight() + ":"+ rescaledTomato.getWidth());  
			return true;  
		} catch (IOException e) {  
			e.printStackTrace();  
			return false;  
       }  
   }  

	 /** 
    * 接收輸入流輸出圖片 
    * @param input 
    * @param writePath 
    * @param width 
    * @param height 
    * @param format 
    * @return 
    */  
	public boolean resizeCutImage(InputStream input, String writePath, int maxLength, String format) {  
		try {  
			BufferedImage inputBufImage = ImageIO.read(input);
			System.out.println("轉換前圖片高度和寬度：" + inputBufImage.getHeight() + ":"+ inputBufImage.getWidth());  

			// Original size
			int imageWidth = inputBufImage.getWidth(null);
			int imageHeight = inputBufImage.getHeight(null);

			if (imageWidth > imageHeight) {
				imageHeight = (maxLength * imageHeight) / imageWidth;
				imageWidth = maxLength;
			} else {
				imageWidth = (maxLength * imageWidth) / imageHeight;
				imageHeight = maxLength;
			}

			ResampleOp resampleOp = new ResampleOp(imageWidth, imageHeight);// 转换  
			BufferedImage rescaledTomato = resampleOp.filter(inputBufImage, null);  
			ImageIO.write(rescaledTomato, format, new File(writePath));  
			System.out.println("轉換後圖片高度和寬度：" + rescaledTomato.getHeight() + ":"+ rescaledTomato.getWidth());  
			return true;  
		} catch (IOException e) {  
			e.printStackTrace();  
			return false;  
       }  
   }  

	/** 
	 * 接收File輸出圖片 
	 * @param file 
	 * @param writePath 
	 * @param width 
	 * @param height 
	 * @param format 
	 * @return 
	 */  
	public boolean resizeImage(File file, String writePath, int maxLength, String format) {
		try {
			BufferedImage inputBufImage = ImageIO.read(file);
			inputBufImage.getType();

			// Original size
			int imageWidth = inputBufImage.getWidth(null);
			int imageHeight = inputBufImage.getHeight(null);

			if (imageWidth > imageHeight) {
				imageHeight = (maxLength * imageHeight) / imageWidth;
				imageWidth = maxLength;
			} else {
				imageWidth = (maxLength * imageWidth) / imageHeight;
				imageHeight = maxLength;
			}

			log.info("轉換前圖片高度和寬度：" + inputBufImage.getHeight() + ":"+ inputBufImage.getWidth());
			ResampleOp resampleOp = new ResampleOp(imageWidth, imageHeight);// 轉換
			BufferedImage rescaledTomato = resampleOp.filter(inputBufImage, null);
			ImageIO.write(rescaledTomato, format, new File(writePath));
			log.info("轉換後圖片高度和寬度：" + rescaledTomato.getHeight() + ":"+ rescaledTomato.getWidth());
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}  

	/** 
	 * 接收字節數組生成圖片
	 * @param RGBS 
	 * @param writePath 
	 * @param width 
	 * @param height 
	 * @param format 
	 * @return 
	 */  
	public boolean resizeImage(byte[] RGBS, String writePath, int maxLength, String format) {
		InputStream input = new ByteArrayInputStream(RGBS);
		return this.resizeImage(input, writePath, maxLength, format);  
	}  

	public byte[] readBytesFromIS(InputStream is) throws IOException {  
		int total = is.available();  
		byte[] bs = new byte[total];  
		is.read(bs);  
		return bs;  
	}  
}
