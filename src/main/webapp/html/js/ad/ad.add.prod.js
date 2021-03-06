﻿//樣板尺寸
var tproObject = new Object();
tproObject["data"] = {};

//行銷結尾圖
var salesEndIframewp = new Object();
salesEndIframewp["data"] = {};

//行銷圖
var salesIframewp = new Object();
salesIframewp["data"] = {
		index_1:"120_120,120_600",
		index_2:"140_70,140_300",
		index_3:"160_50,160_240",
		index_4:"160_100,160_600",
		index_5:"180_50,180_150",
		index_6:"80_80,250_80",
		index_7:"95_100,300_100",
		index_8:"300_55,300_250",
		index_9:"300_95,300_600",
		index_10:"320_70,320_480",
		index_11:"336_60,336_280",
		index_12:"640_90,640_390",
		index_13:"245_90,728_90",
		index_14:"950_100,950_390",
		index_15:"250_250,970_250"
};

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
			$(".akb_iframe").attr("src","");
		});
	
		$("#checkAdurl").css("display","");
		
		var templateTpro = JSON.parse($("#templateStr").text());
		var templateSizeOptionStr = '';
		var index = 0;
		for (var key in templateTpro) {
			templateSizeOptionStr = '';
			var tproSizeArray =  key.split('_');
			var width = tproSizeArray[0];
			var height = tproSizeArray[1];
			if(key == '300_250'){
				templateSizeOptionStr = '<option value="tpro_'+key+'" selected>'+width+' x '+height+'</option>';	
			}else{
				templateSizeOptionStr = '<option value="tpro_'+key+'">'+width+' x '+height+'</option>';
			}
			$("#adSize").append(templateSizeOptionStr);
			
			var sizeTpros = templateTpro[key].substring(0, templateTpro[key].length -1);
			//建立樣板物件內容
			tproObject.data['tpro_'+key] = sizeTpros;
			//建立行銷結尾圖物件內容
			index = index + 1;
			salesEndIframewp.data['index_'+index] = key;
		}
		
		$("#adSize").change(function() {
			previewTpro = "";
			previewTproIndex = null;
			if($(".akb_iframe")[0].contentDocument.body.children[0] == undefined){
				return false;
			}
			getProdGroup(null);
		});
		
		$(function () {
		    $('#fileupload').fileupload({

		    }).on('fileuploadadd', function (e, data) {
		    	checkUploadRule(data);
		    	return false;
		    	
		    }).on('fileuploaddone', function (e, data) {
		    
		    }).on('fileuploadprogressall', function (e, data) {	
		    
		    }).on('fileuploadprocessalways', function (e, data) {
		    
		    })
		});
		
		
		//init html
		initFancyBoxHtml();
		//init logo color
		initLogoColor();
		
		$(".akb_iframe")[0].addEventListener("load", function() {
			adPreview();
		});
		
		
		$('input[type=radio][name=options]').change(function() {
			getProdGroup(null);
			initLogoColor();
		});
		
		$("input").keyup(function(event){
			 adPreview();
		});
		
		$(".akb_iframe")[0].onload = function(){
			if($(".akb_iframe")[0].src.includes('adProdModel')){
				$('.adcontent').unblock();
			}
		}
});


function initLogoColor(){
	var logoData = JSON.parse($("#userLogoPath").text());
	var colorArray = null;
	var logoRadioType = $('input[name=options]:checked').val()
	if(logoRadioType == "type1"){
		colorArray = logoData.square.color;
	}
	if(logoRadioType == "type2"){
		colorArray = logoData.rectangle.color;
	}
	if(logoRadioType == "type3"){
		colorArray = logoData.square.color;
	}
	var colorInputs = $($(".colorpickr")[0]).find("input");
	$(colorInputs).each(function(index,obj){
		var color = colorArray[index];
		$(obj).css("background-color",color);
		$(obj).css("color",color);
		$(obj).val(color.replace("#",""));
	})
	
}

