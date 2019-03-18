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
				'showCloseButton' :false,
				'href'     :'closeAdKeywordMsg.html' 		                    
			});
		}
		else if(parseInt(status) == 9){
			$.fancybox({
				'showCloseButton' :false,
				'href'     :'stopAdKeywordMsg.html' 		                    
			});
		}
		else{
			updateAdKeywordStatus();
		}
		
	}
	else{

		$.fancybox({
			'showCloseButton' :false,
			'href'     :'adKeywordCheckboxMsg.html' 		                    
		});
	}
	
}

function updateAdKeywordStatus(){
	$("[id*=userPrice_widely_]").removeAttr("min");//先移除min不然會檢查
	$("#tableForm").attr("action","updAdKeywordStatus.html");
	$("#tableForm").submit();
	
}

function closeAdKeywordStatus(adKeywordSeq, status){
	
	//$("#chkN_"+id).attr('checked', true);
	//$("#chkN_"+id).val(adActionSeq);
	$("#adKeywordSeq").val(adKeywordSeq);
	$("#status").val(status);
	
	$.fancybox({
		'showCloseButton' :false,
		'href'     :'closeAdKeywordMsg.html' 		                    
	});

}

function updateKeywordSuggest(seq,keywordType){
	
	if($("#userPrice_" + keywordType + "_" + seq).valid() == 1){
		
		$.ajax({
			url: "updAdKeywordPrice.html",
			data:{
				 "adKeywordSeq": seq,
				 "userPrice": $("#userPrice_" + keywordType + "_" + seq).val(),
				 "adKeywordType": keywordType
			},
			type:"post",
			dataType:"html",
			success:function(response, status){				
				$("<br id='br'/><label generated='true' class='labsu'>搜尋廣告出價更新成功</label>").insertAfter("#bt_" + keywordType + "_" + seq);
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
		'showCloseButton' :false,
		'href'     :'adExcludeKeyword.html?adGroupSeq='+seq		                    
	});
}

function tableSorter(){
	
	$("#tableView").tablesorter({
		headers:{
			0 : {sorter:false},
			3 : {sorter:false},
			4 : {sorter:false},
			5 : {sorter:false},
			6 : {sorter:false},
			7 : {sorter:false},
			8 : {sorter:false},
			9 : {sorter:false},
			10 : {sorter:false},
			11 : {sorter: 'inputs' },
			12 : {sorter:false},
			13 : {sorter: 'inputs' },
			14 : {sorter:false},
			15 : {sorter: 'inputs' },
			16 : { sorter: 'fancyNumber' },
			17 : { sorter: 'fancyNumber' },
			18 : { sorter: 'fancyNumber' },
			19 : { sorter: 'fancyNumber' },
			20 : { sorter: 'fancyNumber' },
			21 : { sorter: 'fancyNumber' },
			22 : { sorter: 'fancyNumber' },
			23 : { sorter: 'fancyNumber' },
			24 : { sorter: 'fancyNumber' },
			25 : { sorter: 'fancyNumber' },
			26 : { sorter: 'fancyNumber' },
			27 : { sorter: 'fancyNumber' },
			28 : { sorter: 'rangesort' },
			29 : { sorter: 'rangesort' },
			30 : { sorter: 'rangesort' },
			31 : { sorter: 'rangesort' },
			32 : { sorter: 'rangesort' },
			33 : { sorter: 'rangesort' },
			34 : { sorter: 'rangesort' },
			35 : { sorter: 'rangesort' },
			36 : { sorter: 'fancyNumber' },
			37 : { sorter: 'fancyNumber' },
			38 : { sorter: 'fancyNumber' }
			}
	});
}

//比對方式改變判斷
function updateKeywordOpen(seq,suggestPrice,keywordType){

	$("#changTypeSeq").val(seq);
	$("#changTypePrice").val(suggestPrice);
	$("#changType").val(keywordType);
	
	
	if((!$("#userOpen_widely_" + seq).attr("checked")) &&
			(!$("#userOpen_phrase_" + seq).attr("checked")) &&
			(!$("#userOpen_precision_" + seq).attr("checked"))){
		$.fancybox({
			'showCloseButton' :false,
			'href'     :'adKeywordCloseTypeMsg.html' 		                    
		});
		$("#userOpen_" + keywordType + "_" + seq).attr("checked",true);
	} else {
		if($("#userOpen_" + keywordType + "_" + seq).attr("checked")){
			updateKeywordOpenData("on");
		} else {
			$.fancybox({
				'showCloseButton' :false,
				'href'     :'adKeywordChangeTypeMsg.html' 		                    
			});
			
		}
	}
}

//更新比對方式資料
function updateKeywordOpenData(changTag){
	var seq = $("#changTypeSeq").val();
	var suggestPrice = 0;
	var keywordType = $("#changType").val();
	if(changTag == "on"){
		suggestPrice = $("#changTypePrice").val();	
		$("#userPrice_" + keywordType + "_" + seq).removeAttr("disabled");
		$("#bt_" + keywordType + "_" + seq).removeAttr("disabled");
	} else {
		$("#userPrice_" + keywordType + "_" + seq).attr("disabled","disabled");
		$("#bt_" + keywordType + "_" + seq).attr("disabled","disabled");
	}
	
	$.ajax({
		url: "updAdKeywordOpen.html",
		data:{
			 "adKeywordSeq": seq,
			 "suggestPrice": suggestPrice,
			 "adKeywordType": keywordType,
			 "adKeywordOpen": $("#userOpen_widely_" + seq).attr("checked"),
			 "adKeywordPhraseOpen" : $("#userOpen_phrase_" + seq).attr("checked"),
			 "adKeywordPrecisionOpen" : $("#userOpen_precision_" + seq).attr("checked")
		},
		type:"post",
		dataType:"html",
		success:function(response, status){				
			$("#userPrice_" + keywordType + "_" + seq).val(suggestPrice);
		},
		error: function(xtl) {
			alert("系統繁忙，請稍後再試！");
		}
	});
}

//不更新比對方式時還原checkbox狀態
function notChangType(){
	var seq = $("#changTypeSeq").val();
	var keywordType = $("#changType").val();
	$("#userOpen_" + keywordType + "_" + seq).attr("checked",true);
}

function toggleTd(tdClass){
	var number = $("." + tdClass + "Th").attr("colspan");
	if(number == 1){
		$("." + tdClass + "Button").val("隱藏");
		$("." + tdClass + "Th").attr("colspan","4");
	} else {
		$("." + tdClass + "Button").val("展開");
		$("." + tdClass + "Th").attr("colspan","1");
	}
	
	$("." + tdClass).toggle();
}