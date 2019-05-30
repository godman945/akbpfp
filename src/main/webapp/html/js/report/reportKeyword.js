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

////顯示open flash
//function showHighChart(){
//	var dataArray;
//	$.ajax({
//		url : "reportAdGroupAjaxChart.html",
//		type : "POST",
//		dataType:'json',
//		async: false,
//		data : {
//			"startDate" : $('#startDate').val(),
//			"endDate": $('#endDate').val(),
//			"charType" : $('#selectChartType').val(),
//			"charPic" : $('#selectChartPic').val(),
//			"searchText" : $('#searchText').val(),
//			"whereMap" : JSON.stringify(whereObject)
//		},
//		success : function(respone) {
////			console.log(respone);
//			dataArray = respone;
//		}
//	});
//	
//	var startDate = $('#startDate').val();
//	var dateArray = startDate.split("-");
//	
//	//圖表格式
//	var selectPic = $("#selectChartPic").val();
//	var chartPic = "";
//	var fontColor = "";
//	switch(selectPic){
//		case "lineChart":
//			chartPic = "";
//			fontColor = "#fead13";
//			break;
//		case "barChart":
//			chartPic = "column";
//			fontColor = "#6eb6ff";
//			break;
//	}
//	
//	//度量
//	var selectType = $("#selectChartType").val();
//	var titleName = "";
//	var selectTypeName = "";
//	var selectSuffix = "";
//	var decimals = 0;		//顯示小數點後幾位數
//	switch(selectType){
//		case "pv":
//			titleName = "曝光數(次)";
//			selectTypeName = "曝光數";
//			selectSuffix = "次";
//			break;
//		case "click":
//			titleName = "互動數(次)";
//			selectTypeName = "互動數";
//			selectSuffix = "次";
//			break;
//		case "ctr":
//			titleName = "互動率(%)";
//			selectTypeName = "互動率";
//			selectSuffix = "%";
//			decimals = 2;
//			break;
//		case "avgCost":
//			titleName = "單次互動費用(NT$)";
//			selectTypeName = "單次互動費用";
//			selectSuffix = "元";
//			decimals = 2;
//			break;
//		case "kiloCost":
//			titleName = "千次曝光費用(NT$)";
//			selectTypeName = "千次曝光費用";
//			selectSuffix = "元";
//			decimals = 2;
//			break;
//		case "cost":
//			titleName = "費用(NT$)";
//			selectTypeName = "費用";
//			selectSuffix = "元";
//			decimals = 3;
//			break;
//		case "convertCount":
//			titleName = "轉換數(次)";
//			selectTypeName = "轉換數";
//			selectSuffix = "次";
//			break;
//		case "convertCTR":
//			titleName = "轉換率(%)";
//			selectTypeName = "轉換率";
//			selectSuffix = "%";
//			decimals = 2;
//			break;
//		case "convertPriceCount":
//			titleName = "總轉換價值(NT$)";
//			selectTypeName = "總轉換價值";
//			selectSuffix = "元";
//			break;
//		case "convertCost":
//			titleName = "平均轉換成本(NT$)";
//			selectTypeName = "平均轉換成本";
//			selectSuffix = "元";
//			decimals = 2;
//			break;
//		case "convertInvestmentCost":
//			titleName = "廣告投資報酬率(%)";
//			selectTypeName = "廣告投資報酬率";
//			selectSuffix = "%";
//			decimals = 2;
//			break;
//	}
//	
//	// ---預設樣式----
//  Highcharts.setOptions({
//  	colors: [fontColor],
//      
//  	symbols:['circle'],
//     	lang: {
//     		months: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '11月', '12月'],
//     		weekdays: ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'],
//     		shortMonths: ['01/', '02/', '03/', '04/', '05/', '06/', '07/', '08/', '09/', '10/', '11/', '12/'],
//     		downloadPNG: '下載 PNG',
//     		downloadJPEG: '下載 JPEG',
//     		downloadPDF: '下載 PDF',
//     		downloadSVG: '下載 SVG',
//     		printChart: '列印圖表',
//     		exportButtonTitle: "輸出",
//     		printButtonTitle: "列印",
//     		resetZoom: "原尺寸",
//     		thousandsSep: ","
//     		//resetZoomTitle: "Reset,           
//     	}
//  });
//
//	$('#hcharts_bx').highcharts({  
//		chart: {
//	        type: chartPic 
//	    },	
//	    title: {
//	        text: titleName,
//	        style: {
//	        	color: '#313131',
//	        	fontWeight: 'bold',
//	        	fontFamily: '"微軟正黑體", Microsoft JhengHei, Arial, Helvetica, sans-serif, verdana'
//	        }
//	    },
//	    subtitle: {
//	        text: '',
//	        x: -20
//	    },
//	    xAxis: {
//	        crosshair: true,
//			type: 'datetime',
//			dateTimeLabelFormats:{
//	            day: '%m/%d',
//	            week:'%m/%d',
//	            month:'%m/%d'
//			}
//		},
//	    yAxis: {
//	        title: {
//	            text: '',
//	            align: 'high',
//	            rotation: 0,
//	            offset: 0,
//	            x: -15,
//	            y: -20
//	        },
//	        plotLines: [{
//	            value: 0,
//	            width: 1,
//	            color: '#808080'
//	        }]
//	    },
//	    tooltip: {
//	        valueSuffix: selectSuffix,
//	        shared: true,
//	        borderColor:'#909090',
//	        borderWidth: 1,
//	        valueDecimals: decimals,
//	        dateTimeLabelFormats:{		
//	            day:"%A, %m/%d, %Y" 
//			}
//	    },
//	    series: [{
//	        name: selectTypeName,
//	        data: dataArray,
//	        lineWidth: 2,
//	        pointStart: Date.UTC(parseInt(dateArray[0]), parseInt(dateArray[1] -1), parseInt(dateArray[2])),
//	        pointInterval: 24 * 3600 * 1000
//	    }],
//	    legend: { //選單
//			enabled:false
//		},
//		exporting: { //右上打印
//			//enabled:false
//		},
//		credits: { //右下網址
//			enabled:false
//		},
//	});
//}


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
			console.log(widDataArray);
			console.log(phrDataArray);
			console.log(preDataArray);
			console.log(dataArray);
		}
	});
	
