package com.pchome.akbpfp.struts2.action.report;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
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

		Map<Date,Float> flashDataMap=new HashMap<Date,Float>();

		double pv = 0;
		double click = 0;
		double cost = 0;
		double invClick = 0;
		double ctr = 0;
		double costAvg = 0;

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		for (int i=0; i<resultData.size(); i++) {

			AdKeywordReportVO keywordReportVO = resultData.get(i);

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
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_COST.getTextValue())) {
				flashDataMap.put(reportDate, new Float((float) cost));
			}
		}

		flashData = openFlashUtil.getChartDataForArray(charType, startDate, endDate, flashDataMap);

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
			pageSize=10;
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

		content.append("帳戶," + customerInfo.getCustomerInfoTitle());
		content.append("\n\n");
		content.append("報表名稱,PChome 關鍵字成效");
		content.append("\n\n");
//		content.append("廣告方式," + getAdShowWayMap().get(adShowWay));
//		content.append("\n\n");
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
		}

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
		NumberFormat doubleFormat = new DecimalFormat("###,###,###,###.##");

		tableDataTotalList = new LinkedList<String>();
		tableDataTotalList.add("");
		tableDataTotalList.add("總計：" + intFormat.format(resultSumData.size()));
		tableDataTotalList.add("");
		tableDataTotalList.add("");
		tableDataTotalList.add("");

		double t_pv = 0; //總曝光數
		double t_click = 0; //總點選次數
		double t_ctr = 0; //點選率
		double t_invalid = 0; //無效點選次數
		double t_costAvg = 0; //平均點選費用
		double t_cost = 0; //總費用

		//加總
		for (int i=0; i<resultSumData.size(); i++) {

			AdKeywordReportVO keywordReportVO = resultSumData.get(i);

			t_pv += new Double(keywordReportVO.getKwPvSum());
			t_click += new Double(keywordReportVO.getKwClkSum());
			t_cost += new Double(keywordReportVO.getKwPriceSum());
			t_invalid += new Double(keywordReportVO.getKwInvClkSum());
		}

		//點選率 = 總點選次數 / 總曝光數
		if (t_pv>0 && t_click>0) {
			t_ctr = (t_click / t_pv) * 100;
		}

		//平均點選費用 = 總費用 / 總點選次數
		if (t_cost>0 && t_click>0) {
			t_costAvg = t_cost / t_click;
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
				} else if (mapKey.trim().equals(EnumReport.REPORT_CHART_TYPE_COST.getTextValue())) {
					tableDataTotalList.addLast(intFormat.format(t_cost));
				}
			}
		}
		tableDataTotalList.add("");
	}

	private void resultDataTrans(List<AdKeywordReportVO> resultData) {

		LinkedList<String> tableInDataList;

		String adActionName = "";
		String adGroupName = "";
		String adKeyword = "";
		String adKeywordRankAvg = ""; //平均廣告排名
		String kwDevice = "";
		int adActionStatus = 0;
		int adGroupStatus = 0;
		int adKeywordStatus = 0;

		NumberFormat intFormat = new DecimalFormat("###,###,###,###");
		NumberFormat doubleFormat = new DecimalFormat("###,###,###,###.##");

		for (int i=0; i<resultData.size(); i++) {

			double pv = 0;
			double click = 0;
			double cost = 0;
			double invClick = 0;
			double ctr = 0;
			double costAvg = 0;

			AdKeywordReportVO keywordReportVO = resultData.get(i);

			tableInDataList = new LinkedList<String>();

			adKeyword = keywordReportVO.getAdKeyword();
			adKeywordRankAvg = keywordReportVO.getAdRankAvg();
			kwDevice = keywordReportVO.getKwDevice();

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
				tableInDataList.addLast(alter);
			} else {
				tableInDataList.addLast("<img src=\"./html/img/" + icon + "\" alt=\"" + alter + "\" title=\"" + alter + "\">");
			}
			tableInDataList.addLast(adKeyword);
			tableInDataList.addLast(adGroupName);
			tableInDataList.addLast(adActionName);
			tableInDataList.addLast(kwDevice);

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
					} else if (mapKey.trim().equals(EnumReport.REPORT_CHART_TYPE_COST.getTextValue())) {
						tableInDataList.addLast(intFormat.format(cost));
					}
				}
			}

			tableInDataList.addLast(doubleFormat.format(new Double(adKeywordRankAvg)));

			tableDataList.addLast(tableInDataList);
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
	
}
