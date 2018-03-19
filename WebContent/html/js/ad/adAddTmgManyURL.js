// 讀取遮罩預設值
var maskingConfig = {
	    message: "<img src='html/img/LoadingWait.gif' />",
	    css: {
	        padding: 0,
	        margin: 0,
	        width: '50%',
	        top: '40%',
	        left: '35%',
	        textAlign: 'center',
	        color: '#000',
	        border: '3px solid #aaa',
	        backgroundColor: '#fff',
	        cursor: 'wait'
	    }
	};

$(document).ready(function(){
	$("#storeProductURL").val(""); //如果按上一頁返回，清除資料
	
	//店家刊登商品網址欄位
	$("#storeProductURL").blur(function(){
		searchStoreProductURLAjax($("#storeProductURL").val(), "storeProductURL");
	});
	
	//確認新增按鈕點擊事件
	$("#confirmAddURLbtn").click(function(){
		searchStoreProductURLAjax($("#confirmAddURL").val(), "confirmAddURL");
	});

	//送出審核
	$('#manyURLSave').click(function(){
		var rowCount = parseInt($(".checkboxCount-up").text()); //目前選擇筆數
		if(rowCount == 0){
			alert("尚未選擇商品物件");
			return false;
		}
		
		if(!confirm("提醒您，您已選擇" + rowCount + "筆商品物件刊登，廣告將在3個工作天(周一到周五)審核完成(不含例假日)，並於廣告審核完成後開始播放")){
			return false;
		}

		var keyWordArray = [];
		$.each($("#KeywordUL li"), function( index, obj ) {
			keyWordArray.push($(obj).text());
		});
		
		var excludeKeywordULArray = [];
		$.each($("#ExcludeKeywordUL li"), function( index, obj ) {
			excludeKeywordULArray.push($(obj).text());
		});
		
		var adType = $("#adType").val();
		if(adType == "0" || adType == "1"){
			var kwLen = document.getElementsByName("keywords").length;
			if( $("#existKW").children().length == 0 ){
				if(kwLen < 2){
					$('#chkAdKeyword').text("請新增關鍵字");
					location.href="#adKeyword";
					return false;
				}
			}
			
			//檢查關建字比對方式是否有被勾選
			if(kwLen >= 2){
				if(!$("#adKeywordOpen").attr('checked') && !$("#adKeywordPhraseOpen").attr('checked') && !$("#adKeywordPrecisionOpen").attr('checked')){
					$('#chkAdKeywordOpen').text("請勾選關鍵字比對方式");
					location.href="#chkAdKeywordOpen";
					return false;
				}
			}
		}
		console.log("lookk");
//		$.blockUI({
//		    message: "<h1>製作新廣告中，請稍後...</h1>",
//		    css: {
//	            width: '500px',
//	            height: '65px',
//	            opacity: .9,
//	            border:         '3px solid #aaa',
//	            backgroundColor:'#fff',
//	            textAlign:      'center',
//	            cursor:         'wait',
//	            '-webkit-border-radius': '10px',
//	            '-moz-border-radius':    '10px'
//	        },
//	        fadeIn: 1000, 
//	        timeout:   200, 
//	        onBlock: function() {
//				// form submit
//				$("#modifyForm").attr("target", "doAdd");
//				$("#modifyForm").attr("action", "adActionManyURLAction.html");
//				$("#modifyForm").submit();
////				window.location = "doAdAdAddTmgManyUrl.html";
//				console.log("KKKKKKKKK");
//	        }
//		});
		
//		$.ajax({
//			type : "post",
//			dataType : "json",
//			url : "adConfirmFastPublishUrlAjax.html",
//			data : {
//				"adFastPublishUrlInfo" : JSON.stringify(urlInfoMap)
//			},
//			timeout : 30000,
//			error : function(xhr) {
//				//	    	$('#loadingWaitBlock').unblock();
//				alert('Ajax request 發生錯誤');
//			},
//			success : function(response, status) {
//				//	    	console.log(response);
//				window.location = "adActionFastPublishUrlViewAction.html";
//			}
//		});
		
		$.ajax({
			type : "post",
			dataType : "json",
			url : "doAdAdAddTmgManyURLAjax.html",
			data : {
				"adFastPublishUrlInfo" : JSON.stringify(urlInfoMap),
				"adGroupSeq" : $("#adGroupSeq").val(),
				"keywords" : JSON.stringify(keyWordArray),
//				"excludeKeywords" : JSON.stringify(excludeKeywordULArray),
				"adKeywordOpen" : $("#adKeywordOpen").attr("checked"),
				"adKeywordPhraseOpen" : $("#adKeywordPhraseOpen").attr("checked"),
				"adKeywordPrecisionOpen" : $("#adKeywordPrecisionOpen").attr("checked")
			},
			timeout : 30000,
			error : function(xhr) {
				alert('Ajax request 發生錯誤');
			},
			success : function(response, status) {
				if (response.status == "ERROR") {
					alert(response.msg);
				} else {
					console.log("OKOK");
//					$(location).attr('href', 'adAddFinish.html?adGroupSeq=' + $("#adGroupSeq").val());	
				}
			}
		});
		
	});
	
});