function initFancyBoxHtml(){
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
		fancyboxSaleEndHtml.push('			<div class="picbx" ondrop="drop(event,this)">');
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
		fancyboxSaleEndHtml.push('<div class="delbx" onclick="deletePreview(this)"><i class="transition"></i>刪除</div>');
		fancyboxSaleEndHtml.push('<div class="reuploadbx" onclick="fileLoad(this)"><i class="transition"></i>重新上傳</div>');
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
	fancyboxSaleEndHtml.push('<input type="button" id="" value="確認送出" class="save-btn" onclick="submitFancybox(this);">');
	fancyboxSaleEndHtml.push('</div>');
	fancyboxSaleEndHtml.push('</div>');
	$("#test").append(fancyboxSaleEndHtml.join(""));
	
	
	
	fancyboxSaleEndHtml = [];
	fancyboxSaleEndHtml.push('<div class="iframewp">');
	fancyboxSaleEndHtml.push('<div class="containr">');
	fancyboxSaleEndHtml.push('<h1>LOGO行銷圖像   <span>商品輪播的LOGO圖像</span></h1>');
	fancyboxSaleEndHtml.push('<ul class="uploadlist bannerLogo">');
	for (var key in salesIframewp.data) {
//		console.log(salesEndIframewp.data[key]);
		var size = salesIframewp.data[key].split(",");
		var bannerSize = size[0];
		var bannerWidth = bannerSize.split('_')[0];
		var bannerHeight = bannerSize.split('_')[1];
		
		var adSize = size[1];
		var adWidth = adSize.split('_')[0];
		var adHeight = adSize.split('_')[1];
		fancyboxSaleEndHtml.push('<li>');
		fancyboxSaleEndHtml.push('	<div class=\"upload-lab\" style=\"display:block;\" onclick=\"fileLoad(this)\">');
		fancyboxSaleEndHtml.push('		<label for="" class="custom-file-upload">');
		fancyboxSaleEndHtml.push('			<i class="icon-uploadfile"></i>圖片尺寸：' + bannerWidth + ' x '+ bannerHeight);
		fancyboxSaleEndHtml.push('			<small>(提供 '+adWidth+'x'+adHeight+' 使用)</small>');
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
		fancyboxSaleEndHtml.push('<div class="delbx" onclick="deletePreview(this)"><i class="transition"></i>刪除</div>');
		fancyboxSaleEndHtml.push('<div class="reuploadbx" onclick="fileLoad(this)"><i class="transition"></i>重新上傳</div>');
		fancyboxSaleEndHtml.push('</div>');
		fancyboxSaleEndHtml.push('<div class="pic">');
		fancyboxSaleEndHtml.push('<div class="form-error">檔案格式錯誤<br>請<strong>刪除</strong>或<strong>重新上傳</strong>');
		fancyboxSaleEndHtml.push('</div>');
		fancyboxSaleEndHtml.push('</div>');
		fancyboxSaleEndHtml.push('</div>');
		fancyboxSaleEndHtml.push('<div class="picinfo">');
		fancyboxSaleEndHtml.push('<p></p>');
		fancyboxSaleEndHtml.push('<u></u>');
		fancyboxSaleEndHtml.push('</div>');
		fancyboxSaleEndHtml.push('</div>');
		fancyboxSaleEndHtml.push('<div style="display:none">' + bannerSize + '</div>');
		fancyboxSaleEndHtml.push('</li> ');
	}              
	fancyboxSaleEndHtml.push('</ul>');
	fancyboxSaleEndHtml.push('<!--uploadlist end-->');
	fancyboxSaleEndHtml.push('</div>');
	fancyboxSaleEndHtml.push('<div class="bottombar">');
	fancyboxSaleEndHtml.push('<input type="button" id="" value="取 消" class="cancel-btn" onclick="closeFancybox();">');
	fancyboxSaleEndHtml.push('<input type="button" id="" value="確認送出" class="save-btn" onclick="submitFancybox(this);">');
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
	$("#groupSelect").val("");
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
			'hideOnOverlayClick': false,
			'enableEscapeButton': false,
			'showCloseButton'	: false,
			 onClosed    :   function() {
			    createSuccessUploadToDom();
			},
    		"onComplete": function(){
    		},
		}
    );
    alex();
}

