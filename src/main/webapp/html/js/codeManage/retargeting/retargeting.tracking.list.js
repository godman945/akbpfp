﻿$(document).ready(function(){
	
	//floating scrollbar
    $('.floatingscroll').floatingScrollbar();
	
    //狀態Checkbox開關
    statusCheckboxChange();
    
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

//複製成功
function copySuccess(){
	$(".copySuccess").css('display','');
}

//跳取code明細頁
function getpTag(codeType,paid,trackingSeq,trackingName){
	
	if (codeType == '0'){
			$.fancybox(
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
	'<script  id="pcadscript" language="javascript" async src="https://pacl.pchome.com.tw/js/ptag.js"></script>\n'+
	'<script>\n'+
	'  window.dataLayer = window.dataLayer || [];\n'+
	'  function ptag(){dataLayer.push(arguments);}\n'+
	'  ptag({"paid":"'+paid+'"});\n'+
	'  ptag("event","tracking",{\n'+
	'  "tracking_id":"'+trackingSeq+'"\n'+
	'  });\n'+   
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
	            'scrolling'			: 'yes' 
	        }
	    );
	}else{
		$.fancybox(
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
				'<script  id="pcadscript" language="javascript" async src="https://pacl.pchome.com.tw/js/ptag.js"></script>\n'+
				'<script>\n'+
				'  window.dataLayer = window.dataLayer || [];\n'+
				'  function ptag(){dataLayer.push(arguments);}\n'+
				'  ptag({"paid":"'+paid+'"});\n'+
				'  ptag("event","tracking",{\n'+
				'  "tracking_id":"'+trackingSeq+'",\n'+
				'  "prod_id":"",\n'+
				'  "prod_price":"",\n'+
				'  "prod_dis":""\n'+
				'  });\n'+   
				'</script>'+
				'</textarea>'+
				                              '</pre>'+
				                          '</div>'+
				                      '</div>'+
				                      '<div class="link-copyto p-t5 txt-right">'+
				                          '<a class="btn-copyto" data-clipboard-action="copy" data-clipboard-target="#code1" onclick="copySuccess()"><em>複製代碼</em></a>'+
				                      '</div>               '+      
				                      '<div class="copySuccess txt-center" style="display:none;">複製成功</div>'+  
				                      '<div id="dynamicAdDescription" class="copyDescription" style="display;"><small>'+
				                      '● prod_id：可填入商品ID，以建立網頁與商品的配對。<br>'+
				                      '● prod_price：可填入商品原價，廣告中會優先顯示填入的價格。<br>'+
				                      '● prod_dis：可填入商品促銷價，廣告中會優先顯示填入的價格。'+
				                      '</small></div>'+
				                      '<div class="section-box">'+
				                          '<p class="title-box h2">以電子郵件寄送代碼<small>若有多個地址請以分號分隔</small></p>'+
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
				            'scrolling'			: 'yes' 
				        }
				    );
	}
    
}



