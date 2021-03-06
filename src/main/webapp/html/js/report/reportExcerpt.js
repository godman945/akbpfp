var defaultPage = "1"; // 預設查詢後回到第一頁
$(document).ready(function() {

	// 日期區間datepicker
	$("#startDate").datepicker({
		buttonImage : "./html/img/icon-cal.png",
		dateFormat: "yy-mm-dd",
	   	yearRange:"-10:+10",
		minDate: "-6M",
		maxDate: 0,
		onSelect: function(dateText, inst) {
			processQueryAjax(defaultPage); // 選擇後重新查詢
		}
	});

	$("#endDate").datepicker({
		buttonImage : "./html/img/icon-cal.png",
		dateFormat : "yy-mm-dd",
		yearRange : "-10:+10",
		minDate : "-6M",
		maxDate : 0,
		onSelect: function(dateText, inst) {
			processQueryAjax(defaultPage); // 選擇後重新查詢
		}
	});

	// 選擇日期區間
	$(".selectDateRange").click(function() {
		$("#startDate").val($(this).attr("date").split(",")[0]);
		$("#endDate").val($(this).attr("date").split(",")[1]);
		processQueryAjax(defaultPage); // 選擇後重新查詢
	});

	// 選擇每頁幾筆
	$("#pageSize").change(function() {
		isRereadChart = false; // 不重新讀取圖表
		processQueryAjax(defaultPage); // 選擇後重新查詢
	});

	// 預設顯示其他欄位
    initShowHideDataListInfo();

    // 綁定事件
	initEvent();

	processKeyupQuery();
	
	// 產圖表
	showHighChart();
	
	// 選取其他圖表項目
	$("#selectChartType, #selectChartPic").change(function() {
		showHighChart(); // 重新產生圖表
	});
});

// 初始顯示其他欄位資訊 下拉選單
function initShowHideDataListInfo() {
	showHideDataListInfo("adType", "false");
	showHideDataListInfo("adOperatingRule", "false");
	showHideDataListInfo("adDate", "false");
	
	var hasPfpCodeflag = $("#hasPfpCodeflag").val();
	if (hasPfpCodeflag == "true") {
		showHideDataListInfo("convertCount", "true");
		showHideDataListInfo("convertCTR", "true");
		showHideDataListInfo("convertPriceCount", "true");
		showHideDataListInfo("convertCost", "true");
		showHideDataListInfo("convertInvestmentCost", "true");
	} else {
		showHideDataListInfo("convertCount", "false");
		showHideDataListInfo("convertCTR", "false");
		showHideDataListInfo("convertPriceCount", "false");
		showHideDataListInfo("convertCost", "false");
		showHideDataListInfo("convertInvestmentCost", "false");
	}
}

function showHideDataListInfo(dataColumnName, select) {
	// 顯示其他欄位下拉列表，勾選或取消勾選
	$("ul.menu-items li[data-column-name=" + dataColumnName + "]").attr("data-select", select);
	
	// 顯示隱藏資料列表欄位
	var dateListColumn = $(".table th[data-info-name=" + dataColumnName + "], .table td[data-info-name=" + dataColumnName + "]");
	if (select == "true") {
		dateListColumn.css("display", "");
	} else {
		dateListColumn.css("display", "none");
	}
	$('.floatingscroll').floatingScroll("update"); // 欄位顯示隱藏後重新調整scrollbar
}

/**
 * 輸入完成後等待再執行查詢
 */
function processKeyupQuery() {
	// 用 jquery.ba-dotimeout.js 套件
	$('#searchText').keyup(function() {
		$(this).doTimeout('searchText', 1000, function() {
			processQueryAjax(defaultPage);
		});
	});
}

/**
 * 處理切換頁的時候
 * @param changePageNo
 * @returns
 */
function processChangePage(changePageNo) {
	isRereadChart = false; // 不重新讀取圖表
	processQueryAjax(changePageNo);
}

/**
 * @param changePageNo 切換到哪頁
 */
