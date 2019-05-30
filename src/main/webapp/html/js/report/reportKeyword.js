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
	showHideDataListInfo("rankAvg", "false");
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
		url : "reportKeywordAjaxTable.html",
	    data: {
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
	
	// 每筆明細列開關
	$('.icon-switchKW').on('click', function() {
		var $txtrow = $(this).closest('.txt-row');
		if ($txtrow.hasClass('close')) {
			$txtrow.removeClass('close');
			$('.table').removeClass('closeall')
		} else {
			$txtrow.addClass('close')
		}
	});

	// 全部明細列開關
	$('.icon-switchall').on('click', function() {
		if ($('.table').hasClass('closeall')) {
			$('.txt-row').removeClass('close')
		} else {
			$('.txt-row').addClass('close')
		}
		$('.table').toggleClass('closeall');
	});
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
	$("#excerptFrom").attr("action", "reportKeywordDownload.html");
	$("#excerptFrom").submit();
}

//顯示open flash
function showHighChart(){
	var widDataArray;
	var phrDataArray;
	var preDataArray;
	var dataArray;
	$.ajax({
		url : "reportKeywordAjaxChart.html",
		type : "POST",
		dataType:'json',
		async: false,
		data : {
			"startDate" : $('#startDate').val(),
			"endDate": $('#endDate').val(),
			"charType" : $('#selectChartType').val(),
			"charPic" : $('#selectChartPic').val(),
			"searchText" : $('#searchText').val(),
			"whereMap" : JSON.stringify(whereObject)
		},
		success : function(respone) {
//			console.log(respone);
			widDataArray = respone[0];
			phrDataArray = respone[1];
			preDataArray = respone[2];
			dataArray = respone[3];
		}
	});
	
	var dateArray = getDays($('#startDate').val(), $('#endDate').val());
	
	//圖表格式
	var selectPic = $("#selectChartPic").val();
	var chartPic = "";
	switch (selectPic) {
		case "lineChart":
			chartPic = "";
			break;
		case "barChart":
			chartPic = "column";
			break;
	}
	
	// 度量
	var selectType = $("#selectChartType").val();
	var titleName = "";
	var selectTypeName = "";
	var selectSuffix = "";
	var decimals = 0; // 顯示小數點後幾位數
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
			break;
	}
	
	// ---預設樣式----
    Highcharts.setOptions({
		colors : [ '#ffac12', '#6eb6ff', '#1cc900', '#313131' ],
		lang : {
			downloadPNG : '下載 PNG',
			downloadJPEG : '下載 JPEG',
			downloadPDF : '下載 PDF',
			downloadSVG : '下載 SVG',
			printChart : '列印圖表',
			exportButtonTitle : "輸出",
			printButtonTitle : "列印",
			resetZoom : "原尺寸",
			thousandsSep : ","
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
	        text: ''
	    },
	    xAxis: {
	    	categories: dateArray
	    	,crosshair: true
		},
	    yAxis: {
	        title: {
	        	text: ''
	        }
	    },
	    legend: {
            layout: 'horizontal',
            align: 'center',
            verticalAlign: 'top'
        },
	    tooltip: {
	        valueSuffix: selectSuffix,
	        valueDecimals: decimals
	    },
	    series: [{	
		    		name: '廣泛比對' + selectTypeName,
			        data: widDataArray
	    		}, {	
		    		name: '詞組比對' + selectTypeName,
			        data: phrDataArray
	    		}, {	
		    		name: '精準比對' + selectTypeName,
			        data: preDataArray
	    		}, {	
		    		name: '總' + selectTypeName,
		        	data: dataArray
	    		}],
		credits: { //右下網址
			enabled:false
		}
	});
}

/**
 * 取得開始日與結束日之間每一天陣列
 * @param startDate
 * @param endDate
 * @returns
 */
function getDays(startDate, endDate) {
	// 獲取傳入參數字串形式日期的Date型日期
	var stDate = startDate.getDate();
	var etDate = endDate.getDate();
	
	var retArr = [];
	// 獲取開始日期的年，月，日
	var yyyy = stDate.getFullYear();
	var mm = stDate.getMonth();
	var dd = stDate.getDate();
	
	// 選擇今天日期，開始結束日會相同
	if (stDate.getTime() == etDate.getTime()) {
		retArr.push(etDate.getYMD());
	} else {
		// 開始、結束日不同時，則產生每一天
		while (stDate.getTime() != etDate.getTime()) {
			stDate = new Date(yyyy, mm, dd++); // 使用dd++進行天數的自增
			retArr.push(stDate.getYMD());
		}
	}
	
//	console.log(retArr); // 或可換為return ret;
	return retArr;
}

// 給Date物件添加getYMD方法，獲取字串形式的年月日
Date.prototype.getYMD = function() {
	// 將結果放在陣列中，使用陣列的join方法返回連接起來的字串，並給不足兩位的天和月十位上補零
//	return [ this.getFullYear(), fz(this.getMonth() + 1), fz(this.getDate()) ].join("-");
	return [ fz(this.getMonth() + 1), fz(this.getDate()) ].join("/");
}
 
// 給String對象添加getDate方法，使字符串形式的日期返回為Date型的日期
String.prototype.getDate = function() {
	var strArr = this.split('-');
	return new Date(strArr[0], strArr[1] - 1, strArr[2]);
}
 
// 給月和天，不足兩位的前面補0
function fz(num) {
	if (num < 10) {
		num = "0" + num;
	}
	return num
}