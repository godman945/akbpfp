package com.pchome.akbpfp.struts2.action.report;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.pchome.akbpfp.db.dao.report.AdVideoPerformanceReportVO;
import com.pchome.akbpfp.db.pojo.PfpCustomerInfo;
import com.pchome.akbpfp.db.service.customerInfo.IPfpCustomerInfoService;
import com.pchome.akbpfp.db.service.report.IAdVideoPerformanceReportService;
import com.pchome.enumerate.report.EnumReport;
import com.pchome.soft.depot.utils.DateValueUtil;
import com.pchome.soft.depot.utils.SpringOpenFlashUtil;
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
	
}