package com.pchome.akbpfp.struts2.action.report;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.pchome.akbpfp.db.dao.report.AdKeywordReportVO;
import com.pchome.akbpfp.db.dao.report.AdVideoPerformanceReportVO;
import com.pchome.akbpfp.db.service.report.IAdVideoPerformanceReportService;
import com.pchome.akbpfp.db.vo.report.ReportQueryConditionVO;
import com.pchome.enumerate.ad.EnumAdType;
import com.pchome.enumerate.ad.EnumAdVideoSizePoolType;
import com.pchome.enumerate.report.EnumReport;
import com.pchome.soft.depot.utils.SpringOpenFlashUtil;
import com.pchome.soft.util.DateValueUtil;

public class ReportVideoPerformanceAction extends BaseReportAction {

	private static final long serialVersionUID = 3038297271832911228L;

	private LinkedList<String> tableHeadList =null; //頁面table head
	

	private LinkedList<String> tableTotalSumList = null; // 頁面 table data

	private String[] align_data = {"center", "center", "center"};
	private String[] align_sum = {"center", "center", "center"};

	//頁面全部加總table total foot
	private LinkedList<String> tableDataTotalSumList = null;
	//下拉選單:計價方式
	private Map<String,String> adPriceTypeMap = new HashMap<>(); 
	//下拉選單:尺寸
	private Map<String,String> adSizeMap = new HashMap<>(); 
	//計價方式
	private String adPriceType="";
	//尺寸
	private String adSize="";
	//裝置:pc、mobile
	private String adPvclkDevice=""; 
	//文字搜尋方式,包含,開頭,全部
	private String adSearchWay=""; 
	//搜尋文字
	private String searchText="";
	//第幾頁
	private int page = 0;
	//每頁筆數
	private int pageSize = 0;
	//總頁數
	private int totalPage = 0;
	
	
	private IAdVideoPerformanceReportService adVideoPerformanceReportService;

	private SpringOpenFlashUtil openFlashUtil=null;


	private LinkedList<String> tableHeadShowList =null;//自訂欄位選擇list,頁面顯示
	private LinkedList<String> tableHeadNotShowList =null;//自訂欄位不選擇list,頁面顯示

	private String optionSelect="";//自訂欄位選擇,頁面回傳
	private String optionNotSelect="";//自訂欄位不選擇,頁面回傳

	private String startDate="";//查詢開始日期
	private String endDate="";//查詢結束日期

	private LinkedHashMap<String,String> dateSelectMap=null;//查詢日期的 rang map,查詢日期頁面顯示

	private String adType=""; //廣告,分類,廣告明細,關鍵字
	
	private String searchId=""; //廣告id,分類id,廣告明細id,關鍵字id
	private String adOperatingRule;//廣告樣式

	private String stepStr="";//頁面顯示,目前位址,廣告活動-->act01-->
	
	private String charPic="";//圖表格式
	private String charType="";//度量

	//download report 
	private String downloadFlag="";//download report 旗標

	private InputStream downloadFileStream;//下載報表的 input stream

	private String downloadFileName;//下載顯示名

	private String flashData;//flash chart json data

	//private String flashInputValue;//flash chart 頁面傳進來的data

	private String reportTitle;

	private String showSearchBarFlag = "y";
	
	//關鍵字報表另外呈現
	private List<AdKeywordReportVO> AdKeywordReportVOList;
	private AdKeywordReportVO AdKeywordReportDataTotal;

	//影音成效明細
	private List<AdVideoPerformanceReportVO> adVideoPerformanceReportVOList;
	//影音成效加總
	private AdVideoPerformanceReportVO adVideoPerformanceReportVOSum;
	
