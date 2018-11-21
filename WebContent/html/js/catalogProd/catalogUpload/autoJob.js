// 讀取遮罩預設值
var maskingConfig = {
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
};

var url = "";
$(document).ready(function() {
	
	if ($("#uploadContent").val()) { 
		// 一進入先判斷此目錄是否已經有設定網址，有則調整預設畫面
		$("div.urlupload-box").removeClass('select');
		$("div.urlupload-box.datatype").addClass('select');
		$("#dataTypeFileName").attr("data-fileName", url.substring(url.lastIndexOf("/") +1));
		$("#dataTypeContent").html("網址：<em data-fileUrl=\"" + url + "\"></em>");
	}
	
	// 檢查輸入網址
	$("#autoJobURL, #errAutoJobURL").blur(function(){
		url = $(this).val();
		
		// 檢查輸入網址是否正確
//		if (url.substr(url.length - 4,url.length).toLowerCase() != ".csv") {
//			alert("網址請輸入規定的格式。\n\n http://******.csv、https://******.csv");
//			return false;
//		}
		
		//顯示遮罩
		$('#loadingWaitBlock').block(maskingConfig);
		
		$.ajax({
		    type: "post",
		    dataType: "json",
		    url: "processCheckJobURL.html",
		    data: {
		    	"jobURL" : url
		    },
		    timeout: 30000,
		    error: function(xhr){
		    	$('#loadingWaitBlock').unblock();
		        alert('Ajax request 發生錯誤');
		    },
		    success: function(response, status){
		    	$("div.urlupload-box").removeClass('select');
		    	
				if (response.status == "ERROR") {
					$("div.urlupload-box.failure").addClass('select');
					$("#errAutoJobURL").val(url);
					$("#errContent").html("<em class=\"icon-error\"></em>請輸入正確網址。");
				} else {
					$("div.urlupload-box.success").addClass('select');
					$("#successFileName").attr("data-fileName", response.fileName);
					$("#successContent").html("網址：<em data-fileUrl=\"" + url + "\"></em>");
				}
				
				$('#loadingWaitBlock').unblock();
			}
		});
		
	});
	
	// 成功狀態的垃圾桶按鈕事件
	$(".icon-delete, .editUrl").click(function() {
		$("div.urlupload-box").removeClass('select');
		$("div.urlupload-box.init").addClass('select');
		$("#autoJobURL").val(url);
	});
});

/**
 * 完成按鈕事件
 * @returns {Boolean}
 */
function autoJobFinish() {
	// 檢查目前狀態是否為成功，或已有資料進來未修改網址
	if (!($("div.urlupload-box.success").hasClass("select")
		|| $("div.urlupload-box.datatype").hasClass("select"))) {
		alert("自動排程網址尚未輸入完成。");
		return false;
	}
	
    $.ajax({
		url : "catalogProdAutoJob.html",
		type : "POST",
		dataType:'json',
		data : {
			catalogSeq:$('#catalogSeq').val(),
			jobURL:url,
			updateWay:$('input[name=updateWay]:checked').val()
		},
		success : function(respone) {
		},
		error: function(xtl) {
			alert("系統繁忙，請稍後再試！");
		}
	});
    
    window.location.replace("catalogProd.html");
}