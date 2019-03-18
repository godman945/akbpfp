$(document).ready(function(){
	$('#btnGetAction').click(function(){
		if($('#existAction').val() != null && $('#existAction').val() != "") {
			var url = "adFreeGroupAdd.html?adActionSeq=" + $('#existAction').val();   
			if (/MSIE (\d+\.\d+);/.test(navigator.userAgent) || /MSIE(\d+\.\d+);/.test(navigator.userAgent)){
				var referLink = document.createElement('a');
				referLink.href = url;
				document.body.appendChild(referLink);
				referLink.click();
			} else {
				location.href = url;
			}
		} else {
			alert("請選擇現有的廣告.....");
		}
	});
	
	$('#existAction').change(function(){
		if($(this).val() != null && $(this).val() != "") {
			$.ajax({
				type: "POST",
				url: "getAdAction.html",
				data: { adActionSeq: $(this).val()}
			}).done(function( msg ) {
				//alert(msg);
				var adActionData = msg.split(",");
				$('#actionName').text(adActionData[0]);
				$('#actionDesc').text(adActionData[1]);
				$('#actionDate').text(adActionData[2] + " ~ " + adActionData[3]);
				$('#actionMax').text(adActionData[4]);
			});
			$('#newAction').hide();
			$('#existAd').show();
			$('#btnExist').show();
		} else {
			$('#actionName').text("");
			$('#actionDesc').text("");
			$('#actionDate').text("");
			$('#actionMax').text("");
			$('#newAction').show();
			$('#existAd').hide();
			$('#btnExist').hide();
		}
	});
	
	$('#btnInitial').click(function(){
		$('#existAction option').get(0).selected = true;
		$('#newAction').show();
		$('#existAd').hide();
		$('#btnExist').hide();
	});
	
	$('#freeAdCancel').click(function(){
		window.open('', '_self', '');
		window.close();
	});
});
