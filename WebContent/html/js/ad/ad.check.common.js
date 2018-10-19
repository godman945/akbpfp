$(document).ready(function(){
	

	
})


//檢查字數用
function checkAdInputTextLength(type1,textDom,type2,hintDom,isShow,msg){
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
		if(text.val().length > 0 && text.val().length <= 4 && isShow) {
			alert(msg);
		}
	});
}
//提示剩餘字數用
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
	text.bind('keyup', function(callback) {
		var wordLength = text.val().length;
		var count = maxWordLength - wordLength;
		if(wordLength > 0 && count >= 0 ){
			hint.text("已輸入"+wordLength+"字，剩"+count+"字");
		}
	});
}
//檢察連結網址
//檢查網址是否有效
function urlCheck(type1,domName,type2,checkAdurl){
	var adUrlDom = null;
	var hintAdUrlDom = null;
	
	if(type1 == 'id'){
		adUrlDom = $("#"+domName);
	}else{
		adUrlDom = $("."+domName);
	}
	
	if(type2 == 'id'){
		hintAdUrlDom = $("#"+checkAdurl);
	}else{
		hintAdUrlDom = $("."+checkAdurl);
	}
	
	adUrlDom.bind('blur', function() {
		hintAdUrlDom.text("檢查網址中");
//		console.log(adUrlDom.val());
		$.ajax({
			type: "POST",
			url: "checkAdUrl.html",
			data: { url: adUrlDom.val()}
		}).done(function(msg) {
			if(msg == 'true'){
				hintAdUrlDom.text("網址確認正確");
				hintAdUrlDom.css("color","green");
//				console.log(msg);
			}else {
				hintAdUrlDom.text("請輸入正確的廣告連結網址");
				hintAdUrlDom.css("color","red");
			}
		});
	});
}


