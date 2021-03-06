﻿$(document).ready(function(){
	// 點選取消
	$('#cancel').click(function() {
		location.href = 'adActionView.html';
	});
	
	if($("#defaultHasActionRecord").val() == "N"){
		$('#setAdActionName').show();
		$('#setGroupName').show();
		setUserActionData();
	}else{
		//設定廣告活動預設資料
		initDefaultActionInfo();
		
		//切換廣告名稱
		$("#adActionNameSelect").change(function(){
			changeActionDefaultData(this.value);
		});
		
		//切換分類名稱
		$('#adGroupNameSelect').change(function(){
			var groupInfo = $("#adGroupNameSelect").val().split('_');
			$("#adGroupChannelPrice").val(groupInfo[2]);
			changeGroupDefaultData(groupInfo[0]+"_"+groupInfo[1]);
			getAdAsideRate();
		});
		
		$('#adType').change(function(){
			if($("#addAdActionName").val() == '返回建立過的廣告'){
				if($('#adType').val() == '2'){
					$("#searchTr").hide();
					$("#channelTr").show();
				}else if($('#adType').val() == '1'){
					$("#searchTr").show();
					$("#channelTr").hide();
				}else{
					$("#searchTr").show();
					$("#channelTr").show();
				}
			}
		});
		
		//設定聯播網廣告出價
		$("#priceType").html('聯播網廣告出價');
		var groupInfo = $("#adGroupNameSelect").val().split('_');
		$("#adGroupChannelPrice").val(groupInfo[2]);
		getAdAsideRate();
	}
	
	initDate();
	function initDate() {
		$("#adActionStartDate").datepicker({
            dateFormat: "yy-mm-dd",
            yearRange:"-10:+10",
            minDate: "-6M",
            maxDate: 0
        });
		
		$("#adActionStartDate").datepicker({
            dateFormat: "yy-mm-dd",
            yearRange:"-10:+10",
            minDate: "-6M",
            maxDate: 0
		});
	}
	
	$("#adGroupChannelPrice").keyup(function(){
		var cpvRex = /^-?\d+\.?\d{0}$/;
		var price = $("#adGroupChannelPrice").val();
		var submitFlag = cpvRex.test(price);
		if(!submitFlag){
			errMsg = '只接受整數...';
			$('#errorMsg').html(errMsg);
		}else if(price < 3){
			errMsg = '聯播廣告出價最少為3元';
			$('#errorMsg').html(errMsg);
		}else{
			$('#errorMsg').empty();
			getAdAsideRate();
		}
	});
	
	
	$('#adGroupChannelPrice').change(function(){
		var cpvRex = /^-?\d+\.?\d{0}$/;
		var price = $("#adGroupChannelPrice").val();
		var submitFlag = cpvRex.test(price);
		if(!submitFlag){
			errMsg = '只接受整數...';
			$('#errorMsg').html(errMsg);
		}else if(price < 3){
			errMsg = '聯播廣告出價最少為3元';
			$('#errorMsg').html(errMsg);
		}else{
			$('#errorMsg').empty();
			getAdAsideRate();
		}
	});
	
	$("#setAdActionName").keyup(function(){
		if($("#setAdActionName").val() != ""){
			$.ajax({
				url: "chkAdActionName.html",
				data: {
					adActionName: $("#setAdActionName").val()
				},
				type: "post",
				success: function(response, state){
					if(response == 'false'){
						$("#actionNameErrorMsg").html('廣告名稱重複，請重新輸入');
						$("#actionNameErrorMsg").css('display','inline');
						$("#actionNameErrorMsg").show();
					}else{
						$("#actionNameErrorMsg").html('');
						$("#actionNameErrorMsg").css('display','none');
					}
				},
				error: function(xtl) {
					errorDialogBtn("錯誤訊息","系統繁忙，請稍後再試！");
				}
			});
		}
	});
	
	$("#setGroupName").keyup(function(){
		if($("#setGroupName").val() != ''){
			$("#setGroupNameErrorMsg").html('');
			$("#setGroupNameErrorMsg").css('display','none');
		}else{
			$("#setGroupNameErrorMsg").html('請輸入分類名稱.');
			$("#setGroupNameErrorMsg").css('display','inline');
			$("#setGroupNameErrorMsg").show();
		}
	});
	
	$("#adGroupSearchPrice").keyup(function(){
		var cpmRex = /^[0-9]*[1-9][0-9]*$/;
		var submitFlag = cpmRex.test($('#adGroupSearchPrice').val());
		if(!submitFlag){
			$("#searchPricEerrorMsg").css('display','inline');
			$("#searchPricEerrorMsg").text('搜尋廣告出價須正整數');
		}else{
			$("#searchPricEerrorMsg").css('display','none');
			$("#searchPricEerrorMsg").text('');
			
			if($("#adGroupSearchPrice").val() < 3){
				$("#searchPricEerrorMsg").css('display','inline');
				$("#searchPricEerrorMsg").text('搜尋廣告出價最少為3元');
			}else{
				$("#searchPricEerrorMsg").css('display','none');
				$("#searchPricEerrorMsg").text('');
			}
		}
	});
	
	
	$("#adActionMax").keyup(function(){
		if($("#adActionMax").val() == ""){
			$("#adActionMaxErrorMsg").html('請填寫每日預算金額.');
			$("#adActionMaxErrorMsg").css('display','inline');
		}else if($("#adActionMax").val() < 100){
			$("#adActionMaxErrorMsg").html('為讓您的廣告有足夠曝光量，每日預算至少為NT$100');
			$("#adActionMaxErrorMsg").css('display','inline');
		}else if($("#adActionMax").val() >= 100){
			$("#adActionMaxErrorMsg").html('');
			$("#adActionMaxErrorMsg").css('display','none');
		}
	});
	
	$('#adActionMax').change(function(){
		if($("#adActionMax").val() == ""){
			$("#adActionMaxErrorMsg").html('請填寫每日預算金額.');
			$("#adActionMaxErrorMsg").css('display','inline');
		}else if($("#adActionMax").val() < 100){
			$("#adActionMaxErrorMsg").html('為讓您的廣告有足夠曝光量，每日預算至少為NT$100');
			$("#adActionMaxErrorMsg").css('display','inline');
		}else if($("#adActionMax").val() >= 100){
			$("#adActionMaxErrorMsg").html('');
			$("#adActionMaxErrorMsg").css('display','none');
		}
	});
});


