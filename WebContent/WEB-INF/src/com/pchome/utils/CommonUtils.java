package com.pchome.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CommonUtils {

	protected static final Log log = LogFactory.getLog(CommonUtils.class);

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

}
