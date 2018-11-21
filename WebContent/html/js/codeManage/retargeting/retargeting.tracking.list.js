$(document).ready(function(){
	
//	alert('retargeting list')
	//floating scrollbar
    $('.floatingscroll').floatingScrollbar();
	
	
	//全部選取按鈕
    var $btn_select=$('.btn-selectall');
    $btn_select.on('click',function() {
    	console.log('11')
        var $checkall = $('#checkAll');
        if(!$checkall.prop('checked')) {
            $checkall.trigger("click");
        }
        sumSelectedPord();
    });

    
    //取消全選按鈕
    var $btn_selectnone=$('.btn-selectnone');
    $btn_selectnone.on('click',function() {
        
        var $menu = $('.nav-wrap.prodtable');
        $menu.attr('data-menu','default');

        var $checkbox =$('#checkAll, .txt-row input[type="checkbox"],.prodcard-box input[type="checkbox"]');
        $checkbox.each(function(){
        this.checked=false;
        $(this).parent().parent().parent().removeClass('selected');
        });
    });
	
	
	//table 第一列 目錄的全選核取按鈕功能 + 被選取的物件改底色為白色
    checkall();
	
    //被選取的物件改style
    checkboxChange();
    
    //已啟用物件選取
    checkboxEnable();
    
    // 已封存列物件選取
    checkboxSealed();
    
    //刪除再行銷
    deleteRetargeting();
    
    //初始copy代碼
    initClipboard();
    
    //每頁顯示數量選擇
	$("#pageSizeSelect").change(function() {
		$('.pagination-wrap').data('order', "1");
		queryRetargetingListAjax();
	});
	
	
	//按首頁
	$('#firstPageBtn').click(function(){
		$('.pagination-wrap').data('order', "1");
		queryRetargetingListAjax();
	});
	
	//按末頁
	$('#finalPageBtn').click(function(){
		$('.pagination-wrap').data('order', $('#finalPageBtn').attr('data-num'));
		queryRetargetingListAjax();
	});
	
	//1~10頁碼
	$('.pagination-buttongroup a').click(function(){
		$('.pagination-wrap').data('order', $(this).attr('data-num'));
		queryRetargetingListAjax();
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

      	queryRetargetingListAjax();
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
      	
      	queryRetargetingListAjax();
	});
    
	//處理頁數與總頁數及按鈕
	processPageAndTotalPage();
	
	//搜尋商品名稱
	searchProdName();

});


//初始copy代碼
function initClipboard(){
    var clipboard = new ClipboardJS('.btn-copyto');
    clipboard.on('success', function (e) {
        e.clearSelection();
    });
    clipboard.on('error', function (e) {
    });
}

//function getpTag(){
//    $.fancybox(
//        $('#getpTagDIV').html(),
//        {
//            'autoDimensions'	: false,
//            'width'         	: 720,
//            'height'        	: 590,
//            'autoSize'			: false,
//            'autoHeight'		: false,
//            'autoScale'			: false,
//            'padding'			: 0,
//            'overlayOpacity'    : .70,
//            'overlayColor'      : '#000',
//            'scrolling'			: 'no' 
//        }
//    );
//}

