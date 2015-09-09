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
import com.pchome.akbpfp.db.dao.report.AdActionReportVO;
import com.pchome.akbpfp.db.pojo.PfpCustomerInfo;
import com.pchome.akbpfp.db.service.customerInfo.IPfpCustomerInfoService;
import com.pchome.akbpfp.db.service.report.IAdActionReportService;
import com.pchome.soft.util.DateValueUtil;
import com.pchome.soft.depot.utils.SpringOpenFlashUtil;

public class ReportAdDailyAction extends BaseReportAction {

	private static final long serialVersionUID = 1L;

	private LinkedList<String> tableHeadList = null; //頁面table head
	private LinkedList<String> tableDataTotalList = null; //頁面全部加總table total foot

	private LinkedList<LinkedList<String>> tableDataList = null; // 頁面 table data

	//private String[] align_data = {"center", "center", "right", "right", "right", "right", "right", "right"};
	//private String[] align_sum = {"center", "center", "right", "right", "right", "right", "right", "right"};
	// 20140318： 隱藏 "無效點選次數" 欄位
	private String[] align_data = {"center", "center", "right", "right", "right", "right", "right"};
	private String[] align_sum = {"center", "center", "right", "right", "right", "right", "right"};

	private IAdActionReportService adActionReportService = null;
	private IPfpCustomerInfoService customerInfoService = null;

	private SpringOpenFlashUtil openFlashUtil=null;

	private Map<String,String> tableHeadNameMap;//自訂欄位

	private LinkedList<String> tableHeadShowList = null;//自訂欄位選擇list,頁面顯示
	private LinkedList<String> tableHeadNotShowList = null;//自訂欄位不選擇list,頁面顯示

	private String optionSelect = "";//自訂欄位選擇,頁面回傳
	private String optionNotSelect = "";//自訂欄位不選擇,頁面回傳

	private String startDate = "";//查詢開始日期
	private String endDate = "";//查詢結束日期

	private LinkedHashMap<String,String> dateSelectMap = null;//查詢日期的 rang map,查詢日期頁面顯示

	private int page = 0;//第幾頁
	private int pageSize = 0;//每頁筆數
	private int totalPage = 0;//總頁數

	private String adPvclkDevice=""; //裝置:pc、mobile
	private String adType = "";// 廣告類型,活動,群組,關鍵字
	private String adSearchWay = "";//文字搜尋方式,包含,開頭,全部
	private String searchText = "";//搜尋文字
	private String adShowWay = "";//廣告顯示位址,一般,內容
	private String searchId = "";//廣告id ,某活動,某群組id

	private String stepStr = "";//頁面顯示,目前位址,廣告活動-->act01-->

	//download report 
	private String downloadFlag = "";//download report 旗標

	private InputStream downloadFileStream;//下載報表的 input stream

	private String downloadFileName;//下載顯示名

	private String flashData;//flash chart json data

	private String flashInputValue;//flash chart 頁面傳進來的data

	private String reportTitle;

