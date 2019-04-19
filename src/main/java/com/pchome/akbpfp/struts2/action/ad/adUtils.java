package com.pchome.akbpfp.struts2.action.ad;

import java.io.File;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pchome.akbpfp.db.vo.ad.AdFreeVO;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.utils.EnumStatus;
import com.pchome.soft.depot.utils.HttpUtil;

@SuppressWarnings("serial")
public class adUtils extends BaseCookieAction {

    /**
     * 將資料補成固定長度的資料，補入字元僅能為一個字元，例如(0,7,"0","11")會傳回0000011;
     * @param fb 輸入 int，0 為前，1 為後
     * @param len 輸入 int，回傳字串的總長度
     * @param pword 補入字串中的字元
     * @param data 待補的資料
     * @return String 傳回補入固定字元且為固定長度的字串
     */
	public static String patchWord(int fb,int len,String pword,String data) {
		int datalen = data.length();
		int plen = len-datalen;
		if(pword.equals(" ")) 			         pword = " ";

        if(pword.length() == 1) {
			if( datalen < len ) {
				if(fb == 0) {
					for(int i = 0 ; i < plen ; i++) 			data = pword + data;
				} else if(fb == 1) {
					for(int i = 0 ; i < plen ; i++)        		data = data + pword;
				}
			}
		}
		return data;
	}

	//conver time format
	public static String TimeFormat(Date timestr, String format) {
		String back = "";
		try {
			if(timestr != null) {
				java.text.SimpleDateFormat df = new java.text.SimpleDateFormat(format);    
				back = df.format(timestr);
			}
		} catch(Exception ex) {
			System.out.println("Error!!");
			System.out.println(ex);
		}
		return back;
	}

	//conver DBtime format
	public static String DBtimeFormat(String timestr) {
		String back = "";
		try {
			if(timestr != null && !timestr.trim().equals("")) {
                if(timestr.lastIndexOf(".") > 0) {
                    timestr = timestr.substring(0,timestr.lastIndexOf("."));
                }
                timestr = timestr.replaceAll("-", "/");
				java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy/MM/dd");
                //將 new Date(src_time) 轉為新的寫法 DateFormat.getDateInstance().parse(timestr)
                //Date time = DateFormat.getDateInstance().parse(timestr);
				back = df.format(df.parse(timestr, new ParsePosition(0)));
			}
		} catch(Exception ex) {
			System.out.println("Error!!");
			System.out.println(ex);
		}
		return back;
	}

	//conver DBtime format
	public static String DBtimeFormat(String timestr, String format) {
		String back = "";
		try {
			if(timestr != null && !timestr.trim().equals("")) {
                if(timestr.lastIndexOf(".") > 0) {
                    timestr = timestr.substring(0,timestr.lastIndexOf("."));
                }
                timestr = timestr.replaceAll("-", "/");
				java.text.SimpleDateFormat df = new java.text.SimpleDateFormat(format);
                //將 new Date(src_time) 轉為新的寫法 DateFormat.getDateInstance().parse(timestr)
				//Date time = DateFormat.getDateInstance().parse(timestr);
                back = df.format(df.parse(timestr, new ParsePosition(0)));
			}
		} catch(Exception ex) {
			System.out.println("Error!!");
			System.out.println(ex);
		}
		return back;
	}

    //conver String to Date
    public static Date DateFormat(String timestr) {
        Date back = new Date();
        try {
            if(timestr != null && !timestr.trim().equals("")) {
                if(timestr.lastIndexOf(".") > 0) {
                    timestr = timestr.substring(0,timestr.lastIndexOf("."));
                }
                timestr = timestr.replaceAll("-", "/");
                java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy/MM/dd");
                //將 new Date(src_time) 轉為新的寫法 DateFormat.getDateInstance().parse(timestr)
                //Date time = DateFormat.getDateInstance().parse(timestr);
                back = df.parse(timestr, new java.text.ParsePosition(0));
            }
        } catch(Exception ex) {
            System.out.println("Error!!");
            System.out.println(ex);
        }
        return back;
    }