// 顯示輸入店家商品網址區塊
function showAddStoreProductURL() {
	$(".addStoreProductURL").show();
}

var page = 1; //目前頁數
var totalPage; //總頁數

var urlInfoMap = new Map(); //存放此筆資料是否被勾選
//店家刊登商品網址，查詢網址內容
function searchStoreProductURLAjax(URL, errorMsgBlock){
	//一開始先將錯誤訊息欄位清空、隱藏
	if (errorMsgBlock == "storeProductURL") {
		$("#chkStoreProductURL").hide().html("");
	} else if (errorMsgBlock == "confirmAddURL") {
		$("#chkConfirmAddURL").hide().html("");
	}
	
	//檢查輸入網址是否正確
	if(isURLInaccurate(URL, errorMsgBlock)){
		return false;
	}
	
	//顯示遮罩
	$('#loadingWaitBlock').block(maskingConfig);
	
	$.ajax({
	    type: "post",
	    dataType: "json",
	    url: "searchStoreProductURLAjax.html",
	    data: {
	    	"page": page,
	    	"pageSize": $('#pageSizeSelect').val(),
	    	"searchURL" : URL
	    },
	    timeout: 30000,
	    error: function(xhr){
	    	$('#loadingWaitBlock').unblock();
	        alert('Ajax request 發生錯誤');
	    },
	    success: function(response, status){
			if (response.status == "ERROR") {
//				alert(response.msg);
				if (errorMsgBlock == "storeProductURL") { // 輸入為店家刊登商品網址，則顯示在該區塊
					$("#chkStoreProductURL").show().html(response.msg);
				} else if (errorMsgBlock == "confirmAddURL") { // 輸入為新增商品網址
					$("#chkConfirmAddURL").show().html(response.msg);
				}
			} else {
				//查詢結果顯示
		    	$(".queryResult").show();
		    	
		    	page = response.page;
		    	totalPage = response.totalPage;

		    	processPageAndTotalPage();
		    	
		    	//依照每頁幾筆來組畫面，超過每頁幾筆數則畫面不調整
		    	if($('.dataDetailTable tr').length < parseInt($("#pageSizeSelect").val())){
		    		tempHtml = "";
		    		//處理查詢結果畫面
		    		processSearchResultViewHtml(JSON.parse(response.redisData));
		    	}
		    	
		    	/*先判斷是否有值，有值表示此網址已輸入過，則不修改flag，沒值則新增預設值。 
		    	      避免已經勾選過的值，因再次輸入，就被改掉*/
		    	if(!urlInfoMap[URL + "_ckeck_flag"]){
		    		urlInfoMap[URL + "_ckeck_flag"] = "N";		    		
		    	}
			}
			$('#loadingWaitBlock').unblock();
		}
	});
}

