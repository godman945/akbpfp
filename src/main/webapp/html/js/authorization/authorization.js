$(document).ready(function(){

	// validate field
	$("#formAuthorization").validate({
		rules: {
			memberName: {
				required: true,
				maxlength: 50
			},
			memberSex: {
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
				required: "請填寫聯絡人姓名.",
				maxlength: "姓名字數限50字."
			},
			memberSex: {
				required: "請選擇姓別."
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
			}
		}
	});
	
	// 生日 
	$("#memberBirthday").datepicker({
		changeYear : true,
		changeMonth : true
    });
				
	// 郵遞區號
	$('#city').twzipcode({
		zipcodeSel: $("#memberZip").val()
	});
	// 地址
	$("#address").val($("#memberAddress").val());

	$('#save').click(function(){
		//取得驗證回傳值
		if($("#formAuthorization").valid() == 1){
			$('#formAuthorization').submit();
		}
	});
	
	// 同意條款
	$("#accept").click(function() {

		   if($("#accept").attr("checked")){
			   $("#save").attr("disabled",false);
		   }else{
			   $("#save").attr("disabled",true);
		   }
			
	});
	
	// 儲值使用者資料
	$("#save").click(function(){
		
		if($("#accept").attr("checked")){
			
			$("#next").attr("disabled",true);
			
			if($("#formAuthorization").valid() == 1){
				
				$("#formAuthorization").submit();
			}
			
		 }
	});
	
});


