$(document).ready(function(){

	var msg = $('#messageId');
	if (msg.val()!="") {
		alert(msg.val());
	}

	// validate field
	$("#inviteForm").validate({
		rules: {			
			memberEmail: {
				required: true,
				email: true
			},
			nickName: {
				required: true
			}
		},
		messages: {
			
			memberEmail: {
				required: "請填寫電子信箱.",
				email: "電子信箱格式不正確"
			},
			nickName: {
				required: "請填暱稱."
			}
		},
		errorPlacement: function(error, element) {  //錯誤訊息位置設置                             
			if(element.attr("id") == "memberEmail") {//需要特殊處理驗證元素
				error.insertAfter("#checkEmail");  //控制顯示位置
			}else{
				error.insertAfter(element);
			} 
		}
	});
	
	$("#checkEmail").click(function(){
		
		//取得驗證回傳值
		if($("#memberEmail").valid() == 1){

			checkUserEmail(false);
		}
		
	});
	
	$("#invite").click(function(){
		
		//取得驗證回傳值
		if($("#memberEmail").valid() == 1){

			checkUserEmail(true);
		}
		
	});
	
	$("#cancel").click(function(){
		window.location = "accountUsers.html";
	});
		
});

function checkUserEmail(fromCheck){
	
	//先確認是否無效網址
	$.ajax({
		url: "checkUserEmail.html",
		data: {
			email: $("#memberEmail").val()
		},
		type: "post",
		dataType: "json",
		success: function(response, state){
			
			$("label").remove(".error");
			
			if (response.checkUserEmailResult == "") {
				$("<label for='memberEmail' generated='true' class='error'>可邀請的使用者</label>").insertAfter("#checkEmail");
				if(fromCheck){
					$("#inviteForm").submit();
				}
			} else {
				$("<label for='memberEmail' generated='true' class='error'>" + response.checkUserEmailResult + "</label>").insertAfter("#checkEmail");
			}
		},
		error: function(xtl) {
			alert("系統繁忙，請稍後再試！！");
		}
	});
}


