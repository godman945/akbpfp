//1.建立上傳畫面
//2.建立點擊預覽
//3.送出
var imgSeq = "";
var jsonObj = null;
//建立上傳後畫面
var fileArray =[];
var seqArray = [];
var uploadFileSize = "";
 
$(document).ready(function(){
	if($.browser.msie){
		if(parseInt($.browser.version) < 10){
			$("#alex").css("display","none");
			$("#notSuppotDiv").css("display","");
			$("#submitBtn").attr("disabled", true);
		}
	}
	
	
	//存入上傳後的圖片陣列
	$(function () {
	    $('#fileupload').fileupload({
	        url: 'adAddImgAjax.html',
	        fileElementId: 'fileupload',
	        success: function (respone) {
	        	imgSeq = respone;
	        	jsonObj =  JSON.parse(respone);
	        },
	        done: function (e, data) {
//	        	$("#fileUploadSize").text(parseInt($("#fileUploadSize").text())+ data.originalFiles.length);
	        },
	        dataType: 'json',
	        async: false,
//	        autoUpload: false,
//	        acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
//	        maxFileSize: 5000000, // 5 MB
//			previewMaxWidth: 210,
//			previewMaxHeight: 180,
//	        previewCrop: true
	    }).on('fileuploadadd', function (e, data) {
	    	uploadFileSize = data.originalFiles.length
	    	callBlockUpload();
	    }).on('fileuploaddone', function (e, data) {
	    	var index = parseInt($("#fileUploadIndex").text());
	    	index = index + 1;
	    	$("#fileUploadIndex").text(index);
//	    	if($("#fileUploadSize").text() == "0"){
//	    		$("#fileUploadSize").text(data.originalFiles.length);	
//	    	}
	    	fileArray.push(data.files[0]);
	    	seqArray.push(jsonObj.adSeq);
	    	//呼叫建立畫面
	
	    	createImgObjDom(data.files[0],jsonObj.imgWidth,jsonObj.imgHeight,jsonObj.fileSize,jsonObj.adSeq);
	    }).on('fileuploadprogressall', function (e, data) {	
	    }).on('fileuploadprocessalways', function (e, data) {
	    	//2015.7.12  tim   由於error後不會執行fileuploaddone,所以要加unblock()
	    	if(data.files.error){
	    		$('body').unblock();
	    	}
	    })
	});
	
	//連結網址鍵盤件鍵檢查
	$('#adLinkURL').bind('keyup', function() {
		chkWord($("#adLinkURL"), $("#spanAdLinkURL"));
	});
	
	//檢查網址blur事件
	$("#adLinkURL").blur(function() {
		chkUrl($("#adLinkURL"), $("#chkLinkURL"));
	});
});


function callBlock(){
	$("body").block({
		message: "<img src='html/img/LoadingWait.gif' />",
		css: {
			padding: 0,
			margin: 0,
			width: '50%',
			top: '40%',
			left: '35%',
			textAlign: 'center',
			color: '#000',
			border: '3px solid #aaa',
			backgroundColor: '#fff',
			cursor: 'wait'
		}
	});
}

function callBlockUpload(){
	$("body").block({
		message: "<h3>圖片上傳中，請稍後</h3>",
		css: {
			padding: 0,
			margin: 0,
			width: '50%',
			top: '40%',
			left: '35%',
			textAlign: 'center',
			color: '#000',
			border: '3px solid #aaa',
			backgroundColor: '#fff',
			cursor: 'wait'
		}
	});
}

