var logoDataObj = new Object();
logoDataObj = {square:{base64:null,file_name:null,file_extension_name:null,status:null},rectangle:{base64:null,file_name:null,file_extension_name:null,status:null}};
$(document).ready(function(){
	if($.browser.msie){
		if(parseInt($.browser.version) < 9){
			$("body").block({
				message: "<div><p>您的瀏覽器不支援唷!</p><br/><p>本系統建議使用 Chrome 、 Firefox 瀏覽器</p><br/><p>如需使用 IE，請使用 IE9以上版本。</p></div>",
				css: {
					padding: 0,
					margin: 0,
					width: '80%',
					top: '40%',
					left: '35%',
					textAlign: 'center',
					color: '#000',
					border: '3px solid #aaa',
					backgroundColor: '#fff',
					cursor: 'wait'
				}
			});
			var pagehtml = location.pathname;
			if(pagehtml == ("/" + "index.html") || pagehtml == ("/")
				|| pagehtml == "/pfp/index.html"){
				$(".blockOverlay").css("height","270%");
				$(".blockOverlay").css("width","115%");
			}
		}
	}
	
	$(function () {
		$('#fileupload').fileupload({
		}).on('fileuploadadd', function (e, data) {
		    	
		}).on('fileuploaddone', function (e, data) {
			if(alex != null){
				checkUploadFile(data.files[0],clickObj);
			}
			alex = null;
		}).on('fileuploadprogressall', function (e, data) {	
		}).on('fileuploadprocessalways', function (e, data) {
		})
	});
	
	
	
	//初始化審核的圖
	initLogoSave();
	
});


function initLogoSave(){
	if($("#saveLogoImg").text() != "{}"){
		logoDataObj = JSON.parse($("#saveLogoImg").text());
		var squareBase64 = logoDataObj.square.base64;
		var rectangleBase64 = logoDataObj.rectangle.base64;
		
		var squareStatus = logoDataObj.square.status;
		var rectangleStatus = logoDataObj.rectangle.status;
		
		var squareRejectReason = logoDataObj.square.reject_reason;
		var rectangleRejectReason = logoDataObj.rectangle.reject_reason;
		
		var select = document.getElementsByClassName("select")[1];
		var review = document.getElementsByClassName("review")[1];
		var success = document.getElementsByClassName("success")[1];
		
		if(squareStatus != -1 && rectangleStatus != -1){
			$("#submitBtn").css("background-color","#a8a8a8");
		}
		if(rectangleStatus == "1"){
			var img = $(success).find(".logomanage-imgbox")[0].getElementsByTagName("img")[0];
			$(success).find(".rectanglelogo-box")[0].setAttribute("style", "height:auto");
			img.src = rectangleBase64;
			$(success).css("display", "block");
			$(select).css("display", "none");
			$(review).css("display", "none");
		}else if(rectangleStatus == "2" || rectangleStatus == "0" || rectangleStatus == "9"){
			var img = $(review).find(".logomanage-imgbox")[0].getElementsByTagName("img")[0];
			img.src = rectangleBase64;
			$(success).css("display", "none");
			$(select).css("display", "none");
			$(review).css("display", "block");
			var notebox = $(review)[0].getElementsByClassName("notebox")[0];
			$(review).find(".logomanage-imgbox")[0].style.display = "inherit";
			if(rectangleStatus == 0){
				$(review)[0].getElementsByClassName("delbtn")[0].remove(); 
			}
			if(rectangleStatus == 2){
				notebox.innerHTML = rectangleStatus == 0 ? "審核中" :'審核失敗 <div class="msg-btn" onclick="$(this).children(\'em\').fadeToggle(\'fast\');"><em style="display: none;">'+rectangleRejectReason+'</em></div>'; 
			}
		}
		
		var select = document.getElementsByClassName("select")[0];
		var review = document.getElementsByClassName("review")[0];
		var success = document.getElementsByClassName("success")[0];
		
		if(squareStatus == "1"){
			var img = $(success).find(".logomanage-imgbox")[0].getElementsByTagName("img")[0];
			img.src = squareBase64;
			$(success).css("display", "block");
			$(select).css("display", "none");
			$(review).css("display", "none");
			
		}else if(squareStatus == "2" || squareStatus == "0" || squareStatus == "9"){
			var img = $(review).find(".logomanage-imgbox")[0].getElementsByTagName("img")[0];
			img.src = squareBase64;
			$(success).css("display", "none");
			$(select).css("display", "none");
			$(review).css("display", "block");
			var notebox = $(review)[0].getElementsByClassName("notebox")[0];
			if(squareStatus == 0){
				$(review)[0].getElementsByClassName("delbtn")[0].remove(); 
			}
			if(squareStatus == 2){
				notebox.innerHTML = squareStatus == 0 ? "審核中" :'審核失敗 <div class="msg-btn" onclick="$(this).children(\'em\').fadeToggle(\'fast\');"><em style="display: none;">'+squareRejectReason+'</em></div>';
			}
		}
	}
}


