$(document).ready(function(){
	
	$("#closeStatus input:button[name='yes']").click(function(){
		parent.updateAdActionStatus();
		
	});
	
	$("#closeStatus input:button[name='no']").click(function(){
		parent.$.fancybox.close();
	});
	
	$("#checkBox input:button[name='yes']").click(function(){			
		parent.$.fancybox.close();
	});
		
	$("#stopStatus input:button[name='yes']").click(function(){
		parent.updateAdActionStatus();
		
	});
	
	$("#stopStatus input:button[name='no']").click(function(){
		parent.$.fancybox.close();
	});
});