//建立圖片Dom
var imgIndex = 0;
var flag = false;
function createImgObjDom(file,width, height, fileSize, adSeq) {
	
	if(flag == false){
		$("#fileUploadSize").text(parseInt($("#fileUploadSize").text()) + uploadFileSize);
		flag = true;
	}
	
	
	
	var imgFileSize = 'no';
	var imgSize = 'no';
	var imgSizeFlag = false;
	var imgTypeName = file.name.substr(file.name.indexOf(".")+1,file.name.length);
	var imgType ='no';
	var errorMsg ='';
	if(imgTypeName.toUpperCase() == "PNG" || imgTypeName.toUpperCase() == "JPG" || imgTypeName.toUpperCase() == "GIF"){
		imgType = "yes";
	}else{
		errorMsg = '檔案格式不符';
	}
	if(Math.round(file.size/1024) < 4000){
		imgFileSize = "yes";
	}else{
		errorMsg = '檔案格式不符';
	}
	
	$.each($("#adSizeDiv p"), function( index, obj ) {
		if($(obj).text() == width+" x "+height){
			imgSize = "yes";
			imgSizeFlag = true;
			return false;
		}
	});
	
	if(!imgSizeFlag){
		errorMsg = '檔案尺寸不符';
	}
	
	if (adSeq == "") {
		errorMsg = '檔案傳輸失敗';
	}
	
	if(imgFileSize == "yes" && imgSize == "yes" && imgType == "yes"){
		var anyWindow = window.URL || window.webkitURL;
		var objectUrl = anyWindow.createObjectURL(file);
		var a =
			 '<li class="okbox" id="'+adSeq+'">'+
			 '<div class="adboxdv">'+
			 '<img src="'+objectUrl+'">'+
			 '<p class="fancy adinf" onclick="preViewImg(\''+file.name+'\',\''+width+'\',\''+height+'\');" alt="預覽">預覽</p></div>'+
			 '<ul>'+
			 '<li class="'+imgSize+'"><i>尺寸</i><b>'+width+' x '+height+'</b></li>'+
			 '<li class="'+imgFileSize+'"><i>大小</i><b>'+Math.round(file.size/1024)+'</b></li>'+
			 '<li class="'+imgType+'"><i>格式</i><b>'+imgTypeName.toUpperCase()+'</b></li>'+
			 '</ul>'+
			 '<a class="addel" onclick="deleteImgDom(\''+adSeq+'\')">丟</a>'+ 
			 '</li>';
		$(".aduplodul").append(a);
	}else{
		var a =
			'<li class="failbox" id="'+adSeq+'">'+    
		    '<p class="addel"  onclick="deleteImgDom(\''+adSeq+'\');"></p> '+
		    '<em>上傳失敗!</em>'+
		    '<ul>'+
		    '<li class="'+imgSize+'"><i>尺寸</i><b>'+width+' x '+height+'</b></li>'+
			'<li><i>檔名</i><b>'+file.name+'</b></li>'+
			'<li class="'+imgType+'"><i>格式</i><b>'+imgTypeName.toUpperCase()+'</b></li>'+
		    '</ul> '+
		    '<div class="adboxdv">'+
		    '<span><i>說明：</i>'+errorMsg+'</span>'+
		    '<span class="adinf">系統無法上傳檔案!</span>  '+
		    '</div>'+
		    '</li>';
		$(".aduplodul").append($(a));
	}
	
	imgIndex = imgIndex +1
	if(imgIndex == uploadFileSize){
		$('body').unblock();
		
		
		$("#finalCount").empty();
		successCount = 0;
		failCount = 0;
		
		$.each($("#AG").children() , function( index, liObj ) {
			if($(liObj).attr("class") == "failbox"){
				failCount = failCount + 1;
			}else if($(liObj).attr("class") == "okbox"){
				successCount = successCount + 1;
			}
			
		})
		
		$("#finalCount").append("上傳成功:"+successCount+"張"+" 失敗:"+failCount+"張");
		imgIndex  = 0;
		flag = false;
	}
}
var successCount =0;
var failCount = 0;


//點擊預覽
function preViewImg(imgName,width,height){
	$.each($(fileArray), function( index, file ) {
		if(imgName == file.name){
			var anyWindow = window.URL || window.webkitURL;
		    var objectUrl = anyWindow.createObjectURL(file);
		    $("#preDiv").prepend('<img src="'+objectUrl+'" height="'+height+'" width="'+width+'">');
		    $.fancybox(
		    		$('#preDiv').html(),
		    		{
		    			'autoDimensions'	: false,
		    			'width'         	: width,
		    			'height'        	: height,
		    			'autoSize'			: true,
		    			'autoHeight'		: true,
		    			'autoScale'			: true,
		    			'transitionIn'		: 'none',
		    			'transitionOut'		: 'none',
		    			'padding'			: 0,
		    			'overlayOpacity'    : .75,
		    			'overlayColor'      : '#fff',
		    			'scrolling'			: 'no',
		    			onClosed    :   function() {
					    closePrew();
					    }  
		    		}
		    );
		}
	});
}

//刪除欲上傳檔案
function deleteImgDom(fileName){
//	var index = $("#fileUploadSize").text();
	$.each($(".aduplodul li"), function( index, obj ) {
		if(fileName == obj.id){
			$(this).remove();
			$.each($(fileArray), function( index, file ) {
				if(fileName == file.name){
					fileArray.splice(index, 1);
					return false;
				}
			});
			return false;
		}
	});
	
	$.each($(seqArray), function( index, adSeq ) {
		if( fileName == adSeq){
			seqArray.splice(index, 1);
			return false;
		}
	});
	
	$("#fileUploadSize").text($(seqArray).length);
	$("#fileUploadIndex").text($(seqArray).length);
	
	
	$("#finalCount").empty();
	successCount = 0;
	failCount = 0;
	$.each($("#AG").children() , function( index, liObj ) {
		if($(liObj).attr("class") == "failbox"){
			failCount = failCount + 1;
		}else if($(liObj).attr("class") == "okbox"){
			successCount = successCount + 1;
		}
		
	})
	$("#finalCount").append("上傳成功:"+successCount+"張"+" 失敗:"+failCount+"張");
	
}


//點擊允許尺寸
function approveSize(){
	 $.fancybox(
	    		$('#approveSizeDiv').html(),
	    		{
	    			'modal'             : true,
	    			'autoDimensions'	: false,
	    			'width'         	: 300,
	    			'height'        	: 'auto',
	    			'autoSize'			: true,
	    			'autoHeight'		: true,
	    			'autoScale'			: true,
	    			'transitionIn'		: 'none',
	    			'transitionOut'		: 'none',
	    			'padding'			: 0,
	    			'overlayOpacity'    : .75,
	    			'overlayColor'      : '#fff',
	    			'scrolling'			: 'no'
	    		}
	    );
}

