$(document).ready(function(){
	//新增圖文、圖像廣告時將最下面的已建立的分類關鍵字隱藏
	if($("#hiddenType").val() == 'YES'){
		$("#divExistKW").hide();
	}
	
	$('#saveKW').click(function(){
		saveData();
	});

	$('#cancelKW').click(function(event){
		event.preventDefault();
		history.back(1);
	});

	function saveData() {
		if(document.getElementsByName("keywords").length > 0 || document.getElementsByName("excludeKeywords").length > 0){
			// form submit
			$("#modifyForm").submit();
		} else {
			$('#chkAdKeyword').css("color","red");
			$('#chkAdKeyword').text("請輸入關鍵字");
		}
	}
});
