$(document).ready(function(){
	//切換商品目錄
	$("#catalog").change(function() {
		location.href = "prodListCardStyleView.html?catalogSeq="+ $('#catalog option:selected').val() + "&currentPage=1&pageSizeSelected=10";
	 });
});


function stopBubble(e) {
	if (e && e.stopPropagation) {
		e.stopPropagation();
	} else {
		window.event.cancelBubble = true;
	}
}

//複製篩選條件
function createPortfolio(catalogSeqStr,catalogGroupSeqStr) {
	location.href = "queryProdGroupFilterItem.html?catalogSeq="+catalogSeqStr+"&catalogGroupSeq="+catalogGroupSeqStr+"&currentPage="+1+"&pageSizeSelected="+4;
}
    

//檢查廣告是否有綁定的商品組合
function checkCatalogGroupAdStatus(dataObj){
	alert($(dataObj).attr('value'))
	var catalogGroupSeq = $(dataObj).attr('value');
	var alt = "";
		$.ajax({
			type : "POST",
			dataType :'json',
			url : "checkCatalogGroupAdStatus.html",
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
			alert('response.resultMap.status')
			alert(response)
			alert('status')
			if (response.resultMap.status == "SUCCESS") {
				if (response.resultMap.count != "0"){
					alert("有bind ad！");
					if(confirm("此商品組合有綁定廣告，請確認是否刪除！")) {
						deleteCatalogGroupAjax(catalogGroupSeq)
					}
				}else{
					alert("沒有bind ad ---------！");
					deleteCatalogGroupAjax(catalogGroupSeq)
				}
			} else {
				alert(response);
				alert(response.resultMap.msg)
			}
		});
}

//刪除商品組合
function deleteCatalogGroupAjax(catalogGroupSeq){
	$.ajax({
		type : "POST",
		dataType :'json',
		url : "deleteCatalogGroup.html",
		data : {
			"catalogGroupSeq":catalogGroupSeq
		},
		timeout : 30000,
		error : function(xhr) {
			alert("系統繁忙，請稍後再試！");
		},
		success : function(response, status) {
			
		}
	}).done(function(response) {
		if (response.resultMap.status == "SUCCESS") {
//			alert(response.resultMap.msg)
			$(location).attr('href',"queryCatalogGroup.html?catalogSeq="+ $('#catalog option:selected').val());
		} else {
			alert(response.resultMap.msg)
		}
	});
}
