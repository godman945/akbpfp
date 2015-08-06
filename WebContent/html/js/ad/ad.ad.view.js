﻿$(document).ready(function(){

	$.ajax({ 
		type: "GET", 
		url: "/pfp/html/js/ad/ad.ad.view.js", 
		dataType: "text", 
		cache:false, 
		ifModified :true 
		}); 
	
	
	$.ajax({ 
		type: "GET", 
		url: "/pfp/html/js/ad/ad.ad.view.js", 
		dataType: "text", 
		beforeSend :function(xmlHttp){ 
		xmlHttp.setRequestHeader("If-Modified-Since","0"); 
		xmlHttp.setRequestHeader("Cache-Control","no-cache"); 

		} 
		}); 
});


function findTableView(){

	var date = $("#IT_dateRange").val().split("~");
	var startDate = date[0];
	var endDate = date[1];
	var searchType = $("#searchType").val();
	var pageNo = $("#pageNo").val();
	var pageSize = $("#pageSize").val();
	var keyword = $("#keyword").val();
	var adGroupSeq = $("#adGroupSeq").val();
	
	$.ajax({
		url: "adAdViewTable.html",
		data:{
			 "adGroupSeq": adGroupSeq,
			 "startDate": startDate,
			 "endDate": endDate,
			 "searchType": searchType,
			 "keyword": keyword,
			 "pageNo": pageNo,
			 "pageSize": pageSize
		},
		type:"post",
		dataType:"html",
		success:function(response, status){
			$("#tableList").html(response);	
			page();
			 tableSorter();
		},
		error: function(xtl) {
			alert("系統繁忙，請稍後再試！");
		}
	});
	
}


function modifyAdStatus(status){

	var adAdSeq = [];
	
	for(var i=0;i<$("#tableView input:checkbox").length;i++){
		if($("#chkY_"+i).attr('checked')){			
			adAdSeq.push($("#chkY_"+i).val());
		}
	}

	if(adAdSeq.length > 0){
		
		$("#adAdSeq").val(adAdSeq.toString());
		$("#status").val(status);
		
		if(parseInt(status) == 10){			
			$.fancybox({
				'href'     :'closeAdAdMsg.html' 		                    
			});
		}
		else if(parseInt(status) == 9){
			$.fancybox({
				'href'     :'stopAdAdMsg.html' 		                    
			});
		}
		else{
			updateAdAdStatus();
		}
	}
	else{

		$.fancybox({
			'href'     :'adAdCheckboxMsg.html' 		                    
		});
	}
}

function updateAdAdStatus(){

	$("#tableForm").attr("action","updAdAdStatus.html");
	$("#tableForm").submit();
	
}

function closeAdAdStatus(adAdSeq, status){
	
	//$("#chkN_"+id).attr('checked', true);
	//$("#chkN_"+id).val(adActionSeq);
	$("#adAdSeq").val(adAdSeq.toString());
	$("#status").val(status);
	
	$.fancybox({
		'href'     :'closeAdAdMsg.html' 		                    
	});

}

function tableSorter(){
	
	$("#tableView").tablesorter({
		headers:{
			0:{sorter:false},
			3 : { sorter: 'fancyNumber' },
			4 : { sorter: 'fancyNumber' },
			5 : { sorter: 'fancyNumber' },
			6 : { sorter: 'fancyNumber' },
			7 : { sorter: 'fancyNumber' },
			9:{sorter:false},
			10:{sorter:false},
			11:{sorter:false}
			}
	});
}

function preview(img) {
    $.fancybox({
        'href':img,
        'autoSize':true,
        'autoHeight':true,
        'autoScale':true,
        'transitionIn':'none',
        'transitionOut':'none',
        'padding':0,
        'overlayOpacity':.75,
        'overlayColor':'#fff',
        'scrolling':'no'
    });
}

//檢查網址blur事件
$("#adLinkURL").blur(function() {
	if($("#adLinkURL").val() != "show.pchome.com.tw"){
		urlCheck("adLinkURL",$("#adLinkURL").val());
	}else{
		$("#chkLinkURL").css("color","red");
		$("#chkLinkURL").text("請輸入廣告連結網址");
	}
});

//檢查網址是否有效
function urlCheck(urlType,adUrl){
	var adUrlHint = urlType != "adShowURL" ? "chkLinkURL" : "chkShowURL";
	if(adUrl != "" && urlType.indexOf("show.pchome.com.tw") < 0) {
		if(ValidURL(adUrl)) {
			$("#"+adUrlHint).text("網址檢查中");
			$.ajax({
				type: "POST",
				url: "checkAdUrl.html",
				data: { url: adUrl}
			}).done(function( msg ) {
				if(msg == "false") {
					$("#chkShowURL").css("color","red");
					$("#"+adUrlHint).css("color","red");
					if(adUrlHint == "chkLinkURL"){
						$("#"+adUrlHint).text("請輸入正確的廣告連結網址");
					}else if(adUrlHint == "chkShowURL"){
						$("#"+adUrlHint).text("請輸入正確的廣告顯示網址");
					}
					
					if(urlType == "adShowURL"){
						ShowUrl = false;
					}else{
						LinkUrl = false;
					}
				} else {
					$("#"+urlType).css("color","");
					$("#"+adUrlHint).css("color","green");
					$("#"+adUrlHint).text("網址確認正確");
					if(urlType == "adShowURL"){
						ShowUrl = true;
					}else{
						LinkUrl = true;
					}
				}
			});
		}else {
			if(urlType == 'adLinkURL'){
				if($("#adLinkURL").length > 0){
					$('#chkLinkURL').css("color","red");
					$("#"+adUrlHint).text("請輸入正確廣告連結網址.");
				}
			}
			if(urlType == 'adShowURL'){
				if($("#adShowURL").length > 0){
					$('#chkShowURL').css("color","red");
					$("#"+adUrlHint).text("請輸入正確廣告顯示網址.");
				}
			}
		}
	}else{
		if(urlType == 'adLinkURL'){
			if($("#adLinkURL").val().length > 0){
				$('#chkLinkURL').css("color","red");
				$("#"+adUrlHint).text("請輸入正確廣告連結網址.");
				return false;
			}else{
				$('#chkLinkURL').css("color","red");
				$("#"+adUrlHint).text("請輸入廣告連結網址.");
				return false;
			}
			
		}
		if(urlType == 'adShowURL'){
			$("#"+adUrlHint).text("請輸入正確廣告顯示網址.");
			return false;
		}else{
			$("#"+adUrlHint).text("請輸入廣告顯示網址.");
			return false;
		}
	}
	
	//連結網址字數檢查
	chkWord($('#adShowURL'), "spanAdShowURL");
	chkWord($('#adLinkURL'), "spanAdLinkURL");
}