function processQueryAjax(changePageNo) {
	$.ajax({
	    type : "post",
		url : "reportExcerptAjaxTable.html",
	    data: {
	    	  "viewType" : viewTypeFlag,
	    	  "adActionSeq" : adActionSeqFlag,
	    	  "adGroupSeq" : adGroupSeqFlag,
	          "startDate" : $('#startDate').val(),
	          "endDate" : $('#endDate').val(),
	          "searchText" : $('#searchText').val(),
	          "whereMap" : JSON.stringify(whereObject),
	          "sortBy" : sortBy,
	          "pageSize" : $('#pageSize').val(),
	          "page" : changePageNo
	    },
	    timeout : 30000,
	    error : function(xhr) {
			alert("系統繁忙，請稍後再試！");
		},
	    success : function(response) {
	        $('.prodtable-wrap').html(response);
	        initEvent();

	        // 重新勾選排序項目
	        $("[data-sort=" + sortBy + "]").attr("data-select", "true");
	        
	        // 重新勾選篩選項目
			$.each(whereObject, function(key, val) {
				$("[data-where=" + key + "-" + val + "]").siblings().attr("data-select", ""); // 將同一層的data-select取消選取
				$("[data-where=" + key + "-" + val + "]").attr("data-select", "true");
				
				if ($("#totalPage").val() == 0) { // 查無資料則總頁數為0,將where下拉選單值塞入td欄位
					$(".whereNoData-" + key).html($("[data-where=" + key + "-" + val + "]").html());
				}
			});
			
			// 重新調整顯示隱藏欄位
			$.each($('li[data-column-name]'), function(index, obj) {
				showHideDataListInfo($(obj).attr("data-column-name"), $(obj).attr("data-select"));
			});
			
			if (isRereadChart) {
				showHighChart(); // 重新產生圖表
			} else {
				isRereadChart = true; // 此次不重讀圖表，還原預設值
			}
			
			if (viewTypeFlag == "adGroup" && isAdGroupViewFlag == false) {
				processAdGroupView(); // 第一次進入才處理第二層相關畫面
			} else if (viewTypeFlag == "advertise" && isAdvertiseViewFlag == false) {
				processAdvertiseView(); // 第一次進入才處理第三層相關畫面
			}
			
			processViewPosition(); // 在commonReport.js
	    }
	});
}

var whereObject = new Object(); // 紀錄需篩選欄位
var sortBy = ""; // 紀錄需排序欄位
var isRereadChart = true; // 是否需要重新讀取圖表
/**
 * 綁定事件或重新綁定相關事件
 * @returns
 */
function initEvent() {
	// 篩選顯示其他欄位 下拉選單
	$(".menu-items.columnType li").unbind("click").click(function() {
		$(this).attr("data-select", !($(this).attr("data-select") == "true"));
		var dataColumnName = $(this).attr("data-column-name");
		var select = $(this).attr("data-select");
		showHideDataListInfo(dataColumnName, select);
	});
    
	// 表頭欄位排序下拉選單
	$(".sortbx-selectTH").unbind("click").click(function() {
		var $this = $(this).children(".sort-item");
		var $all = $(".sortbx-selectTH").children(".sort-item");

		if ($this.hasClass("active")) {
			$all.removeClass("active");
		} else {
			$all.removeClass("active");
			$this.addClass("active");
		}
	});
	
	// 排序事件，目前僅排序一欄
	$("[data-sort$=-DESC], [data-sort$=-ASC]").unbind("click").click(function() {
		var sortTF = !($(this).attr("data-select") == "true"); // 紀錄選擇的是否要排序

		// 將所有設定清除
		sortBy = "";
		$("[data-sort$=-DESC], [data-sort$=-ASC]").attr("data-select", "");
		
		if (sortTF) { // 有要排序，重新紀錄
			$(this).attr("data-select", sortTF);
			sortBy = $(this).attr("data-sort");
		}
		
		isRereadChart = false; // 不重新讀取圖表
		processQueryAjax($("#page").val()); // 選擇後重新查詢，目前頁數重查排序
	});
	
	// 篩選事件
	$("[data-where]").unbind("click").click(function() {
		if ($(this).attr("data-select")) { // 點選目前選擇的，則不再重複查詢
			return false;
		}
		
		$(this).siblings().attr("data-select", ""); // 將同一層的data-select取消選取
		$(this).attr("data-select", "true");
		
		var arr = $(this).attr("data-where").split("-");
		whereObject[arr[0]] = arr[1];
		processQueryAjax(defaultPage); // 選擇後重新查詢
	});
	
	processPageNumber(); // 在commonReport.js
	horisontal_scroll_listing($("#table-listing")); // 在commonReport.js
	$('.floatingscroll').floatingScroll(); // floating scrollbar
}

var showHideColumn = ""; // 紀錄產報表欄位是否顯示
/**
 * 下載報表
 * @returns
 */
