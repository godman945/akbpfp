$(document).ready(function(){
	
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
    
    //刪除轉換
    deleteConvert();
    
    //初始copy代碼
    initClipboard();
    
    //每頁顯示數量選擇
	$("#pageSizeSelect").change(function() {
		$('.pagination-wrap').data('order', "1");
		queryConvertListAjax();
	});
	
	
	//按首頁
	$('#firstPageBtn').click(function(){
		$('.pagination-wrap').data('order', "1");
		queryConvertListAjax();
	});
	
	//按末頁
	$('#finalPageBtn').click(function(){
		$('.pagination-wrap').data('order', $('#finalPageBtn').attr('data-num'));
		queryConvertListAjax();
	});
	
	//1~10頁碼
	$('.pagination-buttongroup a').click(function(){
		$('.pagination-wrap').data('order', $(this).attr('data-num'));
		queryConvertListAjax();
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

      	queryConvertListAjax();
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
      	
      	queryConvertListAjax();
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


//跳出取code明細頁
function getpTag(paid,convertSeq,convertName){
			$.fancybox(
	    		  '<div style="position:;top:-1000%; left:-1000%; z-index:-1;" id="getpTagDIV">'+
	              '<div class="getpTag-wrap">'+
	                  '<div class="getpTag-nav ali-middle"><span>轉換追蹤代碼 - '+convertName+'</span></div>'+
	                  '<div class="getpTag-box">'+
	                      '<span>請將下方代碼複製並貼在您網站上的每個網頁</span>'+
	                      '<div class="code-wrap pos-relative">'+
	                          '<a class="btn-copyto code-copy pos-absolute pos-right pos-top txt-noselect" data-clipboard-action="copy" data-clipboard-target="#code1"></a>'+
	                          '<div class="code-box">'+
	                              '<pre class="snippet">'+
	'<textarea id="code1" readonly>'+
	'<script  id="pcadscript" language="javascript" async src="https://kdpic.pchome.com.tw/js/ptag.js"></script>\n'+
	'<script>\n'+
	'   window.dataLayer = window.dataLayer || [];\n'+
	'   function ptag(){dataLayer.push(arguments);}\n'+
	'   ptag({"paid":'+paid+'});\n'+
	'   ptag("event","convert",{\n'+
	'   "convert_id":'+convertSeq+',\n'+
	'   "convert_price":"",\n'+
	'   "op1":"",\n'+
	'   "op2":""});\n'+
	'</script>'+
	'</textarea>'+
	                              '</pre>'+
	                          '</div>'+
	                      '</div>'+
	                      '<div class="link-copyto p-t5 txt-right">'+
	                          '<a class="btn-copyto" data-clipboard-action="copy" data-clipboard-target="#code1"><em>複製代碼</em></a>'+
	                      '</div>               '+             
	                      '<div class="section-box">'+
	                          '<p class="title-box h2">以電子郵件寄送代碼<small>若有多個地址請以分號分隔</small></p>'+
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
	          '<input type="hidden" id="convertSeqSendMail" value='+convertSeq+'>',
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


/**
* 取得轉換追蹤清單Ajax
*/
function queryConvertListAjax(){
	//重撈商品清單前，將畫面有勾選商品內容清空
	$('.btn-selectnone').trigger("click")
    
	$.ajax({
	    type: "post",
	    dataType: "json",
	    url: "queryConvertListAjax.html",
	    data: {
   	        "currentPage": $('#pageData').data('order'),
   	        "pageSizeSelected":  $('#pageSizeSelect option:selected').val(),
   	        "convertName": $('#txtProdName').val()
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
    		$("#convertListDiv").empty();
    		$("#sumConvertCountDiv").empty();
    		alert(response.msg)
    	}else{
    		$("#convertListDiv").empty();
    		$("#sumConvertCountDiv").empty();
    		var tempHtml = "";
    		var i=0;
	    	$.each(response.convertList, function(index, list){
	    		i=i+1;
	    		var paId ="";		
	    		var convertName ="";			// 轉換追蹤名稱	
	    		var convertSeq ="";				// 轉換追蹤seq
	    		var verifyStatus ="";			// 狀態(已認證、未認證)
	    		var convertType ="";			// 轉換類型(1.標準轉換追蹤2.自訂轉換追蹤條件)
	    		var convertClass ="";			// 轉換分類(1.查看內容 2.搜尋 3.加到購物車 4.加到購物清單 5.開始結帳 6.新增付款資料 7.購買 8.完成註冊)
	    		var clickRangeDate ="";	 		// 互動後轉換追溯天數
	    		var impRangeDate ="";	 		// 瀏覽後轉換追溯天數
	    		var transConvertPrice ="";		// 轉換價格(目前只要點擊ck的價值)
	    		var transCKConvertCount ="";	// 點擊後轉換數(ckConvertCount)
	    		var transPVConvertCount ="";	// 瀏覽後轉換數(pvConvertCount)
	    		var transAllConvertCount="";	// 所有轉換(ckConvertCount+pvConvertCount)
	    		var convertStatus ="";			// 轉換狀態(0:關閉;1:開啟;2:刪除)
	    		
	    		$.each(list, function(key, val){
	    			if(key == "paId"){
	    				paId = val;
	    			}
	    			if(key == "convertName"){
	    				convertName = val;
	    			}
	    			if(key == "convertSeq"){
	    				convertSeq = val;
	    			}
	    			if(key == "verifyStatus"){
	    				verifyStatus = val;
	    			}
	    			if(key == "convertType"){
	    				convertType = val;
	    			}
	    			if(key == "convertTypeDesc"){
	    				convertTypeDesc = val;
	    			}
	    			if(key == "convertClass"){
	    				convertClass = val;
	    			}
	    			if(key == "convertClassDesc"){
	    				convertClassDesc = val;
	    			}
	    			if(key == "clickRangeDate"){
	    				clickRangeDate = val;
	    			}
	    			if(key == "impRangeDate"){
	    				impRangeDate = val;
	    			}
	    			if(key == "transConvertPrice"){		//轉換價值
	    				transConvertPrice = val;
	    			}
	    			if(key == "transCKConvertCount"){	//點擊後轉換數
	    				transCKConvertCount = val;
	    			}
	    			if(key == "transPVConvertCount"){	//瀏覽後轉換數
	    				transPVConvertCount = val;
	    			}
	    			if(key == "transAllConvertCount"){	//所有轉換
	    				transAllConvertCount = val;
	    			}
	    			if(key == "convertStatus"){
	    				convertStatus = val;
	    			}
	    		});
	    		
            		tempHtml += " <div class='txt-row txt-row-data' data-type='enable'> ";
            		tempHtml += " <div class='txt-cell col-ptagcheckbox'><div class='input-check'><input type='checkbox' id='check"+i+"' value='"+convertSeq+"'><label for='check"+i+"'></label></div></div> ";
            		tempHtml += " <div class='txt-cell col-ptagname-trans'><a href='editConvertTrackingView.html?convertSeq="+convertSeq+"'>"+convertName+"</a><br><small>ID："+convertSeq+"</small></div> ";
            		tempHtml += " <div class='txt-cell col-ptagcode-trans'><a href='javascript:void(0)' onclick='getpTag(\""+paId+"\",\""+convertSeq+"\",\""+convertName+"\");'>取得代碼</a></div> ";
            		if (verifyStatus == "1"){
            			tempHtml += " <div class='txt-cell col-ptagstatus-trans'><span data-certificated='true'>已認證</span></div> ";
            		}else{
            			tempHtml += " <div class='txt-cell col-ptagstatus-trans'><span data-certificated='false'>未認證</span></div> "; 
            		}
            		tempHtml += " <div class='txt-cell col-type-transfer	'>"+convertTypeDesc+"</div> ";
            		tempHtml += " <div class='txt-cell col-type-transfer	'>"+convertClassDesc+"</div> ";
            		tempHtml += " <div class='txt-cell col-days-interactive	'>"+clickRangeDate+"</div> ";
            		tempHtml += " <div class='txt-cell col-days-browse		'>"+impRangeDate+"</div> ";
            		tempHtml += " <div class='txt-cell col-value-transfer	'>"+transConvertPrice+"</div> ";
            		tempHtml += " <div class='txt-cell col-click-transfer	'>"+transCKConvertCount+"</div> ";
            		tempHtml += " <div class='txt-cell col-browse-transfer	'>"+transPVConvertCount+"</div> ";
            		tempHtml += " <div class='txt-cell col-all-transfer	'>"+transAllConvertCount+"</div> ";
            		tempHtml += " </div> ";
	    	});
	    	$('#convertListDiv').html(tempHtml);
	    	
	    	
	    	//如果總筆數大於0，顯示 總計：所有轉換動作
	    	if (response.totalCount >0 ){
	    		var sumTempHtml = "";
	    		sumTempHtml += " <div class='txt-cell col-ptagcheckbox'><p class='summary-tit pos-absolute pos-left pos-top ali-middle'>總計：所有轉換動作</p></div> ";
	    		sumTempHtml += " <div class='txt-cell col-ptagname-trans'></div> ";
	    		sumTempHtml += " <div class='txt-cell col-ptagcode-trans'></div> ";
	    		sumTempHtml += " <div class='txt-cell col-ptagstatus-trans'></div> ";
	    		sumTempHtml += " <div class='txt-cell col-type-transfer'></div> ";
	    		sumTempHtml += " <div class='txt-cell col-type-transfer'></div> ";
	    		sumTempHtml += " <div class='txt-cell col-days-interactive'></div> ";
	    		sumTempHtml += " <div class='txt-cell col-days-browse'></div> ";
	    		sumTempHtml += " <div class='txt-cell col-value-transfer'></div> ";
	    		sumTempHtml += " <div class='txt-cell col-click-transfer'>"+response.sumConvertCount.transSumCKConvertCount+"</div> ";
	    		sumTempHtml += " <div class='txt-cell col-browse-transfer'>"+response.sumConvertCount.transSumPVConvertCount+"</div> ";
	    		sumTempHtml += " <div class='txt-cell col-all-transfer'>"+response.sumConvertCount.transSumAllConvertCount+"</div> ";
	    		$('#sumConvertCountDiv').html(sumTempHtml);
	    	}
	    	
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
				queryConvertListAjax();
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

//刪除轉換
function deleteConvert(){
	$('.btn-todelete').on('click',function() {
		console.log('00000000 ')
		var convertIdArray = new Object();
		$('.txt-row-data.selected input[type="checkbox"]').each(function(index,value){
			convertIdArray[index] = {convertId:$(this).attr("value")};
		});
		deleteConvertAjax(convertIdArray);
	});
}

//刪除轉換ajax
function deleteConvertAjax(convertIdArray) {
	console.log('deleteAJAX')
	$.ajax({
		type : "post",
		dataType : "json",
		url : "deleteConvertAjax.html",
		data : {
			"currentPage" : 1,
			"pageSizeSelected" : $('#pageSizeSelect option:selected').val(),
			"convertIdArray" : JSON.stringify(convertIdArray),
			"convertName": $('#txtProdName').val()
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
			$("#convertListDiv").empty();
			$("#sumConvertCountDiv").empty();
			alert(response.msg)
			queryConvertListAjax()

		} else {
			alert(response.msg)
			$("#convertListDiv").empty();
			$("#sumConvertCountDiv").empty();
			queryConvertListAjax()
		}
	});
}

//發送mail
function sendMail(){
	console.log('mail mail')
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
		url : "sendConvertTrackingMailAjax.html",
		data : {
			"mailReceivers" : $('#mailReceivers').val(),
			"paId" : $('#paidSendMail').val(),
			"convertSeq" : $('#convertSeqSendMail').val()
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