//跳取code明細頁
function getpTag(codeType,paid,trackingSeq,trackingName){
	
	if (codeType == '0'){
			$.fancybox(
	//    		 '<div style="position:absolute;top:-1000%; left:-1000%; z-index:-1;" id="getpTagDIV">SSSSS</div>',
	    		  '<div style="position:;top:-1000%; left:-1000%; z-index:-1;" id="getpTagDIV">'+
	              '<div class="getpTag-wrap">'+
	                  '<div class="getpTag-nav ali-middle"><span>再行銷追蹤代碼 - '+trackingName+'</span></div>'+
	                  '<div class="getpTag-box">'+
	                      '<span>請將下方代碼複製並貼在您網站上的每個網頁</span>'+
	                      '<div class="code-wrap pos-relative">'+
	                          '<a class="btn-copyto code-copy pos-absolute pos-right pos-top txt-noselect" data-clipboard-action="copy" data-clipboard-target="#code1"></a>'+
	                          '<div class="code-box">'+
	                              '<pre class="snippet">'+
	'<textarea id="code1" readonly>'+
	'<script  id="pcadscript" language="javascript" async src="https://kdpic.pchome.com.tw/js/ptag.js"></script>\n'+
	'<script>\n'+
	'  window.dataLayer = window.dataLayer || [];\n'+
	'  function ptag(){dataLayer.push(arguments);}\n'+
	'  ptag({"paid":'+paid+'});\n'+
	'  ptag("event","tracking",{\n'+
	'  "tracking_id":'+trackingSeq+'\n'+
	'  "});\n'+   
	'</script>'+
	'</textarea>'+
	                              '</pre>'+
	                          '</div>'+
	                      '</div>'+
	                      '<div class="link-copyto p-t5 txt-right">'+
	                          '<a class="btn-copyto" data-clipboard-action="copy" data-clipboard-target="#code1"><em>複製代碼</em></a>'+
	                      '</div>               '+             
	                      '<div class="section-box">'+
	                          '<p class="title-box h2">以電子郵件寄送代碼<small>若有多個地址請以逗號分隔</small></p>'+
	                          '<div class="input-text inputemail">'+
	                              '<input id="mailReceivers" type="text" name="" maxlength="200" value="" required placeholder="you@email.com">'+
	                              '<div id="emailMsgError" class="msg-error" style="display:none">錯誤訊息</div>'+
	                          '</div>'+
	                      '</div>        '+                    
	                      '<div class="button-box p-t10">'+
	                          '<div class="input-button"><input id ="sendMail" type="button" onclick="sendMail()" value="傳送至Email" ></div>'+
	                      '</div>'+
	                  '</div>'+
	              '</div>'+
	          '</div>'+
	          
	          '<input type="hidden" id="paidSendMail" value='+paid+'>'+
	          '<input type="hidden" id="trackingSeqSendMail" value='+trackingSeq+'>'+
	          '<input type="hidden" id="codeTypeSendMail" value='+codeType+'>',
	        {
	            'autoDimensions'	: false,
	            'width'         	: 720,
	            'height'        	: 590,
	            'autoSize'			: false,
	            'autoHeight'		: false,
	            'autoScale'			: false,
	            'padding'			: 0,
	            'overlayOpacity'    : .70,
	            'overlayColor'      : '#000',
	            'scrolling'			: 'no' 
	        }
	    );
	}else{
		$.fancybox(
				//    		 '<div style="position:absolute;top:-1000%; left:-1000%; z-index:-1;" id="getpTagDIV">SSSSS</div>',
				    		  '<div style="position:;top:-1000%; left:-1000%; z-index:-1;" id="getpTagDIV">'+
				              '<div class="getpTag-wrap">'+
				              '<div class="getpTag-nav ali-middle"><span>再行銷追蹤代碼 - '+trackingName+'</span></div>'+
				                  '<div class="getpTag-box">'+
				                      '<span>請將下方代碼複製並貼在您網站上的每個網頁</span>'+
				                      '<div class="code-wrap pos-relative">'+
				                          '<a class="btn-copyto code-copy pos-absolute pos-right pos-top txt-noselect" data-clipboard-action="copy" data-clipboard-target="#code1"></a>'+
				                          '<div class="code-box">'+
				                              '<pre class="snippet">'+
				'<textarea id="code1" readonly>'+
				'<script  id="pcadscript" language="javascript" async src="https://kdpic.pchome.com.tw/js/ptag.js"></script>\n'+
				'<script>\n'+
				'  window.dataLayer = window.dataLayer || [];\n'+
				'  function ptag(){dataLayer.push(arguments);}\n'+
				'  ptag({"paid":'+paid+'});\n'+
				'  ptag("event","tracking",{\n'+
				'  "tracking_id":'+trackingSeq+',\n'+
				'  "prod_id":"",\n'+
				'  "prod_price":"",\n'+
				'  "prod_dis":"",\n'+
				'  "ec_stock_status":"",\n'+
				'  "op1":"",\n'+
				'  "op2":""\n'+
				'  "});\n'+   
				'</script>'+
				'</textarea>'+
				                              '</pre>'+
				                          '</div>'+
				                      '</div>'+
				                      '<div class="link-copyto p-t5 txt-right">'+
				                          '<a class="btn-copyto" data-clipboard-action="copy" data-clipboard-target="#code1"><em>複製代碼</em></a>'+
				                      '</div>               '+             
				                      '<div class="section-box">'+
				                          '<p class="title-box h2">以電子郵件寄送代碼<small>若有多個地址請以逗號分隔</small></p>'+
				                          '<div class="input-text inputemail">'+
				                          '<input id="mailReceivers" type="text" name="" maxlength="200" value="" required placeholder="you@email.com">'+
				                              '<div id="emailMsgError" class="msg-error" style="display:none">錯誤訊息</div>'+
				                          '</div>'+
				                      '</div>        '+                    
				                      '<div class="button-box p-t10">'+
				                      	  '<div class="input-button"><input id ="sendMail" type="button" onclick="sendMail()" value="傳送至Email"></div>'+
				                      '</div>'+
				                  '</div>'+
				              '</div>'+
				          '</div>'+
				          
				          '<input type="hidden" id="paidSendMail" value='+paid+'>'+
				          '<input type="hidden" id="trackingSeqSendMail" value='+trackingSeq+'>'+
				          '<input type="hidden" id="codeTypeSendMail" value='+codeType+'>',
				        {
				            'autoDimensions'	: false,
				            'width'         	: 720,
				            'height'        	: 590,
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
    
}


//    function stopBubble(e){ 
//        if ( e && e.stopPropagation) {
//            e.stopPropagation(); 
//        }else{ 
//            window.event.cancelBubble = true; 
//        }
//    }

    


/**
* 取得再行銷追蹤清單Ajax
*/
function queryRetargetingListAjax(){
	//重撈商品清單前，將畫面有勾選商品內容清空
	$('.btn-selectnone').trigger("click")
    
	$.ajax({
	    type: "post",
	    dataType: "json",
	    url: "queryRetargetingListAjax.html",
	    data: {
   	        "currentPage": $('#pageData').data('order'),
   	        "pageSizeSelected":  $('#pageSizeSelect option:selected').val(),
   	        "trackingName": $('#txtProdName').val()
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
    		$("#retargetingListDiv").empty();
    		alert(response.msg)
    	}else{
    		$("#retargetingListDiv").empty();
    		var tempHtml = "";
    		var i=0;
	    	$.each(response.retargetingList, function(index, list){
	    		i=i+1;
	    		var paId ="";
	    		var trackingSeq ="";
	    		var trackingName ="";
	    		var trackingStatus ="";
	    		var codeType ="";
	    		var trackingRangeDate ="";
	    		var verifyStatus ="";
	    		$.each(list, function(key, val){
	    			
	    			if(key == "paId"){
	    				paId = val;
	    			}
	    			if(key == "trackingSeq"){
	    				trackingSeq = val;
	    			}
	    			if(key == "trackingName"){
	    				trackingName = val;
	    			}
	    			if(key == "trackingStatus"){
	    				trackingStatus = val;
	    			}
	    			if(key == "codeType"){
	    				codeType = val;
	    			}
	    			if(key == "trackingRangeDate"){
	    				trackingRangeDate = val;
	    			}
	    			if(key == "verifyStatus"){
	    				verifyStatus = val;
	    			}
	    		});
	    		
            		tempHtml += " <div class='txt-row txt-row-data' data-type='enable'> ";
            		tempHtml += " <div class='txt-cell col-ptagcheckbox'><div class='input-check'><input type='checkbox' id='check"+i+"' value='"+trackingSeq+"'><label for='check"+i+"'></label></div></div> ";
            		tempHtml += " <div class='txt-cell col-ptagname'><a href='#'>"+trackingName+"</a><br><small>ID："+trackingSeq+"</small></div> ";
            		tempHtml += " <div class='txt-cell col-ptagcode'><a href='javascript:void(0)' onclick='getpTag(\""+codeType+"\",\""+paId+"\",\""+trackingSeq+"\",\""+trackingName+"\");'>取得代碼</a></div> ";
            		if (verifyStatus == "1"){
            			tempHtml += " <div class='txt-cell col-ptagstatus'><span data-certificated='true'>已認證</span></div> ";
            		}else{
            			tempHtml += " <div class='txt-cell col-ptagstatus'><span data-certificated='false'>未認證</span></div> "; 
            		}
            		tempHtml += " <div class='txt-cell col-ptagtype'>"+codeType+"</div> ";
            		tempHtml += " <div class='txt-cell col-ptagperiod'>"+trackingRangeDate+"</div> ";
            		tempHtml += " </div> ";
	    	});
	    	$('#retargetingListDiv').html(tempHtml);
	    	
	    	// table 第一列 目錄的全選核取按鈕功能 + 被選取的物件改底色為白色
	        checkall();
	    	
	        //被選取的物件改style
	        checkboxChange();
	    	
	        // 已啟用物件選取
	        checkboxEnable();
	        
	        // 已封存列物件選取
	        checkboxSealed();
	        
	        //初始copy代碼
	        initClipboard();
	        
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


//將無頁碼的button清除
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
				queryRetargetingListAjax();
			});
		});
	});
}

