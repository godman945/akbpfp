$(document).ready(function(){
	
	$("#closeStatus input:button[name='yes']").click(function(){
		parent.updateAdAdStatus();
		
	});
	
	$("#closeStatus input:button[name='no']").click(function(){
		parent.$.fancybox.close();
	});
		
	$("#checkBox input:button[name='yes']").click(function(){			
		parent.$.fancybox.close();
	});
	
	$("#stopStatus input:button[name='yes']").click(function(){
		parent.updateAdAdStatus();
		
	});
	
	$("#stopStatus input:button[name='no']").click(function(){
		parent.$.fancybox.close();
	});
});