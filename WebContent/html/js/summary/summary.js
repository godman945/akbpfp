﻿$(document).ready(function(){

	ready();
	
	findAdTable();
	
	
	
	$("#reloadChart").click(function(){
		loadChart();
	});
	
	// 日期區間選擇
    $('#dateRangeSelect').click(function(){
    
        $.blockUI({
            message: $("#dateSelectDialog"),
            css: {
                width: "300px"
            },
            focusInput: false
        });
        
    });
	
	// 日期區間選擇 OK
    $('#dateSelectOk').click(function(){
    	
	    var selectValue = $('#selectRange').val();
	    var startDate;
	    var endDate;
	    
	    if(selectValue=="self"){
	    	startDate = $("#startDate").val();
	    	endDate = $("#endDate").val();
	    }else{
	    	startDate = $("#selectRange").val().split(",")[0];
	    	endDate = $("#selectRange").val().split(",")[1];
	    }
	    
	    if(Date.parse(startDate.replace(/\-/g,'/')) > Date.parse(endDate.replace(/\-/g,'/'))){
			alert("結束日期必須晚於開始日期！");
			return false;
	    }else{
	    	$("#IT_dateRange").val(startDate+"~"+endDate);
	    }
	    
		
		findAdTable();
		
        $.unblockUI();       
        
    });
	    
    // 日期區間選擇 cancel
    $('#dateSelectCancel').click(function(){
        $.unblockUI();
        return false;
    });
    
	// 日期區間 datepicker    
    $("#startDate").datepicker({
    	showOn: "button",
    	buttonImage: "html/img/icon_cal.gif",
    	buttonImageOnly: true,
    	yearRange:"-10:+10",
    	minDate: "-6M",
    	maxDate: 0
    });        
	
    $("#endDate").datepicker({
    	showOn: "button",
    	buttonImage: "html/img/icon_cal.gif",
    	buttonImageOnly: true,
    	yearRange:"-10:+10",
    	minDate: "-6M",
    	maxDate: 0
    		
    });
    
    //data search
    $("#adLayerType").change(function(){

    	$("#adSearchTable").block({
    		message:"<img src='html/img/LoadingWait.gif' />",
    		css:{
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
    	
    	findAdTable();
    	
    	$("#adSearchTable").unblock();
    	// for IE、FF
    	// 由於 IE、FF 不會cache 資料，導致點選廣告/分類的連結離開頁面後，再點回到上一頁回到本頁時，下拉選單會跳回廣告，而不是選取的項目
    	// 所以只好用 cookie 記住選到哪一項，重新選擇時，會先移除舊的，再建新的
		//$.cookie('sel_adLayerType', null);
    	//$.cookie('sel_adLayerType', $("#adLayerType").val(), { expires: 1 });
    });
    
  //data search
    $("#boardType").change(function(){
    	findLatestBoard();
    });
    
});

function ready(){

	// for Chrome
	// 因為 chrome 會 cache 日期，導致點選廣告/分類的連結離開頁面後，再點回到上一頁回到本頁時，日期會錯亂，雖然重新整理後沒問題，還是過濾一下
	if($("#startDate").val().indexOf("~") > 0) {
		var startDate = $("#startDate").val(); 
		var dateArea = startDate.split("~");
		$("#startDate").val(dateArea[0]);
		$("#endDate").val(dateArea[1]);
	}
	var dateRange = $("#startDate").val()+"~"+$("#endDate").val();

	$("#IT_dateRange").val(dateRange);

	loadChart();
	findLatestBoard();
}

function findLatestBoard(){
	
	$.ajax({
		url:"latestBoard.html",
		data:{
			 "boardType": $("#boardType").val()
		},
		type:"post",
		dataType:"html",
		success:function(response, status){
			$("#latestBoardList").html(response);	
		},
		error: function(xtl) {
			
			errorDialogBtn("錯誤訊息","系統繁忙，請稍後再試！");
		}
	});
}

function loadChart(){
	var dataArray;
	$.ajax({
		url : "chartDate.html",
		type : "POST",
		dataType:'json',
		async: false,
		data : {
			"chartPic" : $('#selectChartPic').val(),
			"chartType" : $('#selectChartType').val()
		},
		success : function(respone) {
			console.log(respone);
			dataArray = respone;
		}
	});
	
	var Nowdate=new Date();
	var MonthFirstDay=new Date(Nowdate.getFullYear(),Nowdate.getMonth(),1);
	
	//圖表格式
	var selectPic = $("#selectChartPic").val();
	var chartPic = "";
	var fontColor = "#ff5353";
	switch(selectPic){
		case "lineChart":
			chartPic = "";
			fontColor = "#ff5353";
			break;
		case "barChart":
			chartPic = "column";
			fontColor = "#519ae0";
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
			titleName = "點選次數(次)";
			selectTypeName = "點選次數";
			selectSuffix = "次";
			break;
		case "cost":
			titleName = "費用(NT$)";
			selectTypeName = "費用";
			selectSuffix = "元";
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
	        	color: fontColor,
	        	fontWeight: 'bold',
	        	fontFamily: '"微軟正黑體", Microsoft JhengHei, Arial, Helvetica, sans-serif, verdana',
	        	fontSize:'11px'
	        	
	        }
	        //x: -20 //center
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
	        }],
	        minRange: 4
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
	        pointStart: Date.UTC(MonthFirstDay.getFullYear(),MonthFirstDay.getMonth(),MonthFirstDay.getDate()),
	        pointInterval: 24 * 3600 * 1000
	    }],
	    legend: { //選單
			enabled:false
		},
		exporting: { //右上打印
			enabled:false
		},
		credits: { //右下網址
			enabled:false
		},
	});
	
	
	/*var flashData = "chartDate.html";

	flashData+="?chartInputType="+$('#selectChartPic').val();
	flashData+="%26"+$('#selectChartType').val();

	 // flashchart
	$("#flashChart").flash().remove();  
	$("#flashChart").flash({
       swf: 'html/flash/open-flash-chart.swf',
       width: 400,
       height: 100,
       flashvars: {
           'data-file': flashData
       }
	});*/

}

