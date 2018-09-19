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
	
	  var logobnrtogglebtn = document.querySelectorAll(".logobnr-togglebtn");    
	    for (var m=0; m<logobnrtogglebtn.length; m++) {
	        logobnrtogglebtn[m].onclick=function(e){
	            this.parentElement.classList.toggle('open');
	        }
	    }    

	
	    window.onscroll = function() {fixAdpreview()};
		var header = document.getElementById("adpreviewbx");	
		var sticky = header.offsetTop;
		function fixAdpreview() {
		  if (window.pageYOffset > sticky) {
		    header.classList.add("sticky");	    
		  } else {
		    header.classList.remove("sticky");
		  }
		}

		$(document).ready(function() {
			$('.fancybox').fancybox();
		});
		//檢查字數
		checkAdInputTextLength('id','adName','id','checkHintAdName');
		checkAdInputTextLength('id','logoText','id','checkHintLogoText');
		
		//提示剩餘字數
		hintAdInputTextLength('id','adName','id','checkHintAdName');
		hintAdInputTextLength('id','logoText','id','checkHintLogoText');
		//檢查網址
		urlCheck('id','adurl','id','checkAdurl');
		
		
		$("#catalogSelect").change(function() {
			initCatalogSelect();
		});
	
		
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
		    	checkUploadRule(data);
		    	return false;
		    	
		    }).on('fileuploaddone', function (e, data) {
		    	console.log(data);
		    
		    }).on('fileuploadprogressall', function (e, data) {	
		    
		    }).on('fileuploadprocessalways', function (e, data) {
		    
		    })
		});
		
		
		//init html
		initFancyBoxHtml();
		$(".akb_iframe").attr('src' ,"adProdModel.html");
		
});

