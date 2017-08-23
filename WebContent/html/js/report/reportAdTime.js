
var highChartActionPath="reportAdTimeAjaxChart.html";
var reportAjaxActionPath="reportAdTimeAjaxTable.html";
//一開始執行
$(function(){

	//flash chart
	showHighChart();

	//flash data
	ready();

	//日期區間選擇
	$('#seDateSelect').click(function(){
		$.blockUI({
			message: $('#seDateSelectDialog'),
			css: {
				width: '300px'
			},
			focusInput: false
		});
		
	});

	//日期區間選擇 OK
	$('#DateSelectOk').click(function(){
		var selectValue = $('#selectRange').val();
		var startValue;
		var endValue;

		if (selectValue == "self") {
			startValue = $('#startDate').val();
			endValue = $('#endDate').val();
		} else {
			startValue = $('#selectRange').val().split(",")[0];
			endValue = $('#selectRange').val().split(",")[1];
		}

		var startDate = Date.parse(startValue.replace(/\-/g,'/'));
		var endDate = Date.parse(endValue.replace(/\-/g,'/'));
		if (startDate > endDate) {
			alert("結束日期必須晚於開始日期！");
			return false;
		} else {
			$('#fstartDate').val(startValue);
			$('#fendDate').val(endValue);
		}

		$('#formPage').val("1");

		$.unblockUI();

		ajaxFormSubmit();
	});

	//日期區間選擇 cancel
	$('#DateSelectCancel').click(function(){
		$.unblockUI();
		return false;
	});

	//日期區間datepicker
	$("#startDate").datepicker({
		dateFormat: "yy-mm-dd",
	   	yearRange:"-10:+10",
		minDate: "-6M",
		maxDate: 0
	});

	$("#endDate").datepicker({
		dateFormat: "yy-mm-dd",
	   	yearRange:"-10:+10",
		minDate: "-6M",
		maxDate: 0
	});

	//自訂欄位OK
	$('#optionOk').click(function(){
		//$.blockUI({ message: '<h1>Just a moment...</h1>' }); 
		$('#rightselect option').attr("selected", "true");
		var rvalue = $('#rightselect').val();
		if (rvalue == null) {
			rvalue = "";
		}
		document.excerptFrom.optionSelect.value = rvalue;

		$('#leftselect option').attr("selected", "true");
		var lvalue = $('#leftselect').val();
		if (lvalue == null) {
			lvalue = "";
		}
		document.excerptFrom.optionNotSelect.value = lvalue;

		$.unblockUI();

		ajaxFormSubmit();
 
		return false;
	});

	//自訂欄位cancel
	$('#optionCancel').click(function(){
		$.unblockUI();
		return false;
	});

	//Chart開關
	$('#aReportChart').click(function(){

		var charText = $('#aReportChart').html();

		if (charText == "關閉") {
			$('#reportChart').hide();
			$('#aReportChart').html("顯示");
		} else {
			$('#reportChart').show();
			$('#aReportChart').html("關閉");
		}
	});

	//下載報表
	var downlaod = $('#download');
	downlaod.bind('click', function(e){
		$('#excerptFrom').val("yes");
		$('#excerptFrom').submit();
	});

	//flash chart reload
	$('#reloadFlash').click(function(){
	   showHighChart();
	});
});

