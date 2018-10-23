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

var globalUrl = "";
$(document).ready(function() {
	
	if ($("#uploadContent").val()) { 
		// 一進入先判斷此目錄是否已經有設定網址，有則調整預設畫面
		$("div.urlupload-box").removeClass('select');
		$("div.urlupload-box.datatype").addClass('select');
		processShowHideIcon($("#uploadContent").val(), "datatype");
		$("#dataTypeContent").html("網址：<em data-fileUrl=\"" + $("#uploadContent").val() + "\"></em>");
	}
	
	// 檢查輸入網址
	$("#pchomeStoreURL, #errPchomeStoreURL").blur(function(){
		globalUrl = $(this).val();
		
		//檢查輸入網址是否正確
		if(isURLInaccurate(globalUrl)){
			changeErrorView();
			return false;
		}
		
		//顯示遮罩
		$('#loadingWaitBlock').block(maskingConfig);
		
		$.ajax({
		    type: "post",
		    dataType: "json",
		    url: "processCheckPchomeStoreURL.html",
		    data: {
		    	"pchomeStoreURL" : globalUrl
		    },
		    timeout: 30000,
		    error: function(xhr){
		    	$('#loadingWaitBlock').unblock();
		        alert('Ajax request 發生錯誤');
		    },
		    success: function(response, status){
		    	$("div.urlupload-box").removeClass('select');
		    	
				if (response.status == "ERROR") {
					changeErrorView();
				} else {
					$("div.urlupload-box.success").addClass('select');
					
					processShowHideIcon(globalUrl, "success");
					
					$("#successFileName").attr("data-fileName", $("#catalogSeq option:selected").text());
					$("#successContent").html("網址：<em data-fileUrl=\"" + globalUrl + "\"></em>");
				}
				
				$('#loadingWaitBlock').unblock();
			}
		});
		
	});
	
	// 成功狀態的垃圾桶按鈕事件
	$(".icon-delete, .editUrl").click(function() {
		$("div.urlupload-box").removeClass('select');
		$("div.urlupload-box.init").addClass('select');
		$("#pchomeStoreURL").val(globalUrl);
	});
});

//檢查輸入網址是否正確
function isURLInaccurate(url){
	if(url.length == 0){ //未輸入，則不做任何事
		return true;
	}else if(!(url.indexOf("http://24h.pchome.com.tw/", 0) > -1
			|| url.indexOf("https://24h.pchome.com.tw/", 0) > -1
			//商店街
			|| url.indexOf("http://www.pcstore.com.tw/", 0) > -1
			|| url.indexOf("https://www.pcstore.com.tw/", 0) > -1
			|| url.indexOf("http://pcstore.com.tw/", 0) > -1
			//個人賣場(商店街)
			|| url.indexOf("http://seller.pcstore.com.tw/", 0) > -1
			|| url.indexOf("https://seller.pcstore.com.tw/", 0) > -1
			//露天商品頁
			|| url.indexOf("http://goods.ruten.com.tw/item/show?", 0) > -1
			|| url.indexOf("https://goods.ruten.com.tw/item/show?", 0) > -1)){
		return true;
	}
	
	return false;
}

// 切換成錯誤畫面
function changeErrorView() {
	$("div.urlupload-box").removeClass('select');
	$("div.urlupload-box.failure").addClass('select');
	$("#errPchomeStoreURL").val(globalUrl);
}

// 判斷顯示哪一區塊的哪一個icon
function processShowHideIcon(url, block){
	$(".logoIcon").hide();
	if (url.indexOf("24h") > -1) {
		$("." + block + " .24h").show();
	} else if (url.indexOf("seller.pcstore") > -1) {
		$("." + block + " .sellerPcstore").show();
	} else if (url.indexOf("pcstore") > -1) {
		$("." + block + " .pcstore").show();
	} else if (url.indexOf("ruten") > -1) {
		$("." + block + " .ruten").show();
	}
}

/**
 * 上一步
 * 根據商品目錄下拉選單所選擇得目錄返回
 */
//function back() {
//	window.location = "selectUpload.html?catalogSeq=" + $("#catalogSeq").val();
//}

/**
 * 完成按鈕事件
 * @returns {Boolean}
 */
function pchomeStoreURLFinish() {
	// 檢查目前狀態是否為成功，或已有資料進來未修改網址
	if (!($("div.urlupload-box.success").hasClass("select")
		|| $("div.urlupload-box.datatype").hasClass("select"))) {
		alert("商店網址尚未輸入完成。");
		return false;
	}
	
    $.ajax({
		url : "catalogProdPchomeStoreURL.html",
		type : "POST",
		dataType:'json',
		data : {
			catalogSeq:$('#catalogSeq').val(),
			pchomeStoreURL:globalUrl,
			updateWay:$('input[name=updateWay]:checked').val()
		},
		success : function(respone) {
			window.location.replace("catalogProd.html");
		},
		error: function(xtl) {
			alert("系統繁忙，請稍後再試！");
		}
	});
}