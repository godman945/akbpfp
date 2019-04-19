$(document).ready(function(){

	$("#priceForm").validate({
		rules: {
			searchPrice: {
				required: "#definePrice:checked",
				 digits: true,
				 min: 3,
				 max: $("#searchPrice input:hidden[name='adActionMax']").val()
			}
		},
		messages: {
			searchPrice: {
				required: "請填寫分類出價金額.",
				digits: "分類出價金額只能填寫數字.",
				min: "出價金額至少要3塊錢",
				max: "出價已超過每日廣告預算"+$("#searchPrice input:hidden[name='adActionMax']").val()+"元."
			}
		}
	});
	
	var searchPriceType = parent.$("#searchPriceType").val();
	
	if(parseInt(searchPriceType) == 1){
		$("#sysPrice").attr('checked', true);
	}else{
		$("#definePrice").attr('checked', true);
	}
	
	
	
	
	$(document).ajaxStart(function(){
		
		$.blockUI({
			message: '<b><font size=5>'+'儲存中...'+'</font></b><img src="./html/img/LoadingWait.gif" width="" height="" />',
		    css: {
		        border: '3px solid #aaa',
		        padding: '15px',
		        backgroundColor: '#fff',
		        '-webkit-border-radius': '10px',
		        '-moz-border-radius': '10px',
		        opacity: .9,
		        textAlign:      'center',
		        cursor:         'wait',
		    }
		});
		}).ajaxStop($.unblockUI);
	
	
	
	$("#searchPrice input:button[name='yes']").click(function(){
		
		var searchPriceType = $("#searchPrice input[name='searchPriceType']:checked").val();
	if($("#priceForm").valid() == 1){
		parent.$("#searchPriceType").val(searchPriceType);
		parent.$("#adGroupSearchPrice").val($("#searchPrice input[name='searchPrice']").val());
		parent.$.fancybox.close();
		$.ajax({
			url : "updAdGroupSearchPrice.html",
			type : "POST",
			dataType : "json",
			data : {
				"searchPriceType" : searchPriceType,
				"adGroupSearchPrice" : $("#searchPrice input[name='searchPrice']").val(),
				"adActionSeq" : $("#adActionSeq").val(),
				"adGroupSeq" : $("#adGroupSeq").val(),
				"groupMaxPrice" :$("#groupMaxPrice").val()
				
			},
			success : function(respone) {
				$(location).attr( 'href' , 'adGroupView.html?adActionSeq='+$("#adActionSeq").val()+'&groupMaxPrice='+$("#groupMaxPrice").val());
			},
			error:function(respone) {
				
			}
		}).done(function( msg ) {
		
		})
	}
});
		
		
		
//		if($("#priceForm").valid() == 1){
//
//			var searchPriceType = $("#searchPrice input[name='searchPriceType']:checked").val();
//			parent.$("#tableForm").attr("action","updAdGroupSearchPrice.html"); 
//			parent.$("#searchPriceType").val(searchPriceType);
//			parent.$("#adGroupSearchPrice").val($("#searchPrice input[name='searchPrice']").val());
//			parent.$("#tableForm").submit();
//
//			parent.$.fancybox.close();
//		}
		
	
	
	$("#searchPrice input:button[name='no']").click(function(){
		parent.$.fancybox.close();
	});   
		
});