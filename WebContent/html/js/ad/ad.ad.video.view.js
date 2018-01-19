


//document.addEventListener('DOMContentLoaded', function() {
//	$.unblockUI();
//}, false);
 $(window).load(function() {
	 alert('SS');
	 $.unblockUI();
 });
//$('document').onload(function() {
//	$.unblockUI();
//});


﻿﻿﻿$(document).ready(function(){
	callBlockUpload();
//	var a = document.getElementsByTagName("iframe");
//	for(var i = 0; i< a.length; i++){
//		
//		
//		 a[i].onload = function(){
//			 $.unblockUI();
//		 };
//		
//		
//		
//		
//		
//		
////		a[i].attachEvent("onload", function(){
////	        alert("Local iframe is now loaded.");
////	    });
//		
////		if (a[i].attachEvent){
////			
//////			console.log('AAAAA');
////		    a[i].attachEvent("onload", function(){
////		    	
//////		        alert("Local iframe is now loaded.");
////		    });
////		} else {
//////			console.log('CCC');
////		    a[i].onload = function(){
////		    	$.unblockUI();
//////		        alert("Local iframe is now loaded.");
////		    };
////		}
////		console.log(a[i]);
//	}
	
	
	
//	window.onload=function(){
//		$.unblockUI();
//	}
	
	$("#tableView").tablesorter({
		headers: {
			0: { sorter: false },
			10: { sorter: false },
		},
		initialized: function(table) {
			$("#tableView").on('sortStart', function() {
				 blockBody();
				 resetIframeSize();
		    }),
		    $("#tableView").on('sortEnd', function() {
		    	setTimeout(function(){  $.unblockUI(); }, 1000);
				resizeIframeInfo();
		    });
		 }
	});
	
	
	$(function() {
	}).bind("sortStart",function(e, t){
		 blockBody();
		 resetIframeSize();
	 }).bind("sortEnd",function(e, t){
		 setTimeout(function(){  
			 $.unblockUI(); 
			 resizeIframeInfo();
		 }, 1000);
		 
	 });
});


/**重新恢復原本iframe尺寸*/
function resetIframeSize(){
	$("#tableView tbody tr").each(function(index,obj){
		var iframe = $(obj).children()[1].querySelector('.akb_iframe');
		var td = $(obj).children()[2];
		var div = td.querySelector('.ad_size');
		if(div != null){
			var size = $(div).text().replace('尺寸 ','');
			var sizeArray = size.split(' x ');
			var width = sizeArray[0];
			var height = sizeArray[1];
			iframe.width = width;
			iframe.height = height;
		}
	});
}

/**重新計算明細高度*/
function resizeIframeInfo(){
	$("#excerptTable tbody tr").each(function(index,obj){
		var td = $(obj).children()[1];
		var iframe = td.querySelector('.akb_iframe');
		var adratio = iframe.height / iframe.width;
		var	adh = 250 * adratio;
		var infoDiv = $($(td).children()[0]).children()[1];
		$(infoDiv).css('margin-top',(adh / 2) - 45+'px');
	});
}

