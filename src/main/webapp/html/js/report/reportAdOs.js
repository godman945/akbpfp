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
	$("#selectChartType").change(function() {
		showHighChart(); // 重新產生圖表
	});
});

// 初始顯示其他欄位資訊 下拉選單
function initShowHideDataListInfo() {
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
		url : "reportAdOsAjaxTable.html",
	    data: {
	          "startDate" : $('#startDate').val(),
	          "endDate" : $('#endDate').val(),
	          "searchText" : $('#searchText').val(),
//	          "whereMap" : JSON.stringify(whereObject),
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
	        
//	        // 重新勾選篩選項目
//			$.each(whereObject, function(key, val) {
//				$("[data-where=" + key + "-" + val + "]").siblings().attr("data-select", ""); // 將同一層的data-select取消選取
//				$("[data-where=" + key + "-" + val + "]").attr("data-select", "true");
//			});
			
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

//var whereObject = new Object(); // 紀錄需篩選欄位
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
		
//		isRereadChart = false; // 不重新讀取圖表
		processQueryAjax($("#page").val()); // 選擇後重新查詢，目前頁數重查排序
	});
	
//	// 篩選事件
//	$("[data-where]").unbind("click").click(function() {
//		$(this).siblings().attr("data-select", ""); // 將同一層的data-select取消選取
//		$(this).attr("data-select", "true");
//		
//		var arr = $(this).attr("data-where").split("-");
//		whereObject[arr[0]] = arr[1];
//		processQueryAjax(defaultPage); // 選擇後重新查詢
//	});
	
	processPageNumber();
	
	horisontal_scroll_listing($("#table-listing"));
}

/**
 * 處理頁碼按鈕事件
 */
function processPageNumber() {
	var pageNo = $("#page").val(); // 目前第幾頁
	var pageCount = $("#totalPage").val(); // 共幾頁(總頁數)
	
	var currentlyStartPage; // 目前開始頁碼
	var currentlyEndPage; // 目前結束頁碼
	var top10Pages; // 上10頁開始頁碼
	if (parseInt(pageNo) % 10 == 0) { // 目前選擇頁數為10 20 30等10位數的時候處理
		currentlyStartPage = parseInt(pageNo % 10) * 10 + (pageNo - 10) + 1;
		currentlyEndPage = parseInt(pageNo % 10) * 10 + (pageNo - 10) + 10;
		top10Pages = parseInt(parseInt(pageNo / 10) * 10 - 19);
	} else {
		currentlyStartPage = parseInt(pageNo / 10) * 10 + 1;
		currentlyEndPage = parseInt(pageNo / 10) * 10 + 10;
		top10Pages = parseInt(parseInt(pageNo / 10) * 10 - 9);
	}
	
	var tempHtml = "";
	if (pageCount <= 10) { // 總頁數10頁(含)以下
		tempHtml += "<span class=\"pagination-buttongroup\">";
		for (var i = 1; i <= pageCount; i++) {
			if (i == pageNo) { // 目前頁數不帶換頁事件
				tempHtml += "<a data-num=\"" + i + "\" class=\"pagination-button active\" href=\"javascript:void(0);\"></a>";
			} else {
				tempHtml += "<a data-num=\"" + i + "\" class=\"pagination-button\" href=\"javascript:processChangePage('" + i + "');\"></a>";
			}
		}
		tempHtml += "</span>";
	} else {
		tempHtml += "<li class=\"txt-cell txt-left\">";
		tempHtml += "  <a data-num=\"1\" class=\"pagination-button left\" href=\"javascript:processChangePage('1');\"></a>";
		tempHtml += "</li>";
		tempHtml += "<li class=\"txt-cell\">";
		
		if (pageNo > 10) { // 當前頁超過第10頁才顯示上10頁按鈕
		tempHtml += "  <a class=\"pagination-button prev\" href=\"javascript:processChangePage('" + top10Pages + "');\" title=\"上10頁\"></a>";
		}
		
		tempHtml += "  <span class=\"pagination-buttongroup\">";

		for (var i = currentlyStartPage; i <= currentlyEndPage; i++) {
			if (i > pageCount) { // 超過總頁數時離開迴圈
				break;
			} else if (i == pageNo) { // 目前頁數不帶換頁事件
				tempHtml += "  <a data-num=\" " + i + " \" class=\"pagination-button active\" href=\"javascript:void(0);\"></a>";
			}else{
				tempHtml += "  <a data-num=\" " + i + " \" class=\"pagination-button\" href=\"javascript:processChangePage('" + i + "');\"></a>";
			}
		}
		tempHtml += "  </span>";
 
		if (pageNo <= Math.ceil(pageCount / 10) * 10 - 10) { // 總頁數/10  後無條件進位 *10 再-10為當前頁小於此數字才能出現下10頁按鈕
			tempHtml += "  <a class=\"pagination-button next\" href=\"javascript:processChangePage('" + parseInt(Math.ceil(pageNo / 10) * 10 + 1) + "');\" title=\"下10頁\"></a>";
		}
		
		tempHtml += "</li>";
		tempHtml += "<li class=\"txt-cell txt-right\">";
		tempHtml += "  <a data-num=\"" + pageCount + "\" class=\"pagination-button right\" href=\"javascript:processChangePage('" + pageCount + "');\"></a>";
		tempHtml += "</li>";
	}
	$(".pagination-box").html(tempHtml);
}

