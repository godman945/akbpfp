﻿	
	//樣板尺寸
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
	﻿
	//開啟fancybox上傳畫面初始化
	var upload_size = null;
	var uploadDom = null;
	var saleEndImgUpload = new Object();
	var saleImgUpload = new Object();
	var initFancyBoxType = null;
	
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
			var tpro = $("#adSize option:selected").val();
			var tproArray = tproObject.data[tpro].split(",");
			previewTpro = tproArray[0];
			previewTproIndex = 0;
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
});

window.onload = function(){
	//init html
	initFancyBoxHtml();
	
	initCatalogSelect();
	//init 已存資料
	initSaveData();
	$("#saleEndImgUploadBtn").attr("disabled", false);
	
	initLogoColor();
	
}


function initLogoColor(){
	var logoData = JSON.parse($("#saveUserLogoPath").text());
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
		$(obj).val(color.replace("#",""));
	})
	
}

//初始化已存資料
var first = true;
function initSaveData(){
	var saleEndImgTextArea = JSON.parse($("#saveSaleEndImg").text());
	var uploadLogTextFlag = false;
	for(var key in saleEndImgTextArea){
		var imgWidth = saleEndImgTextArea[key].width;
		var imgHeight = saleEndImgTextArea[key].heigth;
		var fileSize = saleEndImgTextArea[key].fileSize;
		var fileName = saleEndImgTextArea[key].fileName;
		var previewSrc = saleEndImgTextArea[key].previewSrc;
		var fileExtensionName = saleEndImgTextArea[key].fileExtensionName;
		for (var salesEndIframewpKey in salesEndIframewp.data) {
			var width = salesEndIframewp.data[salesEndIframewpKey].split("_")[0];
			var height = salesEndIframewp.data[salesEndIframewpKey].split("_")[1];
			if(imgWidth == width && imgHeight == height){
				var index = (salesEndIframewpKey.split("_")[1]) - 1;
				saleEndImgUpload["uploadLiDom_"+index] = {fileExtensionName:fileExtensionName,fileName:fileName,fileSize:fileSize,height:height,index:index,previewSrc:previewSrc,width:width};
				uploadLogTextFlag = true;
			}
		}
	}
	
	
	var saleImgTextArea = JSON.parse($("#saveSaleImg").text());
	var uploadLogoLogTextFlag = false;
	for(var key in saleImgTextArea){
		var imgWidth = saleImgTextArea[key].width;
		var imgHeight = saleImgTextArea[key].heigth;
		var fileSize = saleImgTextArea[key].fileSize;
		var fileName = saleImgTextArea[key].fileName;
		var previewSrc = saleImgTextArea[key].previewSrc;
		var fileExtensionName = saleImgTextArea[key].fileExtensionName;
		for (var salesIframewpKey in salesIframewp.data) {
			var saleImgSizeArray =  salesIframewp.data[salesIframewpKey].split(",")[0].split("_");
			if(imgWidth == saleImgSizeArray[0] && imgHeight == saleImgSizeArray[1]){
				var index = (salesIframewpKey.split("_")[1]) - 1;
				saleImgUpload["uploadLiDom_"+index] = {fileExtensionName:fileExtensionName,fileName:fileName,fileSize:fileSize,height:imgHeight,index:index,previewSrc:previewSrc,width:imgWidth};
				uploadLogoLogTextFlag = true;
			}
		}
	}
	
	if(uploadLogTextFlag){
		initFancyBoxType = 'endSales';
		createSuccessUploadToDom();
	}
	if(uploadLogoLogTextFlag){
		initFancyBoxType = 'logo';
		createSuccessUploadToDom();
	}
	initFancyBoxType = null;
	
	$("#adName").val($("#saveAdName").val()).trigger("keyup");
	$("#adurl").val($("#saveAdLinkURL").val()).trigger("blur");
	$("#btnTxt").val($("#saveBtnTxt").val()).trigger("change");
	$("#disTxtType").val($("#saveDisTxtType").val()).trigger("change");
	$("#catalogSelect").val($("#saveCatalogId").val()).trigger("change");
	$("#logoBgColor").val($("#saveLogoBgColor").val());
	$("#logoFontColor").val($("#saveLogoFontColor").val());
	$("#btnFontColor").val($("#saveBtnFontColor").val());
	$("#btnBgColor").val($("#saveBtnBgColor").val());
	$("#disBgColor").val($("#saveDisBgColor").val());
	$("#disFontColor").val($("#saveDisFontColor").val());
	$("#logoText").val($("#saveLogoText").val()).trigger("keyup");
	$('input:radio[name=options]').val([$("#saveProdLogoType").val()]);
	
	$("#groupSelect option").each(function(){
		if(this.value.indexOf($("#saveCatalogId").val()+"_"+$("#saveCatalogGroupId").val()) >=0){
			$("#groupSelect").val(this.value).trigger("change");
		}
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
	fancyboxSaleEndHtml.push('<ul class="uploadlist">');
	
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


function initCatalogSelect(){
	var catalogSelectValue = $("#catalogSelect").val();
	$("#groupSelect").val("");
	$("#groupSelect option").each(function(){
		if(this.value != ""){
			var groupInfo = this.value.split("_");
			if(groupInfo[0] != catalogSelectValue){
				$(this).css("display","none");
				$(this).hide();
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

var succesImageDataMap = new Object();
function alex(){
//	console.log("alex");
//	console.log(initFancyBoxType);
	upload_size = null;
	uploadDom = null;
	var uploadlist = null;
	var uplogData = null;
	
	
	uploadlist = $(".uploadlist")[2];
	if(initFancyBoxType == 'endSales'){
		uplogData = saleEndImgUpload;
	}
	if(initFancyBoxType == 'logo'){
		uplogData = saleImgUpload;
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
//	console.log("fileLoad");
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
//	console.log("checkUploadRule");
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
            
            if(sizeFlag && fileExtensionNameFlag){
//            	console.log(initFancyBoxType + " SUCCESS UPLOAD");
            	
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
//                	uploadLog["uploadLiDom_"+index] = {index:index,fileName:fileName,width:width,height:height,previewSrc:previewSrc,fileExtensionName:fileExtensionName,fileSize:fileSize};
                	succesImageDataMap["endSales_uploadLiDom_"+index] = {index:index,fileName:fileName,width:width,height:height,previewSrc:previewSrc,fileExtensionName:fileExtensionName,fileSize:fileSize};
                }
                if(initFancyBoxType == 'logo'){
                	succesImageDataMap["logo_uploadLiDom_"+index] = {index:index,fileName:fileName,width:width,height:height,previewSrc:previewSrc,fileExtensionName:fileExtensionName,fileSize:fileSize};
//                	uploadLogoLog["uploadLiDom_"+index] = {index:index,fileName:fileName,width:width,height:height,previewSrc:previewSrc,fileExtensionName:fileExtensionName,fileSize:fileSize};
                }
            }else{
//            	console.log("FAIL UPLOAD");
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
//            		delete uploadLog['uploadLiDom_'+index];
            	}
            	if(initFancyBoxType == 'logo'){
//            		delete uploadLogoLog['uploadLiDom_'+index];
            	}
            }
		}
	});
	
	
	//重設定預覽內容
	//adPreview();
}

function readFile(file, onLoadCallback){
    var reader = new FileReader();
    reader.onload = onLoadCallback;
    reader.readAsDataURL(file);
}

//預覽中刪除上傳檔案
function deletePreview(obj){
//	console.log("刪除上傳檔案");
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
//    	delete uploadLog['uploadLiDom_'+index]; 
	}
	if(initFancyBoxType == 'logo'){
//		delete uploadLogoLog['uploadLiDom_'+index];
	}
}


function closeFancybox(){
//	var alt = "提醒您，上傳檔案將被清空是否仍要關閉";
//	if(confirm(alt)) {
//		Object.keys(uploadLog).forEach(function(key) {
//			if(initFancyBoxType == 'endSales'){
//		    	delete uploadLog[key];
//			}
//			if(initFancyBoxType == 'logo'){
//				delete uploadLogoLog[key];
//			}
//		})
//	}
	$.fancybox.close();	
	
}

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
				saleEndImgUpload[key.replace("endSales_","")] = {index:index,fileName:fileName,width:width,height:height,previewSrc:previewSrc,fileExtensionName:fileExtensionName,fileSize:fileSize};
				delete succesImageDataMap[key];
			}
			if(key.indexOf("logo") >=0){
				saleImgUpload[key.replace("logo_","")] = {index:index,fileName:fileName,width:width,height:height,previewSrc:previewSrc,fileExtensionName:fileExtensionName,fileSize:fileSize};
				delete succesImageDataMap[key];
			}
		});
