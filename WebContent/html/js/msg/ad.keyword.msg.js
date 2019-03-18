$(document).ready(function(){
	
	$("#closeStatus input:button[name='yes']").click(function(){
		parent.updateAdKeywordStatus();	
	});
	
	$("#closeStatus input:button[name='no']").click(function(){
		parent.$.fancybox.close();
	});
		
	$("#checkBox input:button[name='yes']").click(function(){			
		parent.$.fancybox.close();
	});
	
	$("#stopStatus input:button[name='yes']").click(function(){
		parent.updateAdKeywordStatus();	
	});
	
	$("#stopStatus input:button[name='no']").click(function(){
		parent.$.fancybox.close();
	});
	
	$("#changeType input:button[name='yes']").click(function(){
		parent.updateKeywordOpenData("off");
		parent.$.fancybox.close();
	});
	
	$("#changeType input:button[name='no']").click(function(){
		parent.notChangType();
		parent.$.fancybox.close();
	});
	
	$("#closeType input:button[name='yes']").click(function(){			
		parent.$.fancybox.close();
	});
});