var tempHtml = "";
//處理查詢結果畫面
function processSearchResultViewHtml(redisData){
	$.each(redisData, function(index, list){
//		console.log(index + ":" + list);
		var sp_price = "";    //原價
		var title = "";       //標題
		var price = "";       //促銷價
		var description = ""; //描述
		var link_url = "";    //網頁連結
		var pic_url = "";     //圖片網址
		var show_url = "";    //顯示domain
		$.each(list, function(key, val){
//			console.log(key + ":" + val);
			if(key == "sp_price"){
				sp_price = val;
			}else if(key == "title"){
				title = val;
			}else if(key == "price"){
				price = val;
			}else if(key == "description"){
				description = val;
			}else if(key == "link_url"){
				link_url = val;
			}else if(key == "pic_url"){
				pic_url = val;
			}else if(key == "show_url"){
				show_url = val;
			}
        });
		
		//組每一筆資料
	    tempHtml += "<tr role='row'>";
		//checkbox區塊
	    if(urlInfoMap[link_url + "_ckeck_flag"] == "Y"){ //判斷此筆資料是否被勾選
	    	tempHtml += "	<td><input type='checkbox' id='chkN_" + index + "' name='chkN' value='" + link_url + "' onclick='checkAd(this,\"" + link_url + "\");' checked='true' ></td>";
	    }else{
	    	tempHtml += "	<td><input type='checkbox' id='chkN_" + index + "' name='chkN' value='" + link_url + "' onclick='checkAd(this,\"" + link_url + "\");'></td>";
	    }
		//廣告明細區塊
		tempHtml += "	<td height='35' class='td02'>";
		tempHtml += "		<div class='ad-mod'>";
		tempHtml += "			<div class='mod_edit'>";
		tempHtml += "				<input class='mod-button btn_edit modifyADDetailEditBtn' type='button' style='z-index:9' value='修 改'>";
		tempHtml += "				<div style='min-width: 400px;width:337px; height:85px; border:0px rgb(205,205,205) solid; padding:15px 5px 15px 5px; font-family:微軟正黑體,Arial; position:relative; '>";
		tempHtml += "					<div id='logooff_" + index + "' style='position:absolute;top:0; left:0;width:20px; height:18px; line-height:18px; background:rgb(175,175,175); cursor:pointer;' onmouseover='logoEvent(\""+index+"\","+'"mouseover"'+")'>";
		tempHtml += "						<img src='https://kdpic.pchome.com.tw/img/public/adlogo_off.png' width='20' height='18' border='0'>";
		tempHtml += "					</div>";
		tempHtml += "					<div id='logoshow_" + index + "' style='display:none;position:absolute;top:0; left:0; height:18px; line-height:18px; background:rgb(175,175,175); cursor:pointer;' onmouseout='logoEvent(\""+index+"\","+'"mouseout"'+")'>";
		tempHtml += "						<a href='https://show.pchome.com.tw' style='text-decoration:none' target='_new'><span style='font-size:12px;color:#FFF;text-shadow:-1px -1px rgb(152,152,152); padding-left:52px; background:url(https://kdpic.pchome.com.tw/img/public/adlogo_on.png) no-repeat;'>提供的廣告</span></a>";
		tempHtml += "					</div>";
		
		tempHtml += "					<a target='_blank' href='" + link_url + "' style='text-decoration:none'>";
		tempHtml += "						<div style='width:315px; height:85px; float:left;text-align:left; margin-left:12px;'>";
		tempHtml += "							<img src='" + pic_url + "' style=' width:85px; height:85px; float:left; margin-right:5px; border:0'>";
		tempHtml += "							<div>";
		tempHtml += "								<h3 style='font-size:17px; font-weight:600; line-height:20px; margin:0; padding:0; color:rgb(0,69,178); word-break:break-all; display:inline-block; word-wrap:break-word; width:225px;' class='defaultADTitle' >" + title.substring(0, 17) + "</h3>";
		tempHtml += "								<p style='font-size:12px; color:rgb(102,102,102); line-height:15px; margin:0; padding:0; word-break:break-all; display:inline-block; word-wrap:break-word; width:225px;' class='defaultADDescription' >" + description.substring(0, 36) + "</p>";
		tempHtml += "								<span style='font-size:12px; color:rgb(0,107,182); line-height:15px; margin:0; padding:0; word-break:break-all; display:inline-block; word-wrap:break-word; width:225px;' class='defaultADShowURL' >" + show_url + "</span>";
		tempHtml += "							</div>";
		tempHtml += "						</div>";
		tempHtml += "					</a>";
		
		tempHtml += "				</div>";
		tempHtml += "			</div>";
		
		
		tempHtml += "			<div class='mod_ok ad-mod-hide'>";
		tempHtml += "				<input class='mod-button btn_ok modifyADDetailOKBtn' type='button' value='確 認'>";
		tempHtml += "				<div style='width:85%; border:0px rgb(205,205,205) solid; padding:15px 5px 15px 5px; font-family:微軟正黑體, Arial; position:relative;'>";
		tempHtml += "					<div id='logooff' style='position:absolute;top:0; left:0;width:20px; height:18px; line-height:18px; background:rgb(175,175,175); cursor:pointer;' onmouseover='doOver()'>";
		tempHtml += "						<img src='https://kdpic.pchome.com.tw/img/public/adlogo_off.png' width='20' height='18' border='0'>";
		tempHtml += "					</div>";
		tempHtml += "					<div id='logoshow' style='display:none;position:absolute;top:0; left:0; height:18px; line-height:18px; background:rgb(175,175,175); cursor:pointer;' onmouseout='doOut()'>";
		tempHtml += "						<a href='https://show.pchome.com.tw' style='text-decoration:none' target='_new'><span style='font-size:12px;color:#FFF;text-shadow:-1px -1px rgb(152,152,152); padding-left:52px; background:url(https://kdpic.pchome.com.tw/img/public/adlogo_on.png) no-repeat;'>提供的廣告</span></a>";
		tempHtml += "					</div>";
		
		tempHtml += "					<div style='float:left;text-align:left; margin-left:12px;'>";
		tempHtml += "						<img src=" + pic_url + " style='width:85px; height:85px; float:left; margin-right:5px; border:0'>";
		tempHtml += "						<div style='float: left;width: 72%;'>";
		tempHtml += "							<input type='text' class='inputPlaceholderTmg' id='adTitle' name='adTitle' style='width:96%;margin: 1px 0; padding: 3px;' placeholder='" + title.substring(0, 17) + "' maxlength='17'>";
		tempHtml += "							<span style='float:right' id='spanAdTitle'></span>";
		tempHtml += "							<textarea style='width:96%;margin: 1px 0;padding: 3px;' class='inputPlaceholderTmgTextarea' id='adContent' name='adContent' maxlength='36' onkeypress='if(event.keyCode==13) return false;' placeholder='" + description.substring(0, 36) + "'></textarea>";
		tempHtml += "							<span style='float:right' id='spanAdContent'></span>";
		tempHtml += "							<input type='text' class='inputPlaceholderTmg' data-value='spanAdLinkURL' id='adShowURL' name='adShowURL' style='width: 96%;margin: 1px 0;padding: 3px;' maxlength='30' placeholder='" + show_url + "'>";
		tempHtml += "							<span style='float:right' id='spanAdShowURL'></span>";
		tempHtml += "						</div>";
		tempHtml += "					</div>";
		
		tempHtml += "				</div>";
		tempHtml += "			</div>";
		
		
		tempHtml += "		</div>";
		tempHtml += "	</td>";
		
		//連結網址
		tempHtml += "	<td class='td02 linkUrl'>" + link_url + "</td>";
		
		//原價
		tempHtml += "	<td class='td03'>"; 
		if(sp_price){ //有值則補上NT.
			tempHtml += "NT." + sp_price
		}
		tempHtml += "	</td>";
		
		//促銷價
		tempHtml += "	<td class='td03 ad-mod'>";
		tempHtml += "		<div class='mod_edit'>";
		tempHtml += "			<input class='mod-button ps btn_edit modifyPriceEditBtn' type='button' value='修 改'>";
		tempHtml += "			<div class='price_wd'>";
		if(price){ //有值則補上NT.
			tempHtml += "NT." + price
		}
		tempHtml += "			</div>";
		tempHtml += "		</div>";
		//促銷價修改欄位部分
		tempHtml += "		<div class='mod_ok ad-mod-hide'>";
		tempHtml += "			<input class='mod-button ps btn_ok modifyPriceOKBtn' type='button' value='確 認'>";
		tempHtml += "			<p class='price_wd'>NT.<input type='text' class='modifyPrice' style='width:80px;margin: 1px 0; padding: 3px;text-align: center' placeholder='" + price + "' maxlength='8'></p>";
		tempHtml += "		</div>";
		tempHtml += "	</td>";
		
		tempHtml += "</tr>";
		
		tempHtml += ""; //拿來複製用
	});
	$('.dataDetailTable').html(tempHtml);
	
	processResultViewBtn();
}

