//自訂轉換追蹤條件
var convertConditionMap= [];
//互動後轉換追溯天數
var clickRangeDate;
//瀏覽後轉換追溯天數
var impRangeDate;
//轉換類型(1.標準轉換追蹤(預設)2.自訂轉換追蹤條件)
var convertType;
//轉換分類(1.查看內容 2.搜尋 3.加到購物車 4.加到購物清單 5.開始結帳 6.新增付款資料 7.購買 8.完成註冊)
var convertClass;
//轉換價值紀錄方式(1.不指定價值2.統一轉換價值3.自訂轉換價值)
var convertPriceType;
//轉換價值
var convertPrice;
//轉換條件數量
var convertRuleNum=0;
//轉換代碼類型(0：瀏覽代碼(預設)、1：點擊代碼)
var convertCodeType;


$(document).ready(function() {
	
	//預設將畫面"選擇轉換追蹤條件、選擇轉換類型、轉換價值、互動後轉換追溯、瀏覽後轉換追溯"，先勾選第1個
	init();
	
	//複製功能
	var clipboard = new ClipboardJS('.btn-copyto');
    clipboard.on('success', function(e) {
    	console.log(e);
    	e.clearSelection();
	});
	clipboard.on('error', function(e) {
		console.log(e);
	});
                    
	
	// 增加篩選條件
    $('.link-addfilter').click(function(){
    	var cloneDom = $('.filter-group').eq(0).clone();
	 	$(cloneDom).find("input").attr("value", "");
	 	$(cloneDom).children()[0].innerHTML = "也必須符合";
		$(cloneDom).append('<div class="icon-kill" onclick="$(this).parent().remove()"></div>');
   		$(cloneDom).appendTo('.filter-wrap');
    })
    
    
    //互動後轉換追溯天數
    $('#clickRangeDate1').on('click',function(){
    	clickRangeDate=1;
    	$(this).parent().find('.track-handler').css('left',0)
    });
    $('#clickRangeDate2').on('click',function(){
    	clickRangeDate=7;
    	$(this).parent().find('.track-handler').css('left',100)
    });
    $('#clickRangeDate3').on('click',function(){
    	clickRangeDate=28;
    	$(this).parent().find('.track-handler').css('left',400)
    });

    //瀏覽後轉換追溯天數
    $('#impRangeDate1').on('click',function(){
    	impRangeDate=1;
    	$(this).parent().find('.track-handler').css('left',0)
    });
    $('#impRangeDate2').on('click',function(){
    	impRangeDate=7;
    	$(this).parent().find('.track-handler').css('left',100)
    });
    $('#impRangeDate3').on('click',function(){
    	impRangeDate=28;
    	$(this).parent().find('.track-handler').css('left',400)
    });
    
    
    //選擇轉換追蹤條件-1.標準轉換追蹤(預設)
    $('#valueselect4').on('click',function(){
    	convertType=1;
    	convertPrice = 0;
    	$('#valueselect1').trigger("click");	//不指定價值
    	$('#unifiedConvertPrice').val("");
    	$('#unifiedConvertPriceMsg').css("display", "none");
    	$('#customConvertPrice').val("");
    	$('#customConvertPriceMsg').css("display", "none");
    });
    
    
    //選擇轉換追蹤條件-2.自訂轉換追蹤條件
    $('#valueselect5').on('click',function(){
    	convertType=2;
    	convertPrice = 0;
    	$('#valueselect1').trigger("click");	//不指定價值
    	$('#unifiedConvertPrice').val("");
    	$('#unifiedConvertPriceMsg').css("display", "none");
    	$('#customConvertPrice').val("");
    	$('#customConvertPriceMsg').css("display", "none");
    });
    
    
    //轉換價值-1.不指定價值
    $('#valueselect1').on('click',function(){
    	convertPriceType=1;
    });
    //轉換價值-2.統一轉換價值
    $('#valueselect2').on('click',function(){
    	convertPriceType=2;
    });
    //轉換價值-3.自訂轉換價值
    $('#valueselect3').on('click',function(){
    	convertPriceType=3;
    });
    
    
    //選擇轉換類型-1.查看內容
    $('#typeselect1').on('click',function(){
    	convertClass=1;
    });
    //選擇轉換類型-2.搜尋
    $('#typeselect2').on('click',function(){
    	convertClass=2;
    });
    //選擇轉換類型-3.加到購物車
    $('#typeselect3').on('click',function(){
    	convertClass=3;
    });
    //選擇轉換類型-4.加到購物清單
    $('#typeselect4').on('click',function(){
    	convertClass=4;
    });
    //選擇轉換類型-5.開始結帳
    $('#typeselect5').on('click',function(){
    	convertClass=5;
    });
    //選擇轉換類型-6.新增付款資料
    $('#typeselect6').on('click',function(){
    	convertClass=6;
    });
    //選擇轉換類型-7.購買
    $('#typeselect7').on('click',function(){
    	convertClass=7;
    });
    //選擇轉換類型-8.完成註冊
    $('#typeselect8').on('click',function(){
    	convertClass=8;
    });
    //選擇轉換類型-9.加到願望清單
    $('#typeselect9').on('click',function(){
    	convertClass=9;
    });
    
	//發送mail
	$('#sendMail').on('click',function(){
		//檢查全部mail是否合法
		if (checkMailReceivers()==true){
			//send mail
			sendMailAjax();
		}
	})
    
	//編輯轉換追蹤
	$('#editConvertTracking').on('click',function(){
		if (checkData() == true){
			prepareData();
			editConvertTrackingAjax();
		}
	})
	
	//複製成功
	$('.btn-copyto').on('click',function(){
	  $(".copySuccess").css('display','');
	});
});



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
		url : "sendConvertTrackingMailAjax.html",
		data : {
			"mailReceivers" : $('#mailReceivers').val(),
			"paId" : $('#paid').val(),
			"convertSeq" : $('#convertSeq').val(),
			"convertCodeType" : convertCodeType
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
//			$('#sendMail').attr("disabled","disabled");
//			$('#sendMail').val("發送完成");
		}
	});
	
}

