﻿var freeBoolean = true;
$(document).ready(function(){

	// 同意條款
	$("#accept").click(function() {

		   if($("#accept").attr("checked")  && freeBoolean){
			   $("#save").attr("disabled",false);
		   }else{
			   $("#save").attr("disabled",true);
		   }
			
	});
	
	// validate field
	$("#registerForm").validate({
		rules: {
			companyName:{
				required: "#company:checked"
			},
			registration: {
				required: "#company:checked",
				 digits: true,
				minlength: 8
			},
			urlYN: {
				equalTo: "#urlY:checked"
			},
			urlAddress: {
				required: "#urlY:checked"
			},
			memberSex: {
				required: true
			},
			memberName: {
				required: true,
				maxlength: 50
			},
			memberBirthday: {
				required: true
			},
			memberTelephone: {
				required: true,	
				telePhone: true
			},
			memberMobile: {
				required: true,
				mobile: true
			},
			address: {
				checkField: "#county",
				required: true
			},
			addMoney: {
				required: true,
				digits: true,
				min: 3000
			}
		},
		messages: {
			companyName:{
				required: "請填寫公司名稱."
			},
			registration: {
				required: "請填寫統一編號.",
				digits: "統一編號只能填寫數字.",
				minlength: "統一編號為八碼數字."
			},
			urlYN: {
				equalTo: "請提供宣傳網址."
			},
			urlAddress: {
				required: "請填寫宣傳網址."
			},
			memberSex: {
				required: "請選擇姓別."
			},
			memberName: {
				required: "請填寫聯絡人姓名.",
				maxlength: "姓名字數限50字."
			},
			memberBirthday: {
				required: "請填寫生日."
			},
			memberTelephone: {
				required: "請填寫聯絡電話.",
				telePhone: "請填寫區域號碼，若有分機請用#字號區隔，例如07-88888888#233."
			},
			memberMobile: {
				required: "請填寫手機號碼.",
				mobile: "手機號碼格式為 09 開頭，共 10 碼."
			},
			address: {
				checkField: "請選擇縣市.",
				required: "請填寫聯絡地址."
			},
			address: {
				checkField: "請選擇縣市.",
				required: "請填寫聯絡地址."
			},
			addMoney: {
				required: "請填寫儲值金額.",
				digits: "儲值金額只能填寫數字.",
				min: "儲值金額至少要3000元."
			}
		},
		onfocusout: false,
	    invalidHandler: function(form, validator) {
	        var errors = validator.numberOfInvalids();
	        if (errors) {                    
	            validator.errorList[0].element.focus();
	        }
	    } 
	});
	
	$("#addMoney").keyup(function(){
		
		var addMoney = $("#addMoney").val();
		
		if(addMoney >= 3000){
			var addTax = Math.round(addMoney * 0.05);
			var total = Math.round(addMoney * 1.05);
			
			$("#addTax").html(addTax);
			$("#total").html(total);
		}else{
			$("#addTax").html(0);
			$("#total").html(0);
		}
	});
	
	var category = $("input[name='category']:checked").val();
	if(category=="1"){
		$("tr:eq(1)").hide();
		$("tr:eq(2)").hide();
	}	
	
	// 個人戶
	$("#customer").click(function(){
		$("tr:eq(1)").hide();
		$("tr:eq(2)").hide();
	});
	// 公司戶
	$("#company").click(function(){
		$("tr:eq(1)").show();
		$("tr:eq(2)").show();
	});
	
	// 生日 
	$("#memberBirthday").datepicker({
		changeYear : true,
		changeMonth : true,
		yearRange: "1915:-10"
    });
				
	// 郵遞區號
	$('#city').twzipcode({
		zipcodeSel: $("#memberZip").val()
	});
	// 地址
	$("#address").val($("#memberAddress").val());
	
	// 儲值付款
	$("#save").click(function(){
		
		if($("#accept").attr("checked")){
			
			$("#next").attr("disabled",true);
			
			checkGiftSno();
			
			if(!freeBoolean){
				return false;
			}
			
			if($("#registerForm").valid() == 1){
				
				var urlAddress = $("#urlAddress").val();
				
				$.blockUI({
					message: "<img src='html/img/LoadingWait.gif' />",
					css: {},
					overlayCSS: { backgroundColor: '#FFFFFF', opacity: 0.6, border: '1px solid #000000' }
				});		
				
				//先確認是否無效網址
				$.ajax({
					url: "checkUrl.html",
					data: {
						url: urlAddress
					},
					type: "post",
					dataType: "json",
					success: function(response, state){
						
						$.unblockUI();
						
						if(response.urlState == 200){
							$('#registerForm').submit();
						}else{
							$("<label generated='true' class='error'>網址連結失效，請輸入有效連結網址！</label>").insertAfter("#urlMsg");
						}
					},
					error: function(xtl) {
						alert("系統繁忙，請稍後再試！");
					}
				});
				
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
					$("#addMoney").val("3000");
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
	$("#addMoney").val("3000");
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