function getAdAsideRate(){
	$.ajax({
		url: "getAdAsideRate.html",
		data: {
			adActionMax: $("#adGroupChannelPrice").val()
		},
		type: "post",
		success: function(response, state){
			if(response != null) {
				if($.isNumeric(response)) {
					var showRate = (response == "0.0" ? "0" : showRate);
					$("#showRate").text(showRate + "%");
				}
			} else {
				$("#showRate").text("0%");
			}
		},
		error: function(xtl) {
			errorDialogBtn("錯誤訊息","系統繁忙，請稍後再試！");
		}
	});
}

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
	if ($("#defaultAdType").val() != -1) {
		$("#adType").prop("disabled", true);
		$("#adType").val($("#defaultAdType").val());
	}
	
	if($('#adType').val() == '2'){
		$("#searchTr").hide();
		$("#channelTr").show();
	}else if($('#adType').val() == '1'){
		$("#searchTr").show();
		$("#channelTr").hide();
	}else{
		$("#searchTr").show();
		$("#channelTr").show();
	}
	
	if ($("#defaultAdOperatingRule").val() == 'MEDIA') {
		$("#adOperatingRuleSelect").prop("disabled", true);
		$("#adOperatingRuleSelect").val(0);
	}
	
	if ($("#defaultAdDevice").val() != -1) {
		$("#adDevice").prop("disabled", true);
		$("#adDevice").val($("#defaultAdDevice").val());
	}
	
	if ($("#defaultAdActionStartDate").val() != "") {
		$("#adActionStartDate").val($("#defaultAdActionStartDate").val());
		$("#adDevice").prop("disabled", true);
		$("#adActionStartDate").datepicker("disable");
	}
	
	if($("#defaultAdActionEndDate").val() != ""){
		// 結束日期不是無期限，則勾選Y帶上日期
		if ($("#defaultAdActionEndDate").val() != "3000-12-31") {
			$("#adActionEndDate").val($("#defaultAdActionEndDate").val());
			$("input[name='selAdActionEndDate'][value='Y']").prop("checked", true);
		} else {
			$("#adActionEndDate").val("");
			$("input[name='selAdActionEndDate'][value='N']").prop("checked", true);
		}
		$("#setAdTimeAndMaxPriceTable input[type=radio]").each(function(index, obj) {
			$(this).prop("disabled",true);
		});
		$("#adActionEndDate").datepicker("disable");
	}
	
	if ($("#defaultAdActionMax").val() != "") {
		$("#adActionMax").prop("disabled", true);
		$("#adActionMax").val($("#defaultAdActionMax").val());
	}
	
	if ($("#adGroupChannelPrice").val() != "") {
		$("#adGroupChannelPrice").prop("disabled", true);
	}
	
	
	if ($("#defaultAdGroupSearchPriceType").val() != "") {
		if($("#defaultAdGroupSearchPriceType").val() == 1){
    		$("input[name='adGroupSearchPriceType'][value='1']").prop("checked", true);
    	}
    	if($("#defaultAdGroupSearchPriceType").val() == 2){
    		$("input[name='adGroupSearchPriceType'][value='2']").prop("checked", true);
    	}
	}
	
	if($('#defaultAdGroupSearchPrice').val() != ''){
		$("#adGroupSearchPrice").val($('#defaultAdGroupSearchPrice').val());
	}
	
}

