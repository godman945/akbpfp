$(document).ready(function(){
	

	
})


//檢查字數用
function checkAdInputTextLength(type1,textDom,type2,hintDom){
	var text = null;
	var hint = null;
	if(type1 == 'id'){
		text = $("#"+textDom);
	}else{
		text = $("."+textDom);
	}
	if(type2 == 'id'){
		hint = $("#"+hintDom);
	}else{
		hint = $("."+hintDom);
	}
	
	text.bind('blur', function() {
		if(text.val().length > 0 && text.val().length <= 4) {
			alert("廣告標題太短，將影響您的廣告效果。\n\n廣告標題應重點說明您推廣的產品、活動、服務，依您的目標客群，撰寫他們有興趣的廣告標題。");
		}
	});
}
//提是剩餘字數用
function hintAdInputTextLength(type1,textDom,type2,hintDom){
	var text = null;
	var hint = null;
	if(type1 == 'id'){
		text = $("#"+textDom);
	}else{
		text = $("."+textDom);
	}
	if(type2 == 'id'){
		hint = $("#"+hintDom);
	}else{
		hint = $("."+hintDom);
	}
	
	var maxWordLength = text[0].maxLength;
	text.bind('keyup', function() {
		var wordLength = text.val().length;
		var count = maxWordLength - wordLength;
		if(wordLength > 0 && count >= 0 ){
			hint.text("已輸入"+wordLength+"字，剩"+count+"字");
		}
	});
}
//檢察連結網址
//檢查網址是否有效
function urlCheck(adUrl){
	
	
	
	
//	var adUrlHint = urlType != "adShowURL" ? "chkLinkURL" : "chkShowURL";
//	if(adUrl != "" && urlType.indexOf("show.pchome.com.tw") < 0) {
//		if(ValidURL(adUrl)) {
//			$("#"+adUrlHint).text("網址檢查中");
//			$.ajax({
//				type: "POST",
//				url: "checkAdUrl.html",
//				data: { url: adUrl}
//			}).done(function( msg ) {
//				if(msg == "false") {
//					$("#chkShowURL").css("color","red");
//					$("#"+adUrlHint).css("color","red");
//					if(adUrlHint == "chkLinkURL"){
//						$("#"+adUrlHint).text("請輸入正確的廣告連結網址");
//					}else if(adUrlHint == "chkShowURL"){
//						$("#"+adUrlHint).text("請輸入正確的廣告顯示網址");
//					}
//					
//					if(urlType == "adShowURL"){
//						ShowUrl = false;
//					}else{
//						LinkUrl = false;
//					}
//				} else {
//					$("#"+urlType).css("color","");
//					$("#"+adUrlHint).css("color","green");
//					$("#"+adUrlHint).text("網址確認正確");
//					if(urlType == "adShowURL"){
//						ShowUrl = true;
//					}else{
//						LinkUrl = true;
//					}
//				}
//			});
//		}else {
//			if(urlType == 'adLinkURL'){
//				if($("#adLinkURL").length > 0){
//					$('#chkLinkURL').css("color","red");
//					$("#"+adUrlHint).text("請輸入正確廣告連結網址.");
//				}
//			}
//			if(urlType == 'adShowURL'){
//				if($("#adShowURL").length > 0){
//					$('#chkShowURL').css("color","red");
//					$("#"+adUrlHint).text("請輸入正確廣告顯示網址.");
//				}
//			}
//		}
//	}else{
//		if(urlType == 'adLinkURL'){
//			if($("#adLinkURL").val().length > 0){
//				$('#chkLinkURL').css("color","red");
//				$("#"+adUrlHint).text("請輸入正確廣告連結網址.");
//				return false;
//			}else{
//				$('#chkLinkURL').css("color","red");
//				$("#"+adUrlHint).text("請輸入廣告連結網址.");
//				return false;
//			}
//			
//		}
//		if(urlType == 'adShowURL'){
//			$("#"+adUrlHint).text("請輸入正確廣告顯示網址.");
//			return false;
//		}else{
//			$("#"+adUrlHint).text("請輸入廣告顯示網址.");
//			return false;
//		}
//	}
//	//連結網址字數檢查
//	chkWord($('#adShowURL'), "spanAdShowURL");
//	chkWord($('#adLinkURL'), "spanAdLinkURL");
}


