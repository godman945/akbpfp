





function sendMailAjax(){
	
	
	if($("#userNM").val() !="" && $("#userTEL").val() !="" && $("#userEM").val() !=""){
		$(document).ready(function(){
			$.ajax({
			    type : "post",
				url : "sendEmail.html",
				dataType:'json',
			    data: {
			    	"applyEmailUserEmail": $("#userEM").val(),
			    	"applyEmailUserName": $("#userNM").val(),
			    	"applyEmailUserphone": $("#userTEL").val(),
			    },
			    error : function(xhr) {
					alert("系統繁忙，請稍後再試！");
				},
			    success : function(response) {
			    	alert("『PChome聯播網會有專人於2個工作天內與您聯繫！』");
			    }
			});
		})
	}else{
		if($("#userNM").val() ==""){
			alert("請填寫姓名!");	
		}
		if($("#userTEL").val() ==""){
			alert("請填寫電話!");	
		}
		if($("#userEM").val() ==""){
			alert("請填寫Email!");	
		}
		
	}
	
	
}

