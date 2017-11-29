$(document).ready(function(){
	
	$('#adPrice').bind('blur', function() {
		console.log('ddd');
	});
	
	
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
		if($(this).val() == 0){
			$(".msg").text("當影片播超過三秒即計算為一次有效收視，系統接受最低出價NT$0.5");
			$("#adPrice").val("0.5");
		}else if($(this).val() == 1){
			$(".msg").text("當影片曝光1,000次即計算為一次收費，系統接受最低出價NT$65");
			$("#adPrice").val("65");
		}
	})
});

function doAdGroupaddSubmit(){
	var flag = false;
	var errMsg = '';
	
	if($("#adPriceType").val() == 1){
		var cpmRex = /^[0-9]*[1-9][0-9]*$/;
		flag = cpmRex.test($("#adPrice").val());
		errMsg = '只接受整數...';
	}else if($("#adPriceType").val() == 0){
		var cpvRex = /^-?\d+\.?\d{0,1}$/;
		flag = cpvRex.test($("#adPrice").val());
		errMsg = '只接受整數或小數1位...';
	}
	if(!flag){
		$('#errorMsg').append(errMsg);
		setTimeout(function(){ 
			$('#errorMsg').empty();
		}, 1000);
	}else{
		$("#modifyForm").submit();
	}
}
