$(document).ready(function(){
	
	//切換商品目錄
	$("#catalog").change(function() {
		location.href = "prodListTableStyleView.html?catalogSeq="+ $('#catalog option:selected').val() + "&currentPage=1&pageSizeSelected=10";
	 });
	
});



