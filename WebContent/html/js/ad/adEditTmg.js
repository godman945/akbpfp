$(document).ready(function(){
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
		if(adLinkURL != $("#adShowURL").val()) {
//			$('#sameRealUrl').attr("checked", false);
		}
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
//		var maxlength = $('#adLinkURL').attr("maxlength");
//		var adLinkURL = $('#adLinkURL').val();
//		var length = adLinkURL.length;
//		if(adLinkURL != $("#adShowURL").val()) {
//			$('#sameRealUrl').attr("checked", false);
//		}
//		if(adLinkURL == "") {
//			$('#chkLinkURL').css("color","red");
//			$("#chkLinkURL").text("請填寫廣告連結網址.");
//		} else {
//			$("#chkLinkURL").text("");
//			if(length == maxlength) {
//				$('#chkLinkURL').css("color","blue");
//				$("#chkLinkURL").text("廣告連結網址輸入字數已達上限" +maxlength+ "字");
//			} else if(length > maxlength) { 
//				$('#chkLinkURL').css("color","red");
//				$("#chkLinkURL").text("廣告連結網址最多輸入"+maxlength+"字");
//			}
//			setData();
//		}
//		chkWord($('#adLinkURL'), "spanAdLinkURL");
	}
	//檢查網址blur事件
	$('#adLinkURL').blur(function() {
		chk_adLinkURLLink();
		chk_adShowURL();
	});
	
	
	
	//檢查廣告連結網址
	
	function chk_adLinkURLLink() {
		urlCheck("adLinkURL",$("#adLinkURL").val());
		
		
		
//		var adLinkURL = $("#adLinkURL").val();
//		chkWord($("#adLinkURL"), "spanAdLinkURL");
//		if(adLinkURL != "" && adLinkURL.indexOf("show.pchome.com.tw") < 0) {
//			if(ValidURL(adLinkURL)) {
//				$('#chkLinkURL').css("color","red");
//				$('#chkLinkURL').text("網址檢查中");
//				$.ajax({
//					type: "POST",
//					url: "checkAdUrl.html",
//					data: { url: adLinkURL}
//				}).done(function( msg ) {
//					if(msg == "false") {
//						$('#chkLinkURL').css("color","red");
//						$('#chkLinkURL').text("請輸入正確的廣告連結網址");
//						LinkUrl = false;
//					} else {
//						$('#chkLinkURL').css("color","green");
//						$('#chkLinkURL').text("網址確認正確");
//						LinkUrl = true;
//					}
//				});
//			} else {
//				$('#chkLinkURL').css("color","red");
//				$('#chkLinkURL').text("請輸入正確的廣告連結網址");
//				LinkUrl = false;
//			}
//		} else {
//			$('#chkLinkURL').css("color","red");
//			$("#chkLinkURL").text("請填寫廣告連結網址.");
//			LinkUrl = false;
//		}
	}
	
	
	//網域鍵盤輸入事件檢查
	$('#adShowURL').bind('keyup', function() {
		chk_adShowURL();
		chk_adLinkURLLink();
	});
	//檢查網域blur事件
	$('#adShowURL').blur(function() {
		var adShowURL = $("#adShowURL").val();
		if($("#sameRealUrl").prop("checked")){
			chk_adShowURL();
			var hostname = $("<a>").prop("href", "http://"+adShowURL).prop("hostname");
			urlCheck("adShowURL","http://"+hostname);
		}else{
			chk_adShowURL();
			urlCheck("adShowURL",adShowURL);
		}
		
	});
	//顯示網域提示字數與檢查
	function chk_adShowURL() {
		var maxlength = $('#adShowURL').attr("maxlength");
		var adShowURL = $('#adShowURL').val();
		var length = adShowURL.length;
			if(adShowURL == "") {
				$('#chkShowURL').css("color","");
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
//		var maxlength = $('#adShowURL').attr("maxlength");
//		var adShowURL = $('#adShowURL').val();
//		var length = adShowURL.length;
//		if(adShowURL != $("#adLinkURL").val()) {
//			$('#sameRealUrl').attr("checked", false);
//		}
//		if(adShowURL == "") {
//			$('#chkShowURL').css("color","red");
//			$("#chkShowURL").text("請填寫廣告顯示網址.");
//			$("#previewURL").text($('#adShowURL').attr("placeholder"));
//		} else {
//			// 去掉網址的 http://
//			if(adShowURL.indexOf("://") > 0) {
//				adShowURL = adShowURL.substring(adShowURL.indexOf("://") + 3);
//			}
//			// 去掉連結網址 / 後的所有字串
//			if(adShowURL.indexOf("/") > 0) {
//				adShowURL = adShowURL.substring(0, adShowURL.indexOf("/"));
//			}
//			$("#chkShowURL").text("");
//			$("#previewURL").text(adShowURL);
//			$("#adShowURL").val(adShowURL);
//			length = adShowURL.length;
//			//$("#spanAdShowURL").text("已輸入" + length + "字，剩" + (maxlength - length) + "字");
//			if(length == maxlength) {
//				$('#chkShowURL').css("color","blue");
//				$("#chkShowURL").text("廣告顯示網址輸入字數已達上限" +maxlength+ "字");
//			} else if(length > maxlength) { 
//				$('#chkShowURL').css("color","red");
//				$("#chkShowURL").text("廣告顯示網址最多輸入"+maxlength+"字");
//			}
//			setData();
//		}
//		chkWord($('#adShowURL'), "spanAdShowURL");
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
	
//	function chk_adShowURLLink() {
//		var adShowURL = $("#adShowURL").val();
//		chkWord($("#adShowURL"), "spanAdShowURL");
//		if(adShowURL != "" && adShowURL.indexOf("show.pchome.com.tw") < 0) {
//			if(ValidURL(adShowURL)) {
//				$('#chkShowURL').css("color","red");
//				$('#chkShowURL').text("網址檢查中");
//				$.ajax({
//					type: "POST",
//					url: "checkAdUrl.html",
//					data: { url: adShowURL}
//				}).done(function( msg ) {
//					if(msg == "false") {
//						$('#chkShowURL').css("color","red");
//						$('#chkShowURL').text("請輸入正確的廣告顯示網址");
//						ShowUrl = false;
//					} else {
//						$('#chkShowURL').css("color","green");
//						$('#chkShowURL').text("網址確認正確");
//						ShowUrl = true;
//					}
//				});
//			} else {
//				$('#chkShowURL').css("color","red");
//				$('#chkShowURL').text("請輸入正確的廣告顯示網址");
//				ShowUrl = false;
//			}
//		} else {
//			$('#chkShowURL').css("color","red");
//			$("#chkShowURL").text("請填寫廣告顯示網址.");
//			$("#previewURL").text($('#adShowURL').attr("placeholder"));
//			ShowUrl = false;
//		}
//	}
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
//		var length = el.val().length;
//		var maxlength = el.attr("maxlength");
//		if(el.val() == el.attr("placeholder")) {
//			$('#'+showId).text("已輸入0字，剩" + maxlength + "字");
//		} else {
//			$('#'+showId).text("已輸入" + length + "字，剩" + (maxlength - length) + "字");
//		}
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
		window.history.go(pages);
		//$(location).attr("href","adAdView.html?adGroupSeq=" + $("#adGroupSeq").val());
	});

//	var kwLen = document.getElementsByName("keywords").length;

	function saveData() {
		
		//for IE
		if($("#adTitle").val() == "PChome關鍵字廣告 全新登場"){
			$('#chkAdTitle').css("color","red");
			$("#chkAdTitle").text("請填寫廣告內容.");
			location.href="#chkAdTitle";
			return false;
		}
		
		if($("#adContent").val() == "讓您的廣告受到世界矚目、訂單多到接不完！立即使用PChome關鍵字廣告‎。"){
			$('#chkAdContent').css("color","red");
			$("#chkAdContent").text("請填寫廣告內容.");
			location.href="#chkAdContent";
			return false;
		}
		//for ie end
		
		if($("#adTitle").val() == ""){
			$('#chkAdTitle').css("color","red");
			$("#chkAdTitle").text("請填寫廣告內容.");
			location.href="#chkAdTitle";
			return false;
		}
		
		if($("#adContent").val() == ""){
			$('#chkAdContent').css("color","red");
			$("#chkAdContent").text("請填寫廣告內容.");
			location.href="#chkAdContent";
			return false;
		}
		
		
		if($("#chkLinkURL").css("color") == "rgb(255, 0, 0)"  || $("#chkLinkURL").text() != "網址確認正確"){
			$('#chkLinkURL').css("color","red");
			$("#chkLinkURL").text("請填寫廣告連結網址.");
			location.href="#chkLinkURL";
			return false;
		}
		
		if($("#chkShowURL").css("color") == "rgb(255, 0, 0)" || $("#chkShowURL").text() != "網址確認正確"){
			$('#chkShowURL').css("color","red");
			$("#chkShowURL").text("請填寫顯示網址.");
			location.href="#chkShowURL";
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
		
		if(LinkUrl && ShowUrl && $("#adTitle").val()!="" && $("#adContent").val()!=""){
				var adStatus = $("#adStatus").val();
				var alertMsg = "";
				if(adStatus == 3) {
					alertMsg = "請再次確認您的廣告是否符合刊登規範\n\n提醒您，您的廣告將在3工作天(周一到周五)審核完成(不含例假日)，並於廣告審核完成後開始播放";
				} else {
					alertMsg = "PChome將會審核您編輯過的廣告。\n\n提醒您，您的廣告將在3工作天(周一到周五)審核完成(不含例假日)，並於廣告審核完成後開始播放";
				}
				if(confirm(alertMsg)) {
					setData();
					// form submit
					$("#modifyForm").attr("target", "_self");
					$("#modifyForm").attr("action", "doAdAdEditTmg.html");
					$("#modifyForm").submit();
				}
			} 
			if(!LinkUrl){
				if(errId != "#errAdLinkURL") {
					pages--;
					errId = "#errAdLinkURL";
				}
				location.href="#errAdLinkURL";
			}else if(!ShowUrl){
				if(errId != "#errAdShowURL") {
					pages--;
					errId = "#errAdShowURL";
				}
				location.href="#errAdShowURL";
			}else if($("#adTitle").val().length == 0){
				if(errId != "#errAdTitle") {
					pages--;
					errId = "#errAdTitle";
				}
				location.href="#errAdTitle";
			}else if($("#adContent").val().length == 0){
				if(errId != "#errAdContent") {
					pages--;
					errId = "#errAdContent";
				}
				location.href="#errAdContent";
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
	$("#sizeCheckDiv").css("display","none");
}

//預覽圖片
var sizeFlag = true;
function previewImage(file) {
	sizeFlag = true;
	var size = 0;
	if(!$.browser.msie || $.browser.mozilla) { 
		size = ($("#uploadFile")[0].files[0].size / 1024);
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
		if(type!="jpg" && type!="png"){
			$("#chkFile").text("請選擇圖片檔案格式為 jpg、png 的檔案");
			return false;
		} else {
			$("#chkFile").css("display","");
			$("#chkFile").text("圖片上傳中");
			$("#imgType").val(type);
			$("#modifyForm").attr("target", "uploadIMG");
			$("#modifyForm").attr("action", "fileUpload.html");
			$("#modifyForm").submit();
		}
	}
}

// 選擇廣告樣式，並分別導入廣告樣式頁面
function setAdStyle(adStyle) {
	location.href = "adAdAdd.html?adActionSeq="+ $("#adActionSeq").val() + "&adGroupSeq=" + $("#adGroupSeq").val() + "&adStyle=" + adStyle;
}

function closenots(id) {
	$("#shownotes"+id).hide();
}
