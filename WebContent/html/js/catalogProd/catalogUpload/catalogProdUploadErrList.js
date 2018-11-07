$(document).ready(function() {
	processPageNumber();
});

/**
 * @param changePageNo 切換到哪頁
 */
function processQueryAjax(changePageNo){
	$.ajax({
	    type: "post",
	    url: "catalogProdUploadErrLog.html",
	    data: {
	          "pageNo": changePageNo,
	          "catalogUploadLogSeq": $("#catalogUploadLogSeq").val()
	    },
	    timeout: 30000,
	    error: function(xhr){
	        alert('Ajax request 發生錯誤');
	    },
	    success: function(response){
	        $('body').html(response);
	        processPageNumber();
	    }
	});
}

/**
 * 處理頁碼按鈕事件
 */
function processPageNumber() {
	var pageNo = $("#pageNo").val(); // 目前第幾頁
	var pageCount = $("#pageCount").val(); // 共幾頁(總頁數)
	
	var currentlyStartPage; // 目前開始頁碼
	var currentlyEndPage; // 目前結束頁碼
	var top10Pages; // 上10頁開始頁碼
	if (parseInt(pageNo) % 10 == 0) { // 目前選擇頁數為10 20 30等10位數的時候處理
		currentlyStartPage = parseInt(pageNo % 10) * 10 + (pageNo - 10) + 1;
		currentlyEndPage = parseInt(pageNo % 10) * 10 + (pageNo - 10) + 10;
		top10Pages = parseInt(parseInt(pageNo / 10) * 10 - 19);
	} else {
		currentlyStartPage = parseInt(pageNo / 10) * 10 + 1;
		currentlyEndPage = parseInt(pageNo / 10) * 10 + 10;
		top10Pages = parseInt(parseInt(pageNo / 10) * 10 - 9);
	}
	
	var tempHtml = "";
	if (pageCount <= 10) { // 總頁數10頁(含)以下
		tempHtml += "<span class=\"pagination-buttongroup\">";
		for (var i = 1; i <= pageCount; i++) {
			if (i == pageNo) {
				tempHtml += "<a data-num=\"" + i + "\" class=\"pagination-button active\" href=\"javascript:processQueryAjax('" + i + "');\"></a>";
			} else {
				tempHtml += "<a data-num=\"" + i + "\" class=\"pagination-button\" href=\"javascript:processQueryAjax('" + i + "');\"></a>";
			}
		}
		tempHtml += "</span>";
	} else {
		tempHtml += "<li class=\"txt-cell txt-left\">";
		tempHtml += "  <a data-num=\"1\" class=\"pagination-button left\" href=\"javascript:processQueryAjax('1');\"></a>";
		tempHtml += "</li>";
		tempHtml += "<li class=\"txt-cell\">";
		
		if (pageNo > 10) { // 當前頁超過第10頁才顯示上10頁按鈕
		tempHtml += "  <a class=\"pagination-button prev\" href=\"javascript:processQueryAjax('" + top10Pages + "');\" title=\"上10頁\"></a>";
		}
		
		tempHtml += "  <span class=\"pagination-buttongroup\">";

		for (var i = currentlyStartPage; i <= currentlyEndPage; i++) {
			if (i > pageCount) { // 超過總頁數時離開迴圈
				break;
			} else if (i == pageNo) {
				tempHtml += "  <a data-num=\" " + i + " \" class=\"pagination-button active\" href=\"javascript:processQueryAjax('" + i + "');\"></a>";
			}else{
				tempHtml += "  <a data-num=\" " + i + " \" class=\"pagination-button\" href=\"javascript:processQueryAjax('" + i + "');\"></a>";
			}
		}
		tempHtml += "  </span>";
 
		if (pageNo <= Math.ceil(pageCount / 10) * 10 - 10) { // 總頁數/10  後無條件進位 *10 再-10為當前頁小於此數字才能出現下10頁按鈕
			tempHtml += "  <a class=\"pagination-button next\" href=\"javascript:processQueryAjax('" + parseInt(Math.ceil(pageNo / 10) * 10 + 1) + "');\" title=\"下10頁\"></a>";
		}
		
		tempHtml += "</li>";
		tempHtml += "<li class=\"txt-cell txt-right\">";
		tempHtml += "  <a data-num=\"" + pageCount + "\" class=\"pagination-button right\" href=\"javascript:processQueryAjax('" + pageCount + "');\"></a>";
		tempHtml += "</li>";
	}
	$(".pagination-box").html(tempHtml);
}