    //conver String to Date
    public static Date DateFormat(String timestr, String format) {
        Date back = new Date();
        try {
            if(timestr != null && !timestr.trim().equals("")) {
                if(timestr.lastIndexOf(".") > 0) {
                    timestr = timestr.substring(0,timestr.lastIndexOf("."));
                }
                timestr = timestr.replaceAll("-", "/");
                //System.out.println(timestr);
                java.text.SimpleDateFormat df = new java.text.SimpleDateFormat(format);
                //將 new Date(src_time) 轉為新的寫法 DateFormat.getDateInstance().parse(timestr)
                //Date time = DateFormat.getDateInstance().parse(timestr);
                back = df.parse(timestr, new java.text.ParsePosition(0));
            }
        } catch(Exception ex) {
            System.out.println("Error!!");
            System.out.println(ex);
        }
        return back;
    }

	//抓取現在時間的數字 array
	public static int[] getNow() {
		int now[] = new int[3];
		now[0] = Calendar.getInstance().get(Calendar.YEAR);
		now[1] = Calendar.getInstance().get(Calendar.MONTH) + 1;
		now[2] = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		return now;
	}


    //抓取現在時間的數字 array
    public static String getStringNow() {
        String back = "";
        int now[] = new int[3];
        now[0] = Calendar.getInstance().get(Calendar.YEAR);
        now[1] = Calendar.getInstance().get(Calendar.MONTH) + 1;
        now[2] = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        back = Integer.toString(now[0]) + patchWord(0,2,"0",Integer.toString(now[1])) + patchWord(0,2,"0",Integer.toString(now[2]));
        return back;
    }

    /**
     * 設定 select、radio、checkbox 選取的值
     * @param type 物件種類
     * @param value1 第一個字串
     * @param value2 第二個字串
     * @return String 比對正確，若為 select，則傳回 select
     */
    public static String selData(String type, String value1, String value2) {
		String back = "";
		String word = "";
        String val[] = value1.split(";");
		if(type.toLowerCase().equals("checkbox") || type.toLowerCase().equals("radio")) {
			word = " checked";
		} else if(type.toLowerCase().equals("select")) {
			word = " selected";
		}

        for(int i = 0; i < val.length; i++) {
            if(val[i].equals(value2)) {
                back = word;
            }
        }
		return back;
	}

    /**
     * 設定 select、radio、checkbox 選取的值
     * @param type 物件種類
     * @param value1 第一個數值
     * @param value2 第二個數值
     * @return String 比對正確，若為 select，則傳回 select；若為 checkbox 或 radio，則回傳 checked
     */
    public static String selData(String type, int value1, int value2) {
        String back = "";
        String word = "";
        if(type.toLowerCase().equals("checkbox") || type.toLowerCase().equals("radio")) {
            word = " checked";
        } else if(type.toLowerCase().equals("select")) {
            word = " selected";
        }

        if(value1 == value2) {
            back = word;
        }
        return back;
    }
    
    //檢查檔案是否存在, 並傳回  true / false
    public static boolean checkfile(String path) {
        boolean back = false;
        File fileloc = new File(path);
        if(fileloc.exists())            back = true;
        return back;
    }

    //比對字串
    public static boolean compareString(String str1, String str2) {
        boolean back = false;
        if(str1.equals(str2))   back = true;
        return back;
    }

    //陣列與字串比對
    public static boolean compareStringAry(String[] str1, String  str2) {
        boolean back = false;
        for (int i = 0; i < str1.length; i++){
            if(str1[i].equals(str2)) {
                back = true;
                break;
            }
        }
        return back;
    }

