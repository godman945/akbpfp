$(document).ready(function(){
	
	//floating scrollbar
    $('.floatingscroll').floatingScrollbar();
	
	
	if($("#catalogSeqData").val() !=""){
		$("#catalog").val($("#catalogSeqData").val());
	}
	
	
	//切換成卡片模式
	 $('#cardView').on('click',function() {
		 location.href = "prodListCardStyleView.html?catalogSeq="+ $('#catalog option:selected').val() + "&currentPage=1&pageSizeSelected=10";
	 });
	
	//切換商品目錄
	$("#catalog").change(function() {
		location.href = "prodListTableStyleView.html?catalogSeq="+ $('#catalog option:selected').val() + "&currentPage=1&pageSizeSelected=10";
	 });
	
	
	//點選封存-將商品更新為封存
    $('.btn-toseal').on('click',function() {
    	var prodIdArray = new Object();
    	$('.txt-row.selected input[type="checkbox"]').each(function(index,value){
    		prodIdArray[index] = {prodId:$(this).attr("value")};
    	});
    	updateProdStatusAjax("0",prodIdArray);
    });
    
    
    
  //點選啟用-將商品更新為啟用
    $('.btn-toenable').on('click',function() {
    	var prodIdArray = new Object();
    	$('.txt-row.selected input[type="checkbox"]').each(function(index,value){
    		prodIdArray[index] = {prodId:$(this).attr("value")};
    	});
    	updateProdStatusAjax("1",prodIdArray);
    });
	
    
    
	//牙齒底色 && 表格內容篩選                              
    var $btn_filter=$('.btn-filter');
    $btn_filter.on('click',function() {
        var filtertype=$(this).attr('data-filter');
        $('.nav-wrap.prodtable, .prodtable-wrap, .prodcard-wrap').attr('data-filter',filtertype);
        $('.pagination-wrap').data('order', "1");
        queryProdListAjax()
    });

    //     //牙齒底色 && 表格顯示切換 
//     var $btn_listtype=$('.btn-listtype');
//     $btn_listtype.on('click',function() {
//         var listtype=$(this).attr('data-list');
//         $('.nav-wrap.prodtable, .prodtable-wrap .prodcard-wrap').attr('data-list',listtype);
//     });

    //全部選取按鈕
    var $btn_select=$('.btn-selectall');
    $btn_select.on('click',function() {
        if(!$('#checkAll').prop('checked')) {
        	$('#checkAll').trigger("click");
        }
        sumSelectedPord();
    });

    
    //取消全選按鈕
    var $btn_selectnone=$('.btn-selectnone');
    $btn_selectnone.on('click',function() {
        $('.nav-wrap.prodtable').attr('data-menu','default');

        $('#checkAll, .txt-row input[type="checkbox"],.prodcard-box input[type="checkbox"]').each(function(){
	        this.checked=false;
	        $(this).parent().parent().parent().removeClass('selected');
        });
    });
    
    
    // table 第一列 目錄的全選核取按鈕功能 + 被選取的物件改底色為白色
    checkall();
	
    //被選取的物件改style
    checkboxChange();
	
    // 已啟用物件選取
    checkboxEnable();
    
    // 已封存列物件選取
    checkboxSealed();
    
	
  //每頁顯示數量選擇
	$("#pageSizeSelect").change(function() {
		$('.pagination-wrap').data('order', "1");
        queryProdListAjax()
	});
	
	
	//按首頁
	$('#firstPageBtn').click(function(){
		$('.pagination-wrap').data('order', "1");
		queryProdListAjax()
	});
	
	//按末頁
	$('#finalPageBtn').click(function(){
		$('.pagination-wrap').data('order', $('#finalPageBtn').attr('data-num'));
		queryProdListAjax()
	});
	
	//1~10頁碼
	$('.pagination-buttongroup a').click(function(){
		$('.pagination-wrap').data('order', $(this).attr('data-num'));
		queryProdListAjax()
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

      	queryProdListAjax()
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
      	
      	queryProdListAjax()
	});
	
	//處理頁數與總頁數及按鈕
	processPageAndTotalPage();
	
	//搜尋商品名稱
	searchProdName();
	
});



