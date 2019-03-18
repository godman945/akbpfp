$(document).ready(function(){
	
	$("#openAccount input:button[name='yes']").click(function(){		
		parent.$.fancybox.close();
		parent.$('#modifyForm').submit();
	});
	
	$("#openAccount input:button[name='no']").click(function(){	
		parent.$.fancybox.close();
		
	});
		
});