package com.pchome.akbpfp.db.dao.report;

import java.math.BigDecimal;
import java.util.Date;

public class AdKeywordReportVO {

	private String customerInfoId; // 使用者帳號
	private String searchText = ""; // 搜尋文字
	private String startDate = ""; // 查詢開始日期
	private String endDate = ""; // 查詢結束日期
	private int page = 1; // 第幾頁
	private int pageSize = 10; // 每頁筆數
	private String whereMap = ""; // sql篩選條件
	private String sortBy = ""; // 排序欄位
	private boolean isDownloadOrIsNotCuttingPagination = false; // 是否為下載，或是否切分頁FLAG
	private int rowCount = 0; // 總計幾筆
	
	private Date reportDate; // 報表日期
	
	private String adKeywordSeq; // 關鍵字序號
	private String keywordStyle; // 取得平均廣告排名用,代碼參考EnumAdKeywordType
	
	private boolean adStatusOnOff = false; // 狀態on或off，預設off
	private String adStatusName; // 狀態中文，產excel用
	private String adKeyword; // 關鍵字
	private String adActionName; // 廣告活動名稱
	private String adGroupName; // 廣告分類名稱
	private String adDevice; // 裝置
	
	// 廣泛比對
	private boolean kwOpen = false; // 廣泛比對開啟狀態on或off，預設off
	private String kwOpenName = "關閉"; // 廣泛比對開啟狀態中文，預設關閉
	private BigDecimal kwPvSum; // 關鍵字PV總和(曝光數)
	private BigDecimal kwClkSum; // 關鍵字點擊總和(互動數)
	private Double kwCtrSum; // 關鍵字點擊率總和(互動率)
	private Double kwPriceAvgSum; // 關鍵字平均點選費用總和(單次互動費用)
	private Double kwKiloCost; // 千次曝光費用
	private Double kwPriceSum; // 關鍵字價格總和(費用)
	private Double kwRankAvg; // 廣泛比對排名(平均廣告排名)
	private boolean kwRowShowHidden = false; // 廣泛比對列有值就顯示沒值隱藏列，預設隱藏
	
	// 廣泛比對總計-報表用
	private BigDecimal reportKwPvTotal; // 廣告PV總和(曝光數)
	private BigDecimal reportKwClkTotal; // 廣告Click總和(互動數)
	private Double reportKwCtrTotal; // 互動率
	private Double reportKwPriceAvgTotal; // 單次互動費用
	private Double reportKwKiloCostTotal; // 千次曝光費用
	private Double reportKwPriceTotal; // 廣告價格總和(費用)
	
	// 詞組比對
	private boolean kwPhrOpen = false; // 詞組比對開啟狀態on或off，預設off
	private String kwPhrOpenName = "關閉"; // 詞組比對開啟狀態中文，預設關閉
	private BigDecimal kwPhrPvSum; // 關鍵字PV總和(曝光數)
	private BigDecimal kwPhrClkSum; // 關鍵字點擊總和(互動數)
	private Double kwPhrCtrSum; // 關鍵字點擊率總和(互動率)
	private Double kwPhrPriceAvgSum; // 關鍵字平均點選費用總和(單次互動費用)
	private Double kwPhrKiloCost; // 千次曝光費用
	private Double kwPhrPriceSum; // 關鍵字價格總和(費用)
	private Double kwPhrRankAvg; // 詞組比對排名(平均廣告排名)
	private boolean kwPhrRowShowHidden = false; // 詞組比對列有值就顯示沒值隱藏列，預設隱藏
	
	// 詞組比對總計-報表用
	private BigDecimal reportKwPhrPvTotal; // 廣告PV總和(曝光數)
	private BigDecimal reportKwPhrClkTotal; // 廣告Click總和(互動數)
	private Double reportKwPhrCtrTotal; // 互動率
	private Double reportKwPhrPriceAvgTotal; // 單次互動費用
	private Double reportKwPhrKiloCostTotal; // 千次曝光費用
	private Double reportKwPhrPriceTotal; // 廣告價格總和(費用)
		