	/**
	 * Chart 
	 */
	public String flashDataDownLoad() throws Exception {
		//查詢日期寫進 cookie
		this.setChooseDate(startDate, endDate);
		dateSelectMap = DateValueUtil.getInstance().getDateRangeMap();

		log.info("charPic="+charPic);//flash 樣式
		log.info("charType="+charType);//資料
		log.info("searchId="+searchId);//資料
		log.info("chstartDate="+startDate);
		log.info("chendDate="+endDate);

		if(searchText.equals("Null")) {
			searchText = "";
		}

		//查詢條件
		ReportQueryConditionVO reportQueryConditionVO = new ReportQueryConditionVO();
		reportQueryConditionVO.setAdPriceType(adPriceType);
		reportQueryConditionVO.setAdPvclkDevice(adPvclkDevice);
		reportQueryConditionVO.setAdSearchWay(adSearchWay);
		reportQueryConditionVO.setAdSize(adSize);
		reportQueryConditionVO.setSearchText(searchText);
		reportQueryConditionVO.setStartDate(startDate);
		reportQueryConditionVO.setEndDate(endDate);
		reportQueryConditionVO.setPage(page);
		reportQueryConditionVO.setPageSize(pageSize);
		reportQueryConditionVO.setTotalPage(totalPage);
		reportQueryConditionVO.setCustomerInfoId(super.getCustomer_info_id());
		
		String customerInfoId = super.getCustomer_info_id();
		log.info(">>> customerInfoId = " + customerInfoId);
		Map<Date,Float> flashDataMap = new HashMap<Date,Float>();
		List<AdVideoPerformanceReportVO> resultData = adVideoPerformanceReportService.loadReportChart(reportQueryConditionVO);
		if(resultData.size() > 0){
			for (AdVideoPerformanceReportVO adVideoPerformanceReportVO : resultData) {
				if (charType.equals(EnumReport.REPORT_CHART_TYPE_PV.getTextValue())) {
					flashDataMap.put(adVideoPerformanceReportVO.getReportDate(), Float.valueOf(adVideoPerformanceReportVO.getAdPvSum()));
				} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_VIEW.getTextValue())) {
					flashDataMap.put(adVideoPerformanceReportVO.getReportDate(), Float.valueOf(adVideoPerformanceReportVO.getAdViewSum()));
				}else if (charType.equals(EnumReport.REPORT_CHART_TYPE_VIEWRATINGS.getTextValue())) {
					flashDataMap.put(adVideoPerformanceReportVO.getReportDate(), Float.valueOf(adVideoPerformanceReportVO.getAdViewRatings()));
				}else if (charType.equals(EnumReport.REPORT_CHART_TYPE_SINGLE_ADVIEWCOST.getTextValue())) {
					flashDataMap.put(adVideoPerformanceReportVO.getReportDate(), Float.valueOf(adVideoPerformanceReportVO.getSingleAdViewCost()));
				}else if (charType.equals(EnumReport.REPORT_CHART_TYPE_THOUSANDS_COST.getTextValue())) {
					flashDataMap.put(adVideoPerformanceReportVO.getReportDate(), Float.valueOf(adVideoPerformanceReportVO.getThousandsCost()));
				}else if (charType.equals(EnumReport.REPORT_CHART_TYPE_COST.getTextValue())) {
					flashDataMap.put(adVideoPerformanceReportVO.getReportDate(), Float.valueOf(adVideoPerformanceReportVO.getCostSum()));
				}else if (charType.equals(EnumReport.REPORT_CHART_TYPE_VIDEO_PROCESS100_RATINGS.getTextValue())) {
					flashDataMap.put(adVideoPerformanceReportVO.getReportDate(), Float.valueOf(adVideoPerformanceReportVO.getAdVideoProcess100Ratings()));
				}else if (charType.equals(EnumReport.REPORT_CHART_TYPE_CLICK.getTextValue())) {
					flashDataMap.put(adVideoPerformanceReportVO.getReportDate(), Float.valueOf(adVideoPerformanceReportVO.getAdClkSum()));
				}else if (charType.equals(EnumReport.REPORT_CHART_TYPE_VIDEO_UNIQ.getTextValue())) {
					flashDataMap.put(adVideoPerformanceReportVO.getReportDate(), Float.valueOf(adVideoPerformanceReportVO.getAdVideoUniqSum()));
				}else if (charType.equals(EnumReport.REPORT_CHART_TYPE_VIDEO_MUSIC.getTextValue())) {
					flashDataMap.put(adVideoPerformanceReportVO.getReportDate(), Float.valueOf(adVideoPerformanceReportVO.getAdVideoMusicSum()));
				}else if (charType.equals(EnumReport.REPORT_CHART_TYPE_VIDEO_REPLAY.getTextValue())) {
					flashDataMap.put(adVideoPerformanceReportVO.getReportDate(), Float.valueOf(adVideoPerformanceReportVO.getAdVideoReplaySum()));
				}
			}
		}
		flashData = openFlashUtil.getChartDataForArray(charType, startDate, endDate, flashDataMap);

		return SUCCESS;
	}

	public ReportVideoPerformanceAction() throws Exception {
		reportTitle="影音廣告成效";
		downloadFlag="no";
	}

	public String execute() throws Exception {
		adSizeMap.put("", "全部尺寸");
		for (EnumAdVideoSizePoolType enumAdVideoSizePoolType : EnumAdVideoSizePoolType.values()) {
			adSizeMap.put(enumAdVideoSizePoolType.getRealWidth()+"_"+enumAdVideoSizePoolType.getRealHeight(), enumAdVideoSizePoolType.getRealWidth()+" x "+enumAdVideoSizePoolType.getRealHeight());
		}
		adPriceTypeMap.put("", "全部計價方式");
		adPriceTypeMap.put("CPV", "單次收視出價-CPV");
		adPriceTypeMap.put("CPM", "千次曝光出價-CPM");
		
		
		dateSelectMap = DateValueUtil.getInstance().getDateRangeMap();
		String customerInfoId = super.getCustomer_info_id();
		String startDate_cookie = this.getChoose_start_date();
		String endDate_cookie = this.getChoose_end_date();
		if (StringUtils.isEmpty(startDate)) {
			if (StringUtils.isNotEmpty(startDate_cookie)) {
				startDate = startDate_cookie;
			} else {
				startDate = DateValueUtil.getInstance().dateToString(DateValueUtil.getInstance().getDateForStartDateAddDay(DateValueUtil.getInstance().getDateValue(DateValueUtil.TODAY, DateValueUtil.DBPATH), -30));
			}
		}

		if (StringUtils.isEmpty(endDate)) {
			if (StringUtils.isNotEmpty(endDate_cookie)) {
				endDate = endDate_cookie;
			} else {
				endDate=DateValueUtil.getInstance().getDateValue(DateValueUtil.TODAY, DateValueUtil.DBPATH);
			}
		}
		
		//分頁查詢條件
		int rowStart = 0;
		int rowEnd = 0;
		if(page == 0){
			rowStart = 0;
			rowEnd = 20;
			page = 1;
			pageSize = 20;
		}else{
			rowStart = (page - 1) * pageSize;
			rowEnd = pageSize;
		}
		
		ReportQueryConditionVO reportQueryConditionVO = new ReportQueryConditionVO();
		reportQueryConditionVO.setAdPriceType(adPriceType);
		reportQueryConditionVO.setAdPvclkDevice(adPvclkDevice);
		reportQueryConditionVO.setAdSearchWay(adSearchWay);
		reportQueryConditionVO.setAdSize(adSize);
		reportQueryConditionVO.setSearchText(searchText);
		reportQueryConditionVO.setStartDate(startDate);
		reportQueryConditionVO.setEndDate(endDate);
		reportQueryConditionVO.setPage(page);
		reportQueryConditionVO.setRowStart(rowStart);
		reportQueryConditionVO.setRowEnd(rowEnd);
		reportQueryConditionVO.setTotalPage(totalPage);
		reportQueryConditionVO.setCustomerInfoId(super.getCustomer_info_id());
		
		//查詢明細
		this.adVideoPerformanceReportVOList = adVideoPerformanceReportService.loadReportDateList(reportQueryConditionVO);
		
		//查詢總數
		this.adVideoPerformanceReportVOSum = adVideoPerformanceReportService.loadReportDateSum(reportQueryConditionVO);
		
		//總頁數
		totalPage = (int)Math.ceil((double)adVideoPerformanceReportVOSum.getTotalSize() / (double)pageSize);
		
		log.info(">>>>>> customerInfoId = " + customerInfoId);
		log.info(">>>>>> startDate = " + startDate);
		log.info(">>>>>> endDate = " + endDate);

		if (StringUtils.isEmpty(adType)) {
			adType = EnumReport.ADTYPE_ACTIVITY.getTextValue();
		}

		if (StringUtils.isEmpty(adSearchWay)) {
			adSearchWay = EnumReport.ADSEARCH_INCLUDE.getTextValue();
		}

		if(downloadFlag.trim().equals("yes")){
			makeDownloadReportData();
		}
		
		return SUCCESS;
	}


	
	
	private void makeDownloadReportData() throws Exception {

		SimpleDateFormat dformat = new SimpleDateFormat("yyyyMMddhhmmss");
		String filename="影音廣告成效報表_" + dformat.format(new Date()) + FILE_TYPE;

		StringBuffer content = new StringBuffer();
		//報表名稱
		downloadFileName = new String(filename.getBytes("UTF-8"), "ISO8859-1");
		content.append("帳戶," + super.getCustomer_info_title());
		content.append("\n\n");
		content.append("報表名稱,影音廣告成效");
		content.append("\n\n");
		content.append("經銷商帳戶,");
		content.append("\n");
		content.append("經銷商帳號,");
		content.append("\n");
		content.append("日期範圍,"+startDate+"到"+endDate);
		content.append("\n");
		content.append("計價方式,");
		content.append("\n");
		content.append("裝置,");
		content.append("\n");
		content.append("廣告尺寸,");
		content.append("\n");
		content.append("搜尋條件,字詞包含");
		content.append("\n");
		content.append("搜尋廣告明細名稱");
		content.append("\n\n");
		content.append(""
				+ "影片名稱,"
				+ "影片長度,"
				+ "顯示連結,"
				+ "實際連結,"
				+ "計價方式,"
				+ "裝置,"
				+ "廣告尺寸,"
				+ "曝光數,"
				+ "收視次數,"
				+ "收視率,"
				+ "千次曝光費用,"
				+ "單次收視費用,"
				+ "費用,"
				+ "點選次數,"
				+ "收視人數(不重複),"
				+ "聲音開啟次數,"
				+ "影片重播次數,"
				+ "影片播放進度(25%),"
				+ "影片播放進度(50%),"
				+ "影片播放進度(75%),"
				+ "影片播放進度(100%),"
				+ "影片完整播放率");
		
		//列表值
		float sumPV = 0;
		float sumView = 0;
		float sumCost = 0;
		float sumVideoProvess25 = 0;
		float sumVideoProvess50 = 0;
		float sumVideoProvess75 = 0;
		float sumVideoProvess100 = 0;
		float sumClick = 0;
		float sumVideoUniq = 0;
		float sumMusic = 0;
		float sumReplay = 0;
		//總計total值
		float sumViewRatings = 0;
		float sumThousandsCost = 0;
		float sumSingleAdViewCost = 0;
		float sumVideoProcess100Ratings = 0;
		
		//列表
		for (AdVideoPerformanceReportVO adVideoPerformanceReportVO : adVideoPerformanceReportVOList) {
			sumPV = sumPV + Float.valueOf(adVideoPerformanceReportVO.getAdPvSum());
			sumView = sumView + Float.valueOf(adVideoPerformanceReportVO.getAdViewSum());
			sumCost = sumCost + Float.valueOf(adVideoPerformanceReportVO.getCostSum());
			sumVideoProvess25 = sumVideoProvess25 + Float.valueOf(adVideoPerformanceReportVO.getAdVideoProcess25Sum());
			sumVideoProvess50 = sumVideoProvess50 + Float.valueOf(adVideoPerformanceReportVO.getAdVideoProcess50Sum());
			sumVideoProvess75 = sumVideoProvess75 + Float.valueOf(adVideoPerformanceReportVO.getAdVideoProcess75Sum());
			sumVideoProvess100 = sumVideoProvess100 + Float.valueOf(adVideoPerformanceReportVO.getAdVideoProcess100Sum());
			sumClick = sumClick + Float.valueOf(adVideoPerformanceReportVO.getAdClkSum());
			sumVideoUniq = sumVideoUniq + Float.valueOf(adVideoPerformanceReportVO.getAdVideoUniqSum());
			sumMusic = sumMusic + Float.valueOf(adVideoPerformanceReportVO.getAdVideoMusicSum());
			sumReplay = sumReplay + Float.valueOf(adVideoPerformanceReportVO.getAdVideoReplaySum());
			sumViewRatings = (sumView / sumPV * 100);
			sumThousandsCost = (sumCost / sumPV * 1000);
			sumSingleAdViewCost = (sumCost / sumView);
			sumVideoProcess100Ratings = (sumVideoProvess100 / sumView * 100);
			
			content.append("\n");
			content.append("影片名稱"+adVideoPerformanceReportVO.getTitle()+",");
			content.append("影片長度 00:"+adVideoPerformanceReportVO.getAdVideoSec()+",");
			content.append(adVideoPerformanceReportVO.getVideoUrl()+",");
			content.append(adVideoPerformanceReportVO.getAdLinkUrl()+",");
			content.append((StringUtils.isBlank(adPriceType)? "全部" : adPriceType ) + ",");
			content.append((StringUtils.isBlank(adPvclkDevice)? "全部" : adPvclkDevice )+ ",");
			content.append((StringUtils.isBlank(adSize)? "全部" : adVideoPerformanceReportVO.getTemplateProductWidth() + " x "+ adVideoPerformanceReportVO.getTemplateProductHeight())+",");
			content.append(adVideoPerformanceReportVO.getAdPvSum()+",");
			content.append(adVideoPerformanceReportVO.getAdViewSum()+",");
			content.append(adVideoPerformanceReportVO.getAdViewRatings()+",");
			content.append("$"+adVideoPerformanceReportVO.getThousandsCost()+",");
			content.append("$"+adVideoPerformanceReportVO.getSingleAdViewCost()+",");
			content.append("$"+adVideoPerformanceReportVO.getCostSum()+",");
			content.append(adVideoPerformanceReportVO.getAdClkSum()+",");
			content.append(adVideoPerformanceReportVO.getAdVideoUniqSum()+",");
			content.append(adVideoPerformanceReportVO.getAdVideoMusicSum()+",");
			content.append(adVideoPerformanceReportVO.getAdVideoReplaySum()+",");
			content.append(adVideoPerformanceReportVO.getAdVideoProcess25Sum()+"%"+",");
			content.append(adVideoPerformanceReportVO.getAdVideoProcess50Sum()+"%"+",");
			content.append(adVideoPerformanceReportVO.getAdVideoProcess75Sum()+"%"+",");
			content.append(adVideoPerformanceReportVO.getAdVideoProcess100Sum()+"%"+",");
			content.append(adVideoPerformanceReportVO.getAdVideoProcess100Ratings()+"%");
		}
		//總計
		content.append("\n\n");
		content.append(""
				+ "總計:共"+adVideoPerformanceReportVOList.size()+"筆"+","
				+ ","
				+ ","
				+ ","
				+ ","
				+ (StringUtils.isBlank(adPriceType)? "全部" : adPriceType ) + ","
				+ ","
				+ sumPV+","
				+ sumView+","
				+ sumViewRatings+"%,"
				+ "$"+sumThousandsCost+","
				+ "$"+sumSingleAdViewCost+","
				+ "$"+sumCost+","
				+ sumClick+","
				+ sumVideoUniq+","
				+ sumMusic+","
				+ sumReplay+","
				+ sumVideoProvess25+"%,"
				+ sumVideoProvess50+"%,"
				+ sumVideoProvess75+"%,"
				+ sumVideoProvess100+"%,"
				+ sumVideoProcess100Ratings+"%");
		downloadFileStream = new ByteArrayInputStream(content.toString().getBytes("big5"));
	}
	
	
	
	
	
	
	public LinkedList<String> getTableHeadList() {
		return tableHeadList;
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


	public String getAdPriceType() {
		return adPriceType;
	}

	public void setAdPriceType(String adPriceType) {
		this.adPriceType = adPriceType;
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

	public void setOpenFlashUtil(SpringOpenFlashUtil openFlashUtil) {
		this.openFlashUtil = openFlashUtil;
	}

	public String getFlashData() {
		return flashData;
	}


	public LinkedHashMap<String, String> getDateSelectMap() {
		return dateSelectMap;
	}


	public LinkedList<String> getTableDataTotalSumList() {
		return tableDataTotalSumList;
	}

	public void setTableDataTotalSumList(LinkedList<String> tableDataTotalSumList) {
		this.tableDataTotalSumList = tableDataTotalSumList;
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

	public String getShowSearchBarFlag() {
		return showSearchBarFlag;
	}

	public void setShowSearchBarFlag(String showSearchBarFlag) {
		this.showSearchBarFlag = showSearchBarFlag;
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

	public List<AdKeywordReportVO> getAdKeywordReportVOList() {
		return AdKeywordReportVOList;
	}

	public AdKeywordReportVO getAdKeywordReportDataTotal() {
		return AdKeywordReportDataTotal;
	}

	public String getAdOperatingRule() {
		return adOperatingRule;
	}

	public void setAdOperatingRule(String adOperatingRule) {
		this.adOperatingRule = adOperatingRule;
	}

	public List<AdVideoPerformanceReportVO> getAdVideoPerformanceReportVOList() {
		return adVideoPerformanceReportVOList;
	}

	public void setAdVideoPerformanceReportVOList(List<AdVideoPerformanceReportVO> adVideoPerformanceReportVOList) {
		this.adVideoPerformanceReportVOList = adVideoPerformanceReportVOList;
	}

	public IAdVideoPerformanceReportService getAdVideoPerformanceReportService() {
		return adVideoPerformanceReportService;
	}

	public void setAdVideoPerformanceReportService(IAdVideoPerformanceReportService adVideoPerformanceReportService) {
		this.adVideoPerformanceReportService = adVideoPerformanceReportService;
	}

	public Map<String, String> getAdPriceTypeMap() {
		return adPriceTypeMap;
	}

	public void setAdPriceTypeMap(Map<String, String> adPriceTypeMap) {
		this.adPriceTypeMap = adPriceTypeMap;
	}

	public Map<String, String> getAdSizeMap() {
		return adSizeMap;
	}

	public void setAdSizeMap(Map<String, String> adSizeMap) {
		this.adSizeMap = adSizeMap;
	}

	public String getAdSize() {
		return adSize;
	}

	public void setAdSize(String adSize) {
		this.adSize = adSize;
	}

	public AdVideoPerformanceReportVO getAdVideoPerformanceReportVOSum() {
		return adVideoPerformanceReportVOSum;
	}

	public void setAdVideoPerformanceReportVOSum(AdVideoPerformanceReportVO adVideoPerformanceReportVOSum) {
		this.adVideoPerformanceReportVOSum = adVideoPerformanceReportVOSum;
	}
	
}
