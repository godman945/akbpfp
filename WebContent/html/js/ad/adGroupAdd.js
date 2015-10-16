$(document).ready(function(){

	var showSearchPrice = $("#showSearchPrice").val();
	if(showSearchPrice == "no"){
		$("#searchTr").hide();
	}
	
	var showChannelPrice = $("#showChannelPrice").val();
	if(showChannelPrice == "no"){
		$("#channelTr").hide();
	}
	
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