//開啟fancybox上傳畫面初始化
var upload_size = null;
var uploadDom = null;
var succesImageDataMap = new Object();
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

function drop(ev,obj) {
	uploadDom = obj.parentElement.parentElement;
}


//檢查上傳圖片合法性
function checkUploadRule(file_data){
	var imgArea = $(uploadDom.parentElement.parentElement).find('li');
	readFile(file_data.files[0], function(e) {
		var index = $(uploadDom).index();
		upload_size = imgArea[index].children[3].innerHTML;
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
           
            if(fileExtensionName.indexOf("GIF") >=0){
            	fileSizeFlag = (file_data.files[0].size /1024 > 1024) ? false : true;
            }else{
            	fileSizeFlag = (file_data.files[0].size /1024 > 10240) ? false : true;
            }
            
            if(sizeFlag && fileExtensionNameFlag && fileSizeFlag){
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
                	succesImageDataMap["endSales_uploadLiDom_"+index] = {index:index,fileName:fileName,width:width,height:height,previewSrc:previewSrc,fileExtensionName:fileExtensionName,fileSize:fileSize};
                }
                if(initFancyBoxType == 'logo'){
                	succesImageDataMap["logo_uploadLiDom_"+index] = {index:index,fileName:fileName,width:width,height:height,previewSrc:previewSrc,fileExtensionName:fileExtensionName,fileSize:fileSize};
                }
            }else{
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
            	}else if(fileSizeFlag == false){
            		$(p).append(fileName);
            		$(u).css("color","red");
            		if(fileExtensionName.indexOf("GIF") >=0){
            			$(u).append("檔案需小於1024K");
                    }else{
                    	$(u).append("檔案需小於10240K");
                    }
            	}else if(fileExtensionNameFlag == false){
            		$(p).append(fileName);
            		$(u).css("color","red");
            		$(u).append("請上傳jpg或png格式");
            	}
            	
            	
            	$(imgDom).attr("src","");
            	$(noUpload).css("display","none");
            	$(hasUpload).css("display","none");
            	var errorUpload = $(uploadDom).find(".uploadedbx.error");
            	$(errorUpload).css("display","");
            }
		}
	});
	
	
}

function readFile(file, onLoadCallback){
    var reader = new FileReader();
    reader.onload = onLoadCallback;
    reader.readAsDataURL(file);
}

//預覽中刪除上傳檔案
function deletePreview(obj){
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
}


function closeFancybox(){
	$.fancybox.close();
}