//計算被勾選的商品數量
function sumSelectedPord(){
	var count = 0;
	$('.txt-row-data.selected').each(function(){
		count = count+1;
	});
	console.log('count')
	console.log(count)
	$('.txt-cell.group-detail .txt-quantity').empty();
	$('.txt-cell.group-detail .txt-quantity').append(count);
}

//table 第一列 目錄的全選核取按鈕功能 + 被選取的物件改底色為白色
function checkall(){
	console.log('checkall')
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


//被選取的物件改style
function checkboxChange(){
	$('.txt-row input[type="checkbox"]').change(function() {
		console.log('checkboxChange1')
	    if (this.checked) {
	        $(this).parent().parent().parent().addClass('selected');
	    }else{
	        $(this).parent().parent().parent().removeClass('selected');
	    }
		 sumSelectedPord();
	});
}


//已啟用物件選取
function checkboxEnable(){
	$('.txt-row[data-type="enable"] input[type="checkbox"]').click(function () {
		console.log('已啟用物件選取111')
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


// 已封存列物件選取
function checkboxSealed(){
	$('.txt-row[data-type="sealed"] input[type="checkbox"]').click(function () {
		console.log('已封存列物件選取000')
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

//刪除再行銷
function deleteRetargeting(){
	console.log('del del del del del ')
	$('.btn-todelete').on('click',function() {
		console.log('00000000 ')
		var retargetingIdArray = new Object();
		$('.txt-row-data.selected input[type="checkbox"]').each(function(index,value){
			retargetingIdArray[index] = {retargetingId:$(this).attr("value")};
		});
		deleteRetargetingAjax(retargetingIdArray);
	});
}

//刪除再行銷ajax
function deleteRetargetingAjax(retargetingIdArray) {
	console.log('deleteAJAX')
	$.ajax({
		type : "post",
		dataType : "json",
		url : "deleteRetargetingAjax.html",
		data : {
			"currentPage" : 1,
			"pageSizeSelected" : $('#pageSizeSelect option:selected').val(),
			"retargetingIdArray" : JSON.stringify(retargetingIdArray),
			"trackingName": $('#txtProdName').val()
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

		if (response.status == "ERROR") {
			$("#retargetingListDiv").empty();
			alert(response.msg)
			queryRetargetingListAjax()

		} else {
			alert(response.msg)
			$("#retargetingListDiv").empty();
			queryRetargetingListAjax()
		}
	});
}

//發送mail
function sendMail(){
	//檢查全部mail是否合法
	if (checkMailReceivers()==true){
		//send mail
		sendMailAjax();
	}
}

//檢查全部mail是否合法
function checkMailReceivers(){
	//檢查mail是否為空
	if ($('#mailReceivers').val()==""){
			$("#emailMsgError").text("email不得為空");
			$("#emailMsgError").css('display', "");
			return false;
	}else{
		$("#emailMsgError").text("");
		$("#emailMsgError").css('display', "none");
	}
	
	
	//檢查mail是否合法
	var mailReceiversAry = [];
	var mailReceiversStr = $('#mailReceivers').val();
	mailReceiversAry= mailReceiversStr.split(";");
	for (i = 0; i < mailReceiversAry.length; i++) { 
		if (!verifyEmail(mailReceiversAry[i])){
			$("#emailMsgError").text("請填寫正確的電子郵件地址");
			$("#emailMsgError").css('display', "");
			return false;
		}else{
			$("#emailMsgError").text("");
			$("#emailMsgError").css('display', "none");
		}
	}
	
	return true;
}

//檢查email是否合法
function verifyEmail(email) { 
	var regex = /^([a-zA-Z0-9_\.\-\+])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	if(!regex.test(email)) {
		return false;
	}else{
		return true;
	}
}

//send mail
function sendMailAjax(){
	console.log('可send mail');
	$.ajax({
		type : "post",
		dataType : "json",
		url : "sendRetargetingTrackingMailAjax.html",
		data : {
			"mailReceivers" : $('#mailReceivers').val(),
			"paId" : $('#paidSendMail').val(),
			"trackingSeq" : $('#trackingSeqSendMail').val(),
			"codeType" :  $('#codeTypeSendMail').val()
		},
		timeout : 30000,
		error : function(xhr) {
			alert("系統繁忙，請稍後再試！");
		},
		success : function(response, status) {
		}
	}).done(function(response) {
		console.log(response);
		if (response.status == "ERROR") {
			alert(response.msg);
		} else {
			alert(response.msg);
			$('#sendMail').attr("disabled","disabled");
			$('#sendMail').val("發送完成");
		}
	});
	
}
