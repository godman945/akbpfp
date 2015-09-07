$(document).ready(function(){

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
	
	var flashData = "chartDate.html";

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
	});

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
			 2 : { sorter: 'fancyNumber' },
			 7 : { sorter: 'fancyNumber' }
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