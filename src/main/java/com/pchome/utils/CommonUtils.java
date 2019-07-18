package com.pchome.utils;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.ComparatorUtils;
import org.apache.commons.collections.comparators.ComparableComparator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import com.pchome.enumerate.ad.EnumAdPriceType;
import com.pchome.enumerate.ad.EnumAdStyleType;
import com.pchome.enumerate.ad.EnumAdType;
import com.pchome.enumerate.report.EnumReportDevice;
import com.pchome.enumerate.utils.EnumStatus;

public class CommonUtils {
	protected static final Logger log = LogManager.getRootLogger();
	
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
                log.info(">>>>>>>>>>>>>>>>>>>>>>reader.getFormatName():"+reader.getFormatName());
                
                if(reader.getFormatName().toUpperCase().equals("JPEG")){
                	imgInfoMap.put("imgFileType", "jpg");
                }else if(reader.getFormatName().toUpperCase().equals("GIF")){
                	imgInfoMap.put("imgFileType", "gif");
                }else if(reader.getFormatName().toUpperCase().equals("PNG")){
                	imgInfoMap.put("imgFileType", "png");
                }
             }
             stream.close();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return imgInfoMap;
	}
	
	/**
	 * 取得總頁數
	 * @param totalDataSize 總筆數
	 * @param pageSize      每頁筆數
	 * @return
	 */
	public static int getTotalPage(int totalDataSize, int pageSize) {
		int totalPage = (totalDataSize / pageSize); //沒筆數則會為0
		if ((totalDataSize % pageSize) > 0) { //筆數還有剩，則再多加一頁
			totalPage += 1;
		}
		return totalPage;
	}

	/**
	 * 檢查字串是否有 Emoji圖片 字串
	 * @param checkStr
	 * @return
	 */
	public static boolean isHaveEmojiString(String str) {
		int strLength = str.length();
		int strReplaceEmojiLength = str.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "").length();
		if (strLength != strReplaceEmojiLength) { // 將Emoji字串取代完後，長度不同表示字串內有Emoji
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判斷字串是否非英文和數字
	 * @param str
	 * @return true:有非英文和數字 false:只有英文和數字
	 */
	public static boolean checkStringNonEnglishNumbers(String str) {
		return !str.matches("^[A-Za-z0-9]+$");
	}
	
	/**
	 * 包含檔名不能使用的特殊符號
	 * @param str
	 * @return true:有 false:沒有
	 */
	public static boolean containsSpecialSymbolsThatAreNotAllowedByFileName(String str) {
		return str.matches("^.*[(/) | (\\\\) | (:) | (\\*) | (\\?) | (\") | (<) | (>)].*$");
	}
	
	/**
	 * 包含中文
	 * @param str
	 * @return true:有 false:沒有
	 */
	public static boolean containsChineseStr(String str) {
		return str.matches("^.*[\u4e00-\u9fa5].*$");
	}
	
	/**
	 * 取得取代掉特殊符號的字串
	 * @param str
	 * @return
	 */
	public static String getReplaceSpecialSymbolsStr(String str) {
		return str.replaceAll("[^A-Za-z0-9]+$", "");
	}
	
	/**
	 * 取得取代掉Emoji圖片的字串
	 * @param str
	 * @return
	 */
	public static String getReplaceEmojiStr(String str) {
		return str.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "");
	}

	/**
	 * 取得取代掉檔案名稱不允許輸入的9個特殊符號
	 * @param fileName
	 * @return
	 */
	public static String getReplaceSpecialSymbolsThatAreNotAllowedByFileName(String fileName) {
		return fileName.replaceAll("[(/) | (\\\\) | (:) | (\\*) | (\\?) | (\") | (<) | (>)]", "");
	}

	/**
	 * 由網址判斷是否為csv檔
	 * @param url
	 * @return 正確回傳檔名fileName及副檔名filenameExtension  錯誤則檔名副檔名皆為空
	 */
	public static Map<String, String> getDataFromUrl(String url) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("fileName", "");
		map.put("filenameExtension", "");

		// 由網址判斷取得檔名副檔名
		int startLength = url.lastIndexOf("/") + 1;
		int endLength = (url.indexOf("?") > -1 ? url.indexOf("?") : url.length());
		String fileName = url.substring(startLength, endLength);
		String filenameExtension = "";
		if (fileName.length() >= 4) {
			filenameExtension = fileName.substring(fileName.length() - 4);
		}
		
		if (".csv".equalsIgnoreCase(filenameExtension)) {
			map.put("fileName", fileName);
			map.put("filenameExtension", "csv");
		}
		return map;
	}
	
	/**
	 * 如果此目錄路徑沒有資料夾，則建立資料夾
	 * @param path
	 */
	public static void createFolder(String path) {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs(); // 建立資料夾
		}
	}
	
	/**
	 * 將查詢結果排序
	 * 參考 https://www.cnblogs.com/china-li/archive/2013/04/28/3048739.html
	 * @param list
	 * @param fieldName vo內有的參數名
	 * @param sortBy
	 */
	public void sort(List<?> list, String fieldName, String sortBy) {
        Comparator<?> mycmp = ComparableComparator.getInstance();
		if ("asc".equalsIgnoreCase(sortBy)) {
			mycmp = ComparatorUtils.nullLowComparator(mycmp);
		} else {
			mycmp = ComparatorUtils.reversedComparator(mycmp);
		}
        Collections.sort(list, new BeanComparator(fieldName, mycmp));
    }
	
	/**
	 * 取得除法計算值 <br/>
	 * 公式:dividend / divisor
	 * @param dividend 被除數
	 * @param divisor  除數
	 * @return true:unbmer false:0
	 */
	public Double getCalculateDivisionValue(BigDecimal dividend, BigDecimal divisor) {
		if (dividend.compareTo(BigDecimal.ZERO) >= 1 && divisor.compareTo(BigDecimal.ZERO) >= 1) {
			return dividend.divide(divisor, 6, RoundingMode.DOWN).doubleValue();
		}
		return (BigDecimal.ZERO).doubleValue();
	}
	
	/**
	 * 取得除法計算值 <br/>
	 * 公式:(dividend / divisor) * number
	 * @param dividend 被除數
	 * @param divisor  除數
	 * @param number   乘多少自行放入
	 * @return true:unbmer false:0
	 */
	public Double getCalculateDivisionValue(BigDecimal dividend, BigDecimal divisor, int number) {
		if (dividend.compareTo(BigDecimal.ZERO) >= 1 && divisor.compareTo(BigDecimal.ZERO) >= 1) {
			return dividend.divide(divisor, 6, RoundingMode.DOWN).multiply(new BigDecimal(number)).doubleValue();
		}
		return (BigDecimal.ZERO).doubleValue();
	}

	/**
	 * 取得裝置中文
	 * @param whereMap JSONObject格式字串
	 * @return
	 */
	public String getAdDeviceName(String whereMap) {
		String adDevice = EnumReportDevice.ALL.getDevTypeName();
		JSONObject tempJSONObject = new JSONObject(whereMap);
		String tempStr = tempJSONObject.optString("adDevice");
		if (EnumReportDevice.PC.getDevType().equalsIgnoreCase(tempStr)) {
			adDevice = EnumReportDevice.PC.getDevTypeName();
		} else if (EnumReportDevice.MOBILE.getDevType().equalsIgnoreCase(tempStr)) {
			adDevice = EnumReportDevice.MOBILE.getDevTypeName();
		} else if (EnumReportDevice.PCANDMOBILE.getDevType().equalsIgnoreCase(tempStr)) {
			adDevice = EnumReportDevice.PCANDMOBILE.getDevTypeName();
		}
		return adDevice;
	}
	
	/**
	 * 取得廣告狀態Map
	 * @return
	 */
	public Map<String, String> getAdStatusMap() {
		Map<String, String> map = new LinkedHashMap<>();
		for (EnumStatus status : EnumStatus.values()) {
			map.put(Integer.toString(status.getStatusId()), status.getStatusRemark());
		}
		return map;
	}
	
	/**
	 * 取得播放類型map
	 * @return
	 */
	public Map<Integer, String> getAdType() {
		Map<Integer, String> adTypeMap = new HashMap<>();
		for (EnumAdType enumAdType : EnumAdType.values()) {
			adTypeMap.put(enumAdType.getType(), enumAdType.getTypeName());
		}
		return adTypeMap;
	}
	
	/**
	 * 取得計價方式map
	 * @return
	 */
	public Map<String, String> getAdPriceTypeMap() {
		Map<String, String> adPriceTypeMap = new HashMap<>();
		for (EnumAdPriceType enumAdPriceType : EnumAdPriceType.values()) {
			adPriceTypeMap.put(enumAdPriceType.getDbTypeName(), enumAdPriceType.getTypeName());
		}
		return adPriceTypeMap;
	}
	
	/**
	 * 取得廣告樣式map
	 * @return
	 */
	public Map<String, String> getAdStyleTypeMap() {
		Map<String, String> adStyleTypeMap = new HashMap<>();
		for (EnumAdStyleType enumAdStyleType : EnumAdStyleType.values()) {
			adStyleTypeMap.put(enumAdStyleType.getTypeName(), enumAdStyleType.getType());
		}
		return adStyleTypeMap;
	}
}