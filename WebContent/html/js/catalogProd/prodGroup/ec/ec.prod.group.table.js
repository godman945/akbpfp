$(document).ready(function(){
	
	//floating scrollbar
    $('.floatingscroll').floatingScrollbar();
	
	if($("#catalogSeqData").val() !=""){
		$("#catalog").val($("#catalogSeqData").val());
	}
	
	//切換商品目錄
	$("#catalog").change(function() {
		location.href = "prodListTableStyleView.html?catalogSeq="+ $('#catalog option:selected').val() + "&currentPage=1&pageSizeSelected=10";
	 });
	
	
	//每頁顯示數量選擇
	$("#pageSizeSelect").change(function() {
		$('.pagination-wrap').data('order', "1");
		queryProdGroupListAjax();
	});
	
	//按首頁
	$('#firstPageBtn').click(function(){
		$('.pagination-wrap').data('order', "1");
		queryProdGroupListAjax();
	});
	
	//按末頁
	$('#finalPageBtn').click(function(){
		$('.pagination-wrap').data('order', $('#finalPageBtn').attr('data-num'));
		queryProdGroupListAjax();
	});
	
	//1~10頁碼
	$('.pagination-buttongroup a').click(function(){
		$('.pagination-wrap').data('order', $(this).attr('data-num'));
		queryProdGroupListAjax();
	});
	
	//上10頁
	$('#previousPageBtn').click(function(){
		//只要目前頁碼 為10的倍數，即為了運算頁碼要先-1
	    var currentPageInt= parseInt($('#pageData').data('order'));
	    
	    currentPageInt =currentPageInt-10;
	    if (currentPageInt % 10 == 0){
	    	 orderTmp = currentPageInt-1;
	    }else{
	    	orderTmp = currentPageInt;
	    }
	    var pageHeaderNoStr= orderTmp.toString();
      	var pageHeaderNoStr= pageHeaderNoStr.substring(0, pageHeaderNoStr.length-1);
      	var _order =parseInt( pageHeaderNoStr.toString()+"1");
      	$('.pagination-wrap').data('order', _order.toString());

      	queryProdGroupListAjax();
	});
	
	//下10頁
	$('#nextPageBtn').click(function(){
		//只要目前頁碼 為10的倍數，即為了運算頁碼要先-1
	    var currentPageInt= parseInt($('#pageData').data('order'));
	    
	    currentPageInt =currentPageInt+10;
	    if (currentPageInt % 10 == 0){
	    	 orderTmp= currentPageInt-1;
	    }else{
	    	orderTmp = currentPageInt;
	    }
	    var pageHeaderNoStr= orderTmp.toString();
      	var pageHeaderNoStr= pageHeaderNoStr.substring(0, pageHeaderNoStr.length-1);
      	var _order =parseInt( pageHeaderNoStr.toString()+"1");
      	$('.pagination-wrap').data('order', _order.toString());
      	
      	queryProdGroupListAjax();
	});
	
	//處理頁數與總頁數及按鈕
	processPageAndTotalPage();
});


/**
* 取得商品組合清單Ajax
*/
function queryProdGroupListAjax(){
	$.ajax({
	    type: "post",
	    dataType: "json",
	    url: "queryProdGroupListAjax.html",
	    data: {
    	    "catalogGroupSeq": $('#catalogGroupSeqId').data('groupseq'),
   	        "currentPage": $('#pageData').data('order'),
   	        "pageSizeSelected":  $('#pageSizeSelect option:selected').val()
	    },
	    timeout: 30000,
	    error: function(xhr){
	    	alert("系統繁忙，請稍後再試！");
	    },
	    success:function(response, status){
	    	
		}
	}).done(function (response) {
    	//更新頁碼與清單資料
    	$('.pagination-wrap').data('order', response.currentPage.toString());
    	$('.pagination-wrap').data('quantity', response.pageCount.toString());
        
    	if (response.status=="ERROR"){
    		$("#prodListDiv").empty();
    		alert(response.msg)
    	}else{
    		$("#prodListDiv").empty();
    		var tempHtml = "";
	    	$.each(response.prodList, function(index, list){
	    		var id ="";
	    		var ecImg ="";
	    		var ecName ="";
	    		var ecPrice ="";
	    		var ecDiscountPrice ="";
	    		var catalogProdSeq ="";
	    		var ecStockStatus ="";
	    		var ecUseStatus ="";
	    		var ecCategory ="";
	    		var ecUrl ="";
	    		var ecStatus ="";
	    		$.each(list, function(key, val){
	    			
	    			if(key == "id"){
	    				id = val;
	    			}
	    			if(key == "ecImg"){
	    				ecImg = val;
	    			}
	    			if(key == "ecName"){
	    				ecName = val;
	    			}
	    			if(key == "ecPrice"){
	    				ecPrice = val;
	    			}
	    			if(key == "ecDiscountPrice"){
	    				ecDiscountPrice = val;
	    			}
	    			if(key == "catalogProdSeq"){
	    				catalogProdSeq = val;
	    			}
	    			if(key == "ecStockStatus"){
	    				ecStockStatus = val;
	    			}
	    			if(key == "ecStockStatusDesc"){
	    				ecStockStatusDesc = val;
	    			}
	    			if(key == "ecUseStatus"){
	    				ecUseStatus = val;
	    			}
	    			if(key == "ecUseStatusDesc"){
	    				ecUseStatusDesc = val;
	    			}
	    			if(key == "ecCategory"){
	    				ecCategory = val;
	    			}
	    			if(key == "ecUrl"){
	    				ecUrl = val;
	    			}
	    			if(key == "ecStatus"){
	    				ecStatus = val;
	    			}
	    		});
	    			tempHtml += " <div class='txt-row'> ";
	    			tempHtml += " 	<div class='txt-cell col-serial'>"+catalogProdSeq+"</div> ";
	    			tempHtml += " 	<div class='txt-cell col-prodname'>"+ecName+"</div> ";
	    			tempHtml += " 	<div class='txt-cell col-listprice'><span>$</span>"+ecPrice+"</div> ";
	    			tempHtml += " 	<div class='txt-cell col-promoprice'><span>$</span>"+ecDiscountPrice+"</div> ";
	    			tempHtml += " 	<div class='txt-cell col-supplement'>"+ecStockStatusDesc+"</div> ";
	    			tempHtml += " 	<div class='txt-cell col-neworused'>"+ecUseStatusDesc+"</div> ";
	    			tempHtml += " 	<div class='txt-cell col-picture'><img src='http://showstg.pchome.com.tw/pfp/"+ecImg+"'></div> ";
	    			tempHtml += " 	<div class='txt-cell col-class'>"+ecCategory+"</div> ";
	    			tempHtml += " 	<div class='txt-cell col-weburl'><a href="+ecUrl+" target='_blank'></a></div> ";
	    			tempHtml += " </div> ";
	    	});
	    	$('#prodListDiv').html(tempHtml);
    	}
	    	//整理頁碼
	    	processPageAndTotalPage();
	    });
}