function findAdTable(){

	// for IE、FF
	// 由於 IE、FF 不會cache 資料，導致點選廣告/分類的連結離開頁面後，再點回到上一頁回到本頁時，下拉選單會跳回廣告，而不是選取的項目，所以只好用 cookie 記住選到哪一項
	// 進入頁面時，如果 cookie 有值，就設定原先選取的值，讓選單可以選到之前的選項
	//if($.cookie('sel_adLayerType') != null) {
	//	$("#adLayerType").val($.cookie('sel_adLayerType'));
	//	$.cookie('sel_adLayerType', null);
	//}
	
	var date = $("#IT_dateRange").val().split("~");
	var startDate = date[0];
	var endDate = date[1];
	var adLayerType = $("#adLayerType").val();
	var url;
	var pageNo = $("#pageNo").val();
	var pageSize = $("#pageSize").val();
	
	if(adLayerType == "adAction"){
		url = "adActionLayer.html";
	}else if(adLayerType == "adGroup"){
		url = "adGroupLayer.html";
	}else if(adLayerType == "adKeyword"){
		url = "adKeywordLayer.html";
	}else if(adLayerType == "adAd"){
		url = "adAdLayer.html";
	}

	$.ajax({
		url:url,
		data:{
			 "startDate": startDate,
			 "endDate": endDate,
			 "adLayerType": adLayerType,
			 "pageNo": pageNo,
			 "pageSize": pageSize
		},
		type:"post",
		dataType:"html",
		success:function(response, status){
			$("#tableList").html(response);	
			page();
			tableSorter();
			
		},
		error: function(xtl) {
			
			errorDialogBtn("錯誤訊息","系統繁忙，請稍後再試！");
		}
	});
	
}

// 分頁功能
function wantSearch(pageNo) {

    if (pageNo != null) {
        $("#pageNo").val(pageNo);
    }
    findAdTable();
}

function tableSorter(){
	
	$("#listTable").tablesorter({
		headers:{
			 5 : { sorter: 'rangesort' },
			 6 : { sorter: 'rangesort' }
			}
	});
}

function preview(img) {
    $.fancybox({
        'href':img,
        'autoSize':true,
        'autoHeight':true,
        'autoScale':true,
        'transitionIn':'none',
        'transitionOut':'none',
        'padding':0,
        'overlayOpacity':.75,
        'overlayColor':'#fff',
        'scrolling':'no'
    });
}