/**
* 檢查欄位值正確性
*/
function checkData() {
	var checkFlag = true;
	
	//檢查代碼名稱
	if ($('#convertName').val().trim() == ""){
		$('#convertNameMsg').css("display", "");
		checkFlag = false;
	}else{
		$('#convertNameMsg').css("display", "none");
	}
	
		
	//檢查選擇轉換追蹤條件
	if (convertType =="1"){//標準轉換追蹤(預設)
		if(convertPriceType =="2"){//統一轉換價值
			convertPrice =  $('#unifiedConvertPrice').val();
			if (!isInteger(convertPrice)){
				checkFlag = false;
				$('#unifiedConvertPriceMsg').css("display", "");
			}else{
				$('#unifiedConvertPriceMsg').css("display", "none");
			}
		}else if(convertPriceType =="3"){//自訂轉換價值
			convertPrice =  $('#customConvertPrice').val();
			if (!isInteger(convertPrice)){
				checkFlag = false;
				$('#customConvertPriceMsg').css("display", "");
			}else{
				$('#customConvertPriceMsg').css("display", "none");
			}
		}
	}else if (convertType =="2"){//自訂轉換追蹤條件
		//自訂轉換追蹤條件URL
		convertRuleNum = 0;
		$(".filter-group").each(function(index,obj) {
			var conditionStr =$(obj).find("select")[0].value;
			var valueStr =$(obj).find("input")[0].value;
			valueStr =replaceURLprotocolStr(valueStr);
			if ( valueStr != ""){
				convertRuleNum = convertRuleNum+1;
			}
		});
		if(convertRuleNum<1){
			$('.urlMsg').css("display", "");
			checkFlag = false;
		}else{
			$('.urlMsg').css("display", "none");
		}
		
		
		//統一轉換價值
		if(convertPriceType =="2"){
			convertPrice =  $('#unifiedConvertPrice').val();
			if (!isInteger(convertPrice)){
				checkFlag = false;
				$('#unifiedConvertPriceMsg').css("display", "");
			}else{
				$('#unifiedConvertPriceMsg').css("display", "none");
			}
		}
	}
	
	return checkFlag;
};


//判斷url是否需要去除協定
function replaceURLprotocolStr(str) {
	if (str.indexOf("http://")>-1){
		return str.replace("http://", "");
	}
	
	if (str.indexOf("https://")>-1){
		return str.replace("https://", "");
	}
	
	return str;
}

//判斷參數是否為整數(是回傳true、否回傳false)
function isInteger(obj) {
	//不可以為0，第一個數字不能為0，數字中可以有0
	var patten = /^([1-9][0-9]*)$/;
	return patten.test(obj);
	//	var patten = /^(0|[1-9][0-9]*)$/; //可以為0，第一個數字不能為0，數字中可以有0
}


