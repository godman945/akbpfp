$(document).ready(function() {
	
	// 調整檔案上傳css
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
	
});

/**
 * 上一步
 * 根據商品目錄下拉選單所選擇得目錄返回
 */
function back() {
	window.location = "catalogUpload.html?catalogSeq=" + $("#catalogSeq").val();
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
function checkFile(file) {
	// 選擇檔案時，未選擇檔案(選取消)則回到初始畫面
	fileCount = file.files.length;
	if (fileCount == 0) {
		$("div.txt-table").removeClass('select');
		$("div.txt-table.init").addClass('select');
		return false;
	}
	
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
    	$("div.txt-table").removeClass('select');
    	$("div.txt-table.failure").addClass('select');
		$("div.txt-table.failure .txt-filename").html(file.files[0].name);
		$("#fileUpload").show();
    });

}

/**
 * 完成按鈕事件
 * @returns {Boolean}
 */
function fileUploadFinish() {
	if (fileCount == 0) {
		alert("未選擇檔案!");
		return false;
	}
	
	var formData = new FormData($("#fileUploadForm")[0]);
    $.ajax({
      type: "POST",
      url: "catalogProdFileUploadCSV.html",
      data:formData,
      cache:false,
      processData: false,
      contentType: false,
      dataType: 'text',   // 回傳的資料格式
      success: function (data){
    	  
      }
    }).fail(function(){
    	console.log("發生錯誤。。。。");
    });
    
    window.location.replace("catalogProd.html");
}