function initFancyBoxHtml(){
	var salesEndIframewp = new Object();
	salesEndIframewp["data"] = {
			index_1:"120_600",
			index_2:"140_300",
			index_3:"160_240",
			index_4:"160_600",
			index_5:"180_150",
			index_6:"250_80",
			index_7:"300_100",
			index_8:"300_250",
			index_9:"300_600",
			index_10:"320_480",
			index_11:"336_280",
			index_12:"640_390",
			index_13:"782_90",
			index_14:"950_390",
			index_15:"970_250"
	};
	
	var salesIframewp = new Object();
	salesIframewp["data"] = {
			index_1:"300_55"
	};
	
	var fancyboxSaleEndHtml = [];
	fancyboxSaleEndHtml.push('<div class="iframewp">');
	fancyboxSaleEndHtml.push('<div class="containr">');
	fancyboxSaleEndHtml.push('<h1>結尾行銷圖像   <span>商品輪播結尾的行銷圖像，圖片尺寸與廣告版型大小一致</span></h1>');
	fancyboxSaleEndHtml.push('<ul class="uploadlist">');
	for (var key in salesEndIframewp.data) {
//		console.log(salesEndIframewp.data[key]);
		var size = salesEndIframewp.data[key];
		var width = size.split('_')[0];
		var height = size.split('_')[1];
		fancyboxSaleEndHtml.push('<li>');
		fancyboxSaleEndHtml.push('	<div class=\"upload-lab\" style=\"display:block;\" onclick=\"fileLoad(this)\">');
		fancyboxSaleEndHtml.push('		<label for="" class="custom-file-upload">');
		fancyboxSaleEndHtml.push('			<i class="icon-uploadfile"></i>圖片尺寸：' + width + ' x '+ height);
		fancyboxSaleEndHtml.push('			<b class="btn-uploadimg">選擇檔案</b>');
		fancyboxSaleEndHtml.push('		</label>');
		fancyboxSaleEndHtml.push('	</div>');
		fancyboxSaleEndHtml.push('	<div class="uploadedbx" style="display: none">');
		fancyboxSaleEndHtml.push('			<div class="picbx">');
		fancyboxSaleEndHtml.push('				<div class="picbx-hover transition">');
		fancyboxSaleEndHtml.push('					<div class="delbx" onclick="deletePreview(this)"><i class="transition"></i>刪除</div>');
		fancyboxSaleEndHtml.push('					<div class="reuploadbx" onclick="fileLoad(this)"><i class="transition"></i>重新上傳</div>');
		fancyboxSaleEndHtml.push('				</div>');
		fancyboxSaleEndHtml.push('				<div class="pic">');
		fancyboxSaleEndHtml.push('					<img src="">');
		fancyboxSaleEndHtml.push('				</div>');
		fancyboxSaleEndHtml.push('			</div>');
		fancyboxSaleEndHtml.push('			<div class="picinfo">');
		fancyboxSaleEndHtml.push('				<p></p>');
		fancyboxSaleEndHtml.push('				<u></u>');
		fancyboxSaleEndHtml.push('			</div>');
		fancyboxSaleEndHtml.push('	</div>');
		fancyboxSaleEndHtml.push('<div class="uploadedbx error" style="display: none">');
		fancyboxSaleEndHtml.push('<div class="picbx">');
		fancyboxSaleEndHtml.push('<div class="picbx-hover transition">');
		fancyboxSaleEndHtml.push('<div class="delbx"><i class="transition"></i>刪除</div>');
		fancyboxSaleEndHtml.push('<div class="reuploadbx"><i class="transition"></i>重新上傳</div>');
		fancyboxSaleEndHtml.push('</div>');
		fancyboxSaleEndHtml.push('<div class="pic">');
		fancyboxSaleEndHtml.push('<div class="form-error">檔案格式錯誤請<label for="" class="reuploadlab" onclick="fileLoad(this)"> 重新上傳 </label>');
		fancyboxSaleEndHtml.push('</div>');
		fancyboxSaleEndHtml.push('</div>');
		fancyboxSaleEndHtml.push('</div>');
		fancyboxSaleEndHtml.push('<div class="picinfo">');
		fancyboxSaleEndHtml.push('<p></p>');
		fancyboxSaleEndHtml.push('<u></u>');
		fancyboxSaleEndHtml.push('</div>');
		fancyboxSaleEndHtml.push('</div>');
		fancyboxSaleEndHtml.push('<div style="display:none">' + size + '</div>');
		fancyboxSaleEndHtml.push('</li> ');
	}              
	fancyboxSaleEndHtml.push('</ul>');
	fancyboxSaleEndHtml.push('<!--uploadlist end-->');
	fancyboxSaleEndHtml.push('</div>');
	fancyboxSaleEndHtml.push('<div class="bottombar">');
	fancyboxSaleEndHtml.push('<input type="button" id="" value="取 消" class="cancel-btn" onclick="closeFancybox();">');
	fancyboxSaleEndHtml.push('<input type="button" id="" value="確認送出" class="save-btn" onclick="submitFancybox();">');
	fancyboxSaleEndHtml.push('</div>');
	fancyboxSaleEndHtml.push('</div>');
	$("#test").append(fancyboxSaleEndHtml.join(""));
	
	
	
	fancyboxSaleEndHtml = [];
	fancyboxSaleEndHtml.push('<div class="iframewp">');
	fancyboxSaleEndHtml.push('<div class="containr">');
	fancyboxSaleEndHtml.push('<h1>LOGO行銷圖像   <span>商品輪播的LOGO圖像</span></h1>');
	fancyboxSaleEndHtml.push('<ul class="uploadlist">');
	for (var key in salesIframewp.data) {
//		console.log(salesEndIframewp.data[key]);
		var size = salesIframewp.data[key];
		var width = size.split('_')[0];
		var height = size.split('_')[1];
		fancyboxSaleEndHtml.push('<li>');
		fancyboxSaleEndHtml.push('	<div class=\"upload-lab\" style=\"display:block;\" onclick=\"fileLoad(this)\">');
		fancyboxSaleEndHtml.push('		<label for="" class="custom-file-upload">');
		fancyboxSaleEndHtml.push('			<i class="icon-uploadfile"></i>圖片尺寸：' + width + ' x '+ height);
		fancyboxSaleEndHtml.push('			<b class="btn-uploadimg">選擇檔案</b>');
		fancyboxSaleEndHtml.push('		</label>');
		fancyboxSaleEndHtml.push('	</div>');
		fancyboxSaleEndHtml.push('	<div class="uploadedbx" style="display: none">');
		fancyboxSaleEndHtml.push('			<div class="picbx">');
		fancyboxSaleEndHtml.push('				<div class="picbx-hover transition">');
		fancyboxSaleEndHtml.push('					<div class="delbx" onclick="deletePreview(this)"><i class="transition"></i>刪除</div>');
		fancyboxSaleEndHtml.push('					<div class="reuploadbx" onclick="fileLoad(this)"><i class="transition"></i>重新上傳</div>');
		fancyboxSaleEndHtml.push('				</div>');
		fancyboxSaleEndHtml.push('				<div class="pic">');
		fancyboxSaleEndHtml.push('					<img src="">');
		fancyboxSaleEndHtml.push('				</div>');
		fancyboxSaleEndHtml.push('			</div>');
		fancyboxSaleEndHtml.push('			<div class="picinfo">');
		fancyboxSaleEndHtml.push('				<p></p>');
		fancyboxSaleEndHtml.push('				<u></u>');
		fancyboxSaleEndHtml.push('			</div>');
		fancyboxSaleEndHtml.push('	</div>');
		fancyboxSaleEndHtml.push('<div class="uploadedbx error" style="display: none">');
		fancyboxSaleEndHtml.push('<div class="picbx">');
		fancyboxSaleEndHtml.push('<div class="picbx-hover transition">');
		fancyboxSaleEndHtml.push('<div class="delbx"><i class="transition"></i>刪除</div>');
		fancyboxSaleEndHtml.push('<div class="reuploadbx"><i class="transition"></i>重新上傳</div>');
		fancyboxSaleEndHtml.push('</div>');
		fancyboxSaleEndHtml.push('<div class="pic">');
		fancyboxSaleEndHtml.push('<div class="form-error">檔案格式錯誤請<label for="" class="reuploadlab" onclick="fileLoad(this)"> 重新上傳 </label>');
		fancyboxSaleEndHtml.push('</div>');
		fancyboxSaleEndHtml.push('</div>');
		fancyboxSaleEndHtml.push('</div>');
		fancyboxSaleEndHtml.push('<div class="picinfo">');
		fancyboxSaleEndHtml.push('<p></p>');
		fancyboxSaleEndHtml.push('<u></u>');
		fancyboxSaleEndHtml.push('</div>');
		fancyboxSaleEndHtml.push('</div>');
		fancyboxSaleEndHtml.push('<div style="display:none">' + size + '</div>');
		fancyboxSaleEndHtml.push('</li> ');
	}              
	fancyboxSaleEndHtml.push('</ul>');
	fancyboxSaleEndHtml.push('<!--uploadlist end-->');
	fancyboxSaleEndHtml.push('</div>');
	fancyboxSaleEndHtml.push('<div class="bottombar">');
	fancyboxSaleEndHtml.push('<input type="button" id="" value="取 消" class="cancel-btn" onclick="closeFancybox();">');
	fancyboxSaleEndHtml.push('<input type="button" id="" value="確認送出" class="save-btn" onclick="submitFancybox();">');
	fancyboxSaleEndHtml.push('</div>');
	fancyboxSaleEndHtml.push('</div>');
	$("#test2").append(fancyboxSaleEndHtml.join(""));
}


