﻿﻿$(document).ready(function(){
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
	
	$("#videoSize").text(width+" x " +height);
//	url = "http://showstg.pchome.com.tw/pfp/img/video/2017_10_20/adv_201710200001.mp4";
	var a = 
		'<iframe class="akb_iframe" scrolling="no" frameborder="0" marginwidth="0" marginheight="0" vspace="0" hspace="0" id="pchome8044_ad_frame1" width="'+width+'" height="'+height+'" allowtransparency="true" allowfullscreen="true" src="adVideoPreview.html?adPreviewVideoURL='+url+'&adPreviewVideoBgImg='+img+'"></iframe>';
	$("#preViewArea").append(a);
	
    $.fancybox(
    		$('#previewVideoDiv').html(),
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
}

function closePrew(){
	$("#preViewArea").empty();
}