/**
 * 調整查詢結果列表畫面(art提供)
 * @param listing_obj
 * @returns
 */
function horisontal_scroll_listing(listing_obj) {
	if ($("#totalPage").val() == 0) { // 查無資料則總頁數為0，查無資料不調整畫面
		return false;
	}
	
	// get table object
	table_obj = $('.table', listing_obj);
	//get count fixed collumns params
	count_fixed_collumns = table_obj.attr('data-fixed-columns');
	if (count_fixed_collumns > 0) {
		// get wrapper object
		wrapper_obj           = $('.table-scrollable', listing_obj);
		wrapper_left_margin   = 0;
		table_collumns_width  = new Array();
		table_collumns_margin = new Array();
		
		// calculate wrapper margin and fixed column width
		$('th', table_obj).each(function(index) {
			if (index < count_fixed_collumns) {
				wrapper_left_margin        += $(this).outerWidth();
				table_collumns_width[index] = $(this).outerWidth();
			}
		})
		
		// calcualte margin for each column
		$.each(table_collumns_width, function(key, value) {
			if (key == 0) {
				table_collumns_margin[key] = wrapper_left_margin;
			} else {
				next_margin = 0;
				$.each(table_collumns_width, function(key_next, value_next) {
					if (key_next < key) {
						next_margin += value_next;
					}
				});
				table_collumns_margin[key] = wrapper_left_margin - next_margin;
			}
		});
		
		// set wrapper margin
		if (wrapper_left_margin > 0) {
			wrapper_obj.css('cssText', 'margin-left:' + wrapper_left_margin + 'px !important; width: auto');
		}
		
		// set position for fixed columns
		$('tr', table_obj).each(function() {
			// get current row height
			current_row_height = $(this).outerHeight();
			$('th,td', $(this)).each(function(index) {
				// set row height for all cells
				$(this).css('height', current_row_height);
				// set position
				if (index < count_fixed_collumns) {
					$(this).css({
						'position' : 'absolute',
						'margin-left' : '-' + table_collumns_margin[index] + 'px', 'width' : table_collumns_width[index]
					})
					$(this).addClass('table-fixed-cell');
				}
			})
		})  
	}
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
	
//	$("#whereMap").val(JSON.stringify(whereObject));
	$("#sortBy").val(sortBy);
	$("#showHideColumn").val(showHideColumn);
	$("#excerptFrom").attr("action", "reportAdOsDownload.html");
	$("#excerptFrom").submit();
}

