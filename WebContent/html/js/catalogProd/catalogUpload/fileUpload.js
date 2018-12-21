$(document).ready(function() {

	// 一進入畫面先調整檔案上傳css
	updateFileUploadCss();
	// 調整瀏覽器視窗大小時，重新調整檔案上傳的位置
	$(window).resize(function() {
		updateFileUploadCss();
	});
	
});

/**
 * 調整檔案上傳css
 */
function updateFileUploadCss() {
	$("#fileUpload").css({
		"width" : $(".scvupload-wrap").width(),
		"height" : $(".scvupload-wrap").height() - 21,
		"top" : $(".scvupload-wrap").offset().top,
		"left" : $(".scvupload-wrap").offset().left,
		"z-index" : 2,
		"position" : "absolute", // 改為遮罩
		"cursor" : "pointer", // 調整鼠標
		"opacity" : 0 // 完全透明
	});
}

/**
 * 藉由觸發事件
 */
function fileUpload() {
	$("#fileUpload").trigger("click");
}

/**
 * 檢查檔案格式
 */
var fileCount = 0; // 判斷目前是否有上傳檔案 0:沒有 1:有
var fileTemp; //檔案暫存，按取消後回復
var fileFormatIsErr = false; // 檔案格式有錯
function checkFile(file) {
	fileFormatIsErr = false;
	// 選擇檔案時，未選擇檔案(選取消)，將剛剛上傳的檔案還原
	fileCount = file.files.length;
	if (fileCount == 0) {
		$("#fileUpload").remove(); // 將目前被清空的tag刪除
		$(".container-prodmanage").after(fileTemp); // 重新插入剛剛放入temp內的整個tag資料
		fileCount = 1; // 回復後將目前上傳數量調回1
		return false;
	}
	fileTemp = $("#fileUpload").clone(); // 將目前上傳的檔案資料放入temp
	
	// 切換成上傳中畫面
	$("div.txt-table").removeClass('select');
	$("div.txt-table.progress").addClass('select');
	$("div.txt-table.progress .txt-filename").html(file.files[0].name);
	$(".scvupload-box").css("cursor", "default"); // 鼠標調整預設值
	$("#fileUpload").hide(); // 瀏覽檔案上傳先隱藏不使用
	
    var formData = new FormData($("#fileUploadForm")[0]);
    $.ajax({
      type: "POST",
      url: "catalogUploadCheckFileData.html",
      data:formData,
      cache:false,
      processData: false,
      contentType: false,
      dataType: 'text',   // 回傳的資料格式
      success: function (data){
    	$("div.txt-table").removeClass('select');

        var dataMap = JSON.parse(data);
		if (dataMap.status == "SUCCESS") { // 切換到成功畫面
			$("div.txt-table.success").addClass('select');
			$("div.txt-table.success .txt-filename").html(file.files[0].name);
		} else { // 切換到失敗畫面
			fileFormatIsErr = true;
			$("div.txt-table.failure").addClass('select');
			$("div.txt-table.failure .txt-filename").html(file.files[0].name);
		}
		$("#fileUpload").show();
      },
      xhr:function(){
        var xhr = new window.XMLHttpRequest(); // 建立xhr(XMLHttpRequest)物件
        xhr.upload.addEventListener("progress", function(progressEvent){ // 監聽ProgressEvent
            if (progressEvent.lengthComputable) {
            	var percentComplete = progressEvent.loaded / progressEvent.total;
				var percentVal = Math.round(percentComplete * 100);
            	$("#progressBar").css("width", percentVal + "%"); // 進度條顏色
            	$("#uploadPercentage").html(percentVal); // 進度條百分比文字
            }
        }, false);
        return xhr; // 注意必須將xhr(XMLHttpRequest)物件回傳
      }
    }).fail(function(){
    	// 切換到失敗畫面
    	fileFormatIsErr = true;
    	$("div.txt-table").removeClass('select');
    	$("div.txt-table.failure").addClass('select');
		$("div.txt-table.failure .txt-filename").html(file.files[0].name);
		$("#fileUpload").show();
    });

}

/**
 * 完成按鈕事件
 */
var ajaxIsNotFinish = false;
function fileUploadFinish() {
	if (ajaxIsNotFinish) {
		return false;
	} else {
		if (fileCount == 0) {
			alert("未選擇檔案!");
			return false;
		} else if(fileFormatIsErr){
			alert("檔案格式錯誤!");
			return false;
		}
		
		ajaxIsNotFinish = true;
		
		var formData = new FormData($("#fileUploadForm")[0]);
	    $.ajax({
	      type: "POST",
	      url: "catalogProdFileUploadCSV.html",
	      data:formData,
	      cache:false,
	      processData: false,
	      contentType: false,
	      dataType: 'text',   // 回傳的資料格式
	      error: function(xhr){
				alert("系統繁忙，請稍後再試！");
				ajaxIsNotFinish = false;
	      },
	      success: function (data){
	    	  // 等後端檔案抓完，狀態更新再導頁
	    	  window.location.replace("catalogProd.html");
	      }
	    });
	}
}