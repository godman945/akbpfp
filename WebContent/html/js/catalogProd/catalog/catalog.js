$(document).ready(function() {
	console.log("AAAAAAAAAAXXXXXXXX");
	
	
	// 查詢目錄
//	$("#addCatalog").click(function(){
//		$.ajax({
//		    type: "post",
//		    url: "queryCatalog.html",
//		    data: {
//		          "queryString" : $('#queryString').val(),
//		          "pageSize": $('#pageSizeSelect').val()
////		          "pageNo": $('#formPage').val()
//		      },
//		    timeout: 30000,
//		    error: function(xhr){
//		        alert('Ajax request 發生錯誤');
//		    },
//		    success: function(response){
//		        $('.container-prodmanage').html(response);
//		    }
//		});
//	});
	
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
function processKeyupQuery(){
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









///**
// * 處理查詢事件
// */
//function processQuery2(){
//	var timeIndex;
//	$("#queryString").on({
//		
//		keydown:function(){ 
//			console.log("timeIndex:" + timeIndex);
//			clearTimeout(timeIndex);
//		},
//		keyup:function(event){
//			
//			// 可能會要判斷 鍵盤按鍵代碼，先準備
//			console.log(event.which);
//			
//			timeIndex = setTimeout(function() {
//				console.log("test look");
//				$.ajax({
//				    type: "post",
//				    url: "queryCatalog.html",
//				    data: {
//				          "queryString" : $('#queryString').val(),
//				          "pageSize": $('#pageSizeSelect').val()
//	//			          "pageNo": $('#formPage').val()
//				      },
//				    timeout: 30000,
//				    error: function(xhr){
//				        alert('Ajax request 發生錯誤');
//				    },
//				    success: function(response){
//				        $('.container-prodmanage').html(response);
////				        $("#queryString").focus(); //ftl調整的話，應該不用此方式
//				      
//				    }
//				});
//			}, 1000);
//		}
//		
////		keydown:function(){ console.log("aaa"); }
////	    mouseover:function(){$("body").css("background-color","lightgray");},  
////
////	    mouseout:function(){$("body").css("background-color","lightblue");}, 
////
////	    click:function(){$("body").css("background-color","yellow");}  
//
//	  });
//}

///**
// * 處理頁碼按鈕事件
// */
//function processPageNumber2() {
//	var pageNo = $("#pageNo").val(); // 目前第幾頁
//	var pageCount = $("#pageCount").val(); // 共幾頁(總頁數)
////	var totalCount = $("#totalCount").val(); // 共幾筆(總筆數)
//	
//	pageNo = 10;
//	pageCount = 30;
//	
//	console.log("共" + pageCount + "頁");
//	console.log("共" + totalCount + "筆");
//	console.log("顯示" + $("#pageSizeSelect").val() + "則");
//	
//	var tempHtml = "";
//	if (pageCount <= 10) {
//		tempHtml += "<span class=\"pagination-buttongroup\">";
//		for (var i = 1; i <= pageCount; i++) {
//			if (i == pageNo) {
//				tempHtml += "<a data-num=\"" + i + "\" class=\"pagination-button active\" href=\"#\"></a>";
//			} else {
//				tempHtml += "<a data-num=\"" + i + "\" class=\"pagination-button\" href=\"#\"></a>";
//			}
//		}
//		tempHtml += "</span>";
//	} else {
//		tempHtml += "<li class=\"txt-cell txt-left\">";
//		tempHtml += "  <a data-num=\"1\" class=\"pagination-button left\" href=\"#\"></a>";
//		tempHtml += "</li>";
//		tempHtml += "<li class=\"txt-cell\">";
//		
//		if (pageNo > 10) {
//		tempHtml += "  <a class=\"pagination-button prev\" href=\"javascript:void(0);\" title=\"上" + parseInt(parseInt(pageNo/10)*10- (pageNo-10)-9) + " 10頁\"></a>";
//		}
//		
//		tempHtml += "  <span class=\"pagination-buttongroup\">";
////		&& i<=pageCount
//		var currentlyStartPage = parseInt(pageNo) % 10 == 0 ? parseInt(pageNo%10)*10+ (pageNo-10)+1 : parseInt(pageNo/10)*10+1;
//		var currentlyEndPage = parseInt(pageNo) % 10 == 0 ? parseInt(pageNo%10)*10+ (pageNo-10)+10 : parseInt(pageNo/10)*10+10;
//		for (var i = currentlyStartPage; i <= currentlyEndPage; i++) {
//			if (i > pageCount) { // 超過總頁數時離開迴圈
//				break;
//			} else if (i == pageNo) {
//				tempHtml += "    <a data-num=\" " + i + " \" class=\"pagination-button active\" href=\"#\"></a>";
//			}else{
//				tempHtml += "    <a data-num=\" " + i + " \" class=\"pagination-button\" href=\"#\"></a>";
//			}
//		}
//		tempHtml += "  </span>";
// 
////		var finalPage = parseInt(pageCount%10)== 0 ? parseInt(pageCount % 10) * 10 -10 :parseInt(pageCount / 10) * 10 -10;// 當前頁小於 假設101頁/10*10為100則表示還有下10頁
////		if (pageNo <= finalPage) { // 且finalPage 不能與 最後總頁數相同
//		
//		if (pageNo <= Math.ceil(pageCount/10)*10 -10) {
////		var nextTenPage = parseInt(pageNo%10)== 0 ? parseInt(parseInt(pageNo%10)*10+11) : parseInt(parseInt(pageNo/10)*10+11);
//		tempHtml += "  <a class=\"pagination-button next\" href=\"#\" title=\"下" + parseInt(Math.ceil(pageNo/10)*10+1) + " 10頁\"></a>";
//		}
//		
//		tempHtml += "</li>";
//		tempHtml += "<li class=\"txt-cell txt-right\">";
//		tempHtml += "  <a data-num=\"" + pageCount + "\" class=\"pagination-button right\" href=\"#\"></a>";
//		tempHtml += "</li>";
//	}
//	tempHtml += "";
//	$(".pagination-box").html(tempHtml);
//	
//	
////	//參考用
////    var pagedata = $('.pagination-wrap').data();
////    var order = pagedata.order;
////    var quantity = pagedata.quantity;
////
////    //設定頁碼
////    setPagination(order, quantity);
////
////    function setPagination(order, quantity){
////        var $buttongroup=$('.pagination-buttongroup a');
////        var $buttonright=$('a.pagination-button.right');
////        //計算頁碼起始顯示值 +修改目前所在頁碼按鈕底色
////        var _length=$buttongroup.length;
////        var _order=(order<=_length)?1:order;
////            _order=(quantity-order<=_length)?quantity-_length+1:_order;
////            _order=(_order>_length &&_order%_length!=0)?(_order-_order%_length+1):_order;
////        //寫入頁碼
////        for(var i=0;i<_length;i++){
////            $buttongroup.eq(i).attr('data-num',_order+i);
////            if((_order+i)==order) $buttongroup.eq(i).addClass('active');
////        }
////        //寫入最後一頁頁碼 + 最後一頁時修改按鈕底色
////        $buttonright.attr('data-num', quantity);
////        if(order==quantity) $buttonright.addClass('active');
////    }
//}