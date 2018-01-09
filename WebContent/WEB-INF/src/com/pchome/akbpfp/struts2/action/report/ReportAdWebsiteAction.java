package com.pchome.akbpfp.struts2.action.report;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONArray;

import com.pchome.akbpfp.db.dao.report.AdWebsiteReportVO;
import com.pchome.akbpfp.db.pojo.PfpAdGroup;
import com.pchome.akbpfp.db.pojo.PfpCustomerInfo;
import com.pchome.akbpfp.db.service.ad.IPfbxWebsiteCategoryService;
import com.pchome.akbpfp.db.service.ad.IPfpAdGroupService;
import com.pchome.akbpfp.db.service.customerInfo.IPfpCustomerInfoService;
import com.pchome.akbpfp.db.service.report.IAdWebsiteReportService;
import com.pchome.enumerate.ad.EnumAdType;
import com.pchome.enumerate.report.EnumReport;
import com.pchome.enumerate.utils.EnumStatus;
import com.pchome.soft.depot.utils.SpringOpenFlashUtil;
import com.pchome.soft.util.DateValueUtil;

public class ReportAdWebsiteAction extends BaseReportAction {

	private static final long serialVersionUID = 1L;

	private LinkedList<String> tableHeadList =null; //頁面table head

	// 欄位
	private String[] align_data = {"center", "center", "center", "center", "center", "center", "center", "center", "right", "right", "right", "right", "right", "right"};
	private String[] align_sum = {"center", "center", "center", "center", "center", "center", "center", "center", "right", "right", "right", "right", "right", "right"};

	private LinkedList<LinkedList<String>> tableDataList =null; // 頁面 table data
	private LinkedList<String> tableDataTotalList =null; //頁面全部加總table total foot

	private IAdWebsiteReportService adWebsiteReportService = null;
	private IPfpAdGroupService adGroupService = null;
	private IPfpCustomerInfoService customerInfoService = null;
	private IPfbxWebsiteCategoryService pfbxWebsiteCategoryService;

	private SpringOpenFlashUtil openFlashUtil=null;

	private Map<String,String> tableHeadNameMap;//自訂欄位

	private LinkedList<String> tableHeadShowList =null;//自訂欄位選擇list,頁面顯示
	private LinkedList<String> tableHeadNotShowList =null;//自訂欄位不選擇list,頁面顯示

	private String optionSelect="";//自訂欄位選擇,頁面回傳
	private String optionNotSelect="";//自訂欄位不選擇,頁面回傳

	private String startDate="";//查詢開始日期
	private String endDate="";//查詢結束日期

	private LinkedHashMap<String,String> dateSelectMap=null;//查詢日期的 rang map,查詢日期頁面顯示

	private int page=0;//第幾頁
	private int pageSize=0;//每頁筆數
	private int totalPage=0;//總頁數

	private String adPvclkDevice=""; //裝置:pc、mobile
	private String adType="";// 廣告類型,活動,群組,關鍵字
	private String adSearchWay="";//文字搜尋方式,包含,開頭,全部
	private String searchText="";//搜尋文字
	private String adShowWay="0";//廣告顯示位址,一般,內容
	private String searchId="";//廣告id ,某活動,某群組id
	private String adOperatingRule;//廣告樣式
	
	private String charPic="";//圖表格式
	private String charType="";//度量
	
	private Map<String,String> reportWebsiteCategoryMap;

	//download report 
	private String downloadFlag="";//download report 旗標

	private InputStream downloadFileStream;//下載報表的 input stream

	private String downloadFileName;//下載顯示名

	private String flashData;//flash chart json data
	
	private String reportTitle;
	
	private String searchWebsiteCode = "";
	private NumberFormat doubleFormat = new DecimalFormat("###,###,###,###.###");
	
