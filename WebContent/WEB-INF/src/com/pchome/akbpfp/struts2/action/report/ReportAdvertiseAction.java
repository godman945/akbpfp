package com.pchome.akbpfp.struts2.action.report;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.pchome.akbpfp.db.dao.ad.IPfpAdActionDAO;
import com.pchome.akbpfp.db.dao.ad.IPfpAdDAO;
import com.pchome.akbpfp.db.dao.ad.IPfpAdGroupDAO;
import com.pchome.akbpfp.db.dao.report.AdReportVO;
import com.pchome.akbpfp.db.pojo.PfpAd;
import com.pchome.akbpfp.db.pojo.PfpAdAction;
import com.pchome.akbpfp.db.service.customerInfo.IPfpCustomerInfoService;
import com.pchome.akbpfp.db.service.report.IAdReportService;
import com.pchome.enumerate.ad.EnumAdType;
import com.pchome.enumerate.report.EnumReport;
import com.pchome.enumerate.utils.EnumStatus;
import com.pchome.soft.depot.utils.SpringOpenFlashUtil;
import com.pchome.soft.util.DateValueUtil;

public class ReportAdvertiseAction extends BaseReportAction {

	private static final long serialVersionUID = -8461736631135913196L;

	private LinkedList<String> tableHeadList =null; //頁面table head

	private LinkedList<LinkedList<String>> tableDataList =null; // 頁面 table data
	private LinkedList<String> tableDataTotalList =null; //頁面全部加總table total foot


	private List<AdReportVO> adReportVOList;
	private IAdReportService adReportService=null;
	private IPfpCustomerInfoService customerInfoService=null;

	private IPfpAdActionDAO pfpAdActionDAO;
	private IPfpAdGroupDAO pfpAdGroupDAO;
	private IPfpAdDAO pfpAdDAO;

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
	private String adShowWay="";//廣告顯示位址,一般,內容
	private String searchId="";//廣告id ,某活動,某群組id
	private String adOperatingRule;//廣告樣式
	private String searchAdseq="";
	
	private String charPic="";//圖表格式
	private String charType="";//度量

	//download report 
	private String downloadFlag="";//download report 旗標

	private InputStream downloadFileStream;//下載報表的 input stream

	private String downloadFileName;//下載顯示名

	private String flashData;//flash chart json data
	
	private String reportTitle;
	
	private String stepStr = "";
	
	private String adName = "";

	NumberFormat doubleFormat = new DecimalFormat("###,###,###,###.###");
	