//勾選全部廣告
function checkAll() {
	//目前幾筆是未勾選
	var notCheckCount = 0;
	
	$("#tableView input[type=checkbox]").each(function(index, obj) {
		//目前迴圈繞到的此筆，是否為已勾選的，不是則做計算、勾選、改值動作
		if(urlInfoMap[$(obj).prop('value') + "_ckeck_flag"] == "N"){
			notCheckCount += 1;
			$(obj).prop('checked', true);
			urlInfoMap[$(obj).prop('value') + "_ckeck_flag"] = "Y";
		}
	});
	
	//目前勾選筆數 + 未勾選筆數
	$("[class^=checkboxCount]").text(parseInt($(".checkboxCount-up").text()) + parseInt(notCheckCount));
}

//點擊勾選廣告
function checkAd(obj, link_url) {
	//目前勾選筆數
	var checkAdCount = parseInt($(".checkboxCount-up").text());
	
	//計算數量及修改flag
	if ($(obj).prop('checked') == true) {
		checkAdCount = checkAdCount + 1;
		urlInfoMap[link_url + "_ckeck_flag"] = "Y";
	}
	if ($(obj).prop('checked') == false) {
		checkAdCount = checkAdCount - 1;
		urlInfoMap[link_url + "_ckeck_flag"] = "N";
	}
	
	// 模糊查詢
	$("[class^=checkboxCount]").text(checkAdCount);
}

