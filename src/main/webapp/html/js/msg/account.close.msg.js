$(document).ready(function(){
	
	$("#closeAccount input:button[name='yes']").click(function(){			
		parent.$.fancybox.close();
		parent.$('#modifyForm').submit();
	});
	
	$("#closeAccount input:button[name='no']").click(function(){		
		parent.$.fancybox.close();
	});
		
});