
var json_data;
var flashChartActionPath="reportAdOsAjaxChart.html";
var reportAjaxActionPath="reportAdOsAjaxTable.html";

//一開始執行
$(function(){

	//flash chart
	showFlashChart();

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
	   showFlashChart();
	});
});

//ajax id  重新榜定
function ready(){
	//sort table plugin
	$.tablesorter.defaults.widgets = ['zebra'];
	//$.tablesorter.defaults.sortList = [[0,0]];
	//$("#excerptTable").tablesorter();

	$("#excerptTable").tablesorter({
		headers: {
			0 : { sorter: false },
			2 : { sorter: 'fancyNumber' },
			3 : { sorter: 'fancyNumber' },
			4 : { sorter: 'rangesort' },
			5 : { sorter: 'rangesort' },
			6 : { sorter: 'fancyNumber' },
			7 : { sorter: 'fancyNumber' }
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
	var adPvclkOs = document.excerptFrom.adPvclkOs.value;
	var adSearchWay = document.excerptFrom.adSearchWay.value;
	var searchText = document.excerptFrom.searchText.value;

	$("#searchText").attr("value", searchText);
	
	$("#adPvclkOs").children().each(function(){
		if ($(this).val() == adPvclkOs) {
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

	//搜尋動作 Do
	$('#btnSearchDo').click(function(){
		searchDo();
		ajaxFormSubmit();
	});

	//重置 result
	$('#btnSearchReset').click(function(){
		serachReset();
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
function showFlashChart(){
	var flash_data = flashChartActionPath;
	flash_data+="?flashInputValue="+$('#fstartDate').val();
	flash_data+="%26"+$('#fendDate').val();
	flash_data+="%26"+$('#fadPvclkOs').val();
	flash_data+="%26"+$('#fadSearchWay').val();
	flash_data+="%26"+$('#selectChartPic').val();
	flash_data+="%26"+$('#selectChartType').val();
	flash_data+="%26"+$('#fsearchId').val();

	//中文處理
	if ($('#searchText').val().length == 0) {
		flash_data += "%26Null";
	} else {
		flash_data += "%26" + encodeURIComponent($('#searchText').val());
	}

	//flashchart
	$('#flashChart').flash().remove();  
	$('#flashChart').flash({
		swf: 'html/flash/open-flash-chart.swf',
		width: 600,
		height: 200,
		flashvars: {
			'data-file': flash_data
		}
	});

	//$('#debug').html(flash_data);
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
		"adPvclkOs": $('#fadPvclkOs').val(),
		"adSearchWay": $('#fadSearchWay').val(),
		"searchText": $('#fsearchText').val(),
		"searchId": $('#fsearchId').val(),
		"downloadFlag": $('#downloadFlag').val()
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
		url: 'reportAdOsAjaxTable.html',
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

	showFlashChart();

}

//搜尋執行
function searchDo(){
	document.excerptFrom.adPvclkOs.value = $('#adPvclkOs').val();
	document.excerptFrom.adSearchWay.value = $('#adSearchWay').val();
	document.excerptFrom.searchText.value = $('#searchText').val();
	//document.excerptFrom.searchId.value = "";
	document.excerptFrom.formPage.value = "1";
}

//搜尋 search 
function serachReset(){
	$("#searchText").attr("value", "");

	$("#adPvclkOs").children().each(function(){
		if ($(this).val() == "all") {
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

	document.excerptFrom.searchId.value = "";
}
