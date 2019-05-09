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
	
	// 選擇族群
	$("#age, #sex").click(function() {
		var type = $(this).attr("id");
		if($("#viewType").val() == type){
			return false;
		}
		
		if(type == "age"){
			$(".nav-title p").html("廣告族群成效-年齡");
			if(sortBy == "ageCode-DESC" || sortBy == "ageCode-ASC"){ 
				sortBy = "";
			}
		}else{
			$(".nav-title p").html("廣告族群成效-性別");
		}
		
		$("#viewType").val(type);
		processQueryAjax(defaultPage); // 選擇後重新查詢
	});
	
});

// 初始顯示其他欄位資訊 下拉選單
function initShowHideDataListInfo() {
	showHideDataListInfo("adType", "false");
	showHideDataListInfo("adOperatingRule", "false");
	showHideDataListInfo("adClkPriceType", "false");
	
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
		url : "reportAdAgesexAjaxTable.html",
	    data: {
	    	  "viewType" : $('#viewType').val(),
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
		$(this).siblings().attr("data-select", ""); // 將同一層的data-select取消選取
		$(this).attr("data-select", "true");
		
		var arr = $(this).attr("data-where").split("-");
		whereObject[arr[0]] = arr[1];
		processQueryAjax(defaultPage); // 選擇後重新查詢
	});
	
	processPageNumber(); // 在commonReport.js
	horisontal_scroll_listing($("#table-listing")); // 在commonReport.js
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
	$("#excerptFrom").attr("action", "reportAdAgesexDownload.html");
	$("#excerptFrom").submit();
}

