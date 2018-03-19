$(document).ready(function(){
	$("#searchType").change(function(){
		findTableView();
	});
	
	dealWithAddAdMenu(); //處理廣告刊登選擇介面部分
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
				'showCloseButton' :false,
				'href'     :'closeAdActionMsg.html' 		                    
			});
		}else if(parseInt(status) == 9){
			$.fancybox({
				'showCloseButton' :false,
				'href'     :'stopAdActionMsg.html' 		                    
			});
		}else{
			updateAdActionStatus();
		}
		
	}else{

		$.fancybox({
			'showCloseButton' :false,
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
		'showCloseButton' :false,
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
			5 : { sorter: 'shortDate' },
			6 : { sorter: 'inputs' },
			7 : { sorter: 'fancyNumber' },
			8 : { sorter: 'fancyNumber' },
			9 : { sorter: 'fancyNumber' },
			10 : { sorter: 'rangesort' },
			11 : { sorter: 'rangesort' },
			12 : { sorter: 'rangesort' },
			13 : { sorter: 'rangesort' },
			14: { sorter: false}
			}
	});
}

// 處理廣告刊登選擇介面部分
function dealWithAddAdMenu() {

	// 調整介面位置在新增廣告按鈕下方
	var addAdImg = $('.addAdImg').offset();
	$(".menu-option-addad").css({
		top : addAdImg.top + $('.addAdImg').height(),
		left : addAdImg.left
	});
	
	// 鼠標移到新增廣告圖片及選取介面時，介面都為顯示，移出則隱藏
	$('.addAdImg, .menu-option-addad').mouseenter(function(){
		$(".menu-option-addad").show();
	}).mouseleave(function(){
		$(".menu-option-addad").hide();
	});
	
	// 關閉選取介面
	$('.close-menu').click(function() {
		$(".menu-option-addad").hide();
	});

	//一般廣告刊登導頁
	$('.adAdd').click(function() {
		window.location = "adActionAdd.html";
	});
	
	//快速網址刊登導頁
	$('.fastURLAdAdd').click(function() {
//		alert('功能待開放。');
		window.location = "adFastPublishAdd.html";
	});
	
}