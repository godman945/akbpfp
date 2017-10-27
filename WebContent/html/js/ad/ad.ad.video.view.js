﻿﻿﻿$(document).ready(function(){
	
	

});

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
	callBlockUpload();
	$.ajax({
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
		tableSorter();
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

function tableSorter(){
	
	$("#tableView").tablesorter({
		headers:{
			0:{sorter:false},
			3 : { sorter: 'fancyNumber' },
			4 : { sorter: 'fancyNumber' },
			5 : { sorter: 'fancyNumber' },
			6 : { sorter: 'rangesort' },
			7 : { sorter: 'rangesort' },
			8:{sorter:false},
			9:{sorter:false},
			10:{sorter:false}
			}
	});
}


//點擊預覽影片
function previewVideo(width,height,img,url) {
	callBlockUpload();
	$("#videoSize").text(width+" x " +height);
//	url = "http://showstg.pchome.com.tw/pfp/img/video/2017_10_20/adv_201710200001.mp4";
	var preview = 
	'<div id="previewVideoDiv" >'+
	'<div class="aduplodul_v">'+
	   '<div class="v_box">'+
	      '<div class=""><span id="videoSize">'+width+' x '+height+'  </span></div>'+
	      '<div id="preViewArea" class="v_preview box_a_style"><iframe class="akb_iframe" scrolling="no" frameborder="0" marginwidth="0" marginheight="0" vspace="0" hspace="0" id="pchome8044_ad_frame1" width="'+width+'" height="'+height+'" allowtransparency="true" allowfullscreen="true" src="adVideoPreview.html?adPreviewVideoURL='+encodeURIComponent(url)+'&adPreviewVideoBgImg='+img+'"></iframe></div>'+
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
    $('body').unblock();
}

function closePrew(){
	$("#preViewArea").empty();
}

function callBlockUpload(){
	  $('body').block({
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
	  $($('.blockUI')[1]).css('height',1024)
}
