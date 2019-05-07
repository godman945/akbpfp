package com.pchome.akbpfp.struts2.action.report;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.pchome.akbpfp.struts2.BaseSSLAction;
import com.pchome.enumerate.ad.EnumAdStyleType;
import com.pchome.enumerate.utils.EnumStatus;
import com.pchome.soft.depot.utils.DateValueUtil;

public class BaseReportAction extends BaseSSLAction {

	private static final long serialVersionUID = 1L;

	public static final String FILE_TYPE = ".csv";

	/**
	 * 查詢日期的 rang map,查詢日期頁面顯示
	 * @return
	 */
	public LinkedHashMap<String, String> getDateSelectMap() {
		return DateValueUtil.getInstance().getDateRangeMap();
	}
	
	
	//就是 ad_type 前人用這名字
	public Map<String, String> getAdShowWayMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("0", "全部播放類型");
		map.put("1", "搜尋廣告");
		map.put("2", "聯播網廣告");

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
		list.add("20");
		list.add("50");
		list.add("100");
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
		map.put("", "全部裝置");
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
	
	public Map<String,String> getAdStyleTypeMap(){
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("", "全部廣告樣式");
		for(EnumAdStyleType enumAdStyleType:EnumAdStyleType.values()){
			map.put(enumAdStyleType.getTypeName(), enumAdStyleType.getType());
		}
		
		return map;
	}
}
