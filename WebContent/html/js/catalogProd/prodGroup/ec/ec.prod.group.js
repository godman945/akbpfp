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

//編輯篩選條件
function editPortfolio(catalogSeqStr,catalogGroupSeqStr) {
	location.href = "editProdGroupFilterItem.html?catalogSeq="+catalogSeqStr+"&catalogGroupSeq="+catalogGroupSeqStr+"&currentPage="+1+"&pageSizeSelected="+4;
}
    

//檢查廣告是否有綁定的商品組合
function checkCatalogGroupAdStatus(dataObj){
//	alert($(dataObj).attr('value'))
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
			console.log(response.status);
			if (response.status == "SUCCESS") {
				if (response.count != "0"){
//					alert("有bind ad！");
					if(confirm("提醒您，刪除商品組合將有可能會影響正在播放中的廣告，您確定要刪除嗎?")) {
						deleteCatalogGroupAjax(catalogGroupSeq)
					}
				}else{
//					alert("沒有bind ad ---------！");
					if(confirm("您確定要刪除此商品組合嗎?")) {
						deleteCatalogGroupAjax(catalogGroupSeq)
					}
				}
			} else {
//				alert(response);
				alert(response.msg)
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
		if (response.status == "SUCCESS") {
//			alert(response.msg)
			$(location).attr('href',"queryCatalogGroup.html?catalogSeq="+ $('#catalog option:selected').val());
		} else {
//			alert(response.resultMap.msg)
			alert(response.msg)
		}
	});
}
