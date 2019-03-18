$(document).ready(function() {

	// 判斷選取上傳方式
	$('#dataupload .dataupload-box').click(function() {
		$('#dataupload .dataupload-box').removeClass('selected');
		$(this).addClass('selected');
		// 判斷目前選擇的為#dataupload .dataupload-box的第幾個，flag記錄
		$("#selectUploadFlag").val($('#dataupload .dataupload-box').index($(this)) + 1);
	});
	
});

/**
 * 選擇完上傳方式後點下一步事件
 */
function catalogUploadNext() {
	$("#confirmUpload").submit();
}