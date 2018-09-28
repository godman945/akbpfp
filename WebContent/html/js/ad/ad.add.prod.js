$(document).ready(function(){
	
	$(".akb_iframe").attr("src","");
	$("#groupSelect").children()[0].selected = 'selected';
	
	
	
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
		checkAdInputTextLength('id','adName','id','checkHintAdName',false,'');
		checkAdInputTextLength('id','logoText','id','checkHintLogoText',true,'廣告標題太短，將影響您的廣告效果。\n\n廣告標題應重點說明您推廣的產品、活動、服務，依您的目標客群，撰寫他們有興趣的廣告標題。');
		
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
		
		
		$(".akb_iframe")[0].addEventListener("load", function() {
			adPreview();
		});
		
		
		$('input[type=radio][name=options]').change(function() {
			adPreview();
		});
		
		 $("input").keyup(function(event){
			 adPreview();
		 });
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
	
	//變更預覽
	changeActive(obj);
	
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
	
	
	//重設定預覽內容
	adPreview();
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
	//變更iframe內容
	getProdGroup(null);
}

function clickDeleteUpload(obj,deleteKey){
	var imgAreaId = obj.parentElement.parentElement.parentElement.id;
	if(imgAreaId == 'salesEndImgArea'){
		delete uploadLog[deleteKey];
	}else if(imgAreaId == 'logoImgArea'){
		delete uploadLogoLog[deleteKey];
	}
	$(obj.parentElement).remove();
	
	
	//變更iframe內容
	getProdGroup(null);
}


function checkSubmit(){
	var adName = $("#adName").val();
	if(adName.length == 0){
		$("#adName")[0].scrollIntoView();
		return {msg:"廣告名稱不可為空",flag:false};
	}
	var catalogId = $("#catalogSelect").val();
	if(catalogId == null){
		$("#catalogSelect")[0].scrollIntoView();
		return {msg:"請先建立商品目錄",flag:false};
	}
	var catalogGroupId = $("#groupSelect").val();
	if(catalogGroupId == ""){
		$("#groupSelect")[0].scrollIntoView();
		return {msg:"請先建立商品群組",flag:false};
	}
	var checkAdurl = $("#checkAdurl").text();
	if(checkAdurl != "網址確認正確"){
		$("#adurl")[0].scrollIntoView();
		return {msg:"請輸入正確網址",flag:false};
	}
	var logoText = $("#logoText").val();
	if(logoText.length == 0 && $('input[name=options]:checked').val() == "type3"){
		$("#logoText")[0].scrollIntoView();
		return {msg:"請輸入LOGO標題文字",flag:false};
	}
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
		if(Object.keys(uploadLogoLog).length > 0){
			logoType = "type3";
		}else{
			logoType == "type3" ? "type2" : "type1";
		}
			
			
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
		
		var alt = "提醒您，您的廣告將在3工作天(周一到周五)審核完成(不含例假日)，並於廣告審核完成後開始播放";
		if(confirm(alt)) {
			$.blockUI({
			    message: "<h1>製作新廣告中，請稍後...</h1>",
			    css: {
		            width: '500px',
		            height: '65px',
		            opacity: .9,
		            border:         '3px solid #aaa',
		            backgroundColor:'#fff',
		            textAlign:      'center',
		            cursor:         'wait',
		            '-webkit-border-radius': '10px',
		            '-moz-border-radius':    '10px'
		        },
		        fadeIn: 1000, 
		        timeout:   100, 
		        onBlock: function() {
		        	console.log(uploadLogoLog);
		        	var logoImg = "";
		        	Object.keys(uploadLogoLog).forEach(function(key) {
		        		logoImg = uploadLogoLog[key].previewSrc;
		        	})
		        	
			    		$.ajax({
						url : "adAddProdSaveAjax.html",
						type : "POST",
						dataType:'json',
						data : {
							"uploadLog":JSON.stringify(uploadLog),
							"uploadLogoLog":JSON.stringify(uploadLogoLog),
							"adGroupSeq":$("#adGroupSeq").val(),
							"adName" : $("#adName").val(),
							"catalogId":$("#catalogSelect").val(),
							"catalogGroupId" : $("#groupSelect").val().split("_")[1],
			    			"adLinkURL" : $("#adurl").val(),
			    			"logoType" : logoType,
			    			"logoText" : $("#logoText").val(),
			    			"logoBgColor":"#"+$("#logoBgColor").val(),
			    			"logoFontColor":"#"+$("#logoFontColor").val(),
			    			"btnTxt":$("#btnTxt").val(),
			    			"btnFontColor":"#"+$("#btnFontColor").val(),
			    			"btnBgColor":"#"+$("#btnBgColor").val(),
			    			"disFontColor":"#"+$("#disFontColor").val(),
			    			"disBgColor":"#"+$("#disBgColor").val(),
			    			"disTxtType":$("#disTxtType").val(),
			    			"prodLogoType":$('input[name=options]:checked').val()
						},
						success : function(respone) {
							console.log(respone);
							if(respone == "success"){
								$(location).attr('href','adAddFinish.html?adGroupSeq='+$("#adGroupSeq").val());	
							} else {
								alert(respone);
							}
						},
						error: function(xtl) {
							alert("系統繁忙，請稍後再試！");
						}
					});
		        }
			});
		}
	}
}