//顯示open flash
//function showHighChart(){
//	var dataArray;
//	$.ajax({
//		url : "reportAdTimeAjaxChart.html",
//		type : "POST",
//		dataType:'json',
//		async: false,
//		data : {
//			"viewType" : $('#viewType').val(),
//			"charType" : $('#selectChartType').val(),
//			"startDate" : $('#startDate').val(),
//			"endDate" : $('#endDate').val(),
//			"searchText" : $('#searchText').val(),
//			"whereMap" : JSON.stringify(whereObject)
//		},
//		success : function(respone) {
////			console.log(respone);
//			dataArray = respone;
//		}
//	});
//	
//	//圖表格式
//	var searchTime = $('#viewType').val();
//	var xAxisArray;
//	if(searchTime == "week"){
//		xAxisArray = ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'];
//	} else {
//		xAxisArray = ['0-3時','4-7時','8-11時','12-15時','16-19時','20-23時'];
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
//	Highcharts.setOptions({
//	  	colors: ["#6eb6ff"],
//	  	symbols:['circle'],
//	  	lang: {
//			months: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '11月', '12月'],
//			weekdays: ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'],
//			shortMonths: ['01/', '02/', '03/', '04/', '05/', '06/', '07/', '08/', '09/', '10/', '11/', '12/'],
//			downloadPNG: '下載 PNG',
//			downloadJPEG: '下載 JPEG',
//			downloadPDF: '下載 PDF',
//			downloadSVG: '下載 SVG',
//			printChart: '列印圖表',
//			exportButtonTitle: "輸出",
//			printButtonTitle: "列印",
//			resetZoom: "原尺寸",
//			thousandsSep: "," // 顯示千分位逗號
//			//resetZoomTitle: "Reset,           
//	     }
//	});
//
//	$('#hcharts_bx').highcharts({  
//		chart: {
//	        type: "column" 
//	    },
//	    title: {
//	        text: titleName,
//	        style: {
//	        	color: "#313131",
//	        	fontWeight: 'bold',
//	        	fontFamily: '"微軟正黑體", Microsoft JhengHei, Arial, Helvetica, sans-serif, verdana'
//	        	//fontSize:'11px'
//	        	
//	        }
//	        //x: -20 //center
//	    },
//	    subtitle: {
//	    	text: ''
//	    },
//	    xAxis: {
//	    	categories: xAxisArray,
//	    	crosshair: true
//		},
//	    yAxis: {
//	    	min: 0,
//            title: {
//            	text: ''
//            }
//	    },
//	    tooltip: {
//	    	headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
//            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
//            '<td style="padding:0"><b>{point.y:#,###.' + decimals + 'f} ' + selectSuffix +'</b></td></tr>', //單位
//            footerFormat: '</table>',
//            shared: true,
//            useHTML: true
//	    },
//	    plotOptions: {
//            column: {
//	            pointPadding: 0.2,
//	            borderWidth: 0
//            }
//        },
//	    series: [{
//	        name: selectTypeName,
//	        data: dataArray
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
	var dataArray;
	$.ajax({
		url : "reportAdAgesexAjaxChart.html",
		type : "POST",
		dataType:'json',
		async: false,
		data : {
			"viewType" : $('#viewType').val(),
			"charType" : $('#selectChartType').val(),
			"startDate" : $('#startDate').val(),
			"endDate" : $('#endDate').val(),
			"searchText" : $('#searchText').val(),
			"whereMap" : JSON.stringify(whereObject)
		},
		success : function(respone) {
//			console.log(respone);
			dataArray = respone;
		}
	});
	
	// ---預設樣式----
  Highcharts.setOptions({
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

  if($('#viewType').val() == 'sex'){
  	$('#hcharts_bx').highcharts({
  		colors: ['#6eb6ff', '#ffac12'],
  		chart: {
  			plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            type: 'pie'
//  			type: 'pie',
//  			options3d: {
//                  enabled: true,
//                  alpha: 50,
//                  beta: 0
//              }
  		},
  		title: {
  			text: "性別",
  			style: {
  				color: '#313131',
  				fontWeight: 'bold',
  				fontFamily: '"微軟正黑體", Microsoft JhengHei, Arial, Helvetica, sans-serif, verdana'
  					//fontSize:'11px'
  					
  			}
  		},
  		tooltip: {
  			pointFormat: '{series.name}: <b>{point.percentage:.2f}%</b>'
//  				,
//  			backgroundColor: "#FFFFFF",
//              borderWidth: 1
  		},
  		plotOptions: {
  			pie: {
  				allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    format: '<b>{point.name}</b>: {point.percentage:.2f} %',
                    style: {
                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                    }
                }
//  				allowPointSelect: false,
//                  cursor: 'pointer',
//                  depth: 50,
//  				dataLabels: {
//  					enabled: true,
//  					connectorWidth: 0,
//  					format: '<b style="color:{point.color}">{point.name}</b><b style="color:{point.color}">: {point.percentage:.2f} %</b>'    				}
  			}
  		},
  		series: [{
  			name: '比例',
  			colorByPoint: true,
  			data: [
  			       {
  			    	   name: '男性',
  			    	   y: dataArray[0],
  			    	   sliced: true,
  			    	   selected: true
//  			    	   color: "#519ae0"
  			       }, {
  			    	   name: '女性',
  			    	   y: dataArray[1]
//  			       	   ,color: "#ff5353"
  			       }]
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
  } else {
  	$('#hcharts_bx').highcharts({
          chart: {
              type: 'bar'
          },
          title: {
              text: '<b>年齡層</b>',
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
              categories: ['18歲(不含)以下', '18歲~24歲', '25歲~34歲', '35歲~44歲', '45歲~54歲', '55歲~64歲', '65歲~74歲', '75歲以上', '不分年齡'],
              title: {
                  text: null
              }
          },
          yAxis: {
              min: 0,
              max: 100,
              title: {
            	  text: '',
                  align: 'high'
              },
              labels: {
              	format: '{value}%',
                  overflow: 'justify'
              }
          },
          tooltip: {
              valueSuffix: ' %',
              valueDecimals: 2,
              backgroundColor: "#FFFFFF",
              borderWidth: 1
          },
          plotOptions: {
              bar: {
                  dataLabels: {
                      enabled: true,
                      format: '{point.y:.2f}%'
                  }
              }
          },
          legend: {
          	enabled:false
          },
          credits: {
              enabled: false
          },
          series: [{
              name: '比例',
              data: dataArray
          }]
      });
  }

}







//var highChartActionPath="reportAdAgesexAjaxChart.html";
//var reportAjaxActionPath="reportAdAgesexAjaxTable.html";
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
//		
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
//	//自訂欄位OK
//	$('#optionOk').click(function(){
//		//$.blockUI({ message: '<h1>Just a moment...</h1>' }); 
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
//
//		var charText = $('#aReportChart').html();
//
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
//		$($($("#excerptTable").children()[0]).children()[0]).children()[9].append(node);
//		
//		var node2 = document.createElement("b");
//		node2.style.float = 'left';
//		node2.style.marginTop = '3px';
//		var img2 = document.createElement("img");
//		img2.src='./html/img/question.gif';
//		img2.title="廣告費用因小數點進位影響總計費用，實際扣款依帳單管理為主";
//		node2.appendChild(img2);
//		$($($("#excerptTable").children()[0]).children()[0]).children()[13].append(node2);
//	}
//	
//	//sort table plugin
//	$.tablesorter.defaults.widgets = ['zebra'];
//
//	$("#excerptTable").tablesorter({
//		headers: {
//			0 : { sorter: false },
//			//4 : { sorter: 'fancyNumber' },
//			8 : { sorter: 'fancyNumber' },
//			9 : { sorter: 'fancyNumber' },
//			10 : { sorter: 'fancyNumber' },
//			11 : { sorter: 'rangesort' },
//			12 : { sorter: 'rangesort' },
//			13 : { sorter: 'rangesort' }
//		}
//	});
//
//	 //日期區間內容
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
//	var adPvclkDevice = document.excerptFrom.adPvclkDevice.value;
//	var adType = document.excerptFrom.adType.value;
//	var adSearchWay = document.excerptFrom.adSearchWay.value;
//	var searchText = document.excerptFrom.searchText.value;
//	var adShowWay = document.excerptFrom.adShowWay.value;
//	var searchAgesex = document.excerptFrom.searchAgesex.value;
//	var adOperatingRule = document.excerptFrom.adOperatingRule.value;
//
//	$("#searchText").attr("value", searchText);
//
//	$("#adType").children().each(function(){
//		if ($(this).val() == adType) {
//			//jQuery給法
//			$(this).attr("selected", "true"); //或是給selected也可
//		}
//	});
//
//	$("#adPvclkDevice").children().each(function(){
//		if ($(this).val() == adPvclkDevice) {
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
//	$("#adShowWay").children().each(function(){
//		if ($(this).val() == adShowWay) {
//			//jQuery給法
//			$(this).attr("selected", "true"); //或是給selected也可
//		}
//	});
//
//	$("#searchAgesex").children().each(function(){
//		if ($(this).val() == searchAgesex) {
//			//jQuery給法
//			$(this).attr("selected", "true"); //或是給selected也可
//		}
//	});
//	
//	$("#adOperatingRule").children().each(function(){
//		if ($(this).val() == adOperatingRule) {
//			//jQuery給法
//			$(this).attr("selected", "true"); //或是給selected也可
//		}
//	});
//	
//	//搜尋動作 Do
//	$('#btnSearchDo').click(function(){
//		searchDo();
//		//document.excerptFrom.submit();
//		ajaxFormSubmit();		
//	});
//	
//	$("#adShowWay, #adPvclkDevice, #adSearchWay, #searchAgesex, #adOperatingRule").change(function(){
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
//
//	//活動標籤
//	$('#stepActivityId').click(function(){
//		serachReset();
//		searchDo();
//		//document.excerptFrom.submit();
//		ajaxFormSubmit();
//	});
//
//	//群組標籤
//	$('#stepGroupId').click(function(){
//		//alert("aa");
//		serachReset();
//		document.excerptFrom.adType.value = "adtype_group";
//		document.excerptFrom.searchId.value = "";
//		//searchDo();
//		//document.excerptFrom.submit();
//		ajaxFormSubmit();
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
//			"adPvclkDevice" : $('#fadPvclkDevice').val(),
//			"adType" : $('#fadType').val(),
//			"adSearchWay" : $('#fadSearchWay').val(),
//			"adShowWay" : $('#fadShowWay').val(),
//			"charPic" : $('#selectChartPic').val(),
//			"charType" : $('#selectChartType').val(),
//			"searchId" : $('#fsearchId').val(),
//			"searchText" : $('#searchText').val(),
//			"searchAgesex" : $('#fadSearchAgesex').val(),
//			"adOperatingRule" : $('#fadOperatingRule').val()
//		},
//		success : function(respone) {
//			console.log(respone);
//			dataArray = respone;
//		}
//	});
//	
//	// ---預設樣式----
//    Highcharts.setOptions({
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
//    if($('#fadSearchAgesex').val() == 'S'){
//    	$('#hcharts_bx').highcharts({
//    		chart: {
//    			type: 'pie',
//    			options3d: {
//                    enabled: true,
//                    alpha: 50,
//                    beta: 0
//                }
//    		},
//    		title: {
//    			text: "性別",
//    			style: {
//    				fontWeight: 'bold',
//    				fontFamily: '"微軟正黑體", Microsoft JhengHei, Arial, Helvetica, sans-serif, verdana'
//    					//fontSize:'11px'
//    					
//    			}
//    		},
//    		tooltip: {
//    			pointFormat: '{series.name}: <b>{point.percentage:.2f}%</b>',
//    			backgroundColor: "#FFFFFF",
//                borderWidth: 1
//    		},
//    		plotOptions: {
//    			pie: {
//    				allowPointSelect: false,
//                    cursor: 'pointer',
//                    depth: 50,
//    				dataLabels: {
//    					enabled: true,
//    					connectorWidth: 0,
//    					format: '<b style="color:{point.color}">{point.name}</b><b style="color:{point.color}">: {point.percentage:.2f} %</b>'    				}
//    			}
//    		},
//    		series: [{
//    			name: '比例',
//    			colorByPoint: true,
//    			data: [
//    			       {
//    			    	   name: '男性',
//    			    	   y: dataArray[0],
//    			    	   color: "#519ae0"
//    			       }, {
//    			    	   name: '女性',
//    			    	   y: dataArray[1],
//    			    	   color: "#ff5353"
//    			       }]
//    		}],
//    		legend: { //選單
//    			enabled:false
//    		},
//    		exporting: { //右上打印
//    			//enabled:false
//    		},
//    		credits: { //右下網址
//    			enabled:false
//    		},
//    	});
//    } else {
//    	$('#hcharts_bx').highcharts({
//            chart: {
//                type: 'bar'
//            },
//            title: {
//                text: '<b>年齡層</b>'
//            },
//            xAxis: {
//                categories: ['18歲(不含)以下', '18歲~24歲', '25歲~34歲', '35歲~44歲', '45歲~54歲', '55歲~64歲', '65歲~74歲', '75歲以上', '不分年齡'],
//                title: {
//                    text: null
//                }
//            },
//            yAxis: {
//                min: 0,
//                max: 100,
//                title: {
//                	text: null
//                },
//                labels: {
//                	format: '{value}%',
//                    overflow: 'justify'
//                }
//            },
//            tooltip: {
//                valueSuffix: ' %',
//                valueDecimals: 2,
//                backgroundColor: "#FFFFFF",
//                borderWidth: 1
//            },
//            plotOptions: {
//                bar: {
//                    dataLabels: {
//                        enabled: true,
//                        format: '{point.y:.2f}%'
//                    }
//                }
//            },
//            legend: {
//            	enabled:false
//            },
//            credits: {
//                enabled: false
//            },
//            series: [{
//                name: '比例',
//                data: dataArray,
//                color: "#00DD00"
//            }]
//        });
//    }
//
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
//		"adPvclkDevice": $('#fadPvclkDevice').val(),
//		"adType": $('#fadType').val(),
//		"adSearchWay": $('#fadSearchWay').val(),
//		"adShowWay": $('#fadShowWay').val(),
//		"searchText": $('#fsearchText').val(),
//		"searchId": $('#fsearchId').val(),
//		"downloadFlag": $('#downloadFlag').val(),
//		"searchAgesex": $('#fadSearchAgesex').val(),
//		"adOperatingRule": $('#fadOperatingRule').val()
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
//		url: reportAjaxActionPath,
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
//	document.excerptFrom.adPvclkDevice.value = $('#adPvclkDevice').val();
//	document.excerptFrom.adType.value = $('#adType').val();
//	document.excerptFrom.adSearchWay.value = $('#adSearchWay').val();
//	document.excerptFrom.searchText.value = $('#searchText').val();
//	document.excerptFrom.adShowWay.value = $('#adShowWay').val();
//	document.excerptFrom.searchAgesex.value = $('#searchAgesex').val();
//	document.excerptFrom.adOperatingRule.value = $('#adOperatingRule').val();
//	document.excerptFrom.searchId.value = "";
//	document.excerptFrom.formPage.value = "1";
//}
//
////搜尋 search 
//function serachReset(){
//	$("#searchText").attr("value", "");
//	$("#adPvclkDevice").children().each(function(){
//		if ($(this).val() == "") {
//			//jQuery給法
//			$(this).attr("selected", "true"); //或是給selected也可
//		}
//	});
//
//	$("#adType").children().each(function(){
//		if ($(this).val() == "adtype_activity") {
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
//	$("#adShowWay").children().each(function(){
//		if ($(this).val() == "0") {
//			//jQuery給法
//			$(this).attr("selected", "true"); //或是給selected也可
//		}
//	});
//
//	$("#searchAgesex").children().each(function(){
//		if ($(this).val() == "A") {
//			//jQuery給法
//			$(this).attr("selected", "true"); //或是給selected也可
//		}
//	});
//	
//	$("#adOperatingRule").children().each(function(){
//		if ($(this).val() == "") {
//			//jQuery給法
//			$(this).attr("selected", "true"); //或是給selected也可
//		}
//	});
//	
//	document.excerptFrom.searchId.value = "";
//}
