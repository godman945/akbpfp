$(document).ready(function(){

	//店家刊登商品網址欄位
	$("#storeProductURL").blur(function(){
//		console.log($(this).attr("id"));
		searchURLAjax($("#storeProductURL").val(), "storeProductURL");
	});
	
	//確認新增按鈕點擊事件
	$("#confirmAddURLbtn").click(function(){
//		console.log($(this).attr("id"));
		searchAddURLAjax($("#confirmAddURL").val(), "confirmAddURL");
	});
});

// 顯示輸入店家商品網址區塊
function showAddStoreProductURL() {
	$(".addStoreProductURL").show();
}

//店家刊登商品網址，查詢網址內容
function searchURLAjax(URL, errorMsgBlock){

	if(isURLInaccurate(URL, errorMsgBlock)){
		return false;
	}
	
	$('#loadingWaitBlock').block({
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
	    type: "post",
	    url: "searchStoreProductURLAjax.html",
	    data: {
//        "page": $('#formPage').val(),
//        "pageSize": $('#fpageSize').val(),
//        "totalPage": $('#ftotalPage').val(),
	    	"searchURL" : URL
	    },
	    timeout: 30000,
	    error: function(xhr){
	    	$('#loadingWaitBlock').unblock();
	        alert('Ajax request 發生錯誤');
	    },
	    success: function(response){
	    	$('#loadingWaitBlock').unblock();
//	    	console.log(response);
	        $('#searchURLDetailsTable').html(response);

	    	//查詢結果顯示
	    	$(".queryResult").show();
	    	// 調整按鈕位置用
	    	$("#addStoreProductURLbtn").closest("span").css("display", "block");
	    	
	    	//從下面的已選擇將筆數帶到上面
//	    	$(".checkboxCount-up").html($(".checkboxCount-down").html());
	    }
	});
}

//新增商品網址
//function searchAddURLAjax(URL, errorMsgBlock){
//
//	if(isURLInaccurate(URL, errorMsgBlock)){
//		return false;
//	}
//	
//	$('#loadingWaitBlock').block({
//	    message: "<img src='html/img/LoadingWait.gif' />",
//	    css: {
//	        padding: 0,
//	        margin: 0,
//	        width: '50%',
//	        top: '40%',
//	        left: '35%',
//	        textAlign: 'center',
//	        color: '#000',
//	        border: '3px solid #aaa',
//	        backgroundColor: '#fff',
//	        cursor: 'wait'
//	    }
//	});
//	
//	$.ajax({
//	    type: "post",
//	    url: "searchStoreProductAddURLAjax.html",
//	    data: {
////	          "page": $('#formPage').val(),
////	          "pageSize": $('#fpageSize').val(),
////	          "totalPage": $('#ftotalPage').val(),
//	          "searchURL" : URL
//	    },
//	    timeout: 30000,
//	    error: function(xhr){
//	    	$('#loadingWaitBlock').unblock();
//	        alert('Ajax request 發生錯誤');
//	    },
//	    success: function(response){
//	    	$('#loadingWaitBlock').unblock();
////	    	console.log(response);
//	        $('#searchURLDetailsTable').html(response);
//
//	    	//查詢結果顯示
////	    	$(".queryResult").show();
//	    	// 調整按鈕位置用
////	    	$("#addStoreProductURLbtn").closest("span").css("display", "block");
//	    	
//	    	//從下面的已選擇將筆數帶到上面
////	    	$(".checkboxCount-up").html($(".checkboxCount-down").html());
//	    }
//	});
//}

//檢查輸入網址是否正確
function isURLInaccurate(URL, errorMsgBlock){
	if(URL.length == 0){ //未輸入，則不做任何事
		return true;
	}else if(!(URL.indexOf("http://24h.pchome.com.tw/", 0) > -1
			|| URL.indexOf("https://24h.pchome.com.tw/", 0) > -1
			|| URL.indexOf("24h.pchome.com.tw/", 0) > -1
			//商店街
			|| URL.indexOf("http://www.pcstore.com.tw/", 0) > -1
			|| URL.indexOf("https://www.pcstore.com.tw/", 0) > -1
			|| URL.indexOf("www.pcstore.com.tw/", 0) > -1
			//個人賣場(商店街)
			|| URL.indexOf("http://seller.pcstore.com.tw/", 0) > -1
			|| URL.indexOf("https://seller.pcstore.com.tw/", 0) > -1
			|| URL.indexOf("seller.pcstore.com.tw/", 0) > -1
			//露天商品頁
			|| URL.indexOf("http://goods.ruten.com.tw/item/show?", 0) > -1)	){
		
		if(errorMsgBlock == "storeProductURL"){ //輸入為店家刊登商品網址，則顯示在該區塊
			$("#chkStoreProductURL").show();
		}else if(errorMsgBlock == "confirmAddURL"){ //輸入為新增商品網址
			$("#chkConfirmAddURL").show();
		}
		return true;
	}
	
	if(errorMsgBlock == "storeProductURL"){			
		$("#chkStoreProductURL").hide();
	}else if(errorMsgBlock == "confirmAddURL"){
		$("#chkConfirmAddURL").hide();
	}
	return false;
}