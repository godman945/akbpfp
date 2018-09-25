$(document).ready(function() {
	console.log("AAAAAAAAAAXXXXXXXX");
	
	
	// 查詢目錄
//	$("#addCatalog").click(function(){
//		$.ajax({
//		    type: "post",
//		    url: "queryCatalog.html",
//		    data: {
//		          "queryString" : $('#queryString').val(),
//		          "pageSize": $('#pageSizeSelect').val()
////		          "pageNo": $('#formPage').val()
//		      },
//		    timeout: 30000,
//		    error: function(xhr){
//		        alert('Ajax request 發生錯誤');
//		    },
//		    success: function(response){
//		        $('.container-prodmanage').html(response);
//		    }
//		});
//	});
	
	
	processPageNumber();
    
	processQuery();
});

/**
 * 刪除目錄
 * @param catalogSeq
 */
function deletePfpCatalog(catalogSeq){
	console.log("catalogSeq:" + catalogSeq);
	
	$.ajax({
	    type: "post",
	    url: "deleteCatalog.html",
	    data: {
	          "deleteCatalogSeq" : catalogSeq
//	          "pageSize": $('#pageSizeSelect').val()
//	          "pageNo": $('#formPage').val()
	      },
	    timeout: 30000,
	    error: function(xhr){
	        alert('Ajax request 發生錯誤');
	    },
	    success: function(response){
//	        $('.container-prodmanage').html(response);
//	    	$("#addCatalog").trigger('click');
	    	window.location = "catalogProd.html";
	    }
	});
}

/**
 * 處理查詢事件
 */
function processQuery(){
	var timeIndex;
	$("#queryString").on({
		
		keydown:function(){ 
			console.log("timeIndex:" + timeIndex);
			clearTimeout(timeIndex);
		},
		keyup:function(event){
			
			// 可能會要判斷 鍵盤按鍵代碼，先準備
			console.log(event.which);
			
			timeIndex = setTimeout(function() {
				console.log("test look");
				$.ajax({
				    type: "post",
				    url: "queryCatalog.html",
				    data: {
				          "queryString" : $('#queryString').val(),
				          "pageSize": $('#pageSizeSelect').val()
	//			          "pageNo": $('#formPage').val()
				      },
				    timeout: 30000,
				    error: function(xhr){
				        alert('Ajax request 發生錯誤');
				    },
				    success: function(response){
				        $('.container-prodmanage').html(response);
//				        $("#queryString").focus(); //ftl調整的話，應該不用此方式
				      
				    }
				});
			}, 1000);
		}
		
//		keydown:function(){ console.log("aaa"); }
//	    mouseover:function(){$("body").css("background-color","lightgray");},  
//
//	    mouseout:function(){$("body").css("background-color","lightblue");}, 
//
//	    click:function(){$("body").css("background-color","yellow");}  

	  });
}

/**
 * 處理頁碼按鈕事件
 */
function processPageNumber(){
	//參考用
    var pagedata = $('.pagination-wrap').data();
    var order = pagedata.order;
    var quantity = pagedata.quantity;

    //設定頁碼
    setPagination(order, quantity);

    function setPagination(order, quantity){
        var $buttongroup=$('.pagination-buttongroup a');
        var $buttonright=$('a.pagination-button.right');
        //計算頁碼起始顯示值 +修改目前所在頁碼按鈕底色
        var _length=$buttongroup.length;
        var _order=(order<=_length)?1:order;
            _order=(quantity-order<=_length)?quantity-_length+1:_order;
            _order=(_order>_length &&_order%_length!=0)?(_order-_order%_length+1):_order;
        //寫入頁碼
        for(var i=0;i<_length;i++){
            $buttongroup.eq(i).attr('data-num',_order+i);
            if((_order+i)==order) $buttongroup.eq(i).addClass('active');
        }
        //寫入最後一頁頁碼 + 最後一頁時修改按鈕底色
        $buttonright.attr('data-num', quantity);
        if(order==quantity) $buttonright.addClass('active');
    }
}