// 更新商品狀態ajax
function updateProdStatusAjax(updateProdStatus, prodIdArray) {
	var filtertype = $('.prodtable-wrap').attr('data-filter');
	if (filtertype == "all") {
		filtertype = '';
	} else if (filtertype == "enable") {
		filtertype = "1";
	} else if (filtertype == "sealed") {
		filtertype = "0";
	}

	$.ajax({
		type : "post",
		dataType : "json",
		url : "updateProdStatus.html",
		data : {
			"catalogSeq" : $('#catalog option:selected').val(),
			"updateProdStatus" : updateProdStatus,
			"prodIdArray" : JSON.stringify(prodIdArray), 
			"currentPage" : 1,
			"pageSizeSelected" : $('#pageSizeSelect option:selected').val(),
			"prodStatus" : filtertype,
			"prodName" : $('#txtProdName').val()
		},
		timeout : 30000,
		error : function(xhr) {
			alert("系統繁忙，請稍後再試！");
		},
		success : function(response, status) {
			//觸發全部取消
			$('.btn-selectnone').trigger("click");
		}
	}).done(function(response) {
		//觸發全部取消
		$('.btn-selectnone').trigger("click");
		// 更新頁碼與清單資料
		$('.pagination-wrap').data('order', response.currentPage.toString());

		if (response.resultMap.status == "ERROR") {
			$("#prodListDiv").empty();
			alert(response.resultMap.msg)
			queryProdListAjax()

		} else {
			alert(response.resultMap.msg)
			$("#prodListDiv").empty();
			queryProdListAjax()
		}
	});
}




// 已封存列物件選取
function checkboxSealed(){
	$('.txt-row[data-type="sealed"] input[type="checkbox"]').click(function () {
	    if ($(this).is(":checked")) {
	    	$('.nav-wrap.prodtable').attr('data-menu','detail');
	        var isAllChecked = 0;
	        $('.txt-row[data-type="sealed"] input[type="checkbox"]').each(function(){
	            if(!this.checked){
	                isAllChecked = 1;
	            }
	        });
	        if(isAllChecked == 0){$('#checkAll').prop("checked", true);}     
	    }
	    else {
	    	$('#checkAll').prop("checked", false);
	    }
	});
}



// 已啟用物件選取
function checkboxEnable(){
	$('.txt-row[data-type="enable"] input[type="checkbox"]').click(function () {
	    if ($(this).is(":checked")) {
	    	$('.nav-wrap.prodtable').attr('data-menu','detail');
	        var isAllChecked = 0;
	        $('.txt-row[data-type="enable"] input[type="checkbox"]').each(function() {
	            if(!this.checked){
	                isAllChecked = 1;
	            }
	        });
	        if(isAllChecked == 0){$('#checkAll').prop("checked", true);}     
	    }
	    else {
	    	$('#checkAll').prop("checked", false);
	    }
	});
}


//被選取的物件改style
function checkboxChange(){
	$('.txt-row input[type="checkbox"]').change(function() {
	    if (this.checked) {
	        $(this).parent().parent().parent().addClass('selected');
	    }else{
	        $(this).parent().parent().parent().removeClass('selected');
	    }
	    sumSelectedPord();
	});
}

//計算被勾選的商品數量
function sumSelectedPord(){
	var count = 0;
	$('.txt-row-data.selected').each(function(){
		count = count+1;
	});
	
	$('.txt-cell.group-detail .txt-quantity').empty();
	$('.txt-cell.group-detail .txt-quantity').append(count);
}


//table 第一列 目錄的全選核取按鈕功能 + 被選取的物件改底色為白色
function checkall(){
	$('#checkAll').change(function() {
	    var _filter = $('.prodtable-wrap').attr('data-filter');
	    if (this.checked) {
	    	$('.nav-wrap.prodtable').attr('data-menu','detail');
	        if (_filter=='sealed'){
	        	$('.txt-row[data-type="sealed"] input[type="checkbox"]').each(function(){
	                this.checked=true;
	                $(this).parent().parent().parent().addClass('selected');
	            });
	        }else{
	        	$('.txt-row[data-type="enable"] input[type="checkbox"]').each(function(){
	                this.checked=true;
	                $(this).parent().parent().parent().addClass('selected');
	            });
	        }
	    } else {
	    	$('.nav-wrap.prodtable').attr('data-menu','default');
	        if (_filter=='sealed'){
	        	$('.txt-row[data-type="sealed"] input[type="checkbox"]').each(function(){
	                this.checked=false;
	                $(this).parent().parent().parent().removeClass('selected');
	            });
	        }else{
	        	$('.txt-row[data-type="enable"] input[type="checkbox"]').each(function(){
	                this.checked=false;
	                $(this).parent().parent().parent().removeClass('selected');
	            });
	        }
	    }
	});
}



