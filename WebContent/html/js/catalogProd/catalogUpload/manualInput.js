$(document).ready(function() {
	
	
	$('#addProd').click(function() {
		// 資料檢查
//		if(checkData()){
//			
//		}
		
		
		prodListView();
	});
	
});

//function checkData(){
//	
//}

/**
 * 新增商品列表
 */
var prodCount = 0; // 商品總數，依增加刪除做加減
var seqIndex = 0; // 商品流水號，只會一直增加
var prodList = new Array(); // 記錄每筆商品資訊
var prodObject = new Object(); // 記錄每項商品的明細
function prodListView(){
	prodCount++;
	seqIndex++;
	prodObject.ecName = $("#ecName").val(); // 商品名稱
	prodObject.catalogProdSeq = $("#catalogProdSeq").val(); // 商品編號
	prodObject.ecPrice = $("#ecPrice").val(); // 原價
	prodObject.ecDiscountPrice = $("#ecDiscountPrice").val(); // 特價
	prodObject.ecStockStatus = $('#ecStockStatus :selected').val(); // 供應情況
	prodObject.ecUseStatus = $('#ecUseStatus :selected').val(); // 使用狀況
	prodObject.ecCategory = $('#ecCategory').val(); // 商品類別
	
	$(".manualprod-box p span").attr("data-value", prodCount); // 商品數量
	
	var tempHtml =  "<div class=\"prodlist prod" + seqIndex + " \">";
	    tempHtml += "  <div class=\"txt-cell prod-pic\">";
	    tempHtml += "    <img src=\" " + "圖片" + " \">";
	    tempHtml += "  </div>";
	    tempHtml += "  <div class=\"txt-cell prod-detail\">";
	    tempHtml += "    <h5 class=\"h2 txt-ellipsis\" data-pdName=\" " + prodObject.ecName + " \"></h5>";
	    tempHtml += "    <small class=\"prodNum\">編號<em data-prodnum=\" " + prodObject.catalogProdSeq + " \"></em></small>";
	    tempHtml += "    <small class=\"prod-opt\">";
	    tempHtml += "      <i>原價NT$ <em class=\"price\" data-info=\" " + processCommaStyle(prodObject.ecPrice) + " \"></em></i>";
	    tempHtml += "      <i>特價NT$ <em class=\"price\" data-info=\" " + processCommaStyle(prodObject.ecDiscountPrice) + " \"></em></i>";
	    tempHtml += "      <i><em data-info=\" " + $('#ecStockStatus :selected').text() + " \"></em></i>";
	    tempHtml += "      <i><em data-info=\" " + $('#ecUseStatus :selected').text() + " \"></em></i>";
	    tempHtml += "      <i><em data-info=\" " + prodObject.ecCategory + " \"></em></i>";
	    tempHtml += "    </small>";
	    tempHtml += "    <small class=\"prodLink txt-ellipsis\" data-prodlink=\"https://b.ecimg.tw//b.ecimg.tw//b.ecimg.tw/items/DGBQ3VA9008VXQO/000001_1535968931/DGBQ3VA9008VXQO/000001_1535968931.jpg\"></small>";
	    tempHtml += "  </div>";
	    tempHtml += "  <div class=\"txt-cell p-l10\">";
	    tempHtml += "    <u class=\"icon-delete\" onClick=\"delProd(" + seqIndex + ")\"></u>";
	    tempHtml += "  </div>";
	    tempHtml += "</div>";
	$(".manualprod-box").append(tempHtml);
	
	prodList.push(prodObject);
	console.log("prodList:" + JSON.stringify(prodList));
}

// 移除商品清單項目
function delProd(index) {
	prodCount--;
	$(".manualprod-box p span").attr("data-value", prodCount); // 商品數量
	$(".prod" + index).remove();
}

//處理千分位逗號
function processCommaStyle(number){
	return parseInt(number).toLocaleString('en-US');
}