    /**
     * 檢查傳入文字字串是否為數字，並將文字字串轉為數值回傳
     * @param srcStr 待檢查的文字字串
     * @param backVal 若不是數字，則回傳此數值(int)
     * @return int; 不是數字回傳 backVal; 是數字則回傳 srcStr 轉換後的數值
     */
    public static int isNan(String srcStr, int backVal) {
        int back = backVal;
        try {
            if(srcStr != null && !srcStr.equals("") && StringUtils.isNumeric(srcStr)){
                back = Integer.parseInt(srcStr);
            }
        } catch(Exception ex) {
            System.out.println("isNan(String srcStr, int backVal) has error : " + ex);
        }
        return back;
    }

    /**
     * 檢查傳入文字字串是否為數字，並將文字字串轉為數值回傳
     * @param srcStr 待檢查的文字字串
     * @param backVal 若不是數字，則回傳此數值(long)
     * @return long; 不是數字回傳 backVal; 是數字則回傳 srcStr 轉換後的數值
     */
    public static long isNan(String srcStr, long backVal) {
        long back = backVal;
        try {
            if(srcStr != null && !srcStr.equals("") && StringUtils.isNumeric(srcStr)){
                back = Long.parseLong(srcStr);
            }
        } catch(Exception ex) {
            System.out.println("isNan(String srcStr, int backVal) has error : " + ex);
        }
        return back;
    }

    /**
     * 檢查 srcStr 是否為 null
     * @param srcStr 待檢查字串
     * @param noValue 如果 srcStr 為 null 或空白，所傳回的值 
     * @return String; 若 srcStr 為 null 或空白，回傳 noValue；若 srcStr 不為 null 或空白，則回傳 srcStr
     */
    public static String checkValue(String srcStr, String noValue) {
        String back = "";
        if(srcStr != null && (! srcStr.trim().equals(""))){
            back = srcStr;
        } else {
            back = noValue;
        }
        return back;
    }

    /**
     * 檢查 srcStr 是否為 null
     * @param srcStr 待檢查字串
     * @param backValue 如果 srcStr 為 null 或空白，所傳回的值 
     * @param noValue 如果 srcStr 為 null 或空白，所傳回的值 
     * @return String; 若 srcStr 為 null 或空白，回傳 noValue；若 srcStr 不為 null 或空白，則回傳 backValue
     */
    public static String checkValue(String srcStr, String backValue, String noValue) {
        String back = "";
        if(srcStr != null && (! srcStr.trim().equals(""))){
            back = backValue;
        } else {
            back = noValue;
        }
        return back;
    }

    /**
     * 檢查 srcStr 是否為 null 或傳入值 checkValue
     * @param srcStr 待檢查字串
     * @param checkValue 檢查條件的字串
     * @param trueValue 如果 srcStr 跟 checkValue 相同，所傳回的值 
     * @param falseValue 如果 srcStr 跟 checkValue 不同，所傳回的值 
     * @return String; 回傳比對結果的值
     */
    public static String checkValue(String srcStr, String checkValue, String trueValue, String falseValue) {
        String back = "";
        if(srcStr != null && srcStr.trim().equals(checkValue)){
            back = trueValue;
        } else {
            back = falseValue;
        }
        return back;
    }

    /**
     * 西元時間與民國時間的轉換
     * @param timeStr 輸入年份
     * @return String; 回傳 年份
     */
    public static String timeTransfer(String timeStr) {
        String back = "";
        String tmp[] = new String[3];
        tmp = timeStr.split("/");
        if(timeStr != null && tmp.length == 3 && Integer.parseInt(tmp[0]) >= 200){
            back = Integer.toString(Integer.parseInt(tmp[0]) - 1911) + "/" + tmp[1] + "/" + tmp[2];
        } else if(timeStr != null && tmp.length == 3 && Integer.parseInt(tmp[0]) < 200) {
            back = Integer.toString(Integer.parseInt(tmp[0]) + 1911) + "/" + tmp[1] + "/" + tmp[2];
        }
        return back;
    }