//切換上下頁或每頁顯示N筆時
function changePageOrPageSizeAjax(){

	//顯示遮罩
	$('#loadingWaitBlock').block(maskingConfig);
	
	$.ajax({
	    type: "post",
	    dataType: "json",
	    url: "changePageOrPageSizeAjax.html",
	    data: {
	          "page": page,
	          "pageSize": $('#pageSizeSelect').val()
//	          "searchText" : $('#fsearchText').val() //之後開放搜尋欄位可能用到
	    },
	    timeout: 30000,
	    error: function(xhr){
	    	$('#loadingWaitBlock').unblock();
	        alert('Ajax request 發生錯誤');
	    },
	    success:function(response, status){
	    	totalPage = response.totalPage;
	    	processPageAndTotalPage();
	    	
	    	tempHtml = "";
    		//處理查詢結果畫面
    		processSearchResultViewHtml(JSON.parse(response.redisData));
    		$('#loadingWaitBlock').unblock();
		}
	});
	
}

//處理頁數與總頁數急按鈕
function processPageAndTotalPage(){
	
	//顯示目前第幾頁與總頁數
	$("#page").html(page);
	$("#totalPage").html(totalPage);
	
	//每頁顯示數量選擇
	$("#pageSizeSelect").unbind('change').change(function() {
		page = 1;
		changePageOrPageSizeAjax();
	});
	
	//每次先還原亮按鈕、手指點擊狀態
	$("#fpage").attr("src", $('#contentPath').val() + "page_first.gif").css("cursor", "pointer");
	$("#ppage").attr("src", $('#contentPath').val() + "page_pre.gif").css("cursor", "pointer");
	$("#epage").attr("src", $('#contentPath').val() + "page_end.gif").css("cursor", "pointer");
	$("#npage").attr("src", $('#contentPath').val() + "page_next.gif").css("cursor", "pointer");
	
	//最前頁(第一頁)
	$('#fpage').unbind('click').click(function () {
		page = 1;
		changePageOrPageSizeAjax();
	});

	// 上一頁
	$('#ppage').unbind('click').click(function () {
		page = page - 1;
		changePageOrPageSizeAjax();
	});

	// 下一頁
	$('#npage').unbind('click').click(function () {
		page = page + 1;
		changePageOrPageSizeAjax();
	});

	// 最末頁(依總頁數到最後頁)
	$('#epage').unbind('click').click(function () {
		page = totalPage;
		changePageOrPageSizeAjax();
	});
	
	if(totalPage == 0 || (page == 1 && totalPage == 1)){ //總頁數為0時(表示沒資料)或僅只有一頁時
		$("#fpage").attr("src", $('#contentPath').val() + "page_first_disable.gif");
		$("#ppage").attr("src", $('#contentPath').val() + "page_pre_disable.gif");
		$("#epage").attr("src", $('#contentPath').val() + "page_end_disable.gif");
		$("#npage").attr("src", $('#contentPath').val() + "page_next_disable.gif");
		$("#fpage, #ppage, #epage, #npage").unbind().css("cursor", ""); //移除事件、鼠標
	}else if(page == 1){ //目前頁數為第一頁時
		//最前頁、上一頁按鈕改為灰色
		$("#fpage").attr("src", $('#contentPath').val() + "page_first_disable.gif");
		$("#ppage").attr("src", $('#contentPath').val() + "page_pre_disable.gif");
		$("#fpage, #ppage").unbind().css("cursor", ""); //移除事件、鼠標
		$("#epage, #npage").css("cursor", "pointer"); // 下一頁、最末頁，鼠標改為點擊手指
	}else if(page == totalPage){ //目前頁數為最後一頁時
		//最末頁、下一頁按鈕改為灰色
		$("#epage").attr("src", $('#contentPath').val() + "page_end_disable.gif");
		$("#npage").attr("src", $('#contentPath').val() + "page_next_disable.gif");
		$("#epage, #npage").unbind().css("cursor", ""); //移除事件、鼠標
		$("#fpage, #ppage").css("cursor", "pointer"); // 最前頁、上一頁，鼠標改為點擊手指
	}
}