//關閉預覽
function closePrew(){
	$("#preDiv").empty();
}

//關閉light box畫面
function closeBtn(){
	$.fancybox.close();
}
//存檔
function multipartImgUuploadSubmit(){
	$("#chkKeyword").html("");
	$("#chkFile").html("");
	
	var submitFlag = true;
	var keyWordArray = [];
	$.each($("#KeywordUL li"), function( index, obj ) {
		keyWordArray.push($(obj).text());
	});
	
	
	if($("#existKW").children().length == 0 && keyWordArray.length == 0){
		$("#chkAdKeyword").html("請新增關鍵字");
		$("#adKeyword").focus();
		return false;
	}
	
	var excludeKeywordULArray = [];
	$.each($("#ExcludeKeywordUL li"), function( index, obj ) {
		excludeKeywordULArray.push($(obj).text());
	});
	
	
	
	$.each($(".aduplodul li"), function( index, obj ) {
		if($(obj).attr("class") == "failbox"){
			submitFlag = false;
			return false;
		}
	});
	
	if (!chkUrl($("#adLinkURL"), $("#chkLinkURL"))) {
		$("#adLinkURL").focus();
		return false;
	}
	
	if(!submitFlag ){
		$("#chkFile").html("有錯誤的檔案");
		location.href = "#fileupload";
		return false;
	}
	if(seqArray.length == 0){
		$("#chkFile").html("請上傳檔案");
		location.href = "#fileupload";
		return false;
	}
	var map = {
		"seqArray" : seqArray
	}
	console.log(seqArray);
	
	var map = JSON.stringify(map);
	callBlock();
	$.ajax({
	url : "adAddImgSaveAjax.html",
	type : "POST",
	dataType:'json',
	data : {
		"seqArray" : map,
		"adGroupSeq": $("#adGroupSeq").val(),
		"adLinkURL" : $("#adLinkURL").val(),
		"keywords" : JSON.stringify(keyWordArray),
		"excludeKeywords" : JSON.stringify(excludeKeywordULArray)
	},
	success : function(respone) {
		$('body').unblock();
		$(location).attr( 'href' , 'adAdView.html?adGroupSeq='+$("#adGroupSeq").val());
	}
});


}  
//取消
function cancerSubmit(){
	$(location).attr( 'href' , 'adActionView.html');
}

//檢查網址是否有效
function chkUrl(valObj, msgObj){
	var val = valObj.val();
	
	msgObj.css("color","");
	msgObj.text("");
	
	if ((val == "") ||
			(val.indexOf("show.pchome.com.tw") == 0)) {
		msgObj.css("color","red");
		msgObj.text("請輸入廣告連結網址");
		return false;
	}

	if(!validURL(val)) {
		msgObj.css("color","red");
		msgObj.text("請輸入正確的廣告連結網址");
		return false;
	}
	
	msgObj.text("網址檢查中");
//	$.ajax({
//		type: "POST",
//		url: "checkAdUrl.html",
//		data: { url: val },
//		async: false
//	}).complete(function(result) {
//		alert(result);
//		alert(result == "true");
//		alert(result == true);
//		if (result == "true") {
//			msgObj.css("color","green");
//			msgObj.text("網址確認正確");
//			return true;
//		}
//		else {
//			msgObj.css("color","red");
//			msgObj.text("請輸入正確的廣告連結網址");
//			return false;
//		}
//	});
	
	var result = $.ajax({
		type: "POST",
		url: "checkAdUrl.html",
		data: { url: val },
		async: false
	});
	
	if (result.responseText == "true") {
		msgObj.css("color","green");
		msgObj.text("網址確認正確");
		return true;
	}
	else {
		msgObj.css("color","red");
		msgObj.text("請輸入正確的廣告連結網址");
		return false;
	}
}

function validURL(url) {
	var re = /^(http[s]?:\/\/){0,1}(www\.){0,1}[a-zA-Z0-9\.\-\u4e00-\u9fa5]+\.[a-zA-Z]{2,5}[\.]{0,1}/;
	return re.test(url);
}

//輸入字數檢查與提示
function chkWord(valObj, msgObj) {
	var length = parseInt(valObj.val().length);
	var maxlength = parseInt(valObj.attr("maxlength"));
	
	msgObj.css("color","");
	msgObj.text("");
	
	if (length <= maxlength) {
		msgObj.css("color","");
		msgObj.text("已輸入" + length + "字，剩" + (maxlength - length) + "字");
		return true;
	}
	
	return false;
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
	
	var number = 0;
	
	$.each($(".adboxdv"),function(){
		number++;
	});
	
	if(keywords != "" || excludeKeywords != "" || number != 0 || 
			$("#adLinkURL").val().replace($("#adShowURL").attr("placeholder"),"") != "" ){
		
		if(!confirm("您的廣告尚未編輯完成，離開後廣告資料不會存檔。")){
			return false;
		}
	}
	
	return true;
}