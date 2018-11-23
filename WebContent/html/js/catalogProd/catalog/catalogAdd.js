$(document).ready(function() {
	
	// 移開輸入框檢查
	$('#catalogName').blur(function() {
		if (checkCatalogNameLength()) {
			return false;
		} else if (checkCatalogName()) {
			return false;
		}
	});
	
});

/**
 * 建立目錄按鈕事件
 */
function addPfpCatalog() {
	// 送出前再檢查
	if (checkCatalogNameLength()) {
		return false;
	} else if ($(".msg-error").html()) {
		// 由後端檢核是否名稱重複，此部分不再用ajax檢查
		return false;
	}
	
	$("#savePfpCatalogFrom").submit();
}

/**
 * 檢查目錄名稱長度
 * @returns {Boolean}
 */
function checkCatalogNameLength() {
	if ($('#catalogName').val().length == 0) {
		$(".msg-error").html("請輸入目錄名稱。");
		return true;
	}
	return false;
}

/**
 * 檢查目錄名稱是否重複
 */
function checkCatalogName() {
	$.ajax({
		type: "post",
		dataType: "json",
		url: "checkCatalogName.html",
		data: {
			"catalogName" : $("#catalogName").val()
		},
		timeout: 30000,
		error: function(xhr){
			alert('Ajax request 發生錯誤');
		},
		success: function(response, status){
			if (response.errorMsg) {
				$(".msg-error").html(response.errorMsg);
				return true;
			} else {
				$(".msg-error").html("");
				return false;
			}
		}
	});
}