function changeBackgroundColor(obj){
	$(".ad-sample").css("background","#"+obj.value);
}

window.onload = function(){
	initCatalogSelect();
	$("#saleEndImgUploadBtn").attr("disabled", false);
}


function initCatalogSelect(){
	var catalogSelectValue = $("#catalogSelect").val();
	$("#groupSelect option").each(function(){
		if(this.value !=""){
			var groupInfo = this.value.split("_");
			if(groupInfo[0] != catalogSelectValue){
				$(this).css("display","none");
			}else{
				$(this).css("display","");
			}
		}
	});
}

//點擊上傳
function openFancyfileLoad(type){
	initFancyBoxType = null;
//	console.log(type);
	var fancyboxDom = null;
	if(type =='endSales'){
		initFancyBoxType = 'endSales';
		fancyboxDom = $('#test');
	}
	if(type =='logo'){
		initFancyBoxType = 'logo';
		fancyboxDom = $('#test2');
	}
	
    $.fancybox(
    	fancyboxDom.html(),
		{
			'autoDimensions'	: false,
			'width'         	: "auto",
			'height'        	: "auto",
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
			    createSuccessUploadToDom();
			}  
		}
    );
    alex();
}
function closePrew(){
	console.log("closePrew");
	//$('#test').empty();
}


