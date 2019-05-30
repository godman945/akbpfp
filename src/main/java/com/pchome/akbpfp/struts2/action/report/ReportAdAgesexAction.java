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
import org.json.JSONArray;

import com.pchome.akbpfp.db.dao.report.AdAgesexReportVO;
import com.pchome.akbpfp.db.pojo.PfpCode;
import com.pchome.akbpfp.db.pojo.PfpCustomerInfo;
import com.pchome.akbpfp.db.service.codeManage.IPfpCodeService;
import com.pchome.akbpfp.db.service.customerInfo.IPfpCustomerInfoService;
import com.pchome.akbpfp.db.service.report.IAdAgesexReportService;
import com.pchome.enumerate.report.EnumReport;
import com.pchome.soft.depot.utils.DateValueUtil;
import com.pchome.soft.depot.utils.SpringOpenFlashUtil;
import com.pchome.utils.CommonUtils;

public class ReportAdAgesexAction extends BaseReportAction {

	private static final long serialVersionUID = 1L;
	
	private IAdAgesexReportService adAgesexReportService;
	private IPfpCodeService pfpCodeService;
	
	private LinkedHashMap<String, String> dateSelectMap; // 查詢日期的 rang map,查詢日期頁面顯示
	private boolean hasPfpCodeflag = false; // 是否有使用轉換追蹤的PFP帳號
	
	private String viewType = "age"; // 預設年齡 (年齡、性別)
	private String startDate = ""; // 查詢開始日期
	private String endDate = ""; // 查詢結束日期
	private String searchText = ""; // 搜尋文字
	private String whereMap; // 篩選
	private String sortBy = ""; // 排序
	private int page = 1; // 第幾頁
	private int pageSize = 10; // 每頁筆數
	private int totalPage = 0; //總頁數
	
	private List<AdAgesexReportVO> resultData = new ArrayList<>(); // 查詢結果
	private List<AdAgesexReportVO> resultSumData = new ArrayList<>(); // 查詢結果加總
	
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
	@Override
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
		
		// 檢查是否有使用轉換追蹤的PFP帳號
		PfpCode pfpCode = pfpCodeService.getPfpCode(super.getCustomer_info_id());
		if (pfpCode != null) {
			hasPfpCodeflag = true;
		}
		
		// 查詢明細與總計資料
		AdAgesexReportVO vo = new AdAgesexReportVO();
		vo.setCustomerInfoId(super.getCustomer_info_id());
		vo.setViewType(viewType);
		vo.setStartDate(startDate);
		vo.setEndDate(endDate);
		vo.setSearchText(searchText);
		vo.setWhereMap(whereMap);
		vo.setSortBy(sortBy);
		vo.setPage(page);
		vo.setPageSize(pageSize);
		vo.setDownloadOrIsNotCuttingPagination(Boolean.parseBoolean(isDownload));
		resultData = adAgesexReportService.queryReportAdAgesexData(vo);
		resultSumData = adAgesexReportService.queryReportAdAgesexSumData(vo);
		
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
		String filename = "廣告族群成效報表_" + dformat.format(new Date()) + FILE_TYPE;
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
		
		content = processDownloadReportTitle(content);
		content.append("\n");
		