	public String flashDataDownLoad() throws Exception {

		//查詢日期寫進 cookie
		this.setChooseDate(startDate, endDate);

		log.info("charPic="+charPic);//flash 樣式
		log.info("charType="+charType);//資料
		log.info("searchId="+searchId);//資料
		log.info("chstartDate="+startDate);
		log.info("chendDate="+endDate);

		if(searchText.equals("Null")){
			searchText="";
		}

		if(searchAdseq.equals("Null")){
			searchAdseq = "";
		}
		
		String customerInfoId = super.getCustomer_info_id();
		log.info(">>> customerInfoId = " + customerInfoId);

		List<AdReportVO> resultData = adReportService.loadReportDate(EnumReport.REPORT_HQLTYPE_ADVERTISE_CHART.getTextValue(),
				searchId, searchAdseq, searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, adOperatingRule, startDate, endDate,-1,-1);

		Map<Date,Float> flashDataMap=new HashMap<Date,Float>();


		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		for (int i=0; i<resultData.size(); i++) {
			double pv = 0;
			double click = 0;
			double cost = 0;
			double invClick = 0;
			double ctr = 0;
			double costAvg = 0;
			double kiloCost = 0;

			AdReportVO adReportVO = resultData.get(i);

			Date reportDate = dateFormat.parse(adReportVO.getReportDate());
			pv = Double.parseDouble(adReportVO.getAdPvSum());
			click = Double.parseDouble(adReportVO.getAdClkSum());
			cost = Double.parseDouble(adReportVO.getAdPriceSum());
			cost = new BigDecimal(String.valueOf(cost)).setScale(3, BigDecimal.ROUND_FLOOR).doubleValue();
			invClick = Double.parseDouble(adReportVO.getAdInvClkSum());

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
			
			if (charType.equals(EnumReport.REPORT_CHART_TYPE_PV.getTextValue())) {
				flashDataMap.put(reportDate, new Float((float) pv));
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_CLICK.getTextValue())) {
				flashDataMap.put(reportDate, new Float((float) click));
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_CTR.getTextValue())) {
				flashDataMap.put(reportDate, new Float((float) ctr));
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_INVALID.getTextValue())) {
				flashDataMap.put(reportDate, new Float((float) invClick));
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_AVGCOST.getTextValue())) {
				flashDataMap.put(reportDate, new Float((float) costAvg));
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_KILOCOST.getTextValue())) {
				flashDataMap.put(reportDate, new Float((float) kiloCost));
            } else if (charType.equals(EnumReport.REPORT_CHART_TYPE_COST.getTextValue())) {
            	flashDataMap.put(reportDate, new Float(doubleFormat.format(cost)));
			}
		}

		flashData = openFlashUtil.getChartDataForArray(charType, startDate, endDate, flashDataMap);

		return SUCCESS;
	}

	public ReportAdvertiseAction() {

		reportTitle="廣告明細成效";
		
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

		String customerInfoId=super.getCustomer_info_id();
		log.info(">>> customerInfoId = " + customerInfoId);
		log.info("adPvclkDevice = " + adPvclkDevice);
		log.info("adSearchWay = " + adSearchWay);
		log.info("adShowWay = " + adShowWay);

		tableHeadList=new LinkedList<String>();//table head
		tableDataList=new LinkedList<LinkedList<String>>();//table data

		//自訂欄位選擇
		if(StringUtils.isNotEmpty(optionSelect)){

			String data[]=optionSelect.split(",");
			for(String s:data){
				tableHeadShowList.addLast(s);
			}
		}

		//自訂欄位移除
		if(StringUtils.isNotEmpty(optionNotSelect)){
			String data[]=optionNotSelect.split(",");
			for(String s:data){
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

		if(StringUtils.isBlank(adSearchWay)){
			adSearchWay=EnumReport.ADSEARCH_INCLUDE.getTextValue();
		}

		if(StringUtils.isBlank(adShowWay)){
			adShowWay=Integer.toString(EnumAdType.AD_ALL.getType());
		}

		log.info(">>> adShowWay = " + adShowWay);
		log.info(">>> adSearchWay = " + adSearchWay);
		log.info(">>> searchText = " + searchText);
		log.info(">>> startDate = " + startDate);
		log.info(">>> endDate = " + endDate);

		dateSelectMap=DateValueUtil.getInstance().getDateRangeMap();

		//輸入欄位順序
		if(!tableHeadShowList.isEmpty()){
			for(String s:tableHeadShowList){

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

		//downloadReport

		log.info("downloadFlag="+downloadFlag);

		if(downloadFlag.trim().equals("yes")){
			page=-1;
		}
		
		tableHeadList.addFirst("裝置");
		tableHeadList.addFirst("計價方式");
		tableHeadList.addFirst("廣告樣式");
		tableHeadList.addFirst("播放類型");
		
		
		//根據searchAdseq判斷是否查看第二層-每日成效
		if(StringUtils.isNotBlank(searchAdseq)){
			tableHeadList.addFirst("日期");
		} else {
			tableHeadList.addFirst("廣告");
			tableHeadList.addFirst("分類");
			if(downloadFlag.equals("yes")){
				tableHeadList.addFirst("實際連結");
				tableHeadList.addFirst("顯示連結");
				tableHeadList.addFirst("廣告內容");
				tableHeadList.addFirst("廣告名稱");
			} else {
				tableHeadList.addFirst("廣告明細");
			}
			tableHeadList.addFirst("狀態");
		}
		
		if(StringUtils.isBlank(searchAdseq) && !downloadFlag.equals("yes")){
			tableHeadList.addLast("每日成效");
		}

		List<AdReportVO> resultSumData = adReportService.loadReportDate(EnumReport.REPORT_HQLTYPE_ADVERTISE_COUNT.getTextValue(),
				searchId, searchAdseq, searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, adOperatingRule, startDate, endDate, -1, -1);

		int totalDataSize = resultSumData.size();

		adReportVOList = adReportService.loadReportDate(EnumReport.REPORT_HQLTYPE_ADVERTISE.getTextValue(),
				searchId, searchAdseq, searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, adOperatingRule, startDate, endDate, page, pageSize);

		this.totalPage = (totalDataSize/pageSize);

		if((totalDataSize%pageSize)>0){
			totalPage+=1;
		}

		if (totalDataSize > 0) {
			resultDataTrans(adReportVOList);
			resultSumDataTrans(resultSumData);
		}

		if(downloadFlag.trim().equals("yes")){
			log.info("makeDownloadReportData");
			makeDownloadReportData();
		}

		log.info("time="+new Date().toString());

		return SUCCESS;
	}

	/**
	 * 點擊下載報表
	 * */
	private void makeDownloadReportData() throws Exception {

		SimpleDateFormat dformat = new SimpleDateFormat("yyyyMMddhhmmss");
		String filename="廣告明細成效報表_" + dformat.format(new Date()) + FILE_TYPE;
		StringBuffer content = new StringBuffer();
		//報表名稱
		downloadFileName = new String(filename.getBytes("UTF-8"), "ISO8859-1");
		content.append("帳戶," + super.getCustomer_info_title());
		content.append("\n\n");
		
		if(StringUtils.isNotBlank(searchAdseq)){
			content.append("報表名稱,PChome 廣告明細成效->每日花費報表");
			content.append("\n\n");
			content.append("廣告明細,"+adReportVOList.get(0).getTitle());
		}else{
			content.append("報表名稱,PChome 廣告明細成效");	
		}
		
		content.append("\n\n");
		content.append("播放類型," + getAdShowWayMap().get(adShowWay));
		content.append("\n");
		content.append("廣告樣式," + getAdStyleTypeMap().get(adOperatingRule));
		content.append("\n");
		content.append("裝置," + getAdPvclkDeviceMap().get(adPvclkDevice));
		content.append("\n");
		content.append("搜尋條件," + getAdSearchWayMap().get(adSearchWay));
		content.append("\n");
		content.append("搜尋內容," + searchText);
		content.append("\n");
		content.append("日期範圍," + startDate + " 到 " + endDate);
		content.append("\n\n");
		
		//查看每日成效
		if(StringUtils.isNotBlank(searchAdseq)){
			content.append(""
					+ "日期,"
					+ "播放類型,"
					+ "廣告樣式,"
					+ "計價方式,"
					+ "裝置,"
					+ "曝光數,"
					+ "互動數,"
					+ "互動率,"
					+ "單次互動費用,"
					+ "千次曝光費用,"
					+ "費用");
		}else{
			content.append(""
					+ "狀態,"
					+ "廣告名稱,"
					+ "廣告內容,"
					+ "影片長度,"
					+ "顯示連結,"
					+ "實際連結,"
					+ "分類,"
					+ "廣告,"
					+ "播放類型,"
					+ "廣告樣式,"
					+ "計價方式,"
					+ "裝置,"
					+ "曝光數,"
					+ "互動數,"
					+ "互動率,"
					+ "單次互動費用,"
					+ "千次曝光費用,"
					+ "費用");
		}
		
		
		
		//列表值
		float sumPV = 0;
		float sumClick = 0;
		//總計total值
		double totalCost = 0;
		double sumSingleAdCost = 0;
		
		//列表
		DecimalFormat df = new DecimalFormat("#.##");
		for (AdReportVO adReportVO : adReportVOList) {
			sumPV = sumPV + Float.valueOf(adReportVO.getAdPvSum());
			sumClick = sumClick + Float.valueOf(adReportVO.getAdClkSum());
			double clickAvg = (Double.valueOf(adReportVO.getAdClkSum()) / Double.valueOf(adReportVO.getAdPvSum())) * 100;
			totalCost = totalCost + Double.valueOf(adReportVO.getAdPriceSum());
			totalCost = (new BigDecimal(String.valueOf(totalCost)).setScale(3, BigDecimal.ROUND_FLOOR)).doubleValue();
			double thousandsCost = Double.parseDouble(adReportVO.getAdPriceSum()) / (Double.parseDouble(adReportVO.getAdPvSum()) / 1000);
			
			double singleCost = 0;
			if(Double.parseDouble(adReportVO.getAdClkSum()) != 0){
				singleCost =  Double.parseDouble(adReportVO.getAdPriceSum()) / Double.parseDouble(adReportVO.getAdClkSum());
			}
			sumSingleAdCost = (singleCost + singleCost);
			
			content.append("\n");
			//searchAdseq有值查看每日成效
			if(StringUtils.isNotBlank(searchAdseq)){
				content.append(adReportVO.getReportDate()+",");
				content.append(adReportVO.getAdType()+",");
				content.append(adReportVO.getAdOperatingRuleDesc()+",");
				content.append(adReportVO.getAdClkPriceType()+",");
				content.append(adReportVO.getAdDevice()+",");
			}else{
				content.append(adReportVO.getAdStatusDesc()+",");
				content.append(StringUtils.isBlank(adReportVO.getTitle()) ? "," : adReportVO.getTitle().trim() + ",");
				
				if(adReportVO.getAdOperatingRule().equals("VIDEO")){
					content.append(adReportVO.getContent()+",");
					content.append(adReportVO.getAdVideoSec()+",");
					content.append(adReportVO.getAdVideoUrl()+",");
				}else{
					content.append(adReportVO.getContent()+",");
					content.append(",");
					content.append(adReportVO.getShowUrl()+",");
				}
				content.append(adReportVO.getRealUrl()+",");
				content.append(adReportVO.getAdGroupName()+",");
				content.append(adReportVO.getAdActionName()+",");
				content.append(adReportVO.getAdType()+",");
				content.append(adReportVO.getAdOperatingRuleDesc()+",");
				content.append(adReportVO.getAdClkPriceType()+",");
				content.append(adReportVO.getAdDevice()+",");
			}
			content.append(adReportVO.getAdPvSum()+",");
			content.append(adReportVO.getAdClkSum()+",");
			content.append(df.format(clickAvg)+"%,");
			content.append(singleCost+",");
			content.append(thousandsCost+",");
			content.append("=\""+Double.parseDouble(adReportVO.getAdPriceSum())+"\"");
		}
		//總計
		content.append("\n\n");
		if(StringUtils.isNotBlank(searchAdseq)){
			content.append(""
					+ "總計:共"+adReportVOList.size()+"筆"+","
					+ ","
					+ ","
					+ ","
					+ ","
					+ sumPV+","
					+ sumClick+","
					+ df.format((sumClick / sumPV) * 100)+"%,"
					+ (totalCost / sumClick)+","
					+ totalCost / (sumPV / 1000)+","
					+ "=\""+totalCost+"\"");
		}else{
			content.append(""
					+ "總計:共"+adReportVOList.size()+"筆"+","
					+ ","
					+ ","
					+ ","
					+ ","
					+ ","
					+ ","
					+ ","
					+ ","
					+ ","
					+ ","
					+ ","
					+ sumPV+","
					+ sumClick+","
					+ df.format((sumClick/sumPV)*100)+"%,"
					+ (totalCost / sumClick)+","
					+ totalCost / (sumPV / 1000)+","
					+ "=\""+totalCost+"\"");
		}
		downloadFileStream = new ByteArrayInputStream(content.toString().getBytes("big5"));
	}

	private void resultSumDataTrans(List<AdReportVO> resultSumData) {

		NumberFormat intFormat = new DecimalFormat("###,###,###,###");
		NumberFormat doubleFormat = new DecimalFormat("###,###,###,###.##");
		NumberFormat doubleFormat2 = new DecimalFormat("###,###,###,###.###");

		tableDataTotalList = new LinkedList<String>();
		tableDataTotalList.add("");
		tableDataTotalList.add("總計：" + intFormat.format(resultSumData.size()));
		tableDataTotalList.add("");
		tableDataTotalList.add("");
		tableDataTotalList.add("");
		if(StringUtils.isBlank(searchAdseq)){
			tableDataTotalList.add("");
			tableDataTotalList.add("");
			tableDataTotalList.add("");
			if(downloadFlag.equals("yes")){
				tableDataTotalList.add("");
				tableDataTotalList.add("");
				tableDataTotalList.add("");
			}
		}

		double t_pv = 0; //總曝光數
		double t_click = 0; //總互動數
		double t_ctr = 0; //互動率
		double t_invalid = 0; //無效點選次數
		double t_costAvg = 0; //單次互動費用
		double t_kiloCost = 0;	//千次曝光費用
		double t_cost = 0; //總費用

		//加總
		for (int i=0; i<resultSumData.size(); i++) {
			AdReportVO adReportVO = resultSumData.get(i);
			t_pv += new Double(adReportVO.getAdPvSum());
			t_click += new Double(adReportVO.getAdClkSum());
			t_cost += new Double(adReportVO.getAdPriceSum());
			t_invalid += new Double(adReportVO.getAdInvClkSum());
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
				if (mapKey.trim().equals(EnumReport.REPORT_CHART_TYPE_PV.getTextValue())) {
					tableDataTotalList.addLast(intFormat.format(t_pv));
				} else if (mapKey.trim().equals(EnumReport.REPORT_CHART_TYPE_CLICK.getTextValue())) {
					tableDataTotalList.addLast(intFormat.format(t_click));
				} else if (mapKey.trim().equals(EnumReport.REPORT_CHART_TYPE_CTR.getTextValue())) {
					tableDataTotalList.addLast(doubleFormat.format(t_ctr));
				} else if (mapKey.trim().equals(EnumReport.REPORT_CHART_TYPE_INVALID.getTextValue())) {
					tableDataTotalList.addLast(intFormat.format(t_invalid));
				} else if (mapKey.trim().equals(EnumReport.REPORT_CHART_TYPE_AVGCOST.getTextValue())) {
					tableDataTotalList.addLast(doubleFormat.format(t_costAvg));
				} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_KILOCOST.getTextValue())) {
					tableDataTotalList.addLast(doubleFormat.format(t_kiloCost));
				} else if (mapKey.trim().equals(EnumReport.REPORT_CHART_TYPE_COST.getTextValue())) {
					tableDataTotalList.addLast(doubleFormat2.format(t_cost));
				}
			}
		}
		
		if(StringUtils.isBlank(searchAdseq) && !downloadFlag.equals("yes")){
			tableDataTotalList.add("");
		}
	}

	private void resultDataTrans(List<AdReportVO> resultData) {

		LinkedList<String> tableInDataList;

		String adPreview = "";
		String adActionName = "";
		String adGroupName = "";
		int adActionStatus = 0;
		int adStatus = 0;
		String adDevice = "";
		String adType = "";
		String adPvclkDate = "";
		adName = "";
		
		/*廣告明細成效第二層*/
		StringBuffer stepStrBuffer=new StringBuffer();
		stepStrBuffer.append("<a href=\"javascript:void(0);\" onclick=\"ajaxFormSubmit();\" >廣告明細成效</a>");
		stepStrBuffer.append("&nbsp; >> &nbsp;");
		stepStrBuffer.append("每日花費： ");

		NumberFormat intFormat = new DecimalFormat("###,###,###,###");
		NumberFormat doubleFormat = new DecimalFormat("###,###,###,###.##");
		NumberFormat doubleFormat2 = new DecimalFormat("###,###,###,###.###");

		for (int i=0; i<resultData.size(); i++) {
			AdReportVO adReportVO = resultData.get(i);
			double pv = 0;
			double click = 0;
			double cost = 0;
			double invClick = 0;
			double ctr = 0;
			double costAvg = 0;
			double kiloCost = 0;
			tableInDataList = new LinkedList<String>();
			adActionName = adReportVO.getAdActionName();
			adGroupName = adReportVO.getAdGroupName();
			adPreview = adReportVO.getAdPreview();
			adDevice = adReportVO.getAdDevice();
			adType = adReportVO.getAdType();
			adPvclkDate = adReportVO.getAdPvclkDate();
			adName = adReportVO.getTitle();
			String adOperatingRuleName = adReportVO.getAdOperatingRuleDesc();
			String adClkPriceTypeName = adReportVO.getAdClkPriceType();
			pv = new Double(adReportVO.getAdPvSum());
			click = new Double(adReportVO.getAdClkSum());
			cost = (new BigDecimal(String.valueOf(adReportVO.getAdPriceSum())).setScale(3, BigDecimal.ROUND_FLOOR)).doubleValue();
			invClick = new Double(adReportVO.getAdInvClkSum());
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
			PfpAdAction pfpAdAction = new PfpAdAction();
			try {
				pfpAdAction = pfpAdActionDAO.getPfpAdActionBySeq(adReportVO.getAdActionSeq());
				adActionName = pfpAdAction.getAdActionName();
				adActionStatus = pfpAdAction.getAdActionStatus();
			} catch (Exception e) {
				e.printStackTrace();
			}

			PfpAd pfpAd = new PfpAd();
			try {
				pfpAd = pfpAdDAO.getPfpAdBySeq(adReportVO.getAdSeq());
				adStatus = pfpAd.getAdStatus();
			} catch (Exception e) {
				e.printStackTrace();
			}

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");

			long nowTime = new Date().getTime();

			//廣告狀態為開啟的話必須判斷走期( 待播放 or 走期中 or 已結束 )
			try {
				String adActionStartDate = dateFormat2.format(pfpAdAction.getAdActionStartDate());
				String adActionEndDate = dateFormat2.format(pfpAdAction.getAdActionEndDate());
				if (adActionStatus == EnumStatus.Open.getStatusId()) {
					long _startDate = (dateFormat.parse(adActionStartDate + " 00:00:00")).getTime();
					long _endDate = (dateFormat.parse(adActionEndDate + " 23:59:59")).getTime();
					if (nowTime < _startDate) {
						adActionStatus = EnumStatus.Waitbroadcast.getStatusId();
						adReportVO.setAdStatusDesc(EnumStatus.Waitbroadcast.getStatusDesc());
					} else if (nowTime > _endDate) {
						adActionStatus = EnumStatus.End.getStatusId();
						adReportVO.setAdStatusDesc(EnumStatus.End.getStatusDesc());
					} else {
						adActionStatus = EnumStatus.Broadcast.getStatusId();
						adReportVO.setAdStatusDesc(EnumStatus.Broadcast.getStatusDesc());
					}
				}

			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			
			//播放狀態
			String alter = "";
			String icon = "icon_adclose.gif";
			if (adActionStatus == EnumStatus.Broadcast.getStatusId() &&	adReportVO.getAdGroupStatus() == EnumStatus.Open.getStatusId() && adStatus == EnumStatus.Open.getStatusId()) {
				alter = "走期中";
				icon = "icon_adopen.gif";
				adReportVO.setAdStatusDesc(alter);

			} else if (adActionStatus == EnumStatus.Broadcast.getStatusId() &&
					adReportVO.getAdGroupStatus() == EnumStatus.Open.getStatusId() &&
					adStatus != EnumStatus.Open.getStatusId()) {
					alter = "廣告明細" + getAdStatusMap().get(Integer.toString(adStatus));;

			} else if (adActionStatus == EnumStatus.Broadcast.getStatusId() &&
					adReportVO.getAdGroupStatus() != EnumStatus.Open.getStatusId() &&
					adStatus != EnumStatus.Open.getStatusId()) {
					alter = "分類" + getAdStatusMap().get(Integer.toString(adReportVO.getAdGroupStatus())) + "，廣告明細" + getAdStatusMap().get(Integer.toString(adStatus));

			} else if (adActionStatus == EnumStatus.Broadcast.getStatusId() &&
					adReportVO.getAdGroupStatus() != EnumStatus.Open.getStatusId() &&
					adStatus == EnumStatus.Open.getStatusId()) {
					alter = "分類" + getAdStatusMap().get(Integer.toString(adReportVO.getAdGroupStatus()));

			} else if (adActionStatus != EnumStatus.Broadcast.getStatusId() &&
					adReportVO.getAdGroupStatus() == EnumStatus.Open.getStatusId() &&
					adStatus == EnumStatus.Open.getStatusId()) {
					alter = "廣告" + getAdStatusMap().get(Integer.toString(adActionStatus));

			} else if (adActionStatus != EnumStatus.Broadcast.getStatusId() &&
					adReportVO.getAdGroupStatus() == EnumStatus.Open.getStatusId() &&
					adStatus != EnumStatus.Open.getStatusId()) {
					alter = "廣告" + getAdStatusMap().get(Integer.toString(adActionStatus)) + "，廣告明細" + getAdStatusMap().get(Integer.toString(adStatus));
			
			} else if (adActionStatus != EnumStatus.Broadcast.getStatusId() &&
					adReportVO.getAdGroupStatus() != EnumStatus.Open.getStatusId() &&
					adStatus == EnumStatus.Open.getStatusId()) {
					alter = "廣告" + getAdStatusMap().get(Integer.toString(adActionStatus)) + "，分類" + getAdStatusMap().get(Integer.toString(adReportVO.getAdGroupStatus()));

			} else if (adActionStatus != EnumStatus.Broadcast.getStatusId() &&
					adReportVO.getAdGroupStatus() != EnumStatus.Open.getStatusId() &&
					adStatus != EnumStatus.Open.getStatusId()) {
					alter = "廣告" + getAdStatusMap().get(Integer.toString(adActionStatus)) + "，分類" + getAdStatusMap().get(Integer.toString(adReportVO.getAdGroupStatus())) + "，廣告明細" + getAdStatusMap().get(Integer.toString(adStatus));

			}
			
			if(downloadFlag.equals("yes")){

				if(StringUtils.isNotBlank(searchAdseq)){
					tableInDataList.addLast(adPvclkDate);
				} else {
					tableInDataList.addLast(alter);
					tableInDataList.addLast(adReportVO.getTitle());
					tableInDataList.addLast(adReportVO.getContent());
					tableInDataList.addLast(adReportVO.getShowUrl());
					tableInDataList.addLast(adReportVO.getRealUrl());
					tableInDataList.addLast(adGroupName);
					tableInDataList.addLast(adActionName);
				}
				tableInDataList.addLast(adType);
				tableInDataList.addLast(adOperatingRuleName);
				tableInDataList.addLast(adClkPriceTypeName);
				tableInDataList.addLast(adDevice);
			} else {
				if(StringUtils.isNotBlank(searchAdseq)){
					tableInDataList.addLast(adPvclkDate);
				} else {
					tableInDataList.addLast("<img src=\"./html/img/" + icon + "\" alt=\"" + alter + "\" title=\"" + alter + "\">");
					tableInDataList.addLast(adPreview);
					tableInDataList.addLast(adGroupName);
					tableInDataList.addLast(adActionName);
				}
				tableInDataList.addLast(adType);
				tableInDataList.addLast(adOperatingRuleName);
				tableInDataList.addLast(adClkPriceTypeName);
				tableInDataList.addLast(adDevice);
			}

			if(!tableHeadShowList.isEmpty()){
				String mapKey;
				for(String s:tableHeadShowList){
					mapKey=tableHeadNameMap.get(s);
					if (mapKey.trim().equals(EnumReport.REPORT_CHART_TYPE_PV.getTextValue())) {
						tableInDataList.addLast(intFormat.format(pv));
					} else if (mapKey.trim().equals(EnumReport.REPORT_CHART_TYPE_CLICK.getTextValue())) {
						tableInDataList.addLast(intFormat.format(click));
					} else if (mapKey.trim().equals(EnumReport.REPORT_CHART_TYPE_CTR.getTextValue())) {
						tableInDataList.addLast(doubleFormat.format(ctr));
					} else if (mapKey.trim().equals(EnumReport.REPORT_CHART_TYPE_INVALID.getTextValue())) {
						tableInDataList.addLast(intFormat.format(invClick));
					} else if (mapKey.trim().equals(EnumReport.REPORT_CHART_TYPE_AVGCOST.getTextValue())) {
						tableInDataList.addLast(doubleFormat.format(costAvg));
					} else if (mapKey.trim().equals(EnumReport.REPORT_CHART_TYPE_KILOCOST.getTextValue())) {
						tableInDataList.addLast(doubleFormat.format(kiloCost));
					} else if (mapKey.trim().equals(EnumReport.REPORT_CHART_TYPE_COST.getTextValue())) {
						tableInDataList.addLast(doubleFormat2.format(cost));
					}
				}
			}

			if(StringUtils.isBlank(searchAdseq) && !downloadFlag.equals("yes")){
				String detailQuery = "<a style=\"cursor:pointer\" onclick=\"detailQuery('" + pfpAd.getAdSeq() + "')\" >查看</a>";
				tableInDataList.addLast(detailQuery);
			}
			
			tableDataList.addLast(tableInDataList);
		}
		
		
		if(StringUtils.isNotBlank(searchAdseq)){
			PfpAd pfpAd = pfpAdDAO.get(searchAdseq);
			adName = pfpAd.getPfpAdGroup().getPfpAdAction().getAdActionName();
			stepStrBuffer.append(adName);
			stepStr = stepStrBuffer.toString();
		}
	}

	public void setPfpAdActionDAO(IPfpAdActionDAO pfpAdActionDAO) {
		this.pfpAdActionDAO = pfpAdActionDAO;
	}

	public void setPfpAdGroupDAO(IPfpAdGroupDAO pfpAdGroupDAO) {
		this.pfpAdGroupDAO = pfpAdGroupDAO;
	}

	public void setPfpAdDAO(IPfpAdDAO pfpAdDAO) {
		this.pfpAdDAO = pfpAdDAO;
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

	public void setAdReportService(IAdReportService adReportService) {
		this.adReportService = adReportService;
	}

	public LinkedList<String> getTableDataTotalList() {
		return tableDataTotalList;
	}

	public String getReportTitle() {
		return reportTitle;
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

	public String getSearchAdseq() {
		return searchAdseq;
	}

	public void setSearchAdseq(String searchAdseq) {
		this.searchAdseq = searchAdseq;
	}

	public String getStepStr() {
		return stepStr;
	}

	public String getAdOperatingRule() {
		return adOperatingRule;
	}

	public void setAdOperatingRule(String adOperatingRule) {
		this.adOperatingRule = adOperatingRule;
	}
	
}
