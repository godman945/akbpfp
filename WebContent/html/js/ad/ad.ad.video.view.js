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
	
	//$("#chkN_"+id).attr('checked', true);
	//$("#chkN_"+id).val(adActionSeq);
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

function preViewHtml5(width,height,imgSrc,realUrl){
	
	 $.fancybox(
			 '<div style="position:absolute;z-index:10;border:0px;background:none;width:' + width + 'px;height:' + height + 'px;">' + 
			 '<a href="' + realUrl + '" target="_blank" style="display:block;width:' + width + 'px;height:' + height + 'px;"><img src="html/img/blank.gif" style="width:' + width + 'px;height:' + height + 'px;border:0px;"></a>'
			 + '</div>'
			 + '<iframe src="' + imgSrc + '" width="' + width + '" height="' + height + '"  allowtransparency="true" frameborder="0" scrolling="no" ></iframe>',
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
	    			'scrolling'			: 'no'
	    		}
	    );
}