		if (!resultData.isEmpty()) {
			// 明細資料
			for (int i = 0; i < resultData.size(); i++) {
				content.append("\"" + resultData.get(i).getAdStatusName() + "\",");
				content.append("\"" + resultData.get(i).getAdActionName() + "\",");
				content.append("\"" + resultData.get(i).getAdGroupName() + "\",");
				
				if ("age".equalsIgnoreCase(viewType)) { // 畫面目前選擇為年齡
					content.append("\"" + resultData.get(i).getAge() + "\",");
				} else {
					content.append("\"" + resultData.get(i).getSex() + "\",");
				}
				
				if (showHideColumnMap.get(EnumReport.ADTYPE.getTextValue())) {
					content.append("\"" + resultData.get(i).getAdType() + "\",");
				}
				if (showHideColumnMap.get(EnumReport.AD_OPERATING_RULE.getTextValue())) {
					content.append("\"" + resultData.get(i).getAdOperatingRule() + "\",");
				}
				if (showHideColumnMap.get(EnumReport.AD_CLK_PRICE_TYPE.getTextValue())) {
					content.append("\"" + resultData.get(i).getAdClkPriceType() + "\",");
				}
				
				content.append("\"" + resultData.get(i).getAdDevice() + "\",");
				content.append("\"" + intFormat.format(resultData.get(i).getAdPvSum()) + "\",");
				content.append("\"" + doubleFormat.format(resultData.get(i).getAdClkSum()) + "\",");
				content.append("\"" + doubleFormat.format(resultData.get(i).getCtr()) + "%\",");
				content.append("\"NT$ " + doubleFormat.format(resultData.get(i).getAvgCost()) + "\",");
				content.append("\"NT$ " + doubleFormat.format(resultData.get(i).getKiloCost()) + "\",");
				content.append("\"NT$ " + doubleFormat2.format(resultData.get(i).getAdPriceSum()) + "\",");
				
				if (showHideColumnMap.get(EnumReport.CONVERT_COUNT.getTextValue())) {
					content.append("\"" + doubleFormat.format(resultData.get(i).getConvertCount()) + "\",");
				}
				if (showHideColumnMap.get(EnumReport.CONVERT_CTR.getTextValue())) {
					content.append("\"" + doubleFormat.format(resultData.get(i).getConvertCTR()) + "%\",");
				}
				if (showHideColumnMap.get(EnumReport.CONVERT_PRICE_COUNT.getTextValue())) {
					content.append("\"NT$ " + doubleFormat.format(resultData.get(i).getConvertPriceCount()) + "\",");
				}
				if (showHideColumnMap.get(EnumReport.CONVERT_COST.getTextValue())) {
					content.append("\"NT$ " + doubleFormat.format(resultData.get(i).getConvertCost()) + "\",");
				}
				if (showHideColumnMap.get(EnumReport.CONVERT_INVESTMENT_COST.getTextValue())) {
					content.append("\"" + doubleFormat.format(resultData.get(i).getConvertInvestmentCost()) + "%\",");
				}
				content.append("\n");
			}
			
			content.append("\n");
			
			// 總計資料
			for (int i = 0; i < resultSumData.size(); i++) {
				content.append("\"\",");
				content.append("\"總計：" + intFormat.format(resultSumData.get(i).getRowCount()) + "\",");
				content.append("\"\",");
				content.append("\"\",");
				
				if (showHideColumnMap.get(EnumReport.ADTYPE.getTextValue())) {
					content.append("\"\",");
				}
				if (showHideColumnMap.get(EnumReport.AD_OPERATING_RULE.getTextValue())) {
					content.append("\"\",");
				}
				if (showHideColumnMap.get(EnumReport.AD_CLK_PRICE_TYPE.getTextValue())) {
					content.append("\"\",");
				}
				content.append("\"\",");
				content.append("\"" + doubleFormat.format(resultSumData.get(i).getAdPvSum()) + "\",");
				content.append("\"" + doubleFormat.format(resultSumData.get(i).getAdClkSum()) + "\",");
				content.append("\"" + doubleFormat.format(resultSumData.get(i).getCtr()) + "%\",");
				content.append("\"NT$ " + doubleFormat.format(resultSumData.get(i).getAvgCost()) + "\",");
				content.append("\"NT$ " + doubleFormat.format(resultSumData.get(i).getKiloCost()) + "\",");
				content.append("\"NT$ " + doubleFormat2.format(resultSumData.get(i).getAdPriceSum()) + "\",");
				
				if (showHideColumnMap.get(EnumReport.CONVERT_COUNT.getTextValue())) {
					content.append("\"" + doubleFormat.format(resultSumData.get(i).getConvertCount()) + "\",");
				}
				if (showHideColumnMap.get(EnumReport.CONVERT_CTR.getTextValue())) {
					content.append("\"" + doubleFormat.format(resultSumData.get(i).getConvertCTR()) + "%\",");
				}
				if (showHideColumnMap.get(EnumReport.CONVERT_PRICE_COUNT.getTextValue())) {
					content.append("\"NT$ " + doubleFormat.format(resultSumData.get(i).getConvertPriceCount()) + "\",");
				}
				if (showHideColumnMap.get(EnumReport.CONVERT_COST.getTextValue())) {
					content.append("\"NT$ " + doubleFormat.format(resultSumData.get(i).getConvertCost()) + "\",");
				}
				if (showHideColumnMap.get(EnumReport.CONVERT_INVESTMENT_COST.getTextValue())) {
					content.append("\"" + doubleFormat.format(resultSumData.get(i).getConvertInvestmentCost()) + "%\",");
				}
			}
		}
		