    /**
     * 阿拉伯數字用中文數字取代(含十、百等)
     * @param num 傳入數字字串
     * @return String; 中文數字字串
     */
    public static String numWord(String num) {
        String[] arrayNum={"○","一","二","三","四","五","六","七","八","九"};
        if( num.equals("") ) return "";
        if( num!=null ) {
            num = Integer.toString( Integer.parseInt(num) );
            int numlen = num.length();
            for(int i =0; i<10; i++) {
                num = num.replaceAll(Integer.toString(i),arrayNum[i]);
            }
            if(numlen == 2) {
                if(num.substring(1).equals("○") && num.substring(0,1).equals("一")) {
                    num = "十";
                } else if(num.substring(1).equals("○")) {
                    num = num.substring(0,1)+"十";
                } else if(num.substring(0,1).equals("一")) {
                    num = "十"+num.substring(1);
                } else {
                    num = num.substring(0,1)+"十"+num.substring(1);
                }
            }
        }
        return num;
    }

    /**
     * 檢查日期字串
     * @param str 傳入日期字串
     * @return boolean 是否是正確的時間字串
     */
    public static boolean CheckDate(String str) throws Exception {
    	boolean chk = false;
    	chk = str.matches("^(19[0-9][0-9]|2[01]?[0-9][0-9])(/|-)([1-9]|0[1-9]|1[0-2])(/|-)([1-9]|0[1-9]|[1-2][0-9]|3[01])$");
		//System.out.println(str.matches("^(19[0-9][0-9]|2[01]?[0-9][0-9])(/|-)([1-9]|0[1-9]|1[0-2])(/|-)([1-9]|0[1-9]|[1-2][0-9]|3[01])$"));
        return chk;
    }

    /**
     * 將字串轉成 Date 型態
     * @param str 傳入日期字串
     * @return Date; 日期
     */
    public static Date StringToDate(String str) throws Exception {
        String[] parsePatterns = {"yyyy/MM/dd HH:mm:ss"};
        return StringToDate(str, parsePatterns);
    }

    /**
     * 將字串轉成 Date 型態
     * @param str 傳入日期字串
     * @param parsePatterns 轉換的格式
     * @return Date; 日期
     */
    public static Date StringToDate(String str, String parsePatterns) throws Exception {
        return org.apache.commons.lang.time.DateUtils.parseDate(str, new String[]{parsePatterns});
    }

    /**
     * 將字串依傳入的 pattern 轉成 Date 型態 
     * @param str 傳入日期字串
     * @param parsePatterns[] 轉換的格式
     * @return Date; 日期
     */
    public static Date StringToDate(String str, String[] parsePatterns) throws Exception {
        return org.apache.commons.lang.time.DateUtils.parseDate(str, parsePatterns);
    }

    /**
     * 將數字轉為金額
     * @param number 一個數字物件
     * @return String; 有 "," 格式的數字字串
     */
    public static String cvtFigureFormat(Object number) {
    	java.text.DecimalFormat df = new java.text.DecimalFormat("##,###,###,###,###.##");
    	return String.valueOf(df.format(number));
    }

    /**
     * 將數字(double)四捨五入, 傳回字串
     * @param number 待處理數值
     * @param digit 小數點以下位數
     * @return String; 回傳 String 型態的字串
     */
    public static String sRoundOff(double number, int digit) {
        BigDecimal roundoff = new BigDecimal(number).setScale(digit,BigDecimal.ROUND_HALF_UP);
        return String.valueOf(roundoff);
    }

    /**
     * 將數字(double)四捨五入, 傳回 BigDecimal
     * @param number 待處理數值
     * @param digit 小數點以下位數
     * @return BigDecimal; 回傳 BigDecimal 型態的字串
     */
    public static BigDecimal bRoundOff(double number, int digit) {
        BigDecimal roundoff = new BigDecimal(number).setScale(digit,BigDecimal.ROUND_HALF_UP);
        return roundoff;
    }

