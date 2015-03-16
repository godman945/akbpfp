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
	
	$.ajax({
		url: "adActionViewTable.html",
		data:{
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
			
			loadValidate();
			
			tableSorter();

		},
		error: function(xtl) {
			alert("系統繁忙，請稍後再試！");
		}
	});
	
}

function modifyAdStatus(status){
	
	var adActionSeq = [];
	
	for(var i=0;i<$("#tableView input:checkbox").length;i++){
		if($("#chkY_"+i).attr('checked')){			
			adActionSeq.push($("#chkY_"+i).val());
		}
	}

	if(adActionSeq.length > 0){

		$("#adActionSeq").val(adActionSeq.toString());
		$("#status").val(status);
		
		if(parseInt(status) == 10){			
			$.fancybox({
				'href'     :'closeAdActionMsg.html' 		                    
			});
		}
		else if(parseInt(status) == 9){
			$.fancybox({
				'href'     :'stopAdActionMsg.html' 		                    
			});
		}else{
			updateAdActionStatus();
		}
		
	}else{

		$.fancybox({
			'href'     :'adActionCheckboxMsg.html' 		                    
		});
	}
	
}

function updateAdActionStatus(){

	$("#tableForm").attr("action","updAdActionStatus.html");
	$("#tableForm").submit();
	
}

function closeAdActionStatus(adActionSeq, status){
	
	//$("#chkN_"+id).attr('checked', true);
	//$("#chkN_"+id).val(adActionSeq);
	$("#adActionSeq").val(adActionSeq);
	$("#status").val(status);
	
	$.fancybox({
		'href'     :'closeAdActionMsg.html' 		                    
	});

}

function updAdActionMax(seq){
	
	//取得驗證回傳值
	if($("#max_"+seq).valid() == 1){		
		$.ajax({
			url: "updAdActionMax.html",
			data:{
				 "adActionSeq": seq,
				 "adActionMax": $("#max_"+seq).val()
			},
			type:"post",
			dataType:"html",
			success:function(response, status){
				$("<br id='br'/><label generated='true' class='labsu'>每日花費更新成功</label>").insertAfter("#bt_"+seq);
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

function tableSorter(){
	
	$("#tableView").tablesorter({
		headers:{
			0 : { sorter: false},
			3 : { sorter: 'shortDate' },
			4 : { sorter: 'inputs' },
			5 : { sorter: 'fancyNumber' },
			6 : { sorter: 'fancyNumber' },
			7 : { sorter: 'fancyNumber' },
			8 : { sorter: 'fancyNumber' },
			9 : { sorter: 'fancyNumber' },
			11: { sorter: false}
			}
	});
}