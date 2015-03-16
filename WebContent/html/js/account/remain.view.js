$(document).ready(function(){
	
	// 同意條款
	$("#accept").click(function() {

		   if($("#accept").attr("checked")){
			   $("#save").attr("disabled",false);
		   }else{
			   $("#save").attr("disabled",true);
		   }
			
	});
	
	// validate field
	$("#addMoneyForm").validate({
		rules: {
			addMoney: {
				required: true,
				 digits: true,
				 min: 500
			}
		},
		messages: {
			addMoney: {
				required: "請填儲值金額.",
				digits: "儲值金額只能填寫數字.",
				min: "儲值金額至少要500元."
			}
		}
	});
	
	$("#addMoney").keyup(function(){
		
		var addMoney = $("#addMoney").val();
		
		if(addMoney >= 500){
			var addTax = Math.round(addMoney * 0.05);
			var total = Math.round(addMoney * 1.05);
			
			$("#addTax").html(addTax);
			$("#total").html(total);
		}else{
			$("#addTax").html(0);
			$("#total").html(0);
		}
	});
	
	$("#save").click(function(){
		
		//取得驗證回傳值
		if($("#addMoneyForm").valid() == 1){
			$('#addMoneyForm').submit();
		}
	});
	
});
