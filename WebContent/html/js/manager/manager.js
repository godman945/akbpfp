$(document).ready(function(){
	
	tableSorter();
	
});

function changeAccount(id){
	//alert(id);
	$("#accountId").val(id);
	$("#formChange").submit();
}

function tableSorter(){
	
	$("#tableList").tablesorter({
		headers:{			
			5 : { sorter: 'rangesort' },
			8 : { sorter: 'rangesort' },
			9 : { sorter: false }
		}
		,
		textExtraction: function (node) { 
			 
	        if (node.innerHTML.length == 0)
	        {
	            return "999999999";  // or some suitably large number!
	        }
	        else
	        {
	            return node.innerHTML;
	        }           
	    }       
	});
}