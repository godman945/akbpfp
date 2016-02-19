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

import com.pchome.enumerate.ad.EnumAdType;
import com.pchome.enumerate.report.EnumReport;
import com.pchome.enumerate.utils.EnumStatus;
import com.pchome.akbpfp.db.dao.ad.IPfpAdActionDAO;
import com.pchome.akbpfp.db.dao.ad.IPfpAdGroupDAO;
import com.pchome.akbpfp.db.dao.ad.IPfpAdKeywordDAO;
import com.pchome.akbpfp.db.dao.report.AdKeywordReportVO;
import com.pchome.akbpfp.db.pojo.PfpAdAction;
import com.pchome.akbpfp.db.pojo.PfpAdGroup;
import com.pchome.akbpfp.db.pojo.PfpAdKeyword;
import com.pchome.akbpfp.db.pojo.PfpCustomerInfo;
import com.pchome.akbpfp.db.service.customerInfo.IPfpCustomerInfoService;
import com.pchome.akbpfp.db.service.report.IAdKeywordReportService;
import com.pchome.soft.util.DateValueUtil;
import com.pchome.soft.depot.utils.SpringOpenFlashUtil;

public class ReportKeywordAction extends BaseReportAction {

	private static final long serialVersionUID = -8461736631135913196L;

	private LinkedList<String> tableHeadList =null; //頁面table head

	private LinkedList<LinkedList<String>> tableDataList =null; // 頁面 table data
	private LinkedList<String> tableDataTotalList =null; //頁面全部加總table total foot

	//private String[] align_data = {"center", "left", "left", "left", "center", "right", "right", "right", "right", "right", "right", "right"};
	//private String[] align_sum = {"center", "center", "left", "left", "center", "right", "right", "right", "right", "right", "right", "right"};
	// 20140318： 隱藏 "無效點選次數" 欄位
	private String[] align_data = {"center", "left", "left", "left", "center", "right", "right", "right", "right", "right", "right"};
	private String[] align_sum = {"center", "center", "left", "left", "center", "right", "right", "right", "right", "right", "right"};

	private List<AdKeywordReportVO> AdKeywordReportVO;
	private AdKeywordReportVO AdKeywordReportDataTotal;
	
	private IAdKeywordReportService adKeywordReportService=null;
	private IPfpCustomerInfoService customerInfoService=null;

	private IPfpAdActionDAO pfpAdActionDAO;
	private IPfpAdGroupDAO pfpAdGroupDAO;
	private IPfpAdKeywordDAO pfpAdKeywordDAO;

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
	private String adType = ""; // 廣告類型,活動,群組,關鍵字
	private String adSearchWay = ""; //文字搜尋方式,包含,開頭,全部
	private String searchText = ""; //搜尋文字
	private String adShowWay = ""; //廣告顯示位址,一般,內容
	private String searchId = ""; //廣告id ,某活動,某群組id
	
	private String charPic="";//圖表格式
	private String charType="";//度量

	//download report 
	private String downloadFlag="";//download report 旗標

	private InputStream downloadFileStream;//下載報表的 input stream

	private String downloadFileName;//下載顯示名

	private String flashData;//flash chart json data
	
	private String reportTitle;

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

		String customerInfoId=super.getCustomer_info_id();
		log.info(">>> customerInfoId = " + customerInfoId);

		List<AdKeywordReportVO> resultData = adKeywordReportService.loadReportDate(EnumReport.REPORT_HQLTYPE_EXCERPT_CHART.getTextValue(),
				searchId, searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, startDate, endDate, -1, -1);
		System.out.println("resultData.size() = " + resultData.size());

		List<Map<Date,Float>> mapList = new ArrayList<Map<Date,Float>>();
		Map<Date,Float> flashDataMap=new HashMap<Date,Float>();
		Map<Date,Float> flashPhrDataMap=new HashMap<Date,Float>();
		Map<Date,Float> flashPreDataMap=new HashMap<Date,Float>();
		Map<Date,Float> flashTotalDataMap=new HashMap<Date,Float>();

		//廣泛比對
		double pv = 0;
		double click = 0;
		double cost = 0;
		double invClick = 0;
		double ctr = 0;
		double costAvg = 0;
		
		//詞組比對
		double phrPv = 0;
		double phrClick = 0;
		double phrCost = 0;
		double phrInvClick = 0;
		double phrCtr = 0;
		double phrCostAvg = 0;
		
		//精準比對
		double prePv = 0;
		double preClick = 0;
		double preCost = 0;
		double preInvClick = 0;
		double preCtr = 0;
		double preCostAvg = 0;
		
