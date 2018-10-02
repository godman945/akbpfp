$(document).ready(function() {
	
	console.log("fileUpload");
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
	
	
//	$("#fileUploadForm").submit(function(e){
//	    e.preventDefault();
	    
//	    if($("#file").val() === ""){
//	      alert('請選擇上傳檔案');
//	      return;
//	    }

    var formData = new FormData($("#fileUploadForm")[0]);
	    $.ajax({
	      type: "POST",
//	      url: "catalogProdFileUploadCSV.html",
	      url: "catalogUploadCheckFileData.html",
	      data:formData,
	      cache:false,
	      processData: false,
	      contentType: false,
	      dataType: 'text',   // 回傳的資料格式
	      success: function (data){
//	        alert(data);
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
//	              var percentVal = Math.round(percentComplete*100) + "%";
	              console.log("percentComplete:"+percentComplete);
	              var percentVal = Math.round(percentComplete*100);
	              console.log("percentVal:"+percentVal);
	              $("#progressBar").css("width", percentVal + "%");
	              $("#uploadPercentage").html(percentVal);
//	              $("#percent").text(percentVal); // 進度條百分比文字
//	              $("#bar").width(percentVal);    // 進度條顏色
	            }
	        }, false);
	        return xhr; // 注意必須將xhr(XMLHttpRequest)物件回傳
	      }
	    }).fail(function(){
	    	console.log("發生錯誤。。。。");
	    	// 切換到失敗畫面
	    	$("div.txt-table").removeClass('select');
	    	$("div.txt-table.failure").addClass('select');
			$("div.txt-table.failure .txt-filename").html(file.files[0].name);
			$("#fileUpload").show();
			
//	      $("#percent").text("0%"); // 錯誤發生進度歸0%
//	      $("#bar").width("0%");
	    });
	        
//	  });
	
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
	console.log("aaaa");
	
//	var formData = new FormData($("#fileUploadForm")[0]);
//	$.ajax({
//	    type: "post",
//	    dataType: "json",
//	    url: "catalogProdFileUploadCSV.html",
//	    data:formData,
//	    timeout: 30000,
//	    error: function(xhr){
//	        alert('Ajax request 發生錯誤');
//	    },
//	    success:function(response, status){
//	    	window.location.replace("catalogProd.html");
//		}
//	});
	
	// 由檢查改為上傳
//	$("#fileUploadForm").attr("action", "catalogProdFileUploadCSV.html");
//	$("#fileUploadForm").submit();
//	window.location.replace("catalogProd.html");
	
	var formData = new FormData($("#fileUploadForm")[0]);
    $.ajax({
      type: "POST",
//      url: "catalogProdFileUploadCSV.html",
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




//function fileUploadSubmit_0926() {
//
//	$("#fileUploadForm").submit();
//}






var fileCount = 0; // 選擇檔案的數量
function fileUploadCSV(file) {
	$('#fileUpload').removeAttr("style");
	
    // 選擇取消，未選擇檔案不做處理
	fileCount = file.files.length;
	if (fileCount == 0) {
		return false;
	}
	
	// 檢查是否上傳CSV檔
	var type = file.accept;
	if (type.indexOf("csv") == -1) {
		alert("檔案格式有誤");
		$('#fileUpload').val("");
		return false;
	}
	
//	$('#fileName').val(file.files[0].name.substring(0 , file.files[0].name.lastIndexOf(".")));
//	$('#fileType').val(type);
	// 檔案正確調整css
	$('#fileUpload').css({'font-family':'Arial', 'font-size':'13px', 'font-weight':'normal', 'font-style':'normal', 'text-decoration':'none', 'color':'#009900'});
}

function fileUploadSubmit() {

	if (fileCount == 0) {
		alert("未選擇檔案!");
		return false;
	}

	$("#fileUploadForm").attr("action", "catalogProdFileUploadCSV.html");
	$("#fileUploadForm").submit();
}







//function aaaa(){
////	$("#modifyForm").attr("target", "fileUploadCSV");
////	$("#modifyForm").attr("action", "catalogProdFileUpload.html");
////	$("#modifyForm").submit();
//	
//	$("#myForm").attr("target", "fileUploadCSV");
//	$("#myForm").attr("action", "catalogProdFileUpload.html");
//	$("#myForm").submit();
//}
//
////$("#upload_file").click(function(){   
//function fileUp() {
//	//    var preview = 1;    
//	alert("AASDD");
//	var files = $("#fileUpload").get(0).files;
//	
////	var formData = new FormData($("#myForm")[0]);
//	var formData = new FormData();
//	//    formData.append("preview", preview);   
////	formData.append("fileUpload", $("#fileUpload")[0].files[0]);
//	formData.append("filename", "small");
//	formData.append("fileUpload", $("#fileUpload").val());
////	formData.append("filename", $("#fileUpload").get(0).files[0].name);
////	console.log(formData);
//	$.ajax({
//		url : 'catalogProdFileUpload.html',
//		data : formData,
////		dataType : "text",
//		type : "POST",
//		cache : false,
//		contentType : false,
//		processData : false,
//		error : function(xhr) {
//			alert('Ajax request 發生錯誤');
//		},
//		success : function(json) {
////			alert("success");
//		},
//		complete : function(json) {
//		}
//	});
//}