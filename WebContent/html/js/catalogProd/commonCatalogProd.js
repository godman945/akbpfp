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

$(document).ready(function() {
	
	// 切換商品目錄下拉選單，依據目錄上傳方式帶到相對應畫面
	$("#catalogSeq").change(function(){
		$('#loadingWaitBlock').block(maskingConfig);
		
		var catalogSeq = $(this).val();
		$.ajax({
			type : "post",
			dataType : "json",
			url : "getCatalogUploadType.html",
			data : {
				catalogSeq : catalogSeq
			},
			timeout : 30000,
			error : function(xhr) {
				alert('Ajax request 發生錯誤');
			},
			success : function(response, status) {
				if (response.catalogUploadType != "") {
					window.location.replace("selectUpload.html?catalogSeq=" + catalogSeq + "&selectUploadFlag=" + response.catalogUploadType);
				} else {
					window.location.replace("catalogUpload.html?catalogSeq=" + catalogSeq);
				}
			}
		});
		
	});

});