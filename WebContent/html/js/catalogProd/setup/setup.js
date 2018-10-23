$(document).ready(function(){
	if($("#cropType").val() != ""){
		if($("#cropType").val() == 0){
			$(".dataupload-wrap").find(".displayimg-box")[0].className = 'displayimg-box selected';
			$(".dataupload-wrap").find(".displayimg-box")[1].className = 'displayimg-box';
		}else if($("#cropType").val() == 1){
			$(".dataupload-wrap").find(".displayimg-box")[0].className = 'displayimg-box ';
			$(".dataupload-wrap").find(".displayimg-box")[1].className = 'displayimg-box selected';
		}
	}
	
	$("#catalog").change(function() {
		$(location).attr('href','prodListCardStyleView.html?catalogSeq='+this.value+'&currentPage=1&pageSizeSelected=10');	
	});
});



function submitSetup(){
	var cropType = null;
	if($(".dataupload-wrap").find(".displayimg-box")[0].className.indexOf("selected") >=0){
		cropType = 0;
	}
	if($(".dataupload-wrap").find(".displayimg-box")[1].className.indexOf("selected") >=0){
		cropType = 1;
	}
	console.log(cropType);
	
	$.ajax({
		url : "catalogSetupSaveAjax.html",
		type : "POST",
		dataType:'json',
		data : {
			"catalogSeq":$("#catalog").val(),
			"cropType":cropType
		},
		success : function(respone) {
			console.log(respone);
			if(respone == "success"){
//				$(location).attr('href','adAddFinish.html?adGroupSeq='+$("#adGroupSeq").val());	
			} else {
				alert(respone);
			}
		},
		error: function(xtl) {
			alert("系統繁忙，請稍後再試！");
		}
	});
	
}