function getProdGroup(obj){
	var catalogGroupId = $("#groupSelect").val();
	if(catalogGroupId == ""){
		return false;
	}
	
	var selectSizeWidth = $("#adSize option:selected").text().split(" x ")[0];
	var selectSizeHeight = $("#adSize option:selected").text().split(" x ")[1];
	var catalogGroupId = $("#groupSelect").val().split("_")[1];
	var adbgType = "noposter";
	console.log(catalogGroupId);
	
	Object.keys(uploadLog).forEach(function(key) {
		var height = String(uploadLog[key].height);
		var width = String(uploadLog[key].width);
		if(width == selectSizeWidth && height == selectSizeHeight){
			adbgType = "hasposter";
		}
	});
	
	$(".akb_iframe").width(selectSizeWidth).height(selectSizeHeight);
	var totalWidth = $(".adcontent").width();
	var adWidth = $("#adSize option:selected").text().split(" x ")[0];
	var left = (totalWidth - adWidth) / 2;
	$(".akb_iframe").css("margin-left",left+"px");
	var src = 'adProdModel.html'
		+'?catalogGroupId='+catalogGroupId
		+'&btnTxt='+encodeURIComponent($("#btnTxt").val())
		+'&btnFontColor='+encodeURIComponent(btnFontColor)
		+'&btnBgColor='+encodeURIComponent(btnBgColor)
		+'&disTxtType='+encodeURIComponent($("#disTxtType").val())
		+'&disBgColor='+encodeURIComponent(disBgColor)
		+'&disFontColor='+encodeURIComponent(disFontColor)
		+"&adbgType="+adbgType
		+"&logoType="+adbgType
		+"&logoText="+encodeURIComponent($("#logoText").val())
		+"&logoBgColor="+encodeURIComponent(logoBgColor)
		+"&logoFontColor="+encodeURIComponent(logoFontColor)
		+"&prodLogoType="+encodeURIComponent($('input[name=options]:checked').val())
	console.log(src);
	$(".akb_iframe").attr('src' ,src);
	
	
	
	
	
	
	
	
	
}

function adPreview(){
	if($(".akb_iframe")[0].contentDocument.body.children[0] == undefined){
		return false;
	}
	console.log('====adPreview start====');
	var css = $(".akb_iframe")[0].contentDocument.head.getElementsByTagName("style")[2].innerHTML;
	css = css.replace("<#dad_logo_font_color>","#"+$("#logoFontColor").val());
	css = css.replace("<#dad_logo_bg_color>","#"+$("#logoBgColor").val());
	css = css.replace("<#dad_buybtn_font_color>","#"+$("#btnFontColor").val());
	css = css.replace("<#dad_buybtn_bg_color>","#"+$("#btnBgColor").val());
	css = css.replace("<#dad_dis_font_color>","#"+$("#disFontColor").val());
	css = css.replace("<#dad_dis_bg_color>","#"+$("#disBgColor").val());
	$(".akb_iframe")[0].contentDocument.head.getElementsByTagName("style")[2].innerHTML = css;
	var body = $(".akb_iframe")[0].contentDocument.body;
	var htmlRadioType = $('input[name=options]:checked').val();
	var typeObj = $(body)[0].getElementsByClassName("ads-container")[0].getElementsByTagName('a')[0].children[0];
	var typeObjClassName = htmlRadioType +" logo-box pos-absolute pos-top pos-left";
	$(typeObj).attr("class",typeObjClassName);
	var logoTitleObj = typeObj.getElementsByClassName("logo-txt-title");
	$(logoTitleObj).text($("#logoText").val());
	
	var prodArea = $(body)[0].getElementsByClassName("slid-items")[0].children;
	$(prodArea).each(function(index) {
		//變更按鈕文字
		var disTextObj = this.getElementsByClassName("pos-absolute")[0];
	});
	
	var bgA = $(body)[0].getElementsByClassName("adbg-container")[0].getElementsByTagName("a")[0];
	var bgDiv = $(body)[0].getElementsByClassName("adbg-container")[0].getElementsByTagName("div")[0];
	var adurl = $("#adurl").val();
	if(adurl.length > 0 && adurl.indexOf("http") < 0){
		adurl = "//"+adurl;
		$(bgA).attr("href",adurl);
	}
	
	//動態結尾蓋圖
	var selectSizeWidth = $("#adSize option:selected").text().split(" x ")[0];
	var selectSizeHeight = $("#adSize option:selected").text().split(" x ")[1];
	
	Object.keys(uploadLog).forEach(function(key) {
		var height = String(uploadLog[key].height);
		var width = String(uploadLog[key].width);
		if(width == selectSizeWidth && height == selectSizeHeight){
			var previewSrc = String(uploadLog[key].previewSrc);
			var bgStyle = $(bgDiv).attr("style","background-image:url("+previewSrc+")");
		}
	});
	
	//商品行銷圖
	var logoBgImgObj = $(body)[0].getElementsByClassName("logo-img-background")[0];
	Object.keys(uploadLogoLog).forEach(function(key) {
		var height = String(uploadLogoLog[key].height);
		var width = String(uploadLogoLog[key].width);
		if(width == 300 && selectSizeWidth  == 300  && height == 55 && selectSizeHeight == 250){
			var previewSrc = String(uploadLogoLog[key].previewSrc);
			$(logoBgImgObj).attr("src",previewSrc);
		}
	});
}

