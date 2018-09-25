$(document).ready(function() {
	
	console.log("catalogUpload");
	
	// 判斷選取上傳方式
	$('#dataupload .dataupload-box').click(function() {
		$('#dataupload .dataupload-box').removeClass('selected');
		$(this).addClass('selected');
		// 判斷目前選擇的為#dataupload .dataupload-box的第幾個，flag記錄
		$("#selectUploadFlag").val($('#dataupload .dataupload-box').index($(this)) + 1);
	});
});

function catalogUpload(){
	$("#confirmUpload").submit();
}

//function addPfpCatalog(){
//
//	if(checkValue()){
//		return false;
//	}
//	
//	$("#savePfpCatalogFrom").submit();
//	
////	$.ajax({
////	    type: "post",
////	    url: "savePfpCatalog.html",
////	    data: {
////	          "catalogName" : $('#catalogName').val(),
////	          // 目前僅只有一種類型
////	          "catalogType": '1'
////	      },
////	    timeout: 30000,
////	    error: function(xhr){
////	        alert('Ajax request 發生錯誤');
////	    },
////	    success: function(response){
//////	    	console.log("catalogProd.html?catalogSeq=" + response.catalogSeq);
//////	    	window.location = "catalogProd.html?catalogSeq=" + response.catalogSeq;
//////	    	console.log("catalogUpload.html?catalogSeq=" + response.catalogSeq);
//////	    	window.location = "catalogUpload.html?catalogSeq=" + response.catalogSeq;
////	    	
////	    }
////	});
//}

//function checkValue() {
//	if ($('#catalogName').val().length == 0) {
//		alert("請輸入目錄名稱。");
//		return true;
//	}
//
//	return false;
//}