$(document).ready(function(){
	$('#save').click(function(){
		var result = adVideoUrlCheck();
		console.log(result);
	});
});


//檢查廣告網址
function adVideoUrlCheck(){
	var regx = new RegExp(/^[hH][tT][tT][pP]([sS]?):\/\/(\S+\.)+\S{2,}$/);
		   
	console.log($("#videoUrl").val());
	console.log(regx.test($("#videoUrl").val()));
	if(!regx.test($("#videoUrl").val())){
		return {'result':false,'msg':'網址格式錯誤'}
	}
	
	$.ajax({
		url: "chkVideoUrl.html",
		data:{
			adVideoUrl: $("#videoUrl").val()
		},
		type:"POST",
		dataType:"JSON",
		success:function(response, status){
			console.log(response);
		},
		error: function(xtl) {
			//alert("系統繁忙，請稍後再試！");
		}
	}).done(function (response) {
		console.log(response);
	});
}