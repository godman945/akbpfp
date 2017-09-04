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
//			$("#adVideoURLMsg").text('影片長度不得超過30秒。請重新上傳30秒以內的影片。');
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
	
	//檢查廣告網址blur事件
	$("#adVideoURL").blur(function() {
		if($("#adVideoURL").val() == ""){
			$("#adVideoURLErrMsg").text('請輸入影片網址');
			console.log('請輸入影片網址');
			return false;
		}
		
		var regx = new RegExp(/^[hH][tT][tT][pP]([sS]?):\/\/(\S+\.)+\S{2,}$/);
		console.log($("#adVideoURL").val());
		console.log(regx.test($("#adVideoURL").val()));
		if(!regx.test($("#adVideoURL").val())){
			$("#adVideoURLErrMsg").text('網址格式錯誤');
			return false;
		}
		
		$("#adVideoURLErrMsg").text('');
		
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
			console.log(result);
			console.log(result.result);
			console.log(result.msg);
//			saveAdAddVideo(result);
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
	        	jsonObj['file'] = data.files[0]
		    	videoInfoJsonArray.push(jsonObj);
		    	doUploadSize += 1;
		    	if(doUploadSize == uploadSize){
		    		console.log('建立預覽畫面');
		    		createPreView(videoInfoJsonArray);
		    	}
//	        	console.log('11111111111');
//	        	console.log(data.files[0]);
//	        	console.log(jsonObj);
	        	//呼叫建立預覽畫面
//	        	$("#fileUploadSize").text(parseInt($("#fileUploadSize").text())+ data.originalFiles.length);
	        },
	        dataType: 'json',
	        async: true,
//	        autoUpload: false,
//	        acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
//	        maxFileSize: 5000000, // 5 MB
//			previewMaxWidth: 210,
//			previewMaxHeight: 180,
//	        previewCrop: true
	    }).on('fileuploadadd', function (e, data) {
//	    	console.log('222222222222222');
//	    	console.log(data.files);
//	    	getPreViewObj(width,height,image,file);
//	    	uploadFileSize = data.originalFiles.length
//	    	callBlockUpload();
	    }).on('fileuploaddone', function (e, data) {
//	    	console.log('333333333333333333333333');
//	    	createPreViw(jsonObj.imgWidth,jsonObj.imgHeight,data.files[0]);

	    	
	    	
	    	
	    	
//	    	var index = parseInt($("#fileUploadIndex").text());
//	    	index = index + 1;
//	    	$("#fileUploadIndex").text(index);
////	    	if($("#fileUploadSize").text() == "0"){
////	    		$("#fileUploadSize").text(data.originalFiles.length);	
////	    	}
//	    	fileArray.push(data.files[0]);
//	    	seqArray.push(jsonObj.adSeq);
	    	//呼叫建立畫面
//	    	createImgObjDom(data.files[0],jsonObj.imgWidth,jsonObj.imgHeight,jsonObj.fileSize,jsonObj.adSeq,jsonObj.imgMD5,jsonObj.imgRepeat,jsonObj.html5Repeat,jsonObj.imgSrc,jsonObj.errorMsg);
	    
	    	
	    }).on('fileuploadprogressall', function (e, data) {	
//	    	console.log('44444444444444444444');
	    	
	    }).on('fileuploadprocessalways', function (e, data) {
	    	//2015.7.12  tim   由於error後不會執行fileuploaddone,所以要加unblock()
	    	if(data.files.error){
	    		$('body').unblock();
	    	}
	    })
	});
});


//建立預覽畫面
var defineSize = {'300250':0,'300600':0,'320480':0,'970250':0};
function createPreView(obj){
	
	$.each(defineSize, function (key, data) {
		defineSize[key] = 0;
	});
	
	//檢查上傳尺寸規格
	$(obj).each(function(index,value){
		$.each(defineSize, function (key, data) {
			if((value.imgWidth+value.imgHeight) == key){
				defineSize[key] = data += 1;
			}
		})
	});
	
	var flag = true;
	$.each(defineSize, function (key, data) {
		if(data > 1){
			var size = key;
			size = size.substring(0,3)+" x "+size.substring(3,size.length);
			flag = false;
			$("#errMsg").text(size+'尺寸只可上傳一張');
			$("#errMsg").css('color','red');
			$("#errMsg").css('display','');
		}
	});
	
	
	//檢查完畢開始建立預覽
	if(flag){
		$("#errMsg").text('上傳成功');
		$("#errMsg").css('display','');
		$("#errMsg").css('color','green');
		
		$.each(defineSize, function (key, data) {
			if(data == 1){
				$(".okbox").each(function(){
					 var sizeObj = $($($(this).children()[1]).children()[0]).children()[0];
					 var size = $(sizeObj).text();
					 size = size.replace(' x ','');
					 if(size == key){
						 $(obj).each(function(index,value){
							 if((value.imgWidth+value.imgHeight) == size){
								 create(value.imgWidth,value.imgHeight,value.file,sizeObj,value.adSeq);
							 }
						 });
					 }
				});
			}
		});
	}
}

