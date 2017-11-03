package com.pchome.akbpfp.struts2.action.report;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
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
import org.json.JSONArray;

import com.pchome.enumerate.ad.EnumAdType;
import com.pchome.enumerate.report.EnumReport;
import com.pchome.enumerate.utils.EnumStatus;
import com.pchome.akbpfp.db.dao.report.AdAgesexReportVO;
import com.pchome.akbpfp.db.pojo.PfpAdGroup;
import com.pchome.akbpfp.db.pojo.PfpCustomerInfo;
import com.pchome.akbpfp.db.service.ad.IPfpAdGroupService;
import com.pchome.akbpfp.db.service.customerInfo.IPfpCustomerInfoService;
import com.pchome.akbpfp.db.service.report.IAdAgesexReportService;
import com.pchome.soft.util.DateValueUtil;
import com.pchome.soft.depot.utils.SpringOpenFlashUtil;

public class ReportAdAgesexAction extends BaseReportAction {

	private static final long serialVersionUID = 1L;

	private LinkedList<String> tableHeadList =null; //頁面table head

	// 欄位
	private String[] align_data = {"center", "center", "center", "center", "center", "center", "center", "center", "right", "right", "right", "right", "right", "right"};
	private String[] align_sum = {"center", "center", "center", "center", "center", "center", "center", "center", "right", "right", "right", "right", "right", "right"};

	private LinkedList<LinkedList<String>> tableDataList =null; // 頁面 table data
	private LinkedList<String> tableDataTotalList =null; //頁面全部加總table total foot

	private IAdAgesexReportService adAgesexReportService = null;
	private IPfpAdGroupService adGroupService = null;
	private IPfpCustomerInfoService customerInfoService = null;

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

	//download report 
	private String downloadFlag="";//download report 旗標

	private InputStream downloadFileStream;//下載報表的 input stream

	private String downloadFileName;//下載顯示名

	private String flashData;//flash chart json data
	
	private String reportTitle;
	
	private String searchAgesex = "A";

	public String flashDataDownLoad() throws Exception {

		//查詢日期寫進 cookie
		this.setChooseDate(startDate, endDate);

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

		List<AdAgesexReportVO> resultData = adAgesexReportService.loadReportDate(EnumReport.REPORT_HQLTYPE_AGESEX_CHART.getTextValue(),searchAgesex,searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, adOperatingRule, startDate, endDate, -1, -1);

		List<Double> dataList = new ArrayList<Double>();
		
		//性別
		String sex = "";
		double sexN = 0;		//未知
		double sexM = 0;		//男性
		double sexF = 0;		//女性
		
		//年齡
		String ageCode = "";
		double ageA = 0;		//18歲(不含)以下
		double ageB = 0;		//18歲~24歲
		double ageC = 0;		//25歲~34歲
		double ageD = 0;		//35歲~44歲
		double ageE = 0;		//45歲~54歲
		double ageF = 0;		//55歲~64歲
		double ageG = 0;		//65歲~74歲
		double ageH = 0;		//75歲以上
		double ageI = 0;		//未知
		
		double total = 0;		//總合

		for (int i=0; i<resultData.size(); i++) {

			AdAgesexReportVO vo = resultData.get(i);

			sex = vo.getSex();
			ageCode = vo.getAge();

			if (charType.equals(EnumReport.REPORT_CHART_TYPE_PV.getTextValue())) {
				
				total += vo.getAdPvSum().doubleValue();
				
				if(StringUtils.equals(searchAgesex, "S")){
					switch(sex) {
						case "M":
							sexM = vo.getAdPvSum().doubleValue();
							break;
						case "F":
							sexF = vo.getAdPvSum().doubleValue();
							break;
						default:
							sexN = vo.getAdPvSum().doubleValue();
							break;
					}
				} else {
					switch(ageCode) {
					case "A":
						ageA = vo.getAdPvSum().doubleValue();
						break;
					case "B":
						ageB = vo.getAdPvSum().doubleValue();
						break;
					case "C":
						ageC = vo.getAdPvSum().doubleValue();
						break;
					case "D":
						ageD = vo.getAdPvSum().doubleValue();
						break;
					case "E":
						ageE = vo.getAdPvSum().doubleValue();
						break;
					case "F":
						ageF = vo.getAdPvSum().doubleValue();
						break;
					case "G":
						ageG = vo.getAdPvSum().doubleValue();
						break;
					case "H":
						ageH = vo.getAdPvSum().doubleValue();
						break;
					default:
						ageI = vo.getAdPvSum().doubleValue();
						break;
					}
				}
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_CLICK.getTextValue())) {

				//total += vo.getAdClkSum().doubleValue();
				
				if(StringUtils.equals(searchAgesex, "S")){
					switch(sex) {
						case "M":
							sexM = vo.getAdClkSum().doubleValue();
							break;
						case "F":
							sexF = vo.getAdClkSum().doubleValue();
							break;
						default:
							sexN = vo.getAdClkSum().doubleValue();
							break;
					}
				} else {
					switch(ageCode) {
					case "A":
						ageA = vo.getAdClkSum().doubleValue();
						break;
					case "B":
						ageB = vo.getAdClkSum().doubleValue();
						break;
					case "C":
						ageC = vo.getAdClkSum().doubleValue();
						break;
					case "D":
						ageD = vo.getAdClkSum().doubleValue();
						break;
					case "E":
						ageE = vo.getAdClkSum().doubleValue();
						break;
					case "F":
						ageF = vo.getAdClkSum().doubleValue();
						break;
					case "G":
						ageG = vo.getAdClkSum().doubleValue();
						break;
					case "H":
						ageH = vo.getAdClkSum().doubleValue();
						break;
					default:
						ageI = vo.getAdClkSum().doubleValue();
						break;
					}
				}
			}
		}

