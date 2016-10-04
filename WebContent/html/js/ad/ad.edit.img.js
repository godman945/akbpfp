//1.建立上傳畫面
//2.建立點擊預覽
//3.送出
var imgSeq = "";
var jsonObj = null;
var pages = -1;
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
	
	//廣告被下架拒刊後不能做修改
	var adStatus = $("#adStatus").val();
	if(adStatus == "6"){
		$("#addAdKeyword").hide();
		$("#sugkw").hide();
		$("#addAdExcludeKeyword").hide();
		$("#batchAddAdKeyword").hide();
		$(":input").not("#cancel").attr("disabled","disabled");
	}
	
	//檢查網址字數
	chkWord($("#adLinkURL"), $("#spanAdLinkURL"));
	
	if($.browser.msie){
		if(parseInt($.browser.version) < 10){
			$("#alex").css("display","none");
			$("#notSuppotDiv").css("display","");
			$("#submitBtn").attr("disabled", true);
		}
	}
	
	//連結網址鍵盤件鍵檢查
	$('#adLinkURL').bind('keyup', function() {
		chkWord($("#adLinkURL"), $("#spanAdLinkURL"));
	});
	
	//檢查網址blur事件
	$("#adLinkURL").blur(function() {
		chkUrl($("#adLinkURL"), $("#chkLinkURL"));
	});
	
	//清除關鍵字比對方式提示
	$("#adKeywordOpen, #adKeywordPhraseOpen, #adKeywordPrecisionOpen").click(function(){
		$('#chkAdKeywordOpen').text("");
	});
});

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

//取消
function cancerSubmit(){
	$("#formImg")[0].reset();
	window.history.go(pages);
	//$(location).attr( 'href' , 'adGroupView.html');
}

//檢查網址是否有效
function chkUrl(valObj, msgObj){
	var val = valObj.val();
	
	msgObj.css("color","");
	msgObj.text("");
	
	if ((val == "")) {
		/* || (val.indexOf("show.pchome.com.tw") == 0)) {*/
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

//點擊預覽
function preViewImg(objectUrl,width,height){
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

//關閉預覽
function closePrew(){
	$("#preDiv").empty();
}

//存檔
function multipartImgUuploadSubmit(){
	$("#chkKeyword").html("");
	$("#chkFile").html("");
	
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
		
		//檢查關建字比對方式是否有被勾選
		if(keyWordArray.length > 0){
			if(!$("#adKeywordOpen").attr('checked') && !$("#adKeywordPhraseOpen").attr('checked') && !$("#adKeywordPrecisionOpen").attr('checked')){
				$('#chkAdKeywordOpen').text("請勾選關鍵字比對方式");
				location.href="#chkAdKeywordOpen";
				return false;
			}
		}
	}
	
	var excludeKeywordULArray = [];
	$.each($("#ExcludeKeywordUL li"), function( index, obj ) {
		excludeKeywordULArray.push($(obj).text());
	});
	
	if (!chkUrl($("#adLinkURL"), $("#chkLinkURL"))) {
		$("#adLinkURL").focus();
		return false;
	}
	
	var adStatus = $("#adStatus").val();
	var alertMsg = "";
	if(adStatus == 3) {
		alertMsg = "請再次確認您的廣告是否符合刊登規範\n\n提醒您，您的廣告將在3工作天(周一到周五)審核完成(不含例假日)，並於廣告審核完成後開始播放";
	} else {
		alertMsg = "PChome將會審核您編輯過的廣告。\n\n提醒您，您的廣告將在3工作天(周一到周五)審核完成(不含例假日)，並於廣告審核完成後開始播放";
	}
	if(confirm(alertMsg)) {
		callBlock();
		$.ajax({
			url : "adEditImgSaveAjax.html",
			type : "POST",
			dataType:'json',
			data : {
				"adSeq" : $("#adSeq").val(),
				"adStyle" : "IMG",
				"adDetailSeq" : $("#adDetailSeq").val(),
				"adGroupSeq": $("#adGroupSeq").val(),
				"adLinkURL" : $("#adLinkURL").val(),
				"keywords" : JSON.stringify(keyWordArray),
				"excludeKeywords" : JSON.stringify(excludeKeywordULArray),
				"adKeywordOpen" : $("#adKeywordOpen").attr("checked"),
				"adKeywordPhraseOpen" : $("#adKeywordPhraseOpen").attr("checked"),
				"adKeywordPrecisionOpen" : $("#adKeywordPrecisionOpen").attr("checked")
			},
			success : function(respone) {
				$('body').unblock();
				if(respone == "success"){
					$(location).attr( 'href' , 'adAdView.html?adGroupSeq='+$("#adGroupSeq").val());
				} else {
					alert(respone);
				}
			}
		});
	}
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