	public String flashDataDownLoad() throws Exception {

		//查詢日期寫進 cookie
		this.setChooseDate(startDate, endDate);
		reportWebsiteCategoryMap = new LinkedHashMap<String,String>();
		reportWebsiteCategoryMap = getWebsiteCategoryMap();

		log.info("charPic="+charPic);//flash 樣式
		log.info("charType="+charType);//資料
		log.info("searchId="+searchId);//資料
		log.info("chstartDate="+startDate);
		log.info("chendDate="+endDate);

		if(searchText.equals("Null")) {
			searchText = "";
		}

		if(adOperatingRule.equals("Null")) {
			adOperatingRule = "";
		}
		
		String customerInfoId = super.getCustomer_info_id();
		log.info(">>> customerInfoId = " + customerInfoId);

		List<AdWebsiteReportVO> resultData = adWebsiteReportService.loadReportDate(EnumReport.REPORT_HQLTYPE_WEBSITE_CHART.getTextValue(),searchWebsiteCode,searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, adOperatingRule, startDate, endDate, -1, -1);

		List<String> titleDataList = new ArrayList<String>();
		List<Double> dataList = new ArrayList<Double>();
		
		for (int i=0; i<resultData.size(); i++) {

			AdWebsiteReportVO vo = resultData.get(i);
			double pv = 0;
			double click = 0;
			double cost = 0;
			double invClick = 0;
			double ctr = 0;
			double costAvg = 0;
			double kiloCost = 0;
			String websiteCategoryName = "";

			pv = vo.getAdPvSum().doubleValue();
			click = vo.getAdClkSum().doubleValue();
			cost = vo.getAdPriceSum().doubleValue();
			cost = new BigDecimal(String.valueOf(cost)).setScale(3, BigDecimal.ROUND_FLOOR).doubleValue();
			invClick = vo.getAdInvClkSum().doubleValue();

			if(StringUtils.isNotEmpty(vo.getWebsiteCategoryCode()) && reportWebsiteCategoryMap.get(vo.getWebsiteCategoryCode()) != null){
				websiteCategoryName = reportWebsiteCategoryMap.get(vo.getWebsiteCategoryCode());
			} else {
				websiteCategoryName = "未分類";
			}
			
			//互動率 = 互動次數 / 曝光數
			if (pv>0 && click>0) {
				ctr = (click / pv) * 100;
			}

			//單次互動費用 = 總費用 / 總互動次數
			if (cost>0 && click>0) {
				costAvg = cost / click;
			}

			//千次曝光費用 = 總費用*1000 / 曝光數
			if(cost>0 && pv>0){
				 kiloCost = (cost * 1000) / pv;
			}

			double data = 0;
			if (charType.equals(EnumReport.REPORT_CHART_TYPE_PV.getTextValue())) {
				data = pv;
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_CLICK.getTextValue())) {
				data = click;
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_CTR.getTextValue())) {
				data = ctr;
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_INVALID.getTextValue())) {
				data = invClick;
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_AVGCOST.getTextValue())) {
				data = costAvg;
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_KILOCOST.getTextValue())) {
				data = kiloCost;
            } else if (charType.equals(EnumReport.REPORT_CHART_TYPE_COST.getTextValue())) {
				data = Double.valueOf(doubleFormat.format(cost));
			}
			
			titleDataList.add(websiteCategoryName);
			dataList.add(data);
		}
		
		List<Object> ObjectList = new ArrayList<Object>();
		ObjectList.add(titleDataList);
		ObjectList.add(dataList);
		
		JSONArray array = new JSONArray(ObjectList);
		
		flashData = array.toString();

		return SUCCESS;
	}

	public ReportAdWebsiteAction() {

		reportTitle="網站類型成效";
		
		downloadFlag="no";

		tableHeadNameMap=new HashMap<String,String>();
		tableHeadNameMap.put("曝光數", EnumReport.REPORT_CHART_TYPE_PV.getTextValue());
		tableHeadNameMap.put("互動數", EnumReport.REPORT_CHART_TYPE_CLICK.getTextValue());
		tableHeadNameMap.put("互動率", EnumReport.REPORT_CHART_TYPE_CTR.getTextValue());
		// 20140318： 隱藏 "無效點選次數" 欄位
		//tableHeadNameMap.put("無效點選次數", EnumReport.REPORT_CHART_TYPE_INVALID.getTextValue());
		tableHeadNameMap.put("單次互動費用", EnumReport.REPORT_CHART_TYPE_AVGCOST.getTextValue());
		tableHeadNameMap.put("千次曝光費用", EnumReport.REPORT_CHART_TYPE_KILOCOST.getTextValue());
		tableHeadNameMap.put("費用", EnumReport.REPORT_CHART_TYPE_COST.getTextValue());
		
		optionSelect="";
		optionNotSelect="";

		//optionSelect="曝光數,點選率(%),點選次數,無效點選次數,平均點選費用,費用";
		// 20140318： 隱藏 "無效點選次數" 欄位
		optionSelect="曝光數,互動數,互動率,單次互動費用,千次曝光費用,費用";

		tableHeadShowList=new LinkedList<String>();

		tableHeadNotShowList=new LinkedList<String>();

	}

	public String execute() throws Exception {

		String customerInfoId = super.getCustomer_info_id();
		log.info(">>> customerInfoId = " + customerInfoId);

		reportWebsiteCategoryMap = new LinkedHashMap<String,String>();
		reportWebsiteCategoryMap = getWebsiteCategoryMap();
		
		tableHeadList = new LinkedList<String>();//table head
		tableDataList = new LinkedList<LinkedList<String>>();//table data

		//自訂欄位選擇
		if (StringUtils.isNotEmpty(optionSelect)) {

			String data[] = optionSelect.split(",");
			for (String s:data) {
				tableHeadShowList.addLast(s);
			}
		}

		//自訂欄位移除
		if (StringUtils.isNotEmpty(optionNotSelect)) {
			String data[] = optionNotSelect.split(",");
			for (String s:data) {
				tableHeadNotShowList.addLast(s);
			}
		}

		log.info(">>> startDate = " + startDate);
		log.info(">>> endDate = " + endDate);

		String startDate_cookie = this.getChoose_start_date();
		String endDate_cookie = this.getChoose_end_date();

		log.info(">>> startDate_cookie = " + startDate_cookie);
		log.info(">>> endDate_cookie = " + endDate_cookie);

		if (StringUtils.isEmpty(startDate)) {
			if (StringUtils.isNotEmpty(startDate_cookie)) {
				startDate = startDate_cookie;
			} else {
				startDate=DateValueUtil.getInstance().dateToString(DateValueUtil.getInstance().getDateForStartDateAddDay(DateValueUtil.getInstance().getDateValue(DateValueUtil.TODAY, DateValueUtil.DBPATH), -30));
			}
		}

		if (StringUtils.isEmpty(endDate)) {
			if (StringUtils.isNotEmpty(endDate_cookie)) {
				endDate = endDate_cookie;
			} else {
				endDate=DateValueUtil.getInstance().getDateValue(DateValueUtil.TODAY, DateValueUtil.DBPATH);
			}
		}

		log.info(">>>>>> startDate = " + startDate);
		log.info(">>>>>> endDate = " + endDate);

		if (StringUtils.isEmpty(adType)) {
			adType = EnumReport.ADTYPE_ACTIVITY.getTextValue();
		}

		if (StringUtils.isEmpty(adSearchWay)) {
			adSearchWay = EnumReport.ADSEARCH_INCLUDE.getTextValue();
		}

		if (StringUtils.isEmpty(adShowWay)) {
			adShowWay = Integer.toString(EnumAdType.AD_ALL.getType());
		}

		log.info("startDate="+startDate);
		log.info("endDate="+endDate);
		log.info("adType="+adType);
		log.info("adSearchWay="+adSearchWay);
		log.info("adShowWay="+adShowWay);
		log.info("adOperatingRule="+adOperatingRule);

		dateSelectMap = DateValueUtil.getInstance().getDateRangeMap();

		//輸入欄位順序
		if (!tableHeadShowList.isEmpty()) {
			for (String s:tableHeadShowList) {
				tableHeadList.addLast(s);
			}
		}

		if(page==0){
			page=1;
		}
		log.info("page="+page);

		if(pageSize==0){
			pageSize=20;
		}
		log.info("pageSize="+pageSize);

		log.info("downloadFlag="+downloadFlag);

		if(downloadFlag.trim().equals("yes")){
			page=-1;
		}

		tableHeadList.addFirst("裝置");
		tableHeadList.addFirst("計價方式");
		tableHeadList.addFirst("廣告樣式");
		tableHeadList.addFirst("播放類型");
		tableHeadList.addFirst("網站分類");
		tableHeadList.addFirst("分類");
		tableHeadList.addFirst("廣告");
		tableHeadList.addFirst("狀態");

		List<AdWebsiteReportVO> resultSumData = adWebsiteReportService.loadReportDate(EnumReport.REPORT_HQLTYPE_WEBSITE_COUNT.getTextValue(), searchWebsiteCode, searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, adOperatingRule, startDate, endDate, -1, -1);

		int totalPageSize = resultSumData.size();

		List<AdWebsiteReportVO> resultData = adWebsiteReportService.loadReportDate(EnumReport.REPORT_HQLTYPE_WEBSITE.getTextValue(), searchWebsiteCode, searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, adOperatingRule, startDate, endDate, page, pageSize);

		totalPage = totalPageSize/pageSize;

		if((totalPageSize%pageSize)>0){
			totalPage+=1;
		}

		if(resultSumData.size()>0){
			resultDataTrans(resultData);
			resultSumDataTrans(resultSumData);
		}

		if(downloadFlag.trim().equals("yes")){
			makeDownloadReportData();
		}

		return SUCCESS;
	}

	private void makeDownloadReportData() throws Exception {

		SimpleDateFormat dformat = new SimpleDateFormat("yyyyMMddhhmmss");

		String filename="網站類型成效報表_" + dformat.format(new Date()) + FILE_TYPE;

		StringBuffer content=new StringBuffer();

		log.info(">>> customerInfoId = " + super.getCustomer_info_id());
		PfpCustomerInfo customerInfo = customerInfoService.findCustomerInfo(super.getCustomer_info_id());

		content.append("帳戶," + customerInfo.getCustomerInfoTitle());
		content.append("\n\n");
		content.append("報表名稱,PChome 網站類型成效");
		content.append("\n\n");
		content.append("播放類型," + getAdShowWayMap().get(adShowWay));
		content.append("\n");
		content.append("廣告樣式," + getAdStyleTypeMap().get(adOperatingRule));
		content.append("\n");
		String searchWebsiteName = "全部類型";
		if(StringUtils.isNotEmpty(searchWebsiteCode)){
			searchWebsiteName = getWebsiteCategoryMap().get(searchWebsiteCode);
		}
		content.append("網站類型," + searchWebsiteName);
		content.append("\n");
		content.append("裝置," + getAdPvclkDeviceMap().get(adPvclkDevice));
		content.append("\n");
		content.append("搜尋條件," + getAdSearchWayMap().get(adSearchWay));
		content.append("\n");
		content.append("搜尋內容," + searchText);
		content.append("\n");
		content.append("日期範圍," + startDate + " 到 " + endDate);
		content.append("\n\n");

		for(String s:tableHeadList){
			content.append("\"" + s + "\"");
			content.append(",");
		}
		content.append("\n");

		for(LinkedList<String> sl:tableDataList){
			int dataNumber = 1;
			for(String s:sl){
				if(dataNumber == 12 || dataNumber == 13|| dataNumber == 14){
					content.append(StringEscapeUtils.escapeCsv("=\"NT$ " + s + "\""));
				} else if(dataNumber == 11){
					content.append("\"" + s + "%\"");
				} else {
					content.append("\"" + s + "\"");	
				}
				content.append(",");
				dataNumber++;
			}
			content.append("\n");
		}
		content.append("\n");

		if (tableDataTotalList!=null) {
			int dataTotalNumber = 1;
			for(String s:tableDataTotalList){
				if(dataTotalNumber == 12 || dataTotalNumber == 13|| dataTotalNumber == 14){
					content.append(StringEscapeUtils.escapeCsv("=\"NT$ " + s + "\""));
				} else if(dataTotalNumber == 11){
					content.append("\"" + s + "%\"");
				} else {
					content.append("\"" + s + "\"");
				}
				content.append(",");
				dataTotalNumber++;
			}
			content.append("\n");
		}

		if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
			downloadFileName = new String(filename.getBytes("UTF-8"), "ISO8859-1");
		} else {
			downloadFileName = URLEncoder.encode(filename, "UTF-8");			
		}

		downloadFileStream = new ByteArrayInputStream(content.toString().getBytes("big5"));

	}

	private void resultSumDataTrans(List<AdWebsiteReportVO> resultSumData) throws Exception {

		NumberFormat intFormat = new DecimalFormat("###,###,###,###");
		NumberFormat doubleFormat = new DecimalFormat("###,###,###,###.##");
		NumberFormat doubleFormat2 = new DecimalFormat("###,###,###,###.###");

		tableDataTotalList = new LinkedList<String>();
		tableDataTotalList.add("");
		tableDataTotalList.add("總計：" + intFormat.format(resultSumData.size()));
		tableDataTotalList.add("");
		tableDataTotalList.add("");
		tableDataTotalList.add("");
		tableDataTotalList.add("");
		tableDataTotalList.add("");
		tableDataTotalList.add("");

		double t_pv = 0; //總曝光數
		double t_click = 0; //總互動數
		double t_ctr = 0; //互動率
		double t_invalid = 0; //無效點選次數
		double t_costAvg = 0; //單次互動費用
		double t_kiloCost = 0;	//千次曝光費用
		double t_cost = 0; //總費用

		//加總
		for (int i=0; i<resultSumData.size(); i++) {
			AdWebsiteReportVO vo = resultSumData.get(i);
			t_pv += vo.getAdPvSum().doubleValue();
			t_click += vo.getAdClkSum().doubleValue();
			t_cost += vo.getAdPriceSum().doubleValue();
			t_invalid += vo.getAdInvClkSum().doubleValue();
		}
		t_cost = new BigDecimal(String.valueOf(t_cost)).setScale(3, BigDecimal.ROUND_FLOOR).doubleValue();
		
		//互動率 = 總互動次數 / 總曝光數
		if (t_pv>0 && t_click>0) {
			t_ctr = (t_click / t_pv) * 100;
		}

		//單次互動費用 = 總費用 / 總互動次數
		if (t_cost>0 && t_click>0) {
			t_costAvg = t_cost / t_click;
		}
		
		//千次曝光費用 = 總費用*1000 / 曝光數
		if(t_cost>0 && t_pv>0){
			t_kiloCost = (t_cost * 1000) / t_pv;
		}
		
		if (!tableHeadShowList.isEmpty()) {
			String mapKey;
			for (String s: tableHeadShowList) {
				mapKey = tableHeadNameMap.get(s);
				if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_PV.getTextValue())) {
					tableDataTotalList.addLast(intFormat.format(t_pv));
				} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_CLICK.getTextValue())) {
					tableDataTotalList.addLast(intFormat.format(t_click));
				} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_CTR.getTextValue())) {
					tableDataTotalList.addLast(doubleFormat.format(t_ctr));
				} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_INVALID.getTextValue())) {
					tableDataTotalList.addLast(intFormat.format(t_invalid));
				} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_AVGCOST.getTextValue())) {
					tableDataTotalList.addLast(doubleFormat.format(t_costAvg));
				} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_KILOCOST.getTextValue())) {
					tableDataTotalList.addLast(doubleFormat.format(t_kiloCost));
				} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_COST.getTextValue())) {
					tableDataTotalList.addLast(doubleFormat2.format(t_cost));
				}
			}
		}
	}

	private void resultDataTrans(List<AdWebsiteReportVO> resultData) throws Exception {

		LinkedList<String> tableInDataList;

        NumberFormat intFormat = new DecimalFormat("###,###,###,###");
		NumberFormat doubleFormat = new DecimalFormat("###,###,###,###.##");
		NumberFormat doubleFormat2 = new DecimalFormat("###,###,###,###.###");

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");

		long nowTime = new Date().getTime();

		for (int i=0; i<resultData.size(); i++) {

			tableInDataList = new LinkedList<String>();

			AdWebsiteReportVO vo = resultData.get(i);

			String adGroupSeq = vo.getAdGroupSeq();

			PfpAdGroup pfpAdGroup = adGroupService.getPfpAdGroupBySeq(adGroupSeq);

			String adGroupName = pfpAdGroup.getAdGroupName();
			int adGroupStatus = pfpAdGroup.getAdGroupStatus();

			String adActionName = pfpAdGroup.getPfpAdAction().getAdActionName();
			int adActionStatus = pfpAdGroup.getPfpAdAction().getAdActionStatus();
			Date adActionStartDate = pfpAdGroup.getPfpAdAction().getAdActionStartDate();
			Date adActionEndDate = pfpAdGroup.getPfpAdAction().getAdActionEndDate();

			double pv = vo.getAdPvSum().doubleValue();
			double click = vo.getAdClkSum().doubleValue();
			double cost = vo.getAdPriceSum().doubleValue();
			double invClick = vo.getAdInvClkSum().doubleValue();
			double ctr = 0;
			double costAvg = 0;
			double kiloCost = 0;
			String adDevice = vo.getAdDevice();
			String adTypeName = vo.getAdType();
			String adOperatingRuleName = vo.getAdOperatingRule();
			String adClkPriceTypeName = vo.getAdClkPriceType();

			//互動率 = 互動次數 / 曝光數
			if (pv>0 && click>0) {
				ctr = (click / pv) * 100;
			}

			//單次互動費用 = 總費用 / 總互動次數
			if (cost>0 && click>0) {
				costAvg = cost / click;
			}

			//千次曝光費用 = 總費用*1000 / 曝光數
			if(cost>0 && pv>0){
				kiloCost = (cost * 1000) / pv;
			}


			//廣告狀態為開啟的話必須判斷走期( 待播放 or 走期中 or 已結束 )
			if (adActionStatus == EnumStatus.Open.getStatusId()) {
				long _startDate = (dateFormat.parse(dateFormat2.format(adActionStartDate) + " 00:00:00")).getTime();
				long _endDate = (dateFormat.parse(dateFormat2.format(adActionEndDate) + " 23:59:59")).getTime();
				if (nowTime < _startDate) {
					adActionStatus = EnumStatus.Waitbroadcast.getStatusId();
				} else if (nowTime > _endDate) {
					adActionStatus = EnumStatus.End.getStatusId();
				} else {
					adActionStatus = EnumStatus.Broadcast.getStatusId();
				}
			}

			//播放狀態
			String alter = "";
			String icon = "icon_adclose.gif";
			if (adActionStatus == EnumStatus.Broadcast.getStatusId() &&
					adGroupStatus == EnumStatus.Open.getStatusId()) {

				alter = "走期中";
				icon = "icon_adopen.gif";

			} else if (adActionStatus != EnumStatus.Broadcast.getStatusId() &&
					adGroupStatus == EnumStatus.Open.getStatusId()) {

				alter = "廣告" + getAdStatusMap().get(Integer.toString(adActionStatus));

			} else if (adActionStatus == EnumStatus.Broadcast.getStatusId() &&
					adGroupStatus != EnumStatus.Open.getStatusId()) {

				alter = "分類" + getAdStatusMap().get(Integer.toString(adGroupStatus));

			} else {

				alter = "廣告" + getAdStatusMap().get(Integer.toString(adActionStatus)) + "，" + "分類" + getAdStatusMap().get(Integer.toString(adGroupStatus));
				
			}
			
			if(downloadFlag.equals("yes")){
				tableInDataList.addLast(alter);
			} else {
				tableInDataList.addLast("<img src=\"./html/img/" + icon + "\" alt=\"" + alter + "\" title=\"" + alter + "\">");
			}
			
			tableInDataList.addLast(adActionName);
			tableInDataList.addLast(adGroupName);
			
			if(StringUtils.isNotEmpty(vo.getWebsiteCategoryCode()) && reportWebsiteCategoryMap.get(vo.getWebsiteCategoryCode()) != null){
				tableInDataList.addLast(reportWebsiteCategoryMap.get(vo.getWebsiteCategoryCode()));
			} else {
				tableInDataList.addLast("未分類");
			}
			
			tableInDataList.addLast(adTypeName);
			tableInDataList.addLast(adOperatingRuleName);
			tableInDataList.addLast(adClkPriceTypeName);
			tableInDataList.addLast(adDevice);

			if(!tableHeadShowList.isEmpty()){
				String mapKey;
				for(String s:tableHeadShowList){
					mapKey=tableHeadNameMap.get(s);
					if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_PV.getTextValue())) {
						tableInDataList.addLast(intFormat.format(pv));
					} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_CLICK.getTextValue())) {
						tableInDataList.addLast(intFormat.format(click));
 					} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_CTR.getTextValue())) {
						tableInDataList.addLast(doubleFormat.format(ctr));
					} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_INVALID.getTextValue())) {
 						tableInDataList.addLast(intFormat.format(invClick));
 					} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_AVGCOST.getTextValue())) {
						tableInDataList.addLast(doubleFormat.format(costAvg));
					} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_KILOCOST.getTextValue())) {
						tableInDataList.addLast(doubleFormat.format(kiloCost));
					} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_COST.getTextValue())) {
						tableInDataList.addLast(doubleFormat2.format(cost));
					}
				}
			}

			tableDataList.addLast(tableInDataList);
		}
	}

	public Map<String, String> getWebsiteCategoryMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map = pfbxWebsiteCategoryService.getPfbxWebsiteCategoryNameMap();
		return map;
	}
	
	public LinkedList<String> getTableHeadList() {
		return tableHeadList;
	}

	public LinkedList<LinkedList<String>> getTableDataList() {
		return tableDataList;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public String getOptionSelect() {
		return optionSelect;
	}

	public void setOptionSelect(String optionSelect) {
		this.optionSelect = optionSelect;
	}

	public String getOptionNotSelect() {
		return optionNotSelect;
	}

	public void setOptionNotSelect(String optionNotSelect) {
		this.optionNotSelect = optionNotSelect;
	}

	public LinkedList<String> getTableHeadShowList() {
		return tableHeadShowList;
	}

	public LinkedList<String> getTableHeadNotShowList() {
		return tableHeadNotShowList;
	}

	public String getAdPvclkDevice() {
		return adPvclkDevice;
	}

	public void setAdPvclkDevice(String adPvclkDevice) {
		this.adPvclkDevice = adPvclkDevice;
	}

	public String getAdType() {
		return adType;
	}

	public void setAdType(String adType) {
		this.adType = adType;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public String getAdShowWay() {
		return adShowWay;
	}

	public void setAdShowWay(String adShowWay) {
		this.adShowWay = adShowWay;
	}

	public String getAdSearchWay() {
		return adSearchWay;
	}

	public void setAdSearchWay(String adSearchWay) {
		this.adSearchWay = adSearchWay;
	}

	public String getSearchId() {
		return searchId;
	}

	public void setSearchId(String searchId) {
		this.searchId = searchId;
	}

	public InputStream getDownloadFileStream() {
		return downloadFileStream;
	}

	public String getDownloadFileName() {
		return downloadFileName;
	}

	public String getDownloadFlag() {
		return downloadFlag;
	}

	public void setDownloadFlag(String downloadFlag) {
		log.info("value set="+downloadFlag);
		this.downloadFlag = downloadFlag;
	}

	public void setCustomerInfoService(IPfpCustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}

	public void setOpenFlashUtil(SpringOpenFlashUtil openFlashUtil) {
		this.openFlashUtil = openFlashUtil;
	}

	public String getFlashData() {
		return flashData;
	}

	public LinkedHashMap<String, String> getDateSelectMap() {
		return dateSelectMap;
	}

	public LinkedList<String> getTableDataTotalList() {
		return tableDataTotalList;
	}

	public String getReportTitle() {
		return reportTitle;
	}

	public void setAdWebsiteReportService(IAdWebsiteReportService adWebsiteReportService) {
		this.adWebsiteReportService = adWebsiteReportService;
	}

	public String[] getAlign_data() {
		return align_data;
	}

	public String[] getAlign_sum() {
		return align_sum;
	}

	public void setAdGroupService(IPfpAdGroupService adGroupService) {
		this.adGroupService = adGroupService;
	}

	public void setPfbxWebsiteCategoryService(IPfbxWebsiteCategoryService pfbxWebsiteCategoryService) {
		this.pfbxWebsiteCategoryService = pfbxWebsiteCategoryService;
	}

	public String getCharPic() {
		return charPic;
	}

	public void setCharPic(String charPic) {
		this.charPic = charPic;
	}

	public String getCharType() {
		return charType;
	}

	public void setCharType(String charType) {
		this.charType = charType;
	}

	public String getSearchWebsiteCode() {
		return searchWebsiteCode;
	}

	public void setSearchWebsiteCode(String searchWebsiteCode) {
		this.searchWebsiteCode = searchWebsiteCode;
	}

	public Map<String, String> getReportWebsiteCategoryMap() {
		return reportWebsiteCategoryMap;
	}

	public String getAdOperatingRule() {
		return adOperatingRule;
	}

	public void setAdOperatingRule(String adOperatingRule) {
		this.adOperatingRule = adOperatingRule;
	}

}