function cleanEndDate() {
	$("#adActionEndDate").val("");
}

//取得活動明細
function changeActionDefaultData(adActionSeq){
	$('#loadingWaitBlock').block(maskingConfig);
	var actionData = null;
	$.ajax({
	    type: "post",
	    dataType: "json",
	    url: "adActionInfoAjax.html",
	    data: {
	          "adActionSeq": adActionSeq
	    },
	    timeout: 30000,
	    error: function(xhr){
	    	$('#loadingWaitBlock').unblock();
	        alert('Ajax request 發生錯誤');
	    },
	    success:function(response, status){
	    	actionData = response;
//	    	console.log(actionData);
//	    	$("#defaultAdGroupSearchPriceType").val(response.defaultAdGroupSearchPriceType);
//	    	$("#defaultAdGroupSearchPrice").val(response.defaultAdGroupSearchPrice);
//	    	$("#defaultAdGroupChannelPrice").val(response.defaultAdGroupChannelPrice);
//    		$('#loadingWaitBlock').unblock();
		}
	}).done(function() {
		setDefaultActionData(actionData);
		//觸發分類名稱事件，更換聯播網廣告出價金額
		$("#adGroupNameSelect").trigger("change");
	});
}

//取得分類明細
function changeGroupDefaultData(adGroupSeq){
	$.ajax({
	    type: "post",
	    dataType: "json",
	    url: "adGroupInfoAjax.html",
	    data: {
	          "adGroupSeq": adGroupSeq
	    },
	    timeout: 30000,
	    error: function(xhr){
	    	$('#loadingWaitBlock').unblock();
	        alert('Ajax request 發生錯誤');
	    },
	    success:function(response, status){
	    	$("#defaultAdGroupSearchPriceType").val(response.adGroupSearchPriceType);
	    	$("#defaultAdGroupSearchPrice").val(response.adGroupSearchPrice);
	    	$("#defaultAdGroupChannelPrice").val(response.adGroupChannelPrice);
	    	
	    	if(response.adGroupSearchPriceType == 1){
	    		$("input[name='adGroupSearchPriceType'][value='1']").prop("checked", true);
	    		
	    	}
	    	if(response.adGroupSearchPriceType == 2){
	    		$("input[name='adGroupSearchPriceType'][value='2']").prop("checked", true);
	    	}
	    	$("#adGroupSearchPrice").val(response.adGroupSearchPrice);
	    	
	    	$('#loadingWaitBlock').unblock();
		}
	}).done(function() {
    	
	});
	
	
}



