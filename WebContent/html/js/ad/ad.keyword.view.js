$(document).ready(function(){

    
});


function findTableView(){
	
	var date = $("#IT_dateRange").val().split("~");
	var startDate = date[0];
	var endDate = date[1];
	var pageNo = $("#pageNo").val();
	var pageSize = $("#pageSize").val();
	var keyword = $("#keyword").val();
	var adGroupSeq = $("#adGroupSeq").val();
	
	//alert(" pageSize = "+pageSize);
	
	$.ajax({
		url: "adKeywordViewTable.html",
		data:{
			 "adGroupSeq": adGroupSeq,
			 "startDate": startDate,
			 "endDate": endDate,
			 "keyword": keyword,
			 "pageNo": pageNo,
			 "pageSize": pageSize
		},
		type:"post",
		dataType:"html",
		success:function(response, status){				
			$("#tableList").html(response);			
			page();
			loadValidate();
			tableSorter();
		},
		error: function(xtl) {
			alert("系統繁忙，請稍後再試！");
		}
	});
	
}

function modifyAdStatus(status){

	var adKeywordSeq = [];
	
	for(var i=0;i<$("#tableView input:checkbox").length;i++){
		if($("#chkY_"+i).attr('checked')){			
			adKeywordSeq.push($("#chkY_"+i).val());
		}
	}
	
	if(adKeywordSeq.length > 0){
		
		$("#adKeywordSeq").val(adKeywordSeq.toString());
		$("#status").val(status);
		
		if(parseInt(status) == 10){			
			$.fancybox({
				'href'     :'closeAdKeywordMsg.html' 		                    
			});
		}
		else if(parseInt(status) == 9){
			$.fancybox({
				'href'     :'stopAdKeywordMsg.html' 		                    
			});
		}
		else{
			updateAdKeywordStatus();
		}
		
	}
	else{

		$.fancybox({
			'href'     :'adKeywordCheckboxMsg.html' 		                    
		});
	}
	
}

function updateAdKeywordStatus(){

	$("#tableForm").attr("action","updAdKeywordStatus.html");
	$("#tableForm").submit();
	
}

function closeAdKeywordStatus(adKeywordSeq, status){
	
	//$("#chkN_"+id).attr('checked', true);
	//$("#chkN_"+id).val(adActionSeq);
	$("#adKeywordSeq").val(adKeywordSeq);
	$("#status").val(status);
	
	$.fancybox({
		'href'     :'closeAdKeywordMsg.html' 		                    
	});

}

function updateKeywordSuggest(seq){
	
	if($("#userPrice_"+seq).valid() == 1){
		
		$.ajax({
			url: "updAdKeywordPrice.html",
			data:{
				 "adKeywordSeq": seq,
				 "userPrice": $("#userPrice_"+seq).val()
			},
			type:"post",
			dataType:"html",
			success:function(response, status){				
				$("<br id='br'/><label generated='true' class='labsu'>搜尋廣告出價更新成功</label>").insertAfter("#bt_"+seq);
			},
			error: function(xtl) {
				alert("系統繁忙，請稍後再試！");
			}
		});
	}
	
}

function loadValidate(){
	$("#tableForm").validate({  
		unhighlight:function(element, errorClass, validClass){
			$("#br").remove();
			$("label").remove(".labsu");
	    },
		highlight:function(element, errorClass, validClass){
			$("#br").remove();
			$("label").remove(".labsu");
	    },
		errorPlacement:function(error, element){
			error.insertAfter(element.next());
		}
	});
}

function adExcludeKeyword(seq){
	
	$.fancybox({
		'href'     :'adExcludeKeyword.html?adGroupSeq='+seq		                    
	});
}

function tableSorter(){
	
	$("#tableView").tablesorter({
		headers:{
			0:{sorter:false},
			3 : { sorter: 'inputs' },
			4 : { sorter: 'fancyNumber' },
			5 : { sorter: 'fancyNumber' },
			6 : { sorter: 'fancyNumber' },
			7 : { sorter: 'fancyNumber' },
			8 : { sorter: 'fancyNumber' },
			9 : { sorter: 'fancyNumber' },
			12:{sorter:false},
			13:{sorter:false}
			}
	});
}

