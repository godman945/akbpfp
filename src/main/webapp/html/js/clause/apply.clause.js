$(document).ready(function(){

	$("#next").attr("disabled",true);
	
	$("#accept").click(function() {

	   if($("#accept").attr("checked")){
		   $("#next").attr("disabled",false);
	   }else{
		   $("#next").attr("disabled",true);
	   }
		
	});
	
	
	$("#previous").click(function(){
		window.location = "apply.html";
	});

	$("#next").click(function(){
		
		if($("#accept").attr("checked")){
			$("#next").attr("disabled",true);
			$("#registerForm").submit();
		 }

	});
	
});