/**
* 取得商品清單Ajax
*/
function queryProdListAjax(){
	//重撈商品清單前，將畫面有勾選商品內容清空
	$('.btn-selectnone').trigger("click")
    
	var filtertype = $('.prodtable-wrap').attr('data-filter');
    if (filtertype=="all"){
    	filtertype ='';
    }else if(filtertype=="enable"){
    	filtertype ="1";
    }else if(filtertype=="sealed"){
    	filtertype ="0";
    }
    
	$.ajax({
	    type: "post",
	    dataType: "json",
	    url: "queryProdListAjax.html",
	    data: {
    	    "catalogSeq": $('#catalog option:selected').val(),
   	        "currentPage": $('#pageData').data('order'),
   	        "pageSizeSelected":  $('#pageSizeSelect option:selected').val(),
   	        "prodStatus": filtertype,
   	        "prodName": $('#txtProdName').val()
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
    	$('#prodQuantityTxt').empty();
    	$('#prodQuantityTxt').append(response.totalCount.toString());
        
    	if (response.status=="ERROR"){
    		$("#prodListDiv").empty();
    		alert(response.msg)
    	}else{
    		$("#prodListDiv").empty();
    		var tempHtml = "";
    		var i=0;
	    	$.each(response.prodList, function(index, list){
	    		i=i+1;
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
	    			if (ecStatus == "1"){
	    				tempHtml += " <div class='txt-row txt-row-data' data-type='enable'> ";
	    			}else{
	    				tempHtml += " <div class='txt-row txt-row-data' data-type='sealed'> ";
	    			}
	    			tempHtml += " <div class='txt-cell col-checkbox'><div class='input-check'><input type='checkbox' id='check"+i+"' value='"+id+"'><label for='check"+i+"'></label></div></div> ";
	    			tempHtml += " <div class='txt-cell col-serial'>"+catalogProdSeq+"</div> ";
	    			tempHtml += " <div class='txt-cell col-prodname'>"+ecName+"</div> ";
	    			tempHtml += " <div class='txt-cell col-listprice'><span>$</span>"+ecPrice+"</div> ";
	    			tempHtml += " <div class='txt-cell col-promoprice'><span>$</span>"+ecDiscountPrice+"</div> ";
	    			tempHtml += " <div class='txt-cell col-supplement'>"+ecStockStatusDesc+"</div> ";
	    			tempHtml += " <div class='txt-cell col-neworused'>"+ecUseStatusDesc+"</div> ";
	    			tempHtml += " <div class='txt-cell col-picture'><img src='http://showstg.pchome.com.tw/pfp/"+ecImg+"'></div> ";
	    			tempHtml += " <div class='txt-cell col-class'>"+ecCategory+"</div> ";
	    			tempHtml += " <div class='txt-cell col-weburl'><a href="+ecUrl+" target='_blank'></a></div> ";
	    			tempHtml += " </div> ";
	    	});
	    	$('#prodListDiv').html(tempHtml);
	    	
	    	// table 第一列 目錄的全選核取按鈕功能 + 被選取的物件改底色為白色
	        checkall();
	    	
	        //被選取的物件改style
	        checkboxChange();
	    	
	        // 已啟用物件選取
	        checkboxEnable();
	        
	        // 已封存列物件選取
	        checkboxSealed();
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



/**
 * 尋找商品
 */
function searchProdName() {
	// 搜尋商品名稱
	$(function() {
		$("#txtProdName").keyup(function() {
			$('.pagination-wrap').data('order', "1");
			$(this).doTimeout("findProdName", 1000, function() {
				queryProdListAjax();
			});
		});
	});
}


