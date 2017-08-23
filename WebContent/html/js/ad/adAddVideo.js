$(document).ready(function(){
	console.log("DD");
	
	$.ajax({
		url: "chkVideoUrl.html",
		data:{
			adVideoUrl: "https://www.youtube.com/watch?v=x0WSucyB5hU"
		},
		type:"POST",
		dataType:"JSON",
		success:function(response, status){
			console.log(response);
		},
		error: function(xtl) {
			alert("系統繁忙，請稍後再試！");
		}
	});
	
});

