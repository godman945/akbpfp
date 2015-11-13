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
	//隱藏關鍵字區域
	var adType = $("#adType").val();
	if(adType == "2"){
		$("#keywordBody").hide();	
	}
	
	$("#fileupload").hide();
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
	
	    	createImgObjDom(data.files[0],jsonObj.imgWidth,jsonObj.imgHeight,jsonObj.fileSize,jsonObj.adSeq,jsonObj.imgMD5,jsonObj.imgRepeat);
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
function createImgObjDom(file,width, height, fileSize, adSeq, imgMD5, imgRepeat) {
	
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
	var errorTitle ='';
	if(Math.round(file.size/1024) < 4000){
		imgFileSize = "yes";
	}else{
		errorTitle = '檔案過大!';
		errorMsg = '檔案大小上限4000KB';
	}
	$.each($("#adSizeDiv p"), function( index, obj ) {
		if($(obj).text().indexOf(width+" x "+height) >= 0){
			imgSize = "yes";
			imgSizeFlag = true;
			return false;
		}
	});
	if(!imgSizeFlag){
		errorTitle = '錯誤的尺寸!';
		errorMsg = '上傳圖片的<a id="errAdImg" name="errAdImg" onclick="approveSize();">支援規格查詢</a>';
	}
	if(imgTypeName.toUpperCase() == "PNG" || imgTypeName.toUpperCase() == "JPG" || imgTypeName.toUpperCase() == "GIF"){
		imgType = "yes";
	}else{
		errorTitle = '錯誤的檔案類型!';
		errorMsg = '支援的檔案類型JPG、PNG、GIF';
	}
	
	if(imgRepeat == 'yes'){
		errorTitle = '廣告圖片已存在!';
		errorMsg = '您所上傳的廣告圖片在分類中已存在';
	}
	
	var thisImgRepeat = 'no';
	$.each($("[name=imgMD5]"), function( index, obj ) {
		if($(obj).val() == imgMD5){
			errorTitle = '廣告圖片重複上傳!';
			errorMsg = '您所上傳的廣告圖片在此次新增中已存在';
			thisImgRepeat = 'yes';
			return false;
		}
	});
	
	if (adSeq == "") {
		errorTitle = '上傳失敗!';
		errorMsg = '檔案空白';
	}
	
	if(imgFileSize == "yes" && imgSize == "yes" && imgType == "yes"  && imgRepeat == "no" && thisImgRepeat == "no"){
		var anyWindow = window.URL || window.webkitURL;
		var objectUrl = anyWindow.createObjectURL(file);
		var fileName = file.name;
		var showFileName = "";
		if(fileName.lastIndexOf(".") >= 0){
			fileName = fileName.substring(0,fileName.lastIndexOf("."));
		}
		if(fileName.length > 1024){
			fileName = fileName.substring(0,1024);
		}
		if(fileName.length > 8){
			showFileName = fileName.substring(0,8) + "...";
		} else {
			showFileName = fileName;	
		}
		
		var a =
			 '<li class="okbox" style="padding: 0 0 20px 0;"  id="'+adSeq+'">'+
			 '<div class="adboxdv" >'+
			 '<img src="'+objectUrl+'">'+
			 '<p class="fancy adinf" onclick="preViewImg(\''+file.name+'\',\''+width+'\',\''+height+'\');" alt="預覽">預覽</p></div>'+
			 '<ul>'+
			 '<li><i>名稱</i><b>' + showFileName + '</b></li>' + 
			 '<li class="'+imgSize+'"><i>尺寸</i><b>'+width+' x '+height+'</b></li>'+
			 '<li class="'+imgFileSize+'"><i>大小</i><b>'+Math.round(file.size/1024)+'</b></li>'+
			 '<li class="'+imgType+'"><i>格式</i><b>'+imgTypeName.toUpperCase()+'</b></li>'+
			 '</ul>'+
			 '<a class="addel" style="top:240px;" onclick="deleteImgDom(\''+adSeq+'\')">丟</a>'+ 
			 '<input type="hidden" id="' + adSeq + '_title" name="imgName" value="' + fileName + '" />' + 
			 '<input type="hidden" id="' + adSeq + '_imgMD5" name="imgMD5" value="' + imgMD5 + '" />' + 
			 '</li>';
		$(".aduplodul").append(a);
	}else{
		var a =
			'<li class="failbox" style="padding: 20px 0 0 0;" id="'+adSeq+'">'+  
			'<a class="addel" onclick="deleteImgDom(\''+adSeq+'\')">丟</a>'+
		    //'<p class="addel"  onclick="deleteImgDom(\''+adSeq+'\');"></p> '+
		    '<em style="line-height:27px;font-size:23px;">'+errorTitle+'</em>'+
		    '<em style="line-height:23px;font-size:15px;">請重新上傳檔案</em>'+
		    '<ul>'+
		    '<li class="'+imgSize+'"><i>尺寸</i><b>'+width+' x '+height+'</b></li>'+
			'<li><i>檔名</i><b style="word-break:keep-all;white-space:nowrap;overflow:hidden;">'+file.name+'</b></li>'+
			'<li class="'+imgFileSize+'"><i>大小</i><b>'+Math.round(file.size/1024)+'</b></li>'+
			'<li class="'+imgType+'"><i>格式</i><b>'+imgTypeName.toUpperCase()+'</b></li>'+
		    '</ul> '+
		    '<div class="adboxdv">'+
		    '<span><i>說明：</i>'+errorMsg+'</span>'+
		    //'<span class="adinf">系統無法上傳檔案!</span>  '+
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
		    			'autoScale'			: false,
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
	    			'width'         	: 460,
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
	
	//var submitFlag = true;
	var keyWordArray = [];
	$.each($("#KeywordUL li"), function( index, obj ) {
		keyWordArray.push($(obj).text());
	});
	
	var adType = $("#adType").val();
	if(adType == "0" || adType == "1"){
		if($("#existKW").children().length == 0 && keyWordArray.length == 0){
			$("#chkAdKeyword").html("請新增關鍵字");
			$("#adKeyword").focus();
			return false;
		}
	}
	
	var excludeKeywordULArray = [];
	$.each($("#ExcludeKeywordUL li"), function( index, obj ) {
		excludeKeywordULArray.push($(obj).text());
	});
	
	
	var seqOkArray = [];
	$.each($(".aduplodul li"), function( index, obj ) {
		if($(obj).attr("class") == "okbox"){
			seqOkArray.push($(obj).attr("id"));
		}
	});
	
	if (!chkUrl($("#adLinkURL"), $("#chkLinkURL"))) {
		$("#adLinkURL").focus();
		return false;
	}
	
	/*if(!submitFlag ){
		$("#chkFile").html("有錯誤的檔案");
		location.href = "#fileupload";
		return false;
	}*/
	if(seqArray.length == 0){
		$("#chkFile").html("請上傳廣告圖片");
		location.href = "#fileButton";
		return false;
	}
	if(seqOkArray.length == 0){
		$("#chkFile").html("請重新上傳正確格式的廣告圖片");
		location.href = "#fileButton";
		return false;
	}
	
	var imgNameMap = {};
	$.each($("[name=imgName]"), function( index, obj ) {
		imgNameMap[$(obj).attr("id")] = $(obj).val();
	});
	
	var imgMD5Map = {};
	$.each($("[name=imgMD5]"), function( index, obj ) {
		imgMD5Map[$(obj).attr("id")] = $(obj).val();
	});
	
	var map = {
		"seqArray" : seqOkArray,
		"imgNameMap" : imgNameMap,
		"imgMD5Map" : imgMD5Map
	}
	console.log(seqOkArray);
	console.log(imgNameMap);
	var alt = "提醒您，您的廣告將在3工作天(周一到周五)審核完成(不含例假日)，並於廣告審核完成後開始播放";
	if(confirm(alt)) {
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
				$(location).attr( 'href' , 'adAddFinish.html?adGroupSeq='+$("#adGroupSeq").val());
			}
		});
	}

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

function fileLoad(){
	$("#chkFile").html("");
	$("#fileupload").click();
}