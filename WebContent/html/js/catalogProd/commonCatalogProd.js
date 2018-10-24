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
	
	// 處理點回上一頁 目錄下拉選單的值還原回正確值
	var url = location.href;
	if (url.indexOf("catalogSeq=") > -1) {
		var urlCatalogSeq = url.substring(url.indexOf("catalogSeq="), url.length).replace("catalogSeq=", "");
		$("#catalogSeq").val(urlCatalogSeq);
	}
	
	// 切換商品目錄下拉選單，依據目錄上傳方式帶到相對應畫面
	$("#catalogSeq").change(function(){
		$('#loadingWaitBlock').block(maskingConfig);
		
		var catalogSeq = $(this).val();
		//回上頁待測試
//		window.location = "prodListCardStyleView.html?catalogSeq=" + catalogSeq + "&currentPage=1&pageSizeSelected=10";
		location.href = "prodListCardStyleView.html?catalogSeq=" + catalogSeq + "&currentPage=1&pageSizeSelected=10";
//		window.location.replace("prodListCardStyleView.html?catalogSeq=" + catalogSeq + "&currentPage=1&pageSizeSelected=10");

//		$.ajax({
//			type : "post",
//			dataType : "json",
//			url : "getCatalogUploadType.html",
//			data : {
//				catalogSeq : catalogSeq
//			},
//			timeout : 30000,
//			error : function(xhr) {
//				alert('Ajax request 發生錯誤');
//			},
//			success : function(response, status) {
//				if (response.catalogUploadType != "") {
//					window.location.replace("selectUpload.html?catalogSeq=" + catalogSeq + "&selectUploadFlag=" + response.catalogUploadType);
//				} else {
//					window.location.replace("catalogUpload.html?catalogSeq=" + catalogSeq);
//				}
//			}
//		});
		
	});

});