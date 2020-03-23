
$(document).ready(function(){
	$.ajax({
	    type : "post",
		url : "sendEmail.html",
		dataType:'json',
	    data: {
	    	
	    },
	    error : function(xhr) {
			alert("系統繁忙，請稍後再試！");
		},
	    success : function(response) {
	    	console.log(response);
	    }
	});

	
})




function sendMailAjax(){
	
	$.ajax({
		url : "sendEmailAjax99999.html",
		type : "POST",
//		dataType:'json',
		data : {
			//"logoDataObj":JSON.stringify(logoDataObj),
		},
		success : function(respone) {
			console.log(respone);
//			if(respone == "success"){
//				//location.href='logo.html'; 
//			} else {
//				console.log(respone);
//			}
		},
		error: function(xtl) {
			console.log(xtl);
			alert("系統繁忙，請稍後再試！");
		}
		}).done(function(msg) {
			console.log(msg);
		});
}