function init(){
	//轉換類型(1.標準轉換追蹤(預設)2.自訂轉換追蹤條件)
	convertType =$('#convertType').val();
	if (convertType =="1"){
		$('#valueselect4').trigger("click");
	}else if (convertType =="2"){
		$('#valueselect5').trigger("click");
	}
	
	//轉換分類(1.查看內容 2.搜尋 3.加到購物車 4.加到購物清單 5.開始結帳 6.新增付款資料 7.購買 8.完成註冊)
	convertClass=$('#convertClass').val();
	if (convertClass =="1"){
		$('#typeselect1').trigger("click");
	}else if (convertClass =="2"){
		$('#typeselect2').trigger("click");
	}else if (convertClass =="3"){
		$('#typeselect3').trigger("click");
	}else if (convertClass =="4"){
		$('#typeselect4').trigger("click");
	}else if (convertClass =="5"){
		$('#typeselect5').trigger("click");
	}else if (convertClass =="6"){
		$('#typeselect6').trigger("click");
	}else if (convertClass =="7"){
		$('#typeselect7').trigger("click");
	}else if (convertClass =="8"){
		$('#typeselect8').trigger("click");
	}else if (convertClass =="9"){
		$('#typeselect9').trigger("click");
	}
	
	//轉換價值紀錄方式(1.不指定價值2.統一轉換價值3.自訂轉換價值)
	convertPriceType=$('#convertPriceType').val();
	convertPrice =$('#convertPrice').val();
	if(convertPriceType =="1"){
		$('#valueselect1').trigger("click");
	}else if (convertPriceType =="2"){
		$('#valueselect2').trigger("click");
	}else if (convertPriceType =="3"){
		$('#valueselect3').trigger("click");
	}
	
	//轉換條件數量(0 沒有條件;有條件是 count(rule 數量))
	convertRuleNum=$('#convertRuleNum').val();
	
	//互動後轉換追溯天數
	clickRangeDate=$('#clickRangeDate').val();
	if(clickRangeDate =="1"){
	 $('#clickRangeDate1').parent().find('.track-handler').css('left',0)
	}else if(clickRangeDate =="7"){
	 $('#clickRangeDate2').parent().find('.track-handler').css('left',100)
	}else if(clickRangeDate =="28"){
	 $('#clickRangeDate3').parent().find('.track-handler').css('left',400)
	}
	 
	//瀏覽後轉換追溯天數
	impRangeDate=$('#impRangeDate').val();
	if(impRangeDate =="1"){
		$('#impRangeDate1').parent().find('.track-handler').css('left',0)
	}else if(impRangeDate =="7"){
		$('#impRangeDate2').parent().find('.track-handler').css('left',100)
	}else if(impRangeDate =="28"){
		$('#impRangeDate3').parent().find('.track-handler').css('left',400)
	}
	
	//轉換代碼類型(0：瀏覽代碼(預設)、1：點擊代碼)
	convertCodeType=$('#convertCodeType').val();
	if(convertCodeType =="0"){
		$('#webImpConvertTracking').trigger("click");
	}else if(convertCodeType =="1"){
		$('#webClickConvertTracking').trigger("click");
	}
		
}

//將畫面資料裝進全域變數中
function prepareData(){
	//計算 轉換條件數量
	convertConditionMap = [];
	convertRuleNum = 0;
	if (convertType =="1"){//標準轉換追蹤(預設)
		convertRuleNum =0;		
	}else if (convertType =="2"){//自訂轉換追蹤條件
		$(".filter-group").each(function(index,obj) {
			var conditionStr =$(obj).find("select")[0].value;
			var valueStr =$(obj).find("input")[0].value;
			valueStr = replaceURLprotocolStr(valueStr);
			if (valueStr != ""){
				convertRuleNum = convertRuleNum+1;
				var map = new Object();
				map["condition"] =conditionStr;
				map["value"] =valueStr;
				convertConditionMap.push(map);
			}
		});
	}
	
	
	//依轉換價值類型，取得轉換價值
	if (convertPriceType =="1"){
		convertPrice = "0";
	}else if(convertPriceType =="2"){
		convertPrice = $('#unifiedConvertPrice').val();
	}else if(convertPriceType =="3"){
		convertPrice = $('#customConvertPrice').val();
	}
}

//編輯轉換追蹤
function editConvertTrackingAjax(){
	$.ajax({
		type : "post",
		dataType : "json",
		url : "editConvertTrackingAjax.html",
		data : {
			"convertSeq" : $('#convertSeq').val(),
			"convertName" : $('#convertName').val().trim(),
			"paId" : $('#paid').val(),
			"convertCodeType" : convertCodeType,
			"convertType" : convertType,
			"clickRangeDate" : clickRangeDate,
			"impRangeDate" : impRangeDate,
			"convertClass" : convertClass,
			"convertPriceType" : convertPriceType,
			"convertPrice" : convertPrice,
			"convertRuleNum": convertRuleNum,
			"convertConditionMap": JSON.stringify(convertConditionMap)
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
			$(location).attr('href','convertTrackingListView.html');
		} else {
			alert(response.msg);
		}
	});
}


//網頁曝光追蹤
function webImpConvertTracking(obj){
	convertCodeType = "0";
	$('.codeswitchTag').removeClass('select');
	obj.addClass('select');
    $('#codeEventWeb').fadeIn('fast');
    $('#codeEventClick').css('display','none');
    $('#copyCodeSuccess1') .css('display','none');
    $('#copyCodeSuccess2') .css('display','none');
}


//點擊事件追蹤
function webClickConvertTracking(obj){
	convertCodeType = "1";
	$('.codeswitchTag').removeClass('select');
	obj.addClass('select');
    $('#codeEventClick').fadeIn('fast');
    $('#codeEventWeb').css('display','none');
    $('#copyCodeSuccess1') .css('display','none');
    $('#copyCodeSuccess2') .css('display','none');
}