	// 精準比對
	private boolean kwPreOpen = false; // 精準比對開啟狀態on或off，預設off
	private String kwPreOpenName = "關閉"; // 精準比對開啟狀態中文，預設關閉
	private BigDecimal kwPrePvSum; // 關鍵字PV總和(曝光數)
	private BigDecimal kwPreClkSum; // 關鍵字點擊總和(互動數)
	private Double kwPreCtrSum; // 關鍵字點擊率總和(互動率)
	private Double kwPrePriceAvgSum; // 關鍵字平均點選費用總和(單次互動費用)
	private Double kwPreKiloCost; // 千次曝光費用
	private Double kwPrePriceSum; // 關鍵字價格總和(費用)
	private Double kwPreRankAvg; // 精準比對排名(平均廣告排名)
	private boolean kwPreRowShowHidden = false; // 精準比對列有值就顯示沒值隱藏列，預設隱藏
	
	// 精準比對總計-報表用
	private BigDecimal reportKwPrePvTotal; // 廣告PV總和(曝光數)
	private BigDecimal reportKwPreClkTotal; // 廣告Click總和(互動數)
	private Double reportKwPreCtrTotal; // 互動率
	private Double reportKwPrePriceAvgTotal; // 單次互動費用
	private Double reportKwPreKiloCostTotal; // 千次曝光費用
	private Double reportKwPrePriceTotal; // 廣告價格總和(費用)
	
	// 小計
	private BigDecimal kwPvSubtotal; // 關鍵字PV總和(曝光數)
	private BigDecimal kwClkSubtotal; // 關鍵字點擊總和(互動數)
	private Double kwCtrSubtotal; // 關鍵字點擊率總和(互動率)
	private Double kwPriceAvgSubtotal; // 關鍵字平均點選費用總和(單次互動費用)
	private Double kwKiloCostSubtotal; // 千次曝光費用
	private BigDecimal kwPriceSubtotal; // 關鍵字價格總和(費用)

	// 總計
	private BigDecimal pvTotal; // 廣告PV總和(曝光數)
	private BigDecimal clkTotal; // 廣告Click總和(互動數)
	private Double ctrTotal; // 互動率
	private Double priceAvgTotal; // 單次互動費用
	private Double kiloCostTotal; // 千次曝光費用
	private Double priceTotal; // 廣告價格總和(費用)
	
	
	
