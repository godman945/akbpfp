var logoDataObj = new Object();
logoDataObj = {square:{base64:null,file_name:null,file_extension_name:null},rectangle:{base64:null,file_name:null,file_extension_name:null}};
$(document).ready(function(){
	
//	$(".akb_iframe").attr("src","");
//	$("#groupSelect").children()[0].selected = 'selected';
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
			url: 'adAddImgAjax.html',
			fileElementId: 'fileupload',
			success: function (respone) {
				imgSeq = respone;
				jsonObj =  JSON.parse(respone);
			},
			done: function (e, data) {
			},
			dataType: 'json',
			async: false,
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
	
	
	//初始畫審核的圖
	initLogoSave();
	
});


function initLogoSave(){
	if($("#saveLogoImg").text() != "{}"){
		logoDataObj = JSON.parse($("#saveLogoImg").text());
		var squareBase64 = logoDataObj.square.base64;
		var rectangleBase64 = logoDataObj.rectangle.base64;
		
		var select = document.getElementsByClassName("select")[0];
		var review = document.getElementsByClassName("review")[0];
		var success = document.getElementsByClassName("success")[0];
		var img = $(review).find(".logomanage-imgbox")[0].getElementsByTagName("img")[0];
		var notebox = $(review)[0].getElementsByClassName("notebox")[0];
		
		img.src = squareBase64;
		$(success).css("display","none");
		$(select).css("display","none");
		$(review).css("display","block");
		notebox.innerHTML = "審核中";
		
		var select = document.getElementsByClassName("select")[1];
		var review = document.getElementsByClassName("review")[1];
		var success = document.getElementsByClassName("success")[1];
		var img = $(review).find(".logomanage-imgbox")[0].getElementsByTagName("img")[0];
		var notebox = $(review)[0].getElementsByClassName("notebox")[0];
		
		img.src = rectangleBase64;
		$(success).css("display","none");
		$(select).css("display","none");
		$(review).css("display","block");
		notebox.innerHTML = "審核中";
		$(review).find(".logomanage-imgbox")[0].style.display = "inherit";
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
	console.log(typeClassName);
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
			if(fileSizeKb > 180){
				flag = false;
				$("#errMsg").text("檔案須小於180kb");
				return false;
			}
			if(fileType !='image/jpeg' && fileType !='image/png' && fileType !='image/gif'){
				flag = false;
				$("#errMsg").text("請上傳jpg、png 或 gif格式");
				return false;
			}
			select = document.getElementsByClassName("select")[0];
			review = document.getElementsByClassName("review")[0];
			success = document.getElementsByClassName("success")[0];
			img = $(success).find(".logomanage-imgbox")[0].getElementsByTagName("img")[0];
			notebox = $(success)[0].getElementsByClassName("notebox")[0];
		}else if(typeClassName.indexOf("rectanglelogo") >=0){
			if(fileSizeKb > 180){
				flag = false;
				$("#errMsg2").text("檔案須小於180kb");
				return false;
			}
			if(fileType !='image/jpeg' && fileType !='image/png' && fileType !='image/gif'){
				flag = false;
				$("#errMsg2").text("請上傳jpg、png 或 gif格式");
				return false;
			}
			select = document.getElementsByClassName("select")[1];
			review = document.getElementsByClassName("review")[1];
			success = document.getElementsByClassName("success")[1];
			img = $(success).find(".logomanage-imgbox")[0].getElementsByTagName("img")[0];
			notebox = $(success)[0].getElementsByClassName("notebox")[0];
		}
		
		readFile(file, function(e) {
			var base64Img = e.target.result;
			img.src = base64Img;
			img.onload = function(){
				if(typeClassName.indexOf("squarelogo") >=0){
					if(img.naturalWidth != 250 || img.naturalHeight != 250){
						flag = false;
						$("#errMsg").text("請上傳圖片解析度250x250尺寸");
					}
					if(flag){
						$("#errMsg").text("");
						$(notebox)[0].innerHTML = '上傳成功';
						$(select).css("display","none");
						$(review).css("display","none");
						$(success).css("display","block");
						logoDataObj.square.base64 = base64Img;
						logoDataObj.square.file_name = fileName;
						logoDataObj.square.file_extension_name = fileType;
					}else{
						img.src = '';
						$(select).css("display","block");
						$(review).css("display","none");
						$(success).css("display","none");
						logoDataObj.base64 = null;
						logoDataObj.file_name = null;
						logoDataObj.file_extension_name = null;
					}
				}else if(typeClassName.indexOf("rectanglelogo") >=0){
					if(img.naturalWidth != 1000 || img.naturalHeight != 250){
						flag = false;
						$("#errMsg2").text("請上傳圖片解析度1000x250尺寸");
					}
					
					if(flag){
						$("#errMsg2").text("");
						$(notebox)[0].innerHTML = '上傳成功';
						$(success).css("display","block");
						$(select).css("display","none");
						$(review).css("display","none");
						logoDataObj.rectangle.base64 = base64Img;
						logoDataObj.rectangle.file_name = fileName;
						logoDataObj.rectangle.file_extension_name = fileType;
					}else{
						img.src = '';
						$(select).css("display","block");
						$(review).css("display","none");
						$(success).css("display","none");
						logoDataObj.rectangle.base64 = null;
						logoDataObj.rectangle.file_name = null;
						logoDataObj.rectangle.file_extension_name = null;
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
	if(obj == 'square'){
		success = document.getElementsByClassName("success")[0];
		select = document.getElementsByClassName("select")[0];
		review = document.getElementsByClassName("review")[0];
		logoDataObj.square.base64 = null;
		logoDataObj.square.file_name = null;
		logoDataObj.square.file_extension_name = null;
	}
	if(obj == 'rectangle'){
		success = document.getElementsByClassName("success")[1];
		select = document.getElementsByClassName("select")[1];
		review = document.getElementsByClassName("review")[1];
		logoDataObj.rectangle.base64 = null;
		logoDataObj.rectangle.file_name = null;
		logoDataObj.rectangle.file_extension_name = null;
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
	if(logoDataObj.square.base64 == null || logoDataObj.rectangle.base64 == null){
		console.log("---");
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
				alert("success");
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