//處理查詢結果畫面的按鈕部分
function processResultViewBtn(){
	
	//修改促銷價按鈕事件
	$('.modifyPriceEditBtn').unbind('click').click(function () {
		var _thisPriceBlock = $(this).closest(".td03.ad-mod"); //促銷價欄位位置
		_thisPriceBlock.find(".modifyPrice").val(_thisPriceBlock.find(".modifyPrice").attr("placeholder"));
		$(this).parent().parent().find(".mod_ok, .mod_edit").toggleClass("ad-mod-hide");
	});
	
	//修改促銷價點確定後事件
	$('.modifyPriceOKBtn').unbind('click').click(function () {

		var _thisPriceBlock = $(this).closest(".td03.ad-mod"); //促銷價欄位位置
		
		var keyinModifyPrice = _thisPriceBlock.find(".modifyPrice").val();
		var defaultModifyPrice = _thisPriceBlock.find(".modifyPrice").attr("placeholder");
		
		//有輸入值且輸入值不是預設值
		if(keyinModifyPrice && (keyinModifyPrice != defaultModifyPrice)){

			//檢核輸入資料
			if(!isNum(keyinModifyPrice)){
				alert('只能填寫數字');
				_thisPriceBlock.find(".modifyPrice").val("");
				return false;
			}
			
			$.ajax({
			    type: "post",
			    dataType: "json",
			    url: "modifyPriceAjax.html",
			    data: {
			    	"searchURL": $(this).closest("tr").find(".linkUrl").html(),
			    	"modifyPrice": keyinModifyPrice
			    },
			    timeout: 30000,
			    error: function(xhr){
			        alert('Ajax request 發生錯誤');
			    },
			    success:function(response, status){
			    	if (response.status == "ERROR") {
			    		alert(response.msg);
			    	}else{
			    		_thisPriceBlock.find("div.price_wd").html("NT." + keyinModifyPrice);
			    		_thisPriceBlock.find(".modifyPrice").attr("placeholder", keyinModifyPrice);
			    		
			    		_thisPriceBlock.find(".mod_ok, .mod_edit").toggleClass("ad-mod-hide");
			    	}
				}
			});
			
		} else {
			_thisPriceBlock.find(".mod_ok, .mod_edit").toggleClass("ad-mod-hide");
		}
	});
	
	//修改明細資料按鈕事件
	$('.modifyADDetailEditBtn').unbind('click').click(function () {
		var _thisADDetailBlock = $(this).closest(".ad-mod"); //廣告明細欄位位置
		
		_thisADDetailBlock.find("#adTitle").val(_thisADDetailBlock.find("#adTitle").attr("placeholder"));
		var adTitleLength = _thisADDetailBlock.find("#adTitle").val().length;
		var adTitleMaxLength = _thisADDetailBlock.find("#adTitle").attr("maxlength");
		_thisADDetailBlock.find("#spanAdTitle").html("已輸入" + adTitleLength + "字，剩" + (parseInt(adTitleMaxLength) - parseInt(adTitleLength)) + "字");
		
		_thisADDetailBlock.find("#adContent").val(_thisADDetailBlock.find("#adContent").attr("placeholder"));
		var adContentLength = _thisADDetailBlock.find("#adContent").val().length;
		var adContentMaxLength = _thisADDetailBlock.find("#adContent").attr("maxlength");
		_thisADDetailBlock.find("#spanAdContent").html("已輸入" + adContentLength + "字，剩" + (parseInt(adContentMaxLength) - parseInt(adContentLength)) + "字");
		
		_thisADDetailBlock.find("#adShowURL").val(_thisADDetailBlock.find("#adShowURL").attr("placeholder"));
		var adShowURLLength = _thisADDetailBlock.find("#adShowURL").val().length;
		var adShowURLMaxLength = _thisADDetailBlock.find("#adShowURL").attr("maxlength");
		_thisADDetailBlock.find("#spanAdShowURL").html("已輸入" + adShowURLLength + "字，剩" + (parseInt(adShowURLMaxLength) - parseInt(adShowURLLength)) + "字");
		
		_thisADDetailBlock.find(".mod_ok, .mod_edit").toggleClass("ad-mod-hide");
	});
	
	//修改明細資料點確定後事件
	$('.modifyADDetailOKBtn').unbind('click').click(function () {
		var _thisADDetailBlock = $(this).closest(".ad-mod"); //廣告明細欄位位置
		
		var tempADTitle = _thisADDetailBlock.find("#adTitle").attr("placeholder");
		var tempADContent = _thisADDetailBlock.find("#adContent").attr("placeholder");
		var tempADShowURL = _thisADDetailBlock.find("#adShowURL").attr("placeholder");
		var keyinADTitle = _thisADDetailBlock.find("#adTitle").val();
		var keyinADContent = _thisADDetailBlock.find("#adContent").val();
		var keyinADShowURL = _thisADDetailBlock.find("#adShowURL").val();
		
		//其中一個欄位有異動過,則更新
		if(tempADTitle != keyinADTitle || tempADContent != keyinADContent || tempADShowURL != keyinADShowURL){
		
			$.ajax({
			    type: "post",
			    dataType: "json",
			    url: "modifyADDetailAjax.html",
			    data: {
			    	"searchURL": $(this).closest("tr").find(".linkUrl").html(),
			    	"modifyADTitle": keyinADTitle,
			    	"modifyADContent": keyinADContent,
			    	"modifyADShowURL": keyinADShowURL
			    },
			    timeout: 30000,
			    error: function(xhr){
			        alert('Ajax request 發生錯誤');
			    },
			    success:function(response, status){
			    	if (response.status == "ERROR") {
			    		alert(response.msg);
			    	}else{
			    		_thisADDetailBlock.find('.defaultADTitle').html(keyinADTitle);
			    		_thisADDetailBlock.find("#adTitle").attr("placeholder", keyinADTitle);
			    		_thisADDetailBlock.find('.defaultADDescription').html(keyinADContent);
			    		_thisADDetailBlock.find("#adContent").attr("placeholder", keyinADContent);
			    		_thisADDetailBlock.find('.defaultADShowURL').html(keyinADShowURL);
			    		_thisADDetailBlock.find("#adShowURL").attr("placeholder", keyinADShowURL);
			    		
			    		_thisADDetailBlock.find(".mod_ok, .mod_edit").toggleClass("ad-mod-hide");
			    	}
				}
			});
		
		}else{
			_thisADDetailBlock.find(".mod_ok, .mod_edit").toggleClass("ad-mod-hide");
		}
	});
	
	//處理可輸入多少字文案
	$('#adTitle, #adContent, #adShowURL').unbind('keyup').keyup(function () {
		var valLength = $(this).val().length;
		var valMaxlength = $(this).attr("maxlength");
		var id = $(this).attr("id");
		
		if(id == "adTitle"){
			$(this).parent().find("#spanAdTitle").html("已輸入" + valLength + "字，剩" + (parseInt(valMaxlength) - parseInt(valLength)) + "字");
		}else if(id == "adContent"){
			$(this).parent().find("#spanAdContent").html("已輸入" + valLength + "字，剩" + (parseInt(valMaxlength) - parseInt(valLength)) + "字");
		}else if(id == "adShowURL"){
			$(this).parent().find("#spanAdShowURL").html("已輸入" + valLength + "字，剩" + (parseInt(valMaxlength) - parseInt(valLength)) + "字");
		}
	});
	
}

