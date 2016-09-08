var freeBoolean = true;
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

		   if($("#accept").attr("checked")  && freeBoolean){
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
			
			if(!freeBoolean){
				return false;
			}
			
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
				$("#showMoney").text("此序號已使用");
				freeBoolean = false;
			}else if(response.giftStatus == "expired"){
				$("#showMoney").text("此序號逾時已失效");
				freeBoolean = false;
			}else if(response.giftStatus == "expired"){
				$("#showMoney").text("此序號不可在註冊時使用");
				freeBoolean = false;
			}else if(response.giftStatus == "errStyle"){
				$("#showMoney").text("此序號不可在註冊時使用");
				freeBoolean = false;
			}else if(response.giftStatus == null){
				$("#showMoney").text("此序號不存在，請輸入正確序號");
				freeBoolean = false;
			}else if(response.giftStatus == "unused"){
				$("#addMoney").attr("readonly", "true");
				$("#giftSno").attr("readonly", "true");
				$("#showMoney").text("NT$" + FormatNumber(response.giftMoney) + "  " + response.giftActionName);
				$("#addMoney").val(response.addMoney);
				$("#giftMoney").val(response.giftMoney);
				$("#addTax").html(response.addTax);
				$("#total").html(response.addTotal);
				freeBoolean = true;
				
				if(response.payment == "N"){
					$("#addMoneyDiv").hide();
					$("#addMoney").val("1000");
				}
				
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
	$("#addMoneyDiv").show();
	$("#addMoney").val("1000");
	var addTax = Math.round(1000 * 0.05);
	var total = Math.round(1000 * 1.05);	
	$("#addTax").html(addTax);
	$("#total").html(total);
	freeBoolean = true;
}

function FormatNumber(n) { 
	n += ""; 
	var arr = n.split("."); 
	var re = /(\d{1,3})(?=(\d{3})+$)/g; 
	return arr[0].replace(re,"$1,") + (arr.length == 2 ? "."+arr[1] : ""); 
} 