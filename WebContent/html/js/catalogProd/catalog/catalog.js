$(document).ready(function() {
	// 切換每頁顯示幾則
	$("#pageSizeSelect").change(function() {
		processQueryAjax("1");
	});
	
	processKeyupQuery();
	processPageNumber();
});

/**
 * 輸入完成後等待再執行查詢
 */
function processKeyupQuery() {
	// 用 jquery.ba-dotimeout.js 套件
	$('#queryString').keyup(function() {
		$(this).doTimeout('queryString', 1000, function() {
			processQueryAjax("1");
		});
	});
}

/**
 * @param changePageNo 切換到哪頁
 */
function processQueryAjax(changePageNo){
	$.ajax({
	    type: "post",
	    url: "queryCatalog.html",
	    data: {
	          "queryString" : $('#queryString').val(),
	          "pageSize": $('#pageSizeSelect').val(),
	          "pageNo": changePageNo
	    },
	    timeout: 30000,
	    error: function(xhr){
	        alert('Ajax request 發生錯誤');
	    },
	    success: function(response){
	        $('.container-prodmanage').html(response);
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
//	pageNo = 31;
//	pageCount = 39;
	console.log("目前第" + pageNo + "頁");
	console.log("共" + pageCount + "頁");
	
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
	tempHtml += "";
	$(".pagination-box").html(tempHtml);
}

/**
 * 刪除目錄
 * @param catalogSeq
 */
function deletePfpCatalog(catalogSeq) {
	$.ajax({
		type : "post",
		url : "deleteCatalog.html",
		data : {
			"deleteCatalogSeq" : catalogSeq
		},
		timeout : 30000,
		error : function(xhr) {
			alert('Ajax request 發生錯誤');
		},
		success : function(response) {
			window.location = "catalogProd.html";
		}
	});
}