//開啟fancybox上傳畫面初始化
var upload_size = null;
var uploadDom = null;
var uploadLog = new Object();
var uploadLogoLog = new Object();
var initFancyBoxType = null;
function alex(){
//	console.log("alex");
//	console.log(initFancyBoxType);
	upload_size = null;
	uploadDom = null;
	var uploadlist = null;
	var uplogData = null;
	
	
	uploadlist = $(".uploadlist")[2];
	if(initFancyBoxType == 'endSales'){
		uplogData = uploadLog;
	}
	if(initFancyBoxType == 'logo'){
		uplogData = uploadLogoLog;
	}
	
//	$(uploadlist).find("li").each(function(){
//		console.log(this);
//	});
	
	Object.keys(uplogData).forEach(function(key) {
//		console.log(key);
		var index = uplogData[key].index;
		var uploadDom = $(uploadlist).children()[index];
		var fileName = uplogData[key].fileName;
		var width = uplogData[key].width;
		var height = uplogData[key].height;
		var fileExtensionName = uplogData[key].fileExtensionName;
		var previewSrc = uplogData[key].previewSrc;
		
		var noUpload = $(uploadDom).find(".upload-lab");
        var hasUpload = $(uploadDom).find(".uploadedbx");
        var errorUpload = $(uploadDom).find(".uploadedbx.error");
        var picinfo = $(hasUpload).find(".picinfo");
        var p = $(picinfo).find("p");
        var u = $(picinfo).find("u");
        var imgDom = $(hasUpload).find("img");
        $(p).append(fileName);
        $(u).append(width +" x "+ height+" ‧ "+fileExtensionName);
        $(imgDom).attr("src",previewSrc);
       
        $(hasUpload).css("display","");
        $(noUpload).css("display","none");
        $(errorUpload).css("display","none");
//        console.log(previewSrc);
	});
}


//上傳資料
function fileLoad(obj){
	console.log("fileLoad");
	var sourceClassName = $(obj.parentElement).attr("class");
	if(sourceClassName == 'form-error'){
		uploadDom = obj.parentElement.parentElement.parentElement.parentElement.parentElement;
		upload_size = $(uploadDom).children().last()[0].innerHTML;
	}else if(sourceClassName == 'picbx-hover transition'){
		uploadDom = obj.parentElement.parentElement.parentElement.parentElement;
		upload_size = $(uploadDom).children().last()[0].innerHTML;
	}else{
		uploadDom = obj.parentElement;
		upload_size = $(uploadDom).children().last()[0].innerHTML;
	}
	$("#fileupload").click();
}

