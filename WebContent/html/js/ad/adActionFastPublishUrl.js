$(document).ready(function(){
	//設定廣告活動預設資料
	initDefaultActionInfo();
	
	$("#adActionNameSelect").change(function(){
		changeActionDefaultData(this.value);
	});
});

var maskingConfig = {
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
	};


function initDefaultActionInfo(){
	if($("#defaultAdType").val() != -1){
		$("#adType").prop( "disabled", true );
		$("#adType").val($("#defaultAdType").val());
	}
	
	if($("#defaultAdOperatingRule").val() == 'MEDIA'){
		$("#adOperatingRuleSelect").prop("disabled", true);
		$("#adOperatingRuleSelect").val(0);
	}
	
	if($("#defaultAdDevice").val() != -1){
		$("#adDevice").prop( "disabled", true );
		$("#adDevice").val($("#defaultAdDevice").val());
	}
	
	
	
}


function changeActionDefaultData(actionName){
	console.log(actionName);
	$('#loadingWaitBlock').block(maskingConfig);
//	$.ajax({
//	    type: "post",
//	    dataType: "json",
//	    url: "adactionInfoAjax.html",
//	    data: {
//	          "actionName": actionName
//	    },
//	    timeout: 30000,
//	    error: function(xhr){
//	    	$('#loadingWaitBlock').unblock();
//	        alert('Ajax request 發生錯誤');
//	    },
//	    success:function(response, status){
//    		$('#loadingWaitBlock').unblock();
//		}
//	});
}