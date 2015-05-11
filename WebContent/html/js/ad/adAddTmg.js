﻿$(document).ready(function(){
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
			$("#chkAdTitle").text("請填寫廣告標題.");
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
			$("#chkAdContent").text("請填寫廣告內容.");
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

	//連結網址鍵盤件鍵檢查
	$('#adLinkURL').bind('keyup', function() {
		chk_adLinkURL();
	});
	
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
			$("#chkLinkURL").text("請填寫廣告連結網址.");
			
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

	//檢查網址blur事件
	$("#adLinkURL").blur(function() {
		if($("#adLinkURL").val()!="show.pchome.com.tw"){
			urlCheck("adLinkURL",$("#adLinkURL").val());
			chk_adLinkURLLink();
		}
	});
	
	
	//檢查廣告連結網址
	function chk_adLinkURLLink() {
		urlCheck("adLinkURL",$("#adLinkURL").val());
	}

	//網域鍵盤輸入事件檢查
	$('#adShowURL').bind('keyup', function() {
		chk_adShowURL();
	});
	
	//檢查網域blur事件
	$("#adShowURL").blur(function() {
		if($("#adShowURL").val()!="show.pchome.com.tw" && $("#sameRealUrl").prop("checked")){
			chk_adShowURL();
			var adShowURL = $("#adShowURL").val();
			var hostname = $("<a>").prop("href", "http://"+adShowURL).prop("hostname");
			urlCheck("adShowURL","http://"+hostname);
		}else{
			chk_adShowURL();
			var adShowURL = $("#adShowURL").val();
			urlCheck("adShowURL",adShowURL);
		}
	});
	
	//顯示網域提示字數與檢查
	function chk_adShowURL() {
		var maxlength = $('#adShowURL').attr("maxlength");
		var adShowURL = $('#adShowURL').val();
		var length = adShowURL.length;
			if(adShowURL == "") {
				$('#chkShowURL').css("color","red");
				$("#chkShowURL").text("請填寫廣告顯示網址.");
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
		}
		//連結網域字數檢查
		chkWord($('#adShowURL'), "spanAdShowURL");
		setData();
	}

	//檢查網址是否有效
	function urlCheck(urlType,adUrl){
		var adUrlHint = urlType != "adShowURL" ? "chkLinkURL" : "chkShowURL";
		var securityUrlJsonObj = "";
		if(adUrl != "" && urlType.indexOf("show.pchome.com.tw") < 0) {
			if(ValidURL(adUrl)) {
				$("#"+adUrlHint).text("網址檢查中");
				$.ajax({
					type: "POST",
					url: "checkAdUrl.html",
					data: { url: adUrl},
				}).done(function(msg) {
					if(msg == 'malware'){
						$("#"+adUrlHint).css("color","red");
						$("#"+adUrlHint).text("抱歉，該網址被判斷為有問題，請填入其它網址!");
						if(urlType == "adShowURL"){
							ShowUrl = false;
						}else{
							LinkUrl = false;
						}
						return false;
					}else if(msg == 'true'){
						$("#"+urlType).css("color","");
						$("#"+adUrlHint).css("color","green");
						$("#"+adUrlHint).text("網址確認正確");
						if(urlType == "adShowURL"){
							ShowUrl = true;
						}else{
							LinkUrl = true;
						}
					}else if(msg == 'false'){
						$("#"+adUrlHint).css("color","red");
						$("#"+adUrlHint).text("請輸入正確的廣告顯示網址");
						if(urlType == "adShowURL"){
							ShowUrl = false;
						}else{
							LinkUrl = false;
						}
						return false;
					}else{
						if($('#adLinkURL').val().length <= 1024){
							LinkUrl = true;
						}else{
							LinkUrl = false;
						}
					}
				});
			}else {
				$("#"+adUrlHint).text("請填寫廣告顯示網址.");
				$("#previewURL").text($("#"+urlType).attr("placeholder"));
				ShowUrl = false;
			}
		}
		//連結網址字數檢查
			chkWord($('#adShowURL'), "spanAdShowURL");
			chkWord($('#adLinkURL'), "spanAdLinkURL");
	}

	//點擊顯示網域
	$("#sameRealUrl").click(function() {
		var hostName ="";
		if($("#sameRealUrl").prop("checked") &&  $("#adLinkURL").val() !=""){
			if($("#adLinkURL").val().indexOf("http") < 0 ){
				hostName = $('<a>').prop('href', "http://"+$("#adLinkURL").val()).prop('hostname');
				urlCheck("adShowURL","http://"+hostName);
			}else{
				hostName = $('<a>').prop('href', $("#adLinkURL").val()).prop('hostname');
				urlCheck("adShowURL",hostName);
			}
			$("#adShowURL").val(hostName);
		}else{
			$("#adShowURL").val("");
			$("#chkShowURL").css("color","red");
			$("#chkShowURL").text("請填寫廣告顯示網址.");
			$("#previewURL").text($("#chkShowURL").attr("placeholder"));
			ShowUrl = false;
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
			if($("#adShowURL").val().length <= maxlength){
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
		if(!sizeFlag){
			location.href="#uploadFile";
			return false;
		}
		var alt = "提醒您，您的廣告將在3工作天(周一到周五)審核完成(不含例假日)，並於廣告審核完成後開始播放";
		if(confirm(alt)) {
			var kwLen = document.getElementsByName("keywords").length;
			//取得驗證回傳值
			if(chk_adTitle() && chk_adContent() && $("#chkFile").text() == "" && ((kwLen > 0 && document.getElementsByName("keywords")[kwLen - 1].value != null) || document.getElementById('existKW').length > 0)){
				if(($("#adLinkURL").val() != "" && LinkUrl) && ($("#adShowURL").val() != "" && ShowUrl)) {
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
				} else if(!LinkUrl || $("#adLinkURL").val() == "") {
					chk_adLinkURLLink();
					chk_adShowURL();
					if(errId != "#errAdLinkURL") {
						pages--;
						errId = "#errAdLinkURL";
					}
					location.href="#errAdLinkURL";
				} else if(!ShowUrl || $("#adShowURL").val() == "") {
					chk_adLinkURLLink();
					chk_adShowURL();
					if(errId != "#errAdShowURL") {
						pages--;
						errId = "#errAdShowURL";
					}
					location.href="#errAdShowURL";
				}
			} else if(!chk_adTitle()) {
				if(errId != "#errAdTitle") {
					pages--;
					errId = "#errAdTitle";
				}
				location.href="#errAdTitle";
			} else if(!chk_adContent()) {
				if(errId != "#errAdContent") {
					pages--;
					errId = "#errAdContent";
				}
				location.href="#errAdContent";
			} else if($("#chkFile").text() != "") {
				if(errId != "#errAdImg") {
					pages--;
					errId = "#errAdImg";
				}
				location.href="#errAdImg";
			} else {
				if(kwLen == 0) {
					location.href="#errAdKeyword";
					$('#chkAdKeyword').text("請輸入關鍵字");
				} else if(kwLen > 0 && document.getElementsByName("keywords")[kwLen - 1].value == null) {
					location.href="#errAdKeyword";
					$('#chkAdKeyword').text("請輸入關鍵字");
				}
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
	if($("#imgFile").val() != "") {
		$.ajax({
			type: "POST",
			url: "deleteIMG.html",
			data: { imgFile: $("#imgFile").val()}
		}).done(function( msg ) {
//			if(msg == "delFinish") {
//				
//			}
		});
	} else {
		$("#chkFile").text("");
	} 
	$("#imghead").attr("src", "./html/img/upl9090.gif?" + (Math.random()*1000+1000));
	$("#previewImg").attr("src", "./html/img/upl9090.gif?" + (Math.random()*1000+1000));
	$("#uploadFile").replaceWith($('#uploadFile').clone());
	$("#imgFile").val("");
	$("#sizeCheckDiv").css("display","none");
	$("#uploadCheckDiv").css("display","none");
}

//預覽圖片
var sizeFlag = true;
function previewImage(file) {
	sizeFlag = true;
	var size = ($("#uploadFile")[0].files[0].size / 1024);
	size = Math.round(size);
	if(size > 1024){
		sizeFlag = false;
		$("#sizeCheckDiv").css("display","");
		$("#uploadCheckDiv").css("display","none");
		$("#imghead").attr("src", "./html/img/upl9090.gif?" + (Math.random()*1000+1000));
		$("#previewImg").attr("src", "./html/img/upl9090.gif?" + (Math.random()*1000+1000));
		$("#uploadFile").replaceWith($('#uploadFile').clone());
		$("#imgFile").val("");
		location.href="#uploadFile";
	}else{
		sizeFlag = true;
		$("#sizeCheckDiv").css("display","none");
		$("#uploadCheckDiv").css("display","none");
	}
	var picPath = file.value;
	var type = picPath.substring(picPath.lastIndexOf(".")+1, picPath.length).toLowerCase();
	$("#imghead").css("display", "inline");
	if(type!="jpg" && type!="png"){
		$("#chkFile").text("請選擇圖片檔案格式為 jpg、png 的檔案");
		return false;
	} else {
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