		if(StringUtils.equals(searchAgesex, "S")){
			total = sexM + sexF;
			if(total != 0){
				sexM = Double.parseDouble(String.format("%.2f",(sexM/total)*100));
				//sexF = Double.parseDouble(String.format("%.2f",(sexF/total)*100));
				//sexN = 100 - sexM - sexF;
				sexF = 100 - sexM;
				
				dataList.add(sexM);
				dataList.add(sexF);
				//dataList.add(sexN);
			} else {
				dataList.add(new Double(0));
				dataList.add(new Double(0));
				//dataList.add(new Double(0));
			}
		} else {
			total = ageA + ageB + ageC + ageD + ageE + ageF + ageG + ageH;
			if(total != 0){
				dataList.add((ageA/total)*100);
				dataList.add((ageB/total)*100);
				dataList.add((ageC/total)*100);
				dataList.add((ageD/total)*100);
				dataList.add((ageE/total)*100);
				dataList.add((ageF/total)*100);
				dataList.add((ageG/total)*100);
				dataList.add((ageH/total)*100);
				//dataList.add((ageI/total)*100);	
			} else {
				dataList.add(new Double(0));
				dataList.add(new Double(0));
				dataList.add(new Double(0));
				dataList.add(new Double(0));
				dataList.add(new Double(0));
				dataList.add(new Double(0));
				dataList.add(new Double(0));
				dataList.add(new Double(0));
				//dataList.add(new Double(0));
			}
		}
		
		JSONArray array = new JSONArray(dataList);
		
		flashData = array.toString();