	public String getCustomerInfoId() {
		return customerInfoId;
	}
	public void setCustomerInfoId(String customerInfoId) {
		this.customerInfoId = customerInfoId;
	}
	public String getSearchText() {
		return searchText;
	}
	public void setSearchText(String searchText) {
		this.searchText = searchText;
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
	public String getWhereMap() {
		return whereMap;
	}
	public void setWhereMap(String whereMap) {
		this.whereMap = whereMap;
	}
	public String getSortBy() {
		return sortBy;
	}
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}
	public boolean isDownloadOrIsNotCuttingPagination() {
		return isDownloadOrIsNotCuttingPagination;
	}
	public void setDownloadOrIsNotCuttingPagination(boolean isDownloadOrIsNotCuttingPagination) {
		this.isDownloadOrIsNotCuttingPagination = isDownloadOrIsNotCuttingPagination;
	}
	public int getRowCount() {
		return rowCount;
	}
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	public String getAdKeywordSeq() {
		return adKeywordSeq;
	}
	public void setAdKeywordSeq(String adKeywordSeq) {
		this.adKeywordSeq = adKeywordSeq;
	}
	public String getKeywordStyle() {
		return keywordStyle;
	}
	public void setKeywordStyle(String keywordStyle) {
		this.keywordStyle = keywordStyle;
	}
	public boolean isAdStatusOnOff() {
		return adStatusOnOff;
	}
	public void setAdStatusOnOff(boolean adStatusOnOff) {
		this.adStatusOnOff = adStatusOnOff;
	}
	public String getAdStatusName() {
		return adStatusName;
	}
	public void setAdStatusName(String adStatusName) {
		this.adStatusName = adStatusName;
	}
	public String getAdKeyword() {
		return adKeyword;
	}
	public void setAdKeyword(String adKeyword) {
		this.adKeyword = adKeyword;
	}
	public String getAdActionName() {
		return adActionName;
	}
	public void setAdActionName(String adActionName) {
		this.adActionName = adActionName;
	}
	public String getAdGroupName() {
		return adGroupName;
	}
	public void setAdGroupName(String adGroupName) {
		this.adGroupName = adGroupName;
	}
	public String getAdDevice() {
		return adDevice;
	}
	public void setAdDevice(String adDevice) {
		this.adDevice = adDevice;
	}
	public boolean isKwOpen() {
		return kwOpen;
	}
	public void setKwOpen(boolean kwOpen) {
		this.kwOpen = kwOpen;
	}
	public String getKwOpenName() {
		return kwOpenName;
	}
	public void setKwOpenName(String kwOpenName) {
		this.kwOpenName = kwOpenName;
	}
	public BigDecimal getKwPvSum() {
		return kwPvSum;
	}
	public void setKwPvSum(BigDecimal kwPvSum) {
		this.kwPvSum = kwPvSum;
	}
	public BigDecimal getKwClkSum() {
		return kwClkSum;
	}
	public void setKwClkSum(BigDecimal kwClkSum) {
		this.kwClkSum = kwClkSum;
	}
	public Double getKwCtrSum() {
		return kwCtrSum;
	}
	public void setKwCtrSum(Double kwCtrSum) {
		this.kwCtrSum = kwCtrSum;
	}
	public Double getKwPriceAvgSum() {
		return kwPriceAvgSum;
	}
	public void setKwPriceAvgSum(Double kwPriceAvgSum) {
		this.kwPriceAvgSum = kwPriceAvgSum;
	}
	public Double getKwKiloCost() {
		return kwKiloCost;
	}
	public void setKwKiloCost(Double kwKiloCost) {
		this.kwKiloCost = kwKiloCost;
	}
	public Double getKwPriceSum() {
		return kwPriceSum;
	}
	public void setKwPriceSum(Double kwPriceSum) {
		this.kwPriceSum = kwPriceSum;
	}
	public Double getKwRankAvg() {
		return kwRankAvg;
	}
	public void setKwRankAvg(Double kwRankAvg) {
		this.kwRankAvg = kwRankAvg;
	}
	public boolean isKwRowShowHidden() {
		return kwRowShowHidden;
	}
	public void setKwRowShowHidden(boolean kwRowShowHidden) {
		this.kwRowShowHidden = kwRowShowHidden;
	}
	public BigDecimal getReportKwPvTotal() {
		return reportKwPvTotal;
	}
	public void setReportKwPvTotal(BigDecimal reportKwPvTotal) {
		this.reportKwPvTotal = reportKwPvTotal;
	}
	public BigDecimal getReportKwClkTotal() {
		return reportKwClkTotal;
	}
	public void setReportKwClkTotal(BigDecimal reportKwClkTotal) {
		this.reportKwClkTotal = reportKwClkTotal;
	}
	public Double getReportKwCtrTotal() {
		return reportKwCtrTotal;
	}
	public void setReportKwCtrTotal(Double reportKwCtrTotal) {
		this.reportKwCtrTotal = reportKwCtrTotal;
	}
	public Double getReportKwPriceAvgTotal() {
		return reportKwPriceAvgTotal;
	}
	public void setReportKwPriceAvgTotal(Double reportKwPriceAvgTotal) {
		this.reportKwPriceAvgTotal = reportKwPriceAvgTotal;
	}
	public Double getReportKwKiloCostTotal() {
		return reportKwKiloCostTotal;
	}
	public void setReportKwKiloCostTotal(Double reportKwKiloCostTotal) {
		this.reportKwKiloCostTotal = reportKwKiloCostTotal;
	}
	public Double getReportKwPriceTotal() {
		return reportKwPriceTotal;
	}
	public void setReportKwPriceTotal(Double reportKwPriceTotal) {
		this.reportKwPriceTotal = reportKwPriceTotal;
	}
	public boolean isKwPhrOpen() {
		return kwPhrOpen;
	}
	public void setKwPhrOpen(boolean kwPhrOpen) {
		this.kwPhrOpen = kwPhrOpen;
	}
	public String getKwPhrOpenName() {
		return kwPhrOpenName;
	}
	public void setKwPhrOpenName(String kwPhrOpenName) {
		this.kwPhrOpenName = kwPhrOpenName;
	}
	public BigDecimal getKwPhrPvSum() {
		return kwPhrPvSum;
	}
	public void setKwPhrPvSum(BigDecimal kwPhrPvSum) {
		this.kwPhrPvSum = kwPhrPvSum;
	}
	public BigDecimal getKwPhrClkSum() {
		return kwPhrClkSum;
	}
	public void setKwPhrClkSum(BigDecimal kwPhrClkSum) {
		this.kwPhrClkSum = kwPhrClkSum;
	}
	public Double getKwPhrCtrSum() {
		return kwPhrCtrSum;
	}
	public void setKwPhrCtrSum(Double kwPhrCtrSum) {
		this.kwPhrCtrSum = kwPhrCtrSum;
	}
	public Double getKwPhrPriceAvgSum() {
		return kwPhrPriceAvgSum;
	}
	public void setKwPhrPriceAvgSum(Double kwPhrPriceAvgSum) {
		this.kwPhrPriceAvgSum = kwPhrPriceAvgSum;
	}
	public Double getKwPhrKiloCost() {
		return kwPhrKiloCost;
	}
	public void setKwPhrKiloCost(Double kwPhrKiloCost) {
		this.kwPhrKiloCost = kwPhrKiloCost;
	}
	public Double getKwPhrPriceSum() {
		return kwPhrPriceSum;
	}
	public void setKwPhrPriceSum(Double kwPhrPriceSum) {
		this.kwPhrPriceSum = kwPhrPriceSum;
	}
	public Double getKwPhrRankAvg() {
		return kwPhrRankAvg;
	}
	public void setKwPhrRankAvg(Double kwPhrRankAvg) {
		this.kwPhrRankAvg = kwPhrRankAvg;
	}
	public boolean isKwPhrRowShowHidden() {
		return kwPhrRowShowHidden;
	}
	public void setKwPhrRowShowHidden(boolean kwPhrRowShowHidden) {
		this.kwPhrRowShowHidden = kwPhrRowShowHidden;
	}
	public BigDecimal getReportKwPhrPvTotal() {
		return reportKwPhrPvTotal;
	}
	public void setReportKwPhrPvTotal(BigDecimal reportKwPhrPvTotal) {
		this.reportKwPhrPvTotal = reportKwPhrPvTotal;
	}
	public BigDecimal getReportKwPhrClkTotal() {
		return reportKwPhrClkTotal;
	}
	public void setReportKwPhrClkTotal(BigDecimal reportKwPhrClkTotal) {
		this.reportKwPhrClkTotal = reportKwPhrClkTotal;
	}
	public Double getReportKwPhrCtrTotal() {
		return reportKwPhrCtrTotal;
	}
	public void setReportKwPhrCtrTotal(Double reportKwPhrCtrTotal) {
		this.reportKwPhrCtrTotal = reportKwPhrCtrTotal;
	}
	public Double getReportKwPhrPriceAvgTotal() {
		return reportKwPhrPriceAvgTotal;
	}
	public void setReportKwPhrPriceAvgTotal(Double reportKwPhrPriceAvgTotal) {
		this.reportKwPhrPriceAvgTotal = reportKwPhrPriceAvgTotal;
	}
	public Double getReportKwPhrKiloCostTotal() {
		return reportKwPhrKiloCostTotal;
	}
	public void setReportKwPhrKiloCostTotal(Double reportKwPhrKiloCostTotal) {
		this.reportKwPhrKiloCostTotal = reportKwPhrKiloCostTotal;
	}
	public Double getReportKwPhrPriceTotal() {
		return reportKwPhrPriceTotal;
	}
	public void setReportKwPhrPriceTotal(Double reportKwPhrPriceTotal) {
		this.reportKwPhrPriceTotal = reportKwPhrPriceTotal;
	}
	public boolean isKwPreOpen() {
		return kwPreOpen;
	}
	public void setKwPreOpen(boolean kwPreOpen) {
		this.kwPreOpen = kwPreOpen;
	}
	public String getKwPreOpenName() {
		return kwPreOpenName;
	}
	public void setKwPreOpenName(String kwPreOpenName) {
		this.kwPreOpenName = kwPreOpenName;
	}
	public BigDecimal getKwPrePvSum() {
		return kwPrePvSum;
	}
	public void setKwPrePvSum(BigDecimal kwPrePvSum) {
		this.kwPrePvSum = kwPrePvSum;
	}
	public BigDecimal getKwPreClkSum() {
		return kwPreClkSum;
	}
	public void setKwPreClkSum(BigDecimal kwPreClkSum) {
		this.kwPreClkSum = kwPreClkSum;
	}
	public Double getKwPreCtrSum() {
		return kwPreCtrSum;
	}
	public void setKwPreCtrSum(Double kwPreCtrSum) {
		this.kwPreCtrSum = kwPreCtrSum;
	}
	public Double getKwPrePriceAvgSum() {
		return kwPrePriceAvgSum;
	}
	public void setKwPrePriceAvgSum(Double kwPrePriceAvgSum) {
		this.kwPrePriceAvgSum = kwPrePriceAvgSum;
	}
	public Double getKwPreKiloCost() {
		return kwPreKiloCost;
	}
	public void setKwPreKiloCost(Double kwPreKiloCost) {
		this.kwPreKiloCost = kwPreKiloCost;
	}
	public Double getKwPrePriceSum() {
		return kwPrePriceSum;
	}
	public void setKwPrePriceSum(Double kwPrePriceSum) {
		this.kwPrePriceSum = kwPrePriceSum;
	}
	public Double getKwPreRankAvg() {
		return kwPreRankAvg;
	}
	public void setKwPreRankAvg(Double kwPreRankAvg) {
		this.kwPreRankAvg = kwPreRankAvg;
	}
	public boolean isKwPreRowShowHidden() {
		return kwPreRowShowHidden;
	}
	public void setKwPreRowShowHidden(boolean kwPreRowShowHidden) {
		this.kwPreRowShowHidden = kwPreRowShowHidden;
	}
	public BigDecimal getReportKwPrePvTotal() {
		return reportKwPrePvTotal;
	}
	public void setReportKwPrePvTotal(BigDecimal reportKwPrePvTotal) {
		this.reportKwPrePvTotal = reportKwPrePvTotal;
	}
	public BigDecimal getReportKwPreClkTotal() {
		return reportKwPreClkTotal;
	}
	public void setReportKwPreClkTotal(BigDecimal reportKwPreClkTotal) {
		this.reportKwPreClkTotal = reportKwPreClkTotal;
	}
	public Double getReportKwPreCtrTotal() {
		return reportKwPreCtrTotal;
	}
	public void setReportKwPreCtrTotal(Double reportKwPreCtrTotal) {
		this.reportKwPreCtrTotal = reportKwPreCtrTotal;
	}
	public Double getReportKwPrePriceAvgTotal() {
		return reportKwPrePriceAvgTotal;
	}
	public void setReportKwPrePriceAvgTotal(Double reportKwPrePriceAvgTotal) {
		this.reportKwPrePriceAvgTotal = reportKwPrePriceAvgTotal;
	}
	public Double getReportKwPreKiloCostTotal() {
		return reportKwPreKiloCostTotal;
	}
	public void setReportKwPreKiloCostTotal(Double reportKwPreKiloCostTotal) {
		this.reportKwPreKiloCostTotal = reportKwPreKiloCostTotal;
	}
	public Double getReportKwPrePriceTotal() {
		return reportKwPrePriceTotal;
	}
	public void setReportKwPrePriceTotal(Double reportKwPrePriceTotal) {
		this.reportKwPrePriceTotal = reportKwPrePriceTotal;
	}
	public BigDecimal getKwPvSubtotal() {
		return kwPvSubtotal;
	}
	public void setKwPvSubtotal(BigDecimal kwPvSubtotal) {
		this.kwPvSubtotal = kwPvSubtotal;
	}
	public BigDecimal getKwClkSubtotal() {
		return kwClkSubtotal;
	}
	public void setKwClkSubtotal(BigDecimal kwClkSubtotal) {
		this.kwClkSubtotal = kwClkSubtotal;
	}
	public Double getKwCtrSubtotal() {
		return kwCtrSubtotal;
	}
	public void setKwCtrSubtotal(Double kwCtrSubtotal) {
		this.kwCtrSubtotal = kwCtrSubtotal;
	}
	public Double getKwPriceAvgSubtotal() {
		return kwPriceAvgSubtotal;
	}
	public void setKwPriceAvgSubtotal(Double kwPriceAvgSubtotal) {
		this.kwPriceAvgSubtotal = kwPriceAvgSubtotal;
	}
	public Double getKwKiloCostSubtotal() {
		return kwKiloCostSubtotal;
	}
	public void setKwKiloCostSubtotal(Double kwKiloCostSubtotal) {
		this.kwKiloCostSubtotal = kwKiloCostSubtotal;
	}
	public BigDecimal getKwPriceSubtotal() {
		return kwPriceSubtotal;
	}
	public void setKwPriceSubtotal(BigDecimal kwPriceSubtotal) {
		this.kwPriceSubtotal = kwPriceSubtotal;
	}
	public BigDecimal getPvTotal() {
		return pvTotal;
	}
	public void setPvTotal(BigDecimal pvTotal) {
		this.pvTotal = pvTotal;
	}
	public BigDecimal getClkTotal() {
		return clkTotal;
	}
	public void setClkTotal(BigDecimal clkTotal) {
		this.clkTotal = clkTotal;
	}
	public Double getCtrTotal() {
		return ctrTotal;
	}
	public void setCtrTotal(Double ctrTotal) {
		this.ctrTotal = ctrTotal;
	}
	public Double getPriceAvgTotal() {
		return priceAvgTotal;
	}
	public void setPriceAvgTotal(Double priceAvgTotal) {
		this.priceAvgTotal = priceAvgTotal;
	}
	public Double getKiloCostTotal() {
		return kiloCostTotal;
	}
	public void setKiloCostTotal(Double kiloCostTotal) {
		this.kiloCostTotal = kiloCostTotal;
	}
	public Double getPriceTotal() {
		return priceTotal;
	}
	public void setPriceTotal(Double priceTotal) {
		this.priceTotal = priceTotal;
	}
	public Date getReportDate() {
		return reportDate;
	}
	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	
	
//	private String reportDate; //報表日期
//
//	private String kwSeq; //關鍵字序號
//	private String keyward; //關鍵字
//	private String adType; //廣告方式
//	private String adStatus; //廣告狀態
//	private String adRankAvg; //廣泛比對排名
//	private String adPhrRankAvg; //詞組比對排名
//	private String adPreRankAvg; //精準比對排名
//	private String kwStatus; //關鍵字狀態
//	private String kwPvclkDate; //關鍵字曝光點擊日期
//
//	private String adGroupSeq; //廣告分類序號
//	private String adGroupName; //廣告分類名稱
//	private String adGroupStatus; //廣告分類狀態
//	private String adGroupSearchPrice; //找東西廣告出價
//	private String adGroupChannelPrice; //PChome頻道廣告出價
//
//	private String adActionSeq; //廣告活動序號
//	private String adActionName; //廣告活動名稱
//	private String adActionStatus; //廣告活動狀態
//	private String adActionStartDate; //廣告活動走期起
//	private String adActionEndDate; //廣告活動走期迄
//
//	private String adKeywordSeq; //關鍵字序號
//	private String adKeyword; //關鍵字
//	private String adKeywordStatus; //關鍵字狀態
//	
////	private String customerInfoId; // 帳號
//	private String kwDevice; // 裝置
//	private String kwAdType; //廣告類型
//	private String dataTotal;	//總比數
//	
//	//廣泛比對
//	private String kwOpen;	//比對方式開啟狀態
//	private String kwPvSum; //關鍵字PV總和
//	private String kwClkSum; //關鍵字點擊總和
//	private String kwCtrSum; //關鍵字點擊率總和
//	private String kwPriceSum; //關鍵字價格總和
//	private String kwPriceAvgSum; //關鍵字平均點選費用總和
//	private String kwInvClkSum; //關鍵字無效點擊總和
//	
//	//詞組比對
//	private String kwPhrOpen;	//比對方式開啟狀態
//	private String kwPhrPvSum; //關鍵字PV總和
//	private String kwPhrClkSum; //關鍵字點擊總和
//	private String kwPhrCtrSum; //關鍵字點擊率總和
//	private String kwPhrPriceSum; //關鍵字價格總和
//	private String kwPhrPriceAvgSum; //關鍵字平均點選費用總和
//	private String kwPhrInvClkSum; //關鍵字無效點擊總和
//	
//	//精準比對
//	private String kwPreOpen;	//比對方式開啟狀態
//	private String kwPrePvSum; //關鍵字PV總和
//	private String kwPreClkSum; //關鍵字點擊總和
//	private String kwPreCtrSum; //關鍵字點擊率總和
//	private String kwPrePriceSum; //關鍵字價格總和
//	private String kwPrePriceAvgSum; //關鍵字平均點選費用總和
//	private String kwPreInvClkSum; //關鍵字無效點擊總和
//	
//	//總計
//	private String kwPvTotal; //關鍵字PV總和
//	private String kwClkTotal; //關鍵字點擊總和
//	private String kwCtrTotal; //關鍵字點擊率總和
//	private String kwPriceTotal; //關鍵字價格總和
//	private String kwPriceAvgTotal; //關鍵字平均點選費用總和
//	private String kwInvClkTotal; //關鍵字無效點擊總和


	

	

}