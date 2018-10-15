$(document).ready(function(){
	
//	if($("#catalogSeqData").val() !=""){
//		console.log("#####################999999999");
//		$("#catalog").val($("#catalogSeqData").val());
//	}

});


function stopBubble(e) {
	if (e && e.stopPropagation) {
		e.stopPropagation();
	} else {
		window.event.cancelBubble = true;
	}
}


function createPortfolio(catalogSeqStr,catalogGroupSeqStr) {
	location.href = "queryProdGroupFilterItem.html?catalogSeq="+catalogSeqStr+"&catalogGroupSeq="+catalogGroupSeqStr;
}
    

function deleteCatalogGroupAjax(dataObj){
	var alt = "確定刪除商品組合？";
	if(confirm(alt)) {
		$.ajax({
			type : "POST",
			dataType :'json',
			url : "deleteCatalogGroup.html",
			data : {
				"catalogGroupSeq":$(dataObj).attr('value')
			},
			timeout : 30000,
			error : function(xhr) {
				alert("系統繁忙，請稍後再試！");
			},
			success : function(response, status) {
				
			}
		}).done(function(response) {
			if (response.resultMap.status == "SUCCESS") {
				alert(response.resultMap.msg)
				$(location).attr('href',"queryCatalogGroup.html?catalogSeq="+ $('#catalog option:selected').val());
			} else {
				alert(response.resultMap.msg)
			}
		});
	}
}