		return SUCCESS;
	}

	public ReportAdAgesexAction() {

		reportTitle="廣告族群成效";
		
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
		String timeName = "年齡";
		if(StringUtils.equals("S", searchAgesex)){
			timeName = "性別";
		} 
		tableHeadList.addFirst("計價方式");
		tableHeadList.addFirst("廣告樣式");
		tableHeadList.addFirst("播放類型");
		tableHeadList.addFirst(timeName);
		tableHeadList.addFirst("分類");
		tableHeadList.addFirst("廣告");
		tableHeadList.addFirst("狀態");

		List<AdAgesexReportVO> resultSumData = adAgesexReportService.loadReportDate(EnumReport.REPORT_HQLTYPE_AGESEX_COUNT.getTextValue(), searchAgesex, searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, adOperatingRule, startDate, endDate, -1, -1);

		int totalPageSize = resultSumData.size();

		List<AdAgesexReportVO> resultData = adAgesexReportService.loadReportDate(EnumReport.REPORT_HQLTYPE_AGESEX.getTextValue(), searchAgesex, searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, adOperatingRule, startDate, endDate, page, pageSize);

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

		String filename="廣告族群成效報表_" + dformat.format(new Date()) + FILE_TYPE;

		StringBuffer content=new StringBuffer();

		log.info(">>> customerInfoId = " + super.getCustomer_info_id());
		PfpCustomerInfo customerInfo = customerInfoService.findCustomerInfo(super.getCustomer_info_id());

		content.append("帳戶," + customerInfo.getCustomerInfoTitle());
		content.append("\n\n");
		content.append("報表名稱,PChome 廣告族群成效");
		content.append("\n\n");
		content.append("播放類型," + getAdShowWayMap().get(adShowWay));
		content.append("\n");
		content.append("廣告樣式," + getAdStyleTypeMap().get(adOperatingRule));
		content.append("\n");
		content.append("統計變項," + getAdShowWayMap().get(adShowWay));
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
				if(dataNumber == 12 || dataNumber == 13 || dataNumber == 14){
					content.append("\"NT$ " + s + "\"");
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
				if(dataTotalNumber == 12 || dataTotalNumber == 13 || dataTotalNumber == 14){
					content.append("\"NT$ " + s + "\"");
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

	private void resultSumDataTrans(List<AdAgesexReportVO> resultSumData) throws Exception {

		NumberFormat intFormat = new DecimalFormat("###,###,###,###");
		NumberFormat doubleFormat = new DecimalFormat("###,###,###,###.##");

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

			AdAgesexReportVO vo = resultSumData.get(i);

			t_pv += vo.getAdPvSum().doubleValue();
			t_click += vo.getAdClkSum().doubleValue();
			t_cost += Math.round(vo.getAdPriceSum().doubleValue());
			t_invalid += vo.getAdInvClkSum().doubleValue();
		}

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
					tableDataTotalList.addLast(intFormat.format(t_cost));
				}
			}
		}
	}

	private void resultDataTrans(List<AdAgesexReportVO> resultData) throws Exception {

		LinkedList<String> tableInDataList;

        NumberFormat intFormat = new DecimalFormat("###,###,###,###");
		NumberFormat doubleFormat = new DecimalFormat("###,###,###,###.##");

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");

		long nowTime = new Date().getTime();

		for (int i=0; i<resultData.size(); i++) {

			tableInDataList = new LinkedList<String>();

			AdAgesexReportVO vo = resultData.get(i);

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
			String age = vo.getAge();
			String sex = vo.getSex();
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

			if(StringUtils.equals("S", searchAgesex)){
				tableInDataList.addLast(sex);
			} else {
				tableInDataList.addLast(age);
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
						tableInDataList.addLast(intFormat.format(cost));
					}
				}
			}

			tableDataList.addLast(tableInDataList);
		}
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

	public void setAdAgesexReportService(IAdAgesexReportService adAgesexReportService) {
		this.adAgesexReportService = adAgesexReportService;
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

	public String getSearchAgesex() {
		return searchAgesex;
	}

	public void setSearchAgesex(String searchAgesex) {
		this.searchAgesex = searchAgesex;
	}

	public String getAdOperatingRule() {
		return adOperatingRule;
	}

	public void setAdOperatingRule(String adOperatingRule) {
		this.adOperatingRule = adOperatingRule;
	}
	
}