/**
* 處理頁數與總頁數及按鈕
*/
function processPageAndTotalPage(){
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
            
            
            //只要 目前頁碼 為10的倍數，即為了運算頁碼要先-1
            var orderTmp= order;
            if (order%10 == 0){
            	orderTmp = order-1;
            }
            
          //區間1~10
            if(order<=_length){ 
            	$('#previousPageBtn').css('display', 'none');
	           	 var _order=(order<=_length)?1:order;
	           	
	           	 //如果頁數不足10頁
	           	 if (quantity<=_length){
	           		_length=quantity;
	          		$('#firstPageBtn').css('display', 'none');
	           		$('#finalPageBtn').css('display', 'none');
	           		$('#nextPageBtn').css('display', 'none');
	           	 }else{
	           		$('#firstPageBtn').css('display', '');
	           		$('#finalPageBtn').css('display', '');
	           		$('#nextPageBtn').css('display', '');
	           	 }
            }else if ( (quantity-orderTmp) < _length){//區間位於後段不足10頁
	           	 var pageHeaderNoStr= orderTmp.toString();
	           	 var pageHeaderNoStr= pageHeaderNoStr.substring(0, pageHeaderNoStr.length-1);
	           	 var _order =parseInt( pageHeaderNoStr.toString()+"1");
	           	 _length = quantity-_order+1;
           		$('#previousPageBtn').css('display', '');
           		$('#nextPageBtn').css('display', 'none');
            } else if(orderTmp>_length){ //區間位於中間
	             var pageHeaderNoStr= orderTmp.toString();
	           	 var pageHeaderNoStr= pageHeaderNoStr.substring(0, pageHeaderNoStr.length-1);
	           	 var _order =parseInt( pageHeaderNoStr.toString()+"1");
	         	$('#previousPageBtn').css('display', '');
	           	$('#firstPageBtn').css('display', '');
           		$('#finalPageBtn').css('display', '');
           		$('#nextPageBtn').css('display', '');
            }

            
            //寫入頁碼
            for(var i=0;i<_length;i++){
                $buttongroup.eq(i).attr('data-num',_order+i);
                $buttongroup.eq(i).css('display', '');
                if((_order+i)==order){
                	$buttongroup.eq(i).addClass('active');
                }else{
                	$buttongroup.eq(i).removeClass('active');
                }
            }
            
            //如果_length小於10頁，把剩的清空
            if (_length<$buttongroup.length){
            	for (var i=9;i>_length-1;i--){
            		 $buttongroup.eq(i).attr('data-num',"none");
            	}
            }
            
            //寫入最後一頁頁碼 + 最後一頁時修改按鈕底色
            $buttonright.attr('data-num', quantity);	
            if(order==quantity){
            	$buttonright.addClass('active');
           		$('#nextPageBtn').css('display', 'none');
            }else{
            	$buttonright.removeClass('active');
            	$('#nextPageBtn').css('display', '');
            }
            
            //修改第一頁按鈕底色
            if ($('.pagination-wrap').data('order').toString()!="1"){
            	var $buttonleft=$('a.pagination-button.left');
            	$buttonleft.removeClass('left');
            }else{
            	$('#firstPageBtn').addClass('left');
            }
            
            //如果頁碼跟最後一頁一樣，將下一頁按鈕隱藏
            for(var i=0;i<_length;i++){
                if ( ($buttongroup.eq(i).attr('data-num').toString()) == (quantity.toString()) ){
                	$('#nextPageBtn').css('display', 'none');
                }
            }
        }
        
        ///將無頁碼的button清除
        clearPaginationButton();
}



// 將無頁碼的button清除
function clearPaginationButton() {
   	var $buttongroup = $('.pagination-buttongroup a');
	$buttongroup.each(function() {
		if ($(this).attr('data-num') == "none") {
			$(this).css('display', 'none');
			$('#nextPageBtn').css('display', 'none');
		}
	});
}



