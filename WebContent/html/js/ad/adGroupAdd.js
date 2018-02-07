var submitFlag = true;
var adGroupNameFlag = true;
$(document).ready(function(){
	if($('#adPriceTypeValue').val() != null){
		$('#adPriceType').val($('#adPriceTypeValue').val());
	}
	
	
	if($('#adGroupName').val().length > 20){
		$('#adGroupNameMsg').text('分類名稱輸入字數已超過上限 20 字');
		var position = $('#adGroupNameMsg').offset();  
		var x = position.left;  
		var y = position.top;  
		window.scrollTo(x,y);
		adGroupNameFlag = false;
	}
	
	$('#adGroupName').keyup(function(){
		$('#adGroupNameMsg').empty();
		if($('#adGroupName').val().length > 20){
			$('#adGroupNameMsg').text('分類名稱輸入字數已超過上限 20 字');
			adGroupNameFlag = false;
		}else{
			$('#adGroupNameMsg').empty();
			adGroupNameFlag = true;
		}
	})
	
	//手動輸入價格
	$('#adPrice').keyup(function(){
		var errMsg = '';
		var price = $("#adPrice").val();
		if($("#adPriceType").val() == 0){
			$("#adPrice").attr('step',0.1);
			$("#adPrice").attr('min',0.5);
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
			$("#adPrice").attr('step',1);
			$("#adPrice").attr('min',65);
			
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
		var userprice = $("#adPrice").val();
		if($("#adPriceType").val() == 1){
			console.log('CPM 出價:'+userprice);
			userprice = userprice - 58; 
			console.log('CPM 出價換算後:'+userprice);
			console.log('目前系統最高出價:10');
		}else if($("#adPriceType").val() == 0){
			console.log('CPV 出價:'+userprice);
			userprice = (userprice * 10) + 10;
			console.log('CPV 出價換算後:'+userprice);
			console.log('目前系統最高出價:10');
		}
		changeRate(userprice);
	})
	
	//點擊元件變更價格
	$('#adPrice').change(function(){
		var errMsg = '';
		var price = $("#adPrice").val();
		if($("#adPriceType").val() == 0){
			$("#adPrice").attr('step',0.1);
			$("#adPrice").attr('min',0.5);
			
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
			$("#adPrice").attr('step',1);
			$("#adPrice").attr('min',65);
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
		
		var userprice = $("#adPrice").val();
		if($("#adPriceType").val() == 1){
			console.log('CPM 出價:'+userprice);
			userprice = userprice - 58; 
			console.log('CPM 出價換算後:'+userprice);
			console.log('目前系統最高出價:10');
		}else if($("#adPriceType").val() == 0){
			console.log('CPV 出價:'+userprice);
			userprice = (userprice * 10) + 10;
			console.log('CPV 出價換算後:'+userprice);
			console.log('目前系統最高出價:10');
		}
		
		changeRate(userprice);
	})
	
	
	//變更出價方式
	$("#adPriceType").change(function(){
		$(".msg").text('');
		$('#errorMsg').empty();
		var adUserAmount = $("#adUserAmount").val();
		if($(this).val() == 0){
			$("#adPrice").attr('step',0.1);
			$("#adPrice").attr('min',0.5);
			var sysprice = 0.5 + (adUserAmount / 100);
			$("#adPrice").val(sysprice.toFixed(1));
			$(".msg").text("當影片播超過三秒即計算為一次有效收視，系統接受最低出價NT$0.5");
		}else if($(this).val() == 1){
			$("#adPrice").attr('step',1);
			$("#adPrice").attr('min',65);
			sysprice = 65 + parseInt(adUserAmount);
			$("#adPrice").val(sysprice);
			$(".msg").text("千次曝光計費，系統接受最低出價NT$65");
		}
		
		var userprice = $("#adPrice").val();
		if($("#adPriceType").val() == 1){
			console.log('CPM 出價:'+userprice);
			userprice = userprice - 58;
			console.log('CPM 出價換算後:'+userprice);
			console.log('目前系統最高出價:10');
		}else if($("#adPriceType").val() == 0){
			console.log('CPV 出價:'+userprice);
			userprice = (userprice * 10) + 10;
			console.log('CPV 出價換算後:'+userprice);
			console.log('目前系統最高出價:10');
		}
		changeRate(userprice);
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
	$(".msg").text("當影片播超過三秒即計算為一次有效收視，系統接受最低出價NT$0.5");
});


function doAdGroupaddSubmit(){
	if(submitFlag && adGroupNameFlag){
		$("#modifyForm").submit();
	}
}

function changeRate(userprice){
	$.ajax({
	url: "adGroupSuggestPrice.html",
	data:{
		"userprice": userprice
	},
	type: "post",
	dataType: "json",
	success: function(response, status){
//		console.log(response);
		$("#showRate").html(response.adAsideRate+'%');
	},
		error: function(xtl) {
			alert("系統繁忙，請稍後再試！");
		}
 })
}