function setDefaultActionData(actionData){
	$('#actionNameErrorMsg').html('');
	$('#actionNameErrorMsg').hide();
	$('#setGroupNameErrorMsg').html('');
	$('#setGroupNameErrorMsg').hide();
	$('#adActionMaxErrorMsg').html('');
	$('#adActionMaxErrorMsg').hide();
	$("#defaultAdType").val(actionData["defaultAdType"]);
	$("#defaultAdOperatingRule").val(actionData["defaultAdOperatingRule"]);
	$("#defaultAdDevice").val(actionData["defaultAdDevice"]);
	$("#defaultAdActionMax").val(actionData["defaultAdActionMax"]);
	$("#defaultAdActionEndDate").val(actionData["defaultAdActionEndDate"]);
	$("#defaultAdActionStartDate").val(actionData["defaultAdActionStartDate"]);
	$("#adGroupNameSelect").empty();
	for (var key in actionData["adGroups"]) {
		$('#adGroupNameSelect').append('<option value="'+key+'">'+(actionData["adGroups"])[key]+'</option>');
	}
	initDefaultActionInfo();
}

//開啟所有隱藏項目
function setUserActionData(){
	$('#adGroupChannelPrice').prop("disabled",false);
	$('#adType').prop("disabled",false);
	$('#adOperatingRuleSelect').prop("disabled",false);
	
	$('#adDevice').prop("disabled",false);
	if($('#userCategory').val() == '1'){
		$($('#adDevice').children()[1]).hide();
		$($('#adDevice').children()[2]).hide();
		$($('#adType').children()[2]).hide();
	}else{
		$($('#adDevice').children()[1]).show();
		$($('#adDevice').children()[2]).show();
		$($('#adType').children()[2]).show();
	}
	
	$("#adActionStartDate").datepicker('enable');
	$("#adActionEndDate").datepicker('enable');
	
	$('#adActionMax').prop("disabled",false);
	$("#adActionMax").val('');
	var d = new Date().toISOString().slice(0,10); 
	$("#adActionStartDate").val(d);
	$("#adActionEndDate").val('');
	$("input[name='selAdActionEndDate'][value='N']").prop("checked", true);
	$("#setAdTimeAndMaxPriceTable input[type=radio]").each(function(index, obj) {
		$(this).prop("disabled", false);
	});
	
	$('[name="adGroupSearchPriceType"]')[0].disabled = false;
	$('[name="adGroupSearchPriceType"]')[1].disabled = false;
	$('[name="adGroupSearchPriceType"]')[0].checked  = false;
	$('[name="adGroupSearchPriceType"]')[1].checked  = true;
	$("#adGroupSearchPrice")[0].disabled = false;
	$("#adGroupSearchPrice").val($("#defaultAdGroupSearchPrice").val());
	
}

function addAdaction(){
	if($("#defaultHasActionRecord").val() == "N"){
		return false;
	}
	if($('#addAdActionName').val() == '新增廣告'){
		$('#adActionNameSelect').hide();
		$('#setAdActionName').show();
		$('#adGroupNameSelect').hide();
		$('#setGroupName').show();
		$('#addAdActionName').val('返回建立過的廣告');
		setUserActionData();
		$("#priceType").html('系統建議出價');
		$("#adGroupChannelPrice").val($("#defaultSysPrice").val());
		$("#adGroupSearchPrice").val("3");
		getAdAsideRate();
	}else if($('#addAdActionName').val() == '返回建立過的廣告'){
		changeActionDefaultData($("#adActionNameSelect option:selected" ).val());
		$("#adActionNameSelect").show();
		$("#setAdActionName").hide();
		$('#addAdActionName').val('新增廣告');
		$('#setAdActionName').val('');
		$('#setGroupName').val('');
		$('#setGroupName').hide();
		$('#adGroupNameSelect').show();
		$("#priceType").html('聯播網廣告出價');
		var groupInfo = $("#adGroupNameSelect").val().split('_');
		$("#adGroupChannelPrice").val(groupInfo[2]);
		$('[name="adGroupSearchPriceType"]')[0].disabled = true;
		$('[name="adGroupSearchPriceType"]')[1].disabled = true;
		$('[name="adGroupSearchPriceType"]')[0].checked  = false;
		$('[name="adGroupSearchPriceType"]')[1].checked  = false;
		$("#adGroupSearchPrice").val('');
		$("#adGroupSearchPrice")[0].disabled = true;
		
		
		getAdAsideRate();
	}
}

function opennots(id) {
	$("#shownotes"+id).css("visibility", "visible");
}

function closenots(id) {
	$("#shownotes"+id).css("visibility", "hidden");
}


