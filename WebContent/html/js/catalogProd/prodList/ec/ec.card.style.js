$(document).ready(function(){
	
	if($("#catalogSeqData").val() !=""){
		$("#catalog").val($("#catalogSeqData").val());
	}
	
	//切換商品目錄
	$("#catalog").change(function() {
		location.href = "prodListCardStyleView.html?catalogSeq="+ $('#catalog option:selected').val() + "&currentPage=1&pageSizeSelected=10";
	});
	
	//切換成表格模式
	 $('#tableView').on('click',function() {
		 location.href = "prodListTableStyleView.html?catalogSeq="+ $('#catalog option:selected').val() + "&currentPage=1&pageSizeSelected=10";
	 });
	
	//點選封存-將商品更新為封存
    $('.btn-toseal').on('click',function() {
    	var prodIdArray = new Object();
    	$('.prodcard-box.selected input[type="checkbox"]').each(function(index,value){
    		prodIdArray[index] = {prodId:$(this).attr("value")};
    	});
    	updateProdStatusAjax("0",prodIdArray);
    });
    
    
    
  //點選啟用-將商品更新為啟用
    $('.btn-toenable').on('click',function() {
    	var prodIdArray = new Object();
    	$('.prodcard-box.selected input[type="checkbox"]').each(function(index,value){
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
        queryProdListAjax();
    });

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
	
	
	 //篩選商品資訊 下拉選單
    initShowHideInfo();
    
    
	//點選篩選商品資訊 下拉選單
    $('.select-menu li').on('click',function(){
        $(this).attr('data-select',function(_, attr){return!(attr=='true')});
        var _datafiltername= $(this).attr('data-filetr-name');
        var _dataselect= $(this).attr('data-select');
        showHideInfo(_datafiltername,_dataselect);
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
		queryProdListAjax();
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

      	queryProdListAjax();
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
      	
      	queryProdListAjax();
	});
	
	
	//處理頁數與總頁數及按鈕
	processPageAndTotalPage();
	
	//搜尋商品名稱
	searchProdName();
	
});




//查看商品名細
function viewDetail(dataObj){
	
  $.fancybox(
		  '<div style="display;" id="detailDiv">'+
		  '	<div class="detail-wrap txt-noselect">'+
		  '		<div class="detail-box">'+
		  '			<div class="img-box">'+
		  ' 			<img src="'+$(dataObj).find('.prodcard-imgbox').children().attr('src')+'">'+
		  '			</div>'+
		  '			<div class="info-box">'+
		  '				<div class="serial">'+$($(dataObj).find('.prodcard-infobox').find('.g3').find('#serialDetailData')).text()+'</div>'+
		  '				<div class="prodname">'+$($(dataObj).find('.prodcard-infobox').find('.g1').find('#prodnameDetailData')).text()+'</div>'+
		  '				<div class="group-box">'+
		  '					<div class="listprice">'+$($(dataObj).find('.prodcard-infobox').find('.g2').find('#listpriceDetailData')).text()+'</div>'+
		  '					<div class="promoprice">'+$($(dataObj).find('.prodcard-infobox').find('.g2').find('#promopriceDetailData')).text()+'</div>'+				
		  '					<div class="supplement">'+$($(dataObj).find('.prodcard-infobox').find('.g3').find('#supplementDetailData')).text()+'</div>'+
		  '					<div class="neworused">'+$($(dataObj).find('.prodcard-infobox').find('.g3').find('#neworusedDetailData')).text()+'</div>'+
		  '					<div class="class">'+$($(dataObj).find('.prodcard-infobox').find('.g3').find('#classDetailData')).text()+'</div>'+
		  '				</div>'+
		  '				<div class="weburl">'+$(dataObj).find('.prodcard-infobox').find('.g4').find('#weburlDetailData').children().attr('href')+'</div>'+
		  '			</div>'+
		  '		</div>'+
		  '	</div>'+
		  '</div>',
      {
          'autoDimensions'	: false,
          'width'         	: 700,
          'height'        	: 385,
          'autoSize'			: false,
          'autoHeight'		: false,
          'autoScale'			: false,
          'padding'			: 0,
          'overlayOpacity'    : .70,
          'overlayColor'      : '#000',
          'scrolling'			: 'no' 
      }
  );
}


// 更新商品狀態ajax
function updateProdStatusAjax(updateProdStatus, prodIdArray) {
	var filtertype = $('.nav-wrap.prodtable, .prodtable-wrap, .prodcard-wrap').attr('data-filter');
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
			alert(response.resultMap.msg);
			queryProdListAjax();

		} else {
			$("#prodListDiv").empty();
			alert(response.resultMap.msg);
			queryProdListAjax();
		}
	});
}


