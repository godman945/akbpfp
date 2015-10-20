package com.pchome.akbpfp.struts2.action.report;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.pchome.akbpfp.struts2.BaseSSLAction;
import com.pchome.enumerate.ad.EnumAdType;
import com.pchome.enumerate.utils.EnumStatus;

public class BaseReportAction extends BaseSSLAction {

	private static final long serialVersionUID = 1L;

	public static final String FILE_TYPE = ".csv";

	//就是 ad_type 前人用這名字
	public Map<String, String> getAdShowWayMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();

		for (EnumAdType adType : EnumAdType.values()) {
			map.put(Integer.toString(adType.getType()), adType.getChName());
		}

		return map;
	}

	public Map<String, String> getReportTypeMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("adtype_activity", "廣告");
		map.put("adtype_group", "分類");
		map.put("adtype_keyword", "關鍵字");
		map.put("adtype_ad", "廣告明細");
		return map;
	}

	public Map<String, String> getAdSearchWayMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("adsearch_include", "字詞包含");
		map.put("adsearch_begin", "開頭文字是");
		map.put("adsearch_same", "完全符合");
		return map;
	}

	public List<String> getPageSizeList() {
		List<String> list = new ArrayList<String>();
		list.add("10");
		list.add("20");
		list.add("30");
		return list;
	}

	public Map<String, String> getAdStatusMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();

		for (EnumStatus status : EnumStatus.values()) {
			map.put(Integer.toString(status.getStatusId()), status.getStatusRemark());
		}

		return map;
	}

	public Map<String, String> getAdPvclkDeviceMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("", "裝置");
		map.put("PC", "電腦");
		map.put("mobile", "行動裝置");
		return map;
	}

	public Map<String, String> getAdPvclkOsMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("all", "全部");
		map.put("IOS", "IOS");
		map.put("Android", "Android");
		map.put("Windows", "Windows");
		map.put("", "其他");
		return map;
	}
}