/**
* 取得再行銷追蹤清單Ajax
*/
function queryRetargetingListAjax(){
    
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
//    		alert(response.msg)
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
	    		var codeTypeDesc ="";
	    		$.each(list, function(key, val){
	    			
	    			if(key == "paId"){
	    				paId = val;
	    			}
	    			if(key == "trackingSeq"){
	    				trackingSeq = val;
	    			}
	    			if(key == "trackingName"){
	    				trackingName = val;
	    				trackingName = trackingName.replace(/ /g, "&nbsp;");
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
	    			if(key == "codeTypeDesc"){
	    				codeTypeDesc = val;
	    			}
	    		});
	    		
            		tempHtml += " <div class='txt-row txt-row-data' data-type='enable'> ";
            		tempHtml += " <div class='txt-cell col-ptagcheckbox'> ";
            		tempHtml += " 	<label class='pos-middle switch-adstatus'> ";
            		if (trackingStatus == "0"){
            			tempHtml += "	<input type='checkbox' id='"+trackingSeq+"'>";
            		}else if(trackingStatus == "1"){
            			tempHtml += "	<input type='checkbox' checked='checked' id='"+trackingSeq+ "'>";
            		}
            		tempHtml += " 		<span class='slider'></span> ";
            		tempHtml += " 	</label> ";
            		tempHtml += "	</div> ";
            		tempHtml += " <div class='txt-cell col-ptagname'><a href='editRetargetingTrackingView.html?trackingSeq="+trackingSeq+"'>"+trackingName+"</a><br><small>ID："+trackingSeq+"</small></div> ";
            		tempHtml += " <div class='txt-cell col-ptagcode'><a href='javascript:void(0)' onclick='getpTag(\""+codeType+"\",\""+paId+"\",\""+trackingSeq+"\",\""+trackingName+"\");'>取得代碼</a></div> ";
            		if (verifyStatus == "1"){
            			tempHtml += " <div class='txt-cell col-ptagstatus'><span data-certificated='true'>已認證</span></div> ";
            		}else{
            			tempHtml += " <div class='txt-cell col-ptagstatus'><span data-certificated='false'>未認證</span></div> "; 
            		}
            		tempHtml += " <div class='txt-cell col-ptagtype'>"+codeTypeDesc+"</div> ";
            		tempHtml += " <div class='txt-cell col-ptagperiod'>"+trackingRangeDate+"天</div> ";
            		tempHtml += " <div class='txt-cell col-ptagcheckbox col-delete p-none'><a href='javascript:void(0)' onclick='deleteRetargetingAjax(\""+trackingSeq+"\")'></a></div> ";
            		tempHtml += " </div> ";
	    	});
	    	$('#retargetingListDiv').html(tempHtml);
	    	
	    	
	    	//狀態Checkbox開關
	    	statusCheckboxChange();
	    	
	        //初始copy代碼
	        initClipboard();
	        
    	}
	    	//整理頁碼
	    	processPageAndTotalPage();
	    	
	    });
}


//狀態Checkbox開關
function statusCheckboxChange(){
	$('.txt-row input[type="checkbox"]').change(function(){
	    if($(this).prop('checked') == false){
	        var _com = window.confirm("若關閉狀態，將停止再行銷代碼功能");
	        if( _com == true){
	            $(this).prop('checked', false);
	            updateTrackingStatusAjax($(this).attr('id'),"0")
	        }else{
	            $(this).prop('checked', true);
	            updateTrackingStatusAjax($(this).attr('id'),"1")
	        }
	    }else{
	    	 $(this).prop('checked', true);
	         updateTrackingStatusAjax($(this).attr('id'),"1")
	    }
	});
	
//	$('.txt-row input[type="checkbox"]').change(function() {
//		    if (this.checked) { //狀態按開啟
//		    	this.setAttribute("checked", "checked");
//		    	updateTrackingStatusAjax($(this).attr('id'),"1")
//		    }else{	//狀態按關閉
//		    	if(confirm("若關閉狀態，將停止再行銷代碼功能")) {
//			    	this.removeAttribute("checked");
//			    	updateTrackingStatusAjax($(this).attr('id'),"0")
//		    	}else{
//		    		this.setAttribute("checked", "checked");
//		    	}
//		    }
//	});
}


//更新再行銷目錄狀態
function updateTrackingStatusAjax(trackingSeq,trackingStatus) {
		$.ajax({
			type : "post",
			dataType : "json",
			url : "updateTrackingStatusAjax.html",
			data : {
				"trackingSeq" : trackingSeq,
				"trackingStatus" : trackingStatus
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


//刪除再行銷ajax
function deleteRetargetingAjax(trackingSeq) {
	if(confirm("是否刪除再行銷代碼？")) {
		$.ajax({
			type : "post",
			dataType : "json",
			url : "deleteRetargetingAjax.html",
			data : {
				"currentPage" : 1,
				"pageSizeSelected" : $('#pageSizeSelect option:selected').val(),
				"trackingSeq" : trackingSeq,
				"trackingName": $('#txtProdName').val()
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
