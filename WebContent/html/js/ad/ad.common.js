﻿$(document).ready(function(){
	
	ready();
	
	// 日期區間選擇
    $('#dateRangeSelect').click(function(){    
    	$.fancybox({
    		'onStart'	: function() { $("#dateSelectDialog").css("display","block"); },            
            'onClosed'	: function() { $("#dateSelectDialog").css("display","none"); },
    		'href'      :'#dateSelectDialog'  		                    
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
		
		findTableView();
		      
		$.fancybox.close();
    });
	    
    // 日期區間選擇 cancel
    $('#dateSelectCancel').click(function(){
    	$.fancybox.close();
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
    
    $("#search").click(function(){
		findTableView();      
    });
     
    
});

function ready(){
	
	var dateRange = $("#startDate").val()+"~"+$("#endDate").val();
	$("#IT_dateRange").val(dateRange);
	
	findTableView();
}

function checkAll(){	
	
	if ($("#tableView input:checkbox[name='chkY']").attr('checked')) {
		$("#tableView input:checkbox[name='chkY']").attr('checked', false);
	}else{
		$("#tableView input:checkbox[name='chkY']").attr('checked', true);
	}
}

//分頁功能
function wantSearch(pageNo) {

    if (pageNo != null) {
        $("#pageNo").val(pageNo);
    }
    findTableView();
}
