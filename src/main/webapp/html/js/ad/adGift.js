﻿$(document).ready(function(){

	var LinkUrl = true;
	var ShowUrl = true;
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
	$('#adTitle').bind('blur keyup', function() {
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

	$('#adContent').bind('blur keyup', function() {
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

	
	$('#adLinkURL').bind('keyup', function() {
		chk_adLinkURL();
	});
	function chk_adLinkURL() {
		var maxlength = $('#adLinkURL').attr("maxlength");
		var adLinkURL = $('#adLinkURL').val();
		var length = adLinkURL.length;
		if(adLinkURL != $("#adShowURL").val()) {
			$('#sameRealUrl').attr("checked", false);
		}
		if(adLinkURL == "") {
			$('#chkLinkURL').css("color","red");
			$("#chkLinkURL").text("請填寫廣告連結網址.");
		} else {
			$("#chkLinkURL").text("");
			if(length == maxlength) {
				$('#chkLinkURL').css("color","blue");
				$("#chkLinkURL").text("廣告連結網址輸入字數已達上限" +maxlength+ "字");
			} else if(length > maxlength) { 
				$('#chkLinkURL').css("color","red");
				$("#chkLinkURL").text("廣告連結網址最多輸入"+maxlength+"字");
			}
			setData();
		}
		chkWord($('#adLinkURL'), "spanAdLinkURL");
	}

	$('#adLinkURL').blur(function() {
		chk_adLinkURLLink();
	});
	function chk_adLinkURLLink() {
		var adLinkURL = $("#adLinkURL").val();
		chkWord($("#adLinkURL"), "spanAdLinkURL");
		if(adLinkURL != "" && adLinkURL.indexOf("example.pchome.com.tw") < 0) {
			if(ValidURL(adLinkURL)) {
				$('#chkLinkURL').css("color","red");
				$('#chkLinkURL').text("網址檢查中");
				$.ajax({
					type: "POST",
					url: "checkAdUrl.html",
					data: { url: adLinkURL}
				}).done(function( msg ) {
					if(msg == "false") {
						$('#chkLinkURL').css("color","red");
						$('#chkLinkURL').text("請輸入正確的廣告連結網址");
						LinkUrl = false;
					} else {
						$('#chkLinkURL').css("color","green");
						$('#chkLinkURL').text("網址確認正確");
						LinkUrl = true;
					}
				});
			} else {
				$('#chkLinkURL').css("color","red");
				$('#chkLinkURL').text("請輸入正確的廣告連結網址");
				LinkUrl = false;
			}
		} else {
			$('#chkLinkURL').css("color","red");
			$("#chkLinkURL").text("請填寫廣告連結網址.");
			LinkUrl = false;
		}
	}

	$('#adShowURL').bind('keyup', function() {
		chk_adShowURL();
	});
	function chk_adShowURL() {
		var maxlength = $('#adShowURL').attr("maxlength");
		var adShowURL = $('#adShowURL').val();
		var length = adShowURL.length;
		if(adShowURL != $("#adLinkURL").val()) {
			$('#sameRealUrl').attr("checked", false);
		}
		if(adShowURL == "") {
			$('#chkShowURL').css("color","red");
			$("#chkShowURL").text("請填寫廣告顯示網址.");
			$("#previewURL").text($('#adShowURL').attr("placeholder"));
		} else {
			// 去掉網址的 http://
			if(adShowURL.indexOf("://") > 0) {
				adShowURL = adShowURL.substring(adShowURL.indexOf("://") + 3);
			}
			// 去掉連結網址 / 後的所有字串
			if(adShowURL.indexOf("/") > 0) {
				adShowURL = adShowURL.substring(0, adShowURL.indexOf("/"));
			}
			$("#chkShowURL").text("");
			$("#previewURL").text(adShowURL);
			$("#adShowURL").val(adShowURL);
			length = adShowURL.length;
			//$("#spanAdShowURL").text("已輸入" + length + "字，剩" + (maxlength - length) + "字");
			if(length == maxlength) {
				$('#chkShowURL').css("color","blue");
				$("#chkShowURL").text("廣告顯示網址輸入字數已達上限" +maxlength+ "字");
			} else if(length > maxlength) { 
				$('#chkShowURL').css("color","red");
				$("#chkShowURL").text("廣告顯示網址最多輸入"+maxlength+"字");
			}
			setData();
		}
		chkWord($('#adShowURL'), "spanAdShowURL");
	}

	$('#adShowURL').blur(function() {
		chk_adShowURLLink();
	});
	function chk_adShowURLLink() {
		var adShowURL = $("#adShowURL").val();
		chkWord($("#adShowURL"), "spanAdShowURL");
		if(adShowURL != "" && adShowURL.indexOf("example.pchome.com.tw") < 0) {
			if(ValidURL(adShowURL)) {
				$('#chkShowURL').css("color","red");
				$('#chkShowURL').text("網址檢查中");
				$.ajax({
					type: "POST",
					url: "checkAdUrl.html",
					data: { url: adShowURL}
				}).done(function( msg ) {
					if(msg == "false") {
						$('#chkShowURL').css("color","red");
						$('#chkShowURL').text("請輸入正確的廣告顯示網址");
						ShowUrl = false;
					} else {
						$('#chkShowURL').css("color","green");
						$('#chkShowURL').text("網址確認正確");
						ShowUrl = true;
					}
				});
			} else {
				$('#chkShowURL').css("color","red");
				$('#chkShowURL').text("請輸入正確的廣告顯示網址");
				ShowUrl = false;
			}
		} else {
			$('#chkShowURL').css("color","red");
			$("#chkShowURL").text("請填寫廣告顯示網址.");
			$("#previewURL").text($('#adShowURL').attr("placeholder"));
			ShowUrl = false;
		}
	}

	$('#sameRealUrl').click(function() {
		if($('#sameRealUrl').attr("checked")) {
			var adLinkURL = $("#adLinkURL").val();
			// 去掉網址的 http://
			if(adLinkURL.indexOf("://") > 0) {
				adLinkURL = adLinkURL.substring(adLinkURL.indexOf("://") + 3);
			}
			// 去掉連結網址 / 後的所有字串
			if(adLinkURL.indexOf("/") > 0) {
				adLinkURL = adLinkURL.substring(0, adLinkURL.indexOf("/"));
			}
			$('#adShowURL').val(adLinkURL);
			if(adLinkURL != "" && adLinkURL != $("#adShowURL").attr("placeholder")) {
				$("#previewURL").text($('#adShowURL').val());
				$("#adShowURL").css("color", "black");
			} else {
				$("#previewURL").text($('#adShowURL').attr("placeholder"));
				$("#adShowURL").css("color", "#aaa");
			}
			chk_adShowURLLink();
		} else {
			var placeholder = $('#adShowURL').attr("placeholder");
			$('#adShowURL').val(placeholder);
			$('#chkShowURL').text("");
			$('#chkShowURL').css("color","red");
			$("#previewURL").text(placeholder);
			$("#adShowURL").css("color", "#aaa");
		}

		chkWord($("#adShowURL"), "spanAdShowURL");
		setData();
	});

	function chkWord(el, showId) {
		var length = el.val().length;
		var maxlength = el.attr("maxlength");
		if(el.val() == el.attr("placeholder")) {
			$('#'+showId).text("已輸入0字，剩" + maxlength + "字");
		} else {
			$('#'+showId).text("已輸入" + length + "字，剩" + (maxlength - length) + "字");
		}
	}

	$('#save').click(function(){
		saveData();
	});

//	$('#saveNew').click(function(){
//		$('#saveAndNew').val("save+new");
//		saveData();
//	});
//
	$('#cancel').click(function(){
		$("#modifyForm")[0].reset();
		window.history.go(pages);
		//$(location).attr("href","adAdView.html?adGroupSeq=" + $("#adGroupSeq").val());
	});

	var kwLen = document.getElementsByName("keywords").length;

	function saveData() {
		//取得驗證回傳值
		if(chk_adTitle() && chk_adContent() && $("#chkFile").text() == "" && ((kwLen > 0 && document.getElementsByName("keywords")[kwLen - 1].value != null) || document.getElementById('existKW').length > 0)){
			if(($("#adLinkURL").val() != "" && LinkUrl) && ($("#adShowURL").val() != "" && ShowUrl)) {
				var adStatus = $("#adStatus").val();
				var alertMsg = "";
				if(adStatus == 3) {
					alertMsg = "請再次確認您的廣告是否符合刊登規範\n\n提醒您，您的廣告將在2工作天(周一到周五)審核完成(不含例假日)，並於廣告審核完成後開始播放";
				} else {
					alertMsg = "PChome將會審核您編輯過的廣告。\n\n提醒您，您的廣告將在2工作天(周一到周五)審核完成(不含例假日)，並於廣告審核完成後開始播放";
				}
				if(confirm(alertMsg)) {
					setData();
					// form submit
					$("#modifyForm").attr("target", "_self");
					$("#modifyForm").attr("action", "doAdAdEditTmg.html");
					$("#modifyForm").submit();
				}
			} else if(!LinkUrl || $("#adLinkURL").val() == "") {
				chk_adLinkURLLink();
				chk_adShowURLLink();
				if(errId != "#errAdLinkURL") {
					pages--;
					errId = "#errAdLinkURL";
				}
				location.href="#errAdLinkURL";
			} else if(!ShowUrl || $("#adShowURL").val() == "") {
				chk_adLinkURLLink();
				chk_adShowURLLink();
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
				if(errId != "#errAdKeyword") {
					pages--;
					errId = "#errAdKeyword";
				}
				location.href="#errAdKeyword";
				$('#chkAdKeyword').text("請輸入關鍵字");
			} else if(kwLen > 0 && document.getElementsByName("keywords")[kwLen - 1].value == null) {
				location.href="#errAdKeyword";
				$('#chkAdKeyword').text("請輸入關鍵字");
			}
		}
	}

	function setData() {
		document.getElementsByName("adDetailContent")[0].value = document.getElementById('uploadFile').value;
		document.getElementsByName("adDetailContent")[1].value = $('#adTitle').val();
		document.getElementsByName("adDetailContent")[2].value = $('#adContent').val();
		document.getElementsByName("adDetailContent")[3].value = $('#adLinkURL').val();
		document.getElementsByName("adDetailContent")[4].value = $('#adShowURL').val();
	}
});

function ValidURL(url) {
	var re = /^(http[s]?:\/\/){0,1}(www\.){0,1}[a-zA-Z0-9\.\-\u4e00-\u9fa5]+\.[a-zA-Z]{2,5}[\.]{0,1}/;
	var isUrl = re.test(url);
	return isUrl;
}

function deleteImage() {
	$("#previewImg").css("display", "none");
	if($("#imgFile").val() != "") {
		$.ajax({
			type: "POST",
			url: "deleteIMG.html",
			data: { imgFile: $("#imgFile").val()}
		}).done(function( msg ) {
			if(msg == "delFinish") {
				$("#imghead").attr("src", "./html/img/upl9090.gif?" + (Math.random()*1000+1000));
				$("#previewImg").attr("src", "./html/img/upl9090.gif?" + (Math.random()*1000+1000));
				$("#uploadFile").replaceWith($('#uploadFile').clone());
				$("#imgFile").val("");
			}
		});
	} else {
		$("#chkFile").text("");
	} 
}

//預覽圖片
function previewImage(file) {
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
