var submitFlag = true;
$(document).ready(function(){
	$('#adPrice').keyup(function(){
		var errMsg = '';
		var price = $("#adPrice").val();
		if($("#adPriceType").val() == 0){
			var cpvRex = /^-?\d+\.?\d{0,1}$/;
			submitFlag = cpvRex.test(price);
			if(!submitFlag){
				errMsg = '只接受整數或小數1位...';
			}
			if(price < 0.5){
				submitFlag = false;
				errMsg = '出價不得低於系統接受的最低單次收視出價 NT$0.5';
			}
		}
		if($("#adPriceType").val() == 1){
			var cpmRex = /^[0-9]*[1-9][0-9]*$/;
			submitFlag = cpmRex.test(price);
			if(!submitFlag){
				errMsg = '只接受整數...';
			}
			if(price < 65){
				submitFlag = false;
				errMsg = '出價不得低於系統接受的最低千次曝光出價 NT$65';
			}
		}
		
		if(!submitFlag){
			$('#errorMsg').empty();
			$('#errorMsg').append(errMsg);
			return false;
		}else{
			$('#errorMsg').empty();
			$("#userPrice").val(price);
		}
		
		$.ajax({
			url: "adGroupSuggestPrice.html",
			data:{
				"userprice": $("#adPrice").val()
			},
			type: "post",
			dataType: "json",
			success: function(response, status){
				$("#showRate").html(response.adAsideRate);
			},
				error: function(xtl) {
					alert("系統繁忙，請稍後再試！");
				}
		 })
	})
	
	
	$('#adPrice').click(function(){
		var errMsg = '';
		var price = $("#adPrice").val();
		if($("#adPriceType").val() == 0){
			var cpvRex = /^-?\d+\.?\d{0,1}$/;
			submitFlag = cpvRex.test(price);
			if(!submitFlag){
				errMsg = '只接受整數或小數1位...';
			}
			if(price < 0.5){
				submitFlag = false;
				errMsg = '出價不得低於系統接受的最低單次收視出價 NT$0.5';
			}
		}
		if($("#adPriceType").val() == 1){
			var cpmRex = /^[0-9]*[1-9][0-9]*$/;
			submitFlag = cpmRex.test(price);
			if(!submitFlag){
				errMsg = '只接受整數...';
			}
			if(price < 65){
				submitFlag = false;
				errMsg = '出價不得低於系統接受的最低千次曝光出價 NT$65';
			}
		}
		
		if(!submitFlag){
			$('#errorMsg').empty();
			$('#errorMsg').append(errMsg);
			return false;
		}else{
			$('#errorMsg').empty();
			$("#userPrice").val(price);
		}
		
		$.ajax({
			url: "adGroupSuggestPrice.html",
			data:{
				"userprice": $("#adPrice").val()
			},
			type: "post",
			dataType: "json",
			success: function(response, status){
				$("#showRate").html(response.adAsideRate);
			},
				error: function(xtl) {
					alert("系統繁忙，請稍後再試！");
				}
		 })
	})
	
	
	
	var showSearchPrice = $("#showSearchPrice").val();
	if(showSearchPrice == "no"){
		$("#searchTr").hide();
	}
	
	var showChannelPrice = $("#showChannelPrice").val();
	if(showChannelPrice == "no"){
		$("#channelTr").hide();
	}
	
	$('#cancel').click(function(){
		$("#modifyForm")[0].reset();
		//window.location.href = $("#backPage").val();
		refererHref($("#backPage").val());
	});
	
	function refererHref(url) {
		if (/MSIE (\d+\.\d+);/.test(navigator.userAgent) || /MSIE(\d+\.\d+);/.test(navigator.userAgent)){
			var referLink = document.createElement('a');
			referLink.href = url;
			document.body.appendChild(referLink);
			referLink.click();
		} else {
			location.href = url;
		}
	}
	
	//預設影音廣告基本價格與收費方式
	if($("#adPriceType").val() == 0){
		$(".msg").text("當影片播超過三秒即計算為一次有效收視，系統接受最低出價NT$0.5");
		$("#adPrice").val("0.5");
	}else if($("#adPriceType").val() == 1){
		$(".msg").text("當影片曝光1,000次即計算為一次收費，系統接受最低出價NT$65");
		$("#adPrice").val("65");
	}
	
	$("#adPriceType").change(function(){
		$(".msg").text('');
		$('#errorMsg').empty();
		if($(this).val() == 0){
			$(".msg").text("當影片播超過三秒即計算為一次有效收視，系統接受最低出價NT$0.5");
			$("#adPrice").val("0.5");
		}else if($(this).val() == 1){
			$(".msg").text("當影片曝光1,000次即計算為一次收費，系統接受最低出價NT$65");
			$("#adPrice").val("65");
		}
		
		$.ajax({
			url: "adGroupSuggestPrice.html",
			data:{
				"userprice": $("#adPrice").val()
			},
			type: "post",
			dataType: "json",
			success: function(response, status){
				console.log(response);
				$("#showRate").html(response.adAsideRate);
			},
				error: function(xtl) {
					alert("系統繁忙，請稍後再試！");
				}
		 })
		
	})
	
});


function doAdGroupaddSubmit(){
	if(submitFlag){
		$("#modifyForm").submit();
	}
}

