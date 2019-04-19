$(document).ready(function(){
	
	//floating scrollbar
    $('.floatingscroll').floatingScrollbar();
	
    //狀態Checkbox開關
    statusCheckboxChange();
    
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

//複製成功
function copySuccess(){
	$(".copySuccess").css('display','');
}

//跳出取code明細頁
function getpTag(paid,convertSeq,convertName,convertCodeType){
	
	if (convertCodeType == "0"){
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
			'<script  id="pcadscript" language="javascript" async src="https://pacl.pchome.com.tw/js/ptag.js"></script>\n'+
			'<script>\n'+
			'   window.dataLayer = window.dataLayer || [];\n'+
			'   function ptag(){dataLayer.push(arguments);}\n'+
			'   ptag({"paid":"'+paid+'"});\n'+
			'   ptag("event","convert",{\n'+
			'   "convert_id":"'+convertSeq+'",\n'+
			'   "convert_price":""});\n'+
			'</script>'+
				
				'</textarea>'+
		                              '</pre>'+
		                          '</div>'+
		                      '</div>'+
		                      '<div class="link-copyto p-t5 txt-right">'+
		                          '<a class="btn-copyto" data-clipboard-action="copy" data-clipboard-target="#code1" onclick="copySuccess()"><em>複製代碼</em></a>'+
		                      '</div>               '+
		                      '<div class="copySuccess txt-center" style="display:none;">複製成功</div>'+
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
		            'scrolling'			: 'yes' 
		        }
		    );
	}else if(convertCodeType == "1"){
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
			'<script  id="pcadscript" language="javascript" async src="https://pacl.pchome.com.tw/js/ptag.js"></script>\n'+
			'<script>\n'+
			'   window.dataLayer = window.dataLayer || [];\n'+
			'   function ptag(){dataLayer.push(arguments);}\n'+
			'   ptag({"paid":"'+paid+'"});\n'+
			'   ptag("event","convert",{\n'+
			'   "convert_id":"'+convertSeq+'",\n'+
			'   "convert_price":""},"click");\n'+
			'</script>'+
				
				'</textarea>'+
		                              '</pre>'+
		                          '</div>'+
		                      '</div>'+
		                      '<div class="link-copyto p-t5 txt-right">'+
		                          '<a class="btn-copyto" data-clipboard-action="copy" data-clipboard-target="#code1" onclick="copySuccess()"><em>複製代碼</em></a>'+
		                      '</div>               '+
		                      '<div class="copySuccess txt-center" style="display:none;">複製成功</div>'+
		                      '<div class="eventClick-codenote p-b25">'+
                              '將追蹤碼：<strong>pchome_click("到達網址",true/false)</strong> 套入您網頁上的按鈕或連結。<br>(true：開新頁籤，false：本頁開啟)<br>範例：<span>< </span>input type="button" value="TEST" onclick="pchome_click("http://www.pchome.com.tw",true);"/>'+
                              '</div>'+
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
		            'scrolling'			: 'yes' 
		        }
		    );
	}

}


