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
	$('#dropImg').on('dragover', false); // 拖曳時不執行動作，此動作結束後才能觸發ondrop事件
	
	//新增商品按鈕事件
	$('#addProd').click(function() {
		// 資料檢查
		if (checkKeyInDataIsError()) {
			return false;
		}
		
		$('#loadingWaitBlock').block(maskingConfig);
		
		$.ajax({
			type: "post",
			dataType: "json",
//			async:false,
			url: "processCheckManualInputData.html",
			data: {
				"ecUrl" : $("#ecUrl").val(),
				"catalogSeq" : $("#catalogSeq").val(),
				"catalogProdSeq" : $("#catalogProdSeq").val()
			},
			timeout: 30000,
			error: function(xhr){
				$('#loadingWaitBlock').unblock();
				alert('Ajax request 發生錯誤');
			},
			success: function(response, status){
				var isOK = true;
				if (response.checkURLStatus == "ERROR") {
					$("#ecUrlErrMsg").html("請確認輸入網址是否正確。");
					isOK = false;
				}
				
				if (response.checkCatalogProdSeqStatus == "ERROR") {
					$("#catalogProdSeqErrMsg").html("此目錄下，商品編號已重複。");
					isOK = false;
				}
					
				if(isOK){
					prodListView();
					cleanData();
				}
				
				console.log("success");
				$('#loadingWaitBlock').unblock();
			}
		});
		
	});
	
});

var prodCount = 0; // 商品總數，依增加刪除做加減
var seqIndex = 0; // 商品流水號，只會一直增加
var prodList = new Array(); // 記錄每筆商品資訊
var prodObject = new Object(); // 記錄每項商品的明細
/**
 * 新增商品列表
 */
function prodListView() {
	prodCount++;
	seqIndex++;
	prodObject.seqIndex = seqIndex;
	prodObject.ecName = $("#ecName").val(); // 商品名稱
	prodObject.ecUrl = $("#ecUrl").val(); // 商品網址
	prodObject.catalogProdSeq = $("#catalogProdSeq").val(); // 商品編號
	if ($("#ecPrice").val().length == 0) { //未輸入原價，則將特價帶入原價
		prodObject.ecPrice = $("#ecDiscountPrice").val(); // 特價
	} else {
		prodObject.ecPrice = $("#ecPrice").val(); // 原價
	}
	prodObject.ecDiscountPrice = $("#ecDiscountPrice").val(); // 特價
	prodObject.ecStockStatus = $('#ecStockStatus :selected').val(); // 供應情況
	prodObject.ecUseStatus = $('#ecUseStatus :selected').val(); // 使用狀況
	prodObject.ecCategory = $('#ecCategory').val(); // 商品類別
	prodObject.ecImgBase64 = base64Img; // 圖片base64路徑
	
	$(".manualprod-box p span").attr("data-value", prodCount); // 商品數量
	
	var tempHtml =  "<div class=\"prodlist prod" + seqIndex + " \">";
	    tempHtml += "  <div class=\"txt-cell prod-pic\">";
	    tempHtml += "    <img src=\" " + prodObject.ecImgBase64 + " \">";
	    tempHtml += "  </div>";
	    tempHtml += "  <div class=\"txt-cell prod-detail\">";
	    tempHtml += "    <h5 class=\"h2 txt-ellipsis\" data-pdName=\" " + prodObject.ecName + " \"></h5>";
	    tempHtml += "    <small class=\"prodNum\">編號<em data-prodnum=\" " + prodObject.catalogProdSeq + " \"></em></small>";
	    tempHtml += "    <small class=\"prod-opt\">";
	    tempHtml += "      <i>原價NT$ <em class=\"price\" data-info=\" " + processCommaStyle(prodObject.ecPrice) + " \"></em></i>";
	    tempHtml += "      <i>特價NT$ <em class=\"price\" data-info=\" " + processCommaStyle(prodObject.ecDiscountPrice) + " \"></em></i>";
	    tempHtml += "      <i><em data-info=\" " + $('#ecStockStatus :selected').text() + " \"></em></i>";
	    tempHtml += "      <i><em data-info=\" " + $('#ecUseStatus :selected').text() + " \"></em></i>";
	    if (prodObject.ecCategory.length != 0) { // 商品類別有輸入在組此部分
	    	tempHtml += "      <i><em data-info=\" " + prodObject.ecCategory + " \"></em></i>";
		}
	    tempHtml += "    </small>";
	    tempHtml += "    <small class=\"prodLink txt-ellipsis\" data-prodlink=\" " + prodObject.ecUrl + " \"></small>";
	    tempHtml += "  </div>";
	    tempHtml += "  <div class=\"txt-cell p-l10\">";
	    tempHtml += "    <u class=\"icon-delete\" onClick=\"delProd(" + seqIndex + ")\"></u>";
	    tempHtml += "  </div>";
	    tempHtml += "</div>";
	$(".manualprod-box").append(tempHtml);
	
	prodList.push(JSON.stringify(prodObject)); // 將json轉成字串，才push的進去
	console.log("prodList:" + JSON.stringify(prodList));
}

