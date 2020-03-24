





function sendMailAjax(){
	if($("#userNM").val() ==""){
		alert("請填寫姓名!");
		return;
	}else if($("#userTEL").val() ==""){
		alert("請填寫電話!");
		return;
	}else if($("#userEM").val() ==""){
		alert("請填寫Email!");
		return;
	}
	
	if($("#userEM").val() !=""){
		var emailRegxp = /^\S+@\S+\.\S{2,}$/;
		if (emailRegxp.test($("#userEM").val()) != true){
			alert('電子信箱格式錯誤');
			return;
		}
	}
	
	
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
			    	$("#userEM").val(""),
			    	$("#userNM").val(""),
			    	$("#userTEL").val(""),
			    	alert("『PChome聯播網會有專人於2個工作天內與您聯繫！』");
			    }
			});
	})
	
	
}

