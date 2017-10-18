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
	});
	
	//檢查網址blur事件
	$("#adLinkURL").blur(function() {
		if($("#adLinkURL").length > 0 && $("#adLinkURL").val() != ""){
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
		}
	});
	
	var videoUrl = null;
	//檢查廣告網址blur事件
	$("#adVideoURL").blur(function() {
		if($("#adVideoURL").val() == ""){
			$("#adVideoURLMsg").css('color','red');
			$("#adVideoURLMsg").text('請輸入影片網址');
			return false;
		}
		var regx = new RegExp(/^[hH][tT][tT][pP]([sS]?):\/\/(\S+\.)+\S{2,}$/);
		if($("#adVideoURL").val() == ""){
			$("#adVideoURLMsg").css('color','red');
			$("#adVideoURLMsg").text('請輸入廣告網址');
			return false;
		}else if(!regx.test($("#adVideoURL").val())){
			$("#adVideoURLMsg").css('color','red');
			$("#adVideoURLMsg").text('網址格式錯誤');
			return false;
		}
		
		//相同網址不再重複檢查
		if(videoUrl != $("#adVideoURL").val()){
			$("#adVideoURLMsg").text('');
			callBlockUpload("取得影片資訊中...");
			$.ajax({
				url: "chkVideoUrl.html",
				data:{
					adVideoUrl: $("#adVideoURL").val()
				},
				type:"POST",
				dataType:"JSON",
				success:function(result, status){
//					console.log(result);
				},
				error: function(xtl) {
					//alert("系統繁忙，請稍後再試！");
				}
			}).done(function (result) {
				if(result.result == true){
					videoUrl = $("#adVideoURL").val();
					$("#adVideoURLMsg").css('color','green');
					$("#adVideoURLMsg").text('影片網址確認正確');
					adPreviewVideoData = result;
					/**影片預設尺寸*/
					if(adPreviewVideoData != null){
						$("#preViewArea").empty();
						autoPreview(result);
						appendVideoPreview();
					}
					$('body').unblock();
				}else{
					adPreviewVideoData = null;
					videoUrl = null;
					$("#adVideoURLMsg").css('color','red');
					$("#adVideoURLMsg").text(result.msg);
					$("#preViewArea").empty();
					$('body').unblock();
				}
			});
		}
		
	});
	
	var fileFinishSize = 0;
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
	    	uploadFileSize = data.originalFiles.length;
	    	fileFinishSize = uploadFileSize;
	    	if(uploadFileSize > 10){
	    		alert("一次最多只能上傳10個檔案!!");
	    		return false;
	    	}
	    	callBlockUpload("圖片上傳中，請稍後");
	    }).on('fileuploaddone', function (e, data) {
	    	var index = parseInt($("#fileUploadIndex").text());
	    	index = index + 1;
	    	$("#fileUploadIndex").text(index);
	    	//呼叫建立畫面
	    	var result = createImgObjDom(data.files[0],jsonObj.imgWidth,jsonObj.imgHeight,jsonObj.fileSize,jsonObj.adSeq,jsonObj.imgMD5,jsonObj.imgRepeat,jsonObj.html5Repeat,jsonObj.imgSrc,jsonObj.errorMsg);
	    	
	    	if(result.result){
	    		fileArray.push(data.files[0]);
		    	seqArray.push(jsonObj.adSeq);
		    	
		    	//建立影音廣告尺寸選項
		    	var addOptionFlag = true 
		    	$("#adViseoSize").children().each(function(index,obj) {
		    		if(obj.value == (result.width+result.height)){
		    			addOptionFlag = false;
		    			return false;
		    		}
		    	});
		    	if(addOptionFlag){
		    		$("#adViseoSize").append('<option value="'+result.width+result.height+'">'+result.width+" x "+result.height+'</option>');
		    	}
	    	}
	    	
	    	fileFinishSize = fileFinishSize - 1;
	    	if(fileFinishSize == 0 && adPreviewVideoData != null){
		    	/**建立預覽圖*/
		    	appendVideoPreview();
	    	}
	    }).on('fileuploadprogressall', function (e, data) {	
	    }).on('fileuploadprocessalways', function (e, data) {
	    	//2015.7.12  tim   由於error後不會執行fileuploaddone,所以要加unblock()
	    	if(data.files.error){
	    		$('body').unblock();
	    	}
	    })
	});
	
	
	//點擊選取全部尺寸
	$("#adViseoSize").on('change', function() {
		var optionSize = this.value;
		if(optionSize == 0){
			$("#preViewArea input[type=checkbox]").each(function(index,checkboxObj){
				var div = $($($(checkboxObj).parent()).parent()).parent();
				$(div).css('display','');
			});
		}else{
			/*取消選擇全部*/
			$("#checkboxAll").attr('checked',false);
			/*隱藏非選擇的影音尺寸，所有勾選取消*/
			$("#preViewArea input[type=checkbox]").each(function(index,checkboxObj){
				$(checkboxObj).attr('checked',false);
				var size = checkboxObj.id.replace('checkbox_','');
				var div = $($($(checkboxObj).parent()).parent()).parent();
				if(size != optionSize){
					$(div).css('display','none');
				}else{
					$(div).css('display','');
				}
			});
		}
	});
	
	
	/*全部尺寸*/
	$("#checkboxAll").on('change', function() {
		if(this.checked){
			$("#preViewArea input[type=checkbox]").each(function(index,checkboxObj){
				$(checkboxObj).attr('checked',true);
			});
		}else{
			$("#preViewArea input[type=checkbox]").each(function(index,checkboxObj){
				$(checkboxObj).attr('checked',false);
			});
		}
	});
	
});
var adPreviewVideoData = null;