/**
 * 新增完成後，清除資料
 */
function cleanData() {
	$("#ecName").val("");
	$("#ecUrl").val("");
	$("#ecPrice").val("");
	$("#ecDiscountPrice").val("0");
	$("#ecStockStatus").val("1");
	$("#ecUseStatus").val("0");
	$("#catalogProdSeq").val("");
	$("#ecCategory").val("");
	deleteImg();
}

/**
 * 移除商品清單項目
 * @param index
 */
function delProd(index) {
	prodCount--;
	$(".manualprod-box p span").attr("data-value", prodCount); // 商品數量
	$(".prod" + index).remove();
	
	var tempList = new Array(); // 暫存清單
	$.each(prodList, function(indexNum, object) { // 跑商品清單每筆資料來比對
		var currentData = JSON.parse(object); // 將字串轉成json格式
		if (currentData.seqIndex != index) { // 目前跑到的資料與要刪除資料不同，則先存入暫存清單
			tempList.push(object);
		}
	});
	prodList = tempList; // 要被刪除的資料，不會存入暫存清單，將最後清單資料放回商品清單
//	console.log("刪除後的prodList:" + JSON.stringify(prodList));
}

/**
 * 點擊選擇要上傳的圖片
 * @param obj
 */
function openFileLoad(obj) {
	$("#fileupload").click();
}

/**
 * 選取圖片
 * @param file
 * @returns {Boolean}
 */
function selectImg(file) {
	// 選擇取消，未選擇檔案不做處理
	fileCount = file.files.length;
	if (fileCount == 0) {
		return false;
	}

	var file = file.files[0];
	checkUploadFile(file);
}

/**
 * 將檔案丟到範圍內觸發事件
 * @param ev
 */
function drop(ev) {
	var file = ev.dataTransfer.files[0];
	checkUploadFile(file);
	ev.preventDefault(); // 防止瀏覽器再次處理資料，檔案丟入後瀏覽器後避免被開啟
}

var base64Img = ""; // 圖片路徑
/**
 * 檢查上傳的圖片是否符合規範
 * @param file
 * @returns {Boolean}
 */
function checkUploadFile(file) {
	var fileName = file.name;
	var fileSizeKb = Math.round(file.size/1024);
	var fileType = file.type;

	if (fileSizeKb > 180) {
		alert("檔案須小於180kb");
		return false;
	}

	if (fileType != 'image/jpeg' && fileType != 'image/png' && fileType != 'image/gif') {
		alert("請上傳jpg、png 或 gif格式");
		return false;
	}

	readFile(file, function(e) {
		base64Img = e.target.result;
		$("#successImg").attr("src", base64Img);
		
		// 去除重複執行unbind 再重新綁定
		$('#successImg').unbind('load').load(function(){ // 圖片完成後判斷"原生圖片"長寬
			if ($(this)[0].naturalHeight < 300 && $(this)[0].naturalWidth < 300) {
				alert("請上傳圖片解析度300x300以上尺寸");
				deleteImg();
				return false;
			} else {
				// 成功切換成功畫面
				$(".pdimgupload-box").removeClass('select').addClass('success');
			}
		});

	});

}

/**
 * 圖片進行base64編碼
 * @param file
 * @param onLoadCallback
 */
function readFile(file, onLoadCallback) {
	var reader = new FileReader();
	reader.onload = onLoadCallback;
	reader.readAsDataURL(file);
}

/**
 * 移除圖片
 */
function deleteImg() {
	base64Img = "";
	$("#successImg").attr("src", "");
	$(".pdimgupload-box").removeClass('success').addClass('select');
	$('#fileupload').val(""); // 使用點擊選擇要上傳的檔案，所上傳的圖片額外清除
}

/**
 * 處理千分位逗號
 * @param number
 * @returns
 */
function processCommaStyle(number) {
	return parseInt(number).toLocaleString('en-US');
}

/**
 * 新增商品時檢查全部該輸入的資料是否正確
 * @returns {Boolean}
 */
function checkKeyInDataIsError() {
	var ecNameIsErr = checkEcNameIsErr();
	var ecUrlIsErr = checkEcUrlIsErr();
	var ecPriceIsErr = checkEcPriceIsErr();
	var ecDiscountPriceIsErr = checkEcDiscountPriceIsErr();
	var catalogProdSeqIsErr = checkCatalogProdSeqIsErr();
	var ecCategoryIsErr = checkEcCategoryIsErr();
	var imgIsErr = checkImgIsErr();
	if(ecNameIsErr || ecUrlIsErr || ecPriceIsErr || ecDiscountPriceIsErr ||
			catalogProdSeqIsErr || ecCategoryIsErr || imgIsErr){
		return true;
	} else {
		return false;
	}
}