		if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") >= 1) {
			downloadFileName = new String(filename.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
		} else {
			downloadFileName = URLEncoder.encode(filename, "UTF-8");
		}

		downloadFileStream = new ByteArrayInputStream(content.toString().getBytes("big5"));
	}
	
	/**
	 * 處理下載報表標題
	 * @param content
	 * @return
	 */
	private StringBuffer processDownloadReportTitle(StringBuffer content) {
		content.append("狀態,廣告活動,廣告分類,");
		if ("age".equalsIgnoreCase(viewType)) { // 畫面目前選擇為年齡
			content.append("年齡,");
		} else {
			content.append("性別,");
		}

		if (showHideColumnMap.get(EnumReport.ADTYPE.getTextValue())) {
			content.append("播放類型,");
		}

		if (showHideColumnMap.get(EnumReport.AD_OPERATING_RULE.getTextValue())) {
			content.append("廣告樣式,");
		}

		if (showHideColumnMap.get(EnumReport.AD_CLK_PRICE_TYPE.getTextValue())) {
			content.append("計價方式,");
		}

		content.append("裝置,曝光數,互動數,互動率,單次互動費用,千次曝光費用,費用,");

		if (showHideColumnMap.get(EnumReport.CONVERT_COUNT.getTextValue())) {
			content.append("轉換次數,");
		}

		if (showHideColumnMap.get(EnumReport.CONVERT_CTR.getTextValue())) {
			content.append("轉換率,");
		}

		if (showHideColumnMap.get(EnumReport.CONVERT_PRICE_COUNT.getTextValue())) {
			content.append("總轉換價值,");
		}

		if (showHideColumnMap.get(EnumReport.CONVERT_COST.getTextValue())) {
			content.append("平均轉換成本,");
		}

		if (showHideColumnMap.get(EnumReport.CONVERT_INVESTMENT_COST.getTextValue())) {
			content.append("廣告投資報酬率,");
		}
		return content;
	}
	
	/**
	 * 圖表
	 * @return
	 * @throws Exception
	 */
	public String flashDataDownLoad() {
		
		AdAgesexReportVO chartVo = new AdAgesexReportVO();
		chartVo.setCustomerInfoId(super.getCustomer_info_id());
		chartVo.setViewType(viewType);
		chartVo.setStartDate(startDate);
		chartVo.setEndDate(endDate);
		chartVo.setSearchText(searchText);
		chartVo.setWhereMap(whereMap);
		chartVo.setDownloadOrIsNotCuttingPagination(true);
		List<AdAgesexReportVO> resultChartData = adAgesexReportService.queryReportAdAgesexChartData(chartVo);

		List<Double> dataList = new ArrayList<>();
		
		// 性別
		String sexCode = "";
		double sexM = 0; // 男性
		double sexF = 0; // 女性
		double sexN = 0; // 未知
		
		// 年齡
		String ageCode = "";
		double ageA = 0; // 18歲(不含)以下
		double ageB = 0; // 18歲~24歲
		double ageC = 0; // 25歲~34歲
		double ageD = 0; // 35歲~44歲
		double ageE = 0; // 45歲~54歲
		double ageF = 0; // 55歲~64歲
		double ageG = 0; // 65歲~74歲
		double ageH = 0; // 75歲以上
		double ageI = 0; // 未知
		
		double total = 0; // 總合

		for (int i = 0; i < resultChartData.size(); i++) {
			AdAgesexReportVO vo = resultChartData.get(i);
			sexCode = vo.getSexCode();
			ageCode = vo.getAgeCode();
			if (charType.equals(EnumReport.REPORT_CHART_TYPE_PV.getTextValue())) {
				double adPvSum = vo.getAdPvSum().doubleValue();
				if (StringUtils.equals(viewType, "sex")) {
					switch (sexCode) {
					case "M":
						sexM = adPvSum;
						break;
					case "F":
						sexF = adPvSum;
						break;
					default:
						sexN = adPvSum;
						break;
					}
				} else {
					switch (ageCode) {
					case "A":
						ageA = adPvSum;
						break;
					case "B":
						ageB = adPvSum;
						break;
					case "C":
						ageC = adPvSum;
						break;
					case "D":
						ageD = adPvSum;
						break;
					case "E":
						ageE = adPvSum;
						break;
					case "F":
						ageF = adPvSum;
						break;
					case "G":
						ageG = adPvSum;
						break;
					case "H":
						ageH = adPvSum;
						break;
					default:
						ageI = adPvSum;
						break;
					}
				}
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_CLICK.getTextValue())) {
				double adClkSum = vo.getAdClkSum().doubleValue();
				if (StringUtils.equals(viewType, "sex")) {
					switch (sexCode) {
					case "M":
						sexM = adClkSum;
						break;
					case "F":
						sexF = adClkSum;
						break;
					default:
						sexN = adClkSum;
						break;
					}
				} else {
					switch (ageCode) {
					case "A":
						ageA = adClkSum;
						break;
					case "B":
						ageB = adClkSum;
						break;
					case "C":
						ageC = adClkSum;
						break;
					case "D":
						ageD = adClkSum;
						break;
					case "E":
						ageE = adClkSum;
						break;
					case "F":
						ageF = adClkSum;
						break;
					case "G":
						ageG = adClkSum;
						break;
					case "H":
						ageH = adClkSum;
						break;
					default:
						ageI = adClkSum;
						break;
					}
				}
			} else if (charType.equals(EnumReport.REPORT_CHART_CONVERT.getTextValue())) {
				double convertCount = vo.getConvertCount().doubleValue();
				if (StringUtils.equals(viewType, "sex")) {
					switch (sexCode) {
					case "M":
						sexM = convertCount;
						break;
					case "F":
						sexF = convertCount;
						break;
					default:
						sexN = convertCount;
						break;
					}
				} else {
					switch (ageCode) {
					case "A":
						ageA = convertCount;
						break;
					case "B":
						ageB = convertCount;
						break;
					case "C":
						ageC = convertCount;
						break;
					case "D":
						ageD = convertCount;
						break;
					case "E":
						ageE = convertCount;
						break;
					case "F":
						ageF = convertCount;
						break;
					case "G":
						ageG = convertCount;
						break;
					case "H":
						ageH = convertCount;
						break;
					default:
						ageI = convertCount;
						break;
					}
				}
			}
		}

		if (StringUtils.equals(viewType, "sex")) {
			total = sexM + sexF;
			if (total != 0) {
				sexM = Double.parseDouble(String.format("%.2f", (sexM / total) * 100));
				sexF = 100 - sexM;

				dataList.add(sexM);
				dataList.add(sexF);
			} else {
				dataList.add(new Double(0));
				dataList.add(new Double(0));
			}
		} else {
			total = ageA + ageB + ageC + ageD + ageE + ageF + ageG + ageH;
			if (total != 0) {
				dataList.add((ageA / total) * 100);
				dataList.add((ageB / total) * 100);
				dataList.add((ageC / total) * 100);
				dataList.add((ageD / total) * 100);
				dataList.add((ageE / total) * 100);
				dataList.add((ageF / total) * 100);
				dataList.add((ageG / total) * 100);
				dataList.add((ageH / total) * 100);
			} else {
				dataList.add(new Double(0));
				dataList.add(new Double(0));
				dataList.add(new Double(0));
				dataList.add(new Double(0));
				dataList.add(new Double(0));
				dataList.add(new Double(0));
				dataList.add(new Double(0));
				dataList.add(new Double(0));
			}
		}
		JSONArray array = new JSONArray(dataList);
		flashData = array.toString();
		
		return SUCCESS;
	}
	
	public LinkedHashMap<String, String> getDateSelectMap() {
		return dateSelectMap;
	}

	public String getViewType() {
		return viewType;
	}

	public void setViewType(String viewType) {
		this.viewType = viewType;
	}

	public void setAdAgesexReportService(IAdAgesexReportService adAgesexReportService) {
		this.adAgesexReportService = adAgesexReportService;
	}

	public void setPfpCodeService(IPfpCodeService pfpCodeService) {
		this.pfpCodeService = pfpCodeService;
	}

	public boolean isHasPfpCodeflag() {
		return hasPfpCodeflag;
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

	public List<AdAgesexReportVO> getResultData() {
		return resultData;
	}

	public List<AdAgesexReportVO> getResultSumData() {
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