$(document).ready(function(){
	console.log("DD");
	
	
	$.ajax({
		type: "POST",
		url: "chkVideoUrl.html",
		data: { adVideoUrl: "https://www.youtube.com/watch?v=x0WSucyB5hU"}
	}).done(function(msg) {
		console.log(msg);
	});
	
});