function changeActive(obj){
	if($(".akb_iframe")[0].contentDocument.body.children[0] == undefined){
		return false;
	}
	
	var  disTxtType = $("#disTxtType").val();
	
	var css = $(".akb_iframe")[0].contentDocument.head.getElementsByTagName("style")[2].innerHTML.trim();
	css.trim().split('\n').forEach(function(v, i) {
		if(v.indexOf(".logo-box") >=0 ){
			css = css.replace(v,".logo-box{color:"+"#"+$("#logoFontColor").val()+"!important;background-color:"+"#"+$("#logoBgColor").val()+"}");
		}
		if(v.indexOf(".buybtn{color:") >=0 ){
			css = css.replace(v,".buybtn{color:"+"#"+$("#btnFontColor").val()+";background-color:"+"#"+$("#btnBgColor").val()+"!important}");
		}
		if(v.indexOf(".imgbox span{color:") >=0 ){
			css = css.replace(v,".imgbox span{color:"+"#"+$("#disFontColor").val()+";background-color:"+"#"+$("#disBgColor").val()+"}");
		}
	})
	$(".akb_iframe")[0].contentDocument.head.getElementsByTagName("style")[2].innerHTML = css;
	var body = $(".akb_iframe")[0].contentDocument.body;
	var prodArea = $(body)[0].getElementsByClassName("slid-items")[0].children;
	$(prodArea).each(function(index) {
		//變更按鈕文字
		var bybtnTextSmallObj = this.getElementsByClassName("infobox-buytxt")[0];
		var bybtnTextBigObj = this.getElementsByClassName("txt-tablecell")[1];
		var bybtnText = $("#btnTxt option:selected").text();
		bybtnTextSmallObj.innerHTML= bybtnText;
		bybtnTextBigObj.innerHTML= bybtnText;
		
		//變更折扣標籤內容
		var disPriceObj = this.getElementsByClassName("infobox-price")[0].innerHTML;
		var originalPriceObj = this.getElementsByClassName("price-original")[0].innerHTML;
		var disPrice = disPriceObj.substring(disPriceObj.indexOf("</b>")+8,disPriceObj.length);
		var originalPrice = originalPriceObj.replace("$NT.","")
		var imgboxObj = this.getElementsByClassName("imgbox")[0];
		var disObject = imgboxObj.getElementsByClassName("pos-absolute")[0];
		var chnineseDiscount = 0;
		var engDiscount = 0;
		if(disPrice < originalPrice){
			chnineseDiscount = parseInt((disPrice * 10) / originalPrice);
			engDiscount = parseInt((originalPrice - disPrice) * 100 / 1000);
			if(disTxtType == "1"){
				$(imgboxObj).attr("class","imgbox crop-height tag-hide");
			}else if(disTxtType == "2"){
				$(imgboxObj).attr("class","imgbox crop-height tag-show");
				disObject.innerHTML = chnineseDiscount+"折";
			}else if(disTxtType == "3"){
				$(imgboxObj).attr("class","imgbox crop-height tag-show");
				disObject.innerHTML = "-"+engDiscount+"%";
			}
		}
	});
}

