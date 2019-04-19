$(document).ready(function(){

	// validate field
	$("#modifyForm").validate({
		rules: {

			memberName: {
				required: true
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
			}
		},
		messages: {
			memberName: {
				required: "請填寫聯絡人姓名."
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
			}
		}
	});
	
	
	// 生日 
	$("#memberBirthday").datepicker({
		changeYear : true
	});
				
	// 郵遞區號
	$('#city').twzipcode({
		zipcodeSel: $("#memberZip").val()
	});
	// 地址
	$("#address").val($("#memberAddress").val());

	
	$('#save').click(function(){

		//取得驗證回傳值
		if($("#modifyForm").valid() == 1){
			
			$('#modifyForm').submit();			
			//alertSuccessDialog(0);
		}
		
	});

	$("#cancel").click(function(){
		window.location = "accountInfo.html";
	});
});