//ajax id  重新榜定
function ready(){
	//sort table plugin
	$.tablesorter.defaults.widgets = ['zebra'];

	$("#excerptTable").tablesorter({
		headers: {
			0 : { sorter: false },
			//4 : { sorter: 'fancyNumber' },
			8 : { sorter: 'fancyNumber' },
			9 : { sorter: 'fancyNumber' },
			10 : { sorter: 'fancyNumber' },
			11 : { sorter: 'rangesort' },
			12 : { sorter: 'rangesort' },
			13 : { sorter: 'rangesort' }
		}
	});

	 //日期區間內容
	$('#IT_dateRange').attr("value", $('#fstartDate').val() + "~" + $('#fendDate').val());

	//自訂欄位點擊
	$('#fieldSelect').click(function(){
		$.blockUI({
			message: $('#optionSelectDialog'),
			css: {
				width: '520px'
			}
		});
	});

	//每頁顯示數量選擇
	$("#pageSizeSelect").change(function(){ //事件發生
		document.excerptFrom.page.value = "1";
		document.excerptFrom.pageSize.value = $("#pageSizeSelect").val();
		//document.excerptFrom.submit();
		ajaxFormSubmit();
	});

	//每頁顯示數量正確數量顯示判斷
	$("#pageSizeSelect").children().each(function(){
		var pageSize = document.excerptFrom.pageSize.value;
		if ($(this).text() == pageSize) {
			$(this).attr("selected", "true"); //或是給selected也可
		}
	});

	//pageButton 第一頁
	var fpage = $('#fpage');
	fpage.bind('click', function(e){
		document.excerptFrom.page.value = "1";
		//document.excerptFrom.submit();
		ajaxFormSubmit();
	});

	//pageButton 最後一頁
	var epage = $('#epage');
	epage.bind('click', function(e){
		document.excerptFrom.page.value = document.excerptFrom.totalPage.value;
		//document.excerptFrom.submit();
		ajaxFormSubmit();
	});

	//pageButton 上一頁
	var ppage = $('#ppage');
	ppage.bind('click', function(e){
		var page = parseInt($('#formPage').val());
		page = page - 1;
		if (page < 1) {
			page = 1;
		}

		$('#formPage').val(page);
		//document.excerptFrom.submit();
		ajaxFormSubmit();
	});

	//pageButton 下一頁
	var npage = $('#npage');
	npage.bind('click', function(e){
		var page = parseInt($('#formPage').val());
		var totalPage = $('#ftotalPage').val();

		page = page + 1;
		if (page > totalPage) {
			page = totalPage;
		}
		//alert("pagea="+$('#fpage').val());

		$('#formPage').val(page);

		//document.excerptFrom.submit();
		ajaxFormSubmit();
	});

	//pageButton 第一頁不顯示檢查
	if (document.excerptFrom.page.value == 1) {
		$("#fpage").attr("src", $('#contentPath').val() + "page_first_disable.gif").css("cursor", "pointer");
		$("#ppage").attr("src", $('#contentPath').val() + "page_pre_disable.gif").css("cursor", "pointer");
	}

	//pageButton 最後頁顯示檢查
	if (document.excerptFrom.page.value == document.excerptFrom.totalPage.value) {
		$("#epage").attr("src", $('#contentPath').val() + "page_end_disable.gif").css("cursor", "pointer");
		$("#npage").attr("src", $('#contentPath').val() + "page_next_disable.gif").css("cursor", "pointer");
	}

	//totalPage =0 頁數功能取消	
	if($('#ftotalPage').val()=="0"){
	   $("#fpage").attr("src", $('#contentPath').val() + "page_first_disable.gif").css("cursor", "pointer");
	   $("#ppage").attr("src", $('#contentPath').val() + "page_pre_disable.gif").css("cursor", "pointer");
	   $("#epage").attr("src", $('#contentPath').val() + "page_end_disable.gif").css("cursor", "pointer");
	   $("#npage").attr("src", $('#contentPath').val() + "page_next_disable.gif").css("cursor", "pointer");
	}

	//Search 搜尋 
	//init select 指定
	var adPvclkDevice = document.excerptFrom.adPvclkDevice.value;
	var adType = document.excerptFrom.adType.value;
	var adSearchWay = document.excerptFrom.adSearchWay.value;
	var searchText = document.excerptFrom.searchText.value;
	var adShowWay = document.excerptFrom.adShowWay.value;
	var searchTime = document.excerptFrom.searchTime.value;
	var adOperatingRule = document.excerptFrom.adOperatingRule.value;

	$("#searchText").attr("value", searchText);

	$("#adType").children().each(function(){
		if ($(this).val() == adType) {
			//jQuery給法
			$(this).attr("selected", "true"); //或是給selected也可
		}
	});

	$("#adPvclkDevice").children().each(function(){
		if ($(this).val() == adPvclkDevice) {
			//jQuery給法
			$(this).attr("selected", "true"); //或是給selected也可
		}
	});

	$("#adSearchWay").children().each(function(){
		if ($(this).val() == adSearchWay) {
			//jQuery給法
			$(this).attr("selected", "true"); //或是給selected也可
		}
	});

	$("#adShowWay").children().each(function(){
		if ($(this).val() == adShowWay) {
			//jQuery給法
			$(this).attr("selected", "true"); //或是給selected也可
		}
	});

	$("#searchTime").children().each(function(){
		if ($(this).val() == searchTime) {
			//jQuery給法
			$(this).attr("selected", "true"); //或是給selected也可
		}
	});
	
	$("#adOperatingRule").children().each(function(){
		if ($(this).val() == adOperatingRule) {
			//jQuery給法
			$(this).attr("selected", "true"); //或是給selected也可
		}
	});
	
	//搜尋動作 Do
	$('#btnSearchDo').click(function(){
		searchDo();
		//document.excerptFrom.submit();
		ajaxFormSubmit();		
	});
	
	$("#adShowWay, #adPvclkDevice, #adSearchWay, #searchTime, #adOperatingRule").change(function(){
    	searchDo();
    	ajaxFormSubmit();
   });

	//重置 result
	$('#btnSearchReset').click(function(){
		serachReset();
		searchDo();
    	ajaxFormSubmit();
	});

	//活動標籤
	$('#stepActivityId').click(function(){
		serachReset();
		searchDo();
		//document.excerptFrom.submit();
		ajaxFormSubmit();
	});

	//群組標籤
	$('#stepGroupId').click(function(){
		//alert("aa");
		serachReset();
		document.excerptFrom.adType.value = "adtype_group";
		document.excerptFrom.searchId.value = "";
		//searchDo();
		//document.excerptFrom.submit();
		ajaxFormSubmit();
	});
}

