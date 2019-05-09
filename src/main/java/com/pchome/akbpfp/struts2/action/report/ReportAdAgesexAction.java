package com.pchome.akbpfp.struts2.action.report;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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

import com.pchome.akbpfp.db.dao.report.AdAgesexReportVO;
import com.pchome.akbpfp.db.dao.report.AdTimeReportVO;
import com.pchome.akbpfp.db.pojo.PfpAdGroup;
import com.pchome.akbpfp.db.pojo.PfpCode;
import com.pchome.akbpfp.db.pojo.PfpCustomerInfo;
import com.pchome.akbpfp.db.service.ad.IPfpAdGroupService;
import com.pchome.akbpfp.db.service.codeManage.IPfpCodeService;
import com.pchome.akbpfp.db.service.customerInfo.IPfpCustomerInfoService;
import com.pchome.akbpfp.db.service.report.IAdAgesexReportService;
import com.pchome.akbpfp.db.service.report.IAdTimeReportService;
import com.pchome.enumerate.ad.EnumAdType;
import com.pchome.enumerate.report.EnumReport;
import com.pchome.enumerate.utils.EnumStatus;
import com.pchome.soft.depot.utils.SpringOpenFlashUtil;
import com.pchome.soft.util.DateValueUtil;
import com.pchome.utils.CommonUtils;

public class ReportAdAgesexAction extends BaseReportAction {

	private static final long serialVersionUID = 1L;

	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	NumberFormat intFormat = new DecimalFormat("###,###,###,###");
	NumberFormat doubleFormat = new DecimalFormat("###,###,###,###.##");
	
	private IAdAgesexReportService adAgesexReportService;
	private IPfpCodeService pfpCodeService;
	
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
				content.append("\"NT$ " + doubleFormat.format(resultData.get(i).getAdPriceSum()) + "\",");
				
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
				content.append("\"NT$ " + doubleFormat.format(resultSumData.get(i).getAdPriceSum()) + "\",");
				
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

		if (showHideColumnMap.get(EnumReport.REPORT_CHART_CONVERT.getTextValue())) {
			content.append("轉換次數,");
		}

		if (showHideColumnMap.get(EnumReport.REPORT_CHART_CONVERT_CTR.getTextValue())) {
			content.append("轉換率,");
		}

		if (showHideColumnMap.get(EnumReport.REPORT_CHART_CONVERT_PRICE.getTextValue())) {
			content.append("總轉換價值,");
		}

		if (showHideColumnMap.get(EnumReport.REPORT_CHART_CONVERT_COST.getTextValue())) {
			content.append("平均轉換成本,");
		}

