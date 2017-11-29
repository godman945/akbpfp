$(document).ready(function(){
	
	$("#channelPrice input:button[name='no']").click(function(){
		parent.$.fancybox.close();
	});
	//計算播出率
	var submitFlag = true;
	$("#channelPrice input:text[name='userPrice']").keyup(function(){
		if($('#adGroupPriceType').val() == 'CPC'){
			submitFlag = true;
			// validate field
			$("#priceForm").validate({
				rules: {
					userPrice: {
						required: true,
						digits: true,
						min: 3,
						max: $("#channelPrice input:hidden[name='adActionMax']").val()
					}
			
				},
				messages: {
					userPrice: {
						required: "<br/>請填寫出價金額.",
						digits: "<br/>出價金額必需為整數.",
						min: "<br/>出價金額最少3元",
						max: "<br/>出價已超過每日廣告預算"+$("#channelPrice input:hidden[name='adActionMax']").val()+"元."
					}
				},
				errorPlacement:function(error, element){
					error.insertAfter(element.next().next());
					submitFlag = false;
				}
			});
			
			if($("#priceForm").valid() == 1){
				$.ajax({
		    		url: "adGroupSuggestPrice.html",
		    		data:{
		       			 "userprice": $("#channelPrice input:text[name='userPrice']").val()
		    		},
		    		type: "post",
		    		dataType: "json",
		    		success: function(response, status){    			
		    			$("#channelPrice label[name='adAsideRate']").html(response.adAsideRate);    			
		    		},
		    		error: function(xtl) {
		    			alert("系統繁忙，請稍後再試！");
		    		}
		    	});
			}
		}else{
			var errMsg = '';
			var price = $("#channelPrice input:text[name='userPrice']").val();
			if($('#adGroupPriceType').val() == 'CPV'){
				var cpvRex = /^-?\d+\.?\d{0,1}$/;
				submitFlag = cpvRex.test(price);
				if(!submitFlag){
					errMsg = '只接受整數或小數1位...';
				}
				if(price < 0.5){
					submitFlag = false;
					errMsg = '出價不得低於系統接受的最低單次收視出價 NT$0.5';
				}
				
			}else if($('#adGroupPriceType').val() == 'CPM'){
				var cpmRex = /^[0-9]*[1-9][0-9]*$/;
				submitFlag = cpmRex.test(price);
				if(!submitFlag){
					errMsg = '只接受整數...';
				}
				if(price < 65){
					submitFlag = false;
					errMsg = '出價不得低於系統接受的最低千次曝光出價 NT$65';
				}
			}
			
			if(!submitFlag){
				$('#errorMsg').empty();
				$('#errorMsg').append(errMsg);
				return false;
			}else{
				$('#errorMsg').empty();
				$("#userPrice").val(price);
			}
			
			$.ajax({
				url: "adGroupSuggestPrice.html",
				data:{
					"userprice": $("#userPrice").val()
				},
				type: "post",
				dataType: "json",
				success: function(response, status){
					$("#channelPrice label[name='adAsideRate']").html(response.adAsideRate);
				},
					error: function(xtl) {
						alert("系統繁忙，請稍後再試！");
					}
			 })
		}
	});
	
	$("#channelPrice input:button[name='yes']").click(function(){
		if(submitFlag){
    		var userPrice = $("#channelPrice input:text[name='userPrice']").val();
    		parent.$("#tableForm").attr("action","updAdGroupChannelPrice.html");    		
    		parent.$("#userPrice").val(userPrice);
    		parent.$("#tableForm").submit(); 
    		parent.$.fancybox.close();
    	}
	});
});
//儲存影音分類出價
function updatePrice(){
	parent.$("#videoPriceForm").attr("action","updAdGroupChannelPrice.html");    		
	parent.$("#videoPriceForm").submit(); 
	parent.$.fancybox.close();
}