/**
 * 檢查商品名稱
 * @returns {Boolean}
 */
function checkEcNameIsErr() {
	var ecName = $("#ecName").val();
	if (ecName.length <= 0) {
		$("#ecNameErrMsg").html("此欄位為必填。");
		return true;
	} else if (ecName.length > 20) {
		$("#ecNameErrMsg").html("輸入字數大於限制字數。");
		return true;
	}
	
	$("#ecNameErrMsg").html("");
	return false;
}

/**
 * 檢查商品網址
 * @returns {Boolean}
 */
function checkEcUrlIsErr() {
	var ecUrl = $("#ecUrl").val();
	var isErr = false;
	$("#ecUrlErrMsg").html("");
	
	if (ecUrl.length <= 0) {
		$("#ecUrlErrMsg").html("此欄位為必填。");
		return true;
	}
	
	return isErr;
}

/**
 * 檢查原價
 * @returns {Boolean}
 */
function checkEcPriceIsErr() {
	var ecPrice = $("#ecPrice").val();
	if (ecPrice.length > 6) {
		$("#ecPriceErrMsg").html("輸入數字超過6位數。");
		return true;
	} else if (!isNum(ecPrice)) {
		$("#ecPriceErrMsg").html("只能輸入數字。");
		return true;
	}
	
	$("#ecPriceErrMsg").html("");
	return false;
}

/**
 * 檢查特價
 * @returns {Boolean}
 */
function checkEcDiscountPriceIsErr() {
	var ecDiscountPrice = $("#ecDiscountPrice").val();
	if (ecDiscountPrice.length <= 0) {
		$("#ecDiscountPriceErrMsg").html("此欄位為必填。");
		return true;
	} else if (ecDiscountPrice.length > 6) {
		$("#ecDiscountPriceErrMsg").html("輸入數字超過6位數。");
		return true;
	} else if (!isNum(ecDiscountPrice)) {
		$("#ecDiscountPriceErrMsg").html("只能輸入數字。");
		return true;
	}
	
	$("#ecDiscountPriceErrMsg").html("");
	return false;
}

/**
 * 檢查商品編號
 * @returns {Boolean}
 */
function checkCatalogProdSeqIsErr() {
	var catalogProdSeq = $("#catalogProdSeq").val();
	if (catalogProdSeq.length <= 0) {
		$("#catalogProdSeqErrMsg").html("此欄位為必填。");
		return true;
	} else if (catalogProdSeq.length > 30) {
		$("#catalogProdSeqErrMsg").html("輸入字數大於限制字數。");
		return true;
	}
	
	// 檢查商品編號是否重複
	var checkFlag = false;
	$("#catalogProdSeqErrMsg").html("");
	
	// 先檢查畫面上輸入的商品編號是否重複
	$.each(prodList, function(indexNum, object) { // 跑商品清單每筆資料來比對
		var currentData = JSON.parse(object); // 將字串轉成json格式
		if (currentData.catalogProdSeq == catalogProdSeq) {
			$("#catalogProdSeqErrMsg").html("此目錄下，商品編號已重複。");
			checkFlag = true;
			return false; // 跳出each
		}
	});
	
	return checkFlag;
}

/**
 * 檢查商品類別
 * @returns {Boolean}
 */
function checkEcCategoryIsErr() {
	var ecCategory = $("#ecCategory").val();
	if (ecCategory.length > 15) {
		$("#ecCategoryErrMsg").html("輸入字數大於限制字數。");
		return true;
	}
	
	$("#ecCategoryErrMsg").html("");
	return false;
}

/**
 * 檢查是否上傳圖片
 * @returns {Boolean}
 */
function checkImgIsErr() {
	//拖曳上傳和選擇檔案上傳最後都會產生base64，皆無資料則請上傳圖片
	if(base64Img.length == 0){
		alert("請上傳圖片。");
		return true;
	}
	
	return false;
}

/**
 * 檢查是否為數字，是數字回true
 * @param val
 * @returns
 */
function isNum(val) {
	return /^[0-9]*$/.test(val);
}

/**
 * 上一步
 * 根據商品目錄下拉選單所選擇得目錄返回
 */
function back() {
	window.location = "catalogUpload.html?catalogSeq=" + $("#catalogSeq").val();
}

/**
 * 手動上傳完成事件
 */
function manualInputFinish() {
	if (prodList.length == 0) {
		alert("未建立商品。");
		return false;
	}
	
	$.ajax({
		type : "post",
		dataType : "json",
		url : "catalogProdManualInput.html",
		data : {
			catalogSeq : $("#catalogSeq").val(),
			"manualInputDataMap" : JSON.stringify(prodList)
		},
		timeout : 30000,
		error : function(xhr) {
			alert('Ajax request 發生錯誤');
		},
		success : function(response, status) {
			console.log("END");
//			window.location = "adActionFastPublishUrlViewAction.html";
		}
	});
	
}