//		console.log(succesImageDataMap);
//		console.log(uploadLog);
//		console.log(uploadLogoLog);
//		console.log(initFancyBoxType);
		$(llDom).each(function(index) {
			if($(this).children()[0].style.display == ""){
				if(initFancyBoxType == 'logo'){
					delete saleImgUpload["uploadLiDom_"+index];
				}else if(initFancyBoxType == 'endSales'){
					delete saleEndImgUpload["uploadLiDom_"+index];
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
    	uploadData = saleEndImgUpload;
	}
	if(initFancyBoxType == 'logo'){
		a = $(".adbannerpicbx")[0];
		uploadData = saleImgUpload;
	}
	
	$(a).empty();
	
	if(uploadData != null){
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
}

function clickDeleteUpload(obj,deleteKey){
	var imgAreaId = obj.parentElement.parentElement.parentElement.id;
	if(imgAreaId == 'salesEndImgArea'){
		delete saleEndImgUpload[deleteKey];
	}else if(imgAreaId == 'logoImgArea'){
		delete saleImgUpload[deleteKey];
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
	return {msg:"",flag:true};
}

//點擊送出審核
function adEditSubmit(){
	var submitFlag = true;
	if(checkSubmit().flag == false){
		submitFlag = checkSubmit().flag;
//		alert(checkSubmit().msg);
	}
	
	if(submitFlag){
		console.log("驗證OK");
		var adName = $("#adName").val();
		var catalogId = $("#catalogSelect").val();
		var catalogGroupId = $("#groupSelect").val();
		var adurl = $("#adurl").val();
		var logoBgColor = $("#logoBgColor").val();
		var logoType = $('input[name=options]:checked').val();
		var radioType = $('input[name=options]:checked').val();
		var logoPath = "";
		
		var logoData = JSON.parse($("#saveUserLogoPath").text());
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
//		        	console.log(saleImgUpload);
		        	var logoImg = "";
		        	Object.keys(saleImgUpload).forEach(function(key) {
		        		logoImg = saleImgUpload[key].previewSrc;
		        	})
		        	
			    		
		        	$.ajax({
						url : "adProdEditSaveAjax.html",
						type : "POST",
						dataType:'json',
						data : {
							"adSeq":$("#adSeq").val(),
							"uploadLog":JSON.stringify(saleEndImgUpload),
							"uploadLogoLog":JSON.stringify(saleImgUpload),
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
			    			"prodLogoType":$('input[name=options]:checked').val(),
			    			"logoPath":logoPath
						},
						success : function(respone) {
//							console.log(respone);
							if(respone == "success"){
								$(location).attr('href','adAdView.html?adGroupSeq='+$("#adGroupSeq").val());	
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


var previewTpro = "";
var previewTproIndex = 0;
function getProdGroup(obj){
	var catalogGroupId = $("#groupSelect").val();
	if(catalogGroupId == ""){
		$(".akb_iframe").attr("src","");
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
	
	
	var selectSizeWidth = $("#adSize option:selected").text().split(" x ")[0];
	var selectSizeHeight = $("#adSize option:selected").text().split(" x ")[1];
	var catalogGroupId = $("#groupSelect").val().split("_")[1];
	var imgProportiona = $("#groupSelect").val().split("_")[2];
	var imgShowType = "noposter";
//	console.log(catalogGroupId);
	
	Object.keys(saleEndImgUpload).forEach(function(key) {
		var height = String(saleEndImgUpload[key].height);
		var width = String(saleEndImgUpload[key].width);
		if(width == selectSizeWidth && height == selectSizeHeight){
			imgShowType = "hasposter";
		}
	});
	$(".akb_iframe").width(selectSizeWidth).height(selectSizeHeight);
	var totalWidth = $(".adcontent").width();
	var adWidth = $("#adSize option:selected").text().split(" x ")[0];
	var left = (totalWidth - adWidth) / 2;
	$(".akb_iframe").css("margin-left",left+"px");
	
	var btnFontColor = $("#btnFontColor").val();
	var btnBgColor = $("#btnBgColor").val();
	var logoBgColor = $("#logoBgColor").val();
	var logoFontColor = $("#logoFontColor").val();
	var disBgColor = $("#disBgColor").val();
	var disFontColor = $("#disFontColor").val();
	if(btnFontColor.indexOf("#") < 0){
		btnFontColor = "#"+btnFontColor;
	}
	if(btnBgColor.indexOf("#") < 0){
		btnBgColor = "#"+btnBgColor;
	}
	if(logoBgColor.indexOf("#") < 0){
		logoBgColor = "#"+logoBgColor;
	}
	if(logoFontColor.indexOf("#") < 0){
		logoFontColor = "#"+logoFontColor;
	}
	if(disBgColor.indexOf("#") < 0){
		disBgColor = "#"+disBgColor;
	}
	if(disFontColor.indexOf("#") < 0){
		disFontColor = "#"+disFontColor;
	}
	
	var logoData = JSON.parse($("#saveUserLogoPath").text());
	var prodLogoType = "";
	var logoPath = "";
	var radioType = $('input[name=options]:checked').val();
	if(radioType == "type1"){
		prodLogoType = "type1";
		logoPath = encodeURIComponent(logoData.square.logoPath);
	}
	if(radioType == "type2"){
		prodLogoType = "type1";
		logoPath = encodeURIComponent(logoData.rectangle.logoPath);
	}
	if(radioType == "type3"){
		prodLogoType = "type2";
		logoPath = encodeURIComponent(logoData.square.logoPath);
	}
	
	var realUrl = null;
	if(($("#checkAdurl")[0].innerHTML) == '網址確認正確'){
		if($("#adurl").val().indexOf("http") >=0){
			realUrl = $("#adurl").val();
		}else{
			realUrl = "//"+$("#adurl").val()
		}
	}else{
		realUrl = "javascript:void(0);";
	}
	if(first){
		if($("#adurl").val().indexOf("http") >=0){
			realUrl = $("#adurl").val();
		}else{
			realUrl = "//"+$("#adurl").val()
		}
		first = false;
	}
	console.log(imgProportiona);
	
		var src = 'adProdModel.html'
		+'?catalogGroupId='+encodeURIComponent(catalogGroupId)
		+'&btnTxt='+encodeURIComponent($("#btnTxt").val())
		+'&btnFontColor='+encodeURIComponent(btnFontColor)
		+'&btnBgColor='+encodeURIComponent(btnBgColor)
		+'&disTxtType='+encodeURIComponent($("#disTxtType").val())
		+'&disBgColor='+encodeURIComponent(disBgColor)
		+'&disFontColor='+encodeURIComponent(disFontColor)
		+"&imgShowType="+imgShowType
		+"&logoText="+encodeURIComponent($("#logoText").val())
		+"&logoBgColor="+encodeURIComponent(logoBgColor)
		+"&logoFontColor="+encodeURIComponent(logoFontColor)
		+"&prodLogoType="+encodeURIComponent(prodLogoType)
		+"&imgProportiona="+encodeURIComponent(imgProportiona)
		+"&userLogoPath="+logoPath
		+"&realUrl="+encodeURIComponent(realUrl)
		+"&previewTpro="+encodeURIComponent(previewTpro)
		+"&saleImg="
		+"&saleEndImg="
		$(".akb_iframe").attr('src' ,src);
		console.log(src);
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
	
	Object.keys(saleEndImgUpload).forEach(function(key) {
		var height = String(saleEndImgUpload[key].height);
		var width = String(saleEndImgUpload[key].width);
		if(width == selectSizeWidth && height == selectSizeHeight){
			var previewSrc = String(saleEndImgUpload[key].previewSrc);
			var bgStyle = $(bgDiv).attr("style","background-image:url("+previewSrc+")");
		}
	});
	
	//商品行銷圖
	var logoBgImgObj = $(body)[0].getElementsByClassName("logo-img-background")[0];
	var flag = false;
	
	for (var key in saleImgUpload) {
		var uploadBannerWidth = saleImgUpload[key].width;
		var uploadBannerHeight = saleImgUpload[key].height;
		var uploadBannerPreviewSrc = saleImgUpload[key].previewSrc;
		//檢查目前預覽尺寸
		for (var key in salesIframewp.data) {
			var saleImgSizeArray =  salesIframewp.data[key].split(",")[1].split("_");			
			var adWidth = saleImgSizeArray[0];
			var adHeight = saleImgSizeArray[1];
			if(selectSizeWidth == adWidth && selectSizeHeight == adHeight){
				saleImgSizeArray =  salesIframewp.data[key].split(",")[0].split("_");
				var BannerSizeWidth = saleImgSizeArray[0];
				var BannerSizeHeight = saleImgSizeArray[1]; 
				if(uploadBannerWidth == BannerSizeWidth && uploadBannerHeight == BannerSizeHeight){
					$(logoBgImgObj).attr("src",uploadBannerPreviewSrc);
					logoBgImgObj.parentElement.parentElement.className ="type3 logo-box pos-absolute pos-top pos-left";
					return false;
				}
			}
		}
	}
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
		var chineseDiscount = 0;
		var engDiscount = 0;
		if(disPrice < originalPrice){
			chineseDiscount = parseInt(((disPrice / originalPrice) * 10));
			engDiscount =  ((disPrice / originalPrice) - 1) * 100;
			engDiscount = (engDiscount.toFixed(1));
//			chineseDiscount = parseInt((disPrice * 10) / originalPrice);
//			engDiscount = parseInt((originalPrice - disPrice) * 100 / 1000);
			if(disTxtType == "1"){
				$(imgboxObj).attr("class","imgbox crop-height tag-hide");
			}else if(disTxtType == "2"){
				$(imgboxObj).attr("class","imgbox crop-height tag-show");
				disObject.innerHTML = chineseDiscount+"折";
			}else if(disTxtType == "3"){
				$(imgboxObj).attr("class","imgbox crop-height tag-show");
				disObject.innerHTML = engDiscount+"%";
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
		realUrl = "javascript:void(0);";
	}
	logoAhref.href = realUrl;
	var adUrlDom = $($($(".akb_iframe")[0].contentDocument.body.children[0]).find("nav-box").context.getElementsByClassName("slid-box"))[0]; 
	
//	console.log("-------------");
//	console.log($(adUrlDom).children()[1]);
//	
//	var left = $(adUrlDom).children()[1].href = realUrl;
//	var right = $(adUrlDom).children()[2].href = realUrl;
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
	boxObj.html(),
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