//檢查是否取消全部勾選
function checkVideo(obj){
	if(!obj.checked){
		var flag = true;
		$("#preViewArea input[type=checkbox]").each(function(index,checkboxObj){
			if(checkboxObj.checked == false){
				flag = false
				return false;
			}
		});
	}
	
	if(!flag){
		$("#checkboxAll").attr('checked',false);
	}
}

function callBlockUpload(msg){
	$("body").block({
		message: "<h3>"+msg+"</h3>",
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
	var result = null;
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
	
	$("#videoSize").children().each(function(index,obj) {
		if((width+height) == $(obj).text()){
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
			return false;
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
		result = true;

		if(width == 310 && height == 390){
			width = "950";
			height = "390";
		}else if(width == 579 && height == 250){
			width = "970";
			height = "250";
		}
		
		var anyWindow = window.URL || window.webkitURL;
		var objectUrl = anyWindow.createObjectURL(file);
		var readoName = width+height;
		
		var a =
			 '<li class="okbox" style="padding: 0 0 20px 0;width: 18.5%; "  id="'+adSeq+'">'+
			 '<div class="adboxdv" >'+
			 '<img src="'+objectUrl+'">'+
			 '<p class="fancy adinf" onclick="preViewImg(\''+file.name+'\',\''+width+'\',\''+height+'\');" alt="預覽">預覽</p></div>'+
			 '<ul style="margin-top: 5px;margin-left: -5px;">'+
			 '<li><input type="radio" style="visibility:visible;float: left;display: block;"  name="'+readoName+'" checked onclick="clickSizePic(\''+showFileName+'\',\''+width+'\',\''+height+'\')"><i>名稱</i><b>' + showFileName + '</b></li>' + 
			 '<li style="margin-left:30px;" class="'+imgSize+'"><i>尺寸</i><b>'+width+' x '+height+'</b></li>'+
			 '<li style="margin-left:30px;" class="'+imgFileSize+'"><i>大小</i><b>'+fileSize+'</b></li>'+
			 '<li style="margin-left:30px;" class="'+imgType+'"><i>格式</i><b>'+imgTypeName.toUpperCase()+'</b></li>'+
			 '</ul>'+
			 '<a class="addel" style="top:275px;" onclick="deleteImgDom(\''+adSeq+'\',\''+file.name+'\')">丟</a>'+ 
			 '<input type="hidden" id="' + adSeq + '_title" name="imgName" value="' + fileName + '" />' + 
			 '<input type="hidden" id="' + adSeq + '_imgMD5" name="imgMD5" value="' + imgMD5 + '" />' + 
			 '<input type="hidden" id="' + adSeq + '_format" value="' + imgTypeName + '" />' +
			 '</li>';
		$(".aduplodul_p").append(a);
		
		$("#AG").children().each(function(index,value){
			var checkFlag = false;
			$(value.getElementsByTagName("ul")).children().each(function(index,obj){
				if(index == 0 && readoName == $(obj).children()[0].name && $($(obj).children()[2]).text() != showFileName){
					checkFlag = true;
				}
				
				if(checkFlag && index == 1){
					$(this).attr('class','no');
					var data = $($(this).children()[1]).html("<br>");
					$(this).children()[1].append('尺寸重複，僅能選擇一款。');
					$(data).prepend(width+" x "+height);
				}
				
			})
		})
	}else if(imgSize == "no" || imgFileSize == "no" || imgType == "no" || imgRepeatFlag){
		result = false;
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
	
	var result = {result:result,width:width,height:height};
	return result;
}

function clickSizePic(showFileName,width,height){
	$("#AG").children().each(function(index,value){
		var flag = null;
		var ulObj = null;
		var radioName = null;
		$(value.getElementsByTagName("ul")).children().each(function(index,obj){
			radioName = $(obj).children()[0].name;
			var name = $($(obj).children()[2]).text();
			ulObj = $(obj).parent();
			if(showFileName == name && radioName == (width+height)){
				flag = true;
			}else if(radioName == (width+height)){
				flag = false;
			}
			return false;
		})
		
		if(flag == null){
			return true;
		}
		
		$(ulObj).children().each(function(index,obj){
			var width = radioName.substring(0,3);
			var height = radioName.substring(3,radioName.length);
			
			if(flag){
				if(index == 1){
					$(obj).attr('class','yes');
					var data = $(obj).innerHtml = '<i>尺寸</i><b>'+width+' x '+height+'</b>';
					$(obj).empty();
					$(obj).append(data);
				}
			}else{
				if(index == 1){
					$(obj).attr('class','no');
					var data = $(obj).innerHtml = '<i>尺寸</i><b>'+width+' x '+height+'<br>尺寸重複，僅能選擇一款。</b>';
					$(obj).empty();
					$(obj).append(data);
				}
			}
		})
	})
	
	/**變更影片底圖*/
	appendVideoPreview();
}

function saveAdAddVideo(result){
	console.log(result);
}

//清空上傳資料
function fileLoad(){
	$("#fileupload").click();
}

//重新決定redio圖片
function changeRedioPanel(obj){
	var deleteBannerFlag = true;
	var redioName = null;
	var text = null; 
	$(obj.getElementsByTagName("ul")).children().each(function(index,value){
		if(index == 0){
			redioName = $(value).children()[0].name;
		}
		if(index == 1){
			text = $($(value).children()[1]).text();
		}
		if($(value).attr('class') == 'no'){
			deleteBannerFlag = false;
			return false;
		}
	});
	
	//挑選一張圖片變更正確
	var changeFlag = false;
	if(deleteBannerFlag){
		$("#AG").children().each(function(index,value){
			$(value.getElementsByTagName("ul")).children().each(function(index,obj){
				if(index == 0 && redioName == $(obj).children()[0].name){
					changeFlag = true;
					$($(obj).children()[0]).attr("checked",true);
				}
				if(changeFlag){
					if(index == 1){
						$(obj).attr('class','yes');
						$($(obj).children()[1]).text(text);
					}
				}
			});
			if(changeFlag){
				return false;
			}
		});
	}
	/**變更影片底圖*/
	appendVideoPreview();
}

//刪除欲上傳檔案
function deleteImgDom(fileName,file){
	var deleteSize = null;
	$.each($(".aduplodul_p li"), function(index,obj) {
		if(fileName == obj.id){
			if($(this).attr('class') == 'okbox'){
				deleteSize = $($($(this).children()[1]).children()[0]).children()[0].name;
			}
			$(this).remove();
			changeRedioPanel(this);
			fileArray.forEach(function(fileData,index) {
				if(file == fileData.name){
					fileArray.splice(index, 1);
					return false;
				}
			})
			return false;
		}
	});
	
	$.each($(fileArray), function( index, file ) {
		if(fileName == file.name){
			fileArray.splice(index, 1);
			return false;
		}
	});
	
	$.each($(seqArray), function( index, adSeq ) {
		if( fileName == adSeq){
			seqArray.splice(index, 1);
			return false;
		}
	});

	//判斷是否刪除最後一張尺寸圖
	var previewobj = null;
	$("#preViewArea input[type=checkbox]").each(function(index,checkboxObj){
		var size = checkboxObj.id.replace('checkbox_','');
		if(size == deleteSize){
			previewobj = $($($(checkboxObj).parent()).parent().parent()[0]);
		}
	});
	
	var picIndexTotal = 0;
	$("#AG").children().each(function(index,value){
		var obj = value;
		$($(value).children("ul")).children().each(function(index,value){
			if($(obj).attr('class') == 'okbox' && index == 0 && $(value).children()[0].name == deleteSize){
				var text = $(value).children()[0].name;
				picIndexTotal = picIndexTotal + 1;
			}
		});
	});
	
	if(picIndexTotal == 0){
		$(previewobj).empty();
		$("#adViseoSize").children().each(function(index,obj) {
    		if(obj.value == deleteSize){
    			obj.remove(index);
    			return false;
    		}
    	});
	}
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

/*動態新增影片預覽*/
var iframeInfoMap = new Object();
function autoPreview(objData){
	iframeInfoMap["iframe"+0] = {width:300,height:250};
	iframeInfoMap["iframe"+1] = {width:336,height:280};
	iframeInfoMap["iframe"+2] = {width:640,height:390};
	
	var url = objData.previewUrl;
	$.each(iframeInfoMap, function(key, obj) {
		var a = 
			'<div class="v_box">'+
			   '<div class="">'+
			      '<span><input type="checkbox" name="checkbox" id="checkbox_'+obj.width+obj.height+'" checked onclick="checkVideo(this)"/>'+obj.width+'x'+obj.height+'</span>'+
			   '</div>'+
			   '<div  class="v_preview box_a_style">'+
			   '<iframe class="akb_iframe"  scrolling="no" frameborder="0" marginwidth="0" marginheight="0" vspace="0" hspace="0" id="pchome8044_ad_frame1" width="'+obj.width+'" height="'+obj.height+'" allowtransparency="true" allowfullscreen="true"' +
			   'src="adVideoPreview.html?adPreviewVideoURL='+encodeURIComponent(url)+'&adPreviewVideoBgImg='+""+'"></iframe>';
			   '</div>'+
			'</div>';
			$("#preViewArea").append(a);
	});
}



//影片存在建立預覽
function appendVideoPreview(){
	$("#AG input[type=radio]").each(function(index,radioObj){
		if(radioObj.checked){
			var url = adPreviewVideoData.previewUrl;
			var createPreViewVideoExist = false;
			var createPreViewCheckboxObj = null;
			var imgSrc = radioObj.parentElement.parentElement.parentElement.getElementsByTagName("img")[0].src;
			
			$("#preViewArea input[type=checkbox]").each(function(index,checkboxObj){
				var size = checkboxObj.id.replace("checkbox_","");
				if(size == radioObj.name){
					console.log(size);
					createPreViewCheckboxObj = checkboxObj;
					createPreViewVideoExist = true;
				}
			});
			
			
			console.log(imgSrc);
			console.log(createPreViewVideoExist);
			console.log($("#preViewArea").length);
			
			
			
			if(!createPreViewVideoExist){
				var width = radioObj.name.substring(0,3);
				var height = radioObj.name.substring(3,radioObj.name.length);
				var a = 
				'<div class="v_box">'+
				   '<div class="">'+
				      '<span><input type="checkbox" name="checkbox" id="checkbox_'+width+height+'" checked onclick="checkVideo(this)"/>'+width+'x'+height+'</span>'+
				   '</div>'+
				   '<div  class="v_preview box_a_style">'+
				   '<iframe class="akb_iframe"  scrolling="no" frameborder="0" marginwidth="0" marginheight="0" vspace="0" hspace="0" id="pchome8044_ad_frame1" width="'+width+'" height="'+height+'" allowtransparency="true" allowfullscreen="true"' +
				   'src="adVideoPreview.html?adPreviewVideoURL='+encodeURIComponent(url)+'&adPreviewVideoBgImg='+imgSrc+'"></iframe>';
				   '</div>'+
				'</div>';
				$("#preViewArea").append(a);
				
				console.log($("#preViewArea").length);
				
			}else{
				var head = createPreViewCheckboxObj.parentElement.parentElement.parentElement.getElementsByTagName("iframe")[0].contentDocument.getElementsByTagName("head")[0];
				var style = head.children[0];
				var data = style.innerHTML;				
				var imgStr = data.substring(data.indexOf("background-image:url("),data.length - 1);
				data = data.replace(imgStr,'background-image:url('+imgSrc+')');
				head.removeChild(style);
				var css = document.createElement("style");
				css.type = "text/css";
				css.innerHTML = data;
				head.insertBefore(css, head.childNodes[3]);
			}
		}
	});
}

//儲存廣告
function saveData() {
	
//	//廣告影片網址不可為空
	if($("#adVideoURL").val() == ""){
		$("#adVideoURLMsg").text('請輸入影片網址');
		var position = $('#adVideoURL').offset();  
		var x = position.left;  
		var y = position.top;  
		window.scrollTo(x,y);
		return false;
	}
	
	//廣告連結網址不可為空
	if($("#adLinkURL").val() == ""){
		$("#chkLinkURL").text('請輸入影片網址');
		var position = $('#adLinkURL').offset();  
		var x = position.left;  
		var y = position.top;  
		window.scrollTo(x,y);
		return false;
	}
	
	if($("#adVideoURLMsg").text() != "影片網址確認正確" || $("#chkLinkURL").text() != "網址確認正確"){
		if($("#adVideoURLMsg").text() != "影片網址確認正確"){
			var position = $('#adVideoURL').offset();  
			var x = position.left;  
			var y = position.top;  
			window.scrollTo(x,y);
			return false;
		}
		
		if($("#chkLinkURL").text() != "網址確認正確"){
			var position = $('#adLinkURL').offset();  
			var x = position.left;  
			var y = position.top;  
			window.scrollTo(x,y);
			return false;
		}
	}
	/*檢查勾選的尺寸*/
	var videoDetailMap = [];
	$('#AG li input[type=radio]').each(function(){
		var checked = $(this).attr('checked');
		if(checked == 'checked'){
			var sizeObj = $($($(this).parent()).parent()).children()[1];
			var adSeq = $($($($(this).parent()).parent()).parent()).attr("id");
			sizeObj = $($(sizeObj).children()[1]).text();
			sizeObj = sizeObj.replace(" x ","");
			$("#preViewArea input[type=checkbox]").each(function(index,checkboxObj){
				var size = checkboxObj.id.replace('checkbox_','');
				if(checkboxObj.checked && size == sizeObj){
					var map = new Object();
					map["size"] = sizeObj;
					map["adSeq"] = adSeq;
					map["format"] = $("#"+adSeq+"_format").val();
					videoDetailMap.push(map);
					return false;
				}else if(checkboxObj.checked){
					var map = new Object();
					map["size"] = size;
					map["adSeq"] = "";
					map["format"] = "";
					videoDetailMap.push(map);
				}
			});
		}
	});
	
	callBlock();
	
	console.log(videoDetailMap);
	console.log($("#adGroupSeq").val());
	console.log($("#adStyle").val());
	console.log($("#adClass").val());
	console.log($("#adVideoURL").val());
	console.log($("#adLinkURL").val());
	
	$.ajax({
		url : "adAddVideoSaveAjax.html",
		type : "POST",
		dataType:'json',
		data : {
			"videoDetailMap":JSON.stringify(videoDetailMap),
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
