function page(){
    var pageNo = parseInt($("#pageNo").val());
    var selectPageSize = parseInt($("#selectPageSize").val());
    var pageCount = parseInt($("#pageCount").val());
    var contentPath = $("#contentPath").val();
    
    //pageButton 顯示第一頁
    if (pageNo > 1) {
        $("#fpage").attr("src", contentPath + "page_first.gif")
                    .css("cursor", "pointer");
        $("#ppage").attr("src", contentPath + "page_pre.gif")
                    .css("cursor", "pointer");
        
        //pageButton 第一頁
        $("#fpage").click(function(){
            wantSearch(1);
        });
        
        //pageButton 上一頁
        $("#ppage").click(function(){
            wantSearch(pageNo - 1);
        });
    }
    
    //pageButton 顯示最後一頁
    if (pageNo < pageCount) {
        $("#npage").attr("src", contentPath + "page_next.gif")
                    .css("cursor", "pointer");
        $("#epage").attr("src", contentPath + "page_end.gif")
                    .css("cursor", "pointer");
        
        //pageButton 下一頁
        $("#npage").click(function(){
            wantSearch(pageNo + 1);
        });
        
        //pageButton 最後一頁
        $("#epage").click(function(){
            wantSearch(pageCount);
        });
    }
    
    //每頁顯示數量正確數量顯示判斷
    $("#pageSize").children().each(function(){
        if ($(this).text() == selectPageSize) {
            $(this).attr("selected", "true"); //或是給selected也可
        }
    });
    
    //每頁顯示數量選擇
    $("#pageSize").change(function(){
        wantSearch(1);
    });
}