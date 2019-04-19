﻿var trackingRangeDate=1;
var codeType=0;

$(document).ready(function() {
	
	//追蹤行銷效期	
	$('.slider-pointer.type1').on('click',function(){
		trackingRangeDate=1;
		$('.track-handler').css('left',0);
	});
	$('.slider-pointer.type2').on('click',function(){
		trackingRangeDate=7;
		$('.track-handler').css('left',100);
	});
	$('.slider-pointer.type3').on('click',function(){
		trackingRangeDate=28;
		$('.track-handler').css('left',400);
	})
	
	//發送mail
	$('#sendMail').on('click',function(){
		//檢查全部mail是否合法
		if (checkMailReceivers()==true){
			//send mail
			sendMailAjax();
		}
	})
	
	//新增再行銷追蹤
	$('#addRetargetingTracking').on('click',function(){
		if (checkData() == true){
			addRetargetingTrackingAjax();
		}
	})
	
	//複製成功
	$('.btn-copyto').on('click',function(){
	  $(".copySuccess").css('display','');
	});
});

/**
* 檢查欄位值正確性
*/
function checkData() {
	var checkFlag = true;
	
	//檢查代碼名稱
	if ($('#retargetingName').val().trim() == ""){
		$('#retargetingNameMsg').css("display", "");
		checkFlag = false;
	}else{
		$('#retargetingNameMsg').css("display", "none");
	}
	
	return checkFlag;
};


//新增再行銷追蹤
function addRetargetingTrackingAjax(){
	$.ajax({
		type : "post",
		dataType : "json",
		url : "addRetargetingTrackingAjax.html",
		data : {
			"trackingSeq" : $('#trackingSeq').val(),
			"trackingName" : $('#retargetingName').val().trim(),
			"paId" : $('#paid').val(),
			"codeType" : codeType,
			"trackingRangeDate" : trackingRangeDate
		},
		timeout : 30000,
		error : function(xhr) {
			alert("系統繁忙，請稍後再試！");
		},
		success : function(response, status) {
		}
	}).done(function(response) {
		console.log(response);
		if (response.status == "SUCCESS") {
			$(location).attr('href','retargetingTrackingListView.html');
		} else {
			alert(response.msg);
		}
	});
}



//檢查全部mail是否合法
function checkMailReceivers(){
	//檢查mail是否為空
	if ($('#mailReceivers').val()==""){
			$("#emailMsgError").text("email不得為空");
			$("#emailMsgError").css('display', "");
			return false;
	}else{
		$("#emailMsgError").text("");
		$("#emailMsgError").css('display', "none");
	}
	
	
	//檢查mail是否合法
	var mailReceiversAry = [];
	var mailReceiversStr = $('#mailReceivers').val();
	mailReceiversAry= mailReceiversStr.split(";");
	for (i = 0; i < mailReceiversAry.length; i++) { 
		if (!verifyEmail(mailReceiversAry[i])){
			$("#emailMsgError").text("請填寫正確的電子郵件地址");
			$("#emailMsgError").css('display', "");
			return false;
		}else{
			$("#emailMsgError").text("");
			$("#emailMsgError").css('display', "none");
		}
	}
	
	return true;
}

//檢查email是否合法
function verifyEmail(email) { 
	var regex = /^([a-zA-Z0-9_\.\-\+])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	if(!regex.test(email)) {
		return false;
	}else{
		return true;
	}
}

//send mail
function sendMailAjax(){
	
	$.ajax({
		type : "post",
		dataType : "json",
		url : "sendRetargetingTrackingMailAjax.html",
		data : {
			"mailReceivers" : $('#mailReceivers').val(),
			"paId" : $('#paid').val(),
			"trackingSeq" : $('#trackingSeq').val(),
			"codeType" : codeType
		},
		timeout : 30000,
		error : function(xhr) {
			alert("系統繁忙，請稍後再試！");
		},
		success : function(response, status) {
		}
	}).done(function(response) {
		if (response.status == "ERROR") {
			alert(response.msg);
		} else {
			alert(response.msg);
//			$('#sendMail').attr("disabled","disabled");
//			$('#sendMail').val("發送完成");
		}
	});
	
}

function changeTopicboxStyle(obj,clickCodeType){
	$('.topic-box').removeClass('selected');
	$(obj).addClass('selected');
	
	var codeContent='';
	var paid = $('#paid').val();
	var trackingSeq = $('#trackingSeq').val();
	$('#code1').val('');
	
	if (clickCodeType == '0'){
		$(".copySuccess").css('display', "none");
		$("#dynamicAdDescription").css('display', "none");
		
		codeType=0;
		codeContent =
			'<script  id="pcadscript" language="javascript" async src="https://pacl.pchome.com.tw/js/ptag.js"></script>\n'+
			'<script>\n'+
			'  window.dataLayer = window.dataLayer || [];\n'+
			'  function ptag(){dataLayer.push(arguments);}\n'+
			'  ptag({"paid":"'+paid+'"});\n'+
			'  ptag("event","tracking",{\n'+
			'  "tracking_id":"'+trackingSeq+'"\n'+
			'  });\n'+   
			'</script>'
	}else if(clickCodeType == '1'){
		$(".copySuccess").css('display', "none");
		$("#dynamicAdDescription").css('display', "");
		
		codeType=1;
		codeContent =
			'<script  id="pcadscript" language="javascript" async src="https://pacl.pchome.com.tw/js/ptag.js"></script>\n'+
			'<script>\n'+
			'  window.dataLayer = window.dataLayer || [];\n'+
			'  function ptag(){dataLayer.push(arguments);}\n'+
			'  ptag({"paid":"'+paid+'"});\n'+
			'  ptag("event","tracking",{\n'+
			'  "tracking_id":"'+trackingSeq+'",\n'+
			'  "prod_id":"",\n'+
			'  "prod_price":"",\n'+
			'  "prod_dis":""\n'+
			'  });\n'+   
			'</script>'
	}
	
	$('#code1').val(codeContent);
}



