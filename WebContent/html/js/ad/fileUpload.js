var fileCount = 0; // 選擇檔案的數量
function fileUploadCSV(file) {
	$('#fileUpload').removeAttr("style");
	
    // 選擇取消，未選擇檔案不做處理
	fileCount = file.files.length;
	if (fileCount == 0) {
		return false;
	}
	
	// 檢查是否上傳csv檔
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