//檢查上傳圖片合法性
function checkUploadRule(file_data){
	console.log("checkUploadRule");
	readFile(file_data.files[0], function(e) {
		var index = $(uploadDom).index();
        var image = new Image();
		image.src = e.target.result;
		image.onload = function () {
			var sizeFlag = false;
			var fileSizeFlag = false;
			var fileExtensionNameFlag = false;
			
			var height = this.height;
            var width = this.width;
            var validityWidth = upload_size.split("_")[0];
            var validityHeight = upload_size.split("_")[1];
            var fileName = file_data.files[0].name;
            fileName = fileName.substring(0,fileName.indexOf("."));
            var fileExtensionName = file_data.files[0].type;
            fileExtensionName = fileExtensionName.substring(fileExtensionName.indexOf("/") + 1 ,fileExtensionName.length).toUpperCase();
            var fileSize = file_data.files[0].size;
            
            if(width == validityWidth && height == validityHeight ){
            	sizeFlag = true;
            }
            if(fileExtensionName.includes("JPEG") || fileExtensionName.includes("PNG") || fileExtensionName.includes("GIF")){
            	fileExtensionNameFlag = true
            }
            
            if(sizeFlag && fileExtensionNameFlag){
            	console.log(initFancyBoxType + " SUCCESS UPLOAD");
            	
            	var previewSrc = e.target.result;
                var noUpload = $(uploadDom).find(".upload-lab");
                var hasUpload = $(uploadDom).find(".uploadedbx");
                var errorUpload = $(uploadDom).find(".uploadedbx.error");
                 
                var picinfo = $(hasUpload).find(".picinfo");
                var p = $(picinfo).find("p");
                var u = $(picinfo).find("u");
                var imgDom = $(hasUpload).find("img");
                 
                $(p).empty();
                $(u).empty();
                $(u).css("color","#808080");
                $(p).append(fileName);
                $(u).append(width +" x "+ height+" ‧ "+fileExtensionName);
                $(imgDom).attr("src",previewSrc);
                 
                //以下順序不可變動
                $(hasUpload).css("display","block");
                $(errorUpload).css("display","none");
                $(noUpload).css("display","none");
                if(initFancyBoxType == 'endSales'){
                	uploadLog["uploadLiDom_"+index] = {index:index,fileName:fileName,width:width,height:height,previewSrc:previewSrc,fileExtensionName:fileExtensionName,fileSize:fileSize};	
                }
                if(initFancyBoxType == 'logo'){
                	uploadLogoLog["uploadLiDom_"+index] = {index:index,fileName:fileName,width:width,height:height,previewSrc:previewSrc,fileExtensionName:fileExtensionName,fileSize:fileSize};
                }
            }else{
            	console.log("FAIL UPLOAD");
            	
            	var noUpload = $(uploadDom).find(".upload-lab");
            	var hasUpload = $(uploadDom).find(".uploadedbx");
            	var picinfo = $(hasUpload).find(".picinfo");
            	var p = $(picinfo).find("p");
            	var u = $(picinfo).find("u");
            	var imgDom = $(hasUpload).find("img");
            	$(p).empty();
            	$(u).empty();
            	
            	if(sizeFlag == false){
            		$(p).append(fileName);
            		$(u).css("color","red");
            		$(u).append("請上傳"+validityWidth+" x "+ validityHeight+"圖片尺寸");
            	}
            	$(imgDom).attr("src","");
            	$(noUpload).css("display","none");
            	$(hasUpload).css("display","none");
            	var errorUpload = $(uploadDom).find(".uploadedbx.error");
            	$(errorUpload).css("display","");
            	if(initFancyBoxType == 'endSales'){
            		delete uploadLog['uploadLiDom_'+index];
            	}
            	if(initFancyBoxType == 'logo'){
            		delete uploadLogoLog['uploadLiDom_'+index];
            	}
            }
		}
	});
}

function readFile(file, onLoadCallback){
    var reader = new FileReader();
    reader.onload = onLoadCallback;
    reader.readAsDataURL(file);
}

//刪除上傳檔案
function deletePreview(obj){
	console.log("刪除上傳檔案");
	var uploadDom = obj.parentElement.parentElement.parentElement.parentElement;
	var index = $(uploadDom).index();
	var noUpload = $(uploadDom).find(".upload-lab");
    var hasUpload = $(uploadDom).find(".uploadedbx");
    var picinfo = $(hasUpload).find(".picinfo");
    var p = $(picinfo).find("p");
    var u = $(picinfo).find("u");
    var imgDom = $(hasUpload).find("img");
    var errorUpload = $(uploadDom).find(".uploadedbx.error");
    
    $(p).empty();
    $(u).empty();
    $(imgDom).attr("src","");
    $(noUpload).css("display","");
    $(hasUpload).css("display","none");
    $(errorUpload).css("display","none");
    if(initFancyBoxType == 'endSales'){
    	delete uploadLog['uploadLiDom_'+index]; 
	}
	if(initFancyBoxType == 'logo'){
		delete uploadLogoLog['uploadLiDom_'+index];
	}
}


function closeFancybox(){
	var alt = "提醒您，上傳檔案將被清空是否仍要關閉";
	if(confirm(alt)) {
		Object.keys(uploadLog).forEach(function(key) {
			if(initFancyBoxType == 'endSales'){
		    	delete uploadLog[key];
			}
			if(initFancyBoxType == 'logo'){
				delete uploadLogoLog[key];
			}
		})
		$.fancybox.close();
	}
}

function submitFancybox(){
	console.log("submitFancybox");
	$.fancybox.close();
}