function processDownloadReport() {
	$.each($('li[data-column-name]'), function(index, obj) { // 紀錄顯示隱藏欄位
		if(index == 0){
			showHideColumn = $(obj).attr("data-column-name") + "-" + $(obj).attr("data-select");
		} else{
			showHideColumn += "," + $(obj).attr("data-column-name") + "-" + $(obj).attr("data-select");			
		}
	});
	
	$("#whereMap").val(JSON.stringify(whereObject));
	$("#sortBy").val(sortBy);
	$("#showHideColumn").val(showHideColumn);
	$("#excerptFrom").attr("action", "reportExcerptDownload.html");
	$("#excerptFrom").submit();
}

//顯示open flash
function showHighChart(){
	var dataArray;
	$.ajax({
		url : "reportExcerptAjaxChart.html",
		type : "POST",
		dataType:'json',
		async: false,
		data : {
			"viewType" : viewTypeFlag,
			"adActionSeq" : adActionSeqFlag,
			"adGroupSeq" : adGroupSeqFlag,
			"startDate" : $('#startDate').val(),
			"endDate": $('#endDate').val(),
			"charType" : $('#selectChartType').val(),
			"charPic" : $('#selectChartPic').val(),
			"searchText" : $('#searchText').val(),
			"whereMap" : JSON.stringify(whereObject)
		},
		success : function(respone) {
//			console.log(respone);
			dataArray = respone;
		}
	});
	
	var startDate = $('#startDate').val();
	var dateArray = startDate.split("-");
	
	//圖表格式
	var selectPic = $("#selectChartPic").val();
	var chartPic = "";
	var fontColor = "";
	switch(selectPic){
		case "lineChart":
			chartPic = "";
			fontColor = "#fead13";
			break;
		case "barChart":
			chartPic = "column";
			fontColor = "#6eb6ff";
			break;
	}
	
	//度量
	var selectType = $("#selectChartType").val();
	var titleName = "";
	var selectTypeName = "";
	var selectSuffix = "";
	var decimals = 0;		//顯示小數點後幾位數
	switch(selectType){
		case "pv":
			titleName = "曝光數(次)";
			selectTypeName = "曝光數";
			selectSuffix = "次";
			break;
		case "click":
			titleName = "互動數(次)";
			selectTypeName = "互動數";
			selectSuffix = "次";
			break;
		case "ctr":
			titleName = "互動率(%)";
			selectTypeName = "互動率";
			selectSuffix = "%";
			decimals = 2;
			break;
		case "invClk":
			titleName = "無效點選數(次)";
			selectTypeName = "無效點選數";
			selectSuffix = "次";
			break;
		case "avgCost":
			titleName = "單次互動費用(NT$)";
			selectTypeName = "單次互動費用";
			selectSuffix = "元";
			decimals = 2;
			break;
		case "kiloCost":
			titleName = "千次曝光費用(NT$)";
			selectTypeName = "千次曝光費用";
			selectSuffix = "元";
			decimals = 2;
			break;
		case "cost":
			titleName = "費用(NT$)";
			selectTypeName = "費用";
			selectSuffix = "元";
			decimals = 3;
			break;
		case "convertCount":
			titleName = "轉換次數(次)";
			selectTypeName = "轉換次數";
			selectSuffix = "次";
			break;
		case "convertCTR":
			titleName = "轉換率(%)";
			selectTypeName = "轉換率";
			selectSuffix = "%";
			decimals = 2;
			break;
		case "convertPriceCount":
			titleName = "總轉換價值(NT$)";
			selectTypeName = "總轉換價值";
			selectSuffix = "元";
			break;
		case "convertCost":
			titleName = "平均轉換成本(NT$)";
			selectTypeName = "平均轉換成本";
			selectSuffix = "元";
			decimals = 2;
			break;
		case "convertInvestmentCost":
			titleName = "廣告投資報酬率(%)";
			selectTypeName = "廣告投資報酬率";
			selectSuffix = "%";
			decimals = 2;
			break;
	}
	
	// ---預設樣式----
  Highcharts.setOptions({
  	colors: [fontColor],
      
  	symbols:['circle'],
     	lang: {
     		months: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '11月', '12月'],
     		weekdays: ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'],
     		shortMonths: ['01/', '02/', '03/', '04/', '05/', '06/', '07/', '08/', '09/', '10/', '11/', '12/'],
     		downloadPNG: '下載 PNG',
     		downloadJPEG: '下載 JPEG',
     		downloadPDF: '下載 PDF',
     		downloadSVG: '下載 SVG',
     		printChart: '列印圖表',
     		exportButtonTitle: "輸出",
     		printButtonTitle: "列印",
     		resetZoom: "原尺寸",
     		thousandsSep: ","
     		//resetZoomTitle: "Reset,           
     	}
  });

	$('#hcharts_bx').highcharts({  
		chart: {
	        type: chartPic 
	    },	
	    title: {
	        text: titleName,
	        style: {
	        	color: '#313131',
	        	fontWeight: 'bold',
	        	fontFamily: '"微軟正黑體", Microsoft JhengHei, Arial, Helvetica, sans-serif, verdana'
	        }
	    },
	    subtitle: {
	        text: '',
	        x: -20
	    },
	    xAxis: {
	        crosshair: true,
			type: 'datetime',
			dateTimeLabelFormats:{
	            day: '%m/%d',
	            week:'%m/%d',
	            month:'%m/%d'
			}
		},
	    yAxis: {
	        title: {
	            text: '',
	            align: 'high',
	            rotation: 0,
	            offset: 0,
	            x: -15,
	            y: -20
	        },
	        plotLines: [{
	            value: 0,
	            width: 1,
	            color: '#808080'
	        }]
	    },
	    tooltip: {
	        valueSuffix: selectSuffix,
	        shared: true,
	        borderColor:'#909090',
	        borderWidth: 1,
	        valueDecimals: decimals,
	        dateTimeLabelFormats:{		
	            day:"%A, %m/%d, %Y" 
			}
	    },
	    series: [{
	        name: selectTypeName,
	        data: dataArray,
	        lineWidth: 2,
	        pointStart: Date.UTC(parseInt(dateArray[0]), parseInt(dateArray[1] -1), parseInt(dateArray[2])),
	        pointInterval: 24 * 3600 * 1000
	    }],
	    legend: { //選單
			enabled:false
		},
		exporting: { //右上打印
			//enabled:false
		},
		credits: { //右下網址
			enabled:false
		},
	});
}