		//總計
		double totalPv = 0;
		double totalClick = 0;
		double totalCost = 0;
		double totalInvClick = 0;
		double totalCtr = 0;
		double totalCostAvg = 0;

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		for (int i=0; i<resultData.size(); i++) {

			AdKeywordReportVO keywordReportVO = resultData.get(i);

			//廣泛比對
			Date reportDate = dateFormat.parse(keywordReportVO.getReportDate());
			pv = Double.parseDouble(keywordReportVO.getKwPvSum());
			click = Double.parseDouble(keywordReportVO.getKwClkSum());
			cost = Double.parseDouble(keywordReportVO.getKwPriceSum());
			invClick = Double.parseDouble(keywordReportVO.getKwInvClkSum());
			//System.out.println("reportDate = " + reportDate);
			//System.out.println("pv = " + pv);
			//System.out.println("click = " + click);
			//System.out.println("cost = " + cost);
			//System.out.println("invClick = " + invClick);
			
			//點選率 = 點選次數 / 曝光數
			if (pv>0 && click>0) {
				ctr = (click / pv) * 100;
			}

			//平均點選費用 = 總費用 / 總點選次數
			if (cost>0 && click>0) {
				costAvg = cost / click;
			}

			//詞組比對
			phrPv = Double.parseDouble(keywordReportVO.getKwPhrPvSum());
			phrClick = Double.parseDouble(keywordReportVO.getKwPhrClkSum());
			phrCost = Double.parseDouble(keywordReportVO.getKwPhrPriceSum());
			phrInvClick = Double.parseDouble(keywordReportVO.getKwPhrInvClkSum());
			
			//點選率 = 點選次數 / 曝光數
			if (phrPv>0 && phrClick>0) {
				phrCtr = (phrClick / phrPv) * 100;
			}

			//平均點選費用 = 總費用 / 總點選次數
			if (phrCost>0 && phrClick>0) {
				phrCostAvg = phrCost / phrClick;
			}
			
			//精準比對
			prePv = Double.parseDouble(keywordReportVO.getKwPrePvSum());
			preClick = Double.parseDouble(keywordReportVO.getKwPreClkSum());
			preCost = Double.parseDouble(keywordReportVO.getKwPrePriceSum());
			preInvClick = Double.parseDouble(keywordReportVO.getKwPreInvClkSum());
			
			//點選率 = 點選次數 / 曝光數
			if (prePv>0 && preClick>0) {
				preCtr = (preClick / prePv) * 100;
			}

			//平均點選費用 = 總費用 / 總點選次數
			if (preCost>0 && preClick>0) {
				preCostAvg = preCost / preClick;
			}
			
			//總計
			totalPv = pv + phrPv + prePv;
			totalClick = click + phrClick + preClick;
			totalCost = cost + phrCost + preCost;
			totalInvClick = invClick + phrInvClick + preInvClick;
			
			//點選率 = 點選次數 / 曝光數
			if (totalPv>0 && totalClick>0) {
				totalCtr = (totalClick / totalPv) * 100;
			}

			//平均點選費用 = 總費用 / 總點選次數
			if (totalCost>0 && totalClick>0) {
				totalCostAvg = totalCost / totalClick;
			}
			
			if (charType.equals(EnumReport.REPORT_CHART_TYPE_PV.getTextValue())) {
				flashDataMap.put(reportDate, new Float((float) pv));
				flashPhrDataMap.put(reportDate, new Float((float) phrPv));
				flashPreDataMap.put(reportDate, new Float((float) prePv));
				flashTotalDataMap.put(reportDate, new Float((float) totalPv));
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_CLICK.getTextValue())) {
				flashDataMap.put(reportDate, new Float((float) click));
				flashPhrDataMap.put(reportDate, new Float((float) phrClick));
				flashPreDataMap.put(reportDate, new Float((float) preClick));
				flashTotalDataMap.put(reportDate, new Float((float) totalClick));
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_CTR.getTextValue())) {
				flashDataMap.put(reportDate, new Float((float) ctr));
				flashPhrDataMap.put(reportDate, new Float((float) phrCtr));
				flashPreDataMap.put(reportDate, new Float((float) preCtr));
				flashTotalDataMap.put(reportDate, new Float((float) totalCtr));
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_INVALID.getTextValue())) {
				flashDataMap.put(reportDate, new Float((float) invClick));
				flashPhrDataMap.put(reportDate, new Float((float) phrInvClick));
				flashPreDataMap.put(reportDate, new Float((float) preInvClick));
				flashTotalDataMap.put(reportDate, new Float((float) totalInvClick));
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_AVGCOST.getTextValue())) {
				flashDataMap.put(reportDate, new Float((float) costAvg));
				flashPhrDataMap.put(reportDate, new Float((float) phrCostAvg));
				flashPreDataMap.put(reportDate, new Float((float) preCostAvg));
				flashTotalDataMap.put(reportDate, new Float((float) totalCostAvg));
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_COST.getTextValue())) {
				flashDataMap.put(reportDate, new Float((float) cost));
				flashPhrDataMap.put(reportDate, new Float((float) phrCost));
				flashPreDataMap.put(reportDate, new Float((float) preCost));
				flashTotalDataMap.put(reportDate, new Float((float) totalCost));
			}
		}
		
		mapList.add(flashDataMap);
		mapList.add(flashPhrDataMap);
		mapList.add(flashPreDataMap);
		mapList.add(flashTotalDataMap);

		flashData = openFlashUtil.getKeywordChartDataForArray(charType, startDate, endDate, mapList);

		return SUCCESS;
	}

	public ReportKeywordAction() {

		reportTitle="關鍵字成效";
		
		downloadFlag="no";

		tableHeadNameMap=new HashMap<String,String>();
		tableHeadNameMap.put("曝光數", EnumReport.REPORT_CHART_TYPE_PV.getTextValue());
		tableHeadNameMap.put("點選次數", EnumReport.REPORT_CHART_TYPE_CLICK.getTextValue());
		tableHeadNameMap.put("點選率", EnumReport.REPORT_CHART_TYPE_CTR.getTextValue());
		// 20140318： 隱藏 "無效點選次數" 欄位
		//tableHeadNameMap.put("無效點選次數", EnumReport.REPORT_CHART_TYPE_INVALID.getTextValue());
		tableHeadNameMap.put("平均點選費用", EnumReport.REPORT_CHART_TYPE_AVGCOST.getTextValue());
		tableHeadNameMap.put("費用", EnumReport.REPORT_CHART_TYPE_COST.getTextValue());

		optionSelect="";
		optionNotSelect="";

		//optionSelect="曝光數,點選率(%),點選次數,無效點選次數,平均點選費用,費用";
		// 20140318： 隱藏 "無效點選次數" 欄位
		optionSelect="曝光數,點選次數,點選率,平均點選費用,費用";

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

		if(StringUtils.isEmpty(adType)){
			adType=EnumReport.ADTYPE_KEYWORD.getTextValue();
		}

		if(StringUtils.isEmpty(adSearchWay)){
			adSearchWay=EnumReport.ADSEARCH_INCLUDE.getTextValue();
		}

		if(StringUtils.isEmpty(adShowWay)){
			adShowWay=Integer.toString(EnumAdType.AD_ALL.getType());
		}

		log.info("startDate="+startDate);
		log.info("endDate="+endDate);
		log.info("adType="+adType);
		log.info("adSearchWay="+adSearchWay);
		log.info("adShowWay="+adShowWay);

		dateSelectMap=DateValueUtil.getInstance().getDateRangeMap();

		//輸入欄位順序
		if(!tableHeadShowList.isEmpty()){
			for(String s:tableHeadShowList){
				tableHeadList.addLast(s);
			}
		}
		tableHeadList.addFirst("裝置");
		tableHeadList.addFirst("廣告");
		tableHeadList.addFirst("分類");
		tableHeadList.addFirst("關鍵字");
		tableHeadList.addFirst("狀態");
		tableHeadList.addLast("平均廣告排名");

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


		List<AdKeywordReportVO> resultSumData=adKeywordReportService.loadReportDate(EnumReport.REPORT_HQLTYPE_EXCERPT_COUNT.getTextValue(),"",searchText, adSearchWay, Integer.toString(EnumAdType.AD_SEARCH.getType()), adPvclkDevice,customerInfoId,startDate, endDate,-1,-1);

		int totalPageSize=resultSumData.size();

		List<AdKeywordReportVO> resultData=adKeywordReportService.loadReportDate(EnumReport.REPORT_HQLTYPE_EXCERPT.getTextValue(),"",searchText, adSearchWay, Integer.toString(EnumAdType.AD_SEARCH.getType()), adPvclkDevice,customerInfoId,startDate, endDate, page, pageSize);

		totalPage =(totalPageSize/pageSize);

		if((totalPageSize%pageSize)>0){
			totalPage+=1;
		}

		if(resultSumData.size()>0){
			resultDataTrans(resultData);
			resultSumDataTrans(resultSumData);
		}

		if(downloadFlag.trim().equals("yes")){
			log.info("makeDownloadReportData");
			makeDownloadReportData();
		}

		log.info("time="+new Date().toString());

		return SUCCESS;
	}

	private void makeDownloadReportData() throws Exception {

		SimpleDateFormat dformat = new SimpleDateFormat("yyyyMMddhhmmss");

		String filename="關鍵字成效報表_" + dformat.format(new Date()) + FILE_TYPE;

		StringBuffer content=new StringBuffer();

		PfpCustomerInfo customerInfo = customerInfoService.findCustomerInfo(super.getCustomer_info_id());

		String comma = ",";
		content.append("帳戶," + customerInfo.getCustomerInfoTitle());
		content.append("\n\n");
		content.append("報表名稱,PChome 關鍵字成效");
		content.append("\n\n");
//		content.append("廣告方式," + getAdShowWayMap().get(adShowWay));
//		content.append("\n\n");
		content.append("日期範圍," + startDate + " 到 " + endDate);
		content.append("\n\n");
		
		//欄位名稱
		content.append("狀態" + comma);
		content.append("關鍵字" + comma);
		content.append("廣告" + comma);
		content.append("分類" + comma);
		content.append("裝置" + comma);
		content.append("廣泛比對方式" + comma);
		content.append("詞組比對方式" + comma);
		content.append("精準比對方式" + comma);
		content.append("廣泛比對曝光數" + comma);
		content.append("詞組比對曝光數" + comma);
		content.append("精準比對曝光數" + comma);
		content.append("總曝光數" + comma);
		content.append("廣泛比對點選次數" + comma);
		content.append("詞組比對點選次數" + comma);
		content.append("精準比對點選次數" + comma);
		content.append("總點選次數" + comma);
		content.append("廣泛比對點選率" + comma);
		content.append("詞組比對點選率" + comma);
		content.append("精準比對點選率" + comma);
		content.append("總點選率" + comma);
		content.append("廣泛比對平均點選費用" + comma);
		content.append("詞組比對平均點選費用" + comma);
		content.append("精準比對平均點選費用" + comma);
		content.append("總平均點選費用" + comma);
		content.append("廣泛比對費用" + comma);
		content.append("詞組比對費用" + comma);
		content.append("精準比對費用" + comma);
		content.append("總費用" + comma);
		content.append("廣泛比對平均廣告排名" + comma);
		content.append("詞組比對平均廣告排名" + comma);
		content.append("精準比對平均廣告排名");
		content.append("\n");
		
		//data
		for(AdKeywordReportVO data:AdKeywordReportVO){
			content.append("'" + data.getAdStatus() + "'" + comma);
			content.append("'" + data.getAdKeyword() + "'" + comma);
			content.append("'" + data.getAdActionName() + "'" + comma);
			content.append("'" + data.getAdGroupName() + "'" + comma);
			content.append("'" + data.getKwDevice() + "'" + comma);
			content.append("'" + data.getKwOpen() + "'" + comma);
			content.append("'" + data.getKwPhrOpen() + "'" + comma);
			content.append("'" + data.getKwPreOpen() + "'" + comma);
			content.append("'" + data.getKwPvSum() + "'" + comma);
			content.append("'" + data.getKwPhrPvSum() + "'" + comma);
			content.append("'" + data.getKwPrePvSum() + "'" + comma);
			content.append("'" + data.getKwPvTotal() + "'" + comma);
			content.append("'" + data.getKwClkSum() + "'" + comma);
			content.append("'" + data.getKwPhrClkSum() + "'" + comma);
			content.append("'" + data.getKwPreClkSum() + "'" + comma);
			content.append("'" + data.getKwClkTotal() + "'" + comma);
			content.append("'" + data.getKwCtrSum() + "%'" + comma);
			content.append("'" + data.getKwPhrCtrSum() + "%'" + comma);
			content.append("'" + data.getKwPreCtrSum() + "%'" + comma);
			content.append("'" + data.getKwCtrTotal() + "%'" + comma);
			content.append("'NT$ " + data.getKwPriceAvgSum() + "'" + comma);
			content.append("'NT$ " + data.getKwPriceAvgSum() + "'" + comma);
			content.append("'NT$ " + data.getKwPrePriceAvgSum() + "'" + comma);
			content.append("'NT$ " + data.getKwPriceAvgTotal() + "'" + comma);
			content.append("'NT$ " + data.getKwPriceSum() + "'" + comma);
			content.append("'NT$ " + data.getKwPriceSum() + "'" + comma);
			content.append("'NT$ " + data.getKwPrePriceSum() + "'" + comma);
			content.append("'NT$ " + data.getKwPriceTotal() + "'" + comma);
			content.append("'" + data.getAdRankAvg() + "'" + comma);
			content.append("'" + data.getAdPhrRankAvg() + "'" + comma);
			content.append("'" + data.getAdPreRankAvg() + "'");
			content.append("\n");
		}
		content.append("\n");
		
		//合計
		if (AdKeywordReportDataTotal!=null) {
			content.append(" " + comma);
			content.append("'" + AdKeywordReportDataTotal.getDataTotal() + "'" + comma);
			content.append(" " + comma);
			content.append(" " + comma);
			content.append(" " + comma);
			content.append(" " + comma);
			content.append(" " + comma);
			content.append(" " + comma);
			content.append("'" + AdKeywordReportDataTotal.getKwPvSum() + "'" + comma);
			content.append("'" + AdKeywordReportDataTotal.getKwPhrPvSum() + "'" + comma);
			content.append("'" + AdKeywordReportDataTotal.getKwPrePvSum() + "'" + comma);
			content.append("'" + AdKeywordReportDataTotal.getKwPvTotal() + "'" + comma);
			content.append("'" + AdKeywordReportDataTotal.getKwClkSum() + "'" + comma);
			content.append("'" + AdKeywordReportDataTotal.getKwPhrClkSum() + "'" + comma);
			content.append("'" + AdKeywordReportDataTotal.getKwPreClkSum() + "'" + comma);
			content.append("'" + AdKeywordReportDataTotal.getKwClkTotal() + "'" + comma);
			content.append("'" + AdKeywordReportDataTotal.getKwCtrSum() + "%'" + comma);
			content.append("'" + AdKeywordReportDataTotal.getKwPhrCtrSum() + "%'" + comma);
			content.append("'" + AdKeywordReportDataTotal.getKwPreCtrSum() + "%'" + comma);
			content.append("'" + AdKeywordReportDataTotal.getKwCtrTotal() + "%'" + comma);
			content.append("'NT$ " + AdKeywordReportDataTotal.getKwPriceAvgSum() + "'" + comma);
			content.append("'NT$ " + AdKeywordReportDataTotal.getKwPriceAvgSum() + "'" + comma);
			content.append("'NT$ " + AdKeywordReportDataTotal.getKwPrePriceAvgSum() + "'" + comma);
			content.append("'NT$ " + AdKeywordReportDataTotal.getKwPriceAvgTotal() + "'" + comma);
			content.append("'NT$ " + AdKeywordReportDataTotal.getKwPriceSum() + "'" + comma);
			content.append("'NT$ " + AdKeywordReportDataTotal.getKwPriceSum() + "'" + comma);
			content.append("'NT$ " + AdKeywordReportDataTotal.getKwPrePriceSum() + "'" + comma);
			content.append("'NT$ " + AdKeywordReportDataTotal.getKwPriceTotal() + "'" + comma);
			content.append(" " + comma);
			content.append(" " + comma);
			content.append(" ");
			content.append("\n");
		}
		
		/*for(String s:tableHeadList){
			content.append("\"" + s + "\"");
			content.append(",");
		}
		content.append("\n");

		for(LinkedList<String> sl:tableDataList){
			int dataNumber = 1;
			for(String s:sl){
				if(dataNumber == 9 || dataNumber == 10){
					content.append("\"NT$ " + s + "\"");
				} else if(dataNumber == 8){
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
				if(dataTotalNumber == 9 || dataTotalNumber == 10){
					content.append("\"NT$ " + s + "\"");
				} else if(dataTotalNumber == 8){
					content.append("\"" + s + "%\"");
				} else {
					content.append("\"" + s + "\"");
				}
				content.append(",");
				dataTotalNumber++;
			}
			content.append("\n");
		}*/

		if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
			downloadFileName = new String(filename.getBytes("UTF-8"), "ISO8859-1");
		} else {
			downloadFileName = URLEncoder.encode(filename, "UTF-8");			
		}

		downloadFileStream = new ByteArrayInputStream(content.toString().getBytes("big5"));

		//處理 BOM 開頭要加上 "\uFEFF"
		//downloadFileStream = new ByteArrayInputStream(("\uFEFF" + content.toString()).getBytes("utf-8"));
	}

	private void resultSumDataTrans(List<AdKeywordReportVO> resultSumData) {

		NumberFormat intFormat = new DecimalFormat("###,###,###,###");
		NumberFormat doubleFormat = new DecimalFormat("###,###,###,##0.00");
		AdKeywordReportDataTotal = new AdKeywordReportVO();

		AdKeywordReportDataTotal.setDataTotal("總計：" + intFormat.format(resultSumData.size()));
		
		//廣泛比對
		double t_pv = 0; //總曝光數
		double t_click = 0; //總點選次數
		double t_ctr = 0; //點選率
		double t_invalid = 0; //無效點選次數
		double t_costAvg = 0; //平均點選費用
		double t_cost = 0; //總費用
		
		//詞組比對
		double t_phrPv = 0; //總曝光數
		double t_phrClick = 0; //總點選次數
		double t_phrCtr = 0; //點選率
		double t_phrInvalid = 0; //無效點選次數
		double t_phrCostAvg = 0; //平均點選費用
		double t_phrCost = 0; //總費用
		
		//精準比對
		double t_prePv = 0; //總曝光數
		double t_preClick = 0; //總點選次數
		double t_preCtr = 0; //點選率
		double t_preInvalid = 0; //無效點選次數
		double t_preCostAvg = 0; //平均點選費用
		double t_preCost = 0; //總費用
		
		//總計比對
		double t_pvTotal = 0; //總曝光數
		double t_clickTotal = 0; //總點選次數
		double t_ctrTotal = 0; //點選率
		double t_invalidTotal = 0; //無效點選次數
		double t_costAvgTotal = 0; //平均點選費用
		double t_costTotal = 0; //總費用

		//加總
		for (int i=0; i<resultSumData.size(); i++) {

			AdKeywordReportVO keywordReportVO = resultSumData.get(i);

			//廣泛比對
			t_pv += new Double(keywordReportVO.getKwPvSum());
			t_click += new Double(keywordReportVO.getKwClkSum());
			t_cost += new Double(keywordReportVO.getKwPriceSum());
			t_invalid += new Double(keywordReportVO.getKwInvClkSum());
			
			//詞組比對
			t_phrPv += new Double(keywordReportVO.getKwPhrPvSum());
			t_phrClick += new Double(keywordReportVO.getKwPhrClkSum());
			t_phrCost += new Double(keywordReportVO.getKwPhrPriceSum());
			t_phrInvalid += new Double(keywordReportVO.getKwPhrInvClkSum());
			
			//精準比對
			t_prePv += new Double(keywordReportVO.getKwPrePvSum());
			t_preClick += new Double(keywordReportVO.getKwPreClkSum());
			t_preCost += new Double(keywordReportVO.getKwPrePriceSum());
			t_preInvalid += new Double(keywordReportVO.getKwPreInvClkSum());
		}

		//總計
		t_pvTotal = t_pv + t_phrPv + t_prePv;
		t_clickTotal = t_click + t_phrClick + t_preClick;
		t_costTotal = t_cost + t_phrCost + t_preCost;
		t_invalidTotal = t_invalid + t_phrInvalid + t_preInvalid;
		
		//點選率 = 總點選次數 / 總曝光數
		if (t_pv>0 && t_click>0) {	//廣泛比對
			t_ctr = (t_click / t_pv) * 100;
		}
		if (t_phrPv>0 && t_phrClick>0) {	//詞組比對
			t_phrCtr = (t_phrClick / t_phrPv) * 100;
		}
		if (t_prePv>0 && t_preClick>0) {	//精準比對
			t_preCtr = (t_preClick / t_prePv) * 100;
		}
		if (t_pvTotal>0 && t_clickTotal>0) {	//總計
			t_ctrTotal = (t_clickTotal / t_pvTotal) * 100;
		}

		//平均點選費用 = 總費用 / 總點選次數
		if (t_cost>0 && t_click>0) {	//廣泛比對
			t_costAvg = t_cost / t_click;
		}
		if (t_phrCost>0 && t_phrClick>0) {	//詞組比對
			t_phrCostAvg = t_phrCost / t_phrClick;
		}
		if (t_preCost>0 && t_preClick>0) {	//精準比對
			t_preCostAvg = t_preCost / t_preClick;
		}
		if (t_costTotal>0 && t_clickTotal>0) {	//總計
			t_costAvgTotal = t_costTotal / t_clickTotal;
		}
		//廣泛比對
		AdKeywordReportDataTotal.setKwPvSum(intFormat.format(t_pv));
		AdKeywordReportDataTotal.setKwClkSum(intFormat.format(t_click));
		AdKeywordReportDataTotal.setKwCtrSum(doubleFormat.format(t_ctr));
		AdKeywordReportDataTotal.setKwPriceSum(intFormat.format(t_cost));
		AdKeywordReportDataTotal.setKwPriceAvgSum(doubleFormat.format(t_costAvg));
		AdKeywordReportDataTotal.setKwInvClkSum(intFormat.format(t_invalid));
		
		//詞組比對
		AdKeywordReportDataTotal.setKwPhrPvSum(intFormat.format(t_phrPv));
		AdKeywordReportDataTotal.setKwPhrClkSum(intFormat.format(t_phrClick));
		AdKeywordReportDataTotal.setKwPhrCtrSum(doubleFormat.format(t_phrCtr));
		AdKeywordReportDataTotal.setKwPhrPriceSum(intFormat.format(t_phrCost));
		AdKeywordReportDataTotal.setKwPhrPriceAvgSum(doubleFormat.format(t_phrCostAvg));
		AdKeywordReportDataTotal.setKwPhrInvClkSum(intFormat.format(t_phrInvalid));
		
		//精準比對
		AdKeywordReportDataTotal.setKwPrePvSum(intFormat.format(t_prePv));
		AdKeywordReportDataTotal.setKwPreClkSum(intFormat.format(t_preClick));
		AdKeywordReportDataTotal.setKwPreCtrSum(doubleFormat.format(t_preCtr));
		AdKeywordReportDataTotal.setKwPrePriceSum(intFormat.format(t_preCost));
		AdKeywordReportDataTotal.setKwPrePriceAvgSum(doubleFormat.format(t_preCostAvg));
		AdKeywordReportDataTotal.setKwPreInvClkSum(intFormat.format(t_preInvalid));
		
		//總計
		AdKeywordReportDataTotal.setKwPvTotal(intFormat.format(t_pvTotal));
		AdKeywordReportDataTotal.setKwClkTotal(intFormat.format(t_clickTotal));
		AdKeywordReportDataTotal.setKwCtrTotal(doubleFormat.format(t_ctrTotal));
		AdKeywordReportDataTotal.setKwPriceTotal(intFormat.format(t_costTotal));
		AdKeywordReportDataTotal.setKwPriceAvgTotal(doubleFormat.format(t_costAvgTotal));
		AdKeywordReportDataTotal.setKwInvClkTotal(intFormat.format(t_invalidTotal));
		
		
		/*if (!tableHeadShowList.isEmpty()) {
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
				} else if (mapKey.trim().equals(EnumReport.REPORT_CHART_TYPE_COST.getTextValue())) {
					tableDataTotalList.addLast(intFormat.format(t_cost));
				}
			}
		}
		tableDataTotalList.add("");*/
	}

	private void resultDataTrans(List<AdKeywordReportVO> resultData) {

		LinkedList<String> tableInDataList;

		String adActionName = "";
		String adGroupName = "";
		String adKeyword = "";
		String adKeywordRankAvg = ""; 	 //廣泛比對平均廣告排名
		String adKeywordPhrRankAvg = ""; //詞組比對平均廣告排名
		String adKeywordPreRankAvg = ""; //精準比對平均廣告排名

		int adActionStatus = 0;
		int adGroupStatus = 0;
		int adKeywordStatus = 0;
		int adKeywordpoen = 0;
		int adKeywordPhrpoen = 0;
		int adKeywordPrepoen = 0;

		NumberFormat intFormat = new DecimalFormat("###,###,###,###");
		NumberFormat doubleFormat = new DecimalFormat("###,###,###,##0.00");
		AdKeywordReportVO = new ArrayList<AdKeywordReportVO>();
		
		for (int i=0; i<resultData.size(); i++) {

			//廣泛比對
			double pv = 0;
			double click = 0;
			double cost = 0;
			double invClick = 0;
			double ctr = 0;
			double costAvg = 0;
			
			//詞組比對
			double phrPv = 0;
			double phrClick = 0;
			double phrCost = 0;
			double phrInvClick = 0;
			double phrCtr = 0;
			double phrCostAvg = 0;
			
			//精準比對
			double prePv = 0;
			double preClick = 0;
			double preCost = 0;
			double preInvClick = 0;
			double preCtr = 0;
			double preCostAvg = 0;
			
			//總計
			double pvTotal = 0;
			double clickTotal = 0;
			double costTotal = 0;
			double invClickTotal = 0;
			double ctrTotal = 0;
			double costAvgTotal = 0;

			AdKeywordReportVO keywordReportVO = resultData.get(i);

			tableInDataList = new LinkedList<String>();

			adKeywordRankAvg = keywordReportVO.getAdRankAvg();
			adKeywordPhrRankAvg = keywordReportVO.getAdPhrRankAvg();
			adKeywordPreRankAvg = keywordReportVO.getAdPreRankAvg();
			keywordReportVO.setAdRankAvg(doubleFormat.format(new Double(adKeywordRankAvg)));
			keywordReportVO.setAdPhrRankAvg(doubleFormat.format(new Double(adKeywordPhrRankAvg)));
			keywordReportVO.setAdPreRankAvg(doubleFormat.format(new Double(adKeywordPreRankAvg)));

			//廣泛比對
			pv = new Double(keywordReportVO.getKwPvSum());
			click = new Double(keywordReportVO.getKwClkSum());
			cost = new Double(keywordReportVO.getKwPriceSum());
			invClick = new Double(keywordReportVO.getKwInvClkSum());

			//點選率 = 點選次數 / 曝光數
			if (pv>0 && click>0) {
				ctr = (click / pv) * 100;
			}

			//平均點選費用 = 總費用 / 總點選次數
			if (cost>0 && click>0) {
				costAvg = cost / click;
			}
			
			keywordReportVO.setKwPvSum(intFormat.format(pv));
			keywordReportVO.setKwClkSum(intFormat.format(click));
			keywordReportVO.setKwCtrSum(doubleFormat.format(ctr));
			keywordReportVO.setKwPriceSum(intFormat.format(cost));
			keywordReportVO.setKwPriceAvgSum(doubleFormat.format(costAvg));
			keywordReportVO.setKwInvClkSum(intFormat.format(invClick));

			
			//詞組比對
			phrPv = new Double(keywordReportVO.getKwPhrPvSum());
			phrClick = new Double(keywordReportVO.getKwPhrClkSum());
			phrCost = new Double(keywordReportVO.getKwPhrPriceSum());
			phrInvClick = new Double(keywordReportVO.getKwPhrInvClkSum());

			//點選率 = 點選次數 / 曝光數
			if (phrPv>0 && phrClick>0) {
				phrCtr = (phrClick / phrPv) * 100;
			}

			//平均點選費用 = 總費用 / 總點選次數
			if (phrCost>0 && phrClick>0) {
				phrCostAvg = phrCost / phrClick;
			}
			
			keywordReportVO.setKwPhrPvSum(intFormat.format(phrPv));
			keywordReportVO.setKwPhrClkSum(intFormat.format(phrClick));
			keywordReportVO.setKwPhrCtrSum(doubleFormat.format(phrCtr));
			keywordReportVO.setKwPhrPriceSum(intFormat.format(phrCost));
			keywordReportVO.setKwPhrPriceAvgSum(doubleFormat.format(phrCostAvg));
			keywordReportVO.setKwPhrInvClkSum(intFormat.format(phrInvClick));
			
			
			//精準比對
			prePv = new Double(keywordReportVO.getKwPrePvSum());
			preClick = new Double(keywordReportVO.getKwPreClkSum());
			preCost = new Double(keywordReportVO.getKwPrePriceSum());
			preInvClick = new Double(keywordReportVO.getKwPreInvClkSum());

			//點選率 = 點選次數 / 曝光數
			if (prePv>0 && preClick>0) {
				preCtr = (preClick / prePv) * 100;
			}

			//平均點選費用 = 總費用 / 總點選次數
			if (preCost>0 && preClick>0) {
				preCostAvg = preCost / preClick;
			}
			
			keywordReportVO.setKwPrePvSum(intFormat.format(prePv));
			keywordReportVO.setKwPreClkSum(intFormat.format(preClick));
			keywordReportVO.setKwPreCtrSum(doubleFormat.format(preCtr));
			keywordReportVO.setKwPrePriceSum(intFormat.format(preCost));
			keywordReportVO.setKwPrePriceAvgSum(doubleFormat.format(preCostAvg));
			keywordReportVO.setKwPreInvClkSum(intFormat.format(preInvClick));
			
			
			//總計
			pvTotal = pv + phrPv + prePv;
			clickTotal = click + phrClick + preClick;
			costTotal = cost + phrCost + preCost;
			invClickTotal = invClick + phrInvClick + preInvClick;;

			//點選率 = 點選次數 / 曝光數
			if (pvTotal>0 && clickTotal>0) {
				ctrTotal = (clickTotal / pvTotal) * 100;
			}

			//平均點選費用 = 總費用 / 總點選次數
			if (costTotal>0 && clickTotal>0) {
				costAvgTotal = costTotal / clickTotal;
			}
			
			keywordReportVO.setKwPvTotal(intFormat.format(pvTotal));
			keywordReportVO.setKwClkTotal(intFormat.format(clickTotal));
			keywordReportVO.setKwCtrTotal(doubleFormat.format(ctrTotal));
			keywordReportVO.setKwPriceTotal(intFormat.format(costTotal));
			keywordReportVO.setKwPriceAvgTotal(doubleFormat.format(costAvgTotal));
			keywordReportVO.setKwInvClkTotal(intFormat.format(invClickTotal));
			
			
			PfpAdAction pfpAdAction = new PfpAdAction();
			try {
				pfpAdAction = pfpAdActionDAO.getPfpAdActionBySeq(keywordReportVO.getAdActionSeq());
				adActionName = pfpAdAction.getAdActionName();
				adActionStatus = pfpAdAction.getAdActionStatus();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			PfpAdGroup pfpAdGroup = new PfpAdGroup();
			try {
				pfpAdGroup = pfpAdGroupDAO.getPfpAdGroupBySeq(keywordReportVO.getAdGroupSeq());
				adGroupName = pfpAdGroup.getAdGroupName();
				adGroupStatus = pfpAdGroup.getAdGroupStatus();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			PfpAdKeyword pfpAdKeyword = new PfpAdKeyword();
			try {
				pfpAdKeyword = pfpAdKeywordDAO.getPfpAdKeywordBySeq(keywordReportVO.getAdKeywordSeq());
				adKeywordStatus = pfpAdKeyword.getAdKeywordStatus();
				adKeywordpoen = pfpAdKeyword.getAdKeywordOpen();
				adKeywordPhrpoen = pfpAdKeyword.getAdKeywordPhraseOpen();
				adKeywordPrepoen = pfpAdKeyword.getAdKeywordPrecisionOpen();
				adKeyword = pfpAdKeyword.getAdKeyword();
			} catch (Exception e) {
				// TODO Auto-generated catch block
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
					} else if (nowTime > _endDate) {
						adActionStatus = EnumStatus.End.getStatusId();
					} else {
						adActionStatus = EnumStatus.Broadcast.getStatusId();
					}
				}

			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			
			//播放狀態
			String alter = "";
			String icon = "icon_adclose.gif";
			if ((adActionStatus == EnumStatus.Broadcast.getStatusId()) &&
					(adGroupStatus == EnumStatus.Open.getStatusId()) &&
					(adKeywordStatus == EnumStatus.Open.getStatusId())) {

				alter = "走期中";
				icon = "icon_adopen.gif";

			} else if ((adActionStatus == EnumStatus.Broadcast.getStatusId()) &&
					(adGroupStatus == EnumStatus.Open.getStatusId()) &&
					(adKeywordStatus != EnumStatus.Open.getStatusId())) {

				alter = "關鍵字" + getAdStatusMap().get(Integer.toString(adKeywordStatus));;

			} else if ((adActionStatus == EnumStatus.Broadcast.getStatusId()) &&
					(adGroupStatus != EnumStatus.Open.getStatusId()) &&
					(adKeywordStatus != EnumStatus.Open.getStatusId())) {

				alter = "分類" + getAdStatusMap().get(Integer.toString(adGroupStatus)) + "，關鍵字" + getAdStatusMap().get(Integer.toString(adKeywordStatus));

			} else if ((adActionStatus == EnumStatus.Broadcast.getStatusId()) &&
					(adGroupStatus != EnumStatus.Open.getStatusId()) &&
					(adKeywordStatus == EnumStatus.Open.getStatusId())) {

				alter = "分類" + getAdStatusMap().get(Integer.toString(adGroupStatus));

			} else if ((adActionStatus != EnumStatus.Broadcast.getStatusId()) &&
					(adGroupStatus == EnumStatus.Open.getStatusId()) &&
					(adKeywordStatus == EnumStatus.Open.getStatusId())) {

				alter = "廣告" + getAdStatusMap().get(Integer.toString(adActionStatus));

			} else if ((adActionStatus != EnumStatus.Broadcast.getStatusId()) &&
					(adGroupStatus == EnumStatus.Open.getStatusId()) &&
					(adKeywordStatus != EnumStatus.Open.getStatusId())) {

				alter = "廣告" + getAdStatusMap().get(Integer.toString(adActionStatus)) + "，關鍵字" + getAdStatusMap().get(Integer.toString(adKeywordStatus));
			
			} else if ((adActionStatus != EnumStatus.Broadcast.getStatusId()) &&
					(adGroupStatus != EnumStatus.Open.getStatusId()) &&
					(adKeywordStatus == EnumStatus.Open.getStatusId())) {

				alter = "廣告" + getAdStatusMap().get(Integer.toString(adActionStatus)) + "，分類" + getAdStatusMap().get(Integer.toString(adGroupStatus));

			} else if ((adActionStatus != EnumStatus.Broadcast.getStatusId()) &&
					(adGroupStatus != EnumStatus.Open.getStatusId()) &&
					(adKeywordStatus != EnumStatus.Open.getStatusId())) {

				alter = "廣告" + getAdStatusMap().get(Integer.toString(adActionStatus)) + "，分類" + getAdStatusMap().get(Integer.toString(adGroupStatus)) + "，關鍵字" + getAdStatusMap().get(Integer.toString(adKeywordStatus));

			}
			
			if(downloadFlag.equals("yes")){
				keywordReportVO.setAdStatus(alter);
				
				if(adKeywordpoen == 1){
					keywordReportVO.setKwOpen("開啟");
				} else {
					keywordReportVO.setKwOpen("關閉");
				}
				if(adKeywordPhrpoen == 1){
					keywordReportVO.setKwPhrOpen("開啟");
				} else {
					keywordReportVO.setKwPhrOpen("關閉");
				}
				if(adKeywordPrepoen == 1){
					keywordReportVO.setKwPreOpen("開啟");
				} else {
					keywordReportVO.setKwPreOpen("關閉");
				}
			} else {
				keywordReportVO.setAdStatus("<img src=\"./html/img/" + icon + "\" alt=\"" + alter + "\" title=\"" + alter + "\">");
				
				if(adKeywordpoen == 1){
					keywordReportVO.setKwOpen("<img src=\"./html/img/icon_adopen.gif\" alt=\"開啟\" title=\"開啟\" >");
				} else {
					keywordReportVO.setKwOpen("<img src=\"./html/img/icon_adclose.gif\" alt=\"關閉\" title=\"關閉\" >");
				}
				if(adKeywordPhrpoen == 1){
					keywordReportVO.setKwPhrOpen("<img src=\"./html/img/icon_adopen.gif\" alt=\"開啟\" title=\"開啟\" >");
				} else {
					keywordReportVO.setKwPhrOpen("<img src=\"./html/img/icon_adclose.gif\" alt=\"關閉\" title=\"關閉\" >");
				}
				if(adKeywordPrepoen == 1){
					keywordReportVO.setKwPreOpen("<img src=\"./html/img/icon_adopen.gif\" alt=\"開啟\" title=\"開啟\" >");
				} else {
					keywordReportVO.setKwPreOpen("<img src=\"./html/img/icon_adclose.gif\" alt=\"關閉\" title=\"關閉\" >");
				}
			}
			
			keywordReportVO.setAdKeyword(adKeyword);
			keywordReportVO.setAdGroupName(adGroupName);
			keywordReportVO.setAdActionName(adActionName);
			
			AdKeywordReportVO.add(keywordReportVO);
		}
	}

	
	public void setPfpAdActionDAO(IPfpAdActionDAO pfpAdActionDAO) {
		this.pfpAdActionDAO = pfpAdActionDAO;
	}

	public void setPfpAdGroupDAO(IPfpAdGroupDAO pfpAdGroupDAO) {
		this.pfpAdGroupDAO = pfpAdGroupDAO;
	}

	public void setPfpAdKeywordDAO(IPfpAdKeywordDAO pfpAdKeywordDAO) {
		this.pfpAdKeywordDAO = pfpAdKeywordDAO;
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

//	public String getAdShowWay() {
//		return adShowWay;
//	}

//	public void setAdShowWay(String adShowWay) {
//		this.adShowWay = adShowWay;
//	}

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

	public void setAdKeywordReportService(
			IAdKeywordReportService adKeywordReportService) {
		this.adKeywordReportService = adKeywordReportService;
	}

	public String[] getAlign_data() {
		return align_data;
	}

	public String[] getAlign_sum() {
		return align_sum;
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

	public List<AdKeywordReportVO> getAdKeywordReportVO() {
		return AdKeywordReportVO;
	}

	public AdKeywordReportVO getAdKeywordReportDataTotal() {
		return AdKeywordReportDataTotal;
	}
	
}
