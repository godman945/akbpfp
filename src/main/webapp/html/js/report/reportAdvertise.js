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
		url : "reportAdvertiseAjaxTable.html",
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
	$("#excerptFrom").attr("action", "reportAdvertiseDownload.html");
	$("#excerptFrom").submit();
}

//顯示open flash
function showHighChart(){
	var dataArray;
	$.ajax({
		url : "reportAdvertiseAjaxChart.html",
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
			console.log(respone);
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

//樣板尺寸
var tproObject = new Object();
tproObject["data"] = {};

//行銷結尾圖
var salesEndIframewp = new Object();
salesEndIframewp["data"] = {};
/**
 * 明細預覽畫面
 * @param index
 * @returns
 */
function preView(adStyle, Obj){
	var tempObj = $(Obj).siblings(".hiddenVal"); // 取得同一層隱藏欄位值區塊
	var tempWidth = $(tempObj).find('#width').val();
	var tempHeight = $(tempObj).find('#height').val();

	var showSelectAdSize = false;
	var showLeftRightBtn = false;
	if (adStyle == 'PROD') { // 商品廣告
		showLeftRightBtn = true; // 顯示左右按鈕，商品廣告同尺寸不同樣板切換
		showSelectAdSize = true; // 顯示選擇廣告尺寸
		
		var templateTpro = JSON.parse($(tempObj).find('#productTemplateStr').html());
		var templateSizeOptionStr = '<div id="previewselector" class="previewselector">選擇廣告尺寸<select>';
		var index = 0;
		for (var key in templateTpro) {
//			templateSizeOptionStr = '';
			var tproSizeArray =  key.split('_');
			var width = tproSizeArray[0];
			var height = tproSizeArray[1];
			if(key == '300_250'){
				templateSizeOptionStr += '<option value="tpro_'+key+'" selected>'+width+' x '+height+'</option>';	
			}else{
				templateSizeOptionStr += '<option value="tpro_'+key+'">'+width+' x '+height+'</option>';
			}
//			$("#adSize").append(templateSizeOptionStr);
			
			var sizeTpros = templateTpro[key].substring(0, templateTpro[key].length -1);
			//建立樣板物件內容
			tproObject.data['tpro_'+key] = sizeTpros;
			//建立行銷結尾圖物件內容
			index = index + 1;
			salesEndIframewp.data['index_'+index] = key;
		}
		templateSizeOptionStr += '</select></div>';
	}

	if (adStyle == 'TMG') { // 圖文廣告選擇廣告尺寸
		showSelectAdSize = true; // 顯示選擇廣告尺寸
		var templateSizeOptionStr = '<div id="previewselector" class="previewselector">選擇廣告尺寸<select>';
//		var templateSizeOptionStr = '<div id="previewselector" class="previewselector">選擇廣告尺寸<select>'
//			+ '<option value="120 x 600" selected>120 x 600</option>';
	}
	
	// 組中間圖片畫面
	var tempHtmlView = '<div class="predivbox">';
	if (adStyle == 'IMG') { // 圖像廣告
		tempHtmlView += '<iframe src="' + $(tempObj).find('#img').val() 
				+ '" width="' + tempWidth + '" height="' + tempHeight
				+ '" allowtransparency="true" frameborder="0" scrolling="no"></iframe>';
	} else if (adStyle == 'VIDEO') { // 影音廣告
		tempHtmlView += '<iframe scrolling="no" frameborder="0" marginwidth="0" marginheight="0" vspace="0" hspace="0" width="' + tempWidth
				+ '" height="' + tempHeight
				+ '" allowtransparency="true" allowfullscreen="true" src="adVideoModel.html?adPreviewVideoURL=' + $(tempObj).find('#adPreviewVideoURL').val()
				+ '&adPreviewVideoBgImg=' + $(tempObj).find('#adPreviewVideoBgImg').val()
				+ '&realUrl=' + $(tempObj).find('#realUrl').val()
				+ '&resize=true\"></iframe>';
	} else if (adStyle == 'PROD') { // 商品廣告
		tempHtmlView += '<iframe height="250" width="300" class="akb_iframe" scrolling="no" frameborder="0" marginwidth="0" marginheight="0" vspace="0" hspace="0" id="pchome8044_ad_frame1" allowtransparency="true" allowfullscreen="true"'
				+ 'src="adProdModel.html' + '?catalogGroupId=' + $(tempObj).find('#catalogGroupId').val()
				+ '&disTxtType=' + $(tempObj).find('#disTxtType').val()
				+ '&disBgColor=' + $(tempObj).find('#disBgColor').val()
				+ '&disFontColor=' + $(tempObj).find('#disFontColor').val()
				+ '&btnTxt=' + $(tempObj).find('#btnTxt').val()
				+ '&btnFontColor=' + $(tempObj).find('#btnFontColor').val()
				+ '&btnBgColor=' + $(tempObj).find('#btnBgColor').val()
				+ '&logoType=' + $(tempObj).find('#logoType').val()
				+ '&logoText=' + $(tempObj).find('#logoText').val()
				+ '&logoBgColor=' + $(tempObj).find('#logoBgColor').val()
				+ '&logoFontColor=' + $(tempObj).find('#logoFontColor').val()
				+ '&prodLogoType=' + $(tempObj).find('#prodLogoType').val()
				+ '&adbgType=' + $(tempObj).find('#adbgType').val()
				+ '&imgProportiona=' + $(tempObj).find('#imgProportiona').val()
				+ '&userLogoPath=' + $(tempObj).find('#userLogoPath').val()
				+ '&previewTpro=' + $(tempObj).find('#previewTpro').val()
				+ '&saleImg=' + $(tempObj).find('#saleImg').val()
				+ '&saleEndImg=' + $(tempObj).find('#saleEndImg').val()
				+ '&posterType=' + $(tempObj).find('#posterType').val()
				+ '&realUrl=' + $(tempObj).find('#realUrl').val() + '"></iframe>'
	}
	
	tempHtmlView += '</div>';
	
	$.fancybox(
		[
			tempHtmlView
//			'<div class="predivbox"><iframe height="292" width="350" src="https://kdcl.pchome.com.tw/adshow2.html?pfbxCustomerInfoId=PFBC20160804001&amp;positionId=PFBP201608310008&amp;sampleId=us_201609300002&amp;tproId=c_x03_po_tpro_0061&amp;format=0&amp;page=1&amp;padHeight=292&amp;padWidth=350&amp;keyword=&amp;fig=ae1ab9f5db00d24b4b6f3cf9418cb85a&amp;ref=ODczOL%2Bay5aRVYadzp2FlrqOxpO8VLqVxFTLnYY%3D%0D%0A" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" align="ceneter" class="akb_iframe"></iframe></div>',
//			'<div class="predivbox"><iframe height="292" width="350" src="https://kdcl.pchome.com.tw/adshow2.html?pfbxCustomerInfoId=PFBC20160804001&amp;positionId=PFBP201608310008&amp;sampleId=us_201609300002&amp;tproId=c_x03_po_tpro_0061&amp;format=0&amp;page=1&amp;padHeight=292&amp;padWidth=350&amp;keyword=&amp;fig=ae1ab9f5db00d24b4b6f3cf9418cb85a&amp;ref=ODczOL%2Bay5aRVYadzp2FlrqOxpO8VLqVxFTLnYY%3D%0D%0A" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" align="ceneter" class="akb_iframe"></iframe></div>',
//			'<div class="predivbox"><iframe height="292" width="350" src="https://kdcl.pchome.com.tw/adshow2.html?pfbxCustomerInfoId=PFBC20160804001&amp;positionId=PFBP201608310008&amp;sampleId=us_201609300002&amp;tproId=c_x03_po_tpro_0061&amp;format=0&amp;page=1&amp;padHeight=292&amp;padWidth=350&amp;keyword=&amp;fig=ae1ab9f5db00d24b4b6f3cf9418cb85a&amp;ref=ODczOL%2Bay5aRVYadzp2FlrqOxpO8VLqVxFTLnYY%3D%0D%0A" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" align="ceneter" class="akb_iframe"></iframe></div>',
//			'<div class="predivbox"><iframe height="292" width="350" src="https://kdcl.pchome.com.tw/adshow2.html?pfbxCustomerInfoId=PFBC20160804001&amp;positionId=PFBP201608310008&amp;sampleId=us_201609300002&amp;tproId=c_x03_po_tpro_0061&amp;format=0&amp;page=1&amp;padHeight=292&amp;padWidth=350&amp;keyword=&amp;fig=ae1ab9f5db00d24b4b6f3cf9418cb85a&amp;ref=ODczOL%2Bay5aRVYadzp2FlrqOxpO8VLqVxFTLnYY%3D%0D%0A" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" align="ceneter" class="akb_iframe"></iframe></div>',
//			'<div class="predivbox"><iframe height="292" width="350" src="https://kdcl.pchome.com.tw/adshow2.html?pfbxCustomerInfoId=PFBC20160804001&amp;positionId=PFBP201608310008&amp;sampleId=us_201609300002&amp;tproId=c_x03_po_tpro_0061&amp;format=0&amp;page=1&amp;padHeight=292&amp;padWidth=350&amp;keyword=&amp;fig=ae1ab9f5db00d24b4b6f3cf9418cb85a&amp;ref=ODczOL%2Bay5aRVYadzp2FlrqOxpO8VLqVxFTLnYY%3D%0D%0A" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" align="ceneter" class="akb_iframe"></iframe></div>'
		], {
			'type': 'html',
//			'index': id,
			'fitToView': false,
			'autoScale': false,
			'autoDimensions': false,
			'modal': false,
			'hideOnOverlayClick': false,
			'showCloseButton': true,
			'width': 970,
			'height': 600,
			'autoSize': false,
			'autoHeight': false,
			'transitionIn': 'none',
			'transitionOut': 'none',
			'speedIn': 100,
			'speedOut': 100,
			'padding': 0,
			'overlayOpacity': .70,
			'overlayColor': '#000',
			'scrolling': 'no',
			'showNavArrows': showLeftRightBtn,
			'titleShow': true,
			'titlePosition': 'outside',
			'titleFormat': function (title, currentArray, currentIndex, currentOpts) {
				return '<div class="predivtitle">廣告尺寸：' + tempWidth + ' x ' + tempHeight + '</div>';
			},
			onStart: function () {
				$('#fancybox-outer').css('backgroundColor', '#000');
				if(showSelectAdSize) {
					
					
					
					$('#fancybox-outer').prepend(templateSizeOptionStr);
				}
			},
			onCleanup: function () {
				$('#fancybox-outer').css('backgroundColor', '#fff');
				$('#previewselector').remove();
			},
			onClosed: function () {
				$("#preDiv>div").empty();
			}
		}
	);
}

/**
 * 每日成效
 * @returns
 */
function dailyPerformance(adSeq) {
console.log(adSeq);
}


//var highChartActionPath="reportAdvertiseAjaxChart.html";
//var reportAjaxActionPath="reportAdvertiseAjaxTable.html";
//
////鎖住畫面直到資料載入完畢
//blockBody();
//
//
////一開始執行
//$(function(){
//	//flash chart
//	showHighChart();
//
//	//flash data
//	ready();
//	
//	//綁定排序
//	tablesorter1();
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
//	   if(chartAdSeq != null){
//		   showHighChart(chartAdSeq);
//	   }else{
//		   showHighChart(null);
//	   }
//	});
//	
//	$(function() {
//	
//	}).bind("sortStart",function(e, t){
//		 blockBody();
//		 resetIframeSize();
//	 }).bind("sortEnd",function(e, t){
//		 resizeIframeInfo();
//		 setTimeout(function(){  $.unblockUI(); }, 1000);
//	 });
//});
//
//$(window).load(function(){ 
//	 $.unblockUI();
//}); 
//
//function blockBody(){
//	$('body').block({
//		message: "",
//		css: {
//			padding: 0,
//			margin: 0,
//			width: '100%',
//			top: '40%',
//			left: '35%',
//			textAlign: 'center',
//			color: '#000',
//			border: '3px solid #aaa',
//			backgroundColor: '#fff',
//			cursor: 'wait'
//		}
//	});
//}
//
///**重新恢復原本iframe尺寸*/
//function resetIframeSize(){
//	$("#excerptTable tbody tr").each(function(index,obj){
//		var td = $(obj).children()[1];
//		var iframe = td.querySelector('.akb_iframe');
//		var div = td.querySelector('.ad_size');
//		if(div != null){
//			var size = $(div).text().replace('尺寸 ','');
//			var sizeArray = size.split(' x ');
//			var width = sizeArray[0].trim();
//			var height = sizeArray[1].trim();
//			iframe.width = width;
//			iframe.height = height;
//		}
//	});
//}
//
///**重新計算明細高度*/
//function resizeIframeInfo(){
//	$("#excerptTable tbody tr").each(function(index,obj){
//		var td = $(obj).children()[1];
//		var iframe = td.querySelector('.akb_iframe');
//		if(iframe != null){
//			var adratio = iframe.height / iframe.width;
//			var	adh = 250 * adratio;
//			var infoDiv = $($(td).children()[0]).children()[1];
//			$(infoDiv).css('margin-top',(adh / 2) - 45+'px');
//		}
//	});
//}
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
//		
//		
//		var node2 = document.createElement("b");
//		node2.style.float = 'left';
//		node2.style.marginTop = '3px';
//		var img2 = document.createElement("img");
//		img2.src='./html/img/question.gif';
//		img2.title="廣告費用因小數點進位影響總計費用，實際扣款依帳單管理為主";
//		node2.appendChild(img2);
//		
//		if($($($('#excerptTable').children()[0]).children()).children().length == 15){
//			$($($("#excerptTable").children()[0]).children()[0]).children()[9].appendChild(node);
//			$($($("#excerptTable").children()[0]).children()[0]).children()[13].appendChild(node2);
//		}else{
//			$($($("#excerptTable").children()[0]).children()[0]).children()[6].appendChild(node);
//			$($($("#excerptTable").children()[0]).children()[0]).children()[10].appendChild(node2);
//		}
//	}
//	
//	//sort table plugin
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
//	var adPvclkDevice = document.excerptFrom.adPvclkDevice.value;
//	var adType = document.excerptFrom.adType.value;
//	var adSearchWay = document.excerptFrom.adSearchWay.value;
//	var searchText = document.excerptFrom.searchText.value;
//	var adShowWay = document.excerptFrom.adShowWay.value;
//	var adOperatingRule = document.excerptFrom.adOperatingRule.value;
//
//	$("#searchText").attr("value", searchText);
//
//	$("#adPvclkDevice").children().each(function(){
//		if ($(this).val() == adPvclkDevice) {
//			//jQuery給法
//			$(this).attr("selected", "true"); //或是給selected也可
//		}
//	});
//
//	$("#adType").children().each(function(){
//		if ($(this).val() == adType) {
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
//		var searchAdseq = $("#fsearchAdseq").val();
//		if(searchAdseq != ""){
//			detailQuery(searchAdseq);
//		} else {
//			ajaxFormSubmit();
//		}
//	});
//	
//	$("#adShowWay, #adPvclkDevice, #adSearchWay, #adOperatingRule").change(function(){
//    	searchDo();
//    	var searchAdseq = $("#fsearchAdseq").val();
//		if(searchAdseq != ""){
//			detailQuery(searchAdseq);
//		} else {
//			ajaxFormSubmit();
//		}
//   });
//
//	//重置 result
//	$('#btnSearchReset').click(function(){
//		serachReset();
//		searchDo();
//		var searchAdseq = $("#fsearchAdseq").val();
//		if(searchAdseq != ""){
//			detailQuery(searchAdseq);
//		} else {
//			ajaxFormSubmit();
//		}
//	});
//
//	//活動標籤
//	$('#stepActivityId').click(function(){
//		serachReset();
//		searchDo();
//		//document.excerptFrom.submit();
//		var searchAdseq = $("#fsearchAdseq").val();
//		if(searchAdseq != ""){
//			detailQuery(searchAdseq);
//		} else {
//			ajaxFormSubmit();
//		}
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
//		var searchAdseq = $("#fsearchAdseq").val();
//		if(searchAdseq != ""){
//			detailQuery(searchAdseq);
//		} else {
//			ajaxFormSubmit();
//		}
//	});
//}
//
////單一 ajax table a href call ad search
//function adIdSearch(adType, adId){
//	document.excerptFrom.searchId.value = adId;
//	document.excerptFrom.adType.value = adType;
//	//document.excerptFrom.submit();
//	var searchAdseq = $("#fsearchAdseq").val();
//	if(searchAdseq != ""){
//		detailQuery(searchAdseq);
//	} else {
//		ajaxFormSubmit();
//	}
//}
//
////顯示open flash
//function showHighChart(adSeq){
//	var searchAdSeq = "Null";
//	if(adSeq != null){
//		searchAdSeq = adSeq;
//	}
//	
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
//			"searchAdseq" : searchAdSeq,
//			"adOperatingRule" : $('#fadOperatingRule').val()
//		},
//		success : function(respone) {
////			console.log(respone);
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
//			selectTypeName = "互動率";
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
//	$('#fsearchAdseq').val("");
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
//			$('#reportTableOut').unblock();
//			alert('Ajax request 發生錯誤');
//			
//		},
//		success: function(response){
//			$('#reportTableOut').unblock();
//			$('#reportTable').html(response);
//
//			ready();
//			tablesorter1();
//		}
//	});
//
//	//$('#reportTableOut').unblock();
//
//	showHighChart(null);
//}
//
///**廣告明細第三層*/
//var chartAdSeq = null;
//function detailQuery(adSeq) {
// 	chartAdSeq = adSeq;
//	$('#downloadFlag').val("no");
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
//		"searchAdseq": adSeq,
//		"adOperatingRule": $('#fadOperatingRule').val()
//	};
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
//			$('#reportTableOut').unblock();
//			alert('Ajax request 發生錯誤');
//			
//		},
//		success: function(response){
//			$('#reportTableOut').unblock();
//			$('#reportTable').html(response);
//
//			ready();
//			
//			tablesorter2();
//			
//		}
//	});
//	
//	showHighChart(adSeq);
//}
//
//function tablesorter1(){
//	
////	$("#excerptTable thead").addEventListener("click", function(){};
//
////	  $(function() {
////          $("#excerptTable").tablesorter({debug: true});
////          $("#excerptTable").toggle(
////            function() {
////              alert('major asc')
////            },
////            function() {
////              alert('major desc')
////            }
////          );
////      });
//	
//	$.tablesorter.defaults.widgets = ['zebra'];
//	
//	
//	//$.tablesorter.defaults.sortList = [[0,0]];
//	//$("#excerptTable").tablesorter();
//
//	
//	
//	$("#excerptTable").tablesorter({
//		headers: {
//			0 : { sorter: false },
////			8 : { sorter: 'fancyNumber' },
////			9 : { sorter: 'fancyNumber' },
////			10 : { sorter: 'fancyNumber' },
////			11 : { sorter: 'rangesort' },
////			12 : { sorter: 'rangesort' },
////			13 : { sorter: 'rangesort' },
//			14 : { sorter: false }
//		},
//		initialized: function(table) {
//			$("#excerptTable").on('sortStart', function() {
//				 blockBody();
//				 resetIframeSize();
//		    }),
//		    $("#excerptTable").on('sortEnd', function() {
//		    	setTimeout(function(){  $.unblockUI(); }, 1000);
//				resizeIframeInfo();
//		    });
//		 }
//	});
//}
//
//function tablesorter2(){
//	
//	$.tablesorter.defaults.widgets = ['zebra'];
//	
//	$("#excerptTable").tablesorter({
//		headers: {
//			5 : { sorter: 'fancyNumber' },
//			6 : { sorter: 'fancyNumber' },
//			7 : { sorter: 'fancyNumber' },
//			8 : { sorter: 'rangesort' },
//			9 : { sorter: 'rangesort' },
//			10 : { sorter: 'rangesort' }
//		}
//	});
//}
//
////搜尋執行
//function searchDo(){
//	document.excerptFrom.adPvclkDevice.value = $('#adPvclkDevice').val();
//	document.excerptFrom.adType.value = $('#adType').val();
//	document.excerptFrom.adSearchWay.value = $('#adSearchWay').val();
//	document.excerptFrom.searchText.value = $('#searchText').val();
//	document.excerptFrom.adShowWay.value = $('#adShowWay').val();
//	document.excerptFrom.adOperatingRule.value = $('#adOperatingRule').val();
//	document.excerptFrom.searchId.value = "";
//	document.excerptFrom.formPage.value = "1";
//}
//
////搜尋 search 
//function serachReset(){
//	$("#searchText").attr("value", "");
//
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
//	$("#adOperatingRule").children().each(function(){
//		if ($(this).val() == "") {
//			//jQuery給法
//			$(this).attr("selected", "true"); //或是給selected也可
//		}
//	});
//	
//	document.excerptFrom.searchId.value = "";
//}
//
//function preview(img) {
//    $.fancybox({
//        'href':img,
//        'autoSize':true,
//        'autoHeight':true,
//        'autoScale':true,
//        'transitionIn':'none',
//        'transitionOut':'none',
//        'padding':0,
//        'overlayOpacity':.75,
//        'overlayColor':'#fff',
//        'scrolling':'no'
//    });
//}
//
//function previewHtml5(width,height,imgSrc,realUrl){
//	 $.fancybox(
//			 '<div style="position:absolute;z-index:10;border:0px;background:none;width:' + width + 'px;height:' + height + 'px;">' + 
//			 '<a href="' + realUrl + '" target="_blank" style="display:block;width:' + width + 'px;height:' + height + 'px;"><img src="html/img/blank.gif" style="width:' + width + 'px;height:' + height + 'px;border:0px;"></a>'
//			 + '</div>'
//			 + '<iframe src="' + imgSrc + '" width="' + width + '" height="' + height + '"  allowtransparency="true" frameborder="0" scrolling="no" ></iframe>',
//	    		{
//	    			'autoDimensions'	: false,
//	    			'width'         	: width,
//	    			'height'        	: height,
//	    			'autoSize'			: true,
//	    			'autoHeight'		: true,
//	    			'autoScale'			: false,
//	    			'transitionIn'		: 'none',
//	    			'transitionOut'		: 'none',
//	    			'padding'			: 0,
//	    			'overlayOpacity'    : .75,
//	    			'overlayColor'      : '#fff',
//	    			'scrolling'			: 'no'
//	    		}
//	    );
//}
//
////呼叫商品成效
//function previewProdAdDetail(customerInfoId,adSeq){
//	var startDate = $("#IT_dateRange").val().split('~')[0];
//	var endDate = $("#IT_dateRange").val().split('~')[1];
//	window.open ('adProdDetailReport.html?pfpCustomerInfoId='+customerInfoId+'&adSeq='+adSeq+'&startDate='+startDate+'&endDate='+endDate ,'_blank');
//	
//}