var viewTypeFlag; // 顯示畫面
var adActionSeqFlag; // 廣告活動序號
var breadcrumbTrailAdActionName; // 麵包屑-廣告成效名稱
// 進入第二層
function adGroupSearch(viewType, adActionSeq, adActionName) {
	viewTypeFlag = viewType;
	adActionSeqFlag = adActionSeq;
	breadcrumbTrailAdActionName = adActionName;
	$("#searchText").val("");
	processQueryAjax(defaultPage);
}

/**
 * 處理第二層相關畫面
 * @returns
 */
var isAdGroupViewFlag = false; // 第一次進入才調整
function processAdGroupView() {
	$('.nav-title p').html('總廣告成效-分類'); // 標題調整
	// 麵包屑調整
	$('.tableform_breadcrumbs').show();
	$('#breadcrumbsAdAction').html(breadcrumbTrailAdActionName);
	$('#breadcrumbsAdGroup').hide();
	$('#searchText').attr('placeholder', '搜尋廣告分類名稱'); // 搜尋欄位調整
	// 顯示其他欄位調整
	$('.menu-items li[data-column-name="adDate"]').attr('data-column-name', 'adClkPriceType').html('計價方式');
	showHideDataListInfo("adClkPriceType", "false");
	// 下載報表用
	$('#viewType').val(viewTypeFlag);
	$('#adActionSeq').val(adActionSeqFlag);
	isAdGroupViewFlag = true;
	isAdvertiseViewFlag = false;
	
	processViewPosition(); // 在commonReport.js
}

var adGroupSeqFlag; // 廣告活動序號
var breadcrumbTrailAdGroupName; // 麵包屑-分類成效名稱
//進入第三層
function advertiseSearch(viewType, adGroupSeq, adGroupName) {
	viewTypeFlag = viewType;
	adGroupSeqFlag = adGroupSeq;
	breadcrumbTrailAdGroupName = adGroupName;
	$("#searchText").val("");
	processQueryAjax(defaultPage);
}

/**
 * 處理第三層相關畫面
 * @returns
 */
var isAdvertiseViewFlag = false; // 第一次進入才調整
function processAdvertiseView() {
	$('.nav-title p').html('總廣告成效-明細'); // 標題調整
	// 麵包屑調整
	$('#breadcrumbsAdAction').html('<a href="javascript:adGroupSearch(\'adGroup\', \'' + adActionSeqFlag + '\', \'' + breadcrumbTrailAdActionName + '\')">' + breadcrumbTrailAdActionName + '</a></span>')
	$('#breadcrumbsAdGroup').html(breadcrumbTrailAdGroupName).show();
	$('#searchText').attr('placeholder', '搜尋廣告明細名稱 (標題)'); // 搜尋欄位調整
	// 下載報表用
	$('#viewType').val(viewTypeFlag);
	$('#adGroupSeq').val(adGroupSeqFlag);
	isAdGroupViewFlag = false;
	isAdvertiseViewFlag = true;
	
	processViewPosition(); // 在commonReport.js
}