$(document).ready(function(){
	
	// 同意條款
	$("#accept").click(function() {

		   if($("#accept").attr("checked")){
			   $("#save").attr("disabled",false);
		   }else{
			   $("#save").attr("disabled",true);
		   }
			
	});
	
	// validate field
	$("#addMoneyForm").validate({
		rules: {
			addMoney: {
				required: true,
				 digits: true,
				 min: 500
			}
		},
		messages: {
			addMoney: {
				required: "請填儲值金額.",
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
	
	$("#save").click(function(){
		
		//取得驗證回傳值
		if($("#addMoneyForm").valid() == 1){
			$('#addMoneyForm').submit();
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
		url: "accountCheckGiftSno.html",
		data: {
			"giftSno": $("#giftSno").val()
		},
		type: "post",
		dataType: "json",
		success: function(response, status) {			
			
			if(response.giftStatus == "used"){
				$("#showMoney").text("此序號已使用");
			}else if(response.giftStatus == "expired"){
				$("#showMoney").text("此序號逾時已失效");
			}else if(response.giftStatus == "expired"){
				$("#showMoney").text("此序號不可在儲值時使用");
			}else if(response.giftStatus == "notUsed"){
				$("#showMoney").text("已參加過該活動，請勿再輸入該活動序號");
			}else if(response.giftStatus == null){
				$("#showMoney").text("此序號不存在，請輸入正確序號");
			}else if(response.giftStatus == "unused"){
				$("#addMoney").attr("readonly", "true");
				$("#giftSno").attr("readonly", "true");
				$("#showMoney").text("NT$" + FormatNumber(response.giftMoney) + "  " + response.giftActionName);
				$("#addMoney").val(response.addMoney);
				$("#giftMoney").val(response.giftMoney);
				$("#addTax").html(response.addTax);
				$("#total").html(response.addTotal);
				
				if(response.payment == "N"){
					$(".addTr").hide();
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
	$(".addTr").show();
	$("#addMoney").val("1000");
	var addTax = Math.round(1000 * 0.05);
	var total = Math.round(1000 * 1.05);	
	$("#addTax").html(addTax);
	$("#total").html(total);
}

function FormatNumber(n) { 
	n += ""; 
	var arr = n.split("."); 
	var re = /(\d{1,3})(?=(\d{3})+$)/g; 
	return arr[0].replace(re,"$1,") + (arr.length == 2 ? "."+arr[1] : ""); 
} 