//上傳合法的檔案建立到頁面
function createSuccessUploadToDom(){
	var a = null;
	var uploadData = null;
	if(initFancyBoxType == 'endSales'){
    	a = $(".adbannerpicbx")[1];
    	uploadData = uploadLog;
	}
	if(initFancyBoxType == 'logo'){
		a = $(".adbannerpicbx")[0];
		uploadData = uploadLogoLog;
	}
	
	$(a).empty();
	Object.keys(uploadData).forEach(function(key) {
		var fileName = uploadData[key].fileName;
		var width = uploadData[key].width;
		var height = uploadData[key].height;
		var fileExtensionName = uploadData[key].fileExtensionName;
		var previewSrc = uploadData[key].previewSrc;
		var index = uploadData[key].index;
		var li = 
			'<li class="transition">'+
			'<div class="picuploadtb" onclick="clickDeleteUpload(this,\''+key+'\');">'+
				'<div class="del transition"></div>'+
				'<div class="picuploadcell">'+
					'<img src="'+previewSrc+'">'+
				'</div>'+
			'</div>'+
			'<p class="adbnrinfo">檔名：'+
				fileName+
				'<span>尺寸：'+width+' x '+height+' ‧ '+fileExtensionName+'</span>'+
			'</p>'+
			'</li>';
		$(a).append(li);
	});
}

function clickDeleteUpload(obj,deleteKey){
	var imgAreaId = obj.parentElement.parentElement.parentElement.id;
	if(imgAreaId == 'salesEndImgArea'){
		delete uploadLog[deleteKey];
	}else if(imgAreaId == 'logoImgArea'){
		delete uploadLogoLog[deleteKey];
	}
	$(obj.parentElement).remove();
}


function checkSubmit(){
//	var adName = $("#adName").val();
//	if(adName.length == 0){
//		$("#adName")[0].scrollIntoView();
//		return {msg:"廣告名稱不可為空",flag:false};
//	}
//	var catalogId = $("#catalogSelect").val();
//	if(catalogId == null){
//		$("#catalogSelect")[0].scrollIntoView();
//		return {msg:"請先建立商品目錄",flag:false};
//	}
//	var catalogGroupId = $("#groupSelect").val();
//	if(catalogGroupId == ""){
//		$("#groupSelect")[0].scrollIntoView();
//		return {msg:"請先建立商品群組",flag:false};
//	}
//	var checkAdurl = $("#checkAdurl").text();
//	if(checkAdurl != "網址確認正確"){
//		$("#adurl")[0].scrollIntoView();
//		return {msg:"請輸入正確網址",flag:false};
//	}
//	var logoText = $("#logoText").val();
//	if(logoText.length == 0){
//		$("#logoText")[0].scrollIntoView();
//		return {msg:"請輸入LOGO標題文字",flag:false};
//	}
	return {msg:"",flag:true};
}