    /**
     * 將數字(double)四捨五入, 並依傳入的格式傳回字串
     * @param number
     * @return String; 回傳 String 型態的字串
     */
    public static String sNumberFormat(double number) {
        //BigDecimal roundoff = new BigDecimal(number).setScale(digit,BigDecimal.ROUND_HALF_UP);
        String f = MessageFormat.format("{0,number,##,###,###,###,###.###}", number);
        return f;
    }

    /**
     * 將數字(double)四捨五入, 並依傳入的格式傳回字串
     * @param number 待處理數值
     * @param digit 小數點以下位數
     * @return String; 回傳 String 型態的字串
     */
    public static String sNumberFormat(double number, int digit) {
        //BigDecimal roundoff = new BigDecimal(number).setScale(digit,BigDecimal.ROUND_HALF_UP);
        String number_format = "##,###,###,###,###";
        if(digit > 0 ) {
            number_format += ".";
            for(int i = 1; i <= digit; i++) {
                number_format += "#";
            }
        }
        String back = sNumberFormat(number, number_format);
        return back;
    }

    /**
     * 將數字(double)四捨五入, 並依傳入的格式傳回字串
     * @param number 待處理數值
     * @param number_format 數值格式字串
     * @return String; 回傳 String 型態的字串
     */
    public static String sNumberFormat(double number, String number_format) {
        //BigDecimal roundoff = new BigDecimal(number).setScale(digit,BigDecimal.ROUND_HALF_UP);
        String f = MessageFormat.format("{0,number,"+number_format+"}", number);
        return f;
    }


    /**
     * 將數字(double)四捨五入, 並依傳入的格式傳回字串
     * @param number 待處理數值
     * @param format 數值格式字串
     * @return String; 回傳 String 型態的字串
     */
    public static String cvtFigureFormat(Object number, String format) {
    	java.text.DecimalFormat df = new java.text.DecimalFormat(format);
    	return String.valueOf(df.format(number));
     
    }
    
    public static String getEnumStatusDesc(int statusId) {
    	String statusDesc = "";
    	for (EnumStatus enumStatus : EnumSet.allOf(EnumStatus.class)) {
    		if (statusId == enumStatus.getStatusId()) {
    			statusDesc = enumStatus.getStatusDesc();
    		}
    	}
    	return statusDesc;
    }
    
