

//1.建立上傳畫面
//2.建立點擊預覽
//3.送出

$(document).ready(function(){
	//存入上傳後的圖片陣列
	$(function () {
		uploadButton = $(".alex99");
		
	    $('#fileupload').fileupload({
	    	
	    	
	    	
	    	
	    	
	    	
//	        url: 'http://alex.pchome.com.tw:8089/akbpfb/testJQPlot2.html',
//	        dataType: 'json',
//	        autoUpload: false,
//	        acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
//	        maxFileSize: 5000000, // 5 MB
//	        previewMaxWidth: 210,
//	        previewMaxHeight: 180,
//	        previewCrop: true
	    }).on('fileuploadadd', function (e, data) {
	    	createImgDom(data);
	    });
//	    	alert("GGG5");
//	        data.context = $('<p/>').appendTo('#files');
//	    }).on('fileuploadprocessalways', function (e, data) {
//	    	
//	    	
//	    }).on('fileuploadprogressall', function (e, data) {
//
//	    }).on('fileuploaddone', function (e, data) {
//	        $.each(data.result.files, function (index, file) {
//
//	        });
//	    }).on('fileuploadfail', function (e, data) {
//	       
	});
	
	
	
});

//取得圖片尺寸
function createImgObjDom(file,width, height, imgsrc) {
	var imgFileSize = 'no';
	var imgSize = 'no';
	var imgSizeFlag = false;
	var imgTypeName = file.name.substr(file.name.indexOf(".")+1,file.name.length);
	var imgType ='no';
	var errorMsg ='';
	if(imgTypeName.toUpperCase() == "PNG" || imgTypeName.toUpperCase() == "JPG"){
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
	
	if(imgFileSize == "yes" && imgSize == "yes" && imgType == "yes"){
		var a =
			 '<li class="okbox" id="'+file.name+'">'+
			 '<div class="adboxdv">'+
			 '<img src="'+imgsrc+'">'+
			 '<p class="fancy adinf" onclick="preViewImg(\''+file.name+'\',\''+width+'\',\''+height+'\');" alt="預覽">預覽</p></div>'+
			 '<ul>'+
			 '<li class="'+imgSize+'"><i>尺寸</i><b>'+width+' x '+height+'</b></li>'+
			 '<li class="'+imgFileSize+'"><i>大小</i><b>'+Math.round(file.size/1024)+'</b></li>'+
			 '<li class="'+imgType+'"><i>格式</i><b>'+imgTypeName.toUpperCase()+'</b></li>'+
			 '</ul>'+
			 '<a class="addel" onclick="deleteImgDom(\''+file.name+'\')">丟</a>'+ 
			 '</li>';
		$(".aduplodul").append(a);
	}else{
		var a =
			'<li class="failbox" id="'+file.name+'">'+    
		    '<p class="addel"  onclick="deleteImgDom(\''+file.name+'\');"></p> '+
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
		$(".aduplodul").append(a);
	}
	
}

function createErrorObjDom(file) {
	var imgFileSize = 'no';
	var imgSize = 'no';
	var imgSizeFlag = false;
	var imgTypeName = file.name.substr(file.name.indexOf(".")+1,file.name.length);
	var imgType ='no';
	var errorMsg ='';
	
	var a =
		'<li class="failbox" id="'+file.name+'">'+    
	    '<p class="addel"  onclick="deleteImgDom(\''+file.name+'\');"></p> '+
	    '<em>上傳失敗!</em>'+
	    '<ul>'+
		'<li><i>檔名</i><b>'+file.name+'</b></li>'+
		'<li class="'+imgType+'"><i>格式</i><b>'+imgTypeName.toUpperCase()+'</b></li>'+
	    '</ul> '+
	    '<div class="adboxdv">'+
	    '<span><i>說明：</i>'+"格式不符"+'</span>'+
	    '<span class="adinf">系統無法上傳檔案!</span>  '+
	    '</div>'+
	    '</li>';
	$(".aduplodul").append(a);
}
//建立上傳後畫面
var fileArray =[];
function createImgDom(obj){
	 var files = document.getElementById("fileupload");
//	 if(!$.browser.msie){
		 $.each($(files).context.files, function(index, file) {
			 var imgTypeName = file.name.substr(file.name.indexOf(".")+1,file.name.length);
			 if(imgTypeName.toUpperCase() == "PNG" || imgTypeName.toUpperCase() == "JPG"){
				 fileArray.push(file);
				 var anyWindow = window.URL || window.webkitURL;
				 var objectUrl = anyWindow.createObjectURL(file);
				 var img = new Image();
				 img.onload = function () {
					 var width = this.width,
					 height = this.height,
					 imgsrc = this.src;
					 createImgObjDom(file,width,height,imgsrc); //call function
				 };
				 img.src = objectUrl;
			 }else{
				 createErrorObjDom(file)
			 }

		 });
		 
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
}



//點擊允許尺寸
function approveSize(){
	 $.fancybox(
	    		$('#approveSizeDiv').html(),
	    		{
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



//送出批次圖片上傳檔案
function multipartImgUuploadSubmit(){
	
	$.each($(fileArray), function( index, file ) {
	
	  console.log(fileArray);
	
	});
	
	
	
//	$.ajax({
//		url : "adAddImgAjax.html",
//		data : {
//			fileArray:fileArray.__proto__
//		},
//		type : "post",
//		success : function(respone) {
////			obj = JSON.parse(respone);
//			console.log(respone);
//			console.log(">>>>>>>success");
//		},
//		error : function() {
//			console.log(">>>>>>>error");
//		}
//	});
}
function uploadImg(){
	
//	console.log($("#fileupload").val(fileArray));
	
//    var names=$("#AidImg").val().split(".");  
//    if(names[1]!="gif"&&names[1]!="GIF"&&names[1]!="jpg"&&names[1]!="JPG"&&names[1]!="png"&&names[1]!="PNG")  
//    {  
//        $("#imgError").html("<div class='bzsj_left'></div>"+"海报必须为gif,jpg,png格式");  
//        $("#imgError").show();  
//        return;  
//    }  
    $("#formImg").submit();  
}  