function blockBody(){
	$('body').block({
		message: "",
		css: {
			padding: 0,
			margin: 0,
			width: '100%',
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


//default查詢廣告明細資料
function findTableView(){
	
	var date = $("#IT_dateRange").val().split("~");
	var startDate = date[0];
	var endDate = date[1];
	var searchType = $("#searchType").val();
	var pageNo = $("#pageNo").val();
	var pageSize = $("#pageSize").val();
	var keyword = $("#keyword").val();
	var adGroupSeq = $("#adGroupSeq").val();
	var adType = $("#adType").val();
	var adOperatingRule = $("#adOperatingRule").val();
//	callBlockUpload();
	$.ajax({
		async: false,
		url: "adAdVideoViewTable.html",
		data:{
			 "adGroupSeq": adGroupSeq,
			 "startDate": startDate,
			 "endDate": endDate,
			 "searchType": searchType,
			 "keyword": keyword,
			 "pageNo": pageNo,
			 "pageSize": pageSize,
			 "adType": adType,
			 "adOperatingRule":adOperatingRule
		},
		type:"post",
		dataType:"html",
		success:function(response, status){
			
		},
		error: function(xtl) {
			$('body').unblock();
			alert("系統繁忙，請稍後再試！");
		}
	}).done(function (response) {
		$('body').unblock();
		$("#tableList").html(response);	
		page();
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
				'showCloseButton' :false,
				'href'     :'closeAdAdMsg.html' 		                    
			});
		}
		else if(parseInt(status) == 9){
			$.fancybox({
				'showCloseButton' :false,
				'href'     :'stopAdAdMsg.html' 		                    
			});
		}
		else{
			updateAdAdStatus();
		}
	}
	else{

		$.fancybox({
			'showCloseButton' :false,
			'href'     :'adAdCheckboxMsg.html' 		                    
		});
	}
}

function updateAdAdStatus(){

	$("#tableForm").attr("action","updAdAdStatus.html");
	$("#tableForm").submit();
	
}

function closeAdAdStatus(adAdSeq, status){
	$("#adAdSeq").val(adAdSeq.toString());
	$("#status").val(status);
	
	$.fancybox({
		'showCloseButton' :false,
		'href'     :'closeAdAdMsg.html' 		                    
	});
}

//function tableSorter(){
//	
//	$("#tableView").tablesorter({
//		 theme: 'blue',
//		headers:{
//			0:{sorter:false},
//			3 : { sorter: 'fancyNumber' },
//			4 : { sorter: 'fancyNumber' },
//			5 : { sorter: 'fancyNumber' },
//			6 : { sorter: 'rangesort' },
//			7 : { sorter: 'rangesort' },
////			8:{sorter:false},
//			9:{sorter:'string'},
//			10:{sorter:false}
//			}
//	});
//}


//點擊預覽影片
function previewVideo(width,height,img,url) {
	$("#videoSize").text(width+" x " +height);
//	url = "http://showstg.pchome.com.tw/pfp/img/video/2017_10_20/adv_201710200001.mp4";
	var preview = 
	'<div id="previewVideoDiv" >'+
	'<div class="aduplodul_v">'+
	   '<div class="v_box">'+
	      '<div class=""><span id="videoSize">'+width+' x '+height+'  </span></div>'+
	      '<div id="preViewArea" class="v_preview box_a_style"><iframe class="akb_iframe" scrolling="no" frameborder="0" marginwidth="0" marginheight="0" vspace="0" hspace="0" id="pchome8044_ad_frame1" width="'+width+'" height="'+height+'" allowtransparency="true" allowfullscreen="true" src="adVideoPreview.html?adPreviewVideoURL='+encodeURIComponent(url)+'&adPreviewVideoBgImg='+img+'&resize=true"></iframe></div>'+
	   '</div>'+
	'</div>'+
	'</div>';
    $.fancybox(
    		preview,
    		{
    			
    			'autoDimensions'	: false,
    			'width'         	: 250,
    			'height'        	: 550,
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
    $("#fancybox-wrap").css('height',500);
}

function closePrew(){
	$("#preViewArea").empty();
}

//鎖住UI
function callBlockUpload(){
	  $.blockUI({
		  message: "<b><font size=5>影片載入中...</font></b><img src='html/img/LoadingWait.gif' />",
	        css: {
	        	  border: '3px solid #aaa',
			      padding: '15px',
			      backgroundColor: '#fff',
			       '-webkit-border-radius': '10px',
			       '-moz-border-radius': '10px',
			        opacity: .9,
			        textAlign:      'center',
			        cursor:         'wait',
			        top:'50%'
	        }
	    });
}
//alert('FF1');
document.addEventListener('DOMContentLoaded', function () {
//	alert('FF');
}, false);