		if (showHideColumnMap.get(EnumReport.REPORT_CHART_CONVERT_INVESTMENT.getTextValue())) {
			content.append("廣告投資報酬率,");
		}
		return content;
	}
	
	/**
	 * 圖表
	 * @return
	 * @throws Exception
	 */
	public String flashDataDownLoad() throws Exception {
		
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
		double sexN = 0; // 未知
		double sexM = 0; // 男性
		double sexF = 0; // 女性
		
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
				total += vo.getAdPvSum().doubleValue();
				if (StringUtils.equals(viewType, "sex")) {
					switch (sexCode) {
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
					switch (ageCode) {
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
				if (StringUtils.equals(viewType, "sex")) {
					switch (sexCode) {
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
					switch (ageCode) {
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
			} else if (charType.equals(EnumReport.REPORT_CHART_CONVERT.getTextValue())) {
				if (StringUtils.equals(viewType, "sex")) {
					switch (sexCode) {
					case "M":
						sexM = vo.getConvertCount().doubleValue();
						break;
					case "F":
						sexF = vo.getConvertCount().doubleValue();
						break;
					default:
						sexN = vo.getConvertCount().doubleValue();
						break;
					}
				} else {
					switch (ageCode) {
					case "A":
						ageA = vo.getConvertCount().doubleValue();
						break;
					case "B":
						ageB = vo.getConvertCount().doubleValue();
						break;
					case "C":
						ageC = vo.getConvertCount().doubleValue();
						break;
					case "D":
						ageD = vo.getConvertCount().doubleValue();
						break;
					case "E":
						ageE = vo.getConvertCount().doubleValue();
						break;
					case "F":
						ageF = vo.getConvertCount().doubleValue();
						break;
					case "G":
						ageG = vo.getConvertCount().doubleValue();
						break;
					case "H":
						ageH = vo.getConvertCount().doubleValue();
						break;
					default:
						ageI = vo.getConvertCount().doubleValue();
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
	
	
	
	
	
//	private LinkedList<String> tableHeadList =null; //頁面table head
//
//	// 欄位
//	private String[] align_data = {"center", "center", "center", "center", "center", "center", "center", "center", "right", "right", "right", "right", "right", "right", "right", "right", "right", "right", "right"};
//	private String[] align_sum = {"center", "center", "center", "center", "center", "center", "center", "center", "right", "right", "right", "right", "right", "right", "right", "right", "right", "right", "right"};
//
//	private LinkedList<LinkedList<String>> tableDataList =null; // 頁面 table data
//	private LinkedList<String> tableDataTotalList =null; //頁面全部加總table total foot
//
//	private IAdAgesexReportService adAgesexReportService = null;
//	private IPfpAdGroupService adGroupService = null;
//	private IPfpCustomerInfoService customerInfoService = null;
//
//	private SpringOpenFlashUtil openFlashUtil=null;
//
//	private Map<String,String> tableHeadNameMap;//自訂欄位
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
//	private int page=0;//第幾頁
//	private int pageSize=0;//每頁筆數
//	private int totalPage=0;//總頁數
//
//	private String adPvclkDevice=""; //裝置:pc、mobile
//	private String adType="";// 廣告類型,活動,群組,關鍵字
//	private String adSearchWay="";//文字搜尋方式,包含,開頭,全部
//	private String searchText="";//搜尋文字
//	private String adShowWay="0";//廣告顯示位址,一般,內容
//	private String searchId="";//廣告id ,某活動,某群組id
//	private String adOperatingRule;//廣告樣式
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
//	private String reportTitle;
//	
//	private String searchAgesex = "A";
//
//	private String timeName = "";
//	
//	private IPfpCodeService pfpCodeService;
//	
//	private boolean hasPfpCodeflag = false;
//	
//	public String flashDataDownLoad() throws Exception {
//
//		//查詢日期寫進 cookie
//		this.setChooseDate(startDate, endDate);
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
//		if(adOperatingRule.equals("Null")) {
//			adOperatingRule = "";
//		}
//		
//		String customerInfoId = super.getCustomer_info_id();
//		log.info(">>> customerInfoId = " + customerInfoId);
//
//		List<AdAgesexReportVO> resultData = adAgesexReportService.loadReportDate(EnumReport.REPORT_HQLTYPE_AGESEX_CHART.getTextValue(),searchAgesex,searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, adOperatingRule, startDate, endDate, -1, -1);
//
//		List<Double> dataList = new ArrayList<Double>();
//		
//		//性別
//		String sex = "";
//		double sexN = 0;		//未知
//		double sexM = 0;		//男性
//		double sexF = 0;		//女性
//		
//		//年齡
//		String ageCode = "";
//		double ageA = 0;		//18歲(不含)以下
//		double ageB = 0;		//18歲~24歲
//		double ageC = 0;		//25歲~34歲
//		double ageD = 0;		//35歲~44歲
//		double ageE = 0;		//45歲~54歲
//		double ageF = 0;		//55歲~64歲
//		double ageG = 0;		//65歲~74歲
//		double ageH = 0;		//75歲以上
//		double ageI = 0;		//未知
//		
//		double total = 0;		//總合
//
//		for (int i=0; i<resultData.size(); i++) {
//			AdAgesexReportVO vo = resultData.get(i);
//			sex = vo.getSex();
//			ageCode = vo.getAge();
//			if (charType.equals(EnumReport.REPORT_CHART_TYPE_PV.getTextValue())) {
//				total += vo.getAdPvSum().doubleValue();
//				if(StringUtils.equals(searchAgesex, "S")){
//					switch(sex) {
//						case "M":
//							sexM = vo.getAdPvSum().doubleValue();
//							break;
//						case "F":
//							sexF = vo.getAdPvSum().doubleValue();
//							break;
//						default:
//							sexN = vo.getAdPvSum().doubleValue();
//							break;
//					}
//				} else {
//					switch(ageCode) {
//					case "A":
//						ageA = vo.getAdPvSum().doubleValue();
//						break;
//					case "B":
//						ageB = vo.getAdPvSum().doubleValue();
//						break;
//					case "C":
//						ageC = vo.getAdPvSum().doubleValue();
//						break;
//					case "D":
//						ageD = vo.getAdPvSum().doubleValue();
//						break;
//					case "E":
//						ageE = vo.getAdPvSum().doubleValue();
//						break;
//					case "F":
//						ageF = vo.getAdPvSum().doubleValue();
//						break;
//					case "G":
//						ageG = vo.getAdPvSum().doubleValue();
//						break;
//					case "H":
//						ageH = vo.getAdPvSum().doubleValue();
//						break;
//					default:
//						ageI = vo.getAdPvSum().doubleValue();
//						break;
//					}
//				}
//			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_CLICK.getTextValue())) {
//				//total += vo.getAdClkSum().doubleValue();
//				if(StringUtils.equals(searchAgesex, "S")){
//					switch(sex) {
//						case "M":
//							sexM = vo.getAdClkSum().doubleValue();
//							break;
//						case "F":
//							sexF = vo.getAdClkSum().doubleValue();
//							break;
//						default:
//							sexN = vo.getAdClkSum().doubleValue();
//							break;
//					}
//				} else {
//					switch(ageCode) {
//					case "A":
//						ageA = vo.getAdClkSum().doubleValue();
//						break;
//					case "B":
//						ageB = vo.getAdClkSum().doubleValue();
//						break;
//					case "C":
//						ageC = vo.getAdClkSum().doubleValue();
//						break;
//					case "D":
//						ageD = vo.getAdClkSum().doubleValue();
//						break;
//					case "E":
//						ageE = vo.getAdClkSum().doubleValue();
//						break;
//					case "F":
//						ageF = vo.getAdClkSum().doubleValue();
//						break;
//					case "G":
//						ageG = vo.getAdClkSum().doubleValue();
//						break;
//					case "H":
//						ageH = vo.getAdClkSum().doubleValue();
//						break;
//					default:
//						ageI = vo.getAdClkSum().doubleValue();
//						break;
//					}
//				}
//			}
//		}
//
//		if(StringUtils.equals(searchAgesex, "S")){
//			total = sexM + sexF;
//			if(total != 0){
//				sexM = Double.parseDouble(String.format("%.2f",(sexM/total)*100));
//				//sexF = Double.parseDouble(String.format("%.2f",(sexF/total)*100));
//				//sexN = 100 - sexM - sexF;
//				sexF = 100 - sexM;
//				
//				dataList.add(sexM);
//				dataList.add(sexF);
//				//dataList.add(sexN);
//			} else {
//				dataList.add(new Double(0));
//				dataList.add(new Double(0));
//				//dataList.add(new Double(0));
//			}
//		} else {
//			total = ageA + ageB + ageC + ageD + ageE + ageF + ageG + ageH;
//			if(total != 0){
//				dataList.add((ageA/total)*100);
//				dataList.add((ageB/total)*100);
//				dataList.add((ageC/total)*100);
//				dataList.add((ageD/total)*100);
//				dataList.add((ageE/total)*100);
//				dataList.add((ageF/total)*100);
//				dataList.add((ageG/total)*100);
//				dataList.add((ageH/total)*100);
//				//dataList.add((ageI/total)*100);	
//			} else {
//				dataList.add(new Double(0));
//				dataList.add(new Double(0));
//				dataList.add(new Double(0));
//				dataList.add(new Double(0));
//				dataList.add(new Double(0));
//				dataList.add(new Double(0));
//				dataList.add(new Double(0));
//				dataList.add(new Double(0));
//				//dataList.add(new Double(0));
//			}
//		}
//		JSONArray array = new JSONArray(dataList);
//		flashData = array.toString();
//		return SUCCESS;
//	}
//
//	public ReportAdAgesexAction() {
//
//		reportTitle="廣告族群成效";
//		
//		downloadFlag="no";
//
//		tableHeadNameMap=new HashMap<String,String>();
//		tableHeadNameMap.put("曝光數", EnumReport.REPORT_CHART_TYPE_PV.getTextValue());
//		tableHeadNameMap.put("互動數", EnumReport.REPORT_CHART_TYPE_CLICK.getTextValue());
//		tableHeadNameMap.put("互動率", EnumReport.REPORT_CHART_TYPE_CTR.getTextValue());
//		// 20140318： 隱藏 "無效點選次數" 欄位
//		//tableHeadNameMap.put("無效點選次數", EnumReport.REPORT_CHART_TYPE_INVALID.getTextValue());
//		tableHeadNameMap.put("單次互動費用", EnumReport.REPORT_CHART_TYPE_AVGCOST.getTextValue());
//		tableHeadNameMap.put("千次曝光費用", EnumReport.REPORT_CHART_TYPE_KILOCOST.getTextValue());
//		tableHeadNameMap.put("費用", EnumReport.REPORT_CHART_TYPE_COST.getTextValue());
//		tableHeadNameMap.put("轉換次數", EnumReport.REPORT_CHART_CONVERT.getTextValue());
//		tableHeadNameMap.put("總轉換價值", EnumReport.REPORT_CHART_CONVERT_PRICE.getTextValue());
//		tableHeadNameMap.put("轉換率", EnumReport.REPORT_CHART_CONVERT_CTR.getTextValue());
//		tableHeadNameMap.put("平均轉換成本", EnumReport.REPORT_CHART_CONVERT_COST.getTextValue());
//		tableHeadNameMap.put("廣告投資報酬率", EnumReport.REPORT_CHART_CONVERT_INVESTMENT.getTextValue());
//		optionSelect="";
//		optionNotSelect="";
//
//		//optionSelect="曝光數,點選率(%),點選次數,無效點選次數,平均點選費用,費用";
//		// 20140318： 隱藏 "無效點選次數" 欄位
//		optionSelect="曝光數,互動數,互動率,單次互動費用,千次曝光費用,費用";
//
//		tableHeadShowList=new LinkedList<String>();
//
//		tableHeadNotShowList=new LinkedList<String>();
//
//	}
//
//	public String execute() throws Exception {
//
//		String customerInfoId = super.getCustomer_info_id();
//		log.info(">>> customerInfoId = " + customerInfoId);
//		PfpCode pfpCode = pfpCodeService.getPfpCode(super.getCustomer_info_id());
//		if(pfpCode != null){
//			hasPfpCodeflag = true;
//		}
//		if(hasPfpCodeflag){
//			optionSelect="曝光數,互動數,互動率,單次互動費用,千次曝光費用,費用,轉換次數,總轉換價值,轉換率,平均轉換成本,廣告投資報酬率";	
//		}else{
//			optionSelect="曝光數,互動數,互動率,單次互動費用,千次曝光費用,費用";
//		}
//		
//		tableHeadList = new LinkedList<String>();//table head
//		tableDataList = new LinkedList<LinkedList<String>>();//table data
//
//		//自訂欄位選擇
//		if (StringUtils.isNotEmpty(optionSelect)) {
//
//			String data[] = optionSelect.split(",");
//			for (String s:data) {
//				tableHeadShowList.addLast(s);
//			}
//		}
//
//		//自訂欄位移除
//		if (StringUtils.isNotEmpty(optionNotSelect)) {
//			String data[] = optionNotSelect.split(",");
//			for (String s:data) {
//				tableHeadNotShowList.addLast(s);
//			}
//		}
//
//		log.info(">>> startDate = " + startDate);
//		log.info(">>> endDate = " + endDate);
//
//		String startDate_cookie = this.getChoose_start_date();
//		String endDate_cookie = this.getChoose_end_date();
//
//		log.info(">>> startDate_cookie = " + startDate_cookie);
//		log.info(">>> endDate_cookie = " + endDate_cookie);
//
//		if (StringUtils.isEmpty(startDate)) {
//			if (StringUtils.isNotEmpty(startDate_cookie)) {
//				startDate = startDate_cookie;
//			} else {
//				startDate=DateValueUtil.getInstance().dateToString(DateValueUtil.getInstance().getDateForStartDateAddDay(DateValueUtil.getInstance().getDateValue(DateValueUtil.TODAY, DateValueUtil.DBPATH), -30));
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
//		if (StringUtils.isEmpty(adShowWay)) {
//			adShowWay = Integer.toString(EnumAdType.AD_ALL.getType());
//		}
//
//		log.info("startDate="+startDate);
//		log.info("endDate="+endDate);
//		log.info("adType="+adType);
//		log.info("adSearchWay="+adSearchWay);
//		log.info("adShowWay="+adShowWay);
//		log.info("adOperatingRule="+adOperatingRule);
//
//		dateSelectMap = DateValueUtil.getInstance().getDateRangeMap();
//
//		//輸入欄位順序
//		if (!tableHeadShowList.isEmpty()) {
//			for (String s:tableHeadShowList) {
//				tableHeadList.addLast(s);
//			}
//		}
//
//		if(page==0){
//			page=1;
//		}
//		log.info("page="+page);
//
//		if(pageSize==0){
//			pageSize=20;
//		}
//		log.info("pageSize="+pageSize);
//
//		log.info("downloadFlag="+downloadFlag);
//
//		if(downloadFlag.trim().equals("yes")){
//			page=-1;
//		}
//
//		tableHeadList.addFirst("裝置");
//			this.timeName = "年齡";
//		if(StringUtils.equals("S", searchAgesex)){
//			this.timeName = "性別";
//		} 
//		tableHeadList.addFirst("計價方式");
//		tableHeadList.addFirst("廣告樣式");
//		tableHeadList.addFirst("播放類型");
//		tableHeadList.addFirst(timeName);
//		tableHeadList.addFirst("分類");
//		tableHeadList.addFirst("廣告");
//		tableHeadList.addFirst("狀態");
//
//		List<AdAgesexReportVO> resultSumData = adAgesexReportService.loadReportDate(EnumReport.REPORT_HQLTYPE_AGESEX_COUNT.getTextValue(), searchAgesex, searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, adOperatingRule, startDate, endDate, -1, -1);
//
//		int totalPageSize = resultSumData.size();
//
//		List<AdAgesexReportVO> resultData = adAgesexReportService.loadReportDate(EnumReport.REPORT_HQLTYPE_AGESEX.getTextValue(), searchAgesex, searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, adOperatingRule, startDate, endDate, page, pageSize);
//
//		totalPage = totalPageSize/pageSize;
//
//		if((totalPageSize%pageSize)>0){
//			totalPage+=1;
//		}
//
//		if(resultSumData.size()>0){
//			resultDataTrans(resultData);
//			resultSumDataTrans(resultSumData);
//		}
//
//		if(downloadFlag.trim().equals("yes")){
//			makeDownloadReportData();
//		}
//
//		return SUCCESS;
//	}
//
//	private void makeDownloadReportData() throws Exception {
//
//		SimpleDateFormat dformat = new SimpleDateFormat("yyyyMMddhhmmss");
//
//		String filename="廣告族群成效報表_" + dformat.format(new Date()) + FILE_TYPE;
//
//		StringBuffer content=new StringBuffer();
//
//		log.info(">>> customerInfoId = " + super.getCustomer_info_id());
//		PfpCustomerInfo customerInfo = customerInfoService.findCustomerInfo(super.getCustomer_info_id());
//
//		content.append("帳戶," + customerInfo.getCustomerInfoTitle());
//		content.append("\n\n");
//		content.append("報表名稱,PChome 廣告族群成效");
//		content.append("\n\n");
//		content.append("播放類型," + getAdShowWayMap().get(adShowWay));
//		content.append("\n");
//		content.append("廣告樣式," + getAdStyleTypeMap().get(adOperatingRule));
//		content.append("\n");
//		content.append("統計變項," + timeName);
//		content.append("\n");
//		content.append("裝置," + getAdPvclkDeviceMap().get(adPvclkDevice));
//		content.append("\n");
//		content.append("搜尋條件," + getAdSearchWayMap().get(adSearchWay));
//		content.append("\n");
//		content.append("搜尋內容," + searchText);
//		content.append("\n");
//		content.append("日期範圍," + startDate + " 到 " + endDate);
//		content.append("\n\n");
//
//		for(String s:tableHeadList){
//			content.append("\"" + s + "\"");
//			content.append(",");
//		}
//		content.append("\n");
//
//		for(LinkedList<String> sl:tableDataList){
//			int dataNumber = 1;
//			for(String s:sl){
//				if(dataNumber == 12 || dataNumber == 13 || dataNumber == 14 || dataNumber == 16 || dataNumber == 18){
//					content.append(StringEscapeUtils.escapeCsv("=\"NT$ " + s + "\""));
//				} else if(dataNumber == 11 || dataNumber == 17 || dataNumber == 19){
//					content.append("\"" + s + "%\"");
//				} else {
//					content.append("\"" + s + "\"");	
//				}
//				content.append(",");
//				dataNumber++;
//			}
//			content.append("\n");
//		}
//		content.append("\n");
//
//		if (tableDataTotalList!=null) {
//			int dataTotalNumber = 1;
//			for(String s:tableDataTotalList){
//				if(dataTotalNumber == 12 || dataTotalNumber == 13 || dataTotalNumber == 14 || dataTotalNumber == 16 || dataTotalNumber == 18){
//					content.append(StringEscapeUtils.escapeCsv("=\"NT$ " + s + "\""));
//				} else if(dataTotalNumber == 11 || dataTotalNumber == 17 || dataTotalNumber == 19){
//					content.append("\"" + s + "%\"");
//				} else {
//					content.append("\"" + s + "\"");
//				}
//				content.append(",");
//				dataTotalNumber++;
//			}
//			content.append("\n");
//		}
//
//		if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
//			downloadFileName = new String(filename.getBytes("UTF-8"), "ISO8859-1");
//		} else {
//			downloadFileName = URLEncoder.encode(filename, "UTF-8");			
//		}
//
//		downloadFileStream = new ByteArrayInputStream(content.toString().getBytes("big5"));
//
//	}
//
//	private void resultSumDataTrans(List<AdAgesexReportVO> resultSumData) throws Exception {
//
//		NumberFormat intFormat = new DecimalFormat("###,###,###,###");
//		NumberFormat doubleFormat = new DecimalFormat("###,###,###,###.##");
//		NumberFormat doubleFormat2 = new DecimalFormat("###,###,###,###.###");
//
//		tableDataTotalList = new LinkedList<String>();
//		tableDataTotalList.add("");
//		tableDataTotalList.add("總計：" + intFormat.format(resultSumData.size()));
//		tableDataTotalList.add("");
//		tableDataTotalList.add("");
//		tableDataTotalList.add("");
//		tableDataTotalList.add("");
//		tableDataTotalList.add("");
//		tableDataTotalList.add("");
//
//		double t_pv = 0; //總曝光數
//		double t_click = 0; //總互動數
//		double t_ctr = 0; //互動率
//		double t_invalid = 0; //無效點選次數
//		double t_costAvg = 0; //單次互動費用
//		double t_kiloCost = 0;	//千次曝光費用
//		double t_cost = 0; //總費用
//		//轉換數
//		double t_convert_count = 0;
//		//轉換價值
//		double t_convert_price_count = 0;
//		double t_convert_ctr = 0;
//		double t_convert_cost = 0;
//		double t_convert_investment_cost = 0;
//		//加總
//		for (int i=0; i<resultSumData.size(); i++) {
//			AdAgesexReportVO vo = resultSumData.get(i);
//			t_pv += vo.getAdPvSum().doubleValue();
//			t_click += vo.getAdClkSum().doubleValue();
//			t_cost += vo.getAdPriceSum().doubleValue();
//			t_invalid += vo.getAdInvClkSum().doubleValue();
//			t_convert_count += vo.getConvertCount().doubleValue();
//			t_convert_price_count += vo.getConvertPriceCount().doubleValue();
//		}
//		t_cost = new BigDecimal(String.valueOf(t_cost)).setScale(3, BigDecimal.ROUND_FLOOR).doubleValue();
//		
//		//互動率 = 總互動次數 / 總曝光數
//		if (t_pv>0 && t_click>0) {
//			t_ctr = (t_click / t_pv) * 100;
//		}
//
//		//單次互動費用 = 總費用 / 總互動次數
//		if (t_cost>0 && t_click>0) {
//			t_costAvg = t_cost / t_click;
//		}
//		
//		//千次曝光費用 = 總費用*1000 / 曝光數
//		if(t_cost>0 && t_pv>0){
//			t_kiloCost = (t_cost * 1000) / t_pv;
//		}
//		//轉換率
//		if(t_convert_count > 0 && t_click > 0){
//			t_convert_ctr  = (t_convert_count / t_click) * 100;
//		}
//		//平均轉換成本
//		if(t_cost > 0 && t_convert_count > 0){
//			t_convert_cost = t_cost / t_convert_count;
//		}
//		//廣告投資報酬率
//		if(t_convert_price_count > 0 && t_cost > 0){
//			t_convert_investment_cost = (t_convert_price_count / t_cost) * 100;
//		}		
//		if (!tableHeadShowList.isEmpty()) {
//			String mapKey;
//			for (String s: tableHeadShowList) {
//				mapKey = tableHeadNameMap.get(s);
//				if(mapKey.equals(EnumReport.REPORT_CHART_CONVERT_CTR.getTextValue())){
//					tableDataTotalList.addLast(doubleFormat.format(t_convert_ctr));
//				}else if(mapKey.equals(EnumReport.REPORT_CHART_CONVERT_COST.getTextValue())){
//					tableDataTotalList.addLast(doubleFormat.format(t_convert_cost));
//				}else if(mapKey.equals(EnumReport.REPORT_CHART_CONVERT_INVESTMENT.getTextValue())){
//					tableDataTotalList.addLast(doubleFormat.format(t_convert_investment_cost));
//				}else if(mapKey.equals(EnumReport.REPORT_CHART_CONVERT.getTextValue())){
//					tableDataTotalList.addLast(intFormat.format(t_convert_count));
//				}else if(mapKey.equals(EnumReport.REPORT_CHART_CONVERT_PRICE.getTextValue())){
//					tableDataTotalList.addLast(intFormat.format(t_convert_price_count));
//				}else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_PV.getTextValue())) {
//					tableDataTotalList.addLast(intFormat.format(t_pv));
//				} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_CLICK.getTextValue())) {
//					tableDataTotalList.addLast(intFormat.format(t_click));
//				} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_CTR.getTextValue())) {
//					tableDataTotalList.addLast(doubleFormat.format(t_ctr));
//				} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_INVALID.getTextValue())) {
//					tableDataTotalList.addLast(intFormat.format(t_invalid));
//				} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_AVGCOST.getTextValue())) {
//					tableDataTotalList.addLast(doubleFormat.format(t_costAvg));
//				} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_KILOCOST.getTextValue())) {
//					tableDataTotalList.addLast(doubleFormat.format(t_kiloCost));
//				} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_COST.getTextValue())) {
//					tableDataTotalList.addLast(doubleFormat2.format(t_cost));
//				}
//			}
//		}
//	}
//
//	private void resultDataTrans(List<AdAgesexReportVO> resultData) throws Exception {
//
//		LinkedList<String> tableInDataList;
//
//        NumberFormat intFormat = new DecimalFormat("###,###,###,###");
//		NumberFormat doubleFormat = new DecimalFormat("###,###,###,###.##");
//		NumberFormat doubleFormat2 = new DecimalFormat("###,###,###,###.###");
//
//		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
//
//		long nowTime = new Date().getTime();
//
//		for (int i=0; i<resultData.size(); i++) {
//
//			tableInDataList = new LinkedList<String>();
//
//			AdAgesexReportVO vo = resultData.get(i);
//
//			String adGroupSeq = vo.getAdGroupSeq();
//
//			PfpAdGroup pfpAdGroup = adGroupService.getPfpAdGroupBySeq(adGroupSeq);
//
//			String adGroupName = pfpAdGroup.getAdGroupName();
//			int adGroupStatus = pfpAdGroup.getAdGroupStatus();
//
//			String adActionName = pfpAdGroup.getPfpAdAction().getAdActionName();
//			int adActionStatus = pfpAdGroup.getPfpAdAction().getAdActionStatus();
//			Date adActionStartDate = pfpAdGroup.getPfpAdAction().getAdActionStartDate();
//			Date adActionEndDate = pfpAdGroup.getPfpAdAction().getAdActionEndDate();
//
//			double pv = vo.getAdPvSum().doubleValue();
//			double click = vo.getAdClkSum().doubleValue();
//			
//			double cost = vo.getAdPriceSum().doubleValue();
//			double invClick = vo.getAdInvClkSum().doubleValue();
//			double ctr = 0;
//			double costAvg = 0;
//			double kiloCost = 0;
//			double convertCount = vo.getConvertCount().doubleValue();
//			double convertPriceCount = vo.getConvertPriceCount().doubleValue();
//			double convertCTR = 0;
//			double convertCost = 0;
//			double convertInvestmentCost = 0;
//			String adDevice = vo.getAdDevice();
//			String age = vo.getAge();
//			String sex = vo.getSex();
//			String adTypeName = vo.getAdType();
//			String adOperatingRuleName = vo.getAdOperatingRule();
//			String adClkPriceTypeName = vo.getAdClkPriceType();
//
//			//互動率 = 互動次數 / 曝光數
//			if (pv>0 && click>0) {
//				ctr = (click / pv) * 100;
//			}
//
//			//單次互動費用 = 總費用 / 總互動次數
//			if (cost>0 && click>0) {
//				costAvg = cost / click;
//			}
//
//			//千次曝光費用 = 總費用*1000 / 曝光數
//			if(cost>0 && pv>0){
//				kiloCost = (cost * 1000) / pv;
//			}
//			//轉換率
//			if(convertCount > 0 && click > 0){
//				convertCTR  = (convertCount / click) * 100;
//			}
//			//平均轉換成本
//			if(cost > 0 && convertCount > 0){
//				convertCost = cost / convertCount;
//			}
//			//廣告投資報酬率
//			if(convertPriceCount > 0 && cost > 0){
//				convertInvestmentCost = (convertPriceCount / cost) * 100;
//			}
//
//			//廣告狀態為開啟的話必須判斷走期( 待播放 or 走期中 or 已結束 )
//			if (adActionStatus == EnumStatus.Open.getStatusId()) {
//				long _startDate = (dateFormat.parse(dateFormat2.format(adActionStartDate) + " 00:00:00")).getTime();
//				long _endDate = (dateFormat.parse(dateFormat2.format(adActionEndDate) + " 23:59:59")).getTime();
//				if (nowTime < _startDate) {
//					adActionStatus = EnumStatus.Waitbroadcast.getStatusId();
//				} else if (nowTime > _endDate) {
//					adActionStatus = EnumStatus.End.getStatusId();
//				} else {
//					adActionStatus = EnumStatus.Broadcast.getStatusId();
//				}
//			}
//
//			//播放狀態
//			String alter = "";
//			String icon = "icon_adclose.gif";
//			if (adActionStatus == EnumStatus.Broadcast.getStatusId() &&
//					adGroupStatus == EnumStatus.Open.getStatusId()) {
//
//				alter = "走期中";
//				icon = "icon_adopen.gif";
//
//			} else if (adActionStatus != EnumStatus.Broadcast.getStatusId() &&
//					adGroupStatus == EnumStatus.Open.getStatusId()) {
//
//				alter = "廣告" + getAdStatusMap().get(Integer.toString(adActionStatus));
//
//			} else if (adActionStatus == EnumStatus.Broadcast.getStatusId() &&
//					adGroupStatus != EnumStatus.Open.getStatusId()) {
//
//				alter = "分類" + getAdStatusMap().get(Integer.toString(adGroupStatus));
//
//			} else {
//
//				alter = "廣告" + getAdStatusMap().get(Integer.toString(adActionStatus)) + "，" + "分類" + getAdStatusMap().get(Integer.toString(adGroupStatus));
//				
//			}
//			
//			if(downloadFlag.equals("yes")){
//				tableInDataList.addLast(alter);
//			} else {
//				tableInDataList.addLast("<img src=\"./html/img/" + icon + "\" alt=\"" + alter + "\" title=\"" + alter + "\">");
//			}
//			
//			tableInDataList.addLast(adActionName);
//			tableInDataList.addLast(adGroupName);
//
//			if(StringUtils.equals("S", searchAgesex)){
//				tableInDataList.addLast(sex);
//			} else {
//				tableInDataList.addLast(age);
//			}
//			tableInDataList.addLast(adTypeName);
//			tableInDataList.addLast(adOperatingRuleName);
//			tableInDataList.addLast(adClkPriceTypeName);
//			tableInDataList.addLast(adDevice);
//
//			if(!tableHeadShowList.isEmpty()){
//				String mapKey;
//				for(String s:tableHeadShowList){
//					mapKey=tableHeadNameMap.get(s);
//					if(mapKey.equals(EnumReport.REPORT_CHART_CONVERT_CTR.getTextValue())){
//						tableInDataList.addLast(doubleFormat.format(convertCTR));
//					}else if(mapKey.equals(EnumReport.REPORT_CHART_CONVERT_COST.getTextValue())){
//						tableInDataList.addLast(doubleFormat.format(convertCost));
//					}else if(mapKey.equals(EnumReport.REPORT_CHART_CONVERT_INVESTMENT.getTextValue())){
//						tableInDataList.addLast(doubleFormat.format(convertInvestmentCost));
//					}else if(mapKey.equals(EnumReport.REPORT_CHART_CONVERT.getTextValue())){
//						tableInDataList.addLast(intFormat.format(convertCount));
//					}else if(mapKey.equals(EnumReport.REPORT_CHART_CONVERT_PRICE.getTextValue())){
//						tableInDataList.addLast(intFormat.format(convertPriceCount));
//					}else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_PV.getTextValue())) {
//						tableInDataList.addLast(intFormat.format(pv));
//					} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_CLICK.getTextValue())) {
//						tableInDataList.addLast(intFormat.format(click));
// 					} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_CTR.getTextValue())) {
//						tableInDataList.addLast(doubleFormat.format(ctr));
//					} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_INVALID.getTextValue())) {
// 						tableInDataList.addLast(intFormat.format(invClick));
// 					} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_AVGCOST.getTextValue())) {
//						tableInDataList.addLast(doubleFormat.format(costAvg));
//					} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_KILOCOST.getTextValue())) {
//						tableInDataList.addLast(doubleFormat.format(kiloCost));
//					} else if (mapKey.equals(EnumReport.REPORT_CHART_TYPE_COST.getTextValue())) {
//						tableInDataList.addLast(doubleFormat2.format(cost));
//					}
//				}
//			}
//
//			tableDataList.addLast(tableInDataList);
//		}
//	}
//
//	public LinkedList<String> getTableHeadList() {
//		return tableHeadList;
//	}
//
//	public LinkedList<LinkedList<String>> getTableDataList() {
//		return tableDataList;
//	}
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
//	public String getAdShowWay() {
//		return adShowWay;
//	}
//
//	public void setAdShowWay(String adShowWay) {
//		this.adShowWay = adShowWay;
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
//	public String getSearchId() {
//		return searchId;
//	}
//
//	public void setSearchId(String searchId) {
//		this.searchId = searchId;
//	}
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
//	public void setCustomerInfoService(IPfpCustomerInfoService customerInfoService) {
//		this.customerInfoService = customerInfoService;
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
//	public LinkedHashMap<String, String> getDateSelectMap() {
//		return dateSelectMap;
//	}
//
//	public LinkedList<String> getTableDataTotalList() {
//		return tableDataTotalList;
//	}
//
//	public String getReportTitle() {
//		return reportTitle;
//	}
//
//	public void setAdAgesexReportService(IAdAgesexReportService adAgesexReportService) {
//		this.adAgesexReportService = adAgesexReportService;
//	}
//
//	public String[] getAlign_data() {
//		return align_data;
//	}
//
//	public String[] getAlign_sum() {
//		return align_sum;
//	}
//
//	public void setAdGroupService(IPfpAdGroupService adGroupService) {
//		this.adGroupService = adGroupService;
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
//	public String getSearchAgesex() {
//		return searchAgesex;
//	}
//
//	public void setSearchAgesex(String searchAgesex) {
//		this.searchAgesex = searchAgesex;
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
//	public IPfpCodeService getPfpCodeService() {
//		return pfpCodeService;
//	}
//
//	public void setPfpCodeService(IPfpCodeService pfpCodeService) {
//		this.pfpCodeService = pfpCodeService;
//	}
	
}