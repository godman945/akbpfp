$(document).ready(function(){
	$('#btnGetGroup').click(function(){
		if($('#existGroup').val() != null && $('#existGroup').val() != "") {
			refererHref("adFreeAdAdd.html?adGroupSeq=" + $('#existGroup').val());
		} else {
			alert("請選擇現有的分類.....");
		}
	});
	
	$('#existGroup').change(function(){
		if($(this).val() != null && $(this).val() != "") {
			$.ajax({
				type: "POST",
				url: "getAdGroup.html",
				data: { adGroupSeq: $(this).val()}
			}).done(function( msg ) {
				//alert(msg);
				var adGroupData = msg.split(",");
				$('#groupName').text(adGroupData[0]);
				$('#groupSearchPrice').text(adGroupData[1] + " => " + adGroupData[2]);
				$('#groupChannelPrice').text(adGroupData[3]);
			});
			$('#newGroup').hide();
			$('#existAd').show();
			$('#btnExist').show();
		} else {
			$('#groupName').text("");
			$('#groupSearchPrice').text("");
			$('#groupChannelPrice').text("");
			$('#newGroup').show();
			$('#existAd').hide();
			$('#btnExist').hide();
		}
	});
	
	$('#btnInitial').click(function(){
		$('#existGroup option').get(0).selected = true;
		$('#newGroup').show();
		$('#existAd').hide();
		$('#btnExist').hide();
	});

	$('#cancel').click(function(){
		$("#modifyForm")[0].reset();
		//window.location.href = $("#backPage").val();
		refererHref($("#backPage").val());
	});
	
	function refererHref(url) {
		if (/MSIE (\d+\.\d+);/.test(navigator.userAgent) || /MSIE(\d+\.\d+);/.test(navigator.userAgent)){
			var referLink = document.createElement('a');
			referLink.href = url;
			document.body.appendChild(referLink);
			referLink.click();
		} else {
			location.href = url;
		}
	}
});