//開始建立
function create(width,height,file,obj,adSeq){
	var anyWindow = window.URL || window.webkitURL;
	var objectUrl = anyWindow.createObjectURL(file);
	var fileName = file.name;
	var type = fileName.substring(fileName.length-3,fileName.length);
	
	if(fileName.length > 14){
		fileName = fileName.substring(0,10)+'...';
	}
	
	var size = Math.round(file.size / 1024);
	
	$(obj).css('display','none');
	var liObj = $(obj).parent();
	liObj.append('<i>名稱</i><b>'+fileName+'</b>')
		
	var ulObj = $($(obj).parent()).parent();
	ulObj.append('<li class="yes"><i>尺寸</i><b>'+width+' x '+height+'</b></li>');
	ulObj.append('<li class="yes"><i>大小</i><b>'+size+'</b></li>');
	ulObj.append('<li class="yes"><i>格式</i><b>'+type+'</b></li>');
		
	var imgObj = $($($($($(obj).parent()).parent()).parent()).children()[0]).children()[0];
	$(imgObj).attr('src',objectUrl);
	
	var divObj = $($($($(obj).parent()).parent()).parent()).children()[0];
	$(divObj).append('<p class ="fancy adinf" onclick="preView(this);" alt="預覽">預覽</p>');
		
	var deleteObj = $(divObj).parent();
	$(deleteObj).append('<a class="addel" style="top:250px;" onclick="deleteImgDom(\''+file.name+'\',this)">丟</a>');
	$(deleteObj).append('<input class="picSeq" type="hidden" value='+adSeq+'>');
	
	
}


function saveAdAddVideo(result){
	console.log(result);
}

//清空上傳資料
function fileLoad(){
	$("#fileupload").click();
}

//預覽圖片
var uploadSize = 0;
var doUploadSize = 0;
var jsonObj = null;
var videoInfoJsonArray = [];
function previewImage(file) {
	uploadSize = $("#fileupload")[0].files.length;
	
	if(uploadSize > 0){
		jsonObj = null;
		doUploadSize = 0;
		videoInfoJsonArray = [];
		$(".okbox").each(function(){
		 	var imgUrl = $($(this).children()[0]).children()[0];
		 	imgUrl.src = './html/img/upl9090.gif';
		 	$($($(this).children()[0]).children()[1]).remove();
		 	
		 	var ulObj = $(this).children()[1];
			$(ulObj).children().each(function(index,value){
				if(index == 0){
					$($(value).children()[1]).remove();
					$($(value).children()[1]).remove();
					$($(value).children()[0]).css('display','');
					
				}else{
					$(this).remove();
				}
			})
			$($(this).children()[2]).remove();
	 })
	}
	
//	$('#imghead').attr('src','');
//	var flag = checkUploadImg();
	
	
//	sizeFlag = true;
//	var size = 0;
//	
//	if(!$.browser.msie ) { 
//		size = ($("#uploadFile")[0].files[0].size / 1024);
//	}
//	if($.browser.msie) { 
//		deleteImage();
//	}
//	size = Math.round(size);
//	if(size > 1024){
//		sizeFlag = false;
//		$("#sizeCheckDiv").css("display","");
//		$("#uploadCheckDiv").css("display","none");
//		$("#imghead").attr("src", "./html/img/upl9090.gif");
//		$("#previewImg").attr("src", "./html/img/upl9090.gif");
//		$("#uploadFile").replaceWith($('#uploadFile').clone());
//		location.href="#uploadFile";
//		return false;
//	}else{
//		sizeFlag = true;
//		$("#sizeCheckDiv").css("display","none");
//		$("#uploadCheckDiv").css("display","none");
//		var picPath = file.value;
//		var type = picPath.substring(picPath.lastIndexOf(".")+1, picPath.length).toLowerCase();
//		$("#imghead").css("display", "inline");
//		if(type!="jpg" && type != "png" && type != "gif"){
//			$("#chkFile").text("請選擇圖片檔案格式為 jpg、png、gif 的檔案");
//			$("#previewImg").removeAttr("style");
//			$("#imghead").attr("src", "./html/img/upl9090.gif");
//			$("#previewImg").attr("src", "./html/img/upl9090.gif");
//			//$("#uploadFile").replaceWith($(uploadFile).clone());
//			//$("#imgFile").val("");
//			return false;
//		} 
//		
//		$("#chkFile").css("display","");
//		$("#chkFile").text("圖片上傳中");
//		$("#imgType").val(type);
//		$("#modifyForm").attr("target", "uploadIMG");
//		$("#modifyForm").attr("action", "fileUpload.html");
//		$("#modifyForm").submit();
//	}
}


