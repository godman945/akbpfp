$(document).ready(function(){
	var userAgent = window.navigator.userAgent.toLowerCase();
	chkData();
	// 修正 jQuery.browser.version 比對問題
	$.browser.version = (userAgent.match( /.(?:rv|it|ra|ie)[\/: ]([\d.]+)/ ) || [0, '0'])[1];
	var version = $.browser.version;
	if(userAgent.indexOf("msie") > 0 && (version == "6.0" || version == "7.0" || version == "8.0" || version == "9.0")) {
	} else {
		$(document).on("mouseout", ".inputPlaceholderTmg", function() {
//			chkWord($(this).val().length, $(this).attr("maxlength"), $("#" + $(this).data("value")));
		});
		$(document).on("mouseout", ".inputPlaceholderTmgTextarea", function() {
//			chkWord($(this).val().length, $(this).attr("maxlength"), $("#" + $(this).data("value")));
		});
	}
	//載入資料時顯示字數提示
	function chkData(){
		var length = null;
		if($("#adTitle").val()!=""){
			length = $("#adTitle").attr("maxlength");
			$("#spanAdTitle").text("已輸入"+ $("#adTitle").val().length +"字");
			if(length == $("#adTitle").val().length){
				$("#chkAdTitle").text("廣告標題輸入字數已達上限" + $("#adTitle").val().length+ "字 ");	
				$('#chkAdTitle').css("color","blue");
			}
			
		}
		if($("#adContent").val()!=""){
			length = $("#adContent").attr("maxlength");
			$("#spanAdContent").text("已輸入"+ $("#adContent").val().length +"字");
			if(length == $("#adContent").val().length){
				$("#chkAdContent").text("廣告標題輸入字數已達上限" + $("#adContent").val().length+ "字");	
				$('#chkAdContent').css("color","blue");
			}
		}
		
		if($("#adLinkURL").val()!=""){
//			$("#spanAdLinkURL").text("已輸入 "+ $("#adLinkURL").val().length +"字，剩"+(1024 - parseInt($("#adLinkURL").val().length))+"字" );
//			if($("#adLinkURL").val().length == 1024) {
//				$('#chkLinkURL').css("color","blue");
//				$("#chkLinkURL").text("廣告連結網址輸入字數已達上限1024字");
//			}else if($("#adLinkURL").val().length >1024){
//				$('#spanAdLinkURL').css("color","red");
//				$('#'+showId).text("已輸入" + length + "字，超過" + (length - 1024) + "字");
//			}
			urlCheck("adLinkURL",$("#adLinkURL").val());
		}
		
		if($("#adShowURL").val()!=""){
			length = $("#adShowURL").attr("maxlength");
			$("#spanAdShowURL").text("已輸入"+ $("#adShowURL").val().length +"字");
			if(length == $("#adShowURL").val().length){
				$("#chkShowURL").text("廣告標題輸入字數已達上限" + $("#adShowURL").val().length+ "字");	
				$('#chkShowURL').css("color","blue");
			}
			urlCheck("adShowURL",$("#adShowURL").val());
		}
	}
	
	
	
	//檢查網址是否有效
	function urlCheck(urlType,adUrl){
		var adUrlHint = urlType != "adShowURL" ? "chkLinkURL" : "chkShowURL";
		if(adUrl != "" && urlType.indexOf("example.pchome.com.tw") < 0) {
			if(ValidURL(adUrl)) {
				$("#"+urlType).css("color","red");
				$("#"+adUrlHint).text("網址檢查中");
				$.ajax({
					type: "POST",
					url: "checkAdUrl.html",
					data: { url: adUrl}
				}).done(function( msg ) {
					if(msg == "false") {
						$("#"+adUrlHint).css("color","red");
						$("#"+adUrlHint).text("請輸入正確的廣告顯示網址");
						if(urlType == "adShowURL"){
							ShowUrl = false;
						}else{
							LinkUrl = false;
						}
					} else {
						$("#"+urlType).css("color","");
						$("#"+adUrlHint).css("color","green");
						$("#"+adUrlHint).text("網址確認正確");
						if(urlType == "adShowURL"){
							ShowUrl = true;
						}else{
							LinkUrl = true;
						}
					}
				});
			}else {
				$("#"+urlType).css("color","red");
				$("#"+adUrlHint).text("請填寫廣告顯示網址.");
				$("#previewURL").text($("#"+urlType).attr("placeholder"));
				ShowUrl = false;
			}
		}
		//連結網址字數檢查
		chkWord($('#adLinkURL'), "spanAdLinkURL");
		//連結網域字數檢查
		chkWord($('#adShowURL'), "spanAdShowURL");
	}
	
	
	
	
	function chkWord(el, showId) {
		var length = el.val().length;
		var maxlength = el.attr("maxlength");
		if(el.val() == el.attr("placeholder")) {
			$('#'+showId).text("已輸入0字，剩" + maxlength + "字");
		}else{
			if(showId=="spanAdLinkURL"){
				if(length <=1024){
				$('#spanAdLinkURL').css("color","");
				$('#'+showId).text("已輸入" + length + "字，剩" + (1024 - length) + "字");
			}else{
				$('#spanAdLinkURL').css("color","red");
				$('#'+showId).text("已輸入" + length + "字，超過" + (length - 1024) + "字");
				LinkUrl = false;
			}
			}else{
				$('#'+showId).text("已輸入" + length + "字，剩" + (maxlength - length) + "字");
			}
		}
	}
	
	
	
	
	
	
	
//	function chkWord(length, maxlength, elem) {
//		elem.text("已輸入" + length + "字，剩" + (maxlength - length) + "字");
//	}
});
