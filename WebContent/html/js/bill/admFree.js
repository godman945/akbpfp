﻿$(document).ready(function(){


	ready();
	findAdmFreeTable();
	
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
		
	    findAdmFreeTable();
		
        $.unblockUI();       
        
        return false;
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
    	yearRange:"-10:+10"
    });        
	
    $("#endDate").datepicker({
    	showOn: "button",
    	buttonImage: "html/img/icon_cal.gif",
    	buttonImageOnly: true,
    	yearRange:"-10:+10"
    });
});

function ready(){
	
	var dateRange = $("#startDate").val()+"~"+$("#endDate").val();
	$("#IT_dateRange").val(dateRange);
	
}

function findAdmFreeTable(){
	
	var date = $("#IT_dateRange").val().split("~");
	var startDate = date[0];
	var endDate = date[1];
	var url = "searchAdmFree.html";;

	$.ajax({
		url:url,
		data:{
			 "startDate": startDate,
			 "endDate": endDate
		},
		type:"post",
		dataType:"html",
		success:function(response, status){
			$("#tableList").html(response);	
			tableScroll();

			
			
		},
		error: function(xtl) {
			
			errorDialogBtn("錯誤訊息","系統繁忙，請稍後再試！");
		}
	});
	
}

function tableScroll(){
	// table scroll
	$('#transTable').tableScroll({height:800});
}