//	var startDate = $('#startDate').val();
//	var dateArray = startDate.split("-");
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
//			type: 'datetime',
//			dateTimeLabelFormats:{
//	            day: '%m/%d',
//	            week:'%m/%d',
//	            month:'%m/%d'
//			}
	    	categories: dateArray
	    	,crosshair: true
	    	
//	    	categories: ['11/01', '11/08', '11/15', '11/22', '11/29', '12/06', '12/13', '12/20'],
//            crosshair: true
		},
	    yAxis: {
	        title: {
	        	text: ''
	        		
//	            text: '',
//	            align: 'high',
//	            rotation: 0,
//	            offset: 0,
//	            x: -15,
//	            y: -20
	        }
//			,plotLines: [{
//	            value: 0,
//	            width: 1,
//	            color: '#808080'
//	        }]
	    },
	    
	    legend: {
            layout: 'horizontal',
            align: 'center',
            verticalAlign: 'top'
        },
	    
	    tooltip: {
	        valueSuffix: selectSuffix,
//	        shared: true,
//	        borderColor:'#909090',
//	        borderWidth: 1,
	        valueDecimals: decimals
//	        dateTimeLabelFormats:{		
//	            day:"%A, %m/%d, %Y" 
//			}
	    },
	    
	    series: [{	
	    		name: '廣泛比對' + selectTypeName,
		        data: widDataArray
//		        lineWidth: 2,
//		        color: "#FFFF00",
//		        pointStart: Date.UTC(parseInt(dateArray[0]), parseInt(dateArray[1] -1), parseInt(dateArray[2])),
//		        pointInterval: 24 * 3600 * 1000
	    }, {	
	    		name: '詞組比對' + selectTypeName,
		        data: phrDataArray
//		        lineWidth: 2,
//		        color: "#00FF00",
//		        pointStart: Date.UTC(parseInt(dateArray[0]), parseInt(dateArray[1] -1), parseInt(dateArray[2])),
//		        pointInterval: 24 * 3600 * 1000
	    }, {	
	    		name: '精準比對' + selectTypeName,
		        data: preDataArray
//		        lineWidth: 2,
//		        color: "#CC00FF",
//		        pointStart: Date.UTC(parseInt(dateArray[0]), parseInt(dateArray[1] -1), parseInt(dateArray[2])),
//		        pointInterval: 24 * 3600 * 1000
	    }, {	
	    		name: '總' + selectTypeName,
	        	data: dataArray
//	        	lineWidth: 2,
//	        	pointStart: Date.UTC(parseInt(dateArray[0]), parseInt(dateArray[1] -1), parseInt(dateArray[2])),
//	        	pointInterval: 24 * 3600 * 1000
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
	
	console.log(retArr); // 或可換為return ret;
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
 
//給月和天，不足兩位的前面補0
function fz(num) {
	if (num < 10) {
		num = "0" + num;
	}
	return num
}





//var highChartActionPath="reportKeywordAjaxChart.html";
//var reportAjaxActionPath="reportKeywordAjaxTable.html";
////一開始執行
//$(function(){
//
//    //flash chart
//    showHighChart();
//
//    //flash data
//    ready();
//
//    //日期區間選擇
//    $('#seDateSelect').click(function(){
//    
//        $.blockUI({
//            message: $('#seDateSelectDialog'),
//            css: {
//                width: '300px'
//            },
//            focusInput: false
//        });
//        
//    });
//	
//	//日期區間選擇 OK
//    $('#DateSelectOk').click(function(){
//	
//	    var selectValue = $('#selectRange').val();
//
//	    var startValue;
//	    var endValue;
//		if (selectValue == "self") {
//			startValue = $('#startDate').val();
//			endValue = $('#endDate').val();
//		} else {
//			startValue = $('#selectRange').val().split(",")[0];
//			endValue = $('#selectRange').val().split(",")[1];
//		}
//
//		var startDate = Date.parse(startValue.replace(/\-/g,'/'));
//		var endDate = Date.parse(endValue.replace(/\-/g,'/'));
//
//		if (startDate > endDate) {
//			alert("結束日期必須晚於開始日期！");
//			return false;
//		} else {
//			$('#fstartDate').val(startValue);
//			$('#fendDate').val(endValue);
//		}
//
//		$('#formPage').val("1");
//
//		$.unblockUI();
//
//        ajaxFormSubmit();
//
//    });
//	    
//    //日期區間選擇 cancel
//    $('#DateSelectCancel').click(function(){
//        $.unblockUI();
//        return false;
//    });
//    
//	//日期區間datepicker
//    $("#startDate").datepicker({
//        dateFormat: "yy-mm-dd",
//       	yearRange:"-10:+10",
//        minDate: "-6M",
//        maxDate: 0
//    });
//	
//    $("#endDate").datepicker({
//        dateFormat: "yy-mm-dd",
//       	yearRange:"-10:+10",
//        minDate: "-6M",
//        maxDate: 0
//    });
//	
//	 //自訂欄位OK
//    $('#optionOk').click(function(){
//
//        $('#rightselect option').attr("selected", "true");
//        var rvalue = $('#rightselect').val();
//        if (rvalue == null) {
//            rvalue = "";
//        }
//        document.excerptFrom.optionSelect.value = rvalue;
//        
//        $('#leftselect option').attr("selected", "true");
//        var lvalue = $('#leftselect').val();
//        if (lvalue == null) {
//            lvalue = "";
//        }
//        document.excerptFrom.optionNotSelect.value = lvalue;
//
//        $.unblockUI();
//
//        ajaxFormSubmit();
// 
//        return false;
//    });
//    
//    //自訂欄位cancel
//    $('#optionCancel').click(function(){
//        $.unblockUI();
//        return false;
//    });
//
//    //Chart開關
//    $('#aReportChart').click(function(){
//
//        var charText = $('#aReportChart').html();
//        
//        if (charText == "關閉") {
//            $('#reportChart').hide();
//            $('#aReportChart').html("顯示");
//        } else {
//            $('#reportChart').show();
//            $('#aReportChart').html("關閉");
//        }
//    });
//    
//    //下載報表
//    var downlaod = $('#download');
//    downlaod.bind('click', function(e){
//        $('#excerptFrom').val("yes");
//        $('#excerptFrom').submit();
//    });
//    
//    //flash chart reload
//    $('#reloadFlash').click(function(){
//       showHighChart();
//    });
//    
//});
//
//
////ajax id  重新榜定
//function ready(){
//    
//	//sort table plugin
//    $.tablesorter.defaults.widgets = ['zebra'];
//    //$.tablesorter.defaults.sortList = [[0,0]];
//    //$("#excerptTable").tablesorter();
//
//    $("#excerptTable").tablesorter({
//        headers: {
//    	    0 : { sorter: false },
//    	    5 : {sorter:false},
//			6 : {sorter:false},
//			7 : {sorter:false},
//			8 : {sorter:false},
//			9 : {sorter:false},
//			10 : {sorter:false},
//			11 : {sorter:false},
//			12 : {sorter:false},
//			13 : {sorter:false},
//			14 : {sorter:false},
//			15 : { sorter: 'fancyNumber' },
//			16 : { sorter: 'fancyNumber' },
//			17 : { sorter: 'fancyNumber' },
//			18 : { sorter: 'fancyNumber' },
//			19 : { sorter: 'fancyNumber' },
//			20 : { sorter: 'fancyNumber' },
//			21 : { sorter: 'fancyNumber' },
//			22 : { sorter: 'fancyNumber' },
//			23 : { sorter: 'fancyNumber' },
//			24 : { sorter: 'fancyNumber' },
//			25 : { sorter: 'fancyNumber' },
//			26 : { sorter: 'fancyNumber' },
//			27 : { sorter: 'rangesort' },
//			28 : { sorter: 'rangesort' },
//			29 : { sorter: 'rangesort' },
//			30 : { sorter: 'rangesort' },
//			31 : { sorter: 'rangesort' },
//			32 : { sorter: 'rangesort' },
//			33 : { sorter: 'rangesort' },
//			34 : { sorter: 'rangesort' },
//			35 : { sorter: 'fancyNumber' },
//			36 : { sorter: 'fancyNumber' },
//			37 : { sorter: 'fancyNumber' }
//        }
//    });
//	
//	 //日期區間內容
//    $('#IT_dateRange').attr("value", $('#fstartDate').val() + "~" + $('#fendDate').val());
//	
//	
//	//自訂欄位點擊
//    $('#fieldSelect').click(function(){
//        $.blockUI({
//            message: $('#optionSelectDialog'),
//            css: {
//                width: '520px'
//            }
//        });
//    });
//    
//    //每頁顯示數量選擇
//    $("#pageSizeSelect").change(function(){ //事件發生
//        document.excerptFrom.page.value = "1";
//        document.excerptFrom.pageSize.value = $("#pageSizeSelect").val();
//        //document.excerptFrom.submit();
//        ajaxFormSubmit();
//    });
//    
//    
//	//每頁顯示數量正確數量顯示判斷
//    $("#pageSizeSelect").children().each(function(){
//        var pageSize = document.excerptFrom.pageSize.value;
//        if ($(this).text() == pageSize) {
//            $(this).attr("selected", "true"); //或是給selected也可
//        }
//    });
//    
//    //pageButton 第一頁
//    var fpage = $('#fpage');
//    fpage.bind('click', function(e){
//    
//        document.excerptFrom.page.value = "1";
//        //document.excerptFrom.submit();
//        ajaxFormSubmit();
//    });
//    
//    
//	//pageButton 最後一頁
//    var epage = $('#epage');
//    epage.bind('click', function(e){
//       
//        document.excerptFrom.page.value = document.excerptFrom.totalPage.value;
//        //document.excerptFrom.submit();
//        ajaxFormSubmit();
//    });
//    
//    
//    //pageButton 上一頁
//    var ppage = $('#ppage');
//    ppage.bind('click', function(e){
//        var page = parseInt($('#formPage').val());
//        page = page - 1;
//        if (page < 1) {
//            page = 1;
//            
//        }
//        
//        $('#formPage').val(page);
//        //document.excerptFrom.submit();
//        ajaxFormSubmit();
//    });
//    
//    
//    
//    //pageButton 下一頁
//    var npage = $('#npage');
//    npage.bind('click', function(e){
//    
//    
//        var page = parseInt($('#formPage').val());
//        var totalPage = $('#ftotalPage').val();
//        
//        
//        page = page + 1;
//        if (page > totalPage) {
//            page = totalPage;
//        }
//        
//        //alert("pagea="+$('#fpage').val());
//        
//        $('#formPage').val(page);
//        
//        //document.excerptFrom.submit();
//        ajaxFormSubmit();
//    });
//    
//
//    //pageButton 第一頁不顯示檢查
//    if (document.excerptFrom.page.value == 1) {
//		$("#fpage").attr("src", $('#contentPath').val() + "page_first_disable.gif").css("cursor", "pointer");
//		$("#ppage").attr("src", $('#contentPath').val() + "page_pre_disable.gif").css("cursor", "pointer");
//	}
//    
//
//    //pageButton 最後頁顯示檢查
//    if (document.excerptFrom.page.value == document.excerptFrom.totalPage.value) {
//        $("#epage").attr("src", $('#contentPath').val() + "page_end_disable.gif").css("cursor", "pointer");
//		$("#npage").attr("src", $('#contentPath').val() + "page_next_disable.gif").css("cursor", "pointer");
//    }
//	
//	
//	//totalPage =0 頁數功能取消	
//	if($('#ftotalPage').val()=="0"){
//       $("#fpage").attr("src", $('#contentPath').val() + "page_first_disable.gif").css("cursor", "pointer");
//	   $("#ppage").attr("src", $('#contentPath').val() + "page_pre_disable.gif").css("cursor", "pointer");
//	   $("#epage").attr("src", $('#contentPath').val() + "page_end_disable.gif").css("cursor", "pointer");
//	   $("#npage").attr("src", $('#contentPath').val() + "page_next_disable.gif").css("cursor", "pointer");
//	}
//	
//    
//    
//    //Search 搜尋 
//    //init select 指定
//    
//    var adPvclkDevice = document.excerptFrom.adPvclkDevice.value;
//    var adType = document.excerptFrom.adType.value;
//    var adSearchWay = document.excerptFrom.adSearchWay.value;
//    var searchText = document.excerptFrom.searchText.value;
//    //var adShowWay = document.excerptFrom.adShowWay.value;
//    
//    
//    $("#searchText").attr("value", searchText);
//    
//    $("#adType").children().each(function(){
//        if ($(this).val() == adType) {
//            //jQuery給法
//            $(this).attr("selected", "true"); //或是給selected也可
//        }
//    });
//
//	$("#adPvclkDevice").children().each(function(){
//		if ($(this).val() == adPvclkDevice) {
//			//jQuery給法
//			$(this).attr("selected", "true"); //或是給selected也可
//		}
//	});
//    
//    $("#adSearchWay").children().each(function(){
//        if ($(this).val() == adSearchWay) {
//            //jQuery給法
//            $(this).attr("selected", "true"); //或是給selected也可
//        }
//    });
//
//    /*
//    $("#adShowWay").children().each(function(){
//        if ($(this).val() == adShowWay) {
//            //jQuery給法
//            $(this).attr("selected", "true"); //或是給selected也可
//        }
//    });
//    */
//    
//    //搜尋動作 Do
//    $('#btnSearchDo').click(function(){
//        searchDo();
//        //document.excerptFrom.submit();
//        ajaxFormSubmit();
//        
//    });
//    
//    $("#adPvclkDevice, #adSearchWay").change(function(){
//    	searchDo();
//    	ajaxFormSubmit();
//   });
//    
//    //重置 result
//    $('#btnSearchReset').click(function(){
//        serachReset();
//        searchDo();
//    	ajaxFormSubmit();
//        
//    });
//    
//    
//    //活動標籤
//    $('#stepActivityId').click(function(){
//        serachReset();
//        searchDo();
//        //document.excerptFrom.submit();
//        ajaxFormSubmit();
//    });
//    
//    //群組標籤
//    $('#stepGroupId').click(function(){
//        //alert("aa");
//        serachReset();
//        document.excerptFrom.adType.value = "adtype_group";
//        document.excerptFrom.searchId.value = "";
//        //searchDo();
//        //document.excerptFrom.submit();
//        ajaxFormSubmit();
//    });
//    
//}
//
//
////單一 ajax table a href call ad search
//function adIdSearch(adType, adId){
//    document.excerptFrom.searchId.value = adId;
//    document.excerptFrom.adType.value = adType;
//    //document.excerptFrom.submit();
//    ajaxFormSubmit();
//}
//
////顯示open flash
//function showHighChart(){
//	var widDataArray;
//	var phrDataArray;
//	var preDataArray;
//	var dataArray;
//	$.ajax({
//		url : highChartActionPath,
//		type : "POST",
//		dataType:'json',
//		async: false,
//		data : {
//			"startDate" : $('#fstartDate').val(),
//			"endDate": $('#fendDate').val(),
//			"adPvclkDevice" : $('#fadPvclkDevice').val(),
//			"adType" : $('#fadType').val(),
//			"adSearchWay" : $('#fadSearchWay').val(),
//			"adShowWay" : $('#fadShowWay').val(),
//			"charPic" : $('#selectChartPic').val(),
//			"charType" : $('#selectChartType').val(),
//			"searchId" : $('#fsearchId').val(),
//			"searchText" : $('#searchText').val()
//		},
//		success : function(respone) {
//			console.log(respone);
//			widDataArray = respone[0];
//			phrDataArray = respone[1];
//			preDataArray = respone[2];
//			dataArray = respone[3];
//		}
//	});
//	
//	var startDate = $('#fstartDate').val();
//	var dateArray = startDate.split("-");
//	
//	//圖表格式
//	var selectPic = $("#selectChartPic").val();
//	var chartPic = "";
//	var fontColor = "#ff5353";
//	switch(selectPic){
//		case "lineChart":
//			chartPic = "";
//			fontColor = "#ff5353";
//			break;
//		case "barChart":
//			chartPic = "column";
//			fontColor = "#519ae0";
//			break;
//	}
//	
//	//度量
//	var selectType = $("#selectChartType").val();
//	var titleName = "";
//	var selectTypeName = "";
//	var selectSuffix = "";
//	var decimals = 0;		//顯示小數點後幾位數
//	switch(selectType){
//		case "pv":
//			titleName = "曝光數(次)";
//			selectTypeName = "曝光數";
//			selectSuffix = "次";
//			break;
//		case "ctr":
//			titleName = "點選率(%)";
//			selectTypeName = "點選率";
//			selectSuffix = "%";
//			decimals = 2;
//			break;
//		case "click":
//			titleName = "點選次數(次)";
//			selectTypeName = "點選次數";
//			selectSuffix = "次";
//			break;
//		case "invalid":
//			titleName = "無效點選數(次)";
//			selectTypeName = "無效點選數";
//			selectSuffix = "次";
//			break;
//		case "avgCost":
//			titleName = "平均點選費用(NT$)";
//			selectTypeName = "平均點選費用";
//			selectSuffix = "元";
//			decimals = 2;
//			break;
//		case "cost":
//			titleName = "費用(NT$)";
//			selectTypeName = "費用";
//			selectSuffix = "元";
//			break;
//	}
//	
//	// ---預設樣式----
//    Highcharts.setOptions({
//    	colors: [fontColor],
//        
//    	symbols:['circle'],
//       	lang: {
//       		months: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '11月', '12月'],
//       		weekdays: ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'],
//       		shortMonths: ['01/', '02/', '03/', '04/', '05/', '06/', '07/', '08/', '09/', '10/', '11/', '12/'],
//       		downloadPNG: '下載 PNG',
//       		downloadJPEG: '下載 JPEG',
//       		downloadPDF: '下載 PDF',
//       		downloadSVG: '下載 SVG',
//       		printChart: '列印圖表',
//       		exportButtonTitle: "輸出",
//       		printButtonTitle: "列印",
//       		resetZoom: "原尺寸",
//       		thousandsSep: ","
//       		//resetZoomTitle: "Reset,           
//       	}
//    });
//
//	$('#hcharts_bx').highcharts({  
//		chart: {
//	        type: chartPic 
//	    },	
//	    
//	    title: {
//	        text: titleName,
//	        style: {
//	        	color: fontColor,
//	        	fontWeight: 'bold',
//	        	fontFamily: '"微軟正黑體", Microsoft JhengHei, Arial, Helvetica, sans-serif, verdana'
//	        	//fontSize:'11px'
//	        	
//	        }
//	        //x: -20 //center
//	    },
//	    subtitle: {
//	        text: '',
//	        x: -20
//	    },
//	    
//	    xAxis: {
//	        crosshair: true,
//			type: 'datetime',
//			dateTimeLabelFormats:{
//				
//	            day: '%m/%d',
//	            week:'%m/%d',
//	            month:'%m/%d'
//	            
//			}
//		},
//	    yAxis: {
//	        title: {
//	            text: '',
//	            align: 'high',
//	            rotation: 0,
//	            offset: 0,
//	            x: -15,
//	            y: -20
//	        },
//	        plotLines: [{
//	            value: 0,
//	            width: 1,
//	            color: '#808080'
//	        }]
//	    },
//	    tooltip: {
//	        valueSuffix: selectSuffix,
//	        shared: true,
//	        borderColor:'#909090',
//	        borderWidth: 1,
//	        valueDecimals: decimals,
//	        dateTimeLabelFormats:{		
//	            day:"%A, %m/%d, %Y" 
//			}
//	    },
//	    
//	    series: [
//	        {	name: "廣泛比對" + selectTypeName,
//		        data: widDataArray,
//		        lineWidth: 2,
//		        color: "#FFFF00",
//		        pointStart: Date.UTC(parseInt(dateArray[0]), parseInt(dateArray[1] -1), parseInt(dateArray[2])),
//		        pointInterval: 24 * 3600 * 1000},
//	        {	name: "詞組比對" + selectTypeName,
//		        data: phrDataArray,
//		        lineWidth: 2,
//		        color: "#00FF00",
//		        pointStart: Date.UTC(parseInt(dateArray[0]), parseInt(dateArray[1] -1), parseInt(dateArray[2])),
//		        pointInterval: 24 * 3600 * 1000},
//	        {	name: "精準比對" + selectTypeName,
//		        data: preDataArray,
//		        lineWidth: 2,
//		        color: "#CC00FF",
//		        pointStart: Date.UTC(parseInt(dateArray[0]), parseInt(dateArray[1] -1), parseInt(dateArray[2])),
//		        pointInterval: 24 * 3600 * 1000},
//	        {	name: "總" + selectTypeName,
//	        	data: dataArray,
//	        	lineWidth: 2,
//	        	pointStart: Date.UTC(parseInt(dateArray[0]), parseInt(dateArray[1] -1), parseInt(dateArray[2])),
//	        	pointInterval: 24 * 3600 * 1000}
//	    ],
//	    legend: { //選單
//			enabled:true
//		},
//		exporting: { //右上打印
//			//enabled:false
//		},
//		credits: { //右下網址
//			enabled:false
//		},
//	});
//}
//
//
//
//function initJsonData(){
//
//    json_data = {
//        "page": $('#formPage').val(),
//        "pageSize": $('#fpageSize').val(),
//        "totalPage": $('#ftotalPage').val(),
//        "optionSelect": $('#foptionSelect').val(),
//        "optionNotSelect": $('#foptionNotSelect').val(),
//        "startDate": $('#fstartDate').val(),
//        "endDate": $('#fendDate').val(),
//        "adPvclkDevice": $('#fadPvclkDevice').val(),
//        "adType": $('#fadType').val(),
//        "adSearchWay": $('#fadSearchWay').val(),
//        //"adShowWay": $('#fadShowWay').val(),
//        "adShowWay": "",
//        "searchText": $('#fsearchText').val(),
//        "searchId": $('#fsearchId').val(),
//        "downloadFlag": $('#downloadFlag').val()
//    };
//}
//
//
//
//function ajaxFormSubmit(){
//
//
//  
//    $('#downloadFlag').val("no");
//    initJsonData();
//    
//    
//    $('#reportTableOut').block({
//        message: "<img src='html/img/LoadingWait.gif' />",
//        
//        css: {
//            padding: 0,
//            margin: 0,
//            width: '50%',
//            top: '40%',
//            left: '35%',
//            textAlign: 'center',
//            color: '#000',
//            border: '3px solid #aaa',
//            backgroundColor: '#fff',
//            cursor: 'wait'
//        }
//    });
//    
//    
//    $.ajax({
//    
//        type: "post",
//        url: reportAjaxActionPath,
//        data: json_data,
//        timeout: 30000,
//        
//        error: function(xhr){
//            alert('Ajax request 發生錯誤');
//        },
//        
//        success: function(response){
//        
//            $('#reportTable').html(response);
//            
//            ready();
//            
//        }
//        
//    });
//    
//	
//    $('#reportTableOut').unblock();
//	
//	showHighChart();
//    
//}
//
////搜尋執行
//function searchDo(){
//    document.excerptFrom.adPvclkDevice.value = $('#adPvclkDevice').val();
//    document.excerptFrom.adType.value = $('#adType').val();
//    document.excerptFrom.adSearchWay.value = $('#adSearchWay').val();
//    document.excerptFrom.searchText.value = $('#searchText').val();
//    //document.excerptFrom.adShowWay.value = $('#adShowWay').val();
//    document.excerptFrom.searchId.value = "";
//    document.excerptFrom.formPage.value = "1";
//}
//
//
////搜尋 search 
//function serachReset(){
//
//
//    $("#searchText").attr("value", "");
//    
//    $("#adPvclkDevice").children().each(function(){
//        if ($(this).val() == "") {
//            //jQuery給法
//            $(this).attr("selected", "true"); //或是給selected也可
//        }
//    });
//    
//    $("#adType").children().each(function(){
//        if ($(this).val() == "adtype_activity") {
//            //jQuery給法
//            $(this).attr("selected", "true"); //或是給selected也可
//        }
//    });
//    
//    $("#adSearchWay").children().each(function(){
//        if ($(this).val() == "adsearch_include") {
//            //jQuery給法
//            $(this).attr("selected", "true"); //或是給selected也可
//        }
//    });
//    /*
//    $("#adShowWay").children().each(function(){
//        if ($(this).val() == "adshow_all") {
//            //jQuery給法
//            $(this).attr("selected", "true"); //或是給selected也可
//        }
//    });
//    */
//    document.excerptFrom.searchId.value = "";
//}
//
////明細按鈕(展開/隱藏)
//function toggleTd(tdClass){
//	var number = $("." + tdClass + "Th").attr("colspan");
//	if(number == 1){
//		$("." + tdClass + "Button").val("隱藏");
//		$("." + tdClass + "Th").attr("colspan","4");
//	} else {
//		$("." + tdClass + "Button").val("展開");
//		$("." + tdClass + "Th").attr("colspan","1");
//	}
//	
//	$("." + tdClass).toggle();
//}