//點擊送出上傳圖片
function submitFancybox(obj){
	var hasFailImg = false;
	var llDom = $(obj.parentElement.parentElement).find('li');
	$(llDom).each(function(index) {
		if($($(this).find('.error')[0]).css("display") == 'block'){
			hasFailImg = true;
		}
	});
	if(!hasFailImg){
		Object.keys(succesImageDataMap).forEach(function(key) {
			var index = succesImageDataMap[key].index;
			var fileName = succesImageDataMap[key].fileName;
			var width = succesImageDataMap[key].width;
			var height = succesImageDataMap[key].height;
			var previewSrc = succesImageDataMap[key].previewSrc;
			var fileExtensionName = succesImageDataMap[key].fileExtensionName;
			var fileSize = succesImageDataMap[key].fileSize;
			if(key.indexOf("endSales") >=0){
				uploadLog[key.replace("endSales_","")] = {index:index,fileName:fileName,width:width,height:height,previewSrc:previewSrc,fileExtensionName:fileExtensionName,fileSize:fileSize};
				delete succesImageDataMap[key];
			}
			if(key.indexOf("logo") >=0){
				uploadLogoLog[key.replace("logo_","")] = {index:index,fileName:fileName,width:width,height:height,previewSrc:previewSrc,fileExtensionName:fileExtensionName,fileSize:fileSize};
				delete succesImageDataMap[key];
			}
		});
		$(llDom).each(function(index) {
			if($(this).children()[0].style.display == ""){
				if(initFancyBoxType == 'logo'){
					delete uploadLogoLog["uploadLiDom_"+index];
				}else if(initFancyBoxType == 'endSales'){
					delete uploadLog["uploadLiDom_"+index];
				}
			}
		});
		$.fancybox.close();
	}
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
		$("#adTextMsg").val("廣告名稱不可為空");
		$("#adTextMsg").css("display","");
		return {msg:"廣告名稱不可為空",flag:false};
	}else{
		$("#adTextMsg").val("");
		$("#adTextMsg").css("display","none");
	}
	
	var catalogId = $("#catalogSelect").val();
	if(catalogId == null){
		$("#catalogSelect")[0].scrollIntoView();
		return {msg:"請先建立商品目錄",flag:false};
	}
	
	var catalogGroupId = $("#groupSelect").val();
	if(catalogGroupId == ""){
		$("#groupSelect")[0].scrollIntoView();
		$("#groupSelectMsg").css("display","");
		$("#groupSelectMsg")[0].innerHTML = "目前無商品群組，請檢查是否建立或等待審核中";
		return {msg:"目前無商品群組，請檢查是否建立或等待審核中",flag:false};
	}else{
		$("#groupSelectMsg")[0].innerHTML = "";
		$("#groupSelectMsg").css("display","none");
	}
	
	var checkAdurl = $("#checkAdurl").text();
	if(checkAdurl != "網址確認正確"){
		$("#adurl")[0].scrollIntoView();
		$("#checkAdurl").css("display","");
		$("#checkAdurl")[0].innerHTML = "請輸入正確網址";
		return {msg:"請輸入正確網址",flag:false};
	}
	
	var logoText = $("#logoText").val();
	if(logoText.length == 0 && $('input[name=options]:checked').val() == "type3"){
		$("#logoText")[0].scrollIntoView();
		$("#logoTextMsg").css("display","");
		$("#logoTextMsg")[0].innerHTML = '請輸入LOGO標題文字';
		return {msg:"請輸入LOGO標題文字",flag:false};
	}else{
		$("#logoTextMsg").css("display","none");
		$("#logoTextMsg")[0].innerHTML = '';
	}
	
	if($("#adGroupSeq").val() == "" || $("#adGroupSeq").val() == null){
		var url = new URL(location.href);
		var adGroupSeq = url.searchParams.get('adGroupSeq');
		if(adGroupSeq == "" || adGroupSeq == null){
			return {msg:"分類代號錯誤",flag:false};
		}else{
			$("#adGroupSeq").val(adGroupSeq);
		}
	}
	return {msg:"",flag:true};
}

