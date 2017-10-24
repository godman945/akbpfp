$(document).ready(function(){
	chkLinkURL();
	//影片連結檢查
	var adLinkUrlWord = 1024;
	$('#adLinkURL').bind('keyup', function() {
		$("#chkLinkURL").text('網址檢查中...');
		$("#chkLinkURL").css('color','red');
		
		var wordLength = adLinkUrlWord - $("#adLinkURL").val().length;
		if(wordLength >= 0){
			$("#spanAdLinkURL").text('已輸入'+$("#adLinkURL").val().length+'字，剩'+wordLength+'字');
			$("#spanAdLinkURL").css('color','');
		}else if(wordLength < 0){
			wordLength = Math.abs(wordLength);
			$("#spanAdLinkURL").text('已輸入'+$("#adLinkURL").val().length+'字，超過'+wordLength+'字');
			$("#spanAdLinkURL").css('color','red');
		}
	});
	
	//檢查網址blur事件
	$("#adLinkURL").blur(function() {
		if($("#adLinkURL").length > 0 && $("#adLinkURL").val() != ""){
			chkLinkURL();
		}else{
			$("#chkLinkURL").text('請輸入廣告連結網址');
			$("#chkLinkURL").css('color','red');
		}
	});
});

function chkLinkURL(){
	$.ajax({
		type: "POST",
		url: "checkAdUrl.html",
		data: { url: $("#adLinkURL").val()},
	}).done(function(msg) {
		if(msg == 'false'){
			$("#chkLinkURL").text('');
			$("#chkLinkURL").text('請輸入正確廣告連結網址');
			$("#chkLinkURL").css('color','red');
		}else if(msg == 'true'){
			$("#chkLinkURL").text('網址確認正確');
			$("#chkLinkURL").css('color','green');
		}
	});
}

function multipartImgUuploadSubmit(){
	if($("#chkLinkURL").text() !="網址確認正確"){
		return false;
	}
	var alt = "提醒您，您的廣告將在3工作天(周一到周五)審核完成(不含例假日)，並於廣告審核完成後開始播放";
	if(confirm(alt)) {
		$.ajax({
			url : "adAddVideoEditSaveAjax.html",
			type : "POST",
			dataType:'json',
			data : {
				"adLinkURL":$("#adLinkURL").val(),
				"adSeq":$("#adSeq").val()
			},
			success : function(respone) {
				if(respone == "success"){
					$(location).attr('href','adAdVideoView.html?adGroupSeq='+$("#adGroupSeq").val());	
				} else {
					alert("更新失敗");
				}
			},
			error: function(xtl) {
				alert("系統繁忙，請稍後再試！");
			}
		});
	}
}