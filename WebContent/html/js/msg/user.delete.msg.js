$(document).ready(function(){
	
	$("#deleteUser input:button[name='yes']").click(function(){			
		parent.$.fancybox.close();
		parent.deleteUser();
	});
	
	$("#deleteUser input:button[name='no']").click(function(){		
		parent.$.fancybox.close();
	});
		
});