    public static Map<String, AdFreeVO> getAdFree(String classifiedsServer, String member_id, String aid, String key) {
    	Map<String, AdFreeVO> adFreeVOs = new TreeMap<String, AdFreeVO>();
		String kwApi = classifiedsServer + "?member_id="+member_id+"&aid="+aid;
		if(StringUtils.isNotEmpty(key)) {
			if(key.indexOf("ad_no") < 0)	key += "ad_no,";
			kwApi += "&key=" + key;
		} else {
			key = "ad_no,title,img_path,va_1,va_2,va_3,va_4,url,content,tags,first_class,sec_class,st_date,ed_date,ad_name";
		}
		List<String> list = Arrays.asList(key.split(","));
		System.out.println("kwApi = " + kwApi);
		String result = HttpUtil.getInstance().getResult(kwApi, "UTF-8");
		System.out.println("result = " + result);

        try {
            JSONObject json = new JSONObject(result);
            System.out.println("json = " + json);
            System.out.println("status = " + json.getString("status"));
            if(json.getString("status").equals("success")) {
            	JSONArray jsonArray = json.getJSONArray("data");
            
	            for (int i = 0; i < jsonArray.length(); i++) {
	            	AdFreeVO adFreeVO = new AdFreeVO();
	            	adFreeVO.setStatus(json.getString("status"));
	                //取得jsonArray的物件轉成JSONObject
	                JSONObject jsonItem = jsonArray.getJSONObject(i);
	                
	                // 廣告編號
	                if(list.contains("ad_no") && StringUtils.isNotEmpty(jsonItem.getString("ad_no"))) {
		                adFreeVO.setAd_no(jsonItem.getString("ad_no"));
	                }
	                // 廣告標題
	                if(list.contains("title") && StringUtils.isNotEmpty(jsonItem.getString("title"))) {
		                adFreeVO.setTitle(jsonItem.getString("title"));
	                }
	                // 圖片 url
	                if(list.contains("img_path") && StringUtils.isNotEmpty(jsonItem.getString("img_path"))) {
		                adFreeVO.setImg_path(jsonItem.getString("img_path"));
	                }
	                // 擴充欄位一
	                if(list.contains("va_1") && StringUtils.isNotEmpty(jsonItem.getString("va_1"))) {
		                adFreeVO.setVa_1(jsonItem.getString("va_1"));
	                }
	                // 擴充欄位二
	                if(list.contains("va_2") && StringUtils.isNotEmpty(jsonItem.getString("va_2"))) {
	                	adFreeVO.setVa_2(jsonItem.getString("va_2"));
	                }
	                // 擴充欄位三
	                if(list.contains("va_3") && StringUtils.isNotEmpty(jsonItem.getString("va_3"))) {
	                	adFreeVO.setVa_3(jsonItem.getString("va_3"));
	                }
	                // 擴充欄位四
	                if(list.contains("va_4") && StringUtils.isNotEmpty(jsonItem.getString("va_4"))) {
	                	adFreeVO.setVa_4(jsonItem.getString("va_4"));
	                }
	                // 網址
	                if(list.contains("url") && StringUtils.isNotEmpty(jsonItem.getString("url"))) {
	                	adFreeVO.setUrl(jsonItem.getString("url"));
	                }
	                // 廣告內容
	                if(list.contains("content") && StringUtils.isNotEmpty(jsonItem.getString("content"))) {
	                	adFreeVO.setContent(jsonItem.getString("content"));
	                }
	                // 是用逗號分隔的
	                if(list.contains("tags") && StringUtils.isNotEmpty(jsonItem.getString("tags"))) {
	                	adFreeVO.setTags(jsonItem.getString("tags"));
	                }
	                // 主分類
	                if(list.contains("first_class") && StringUtils.isNotEmpty(jsonItem.getString("first_class"))) {
	                	adFreeVO.setFirst_class(jsonItem.getString("first_class"));
	                }
	                // 次分類
	                if(list.contains("sec_class") && StringUtils.isNotEmpty(jsonItem.getString("sec_class"))) {
	                	adFreeVO.setSec_class(jsonItem.getString("sec_class"));
	                }
	                // 廣告檔案名稱
	                if(list.contains("ad_name") && StringUtils.isNotEmpty(jsonItem.getString("ad_name"))) {
	                	adFreeVO.setAd_name(jsonItem.getString("ad_name"));
	                }

	                // 廣告走期開始日期
	                if(list.contains("st_date") && CheckDate(jsonItem.getString("st_date"))) {
	                	adFreeVO.setSt_date(jsonItem.getString("st_date"));
	                	adFreeVO.setdSt_date(adUtils.DateFormat(jsonItem.getString("st_date")));
	                }

	                // 廣告走期結束日期
	                if(list.contains("ed_date") && CheckDate(jsonItem.getString("ed_date"))) {
	                	adFreeVO.setEd_date(jsonItem.getString("ed_date"));
	                	adFreeVO.setdEd_date(adUtils.DateFormat(jsonItem.getString("ed_date")));
	                	adFreeVO.setHaveEndDate("Y");
	                } else {
	                	adFreeVO.setSt_date("3000-12-31");
	                	adFreeVO.setdSt_date(adUtils.DateFormat("3000-12-31"));
	                	adFreeVO.setHaveEndDate("N");
	                }
	                adFreeVOs.put(jsonItem.getString("ad_no"), adFreeVO);
	            }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    	return adFreeVOs;
    }
}
