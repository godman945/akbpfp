$(document).ready(function(){
	
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
	console.log('AAA');
}
