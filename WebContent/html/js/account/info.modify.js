$(document).ready(function(){
	
	// validate field
	$("#modifyForm").validate({
		rules: {
			accountTitle:{
				required: true
			},
			companyTitle:{
				required: "#company:checked"
			},
			registration: {
				required: "#company:checked",
				 digits: true,
				minlength: 8
			},
			urlAddress: {
				required: true
			}
		},
		messages: {
			accountTitle:{
				required: "請填寫帳戶名稱."
			},
			companyTitle:{
				required: "請填寫公司名稱."
			},
			registration: {
				required: "請填寫統一編號.",
				digits: "統一編號只能填寫數字.",
				minlength: "統一編號為八碼數字."
			},
			urlAddress: {
				required: "請填寫宣傳網址."
			}
		}
	});
	
	var category = $("input[name='category']:checked").val();
	
	if(category=="1"){
		$("tr:eq(3)").hide();
		$("tr:eq(4)").hide();
	}
	
	$("#customer").click(function(){
		$("tr:eq(3)").hide();
		$("tr:eq(4)").hide();
	});

	$("#company").click(function(){
		$("tr:eq(3)").show();
		$("tr:eq(4)").show();
	});
	
	var state = $("input[name='state']:checked").val();
	
	$("#cancel").click(function(){
		 window.location = "accountInfo.html";
	});
	
	//帳戶名稱鍵盤件鍵檢查
	$('#accountTitle').bind('keyup', function() {
		chkWord($("#accountTitle"), $("#spanAccountTitle"));
	});
	
	$('#save').click(function(){
		
		//取得驗證回傳值
		if($("#modifyForm").valid() == 1){
			
			var urlAddress = $("#urlAddress").val();
			
			$.blockUI({
				message: "<img src='html/img/LoadingWait.gif' />",
				css: {},
				overlayCSS: { backgroundColor: '#FFFFFF', opacity: 0.6, border: '1px solid #000000' }
			});
			
			//先確認是否無效網址
			$.ajax({
				url: "checkUrl.html",
				data: {
					url: urlAddress
				},
				type: "post",
				dataType: "json",
				success: function(response, state){
					
					$.unblockUI();
					
					var dbStatus = $("#dbStatus").val();

					var selectStatus = $("input:radio[name=status]:checked").val();
					
					if(response.urlState == 200){
						
						if(parseInt(dbStatus) == parseInt(selectStatus)){
							
							$('#modifyForm').submit();
						}
						else{
							if(parseInt(selectStatus) == 1){								
								$.fancybox({
									'href'     :'openAccountMsg.html'  		                    
								});
								
							}
							if(parseInt(selectStatus) == 0){
								$.fancybox({
									'href'     :'closeAccountMsg.html'	                    
								});
							}
						}
					}else{
						$("<label generated='true' class='error'>網址連結失效，請輸入有效連結網址！</label>").insertAfter("#urlMsg");
					}
				},
				error: function(xtl) {
					alert("系統繁忙，請稍後再試！");
				}
			});
		}
		
	});
	
});

//輸入字數檢查與提示
function chkWord(valObj, msgObj) {
	var length = parseInt(valObj.val().length);
	var maxlength = parseInt(valObj.attr("maxlength"));
	
	msgObj.css("color","");
	msgObj.text("");
	
	if (length <= maxlength) {
		msgObj.css("color","");
		msgObj.text("已輸入" + length + "字，剩" + (maxlength - length) + "字");
		return true;
	} else if(length > maxlength){
		msgObj.css("color","red");
		msgObj.text("已輸入" + length + "字，超過" + (length - maxlength) + "字");
		return false;
	}
	
	return false;
}
