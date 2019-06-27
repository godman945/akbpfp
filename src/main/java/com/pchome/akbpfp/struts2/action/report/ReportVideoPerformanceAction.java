package com.pchome.akbpfp.struts2.action.report;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
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

import com.pchome.akbpfp.db.dao.report.AdGroupReportVO;
import com.pchome.akbpfp.db.dao.report.AdKeywordReportVO;
import com.pchome.akbpfp.db.dao.report.AdVideoPerformanceReportVO;
import com.pchome.akbpfp.db.pojo.PfpCode;
import com.pchome.akbpfp.db.pojo.PfpCustomerInfo;
import com.pchome.akbpfp.db.service.customerInfo.IPfpCustomerInfoService;
import com.pchome.akbpfp.db.service.report.IAdVideoPerformanceReportService;
import com.pchome.akbpfp.db.vo.report.ReportQueryConditionVO;
import com.pchome.enumerate.ad.EnumAdPriceType;
import com.pchome.enumerate.ad.EnumAdVideoSizePoolType;
import com.pchome.enumerate.report.EnumReport;
import com.pchome.soft.depot.utils.SpringOpenFlashUtil;
import com.pchome.soft.depot.utils.DateValueUtil;
import com.pchome.utils.CommonUtils;

public class ReportVideoPerformanceAction extends BaseReportAction {

	private static final long serialVersionUID = 3038297271832911228L;

	private IAdVideoPerformanceReportService adVideoPerformanceReportService;
	
	private LinkedHashMap<String, String> dateSelectMap; // 查詢日期的 rang map,查詢日期頁面顯示
	private LinkedHashMap<String, String> sizeSelectMap; // 廣告尺寸下拉選單map
	
	private String startDate = ""; // 查詢開始日期
	private String endDate = ""; // 查詢結束日期
	private String searchText = ""; // 搜尋文字
	private String whereMap; // 篩選
	private String sortBy = ""; // 排序
	private int page = 1; // 第幾頁
	private int pageSize = 10; // 每頁筆數
	private int totalPage = 0; //總頁數
	
	private List<AdVideoPerformanceReportVO> resultData = new ArrayList<>(); // 查詢結果
	private List<AdVideoPerformanceReportVO> resultSumData = new ArrayList<>(); // 查詢結果加總
	
	// 下載報表
	private String isDownload = "false";
	private IPfpCustomerInfoService customerInfoService;
	private InputStream downloadFileStream; // 下載報表的 input stream
	private String downloadFileName; // 下載顯示名
	private String showHideColumn = ""; // 哪些欄位顯示或隱藏
	private Map<String, Boolean> showHideColumnMap = new HashMap<>(); // 紀錄顯示其他欄位值
	
	// 圖表
	private SpringOpenFlashUtil openFlashUtil;
	private String flashData;
	private String charType = ""; // 度量
	
	/**
	 * 查詢
	 */
	public String execute() throws Exception {
		
		dateSelectMap = DateValueUtil.getInstance().getDateRangeMap();
		
		String startDateCookie = super.getChoose_start_date();
		String endDateCookie = super.getChoose_end_date();
		log.info(">>> startDateCookie = " + startDateCookie);
		log.info(">>> endDateCookie = " + endDateCookie);

		if (StringUtils.isEmpty(startDate)) {
			if (StringUtils.isNotEmpty(startDateCookie)) {
				startDate = startDateCookie;
			} else {
				startDate = DateValueUtil.getInstance().dateToString(DateValueUtil.getInstance().getDateForStartDateAddDay(DateValueUtil.getInstance().getDateValue(DateValueUtil.TODAY, DateValueUtil.DBPATH), -30));
			}
		}

		if (StringUtils.isEmpty(endDate)) {
			if (StringUtils.isNotEmpty(endDateCookie)) {
				endDate = endDateCookie;
			} else {
				endDate = DateValueUtil.getInstance().getDateValue(DateValueUtil.TODAY, DateValueUtil.DBPATH);
			}
		}
		
		// 查詢日期寫進 cookie
		super.setChooseDate(startDate, endDate);
		
		AdVideoPerformanceReportVO vo = new AdVideoPerformanceReportVO();
		vo.setCustomerInfoId(super.getCustomer_info_id());
		vo.setStartDate(startDate);
		vo.setEndDate(endDate);
		vo.setSearchText(searchText);
		vo.setWhereMap(whereMap);
		vo.setSortBy(sortBy);
		vo.setPage(page);
		vo.setPageSize(pageSize);
		vo.setDownloadOrIsNotCuttingPagination(Boolean.parseBoolean(isDownload));
		
		sizeSelectMap = adVideoPerformanceReportService.queryReportAdVideoPerformanceSize(vo);
		
		resultData = adVideoPerformanceReportService.queryReportAdVideoPerformanceData(vo);
		resultSumData = adVideoPerformanceReportService.queryReportAdVideoPerformanceSumData(vo);
		
		totalPage = CommonUtils.getTotalPage(vo.getRowCount(), pageSize);
		
		if (Boolean.parseBoolean(isDownload)) {
			makeDownloadReportData();
		}
		
		return SUCCESS;
	}
	