function checkSubmit(){
	if(($("#setAdActionName").val() == '' && $('#addAdActionName').val() == '返回建立過的廣告') || ($("#setAdActionName").val() == '' && $("#defaultHasActionRecord").val() == "N")){
		$("#actionNameErrorMsg").html('請輸入廣告名稱');
		$("#actionNameErrorMsg").css('display','inline');
		$("#actionNameErrorMsg").show();
	}
	
	if($("#adActionMax").val() == ""){
		$("#adActionMaxErrorMsg").html('請填寫每日預算金額.');
		$("#adActionMaxErrorMsg").css('display','inline');
	}else if($("#adActionMax").val() < 100){
		$("#adActionMaxErrorMsg").html('為讓您的廣告有足夠曝光量，每日預算至少為NT$100');
		$("#adActionMaxErrorMsg").css('display','inline');
	}
	
	if(($('#setGroupName').val() == '' && $('#addAdActionName').val() == '返回建立過的廣告') || ($("#setGroupName").val() == '' && $("#defaultHasActionRecord").val() == "N")){
		$("#setGroupNameErrorMsg").html('請輸入分類名稱.');
		$("#setGroupNameErrorMsg").css('display','inline');
	}else{
		$("#setGroupNameErrorMsg").html('');
		$("#setGroupNameErrorMsg").css('display','none');
	}
	
	
}

function alex(){
	checkSubmit();
	var submitFlag = true;
	if($('#errorMsg').text() != '' || $('#adActionMaxErrorMsg').text() != '' || $('#actionNameErrorMsg').text() != '' || $('#setGroupNameErrorMsg').text() != '' || $('#searchPricEerrorMsg').text() != ''){
		submitFlag = false;
	}
	
	if(submitFlag){
		var adActionName = $('#setAdActionName').val();
		var adGroupName = $('#setGroupName').val();
		var adActionStartDate = $('#adActionStartDate').val();
		var adActionEndDate = $('#adActionEndDate').val();
		var adGroupSeq = '';
		if($('#adGroupNameSelect').val() != undefined){
			adGroupSeq = $('#adGroupNameSelect').val();
			adGroupSeq = $('#adGroupNameSelect').val().split('_');
			adGroupSeq = adGroupSeq[0]+"_"+adGroupSeq[1];
		}
		var adActionSeq = $('#adActionNameSelect').val();
		var adType = $('#adType').val();
		var adDevice = $('#adDevice').val();
		var adActionMax = $('#adActionMax').val();
		var adGroupChannelPrice = $('#adGroupChannelPrice').val();
		var adOperatingRule = $('#adOperatingRuleSelect').val();
		var adGroupSearchPriceType = $('input[name=adGroupSearchPriceType]:checked').val();
		var adGroupSearchPrice = $("#adGroupSearchPrice").val();
		
		var alt = "提醒您，您的廣告將在3工作天(周一到周五)審核完成(不含例假日)，並於廣告審核完成後開始播放";
		if(confirm(alt)) {
			$.blockUI({
			    message: "<h1>製作新廣告中，請稍後...</h1>",
			    css: {
		            width: '500px',
		            height: '65px',
		            opacity: .9,
		            border:         '3px solid #aaa',
		            backgroundColor:'#fff',
		            textAlign:      'center',
		            cursor:         'wait',
		            '-webkit-border-radius': '10px',
		            '-moz-border-radius':    '10px'
		        },
		        fadeIn: 1000, 
		        timeout:   300, 
		        onBlock: function() {
		    		$.ajax({
					url : "adAddFastPublisSaveAjax.html",
					type : "POST",
					dataType:'json',
					data : {
						adActionName:adActionName,
						adGroupName:adGroupName,
						adActionStartDate:adActionStartDate,
						adActionEndDate:adActionEndDate,
						adGroupSeq:adGroupSeq,
						adActionSeq:adActionSeq,
						adType:adType,
						adDevice:adDevice,
						adActionMax:adActionMax,
						adGroupChannelPrice:adGroupChannelPrice,
						adOperatingRule:adOperatingRule,
						adGroupSearchPriceType : adGroupSearchPriceType,
						adGroupSearchPrice : adGroupSearchPrice
					},
					success : function(respone) {
						window.location = "adAddFinish.html?adGroupSeq="+respone;
					},
					error: function(xtl) {
						alert("系統繁忙，請稍後再試！");
					}
				});
		    		
		        }
			});
		}
	}
}