//檢查輸入網址是否正確
function isURLInaccurate(URL, errorMsgBlock){
	var errMsg = "僅限PChome24h購物、商店街、個人賣場(商店街)、露天賣家網址!";
	if(URL.length == 0){ //未輸入，則不做任何事
		return true;
	}else if(!(URL.indexOf("http://24h.pchome.com.tw/", 0) > -1
			|| URL.indexOf("https://24h.pchome.com.tw/", 0) > -1
			|| URL.indexOf("24h.pchome.com.tw/", 0) > -1
			//商店街
			|| URL.indexOf("http://www.pcstore.com.tw/", 0) > -1
			|| URL.indexOf("https://www.pcstore.com.tw/", 0) > -1
			|| URL.indexOf("www.pcstore.com.tw/", 0) > -1
			//個人賣場(商店街)
			|| URL.indexOf("http://seller.pcstore.com.tw/", 0) > -1
			|| URL.indexOf("https://seller.pcstore.com.tw/", 0) > -1
			|| URL.indexOf("seller.pcstore.com.tw/", 0) > -1
			//露天商品頁
			|| URL.indexOf("http://goods.ruten.com.tw/item/show?", 0) > -1)	){
		
		if(errorMsgBlock == "storeProductURL"){ //輸入為店家刊登商品網址，則顯示在該區塊
			$("#chkStoreProductURL").show().html(errMsg);
		}else if(errorMsgBlock == "confirmAddURL"){ //輸入為新增商品網址
			$("#chkConfirmAddURL").show().html(errMsg);
		}
		return true;
	}
	
	return false;
}