//點擊送出審核
function adSubmit(){
	var submitFlag = true;
	if(checkSubmit().flag == false){
		submitFlag = checkSubmit().flag;
//		alert(checkSubmit().msg);
	}
	
	if(submitFlag){
//		console.log("驗證OK");
		var adName = $("#adName").val();
		var catalogId = $("#catalogSelect").val();
		var catalogGroupId = $("#groupSelect").val();
		var adurl = $("#adurl").val();
		var logoBgColor = $("#logoBgColor").val();
		var logoType = "";
		var radioType = $('input[name=options]:checked').val();
		var logoPath = "";
		
		var logoData = JSON.parse($("#userLogoPath").text());
		if(radioType == "type1"){
			logoType = "type1";
			logoPath = logoData.square.logoPath;
		}
		if(radioType == "type2"){
			logoType = "type1";
			logoPath = logoData.rectangle.logoPath;
		}
		if(radioType == "type3"){
			logoType = "type2";
			logoPath = logoData.square.logoPath;
		}
		var logoText = $("#logoText").val();
		var logoFontColor = $("#logoFontColor").val();
		var btnTxt = $("#btnTxt").val();
		var btnBgColor = $("#btnBgColor").val();
		var btnFontColor = $("#btnFontColor").val();
		var disTxtType = $("#disTxtType").val();
		var disFontColor = $("#disFontColor").val();
		var disBgColor =  $("#disBgColor").val();
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
		        onBlock: function() {
		        	var uploadLogSave  = JSON.stringify(uploadLog);
		        	var uploadLogoLogSave = JSON.stringify(uploadLogoLog);
		        	var adGroupSeq = $("#adGroupSeq").val();
		        	var adName = $("#adName").val();
		        	var catalogId = $("#catalogSelect").val();
		        	var catalogGroupId = $("#groupSelect").val().split("_")[1];
		        	var adLinkURL = $("#adurl").val();
		        	var logoText = $("#logoText").val();
		        	var logoBgColor = "#"+$("#logoBgColor").val();
		        	var logoFontColor = "#"+$("#logoFontColor").val();
		        	var btnTxt = $("#btnTxt").val();
		        	var btnFontColor = "#"+$("#btnFontColor").val();
		        	var btnBgColor = "#"+$("#btnBgColor").val();
		        	var disFontColor = "#"+$("#disFontColor").val();
		        	var disBgColor = "#"+$("#disBgColor").val();
		        	var disTxtType = $("#disTxtType").val();
		        	var prodLogoType = $('input[name=options]:checked').val();
		        	
		        	$.ajax({
		        		url : "adAddProdSaveAjax.html",
						type : "POST",
						dataType:'json',
						data : {
							"uploadLog":uploadLogSave,
							"uploadLogoLog":uploadLogoLogSave,
							"adGroupSeq":adGroupSeq,
							"adName" : adName,
							"catalogId":catalogId,
							"catalogGroupId" : catalogGroupId,
			    			"adLinkURL" : adLinkURL,
			    			"logoType" : logoType,
			    			"logoText" : logoText,
			    			"logoBgColor":logoBgColor,
			    			"logoFontColor":logoFontColor,
			    			"btnTxt":btnTxt,
			    			"btnFontColor":btnFontColor,
			    			"btnBgColor":btnBgColor,
			    			"disFontColor":disFontColor,
			    			"disBgColor":disBgColor,
			    			"disTxtType":disTxtType,
			    			"prodLogoType":prodLogoType,
			    			"logoPath":logoPath
						},
						success : function(respone) {
							if(respone == "success"){
								$.unblockUI();
								$(location).attr('href','adAddFinish.html?adGroupSeq='+$("#adGroupSeq").val()+'&adOperatingRule='+$("#adOperatingRule").val());	
							} else {
								$.unblockUI();
								alert(respone);
							}
						},
						error: function(xtl) {
							$.unblockUI();
							alert("系統繁忙，請稍後再試！");
						}
					});
		        }
			});
		}
	}
}

