$(document).ready(function(){

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
		}
	});
	
	$("#channelPrice input:button[name='yes']").click(function(){
		
		if($("#priceForm").valid() == 1){
    		var userPrice = $("#channelPrice input:text[name='userPrice']").val();
    		parent.$("#tableForm").attr("action","updAdGroupChannelPrice.html");    		
    		parent.$("#userPrice").val(userPrice);
    		parent.$("#tableForm").submit(); 
    		parent.$.fancybox.close();
    	}
		
	});
	
	$("#channelPrice input:button[name='no']").click(function(){
		parent.$.fancybox.close();
	});
	
	// 計算播出率
    $("#channelPrice input:text[name='userPrice']").keyup(function(){	
    	
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
    	
	});
    
		
});