//單一 ajax table a href call ad search
function adIdSearch(adType, adId){
	document.excerptFrom.searchId.value = adId;
	document.excerptFrom.adType.value = adType;
	//document.excerptFrom.submit();
	ajaxFormSubmit();
}

//顯示open flash
function showHighChart(){
	var dataArray;
	$.ajax({
		url : highChartActionPath,
		type : "POST",
		dataType:'json',
		async: false,
		data : {
			"startDate" : $('#fstartDate').val(),
			"endDate": $('#fendDate').val(),
			"adPvclkDevice" : $('#fadPvclkDevice').val(),
			"adType" : $('#fadType').val(),
			"adSearchWay" : $('#fadSearchWay').val(),
			"adShowWay" : $('#fadShowWay').val(),
			"charPic" : $('#selectChartPic').val(),
			"charType" : $('#selectChartType').val(),
			"searchId" : $('#fsearchId').val(),
			"searchText" : $('#searchText').val(),
			"searchTime" : $('#fadSearchTime').val(),
			"adOperatingRule" : $('#fadOperatingRule').val()
		},
		success : function(respone) {
			console.log(respone);
			dataArray = respone;
		}
	});
	
	var startDate = $('#fstartDate').val();
	var dateArray = startDate.split("-");
	
	//圖表格式
	var searchTime = $("#searchTime").val();
	var xAxisArray;
	if(searchTime == "T"){
		xAxisArray = ['0-3時','4-7時','8-11時','12-15時','16-19時','20-23時'];
	} else {
		xAxisArray = ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'];
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
		case "ctr":
			titleName = "互動率(%)";
			selectTypeName = "互動率";
			selectSuffix = "%";
			decimals = 2;
			break;
		case "click":
			titleName = "互動數(次)";
			selectTypeName = "互動數";
			selectSuffix = "次";
			break;
		case "invalid":
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
			break;
	}
	
	// ---預設樣式----
    Highcharts.setOptions({
    	colors: ["#519ae0"],
        
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
	        type: "column" 
	    },	
	    
	    title: {
	        text: titleName,
	        style: {
	        	color: "#519ae0",
	        	fontWeight: 'bold',
	        	fontFamily: '"微軟正黑體", Microsoft JhengHei, Arial, Helvetica, sans-serif, verdana'
	        	//fontSize:'11px'
	        	
	        }
	        //x: -20 //center
	    },
	    subtitle: {
	        text: '',
	        x: -20
	    },
	    
	    xAxis: {
	    	categories: xAxisArray,
            title: {
                text: null
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
	        valueDecimals: decimals
	    },
	    
	    series: [{
	        name: selectTypeName,
	        data: dataArray,
	        lineWidth: 2
	        
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

function initJsonData(){
	json_data = {
		"page": $('#formPage').val(),
		"pageSize": $('#fpageSize').val(),
		"totalPage": $('#ftotalPage').val(),
		"optionSelect": $('#foptionSelect').val(),
		"optionNotSelect": $('#foptionNotSelect').val(),
		"startDate": $('#fstartDate').val(),
		"endDate": $('#fendDate').val(),
		"adPvclkDevice": $('#fadPvclkDevice').val(),
		"adType": $('#fadType').val(),
		"adSearchWay": $('#fadSearchWay').val(),
		"adShowWay": $('#fadShowWay').val(),
		"searchText": $('#fsearchText').val(),
		"searchId": $('#fsearchId').val(),
		"downloadFlag": $('#downloadFlag').val(),
		"searchTime": $('#fadSearchTime').val(),
		"adOperatingRule": $('#fadOperatingRule').val()
	};
}

function ajaxFormSubmit(){
	$('#downloadFlag').val("no");
	initJsonData();

	$('#reportTableOut').block({
		message: "<img src='html/img/LoadingWait.gif' />",
		css: {
			padding: 0,
			margin: 0,
			width: '50%',
			top: '40%',
			left: '35%',
			textAlign: 'center',
			color: '#000',
			border: '3px solid #aaa',
			backgroundColor: '#fff',
			cursor: 'wait'
		}
	});

	$.ajax({
		type: "post",
		url: reportAjaxActionPath,
		data: json_data,
		timeout: 30000,
		error: function(xhr){
			alert('Ajax request 發生錯誤');
		},
		success: function(response){
			$('#reportTable').html(response);
			ready();
		}
	});

	$('#reportTableOut').unblock();

	showHighChart();

}

//搜尋執行
function searchDo(){
	document.excerptFrom.adPvclkDevice.value = $('#adPvclkDevice').val();
	document.excerptFrom.adType.value = $('#adType').val();
	document.excerptFrom.adSearchWay.value = $('#adSearchWay').val();
	document.excerptFrom.searchText.value = $('#searchText').val();
	document.excerptFrom.adShowWay.value = $('#adShowWay').val();
	document.excerptFrom.searchTime.value = $('#searchTime').val();
	document.excerptFrom.adOperatingRule.value = $('#adOperatingRule').val();
	document.excerptFrom.searchId.value = "";
	document.excerptFrom.formPage.value = "1";
}

//搜尋 search 
function serachReset(){
	$("#searchText").attr("value", "");
	$("#adPvclkDevice").children().each(function(){
		if ($(this).val() == "") {
			//jQuery給法
			$(this).attr("selected", "true"); //或是給selected也可
		}
	});

	$("#adType").children().each(function(){
		if ($(this).val() == "adtype_activity") {
			//jQuery給法
			$(this).attr("selected", "true"); //或是給selected也可
		}
	});

	$("#adSearchWay").children().each(function(){
		if ($(this).val() == "adsearch_include") {
			//jQuery給法
			$(this).attr("selected", "true"); //或是給selected也可
		}
	});

	$("#adShowWay").children().each(function(){
		if ($(this).val() == "0") {
			//jQuery給法
			$(this).attr("selected", "true"); //或是給selected也可
		}
	});

	$("#searchTime").children().each(function(){
		if ($(this).val() == "W") {
			//jQuery給法
			$(this).attr("selected", "true"); //或是給selected也可
		}
	});
	
	$("#adOperatingRule").children().each(function(){
		if ($(this).val() == "") {
			//jQuery給法
			$(this).attr("selected", "true"); //或是給selected也可
		}
	});
	
	document.excerptFrom.searchId.value = "";
}