//顯示open chart
function showHighChart() {
	var dataArray; // 資料列
	var xCategoriesArr; // x軸的分類
	
	$.ajax({
		url : "reportAdOsAjaxChart.html",
		type : "POST",
		dataType:'json',
		async: false,
		data : {
			"startDate" : $('#startDate').val(),
			"endDate": $('#endDate').val(),
			"charType" : $('#selectChartType').val(),
			"searchText" : $('#searchText').val(),
//			"whereMap" : JSON.stringify(whereObject)
			"sortBy" : sortBy
		},
		success : function(respone) {
//			console.log(respone);
			dataArray = respone.dataListArray;
			xCategoriesArr = respone.xAxisCategoriesListArray;
		}
	});
	
	//圖表格式
	var chartPic = "bar";
	var fontColor = "#519ae0";

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
			titleName = "轉換數(次)";
			selectTypeName = "轉換數";
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
	 		downloadPNG: '下載 PNG',
	 		downloadJPEG: '下載 JPEG',
	 		downloadPDF: '下載 PDF',
	 		downloadSVG: '下載 SVG',
	 		printChart: '列印圖表',
	 		exportButtonTitle: "輸出",
	 		printButtonTitle: "列印",
	 		resetZoom: "原尺寸",
	 		thousandsSep: "," // 顯示千分位逗號
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
	    	categories: xCategoriesArr,
            title: {
                text: null
            }
		},
	    yAxis: {
            min: 0,
            title: {
                text: '',
                align: 'high'
            },
            labels: {
                overflow: 'justify'
            }
        },
	    
	    tooltip: {
            valueSuffix: selectSuffix,
            valueDecimals: decimals
        },
	    
	    plotOptions: {
            bar: {
                dataLabels: {
                    enabled: true
                }
            }
        },
        
	    series: [{
	        name: selectTypeName,
	        data: dataArray
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



//var highChartActionPath="reportAdOsAjaxChart.html";
//var reportAjaxActionPath="reportAdOsAjaxTable.html";
//
////一開始執行
//$(function(){
//
//	//flash chart
//	showHighChart();
//
//	//flash data
//	ready();
//
//	//日期區間選擇
//	$('#seDateSelect').click(function(){
//		$.blockUI({
//			message: $('#seDateSelectDialog'),
//			css: {
//				width: '300px'
//			},
//			focusInput: false
//		});
//	});
//
//	//日期區間選擇 OK
//	$('#DateSelectOk').click(function(){
//		var selectValue = $('#selectRange').val();
//		var startValue;
//		var endValue;
//
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
//		ajaxFormSubmit();
//	});
//
//	//日期區間選擇 cancel
//	$('#DateSelectCancel').click(function(){
//		$.unblockUI();
//		return false;
//	});
//
//	//日期區間datepicker
//	$("#startDate").datepicker({
//		dateFormat: "yy-mm-dd",
//	   	yearRange:"-10:+10",
//		minDate: "-6M",
//		maxDate: 0
//	});
//
//	$("#endDate").datepicker({
//		dateFormat: "yy-mm-dd",
//	   	yearRange:"-10:+10",
//		minDate: "-6M",
//		maxDate: 0
//	});
//
//	 //自訂欄位OK
//	$('#optionOk').click(function(){
//		$('#rightselect option').attr("selected", "true");
//		var rvalue = $('#rightselect').val();
//		if (rvalue == null) {
//			rvalue = "";
//		}
//		document.excerptFrom.optionSelect.value = rvalue;
//
//		$('#leftselect option').attr("selected", "true");
//		var lvalue = $('#leftselect').val();
//		if (lvalue == null) {
//			lvalue = "";
//		}
//		document.excerptFrom.optionNotSelect.value = lvalue;
//
//		$.unblockUI();
//
//		ajaxFormSubmit();
//
//		return false;
//	});
//	
//	//自訂欄位cancel
//	$('#optionCancel').click(function(){
//		$.unblockUI();
//		return false;
//	});
//
//	//Chart開關
//	$('#aReportChart').click(function(){
//		var charText = $('#aReportChart').html();
//		if (charText == "關閉") {
//			$('#reportChart').hide();
//			$('#aReportChart').html("顯示");
//		} else {
//			$('#reportChart').show();
//			$('#aReportChart').html("關閉");
//		}
//	});
//	
//	//下載報表
//	var downlaod = $('#download');
//	downlaod.bind('click', function(e){
//		$('#excerptFrom').val("yes");
//		$('#excerptFrom').submit();
//	});
//	
//	//flash chart reload
//	$('#reloadFlash').click(function(){
//	   showHighChart();
//	});
//});
//
////ajax id  重新榜定
//function ready(){
//	if($("#excerptTable").children().length > 1){
//		var node = document.createElement("a");
//		node.style.float = 'left';
//		node.style.marginTop = '3px';
//		var img = document.createElement("img");
//		img.src='./html/img/question.gif';
//		img.title="互動數欄位:計算不同廣告樣式所產生的主要動作次數";
//		node.appendChild(img);
//		$($($("#excerptTable").children()[0]).children()[0]).children()[3].appendChild(node)
//	}
//	
//	//sort table plugin
//	$.tablesorter.defaults.widgets = ['zebra'];
//	//$.tablesorter.defaults.sortList = [[0,0]];
//	//$("#excerptTable").tablesorter();
//
//	$("#excerptTable").tablesorter({
//		headers: {
//			0 : { sorter: false },
//			2 : { sorter: 'fancyNumber' },
//			3 : { sorter: 'fancyNumber' },
//			4 : { sorter: 'rangesort' },
//			5 : { sorter: 'rangesort' },
//			6 : { sorter: 'rangesort' }
//		}
//	});
//	
//	//日期區間內容
//	$('#IT_dateRange').attr("value", $('#fstartDate').val() + "~" + $('#fendDate').val());
//
//	//自訂欄位點擊
//	$('#fieldSelect').click(function(){
//		$.blockUI({
//			message: $('#optionSelectDialog'),
//			css: {
//				width: '520px'
//			}
//		});
//	});
//
//	//每頁顯示數量選擇
//	$("#pageSizeSelect").change(function(){ //事件發生
//		document.excerptFrom.page.value = "1";
//		document.excerptFrom.pageSize.value = $("#pageSizeSelect").val();
//		//document.excerptFrom.submit();
//		ajaxFormSubmit();
//	});
//
//	//每頁顯示數量正確數量顯示判斷
//	$("#pageSizeSelect").children().each(function(){
//		var pageSize = document.excerptFrom.pageSize.value;
//		if ($(this).text() == pageSize) {
//			$(this).attr("selected", "true"); //或是給selected也可
//		}
//	});
//
//	//pageButton 第一頁
//	var fpage = $('#fpage');
//	fpage.bind('click', function(e){
//		document.excerptFrom.page.value = "1";
//		//document.excerptFrom.submit();
//		ajaxFormSubmit();
//	});
//
//	//pageButton 最後一頁
//	var epage = $('#epage');
//	epage.bind('click', function(e){
//		document.excerptFrom.page.value = document.excerptFrom.totalPage.value;
//		//document.excerptFrom.submit();
//		ajaxFormSubmit();
//	});
//
//	//pageButton 上一頁
//	var ppage = $('#ppage');
//	ppage.bind('click', function(e){
//		var page = parseInt($('#formPage').val());
//		page = page - 1;
//		if (page < 1) {
//			page = 1;
//		}
//
//		$('#formPage').val(page);
//		//document.excerptFrom.submit();
//		ajaxFormSubmit();
//	});
//
//	//pageButton 下一頁
//	var npage = $('#npage');
//	npage.bind('click', function(e){
//		var page = parseInt($('#formPage').val());
//		var totalPage = $('#ftotalPage').val();
//
//		page = page + 1;
//		if (page > totalPage) {
//			page = totalPage;
//		}
//		//alert("pagea="+$('#fpage').val());
//
//		$('#formPage').val(page);
//
//		//document.excerptFrom.submit();
//		ajaxFormSubmit();
//	});
//
//	//pageButton 第一頁不顯示檢查
//	if (document.excerptFrom.page.value == 1) {
//		$("#fpage").attr("src", $('#contentPath').val() + "page_first_disable.gif").css("cursor", "pointer");
//		$("#ppage").attr("src", $('#contentPath').val() + "page_pre_disable.gif").css("cursor", "pointer");
//	}
//
//	//pageButton 最後頁顯示檢查
//	if (document.excerptFrom.page.value == document.excerptFrom.totalPage.value) {
//		$("#epage").attr("src", $('#contentPath').val() + "page_end_disable.gif").css("cursor", "pointer");
//		$("#npage").attr("src", $('#contentPath').val() + "page_next_disable.gif").css("cursor", "pointer");
//	}
//	
//	//totalPage =0 頁數功能取消	
//	if($('#ftotalPage').val()=="0"){
//	   $("#fpage").attr("src", $('#contentPath').val() + "page_first_disable.gif").css("cursor", "pointer");
//	   $("#ppage").attr("src", $('#contentPath').val() + "page_pre_disable.gif").css("cursor", "pointer");
//	   $("#epage").attr("src", $('#contentPath').val() + "page_end_disable.gif").css("cursor", "pointer");
//	   $("#npage").attr("src", $('#contentPath').val() + "page_next_disable.gif").css("cursor", "pointer");
//	}
//
//	//Search 搜尋 
//	//init select 指定
//	var adPvclkOs = document.excerptFrom.adPvclkOs.value;
//	var adSearchWay = document.excerptFrom.adSearchWay.value;
//	var searchText = document.excerptFrom.searchText.value;
//
//	$("#searchText").attr("value", searchText);
//	
//	$("#adPvclkOs").children().each(function(){
//		if ($(this).val() == adPvclkOs) {
//			//jQuery給法
//			$(this).attr("selected", "true"); //或是給selected也可
//		}
//	});
//
//	$("#adSearchWay").children().each(function(){
//		if ($(this).val() == adSearchWay) {
//			//jQuery給法
//			$(this).attr("selected", "true"); //或是給selected也可
//		}
//	});
//
//	//搜尋動作 Do
//	$('#btnSearchDo').click(function(){
//		searchDo();
//		ajaxFormSubmit();
//	});
//	
//	$("#adPvclkOs").change(function(){
//    	searchDo();
//    	ajaxFormSubmit();
//   });
//
//	//重置 result
//	$('#btnSearchReset').click(function(){
//		serachReset();
//		searchDo();
//    	ajaxFormSubmit();
//	});
//}
//
////單一 ajax table a href call ad search
//function adIdSearch(adType, adId){
//	document.excerptFrom.searchId.value = adId;
//	document.excerptFrom.adType.value = adType;
//	//document.excerptFrom.submit();
//	ajaxFormSubmit();
//}
//
////顯示open flash
//function showHighChart(){
//	var dataArray;
//	$.ajax({
//		url : highChartActionPath,
//		type : "POST",
//		dataType:'json',
//		async: false,
//		data : {
//			"startDate" : $('#fstartDate').val(),
//			"endDate": $('#fendDate').val(),
//			"adPvclkOs" : $('#adPvclkOs').val(),
//			"adShowWay" : $('#fadShowWay').val(),
//			"charPic" : $('#selectChartPic').val(),
//			"charType" : $('#selectChartType').val(),
//			"searchId" : $('#fsearchId').val()
//		},
//		success : function(respone) {
//			console.log(respone);
//			dataArray = respone;
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
//			titleName = "互動率(%)";
//			selectTypeName = "點選率";
//			selectSuffix = "%";
//			decimals = 2;
//			break;
//		case "click":
//			titleName = "互動數(次)";
//			selectTypeName = "互動數";
//			selectSuffix = "次";
//			break;
//		case "invalid":
//			titleName = "無效點選數(次)";
//			selectTypeName = "無效點選數";
//			selectSuffix = "次";
//			break;
//		case "avgCost":
//			titleName = "單次互動費用(NT$)";
//			selectTypeName = "單次互動費用";
//			selectSuffix = "元";
//			decimals = 2;
//			break;
//		case "cost":
//			titleName = "費用(NT$)";
//			selectTypeName = "費用";
//			selectSuffix = "元";
//			decimals = 3;
//			break;
//		case "kiloCost":
//			titleName = "千次曝光費用(NT$)";
//			selectTypeName = "千次曝光費用";
//			selectSuffix = "元";
//			decimals = 2;
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
//	    series: [{
//	        name: selectTypeName,
//	        data: dataArray,
//	        lineWidth: 2,
//	        pointStart: Date.UTC(parseInt(dateArray[0]), parseInt(dateArray[1] -1), parseInt(dateArray[2])),
//	        pointInterval: 24 * 3600 * 1000
//	        
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
//
//function initJsonData(){
//	json_data = {
//		"page": $('#formPage').val(),
//		"pageSize": $('#fpageSize').val(),
//		"totalPage": $('#ftotalPage').val(),
//		"optionSelect": $('#foptionSelect').val(),
//		"optionNotSelect": $('#foptionNotSelect').val(),
//		"startDate": $('#fstartDate').val(),
//		"endDate": $('#fendDate').val(),
//		"adPvclkOs": $('#fadPvclkOs').val(),
//		"adSearchWay": $('#fadSearchWay').val(),
//		"searchText": $('#fsearchText').val(),
//		"searchId": $('#fsearchId').val(),
//		"downloadFlag": $('#downloadFlag').val()
//	};
//}
//
//function ajaxFormSubmit(){
//	$('#downloadFlag').val("no");
//	initJsonData();
//
//	$('#reportTableOut').block({
//		message: "<img src='html/img/LoadingWait.gif' />",
//		css: {
//			padding: 0,
//			margin: 0,
//			width: '50%',
//			top: '40%',
//			left: '35%',
//			textAlign: 'center',
//			color: '#000',
//			border: '3px solid #aaa',
//			backgroundColor: '#fff',
//			cursor: 'wait'
//		}
//	});
//
//	$.ajax({
//		type: "post",
//		url: 'reportAdOsAjaxTable.html',
//		data: json_data,
//		timeout: 30000,
//		error: function(xhr){
//			alert('Ajax request 發生錯誤');
//		},
//		success: function(response){
//			$('#reportTable').html(response);
//			ready();
//		}
//	});
//
//	$('#reportTableOut').unblock();
//
//	showHighChart();
//
//}
//
////搜尋執行
//function searchDo(){
//	document.excerptFrom.adPvclkOs.value = $('#adPvclkOs').val();
//	document.excerptFrom.adSearchWay.value = $('#adSearchWay').val();
//	document.excerptFrom.searchText.value = $('#searchText').val();
//	//document.excerptFrom.searchId.value = "";
//	document.excerptFrom.formPage.value = "1";
//}
//
////搜尋 search 
//function serachReset(){
//	$("#searchText").attr("value", "");
//
//	$("#adPvclkOs").children().each(function(){
//		if ($(this).val() == "all") {
//			//jQuery給法
//			$(this).attr("selected", "true"); //或是給selected也可
//		}
//	});
//
//	$("#adSearchWay").children().each(function(){
//		if ($(this).val() == "adsearch_include") {
//			//jQuery給法
//			$(this).attr("selected", "true"); //或是給selected也可
//		}
//	});
//
//	document.excerptFrom.searchId.value = "";
//}
