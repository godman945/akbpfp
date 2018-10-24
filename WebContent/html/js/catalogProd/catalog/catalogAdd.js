$(document).ready(function() {

});

/**
 * 建立目錄按鈕事件
 */
function addPfpCatalog() {
	if (checkValue()) {
		return false;
	}
	$("#savePfpCatalogFrom").submit();
}

function checkValue() {
	if ($('#catalogName').val().length == 0) {
		$(".msg-error").html("請輸入目錄名稱。");
		return true;
	}
	return false;
}