var previewTpro = "";
var previewTproIndex = null;
function getProdGroup(obj){
	var catalogGroupId = $("#groupSelect").val();
	if(catalogGroupId == ""){
		return false;
	}
	
	if(previewTpro == ""){
		var tproSize = $("#adSize option:selected").val();
		var tproArray = tproObject.data[tproSize].split(",");
		if(tproArray.length > 0){
			previewTproIndex = 0;
			previewTpro = tproArray[0];
		}else{
			log.info(">>>>>>fail:preview tpro size == 0")
			return false;
		}
	}
	
	
	$('.adcontent').block({ 
		message: "<div><div><b><font size=4>預覽中...</font></b></div><div><img src='html/img/LoadingWait.gif' /></div></div>",
		css: {
	  },
	});  
	
	
	var selectSizeWidth = $("#adSize option:selected").text().split(" x ")[0];
	var selectSizeHeight = $("#adSize option:selected").text().split(" x ")[1];
	var catalogGroupId = $("#groupSelect").val().split("_")[1];
	var imgProportiona = $("#groupSelect").val().split("_")[2];
	var posterType = "noposter";
	
	Object.keys(uploadLog).forEach(function(key) {
		var height = String(uploadLog[key].height);
		var width = String(uploadLog[key].width);
		if(width == selectSizeWidth && height == selectSizeHeight){
			posterType = "hasposter";
		}
	});
	
	$(".akb_iframe").width(selectSizeWidth).height(selectSizeHeight);
	var totalWidth = $(".adcontent").width();
	var adWidth = $("#adSize option:selected").text().split(" x ")[0];
	var left = (totalWidth - adWidth) / 2;
	$(".akb_iframe").css("margin-left",left+"px");
	$(".akb_iframe")[0].style.backgroundColor = "#fff";
	
	
	var logoData = JSON.parse($("#userLogoPath").text());
	var prodLogoType = "";
	var logoPath = "";
	
	var radioType = $('input[name=options]:checked').val();
	if(radioType == "type2"){
		logoPath = logoData.rectangle.logoPath;
	}else{
		logoPath = logoData.square.logoPath;
	}
	prodLogoType = radioType;
	
	var realUrl = null;
	if(($("#checkAdurl")[0].innerHTML) == '網址確認正確'){
		if($("#adurl").val().indexOf("http") >=0){
			realUrl = $("#adurl").val();
		}else{
			realUrl = "//"+$("#adurl").val()
		}
	}else{
		realUrl = "null";
	}
	
	var src = 'adProdModel.html'
		+'?catalogGroupId='+encodeURIComponent(catalogGroupId)
		+'&btnTxt='+encodeURIComponent(encodeURIComponent($("#btnTxt option:selected").text()))
		+'&btnFontColor='+encodeURIComponent("#"+btnFontColor.value)
		+'&btnBgColor='+encodeURIComponent("#"+btnBgColor.value)
		+'&disTxtType='+encodeURIComponent($("#disTxtType").val())
		+'&disBgColor='+encodeURIComponent("#"+disBgColor.value)
		+'&disFontColor='+encodeURIComponent("#"+disFontColor.value)
		+"&posterType="+posterType
		+"&logoText="+encodeURIComponent(encodeURIComponent($("#logoText").val()))
		+"&logoBgColor="+encodeURIComponent("#"+logoBgColor.value)
		+"&logoFontColor="+encodeURIComponent("#"+logoFontColor.value)
		+"&prodLogoType="+encodeURIComponent(prodLogoType)
		+"&imgProportiona="+encodeURIComponent(imgProportiona)
		+"&userLogoPath="+encodeURIComponent(logoPath)
		+"&realUrl="+encodeURIComponent(realUrl)
		+"&previewTpro="+encodeURIComponent(previewTpro)
		+"&saleImg="
		+"&saleEndImg="
		$(".akb_iframe").attr('src' ,src);
}

