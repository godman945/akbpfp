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
	
	// 切換商品目錄下拉選單，切換至商品清單
	$("#catalogSeq").change(function(){
		$('#loadingWaitBlock').block(maskingConfig);
		
		var catalogSeq = $(this).val();
		location.href = "prodListCardStyleView.html?catalogSeq=" + catalogSeq + "&currentPage=1&pageSizeSelected=10";
	});

});