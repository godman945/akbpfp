$(document).ready(function(){

	ready();
    
    $("#boardType").change(function(){
    	boardSearch();
    });
	
});


function ready(){
	
	boardSearch();
}

function boardSearch(){
	
	var boardType = $("#boardType").val();
	var pageNo = $("#pageNo").val();
	var pageSize = $("#pageSize").val();

    $("#boardSearchType").block({
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
	
	$.ajax({
		url: "searchBoard.html",
		data: {
			"boardType": boardType,
			"pageNo": pageNo,
			"pageSize": pageSize
		},
		type: "post",
		dataType: "html",
		success: function(response, status) {			
			$("#boardTable").html(response);
			page();
			
			$("#listTable").tablesorter();
		},
		error: function(xtl) {
			errorDialogBtn("錯誤訊息","系統繁忙，請稍後再試！");
		}
	});
	
	$("#boardSearchType").unblock();
}


//分頁功能
function wantSearch(pageNo) {
    if (pageNo != null) {
        $("#pageNo").val(pageNo);
    }
    boardSearch();
    
}