function adPreview(){
	if($(".akb_iframe")[0].contentDocument.body.children[0] == undefined){
		return false;
	}
//	console.log('====adPreview start====');
	var css = $(".akb_iframe")[0].contentDocument.head.getElementsByTagName("style")[2].innerHTML;
	css = css.replace("<#dad_logo_font_color>","#"+$("#logoFontColor").val());
	css = css.replace("<#dad_logo_bg_color>","#"+$("#logoBgColor").val());
	css = css.replace("<#dad_buybtn_font_color>","#"+$("#btnFontColor").val());
	css = css.replace("<#dad_buybtn_bg_color>","#"+$("#btnBgColor").val());
	css = css.replace("<#dad_dis_font_color>","#"+$("#disFontColor").val());
	css = css.replace("<#dad_dis_bg_color>","#"+$("#disBgColor").val());
	$(".akb_iframe")[0].contentDocument.head.getElementsByTagName("style")[2].innerHTML = css;
	var body = $(".akb_iframe")[0].contentDocument.body;
	var typeObj = $(body)[0].getElementsByClassName("ads-container")[0].getElementsByTagName('a')[0].children[0];
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
	var flag = false;
	for (var key in uploadLogoLog) {
		var uploadBannerWidth = uploadLogoLog[key].width;
		var uploadBannerHeight = uploadLogoLog[key].height;
		var uploadBannerPreviewSrc = uploadLogoLog[key].previewSrc;
		for (var key in salesIframewp.data) {
			var size = salesIframewp.data[key].split(",");
			var adSize = size[1];
			var adWidth = adSize.split('_')[0];
			var adHeight = adSize.split('_')[1];
			if(selectSizeWidth == adWidth && selectSizeHeight == adHeight){
				var bannerSize = size[0];
				var bannerWidth = bannerSize.split('_')[0];
				var bannerHeight = bannerSize.split('_')[1];
				if(uploadBannerWidth == bannerWidth && uploadBannerHeight == bannerHeight){
					$(logoBgImgObj).attr("src",uploadBannerPreviewSrc);
					if((uploadBannerWidth == '250' && uploadBannerHeight == '250') || (uploadBannerWidth == '245' && uploadBannerHeight == '90') ||  (uploadBannerWidth == '95' && uploadBannerHeight == '100' && previewTpro == 'c_x05_pad_tpro_0127') || (uploadBannerWidth == '80' && uploadBannerHeight == '80' && previewTpro == 'c_x05_pad_tpro_0119')){
						logoBgImgObj.parentElement.parentElement.className ="type3 logo-box pos-absolute pos-top pos-right";
					}else{
						logoBgImgObj.parentElement.parentElement.className ="type3 logo-box pos-absolute pos-top pos-left";
					}
					flag = true;
					break;
				}
			}
		}
		if(flag){
			break;
		}
	}
}

function changeActive(obj){
	if($(".akb_iframe")[0].contentDocument.body.children[0] == undefined){
		return false;
	}
	//1.變更按紐文字
	var body = $(".akb_iframe")[0].contentDocument.body;
	var prodArea = $(body)[0].getElementsByClassName("slid-items")[0].children;
	var logoArea = $(body)[0].getElementsByClassName("logo-box")[0];
	$(logoArea).css('background-color', "#"+$("#logoBgColor").val());
	//變更標題文字顏色
	$($(logoArea)[0].getElementsByClassName("type2")[0]).css('color', "#"+$("#logoFontColor").val());
	$(prodArea).each(function(index) {
		//變更按鈕文字
		var bybtnTextSmallObj = this.getElementsByClassName("infobox-buytxt")[0];
		var bybtnTextBigObj = this.getElementsByClassName("txt-tablecell")[1];
		var bybtnText = $("#btnTxt option:selected").text();
		bybtnTextSmallObj.innerHTML= bybtnText;
		bybtnTextBigObj.innerHTML= bybtnText;
		//變更為當前按鈕顏色
		$(bybtnTextBigObj).css('background-color', "#"+$("#btnBgColor").val());
		//變更為當前按鈕文字顏色
		$(bybtnTextBigObj).css('color', "#"+$("#btnFontColor").val());
		//變更折扣標籤內容
		if($("#disTxtType").val() == 1){
			$(this.getElementsByClassName("pos-absolute")[0]).hide();
		}else{
			var crop = this.getElementsByClassName("imgbox")[0].className;
			if(crop.match("nocrop")){
				crop = 'nocrop';
			}else {
				crop = 'crop';
			}
			$(this.getElementsByClassName("pos-absolute")[0]).show();
			this.getElementsByClassName("imgbox")[0].setAttribute("class","imgbox "+crop+" tag-show");
			
			var disObject = this.getElementsByClassName("imgbox")[0];
			var disText = disObject.getElementsByClassName("infobox-price")[0].innerHTML;
			var originalPrice =  disText.substring(disText.indexOf("</small>"),disText.indexOf("</b>")).replace("</small>","");
			var disPrice =  disText.substring(disText.lastIndexOf("</small>"),disText.length).replace("</small>","");
			var dis = "";
			//中文折扣
			if($("#disTxtType").val() == "2"){
				if(disPrice != originalPrice){
					dis = ((disPrice / originalPrice) * 10).toFixed(1);
					dis = dis.replace(".","");
					dis = dis.replace("0","");
					var disObject = this.getElementsByClassName("imgbox")[0].getElementsByTagName("span")[0];
					disObject.innerHTML = dis+"折";
					$(disObject).css('color', "#"+$("#disFontColor").val());
					$(disObject).css('background-color', "#"+$("#disBgColor").val());
				}
				if(disPrice == originalPrice){
					$(this.getElementsByClassName("pos-absolute")[0]).hide();
				}
			}else if($("#disTxtType").val() == "3"){//英文折扣
				if(disPrice != originalPrice){
					dis = Math.round((((disPrice / originalPrice) - 1) * 100));
					var disObject = this.getElementsByClassName("imgbox")[0].getElementsByTagName("span")[0];
					disObject.innerHTML = dis+"%";
					$(disObject).css('color', "#"+$("#disFontColor").val());
					$(disObject).css('background-color', "#"+$("#disBgColor").val());
				}
				if(disPrice == originalPrice){
					$(this.getElementsByClassName("pos-absolute")[0]).hide();
				}
			}
		}
	});
}