//處理廣告明細，圖片左上角的Logo顯示隱藏效果 start
//廣告logo控制
function logoEvent(id, behavior) {
	if (behavior == 'mouseover') {
		$($("#logooff_" + id)[0]).css('display', 'none');
		$($("#logoshow_" + id)[0]).css('display', '');
	} else {
		$($("#logooff_" + id)[0]).css('display', '');
		$($("#logoshow_" + id)[0]).css('display', 'none');
	}
}

function doOver() {
	//showlogo
	document.getElementById('logoshow').style.display = "";
	document.getElementById('logooff').style.display = "none";
}
function doOut() {
	//hiddenlogo
	document.getElementById('logoshow').style.display = "none";
	document.getElementById('logooff').style.display = "";
}
//處理廣告明細，圖片左上角的Logo顯示隱藏效果 end

//檢查是否為數字，是數字回true
function isNum(val) {
	return /^[0-9]*$/.test(val);
}

//點擊下一步
function fastPublishNext() {
	//	console.log(JSON.stringify(urlInfoMap));
	$.ajax({
		type : "post",
		dataType : "json",
		url : "adConfirmFastPublishUrlAjax.html",
		data : {
			"adFastPublishUrlInfo" : JSON.stringify(urlInfoMap)
		},
		timeout : 30000,
		error : function(xhr) {
			//	    	$('#loadingWaitBlock').unblock();
			alert('Ajax request 發生錯誤');
		},
		success : function(response, status) {
			//	    	console.log(response);
			window.location = "adActionFastPublishUrlViewAction.html";
		}
	});

	//	$("#alex").text(urlInfo);
}