	/**
	 * 下載報表
	 * 先執行execute()，是下載報表才再執行此方法
	 * @throws Exception
	 */
	private void makeDownloadReportData() throws Exception {
		
		SimpleDateFormat dformat = new SimpleDateFormat("yyyyMMddhhmmss");
		String filename = "影音廣告成效報表_" + dformat.format(new Date()) + FILE_TYPE;
		PfpCustomerInfo customerInfo = customerInfoService.findCustomerInfo(super.getCustomer_info_id());

		// 紀錄顯示其他欄位，哪些顯示哪些不顯示
		String[] showHideColumnArr = showHideColumn.split(",");
		for (int i = 0; i < showHideColumnArr.length; i++) {
			String mapKey = showHideColumnArr[i].split("-")[0];
			boolean mapVal = Boolean.parseBoolean(showHideColumnArr[i].split("-")[1]);
			showHideColumnMap.put(mapKey, mapVal);
		}
		
		StringBuffer content = new StringBuffer();
		content.append("帳戶," + customerInfo.getCustomerInfoTitle());
		content.append("\n");
		content.append("搜尋內容," + searchText);
		content.append("\n");
		content.append("日期範圍," + startDate + " 到 " + endDate);
		content.append("\n\n");
		
		content.append("影片名稱,影片長度,顯示連結,實際連結,廣告活動,廣告分類,");

		if (showHideColumnMap.get(EnumReport.AD_CLK_PRICE_TYPE.getTextValue())) {
			content.append("計價方式,");
		}

		content.append("裝置,廣告尺寸,曝光數,收視數,收視率,單次收視費用,千次曝光費用,費用,");

		if (showHideColumnMap.get(EnumReport.AD_VIDEO_PROCESS.getTextValue())) {
			content.append("影片播放進度-25%,");
			content.append("影片播放進度-50%,");
			content.append("影片播放進度-75%,");
			content.append("影片播放進度-100%,");
		}
		
		if (showHideColumnMap.get(EnumReport.AD_VIDEO_PROCESS_100_RATINGS.getTextValue())) {
			content.append("影片完整播放率,");
		}
		
		if (showHideColumnMap.get(EnumReport.AD_CLK_SUM.getTextValue())) {
			content.append("點選次數,");
		}
		
		if (showHideColumnMap.get(EnumReport.AD_VIDEO_UNIQ_SUM.getTextValue())) {
			content.append("收視人數-不重複,");
		}
		
		if (showHideColumnMap.get(EnumReport.AD_VIDEO_MUSIC_SUM.getTextValue())) {
			content.append("聲音開啟次數,");
		}
		
		if (showHideColumnMap.get(EnumReport.AD_VIDEO_REPLAY_SUM.getTextValue())) {
			content.append("重播次數,");
		}
		content.append("\n");
		
		if (!resultData.isEmpty()) {
			// 明細資料
			for (int i = 0; i < resultData.size(); i++) {
				content.append("\"" + resultData.get(i).getTitle() + "\",");
				content.append("\"00:" + resultData.get(i).getAdVideoSec() + "\",");
				content.append("\"" + resultData.get(i).getVideoUrl() + "\",");
				content.append("\"" + resultData.get(i).getAdLinkUrl() + "\",");
				content.append("\"" + resultData.get(i).getAdActionName() + "\",");
				content.append("\"" + resultData.get(i).getAdGroupName() + "\",");
				
				if (showHideColumnMap.get(EnumReport.AD_CLK_PRICE_TYPE.getTextValue())) {
					content.append("\"" + resultData.get(i).getAdClkPriceType() + "\",");
				}
				
				content.append("\"" + resultData.get(i).getAdDevice() + "\",");
				content.append("\"" + resultData.get(i).getTemplateProductWidth() + " x " + resultData.get(i).getTemplateProductHeight() + "\",");
				content.append("\"" + intFormat.format(resultData.get(i).getAdPvSum()) + "\",");
				content.append("\"" + doubleFormat.format(resultData.get(i).getAdViewSum()) + "\",");
				content.append("\"" + doubleFormat.format(resultData.get(i).getAdViewRatings()) + "%\",");
				content.append("\"NT$ " + doubleFormat.format(resultData.get(i).getSingleAdViewCost()) + "\",");
				content.append("\"NT$ " + doubleFormat.format(resultData.get(i).getKiloCost()) + "\",");
				content.append("\"NT$ " + doubleFormat2.format(resultData.get(i).getAdPriceSum()) + "\",");
				
				if (showHideColumnMap.get(EnumReport.AD_VIDEO_PROCESS.getTextValue())) {
					content.append("\"" + doubleFormat.format(resultData.get(i).getAdVideoProcess25Sum()) + "\",");
					content.append("\"" + doubleFormat.format(resultData.get(i).getAdVideoProcess50Sum()) + "\",");
					content.append("\"" + doubleFormat.format(resultData.get(i).getAdVideoProcess75Sum()) + "\",");
					content.append("\"" + doubleFormat.format(resultData.get(i).getAdVideoProcess100Sum()) + "\",");
				}
				
				if (showHideColumnMap.get(EnumReport.AD_VIDEO_PROCESS_100_RATINGS.getTextValue())) {
					content.append("\"" + doubleFormat.format(resultData.get(i).getAdVideoProcess100Ratings()) + "%\",");
				}
				
				if (showHideColumnMap.get(EnumReport.AD_CLK_SUM.getTextValue())) {
					content.append("\"" + doubleFormat.format(resultData.get(i).getAdClkSum()) + "\",");
				}
				
				if (showHideColumnMap.get(EnumReport.AD_VIDEO_UNIQ_SUM.getTextValue())) {
					content.append("\"" + doubleFormat.format(resultData.get(i).getAdVideoUniqSum()) + "\",");
				}
				
				if (showHideColumnMap.get(EnumReport.AD_VIDEO_MUSIC_SUM.getTextValue())) {
					content.append("\"" + doubleFormat.format(resultData.get(i).getAdVideoMusicSum()) + "\",");
				}
				
				if (showHideColumnMap.get(EnumReport.AD_VIDEO_REPLAY_SUM.getTextValue())) {
					content.append("\"" + doubleFormat.format(resultData.get(i).getAdVideoReplaySum()) + "\",");
				}
				content.append("\n");
			}
			
			content.append("\n");
			
			// 總計資料
			for (int i = 0; i < resultSumData.size(); i++) {
				content.append("\"總計：" + intFormat.format(resultSumData.get(i).getRowCount()) + "\",");
				content.append("\"\",");
				content.append("\"\",");
				content.append("\"\",");
				content.append("\"\",");
				content.append("\"\",");
				
				if (showHideColumnMap.get(EnumReport.AD_CLK_PRICE_TYPE.getTextValue())) {
					content.append("\"\",");
				}
				content.append("\"\",");
				content.append("\"\",");
				content.append("\"" + doubleFormat.format(resultSumData.get(i).getAdPvSum()) + "\",");
				content.append("\"" + doubleFormat.format(resultSumData.get(i).getAdViewSum()) + "\",");
				content.append("\"" + doubleFormat.format(resultSumData.get(i).getAdViewRatings()) + "%\",");
				content.append("\"NT$ " + doubleFormat.format(resultSumData.get(i).getSingleAdViewCost()) + "\",");
				content.append("\"NT$ " + doubleFormat.format(resultSumData.get(i).getKiloCost()) + "\",");
				content.append("\"NT$ " + doubleFormat2.format(resultSumData.get(i).getAdPriceSum()) + "\",");
				
				if (showHideColumnMap.get(EnumReport.AD_VIDEO_PROCESS.getTextValue())) {
					content.append("\"" + doubleFormat.format(resultSumData.get(i).getAdVideoProcess25Sum()) + "\",");
					content.append("\"" + doubleFormat.format(resultSumData.get(i).getAdVideoProcess50Sum()) + "\",");
					content.append("\"" + doubleFormat.format(resultSumData.get(i).getAdVideoProcess75Sum()) + "\",");
					content.append("\"" + doubleFormat.format(resultSumData.get(i).getAdVideoProcess100Sum()) + "\",");
				}

				if (showHideColumnMap.get(EnumReport.AD_VIDEO_PROCESS_100_RATINGS.getTextValue())) {
					content.append("\"" + doubleFormat.format(resultSumData.get(i).getAdVideoProcess100Ratings()) + "%\",");
				}

				if (showHideColumnMap.get(EnumReport.AD_CLK_SUM.getTextValue())) {
					content.append("\"" + doubleFormat.format(resultSumData.get(i).getAdClkSum()) + "\",");
				}

				if (showHideColumnMap.get(EnumReport.AD_VIDEO_UNIQ_SUM.getTextValue())) {
					content.append("\"" + doubleFormat.format(resultSumData.get(i).getAdVideoUniqSum()) + "\",");
				}

				if (showHideColumnMap.get(EnumReport.AD_VIDEO_MUSIC_SUM.getTextValue())) {
					content.append("\"" + doubleFormat.format(resultSumData.get(i).getAdVideoMusicSum()) + "\",");
				}

				if (showHideColumnMap.get(EnumReport.AD_VIDEO_REPLAY_SUM.getTextValue())) {
					content.append("\"" + doubleFormat.format(resultSumData.get(i).getAdVideoReplaySum()) + "\",");
				}
			}
		}
		
		if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > -1) {
			downloadFileName = new String(filename.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
		} else {
			downloadFileName = URLEncoder.encode(filename, "UTF-8");
		}

		downloadFileStream = new ByteArrayInputStream(content.toString().getBytes("big5"));
	}
	
