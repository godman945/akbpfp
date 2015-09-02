$(document).ready(function(){
	$("#uploadFile").hide();
	
	var LinkUrl = false;
	var ShowUrl = false;
	var pages = -1;
	var errId = "";
	// 檢查字串是否有$
	function isAllowKW(str) {
		if (str.indexOf("$") >= 0){
			return false;
		} else {
			return true;
		}
	}

	// 檢查資料是否正確
	$('#adTitle').bind('blur', function() {
		if($('#adTitle').val().length > 0 && $('#adTitle').val().length <= 4) {
			//$("#shownotes1").show();
			alert("廣告標題太短，將影響您的廣告效果。\n\n廣告標題應重點說明您推廣的產品、活動、服務，依您的目標客群，撰寫他們有興趣的廣告標題。");
		}
		chk_adTitle();
	});
	$('#adTitle').bind('keyup', function() {
		chk_adTitle();
	});
	function chk_adTitle() {
		var back = false;
		var maxlength = $('#adTitle').attr("maxlength");
		var adTitle = $('#adTitle').val();
		var length = adTitle.length;
		if(adTitle == "") {
			$('#chkAdTitle').css("color","red");
			$("#chkAdTitle").text("請輸入廣告標題.");
			$("#previewTitle").text($('#adTitle').attr("placeholder"));
		} else if(adTitle == $('#adTitle').attr("placeholder")) {
			$("#previewTitle").text($('#adTitle').attr("placeholder"));
		} else if(adTitle != "" && !isAllowKW(adTitle)) {
			$('#chkAdTitle').css("color","red");
			$("#chkAdTitle").text("廣告標題不得輸入'$'");
		} else {
			$("#chkAdTitle").text("");
			$("#previewTitle").text(adTitle);
			if(length == maxlength) {
				$('#chkAdTitle').css("color","blue");
				$("#chkAdTitle").text("廣告標題輸入字數已達上限" +maxlength+ "字");
				back = true;
			} else if(length > maxlength) { 
				$('#chkAdTitle').css("color","red");
				$("#chkAdTitle").text("廣告標題最多輸入" +maxlength+ "字");
			} else {
				setData();
				back = true;
			}
		}
		chkWord($('#adTitle'), "spanAdTitle");
		return back;
	}

	$('#adContent').bind('blur', function() {
		if($('#adContent').val().length > 0 && $('#adContent').val().length <= 6) {
			//$("#shownotes2").show();
			alert("廣告內容太短、說明不清，都將影響您的廣告效果。\n\n建議用直接、吸睛的文字，清晰具體的描述產品、服務、活動，才能增加您的廣告吸引力，提升廣告成效。");
		}
		chk_adContent();
	});
	$('#adContent').bind('keyup', function() {
		chk_adContent();
	});
	function chk_adContent() {
		var back = false;
		var maxlength = $('#adContent').attr("maxlength");
		var adContent = $('#adContent').val();
		var length = adContent.length;
		if(adContent == "") {
			$('#chkAdContent').css("color","red");
			$("#chkAdContent").text("請輸入廣告內容.");
			$("#previewContent").text($('#adContent').attr("placeholder"));
		} else if(adContent == $('#adContent').attr("placeholder")) {
			$("#previewContent").text($('#adContent').attr("placeholder"));
		} else if(adContent != "" && !isAllowKW(adContent)) {
			$('#chkAdContent').css("color","red");
			$("#chkAdContent").text("廣告內容不得輸入'$'");
		} else {
			$("#chkAdContent").text("");
			if(length == maxlength) {
				$('#chkAdContent').css("color","blue");
				$("#chkAdContent").text("廣告內容輸入字數已達上限" +maxlength+ "字");
				back = true;
			} else if(length > maxlength) { 
				adContent = $("#adContent").val().slice(0, maxlength);
				$('#adContent').val(adContent);
				$('#chkAdContent').css("color","red");
				$("#chkAdContent").text("廣告內容最多輸入" +maxlength+ "字");
			} else {
				setData();
				back = true;
			}
			$("#previewContent").text(adContent);
		}
		chkWord($('#adContent'), "spanAdContent");
		return back;
	}

	
	
	//提示顯示輸入連結網址字數與檢查
	function chk_adLinkURL() {
		var maxlength = $('#adLinkURL').attr("maxlength");
		var adLinkURL = $('#adLinkURL').val();
		var length = adLinkURL.length;
		
//		if(adLinkURL != $("#adShowURL").val()) {
//			$('#sameRealUrl').attr("checked", false);
//			$("#adShowURL").val("");
//			$("#chkShowURL").text("");
//		}
		if(adLinkURL == "") {
			$('#chkLinkURL').css("color","red");
			$("#chkLinkURL").text("請輸入廣告連結網址.");
		} else if(adLinkURL == $('#previewURL').attr("placeholder")) {
			$("#previewURL").text($('#previewURL').attr("placeholder"));
		} else {
			$("#chkLinkURL").text("");
			if(length == 1024) {
				$('#chkLinkURL').css("color","blue");
				$("#chkLinkURL").text("廣告連結網址輸入字數已達上限1024字");
			} else if(length > maxlength) { 
				$('#chkLinkURL').css("color","red");
				$("#chkLinkURL").text("廣告連結網址最多輸入1024字");
			}
		}
		//連結網址字數檢查
		chkWord($('#adLinkURL'), "spanAdLinkURL");
		setData();
	}
	

	//檢查廣告連結網址
	function chk_adLinkURLLink() {
		urlCheck("adLinkURL",$("#adLinkURL").val());
	}
	
	//連結網址鍵盤件鍵檢查
	$('#adLinkURL').bind('keyup', function() {
		chk_adLinkURL();
	});
	
	//檢查網址blur事件
	$("#adLinkURL").blur(function() {
		if($("#adLinkURL").val() != "show.pchome.com.tw"){
			urlCheck("adLinkURL",$("#adLinkURL").val());
		}else{
			$("#chkLinkURL").css("color","red");
			$("#chkLinkURL").text("請輸入廣告連結網址");
		}
	});
	

	//網域鍵盤輸入事件檢查
	$('#adShowURL').bind('keyup', function() {
		chk_adShowURL();
	});
	
	
	//檢查網域blur事件
	$("#adShowURL").blur(function() {
		if($("#adShowURL").val() == "show.pchome.com.tw"){
			$("#chkShowURL").css("color","red");
			$("#chkShowURL").text("請輸入廣告顯示網址");
		}else{
			urlCheck("adShowURL",$("#adShowURL").val());
			if($("#adShowURL").val() == ""){
				$("#chkShowURL").css("color","red");
				$("#chkShowURL").text("請輸入廣告顯示網址");
			}
		}
	});
	
	
	//顯示網域提示字數與檢查
	function chk_adShowURL() {
		var maxlength = $('#adShowURL').attr("maxlength");
		var adShowURL = $('#adShowURL').val();
		var length = adShowURL.length;
			if(adShowURL == "") {
				$('#chkShowURL').css("color","red");
				$("#chkShowURL").text("請輸入廣告顯示網址.");
				$("#previewURL").text($('#adShowURL').attr("placeholder"));
			} else {
				// 去掉網址的 http://
				if(adShowURL.indexOf("://") > 0) {
					adShowURL = adShowURL.substring(adShowURL.indexOf("://") + 3);
				}
				if($("#sameRealUrl").prop("checked")){
					// 去掉連結網址 / 後的所有字串
					if(adShowURL.indexOf("/") > 0) {
						adShowURL = adShowURL.substring(0, adShowURL.indexOf("/"));
					}
				}
				
				$("#chkShowURL").text("");
				$("#previewURL").text(adShowURL);
				$("#adShowURL").val(adShowURL);
				length = adShowURL.length;
			if(length == maxlength) {
				$('#chkShowURL').css("color","blue");
				$("#chkShowURL").text("廣告顯示網址輸入字數已達上限" +maxlength+ "字");
			} else if(length > maxlength) { 
				$('#chkShowURL').css("color","red");
				$("#chkShowURL").text("廣告顯示網址最多輸入"+maxlength+"字");
			}
			$("#previewURL").text($('#adShowURL').val());
		}
		//連結網域字數檢查
		chkWord($('#adShowURL'), "spanAdShowURL");
		setData();
	}

	//檢查網址是否有效
	function urlCheck(urlType,adUrl){
		var adUrlHint = urlType != "adShowURL" ? "chkLinkURL" : "chkShowURL";
		if(adUrl != "" && urlType.indexOf("show.pchome.com.tw") < 0) {
			if(ValidURL(adUrl)) {
				$("#"+adUrlHint).text("網址檢查中");
				$.ajax({
					type: "POST",
					url: "checkAdUrl.html",
					data: { url: adUrl}
				}).done(function( msg ) {
					if(msg == "false") {
						$("#chkShowURL").css("color","red");
						$("#"+adUrlHint).css("color","red");
						if(adUrlHint == "chkLinkURL"){
							$("#"+adUrlHint).text("請輸入正確的廣告連結網址");
						}else if(adUrlHint == "chkShowURL"){
							$("#"+adUrlHint).text("請輸入正確的廣告顯示網址");
						}
						
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
				if(urlType == 'adLinkURL'){
					if($("#adLinkURL").length > 0){
						$('#chkLinkURL').css("color","red");
						$("#"+adUrlHint).text("請輸入正確廣告連結網址.");
					}
				}
				if(urlType == 'adShowURL'){
					if($("#adShowURL").length > 0){
						$('#chkShowURL').css("color","red");
						$("#"+adUrlHint).text("請輸入正確廣告顯示網址.");
					}
				}
			}
		}else{
			if(urlType == 'adLinkURL'){
				if($("#adLinkURL").val().length > 0){
					$('#chkLinkURL').css("color","red");
					$("#"+adUrlHint).text("請輸入正確廣告連結網址.");
					return false;
				}else{
					$('#chkLinkURL').css("color","red");
					$("#"+adUrlHint).text("請輸入廣告連結網址.");
					return false;
				}
				
			}
			if(urlType == 'adShowURL'){
				$("#"+adUrlHint).text("請輸入正確廣告顯示網址.");
				return false;
			}else{
				$("#"+adUrlHint).text("請輸入廣告顯示網址.");
				return false;
			}
		}
		//連結網址字數檢查
			chkWord($('#adShowURL'), "spanAdShowURL");
			chkWord($('#adLinkURL'), "spanAdLinkURL");
	}

	//點擊顯示網域
	$("#sameRealUrl").click(function() {
		if($("#chkLinkURL").css("color") == "rgb(255, 0, 0)"  || $("#chkLinkURL").text() != "網址確認正確"){
			return false;
		}
		if($("#sameRealUrl").prop("checked")){
			if($("#sameRealUrl").prop("checked") && $("#chkLinkURL").text() != "網址確認正確" ){
				$('#chkLinkURL').css("color","red");
				if($("#adLinkURL").val() == ""){
					$("#chkLinkURL").text("請輸入廣告連結網址.");
					return false;
				}else{
					if($("#adLinkURL").val() == "show.pchome.com.tw"){
						$("#chkLinkURL").text("請輸入廣告連結網址.");
						return false;
					}
				}
			}else{
				hostName = $('<a>').prop('href', "http://"+$("#adLinkURL").val()).prop('hostname');
				urlCheck("adShowURL","http://"+hostName)
				var url = $("#adLinkURL").val();
				var urlParts = url.replace('http://','').replace('https://','').split(/[/?#]/);
				var domain = urlParts[0];
				if($("#chkLinkURL").text() == "網址確認正確"){
					$("#adShowURL").val(domain);
				}
				urlCheck("adShowURL",$("#adShowURL").val());
				$("#previewURL").text($('#adShowURL').val());
			}
		}else{
			$("#adShowURL").val("");
			$("#previewURL").text($('#adShowURL').attr("placeholder"));
			$('#chkShowURL').css("color","red");
			$("#chkShowURL").text("請輸入廣告顯示網址.");
		}
	});
	//輸入字數檢查與提示
	function chkWord(el, showId) {
		var length = 0;
		if(showId == "spanAdLinkURL"){
			if(parseInt($("#adLinkURL").val().length) <= 1024){
				length=$("#adLinkURL").val().length;
				$('#spanAdLinkURL').css("color","");
				$("#spanAdLinkURL").text("已輸入" + length + "字，剩" + (1024 - length) + "字");
			}else if($("#adLinkURL").val().length > 1024){
				$('#spanAdLinkURL').css("color","red");
				$("#spanAdLinkURL").text("已輸入" + $("#adLinkURL").val().length + "字，超過" + ($("#adLinkURL").val().length - 1024) + "字");
				LinkUrl = false;
			}
		}else if(showId == "spanAdShowURL"){
			var maxlength = el.attr("maxlength");
			if(($("#adShowURL").val()+"").length <= maxlength){
				$("#spanAdShowURL").text("已輸入" + $("#adShowURL").val().length + "字，剩" + (maxlength - $("#adShowURL").val().length) + "字");
			}
		}else if( showId == "spanAdContent"){
			var maxlength = el.attr("maxlength");
			var adContentLength = maxlength -($('#adContent').val().length);
			$("#spanAdContent").text("已輸入"+$('#adContent').val().length+"字，剩" + adContentLength + "字");
		}else if(showId == "spanAdTitle"){
			var maxlength = el.attr("maxlength");
			var adTitleLength = maxlength -($('#adTitle').val().length);
			$("#spanAdTitle").text("已輸入"+$('#adTitle').val().length+"字，剩" + adTitleLength + "字");
		}
	}
	
	$('#save').click(function(){
		saveData();
	});

	$('#saveNew').click(function(){
		$('#saveAndNew').val("save+new");
    	saveData();
	});

	$('#cancel').click(function(){
		$("#modifyForm")[0].reset();
		//window.location.href = $("#backPage").val();
		refererHref($("#backPage").val());
	});
	
	function refererHref(url) {
		if (/MSIE (\d+\.\d+);/.test(navigator.userAgent) || /MSIE(\d+\.\d+);/.test(navigator.userAgent)){
			var referLink = document.createElement('a');
			referLink.href = url;
			document.body.appendChild(referLink);
			referLink.click();
		} else {
			location.href = url;
		}
	}
	
	
	function saveData() {
		//2015.08.27  關鍵字不為必填 by Tim
		/*var kwLen = document.getElementsByName("keywords").length;
		if( $("#existKW").children().length == 0 ){
			if(kwLen < 2){
				$('#chkAdKeyword').text("請輸入關鍵字");
				location.href="#errAdKeyword";
				return false;
			}
		}*/
		if($("#chkFile").text() != ""){
			location.href="#imghead";
			return false;
		}
		
		//for IE
		if($("#adTitle").val() == "PChome關鍵字廣告 全新登場"){
			$('#chkAdTitle').css("color","red");
			$("#chkAdTitle").text("請輸入廣告內容.");
			location.href="#adTitle";
			return false;
		}
		
		if($("#adContent").val() == "讓您的廣告受到世界矚目、訂單多到接不完！立即使用PChome關鍵字廣告‎。"){
			$('#chkAdContent').css("color","red");
			$("#chkAdContent").text("請輸入廣告內容.");
			location.href="#adContent";
			return false;
		}
		//for ie end
		
		if($("#adTitle").val() == ""){
			$('#chkAdTitle').css("color","red");
			$("#chkAdTitle").text("請輸入廣告內容.");
			location.href="#adTitle";
			return false;
		}
		
		if($("#adContent").val() == ""){
			$('#chkAdContent').css("color","red");
			$("#chkAdContent").text("請輸入廣告內容.");
			location.href="#adContent";
			return false;
		}
		
		
		if($("#chkLinkURL").css("color") == "rgb(255, 0, 0)"  || $("#chkLinkURL").text() != "網址確認正確"){
			$('#chkLinkURL').css("color","red");
			if($("#adLinkURL").val() == "" || $("#adLinkURL").val() == "show.pchome.com.tw"){
				$("#chkLinkURL").text("請輸入廣告連結網址.");
			}else{
				$('#chkLinkURL').css("color","red");
				$("#chkLinkURL").text("請輸入正確廣告連結網址.");
			}
			location.href="#adLinkURL";
			return false;
		}
		
		if($("#chkShowURL").css("color") == "rgb(255, 0, 0)" || $("#chkShowURL").text() != "網址確認正確"){
			$('#chkShowURL').css("color","red");
			if($("#adShowURL").val() == "" || $("#adShowURL").val() == "show.pchome.com.tw"){
				$("#chkShowURL").text("請輸入廣告顯示網址.");
			}else{
				$("#chkShowURL").text("請輸入正確廣告顯示網址.");
			}
			location.href="#adShowURL";
			return false;
		}
		
		if(!sizeFlag){
			location.href="#uploadFile";
			return false;
		}
		if($("#sizeCheckDiv").css("display") == "block"){
			location.href="#uploadFile";
			return false;
		}
		
		
		
		
		
		
		
		var alt = "提醒您，您的廣告將在3工作天(周一到周五)審核完成(不含例假日)，並於廣告審核完成後開始播放";
		if(confirm(alt)) {
			var kwLen = document.getElementsByName("keywords").length;
			
			
			
			//取得驗證回傳值
			if(chk_adTitle() && chk_adContent() && $("#chkFile").text() == ""){
					$.blockUI.defaults.applyPlatformOpacityRules = false;
					$.blockUI({
					    message: "<h1>製作新廣告中，請稍後...</h1>",
					    css: {
				            width: '500px',
				            height: '65px',
				            opacity: .9,
				            border:         '3px solid #aaa',
				            backgroundColor:'#fff',
				            textAlign:      'center',
				            cursor:         'wait',
				            '-webkit-border-radius': '10px',
				            '-moz-border-radius':    '10px'
				        },
				        fadeIn: 1000, 
				        timeout:   200, 
				        onBlock: function() {
							setData();
							// form submit
							$("#modifyForm").attr("target", "doAdd");
							$("#modifyForm").attr("action", "doAdAdAddTmg.html");
							$("#modifyForm").submit();
				        }
					});
				} 
		}
	}

	
	function setData() {
		if(LinkUrl && ShowUrl && $("#adTitle").val()!="" && $("#adContent").val()!=""){
			document.getElementsByName("adDetailContent")[0].value = document.getElementById('uploadFile').value;
			document.getElementsByName("adDetailContent")[1].value = $('#adTitle').val();
			document.getElementsByName("adDetailContent")[2].value = $('#adContent').val();
			document.getElementsByName("adDetailContent")[3].value = $('#adLinkURL').val();
			document.getElementsByName("adDetailContent")[4].value = $("#adShowURL").val();
		}
	}
});

function ValidURL(url) {
	var re = /^(http[s]?:\/\/){0,1}(www\.){0,1}[a-zA-Z0-9\.\-\u4e00-\u9fa5]+\.[a-zA-Z]{2,5}[\.]{0,1}/;
	var isUrl = re.test(url);
	return isUrl;
}


function deleteImage() {
	sizeFlag = true;
	$("#previewImg").css("display", "none");
	$("#uploadFile").val("");
	if($("#imgFile").val() != "") {
		$.ajax({
			type: "POST",
			url: "deleteIMG.html",
			data: { imgFile: $("#imgFile").val()}
		}).done(function( msg ) {
			if(msg == "delFinish") {
				var date = new Date();
				var time = date.getTime();
				$("#imghead").attr("src", "./html/img/upl9090.gif?" + time);
				$("#previewImg").attr("src", "./html/img/upl9090.gif?" + time);
				$("#uploadFile").replaceWith($('#uploadFile').clone());
				$("#imgFile").val("");
			}
		});
	} /*else {
		$("#chkFile").text("");
	} */
	$("#chkFile").text("");
	$("#sizeCheckDiv").css("display","none");
}

//預覽圖片
var sizeFlag = true;
function previewImage(file) {
	sizeFlag = true;
	var size = 0;
	
	if(!$.browser.msie ) { 
		size = ($("#uploadFile")[0].files[0].size / 1024);
	}
	if($.browser.msie) { 
		deleteImage();
	}
	size = Math.round(size);
	if(size > 1024){
		sizeFlag = false;
		$("#sizeCheckDiv").css("display","");
		$("#uploadCheckDiv").css("display","none");
		$("#imghead").attr("src", "./html/img/upl9090.gif");
		$("#previewImg").attr("src", "./html/img/upl9090.gif");
		$("#uploadFile").replaceWith($('#uploadFile').clone());
		location.href="#uploadFile";
		return false;
	}else{
		sizeFlag = true;
		$("#sizeCheckDiv").css("display","none");
		$("#uploadCheckDiv").css("display","none");
		var picPath = file.value;
		var type = picPath.substring(picPath.lastIndexOf(".")+1, picPath.length).toLowerCase();
		$("#imghead").css("display", "inline");
		if(type!="jpg" && type != "png" && type != "gif"){
			$("#chkFile").text("請選擇圖片檔案格式為 jpg、png、gif 的檔案");
			$("#previewImg").removeAttr("style");
			$("#imghead").attr("src", "./html/img/upl9090.gif");
			$("#previewImg").attr("src", "./html/img/upl9090.gif");
			//$("#uploadFile").replaceWith($(uploadFile).clone());
			//$("#imgFile").val("");
			return false;
		} 
		
		$("#chkFile").css("display","");
		$("#chkFile").text("圖片上傳中");
		$("#imgType").val(type);
		$("#modifyForm").attr("target", "uploadIMG");
		$("#modifyForm").attr("action", "fileUpload.html");
		$("#modifyForm").submit();
	}
}
 
// 選擇廣告樣式，並分別導入廣告樣式頁面
function setAdStyle(adStyle) {
	location.href = "adAdAdd.html?adActionSeq="+ $("#adActionSeq").val() + "&adGroupSeq=" + $("#adGroupSeq").val() + "&adStyle=" + adStyle;
}

function closenots(id) {
	$("#shownotes"+id).hide();
}

function chkLeave(){
	var keywords = "";
	$.each($("[name=keywords]"),function(){
		keywords += $(this).val();
	});
	var excludeKeywords = "";
	$.each($("[name=excludeKeywords]"),function(){
		excludeKeywords += $(this).val();
	});
	
	if(keywords != "" || excludeKeywords != "" || 
			$("#adTitle").val().replace($("#adTitle").attr("placeholder"),"") != "" || 
			$("#adContent").val().replace($("#adContent").attr("placeholder"),"") != "" || 
			$("#adLinkURL").val().replace($("#adLinkURL").attr("placeholder"),"") != "" || 
			$("#adShowURL").val().replace($("#adShowURL").attr("placeholder"),"") != "" || 
			$("#uploadFile").val() != ""){
		
		if(!confirm("您的廣告尚未編輯完成，離開後廣告資料不會存檔。")){
			return false;
		}
	}
	
	return true;
}

function fileLoad(){
	$("#uploadFile").click();
}