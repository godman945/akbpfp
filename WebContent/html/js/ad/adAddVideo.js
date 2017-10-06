
var fileArray =[];
var seqArray = [];
$(document).ready(function(){
	$('#save').click(function(){
		saveData();
	});
	
	//影片網址檢查
	var adVideoLinkWord = 1024;
	$('#adVideoURL').bind('keyup', function() {
		var wordLength = adVideoLinkWord - $("#adVideoURL").val().length;
		if(wordLength >= 0){
			$("#adVideoUrlWordController").text('已輸入'+$("#adVideoURL").val().length+'字，剩'+wordLength+'字');
			$("#checkWord").text(wordLength);
			$("#adVideoUrlWordController").css('color','');
			$("#adVideoURLMsg").text('');
		}else if(wordLength < 0){
			wordLength = Math.abs(wordLength);
			$("#adVideoUrlWordController").text('已輸入'+$("#adVideoURL").val().length+'字，超過'+wordLength+'字');
			$("#checkWord").text(wordLength);
			$("#adVideoUrlWordController").css('color','red');
			$("#adVideoURLMsg").text('廣告連結網址最多輸入1024字');
		}
	});
	
	
	//影片連結檢查
	var adLinkUrlWord = 1024;
	$('#adLinkURL').bind('keyup', function() {
		var wordLength = adLinkUrlWord - $("#adLinkURL").val().length;
		if(wordLength >= 0){
			$("#spanAdLinkURL").text('已輸入'+$("#adLinkURL").val().length+'字，剩'+wordLength+'字');
			$("#spanAdLinkURL").css('color','');
		}else if(wordLength < 0){
			wordLength = Math.abs(wordLength);
			$("#spanAdLinkURL").text('已輸入'+$("#adLinkURL").val().length+'字，超過'+wordLength+'字');
			$("#spanAdLinkURL").css('color','red');
		}
		
		$("#alex").attr("src","http://localhost:8080/akbpfp/videoad_01.jsp?width=300&height=168");
	});
	
	
	//檢查網址blur事件
	$("#adLinkURL").blur(function() {
			$.ajax({
			type: "POST",
			url: "checkAdUrl.html",
			data: { url: $("#adLinkURL").val()},
		}).done(function(msg) {
			if(msg == 'false'){
				$("#chkLinkURL").text('');
				$("#chkLinkURL").text('請輸入正確廣告連結網址');
				$("#chkLinkURL").css('color','red');
			}else if(msg == 'true'){
				$("#chkLinkURL").text('網址確認正確');
				$("#chkLinkURL").css('color','green');
			}
		});
	});
	
	var videoUrl = null;
	//檢查廣告網址blur事件
	$("#adVideoURL").blur(function() {
		if($("#adVideoURL").val() == ""){
			$("#adVideoURLMsg").text('請輸入影片網址');
			console.log('請輸入影片網址');
			return false;
		}
		
		var regx = new RegExp(/^[hH][tT][tT][pP]([sS]?):\/\/(\S+\.)+\S{2,}$/);
		console.log($("#adVideoURL").val());
		console.log(regx.test($("#adVideoURL").val()));
		if(!regx.test($("#adVideoURL").val())){
			$("#adVideoURLMsg").text('網址格式錯誤');
			return false;
		}else{
			videoUrl = $("#adVideoURL").val();
			videoUrl = videoUrl.replace('watch?v=','embed/');
			console.log(videoUrl);
			$("#bessie").attr('src',videoUrl);
		}
		
		$("#adVideoURLMsg").text('');
		
		$.ajax({
			url: "chkVideoUrl.html",
			data:{
				adVideoUrl: $("#adVideoURL").val()
			},
			type:"POST",
			dataType:"JSON",
			success:function(result, status){
//				console.log(response);
			},
			error: function(xtl) {
				//alert("系統繁忙，請稍後再試！");
			}
		}).done(function (result) {
			if(result.result == true){
				$("#adVideoURLMsg").css('color','green');
				$("#adVideoURLMsg").text('影片網址確認正確');
			}else{
				$("#adVideoURLMsg").css('color','red');
				$("#adVideoURLMsg").text(result.msg);
			}
		});
	});
	
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
	        },
	        dataType: 'json',
	        async: false,
	    }).on('fileuploadadd', function (e, data) {
	    	uploadFileSize = data.originalFiles.length
	    	if(uploadFileSize > 10){
	    		alert("一次最多只能上傳10個檔案!!");
	    		return false;
	    	}
	    	callBlockUpload();
	    }).on('fileuploaddone', function (e, data) {
	    	var index = parseInt($("#fileUploadIndex").text());
	    	index = index + 1;
	    	$("#fileUploadIndex").text(index);
	    	//呼叫建立畫面
	    	createImgObjDom(data.files[0],jsonObj.imgWidth,jsonObj.imgHeight,jsonObj.fileSize,jsonObj.adSeq,jsonObj.imgMD5,jsonObj.imgRepeat,jsonObj.html5Repeat,jsonObj.imgSrc,jsonObj.errorMsg);
	    	fileArray.push(data.files[0]);
	    	seqArray.push(jsonObj.adSeq);
	    }).on('fileuploadprogressall', function (e, data) {	
	    }).on('fileuploadprocessalways', function (e, data) {
	    	//2015.7.12  tim   由於error後不會執行fileuploaddone,所以要加unblock()
	    	if(data.files.error){
	    		$('body').unblock();
	    	}
	    })
	});
	
	
	
	
	
});


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
function createImgObjDom(file,width, height, fileSize, adSeq, imgMD5, imgRepeat, html5Repeat, imgSrc, zipErrorMsg) {
	
	if(flag == false){
		$("#fileUploadSize").text(parseInt($("#fileUploadSize").text()) + uploadFileSize);
		flag = true;
	}
	
	var imgRepeatFlag = false;
	var imgFileSize = 'no';
	var imgSize = 'no';
	var imgSizeFlag = false;
	var imgTypeName = file.name.substr(file.name.indexOf(".")+1,file.name.length);
	var imgType ='no';
	var fileSize = Math.round(file.size/1024);
	var errorMsg ='';
	var errorTitle ='';
	
	$.each($("#adSizeDiv p"), function( index, obj ) {
		if($(obj).text().indexOf(width+" x "+height) >= 0){
			imgSize = "yes";
			imgSizeFlag = true;
			return false;
		}
	});
	
	//檢核檔案大小
	if(fileSize < 180){
		imgFileSize = "yes";
	}else{
		errorTitle = '檔案過大!';
		errorMsg = '檔案大小上限180KB';
	}
	
	//檢核檔案尺寸
	if(!imgSizeFlag){
		errorTitle = '錯誤的尺寸!';
		errorMsg = '上傳圖片的<a id="errAdImg" name="errAdImg" style="cursor: pointer;" onclick="approveSize(\'approveSizeDiv\');">支援規格查詢</a>';
	}
	
	//檢核檔案附檔名
	if(imgTypeName.toUpperCase() == "PNG" || imgTypeName.toUpperCase() == "JPG" || imgTypeName.toUpperCase() == "GIF"){
		imgType = "yes";
	}else{
		errorTitle = '錯誤的檔案類型!';
		errorMsg = '支援的檔案類型JPG、PNG、GIF、ZIP';
	}
	
	//檢核檔案上傳結果
	if(adSeq == "") {
		errorTitle = '上傳失敗!';
		errorMsg = '檔案空白';
	}
	
	//檢核重複上傳
	fileArray.forEach(function(fileData,index) {
		if(file.name == fileData.name){
			imgRepeatFlag = true;
			errorTitle = "廣告圖片已存在!"
				errorMsg = '您所上傳的廣告圖片在此次新增中已存在';
			return;
		}
	})
	
	
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
	
	if(imgFileSize == "yes" && imgSize == "yes" && imgType == "yes" && imgRepeatFlag == false){
		var anyWindow = window.URL || window.webkitURL;
		var objectUrl = anyWindow.createObjectURL(file);
		var a =
			 '<li class="okbox" style="padding: 0 0 20px 0;width: 18.5%; "  id="'+adSeq+'">'+
			 '<div class="adboxdv" >'+
			 '<img src="'+objectUrl+'">'+
			 '<p class="fancy adinf" onclick="preViewImg(\''+file.name+'\',\''+width+'\',\''+height+'\');" alt="預覽">預覽</p></div>'+
			 '<ul style="margin-top: 5px;margin-left: -5px;">'+
			 '<li><input type="radio" style="visibility:visible;float: left;display: block;"  name="'+width+height+'" checked onclick="clickSizePic(this,\''+width+'\',\''+height+'\')"><i>名稱</i><b>' + showFileName + '</b></li>' + 
			 '<li style="margin-left:30px;" class="'+imgSize+'"><i>尺寸</i><b>'+width+' x '+height+'</b></li>'+
			 '<li style="margin-left:30px;" class="'+imgFileSize+'"><i>大小</i><b>'+fileSize+'</b></li>'+
			 '<li style="margin-left:30px;" class="'+imgType+'"><i>格式</i><b>'+imgTypeName.toUpperCase()+'</b></li>'+
			 '</ul>'+
			 '<a class="addel" style="top:275px;" onclick="deleteImgDom(\''+adSeq+'\')">丟</a>'+ 
			 '<input type="hidden" id="' + adSeq + '_title" name="imgName" value="' + fileName + '" />' + 
			 '<input type="hidden" id="' + adSeq + '_imgMD5" name="imgMD5" value="' + imgMD5 + '" />' + 
			 '<input type="hidden" id="' + adSeq + '_format" value="' + imgTypeName + '" />' +
			 '</li>';
		$(".aduplodul_p").append(a);
		
		
		
		
		$("#AG").children().each(function(index,value){
			var checkFlag = true;
			var radioName = null;
			$(this).children().each(function(index,value){
				if(index ==1){
					$(this).children().each(function(index,value){
						if(index == 0){
							checkFlag = $(this).children()[0].checked;
							radioName = $(this).children()[0].name;
						}
						
						if(!checkFlag && index == 1 && radioName == width+height && $($(this).children()[1]).text() == width+' x '+height){
							$(this).attr('class','no');
							var data = $($(this).children()[1]).html("<br>");
							$(this).children()[1].append('尺寸重複，僅能選擇一款。');
							$(data).prepend(width+" x "+height);
						}
					})
					}
			})
		})
	}else if(imgSize == "no" || imgFileSize == "no" || imgType == "no" || imgRepeatFlag){
		var a =
			'<li class="failbox" style="padding: 20px 0 0 0;width: 18.5%;" id="'+adSeq+'">'+  
			'<a class="addel" onclick="deleteImgDom(\''+adSeq+'\')">丟</a>'+
		    '<em style="line-height:27px;font-size:23px;">'+errorTitle+'</em>'+
		    '<em style="line-height:23px;font-size:15px;">請重新上傳檔案</em>'+
		    '<ul style="height:110px;" >'+
		    '<li class="'+imgSize+'"><i>尺寸</i><b>'+width+' x '+height+'</b></li>'+
			'<li><i>檔名</i><b style="word-break:keep-all;white-space:nowrap;overflow:hidden;">'+showFileName+'</b></li>'+
			'<li class="'+imgFileSize+'"><i>大小</i><b>'+fileSize+'</b></li>'+
			'<li class="'+imgType+'"><i>格式</i><b>'+imgTypeName.toUpperCase()+'</b></li>'+
		    '</ul> '+
		    '<div class="adboxdv">'+
		    '<span><i>說明：</i>'+errorMsg+'</span>'+
		    '</div>'+
		    '</li>';
		$(".aduplodul_p").append($(a));
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

function clickSizePic(obj,width,height){
	var ulObj = $($(obj).parent()).parent();
	var liObj = $(ulObj).children()[1];
	$(liObj).attr('class','yes');
	
	var text =  $($(liObj).children()[1]).text();
	text = text.replace("	尺寸重複，僅能選取一張","");
	$($(liObj).children()[1]).text(text);
	
	$("#AG").children().each(function(index,value){
		var checkFlag = true;
		var radioName = null;
		$(this).children().each(function(index,value){
			if(index ==1){
				$(this).children().each(function(index,value){
					if(index == 0){
						checkFlag = $(this).children()[0].checked;
						radioName = $(this).children()[0].name;
					}
					
					if(!checkFlag  && index == 1 && radioName == obj.name){
						//$($(this).children()[1]).empty();
						console.log(this);
						console.log(checkFlag);
						console.log(radioName);
						console.log($(this).children()[1]);
					}
					
					if(!checkFlag && index == 1 && radioName == obj.name){
						$($(this).children()[1]).empty();
						$(this).attr('class','no');
						var data = $($(this).children()[1]).html("<br>");
						$(this).children()[1].append('尺寸重複，僅能選擇一款。');
						$(data).prepend(width+" x "+height);
					}
				})
			}
		})
	})
}

function saveAdAddVideo(result){
	console.log(result);
}

//清空上傳資料
function fileLoad(){
	$("#fileupload").click();
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
	
	successCount = 0;
	failCount = 0;
	$.each($("#AG").children() , function( index, liObj ) {
		if($(liObj).attr("class") == "failbox"){
			failCount = failCount + 1;
		}else if($(liObj).attr("class") == "okbox"){
			successCount = successCount + 1;
		}
	})
}

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

//儲存廣告
function saveData() {
	
//	//廣告影片網址不可為空
//	if($("#adVideoURL").val() == ""){
//		$("#adVideoURLMsg").text('請輸入影片網址');
//		var position = $('#adVideoURL').offset();  
//		var x = position.left;  
//		var y = position.top;  
//		window.scrollTo(x,y);
//		return false;
//	}
//	
//	//廣告連結網址不可為空
//	if($("#adLinkURL").val() == ""){
//		$("#chkLinkURL").text('請輸入影片網址');
//		var position = $('#adLinkURL').offset();  
//		var x = position.left;  
//		var y = position.top;  
//		window.scrollTo(x,y);
//		return false;
//	}
//	
//	if($("#adVideoURLMsg").text() != "影片網址確認正確" || $("#chkLinkURL").text() != "網址確認正確"){
//		if($("#adVideoURLMsg").text() != "影片網址確認正確"){
//			var position = $('#adVideoURL').offset();  
//			var x = position.left;  
//			var y = position.top;  
//			window.scrollTo(x,y);
//		}
//		
//		if($("#chkLinkURL").text() != "網址確認正確"){
//			var position = $('#adLinkURL').offset();  
//			var x = position.left;  
//			var y = position.top;  
//			window.scrollTo(x,y);
//		}
//		return false;
//	}
//	
//	if($("#errMsg").text() != "" && $("#errMsg").text() != "上傳成功"){
//		var position = $('#fileButton').offset();  
//		var x = position.left;  
//		var y = position.top;  
//		window.scrollTo(x,y);
//		return false;
//	}
	
	console.log('資料OK');
	
	
	
	
	var videoPic = [];
	$('#AG li input[type=radio]').each(function(){
		var checked = $(this).attr('checked');
		if(checked == 'checked'){
			var sizeObj = $($($(this).parent()).parent()).children()[1];
			var adSeq = $($($($(this).parent()).parent()).parent()).attr("id");
			sizeObj = $($(sizeObj).children()[1]).text();
			var map = new Object();
			map["size"] = sizeObj.replace(" x ","");
			map["adSeq"] = adSeq;
			map["format"] = $("#"+adSeq+"_format").val();
			videoPic.push(map);
		}
	});
	
	callBlock();
	console.log(videoPic);
//	$("#modifyForm").submit();
	$.ajax({
		url : "adAddVideoSaveAjax.html",
		type : "POST",
		dataType:'json',
		data : {
			"videoPicId":JSON.stringify(videoPic),
			"adGroupSeq":$("#adGroupSeq").val(),
			"adStyle":$("#adStyle").val(),
			"adClass":$("#adClass").val(),
			"adVideoURL":$("#adVideoURL").val(),
			"adLinkURL":$("#adLinkURL").val(),
		},
		success : function(respone) {
			console.log(respone);
			$('body').unblock();
			if(respone == "success"){
				$(location).attr('href','adAddVideoFinish.html?adGroupSeq='+$("#adGroupSeq").val());	
			} else {
//				alert(respone);
			}
		}
	});
}





//點擊允許尺寸
function approveSize(approveSizeDiv){
	var height = $('#' + approveSizeDiv).height;
	var width = $('#' + approveSizeDiv).width;
	$.fancybox(
	    		$('#' + approveSizeDiv).html(),
	    		{
	    			'modal'             : true,
	    			'autoDimensions'	: false,
	    			'width'        		: width,
	    			'height'        	: height,
	    			'autoSize'			: true,
	    			'autoHeight'		: true,
	    			'autoScale'			: true
	    		}
	);
}

//關閉light box畫面
function closeBtn(){
	$.fancybox.close();
}

//關閉預覽
function closePrew(){
	$("#preDiv").empty();
}

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