	/**
	 * 圖表
	 * @return
	 */
	public String flashDataDownLoad() {
		AdVideoPerformanceReportVO chartVo = new AdVideoPerformanceReportVO();
		chartVo.setCharType(charType);
		chartVo.setCustomerInfoId(super.getCustomer_info_id());
		chartVo.setStartDate(startDate);
		chartVo.setEndDate(endDate);
		chartVo.setSearchText(searchText);
		chartVo.setWhereMap(whereMap);
		Map<Date, Float> flashDataMap = adVideoPerformanceReportService.queryReportAdVideoPerformanceChartDataMap(chartVo);
		
		flashData = openFlashUtil.getChartDataForArray(charType, startDate, endDate, flashDataMap);
		return SUCCESS;
	}
	
	public void setAdVideoPerformanceReportService(IAdVideoPerformanceReportService adVideoPerformanceReportService) {
		this.adVideoPerformanceReportService = adVideoPerformanceReportService;
	}

	public LinkedHashMap<String, String> getDateSelectMap() {
		return dateSelectMap;
	}
	
	public LinkedHashMap<String, String> getSizeSelectMap() {
		return sizeSelectMap;
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

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public void setWhereMap(String whereMap) {
		this.whereMap = whereMap;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public List<AdVideoPerformanceReportVO> getResultData() {
		return resultData;
	}

	public List<AdVideoPerformanceReportVO> getResultSumData() {
		return resultSumData;
	}

	public void setIsDownload(String isDownload) {
		this.isDownload = isDownload;
	}

	public void setCustomerInfoService(IPfpCustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}

	public InputStream getDownloadFileStream() {
		return downloadFileStream;
	}

	public String getDownloadFileName() {
		return downloadFileName;
	}

	public void setShowHideColumn(String showHideColumn) {
		this.showHideColumn = showHideColumn;
	}

	public void setOpenFlashUtil(SpringOpenFlashUtil openFlashUtil) {
		this.openFlashUtil = openFlashUtil;
	}

	public String getFlashData() {
		return flashData;
	}

	public void setCharType(String charType) {
		this.charType = charType;
	}
	
	
	
	
	
	
//	private LinkedList<String> tableHeadList =null; //頁面table head
//	private String[] align_data = {"center", "center", "center"};
//	private String[] align_sum = {"center", "center", "center"};
//
//	//頁面全部加總table total foot
//	private LinkedList<String> tableDataTotalSumList = null;
//	//下拉選單:計價方式
//	private Map<String,String> adPriceTypeMap = new HashMap<>(); 
//	//下拉選單:尺寸
//	private Map<String,String> adSizeMap = new HashMap<>(); 
//	//計價方式
//	private String adPriceType="";
//	//尺寸
//	private String adSize="";
//	//裝置:pc、mobile
//	private String adPvclkDevice=""; 
//	//文字搜尋方式,包含,開頭,全部
//	private String adSearchWay=""; 
//	//搜尋文字
//	private String searchText="";
//	//第幾頁
//	private int page = 0;
//	//每頁筆數
//	private int pageSize = 0;
//	//總頁數
//	private int totalPage = 0;
//	
//	private IAdVideoPerformanceReportService adVideoPerformanceReportService;
//
//	private SpringOpenFlashUtil openFlashUtil=null;
//
//
//	private LinkedList<String> tableHeadShowList =null;//自訂欄位選擇list,頁面顯示
//	private LinkedList<String> tableHeadNotShowList =null;//自訂欄位不選擇list,頁面顯示
//
//	private String optionSelect="";//自訂欄位選擇,頁面回傳
//	private String optionNotSelect="";//自訂欄位不選擇,頁面回傳
//
//	private String startDate="";//查詢開始日期
//	private String endDate="";//查詢結束日期
//
//	private LinkedHashMap<String,String> dateSelectMap=null;//查詢日期的 rang map,查詢日期頁面顯示
//
//	private String adType=""; //廣告,分類,廣告明細,關鍵字
//	
//	private String searchId=""; //廣告id,分類id,廣告明細id,關鍵字id
//	private String adOperatingRule;//廣告樣式
//
//	private String stepStr="";//頁面顯示,目前位址,廣告活動-->act01-->
//	
//	private String charPic="";//圖表格式
//	private String charType="";//度量
//
//	//download report 
//	private String downloadFlag="";//download report 旗標
//
//	private InputStream downloadFileStream;//下載報表的 input stream
//
//	private String downloadFileName;//下載顯示名
//
//	private String flashData;//flash chart json data
//
//	//private String flashInputValue;//flash chart 頁面傳進來的data
//
//	private String reportTitle;
//
//	private String showSearchBarFlag = "y";
//	
//	//關鍵字報表另外呈現
//	private List<AdKeywordReportVO> AdKeywordReportVOList;
//	private AdKeywordReportVO AdKeywordReportDataTotal;
//
//	//影音成效明細
//	private List<AdVideoPerformanceReportVO> adVideoPerformanceReportVOList;
//	//影音成效加總
//	private List<AdVideoPerformanceReportVO> adVideoPerformanceReportVOSum;
//	
//	private AdVideoPerformanceReportVO adVideoPerformanceReportVOCount = new AdVideoPerformanceReportVO();
//	
//	private DecimalFormat decimalFormat = new DecimalFormat("0.00");
//	/**
//	 * Chart 
//	 */
//	public String flashDataDownLoad() throws Exception {
//		//查詢日期寫進 cookie
//		this.setChooseDate(startDate, endDate);
//		dateSelectMap = DateValueUtil.getInstance().getDateRangeMap();
//
//		log.info("charPic="+charPic);//flash 樣式
//		log.info("charType="+charType);//資料
//		log.info("searchId="+searchId);//資料
//		log.info("chstartDate="+startDate);
//		log.info("chendDate="+endDate);
//
//		if(searchText.equals("Null")) {
//			searchText = "";
//		}
//
//		//查詢條件
//		ReportQueryConditionVO reportQueryConditionVO = new ReportQueryConditionVO();
//		reportQueryConditionVO.setAdPriceType(adPriceType);
//		reportQueryConditionVO.setAdPvclkDevice(adPvclkDevice);
//		reportQueryConditionVO.setAdSearchWay(adSearchWay);
//		reportQueryConditionVO.setAdSize(adSize);
//		reportQueryConditionVO.setSearchText(searchText);
//		reportQueryConditionVO.setStartDate(startDate);
//		reportQueryConditionVO.setEndDate(endDate);
//		reportQueryConditionVO.setPage(page);
//		reportQueryConditionVO.setPageSize(pageSize);
//		reportQueryConditionVO.setTotalPage(totalPage);
//		reportQueryConditionVO.setCustomerInfoId(super.getCustomer_info_id());
//		
//		String customerInfoId = super.getCustomer_info_id();
//		log.info(">>> customerInfoId = " + customerInfoId);
//		Map<Date,Float> flashDataMap = new HashMap<Date,Float>();
//		List<AdVideoPerformanceReportVO> resultData = adVideoPerformanceReportService.loadReportChart(reportQueryConditionVO);
//		
//		if(resultData.size() > 0){
//			for (AdVideoPerformanceReportVO adVideoPerformanceReportVO : resultData) {
//				if (charType.equals(EnumReport.REPORT_CHART_TYPE_PV.getTextValue())) {
//					flashDataMap.put(adVideoPerformanceReportVO.getReportDate(), Float.valueOf(adVideoPerformanceReportVO.getAdPvSum()));
//				} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_VIEW.getTextValue())) {
//					flashDataMap.put(adVideoPerformanceReportVO.getReportDate(), Float.valueOf(adVideoPerformanceReportVO.getAdViewSum()));
//				}else if (charType.equals(EnumReport.REPORT_CHART_TYPE_VIEWRATINGS.getTextValue())) {
//					flashDataMap.put(adVideoPerformanceReportVO.getReportDate(), Float.valueOf(adVideoPerformanceReportVO.getAdViewRatings()));
//				}else if (charType.equals(EnumReport.REPORT_CHART_TYPE_SINGLE_ADVIEWCOST.getTextValue())) {
//					flashDataMap.put(adVideoPerformanceReportVO.getReportDate(), Float.valueOf(adVideoPerformanceReportVO.getSingleAdViewCost()));
//				}else if (charType.equals(EnumReport.REPORT_CHART_TYPE_THOUSANDS_COST.getTextValue())) {
//					flashDataMap.put(adVideoPerformanceReportVO.getReportDate(), Float.valueOf(adVideoPerformanceReportVO.getThousandsCost()));
//				}else if (charType.equals(EnumReport.REPORT_CHART_TYPE_COST.getTextValue())) {
//					adVideoPerformanceReportVO.setCostSum(new BigDecimal(String.valueOf(adVideoPerformanceReportVO.getCostSum())).setScale(3, BigDecimal.ROUND_FLOOR).toString());
//					flashDataMap.put(adVideoPerformanceReportVO.getReportDate(), Float.valueOf(adVideoPerformanceReportVO.getCostSum()));
//				}else if (charType.equals(EnumReport.REPORT_CHART_TYPE_VIDEO_PROCESS100_RATINGS.getTextValue())) {
//					flashDataMap.put(adVideoPerformanceReportVO.getReportDate(), Float.valueOf(adVideoPerformanceReportVO.getAdVideoProcess100Ratings()));
//				}else if (charType.equals(EnumReport.REPORT_CHART_TYPE_CLICK.getTextValue())) {
//					flashDataMap.put(adVideoPerformanceReportVO.getReportDate(), Float.valueOf(adVideoPerformanceReportVO.getAdClkSum()));
//				}else if (charType.equals(EnumReport.REPORT_CHART_TYPE_VIDEO_UNIQ.getTextValue())) {
//					flashDataMap.put(adVideoPerformanceReportVO.getReportDate(), Float.valueOf(adVideoPerformanceReportVO.getAdVideoUniqSum()));
//				}else if (charType.equals(EnumReport.REPORT_CHART_TYPE_VIDEO_MUSIC.getTextValue())) {
//					flashDataMap.put(adVideoPerformanceReportVO.getReportDate(), Float.valueOf(adVideoPerformanceReportVO.getAdVideoMusicSum()));
//				}else if (charType.equals(EnumReport.REPORT_CHART_TYPE_VIDEO_REPLAY.getTextValue())) {
//					flashDataMap.put(adVideoPerformanceReportVO.getReportDate(), Float.valueOf(adVideoPerformanceReportVO.getAdVideoReplaySum()));
//				}
//			}
//		}
//		flashData = openFlashUtil.getChartDataForArray(charType, startDate, endDate, flashDataMap);
//
//		return SUCCESS;
//	}
//
//	public ReportVideoPerformanceAction() throws Exception {
//		reportTitle="影音廣告成效";
//		downloadFlag="no";
//	}
//
//	public String execute() throws Exception {
//		adSizeMap.put("", "全部尺寸");
//		for (EnumAdVideoSizePoolType enumAdVideoSizePoolType : EnumAdVideoSizePoolType.values()) {
//			adSizeMap.put(enumAdVideoSizePoolType.getRealWidth()+"_"+enumAdVideoSizePoolType.getRealHeight(), enumAdVideoSizePoolType.getRealWidth()+" x "+enumAdVideoSizePoolType.getRealHeight());
//		}
//		adPriceTypeMap.put("", "全部計價方式");
//		adPriceTypeMap.put("CPV", "單次收視出價-CPV");
//		adPriceTypeMap.put("CPM", "千次曝光出價-CPM");
//		
//		
//		dateSelectMap = DateValueUtil.getInstance().getDateRangeMap();
//		String customerInfoId = super.getCustomer_info_id();
//		String startDate_cookie = this.getChoose_start_date();
//		String endDate_cookie = this.getChoose_end_date();
//		if (StringUtils.isEmpty(startDate)) {
//			if (StringUtils.isNotEmpty(startDate_cookie)) {
//				startDate = startDate_cookie;
//			} else {
//				startDate = DateValueUtil.getInstance().dateToString(DateValueUtil.getInstance().getDateForStartDateAddDay(DateValueUtil.getInstance().getDateValue(DateValueUtil.TODAY, DateValueUtil.DBPATH), -30));
//			}
//		}
//
//		if (StringUtils.isEmpty(endDate)) {
//			if (StringUtils.isNotEmpty(endDate_cookie)) {
//				endDate = endDate_cookie;
//			} else {
//				endDate=DateValueUtil.getInstance().getDateValue(DateValueUtil.TODAY, DateValueUtil.DBPATH);
//			}
//		}
//		
//		//分頁查詢條件
//		int rowStart = 0;
//		int rowEnd = 0;
//		if(page == 0){
//			rowStart = 0;
//			rowEnd = 20;
//			page = 1;
//			pageSize = 20;
//		}else{
//			rowStart = (page - 1) * pageSize;
//			rowEnd = pageSize;
//		}
//		
//		ReportQueryConditionVO reportQueryConditionVO = new ReportQueryConditionVO();
//		reportQueryConditionVO.setAdPriceType(adPriceType);
//		reportQueryConditionVO.setAdPvclkDevice(adPvclkDevice);
//		reportQueryConditionVO.setAdSearchWay(adSearchWay);
//		reportQueryConditionVO.setAdSize(adSize);
//		reportQueryConditionVO.setSearchText(searchText);
//		reportQueryConditionVO.setStartDate(startDate);
//		reportQueryConditionVO.setEndDate(endDate);
//		reportQueryConditionVO.setPage(page);
//		reportQueryConditionVO.setRowStart(rowStart);
//		reportQueryConditionVO.setRowEnd(rowEnd);
//		reportQueryConditionVO.setTotalPage(totalPage);
//		reportQueryConditionVO.setCustomerInfoId(super.getCustomer_info_id());
//		
//		//查詢明細
//		this.adVideoPerformanceReportVOList = adVideoPerformanceReportService.loadReportDateList(reportQueryConditionVO);
//		
//		//查詢總數
//		this.adVideoPerformanceReportVOSum = adVideoPerformanceReportService.loadReportDateCount(reportQueryConditionVO);
//		double costSum = 0;
//		BigDecimal costSumBigDecimal = new BigDecimal(0);
//		int adVideoProcess25Sum = 0;
//		int adVideoProcess50Sum = 0;
//		int adVideoProcess75Sum = 0;
//		int adVideoProcess100Sum = 0;
//		double adPvSum = 0;
//		double adViewSum = 0;
//		int adClkSum = 0;
//		int adVideoUniqSum = 0;
//		int adVideoMusicSum = 0;
//		int adVideoReplaySum = 0;
//		for (AdVideoPerformanceReportVO adVideoPerformanceReportVO : adVideoPerformanceReportVOSum) {
//			adPvSum = adPvSum + Integer.parseInt(adVideoPerformanceReportVO.getAdPvSum());
//			adViewSum = adViewSum +  Integer.parseInt(adVideoPerformanceReportVO.getAdViewSum());
//			costSumBigDecimal = new BigDecimal(adVideoPerformanceReportVO.getCostSum()).add(costSumBigDecimal);
//			adVideoProcess25Sum = adVideoProcess25Sum + Integer.parseInt(adVideoPerformanceReportVO.getAdVideoProcess25Sum());
//			adVideoProcess50Sum = adVideoProcess50Sum + Integer.parseInt(adVideoPerformanceReportVO.getAdVideoProcess50Sum());
//			adVideoProcess75Sum = adVideoProcess75Sum + Integer.parseInt(adVideoPerformanceReportVO.getAdVideoProcess75Sum());
//			adVideoProcess100Sum = adVideoProcess100Sum + Integer.parseInt(adVideoPerformanceReportVO.getAdVideoProcess100Sum());
//			adVideoUniqSum = adVideoUniqSum + Integer.parseInt(adVideoPerformanceReportVO.getAdVideoUniqSum());
//			adClkSum = adClkSum + Integer.parseInt(adVideoPerformanceReportVO.getAdClkSum());
//			adVideoReplaySum = adVideoReplaySum + Integer.parseInt(adVideoPerformanceReportVO.getAdVideoReplaySum());
//			adVideoMusicSum = adVideoMusicSum + Integer.parseInt(adVideoPerformanceReportVO.getAdVideoMusicSum());
//		}
//		costSum = (costSumBigDecimal.setScale(3, BigDecimal.ROUND_FLOOR)).doubleValue();
//		double adViewRatings = 0;
//		double singleAdViewCost =0;
//		double thousandsCost = 0;
//		double adVideoProcess100Ratings = 0;
//		//收視率
//		adViewRatings = (adViewSum / adPvSum) * 100;
//		//單次收視費用
//		singleAdViewCost = costSum / adViewSum;
//		//千次曝光費用
//		thousandsCost = (costSum / adPvSum) * 1000;
//		//影片完整播放率
//		adVideoProcess100Ratings = ((double)adVideoProcess100Sum) / adPvSum * 100;
//		
//		
//		this.adVideoPerformanceReportVOCount.setAdPvSum(String.valueOf(adPvSum));
//		this.adVideoPerformanceReportVOCount.setAdViewSum(String.valueOf(adViewSum));
//		this.adVideoPerformanceReportVOCount.setCostSum(String.valueOf(costSum));
//		this.adVideoPerformanceReportVOCount.setAdVideoProcess25Sum(String.valueOf(adVideoProcess25Sum));
//		this.adVideoPerformanceReportVOCount.setAdVideoProcess50Sum(String.valueOf(adVideoProcess50Sum));
//		this.adVideoPerformanceReportVOCount.setAdVideoProcess75Sum(String.valueOf(adVideoProcess75Sum));
//		this.adVideoPerformanceReportVOCount.setAdVideoProcess100Sum(String.valueOf(adVideoProcess100Sum));
//		this.adVideoPerformanceReportVOCount.setAdClkSum(String.valueOf(adClkSum));
//		this.adVideoPerformanceReportVOCount.setAdVideoMusicSum(String.valueOf(adVideoMusicSum));
//		this.adVideoPerformanceReportVOCount.setAdVideoUniqSum(String.valueOf(adVideoUniqSum));
//		this.adVideoPerformanceReportVOCount.setAdVideoReplaySum(String.valueOf(adVideoReplaySum));
//		this.adVideoPerformanceReportVOCount.setAdViewRatings(String.valueOf(adViewRatings));
//		this.adVideoPerformanceReportVOCount.setSingleAdViewCost(String.valueOf(singleAdViewCost));
//		this.adVideoPerformanceReportVOCount.setThousandsCost(String.valueOf(thousandsCost));
//		this.adVideoPerformanceReportVOCount.setAdVideoProcess100Ratings(String.valueOf(adVideoProcess100Ratings));
//		this.adVideoPerformanceReportVOCount.setTotalSize(adVideoPerformanceReportVOSum.size());
//		
//		//總頁數
//		totalPage = (int)Math.ceil((double)adVideoPerformanceReportVOCount.getTotalSize() / (double)pageSize);
//		
//		log.info(">>>>>> customerInfoId = " + customerInfoId);
//		log.info(">>>>>> startDate = " + startDate);
//		log.info(">>>>>> endDate = " + endDate);
//
//		if (StringUtils.isEmpty(adType)) {
//			adType = EnumReport.ADTYPE_ACTIVITY.getTextValue();
//		}
//
//		if (StringUtils.isEmpty(adSearchWay)) {
//			adSearchWay = EnumReport.ADSEARCH_INCLUDE.getTextValue();
//		}
//
//		if(downloadFlag.trim().equals("yes")){
//			makeDownloadReportData();
//		}
//		
//		return SUCCESS;
//	}
//
//
//	
//	
//	private void makeDownloadReportData() throws Exception {
//		SimpleDateFormat dformat = new SimpleDateFormat("yyyyMMddhhmmss");
//		String filename="影音廣告成效報表_" + dformat.format(new Date()) + FILE_TYPE;
//
//		StringBuffer content = new StringBuffer();
//		String adPriceTypeName = StringUtils.isBlank(adPriceType) ? "全部計價方式" : adPriceTypeMap.get(adPriceType);
//		String searchDeviceTitle = "全部裝置";
//		String searchDevice = "全部";
//		if(adPvclkDevice.equals("PC")){
//			searchDeviceTitle = "電腦";
//			searchDevice = "電腦";
//		}else if(adPvclkDevice.equals("mobile")){
//			searchDeviceTitle = "行動裝置";
//			searchDevice = "行動裝置";
//		}
//		
//		String searchAdSize = "全部尺寸";
//		if(StringUtils.isNotBlank(adSize)){
//			String sizeArray[] = adSize.split("_");
//			searchAdSize = sizeArray[0] + " x " + sizeArray[1];
//		}
//		
//		//報表名稱
//		downloadFileName = new String(filename.getBytes("UTF-8"), "ISO8859-1");
//		content.append("帳戶," + super.getCustomer_info_title());
//		content.append("\n\n");
//		content.append("報表名稱,影音廣告成效");
//		content.append("\n\n");
//		content.append("日期範圍,"+startDate+"到"+endDate);
//		content.append("\n");
//		content.append("計價方式,"+adPriceTypeName);
//		content.append("\n");
//		content.append("裝置,"+searchDeviceTitle);
//		content.append("\n");
//		content.append("廣告尺寸,"+searchAdSize);
//		content.append("\n");
//		content.append("搜尋條件,字詞包含,"+searchText);
//		content.append("\n");
//		content.append("搜尋廣告明細名稱");
//		content.append("\n\n");
//		content.append(""
//				+ "影片名稱,"
//				+ "影片長度,"
//				+ "顯示連結,"
//				+ "實際連結,"
//				+ "計價方式,"
//				+ "裝置,"
//				+ "廣告尺寸,"
//				+ "曝光數,"
//				+ "收視次數,"
//				+ "收視率,"
//				+ "千次曝光費用,"
//				+ "單次收視費用,"
//				+ "費用,"
//				+ "點選次數,"
//				+ "收視人數(不重複),"
//				+ "聲音開啟次數,"
//				+ "影片重播次數,"
//				+ "影片播放進度(25%),"
//				+ "影片播放進度(50%),"
//				+ "影片播放進度(75%),"
//				+ "影片播放進度(100%),"
//				+ "影片完整播放率");
//		
//		//列表值
//		float sumPV = 0;
//		float sumView = 0;
//		float sumCost = 0;
//		float sumVideoProvess25 = 0;
//		float sumVideoProvess50 = 0;
//		float sumVideoProvess75 = 0;
//		float sumVideoProvess100 = 0;
//		float sumClick = 0;
//		float sumVideoUniq = 0;
//		float sumMusic = 0;
//		float sumReplay = 0;
//		//總計total值
//		float sumViewRatings = 0;
//		float sumThousandsCost = 0;
//		float sumSingleAdViewCost = 0;
//		float sumVideoProcess100Ratings = 0;
//		
//		//列表
//		for (AdVideoPerformanceReportVO adVideoPerformanceReportVO : adVideoPerformanceReportVOSum) {
//			sumPV = sumPV + Float.valueOf(adVideoPerformanceReportVO.getAdPvSum());
//			sumView = sumView + Float.valueOf(adVideoPerformanceReportVO.getAdViewSum());
//			sumCost = sumCost + Float.valueOf(adVideoPerformanceReportVO.getCostSum());
//			sumVideoProvess25 = sumVideoProvess25 + Float.valueOf(adVideoPerformanceReportVO.getAdVideoProcess25Sum());
//			sumVideoProvess50 = sumVideoProvess50 + Float.valueOf(adVideoPerformanceReportVO.getAdVideoProcess50Sum());
//			sumVideoProvess75 = sumVideoProvess75 + Float.valueOf(adVideoPerformanceReportVO.getAdVideoProcess75Sum());
//			sumVideoProvess100 = sumVideoProvess100 + Float.valueOf(adVideoPerformanceReportVO.getAdVideoProcess100Sum());
//			sumClick = sumClick + Float.valueOf(adVideoPerformanceReportVO.getAdClkSum());
//			sumVideoUniq = sumVideoUniq + Float.valueOf(adVideoPerformanceReportVO.getAdVideoUniqSum());
//			sumMusic = sumMusic + Float.valueOf(adVideoPerformanceReportVO.getAdVideoMusicSum());
//			sumReplay = sumReplay + Float.valueOf(adVideoPerformanceReportVO.getAdVideoReplaySum());
//			sumViewRatings = (sumView / sumPV * 100);
//			sumThousandsCost = (sumCost / sumPV * 1000);
//			sumSingleAdViewCost = (sumCost / sumView);
//			sumVideoProcess100Ratings = (sumVideoProvess100 / sumPV * 100);
//			
//			content.append("\n");
//			content.append(adVideoPerformanceReportVO.getTitle().trim()  +",");
//			content.append("00:"+adVideoPerformanceReportVO.getAdVideoSec()+",");
//			content.append(adVideoPerformanceReportVO.getVideoUrl()+",");
//			content.append(adVideoPerformanceReportVO.getAdLinkUrl()+",");
//			for (EnumAdPriceType enumAdPriceType : EnumAdPriceType.values()) {
//				if(enumAdPriceType.getDbTypeName().equals(adVideoPerformanceReportVO.getAdPriceType())){
//					content.append(enumAdPriceType.getTypeName() + ",");
//					break;
//				}
//			}
//			content.append(searchDevice+ ",");
//			content.append(adVideoPerformanceReportVO.getTemplateProductWidth() + " x "+ adVideoPerformanceReportVO.getTemplateProductHeight()+",");
//			content.append(adVideoPerformanceReportVO.getAdPvSum()+",");
//			content.append(adVideoPerformanceReportVO.getAdViewSum()+",");
//			content.append(adVideoPerformanceReportVO.getAdViewRatings()+"%,");
//			content.append("$"+adVideoPerformanceReportVO.getThousandsCost()+",");
//			content.append("$"+adVideoPerformanceReportVO.getSingleAdViewCost()+",");
////			content.append(StringEscapeUtils.escapeCsv("=\"$"+adVideoPerformanceReportVO.getCostSum())+"\",");
//			content.append("=\"$"+adVideoPerformanceReportVO.getCostSum()+"\",");
//			content.append(adVideoPerformanceReportVO.getAdClkSum()+",");
//			content.append(adVideoPerformanceReportVO.getAdVideoUniqSum()+",");
//			content.append(adVideoPerformanceReportVO.getAdVideoMusicSum()+",");
//			content.append(adVideoPerformanceReportVO.getAdVideoReplaySum()+",");
//			content.append(adVideoPerformanceReportVO.getAdVideoProcess25Sum()+",");
//			content.append(adVideoPerformanceReportVO.getAdVideoProcess50Sum()+",");
//			content.append(adVideoPerformanceReportVO.getAdVideoProcess75Sum()+",");
//			content.append(adVideoPerformanceReportVO.getAdVideoProcess100Sum()+",");
//			content.append(adVideoPerformanceReportVO.getAdVideoProcess100Ratings()+"%");
//		}
//		sumCost = new BigDecimal(String.valueOf(sumCost)).setScale(3, BigDecimal.ROUND_FLOOR).floatValue();
//		
//		//總計
//		content.append("\n\n");
//		content.append(""
//				+ "總計:共"+adVideoPerformanceReportVOSum.size()+"筆"+","
//				+ ","
//				+ ","
//				+ ","
//				+ ","
//				+ searchDeviceTitle + ","
//				+ ","
//				+ sumPV+","
//				+ sumView+","
//				+ sumViewRatings+"%,"
//				+ "$"+sumThousandsCost+","
//				+ "$"+sumSingleAdViewCost+","
////				+ StringEscapeUtils.escapeCsv("=\"$"+sumCost)+"\","
//				+ "=\"$"+sumCost+"\","
//				+ sumClick+","
//				+ sumVideoUniq+","
//				+ sumMusic+","
//				+ sumReplay+","
//				+ sumVideoProvess25+","
//				+ sumVideoProvess50+","
//				+ sumVideoProvess75+","
//				+ sumVideoProvess100+","
//				+ sumVideoProcess100Ratings+"%");
//		downloadFileStream = new ByteArrayInputStream(content.toString().getBytes("big5"));
//	}
//	
//	
//	
//	
//	
//	
//	public LinkedList<String> getTableHeadList() {
//		return tableHeadList;
//	}
//
//
//	public String getStartDate() {
//		return startDate;
//	}
//
//	public void setStartDate(String startDate) {
//		this.startDate = startDate;
//	}
//
//	public String getEndDate() {
//		return endDate;
//	}
//
//	public void setEndDate(String endDate) {
//		this.endDate = endDate;
//	}
//
//	public int getPage() {
//		return page;
//	}
//
//	public void setPage(int page) {
//		this.page = page;
//	}
//
//	public int getPageSize() {
//		return pageSize;
//	}
//
//	public void setPageSize(int pageSize) {
//		this.pageSize = pageSize;
//	}
//
//	public int getTotalPage() {
//		return totalPage;
//	}
//
//	public String getOptionSelect() {
//		return optionSelect;
//	}
//
//	public void setOptionSelect(String optionSelect) {
//		this.optionSelect = optionSelect;
//	}
//
//	public String getOptionNotSelect() {
//		return optionNotSelect;
//	}
//
//	public void setOptionNotSelect(String optionNotSelect) {
//		this.optionNotSelect = optionNotSelect;
//	}
//
//	public LinkedList<String> getTableHeadShowList() {
//		return tableHeadShowList;
//	}
//
//	public LinkedList<String> getTableHeadNotShowList() {
//		return tableHeadNotShowList;
//	}
//
//	public String getAdPvclkDevice() {
//		return adPvclkDevice;
//	}
//
//	public void setAdPvclkDevice(String adPvclkDevice) {
//		this.adPvclkDevice = adPvclkDevice;
//	}
//
//	public String getAdType() {
//		return adType;
//	}
//
//	public void setAdType(String adType) {
//		this.adType = adType;
//	}
//
//	public String getSearchText() {
//		return searchText;
//	}
//
//	public void setSearchText(String searchText) {
//		this.searchText = searchText;
//	}
//
//
//	public String getAdPriceType() {
//		return adPriceType;
//	}
//
//	public void setAdPriceType(String adPriceType) {
//		this.adPriceType = adPriceType;
//	}
//
//	public String getAdSearchWay() {
//		return adSearchWay;
//	}
//
//	public void setAdSearchWay(String adSearchWay) {
//		this.adSearchWay = adSearchWay;
//	}
//
//
//	public String getSearchId() {
//		return searchId;
//	}
//
//	public void setSearchId(String searchId) {
//		this.searchId = searchId;
//	}
//
//	public String getStepStr() {
//		return stepStr;
//	}
//
//
//
//	public InputStream getDownloadFileStream() {
//		return downloadFileStream;
//	}
//
//	public String getDownloadFileName() {
//		return downloadFileName;
//	}
//
//	public String getDownloadFlag() {
//		return downloadFlag;
//	}
//
//	public void setDownloadFlag(String downloadFlag) {
//		log.info("value set="+downloadFlag);
//		this.downloadFlag = downloadFlag;
//	}
//
//	public void setOpenFlashUtil(SpringOpenFlashUtil openFlashUtil) {
//		this.openFlashUtil = openFlashUtil;
//	}
//
//	public String getFlashData() {
//		return flashData;
//	}
//
//
//	public LinkedHashMap<String, String> getDateSelectMap() {
//		return dateSelectMap;
//	}
//
//
//	public LinkedList<String> getTableDataTotalSumList() {
//		return tableDataTotalSumList;
//	}
//
//	public void setTableDataTotalSumList(LinkedList<String> tableDataTotalSumList) {
//		this.tableDataTotalSumList = tableDataTotalSumList;
//	}
//
//	public String getReportTitle() {
//		return reportTitle;
//	}
//
//
//	public String[] getAlign_data() {
//		return align_data;
//	}
//
//	public String[] getAlign_sum() {
//		return align_sum;
//	}
//
//	public String getShowSearchBarFlag() {
//		return showSearchBarFlag;
//	}
//
//	public void setShowSearchBarFlag(String showSearchBarFlag) {
//		this.showSearchBarFlag = showSearchBarFlag;
//	}
//
//	public String getCharPic() {
//		return charPic;
//	}
//
//	public void setCharPic(String charPic) {
//		this.charPic = charPic;
//	}
//
//	public String getCharType() {
//		return charType;
//	}
//
//	public void setCharType(String charType) {
//		this.charType = charType;
//	}
//
//	public List<AdKeywordReportVO> getAdKeywordReportVOList() {
//		return AdKeywordReportVOList;
//	}
//
//	public AdKeywordReportVO getAdKeywordReportDataTotal() {
//		return AdKeywordReportDataTotal;
//	}
//
//	public String getAdOperatingRule() {
//		return adOperatingRule;
//	}
//
//	public void setAdOperatingRule(String adOperatingRule) {
//		this.adOperatingRule = adOperatingRule;
//	}
//
//	public List<AdVideoPerformanceReportVO> getAdVideoPerformanceReportVOList() {
//		return adVideoPerformanceReportVOList;
//	}
//
//	public void setAdVideoPerformanceReportVOList(List<AdVideoPerformanceReportVO> adVideoPerformanceReportVOList) {
//		this.adVideoPerformanceReportVOList = adVideoPerformanceReportVOList;
//	}
//
//	public IAdVideoPerformanceReportService getAdVideoPerformanceReportService() {
//		return adVideoPerformanceReportService;
//	}
//
//	public void setAdVideoPerformanceReportService(IAdVideoPerformanceReportService adVideoPerformanceReportService) {
//		this.adVideoPerformanceReportService = adVideoPerformanceReportService;
//	}
//
//	public Map<String, String> getAdPriceTypeMap() {
//		return adPriceTypeMap;
//	}
//
//	public void setAdPriceTypeMap(Map<String, String> adPriceTypeMap) {
//		this.adPriceTypeMap = adPriceTypeMap;
//	}
//
//	public Map<String, String> getAdSizeMap() {
//		return adSizeMap;
//	}
//
//	public void setAdSizeMap(Map<String, String> adSizeMap) {
//		this.adSizeMap = adSizeMap;
//	}
//
//	public String getAdSize() {
//		return adSize;
//	}
//
//	public void setAdSize(String adSize) {
//		this.adSize = adSize;
//	}
//
//	public List<AdVideoPerformanceReportVO> getAdVideoPerformanceReportVOSum() {
//		return adVideoPerformanceReportVOSum;
//	}
//
//	public void setAdVideoPerformanceReportVOSum(List<AdVideoPerformanceReportVO> adVideoPerformanceReportVOSum) {
//		this.adVideoPerformanceReportVOSum = adVideoPerformanceReportVOSum;
//	}
//
//	public AdVideoPerformanceReportVO getAdVideoPerformanceReportVOCount() {
//		return adVideoPerformanceReportVOCount;
//	}
//
//	public void setAdVideoPerformanceReportVOCount(AdVideoPerformanceReportVO adVideoPerformanceReportVOCount) {
//		this.adVideoPerformanceReportVOCount = adVideoPerformanceReportVOCount;
//	}

	
}
