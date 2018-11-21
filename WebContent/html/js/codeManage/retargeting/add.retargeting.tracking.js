var trackingRangeDate=1;
var codeType=0;
$(document).ready(function() {
	console.log('天數: '+trackingRangeDate)
	//追蹤行銷效期	
	$('.slider-pointer.type1').on('click',function(){
		trackingRangeDate=1;
		$('.track-handler').css('left',0);
		console.log('天數: '+trackingRangeDate)
	});
	$('.slider-pointer.type2').on('click',function(){
		trackingRangeDate=7;
		$('.track-handler').css('left',100);
		console.log('天數: '+trackingRangeDate)
	});
	$('.slider-pointer.type3').on('click',function(){
		trackingRangeDate=28;
		$('.track-handler').css('left',400);
		console.log('天數: '+trackingRangeDate)
	})
	
	//發送mail
	$('#sendMail').on('click',function(){
		console.log('mail')
		//檢查全部mail是否合法
		checkMailReceivers();
		//send mail
		sendMailAjax();
	})
	
	//新增再行銷追蹤
	$('#addRetargetingTracking').on('click',function(){
		addRetargetingTrackingAjax();
	})
	
});


//新增再行銷追蹤
function addRetargetingTrackingAjax(){
	$.ajax({
		type : "post",
		dataType : "json",
		url : "addRetargetingTrackingAjax.html",
		data : {
			"trackingSeq" : $('#trackingSeq').val(),
			"trackingName" : $('#retargetingName').val(),
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
		console.log(response);
		if (response.status == "ERROR") {
			alert(response.msg);
		} else {
			alert(response.msg);
			$('#sendMail').attr("disabled","disabled");
			$('#sendMail').val("發送完成");
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
		codeType=0;
		codeContent =
			'<script  id="pcadscript" language="javascript" async src="https://kdpic.pchome.com.tw/js/ptag.js"></script>\n'+
			'<script>\n'+
			'  window.dataLayer = window.dataLayer || [];\n'+
			'  function ptag(){dataLayer.push(arguments);}\n'+
			'  ptag({"paid":'+paid+'});\n'+
			'  ptag("event","tracking",{\n'+
			'  "tracking_id":'+trackingSeq+'\n'+
			'  "});\n'+   
			'</script>'
	}else if(clickCodeType == '1'){
		codeType=1;
		codeContent =
			'<script  id="pcadscript" language="javascript" async src="https://kdpic.pchome.com.tw/js/ptag.js"></script>\n'+
			'<script>\n'+
			'  window.dataLayer = window.dataLayer || [];\n'+
			'  function ptag(){dataLayer.push(arguments);}\n'+
			'  ptag({"paid":'+paid+'});\n'+
			'  ptag("event","tracking",{\n'+
			'  "tracking_id":'+trackingSeq+',\n'+
			'  "prod_id":"",\n'+
			'  "prod_price":"",\n'+
			'  "prod_dis":"",\n'+
			'  "ec_stock_status":"",\n'+
			'  "op1":"",\n'+
			'  "op2":""\n'+
			'  "});\n'+   
			'</script>'
	}
	
	$('#code1').val(codeContent);
}