//點擊送出審核
function adSubmit(){
	var submitFlag = true;
	if(checkSubmit().flag == false){
		submitFlag = checkSubmit().flag;
		alert(checkSubmit().msg);
	}
	
	
	
	
	if(submitFlag){
		console.log("驗證OK");
		var adName = $("#adName").val();
		var catalogId = $("#catalogSelect").val();
		var catalogGroupId = $("#groupSelect").val();
		var adurl = $("#adurl").val();
		var logoBgColor = $("#logoBgColor").val();
		var logoType = $('input[name=options]:checked').val();
		var logoText = $("#logoText").val();
		var logoFontColor = $("#logoFontColor").val();
		
		var btnTxt = $("#btnTxt").val();
		var btnBgColor = $("#btnBgColor").val();
		var btnFontColor = $("#btnFontColor").val();
		
		
		var disTxtType = $("#disTxtType").val();
		var disFontColor = $("#disFontColor").val();
		var disBgColor =  $("#disBgColor").val();
		
		
		console.log("adName:"+adName);
		console.log("catalogId:"+catalogId);
		console.log("catalogGroupId:"+catalogGroupId);
		console.log("adurl:"+adurl);
		console.log("logoBgColor:"+logoBgColor);
		console.log("logoType:"+logoType);
		console.log("logoText:"+logoText);
		console.log("logoFontColor:"+logoFontColor);
		console.log("btnTxt:"+btnTxt);
		console.log("btnBgColor:"+btnBgColor);
		console.log("btnFontColor:"+btnFontColor);
		
		
//    	var logoImg = "";
//    	Object.keys(uploadLogoLog).forEach(function(key) {
//    		logoImg = uploadLogoLog[key].previewSrc;
//    	})
		
		
    	
    	
    	
//    			$.ajax({
//					url : "adProdModel.html",
//					type : "POST",
//					dataType:'text',
//					data : {
//						"logoType":"type1",
//						"logoImg":"data:image/jpeg;base64,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",
//					},
//					success : function(respone) {
//						console.log(respone);
//						
//						$(".akb_iframe").append(respone);
//						
////						if(respone == "success"){
//							$(".adcontent").append(respone);
////						} else {
//////							alert(respone);
////						}
//					},
//					error: function(xtl) {
//						alert("系統繁忙，請稍後再試！");
//					}
//				});
//    	console.log($.md5(logoImg));
		
    	
//		var uploadLog = new Object();
//		var uploadLogoLog = new Object();
		
		
		//console.log(JSON.stringify(uploadLog));
		
//		var alt = "提醒您，您的廣告將在3工作天(周一到周五)審核完成(不含例假日)，並於廣告審核完成後開始播放";
//		if(confirm(alt)) {
//			$.blockUI({
//			    message: "<h1>製作新廣告中，請稍後...</h1>",
//			    css: {
//		            width: '500px',
//		            height: '65px',
//		            opacity: .9,
//		            border:         '3px solid #aaa',
//		            backgroundColor:'#fff',
//		            textAlign:      'center',
//		            cursor:         'wait',
//		            '-webkit-border-radius': '10px',
//		            '-moz-border-radius':    '10px'
//		        },
//		        fadeIn: 1000, 
//		        timeout:   100, 
//		        onBlock: function() {
//		        	console.log(uploadLogoLog);
//		        	var logoImg = "";
//		        	Object.keys(uploadLogoLog).forEach(function(key) {
//		        		logoImg = uploadLogoLog[key].previewSrc;
//		        	})
//		        	
//		        	
//		        	
//		        	$(".akb_iframe").attr('src' , "adProdModel.html?logoType=type1");
//		        	
////		    		$.ajax({
////					url : "adAddProdSaveAjax.html",
////					type : "POST",
////					dataType:'json',
////					data : {
////						"uploadLog":JSON.stringify(uploadLog),
////						"uploadLogoLog":JSON.stringify(uploadLogoLog),
////						"adGroupSeq":$("#adGroupSeq").val(),
////						"adName" : $("#adName").val(),
////						"catalogId":$("#catalogSelect").val(),
////						"catalogGroupId" : $("#groupSelect").val().split("_")[1],
////		    			"adLinkURL" : $("#adurl").val(),
////		    			"logoType" : $('input[name=options]:checked').val(),
////		    			"logoText" : $("#logoText").val(),
////		    			"logoBgColor":$("#logoBgColor").val(),
////		    			"logoFontColor":$("#logoFontColor").val(),
////		    			"btnTxt":$("#btnTxt").val(),
////		    			"btnFontColor":$("#btnFontColor").val(),
////		    			"btnBgColor":$("#btnBgColor").val(),
////		    			"disFontColor":$("#disFontColor").val(),
////		    			"disBgColor":$("#disBgColor").val(),
////		    			"disTxtType":$("#disTxtType").val(),
////					},
////					success : function(respone) {
//////						console.log(respone);
////						if(respone == "success"){
//////							$(location).attr('href','adAddVideoFinish.html?adGroupSeq='+$("#adGroupSeq").val());	
////						} else {
////							alert(respone);
////						}
////					},
////					error: function(xtl) {
////						alert("系統繁忙，請稍後再試！");
////					}
////				});
//		        }
//			});
//		}
	}
	
}

function getProdGroup(obj){
	
	var catalogGroupId = $("#groupSelect").val().split("_")[1];
	console.log(catalogGroupId);
	
	
	
	
	
	
	
//	 var xhr = new XMLHttpRequest();
//     xhr.open("GET", "http://showstg.pchome.com.tw/pfp/prodGroupListAPI.html?groupId=PCG20180906000000001&prodNum=8", true);
//     xhr.withCredentials = true;
//     xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
//     xhr.onreadystatechange = function() {
//         if (xhr.readyState === 4 && xhr.status === 200) {
//             console.log("response:" + xhr.response)
//         }
//     };
//     xhr.send();
	
	$.ajax({
		url : "prodGroupListAPI.html?groupId=PCG20180906000000001&prodNum=8",
		type : "GET",
		dataType:'  ',
		data : {
//			"groupId":catalogGroupId,
//			"prodNum":10,
		},
		success : function(respone) {
			console.log(respone);
		},
		error: function(xtl) {
			console.log(xtl);//alert("系統繁忙，請稍後再試！");
		}
	}).done(function() {
        console.log('Done!');
    });
}