/**
* 取得轉換追蹤清單Ajax
*/
function queryConvertListAjax(){
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
//    		alert(response.msg)
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
	    		var convertCodeType ="";		//轉換代碼類型(0：瀏覽代碼(預設)、1：點擊代碼)
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
	    				convertName = convertName.replace(/ /g, "&nbsp;");
	    			}
	    			if(key == "convertSeq"){
	    				convertSeq = val;
	    			}
	    			if(key == "verifyStatus"){
	    				verifyStatus = val;
	    			}
	    			if(key == "convertCodeType"){
	    				convertCodeType = val;
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
            		tempHtml += " <div class='txt-cell col-ptagcheckbox'> ";
            		tempHtml += "	<label class='pos-middle switch-adstatus'> ";
            		if (convertStatus == "0"){
            			tempHtml += "	<input type='checkbox' id='"+convertSeq+"'>";
            		}else if(convertStatus == "1"){
            			tempHtml += "	<input type='checkbox' checked='checked' id='"+convertSeq+ "'>";
            		}
            		tempHtml += " 		<span class='slider'></span> ";
            		tempHtml += " 	</label> ";
            		tempHtml += "	</div> ";
            		tempHtml += " <div class='txt-cell col-ptagname-trans'><a href='editConvertTrackingView.html?convertSeq="+convertSeq+"'>"+convertName+"</a><br><small>ID："+convertSeq+"</small></div> ";
            		tempHtml += " <div class='txt-cell col-ptagcode-trans'><a href='javascript:void(0)' onclick='getpTag(\""+paId+"\",\""+convertSeq+"\",\""+convertName+"\",\""+convertCodeType+"\");'>取得代碼</a></div> ";
            		if (verifyStatus == "1"){
            			tempHtml += " <div class='txt-cell col-ptagstatus-trans'><span data-certificated='true'>已認證</span></div> ";
            		}else{
            			tempHtml += " <div class='txt-cell col-ptagstatus-trans'><span data-certificated='false'>未認證</span></div> "; 
            		}
            		tempHtml += " <div class='txt-cell col-type-transfer	'>"+convertTypeDesc+"</div> ";
            		tempHtml += " <div class='txt-cell col-type-transfer	'>"+convertClassDesc+"</div> ";
            		tempHtml += " <div class='txt-cell col-days-interactive	'>"+clickRangeDate+"天</div> ";
            		tempHtml += " <div class='txt-cell col-days-browse		'>"+impRangeDate+"天</div> ";
            		tempHtml += " <div class='txt-cell col-value-transfer	'>"+transConvertPrice+"</div> ";
            		tempHtml += " <div class='txt-cell col-click-transfer	'>"+transCKConvertCount+"</div> ";
            		tempHtml += " <div class='txt-cell col-browse-transfer	'>"+transPVConvertCount+"</div> ";
            		tempHtml += " <div class='txt-cell col-all-transfer	'>"+transAllConvertCount+"</div> ";
            		tempHtml += " <div class='txt-cell col-ptagcheckbox col-delete p-none'><a href='javascript:void(0)' onclick='deleteConvertAjax(\""+convertSeq+"\")'></a></div> ";
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
	    		sumTempHtml += " <div class='txt-cell col-ptagcheckbox'></div> ";
	    		$('#sumConvertCountDiv').html(sumTempHtml);
	    	}
	    	
	    	//狀態Checkbox開關
	    	statusCheckboxChange();
	    	
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


//狀態Checkbox開關
function statusCheckboxChange(){
	$('.txt-row input[type="checkbox"]').change(function(){
	    if($(this).prop('checked') == false){
	        var _com = window.confirm("若關閉狀態，將停止轉換追蹤功能");
	        if( _com == true){
	            $(this).prop('checked', false);
	            updateConvertStatusAjax($(this).attr('id'),"0")
	        }else{
	            $(this).prop('checked', true);
	            updateConvertStatusAjax($(this).attr('id'),"1")
	        }
	    }else{
	    	 $(this).prop('checked', true);
	    	 updateConvertStatusAjax($(this).attr('id'),"1")
	    }
	});
	
	
//	$('.txt-row input[type="checkbox"]').change(function() {
//	    if (this.checked) { //狀態按開啟
//	    	this.setAttribute("checked", "checked");
//	    	updateConvertStatusAjax($(this).attr('id'),"1")
//	    }else{	//狀態按關閉
//	    	this.removeAttribute("checked");
//	    	updateConvertStatusAjax($(this).attr('id'),"0")
//	    }
//	});
}

//更新轉換目錄狀態
function updateConvertStatusAjax(convertSeq,convertStatus) {
	$.ajax({
		type : "post",
		dataType : "json",
		url : "updateConvertStatusAjax.html",
		data : {
			"convertSeq" : convertSeq,
			"convertStatus" : convertStatus
		},
		timeout : 30000,
		error : function(xhr) {
			alert("系統繁忙，請稍後再試！");
		},
		success : function(response, status) {
		}
	}).done(function(response) {
//		alert(response.msg);
	});
}

//刪除轉換ajax
function deleteConvertAjax(convertSeq) {
	if(confirm("是否刪除轉換追蹤代碼？")) {
		$.ajax({
			type : "post",
			dataType : "json",
			url : "deleteConvertAjax.html",
			data : {
				"currentPage" : 1,
				"pageSizeSelected" : $('#pageSizeSelect option:selected').val(),
				"convertSeq" : convertSeq,
				"convertName": $('#txtProdName').val()
			},
			timeout : 30000,
			error : function(xhr) {
				alert("系統繁忙，請稍後再試！");
			},
			success : function(response, status) {
			}
		}).done(function(response) {
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
	$.ajax({
		type : "post",
		dataType : "json",
		url : "sendConvertTrackingMailAjax.html",
		data : {
			"mailReceivers" : $('#mailReceivers').val(),
			"paId" : $('#paidSendMail').val(),
			"convertSeq" : $('#convertSeqSendMail').val(),
			"convertCodeType" : ""
		},
		timeout : 30000,
		error : function(xhr) {
			alert("系統繁忙，請稍後再試！");
		},
		success : function(response, status) {
		}
	}).done(function(response) {
		if (response.status == "ERROR") {
			alert(response.msg);
		} else {
			alert(response.msg);
			$('#sendMail').attr("disabled","disabled");
			$('#sendMail').val("發送完成");
		}
	});
	
}