// 已封存列物件選取
function checkboxSealed(){
	$('.txt-row[data-type="sealed"] input[type="checkbox"], .prodcard-box[data-type="sealed"] input[type="checkbox"]').click(function () {
        if ($(this).is(":checked")) {
        	$('.nav-wrap.prodtable').attr('data-menu','detail');
            var isAllChecked = 0;
            $('.txt-row[data-type="sealed"] input[type="checkbox"], .prodcard-box[data-type="sealed"] input[type="checkbox"]').each(function(){
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
	$('.txt-row[data-type="enable"] input[type="checkbox"], .prodcard-box[data-type="enable"] input[type="checkbox"]').click(function () {
      if ($(this).is(":checked")) {
      	$('.nav-wrap.prodtable').attr('data-menu','detail');
          var isAllChecked = 0;
          $('.txt-row[data-type="enable"] input[type="checkbox"], .prodcard-box[data-type="enable"] input[type="checkbox"]').each(function() {
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
	$('.txt-row input[type="checkbox"], .prodcard-box input[type="checkbox"]').change(function() {
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
	$('.prodcard-box.selected').each(function(){
		count = count+1;
	});
	
	$('.txt-cell.group-detail .txt-quantity').empty();
	$('.txt-cell.group-detail .txt-quantity').append(count);
}


//table 第一列 目錄的全選核取按鈕功能 + 被選取的物件改底色為白色
function checkall(){
$('#checkAll').change(function() {
  var _filter = $('.prodtable-wrap, .prodcard-wrap').attr('data-filter');
  if (this.checked) {
	  $('.nav-wrap.prodtable').attr('data-menu','detail');
      if (_filter=='sealed'){
    	  $('.txt-row[data-type="sealed"] input[type="checkbox"], .prodcard-box[data-type="sealed"] input[type="checkbox"]').each(function(){
              this.checked=true;
              $(this).parent().parent().parent().addClass('selected');
          });
      }else{
    	  $('.txt-row[data-type="enable"] input[type="checkbox"], .prodcard-box[data-type="enable"] input[type="checkbox"]').each(function(){
              this.checked=true;
              $(this).parent().parent().parent().addClass('selected');
          });
      }
  } else {
	  $('.nav-wrap.prodtable').attr('data-menu','default');
      if (_filter=='sealed'){
    	  $('.txt-row[data-type="sealed"] input[type="checkbox"], .prodcard-box[data-type="sealed"] input[type="checkbox"]').each(function(){
              this.checked=false;
              $(this).parent().parent().parent().removeClass('selected');
          });
      }else{
    	  $('.txt-row[data-type="enable"] input[type="checkbox"], .prodcard-box[data-type="enable"] input[type="checkbox"]').each(function(){
              this.checked=false;
              $(this).parent().parent().parent().removeClass('selected');
          });
      }
  }
});
}


//初始篩選商品資訊 下拉選單
function initShowHideInfo(){
	showHideInfo('prodname','true');
	showHideInfo('listprice','false');
	showHideInfo('promoprice','true');
	showHideInfo('serial','false');
	showHideInfo('supplement','false');
	showHideInfo('neworused','false');
	showHideInfo('class','false');
	showHideInfo('weburl','false');
}

//處理篩選商品資訊 下拉選單
function showHideInfo(datainfoname,dataselect){
    console.log(datainfoname,dataselect);
   if (dataselect=="true"){
       $('.prodcard-infobox div[data-info-name*='+ datainfoname + ']').css('display','inline-block');
   }else{
       $('.prodcard-infobox div[data-info-name*='+ datainfoname + ']').css('display','none');
   }
}

/**
* 取得商品清單Ajax
*/
function queryProdListAjax(){
	//重撈商品清單前，將畫面有勾選商品內容清空
	$('.btn-selectnone').trigger("click")
    
    var filtertype = $('.nav-wrap.prodtable, .prodtable-wrap, .prodcard-wrap').attr('data-filter');
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
	    		var ecStockStatusDesc ="";
	    		var ecUseStatus ="";
	    		var ecUseStatusDesc ="";
	    		var ecCategory ="";
	    		var ecUrl ="";
	    		var ecStatus ="";
	    		var ecCheckStatus ="";
	    		var ecCheckStatusDesc ="";
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
	    			if(key == "ecCheckStatus"){
	    				ecCheckStatus = val;
	    			}
	    			if(key == "ecCheckStatusDesc"){
	    				ecCheckStatusDesc = val;
	    			}
	    		});
	    			if (ecStatus == "1"){
	    				tempHtml += " <div class='prodcard-box txt-noselect pos-relative' data-type='enable'> ";
	    			}else{
	    				tempHtml += " <div class='prodcard-box txt-noselect pos-relative' data-type='sealed'> ";
	    			}
	    			tempHtml += " 	<div class='prodcard-checkbox'><div class='input-check'><input type='checkbox' id='check"+i+"' value='"+id+"'><label for='check"+i+"'></label></div></div> ";
	    			tempHtml += " 		<div onclick='viewDetail(this)'> ";
	    			tempHtml += " 		<div class='prodcard-imgbox'> ";
	    			tempHtml += "	    	<img src='http://showstg.pchome.com.tw/pfp/"+ecImg+"'> ";
	    			tempHtml += "   	 </div> ";
	    			tempHtml += "    	<div class='prodcard-infobox'> ";
	    			tempHtml += "       	<div class='group g1'>	";
	    			tempHtml += "           	<div data-info-name='prodname' id='prodnameDetailData' >"+ecName+"</div>	";
	    			tempHtml += "       	</div>	";
	    			tempHtml += "        	<div class='group g2'>	";
	    			tempHtml += "           	<div data-info-name='listprice' id='listpriceDetailData' >原價<span>$</span>"+ecPrice+"</div>	";
	    			tempHtml += "            	<div data-info-name='promoprice' id='promopriceDetailData' >特價<span>$</span><i>"+ecDiscountPrice+"</i></div>	";
	    			tempHtml += "	        </div>	";
	    			tempHtml += "	        <div class='group g3'> ";
	    			tempHtml += "	        	<div data-info-name='serial' id='serialDetailData' >"+catalogProdSeq+"</div>	";
	    			tempHtml += "	            <div data-info-name='supplement' id='supplementDetailData' >"+ecStockStatusDesc+"</div>	";
	    			tempHtml += "            	<div data-info-name='neworused' id='neworusedDetailData' >"+ecUseStatusDesc+"</div>	";
	    			tempHtml += "            	<div data-info-name='class' id='classDetailData' >"+ecCategory+"</div>	";
	    			tempHtml += " 	        </div>	";
	    			tempHtml += "        	<div class='group g4'>	";
	    			tempHtml += "           	<div data-info-name='weburl' id='weburlDetailData' ><a href="+ecUrl+" target='_blank'></a></div>	";
	    			tempHtml += "	        </div>	";
	    			tempHtml += "	    </div>	";
	    			tempHtml += "	</div>	";
	    			if (ecCheckStatus == "2"){
	    				tempHtml += "<div class='prodcard-verify' data-verify='reject'> "+ecCheckStatusDesc;
	    				tempHtml += "	<div class='notice-btn up' onclick=$(this).children('em').fadeToggle('fast');> ";
	    				tempHtml += "		<em style='display:none'>error reason！</em> ";
	    				tempHtml += "	</div> ";
	    				tempHtml += "</div> ";
	    			}else{
	    				tempHtml += "<div class='prodcard-verify' data-verify=''> "+ecCheckStatusDesc;
	    				tempHtml += "	<div class='notice-btn up' onclick=$(this).children('em').fadeToggle('fast');> ";
	    				tempHtml += "		<em style='display:none'></em> ";
	    				tempHtml += "	</div> ";
	    				tempHtml += "</div> ";
	    			}
	    			tempHtml += "</div>	";
	    	
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
	
	//每次ajax回來更新篩選商品資訊
	refreshShowHideInfo();
}



/**
 * 每次ajax回來更新篩選商品資訊
 */
function refreshShowHideInfo() {
	$('.select-menu li').each(function() {
		var _datafiltername= $(this).attr('data-filetr-name');
	    var _dataselect= $(this).attr('data-select');
	    showHideInfo(_datafiltername,_dataselect);
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


