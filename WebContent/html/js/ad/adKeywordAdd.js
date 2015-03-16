$(document).ready(function(){
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