function changeLogoUrl(){
	if($(".akb_iframe")[0].contentDocument.body.children[0] == undefined){
		return false;
	}
	var logoAhref = $($(".akb_iframe")[0].contentDocument.body.children[0]).find("a")[0];
	var realUrl = null;
	if(($("#checkAdurl")[0].innerHTML) == '網址確認正確'){
		if($("#adurl").val().indexOf("http") >=0){
			realUrl = $("#adurl").val();
		}else{
			realUrl = "//"+$("#adurl").val()
		}
	}else{
		realUrl = "null";
	}
	logoAhref.href = realUrl;
	var adUrlDom = $($($(".akb_iframe")[0].contentDocument.body.children[0]).find("nav-box").context.getElementsByClassName("slid-box"))[0]; 
	
	if(($("#checkAdurl")[0].innerHTML) == '網址確認正確'){
		getProdGroup(null);
	}
}

function clickColor(obj){
	$("#logoBgColor").val(obj.value);
	$("#logoBgColor").css("background","#"+obj.value);
	//變更預覽
	changeActive(obj);
}

function approveSize(obj){
	var boxObj = null;
	if(obj=='行銷圖像支援規格查詢' || obj == '結尾行銷圖像支援規格查詢'){
		boxObj = $("#approveSize").find("h4")[0].innerHTML = obj;
		boxObj = $("#approveSize").html();
	}else if(obj =='行銷圖像說明'){
		boxObj = $("#note1").find("h4")[0].innerHTML = obj;
		boxObj = $("#note1").html();
	}else if(obj == '結尾行銷圖像設定'){
		boxObj = $("#note2").find("h4")[0].innerHTML = obj;
		boxObj = $("#note2").html();
	}
	$.fancybox(
	boxObj,
	{
		'autoDimensions'	: false,
		'width'         	: "500",
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
		'enableEscapeButton': false,
		'showCloseButton'	: false,
	}
);
}

function opennots(id){
	$("#shownotes"+id).css("visibility", "visible");
}

function closenots(id) {
	$("#shownotes"+id).css("visibility", "hidden");
}

function changeTpro(switchType){
	if($(".akb_iframe")[0].contentDocument.body.children[0] == undefined){
		return false;
	}
	
	var tproSize = $("#adSize option:selected").val();
	var tproArray = tproObject.data[tproSize].split(",");
	if(switchType == "right"){
		previewTproIndex = previewTproIndex + 1;
		if(previewTproIndex <= (tproArray.length - 1)){
			previewTpro = tproArray[previewTproIndex];
		}else{
			previewTproIndex = 0;
			previewTpro = tproArray[0];
		}
		getProdGroup(null);
	}else if(switchType == "left"){
		previewTproIndex = previewTproIndex - 1;
		if(previewTproIndex < 0){
			previewTproIndex = tproArray.length - 1;
			previewTpro = tproArray[previewTproIndex];
		}else{
			previewTpro = tproArray[previewTproIndex];
		}
		getProdGroup(null);
	}
}