var alex = null;
function openFileLoad(obj){
	alex = 'alex';
	clickObj = obj.parentElement.parentElement;
	$("#fileupload").click();
}
function drop(ev,obj) {
    var file = ev.dataTransfer.files[0];
    checkUploadFile(file,obj);
}
function checkUploadFile(file,obj){
	var typeClassName = $(obj).attr("class");
	var flag = true;
	if(typeClassName.indexOf("squarelogo") >=0 || typeClassName.indexOf("rectanglelogo") >=0){
		var fileName = file.name;
		var fileSizeKb = Math.round(file.size/1024);
		var fileType = file.type;
	
		var select = null;
		var review = null;
		var success = null;
		var notebox = null;
		var img = null;
		
		if(typeClassName.indexOf("squarelogo") >=0){
			select = document.getElementsByClassName("select")[0];
			review = document.getElementsByClassName("review")[0];
			success = document.getElementsByClassName("success")[0];
			if(fileType !='image/jpeg' && fileType !='image/png'){
				flag = false;
				$("#errMsg").text("請上傳jpg或png");
				$(success).css("display", "none");
				$(select).css("display", "block");
				$(review).css("display", "none");
				return false;
			}
			if(fileSizeKb > 10240){
				flag = false;
				$("#errMsg").text("檔案須小於10240kb");
				$(success).css("display", "none");
				$(select).css("display", "block");
				$(review).css("display", "none");
				return false;
			}
			img = $(success).find(".logomanage-imgbox")[0].getElementsByTagName("img")[0];
			notebox = $(success)[0].getElementsByClassName("notebox")[0];
		}else if(typeClassName.indexOf("rectanglelogo") >=0){
			select = document.getElementsByClassName("select")[1];
			review = document.getElementsByClassName("review")[1];
			success = document.getElementsByClassName("success")[1];
			if(fileType !='image/jpeg' && fileType !='image/png'){
				flag = false;
				$("#errMsg2").text("請上傳jpg或png格式");
				$(success).css("display", "none");
				$(select).css("display", "block");
				$(review).css("display", "none");
				return false;
			}
			if(fileSizeKb > 10240){
				flag = false;
				$("#errMsg2").text("檔案須小於10240kb");
				$(success).css("display", "none");
				$(select).css("display", "block");
				$(review).css("display", "none");
				return false;
			}
			img = $(success).find(".logomanage-imgbox")[0].getElementsByTagName("img")[0];
			notebox = $(success)[0].getElementsByClassName("notebox")[0];
		}
		
		readFile(file, function(e) {
			var base64Img = e.target.result;
			img.src = base64Img;
			img.onload = function(){
				if(typeClassName.indexOf("squarelogo") >=0){
					if(img.naturalWidth != img.naturalHeight){
						flag = false;
						$("#errMsg").text("請上傳1:1圖片尺寸");
					}
					if(img.naturalWidth < 250 || img.naturalHeight < 250){
						flag = false;
						$("#errMsg").text("請上傳圖片解析度大於250x250尺寸");
					}
					if(flag){
						img.width = 250;
						img.height = 250;
						$("#errMsg").text("");
						$(notebox)[0].innerHTML = '上傳成功';
						$(select).css("display","none");
						$(review).css("display","none");
						$(success).css("display","block");
						logoDataObj.square.base64 = base64Img;
						logoDataObj.square.file_name = 'square';
						logoDataObj.square.file_extension_name = fileType;
						logoDataObj.square.status = -1;
						$("#submitBtn").css("background-color","");
						
						$(success)[0].getElementsByTagName("object")[0].data="html/img/deleteimg.svg";
						$(success)[0].getElementsByClassName("logoreupload-box")[0].setAttribute("onclick","deleteImg('square')");
						$(success)[0].getElementsByClassName("text")[0].innerHTML = '';
						
					}else{
						img.src = '';
						$(select).css("display","block");
						$(review).css("display","none");
						$(success).css("display","none");
						logoDataObj.base64 = null;
						logoDataObj.file_name = null;
						logoDataObj.file_extension_name = null;
						logoDataObj.square.status = null;
					}
				}else if(typeClassName.indexOf("rectanglelogo") >=0){
					if(img.naturalWidth < 1000 || img.naturalHeight < 250){
						flag = false;
						$("#errMsg2").text("請上傳圖片解析度大於1000x250尺寸");
					}
					if((img.naturalWidth / img.naturalHeight) != 4){
						flag = false;
						$("#errMsg2").text("請上傳4:1圖片尺寸");
					}
					if(flag){
						$("#errMsg2").text("");
						$(notebox)[0].innerHTML = '上傳成功';
						$(success).css("display","block");
						$(select).css("display","none");
						$(review).css("display","none");
						logoDataObj.rectangle.base64 = base64Img;
						logoDataObj.rectangle.file_name = 'rectangle';
						logoDataObj.rectangle.file_extension_name = fileType;
						logoDataObj.rectangle.status = -1;
						$("#submitBtn").css("background-color","");
						
						
						$(success)[0].getElementsByTagName("object")[0].data="html/img/deleteimg.svg";
						$(success)[0].getElementsByClassName("logoreupload-box")[0].setAttribute("onclick","deleteImg('rectangle')");
						$(success)[0].getElementsByClassName("text")[0].innerHTML = '';
						$(success).find(".rectanglelogo-box")[0].setAttribute("style", "height:auto");
						
					}else{
						img.src = '';
						$(select).css("display","block");
						$(review).css("display","none");
						$(success).css("display","none");
						logoDataObj.rectangle.base64 = null;
						logoDataObj.rectangle.file_name = null;
						logoDataObj.rectangle.file_extension_name = null;
						logoDataObj.rectangle.status = null;
					}
				}
			}
		});
	}
}
function readFile(file, onLoadCallback){
	var reader = new FileReader();
	reader.onload = onLoadCallback;
	reader.readAsDataURL(file);
}
function deleteImg(obj){
	var success = null;
	var select = null;
	var review = null;
	
//	if((obj == 'rectangle' && logoDataObj.rectangle.status == "1") || (obj == 'square' && logoDataObj.square.status == "1")){
//		var alt = "刪除LOGO將影響正在投放之商品廣告，是否確認刪除？";
//		if(confirm(alt)) {
//			$.ajax({
//				url : "deleteLogoAjax.html",
//				type : "POST",
//				dataType:'json',
//				data : {
//					"deleteLogoType": obj == 'square'? '0' : '1',
//				},
//				success : function(respone) {
//				},
//				error: function(xtl) {
//					alert("系統繁忙，請稍後再試！");
//				}
//				}).done(function( msg ) {
//					
//					
//				});
//		}else{
//			return false;
//		}
//	}
	if(obj == 'square'){
		success = document.getElementsByClassName("success")[0];
		select = document.getElementsByClassName("select")[0];
		review = document.getElementsByClassName("review")[0];
		logoDataObj.square.base64 = null;
		logoDataObj.square.file_name = null;
		logoDataObj.square.file_extension_name = null;
		logoDataObj.square.status = "";
	}
	if(obj == 'rectangle'){
		success = document.getElementsByClassName("success")[1];
		select = document.getElementsByClassName("select")[1];
		review = document.getElementsByClassName("review")[1];
		logoDataObj.rectangle.base64 = null;
		logoDataObj.rectangle.file_name = null;
		logoDataObj.rectangle.file_extension_name = null;
		logoDataObj.rectangle.status = "";
		$($(select)[0].getElementsByClassName("rectanglelogo-box")).height(185)
	}
	var img = $(success).find(".logomanage-imgbox")[0].getElementsByTagName("img")[0];
	img.src = '';
	img = $(review).find(".logomanage-imgbox")[0].getElementsByTagName("img")[0];
	img.src = '';
	
	$(select).css("display","block");
	$(review).css("display","none");
	$(success).css("display","none");
}
function saveLogo(){
	var saveFlag = true;
	if((logoDataObj.square.base64 == "" || logoDataObj.square.base64 == null)  || logoDataObj.square.status == ""){
		alert("LOGO圖不可為空!!");
		saveFlag = false;
	}
	
	if((logoDataObj.rectangle.base64 == "" || logoDataObj.rectangle.base64 == null) || logoDataObj.rectangle.status == ""){
		alert("LOGO圖不可為空!!");
		saveFlag = false;
	}
	
	if(logoDataObj.square.status != -1 && logoDataObj.rectangle.status != -1){
		saveFlag = false;
	}
	
	if(saveFlag){
		$.ajax({
		url : "saveLogoAjax.html",
		type : "POST",
		dataType:'json',
		data : {
			"logoDataObj":JSON.stringify(logoDataObj),
		},
		success : function(respone) {
			if(respone == "success"){
				location.href='logo.html'; 
			} else {
				console.log(respone);
			}
		},
		error: function(xtl) {
			alert("系統繁忙，請稍後再試！");
		}
		}).done(function( msg ) {
			changeStatus();
		});
	}
}

function changeStatus(){
	
}
