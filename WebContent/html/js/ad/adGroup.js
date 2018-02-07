$(document).ready(function(){
	$.validator.addMethod(
		"getRate",
		function(value, element, params) {
			$.ajax({
				url: "getAdAsideRate.html",
				data: {
					adActionMax: $("#adGroupChannelPrice").val()
				},
				type: "post",
				success: function(response, state){
					if(response != null) {
						if($.isNumeric(response)) {
							$("#showRate").text(response + "%");
						} else {
							alert("您尚未登入帳號，請先登入帳號後，再進行新增，謝謝您!!!");
							$(location).attr('href',"index.html");
						}
					} else {
						$("#showRate").text("0%");
					}
				},
				error: function(xtl) {
					errorDialogBtn("錯誤訊息","系統繁忙，請稍後再試！");
				}
			});
			return true;
	    },
	    '');

	var showSearchPrice = $("#showSearchPrice").val();
	if(showSearchPrice == "no"){
		$("#searchTr").hide();
	}
	
	var showChannelPrice = $("#showChannelPrice").val();
	if(showChannelPrice == "no"){
		$("#channelTr").hide();
	}
	
	// validate field
	var submitFlag = true;
	if($('#adGroupPriceType').val() == 'CPC'){
		submitFlag = true;
		$("#modifyForm").validate({
			rules: {
				adGroupName: {
					required : true,
					maxlength : 20,
					remote: {
						url: "chkAdGroupName.html",     //後台處理程序
						type: "post",               //數據發送方式
						data: {                     //要傳遞的數據，默認已傳遞應用此規則的表單項
							adGroupName: function() {
								return $("#adGroupName").val();
							},
							adGroupSeq: function() {
								return $("#adGroupSeq").val();
							},
							adActionSeq: function() {
								return $("#adActionSeq").val();
							}
						}
					}
				},
				searchprice: "required",
				adGroupSearchPrice: {
					required: {
						depends: function() {
							return $('input[name=adGroupSearchPriceType]:checked').val() == '2';
						}
					},
					digits: {
						depends: function() {
							return $('input[name=adGroupSearchPriceType]:checked').val() == '2';
						}
					},
					min: 3,
					max: $('#adActionMax').val()
				},
				adGroupChannelPrice: {
					required: true,
					digits: true,
					min: 3,
					max: $('#adActionMax').val(),
					getRate: "#adGroupChannelPrice"
				}
			},
			messages: {
				adGroupName: {
					required: "請填寫廣告分類名稱.",
					maxlength : "分類名稱輸入字數已超過上限 20 字",
					remote: "廣告分類名稱重複，請重新輸入"
				},
				searchprice: "請選擇搜尋廣告出價.",
				adGroupSearchPrice: {
					required:  "請填寫搜尋廣告出價.",
					digits: "搜尋廣告出價只能填寫數字.",
					min: "搜尋廣告出價最少為3元",
					max: "出價已超過廣告每日預算 " +$('#adActionMax').val()+ " 元"
				},
				adGroupChannelPrice: {
					required:  "請填寫聯播廣告出價.",
					digits: "聯播廣告出價只能填寫數字.",
					min: "聯播廣告出價最少為3元",
					max: "出價已超過廣告每日預算  "+$('#adActionMax').val()+" 元",
					getRate: ""
				}
			},
			errorPlacement:function(error, element){
				error.insertAfter($('#adGroupChannelPrice').next());
				submitFlag = false;
			}
		});
	}else{
		$("#adGroupChannelPrice").keyup(function(){
			var errMsg = '';
			var price = $("#adGroupChannelPrice").val()
			
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
					"userprice": $("#adGroupChannelPrice").val()
				},
				type: "post",
				dataType: "json",
				success: function(response, status){
					$("#showRate").html(response.adAsideRate);
				},
					error: function(xtl) {
						alert("系統繁忙，請稍後再試！");
					}
			 })
		});
	}
	
	//新增分類時，計費方式為CPC檢查用
	if($('#adOperatingRule').val() == 'MEDIA'){
		$('#adGroupChannelPrice').change(function(){
			submitFlag = true;
			$("#errorMsg").text('');
			
			var adGroupChannelPrice = $('#adGroupChannelPrice').val();
			if(/[^0-9]$/.test(parseInt(adGroupChannelPrice))){
				$("#errorMsg").text('聯播廣告出價只能填寫數字');
				submitFlag = false;
			}else if (parseInt(adGroupChannelPrice) < 3) {
				$("#errorMsg").text('聯播廣告出價最少為3元');
				submitFlag = false;
			}else if (parseInt(adGroupChannelPrice) > parseInt($('#adActionMax').val())) {
				$("#errorMsg").text('出價已超過每日廣告預算' + $('#adActionMax').val() + '元');
				submitFlag = false;
			}
		})
	}
	
	$('#save').click(function(){
		//取得驗證回傳值
		if(submitFlag){
			$("#modifyForm").submit();
		}
	});
});



function opennots(id) {
	$("#shownotes"+id).css("visibility", "visible");
}

function closenots(id) {
	$("#shownotes"+id).css("visibility", "hidden");
}