function deleteImgDom(fileName,obj){
	var divObj = $($(obj).parent()).children()[0];
	var img = $(divObj).children()[0];
	img.src = './html/img/upl9090.gif';
	
	var ulObj = $(obj).parent();
	$(ulObj).find("li").each(function(index,value){
		if(index == 0){
			$(this).children().each(function(index,value){
				if(index==0){
					$(this).css('display','');
				}else{
					$(this).remove();
				}
			});
		}else{
			if(index == 1){
				var size = $(this).children()[1].innerHTML;
				size = size.replace(' x ','');
				defineSize[size] = defineSize[size]-1;
			}
			$(this).remove();
		}
	});
	$(obj).remove();
	
	var sizeIndex = 0;
	$.each(defineSize, function (key, data) {
		if(data == 0){
			sizeIndex =  sizeIndex + 1;
		}
		
		if(sizeIndex == 4){
			$("#errMsg").text('');
			$("#errMsg").css('color','red');
			$("#errMsg").css('display','none');
		}
		
	});
}

//上傳圖片預覽
function preView(obj){
	var img = $($($($(obj).parent()).parent()).children()[0]).children()[0];
	var src = $($($($(obj).parent()).parent()).children()[0]).children()[0].src;
    $("#preDiv").prepend('<img src="'+img.src+'" >');
    $.fancybox(
    		$('#preDiv').html(),
    		{
    			'autoDimensions'	: false,
    			'width'         	: $(img).context.naturalWidth,
    			'height'        	: $(img).context.naturalHeight,
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

//儲存廣告
function saveData() {
//	$("#modifyForm").attr('action','doAdAdAddVideo.html');
//	$("#modifyForm").submit();
	
	
	var videoPic = "";
	$(".picSeq").each(function(){
		if(this.value != ""){
			videoPic = videoPic+this.value+",";
		}
	});
	
	if(videoPic != ""){
		videoPic = videoPic.substring(0,videoPic.length - 1);
	}
	
	
	//廣告影片網址不可為空
	if($("#adVideoURL").val() == ""){
		$("#adVideoURLErrMsg").text('請輸入影片網址');
		console.log('請輸入影片網址');
		return false;
	}
	
	//廣告連結網址不可為空
	if($("#adLinkURL").val() == ""){
		$("#chkLinkURL").text('請輸入影片網址');
		console.log('請輸入影片網址');
		return false;
	}
	
	
	if($("#adVideoURLErrMsg").text() != "" || $("#chkLinkURL").text() != "網址確認正確"){
		
	}
	
	
	callBlock();
	$.ajax({
		url : "adAddVideoSaveAjax.html",
		type : "POST",
		dataType:'json',
		data : {
			//"videoPic":JSON.stringify('PIC1,PIC2'),
			"videoPicId":videoPic,
//			"seqArray" : map,
//			"adGroupSeq": $("#adGroupSeq").val(),
//			"adLinkURL" : $("#adLinkURL").val(),
//			"keywords" : JSON.stringify(keyWordArray),
//			"excludeKeywords" : JSON.stringify(excludeKeywordULArray),
//			"adKeywordOpen" : $("#adKeywordOpen").attr("checked"),
//			"adKeywordPhraseOpen" : $("#adKeywordPhraseOpen").attr("checked"),
//			"adKeywordPrecisionOpen" : $("#adKeywordPrecisionOpen").attr("checked")
		},
		success : function(respone) {
			console.log(respone);
			$('body').unblock();
			if(respone == "success"){
//				$(location).attr( 'href' , 'adAddFinish.html?adGroupSeq='+$("#adGroupSeq").val());	
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