	public String flashDataDownLoad() throws Exception {

		String fdata[]=flashInputValue.split("&");

		log.info("flashInputValue="+flashInputValue);
		startDate=StringUtils.defaultIfEmpty(fdata[0],"");
		endDate=StringUtils.defaultIfEmpty(fdata[1],"");
		adPvclkDevice=StringUtils.defaultIfEmpty(fdata[2],"");
		adType=StringUtils.defaultIfEmpty(fdata[3],"");
		adSearchWay=StringUtils.defaultIfEmpty(fdata[4],"");
		adShowWay=StringUtils.defaultIfEmpty(fdata[5],"");
		String charPic=StringUtils.defaultIfEmpty(fdata[6],"");//lineChart,barChart
		String charType=StringUtils.defaultIfEmpty(fdata[7],"");//pv,ctr,click,avgcost.cost
		searchId=StringUtils.defaultIfEmpty(fdata[8],"");//pv,ctr,click,avgcost.cost
		searchText=StringUtils.defaultIfEmpty(fdata[9],"");

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

		String customerInfoId = super.getCustomer_info_id();
		log.info(">>> customerInfoId = " + customerInfoId);

		List<AdActionReportVO> resultData = adActionReportService.loadReportDate(EnumReport.REPORT_HQLTYPE_DAILY_CHART.getTextValue(),
				searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, startDate, endDate,-1,-1);

		Map<Date, Float> flashDataMap = new HashMap<Date, Float>();

		double pv = 0;
		double click = 0;
		double cost = 0;
		double invClick = 0;
		double ctr = 0;
		double costAvg = 0;

		for (int i=0; i<resultData.size(); i++) {

			AdActionReportVO vo = resultData.get(i);

			Date reportDate = vo.getReportDate();
			pv = vo.getAdPvSum().doubleValue();
			click = vo.getAdClkSum().doubleValue();
			cost = vo.getAdPriceSum().doubleValue();
			invClick = vo.getAdInvClkSum().doubleValue();

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
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_CTR.getTextValue())) {
				flashDataMap.put(reportDate, new Float((float) ctr));
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_CLICK.getTextValue())) {
				flashDataMap.put(reportDate, new Float((float) click));
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_INVALID.getTextValue())) {
				flashDataMap.put(reportDate, new Float((float) invClick));
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_AVGCOST.getTextValue())) {
				flashDataMap.put(reportDate, new Float((float) costAvg));
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_COST.getTextValue())) {
				flashDataMap.put(reportDate, new Float((float) cost));
			}
		}

		flashData=openFlashUtil.getChartDataForMap(charPic,charType,startDate,endDate,flashDataMap);

		return SUCCESS;
	}

	public ReportAdDailyAction() {

		reportTitle="每日花費成效";

		downloadFlag="no";

		tableHeadNameMap=new HashMap<String,String>();
		tableHeadNameMap.put("曝光數", EnumReport.REPORT_CHART_TYPE_PV.getTextValue());
		tableHeadNameMap.put("點選率", EnumReport.REPORT_CHART_TYPE_CTR.getTextValue());
		tableHeadNameMap.put("點選次數", EnumReport.REPORT_CHART_TYPE_CLICK.getTextValue());
		// 20140318： 隱藏 "無效點選次數" 欄位
		//tableHeadNameMap.put("無效點選次數", EnumReport.REPORT_CHART_TYPE_INVALID.getTextValue());
		tableHeadNameMap.put("平均點選費用", EnumReport.REPORT_CHART_TYPE_AVGCOST.getTextValue());
		tableHeadNameMap.put("費用", EnumReport.REPORT_CHART_TYPE_COST.getTextValue());

		optionSelect="";
		optionNotSelect="";

		//optionSelect="曝光數,點選率(%),點選次數,無效點選次數,平均點選費用,費用";
		// 20140318： 隱藏 "無效點選次數" 欄位
		optionSelect="曝光數,點選率,點選次數,平均點選費用,費用";

		tableHeadShowList=new LinkedList<String>();

		tableHeadNotShowList=new LinkedList<String>();
	}

	public String execute() throws Exception {

		String customerInfoId=super.getCustomer_info_id();
		log.info(">>> customerInfoId = " + customerInfoId);

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

		if(StringUtils.isBlank(adPvclkDevice)){
			adPvclkDevice="";
		}

		if(StringUtils.isEmpty(adType)){
			adType=EnumReport.ADTYPE_ACTIVITY.getTextValue();
		}

		if(StringUtils.isEmpty(adSearchWay)){
			adSearchWay=EnumReport.ADSEARCH_INCLUDE.getTextValue();
		}

		if(StringUtils.isEmpty(adShowWay)){
			adShowWay=Integer.toString(EnumAdType.AD_ALL.getType());
		}

		log.info("adPvclkDevice = " + adPvclkDevice);
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

		int totalPageSize=0;

		tableHeadList.addFirst("裝置");
		tableHeadList.addFirst("日期");

		List<AdActionReportVO> resultSumData = adActionReportService.loadReportDate(EnumReport.REPORT_HQLTYPE_DAILY_COUNT.getTextValue(),
				searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, startDate, endDate, -1, -1);

		totalPageSize=resultSumData.size();

		List<AdActionReportVO> resultData = adActionReportService.loadReportDate(EnumReport.REPORT_HQLTYPE_DAILY.getTextValue(),
				searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, startDate, endDate, page, pageSize);

		if(resultSumData.size()>0){
			resultSumDataTrans(resultSumData);
			resultDataTrans(resultData);
		}

		totalPage =(totalPageSize/pageSize);

		if((totalPageSize%pageSize)>0){
			totalPage+=1;
		}

		if(tableDataList.isEmpty()){
			stepStr="";
		}

		if(downloadFlag.trim().equals("yes")){
			log.info("makeDownloadReportData");
			makeDownloadReportData();
		}

		return SUCCESS;
	}

	private void makeDownloadReportData() throws Exception {

		SimpleDateFormat dformat = new SimpleDateFormat("yyyyMMddhhmmss");

		String filename="每日花費成效報表_" + dformat.format(new Date()) + FILE_TYPE;

		StringBuffer content=new StringBuffer();

		PfpCustomerInfo customerInfo = customerInfoService.findCustomerInfo(super.getCustomer_info_id());

		content.append("帳戶," + customerInfo.getCustomerInfoTitle());
		content.append("\n\n");
		content.append("報表名稱,PChome 每日花費成效");
		content.append("\n\n");
		content.append("廣告方式," + getAdShowWayMap().get(adShowWay));
		content.append("\n\n");
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
				if(dataNumber == 6 || dataNumber == 7){
					content.append("\"NT$ " + s + "\"");
				} else if(dataNumber == 4){
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
				if(dataTotalNumber == 6 || dataTotalNumber == 7){
					content.append("\"NT$ " + s + "\"");
				} else if(dataTotalNumber == 4){
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

	private void resultSumDataTrans(List<AdActionReportVO> resultSumData) {

		NumberFormat intFormat = new DecimalFormat("###,###,###,###");
		NumberFormat doubleFormat = new DecimalFormat("###,###,###,###.##");

		tableDataTotalList = new LinkedList<String>();
		tableDataTotalList.add("總計：" + intFormat.format(resultSumData.size()));
		tableDataTotalList.add("");

		double t_pv = 0; //總曝光數
		double t_click = 0; //總點選次數
		double t_ctr = 0; //點選率
		double t_invalid = 0; //無效點選次數
		double t_costAvg = 0; //平均點選費用
		double t_cost = 0; //總費用

		//加總
		for (int i=0; i<resultSumData.size(); i++) {

			AdActionReportVO vo = resultSumData.get(i);

			t_pv += vo.getAdPvSum().doubleValue();
			t_click += vo.getAdClkSum().doubleValue();
			t_cost += new Double(vo.getAdPriceSum());
			t_invalid += vo.getAdInvClkSum().doubleValue();
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
				if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_PV.getTextValue())) {
					tableDataTotalList.addLast(intFormat.format(t_pv));
				} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_CTR.getTextValue())) {
					tableDataTotalList.addLast(doubleFormat.format(t_ctr));
				} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_CLICK.getTextValue())) {
					tableDataTotalList.addLast(intFormat.format(t_click));
				} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_INVALID.getTextValue())) {
					tableDataTotalList.addLast(intFormat.format(t_invalid));
				} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_AVGCOST.getTextValue())) {
					tableDataTotalList.addLast(doubleFormat.format(t_costAvg));
				} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_COST.getTextValue())) {
					tableDataTotalList.addLast(intFormat.format(t_cost));
				}
			}
		}
	}

	private void resultDataTrans(List<AdActionReportVO> resultData) {

		LinkedList<String> tableInDataList;

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		NumberFormat intFormat = new DecimalFormat("###,###,###,###");
		NumberFormat doubleFormat = new DecimalFormat("###,###,###,###.##");

		for (int i=0; i<resultData.size(); i++) {

			tableInDataList = new LinkedList<String>();

			AdActionReportVO vo = resultData.get(i);

			String reportDate = dateFormat.format(vo.getReportDate());
			double pv = vo.getAdPvSum().doubleValue();
			double click = vo.getAdClkSum().doubleValue();
			double cost = new Double(vo.getAdPriceSum());
			double invClick = vo.getAdInvClkSum().doubleValue();
			double ctr = 0;
			double costAvg = 0;
			String adDevice = vo.getAdDevice();

			//點選率 = 點選次數 / 曝光數
			if (pv>0 && click>0) {
				ctr = (click / pv) * 100;
			}

			//平均點選費用 = 總費用 / 總點選次數
			if (cost>0 && click>0) {
				costAvg = cost / click;
			}

			tableInDataList.addLast(reportDate);
			tableInDataList.addLast(adDevice);

			if(!tableHeadShowList.isEmpty()){
				String mapKey;
				for(String s:tableHeadShowList){
					mapKey=tableHeadNameMap.get(s);
					if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_PV.getTextValue())) {
						tableInDataList.addLast(intFormat.format(pv));
					} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_CTR.getTextValue())) {
						tableInDataList.addLast(doubleFormat.format(ctr));
					} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_CLICK.getTextValue())) {
						tableInDataList.addLast(intFormat.format(click));
					} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_INVALID.getTextValue())) {
						tableInDataList.addLast(intFormat.format(invClick));
					} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_AVGCOST.getTextValue())) {
						tableInDataList.addLast(doubleFormat.format(costAvg));
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

	public void setAdActionReportService(IAdActionReportService adActionReportService) {
		this.adActionReportService = adActionReportService;
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

	public String getStepStr() {
		return stepStr;
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

	public void setFlashInputValue(String flashInputValue) {
		this.flashInputValue = flashInputValue;
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

	public String[] getAlign_data() {
		return align_data;
	}

	public String[] getAlign_sum() {
		return align_sum;
	}
}
