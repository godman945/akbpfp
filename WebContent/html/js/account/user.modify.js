$(document).ready(function(){
	
	// validate field
	$("#modifyForm").validate({
		rules: {
			nickName: {
				required: true
			}
		},
		messages: {
			nickName: {
				required: "請填寫暱稱."
			}
		}
	});
	
	$("#save").click(function(){
		
		if($("#modifyForm").valid() == 1){
			
			$("#modifyForm").submit();
		}
	});
	
	$("#cancel").click(function(){
		window.location = "accountUsers.html";
	});
});
