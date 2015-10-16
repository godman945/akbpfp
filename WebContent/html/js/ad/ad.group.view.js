$(document).ready(function(){

    
});

function findTableView(){

	var date = $("#IT_dateRange").val().split("~");
	var startDate = date[0];
	var endDate = date[1];
	var searchType = $("#searchType").val();
	var pageNo = $("#pageNo").val();
	var pageSize = $("#pageSize").val();
	var keyword = $("#keyword").val();
	var adActionSeq = $("#adActionSeq").val();
	var adType = $("#adType").val();
	$.ajax({
		url: "adGroupViewTable.html",
		data:{
			 "adActionSeq": adActionSeq,
			 "startDate": startDate,
			 "endDate": endDate,
			 "searchType": searchType,
			 "keyword": keyword,
			 "pageNo": pageNo,
			 "pageSize": pageSize,
			 "groupMaxPrice" : $("#groupMaxPrice").val(),
			 "adType" : adType
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

	var adGroupSeq = [];
	
	for(var i=0;i<$("#tableView input:checkbox").length;i++){
		if($("#chkY_"+i).attr('checked')){			
			adGroupSeq.push($("#chkY_"+i).val());
		}
	}

	if(adGroupSeq.length > 0){
		
		$("#adGroupSeq").val(adGroupSeq.toString());
		$("#status").val(status);
		
		if(parseInt(status) == 10){	
			
			$.fancybox({
				'showCloseButton' :false,
				'href'     :'closeAdGroupMsg.html' 		                    
			});
		}
		else if(parseInt(status) == 9){
			$.fancybox({
				'showCloseButton' :false,
				'href'     :'stopAdGroupMsg.html' 		                    
			});
		}
		else{
			updateAdGroupStatus();
		}		
	}
	else{

		$.fancybox({
			'showCloseButton' :false,
			'href'     :'adGroupCheckboxMsg.html' 		                    
		});
	}
	
}

function updateAdGroupStatus(){

	$("#tableForm").attr("action","updAdGroupStatus.html");
	$("#tableForm").submit();
	
}

function closeAdGroupStatus(adGroupSeq, status){
	
	$("#adGroupSeq").val(adGroupSeq);
	$("#status").val(status);
	
	$.fancybox({
		'showCloseButton' :false,
		'href'     :'closeAdGroupMsg.html' 		                    
	});

}


function modifyChannelPrice(seq,price){
	
	$("#adGroupSeq").val(seq);
	
	$.fancybox({
		'href'     :'modifyAdGroupChannelPriceMsg.html?adGroupSeq='+seq+'&userprice='+price		                    
	});
	
	
}

function modifySearchPrice(seq,type){

	$("#adGroupSeq").val(seq);
	$("#searchPriceType").val(type);
	
	$.fancybox({
		'href'     :'modifyAdGroupSearchPriceMsg.html?adGroupSeq='+seq		                    
	});
		
}

function tableSorter(){
	var adType = $("#adType").val();
	
	if(adType == "0"){
		$("#tableView").tablesorter({
			headers:{
				0:{sorter:false},
				5 : { sorter: 'fancyNumber' },
				6 : { sorter: 'fancyNumber' },
				7 : { sorter: 'fancyNumber' },
				8 : { sorter: 'rangesort' },
				9 : { sorter: 'rangesort' },
				11:{sorter:false}
				}
		});
	} else {
		$("#tableView").tablesorter({
			headers:{
				0:{sorter:false},
				4 : { sorter: 'fancyNumber' },
				5 : { sorter: 'fancyNumber' },
				6 : { sorter: 'fancyNumber' },
				7 : { sorter: 'rangesort' },
				8 : { sorter: 'rangesort' },
				10:{sorter:false}
			}
		});
	}
}
	