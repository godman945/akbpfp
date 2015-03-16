$(document).ready(function(){
	
	// validate field
	$("#registerForm").validate({
		rules: {
			
			addMoney: {
				required: true,
				digits: true,
				min: 500
			}
		},
		messages: {
			addMoney: {
				required: "請填寫儲值金額.",
				digits: "儲值金額只能填寫數字.",
				min: "儲值金額至少要500元."
			}
		}
	});
	
	$("#addMoney").keyup(function(){
		
		var addMoney = $("#addMoney").val();
		
		if(addMoney >= 500){
			var addTax = Math.round(addMoney * 0.05);
			var total = Math.round(addMoney * 1.05);
			
			$("#addTax").html(addTax);
			$("#total").html(total);
		}else{
			$("#addTax").html(0);
			$("#total").html(0);
		}
	});
	
	// 郵遞區號
	$('#city').twzipcode({
		zipcodeSel: $("#memberZip").val()		
	});
	
	$("#address").val($("#memberAddress").val());
	$("#county").attr("style","display:none");
	$("#district").attr("style","display:none");
	$("#address").attr("style","display:none");
	
	// 地址
	$("<span>"+$("#county").val()+$("#district").val()+$("#address").val()+"</span>").insertBefore("#city");
	$('#city').remove();
	
	// 同意條款
	$("#accept").click(function() {

		   if($("#accept").attr("checked")){
			   $("#save").attr("disabled",false);
		   }else{
			   $("#save").attr("disabled",true);
		   }
			
	});
	
	// 儲值付款
	$("#save").click(function(){
		
		if($("#accept").attr("checked")){
			
			$("#next").attr("disabled",true);
			
			checkGiftSno();
			
			if($("#registerForm").valid() == 1){
				
				$("#registerForm").submit();
			}
			
		 }

	});
	
	// 確認序號
	$("#giftSno").hover(function(){
		checkGiftSno();
	});
	
	// 清除序號
	$("#btnClrGift").click(function(){
		
		clearGift();
	});
	
});

function checkGiftSno() {
	if($.trim($("#giftSno").val()) != ""){
		checkGift();
	}
}

function checkGift(){
	
	$.ajax({
		url: "checkGiftSno.html",
		data: {
			"giftSno": $("#giftSno").val()
		},
		type: "post",
		dataType: "json",
		success: function(response, status) {			
			
			if(response.giftStatus == "used"){
				$("#showMoney").text("禮金序號已使用");
			}else if(response.giftStatus == "expired"){
				$("#showMoney").text("禮金序號已到期，無法使用");
			}else if(response.giftStatus == null){
				$("#showMoney").text("此序號不存在，請輸入正確序號");
			}else if(response.giftStatus == "unused"){
				$("#addMoney").attr("readonly", "true");
				$("#giftSno").attr("readonly", "true");
				$("#showMoney").text(response.giftMoney);
				$("#addMoney").val(response.giftMoney);
				$("#giftMoney").val(response.giftMoney);
				
			}
		},
		error: function(xtl) {
			errorDialogBtn("錯誤訊息","系統繁忙，請稍後再試！");
		}
	});
}

function clearGift(){
	
	$("#giftSno").val("");
	$("#showMoney").text("");
	$("#giftMoney").val("");
	$("#addMoney").removeAttr("readonly");
	$("#giftSno").removeAttr("readonly");
	
}