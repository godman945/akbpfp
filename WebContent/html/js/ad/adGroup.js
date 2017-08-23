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
		}
	});
	
	
	
	
	
	$('#save').click(function(){

		if($("#adOperatingRule").val() == 'VIDEO'){
			var result = checkVideoErrMsg();
			if(!result.flag){
				return false
			}
		}
		
		//取得驗證回傳值
		if($("#modifyForm").valid() == 1){
			// form submit
			$("#modifyForm").submit();
		}
	});
});


function checkVideoErrMsg(){
	var adPrice = $("#adPrice").val();
	if($("#adPriceType").val() == 0){
		if(adPrice < 0.5){
			$(".errMsg").text('出價不得低於系統接受的最低單次收視出價 NT$0.5');
			return {"flag":false};
		}
	}else if($("#adPriceType").val() == 1){
		if(adPrice < 65){
			$(".errMsg").text('出價不得低於系統接受的最低千次曝光出價 NT$65');
			return {"flag":false};
		}
	}
	$(".errMsg").text('');
	return {"flag":true};
}

function opennots(id) {
	$("#shownotes"+id).css("visibility", "visible");
}

function closenots(id) {
	$("#shownotes"+id).css("visibility", "hidden");
}
